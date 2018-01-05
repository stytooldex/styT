package nico.styTool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by luxin on 15-12-18.
 *  http://luxin.gitcafe.io
 */
public class ToastUtil {
    private static Toast toast;

    public static void show(Context context,CharSequence character,int duration){
        if(toast==null){
            toast=Toast.makeText(context,character,duration);
        }else {
            toast.setText(character);
            toast.setDuration(duration);
        }
        toast.show();
    }
}
