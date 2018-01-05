package dump_dex.root;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;

import dump.a.ShellUtils;
import dump.z.AppCompatPreferenceActivity;
import nico.styTool.R;
import nico.styTool.wlflActivity;


public class rootActivity extends AppCompatPreferenceActivity {

    private String a = "reboot recovery";//重启到Recovery界面
    private String b = "reboot bootloader";//重启到bootloader界面
    private String c = "reboot";//重启手机
    private String d = "remount";//重新挂载文件系统
    private String e = "erase boot";//擦除boot分区
    private String f = "erase system";//擦除system分区
    private String g = "erase cache";//擦除cache分区
    private String i = "erase userdata";//擦除userdata分区
    //private String a="reboot recovery";//
    private boolean isRoot = true;
    private boolean isNeedResultMsg = true;

    Preference a1 = null;
    Preference a2 = null;
    Preference a3 = null;
    Preference a4 = null;
    Preference a5 = null;
    Preference a6 = null;
    Preference a7 = null;
    Preference a8 = null;
    Preference a9 = null;

    public boolean is_root() {
        boolean res = false;
        try {
            res = !((!new File("/system/bin/su").exists()) &&
                    (!new File("/system/xbin/su").exists()));
            ;
        } catch (Exception ignored) {
        }
        return res;
    }

    private void j() {

        if (is_root()) {
            // Toast.makeText(this, "已经具有ROOT权限!", Toast.LENGTH_LONG).show();
        } else {
            try {
                //ProgressDialog.show(this, "ROOT", "正在获取ROOT权限...", true, false);
                Runtime.getRuntime().exec("su");
            } catch (Exception e) {
                Toast.makeText(this, "获取ROOT权限时出错!", Toast.LENGTH_LONG).show();
            }
        }

        //判断busybox存在与否
        File f1 = new File("/system/xbin/busybox");
        if (f1.exists()) {
            //存在
            Toast.makeText(rootActivity.this, "检测busybox:已安装", Toast.LENGTH_LONG).show();
        } else {
            File f2 = new File("/system/bin/busybox");
            if (f2.exists()) {
                //存在
                Toast.makeText(rootActivity.this, "检测busybox:已安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(rootActivity.this, "检测busybox:未安装", Toast.LENGTH_LONG).show();
            }
        }

    }

    private int mAlpha;
    String[] str = {"rm data/system/batterystats.binsystem", "reboot"};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_setting);
        j();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        addPreferencesFromResource(R.xml.pref_data_sync);
        a1 = findPreference("sync_frequency");
        a1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                LayoutInflater inflater = LayoutInflater.from(rootActivity.this);
                View view = inflater.inflate(R.layout.eootb, null);
                final EditText ediComment = view.findViewById(R.id.eoot_1);

                SeekBar mSbChangeAlpha = view.findViewById(R.id.eoot_change_alpha);

                //mSbChangeAlpha.setMax(700);
                mSbChangeAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mAlpha = progress;
                        ediComment.setText(String.valueOf(mAlpha));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(rootActivity.this);
                builder.setView(view).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comment = ediComment.getText().toString().trim();
                        if (TextUtils.isEmpty(comment)) {

                            return;
                        }
                        Toast.makeText(rootActivity.this, "修改完成，请重启设备", Toast.LENGTH_SHORT).show();
                        ShellUtils.execCommand("wm density " + comment, isRoot, isNeedResultMsg);
                    }
                }).setNegativeButton("恢复到系统默认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(rootActivity.this, "恢复完成，请重启设备", Toast.LENGTH_SHORT).show();
                        ShellUtils.execCommand("wm density reset", isRoot, isNeedResultMsg);
                    }
                }).create().show();
                return false;
            }
        });
        a2 = findPreference("sync_frequency1");//电池校准
        a2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(rootActivity.this);
                AlertDialog alert = builder.setMessage("电池校准")
                        .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ShellUtils.execCommand("rm data/system/batterystats.binsystem", isRoot, isNeedResultMsg);
                                ShellUtils.execCommand(str, isRoot, isNeedResultMsg);
                            }
                        }).create();
                alert.show();
                return false;
            }
        });

        a3 = findPreference("sync_frequency2");//预装软件管理
        a3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                return false;
            }
        });

        a4 = findPreference("sync_frequency3");//自定义运行shell命令-终端
        a4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                LayoutInflater inflater = LayoutInflater.from(rootActivity.this);
                View view = inflater.inflate(R.layout.smali_layout_2, null);
                final EditText ediComment = view.findViewById(R.id.smalilayout2EditText1);

                AlertDialog.Builder builder = new AlertDialog.Builder(rootActivity.this);
                builder.setView(view).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comment = ediComment.getText().toString().trim();
                        if (TextUtils.isEmpty(comment)) {
                            //ediComment.setError("内容不能为空");
                            // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
                            return;
                        }
                        ShellUtils.execCommand(comment, isRoot, isNeedResultMsg);
                        //	push(comment, myUser);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
                return false;
            }
        });

        a5 = findPreference("sync_frequency4");//重启到recovery
        a5.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(rootActivity.this);
                AlertDialog alert = builder.setMessage("重启到Recovery界面")
                        .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ShellUtils.execCommand(a, isRoot, isNeedResultMsg);


                            }
                        }).create();
                alert.show();
                return false;
            }
        });

        a6 = findPreference("sync_frequency5");//刷入recovery
        a6.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                LayoutInflater inflater = LayoutInflater.from(rootActivity.this);
                View view = inflater.inflate(R.layout.lxw_push_helps_comment, null);
                final EditText ediComment = view.findViewById(R.id.lxw_id_push_helps_comment_edi);

                AlertDialog.Builder builder = new AlertDialog.Builder(rootActivity.this);
                builder.setView(view).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comment = ediComment.getText().toString().trim();
                        if (TextUtils.isEmpty(comment)) {
                            //ediComment.setError("内容不能为空");
                            // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
                            return;
                        }
                        ShellUtils.execCommand("fastboot flash recovery " + comment, isRoot, isNeedResultMsg);
                        //	push(comment, myUser);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
                return false;
            }
        });

        a7 = findPreference("sync_frequency6");//关机
        a7.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                ShellUtils.execCommand(c, isRoot, isNeedResultMsg);
                return false;
            }
        });

        a8 = findPreference("sync_frequency7");//
        a8.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Boolean o00 = ShellUtils.checkRootPermission();
                if (o00) {//第一次
                    Intent wintent1 = new Intent(rootActivity.this, wlflActivity.class);
                    startActivity(wintent1);
                } else {
                    Toast.makeText(rootActivity.this, "Root权限获取失败，查看WIFI密码失败", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        a9 = findPreference("sync_frequency8");//
        a9.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                LayoutInflater inflater = LayoutInflater.from(rootActivity.this);
                View view = inflater.inflate(R.layout.eootb2, null);
                final EditText ediComment = view.findViewById(R.id.styt11);

                final EditText ediComment2 = view.findViewById(R.id.styt333);

                AlertDialog.Builder builder = new AlertDialog.Builder(rootActivity.this);
                builder.setView(view).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comment = ediComment.getText().toString().trim();
                        String comment2 = ediComment2.getText().toString().trim();


                        Toast.makeText(rootActivity.this, "修改完成，请重启设备", Toast.LENGTH_SHORT).show();
                        ShellUtils.execCommand("wm size " + comment + "x" + comment2, isRoot, isNeedResultMsg);
                    }
                }).setNegativeButton("恢复到系统默认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(rootActivity.this, "恢复完成，请重启设备", Toast.LENGTH_SHORT).show();
                        ShellUtils.execCommand("wm size reset", isRoot, isNeedResultMsg);
                    }
                }).create().show();
                return false;
            }
        });

    }
}