package nico.styTool;

import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NativeConfigStore extends dump.z.BaseActivity_ {

    private EditText et;

    private EditText t1;

    private EditText t2;

    private LinearLayout button;

    private void giff() {

        LinearLayout button1 = (LinearLayout) findViewById(R.id.axpLinearLayout2);
        LinearLayout button2 = (LinearLayout) findViewById(R.id.axpLinearLayout1);
        Intent intent = getIntent();
        String s = intent.getStringExtra("#");
        String sr = "A";
        if (s.equals(sr)) {
            button1.setVisibility(View.VISIBLE);

        } else {
            button2.setVisibility(View.VISIBLE);
        }

        button = (LinearLayout) findViewById(R.id.axLinearLayout1);
        t1 = (EditText) findViewById(R.id.axEditText2);//结果
        t2 = (EditText) findViewById(R.id.axEditText1);//原

        Button id = (Button) findViewById(R.id.axButton1);
        id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String comment = t2.getText().toString();

            }

        });

        Button tid = (Button) findViewById(R.id.axButton2);
        tid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String comment = t2.getText().toString();

            }

        });
    }

    private TextView t;

    private void c() {//转U
        Button d = (Button) findViewById(R.id.axpButton1);
        d.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String comment = et.getText().toString().trim();
                if (TextUtils.isEmpty(comment)) {
                    // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
                    return;
                }
                stringUnicode(comment);
            }

            //字符串转换unicode
            public String stringUnicode(String string) {
                StringBuffer unicode = new StringBuffer();
                for (int i = 0; i < string.length(); i++) {
                    // 取出每一个字符
                    char c = 0;
                    try {
                        c = string.charAt(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 转换为unicode
                    try {
                        unicode.append("\\u").append(Integer.toHexString(c));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    t.setText(unicode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SharedPreferences mySharedPreferences = getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("via_r", "" + unicode);
                editor.apply();
                return unicode.toString();
            }

        });
    }

    private void a() {
        Button b = (Button) findViewById(R.id.axpButton2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = et.getText().toString().trim();
                if (TextUtils.isEmpty(comment)) {// ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
                    return;
                }
                unicode2string(comment);
            }

            public void unicode2string(String s) {
                List<String> list = new ArrayList<String>();
                String zz = "\\\\u[0-9,a-z,A-Z]{4}";
                Pattern pattern = Pattern.compile(zz);
                Matcher m = pattern.matcher(s);
                while (m.find()) {
                    list.add(m.group());
                }
                for (int i = 0, j = 2; i < list.size(); i++) {
                    String st = list.get(i).substring(j, j + 4);
                    char ch = (char) Integer.parseInt(st, 16);
                    s = s.replace(list.get(i), String.valueOf(ch));
                }
                t.setText(s);
                SharedPreferences mySharedPreferences = getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("via_r", "" + s);
                editor.apply();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.xp_ro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lxw_action_edi_user_profilea) {
            SharedPreferences sharedPreferences = getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE);
            String via = sharedPreferences.getString("via_r", "");
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, via);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if (id == R.id.action_lxw) {
            SharedPreferences sharedPreferences = getSharedPreferences("test", AppCompatActivity.MODE_PRIVATE);
            String via = sharedPreferences.getString("via_r", "");
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            manager.setText("" + via);
            Toast.makeText(this, R.string.dex_apktool, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_xp);

        t = (TextView) findViewById(R.id.axpTextView1);
        et = (EditText) findViewById(R.id.axpEditText1);
        a();
        c();
        giff();
    }
}
