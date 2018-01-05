package nico.styTool;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by luxin on 15-12-23.
 */
public class MessageAdapter extends BaseAdapter {
    private List<NotifyMsg> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, List<NotifyMsg> list) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lxw_item_message, null);
            holder = new ViewHolder();
            holder.userimg = (CircleImageView) convertView.findViewById(R.id.lxw_id_item_message_comment_userimg);
            holder.username = (TextView) convertView.findViewById(R.id.lxw_id_item_message_username);
            holder.status = (TextView) convertView.findViewById(R.id.lxw_id_item_message_status);
            holder.content = (TextView) convertView.findViewById(R.id.lxw_id_item_message_content);
            holder.creattime = (TextView) convertView.findViewById(R.id.lxw_id_item_message_createtime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.userimg.setImageResource(R.drawable.github);

        NotifyMsg msg = mDatas.get(position);

        MyUser author = mDatas.get(position).getAuthor();
        String userimgpath = null;
        if (author.getAuvter() != null) {
            userimgpath = author.getAuvter().getUrl();
        }
        if (userimgpath != null) {
            // ImageLoader.getmInstance().loaderImage(Constant.USERIMG+userimgpath,holder.userimg,true);
            Glide.with(mContext).load(Constant.USERIMG + userimgpath).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.userimg);
        }

        holder.username.setText(author.getUsername());
        holder.creattime.setText(mDatas.get(position).getCreatedAt());
        holder.content.setText(msg.getMessage());

        if (msg.isStatus()) {
            holder.status.setText("已读");
        } else {
            holder.status.setText("未读");
        }

        return convertView;
    }

    private class ViewHolder {
        CircleImageView userimg;
        TextView username;
        TextView status;
        TextView content;
        TextView creattime;
    }
}
