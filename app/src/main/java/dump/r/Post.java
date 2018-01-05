package dump.r;

import cn.bmob.v3.BmobObject;
import nico.styTool.MyUser;
/**
 * 
 * @ClassName: 帖子
 * @Description: 帖子实体
 * @author smile
 * @date 2014年4月17日 上午11:10:44
 *
 */
public class Post extends BmobObject
{

    private String content;


    private MyUser author;
    
    public String getContent()
    {
	return content;
    }
    public void setContent(String content)
    {
	this.content = content;
    }
    public MyUser getAuthor()
    {
	return author;
    }
    public void setAuthor(MyUser author)
    {
	this.author = author;
    }

}
