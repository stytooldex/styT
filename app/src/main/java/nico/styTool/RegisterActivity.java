package nico.styTool;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.functions.Action1;


public class RegisterActivity extends Fragment {
    private EditText ediusername;
    private EditText ediemail;
    private EditText edipassword;
    private EditText edipasswordTwo;

    //private ProgressDialog mProgressDialog;
    private ProgressDialog dialog;

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

    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            // Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }

    @SuppressLint("HardwareIds")
    private void register() {
        String username = ediusername.getText().toString().trim();
        String email = ediemail.getText().toString().trim();
        String password = edipassword.getText().toString().trim();
        String passwordTwo = edipasswordTwo.getText().toString().trim();
        //ediusername.setError(null);
        // ediemail.setError(null);
        // edipassword.setError(null);
        // edipasswordTwo.setError(null);
        if (TextUtils.isEmpty(username)) {
            // mProgressDialog.dismiss();
            ediusername.setError("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            // mProgressDialog.dismiss();
            ediemail.setError("请输入邮箱");
            return;
        }
        if (!isEmailValidate(email)) {
            // mProgressDialog.dismiss();
            ediemail.setError("这不是一个有效的邮箱");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //   mProgressDialog.dismiss();
            edipassword.setError("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(passwordTwo)) {
            //    mProgressDialog.dismiss();
            edipasswordTwo.setError("请再次输入一次密码");
            return;
        }

        if (!password.equals(passwordTwo)) {
            //   mProgressDialog.dismiss();
            Toast.makeText(getActivity(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            // new AlertDialog.Builder(getActivity()).setMessage("两次输入的密码不一致").create().show();
            return;
        }

        if (!isPasswordValidate(password)) {
            //    mProgressDialog.dismiss();
            Toast.makeText(getActivity(), "密码长度过短，最小7位以上", Toast.LENGTH_SHORT).show();
            //new AlertDialog.Builder(getActivity()).setMessage("密码长度过短，最小7位以上").create().show();
            return;
        }

        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(android.support.v4.app.FragmentActivity.TELEPHONY_SERVICE);
        final MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
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
        user.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException i) {
                if (i == null) {
                    bindUserIdAndDriverice(user);
                    getActivity().finish();
                } else {
                    if (i.getErrorCode() == 202) {
                        //mProgressDialog.dismiss();
                        Toast.makeText(getActivity(), "该用户名已被注册", Toast.LENGTH_SHORT).show();
                    } else if (i.getErrorCode() == 304) {
                        //   mProgressDialog.dismiss();
                        Toast.makeText(getActivity(), "其中一项输入为空", Toast.LENGTH_SHORT).show();

                    } else if (i.getErrorCode() == 9010) {
                        //     mProgressDialog.dismiss();
                        Toast.makeText(getActivity(), "网络超时", Toast.LENGTH_SHORT).show();

                    } else if (i.getErrorCode() == 203) {
                        //  mProgressDialog.dismiss();
                        Toast.makeText(getActivity(), "该邮箱已注册帐号，请登录", Toast.LENGTH_SHORT).show();

                    } else {
                        //   mProgressDialog.dismiss();
                        Toast.makeText(getActivity(), "注册失败" + i, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.lxw_register, null);
        Toast.makeText(getActivity(), "不懂可直接加入官方群使用游客帐号\n(如登录发现闪退.可联系作者)", Toast.LENGTH_SHORT).show();

        ediusername = view.findViewById(R.id.lxw_id_reg_edi_username);
        ediemail = view.findViewById(R.id.lxw_id_reg_edi_email);
        edipassword = view.findViewById(R.id.lxw_id_reg_edi_password);
        edipasswordTwo = view.findViewById(R.id.lxw_id_reg_edi_password_two);
        Button btnReg = view.findViewById(R.id.lxw_id_reg_btn_register);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                dialog = new ProgressDialog(getActivity());
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转盘
                //dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setTitle("提示");
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //Toast.makeText(getActivity(), "消失了", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setMessage("<_>");
                dialog.show();
                // mProgressDialog = ProgressDialog.show(getActivity(), null, "...");
                // Toast.makeText(getActivity(), "注册帐号系统维护中，请暂时使用通用帐号", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }


    private boolean isPasswordValidate(String password) {
        return password.length() > 6;
    }

    private boolean isEmailValidate(String email) {
        return email.contains("@");
    }
}
