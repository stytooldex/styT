package dump.w;

import cn.bmob.v3.BmobObject;
import nico.styTool.MyUser;

/**
 * @ClassName: 帖子
 */
public class Post_ extends BmobObject {


    /**
     * 帖子内容
     */
    private String content;

    /**
     * 微博发布者
     */

    private Integer msignature;// 个性签名

    private String signature;// 个性签名

    public Integer getmTitle() {
        return msignature;
    }

    public void setmTitle(Integer title) {
        this.msignature = title;
    }


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    private MyUser author;

    private Integer commentNum;// 评论数
    private Integer likeNum;// 点赞数

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

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

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

//	/**
//	 * 微博的评论，一条微博是对应多条评论的，像这种一对多的情形，请使用BmobRelation类型
//	 */
//	private BmobRelation comment;

//	public BmobRelation getComment() {
//		return comment;
//	}
//	public void setComment(BmobRelation comment) {
//		this.comment = comment;
//	}
}
