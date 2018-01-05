package dump.y;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import nico.styTool.R;

public class ItemLongClickedPopWindow extends PopupWindow{
	
	/**
	 * 书签条目弹出菜单
	 * @value
	 * {@value}
	 * */
	public static final int FAVORITES_ITEM_POPUPWINDOW = 0;
	/**
	 * 书签页面弹出菜单
	 * @value
	 * {@value}
	 * */
	public static final int FAVORITES_VIEW_POPUPWINDOW = 1;
	/**
	 * 历史条目弹出菜单
	 * @value
	 * {@value}
	 * */
	public static final int HISTORY_ITEM_POPUPWINDOW = 3;
	/**
	 * 历史页面弹出菜单
	 * @value
	 * {@value}
	 * */
	public static final int HISTORY_VIEW_POPUPWINDOW = 4;
	
    private LayoutInflater favAndHisInflater;
    private View favAndHisView;
    private Context context;
    
    private int type;
    
    /**
     * 构造函数
     * @param	context 上下文
     * @param	width	宽度
     * @param	height	高度
     * */
    public ItemLongClickedPopWindow(Context context, int type, int width, int height){
    	super(context);
    	this.context = context;
    	this.type = type;
    	
    	//创建
    	this.initTab();
    	
    	//设置默认选项
    	setWidth(width);
    	setHeight(height);
    	setContentView(this.favAndHisView);
    	setOutsideTouchable(true);
    	setFocusable(true);
    }
    
    
    //实例化
    private void initTab(){
    	this.favAndHisInflater = LayoutInflater.from(this.context);
    	switch(type){
    	case FAVORITES_ITEM_POPUPWINDOW:
    		this.favAndHisView = this.favAndHisInflater.inflate(R.layout.list_item_longclicked_favorites, null);
    		break;
    	case FAVORITES_VIEW_POPUPWINDOW:
    		//对于书签内容弹出菜单，未作处理
    		//this.favAndHisView = this.favAndHisInflater.inflate(R.layout.list_item_longclicked, null);
    		break;
    	case HISTORY_ITEM_POPUPWINDOW:
    		this.favAndHisView = this.favAndHisInflater.inflate(R.layout.list_item_longclicked_history, null);
    		break;
    	case HISTORY_VIEW_POPUPWINDOW:
    		//对于历史内容弹出菜单，未作处理
    		//this.favAndHisView = this.favAndHisInflater.inflate(R.layout.list_item_longclicked_favorites, null);
    		break;
    	}
    	
    }
    
    public View getView(int id){
    	return this.favAndHisView.findViewById(id);
    }
}
