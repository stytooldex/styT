package nico.styTool;

/**
 * Created by lum on 2017/11/29.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private static OnItemClickListener mItemClickListener;
    private ArrayList<String> mData;

    public Adapter(ArrayList<String> data) {
        this.mData = data;
    }

    public static void setOnItemClickListener(OnItemClickListener li) {
        Adapter.mItemClickListener = li;
    }

    @Override
    public Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list, parent, false);
        return new Holder(item);
    }

    @Override
    public void onBindViewHolder(final Adapter.Holder holder, int position) {
        //holder.tv.setText("item " + position);
        holder.tv.setText(mData.get(position));
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(holder.getLayoutPosition(), holder.tv.getText().toString());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tv;

        public Holder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.fileName_s);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String text);
    }
}