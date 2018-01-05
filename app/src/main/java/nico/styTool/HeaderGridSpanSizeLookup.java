package nico.styTool;

import android.support.v7.widget.GridLayoutManager;

/**
 * Created by lum on 2017/12/10.
 */

public class HeaderGridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private HeaderRecyclerViewAdapter<?, ?> mAdapter;
    private GridLayoutManager mGridLayoutManager;

    public HeaderGridSpanSizeLookup(HeaderRecyclerViewAdapter<?, ?> adapter, GridLayoutManager gridLayoutManager) {
        mAdapter = adapter;
        mGridLayoutManager = gridLayoutManager;
    }

    @Override
    public int getSpanSize(int position) {
        //spansize表示一个item的跨度，跨度了多少个span
        if (mAdapter.isHeader(position))
            return mGridLayoutManager.getSpanCount();
        else
            return 1;
    }
}