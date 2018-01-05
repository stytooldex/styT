package nico.styTool;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by luxin on 15-12-13.
 * http://luxin.gitcafe.io
 */
public class UserProfileActivity extends dump.z.BaseActivity implements View.OnClickListener {

    //private final static String TAG = "UserProfileActivity";

    /**
     * Called when the activity is first created.
     */
    android.support.v7.widget.Toolbar toolbar;

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

    private void takePic() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        try {
            startActivityForResult(Intent.createChooser(intent, ""), Crop.REQUEST_PICK);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            Bundle bundle = data.getExtras();
            if (isUsername) {
                usernameText.setText(bundle.getString("username"));
                isChangeUsername = true;
            } else {
                personalityText.setText(bundle.getString("username"));
            }
            isChange = true;
            btnVisibility();
        } else if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void handleCrop(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Log.e(TAG,"=====>Crop.getOutput(data)=====>"+Crop.getOutput(data));
            //   userImg.setImageURI(Crop.getOutput(data));
            filePath = Crop.getOutput(data).getPath();
            ImageLoader.getInstance(1, ImageLoader.Type.LIFO).loaderImage(filePath, userImg, false);
            //Log.e(TAG, "===filePath====" + filePath);
            isChangeUserImgNo = false;
            isChange = true;
            btnVisibility();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(data).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void beginCrop(Uri data) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped.jpg"));
        Crop.of(data, destination).asSquare().start(this);
    }


    private void changeSex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] sexs = {"男", "女"};
        AlertDialog alert = null;
        alert = builder.setTitle("请选择性别")
                .setItems(sexs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sexText.setText(sexs[which]);
                        isChange = true;
                        btnVisibility();
                    }
                }).create();
        alert.show();
    }


    private void uploaderAvertor(String file) {
        File path = new File(file);
        //Log.e(TAG, "=====uploade_avertor___success===>" + path.getAbsolutePath());
        final BmobFile bmobFile = new BmobFile(path);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    user.setAuvter(bmobFile);
                    //Log.e(TAG, "=====uploade_avertor___success===>" + bmobFile.getUrl());
                    updateProfile();
                } else {
                    //toast("上传文件失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 2;


    private RelativeLayout Img;
    private ImageView userImg;
    private RelativeLayout username;

    private RelativeLayout sex;
    private TextView usernameText;
    private TextView sexText;

    private TextView email;

    private RelativeLayout personality;
    private TextView personalityText;

    private Button btn;

    private boolean isChange = false;

    private String filePath = null;

    private MyUser myUser = null;

    private boolean isUsername = false;

    private boolean isChangeUsername = false;

    private ProgressDialog mProgressDialog;

    private boolean isChangeUserImgNo = true;

    private MyUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lxw_user_profile);
        myUser = BmobUser.getCurrentUser(MyUser.class);
        user = new MyUser();
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        if (myUser.getAuvter() != null) {
            // Log.e(TAG, "===getAuvtero file url====" + myUser.getAuvter().getUrl());
            String auvterPath = "" + myUser.getAuvter().getUrl();
            ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loaderImage(auvterPath, userImg, true);
        }
        if (myUser.getSex().equals(0)) {
            sexText.setText("男");
        } else {
            sexText.setText("女");
        }
        usernameText.setText(myUser.getUsername());
        email.setText(myUser.getEmail());

    }


    private void initView() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
//		初始化Toolbar控件
        setSupportActionBar(toolbar);
//		用Toolbar取代ActionBar
        //toolbar.setTitleTextColor(getResources().getColor(R.color.text_font_white));//标题颜色
        //	toolbar.setSubtitleTextColor(getResources().getColor(R.color.text_font_white));//副标题颜色

        username = (RelativeLayout) findViewById(R.id.lxw_user_profile_username);
        usernameText = (TextView) findViewById(R.id.lxw_id_user_profile_username_text);

        email = (TextView) findViewById(R.id.lxw_id_user_profile_email);

        Img = (RelativeLayout) findViewById(R.id.lxw_user_profile_img);
        userImg = (ImageView) findViewById(R.id.lxw_id_user_profile_userimg);
        sex = (RelativeLayout) findViewById(R.id.lxw_user_profile_sex);
        sexText = (TextView) findViewById(R.id.lxw_id_user_profile_sex_text);

        personality = (RelativeLayout) findViewById(R.id.lxw_user_profile_personality);
        personalityText = (TextView) findViewById(R.id.lxw_user_profile_personality_text);

        btn = (Button) findViewById(R.id.lxw_user_profile_btn_save);
        btn = (Button) findViewById(R.id.lxw_user_profile_btn_save);
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.getObject(myUser.getObjectId(), new QueryListener<MyUser>() {

            @Override
            public void done(MyUser movie, BmobException e) {
                if (e == null) {
                    /*
                    TextView sh = (TextView) findViewById(R.id.btn_save);
                    sh.setText(movie.getid());

                    TextView sexText = (TextView) findViewById(R.id.lxw_id_user_profile_sex_text);
                    Integer ios = movie.getSex();
                    sexText.setText(Integer.toString(ios));

                    if (movie.getAddress().equals("激活v")) {
                        CheckBox cb = (CheckBox) findViewById(R.id.lxwuserprofileCheckBox2);
                        cb.setChecked(true);//选中
                    } else {

                    }
                    String s = "" + movie.getEmailVerified();
                    String sr = "true";
                    if (s.equals(sr)) {
                        CheckBox b = (CheckBox) findViewById(R.id.lxwuserprofileCheckBox1);
                        b.setChecked(true);//选中
                    } else {
                        //Toast.makeText(WeiboListActivity.this, "未激活帐号最多(68字)", Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    //TextView s = (TextView) findViewById(R.id.btn_save);
                    //s.setText("???");
                }
            }
        });

    }

    private void initEvent() {
        Img.setOnClickListener(this);
        username.setOnClickListener(this);
        sex.setOnClickListener(this);
        btn.setOnClickListener(this);
        personality.setOnClickListener(this);
    }

    private void updateProfile() {
        String username = usernameText.getText().toString().trim();
        String sex = sexText.getText().toString().trim();
        String personality = personalityText.getText().toString().trim();
        int sexInt = 0;
        if (TextUtils.isEmpty(username)) {
            return;
        }
        if (sex.equals("男")) {
            sexInt = 0;
        } else {
            sexInt = 1;
        }

        if (isChangeUsername) {
            if (!username.equals(myUser.getUsername())) {
                user.setUsername(username);
            }
        }
        user.setSex(sexInt);
        user.setPersonality(personality);
        user.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    mProgressDialog.dismiss();
                    ToastUtil.show(UserProfileActivity.this, "信息更新成功", Toast.LENGTH_SHORT);
                    btn.setVisibility(View.GONE);
                } else {
                    mProgressDialog.dismiss();
                    ToastUtil.show(UserProfileActivity.this, "信息更新失败", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lxw_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lxw_action_menu_logout) {
            BmobUser.logOut();
            finish();
            return true;
        }
        if (id == R.id._menu_logout) {
            TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
            String imei = "" + telephonyManager.getDeviceId();

            final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
            BmobQuery<MyUser> query = new BmobQuery<MyUser>();
            query.getObject(myUser.getObjectId(), new QueryListener<MyUser>() {
                @Override
                public void done(MyUser movie, BmobException e) {
                    if (e == null) {
                        String s = "" + movie.getEmailVerified();
                        String sr = "true";
                        if (s.equals(sr)) {
                            Toast.makeText(UserProfileActivity.this, "帐号已经激活过", Toast.LENGTH_SHORT).show();
                            //这
                        } else {
                            LayoutInflater inflater = LayoutInflater.from(UserProfileActivity.this);
                            View view = inflater.inflate(R.layout.aew, null);
                            final EditText ediComment = view.findViewById(R.id.aewEditText1);
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                            builder.setView(view).setPositiveButton("免费激活", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String comment = ediComment.getText().toString().trim();
                                    if (TextUtils.isEmpty(comment)) {
                                        //ediComment.setError("内容不能为空");
                                        ToastUtil.show(UserProfileActivity.this, "没有填号码", Toast.LENGTH_SHORT);
                                        return;
                                    }
                                    String s = "13800138000";
                                    if (s.equals(comment)) {

                                        //MyUser newUser = new MyUser();
                                        myUser.setEmailVerified(true);
                                        myUser.update(myUser.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(UserProfileActivity.this, "激活成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(UserProfileActivity.this, "激活" + e, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        //Toast.makeText(MainActivity.this, "相等", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Toast.makeText(UserProfileActivity.this, "激活码错误(请加入官方群，群公告激活码)", Toast.LENGTH_SHORT).show();
                                    }
                                    //push(comment, myUser);
                                }
                            }).create().show();
                        }
                    } else {
                        Toast.makeText(UserProfileActivity.this, "貌似没有网络", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lxw_user_profile_img:
                takePic();
                break;
            case R.id.lxw_user_profile_sex:
                changeSex();
                break;
            case R.id.lxw_user_profile_username:
                isUsername = true;
                Intent intent = new Intent(this, EdiUserProfileActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.lxw_user_profile_btn_save:
                mProgressDialog = ProgressDialog.show(this, null, "正在保存，请稍后...");
                if (filePath != null) {
                    uploaderAvertor(filePath);
                } else {
                    updateProfile();
                }
                break;
            case R.id.lxw_user_profile_personality:
                isUsername = false;
                Intent intent1 = new Intent(this, EdiUserProfileActivity.class);
                startActivityForResult(intent1, REQUEST_CODE);
                break;
        }
    }

    private void btnVisibility() {
        if (isChange) {
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        // TODO: Implement this method
        super.onStart();
//		设置副标题
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
//		设置导航按钮监听
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //boolean isChangeUserImgNo = true;
        if (!isChange && !isChangeUsername) {
            initData();
        }
    }
}
