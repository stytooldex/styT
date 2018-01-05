package nico.styTool;

/**
 * Created by lum on 2017/10/20.
 * <p>
 * 通过Setting 接口实现 SharePreferrence的功能。
 * 同时实现了多线程，多进程操作。
 * 特点：具有类似SP操作的简单功能，又保证多进程环境操作的稳定性。
 * 效率上底层使用了contentprovider + sqlite。
 * 中间层使用了异步写入+ 数据双缓冲存储，保证了数据的高效快速操作。
 * 同时在批量数据操作的时候也能够保证非常高效的操作。实测1W条数据写操作只需500MS.
 */

/**
 * 通过Setting 接口实现 SharePreferrence的功能。
 * 同时实现了多线程，多进程操作。
 * 特点：具有类似SP操作的简单功能，又保证多进程环境操作的稳定性。
 * 效率上底层使用了contentprovider + sqlite。
 * 中间层使用了异步写入+ 数据双缓冲存储，保证了数据的高效快速操作。
 * 同时在批量数据操作的时候也能够保证非常高效的操作。实测1W条数据写操作只需500MS.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Se_s {

    public static Se_s instance() {
        return sInstance;
    }

    public static void init(Context app) {
        sInstance.mAppContext = app.getApplicationContext();
        //Log.d("Setting", "AppContext:" + sInstance.mAppContext);
        sInstance.mSecureFactory = sInstance.mReleaseSecureFactory;
    }

    //用于提供加解密key,value的方法。
    public static void init(Context app, SecureFactory secureFactory) {
        init(app);
        if (secureFactory != null) {
            sInstance.mSecureFactory = secureFactory;
        }
    }

    public void setDebugable(boolean debugable) {
        if (debugable) {
            mSecureFactory = mDebugSecureFactory;
        } else {
            mSecureFactory = mReleaseSecureFactory;
        }
    }

    public void setSecureFactory(SecureFactory factory) {
        if (factory != null) {
            mSecureFactory = factory;
        }
    }

    public void setBoolean(String key, Boolean v) {
        set(key, v);
    }

    public void setInt(String key, Integer v) {
        set(key, v);
    }

    public void setLong(String key, Long v) {
        set(key, v);
    }

    public void setFloat(String key, Float v) {
        set(key, v);
    }

    public void setDouble(String key, Double v) {
        set(key, v);
    }

    public void setString(String key, String v) {
        set(key, v);
    }

    public boolean getBoolean(String key, boolean def) {
        Object o = get(key);
        if (o != null) {
            return ((Boolean) o).booleanValue();
        }
        return def;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public int getInt(String key, int def) {
        Object o = get(key);
        if (o != null) {
            return ((Number) o).intValue();
        }
        return def;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public long getLong(String key, long def) {
        Object o = get(key);
        if (o != null) {
            return ((Number) o).longValue();
        }
        return def;
    }

    public long getLone(String key) {
        return getLong(key, 0L);
    }

    public float getFloat(String key, float def) {
        Object o = get(key);
        if (o != null) {
            return ((Number) o).floatValue();
        }
        return def;
    }

    public float getFloat(String key) {
        return getFloat(key, 0F);
    }

    public double getDouble(String key, double def) {
        Object o = get(key);
        if (o != null) {
            return ((Number) o).doubleValue();
        }
        return def;
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public String getString(String key) {
        Object o = get(key);
        if (o != null) {
            return ((String) o);
        }
        return null;
    }

    /**
     * Get the right value of correcte class.
     * class can be String ,char,int,long,byte,float, double,short,boolean
     *
     * @param key
     * @return
     */
    private Object get(String key) {
        ensureContext();
        String keyE = mSecureFactory.encode(key);
        Cursor cursor = mAppContext.getContentResolver().query(ITEM_URI, null, KEY + "=?", new String[]{keyE}, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                if (cursor.getCount() <= 0) return null;
                String v = cursor.getString(cursor.getColumnIndex(VALUE));
                String t = cursor.getString(cursor.getColumnIndex(TYPE));
                if (v == null || t == null || "".equals(v) || "".equals(t)) return null;
                try {
                    t = mSecureFactory.decode(t);
                    v = mSecureFactory.decode(v);

                    Class c = Class.forName(t);
                    if (String.class == Class.forName("java.lang." + t)) {
                        return v;
                    } else if (Number.class.isAssignableFrom(c)) {
                        Method m = mMethodMap.get(c);
                        if (m == null) {
                            m = c.getMethod("valueOf", String.class);
                            mMethodMap.put(c, m);
                        }
                        return m.invoke(null, v);
                    } else if (Boolean.class == c) {
                        return Boolean.valueOf(v);
                    } else if (Character.class == c) {
                        return Character.valueOf(v.charAt(0));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    private void set(String key, Object value) {
        ensureContext();

        if (key == null) return;
        String keyE = mSecureFactory.encode(key);
        if (keyE == null) return;
        String v = value == null ? null : mSecureFactory.encode(value.toString());
        String type = value == null ? null : mSecureFactory.encode(value.getClass().getSimpleName());

        final ContentValues mCacheValues = new ContentValues(3);
        mCacheValues.put(KEY, keyE);
        mCacheValues.put(VALUE, v);
        mCacheValues.put(TYPE, type);
        mAppContext.getContentResolver().insert(ITEM_URI, mCacheValues);

        notifyChanged(key, value);

        //Log.d("Settings", "set:key=" + key + ",value=" + value);
    }

    private void ensureContext() {
        if (mAppContext == null) {
            throw new RuntimeException("Settings must be inited first.");
        }
    }

    public void notifyChanged(String key, Object value) {

        for (int i = mOnChangeListeners.size() - 1; i >= 0; i--) {
            OnKeyValueChangeListener listener = mOnChangeListeners.get(i).get();
            if (listener == null) {
                mOnChangeListeners.remove(i);
                continue;
            }
            listener.onChange(key, value);
        }
    }

    private final Map<Class, Method> mMethodMap = new HashMap<Class, Method>();
    public final static String ID = "_id";
    public final static String KEY = "_key";
    public final static String VALUE = "_value";
    public final static String TYPE = "_type";
    public final static String TABLE = "Setting";
    public final static String AUTHORITY = "com.UCMobile";
    private final static Se_s sInstance = new Se_s();
    public final static Uri ITEM_URI = Uri.parse("content://" + AUTHORITY + "/item");
    private Context mAppContext;
    private SecureFactory mSecureFactory = null;

    private List<SoftReference<OnKeyValueChangeListener>> mOnChangeListeners = new ArrayList<>();

    public void addOnChangeListener(OnKeyValueChangeListener l) {
        if (l == null) return;
        synchronized (mOnChangeListeners) {
            this.mOnChangeListeners.add(new SoftReference<OnKeyValueChangeListener>(l));
        }
    }

    public interface OnKeyValueChangeListener {
        void onChange(String key, Object value);
    }

    public interface SecureFactory {
        //用于存储时加密字符串
        String encode(String k);

        //用于读取时解密字符串
        String decode(String k);
    }

    private final SecureFactory mReleaseSecureFactory = new AESSecureFactory();
    private final SecureFactory mDebugSecureFactory = new SecureFactory() {
        @Override
        public String encode(String k) {
            return k;
        }

        @Override
        public String decode(String k) {
            return k;
        }
    };
}