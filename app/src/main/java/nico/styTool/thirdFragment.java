package nico.styTool;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class thirdFragment extends Fragment {


    public thirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View d = inflater.inflate(R.layout.fragment_third, container, false);
        FloatingActionButton fab = (FloatingActionButton) d.findViewById(R.id.fab);
        android.support.v7.widget.AppCompatSpinner spinner_s = (android.support.v7.widget.AppCompatSpinner) d.findViewById(R.id.spinner_text);
        ViewTooltip
                .on(fab)
                .autoHide(false, 1000)
                .position(ViewTooltip.Position.BOTTOM)
                .corner(4)
                .align(ViewTooltip.ALIGN.CENTER)
                .clickToHide(true)
                .text("可以展开部分功能")
                .show();
        ViewTooltip
                .on(spinner_s)
                .autoHide(false, 100)
                .position(ViewTooltip.Position.BOTTOM)
                .corner(4)
                .align(ViewTooltip.ALIGN.CENTER)
                .clickToHide(true)
                .text("输入爱奇视频艺链接选择需要的接口回车{推荐12接口}")
                .show();
        return d;
    }

}
