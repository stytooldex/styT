package nico.styTool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lum on 2017/11/7.
 */

public class ImageChoseAdapter_sty extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private List<String> mDatas;
    private String dirPath;

    private static final int DEFAULT_MAX_ITEM = 9;
    public static Set<String> mSelectImg = new HashSet<String>();

    public ImageChoseAdapter_sty(Context context, List<String> list, String dirPath) {
        this.mContext = context;
        this.mDatas = list;
        this.dirPath = dirPath;
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lxw_item_chose_im, null);
            holder = new ViewHolder();
            holder.mImage = (ImageView) convertView.findViewById(R.id.id_lxw_item_chose_img_image);
            holder.mSelect = (ImageButton) convertView.findViewById(R.id.id_lxw_item_chose_img_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mImage.setImageResource(R.drawable.pictures_no);
        //holder.mSelect.setImageResource(R.drawable.picture_unselected);
        holder.mImage.setColorFilter(null);

        Glide.with(mContext).load(dirPath + "/" + mDatas.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mImage);
        final String filePath = dirPath + "/" + mDatas.get(position);

        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String str = dirPath;
                // String result = str.substring(-0, str.indexOf("//."));
                //System.out.println(strs[0]);
                Toast.makeText(mContext, filePath, Toast.LENGTH_SHORT).show();
            }
        });
/*

 /*
        if (mSelectImg.contains(filePath)) {
            holder.mImage.setColorFilter(Color.parseColor("#77000000"));
            holder.mSelect.setImageResource(R.drawable.pictures_selected);
        } */
        return convertView;
    }

    private class ViewHolder {
        ImageView mImage;
        ImageButton mSelect;
    }


    public interface OnChangeImageSize {
        void ChangeSlect(Set<String> mSlectImgs);
    }

    public OnChangeImageSize mChangeImgSize;

    public void setmChangeImgSize(OnChangeImageSize mChangeImgSize) {
        this.mChangeImgSize = mChangeImgSize;
    }
}
