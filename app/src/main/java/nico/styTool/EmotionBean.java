package nico.styTool;
/**
 * Created by luxin on 15-12-12.
 *  http://luxin.gitcafe.io
 */
public class EmotionBean {

    private int id;//图片id
    private String name;//表情名字

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmotionBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
