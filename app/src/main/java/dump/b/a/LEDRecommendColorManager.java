package dump.b.a;

import java.util.ArrayList;
import java.util.List;


public class LEDRecommendColorManager {
    public static final String[] colorName = {"黑-黄", "绿-红", "橙-蓝", "紫-黄"};
    public static final int[] fontcolor = {0xffefdc05, 0xffff1700, 0xff1b57eb, 0xff859402};
    public static final int[] bgcolor = {0xff090707, 0xff0f7f42, 0xffebae1b, 0xff560294};

    private static LEDRecommendColorManager instance;
    private List<LEDRecommendColor> mLEDRecommendColors;

    private LEDRecommendColorManager() {
        mLEDRecommendColors = new ArrayList<>();
        for (int i = 0; i < colorName.length; i++) {
            mLEDRecommendColors.add(new LEDRecommendColor(colorName[i], bgcolor[i], fontcolor[i]));
        }
    }

    public static synchronized LEDRecommendColorManager getInstance() {
        if (instance == null) {
            instance = new LEDRecommendColorManager();
        }
        return instance;
    }

    public List<LEDRecommendColor> getLEDRecommendColors() {
        return mLEDRecommendColors;
    }
}
