package dump_dex.service;


import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakurademo.BaseActivity;
import com.bilibili.magicasakurademo.ColorsListAdapter;
import com.bilibili.magicasakurademo.MyThemeUtils;
import com.bilibili.magicasakurademo.PreferenceUtils;
import com.bumptech.glide.Glide;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import dump.a.MyAdapter;
import dump.a.ShellUtils;
import dump.b.LEDActivity;
import dump.b.Main3Activity;
import dump.c.bilI;
import dump.f.xp;
import dump.j.o;
import dump.k.i_a;
import dump.o.SharedPreferencesUtil;
import dump.t.addpr1;
import dump.y.x5_MainActivity;
import dump.z.Main2Activity;
import dump_dex.Activity.ScrollingActivity;
import dump_dex.root.rootActivity;
import nico.Compat.CompatActivity;
import nico.GetPathFromUri4kitkat;
import nico.styTool.Adapter;
import nico.styTool.ChoseImgActivity;
import nico.styTool.ChoseImg_styT;
import nico.styTool.Constant;
import nico.styTool.FJActivity;
import nico.styTool.GankIoActivity;
import nico.styTool.MActivity;
import nico.styTool.MainActivity;
import nico.styTool.MeiziActivity;
import nico.styTool.MyUser;
import nico.styTool.NativeConfigStore;
import nico.styTool.ProperTies;
import nico.styTool.ProviderUi;
import nico.styTool.R;
import nico.styTool.RobotChatActivity;
import nico.styTool.StatusBarUtil;
import nico.styTool.ToastUtil;
import nico.styTool.Update;
import nico.styTool.UserProfileActivity;
import nico.styTool.Viewhtml;
import nico.styTool.WelcomeActivity;
import nico.styTool.api_o;
import nico.styTool.app_th;
import nico.styTool.buff_ext;
import nico.styTool.lua;
import nico.styTool.sizeActivity;
import nico.styTool.smali_layout_apktool;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

public class ws_Main3Activity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    //showIntent(Main4Activity.class);
    private SharedPreferences sp;

    private String url = null;
    private String data = null;
    //private Intent intent = null;
    private CompositeSubscription mCompositeSubscription;

    /**
     * 解决Subscription内存泄露问题
     *
     * @param s
     */
    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    private static final int ANIM_TIME = 2000;

    private static final float SCALE_END = 1.15F;

    private static final int[] Imgs = {R.drawable.welcomimg1, R.drawable.welcomimg2, R.drawable.welcomimg3, R.drawable.welcomimg4, R.drawable.welcomimg5};

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws__main3);


        boolean isF = (boolean) nico.SPUtils.get(this, "if_ThemeS", false);
        if (isF) {
            showIntent(nico.Compat.GirlFragment.class);
        } else {
            boolean isF0 = (boolean) nico.SPUtils.get(this, "if_ThemeS_", false);
            if (isF0) {
                pay();
            } else {

            }
        }
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final android.support.v7.widget.AppCompatSpinner spinner_s = (android.support.v7.widget.AppCompatSpinner) findViewById(R.id.spinner_text);
        final EditText editText = (EditText) findViewById(R.id.et_input);
        // 利用键盘的按钮搜索
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(ws_Main3Activity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    if (TextUtils.isEmpty(editText.getText())) {
                        Toast.makeText(ws_Main3Activity.this, "URL不能为空!", Toast.LENGTH_LONG).show();
                    } else if (Patterns.WEB_URL.matcher(editText.getText()).matches()) {
                        Properties proper = ProperTies.getProperties(getApplicationContext());
                        switch ((String) spinner_s.getSelectedItem()) {
                            case "接口一":
                                data = proper.getProperty("url01");
                                break;
                            case "接口二":
                                data = proper.getProperty("url02");
                                break;
                            case "接口三":
                                data = proper.getProperty("url03");
                                break;
                            case "接口四":
                                data = proper.getProperty("url04");
                                break;
                            case "接口五":
                                data = proper.getProperty("url05");
                                break;
                            case "接口六":
                                data = proper.getProperty("url06");
                                break;
                            case "接口七":
                                data = proper.getProperty("url07");
                                break;
                            case "接口八":
                                data = proper.getProperty("url08");
                                break;
                            case "接口九":
                                data = proper.getProperty("url09");
                                break;
                            case "接口十":
                                data = proper.getProperty("url10");
                                break;
                            case "接口十一":
                                data = proper.getProperty("url11");
                                break;
                            case "接口十二":
                                data = proper.getProperty("url12");
                                break;
                            case "接口十三":
                                data = proper.getProperty("url13");
                                break;
                            case "接口十四":
                                data = proper.getProperty("url14");
                                break;
                        }
                        url = data + editText.getText().toString();
                        Intent intent = new Intent();
                        intent.setClass(ws_Main3Activity.this, x5_MainActivity.class);
                        intent.putExtra("fio", url);
                        startActivity(intent);

                    } else {
                        Toast.makeText(ws_Main3Activity.this, "URL错误,请重新输入!", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(ws_Main3Activity.this);
                View view_s = getLayoutInflater().inflate(R.layout.bottomheets, null);

                Button fab_1 = (Button) view_s.findViewById(R.id.idbutton1);
                fab_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater = LayoutInflater.from(ws_Main3Activity.this);
                        View zview = inflater.inflate(R.layout.bilibilic, null);
                        final EditText ediComment1 = zview.findViewById(R.id.bilibilicEditText1);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ws_Main3Activity.this);
                        builder.setView(zview)
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).setPositiveButton("生成", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String comment = ediComment1.getText().toString().trim();
                                if (TextUtils.isEmpty(comment)) {
                                    ToastUtil.show(ws_Main3Activity.this, "我欲成仙", Toast.LENGTH_SHORT);
                                    return;
                                }
                                SharedPreferences mySharedPreferences = getSharedPreferences(Constant.android, AppCompatActivity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mySharedPreferences.edit();
                                editor.putString(Constant.id, "http://www.baidu-x.com/?q=" + comment);
                                editor.apply();

                                AlertDialog.Builder builder = new AlertDialog.Builder(ws_Main3Activity.this);
                                AlertDialog alertDialog = builder.setMessage("请选择").setCancelable(false)
                                        .setNegativeButton("复制", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                SharedPreferences sharedPreferences = getSharedPreferences(Constant.android, AppCompatActivity.MODE_PRIVATE);
                                                String aname = sharedPreferences.getString(Constant.id, "");
                                                ClipboardManager manager = (ClipboardManager) getSystemService(android.support.v4.app.FragmentActivity.CLIPBOARD_SERVICE);
                                                manager.setText(aname + "");
                                            }
                                        }).setPositiveButton("打开", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                SharedPreferences sharedPreferences = getSharedPreferences(Constant.android, AppCompatActivity.MODE_PRIVATE);
                                                String aname = sharedPreferences.getString(Constant.id, "");
                                                Uri uri = Uri.parse(aname);
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            }
                                        }).create();
                                alertDialog.show();

                            }

                        }).setCancelable(false).create().show();
                    }
                });
                Button fab_2 = (Button) view_s.findViewById(R.id.idbutton2);
                fab_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showIntent(nico.Compat.GirlFragment.class);
                    }
                });
                Button fab_3 = (Button) view_s.findViewById(R.id.idbutton3);
                fab_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showIntent(CompatActivity.class);
                    }
                });
                nico.styTool.GridRecyclerView recyclerView = (nico.styTool.GridRecyclerView) view_s.findViewById(R.id.listview_s);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                GridLayoutManager manager = new GridLayoutManager(ws_Main3Activity.this, 3);
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (3 - position % 3);
                    }
                });
                boolean isF1 = (boolean) nico.SPUtils.get(ws_Main3Activity.this, "if_ThemeS0", false);
                if (isF1) {
                    recyclerView.setLayoutManager(manager);
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(ws_Main3Activity.this, 2)); // 设置布局管理器 GridView
                }

                recyclerView.setAdapter(new Adapter(createItems()));
                Adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, String text) {
                        switch (position) {
                            case 0:
                                showIntent(FJActivity.class);
                                break;
                            case 1:
                                showIntent(ProviderUi.class);
                                break;
                            case 2:
                                showIntent(Viewhtml.class);
                                break;
                            case 3:
                                showIntent(Main3Activity.class);
                                break;
                            case 4:
                                LayoutInflater inflater = LayoutInflater.from(ws_Main3Activity.this);
                                View view0 = inflater.inflate(R.layout.dex_y, null);
                                final TextView ediComment1 = view0.findViewById(R.id.dexyTextView1);

                                StringBuilder phoneInfo = new StringBuilder();
                                phoneInfo.append("Product: ").append(Build.PRODUCT).append(System.getProperty("line.separator"));
                                phoneInfo.append("CPU_ABI: ").append(Build.CPU_ABI).append(System.getProperty("line.separator"));
                                phoneInfo.append("TAGS: ").append(Build.TAGS).append(System.getProperty("line.separator"));
                                phoneInfo.append("VERSION_CODES.BASE: " + Build.VERSION_CODES.BASE).append(System.getProperty("line.separator"));
                                phoneInfo.append("MODEL: ").append(Build.MODEL).append(System.getProperty("line.separator"));
                                phoneInfo.append("SDK: ").append(Build.VERSION.SDK).append(System.getProperty("line.separator"));
                                phoneInfo.append("VERSION.RELEASE: ").append(Build.VERSION.RELEASE).append(System.getProperty("line.separator"));
                                phoneInfo.append("DEVICE: ").append(Build.DEVICE).append(System.getProperty("line.separator"));
                                phoneInfo.append("DISPLAY: ").append(Build.DISPLAY).append(System.getProperty("line.separator"));
                                phoneInfo.append("BRAND: ").append(Build.BRAND).append(System.getProperty("line.separator"));
                                phoneInfo.append("BOARD: ").append(Build.BOARD).append(System.getProperty("line.separator"));
                                phoneInfo.append("FINGERPRINT: ").append(Build.FINGERPRINT).append(System.getProperty("line.separator"));
                                phoneInfo.append("ID: ").append(Build.ID).append(System.getProperty("line.separator"));
                                phoneInfo.append("MANUFACTURER: ").append(Build.MANUFACTURER).append(System.getProperty("line.separator"));
                                phoneInfo.append("USER: ").append(Build.USER).append(System.getProperty("line.separator"));
                                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                                phoneInfo.append("DeviceId(IMEI) = ").append(tm.getDeviceId()).append(System.getProperty("line.separator"));
                                phoneInfo.append("DeviceSoftwareVersion = ").append(tm.getDeviceSoftwareVersion()).append(System.getProperty("line.separator"));
                                phoneInfo.append("NetworkCountryIso = ").append(tm.getNetworkCountryIso()).append(System.getProperty("line.separator"));
                                phoneInfo.append("NetworkOperator = ").append(tm.getNetworkOperator()).append(System.getProperty("line.separator"));
                                phoneInfo.append("NetworkOperatorName = ").append(tm.getNetworkOperatorName()).append(System.getProperty("line.separator"));
                                phoneInfo.append("NetworkType = ").append(tm.getNetworkType()).append(System.getProperty("line.separator"));
                                phoneInfo.append("PhoneType = ").append(tm.getPhoneType()).append(System.getProperty("line.separator"));
                                phoneInfo.append("SimOperatorName = ").append(tm.getSimOperatorName()).append(System.getProperty("line.separator"));
                                phoneInfo.append("SimState = ").append(tm.getSimState()).append(System.getProperty("line.separator"));
                                ediComment1.setText("" + phoneInfo);
                                //"SD卡总大小：" + getSDTotalSize() + " SD卡剩余大小：" + getSDAvailableSize() + "\n" + "手机内存总大小：" + getRomTotalSize() + "手机内存剩余大小：" + getRomAvailableSize() +
                                AlertDialog.Builder builder = new AlertDialog.Builder(ws_Main3Activity.this);
                                builder.setView(view0).create().show();
                                break;
                            case 5:
                                showIntent(MActivity.class);
                                break;
                            case 6:
                                MyUser myUserw = BmobUser.getCurrentUser(MyUser.class);
                                if (myUserw != null) {
                                    Intent inten0t = new Intent(ws_Main3Activity.this, Update.class);
                                    startActivity(inten0t);
                                } else {
                                    Intent inte = new Intent(ws_Main3Activity.this, app_th.class);
                                    startActivity(inte);
                                    Toast.makeText(ws_Main3Activity.this, "需要登录帐号同步到云端", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 7:
                                showIntent(RobotChatActivity.class);
                                break;
                            case 8:
                                showIntent(ChoseImg_styT.class);
                                break;
                            case 9:
                                showIntent(LEDActivity.class);
                                break;
                            case 10:
                                showIntent(nico.styTool.gifa.class);
                                break;
                            case 11:
                                showIntent(lua.class);
                                break;
                            case 12:
                                LayoutInflater infl = LayoutInflater.from(ws_Main3Activity.this);
                                View vie = infl.inflate(R.layout.a_ftp, null);
                                AlertDialog.Builder bu = new AlertDialog.Builder(ws_Main3Activity.this);
                                bu.setView(vie).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create().show();
                                break;
                            case 13:
                                LayoutInflater inflate = LayoutInflater.from(ws_Main3Activity.this);
                                View vie0 = inflate.inflate(R.layout.item_feedback, null);

                                //指日
                                CheckBox as = vie0.findViewById(R.id.itemfeedbackCheckBox1);//回复
                                CheckBox ai = vie0.findViewById(R.id.itemfeedbackCheckBox2);//QQ
                                CheckBox an = vie0.findViewById(R.id.itemfeedbackCheckBox3);//微信
                                Button fil = vie0.findViewById(R.id.b_nl);
                                Button fio = vie0.findViewById(R.id.b_as);
                                SharedPreferences sharedPreferences = getSharedPreferences("hook_Cosplay", AppCompatActivity.MODE_PRIVATE);
                                String i = sharedPreferences.getString("io_kii", "");

                                sp = getSharedPreferences("nico.styTool_preferences", Context.MODE_PRIVATE);
                                boolean isProtecting = sp.getBoolean("ok_a", false);
                                if (isProtecting) {
                                    as.setChecked(true);
                                }
                                as.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putBoolean("ok_a", true);
                                            editor.apply();
                                        } else {
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putBoolean("ok_a", false);
                                            editor.apply();
                                        }
                                    }
                                });
                                sp = getSharedPreferences("nico.styTool_preferences", Context.MODE_PRIVATE);
                                boolean sProtecting = sp.getBoolean("ok_b", false);
                                if (sProtecting) {
                                    ai.setChecked(true);
                                }
                                ai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putBoolean("ok_b", true);
                                            editor.apply();
                                        } else {
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putBoolean("ok_b", false);
                                            editor.apply();
                                        }
                                    }
                                });
                                sp = getSharedPreferences("nico.styTool_preferences", Context.MODE_PRIVATE);
                                boolean Protecting = sp.getBoolean("ok_c", false);
                                if (Protecting) {
                                    an.setChecked(true);
                                }
                                an.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putBoolean("ok_c", true);
                                            editor.apply();
                                        } else {
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putBoolean("ok_c", false);
                                            editor.apply();
                                        }
                                    }
                                });


                                fil.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v8) {
                                        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                                        startActivity(intent);
                                    }
                                });
                                fio.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v8) {
                                        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                        startActivity(intent);
                                    }
                                });

                                AlertDialog.Builder builde = new AlertDialog.Builder(ws_Main3Activity.this);
                                builde.setView(vie0)
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).setPositiveButton("开放模式", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //ToastUtil.show(getActivity(), "适配QQ|微信", Toast.LENGTH_SHORT);
                                    }
                                }).setCancelable(false).create().show();
                                break;
                            case 14:
                                showIntent(dump.d.MainActivity.class);
                                break;

                        }
                        //Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                        //dialog.dismiss();
                    }
                });

                dialog.setContentView(view_s);
                dialog.setTitle("open in");
                dialog.show();
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        if (getClipboardText().startsWith("http://") || getClipboardText().startsWith("https://")) {
            android.support.design.widget.CoordinatorLayout container = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.maibiiii0);
            Snackbar.make(container, "发现复制链接", Snackbar.LENGTH_LONG).setAction("打开链接", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            })
                    .show();

        } else {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Random random = new Random(SystemClock.elapsedRealtime());
        ImageView dr = (ImageView) findViewById(R.id.fruit_image_view);
        dr.setImageResource(Imgs[random.nextInt(Imgs.length)]);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final SharedPreferences i = getSharedPreferences("Hello511", 0);
        Boolean o0 = i.getBoolean("FIRST", true);
        if (o0) {//第一次
            AppCompatDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle(Constant.a_mi)
                    .setMessage(Constant.a_miui)
                    .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            i.edit().putBoolean("FIRST", false).apply();
                            makeRootDirectory(Environment.getExternalStorageDirectory().getPath() + "/Android/styTool");
                            makeRootDirectory(Environment.getExternalStorageDirectory().getPath() + "/datastyTool");
                            makeRootDirectory(Environment.getExternalStorageDirectory().getPath() + "/Teif");

                            toolbar.setSubtitle("乍见之欢，久处不厌");
                            //toolbar.setTitle("乍见之欢，久处不厌");
                            /*
                            ViewTooltip
                                    .on(fab)
                                    .autoHide(false, 1000)
                                    .position(ViewTooltip.Position.TOP)
                                    .corner(4)
                                    .align(ViewTooltip.ALIGN.CENTER)
                                    .clickToHide(true)
                                    .text("可以展开部分功能")
                                    .show();
                             */

                            new MaterialTapTargetPrompt.Builder(ws_Main3Activity.this)
                                    .setTarget(spinner_s)
                                    .setPrimaryText("提示")
                                    .setSecondaryText("输入爱奇视频艺链接选择需要的接口回车{推荐12接口}")
                                    .setPromptBackground(new RectanglePromptBackground())
                                    .setPromptFocal(new RectanglePromptFocal())
                                    .show();

                        }
                    }).setCancelable(false).create();
            alertDialog.show();
        } else {
            Toast.makeText(ws_Main3Activity.this, "主储存" + nico.FileUtils.getSDTotalSize(this) + " / 可用:" + nico.FileUtils.getSDAvailableSize(this), Toast.LENGTH_SHORT).show();
            //toolbar.setSubtitle("主储存" + nico.FileUtils.getSDTotalSize(this) + " / 可用:" + nico.FileUtils.getSDAvailableSize(this));
            boolean sFirstRun = (boolean) nico.SPUtils.get(this, "if_BI", false);
            if (sFirstRun) {

                BmobQuery<o> query = new BmobQuery<>();
                query.getObject("b44e161878", new QueryListener<o>() {

                    @Override
                    public void done(o movie, BmobException e) {
                        if (e == null) {
                            Bi(movie.getContent());
                        } else {

                        }
                    }
                });
            } else {
            }
            final SharedPreferences dl = getSharedPreferences("Hello666", 0);
            Boolean ddi = dl.getBoolean("FIRST", true);
            if (ddi) {//第一次
                FileDownloader.init(ws_Main3Activity.this);
                LayoutInflater inflater = LayoutInflater.from(ws_Main3Activity.this);
                View view2 = inflater.inflate(R.layout.ebcpos, null);
                final ProgressBar ediComment1 = view2.findViewById(R.id.bbilibili1);
                Button fab_2 = (Button) view2.findViewById(R.id.button6);
                fab_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "http://hdyhq.yicuba.com/tongji/chaquanbao-nili.apk";
                        FileDownloader.getImpl().create(url)
                                .setPath(Environment.getExternalStorageDirectory().getPath() + "/" + "nico.apk")
                                .setListener(new FileDownloadListener() {
                                    @Override
                                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                        AddNotification(81, "等待下载中", "下载");
                                    }

                                    @Override
                                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                                        AddNotification(81, "连接中", "下载");
                                    }

                                    @Override
                                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                        ediComment1.setProgress(soFarBytes);
                                        DecimalFormat df = new DecimalFormat("######0.00");
                                        AddNotification(81, df.format((float) soFarBytes / 1024f / 1024f) + "MB" + "/" + df.format(((float) totalBytes / 1024f / 1024f)) + "MB", "下载", totalBytes, soFarBytes);
                                    }

                                    @Override
                                    protected void blockComplete(BaseDownloadTask task) {
                                    }

                                    @Override
                                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                                    }

                                    @Override
                                    protected void completed(BaseDownloadTask task) {
                                        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                                        AddNotification(81, "下载完成", "下载");
                                        installNormal(Environment.getExternalStorageDirectory().getAbsolutePath() + "/nico.apk");
                                        dl.edit().putBoolean("FIRST", false).apply();
                                        addpr1 feedback = new addpr1();
                                        feedback.setContent(tm.getDeviceId() + " null");
                                        feedback.save(new SaveListener<String>() {

                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {

                                                } else {

                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                    }

                                    @Override
                                    protected void error(BaseDownloadTask task, Throwable e) {
                                        AddNotification(81, "下载错误", "下载");
                                    }

                                    @Override
                                    protected void warn(BaseDownloadTask task) {
                                    }
                                }).start();
                    }
                });
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ws_Main3Activity.this);
                builder1.setView(view2)
                        .setTitle("AdapterView")
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("不再弹出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ws_Main3Activity.this, "下载安装后就不再弹出了", Toast.LENGTH_SHORT).show();
                    }

                }).setCancelable(false).create().show();
            } else {
            }
            xft();
        }
        View headerView = navigationView.getHeaderView(0);

        android.support.v7.widget.AppCompatButton spinner = (android.support.v7.widget.AppCompatButton) headerView.findViewById(R.id.spinner1);
        final SharedPreferences ip = getSharedPreferences("Hello511p", 0);
        Boolean o0p = ip.getBoolean("FIRST", true);
        if (o0p) {//第一次
            ip.edit().putBoolean("FIRST", false).apply();

        } else {
        }
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.languages);
                //Toast.makeText(ws_Main3Activity.this, "你点击的是:" + languages[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });   */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH)

        {
            SharedPreferencesUtil.putData("SDK_INT", "14");
        } else

        {
            StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, drawer, 0);
        }

        nico.styTool.GridRecyclerView mRecyclerView = (nico.styTool.GridRecyclerView) findViewById(R.id.lbili_s);
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ws_Main3Activity.this, LinearLayoutManager.VERTICAL, false);
        //mRecyclerView.setLayoutManager(mLayoutManager);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()

        {
            @Override
            public int getSpanSize(int position) {
                return (3 - position % 3);
            }
        });
        boolean isF0 = (boolean) nico.SPUtils.get(this, "if_ThemeS0", false);
        if (isF0)

        {
            mRecyclerView.setLayoutManager(manager);
        } else

        {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 设置布局管理器 GridView
        }

        mRecyclerView.setAdapter(new

                MyAdapter(getData()));
        MyAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener()

        {
            @Override
            public void onItemClick(View view, int position) {
                TextView titleq = view.findViewById(R.id.item_tv_);
                String tvq = titleq.getText().toString();
                ///////
                if (tvq.equals("清除本机全部缓存非root")) {
                    Intent intent8 = new Intent(ws_Main3Activity.this, MainActivity.class);
                    startActivity(intent8);

                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "清除本机全部缓存非root");
                }
                if (tvq.equals("网易云音乐启动背景替换")) {
                    final String[] os = {"替换启动界图片", "恢复默认"};
                    AlertDialog.Builder builder8 = new AlertDialog.Builder(ws_Main3Activity.this);
                    AlertDialog alert8 = builder8.setTitle("操作")
                            .setItems(os, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Intent a = new Intent(ws_Main3Activity.this, sizeActivity.class);
                                            startActivity(a);
                                            break;
                                        case 1:
                                            GetPathFromUri4kitkat.deleteDirectory(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/netease/cloudmusic/Ad");
                                            break;
                                    }
                                    ////showToast("你选择了" + os[which]);
                                }
                            }).create();
                    alert8.show();
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "网易云音乐启动背景替换");
                }
                if (tvq.equals("文字加密解密")) {
                    AlertDialog.Builder uilder = new AlertDialog.Builder(ws_Main3Activity.this);
                    AlertDialog lertDialog = uilder.setMessage("请选择操作")
                            .setNegativeButton("密文", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent1 = new Intent(ws_Main3Activity.this, NativeConfigStore.class);
                                    intent1.putExtra("#", "A");
                                    startActivity(intent1);
                                }
                            }).setPositiveButton("unicode与中文转换", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent1 = new Intent(ws_Main3Activity.this, NativeConfigStore.class);
                                    intent1.putExtra("#", "Ab");
                                    startActivity(intent1);
                                }
                            }).create();
                    lertDialog.show();
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "文字加密解密（转码）");
                }
                if (tvq.equals("解除（MIUI）授权25秒")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ws_Main3Activity.this);
                    AlertDialog alertDialog = builder.setMessage("方法：开启>找到妮**权\n一般在右上角点开启就可以了\n现在只适配6.0系统7.0系统再等等").setCancelable(false)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setPositiveButton("开启", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface d, int which) {
                                    Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                    startActivity(intent);
                                }
                            }).create();
                    alertDialog.show();
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "解除（MIUI助手）授权25秒");
                }
                if (tvq.equals("强制发起临时会话")) {
                    LayoutInflater inflater = LayoutInflater.from(ws_Main3Activity.this);
                    View view1 = inflater.inflate(R.layout.b_bilibili, null);
                    final EditText ediComment1 = view1.findViewById(R.id.bbilibiliEditText1);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ws_Main3Activity.this);
                    builder1.setView(view1)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setPositiveButton("发起会话", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String comment = ediComment1.getText().toString().trim();
                            if (TextUtils.isEmpty(comment)) {
                                return;
                            }
                            Intent intent = new Intent();
                            intent.setData(Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + comment));
                            startActivity(intent);
                        }

                    }).setCancelable(false).create().show();
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "强制发起临时会话");
                }
                if (tvq.equals("root工具")) {
                    Intent w = new Intent(ws_Main3Activity.this, rootActivity.class);
                    startActivity(w);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "root工具");
                }
                if (tvq.equals("QQ空间蓝色动态")) {
                    LayoutInflater inflater2 = LayoutInflater.from(ws_Main3Activity.this);
                    View view2 = inflater2.inflate(R.layout.bilibilib, null);
                    final EditText ediComment12 = view2.findViewById(R.id.bilibilibEditText1);

                    final EditText ediComment = view2.findViewById(R.id.bilibilibEditText2);
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(ws_Main3Activity.this);
                    builder2.setView(view2)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setPositiveButton("生成", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String commentx = ediComment.getText().toString().trim();
                            String comment = ediComment12.getText().toString().trim();
                            if (TextUtils.isEmpty(comment)) {
                                // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
                                return;
                            }

                            ClipboardManager manager = (ClipboardManager) ws_Main3Activity.this.getSystemService(android.support.v4.app.FragmentActivity.CLIPBOARD_SERVICE);
                            manager.setText("{uin:" + commentx + ",nick:+" + comment + ",who:1}");
                            ToastUtil.show(ws_Main3Activity.this, "复制成功！", Toast.LENGTH_SHORT);

                        }

                    }).setCancelable(false).create().show();
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "QQ空间蓝色动态");
                }
                if (tvq.equals("QQ|微信卡屏")) {
                    AlertDialog.Builder buiider = new AlertDialog.Builder(ws_Main3Activity.this);
                    AlertDialog alert = buiider.setMessage("打开QQ想要刷的群或QQ并长按输入框粘贴\n发送\n或者使用 直接发送")

                            .setNeutralButton("直接发送", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("复制卡屏【新】", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setPositiveButton("复制刷屏【原】", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ClipboardManager manager = (ClipboardManager) ws_Main3Activity.this.getSystemService(android.support.v4.app.FragmentActivity.CLIPBOARD_SERVICE);
                                    manager.setText("by:styTool\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                                    Toast.makeText(ws_Main3Activity.this, "复制成功", Toast.LENGTH_SHORT).show();

                                }
                            }).create();
                    alert.show();
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "QQ|微信卡屏");
                }
                if (tvq.equals("应用管理器")) {
                    Intent li = new Intent(ws_Main3Activity.this, buff_ext.class);
                    startActivity(li);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "应用管理器");
                }
                if (tvq.equals("蓝字代码")) {
                    Intent intent5 = new Intent(ws_Main3Activity.this, MeiziActivity.class);
                    startActivity(intent5);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "蓝字代码");
                }
                if (tvq.equals("QQ名片赞")) {
                    LayoutInflater inflater3 = LayoutInflater.from(ws_Main3Activity.this);
                    View view3 = inflater3.inflate(R.layout.a_gey, null);
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(ws_Main3Activity.this);
                    builder3.setView(view3)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS));

                        }
                    }).setCancelable(false).create().show();
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "QQ名片赞");
                }
                if (tvq.equals("提取APK")) {
                    Intent b = new Intent(ws_Main3Activity.this, dump.x.Main.class);
                    startActivity(b);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "提取APK");
                }
                if (tvq.equals("浮窗助手")) {
                    Intent _li = new Intent(ws_Main3Activity.this, api_o.class);
                    startActivity(_li);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "浮窗助手");
                }
                if (tvq.equals("百度云直链提取")) {
                    Intent as = new Intent(ws_Main3Activity.this, Main2Activity.class);
                    as.putExtra("#", "https://pan.baidu.com/");
                    startActivity(as);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "百度云直链提取(不限速)");
                }

                if (tvq.equals("免费看vip付费电视")) {
                    Intent inten = new Intent(ws_Main3Activity.this, Main2Activity.class);
                    inten.putExtra("#", "http://m.iqiyi.com/");
                    startActivity(inten);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "免费看vip付费电视");
                }
                if (tvq.equals("桌面动态壁纸")) {
                    Intent intent2 = new Intent(ws_Main3Activity.this, nico.styTool.z.class);
                    startActivity(intent2);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "桌面动态壁纸");
                }
                if (tvq.equals("以图搜图")) {
                    Intent as = new Intent(ws_Main3Activity.this, Main2Activity.class);
                    as.putExtra("#", "http://shitu.baidu.com/");
                    startActivity(as);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "以图搜图");
                }
                if (tvq.equals("三星设备修改dpi")) {
                    a();
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "三星设备[noroot]修改dpi");
                }
                if (tvq.equals("多进制转换")) {
                    Intent ans = new Intent(ws_Main3Activity.this, ScrollingActivity.class);
                    startActivity(ans);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "多进制转换");
                }
                if (tvq.equals("强制清除RAM内存")) {
                    Intent ans1 = new Intent(ws_Main3Activity.this, smali_layout_apktool.class);
                    startActivity(ans1);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "强制清除RAM内存");
                }
                if (tvq.equals("文件校验修改")) {
                    Intent ans10 = new Intent(ws_Main3Activity.this, nico.styTool.iApp.class);
                    startActivity(ans10);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "文件校验修改");
                }
                if (tvq.equals("哔哩哔哩封面获取")) {
                    Intent ats = new Intent(ws_Main3Activity.this, bilI.class);
                    startActivity(ats);
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "嗶哩嗶哩封面");
                }
                /*
                if (tvq.equals("票房查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "票房查询");
                    showIntent(BoxOfficeAPIActivity.class);
                }
                if (tvq.equals("菜谱查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "菜谱查询");
                    showIntent(CookAPIActivity.class);
                }
                if (tvq.equals("邮编查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "邮编查询");
                    showIntent(PostcodeAPIActivity.class);
                }
                if (tvq.equals("手机号码查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "手机号码查询");
                    showIntent(MobileAPIActivity.class);
                }
                if (tvq.equals("身份证信息查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "身份证信息查询");
                    showIntent(IDCardAPIActivity.class);
                }
                if (tvq.equals("手机号码查吉凶")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "手机号码查吉凶");
                    showIntent(MobileLuckyAPIActivity.class);
                }
                if (tvq.equals("银行卡信息查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "银行卡信息查询");
                    showIntent(BankCardAPIActivity.class);
                }
                if (tvq.equals("万年历查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "万年历查询");
                    showIntent(CalendarAPIActivity.class);
                }
                if (tvq.equals("老黄历信息查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "老黄历信息查询");
                    showIntent(LaoHuangLiAPIActivity.class);
                }
                if (tvq.equals("健康知识查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "健康知识查询");
                    showIntent(HealthAPIActivity.class);
                }
                if (tvq.equals("婚姻匹配查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "婚姻匹配查询");
                    showIntent(MarriageAPIActivity.class);
                }
                if (tvq.equals("历史上的今天查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "历史上的今天查询");
                    showIntent(HistoryAPIActivity.class);
                }
                if (tvq.equals("周公解梦查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "周公解梦查询");
                    showIntent(DreamAPIActivity.class);
                }
                if (tvq.equals("成语查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "成语查询");
                    showIntent(IdiomAPIActivity.class);
                }
                if (tvq.equals("新华字典查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "新华字典查询");
                    showIntent(DictionaryAPIActivity.class);
                }
                if (tvq.equals("算八字查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "算八字查询");
                    showIntent(HoroscopeAPIActivity.class);
                }
                if (tvq.equals("各省今日油价")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "各省今日油价");
                    showIntent(OilPriceAPIActivity.class);
                }
                if (tvq.equals("中国彩票开奖结果")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "中国彩票开奖结果");
                    showIntent(LotteryAPIActivity.class);
                }
                if (tvq.equals("黄金数据查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "黄金数据查询");
                    showIntent(GoldAPIActivity.class);
                }
                if (tvq.equals("汇率数据查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "汇率数据查询");
                    showIntent(ExchangeAPIActivity.class);
                }
                if (tvq.equals("快递查询")) {
                    //nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "快递查询");
                    showIntent(dump_dex.Activity.MainActivity.class);
                }*/
                if (tvq.equals("在线查询功能")) {
                    ////nico.FileUtils.createIfNotExist(ws_Main3Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "天气预报查询");
                    showIntent(com.mob.mobapi.sample.MainActivity.class);
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
                //   TextView title = view.findViewById(R.id.item_tv);
                // String tv = title.getText().toString();
            }
        });

    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("浮窗助手");
        data.add("提取APK");
        data.add("蓝字代码");
        data.add("以图搜图");
        data.add("QQ名片赞");
        data.add("root工具");
        data.add("应用管理器");
        data.add("多进制转换");
        data.add("QQ|微信卡屏");
        data.add("桌面动态壁纸");
        data.add("文件校验修改");
        data.add("文字加密解密");
        data.add("百度云直链提取");
        data.add("QQ空间蓝色动态");
        data.add("三星设备修改dpi");
        //data.add("强制清除RAM内存");
        data.add("强制发起临时会话");
        //data.add("免费看vip付费电视");
        data.add("哔哩哔哩封面获取");
        data.add("网易云音乐启动背景替换");
        //data.add("清除本机全部缓存非root");
        data.add("解除（MIUI）授权25秒");
        /*
        data.add("快递查询");
        data.add("票房查询");
        data.add("菜谱查询");
        data.add("邮编查询");
        data.add("成语查询");
        data.add("万年历查询");
        data.add("算八字查询");
        data.add("手机号码查询");
        data.add("新华字典查询");
        data.add("各省今日油价");
        data.add("婚姻匹配查询");
        data.add("黄金数据查询");
        data.add("汇率数据查询");
        data.add("天气预报查询");
        data.add("健康知识查询");
        data.add("周公解梦查询");
        data.add("身份证信息查询");
        data.add("老黄历信息查询");
        data.add("手机号码查吉凶");
        data.add("银行卡信息查询");
        data.add("中国彩票开奖结果");*/
        data.add("在线查询功能");

        return data;
    }

    private ArrayList<String> createItems() {
        ArrayList<String> data = new ArrayList<>();
        data.add("动态图片分解");
        data.add("视频提取音乐");
        data.add("视频转换动图");
        data.add("提取视频图片");
        data.add("设备信息");
        data.add("一个日记");
        data.add("一个记事");
        data.add("一个智障");
        data.add("相册处理");

        data.add("LED字幕");//
        data.add("特ۣۖ殊ۣۖ文ۣۖ本ۣۖ生ۣۖ成ۣۖ器ۣۖ");//
        data.add("QQ信息连发器");//
        data.add("远程管理ftp");//
        data.add("QQ极速抢红包");

        data.add("有道翻译");

        return data;
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @SuppressLint("WrongConstant")
    private void a() {
        String str = "com.android.settings.DisplayScalingActivity";
        String[] strArr = new String[]{"com.android.settings", "android.settings.SETTINGS"};
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str2 = strArr[i];
            Intent intent = new Intent();
            intent.addFlags(0x10000000);
            intent.setClassName(str2, str);
            try {
                startActivity(intent);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                i++;
            }
        }
        Toast.makeText(this, "不支持这个设备", Toast.LENGTH_SHORT).show();
    }

    private String getClipboardText() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String text = "";
        try {
            if (clipboard != null && clipboard.hasText()) {
                CharSequence tmpText = clipboard.getText();
                clipboard.setText(tmpText);
                if (tmpText != null && tmpText.length() > 0) {
                    text = tmpText.toString().trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            text = "";
        }
        return text;
    }

    public void showIntent(Class<?> clzz) {
        Intent intent = new Intent(this, clzz);
        startActivity(intent);
    }

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

    public void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Toast.makeText(this, " 初始化失败！部分功能可能无法使用", Toast.LENGTH_SHORT).show();
            // Log.i("error:", e+"");
        }
    }

    //普通安装
    private void installNormal(String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Uri apkUri = FileProvider.getUriForFile(DownloadService.this, "nico.styTool", file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(Uri.fromFile(new File(apkPath + "")), "application/vnd.android.package-archive");
        this.startActivity(intent);
    }

    public void deleteNo(int id) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);

    }

    public void AddNotification(int id, String s, String name) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ws_Main3Activity.this);
// 设置通知的基本信息：icon、标题、内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(name);
        builder.setContentText(s);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

    }

    public void AddNotification(int id, String s, String name, int max, int dq) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ws_Main3Activity.this);

// 设置通知的基本信息：icon、标题、内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(name);
        builder.setContentText(s);
        builder.setProgress(max, dq, false);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

    }

    private void xft() {

        BmobQuery<i_a> query = new BmobQuery<>();
        query.getObject("03bf357e85", new QueryListener<i_a>() {

            @Override
            public void done(final i_a movie, BmobException e) {
                if (e == null) {
                    String s = movie.getContent();
                    String sr = Constant.a_mi + "\n" + Constant.a_miui;
                    if (s.equals(sr)) {

                    } else {
                        android.support.design.widget.CoordinatorLayout container = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.maibiiii0);
                        Snackbar.make(container, "发现新版", Snackbar.LENGTH_LONG).setAction("查看", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LayoutInflater inflater = LayoutInflater.from(ws_Main3Activity.this);
                                View view = inflater.inflate(R.layout.cpl_main, null);
                                final TextView a = view.findViewById(R.id.cplmainTextView1);
                                a.setText(movie.getContent());
                                AlertDialog.Builder builder = new AlertDialog.Builder(ws_Main3Activity.this);
                                builder.setView(view)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                BmobQuery<o> query = new BmobQuery<o>();
                                                query.getObject("8304c235d2", new QueryListener<o>() {

                                                    @Override
                                                    public void done(o mov, BmobException e) {
                                                        if (e == null) {
                                                            String s = "520";
                                                            String sr = mov.getContent();
                                                            if (s.equals(sr)) {
                                                                Uri uri = Uri.parse("http://www.coolapk.com/apk/nico.styTool");
                                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                                startActivity(intent);
                                                            } else {
                                                                Uri uri = Uri.parse(mov.getContent());
                                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                                startActivity(intent);
                                                            }
                                                        } else {
                                                            Uri uri = Uri.parse("http://www.coolapk.com/apk/nico.styTool");
                                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                });
                                            }
                                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                }).setCancelable(false).create().show();
                            }
                        }).show();
                        //

                    }
                } else {

                }
            }
        });
    }

    private void eoou2(String dec) {
        //com.tencent.mm.plugin.scanner.ui.BaseScanUI
        final SharedPreferences i = this.getSharedPreferences("Hero", 0);
        Boolean o0 = i.getBoolean("FIRST", true);
        if (o0) {//第一次
            AppCompatDialog alertDialog = new AlertDialog.Builder(ws_Main3Activity.this)
                    .setTitle("快捷功能")
                    .setMessage("需要ROOT才能使用")
                    .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            i.edit().putBoolean("FIRST", false).apply();
                        }
                    }).setCancelable(false).create();
            alertDialog.show();
        } else {
            ShellUtils.execCommand("am start -n com.eg.android.AlipayGphone/" + dec, true, true);
        }
    }

    private void eoou(String dec) {
        //com.tencent.mm.plugin.scanner.ui.BaseScanUI
        final SharedPreferences i = this.getSharedPreferences("Hero", 0);
        Boolean o0 = i.getBoolean("FIRST", true);
        if (o0) {//第一次
            AppCompatDialog alertDialog = new AlertDialog.Builder(ws_Main3Activity.this)
                    .setTitle("快捷功能")
                    .setMessage("需要ROOT才能使用")
                    .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            i.edit().putBoolean("FIRST", false).apply();
                        }
                    }).setCancelable(false).create();
            alertDialog.show();
        } else {
            ShellUtils.execCommand("am start -n com.tencent.mm/" + dec, true, true);
        }
    }
/*
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.button1://微信扫一扫:
                eoou("com.tencent.mm.plugin.scanner.ui.BaseScanUI");
                break;
            case R.id.button2://微信付款:
                eoou("com.tencent.mm.plugin.offline.ui.WalletOfflineCoinPurseUI");
                break;
            case R.id.button3://微信收款:
                eoou("com.tencent.mm.plugin.collect.ui.CollectMainUI");
                break;
            case R.id.button4://支付宝扫一扫:
                eoou2("com.alipay.mobile.scan.as.main.MainCaptureActivity");
                break;
            case R.id.button5://支付宝付款：
                eoou2("com.alipay.mobile.onsitepay9.payer.OspTabHostActivity");
                break;
            default:
                break;
        }
    } */

    public void ajoinQQGroupdata(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
        } catch (Exception e) {
// 未安装手Q或安装的版本不支持
        }
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(false);
        //Intent intent = new Intent(Intent.ACTION_MAIN);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addCategory(Intent.CATEGORY_HOME);
        //startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void pay() {
        LayoutInflater inflater = LayoutInflater.from(ws_Main3Activity.this);
        View zview = inflater.inflate(R.layout.skineng1, null);
        final ImageView ediComment1 = zview.findViewById(R.id.imageView2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                File file = new File(String.valueOf(SharedPreferencesUtil.getData("ImageCh", "")));
                try {
                    Glide.with(ws_Main3Activity.this).load(file).into(ediComment1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500L);
        AlertDialog.Builder builder = new AlertDialog.Builder(ws_Main3Activity.this);
        builder.setView(zview)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("选择图片", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showIntent(ChoseImgActivity.class);
            }

        }).setCancelable(false).create().show();
    }

    private void showThemeChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ws_Main3Activity.this);
        builder.setTitle("设置主题");
        Integer[] res = new Integer[]{R.drawable.red_round, R.drawable.brown_round, R.drawable.blue_round, R.drawable.blue_grey_round, R.drawable.yellow_round, R.drawable.deep_purple_round, R.drawable.pink_round, R.drawable.green_round};
        List<Integer> list = Arrays.asList(res);
        ColorsListAdapter adapter = new ColorsListAdapter(ws_Main3Activity.this, list);
        adapter.setCheckItem(MyThemeUtils.getCurrentTheme(ws_Main3Activity.this).getIntValue());
        GridView gridView = (GridView) LayoutInflater.from(ws_Main3Activity.this).inflate(R.layout.colors_panel_layout, null);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setAdapter(adapter);
        builder.setView(gridView);
        final AlertDialog dialog = builder.show();
        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        int value = MyThemeUtils.getCurrentTheme(ws_Main3Activity.this).getIntValue();
                        if (value != position) {
                            PreferenceUtils.getInstance(ws_Main3Activity.this).saveParam("change_theme_key", position);
                            MyThemeUtils.Theme.mapValueToTheme(position);

                        }
                    }
                }

        );
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            /*
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary_dark));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description); */
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ente, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_el) {
            showThemeChooseDialog();
            return true;
        }
        if (id == R.id.lxw_actio) {
            Intent inten = new Intent(this, GankIoActivity.class);
            startActivity(inten);
            return true;
        }
        if (id == R.id.sort_el0) {
            pay();
            /*
            final MyUser myUserw = BmobUser.getCurrentUser(MyUser.class);
            if (myUserw != null) {
                Intent inten = new Intent(this, MessageActivity.class);
                startActivity(inten);
            } else {
                Intent in = new Intent(this, app_th.class);
                startActivity(in);
            } */

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            final MyUser myUserw = BmobUser.getCurrentUser(MyUser.class);
            if (myUserw != null) {
                Intent intent = new Intent(ws_Main3Activity.this, UserProfileActivity.class);
                startActivity(intent);
            } else {
                Intent in = new Intent(ws_Main3Activity.this, app_th.class);
                startActivity(in);
            }
        } else if (id == R.id.nav_bug) {
            LayoutInflater inflater = LayoutInflater.from(ws_Main3Activity.this);
            View zview = inflater.inflate(R.layout.skineng, null);
            final EditText ediComment1 = zview.findViewById(R.id.seditText2);
            AlertDialog.Builder builder = new AlertDialog.Builder(ws_Main3Activity.this);
            builder.setView(zview)
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String content = ediComment1.getText().toString();
                    xp feedback = new xp();
                    feedback.setContent(content);
                    feedback.save(new SaveListener<String>() {

                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(ws_Main3Activity.this, "成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ws_Main3Activity.this, "失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }).setCancelable(false).create().show();
        } else if (id == R.id.nav_sharer) {
            showIntent(WelcomeActivity.class);
        } else if (id == R.id.nav_share) {
            ajoinQQGroupdata("YfKb2i5ShlUlith2KClI5GAxEMj5UoA8");
        } else if (id == R.id.nav_send) {
            LayoutInflater inflater = LayoutInflater.from(ws_Main3Activity.this);
            View view = inflater.inflate(R.layout.x5x5, null);
            final TextView b1 = view.findViewById(R.id.textView);//标题
            try {
                InputStream is = getAssets().open("lice.txt");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String text = new String(buffer, "UTF-8");
                b1.setText("" + text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(ws_Main3Activity.this);
            builder.setView(view).setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create().show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
