package nico.styTool;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luxin on 15-12-17.
 * http://luxin.gitcafe.io
 */
public class LookImgAdapter extends PagerAdapter {
    private Context mContext;
    private List<BmobFile> mData;
    private ArrayList<View> views;


    public LookImgAdapter(Context context, List<BmobFile> list) {
        this.mContext = context;
        this.mData = list;
        initViews();
    }

    private void initViews() {
        views = new ArrayList<View>();
        for (int i = 0; i < mData.size(); i++) {
            View view = View.inflate(mContext, R.layout.lxw_item_viewpager_view, null);
            views.add(view);
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = views.get(position);
        final LxwImageView imageView = (LxwImageView) view.findViewById(R.id.lxw_id_item_viewpager_img);

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.lxw_id_item_viewpager_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        final String path = mData.get(position).getUrl();

        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        progressBar.setVisibility(View.GONE);


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
