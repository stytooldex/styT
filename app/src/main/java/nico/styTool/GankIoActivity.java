package nico.styTool;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.service.quicksettings.TileService;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import dump.z.AppCompatPreferenceActivity;

public class GankIoActivity extends AppCompatPreferenceActivity  {


    public void Bi(String object) {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(object);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 准备完成 开始播放
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //MediaPlayer mPlayer = MediaPlayer.create(this, object);
        // mPlayer.start();
    }

    public static final int REQUEST_CODE = 2233;
    // 微信包名
    private static final String TENCENT_PACKAGE_NAME = "com.tencent.mm";
    // 微信二维码扫描页面地址
    private static final String TENCENT_ACTIVITY_BIZSHORTCUT = "com.tencent.mm.action.BIZSHORTCUT";
    // Extra data
    private static final String TENCENT_EXTRA_ACTIVITY_BIZSHORTCUT = "LauncherUI.From.Scaner.Shortcut";

    /**
     * 启动微信二维码扫描页
     * ps： 需要你引导用户从文件中扫描二维码
     *
     * @param activity activity
     */
    private static void gotoWeChatQrScan(@NonNull Activity activity) {
        Intent intent = new Intent(TENCENT_ACTIVITY_BIZSHORTCUT);
        intent.setPackage(TENCENT_PACKAGE_NAME);
        intent.putExtra(TENCENT_EXTRA_ACTIVITY_BIZSHORTCUT, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    private static void sendPictureStoredBroadcast(Context context, String qrSavePath) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(qrSavePath));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * 保存图片到本地，需要权限
     *
     * @param qrBitmap 二维码图片
     */
    public static void saveDonateQrImage2SDCard(@NonNull String qrSavePath, @NonNull Bitmap qrBitmap) {
        File qrFile = new File(qrSavePath);
        File parentFile = qrFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(qrFile);
            qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信捐赠
     *
     * @param activity   activity
     * @param qrSavePath 个人收款二维码，可以通过微信生成
     */
    public static void donateViaWeiXin(Activity activity, String qrSavePath) {
        if (activity == null || TextUtils.isEmpty(qrSavePath)) {
            //参数错误

            return;
        }
        sendPictureStoredBroadcast(activity, qrSavePath);
        gotoWeChatQrScan(activity);
    }

    /**
     * 判断支付宝客户端是否已安装，建议调用转账前检查
     *
     * @param context Context
     * @return 支付宝客户端是否已安装
     */
    public static boolean hasInstalledWeiXinClient(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(TENCENT_PACKAGE_NAME, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取支付宝客户端版本名称，作用不大
     *
     * @param context Context
     * @return 版本名称
     */
    public static String getWeiXinClientVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(TENCENT_PACKAGE_NAME, 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    // 支付宝包名
    private static final String ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone";

    // 旧版支付宝二维码通用 Intent Scheme Url 格式
    private static final String INTENT_URL_FORMAT = "intent://platformapi/startapp?saId=10000007&" +
            "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{payCode}%3F_s" +
            "%3Dweb-other&_t=1472443966571#Intent;" +
            "scheme=alipayqr;package=com.eg.android.AlipayGphone;end";

    /**
     * 打开转账窗口
     * 旧版支付宝二维码方法，需要使用 https://fama.alipay.com/qrcode/index.htm 网站生成的二维码
     * 这个方法最好，但在 2016 年 8 月发现新用户可能无法使用
     *
     * @param activity Parent Activity
     *                 //@param 手动解析二维码获得地址中的参数，例如 https://qr.alipay.com/aehvyvf4taua18zo6e 最后那段
     * @return 是否成功调用
     */
    public static boolean startAlipayClient(Activity activity, String payCode) {
        return startIntentUrl(activity, INTENT_URL_FORMAT.replace("{payCode}", payCode));
    }

    /**
     * 打开 Intent Scheme Url
     *
     * @param activity      Parent Activity
     * @param intentFullUrl Intent 跳转地址
     * @return 是否成功调用
     */
    public static boolean startIntentUrl(Activity activity, String intentFullUrl) {
        try {
            Intent intent = Intent.parseUri(intentFullUrl, Intent.URI_INTENT_SCHEME);
            activity.startActivity(intent);
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断支付宝客户端是否已安装，建议调用转账前检查
     *
     * @param context Context
     * @return 支付宝客户端是否已安装
     */
    public static boolean hasInstalledAlipayClient(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取支付宝客户端版本名称，作用不大
     *
     * @param context Context
     * @return 版本名称
     */
    public static String getAlipayClientVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 打开支付宝扫一扫界面
     *
     * @param context Context
     * @return 是否成功打开 Activity
     */
    public static boolean openAlipayScan(Context context) {
        try {
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (context instanceof TileService) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ((TileService) context).startActivityAndCollapse(intent);
                }
            } else {
                context.startActivity(intent);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 打开支付宝付款码
     *
     * @param context Context
     * @return 是否成功打开 Activity
     */
    public static boolean openAlipayBarcode(Context context) {
        try {
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=20000056");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (context instanceof TileService) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ((TileService) context).startActivityAndCollapse(intent);
                }
            } else {
                context.startActivity(intent);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 自定义布局B
     **/
    Preference preference30 = null;
    Context mContext = null;
    Preference preference2 = null;
    Preference preference3 = null;
    Preference pref = null;
    private boolean isGooglePlay = true;

    /**
     * 支付宝支付
     *
     * @param //payCode 收款码后面的字符串；例如：收款二维码里面的字符串为 https://qr.alipay.com/stx00187oxldjvyo3ofaw60 ，则
     *                  payCode = stx00187oxldjvyo3ofaw60
     *                  注：不区分大小写
     */
    private void donateAlipay() {
        boolean hasInstalledAlipayClient = hasInstalledAlipayClient(this);
        if (hasInstalledAlipayClient) {
            startAlipayClient(this, "FKX067078RM1IL5WOOSSD2");
        }
    }

    //payCode
    private void checkPermissionAndDonateWeixin() {
        //检测微信是否安装
        if (!hasInstalledWeiXinClient(this)) {
            Toast.makeText(this, "未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //已经有权限
            showDonateTipDialog();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    private void showDonateTipDialog() {
        new AlertDialog.Builder(this)
                .setTitle("步骤")
                .setMessage("1.点击菜单按钮\n" + "2.从相册选取二维码'\n" + "3.选择第一张二维码图片即可\n\n")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        donateWeixin();

                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 需要提前准备好 微信收款码 照片，可通过微信客户端生成
     */
    private void donateWeixin() {
        InputStream weixinQrIs = getResources().openRawResource(R.raw.weixin);
        String qrPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mple" + File.separator + "weixin.png";
        saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs));
        donateViaWeiXin(this, qrPath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            donateWeixin();
        } else {
            Toast.makeText(this, "权限被拒绝", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean ajoinQQGroupdata(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
// 未安装手Q或安装的版本不支持
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_setting);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        addPreferencesFromResource(R.xml.ganklo);
        //((PreferenceGroup) findPreference("thirdC")).removePreference(findPreference("i_teu"));//这是删除 二级节点
        //((PreferenceGroup) findPreference("third")).removePreference(findPreference("iob"));//这是删除 二级节点

        preference3 = findPreference("iob");
        preference3.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GankIoActivity.this);
                AlertDialog alert = builder.setMessage("如果你想支持妮哩可以(捐赠)\n金额请随意，给我买七彩棒棒糖")
                        .setNeutralButton("QQ捐赠", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(GankIoActivity.this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
                                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                manager.setText("2284467793");
                            }
                        }).setPositiveButton("微信捐赠", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkPermissionAndDonateWeixin();
                            }
                        }).setNegativeButton("支付宝捐赠", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                donateAlipay();
                            }
                        })
                        .create();
                alert.show();
                return false;
            }
        });

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        SharedPreferences sp = preference.getSharedPreferences();
        boolean ON_OFF = sp.getBoolean("auto_send_message", false);
        boolean next_screen = sp.getBoolean("next_screen_checkbox_preference", false);
        String text = sp.getString("auto_send_message_text", "");
        String listtext = sp.getString("auto_send_message_frequency", "");
        return true;
    }
}
