package nico.styTool;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tpnet on 2016/5/19.
 */
class WifiAdapter extends BaseAdapter {
    private List<WifiInfo> wifiInfos = new ArrayList<>();
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局


    WifiAdapter(Context context){
        mInflater =LayoutInflater.from(context);
    }

    private static String hexString="0123456789abcdef"; 
    /* 
     * 将字符串编码成16进制数字,适用于所有字符（包括中文） 
     */ 
    static String jiami(String str)
    { 
//根据默认编码获取字节数组 
	byte[] bytes=str.getBytes(); 
	StringBuilder sb=new StringBuilder(bytes.length * 2); 
//将字节数组中每个字节拆解成2位16进制整数 
        for (byte aByte : bytes) {
            sb.append(hexString.charAt((aByte & 0xf0) >> 4));
            sb.append(hexString.charAt((aByte & 0x0f)));
        }

	//加密
	String st1="";
	String rreg[] ={"a","b","c","d","e","f","0","1","2","3","4","5","6","7","8","9"};
	String rre[] = {".",":","+","}","?","tencent","……","%","_","～","SslErrorHandler",",,Ծ^Ծ,,","•",">","、","sdk"};

	for (int a=0;a < 16;a++)
	{

	    if (a == 0)
	    {
		st1 = sb.toString().replace(rreg[a], rre[a]);
	    }
	    st1 = st1.replace(rreg[a], rre[a]);



	}

	return st1; 
    } 
    /* 
     * 将16进制数字解码成字符串,适用于所有字符（包括中文） 
     */ 
    static String jiemi(String st1)
    { 
	//解码
	String bytes="";
	String rreg[] ={"a","b","c","d","e","f","0","1","2","3","4","5","6","7","8","9"};
	String rre[] = {".",":","+","}","?","tencent","……","%","_","～","SslErrorHandler",",,Ծ^Ծ,,","•",">","、","sdk"};

	for (int a=0;a < 16;a++)
	{

	    if (a == 0)
	    {
		bytes = st1.replace(rre[a], rreg[a]);
	    }
	    bytes = bytes.replace(rre[a], rreg[a]);

	}
	ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length() / 2); 
//将每2位16进制整数组装成一个字节 
	for (int i=0;i < bytes.length();i += 2) 
	    baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1)))); 
	return new String(baos.toByteArray()); 
    } 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return wifiInfos.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return wifiInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void clear(){
        wifiInfos.clear();
    }
    void AddAll(List<WifiInfo> infos){
        wifiInfos.addAll(infos);
        //System.out.println("适配器数量"+wifiInfos.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        /*convertView = LayoutInflater.from(con).inflate(android.R.layout.simple_list_item_1, null);
        TextView mess = (TextView)convertView.findViewById(android.R.id.text1);
        mess.setText("Wifi名:"+wifiInfos.get(position).ssidString
                + "\n" + "密码:"+wifiInfos.get(position).PasswordString
        );*/

        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.wlfl_item, null);
            //将convertView中的控件保存到ViewHolder中
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tv_wifi_name);
            holder.tvPass = convertView.findViewById(R.id.tv_wifi_pass);
            holder.tvKey = convertView.findViewById(R.id.tv_wifi_key);
            //将ViewHolder与convertView进行绑定
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        WifiInfo wifiInfo = wifiInfos.get(position);
        holder.tvName.setText(wifiInfo.getSsid());
        if(!TextUtils.isEmpty(wifiInfo.getPass())){
            holder.tvPass.setText(wifiInfo.getPass());
        }else{
            holder.tvPass.setText("密码为空!");
        }
        if(!TextUtils.isEmpty(wifiInfo.getKeymgmt())){
            holder.tvKey.setText(wifiInfo.getKeymgmt());
        }else{
            holder.tvKey.setText("加密类型为空!");
        }

        return convertView;
    }

    /**存放控件*/
    public final class ViewHolder{
        public TextView tvName;
        TextView tvPass;
        TextView tvKey;
    }
}
