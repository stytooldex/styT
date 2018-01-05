package nico.styTool;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by luxin on 15-12-18.
 * http://luxin.gitcafe.io
 */
public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<Comment> mData;

    public CommentAdapter(Context context, List<Comment> list) {
        this.mContext = context;
        this.mData = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lxw_item_helps_comment, null);
            holder = new ViewHolder();
            holder.userimg = (ImageView) convertView.findViewById(R.id.lxw_id_item_helps_comment_userimg);
            holder.username = (TextView) convertView.findViewById(R.id.lxw_id_helps_comment_username);
            holder.comment = (TextView) convertView.findViewById(R.id.lxw_id_helps_comment_content);
            holder.createtime = (TextView) convertView.findViewById(R.id.lxw_id_helps_comment_createtime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.userimg.setImageResource(R.drawable.github);

        Comment comment = mData.get(position);
        MyUser myUser = comment.getUser();

        if (myUser.getAuvter() != null) {
            ImageLoader.getmInstance().loaderImage(Constant.USERIMG + myUser.getAuvter().getUrl(), holder.userimg, true);
        }

        holder.username.setText(myUser.getUsername());
        holder.comment.setText(comment.getComment());
        holder.createtime.setText(DateUtil.getFriendlyDate(comment.getCreatedAt()));
        return convertView;
    }


    private class ViewHolder {
        ImageView userimg;
        TextView username;
        TextView comment;
        TextView createtime;

    }
}
