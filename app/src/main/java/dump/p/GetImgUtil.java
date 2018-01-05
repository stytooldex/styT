package dump.p;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetImgUtil {
	// 获取网络图片的数据
	public static Bitmap getImage(String picturepath)  {
		 URL myFileURL;  
	        Bitmap bitmap=null;  
	        try{  
	            myFileURL = new URL(picturepath);  
	            //获得连接  
	            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();  
	            //设置超时时间为5秒，conn.setConnectionTiem(0);表示没有时间限制  
	            conn.setConnectTimeout(5*1000);  
	            //连接设置获得数据流  
	            conn.setDoInput(true);  
	            //不使用缓存  
	            conn.setUseCaches(true);  
	            //这句可有可无，没有影响  
	            //conn.connect();  
	            //得到数据流  
	            InputStream is = conn.getInputStream();  
	            //解析得到图片  
	            bitmap = BitmapFactory.decodeStream(is);  
	            //关闭数据流  
	            is.close();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  
	          
	        return bitmap; 
	}
}
