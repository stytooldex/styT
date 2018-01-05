package nico.styTool;


import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class gifa extends dump.z.BaseActivity_ implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * Called when the activity is first created.
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apktoolmain);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_meizi) {
            click();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gank) {
            bclick();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            cclick();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_se) {
            dclick();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_blog) {
            eclick();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_blogvip) {
            clickvip();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_blogsvip) {
            clicksvip();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.apktool_textview) {
            aac_styTool();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.apktool_textviewa) {
            ac_styTool();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
            //
        } else if (id == R.id.apktool_textviewb) {
            a();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
            //
        } else if (id == R.id.a_upi) {
            ua();
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
            //
        }
        return true;
    }

    public void ua() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = "妮̶";
        for (char anA : a) {
            b.append(mo.replace('妮', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }
        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void a() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = getResources().getString(R.string.android_gifc);
        for (char anA : a) {
            b.append(mo.replace('n', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }
        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void ac_styTool() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = getResources().getString(R.string.android_gifb);
        for (char anA : a) {
            b.append(mo.replace('n', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }
        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void aac_styTool() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = getResources().getString(R.string.android_gifa);
        for (char anA : a) {
            b.append(mo.replace('n', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }
        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void c_styTool() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = getResources().getString(R.string.android_gifa);
        for (char anA : a) {
            b.append(mo.replace('n', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }
        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void clickvip() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = null;
        try {
            mo = "◤Q◢";
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (char anA : a) {
            try {
                b.append(mo.replace('Q', anA));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            try {
                manager.setText(b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TextView t = (TextView) findViewById(R.id.mainTextView1);
        try {
            t.setText(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clicksvip() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = "❦H❧";
        for (char anA : a) {
            b.append(mo.replace('H', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }
        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void click() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = "ζั͡爱 ั͡✾";
        for (char anA : a) {
            b.append(mo.replace('爱', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }
        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void bclick() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = "爱҉";
        for (char anA : a) {
            b.append(mo.replace('爱', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }

        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void cclick() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = "敏ۣۖ";
        for (char anA : a) {
            b.append(mo.replace('敏', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }

        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void dclick() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = "爱\n";
        for (char anA : a) {
            b.append(mo.replace('爱', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }

        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }

    public void eclick() {
        EditText et = (EditText) findViewById(R.id.mainEditText1);
        String os = et.getText().toString();
        char[] a = os.toCharArray();
        StringBuffer b = new StringBuffer("");
        String mo = "‮ ‮ ‮爱";
        for (char anA : a) {
            b.append(mo.replace('爱', anA));
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText(b);
        }

        TextView t = (TextView) findViewById(R.id.mainTextView1);
        t.setText(b);
    }
}



