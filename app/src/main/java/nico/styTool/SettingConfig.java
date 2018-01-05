package nico.styTool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lum on 2017/11/21.
 */
public class SettingConfig {

    private static SettingConfig settingConfig;

    private SharedPreferences sharedPreferences;

    private Context context;

    //抢红包开关，是否打开抢红包
    private String SETTING_RE_Enable = "setting_re_enable";

    //抢红包声音开关
    private String SETTING_RE_MUSIC_ENABLE = "setting_re_music_enable";

    //抢红包回复信息
    private String SETTING_RE_REPLY_MESSAGE = "setting_re_reply_message";

    //抢红包屏蔽某些群或者人的名字
    private String SETTING_RE_MASK_NAME = "setting_re_mask_name";

    public void init(Context context) {
        this.context = context;
        String SETTING_NAME = "nico.styTool_preferences";
        sharedPreferences = context.getSharedPreferences(SETTING_NAME, Context.MODE_PRIVATE);
    }

    public static SettingConfig getInstance() {
        if (settingConfig == null) {
            synchronized (SettingConfig.class) {
                if (settingConfig == null) {
                    settingConfig = new SettingConfig();
                }
            }
        }
        return settingConfig;
    }

    /**
     * 描述:设置抢红包开关
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 下午1:51
     */
    public void setReEnable(boolean enable) {
        sharedPreferences.edit().putBoolean(SETTING_RE_Enable, enable).commit();
    }

    public boolean getReEnable() {
        return sharedPreferences.getBoolean(SETTING_RE_Enable, false);
    }

    /**
     * 描述:设置声音开关
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 下午1:51
     */
    public void setReMusicEnable(boolean enable) {
        sharedPreferences.edit().putBoolean(SETTING_RE_MUSIC_ENABLE, enable).commit();
    }

    public boolean getReMusicEnable() {
        return sharedPreferences.getBoolean(SETTING_RE_MUSIC_ENABLE, false);
    }

    /**
     * 描述:设置回复信息
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 下午1:51
     */
    public void setReReplyMessage(String message) {
        sharedPreferences.edit().putString(SETTING_RE_REPLY_MESSAGE, message).commit();
    }

    public String getReReplyMessage() {
        return sharedPreferences.getString(SETTING_RE_REPLY_MESSAGE, "");
    }

    /**
     * 描述:设置屏蔽消息名字
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 下午1:51
     */
    public void setReMarkName(String message) {
        Set<String> temp = sharedPreferences.getStringSet(SETTING_RE_MASK_NAME, null);
        if (temp == null) {
            temp = new HashSet<>();
        }
        temp.add(message);
        sharedPreferences.edit().putStringSet(SETTING_RE_MASK_NAME, temp).commit();
    }

    public Set<String> getReMarkName() {
        return sharedPreferences.getStringSet(SETTING_RE_MASK_NAME, null);
    }

    /**
     * 描述:删除屏蔽的文字
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 下午2:13
     */
    public void removeReMarkName(String message) {
        Set<String> temp = sharedPreferences.getStringSet(SETTING_RE_MASK_NAME, null);
        if (temp != null && temp.size() > 0) {
            if (temp.contains(message)) {
                temp.remove(message);
            }
            sharedPreferences.edit().putStringSet(SETTING_RE_MASK_NAME, temp).commit();
        }
    }

}