package nico;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by david on 2016/11/23.
 * 从Assets文件夹中读取.json文件 输出String
 */

public class JsonAssetsReaderUtil {

    public static String getJsonStrFromAssets(Context context, String jsonFileName) {
        InputStreamReader inputStreamReader = null;
        StringBuilder stringBuilder = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open(jsonFileName), "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String jsonStr;
            stringBuilder = new StringBuilder();
            while ((jsonStr = bufferedReader.readLine()) != null) {
                stringBuilder.append(jsonStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }
}