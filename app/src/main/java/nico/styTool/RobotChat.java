package nico.styTool;

import java.util.Date;

/**
 * Created by luxin on 15-12-25.
 */
public class RobotChat {
    private String name;
    private String msg;
    private Date date;
    private ChatType type;

    public enum ChatType{
        INCOMING,OUTCOMING
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }
}
