package nico.styTool;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

/**
 * Created by luxin on 15-12-10.
 *  http://luxin.gitcafe.io
 *  观看鸿阳大神视频
 *  http://www.imooc.com/learn/489
 */
public class ListImageDirPopupWindow extends PopupWindow {

    private int mWidth;
    private int mHeight;

    private View mContentView;
    private ListView mListView;

    private List<FloderBean> mDatas;

    public interface OnDirSelectedListener{
        void OnSelected(FloderBean floderBean);
    }

    public OnDirSelectedListener mSelected;

    public void setOnDirSelectedListener(OnDirSelectedListener mSelected) {
        this.mSelected = mSelected;
    }

    public ListImageDirPopupWindow(Context context,List<FloderBean> list){
        this.mDatas=list;
        calWidthAndHeight(context);

        mContentView= LayoutInflater.from(context).inflate(R.layout.lxw_popup_chose_img,null);
        setContentView(mContentView);
        setWidth(mWidth);
        setHeight(mHeight);

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_OUTSIDE;
            }
        });

        initViews(context);
        initEvent();
    }

    private void initViews(Context context) {
        mListView= (ListView) mContentView.findViewById(R.id.id_lxw_popup_img_listview);
        mListView.setAdapter(new ListDirAdapter(context, mDatas));
    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mSelected!=null){
                    mSelected.OnSelected(mDatas.get(position));
                }
            }
        });
    }

    private void calWidthAndHeight(Context context) {
        WindowManager wm= (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mWidth=outMetrics.widthPixels;
        mHeight= (int) (outMetrics.heightPixels*0.7);

    }
}
