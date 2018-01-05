package nico.styTool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import dump.z.BaseActivity;

/**
 * Created by luxin on 15-12-17.
 * http://luxin.gitcafe.io
 */
public class HelpsCommentActivity extends BaseActivity {
    //private final static String TAG = "HelpsCommentActivity";

    private ListView listView;

    private LinearLayout linearLayout;
    private ImageView userImg;
    private TextView username;
    private TextView personality;
    private TextView creatTime;

    private TextView content;
    private ImageView contentImg;
    private GridView gridView;

    private BILIBILI helps;

    private List<Comment> mItemComment;

    private CommentAdapter adapter;


    private View view;

    private View view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lxw_helps_comment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.lxw_id_helps_comment_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
        initView();
        initData();
    }


    private void initData() {
        helps = (BILIBILI) getIntent().getSerializableExtra("helps");
        MyUser myUser = helps.getUser();
        if (myUser.getAuvter() != null) {
            ImageLoader.getInstance(1, ImageLoader.Type.LIFO).loaderImage(Constant.USERIMG + myUser.getAuvter().getUrl(), userImg, true);
        }
        username.setText(myUser.getUsername());
        personality.setText(myUser.getPersonality());
        creatTime.setText(DateUtil.getFriendlyDate(helps.getCreatedAt()));

        int currentLikeNum = helps.getLikeNum();
        final MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        //currentLikeNum++;
        BILIBILI newUser = new BILIBILI();
        newUser.setLikeNum(currentLikeNum + 1);
        newUser.update(helps.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                } else {

                }
            }
        });
        SpannableString spannableString = ExpressionUtil.getSpannableString(this, helps.getContent().replaceAll("hdc1314", "bilibili"));
        content.setText(spannableString);

        String imgpath = null;
        List<BmobFile> phontos = null;
        final PhontoFiles phontoFiles = helps.getPhontofile();
        if (phontoFiles != null) {
            //Log.e(TAG, "====phonoto files is not null");
            imgpath = helps.getPhontofile().getPhoto();
            phontos = helps.getPhontofile().getPhotos();
        }

        if (imgpath != null) {
            contentImg.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            ImageLoader.getmInstance().loaderImage(imgpath, contentImg, true);
            contentImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HelpsCommentActivity.this, LookImageActivity.class);
                    intent.putExtra("helps", helps);
                    startActivity(intent);
                }
            });
        } else if (phontos != null) {
            contentImg.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            GridViewHelpsAdapter gridViewHelpsAdapter = new GridViewHelpsAdapter(this, phontos);
            gridView.setAdapter(gridViewHelpsAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(HelpsCommentActivity.this, LookImageViewPagerActivity.class);
                    intent.putExtra("phontoFiles", phontoFiles);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
        } else {
            contentImg.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }

        listView.addHeaderView(view);
        listView.addHeaderView(view2);

        mItemComment = new ArrayList<Comment>();
        if (mItemComment.size() == 0) {
            query();
        }
        adapter = new CommentAdapter(this, mItemComment);
        listView.setAdapter(adapter);

    }

    private boolean isFirst = true;

    private void query() {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.addWhereEqualTo("helps", new BmobPointer(helps));
        query.setLimit(50);
        query.include("user,helps.user");
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        mItemComment.clear();
                        mItemComment.addAll(object);
                        adapter.notifyDataSetChanged();
                    }
                } else {

                }

            }

     });
    }


    private void initView() {

        listView = (ListView) findViewById(R.id.lxw_id_helps_comment_listview);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.lxw_id_push_helps_comment_f);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushComment();
            }
        });
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.lxw_item_helps2, null);


        userImg = (ImageView) view.findViewById(R.id.lxw_id_item_helps_userimg);
        username = (TextView) view.findViewById(R.id.lxw_id_item_helps_username);
        personality = (TextView) view.findViewById(R.id.lxw_id_item_helps_user_personality);
        creatTime = (TextView) view.findViewById(R.id.lxw_id_item_helps_create_time);

        content = (TextView) view.findViewById(R.id.lxw_id_item_helps_content);
        gridView = (GridView) view.findViewById(R.id.lxw_id_item_helps_gridview);
        contentImg = (ImageView) view.findViewById(R.id.lxw_id_item_helps_content_img);

        LayoutInflater inflater2 = LayoutInflater.from(this);
        view2 = inflater2.inflate(R.layout.likn, null);
    }

    private void pushComment() {
        final MyUser myUserw = BmobUser.getCurrentUser(MyUser.class);
        if (myUserw != null) {
            if (myUserw.getAuvter() != null) {
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.lxw_push_helps_comment, null);
                final EditText ediComment = (EditText) view.findViewById(R.id.lxw_id_push_helps_comment_edi);
                ediComment.setError(null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(view).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comment = ediComment.getText().toString().trim();
                        if (TextUtils.isEmpty(comment)) {

                            // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
                            return;
                        }
                        push(comment, myUserw);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
            } else {
                Intent a = new Intent(HelpsCommentActivity.this, UserProfileActivity.class);
                startActivity(a);
                finish();
                Toast.makeText(HelpsCommentActivity.this, "请上传头像", Toast.LENGTH_SHORT).show();
            }

        } else {
            Intent in = new Intent(this, app_th.class);
            startActivity(in);
        }


    }

    /**
     * 发表评论
     *
     * @param comment
     * @param myUser
     */
    private void push(final String comment, final MyUser myUser) {
        Comment comment1 = new Comment();
        comment1.setUser(myUser);
        comment1.setHelps(helps);
        comment1.setComment(comment);
        comment1.save(new SaveListener<String>() {

            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    if (!myUser.getObjectId().equals(helps.getUser().getObjectId())) {
                        bmobpush(myUser, comment);
                    }
                    ToastUtil.show(HelpsCommentActivity.this, "评论成功", Toast.LENGTH_SHORT);
                    isFirst = false;
                    query();
                    // Log.e(TAG, "====评论成功====");
                } else {
                    ToastUtil.show(HelpsCommentActivity.this, "评论失败,请检查网络连接后重试", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 使用bmob进行消息推送
     *
     * @param myUser
     */
    private void bmobpush(MyUser myUser, String comment) {
        String installationId = helps.getUser().getObjectId();
        BmobPushManager bmobPushManager = new BmobPushManager();
        BmobQuery<MyUserInstallation> query = new BmobQuery<MyUserInstallation>();
        query.addWhereEqualTo("uid", installationId);
        bmobPushManager.setQuery(query);
        bmobPushManager.pushMessage(myUser.getUsername() + "评论了你");

        NotifyMsg notifyMsg = new NotifyMsg();
        notifyMsg.setHelps(helps);
        notifyMsg.setUser(helps.getUser());
        notifyMsg.setAuthor(myUser);
        notifyMsg.setStatus(false);
        notifyMsg.setMessage(comment);
        notifyMsg.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.setMessage("请登录之后在评论")
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HelpsCommentActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }).setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.show();
    }


}
