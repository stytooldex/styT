package nico.styTool;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lum on 2017/12/10.
 */

public abstract class HeaderRecyclerViewAdapter<H extends RecyclerView.ViewHolder, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    protected static final int TYPE_HEADER = -1;
    protected static final int TYPE_ITEM = -2;
    private Map<Integer, Integer> typeMap;
    private Map<Integer, Integer> headerPositionForPosition;
    private Map<Integer, Integer> itemPositionWithHeader;
    private int count = 0;

    public HeaderRecyclerViewAdapter(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null && layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new HeaderGridSpanSizeLookup(this, gridLayoutManager));
        }
        registerAdapterDataObserver(new DataObserver());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        init();
    }

    private void init() {
        typeMap = new HashMap<>();
        headerPositionForPosition = new HashMap<>();
        itemPositionWithHeader = new HashMap<>();
        count = getAllCount();
        int index = 0;
        for (int i = 0; i < getHeaderCount(); i++) {
            typeMap.put(index, TYPE_HEADER);
            headerPositionForPosition.put(index, i);
            index++;
            for (int j = 0; j < getItemCountForSection(i); j++) {
                typeMap.put(index, TYPE_ITEM);
                headerPositionForPosition.put(index, i);
                itemPositionWithHeader.put(index, j);
                index++;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER)
            return onCreateHeaderViewHolder(parent, viewType);
        else
            return onCreateItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewTypeForPostion(position) == TYPE_HEADER)
            onBindHeaderViewHolder((H) holder, headerPositionForPosition.get(position));
        else
            onBindItemViewHolder((VH) holder, headerPositionForPosition.get(position), itemPositionWithHeader.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewTypeForPostion(position);
    }

    public boolean isHeader(int position) {
        return typeMap.get(position) == TYPE_HEADER;
    }

    @Override
    public int getItemCount() {
        return count;
    }

    //顶部标题View个数
    protected abstract int getHeaderCount();

    //对应标题的Item个数
    protected abstract int getItemCountForSection(int section);

    //创建标题ViewHolder
    protected abstract H onCreateHeaderViewHolder(ViewGroup parent, int viewType);

    //创建对应标题Item的ViewHolder
    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindHeaderViewHolder(H holder, int headerPosition);

    protected abstract void onBindItemViewHolder(VH holder, int headerPosition, int itemPosition);

    private int getAllCount() {
        int count = 0;
        for (int i = 0; i < getHeaderCount(); i++) {
            count += 1 + getItemCountForSection(i);
        }
        return count;
    }

    private int getItemViewTypeForPostion(int position) {
        return typeMap.get(position);
    }

    class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            init();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            init();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            init();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            init();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            init();
        }
    }

}