package dump.x;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nico.styTool.R;

class ItemApkAdapter extends BaseAdapter {
    private ArrayList<ItemApk> alItemApk;
    private LayoutInflater lInflater;

    static class ViewHolder {
        ImageView ivApkIcon;
        TextView tvApkName;
        TextView tvApkRunning;
        TextView tvApkSize;
        TextView tvApkVersion;

        ViewHolder() {
        }
    }

    ItemApkAdapter(Context context, ArrayList<ItemApk> alItemApl) {
        this.alItemApk = alItemApl;
        this.lInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.alItemApk.size();
    }

    public Object getItem(int position) {
        return this.alItemApk.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.lInflater.inflate(R.layout.item_, null);
            holder = new ViewHolder();
            holder.tvApkName = convertView.findViewById(R.id.tvApkName);
            holder.tvApkVersion = convertView.findViewById(R.id.tvApkVersion);
            holder.ivApkIcon = convertView.findViewById(R.id.ivApkIcon);
            holder.tvApkSize = convertView.findViewById(R.id.tvApkSize);
            holder.tvApkRunning = convertView.findViewById(R.id.tvApkRunning);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ItemApk ia = this.alItemApk.get(position);
        holder.tvApkName.setText(ia.getDisplayName());
        holder.tvApkVersion.setText(ia.getVersionName());
        holder.ivApkIcon.setImageDrawable(ia.getIcon());
        holder.tvApkSize.setText(ia.getStrSize());
        holder.tvApkRunning.setText(ia.getRunning() ? "Running" : "");
        return convertView;
    }
}
