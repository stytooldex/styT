package nico.Compat;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import dump.j.o;
import dump.k.i_a;
import dump.w.CommentListActivity_;
import dump.w.Post_;
import nico.styTool.R;

/**
 * Created by lum on 2017/10/17.
 */

public class Compat3 extends Fragment {
    ListView listView;
    static List<Post_> weibos = new ArrayList<>();
    MyAdapter adapter;

    /**
     * 查询微博
     */
    private void findWeibos() {
        BmobQuery<Post_> query = new BmobQuery<Post_>();
        query.order("-updatedAt");
        query.include("author");// 希望在查询微博信息的同时也把发布人的信息查询出来，可以使用include方法
        query.findObjects(new FindListener<Post_>() {
            @Override
            public void done(List<Post_> object, BmobException e) {
                if (e == null) {
                    weibos = object;
                    adapter.notifyDataSetChanged();
                } else {
                    //loge(e);
                }
            }

        });

    }

    /**
     * 发布微博，发表微博时关联了用户类型，是一对一的体现
     */
    private void publishWeibo(String content) {
        nico.styTool.MyUser user = BmobUser.getCurrentUser(nico.styTool.MyUser.class);
        if (user == null) {
            Intent intent = new Intent(getActivity(), nico.styTool.app_th.class);
            startActivity(intent);
            //toast("发布微博前请先登陆");
            return;
        } else if (TextUtils.isEmpty(content)) {
            //toast("发布内容不能为空");
            return;
        }
        // 创建微博信息
        Post_ weibo = new Post_();
        //weibo.setTitle(bi);
        weibo.setLikeNum(0);
        weibo.setmTitle(0);
        weibo.setContent(content);
        weibo.setSignature(android.os.Build.MODEL + System.getProperty("line.separator"));
        weibo.setAuthor(user);
        weibo.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    findWeibos();
                } else {

                }
            }
        });
    }

    private static class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        private Context mContext;

        private String getCreateTimes(String dates) {
            Date old = toDate(dates);
            Date nowtime = new Date(System.currentTimeMillis());
            long values = nowtime.getTime() - old.getTime();
            values = values / 1000;
            //Log.e(TAG, "====values  time===" + values);
            if (values < 60 && values > 0) {
                return values + "秒前";
            }
            if (values > 60 && values < 60 * 60) {
                return values / 60 + "分钟前";
            }
            if (values < 60 * 60 * 24 && values > 60 * 60) {
                return values / 3600 + "小时前";
            }
            if (values < 60 * 60 * 24 * 2 && values > 60 * 60 * 24) {
                return "昨天";
            }
            if (values < 60 * 60 * 3 * 24 && values > 60 * 60 * 24 * 2) {
                return "前天";
            }
            if (values < 60 * 60 * 24 * 30 && values > 60 * 60 * 24 * 3) {
                return values / (60 * 60 * 24) + "天前";
            }
            if (values < 60 * 60 * 24 * 365 && values > 60 * 60 * 24 * 30) {
                return nowtime.getMonth() - old.getMonth() + "个月前";
            }
            return values / (60 * 60 * 24 * 30 * 365) + "年前";
        }

        private Date toDate(String date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
            Date date1 = null;
            try {
                date1 = format.parse(date);
                //  Log.e(TAG,"===date==="+date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date1;
        }

        MyAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        static class ViewHolder {
            TextView tv_content;
            TextView tv_author;
            TextView tv_;
            TextView muserimg;
            TextView img;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
//			return bmobObjects.size();
            return weibos.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.a_bbbw, null);
                holder = new ViewHolder();
                holder.tv_content = convertView.findViewById(R.id.mtv_content);
                holder.tv_author = convertView.findViewById(R.id.mtv_author);
                holder.muserimg = convertView.findViewById(R.id.mtv_au);
                holder.img = convertView.findViewById(R.id.mabbbwTextView10);
                holder.tv_ = convertView.findViewById(R.id.mabbbwTextView1);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            final Post_ weibo = weibos.get(position);

            final String str = weibo.getContent();

            holder.tv_content.setText(str);

            holder.muserimg.setText("" + weibo.getAuthor().getUsername());

            holder.img.setText("评论量" + Integer.toString(weibo.getmTitle()));

            holder.tv_author.setText(getCreateTimes(weibo.getCreatedAt()));

            holder.tv_.setText("点击量" + Integer.toString(weibo.getLikeNum()));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CommentListActivity_.class);
                    //intent.putExtra("objectId", weibo.getObjectId());
                    mContext.startActivity(intent);
                    String content = weibo.getObjectId();
                    nico.SPUtils.put(mContext, "mes_", weibo.getAuthor().getUsername());
                    nico.SPUtils.put(mContext, "mes", content);
                }
            });

            return convertView;
        }

    }

    private void xft() {

        BmobQuery<o> query = new BmobQuery<>();
        query.getObject("132724038f", new QueryListener<o>() {

            @Override
            public void done(o movie, BmobException e) {
                if (e == null) {
                    String s = movie.getContent();
                    String sr = "1";
                    if (s.equals(sr)) {

                        BmobQuery<i_a> query = new BmobQuery<>();
                        query.getObject("03bf357e85", new QueryListener<i_a>() {

                            @Override
                            public void done(i_a move, BmobException e) {
                                if (e == null) {
                                    String s = move.getContent();
                                    String sr = nico.styTool.Constant.a_mi + "\n" + nico.styTool.Constant.a_miui;
                                    if (s.equals(sr)) {
                                        findWeibos();//
                                    } else {
                                        nico.styTool.ToastUtil.show(getActivity(), "版本不一致，请更新", Toast.LENGTH_SHORT);
                                        //finish();
                                    }
                                } else {

                                }
                            }
                        });
                    } else {
                        nico.styTool.ToastUtil.show(getActivity(), "兴趣圈维护中", Toast.LENGTH_SHORT);
                        // finish();
                    }
                } else {
                    Toast.makeText(getActivity(), "兴趣圈需要网络", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_weibo_, null);
        final SharedPreferences setting = getActivity().getSharedPreferences("data_false1", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {

            BmobUser.logOut();
            setting.edit().putBoolean("FIRST", false).apply();
        } else {

        }
        xft();
        adapter = new MyAdapter(getActivity());
        //et_content = (EditText) findViewById(R.id.et_content);
        listView = view.findViewById(R.id.listview);

        //  itemAdapter = new SessionItemAdapter(this);
        listView.setAdapter(adapter);

        android.support.design.widget.FloatingActionButton btn_publish = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.appbili0);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nico.styTool.MyUser myUserw = BmobUser.getCurrentUser(nico.styTool.MyUser.class);
                if (myUserw != null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view = inflater.inflate(R.layout.ad_ssss, null);
                    final EditText b2 = view.findViewById(R.id.adssssEditText2);//
                    final nico.styTool.MyUser myUser = BmobUser.getCurrentUser(nico.styTool.MyUser.class);
                    BmobQuery<nico.styTool.MyUser> query = new BmobQuery<nico.styTool.MyUser>();
                    query.getObject(myUser.getObjectId(), new QueryListener<nico.styTool.MyUser>() {

                        @Override
                        public void done(nico.styTool.MyUser movie, BmobException e) {
                            if (e == null) {
                                String s = "" + movie.getEmailVerified();
                                String sr = "true";
                                if (s.equals(sr)) {
                                    b2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20000)});
                                    //Toast.makeText(WeiboListActivity.this, "已激活帐号支持(20000字)特权", Toast.LENGTH_SHORT).show();
                                    //这
                                } else {
                                    //Toast.makeText(WeiboListActivity.this, "未激活帐号最多(68字)", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                            }
                        }
                    });
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setView(view).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String comment = b2.getText().toString().trim();
                            if (TextUtils.isEmpty(comment)) {

                                return;
                            }
                            publishWeibo(comment);
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                } else {
                    Intent intent = new Intent(getActivity(), nico.styTool.app_th.class);
                    startActivity(intent);
                }
                // TODO Auto-generated method stub

            }
        });

        //findWeibos();
        return view;
    }
}
