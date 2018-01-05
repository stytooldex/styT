package dump.l;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class loveviayou {
    private static Bitmap getUrlBitmap(String url) {
        Bitmap bm;
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            // byte[] bt=getBytes(is); //注释部分换用另外一种方式解码
            // bm=BitmapFactory.decodeByteArray(bt,0,bt.length);
            bm = BitmapFactory.decodeStream(is); // 如果采用这种解码方式在低版本的API上会出现解码问题
            is.close();
            conn.disconnect();
            return bm;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static final int requestcode = 0x123;


    public static void Modi_WangYi_Mp3_UI_beginning(Activity con) {
        if (!new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/netease/cloudmusic/Ad/").exists()) {

            Toast.makeText(con, "没安装网易音乐或者是没有加载广告", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        con.startActivityForResult(intent, requestcode);

    }

    public static void Modi_WangYi_Mp3_UI_doing(Activity con, int requestCode, int resultCode, Intent in) {
        if (requestCode == requestcode && resultCode == Activity.RESULT_OK) {
            Uri uri = in.getData();
            ContentResolver cr = con.getContentResolver();
            try {
                Bitmap bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                File[] files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/netease/cloudmusic/Ad/").listFiles();
                if (files == null) {
                    Toast.makeText(con, "没安装网易音乐或者是没有加载广告", Toast.LENGTH_LONG).show();
                    return;
                }
                Vector<String> c = new Vector<String>();
                for (File f : files) {
                    if (f.isDirectory()) {
                        File[] temp_files = f.listFiles();
                        if (temp_files == null) continue;
                        for (File inner_f : temp_files) {

                            if (inner_f.isFile())
                                c.add(inner_f.getAbsolutePath());
                        }
                    }
                    if (f.isFile())
                        c.add(f.getAbsolutePath());
                }
                for (String s : c) {
                    OutputStream ops = new FileOutputStream(s);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, ops);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Toast.makeText(con, "替换完成", Toast.LENGTH_SHORT).show();
            //finish();
        }
    }

    public static InputStream GetISfromIntent(Intent u, Activity con) {
        ContentResolver cr = con.getContentResolver();
        try {
            return cr.openInputStream(u.getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
