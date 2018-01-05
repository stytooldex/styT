package dump.d;

import java.util.List;

/**
 * Description：有道翻译实体类
 * <p>
 * Created by Mjj on 2016/12/19.
 */

public class TranslateBean {

    private String query; // 你要翻译的文本
    /**
     * 0 - 正常
     * 　20 - 要翻译的文本过长
     * 　30 - 无法进行有效的翻译
     * 　40 - 不支持的语言类型
     * 　50 - 无效的key
     * 　60 - 无词典结果，仅在获取词典结果生效
     */
    private int errorCode;
    private List<String> translation; // 翻译结果

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

}
