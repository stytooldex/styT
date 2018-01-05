package nico.styTool;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class Bookmark {



    private final String TABLE_NAME = "mybookmark";

    private SQLiteDatabase m_db = null;
    private ArrayList<HashMap<String, String>> m_list = null;

    public void initDB(Activity activity) {
        this.openMyBookmark(activity);
        try {
            m_db.execSQL("CREATE TABLE mybookmark(Title TEXT,Url TEXT)");
        } catch (SQLException e) {
            Log.e("BOOKMARK", e.getMessage());
        }
        this.closeMyBookmark();

        m_list = new ArrayList<HashMap<String, String>>();
    }

    public void insert(final String Title, final String Url) {
        String sql = "INSERT INTO " + TABLE_NAME + " values (\"" + Title + "\",\"" + Url + "\")";
        try {
            m_db.execSQL(sql);
        } catch (SQLException e) {
            Log.e("BOOKMARK", e.getMessage());
        }
    }

    public ArrayList<HashMap<String, String>> getList() {
        m_list.clear();
        Cursor cur = m_db.rawQuery("select * from " + TABLE_NAME, null);
        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Title", cur.getString(0));
                    map.put("Url", cur.getString(1));
                    m_list.add(map);

                } while (cur.moveToNext());
            }
        }
        return m_list;
    }

    public void openMyBookmark(Activity activity) {
        try {
            String DBNAME = "MyBookmarkDB";
            m_db = activity.openOrCreateDatabase(DBNAME, 0, null);
        } catch (SQLiteException e) {
            m_db = null;
        }
    }

    public void closeMyBookmark() {
        m_db.close();
    }

}
