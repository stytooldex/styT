package dump.g;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/9.
 */
public class BeList<T> extends BeJson {
    private List<T> list;
    private int islastFlag;

    public int getIslastFlag() {
        return islastFlag;
    }

    public List<T> getList() {
        return list == null ? new ArrayList<T>() : list;
    }
}
