package dump.y;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TabHost;

import nico.styTool.R;

public class ToolsPopWindow extends PopupWindow{
	private TabHost toolsTab;
    private LayoutInflater toolsTabInflater;
    private View toolsTabView;
    private Context context;
    
    /**
     * 构造函数
     * @param	context Context
     * @param	width	int
     * @param	height	int
     * */
    public ToolsPopWindow(Context context, int width, int height){
    	super(context);
    	this.context = context;
    	this.toolsTabInflater = LayoutInflater.from(this.context);
    	
    	//创建标签
    	this.initTab();
    	
    	//设置默认选项
    	setWidth(width);
    	setHeight(height);
    	setContentView(toolsTab);
    	setOutsideTouchable(true);
    	setFocusable(true);
    }
    
    
  //实例化标签页
    private void initTab(){
    	this.toolsTabView =  this.toolsTabInflater.inflate(R.layout.tabactivity_tools,null);
    	this.toolsTab = this.toolsTabView.findViewById(android.R.id.tabhost);       //获取tabhost
    	this.toolsTab.setup();           //使用findViewById()加载tabhost时在调用addTab前必须调用

    	this.toolsTab.addTab(this.toolsTab.newTabSpec("normal").setIndicator("常用").setContent(R.id.tools_normal));
    	this.toolsTab.addTab(this.toolsTab.newTabSpec("setttings").setIndicator("设置").setContent(R.id.tools_settings));
    	this.toolsTab.addTab(this.toolsTab.newTabSpec("tool").setIndicator("工具").setContent(R.id.tools_tool));
    	this.toolsTab.setCurrentTab(0);                                            //设置默认选种标签
    }
    
    public View getView(int id){
    	return this.toolsTabView.findViewById(id);
    }
}
