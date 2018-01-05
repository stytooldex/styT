package nico.styTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by luxin on 15-12-25.
 */
public class RobotHttpUtils {

    private static final String URL="http://www.tuling123.com/openapi/api";




    public static RobotChat sendMessage(String msg){
        RobotChat robotChat=new RobotChat();
        String result=doGet(msg);
        RobotResult robotResult=new RobotResult();

        try {
            JSONObject jsonObject=new JSONObject(result);
            int code=jsonObject.getInt("code");
            String content=jsonObject.getString("text");
            //robotChat.
            robotResult.setCode(code);
            robotResult.setContent(content);

            robotChat.setMsg(content);

        } catch (JSONException e) {
            robotChat.setMsg("妮哩大姨妈来了");
            e.printStackTrace();
        }
        robotChat.setDate(new Date(System.currentTimeMillis()));
        robotChat.setType(RobotChat.ChatType.INCOMING);

        return robotChat;
    }

    private static String doGet(String msg){
        String result="";
        String url=setParams(msg);

        InputStream is=null;
        ByteArrayOutputStream baos=null;
        try {
            URL url1=new URL(url);
            HttpURLConnection conn= (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            is=conn.getInputStream();

            int len=-1;
            byte []buf=new byte[128];
            baos=new ByteArrayOutputStream();

            while ((len=is.read(buf))!=-1){
                baos.write(buf,0,len);
            }
            baos.flush();
            result=new String(baos.toByteArray());
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static String setParams(String msg) {
        String url= null;
        try {
            url = URL+"?key="+ Constant.TULING_KEY+"&info="+ URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
