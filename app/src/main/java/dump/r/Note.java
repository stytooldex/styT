package dump.r;

import cn.bmob.v3.BmobObject;

/**
 * 便签实体类
 * 继承BmobObject
 * 并且自定义content，userObjectId外键属性
 * Created by taoyige on 2016/6/5.
 */
public class Note extends BmobObject{
    private String content;
    private String userObjectId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }
}
