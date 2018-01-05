package com.bilibili.magicasakurademo;

import android.app.Activity;
import android.content.Context;

import nico.styTool.R;

/**
 * Created by 26526 on 2017/12/30.
 */

public class MyThemeUtils {

    public static void changTheme(Activity activity, Theme theme) {
        if (activity == null)
            return;
        int style = R.style.RedTheme;
        switch (theme) {
            case BROWN:
                style = R.style.BrownTheme;
                break;
            case BLUE:
                style = R.style.BlueTheme;
                break;
            case BLUE_GREY:
                style = R.style.BlueGreyTheme;
                break;
            case YELLOW:
                style = R.style.YellowTheme;
                break;
            case DEEP_PURPLE:
                style = R.style.DeepPurpleTheme;
                break;
            case PINK:
                style = R.style.PinkTheme;
                break;
            case GREEN:
                style = R.style.GreenTheme;
                break;
                /*
            case DEEP_ORANGE:
                style = R.style.DeepOrangeTheme;
                break;
            case GREY:
                style = R.style.GreyTheme;
                break;
            case CYAN:
                style = R.style.CyanTheme;
                break;*/
            case AMBER:
                style = R.style.AmberTheme;
                break;
            default:
                break;
        }
        activity.setTheme(style);
    }
    public static void changTheme0(Activity activity, Theme theme) {
        if (activity == null)
            return;
        int style = R.style.RedTheme0;
        switch (theme) {
            case BROWN:
                style = R.style.BrownTheme0;
                break;
            case BLUE:
                style = R.style.BlueTheme0;
                break;
            case BLUE_GREY:
                style = R.style.BlueGreyTheme0;
                break;
            case YELLOW:
                style = R.style.YellowTheme0;
                break;
            case DEEP_PURPLE:
                style = R.style.DeepPurpleTheme0;
                break;
            case PINK:
                style = R.style.PinkTheme0;
                break;
            case GREEN:
                style = R.style.GreenTheme0;
                break;
            case AMBER:
                style = R.style.AmberTheme0;
                break;
            default:
                break;
        }
        activity.setTheme(style);
    }


    public static Theme getCurrentTheme(Context context) {
        int value = PreferenceUtils.getInstance(context).getIntParam("change_theme_key", 0);
        return MyThemeUtils.Theme.mapValueToTheme(value);
    }

    public enum Theme {
        RED(0),
        BROWN(1),
        BLUE(2),
        BLUE_GREY(3),
        YELLOW(4),
        DEEP_PURPLE(5),
        PINK(6),
        GREEN(7),
        //DEEP_ORANGE(8),
        // GREY(9),
        //CYAN(10),
        AMBER(8);

        private int mValue;

        Theme(int value) {
            this.mValue = value;
        }

        public static Theme mapValueToTheme(final int value) {
            for (Theme theme : Theme.values()) {
                if (value == theme.getIntValue()) {
                    return theme;
                }
            }
            // If run here, return default
            return RED;
        }

        static Theme getDefault() {
            return RED;
        }

        public int getIntValue() {
            return mValue;
        }
    }
}