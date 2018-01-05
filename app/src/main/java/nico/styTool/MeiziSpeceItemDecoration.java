package nico.styTool;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by luxin on 16-1-1.
 */
public class MeiziSpeceItemDecoration  extends RecyclerView.ItemDecoration{
    private int space;

    public MeiziSpeceItemDecoration(int space){
        this.space=space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
        if(parent.getChildAdapterPosition(view)==0){
            outRect.top=space;
        }
    }
}
