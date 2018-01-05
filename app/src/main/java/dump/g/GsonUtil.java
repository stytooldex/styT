package dump.g;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
/**
 * @description : Gson使用工具类 所有的Gson 的使用 全部通过这个工具类来进行调用
 */
public class GsonUtil {
    private Gson gons;

    public Gson getGson() {
        if (gons == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gons = gsonBuilder.create();
        }
        return gons;
    }

    /**
     * 解析一个数组
     *
     * @param json 要解析的json
     * @return
     * @type 要解析成的类型
     */
    public <T> T getJsonList(String json, GsonType type) {
        if (!StringUtil.isEmpty(json)) {
            Gson gson = getGson();
            try {
                return gson.fromJson(json, type.type);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 解析一个对象
     *
     * @param json
     * @param classs
     * @return
     */
    public <T> T getJsonObject(String json, Class<T> classs) {
        if (!StringUtil.isEmpty(json)) {
            Gson gson = getGson();
            try {
                return gson.fromJson(json, classs);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 将对象转换成json 对象
     *
     * @param object
     * @return
     */
    public JsonElement getJsonElement(Object object) {
        Gson gson = getGson();
        JsonParser parser = new JsonParser();
        return parser.parse(gson.toJson(object));
    }
}
