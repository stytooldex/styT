package nico.styTool;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by lum on 2017/11/8.
 */

public class BILIBILI extends BmobObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String content;
    private String pushdate;
    private int state;
    private MyUser user;
    private MyUser hepluser;
    private HelpInfo helpInfo;
    private PhontoFiles phontofile;
    private Integer likeNum;// 点赞数

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPushdate() {
        return pushdate;
    }

    public void setPushdate(String pushdate) {
        this.pushdate = pushdate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public MyUser getHepluser() {
        return hepluser;
    }

    public void setHepluser(MyUser hepluser) {
        this.hepluser = hepluser;
    }

    public HelpInfo getHelpInfo() {
        return helpInfo;
    }

    public void setHelpInfo(HelpInfo helpInfo) {
        this.helpInfo = helpInfo;
    }

    public PhontoFiles getPhontofile() {
        return phontofile;
    }

    public void setPhontofile(PhontoFiles phontofile) {
        this.phontofile = phontofile;
    }


}
