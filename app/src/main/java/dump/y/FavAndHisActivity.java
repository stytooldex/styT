package dump.y;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import dump.z.BaseActivity_;
import nico.styTool.R;

public class FavAndHisActivity extends BaseActivity_ {

    private static final String DEG_TAG = "webbrowser_FavAndHisActivity";

    //收藏历史的内容
    private ListView favoritesContent;
    private ListView historyContent;

    //popupwindow弹窗
    private ItemLongClickedPopWindow itemLongClickedPopWindow;

    //书签历史管理
    private FavAndHisManager favAndHisManager;

    //Cursor
    private Cursor favAndHisCursor;

    //Adapter
    private ListAdapter favAndHisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_favoritesandhistory);

        //初始化
        TextView favorites = (TextView) this.findViewById(R.id.favorites);
        TextView history = (TextView) this.findViewById(R.id.history);

        this.favoritesContent = (ListView) this.findViewById(R.id.favoritesAndHisotry_content_favorite);
        this.historyContent = (ListView) this.findViewById(R.id.favoritesAndHisotry_content_history);

        ButtonClickedListener buttonClickedListener = new ButtonClickedListener();
        ListViewOnItemLongListener itemLongListener = new ListViewOnItemLongListener();
        ListViewOnItemClickedListener itemClickedListener = new ListViewOnItemClickedListener();

        //添加监听
        favorites.setOnClickListener(buttonClickedListener);
        history.setOnClickListener(buttonClickedListener);

        this.favoritesContent.setOnItemLongClickListener(itemLongListener);
        this.historyContent.setOnItemLongClickListener(itemLongListener);

        this.favoritesContent.setOnItemClickListener(itemClickedListener);
        this.historyContent.setOnItemClickListener(itemClickedListener);

        //初始化数据
        this.initDataFavorites();
        this.initDataHistory();

        //添加默认返回值
        setResult(-1);

    }

    /**
     * 初始化ListView中的数据
     */
    @SuppressWarnings("deprecation")
    private void initDataFavorites() {
        //获取书签管理
        this.favAndHisManager = new FavAndHisManager(this);
        this.favAndHisCursor = this.favAndHisManager.getAllFavorites();
        this.favAndHisAdapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.list_item_, this.favAndHisCursor,
                new String[]{"_id", "name", "url"},
                new int[]{R.id.item_id, R.id.item_name, R.id.item_url});
        this.favoritesContent.setAdapter(this.favAndHisAdapter);
    }

    /**
     * 初始化ListView中History的数据
     */
    @SuppressWarnings("deprecation")
    private void initDataHistory() {
        //获取书签管理
        this.favAndHisManager = new FavAndHisManager(this);
        this.favAndHisCursor = this.favAndHisManager.getAllHistories();
        this.favAndHisAdapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.list_item_, this.favAndHisCursor,
                new String[]{"_id", "name", "url", "date"},
                new int[]{R.id.item_id, R.id.item_name, R.id.item_url, R.id.item_date});
        this.historyContent.setAdapter(this.favAndHisAdapter);
    }

    /**
     * 长按单项事件
     * 覆盖如下方法
     * 1.	onItemLongClick
     */
    private class ListViewOnItemLongListener implements OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            //Log.d(DEG_TAG, "long item cliced");
            if (parent.getId() == R.id.favoritesAndHisotry_content_favorite) {
                itemLongClickedPopWindow = new ItemLongClickedPopWindow(FavAndHisActivity.this,
                        ItemLongClickedPopWindow.FAVORITES_ITEM_POPUPWINDOW, MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
                itemLongClickedPopWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.favandhis_activity));
                itemLongClickedPopWindow.showAsDropDown(view, view.getWidth() / 2, -view.getHeight() / 2);
                TextView modifyFavorite = (TextView) itemLongClickedPopWindow.getView(R.id.item_longclicked_modifyFavorites);
                TextView deleteFavorite = (TextView) itemLongClickedPopWindow.getView(R.id.item_longclicked_deleteFavorites);
                ItemClickedListener itemClickedListener = new ItemClickedListener(view);
                modifyFavorite.setOnClickListener(itemClickedListener);
                deleteFavorite.setOnClickListener(itemClickedListener);
            } else if (parent.getId() == R.id.favoritesAndHisotry_content_history) {
                itemLongClickedPopWindow = new ItemLongClickedPopWindow(FavAndHisActivity.this,
                        ItemLongClickedPopWindow.HISTORY_ITEM_POPUPWINDOW, MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
                itemLongClickedPopWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.favandhis_activity));
                itemLongClickedPopWindow.showAsDropDown(view, view.getWidth() / 2, -view.getHeight() / 2);
                TextView deleteHistory = (TextView) itemLongClickedPopWindow.getView(R.id.item_longclicked_deleteHistory);
                TextView deleteAllHistories = (TextView) itemLongClickedPopWindow.getView(R.id.item_longclicked_deleteAllHistories);
                ItemClickedListener itemClickedListener = new ItemClickedListener(view);
                deleteHistory.setOnClickListener(itemClickedListener);
                deleteAllHistories.setOnClickListener(itemClickedListener);
            }
            return false;
        }

    }

    /**
     * ListView单击单项事件
     * 覆盖如下方法
     * 1.	onClick
     */
    private class ListViewOnItemClickedListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            if (arg0.getId() == R.id.favoritesAndHisotry_content_favorite) {
                Intent intent = new Intent();
                intent.putExtra("url", ((TextView) arg1.findViewById(R.id.item_url)).getText().toString());
                setResult(0, intent);
                finish();
            } else if (arg0.getId() == R.id.favoritesAndHisotry_content_history) {
                Intent intent = new Intent();
                intent.putExtra("url", ((TextView) arg1.findViewById(R.id.item_url)).getText().toString());
                setResult(0, intent);
                finish();
            }
        }

    }


    /**
     * OnClickListener自定义继承类
     * 覆盖如下方法
     * 1.	onClick
     */
    private class ButtonClickedListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.favorites) {
                if (!favoritesContent.isShown()) {
                    favoritesContent.setVisibility(View.VISIBLE);
                    historyContent.setVisibility(View.GONE);
                }
            } else if (v.getId() == R.id.history) {
                if (!historyContent.isShown()) {
                    favoritesContent.setVisibility(View.GONE);
                    historyContent.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    /**
     * popupwindow按钮事件处理类
     *
     * @param view 传入的ListView条目 用来获取其中的id、name、url这三个值
     *             覆盖如下方法：
     *             1.	onClick
     */
    private class ItemClickedListener implements OnClickListener {

        private String item_id;
        private String item_name;
        private String item_url;

        public ItemClickedListener(View item) {
            this.item_id = ((TextView) item.findViewById(R.id.item_id)).getText().toString();
            this.item_name = ((TextView) item.findViewById(R.id.item_name)).getText().toString();
            this.item_url = ((TextView) item.findViewById(R.id.item_url)).getText().toString();
        }

        @Override
        public void onClick(View view) {
            //取消弹窗
            itemLongClickedPopWindow.dismiss();
            if (view.getId() == R.id.item_longclicked_modifyFavorites) {
                //弹出修改窗口
                LayoutInflater modifyFavoritesInflater = LayoutInflater.from(FavAndHisActivity.this);
                View modifyFavoritesView = modifyFavoritesInflater.inflate(R.layout.dialog_modify, null);
                final TextView item_name_input = modifyFavoritesView.findViewById(R.id.dialog_name_input);
                final TextView item_url_input = modifyFavoritesView.findViewById(R.id.dialog_url_input);
                item_name_input.setText(item_name);
                item_url_input.setText(item_url);
                new AlertDialog.Builder(FavAndHisActivity.this)
                        .setTitle("编辑书签")
                        .setView(modifyFavoritesView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //	Log.d(DEG_TAG, "id:" + item_id + ",name:" + item_name + ",url:" + item_url);
                                if (favAndHisManager.modifyFavorite(item_id, item_name_input.getText().toString(),
                                        item_url_input.getText().toString())) {
                                    Toast.makeText(FavAndHisActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    initDataFavorites();
                                    favoritesContent.invalidate();
                                } else {
                                    Toast.makeText(FavAndHisActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }).setNegativeButton("取消", null)
                        .create()
                        .show();
            } else if (view.getId() == R.id.item_longclicked_deleteFavorites) {
                new AlertDialog.Builder(FavAndHisActivity.this)
                        .setTitle("删除书签")
                        .setMessage("是否要删除\"" + item_name + "\"这个书签？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (favAndHisManager.deleteFavorite(item_id)) {
                                    //删除成功
                                    Toast.makeText(FavAndHisActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    initDataFavorites();
                                    favoritesContent.invalidate();
                                } else {
                                    Toast.makeText(FavAndHisActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            } else if (view.getId() == R.id.item_longclicked_deleteHistory) {
                new AlertDialog.Builder(FavAndHisActivity.this)
                        .setTitle("删除历史")
                        .setMessage("是否要删除\"" + item_name + "\"这个历史？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (favAndHisManager.deleteHistory(item_id)) {
                                    //删除成功
                                    Toast.makeText(FavAndHisActivity.this, "删除成功",Toast.LENGTH_SHORT).show();
                                    initDataHistory();
                                    historyContent.invalidate();
                                } else {
                                    Toast.makeText(FavAndHisActivity.this, "删除失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            } else if (view.getId() == R.id.item_longclicked_deleteAllHistories) {
                new AlertDialog.Builder(FavAndHisActivity.this)
                        .setTitle("清空历史")
                        .setMessage("是否要清空历史？")
                        .setPositiveButton("清空", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (favAndHisManager.deleteAllHistory()) {
                                    //删除成功
                                    Toast.makeText(FavAndHisActivity.this, "成功清空",Toast.LENGTH_SHORT).show();
                                    initDataHistory();
                                    historyContent.invalidate();
                                } else {
                                    Toast.makeText(FavAndHisActivity.this, "清空失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            }

        }

    }

    @Override
    public void finish() {
        if (this.favAndHisCursor != null) {
            this.favAndHisCursor.close();
        }
        super.finish();
    }


    @Override
    protected void onDestroy() {
        if (this.favAndHisCursor != null) {
            this.favAndHisCursor.close();
        }
        super.onDestroy();
    }


}
