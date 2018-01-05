package nico.styTool;

import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.widget.Toast;

public class kbili extends Application {
    public static final String a = (Environment.getExternalStorageDirectory().getPath() + "/Android/data/nico.styTool/");
    public static final String b = (a + "dhb");
    public static final String c = (a + "qqtemp");
    public static final String d = (a + "qqkeytemp");

    public static SharedPreferences a(Context context) {
        return context.getSharedPreferences(kbili.class.getPackage().getName() + "_preferences", Context.MODE_PRIVATE);
    }

    public static void a(Context context, String str, int i) {
        Editor edit = a(context).edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static void a(Context context, String str, String str2) {
        Editor edit = a(context).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static void a(Context context, String str, boolean z) {
        Editor edit = a(context).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static void a(String str, Context context) {
        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setText(str.trim());
        Toast.makeText(context, "已复制", Toast.LENGTH_SHORT).show();
    }

    public static int b(Context context, String str, int i) {
        return a(context).getInt(str, i);
    }

    public static String b(Context context, String str, String str2) {
        return a(context).getString(str, str2);
    }

    public static boolean b(Context context, String str, boolean z) {
        return a(context).getBoolean(str, z);
    }
}
