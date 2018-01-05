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
import android.widget.ListView;
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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by lum on 2017/12/10.
 */

public class SampleAdapter extends HeaderRecyclerViewAdapter<SampleAdapter.HeaderViewHolder, SampleAdapter.ItemViewHolder> {
    private Context mContext;
    private ArrayList<BILIBILI> mData;
    private List<Comment> mItemComment;
    private static List<Comment> weibos = new ArrayList<>();
    CommentAdapter adapter;

    private SampleAdapter.OnItemClickListener onItemClickListener;

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

    public SampleAdapter(RecyclerView.LayoutManager layoutManager, Context context, ArrayList<BILIBILI> data) {
        super(layoutManager);
        mData = data;
        mContext = context;
    }

    public void setOnItemClickListener(SampleAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    protected int getHeaderCount() {
        return mData == null ? 0 : mData.size();
    }


    @Override
    protected int getItemCountForSection(int section) {
        return mItemComment == null ? 0 : mItemComment.size();
    }

    @Override
    protected HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lxw_item_helps, parent, false));
    }

    @Override
    protected ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false));
    }

    @Override
    protected void onBindHeaderViewHolder(final HeaderViewHolder holder, int headerPosition) {
        //holder.mTextView.setText("我是标题" + headerPosition);
        holder.userimg.setImageResource(R.drawable.github);
        holder.contentImg.setImageResource(R.drawable.pictures_no);
        holder.contentImg.setVisibility(View.GONE);
        holder.gridView.setVisibility(View.GONE);
        holder.contentImg.setFocusable(false);
        holder.gridView.setFocusable(false);

        BILIBILI helps = mData.get(headerPosition);
        // Log.e(TAG, "===helps=====createAt=" + helps.getCreatedAt());

        MyUser myUser = helps.getUser();

        if (myUser == null) {
            //Log.e(TAG, "====myUser is null===");
        }

        if (myUser.getAuvter() != null) {
            String auvterPath = "" + myUser.getAuvter().getUrl();
            //  Log.e(TAG,"===auvter url===="+myUser.getAuvter().getUrl());

            // ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loaderImage(auvterPath, holder.userimg, true);
            Glide.with(mContext).load(auvterPath).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL).into(holder.userimg);
        }

        holder.username.setText(myUser.getUsername());
        if (myUser.getPersonality() != null) {
            holder.personality.setText(myUser.getPersonality().toString().length() > 16 ? myUser.getPersonality().substring(0, 22) + "..." : myUser.getPersonality().toString());
        }
        holder.creatTime.setText(getCreateTimes(helps.getCreatedAt()));

        SpannableString spannableString = getSpannableString(helps.getContent().replaceAll("hdc1314", "bilibili"), mContext);
        holder.content.setText(spannableString);

        PhontoFiles phontoFiles = helps.getPhontofile();
        if (phontoFiles != null) {
            setImages(helps, phontoFiles, holder.frameLayout, holder.gridView, holder.contentImg);
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

    @Override
    protected void onBindItemViewHolder(ItemViewHolder holder, final int headerPosition, final int itemPosition) {
        final Comment weibo = weibos.get(itemPosition);

        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Comment> query = new BmobQuery<>();
        //query.addWhereEqualTo("author", user);    // 查询当前用户的所有微博
        query.order("-updatedAt");
        query.include("author");// 希望在查询微博信息的同时也把发布人的信息查询出来，可以使用include方法
        query.setLimit(4);
        query.findObjects(new FindListener<Comment>() {

            @Override
            public void done(List<Comment> object, BmobException e) {
                if (e == null) {
                    weibos = object;
                    adapter.notifyDataSetChanged();
                } else {

                }
            }

        });
        //adapter = new MyAdapter(getActivity());
        holder.listView.setAdapter(adapter);
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        nico.styTool.CircleImageView userimg;
        TextView username;
        TextView personality;
        TextView creatTime;
        TextView content;
        FrameLayout frameLayout;
        GridView gridView;
        ImageView contentImg;

        public HeaderViewHolder(View itemView) {
            super(itemView);
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

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ListView listView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            listView = (ListView) itemView.findViewById(R.id.lxw_id_helps_comment_listview0);

        }
    }
}