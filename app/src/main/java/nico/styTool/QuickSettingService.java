package nico.styTool;

import android.os.Build;
import android.service.quicksettings.TileService;
import android.support.annotation.RequiresApi;

/**
 * Created by lum on 2017/11/18.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class QuickSettingService extends TileService {
    //当用户从Edit栏添加到快速设定中调用
    @Override
    public void onTileAdded() {
       // Log.d(LOG_TAG, "onTileAdded");
    }
    //当用户从快速设定栏中移除的时候调用
    @Override
    public void onTileRemoved() {
      //  Log.d(LOG_TAG, "onTileRemoved");
    }
    // 点击的时候
    @Override
    public void onClick() {
       // Log.d(LOG_TAG, "onClick");
    }
    // 打开下拉菜单的时候调用,当快速设置按钮并没有在编辑栏拖到设置栏中不会调用
    //在TleAdded之后会调用一次
    @Override
    public void onStartListening () {
      //  Log.d(LOG_TAG, "onStartListening");
    }
    // 关闭下拉菜单的时候调用,当快速设置按钮并没有在编辑栏拖到设置栏中不会调用
    // 在onTileRemoved移除之前也会调用移除
    @Override
    public void onStopListening () {
        //Log.d(LOG_TAG, "onStopListening");
    }

}