package nico.styTool;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.functions.Action1;

//import android.support.v7.app.AppCompatActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Fragment {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    //private View mLoginFormView;

    // private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

    /**
     * 将用户与设备绑定起来
     *
     * @param user
     */
    private void bindUserIdAndDriverice(final MyUser user) {
        ;

        BmobQuery<MyUserInstallation> query = new BmobQuery<MyUserInstallation>();
        query.addWhereEqualTo("installationId", BmobInstallationManager.getInstallationId());
        query.findObjectsObservable(MyUserInstallation.class)
                .subscribe(new Action1<List<MyUserInstallation>>() {
                    @Override
                    public void call(List<MyUserInstallation> installations) {

                        if (installations.size() > 0) {
                            if (installations.size() > 0) {

                                MyUserInstallation myUserInstallation = installations.get(0);
                                myUserInstallation.setUid(user.getObjectId());
                                myUserInstallation.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {

                                        } else {

                                        }
                                    }
                                });
                            }

                        } else {
                            //toastE("后台不存在此设备Id的数据，请确认此设备Id是否正确！\n" + id);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // toastE("查询设备数据失败：" + throwable.getMessage());
                    }
                });
    }

    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            lxwLogin(email, password);
            // showProgress(true,email,password);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private void lxwLogin(String email, String password) {
        MyUser user = new MyUser();
        user.setEmail(email);
        user.setPassword(password);
        BmobUser.loginByAccount(email, password, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if (user != null) {
                    bindUserIdAndDriverice(user);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "登录失败！错误信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        View view = inflater.inflate(R.layout.activity_login, null);

        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mEmailSignInButton0 = view.findViewById(R.id.email_sign_in_button0);
        mEmailSignInButton0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view2 = inflater.inflate(R.layout.getda, null);
                final Button fab_2 = view2.findViewById(R.id.ahnButton1);
                final Button fab_3 = view2.findViewById(R.id.ahnButton12);
                EditText ediusername = view2.findViewById(R.id.ahnEditText1);
                EditText ediemail = view2.findViewById(R.id.ahnEditText12);
                final String username = ediusername.getText().toString().trim();
                final String email = ediemail.getText().toString().trim();
                fab_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BmobUser.loginByAccount(username, email, new LogInListener<MyUser>() {

                            @Override
                            public void done(MyUser user, BmobException e) {
                                if (user != null) {
                                    bindUserIdAndDriverice(user);
                                    getActivity().finish();
                                    //Log.i("smile","用户登陆成功");
                                }
                            }
                        });
                        fab_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LayoutInflater inflater = LayoutInflater.from(getActivity());
                                View view2 = inflater.inflate(R.layout.getde0, null);

                                EditText ediusername = view2.findViewById(R.id.ahnEditText1);
                                EditText ediemail = view2.findViewById(R.id.ahnEditText12);
                                final String username = ediusername.getText().toString().trim();
                                final String email = ediemail.getText().toString().trim();
                                Button fab_2 = (Button) view2.findViewById(R.id.ahnButton1);
                                fab_2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(android.support.v4.app.FragmentActivity.TELEPHONY_SERVICE);
                                        MyUser user = new MyUser();
                                        user.setMobilePhoneNumber(username);//设置手机号码（必填）
                                        //user.setUsername(xxx);                  //设置用户名，如果没有传用户名，则默认为手机号码
                                        user.setPassword(email);                  //设置用户密码
                                        user.setScore(10);
                                        user.setSex(1);
                                        user.setNum("vs");
                                        user.setAge(1);
                                        user.setAddress("不激活");
                                        user.setHol(tm.getDeviceId());
                                        user.setid("520");
                                        user.setGender(false);
                                        user.setGen_(false);
                                        user.setGen_v(false);
                                        user.setPlayScore(1);
                                        user.setSignScore(1);
                                        user.setPlayScore_(1);
                                        user.setPlayScore_s(1);
                                        user.setGame("v");
                                        user.setCardNumber("v");
                                        user.setBankName("v");
                                        user.signOrLogin("验证码", new SaveListener<MyUser>() {

                                            @Override
                                            public void done(MyUser user, BmobException e) {
                                                if (e == null) {
                                                    //toast("注册或登录成功");
                                                    // Log.i("smile", ""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId());
                                                } else {
                                                    // toast("失败:" + e.getMessage());
                                                }

                                            }

                                        });
                                    }
                                });
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                builder1.setView(view2)
                                        .setTitle("AdapterView")
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).setPositiveButton("不再弹出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Toast.makeText(ws_Main3Activity.this, "下载安装后就不再弹出了", Toast.LENGTH_SHORT).show();
                                    }

                                }).setCancelable(false).create().show();
                            }
                        });
                    }
                });
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setView(view2)
                        .setTitle("AdapterView")
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("不再弹出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(ws_Main3Activity.this, "下载安装后就不再弹出了", Toast.LENGTH_SHORT).show();
                    }

                }).setCancelable(false).create().show();
            }
        });

        //mLoginFormView = view.findViewById(R.id.login_form);
        //  mProgressView = view.findViewById(R.id.login_progress);

        //注册退出广播
        // IntentFilter filter = new IntentFilter();
        //  filter.addAction(Constant.ACTION_REGISTER_SUCCESS_FINISH);
        //  getActivity().registerReceiver(myBroadcastReceiver, filter);

        return view;
    }
}
    /*
    private class MyBroadcastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
		{
            if (intent != null && intent.getAction().equals(Constant.ACTION_REGISTER_SUCCESS_FINISH))
			{
                getActivity().finish();
            }
        }}}
*/