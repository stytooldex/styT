package nico.styTool;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by lum on 2017/12/6.
 */

public class DialogManager {

    private static DialogManager mInstnce = null;
    private ProgressDialog mDialog;

    public static DialogManager getInstnce() {

        if (mInstnce == null) {
            //线程安全模式
            synchronized (DialogManager.class) {
                if (mInstnce == null) {
                    mInstnce = new DialogManager();
                }
            }
        }
        return mInstnce;
    }

    public void showProgressDialog(Context context) {

        if (mDialog == null) {
            mDialog = new ProgressDialog(context);
            //设置点击dialog外部，不会自动退出dialog
            mDialog.setCanceledOnTouchOutside(false);
        }
        mDialog.show();
    }

    public void dismissProgressDialog() {

        if (mDialog != null) {
            mDialog.dismiss();
        }
        mDialog = null;
    }
}