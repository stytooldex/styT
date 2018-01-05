package nico.styTool;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by luxin on 15-12-12.
 * http://luxin.gitcafe.io
 */
public class EmotionPagerAdapter extends PagerAdapter {

    private ArrayList<GridView> mDatas;

    public EmotionPagerAdapter(Context context, ArrayList<GridView> list) {
        super();
        this.mDatas = list;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(mDatas.get(position));
        return mDatas.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mDatas.get(position));
    }
}
