package nico;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import dump.b.Main4Activity;
import nico.styTool.BILIBILI;
import nico.styTool.Constant;
import nico.styTool.HelpsCommentActivity;
import nico.styTool.MyUser;
import nico.styTool.PublishActivity;
import nico.styTool.R;
import nico.styTool.SampleAdapter;
import nico.styTool.app_th;

public class Blanknt extends Fragment {

    private SampleAdapter adapter;

    private MaterialRefreshLayout materialRefreshLayout;

    private FloatingActionButton fab;

    private ArrayList<BILIBILI> mItemList;

    private int numPage;

    private enum RefleshType {
        REFRESH, LOAD_MORE
    }

    private RefleshType refleshType = RefleshType.LOAD_MORE;

    /**
     * 初始化数据
     */
    private void query(int page) {
        BmobQuery<BILIBILI> helpsBmobQuery = new BmobQuery<BILIBILI>();
        helpsBmobQuery.setLimit(Constant.NUMBER_PAGER);
        helpsBmobQuery.order("-createdAt");
//        BmobDate date=new BmobDate(new Date(System.currentTimeMillis()));
//        helpsBmobQuery.addWhereLessThan("createdAt",date);

        helpsBmobQuery.setSkip(Constant.NUMBER_PAGER * page);
        helpsBmobQuery.include("user,phontofile");
//        helpsBmobQuery.include("phontofile");
        helpsBmobQuery.findObjects(new FindListener<BILIBILI>() {
            @Override
            public void done(List<BILIBILI> object, BmobException e) {
                if (e == null) {
                    //mProgressDialog.dismiss();
                    if (object.size() > 0) {
                        if (refleshType == RefleshType.REFRESH) {

                            numPage = 0;
                            mItemList.clear();

                        }
                        if (object.size() < Constant.NUMBER_PAGER) {

                        }
                        mItemList.addAll(object);
                        numPage++;
                        adapter.notifyDataSetChanged();
                        materialRefreshLayout.finishRefresh();
                        materialRefreshLayout.finishRefreshLoadMore();
                    } else if (refleshType == RefleshType.REFRESH) {

                        materialRefreshLayout.finishRefresh();
                    } else if (refleshType == RefleshType.LOAD_MORE) {
                        materialRefreshLayout.finishRefreshLoadMore();
                    } else {
                        numPage--;
                    }

                } else {
                    //mProgressDialog.dismiss();
                    materialRefreshLayout.finishRefresh();
                    materialRefreshLayout.finishRefreshLoadMore();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    AlertDialog alert = builder.setMessage("未获取到数据，请检查网络数据后重试。")
                            .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create();
                    alert.show();
                }
            }

        });
    }
/*


    @Override
    protected void onRestart() {
        super.onRestart();
        initUserImg();
        fab.setVisibility(View.VISIBLE);
        if (isRefresh) {
            query(numPage - 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  query();
    } */

    /*howUserProfile() {
            if (myUser != null) {
                Intent intent = new Intent(this, UserProfileActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } */
    // private RecyclerView mRecyclerView;

    // private RecyclerView.Adapter mAdapter;

    public void showIntent(Class<?> clzz) {
        Intent intent = new Intent(getActivity(), clzz);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.viewbyid, null);
        // mProgressDialog = ProgressDialog.show(this, null, "正在加载");

        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.main_refresh);
        android.support.v7.widget.RecyclerView listView = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.main_listview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(mLayoutManager);


        TextView floatingActionButton = (TextView) view.findViewById(R.id.apktoo1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(Main4Activity.class);
            }
        });
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refleshType = RefleshType.REFRESH;
                fab.setVisibility(View.VISIBLE);
                query(0);

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                refleshType = RefleshType.LOAD_MORE;
                query(numPage);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyUser myUserw = BmobUser.getCurrentUser(MyUser.class);
                if (myUserw != null) {
                    Intent intent = new Intent(getActivity(), PublishActivity.class);
                    startActivity(intent);
                } else {
                    Intent in = new Intent(getActivity(), app_th.class);
                    startActivity(in);
                }

            }
        });

        mItemList = new ArrayList<>();
        //adapter = new SampleAdapter(getActivity(), mItemList);
        //listView.setAdapter(adapter);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SampleAdapter(mLayoutManager, getActivity(), mItemList);
        //View footer = LayoutInflater.from(getActivity()).inflate(R.layout.likn2, null);
        //listView.addHeaderView(footer);
        listView.setAdapter(adapter);
        if (mItemList.size() == 0) {
            refleshType = RefleshType.REFRESH;
            query(0);
        }
        adapter.setOnItemClickListener(new SampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), HelpsCommentActivity.class);
                intent.putExtra("helps", mItemList.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // Toast.makeText(MDRvActivity.this,"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
