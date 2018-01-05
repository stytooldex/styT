package nico.styTool;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import dump.f.xp;

public class SkinEngine extends dump.z.BaseActivity implements OnClickListener

{
    android.support.v7.widget.Toolbar toolbar;
    ListView listView;
    FeedbackAdapter adapter;
    TextView emptyView;
    static String msg;
    private EditText et_content;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String content = et_content.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            if (content.equals(msg)) {
                Toast.makeText(this, "nit", Toast.LENGTH_SHORT).show();
            } else {
                msg = content;
                // 发送反馈信息
                sendMessage(content);
                Toast.makeText(this, "yef", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "?", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送反馈信息给开发者
     *
     * @param message 反馈信息
     */
    private void sendMessage(String message) {
        BmobPushManager bmobPush = new BmobPushManager();
        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
        query.addWhereEqualTo("isDeveloper", true);
        bmobPush.setQuery(query);
        bmobPush.pushMessage(message);

        saveFeedbackMsg(message);
    }

    /**
     * 保存反馈信息到服务器
     *
     * @param msg 反馈信息
     */
    private void saveFeedbackMsg(String msg) {
        xp feedback = new xp();
        feedback.setContent(msg);
        feedback.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {

                } else {

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        et_content = (EditText) findViewById(R.id.et_content);
        listView = (ListView) findViewById(R.id.lv_feedbacks);

        emptyView = new TextView(this);
        emptyView.setText("っ没有数据");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(15); //设置字体大小
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addContentView(emptyView, params);
        listView.setEmptyView(emptyView);

        BmobQuery<xp> query = new BmobQuery<xp>();
        query.order("-createdAt");
        query.findObjects(new FindListener<xp>() {

            @Override
            public void done(List<xp> object, BmobException e) {
                if (e == null) {
                    adapter = new FeedbackAdapter(SkinEngine.this, object);
                    listView.setAdapter(adapter);
                } else {
                    emptyView.setText((CharSequence) object);
                }
            }

        });

    }


    static class FeedbackAdapter extends BaseAdapter {

        List<xp> fbs;
        LayoutInflater inflater;

        FeedbackAdapter(Context context, List<xp> feedbacks) {
            this.fbs = feedbacks;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fbs.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return fbs.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.fragment_main, null);
                holder.content = convertView.findViewById(R.id.tv_content);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            xp feedback = fbs.get(position);

            holder.content.setText(feedback.getContent());

            return convertView;
        }

        static class ViewHolder {
            TextView content;
        }

    }

}

