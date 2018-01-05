package nico.styTool;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

//import com.bumptech.glide.Glide;

public class RobotChatAdapter extends BaseAdapter {
    private Context mContext;
    private List<RobotChat> mData;
    private LayoutInflater inflater;
    private MyUser user;

    private MyUser myUser = null;

    public RobotChatAdapter(Context context, List<RobotChat> list, MyUser user) {
        this.mContext = context;
        this.mData = list;
        this.user = user;

        // myUser = BmobUser.getCurrentUser(this, MyUser.class);
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
    public int getItemViewType(int position) {
        RobotChat robotChat = mData.get(position);
        if (robotChat.getType() == RobotChat.ChatType.INCOMING) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                convertView = inflater.inflate(R.layout.lxw_robot_from_msg, null);
                holder = new ViewHolder();
                holder.msg = convertView.findViewById(R.id.lxw_id_robot_from_msg);
                holder.userimg = convertView.findViewById(R.id.lxw_id_robot_from_userimg);
            } else {
                convertView = inflater.inflate(R.layout.lxw_robot_to_msg, null);
                holder = new ViewHolder();
                holder.msg = convertView.findViewById(R.id.lxw_id_robot_to_msg);
                holder.userimg = convertView.findViewById(R.id.lxw_id_robot_to_userimg);

            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // String str1 = user.getEmail().replace("@qq.com", "");
        // Glide.with(mContext).load(Constant.USERIMG + user.getAuvter().getUrl()).com.bumptech.glide.load.engine.DiskCacheStrategy(DiskCacheStrategy.ALL).into(holder.muserimg);
        ImageLoader.getmInstance().loaderImage("http://qlogo4.store.qq.com/qzone/" + "2652649464" + "/" + "2652649464" + "/100", holder.userimg, true);
        final RobotChat weibo = mData.get(position);
        convertView.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {//实现接口中的方法
                Toast.makeText(mContext, "已复制内容", Toast.LENGTH_SHORT).show();
                ClipboardManager manager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setText(mData.get(position).getMsg());

                return false;
            }
        });
        holder.msg.setText(mData.get(position).getMsg());

        return convertView;
    }


    private class ViewHolder {
        TextView msg;
        CircleImageView userimg;
        CircleImageView muserimg;
    }
}
