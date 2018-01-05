package dump.n;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SearchHistorysDao {
	
	private DBSearchHelper helper;

	private SQLiteDatabase db;
	
	public SearchHistorysDao(Context context) {
		helper = new DBSearchHelper(context);
	}

	
	/**
	 * 对数据库进行增加或是update的操作
	 *
	 */

	public void addOrUpdate(String word){
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select _id from t_historywords where historyword = ? ";
		Cursor cursor = db.rawQuery(sql, new String[]{word});
		if(cursor.getCount()>0){
			//说明数据库中已经有数据：更新数据库的时间：
			String sql_update = "update t_historywords set updatetime = ? where historyword = ? ";
			db.execSQL(sql_update, new String[]{System.currentTimeMillis()+"",word});
		}else{
			//直接插入一条记录：
			String sql_add = "insert into t_historywords(historyword,updatetime) values (?,?);";
			db.execSQL(sql_add, new String[]{word,System.currentTimeMillis()+""});
		}

		cursor.close();
		db.close();
	}

	/**
	 * 查询数据库中所有的数据
	 *
	 */

	public ArrayList<SearchHistorysBean> findAll(){
		ArrayList<SearchHistorysBean> data = new ArrayList<SearchHistorysBean>();
        SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("t_historywords", null, null, null, null, null, "updatetime desc");
		//遍历游标，将数据存储在
		while(cursor.moveToNext()){
			SearchHistorysBean searchDBData = new SearchHistorysBean();
			searchDBData._id =cursor.getInt(cursor.getColumnIndex("_id"));
			searchDBData.historyword = cursor.getString(cursor.getColumnIndex("historyword"));
			searchDBData.updatetime = cursor.getLong(cursor.getColumnIndex("updatetime"));
			data.add(searchDBData);
		}
		cursor.close();
		db.close();
		return data;
	}


	/**
	 *
	 * 删除数据库中的所有数据
	 */

	public void deleteAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL("delete from t_historywords");
		db.close();
	}
}
