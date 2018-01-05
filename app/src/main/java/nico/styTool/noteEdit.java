package nico.styTool;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import dump.z.BaseActivity_;

public class noteEdit extends BaseActivity_ {
    private EditText et_content;
    private SQLiteDatabase dbread;
    public static int ENTER_STATE = 0;
    public static String last_content;
    public static int id;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.apk_nol, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.sort_e) {
            String content = et_content.getText().toString();
            // Log.d("LOG1", content);
            // 获取写日志时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateNum = sdf.format(date);
            String sql;
            String sql_count = "SELECT COUNT(*) FROM note";
            SQLiteStatement statement = dbread.compileStatement(sql_count);
            long count = statement.simpleQueryForLong();
            // Log.d("COUNT", count + "");
            //Log.d("ENTER_STATE", ENTER_STATE + "");
            // 添加一个新的日志
            if (ENTER_STATE == 0) {
                if (!content.equals("")) {
                    sql = "insert into " + NotesDB.TABLE_NAME_NOTES
                            + " values(" + count + "," + "'" + content
                            + "'" + "," + "'" + dateNum + "')";
                    // Log.d("LOG", sql);
                    Toast.makeText(noteEdit.this, "保存成功", Toast.LENGTH_SHORT).show();
                    dbread.execSQL(sql);
                }
            }
            // 查看并修改一个已有的日志
            else {
                Toast.makeText(noteEdit.this, "更新成功", Toast.LENGTH_SHORT).show();
                // Log.d("执行命令", "执行了该函数");
                String updatesql = "update note set content='"
                        + content + "' where _id=" + id;
                dbread.execSQL(updatesql);
                // et_content.setText(last_content);
            }
            Intent data = new Intent();
            setResult(2, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // 设置无标题
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = sdf.format(date);
        setTitle("" + dateString);
        et_content = (EditText) findViewById(R.id.et_content);
        // 设置软键盘自动弹出
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        NotesDB DB = new NotesDB(this);
        dbread = DB.getReadableDatabase();
        Bundle myBundle = this.getIntent().getExtras();
        last_content = myBundle.getString("info");
        et_content.setText(last_content);
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // if (requestCode == 3 && resultCode == 4) {
        // last_content=data.getStringExtra("data");
        // Log.d("LAST_STRAING", last_content+"gvg");
        // }
    }*/
}
