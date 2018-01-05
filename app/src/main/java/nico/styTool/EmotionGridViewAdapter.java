package nico.styTool;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by luxin on 15-12-12.
 * http://luxin.gitcafe.io
 */
public class EmotionGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<EmotionBean> mDatas;

    private LayoutInflater inflater;

    public EmotionGridViewAdapter(Context context, int page) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mDatas = new ArrayList<EmotionBean>();
        initDatas(page);
    }

    private void initDatas(int page) {
        if (page < 5) {
            int len = 20;
            for (int i = (page * len); i < len + (len * page); i++) {
                EmotionBean emotionBean = new EmotionBean();
                emotionBean.setId(Expression.emojID[i]);
                emotionBean.setName(Expression.emojName[i]);
                mDatas.add(emotionBean);
            }
            EmotionBean bean = new EmotionBean();
            bean.setId(R.mipmap.emoji_del);
            mDatas.add(bean);
        } else {
            for (int i = 100; i < 105; i++) {
                EmotionBean emotionBean = new EmotionBean();
                emotionBean.setId(Expression.emojID[i]);
                emotionBean.setName(Expression.emojName[i]);
                mDatas.add(emotionBean);
            }
            EmotionBean bean = new EmotionBean();
            bean.setId(R.mipmap.emoji_del);
            mDatas.add(bean);
        }
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
            convertView = inflater.inflate(R.layout.lxw_emoj_item_gridview, null);
            holder = new ViewHolder();
            holder.emoj = (ImageView) convertView.findViewById(R.id.id_lxw_emoj_item_gridview_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.emoj.setImageResource(mDatas.get(position).getId());
        return convertView;
    }

    private class ViewHolder {
        ImageView emoj;
    }

}
