package dump.o;

/**
 * Created by lum on 2017/10/22.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SaveArrayListUtil {
    /**
     * 将arrayList的内容保存到sp里
     */
    public void saveArrayList(Context context, ArrayList searchList, String content) {
        //searchList里“无数据”
        if (searchList.size() == 0) {
            //直接存
            searchList.add(content + "");
        } else {
            //searchList里“有数据”
            //但不包含这条数据，直接在0的位置加上这条数据
            if (!searchList.contains(content)) {
                searchList.add(0, content + "");
            } else {
                //包含了这条数据，就删除掉，并放在0位置或者原位置（自由选择）。
                for (int i = 0; i < searchList.size(); i++) {
                    if (searchList.get(i).equals(content)) {
                        searchList.remove(i);
                        searchList.add(0, content + "");//0或者i均可。
                    }
                }
            }
        }
        //定义SP.Editor和文件名称
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "SearchDataList", context.MODE_PRIVATE).edit();
        //将结果放入文件，关键是把集合大小放入，为了后面的取出判断大小。
        editor.putInt("searchNums", searchList.size());
        for (int i = 0; i < searchList.size(); i++) {
            //用条目+i,代表键，解决键的问题，也方便等一下取出，值也对应。
            editor.putString("item_" + i, searchList.get(i) + "");
        }
        editor.commit();
    }

    /**
     * 读取sp里的数组
     */
    public ArrayList<String> getSearchArrayList(Context context) {
        //先定位到文件
        SharedPreferences preferDataList = context.getSharedPreferences(
                "SearchDataList", context.MODE_PRIVATE);
        //定义一个集合等下返回结果
        ArrayList<String> list = new ArrayList<>();
        //刚才存的大小此时派上用场了
        int searchNums = preferDataList.getInt("searchNums", 0);
        //根据键获取到值。
        for (int i = 0; i < searchNums; i++) {
            String searchItem = preferDataList.getString("item_" + i, null);
            //放入新集合并返回
            list.add(searchItem);
        }
        return list;
    }
}