package dump.v;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;

import java.lang.reflect.Method;

public class testThread {
    public static boolean checkAlertWindowsPermission(Context context) {
        try {
            Object object = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                object = context.getSystemService(Context.APP_OPS_SERVICE);
            }
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = 24;
            arrayOfObject1[1] = Binder.getCallingUid();
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return m == AppOpsManager.MODE_ALLOWED;
            }
        } catch (Exception ignored) {

        }
        return false;
    }

}
