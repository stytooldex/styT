package nico.styTool;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class Animateddex extends Fragment {

    private EditText ediComment1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        View view = inflater.inflate(R.layout.log, null);

        ediComment1 = view.findViewById(R.id.logEditText1);

        Button fio = view.findViewById(R.id.logButton1);
        fio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v8) {
                String comment = ediComment1.getText().toString().trim();
                if (TextUtils.isEmpty(comment)) {
                    //ediComment1.setError("内容不能为空");
                    // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
                    return;
                }
                BmobUser.resetPasswordByEmail(comment, new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(getActivity(), "找回密码请求成功，请到邮箱进行密码重置", Toast.LENGTH_SHORT).show();
                            ediComment1.setText(null);
                        } else {
                            Toast.makeText(getActivity(), "网络错误或未注册" + "该邮箱", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
