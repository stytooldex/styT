package nico.styTool;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by lum on 2017/12/11.
 */

public abstract class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_FOOTER = 2000000;//footerView默认type
    private static final int BASE_ITEM_TYPE_HEADER = 1000000;// headerView默认type
    private static final int BASE_ITEM_TYPE_GENERAL = 0;// 一般view默认type 0也是android源码中的默认的type值

    private ArrayList<DataBean> mHeaders = new ArrayList<>();
    private ArrayList<DataBean> mFooters = new ArrayList<>();
    private ArrayList<DataBean> mGeneralData = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderType(viewType)) {
            return onCreateHeaderViewHolder(parent, viewType);
        } else if (isFooterType(viewType)) {
            return onCreateFooterViewHolder(parent, viewType);
        }
        return onCreateGeneralViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            onBindHeaderViewHolder(holder, position);
        } else if (isFooterViewPos(position)) {
            onBindFooterViewHolder(holder, position - getHeaderViewCount() - getInnerItemCount());
        } else {
            onBindGeneralViewHolder(holder, position - getHeaderViewCount());
        }
    }


    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType);

    public abstract RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType);

    public abstract RecyclerView.ViewHolder onCreateGeneralViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract void onBindGeneralViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return getInnerItemCount() + getHeaderViewCount() + getFooterViewCount();
    }

    public int getHeaderViewCount() {
        return mHeaders.size();
    }

    public int getFooterViewCount() {
        return mFooters.size();
    }

    private int getInnerItemCount() {
        return mGeneralData.size();
    }

    private boolean isHeaderViewPos(int position) {
        return getHeaderViewCount() > position;
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeaderViewCount() + getInnerItemCount();
    }

    private boolean isHeaderType(int type) {
        if (mHeaders.size() > 0) {
            for (DataBean bean : mHeaders) {
                if (bean.getType() == type) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFooterType(int type) {
        if (mFooters.size() > 0) {
            for (DataBean bean : mFooters) {
                if (bean.getType() == type) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaders.get(position).getType();
        } else if (isFooterViewPos(position)) {
            return mFooters.get(position - getHeaderViewCount() - getInnerItemCount()).getType();
        }
        return mGeneralData.get(position - getHeaderViewCount()).getType();
    }

    public void addHeader(Object data) {
        addHeader(data, BASE_ITEM_TYPE_HEADER);
    }

    public void addHeader(Object data, int type) {
        mHeaders.add(new DataBean(data, type));
    }

    public void addFooter(Object data) {
        addFooter(data, BASE_ITEM_TYPE_FOOTER);
    }

    public void addFooter(Object data, int type) {
        mFooters.add(new DataBean(data, type));
    }

    public void addGeneral(Object data) {
        addGeneral(data, BASE_ITEM_TYPE_GENERAL);
    }

    public void addGeneral(Object data, int type) {
        mGeneralData.add(new DataBean(data, type));
    }

    private class DataBean {
        private Object data;
        private int type;

        private DataBean(Object data, int type) {
            super();
            this.data = data;
            this.type = type;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //为了兼容GridLayout
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHeaderViewPos(position)) {
                        return gridLayoutManager.getSpanCount();
                    } else if (isFooterViewPos(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null)
                        return spanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {

                StaggeredGridLayoutManager.LayoutParams p =
                        (StaggeredGridLayoutManager.LayoutParams) lp;

                p.setFullSpan(true);
            }
        }
    }
}