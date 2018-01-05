package nico.styTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luxin on 15-12-18.
 * http://luxin.gitcafe.io
 */
public class DateUtil {

    /**
     * 获取友好时间表示
     *
     * @param s
     * @return
     */
    public static String getFriendlyDate(String s) {
        Date old = toDate(s);
        Date nowTime = new Date(System.currentTimeMillis());
        long values = nowTime.getTime() - old.getTime();
        values /= 1000;
        if (values >= 0 && values < 60) {
            return values + "秒前";
        }
        if (values >= 60 && values < 60 * 60) {
            return values / 60 + "分钟前";
        }
        if (values >= 60 * 60 && values < 60 * 60 * 24) {
            return values / 3600 + "小时前";
        }
        if (values >= 60 * 60 * 24 && values < 60 * 60 * 24 * 2) {
            return "昨天:" + old.getHours() + ":" + old.getTime();
        }
        if (values >= 60 * 60 * 24 * 2 && values < 60 * 60 * 24 * 3) {
            return "前天:" + old.getHours() + ":" + old.getTime();
        }
        return s;
    }

    public static Date toDate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
