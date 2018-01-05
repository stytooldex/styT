package nico.styTool;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class secondFragment extends Fragment {


    public secondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        android.support.v7.widget.CardView spinner = (android.support.v7.widget.CardView) v.findViewById(R.id.asaCardView);
        ViewTooltip
                .on(spinner)
                .autoHide(true, 4000)
                .position(ViewTooltip.Position.BOTTOM)
                .corner(4)
                .align(ViewTooltip.ALIGN.CENTER)
                .clickToHide(true)
                .text("打卡满半个月可以免费领取上面物品")
                .show();
        return v;
    }

}
