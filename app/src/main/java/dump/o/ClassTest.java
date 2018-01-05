package dump.o;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lum on 2017/10/22.
 */
public class ClassTest implements Serializable {

    public String mAppId;

    public int mPlatForm;

    public List<ClassInner> mList;

    public static class ClassInner implements Serializable {
        public int id;
        public List<ClassInnerOther> mInnerList;

    }

    public static class ClassInnerOther implements Serializable {
        public String name;
        public int value;
    }
}