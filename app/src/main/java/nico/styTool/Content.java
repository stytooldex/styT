package nico.styTool;

import cn.bmob.v3.BmobObject;

/**
 * Created by apple on 15/9/22.
 */
public class Content extends BmobObject {
    private String nickname;
    private String room_id;
    private String content;
    private String time;

    public String getNickname() {
        return nickname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
