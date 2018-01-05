package nico.styTool;

/**
 * Created by lum on 2017/10/20.
 */


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


public class SPContentProvider extends ContentProvider {
    private final static String TAG = "SPContentProvider";
    private SQLiteOpenHelper mSqlHelper;
    private Queue<ContentValues> mCachedDataIde = new LinkedList<ContentValues>();
    private Queue<ContentValues> mCachedDataInUse = new LinkedList<ContentValues>();
    private ExecutorService mThreadHold = Executors.newSingleThreadExecutor();

    @Override
    public boolean onCreate() {
        mSqlHelper = new SPSqliteDatabaseHelper(getContext(), "sp_data.db");
        // Log.d(TAG, "onCreate");
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mSqlHelper.getReadableDatabase().query(Se_s.TABLE, projection, selection, selectionArgs, null, null, null, "1");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        insert(values);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        insert(values);//这里insert 语句实际上是replace. 即不存在时即插入，存在时即更新。
        return 0;
    }

    private AtomicBoolean isIdle = new AtomicBoolean(true);
    private Runnable mInsertThread = new Runnable() {
        String sql = String.format("REPLACE INTO %s(%s,%s,%s)VALUES(?,?,?)", Se_s.TABLE, Se_s.KEY, Se_s.VALUE, Se_s.TYPE);
        ContentValues value = null;

        @Override
        public void run() {
            SQLiteDatabase db = mSqlHelper.getWritableDatabase();
            long startTime = System.currentTimeMillis();
            try {
                //Log.v(TAG, "INSERT START!" + System.currentTimeMillis());
                if (db == null) {
                    //   Log.e(TAG, "getWritableDatabase() return null!");
                    return;
                }
                db.beginTransactionNonExclusive();
                do {
                    if (mCachedDataInUse.isEmpty()) {
                        swipCache();
                        if (mCachedDataInUse.isEmpty()) break;
                    }
                    value = mCachedDataInUse.poll();
                    if (value == null) continue;
                    db.execSQL(sql,
                            new Object[]{value.getAsString(Se_s.KEY), value.getAsString(Se_s.VALUE), value.getAsString(Se_s.TYPE)});
                } while (true);
                db.setTransactionSuccessful();
            } finally {
                if (db != null) {
                    db.endTransaction();
                }
                isIdle.set(true);
                // Log.d(TAG, "INSERT END:" + (System.currentTimeMillis() - startTime));
            }

        }
    };

    private void swipCache() {
        //Log.d(TAG, "swipCache!");
        Queue<ContentValues> tmp = mCachedDataInUse;
        mCachedDataInUse = mCachedDataIde;
        synchronized (mCachedDataIde) {//因为idle对象可能正在进行添加操作，所以先锁住对象。
            mCachedDataIde = tmp;
        }
    }

    //向空闲列表中插入一条记录。
    private void insert(ContentValues v) {
        synchronized (mCachedDataIde) {
            mCachedDataIde.offer(v);
        }
        if (isIdle.compareAndSet(true, false)) {
            // Log.d(TAG, "insert>>>>>>");
            mThreadHold.submit(mInsertThread);
        }
    }

}