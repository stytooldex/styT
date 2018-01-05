package dump_dex.data;

/**
 * Created by lum on 2017/10/13.
 */


import android.graphics.drawable.Drawable;

public class Modle {
    public String appLable;
    public String packageName;
    public Drawable appIcon;


    public String getAppLable() {
        return appLable;
    }

    public void setAppLable(String appLable) {
        this.appLable = appLable;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

}