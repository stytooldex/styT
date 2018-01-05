package dump.y;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class FavAndHisManager
{


	public static final String TABEL_NAME_Favorite = "favorite";
	public static final String TABEL_NAME_History = "history";

	private IDatabase database;
	private boolean flag = false;
	private Cursor resultMap;
	private Context DEG_TAG;
	public FavAndHisManager(Context context)
	{
        DEG_TAG = context;
		//Context DEG_TAG;
		this.database = new SQLManager(context, "com_webbrowser_data", null, 1);
	}

	/**
	 * 增加书签
	 * @param	name	书签名
	 * @param	url		书签地址
	 * */
	public boolean addFavorite(final String name, final String url)
	{
		flag = false;
		this.database.transactionAround(false, new CallBack() {

				@Override
				public void doSomething(SQLiteDatabase sqLiteDatabase)
				{
					boolean ifmultiply = database.multiply(sqLiteDatabase, TABEL_NAME_Favorite, url);
					if (!ifmultiply)
					{
						//Log.d(DEG_TAG, "reason:未存在相同书签");

						flag = database.add(sqLiteDatabase, TABEL_NAME_Favorite, name, url, -1);
					}
					else
					{
						Toast.makeText(DEG_TAG, "已经存在相同书签", Toast.LENGTH_SHORT).show();
						//Log.d(DEG_TAG, "reason:已经存在相同书签");
						flag = false;
					}
				}

			});
		//Log.d(DEG_TAG, "result:" + flag);
		return flag;
	}

	/**
	 * 删除书签
	 * @param	id		书签ID
	 * */
	public boolean deleteFavorite(final String id)
	{
		flag = false;
		this.database.transactionAround(false, new CallBack() {

				@Override
				public void doSomething(SQLiteDatabase sqLiteDatabase)
				{
					flag = database.delete(sqLiteDatabase, TABEL_NAME_Favorite, id);
				}
			});
		return flag;
	}

	/**
	 * 修改书签
	 * @param	id		修改的书签ID
	 * @param	name	修改后的书签名
	 * @param	url		修改后的书签地址
	 * */
	public boolean modifyFavorite(final String id, final String name, final String url)
	{
		flag = false;
		this.database.transactionAround(false, new CallBack() {

				@Override
				public void doSomething(SQLiteDatabase sqLiteDatabase)
				{
					flag = database.modify(sqLiteDatabase, TABEL_NAME_Favorite, id, name, url);
				}
			});
		return flag;
	}

	/**
	 * 获取所有书签
	 * @return	HashMap<String, String>
	 * */
	public Cursor getAllFavorites()
	{
		this.database.transactionAround(true, new CallBack() {

				@Override
				public void doSomething(SQLiteDatabase sqLiteDatabase)
				{
					resultMap = database.getAll(sqLiteDatabase, TABEL_NAME_Favorite);
				}
			});
		return resultMap;
	}

	/**
	 * 增加历史
	 * @param	name	历史名
	 * @param	url		历史地址
	 * @param	date	存放历史时间
	 * */
	public boolean addHistory(final String name, final String url, final long date)
	{
		flag = false;
		this.database.transactionAround(false, new CallBack() {

				@Override
				public void doSomething(SQLiteDatabase sqLiteDatabase)
				{
					//历史可重复，不重复的为id和date
					flag = database.add(sqLiteDatabase, TABEL_NAME_History, name, url, date);
				}
			});
		//	Log.d(DEG_TAG, "result:" + flag);
		return flag;
	}

	/**
	 * 删除历史
	 * @param	id		历史ID
	 * */
	public boolean deleteHistory(final String id)
	{
		flag = false;
		this.database.transactionAround(false, new CallBack() {

				@Override
				public void doSomething(SQLiteDatabase sqLiteDatabase)
				{
					flag = database.delete(sqLiteDatabase, TABEL_NAME_History, id);
				}
			});
		return flag;
	}

	/**
	 * 删除所有历史
	 * */
	public boolean deleteAllHistory()
	{
		flag = false;
		this.database.transactionAround(false, new CallBack() {

				@Override
				public void doSomething(SQLiteDatabase sqLiteDatabase)
				{
					flag = database.deleteAll(sqLiteDatabase, TABEL_NAME_History);
				}
			});
		return flag;
	}

	/**
	 * 获取所有历史
	 * @return	Cursor
	 * */
	public Cursor getAllHistories()
	{
		this.database.transactionAround(true, new CallBack() {

				@Override
				public void doSomething(SQLiteDatabase sqLiteDatabase)
				{
					resultMap = database.getAll(sqLiteDatabase, TABEL_NAME_History);
				}
			});
		return resultMap;
	}
}
