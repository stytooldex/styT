package nico.styTool;


/**
 * Created by luxin on 15-12-10.
 * http://luxin.gitcafe.io
 */
public class FloderBean {
    /**
     * 当前文件夹的路径
     */
    private String dir;
    private String firsterImagePath;
    private String name;
    private int count;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf);
    }

    public String getFirsterImagePath() {
        return firsterImagePath;
    }

    public void setFirsterImagePath(String firsterImagePath) {
        this.firsterImagePath = firsterImagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
