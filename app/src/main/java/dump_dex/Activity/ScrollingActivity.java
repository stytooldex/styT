package dump_dex.Activity;

import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import dump.z.BaseActivity_;
import nico.styTool.R;

public class ScrollingActivity extends BaseActivity_ {
    private EditText ed1;
    private TextView hTextView;

    private int jz1 = 2, jz2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ed1 = (EditText) findViewById(R.id.mainMaterialEditText1);
        hTextView = (TextView) findViewById(R.id.mainHTextView1);
        Button bt1 = (Button) findViewById(R.id.mainButton1);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.type);
                switch (languages[pos]) {
                    case "2进制":
                        jz1 = 2;
                        ed1.setKeyListener(DigitsKeyListener.getInstance("01"));
                        break;
                    case "8进制":
                        jz1 = 8;
                        ed1.setKeyListener(DigitsKeyListener.getInstance("01234567"));
                        break;
                    case "10进制":
                        jz1 = 10;
                        ed1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                        break;
                    case "16进制":
                        jz1 = 16;
                        ed1.setKeyListener(DigitsKeyListener.getInstance("0123456789ABCDEF"));
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.type);
                switch (languages[pos]) {
                    case "2进制":
                        jz2 = 2;
                        break;
                    case "8进制":
                        jz2 = 8;
                        break;
                    case "10进制":
                        jz2 = 10;
                        break;
                    case "16进制":
                        jz2 = 16;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                if (ed1.getText().toString().length() == 0 || ed1.getText().toString().equals(null)) {
                    Toast.makeText(ScrollingActivity.this, "null", Toast.LENGTH_SHORT).show();
                } else {
                    ChangeText(dump_dex.Activity.base.baseNum(ed1.getText().toString(), jz1, jz2));
                }

            }


        });
    }

    private void ChangeText(final String text) {
        hTextView.setText(text);
    }


}
