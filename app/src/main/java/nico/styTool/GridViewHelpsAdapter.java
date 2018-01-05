package nico.styTool;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luxin on 15-12-15.
 * http://luxin.gitcafe.io
 */
public class GridViewHelpsAdapter extends BaseAdapter {

    private Context mContext;
    private List<BmobFile> mDatas;
    private LayoutInflater inflater;

    public GridViewHelpsAdapter(Context context, List<BmobFile> list) {
        this.mContext = context;
        this.mDatas = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lxw_item_helps_gridview, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.lxw_id_item_helps_gridview_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GridView gridView = (GridView) parent;
        int horizontalSpacing = gridView.getHorizontalSpacing();
        int numCloms = gridView.getNumColumns();
        int itemWidth = (gridView.getWidth() - (numCloms * horizontalSpacing) - gridView.getPaddingLeft() - gridView.getPaddingRight()) / numCloms;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(itemWidth, itemWidth);
        holder.imageView.setLayoutParams(lp);
        holder.imageView.setImageResource(R.drawable.pictures_no);

        String path = mDatas.get(position).getUrl();
        // ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loaderImage(path,holder.imageView,true);

        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
