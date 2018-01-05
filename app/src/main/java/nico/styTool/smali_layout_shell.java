package nico.styTool;
//妮可妮可妮

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import java.io.DataOutputStream;
import java.io.IOException;

import dump.a.ShellUtils;
import dump.z.BaseActivity_;

public class smali_layout_shell extends BaseActivity_ implements NavigationView.OnNavigationItemSelectedListener {
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

    public void m() {
        try {

            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream out = new DataOutputStream(
                    process.getOutputStream());
            out.writeBytes("reboot -p\n");
            out.writeBytes("exit\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            new AlertDialog.Builder(smali_layout_shell.this).setTitle("Error").setMessage(
                    e.getMessage()).setPositiveButton("OK", null).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smali_layout_1);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        navigationView.setNavigationItemSelectedListener(this);
        //ShellUtils.execCommand(a,isRoot,isNeedResultMsg);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.a) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alert = builder.setMessage("重启到bootloader界面")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShellUtils.execCommand(b, isRoot, isNeedResultMsg);
                        }
                    })
                    .create();
            alert.show();
        } else if (id == R.id.b) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alert = builder.setMessage("重新挂载文件系统")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShellUtils.execCommand(d, isRoot, isNeedResultMsg);
                        }
                    })
                    .create();
            alert.show();
        } else if (id == R.id.c) {

        } else if (id == R.id.d) {

            //ShellUtils.execCommand(a,isRoot,isNeedResultMsg);

        } else if (id == R.id.e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alert = builder.setMessage("重启手机")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShellUtils.execCommand(c, isRoot, isNeedResultMsg);


                        }
                    })
                    .create();
            alert.show();

        } else if (id == R.id.g) {
            //路过ShellUtils.execCommand(a,isRoot,isNeedResultMsg);

        } else if (id == R.id.i) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alert = builder.setMessage("擦除boot分区")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShellUtils.execCommand(e, isRoot, isNeedResultMsg);
                        }
                    }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShellUtils.execCommand(e, isRoot, isNeedResultMsg);


                        }
                    })
                    .create();
            alert.show();
        } else if (id == R.id.j) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alert = builder.setMessage("擦除system分区")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShellUtils.execCommand(f, isRoot, isNeedResultMsg);

                        }
                    })
                    .create();
            alert.show();
        } else if (id == R.id.k) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alert = builder.setMessage("擦除cache分区")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShellUtils.execCommand(g, isRoot, isNeedResultMsg);

                        }
                    })
                    .create();
            alert.show();
        } else if (id == R.id.p) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alert = builder.setMessage("擦除userdata分区")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShellUtils.execCommand(i, isRoot, isNeedResultMsg);


                        }
                    })
                    .create();
            alert.show();
        }

        return true;
    }
}
