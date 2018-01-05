package dump.filem.adapter;

import android.view.View;
import android.widget.TextView;

import dump.filem.adapter.base.RecyclerViewAdapter;
import dump.filem.adapter.base.RecyclerViewHolder;
import dump.filem.bean.TitlePath;
import nico.styTool.R;


/**
 * Created by ${zhaoyanjun} on 2017/1/12.
 */

class TitleHolder extends RecyclerViewHolder<TitleHolder> {

    private TextView textView ;

    TitleHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.title_Name );
    }

    @Override
    public void onBindViewHolder(TitleHolder lineHolder, RecyclerViewAdapter adapter, int position) {
        TitlePath titlePath = (TitlePath) adapter.getItem( position );
        lineHolder.textView.setText( titlePath.getNameState() );
    }
}
