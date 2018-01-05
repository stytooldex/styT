package nico.styTool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import dump.z.BaseActivity_;

/**
 * Created by luxin on 15-12-23.
 * http://luxin.gitcafe.io
 */
public class MessageActivity extends BaseActivity_ {

    private ListView listView;

    private MessageAdapter adapter;

    private List<NotifyMsg> mItemData;

    private MaterialRefreshLayout materialRefreshLayout;

    private RefreshType mType = RefreshType.LOADMORE;

    private MyUser myUser;

    private int numPage;

    private enum RefreshType {
        REFRESH, LOADMORE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lxw_message);
        numPage = 0;
        myUser = BmobUser.getCurrentUser(MyUser.class);
        initView();
        initData();
        initEvent();

    }


    private void initView() {
        listView = (ListView) findViewById(R.id.lxw_id_message_listview);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.lxw_id_message_refresh);
    }

    private void initEvent() {
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mType = RefreshType.REFRESH;
                query(0);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                mType = RefreshType.LOADMORE;
                query(numPage);
            }
        });
    }

    private void initData() {
        mItemData = new ArrayList<NotifyMsg>();
        adapter = new MessageAdapter(this, mItemData);
        listView.setAdapter(adapter);
        if (mItemData.size() == 0) {
            mType = RefreshType.REFRESH;
            query(0);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BILIBILI helps = mItemData.get(position).getHelps();
                Intent intent = new Intent(MessageActivity.this, HelpsCommentActivity.class);
                intent.putExtra("helps", helps);
                startActivity(intent);
                if (!mItemData.get(position).isStatus()) {
                    updateMessageStatus(position);
                }
            }
        });
    }

    /**
     * @param position
     */
    private void updateMessageStatus(int position) {
        NotifyMsg msg = mItemData.get(position);
        msg.setStatus(true);
        msg.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                } else {

                }
            }
        });
    }

    private void query(int page) {
        BmobQuery<NotifyMsg> query = new BmobQuery<NotifyMsg>();
        String objectId = myUser.getObjectId();
        query.setLimit(Constant.NUMBER_PAGER);
        query.order("-createdAt");
        query.setSkip(Constant.NUMBER_PAGER * page);
        query.addWhereEqualTo("user", myUser);
        query.include("author,helps,user,comment,helps.phontofile");
        query.findObjects(new FindListener<NotifyMsg>() {
            @Override
            public void done(List<NotifyMsg> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        if (mType == RefreshType.REFRESH) {
                            mItemData.clear();
                            numPage = 0;
                        }
                        numPage++;
                        mItemData.addAll(object);
                        adapter.notifyDataSetChanged();
                        materialRefreshLayout.finishRefresh();
                        materialRefreshLayout.finishRefreshLoadMore();
                    } else if (RefreshType.REFRESH == mType) {
                        materialRefreshLayout.finishRefresh();
                    } else if (RefreshType.LOADMORE == mType) {
                        materialRefreshLayout.finishRefreshLoadMore();
                    } else {
                        numPage--;
                    }
                } else {
                    materialRefreshLayout.finishRefresh();
                    materialRefreshLayout.finishRefreshLoadMore();
                }
            }

        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // query(numPage-1);
    }
}
