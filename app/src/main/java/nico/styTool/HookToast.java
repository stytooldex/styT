package nico.styTool;

import android.text.TextUtils;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by lum on 2017/10/31.
 */
//@Keep
public class HookToast implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // 只对微博国际版进行操作
        if (lpparam.packageName.equals("com.weico.international")) {
            try {
                Class<?> aClass = XposedHelpers.findClassIfExists("com.weico.international.activity.v4.Setting", lpparam.classLoader);
                if (aClass == null) {
                    return;
                }
               /*
                XposedHelpers.findAndHookMethod(clazz, "toastMessage", new XC_MethodHook() {

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult("bili");
                    }
                }); */
                // Hook loadInt方法
                XposedHelpers.findAndHookMethod(aClass, "loadInt", String.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String param1 = (String) param.args[0];
                        // 如果参数为display_ad的时候将返回值改为-1
                        if (!TextUtils.isEmpty(param1) && param1.equals("display_ad")) {
                            //  Log.e("info", "com.weico.international---loadInt---display_ad");
                            param.setResult(-1);
                        }
                    }
                });
                // Hook loadLong方法
                XposedHelpers.findAndHookMethod(aClass, "loadLong", String.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String param1 = (String) param.args[0];
                        // 如果参数为ad_display_time的时候将返回值改为当前时间
                        if (!TextUtils.isEmpty(param1) && param1.equals("ad_display_time")) {
                            //Log.e("info", "com.weico.international---loadLong---ad_display_time");
                            param.setResult(System.currentTimeMillis());
                        }
                    }
                });
            } catch (Throwable t) {
                XposedBridge.log("stytool" + t);
            }
        }
    }
}