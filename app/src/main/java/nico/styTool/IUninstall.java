package nico.styTool;
/**
 * 用来实现删除程序的接口
 */
public interface IUninstall {

    /**
     * 删除应用
     * @param pos       行号
     * @param packageName 包名
     */
    void onBtnClick(int pos, String packageName);
}
