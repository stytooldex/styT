package nico.styTool;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import dump.q.ContantValue;
import dump.z.BaseActivity_;

public class MeiziActivity extends BaseActivity_ implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayAdapter<String> itemAdapter;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_main);

        initView();

    }

    private void initView() {

        editText = (EditText) findViewById(R.id.mainEditText10);
        listView = (ListView) findViewById(R.id.api_id_views_listview);
        itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, ContantValue.viewItem);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(this);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editText.getText().toString().trim();
                if (TextUtils.isEmpty(comment)) {
                    //a.setError("内容不能为空");
                    return;
                }

                ToastUtil.show(MeiziActivity.this, "复制成功", Toast.LENGTH_SHORT);
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                manager.setText(comment);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startIntent("🇦");
                break;
            case 1:
                startIntent("🇧");
                break;
            case 2:
                startIntent("🇨");
                break;
            case 3:
                startIntent("🇩");
                break;
            case 4:
                startIntent("🇪");
                break;
            case 5:
                startIntent("🇫");
                break;
            case 6:
                startIntent("🇬");
                break;
            case 7:
                startIntent("🇭");
                break;
            case 8:
                startIntent("🇮");
                break;
            case 9:
                startIntent("🇯");
                break;
            case 10:
                startIntent("🇰");
                break;
            case 11:
                startIntent("🇱");
                break;
            case 12:
                startIntent("🇲");
                break;
            case 13:
                startIntent("🇳");
                break;
            case 14:
                startIntent("🇴");
                break;
            case 15:
                startIntent("🇵");
                break;
            case 16:
                startIntent("🇶");
                break;
            case 17:
                startIntent("🇷");
                break;
            case 18:
                startIntent("🇸");
                break;
            case 19:
                startIntent("🇹");
                break;
            case 20:
                startIntent("🇺");
                break;
            case 21:
                startIntent("🇻");
                break;
            case 22:
                startIntent("🇼");
                break;
            case 23:
                startIntent("🇽");
                break;
            case 24:
                startIntent("🇾");
                //parent.setVisibility(View.GONE);
                //AbsListView.LayoutParams param = new AbsListView.LayoutParams(0,0); //设置item的weidth和height都为0

                //将设置好的布局属性应用到ListView/GridView等的Item上;
                //parent.setLayoutParams(param);

                break;
            case 25:
                startIntent("🇿");
                break;
        }
    }

    private void startIntent(String classes) {
        SpannableString spannableString = new SpannableString(classes);
        int curosr = editText.getSelectionStart();
        editText.getText().insert(curosr, spannableString);
    }
}

