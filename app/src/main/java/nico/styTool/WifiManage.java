package nico.styTool;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dump.a.RootManage;

public class WifiManage {


    public static List<WifiInfo> Read(Context context) throws Exception {
        List<WifiInfo> wifiInfos = new ArrayList<>();
        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        StringBuilder wifiConf = new StringBuilder();
        try {
            if (!RootManage.isRoot()) {
                Toast.makeText(context, "null", Toast.LENGTH_LONG).show();
            }
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream.writeBytes("cat /data/misc/wifi/*.conf\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                wifiConf.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
                throw e;
            }
        }

        //Pattern.DOTALL在这种模式下，表达式'.'可以匹配任意字符，包括表示一行的结束符。默认情况下，表达式'.'不匹配行的结束符。
        Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
        Matcher networkMatcher = network.matcher(wifiConf.toString());
        while (networkMatcher.find()) {
            String networkBlock = networkMatcher.group();
            Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssid.matcher(networkBlock);
            if (ssidMatcher.find()) {
                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.setSsid(ssidMatcher.group(1));

                //获取密码
                Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
                Matcher pskMatcher = psk.matcher(networkBlock);
                if (pskMatcher.find()) {
                    wifiInfo.setPass(pskMatcher.group(1));
                } else {
                    //密码为空，跳过
                    continue;
                    //wifiInfo.setPass("");
                }

                //获取加密类型
                Pattern key = Pattern.compile("key_mgmt=([^}abcdefghijklmnopqrstuvwxyz]+)");
                Matcher keyMatcher = key.matcher(networkBlock);
                if (keyMatcher.find()) {
                    wifiInfo.setKeymgmt(keyMatcher.group(1));
                } else {
                    wifiInfo.setKeymgmt("");
                }
                wifiInfos.add(wifiInfo);
            }
        }
        return wifiInfos;
    }

}
