package nico.styTool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lum on 2017/12/7.
 */

public class ToaU {

    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}