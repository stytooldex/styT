package nico.styTool;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import dump.z.BaseActivity_;

/**
 * Created by luxin on 15-12-17.
 * http://luxin.gitcafe.io
 */
public class LookImageActivity extends BaseActivity_ {
    private ProgressBar mProgressBar;
    private ImageView imageView;
    private String imgpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lxw_item_viewpager_view);
        initView();
        initData();
        setData();
    }

    private void setData() {
        Glide.with(this).load(imgpath).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }


    private void initView() {
        imageView = (ImageView) findViewById(R.id.lxw_id_item_viewpager_img);
        mProgressBar = (ProgressBar) findViewById(R.id.lxw_id_item_viewpager_progressbar);
    }

    private void initData() {
        BILIBILI helps = (BILIBILI) this.getIntent().getSerializableExtra("helps");
        imgpath = helps.getPhontofile().getPhoto();
    }


}
