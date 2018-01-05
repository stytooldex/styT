package nico.styTool;


import cn.bmob.v3.BmobObject;

/**
 * Created by luxin on 15-12-23.
 * http://luxin.gitcafe.io
 */
public class NotifyMsg extends BmobObject {

    private BILIBILI helps;
    private String message;
    private Comment comment;
    private MyUser user;
    private boolean status;
    private MyUser author;

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public BILIBILI getHelps() {
        return helps;
    }

    public void setHelps(BILIBILI helps) {
        this.helps = helps;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
