package dump.y;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLManager extends SQLiteOpenHelper implements IDatabase
{

	private static final String DEG_TAG = "webBrowser_SQLManager";

	public SQLManager(Context context, String name, CursorFactory factory,
					  int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		//创建表
		db.execSQL(SQLStr.CREATE_TABLE_FAVORITES);
		db.execSQL(SQLStr.CREATE_TABLE_HISTORY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

	}

	@Override
	public boolean add(SQLiteDatabase sqLiteDatabase, String tableName, String name, String url, long date) throws SQLException
	{
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("url", url);
		if (tableName.equals(FavAndHisManager.TABEL_NAME_Favorite))
		{
			//Log.d(DEG_TAG, "name:" + name + ",url:" + url);
		}
		else
		{
			values.put("date", date);
			//Log.d(DEG_TAG, "name:" + name + ",url:" + url + ",date:" + date);
		}
		long id = sqLiteDatabase.insert(tableName, null, values);
        return id != -1;
	}

	@Override
	public boolean delete(SQLiteDatabase sqLiteDatabase, String tableName, String id)
	{
		//Log.d(DEG_TAG, "deleteId:" + id);
		int number = sqLiteDatabase.delete(tableName, "id=?", new String[]{id});
		//Log.d(DEG_TAG, "delete_result:" + number);
        return number != 0;
	}

	@Override
	public boolean deleteAll(SQLiteDatabase sqLiteDatabase, String tableName)
	{
		//Log.d(DEG_TAG, "deleteAll");
		int number = sqLiteDatabase.delete(tableName, null, null);
		//Log.d(DEG_TAG, "deleteAll_result:" + number);
        return number != 0;
	}

	@Override
	public boolean modify(SQLiteDatabase sqLiteDatabase, String tableName, String id, String name, String url)
	{
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("url", url);
		//Log.d(DEG_TAG, "id:" + id + ",name:" + name + ",url:" + url);
		int number = sqLiteDatabase.update(tableName, values, "id=?", new String[]{id});
		//Log.d(DEG_TAG, "number:" + number);
        return number != 0;
	}

	@Override
	public Cursor getAll(SQLiteDatabase sqLiteDatabase, String tableName)
	{
		String[] returnColmuns = null;
		if (tableName.equals(FavAndHisManager.TABEL_NAME_Favorite))
		{
			returnColmuns = new String[]{
				"id as _id",
				"name",
				"url"
			};
		}
		else
		{
			returnColmuns = new String[]{
				"id as _id",
				"name",
				"url",
				"date"
			};
		}
		Cursor result = sqLiteDatabase.query(tableName, returnColmuns, null, null, null, null, null);
		while (result.moveToNext())
		{
			String id = String.valueOf(result.getInt(result.getColumnIndex("_id")));
			String name = result.getString(result.getColumnIndex("name"));
			String url = result.getString(result.getColumnIndex("url"));
			if (tableName.equals(FavAndHisManager.TABEL_NAME_Favorite))
			{
				//Log.d(DEG_TAG, "id:" + id + ",name:" + name + ",url:" + url);
			}
			else
			{
				String date = String.valueOf(result.getLong(result.getColumnIndex("date")));
				//Log.d(DEG_TAG, "id:" + id + ",name:" + name + ",url:" + url + ",date:" + date);
			}
		}
		return result;
	}

	@Override
	public boolean multiply(SQLiteDatabase sqLiteDatabase, String tableName, String url)
	{
		Cursor result = sqLiteDatabase.query(tableName, null, "url=?", new String[]{url}, null, null, null);
		while (result.moveToNext())
		{
			//Log.d(DEG_TAG, "multiply:[id:" + String.valueOf(result.getInt(result.getColumnIndex("id"))+ ",name:" + result.getString(result.getColumnIndex("name"))+ ",url:" + result.getString(result.getColumnIndex("url"))));
		}
		if (result.getCount() > 0)
		{
			result.close();
			return true;
		}
		else
		{
			result.close();
			return false;
		}
	}

	@Override
	public void transactionAround(boolean readOnly, CallBack callback)
	{
		SQLiteDatabase sqLiteDatabase = null;
		if (readOnly)
		{
			sqLiteDatabase = this.getReadableDatabase();
		}
		else
		{
			sqLiteDatabase = this.getWritableDatabase();
		}
		sqLiteDatabase.beginTransaction();
		callback.doSomething(sqLiteDatabase);
		sqLiteDatabase.setTransactionSuccessful();
		sqLiteDatabase.endTransaction();
	}

}
