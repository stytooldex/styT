package nico.styTool;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import dump.z.BaseActivity_;

public class Main3Activity extends BaseActivity_ {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        final String packname = getPackageName();
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packname, PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            int code = sign.hashCode();
            if (code != 312960342) {
                ToaU.showToast(Main3Activity.this, "穷逼打卡会员");
            } else {
                //
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.getObject(bmobUser.getObjectId(), new QueryListener<MyUser>() {
            @Override
            public void done(MyUser object, BmobException e) {
                if (e == null) {
                    if (String.valueOf(object.getAge()).equals("")) {

                    } else {
                        ToaU.showToast(Main3Activity.this, "积分" + String.valueOf(object.getAge()));
                    }
                } else {
                    // Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
        TextView spinner = (TextView) findViewById(R.id.activity_main3_4);
        final SharedPreferences ip = getSharedPreferences("Hello511p", 0);
        Boolean o0p = ip.getBoolean("FIRST", true);
        if (o0p) {//第一次
            ip.edit().putBoolean("FIRST", false).apply();

        } else {
        }
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                BmobQuery<MyUser> query = new BmobQuery<>();
                query.getObject(bmobUser.getObjectId(), new QueryListener<MyUser>() {
                    @Override
                    public void done(final MyUser object, BmobException e) {
                        if (e == null) {
                            if (object.getAge() > 2) {
                                final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                                BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                                query.getObject(myUser.getObjectId(), new QueryListener<MyUser>() {
                                    @Override
                                    public void done(MyUser movie, final BmobException e) {
                                        if (e == null) {
                                            String s = "" + movie.getEmailVerified();
                                            String sr = "true";
                                            if (s.equals(sr)) {
                                                Toast.makeText(Main3Activity.this, "帐号已经激活过", Toast.LENGTH_SHORT).show();
                                                //这
                                            } else {
                                                //MyUser newUser = new MyUser();
                                                myUser.setEmailVerified(true);
                                                myUser.update(myUser.getObjectId(), new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if (e == null) {
                                                            Integer count = object.getAge() - 3;
                                                            MyUser newUser = new MyUser();
                                                            newUser.setAge(count);
                                                            BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                                                            newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                                                                @Override
                                                                public void done(BmobException e) {
                                                                    if (e == null) {
                                                                        Toast.makeText(Main3Activity.this, "激活成功", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        // toast("更新用户信息失败:" + e.getMessage());
                                                                    }
                                                                }
                                                            });

                                                        } else {
                                                            Toast.makeText(Main3Activity.this, "激活" + e, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                                //Toast.makeText(MainActivity.this, "相等", Toast.LENGTH_SHORT).show();

                                                //push(comment, myUser);
                                            }


                                        } else {
                                            Toast.makeText(Main3Activity.this, "貌似没有网络", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                ToaU.showToast(Main3Activity.this, "积分不够");
                            }
                        } else {

                            // Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }

                });
            }
        });
    }
}
