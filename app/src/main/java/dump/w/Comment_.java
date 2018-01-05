package dump.w;

import cn.bmob.v3.BmobObject;
import nico.styTool.MyUser;

/**
 * @author smile
 * @ClassName: Comment
 * @Description: 评论实体
 * @date 2014年4月17日 上午11:29:41
 */
public class Comment_ extends BmobObject {
    private String signature;// 个性签名

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论的用户
     */
    private MyUser user;

    /**
     * 所评论的帖子
     */
    private Post_ post; //一个评论只能属于一个微博

    public Post_ getPost() {
        return post;
    }

    public void setPost(Post_ post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

}
