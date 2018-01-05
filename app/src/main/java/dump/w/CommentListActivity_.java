package dump.w;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import nico.styTool.Constant;
import nico.styTool.ImageLoader;
import nico.styTool.MyUser;
import nico.styTool.R;

public class CommentListActivity_ extends dump.z.BaseActivity_ {

    //Post_ weibo = new Post_();

    private int currentLikeNum;

    public void queryOne() {
        BmobQuery<Post_> query = new BmobQuery<>();
        query.getObject(String.valueOf(nico.SPUtils.get(CommentListActivity_.this, "mes", "")), new QueryListener<Post_>() {

            @Override
            public void done(final Post_ movie, BmobException e) {
                if (e == null) {
                    currentLikeNum = movie.getLikeNum();
                    final MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                    //currentLikeNum++;
                    Post_ newUser = new Post_();
                    newUser.setLikeNum(currentLikeNum + 1);
                    newUser.update(weibo.getObjectId(), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {

                            } else {

                            }
                        }
                    });

                    TextView text1 = (TextView) findViewById(R.id.asaTextView1);//id
                    text1.setText(nico.SPUtils.get(CommentListActivity_.this, "mes_", "") + "");//id

                    TextView text2 = (TextView) findViewById(R.id.asaTextView3);//内容
                    text2.setText(movie.getContent() + "");//内容


                    nico.styTool.CircleImageView mVAuthor0 = (nico.styTool.CircleImageView) findViewById(R.id.v_author0);
                    String str0 = "" + movie.getAuthor().getEmail();
                    String str1 = str0.replace("@qq.com", "");
                    ImageLoader.getmInstance().loaderImage("http://qlogo4.store.qq.com/qzone/" + str1 + "/" + str1 + "/100", mVAuthor0, true);

                    if (movie.getAuthor().getAuvter() != null) {
                        String auvterPath = "" + movie.getAuthor().getAuvter().getUrl();
                        //Glide.with(mContext).load("").DiskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL).into(holder.userimg);
                        ImageLoader.getmInstance().loaderImage(Constant.USERIMG + weibo.getAuthor().getAuvter().getUrl(), mVAuthor0, true);
                    }

                    //Log.i("life", ""+object.getName());

                    LinearLayout button = (LinearLayout) findViewById(R.id.asaLinearLayout1);//内容
                    button.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        //返回值代表是否已经处理结束，后面是否需要再处理
                        public boolean onLongClick(View v) {

                            final String[] os = {"复制全部", "编辑内容", "举报帖子", "分享", "删除", "管理员删除"};
                            AlertDialog.Builder builder = new AlertDialog.Builder(CommentListActivity_.this);
                            AlertDialog alert = builder.setTitle("管理员操作")
                                    .setItems(os, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case 0:
                                                    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                                    manager.setText("\n内容:" + movie.getContent());
                                                    break;
                                                case 1:
                                                    if (bmobUser.getObjectId().equals(movie.getAuthor().getObjectId() + "")) {
                                                        LayoutInflater inflater = LayoutInflater.from(CommentListActivity_.this);
                                                        View view = inflater.inflate(R.layout.aw_q, null);
                                                        final EditText ediComment = view.findViewById(R.id.awqEditText1);
                                                        ediComment.setText(movie.getContent());
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(CommentListActivity_.this);
                                                        builder.setView(view).setPositiveButton("发布", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                String comment = ediComment.getText().toString().trim();
                                                                if (TextUtils.isEmpty(comment)) {
                                                                    // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
                                                                    return;
                                                                }
                                                                Post_ newUser = new Post_();
                                                                newUser.setContent(comment);
                                                                newUser.update(weibo.getObjectId(), new UpdateListener() {

                                                                    @Override
                                                                    public void done(BmobException e) {
                                                                        if (e == null) {
                                                                            queryOne();
                                                                        } else {

                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                            }
                                                        }).create().show();
                                                        //Toast.makeText(MainActivity.this, "相等", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        //Toast.makeText(MainActivity.this, "不相等", Toast.LENGTH_SHORT).show();
                                                    }

                                                    break;
                                                case 2:
                                                /*Fk_ feedback = new Fk_();
                                                feedback.setContent("" + object.getTitle());
                                                feedback.save(getActivity(), new SaveListener() {

                                                    @Override
                                                    public void onFailure(int p1, String p2) {
                                                        Toast.makeText(getActivity(), "举报失败", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onSuccess() {
                                                        Toast.makeText(getActivity(), "举报成功", Toast.LENGTH_SHORT).show();
                                                        BmobSMS.requestSMSCode(getActivity(), "13414957848", "注册模板", new RequestSMSCodeListener() {

                                                            @Override
                                                            public void done(Integer smsId, BmobException ex) {

                                                            }
                                                        });
                                                    }

                                                });*/
                                                    break;
                                                case 3:
                                                    Intent sendIntent = new Intent();
                                                    sendIntent.setAction(Intent.ACTION_SEND);
                                                    sendIntent.putExtra(Intent.EXTRA_TEXT, movie.getContent() + "");
                                                    sendIntent.setType("text/plain");
                                                    startActivity(sendIntent);
                                                    break;
                                                case 4:
                                                    if (bmobUser.getObjectId().equals(movie.getAuthor().getObjectId() + "")) {
                                                        Post_ p2 = new Post_();
                                                        p2.setObjectId(nico.SPUtils.get(CommentListActivity_.this, "mes", "") + "");
                                                        p2.delete(new UpdateListener() {

                                                            @Override
                                                            public void done(BmobException e) {
                                                                if (e == null) {
                                                                    finish();
                                                                    Toast.makeText(CommentListActivity_.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(CommentListActivity_.this, "删除失败", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                        //Toast.makeText(MainActivity.this, "相等", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        //Toast.makeText(MainActivity.this, "不相等", Toast.LENGTH_SHORT).show();
                                                    }

                                                    break;
                                                case 5:
                                                    String objectId = (String) BmobUser.getObjectByKey("objectId");
                                                    if (objectId.equals("fcdee930ae")) {
                                                        Post_ p = new Post_();
                                                        p.setObjectId(nico.SPUtils.get(CommentListActivity_.this, "mes", "") + "");
                                                        p.delete(new UpdateListener() {

                                                            @Override
                                                            public void done(BmobException e) {
                                                                if (e == null) {
                                                                    finish();
                                                                    Toast.makeText(CommentListActivity_.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(CommentListActivity_.this, "删除失败", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        //Toast.makeText(MainActivity.this, "不相等", Toast.LENGTH_SHORT).show();
                                                    }
                                                    break;
                                            }
                                            ////showToast("你选择了" + os[which]);
                                        }
                                    }).create();
                            alert.show();
                            //true事件处理结束，后面不需要再处理
                            return true;
                        }
                    });
                } else {
                    //loge(e);
                }
            }
        });
    }

    ///////
    ///
    ListView listView;
    static EditText et_content;

    //Post_ weibo = new Post_();
    private static void startIntent(String classes) {
        SpannableString spannableString = new SpannableString(classes);
        int curosr = et_content.getSelectionStart();
        et_content.getText().insert(curosr, spannableString);
    }

    private void publishComment(String content) {
        nico.styTool.MyUser user = BmobUser.getCurrentUser(nico.styTool.MyUser.class);
        if (user == null) {
            Intent intent = new Intent(CommentListActivity_.this, nico.styTool.app_th.class);
            startActivity(intent);
            //toast("发表评论前请先登陆");
            return;
        } else if (TextUtils.isEmpty(content)) {
            //toast("发表评论不能为空");
            return;
        }
        final Comment_ comment = new Comment_();
        comment.setContent(content);
        comment.setPost(weibo);
        comment.setSignature(android.os.Build.MODEL + System.getProperty("line.separator"));
        comment.setUser(user);
        comment.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    findComments();
                    et_content.setText("");
                    //toast("评论成功");
                } else {

                }
            }
        });
    }

    private void findComments() {
        BmobQuery<Comment_> query = new BmobQuery<Comment_>();
        // pointer类型
        query.addWhereEqualTo("post", new BmobPointer(weibo));
        query.include("user,post.author");
        query.findObjects(new FindListener<Comment_>() {

            @Override
            public void done(List<Comment_> object, BmobException e) {
                if (e == null) {
                    comments = object;
                    adapter.notifyDataSetChanged();
                    et_content.setText("");
                } else {
                    //loge(e);
                }
            }

        });

    }

    static List<Comment_> comments = new ArrayList<Comment_>();
    MyAdapter adapter;
    static Post_ weibo = new Post_();


    /////
    private static class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context mContext;

        MyAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        static class ViewHolder {
            TextView tv_content;
            TextView tv_author;
            nico.styTool.CircleImageView userimg;
            // TextView tv_;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return comments.size();
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
                convertView = mInflater.inflate(R.layout.a_mbdx, null);
                holder = new ViewHolder();

                holder.userimg = convertView.findViewById(R.id.a_mnd1);
                holder.tv_content = convertView.findViewById(R.id.a_mnd1o);
                holder.tv_author = convertView.findViewById(R.id.a_qx);
                //  holder.tv_ = convertView.findViewById(R.id.abbbwTextView1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Comment_ comment = comments.get(position);

            if (comment.getUser() != null) {
                holder.tv_author.setText("" + comment.getUser().getUsername());
                //  holder.tv_.setText("" + comment.getCreatedAt() + "｜" + comment.getSignature());

            }
            String str0 = "" + comment.getUser().getEmail();
            String str1 = str0.replace("@qq.com", "");
            ImageLoader.getmInstance().loaderImage("http://qlogo4.store.qq.com/qzone/" + str1 + "/" + str1 + "/100", holder.userimg, true);
            final String str = comment.getContent();

            holder.tv_content.setText(str);
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startIntent("@" + comment.getUser().getUsername() + " ");
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                //返回值代表是否已经处理结束，后面是否需要再处理
                public boolean onLongClick(View v) {
                    final String[] os = {"复制评论"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    AlertDialog alert = builder.setTitle("用户操作")
                            .setItems(os, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            ClipboardManager manager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                            manager.setText(comment.getContent() + "");
                                            break;

                                    }
                                    ////showToast("你选择了" + os[which]);
                                }
                            }).create();
                    alert.show();
                    //true事件处理结束，后面不需要再处理
                    return true;
                }
            });
            // Toast.makeText(mContext, getCount() + "", Toast.LENGTH_SHORT).show();
            Post_ newUser = new Post_();
            newUser.setmTitle(getCount());
            newUser.update(weibo.getObjectId(), new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {

                    } else {

                    }
                }
            });
            if (comment.getUser().getAuvter() != null) {
                String auvterPath = "" + comment.getUser().getAuvter().getUrl();
                //Glide.with(mContext).load("").DiskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL).into(holder.userimg);
                ImageLoader.getmInstance().loaderImage(Constant.USERIMG + comment.getUser().getAuvter().getUrl(), holder.userimg, true);
            }

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_ixi);
        //Toolbar部分
        weibo.setObjectId(nico.SPUtils.get(this, "mes", "") + "");
        queryOne();

        adapter = new MyAdapter(this);
        et_content = (EditText) findViewById(R.id.et_content0);

        listView = (ListView) findViewById(R.id.listview0);
        listView.setAdapter(adapter);
        android.support.v7.widget.AppCompatImageView btn_publish = (android.support.v7.widget.AppCompatImageView) findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                publishComment(et_content.getText().toString());
            }
        });
        findComments();

    }

}
