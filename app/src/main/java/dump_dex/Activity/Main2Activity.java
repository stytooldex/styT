package dump_dex.Activity;

/**
 * Created by lum on 2017/10/13.
 */

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dump.o.SharedPreferencesUtil;
import dump.z.BaseActivity_;
import dump_dex.data.Modle;
import nico.styTool.R;

public class Main2Activity extends BaseActivity_ {
    List<Modle> mList = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_el) {
            PackageManager mPackageManager = this.getPackageManager();
            //獲取全部應用
            List<ApplicationInfo> installedApplications = mPackageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
            // 排序
            Collections.sort(installedApplications, new ApplicationInfo.DisplayNameComparator(mPackageManager));
            //
            for (ApplicationInfo app : installedApplications) {
                Modle modle = new Modle();
                modle.setAppLable((String) app.loadLabel(mPackageManager));
                modle.setAppIcon(app.loadIcon(mPackageManager));
                modle.setPackageName(app.packageName);
                //mList.add(modle);
                //SharedPreferencesUtil
                //SharedPreferenceUtil.save(this, "file_key", "value_key", modle);
            }

            return true;
        }
        if (id == R.id.lxw_actio) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mli);

        init();

    }

    private void init() {

        initdata();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerAdapter adapter = new myRecyclerAdapter(this, mList);
        mRecyclerView.setAdapter(adapter);

        SharedPreferencesUtil.putListData("stringKeyf", mList);
        //SharedPreferencesUtil.putData("stringKey", "str");
    }


    class myRecyclerAdapter extends RecyclerView.Adapter<myRecyclerAdapter.myRecyclerViewHodler> {
        private List<Modle> mDatas;
        private Context mContext;
        private LayoutInflater inflater;


        myRecyclerAdapter(Context context, List<Modle> datas) {
            this.mContext = context;
            this.mDatas = datas;
            inflater = LayoutInflater.from(mContext);
        }


        @Override
        public myRecyclerAdapter.myRecyclerViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.tgy2, parent, false);
            return new myRecyclerViewHodler(view);
        }

        @Override
        public void onBindViewHolder(myRecyclerViewHodler holder, int position) {
            holder.mApptMame.setText(mDatas.get(position).getAppLable());
            holder.mPackageMame.setText(mDatas.get(position).getPackageName());
            holder.mAppIcon.setImageDrawable(mDatas.get(position).getAppIcon());
        }


        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class myRecyclerViewHodler extends RecyclerView.ViewHolder {

            TextView mApptMame;
            TextView mPackageMame;
            ImageView mAppIcon;

            myRecyclerViewHodler(View itemView) {
                super(itemView);
                mApptMame = (TextView) itemView.findViewById(R.id.item_app_name);
                mPackageMame = (TextView) itemView.findViewById(R.id.item_package_name);
                mAppIcon = (ImageView) itemView.findViewById(R.id.item_app_icon);
            }
        }
    }

    //數據處理
    private void initdata() {
        PackageManager mPackageManager = this.getPackageManager();
        //獲取全部應用
        List<ApplicationInfo> installedApplications = mPackageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        // 排序
        Collections.sort(installedApplications, new ApplicationInfo.DisplayNameComparator(mPackageManager));
        //
        for (ApplicationInfo app : installedApplications) {
            Modle modle = new Modle();
            modle.setAppLable((String) app.loadLabel(mPackageManager));
            modle.setAppIcon(app.loadIcon(mPackageManager));
            modle.setPackageName(app.packageName);
            mList.add(modle);
        }

    }


//        //獲取當前執行的Activity名
//        public String  getCurrentActivityName( ){
//        ActivityManager activityManager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
//        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
//        return runningActivity;
//    }
}