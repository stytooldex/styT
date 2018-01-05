package nico.styTool;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lum on 2017/12/10.
 */

public class DemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BILIBILI> mDatas = new ArrayList<>();
    //private ArrayList<BILIBILI> mDatas;
    private Context mContext;
    private View mHeaderView;
    private View mFooterView;
    private View mEmptyView;
    private DemoAdapter.OnItemClickListener onItemClickListener;
    private int ITEM_TYPE_NORMAL = 0;
    private int ITEM_TYPE_HEADER = 1;
    private int ITEM_TYPE_FOOTER = 2;
    private int ITEM_TYPE_EMPTY = 3;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    //createAt=2015-12-17 15:26:45

    private String getCreateTimes(String dates) {
        Date old = toDate(dates);
        Date nowtime = new Date(System.currentTimeMillis());
        long values = nowtime.getTime() - old.getTime();
        values = values / 1000;
        // Log.e(TAG, "====values  time===" + values);
        if (values < 60 && values > 0) {
            return values + "秒前";
        }
        if (values > 60 && values < 60 * 60) {
            return values / 60 + "分钟前";
        }
        if (values < 60 * 60 * 24 && values > 60 * 60) {
            return values / 3600 + "小时前";
        }
        if (values < 60 * 60 * 24 * 2 && values > 60 * 60 * 24) {
            return "昨天";
        }
        if (values < 60 * 60 * 3 * 24 && values > 60 * 60 * 24 * 2) {
            return "前天";
        }
        if (values < 60 * 60 * 24 * 30 && values > 60 * 60 * 24 * 3) {
            return values / (60 * 60 * 24) + "天前";
        }
        if (values < 60 * 60 * 24 * 365 && values > 60 * 60 * 24 * 30) {
            return nowtime.getMonth() - old.getMonth() + "个月前";
        }
        return values / (60 * 60 * 24 * 30 * 365) + "年前";
    }

    private Date toDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date1 = null;
        try {
            date1 = format.parse(date);
            //  Log.e(TAG,"===date==="+date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }


    /**
     * 替换表情
     *
     * @param str
     * @param context
     * @return
     */
    private SpannableString getSpannableString(String str, Context context) {
        SpannableString spannableString = new SpannableString(str);
        String s = "\\[(.+?)\\]";
        Pattern pattern = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            int id = Expression.getIdAsName(key);
            if (id != 0) {
                Drawable drawable = context.getResources().getDrawable(id);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan imageSpan = new ImageSpan(drawable);
                spannableString.setSpan(imageSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }
    public DemoAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(ArrayList<BILIBILI> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(DemoAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    // 创建视图
    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        } else if (viewType == ITEM_TYPE_EMPTY) {
            return new ViewHolder(mEmptyView);
        } else if (viewType == ITEM_TYPE_FOOTER) {
            return new ViewHolder(mFooterView);
        } else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.lxw_item_helps, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null != mHeaderView && position == 0) {
            return ITEM_TYPE_HEADER;
        }
        if (null != mFooterView
                && position == getItemCount() - 1) {
            return ITEM_TYPE_FOOTER;
        }
        if (null != mEmptyView && mDatas.size() == 0) {
            return ITEM_TYPE_EMPTY;
        }
        return ITEM_TYPE_NORMAL;

    }

    // 为Item绑定数据
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ITEM_TYPE_HEADER || type == ITEM_TYPE_FOOTER || type == ITEM_TYPE_EMPTY) {
            return;
        }
       //int realPos = getRealItemPosition(position);
       //((DemoAdapter.ViewHolder) holder).mTextVie.setText(mDatas.get(realPos));
        ((DemoAdapter.ViewHolder) holder).userimg.setImageResource(R.drawable.github);
        ((DemoAdapter.ViewHolder) holder).contentImg.setImageResource(R.drawable.pictures_no);
        ((DemoAdapter.ViewHolder) holder).contentImg.setVisibility(View.GONE);
        ((DemoAdapter.ViewHolder) holder).gridView.setVisibility(View.GONE);
        ((DemoAdapter.ViewHolder) holder).contentImg.setFocusable(false);
        ((DemoAdapter.ViewHolder) holder).gridView.setFocusable(false);

        BILIBILI helps = mDatas.get(position);
        // Log.e(TAG, "===helps=====createAt=" + helps.getCreatedAt());

        MyUser myUser = helps.getUser();

        if (myUser == null) {
            //Log.e(TAG, "====myUser is null===");
        }

        if (myUser.getAuvter() != null) {
            String auvterPath = "" + myUser.getAuvter().getUrl();
            //  Log.e(TAG,"===auvter url===="+myUser.getAuvter().getUrl());

            // ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loaderImage(auvterPath, holder.userimg, true);
            Glide.with(mContext).load(auvterPath).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL).into(((DemoAdapter.ViewHolder) holder).userimg);
        }

        ((DemoAdapter.ViewHolder) holder).username.setText(myUser.getUsername());
        if (myUser.getPersonality() != null) {
            ((DemoAdapter.ViewHolder) holder).personality.setText(myUser.getPersonality().toString().length() > 16 ? myUser.getPersonality().substring(0, 22) + "..." : myUser.getPersonality().toString());
        }
        ((DemoAdapter.ViewHolder) holder).creatTime.setText(getCreateTimes(helps.getCreatedAt()));

        SpannableString spannableString = getSpannableString(helps.getContent().replaceAll("hdc1314", "bilibili"), mContext);
        ((DemoAdapter.ViewHolder) holder).content.setText(spannableString);

        PhontoFiles phontoFiles = helps.getPhontofile();
        if (phontoFiles != null) {
            setImages(helps, phontoFiles, ((DemoAdapter.ViewHolder) holder).frameLayout, ((DemoAdapter.ViewHolder) holder).gridView, ((DemoAdapter.ViewHolder) holder).contentImg);
        } else {
            //Log.e(TAG, "===phontofiles is null");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }

    private void setImages(final BILIBILI helps, PhontoFiles phontoFiles, FrameLayout frameLayout, GridView gridView, ImageView contentImg) {
        List<BmobFile> list = phontoFiles.getPhotos();
        String path = phontoFiles.getPhoto();
        if (list != null && list.size() > 1) {

            for (BmobFile file : list) {
                // Log.e(TAG, "====list----bmobfile===" + file.getUrl());
            }

            frameLayout.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.VISIBLE);
            contentImg.setVisibility(View.GONE);

            gridView.setAdapter(new GridViewHelpsAdapter(mContext, list));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext, LookImageViewPagerActivity.class);
                    intent.putExtra("phontoFiles", helps.getPhontofile());
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                }
            });

        } else if (path != null) {
            // Log.e(TAG, "===path url====" + path);
            frameLayout.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            contentImg.setVisibility(View.VISIBLE);
            // ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loaderImage(path, contentImg, true);
            Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(contentImg);
            contentImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LookImageActivity.class);
                    intent.putExtra("helps", helps);
                    mContext.startActivity(intent);
                }
            });
        } else {
            frameLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            contentImg.setVisibility(View.GONE);
        }
    }


    private int getRealItemPosition(int position) {
        if (null != mHeaderView) {
            return position - 1;
        }
        return position;
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        if (null != mEmptyView && itemCount == 0) {
            itemCount++;
        }
        if (null != mHeaderView) {
            itemCount++;
        }
        if (null != mFooterView) {
            itemCount++;
        }
        return itemCount;
    }

    public void addHeaderView(View view) {
        mHeaderView = view;
        notifyItemInserted(0);
    }

    public void addFooterView(View view) {
        mFooterView = view;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setEmptyView(View view) {
        mEmptyView = view;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        nico.styTool.CircleImageView userimg;
        TextView username;
        TextView personality;
        TextView creatTime;
        TextView content;
        FrameLayout frameLayout;
        GridView gridView;
        ImageView contentImg;

        ViewHolder(View v) {
            super(v);
            //mTextView = (TextView) v.findViewById(R.id.item_title);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.lxw_id_item_helps_include_avertor);
            userimg = (nico.styTool.CircleImageView) linearLayout.findViewById(R.id.lxw_id_item_helps_userimg);
            username = (TextView) linearLayout.findViewById(R.id.lxw_id_item_helps_username);
            personality = (TextView) linearLayout.findViewById(R.id.lxw_id_item_helps_user_personality);
            creatTime = (TextView) linearLayout.findViewById(R.id.lxw_id_item_helps_create_time);
            content = (TextView) itemView.findViewById(R.id.lxw_id_item_helps_content);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.lxw_id_item_helps_include_img);
            gridView = (GridView) frameLayout.findViewById(R.id.lxw_id_item_helps_gridview);
            contentImg = (ImageView) frameLayout.findViewById(R.id.lxw_id_item_helps_content_img);
        }
    }
}