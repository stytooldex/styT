package nico.styTool;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by luxin on 15-12-10.
 * http://luxin.gitcafe.io
 */
public class ListDirAdapter extends ArrayAdapter<FloderBean> {
    private Context mContext;
    private LayoutInflater inflater;

    public ListDirAdapter(Context context, List<FloderBean> objects) {
        super(context, 0, objects);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lxw_item_popup_chose_img, parent, false);
            holder = new ViewHolder();
            holder.mImge = (ImageView) convertView.findViewById(R.id.id_lxw_item_popup_dir_image);
            holder.mDirName = (TextView) convertView.findViewById(R.id.id_lxw_item_popup_dir_name);
            holder.mDirCount = (TextView) convertView.findViewById(R.id.id_lxw_item_popup_dir_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FloderBean bean = getItem(position);
        // ImageLoader.getInstance(1, ImageLoader.Type.LIFO).loaderImage(bean.getFirsterImagePath(),holder.mImge,false);
        Glide.with(mContext).load(bean.getFirsterImagePath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mImge);
        holder.mDirName.setText(bean.getName());
        holder.mDirCount.setText(bean.getCount() + "");
        return convertView;
    }

    private class ViewHolder {
        ImageView mImge;
        TextView mDirName;
        TextView mDirCount;
    }
}
