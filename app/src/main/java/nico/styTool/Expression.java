package nico.styTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luxin on 15-12-12.
 * http://luxin.gitcafe.io
 */
public class Expression {

    private static List<int[]> emojiIdList = new ArrayList<int[]>();
    private static List<String[]> emojiNameList = new ArrayList<String[]>();


    public static final int[] emojID = new int[]{R.mipmap.emoji_0, R.mipmap.emoji_1, R.mipmap.emoji_2,
            R.mipmap.emoji_3, R.mipmap.emoji_4, R.mipmap.emoji_5, R.mipmap.emoji_6, R.mipmap.emoji_7,
            R.mipmap.emoji_8, R.mipmap.emoji_9, R.mipmap.emoji_10, R.mipmap.emoji_11, R.mipmap.emoji_12,
            R.mipmap.emoji_13, R.mipmap.emoji_14, R.mipmap.emoji_15, R.mipmap.emoji_16, R.mipmap.emoji_17,
            R.mipmap.emoji_18, R.mipmap.emoji_19, R.mipmap.emoji_20, R.mipmap.emoji_21, R.mipmap.emoji_22,
            R.mipmap.emoji_23, R.mipmap.emoji_24, R.mipmap.emoji_25, R.mipmap.emoji_26, R.mipmap.emoji_27,
            R.mipmap.emoji_28, R.mipmap.emoji_29, R.mipmap.emoji_30, R.mipmap.emoji_31, R.mipmap.emoji_32,
            R.mipmap.emoji_33, R.mipmap.emoji_34, R.mipmap.emoji_35, R.mipmap.emoji_36, R.mipmap.emoji_37,
            R.mipmap.emoji_38, R.mipmap.emoji_39, R.mipmap.emoji_40, R.mipmap.emoji_41, R.mipmap.emoji_42,
            R.mipmap.emoji_43, R.mipmap.emoji_44, R.mipmap.emoji_45, R.mipmap.emoji_46, R.mipmap.emoji_47,
            R.mipmap.emoji_48, R.mipmap.emoji_49, R.mipmap.emoji_50, R.mipmap.emoji_51, R.mipmap.emoji_52,
            R.mipmap.emoji_53, R.mipmap.emoji_54, R.mipmap.emoji_55, R.mipmap.emoji_56, R.mipmap.emoji_57,
            R.mipmap.emoji_58, R.mipmap.emoji_59, R.mipmap.emoji_60, R.mipmap.emoji_61, R.mipmap.emoji_62,
            R.mipmap.emoji_63, R.mipmap.emoji_64, R.mipmap.emoji_65, R.mipmap.emoji_66,
            R.mipmap.emoji_67, R.mipmap.emoji_68, R.mipmap.emoji_69, R.mipmap.emoji_70, R.mipmap.emoji_71,
            R.mipmap.emoji_72, R.mipmap.emoji_73, R.mipmap.emoji_74, R.mipmap.emoji_75, R.mipmap.emoji_76,
            R.mipmap.emoji_77, R.mipmap.emoji_78, R.mipmap.emoji_79, R.mipmap.emoji_80, R.mipmap.emoji_81,
            R.mipmap.emoji_82, R.mipmap.emoji_83, R.mipmap.emoji_84, R.mipmap.emoji_85, R.mipmap.emoji_86,
            R.mipmap.emoji_87, R.mipmap.emoji_88, R.mipmap.emoji_89, R.mipmap.emoji_90, R.mipmap.emoji_91,
            R.mipmap.emoji_92, R.mipmap.emoji_93, R.mipmap.emoji_94, R.mipmap.emoji_95, R.mipmap.emoji_96,
            R.mipmap.emoji_97, R.mipmap.emoji_98, R.mipmap.emoji_99, R.mipmap.emoji_100, R.mipmap.emoji_101,
            R.mipmap.emoji_102, R.mipmap.emoji_103, R.mipmap.emoji_104};

    public static final String[] emojName = new String[]{"[/微笑]", "[/撇嘴]", "[/色]", "[/发呆]",
            "[/得意]", "[/流泪]", "[/害羞]", "[/闭嘴]", "[/睡]", "[/大哭]", "[/尴尬]", "[/发怒]",
            "[/调皮]", "[/呲牙]", "[/惊讶]", "[/难过]", "[/酷]", "[/冷汗]", "[/抓狂]", "[/吐]",
            "[/偷笑]", "[/可爱]", "[/白眼]", "[/傲慢]", "[/饥饿]", "[/困]", "[/惊恐]", "[/流汗]",
            "[/憨笑]", "[/大兵]", "[/奋斗]", "[/咒骂]", "[/疑问]", "[/嘘...]", "[/晕]", "[/折磨]",
            "[/衰]", "[/骷髅]", "[/敲打]", "[/再见]", "[/擦汗]", "[/抠鼻]", "[/鼓掌]", "[/糗大了]",
            "[/坏笑]", "[/左哼哼]", "[/右哼哼]", "[/哈欠]", "[/鄙视]", "[/委屈]", "[/快哭了]", "[/阴险]",
            "[/亲亲]", "[/吓]", "[/可怜]", "[/菜刀]", "[/西瓜]", "[/啤酒]", "[/篮球]", "[/乒乓]", "[/咖啡]",
            "[/饭]", "[/猪头]", "[/玫瑰]", "[/凋谢]", "[/示爱]", "[/爱心]", "[/心碎]", "[/蛋糕]", "[/闪电]",
            "[/炸弹]", "[/刀]", "[/足球]", "[/瓢虫]", "[/便便]", "[/月亮]", "[/太阳]", "[/礼物]", "[/拥抱]",
            "[/强]", "[/弱]", "[/握手]", "[/胜利]", "[/抱拳]", "[/勾引]", "[/拳头]", "[/差劲]", "[/爱你]",
            "[/NO]", "[/OK]", "[/爱情]", "[/飞吻]", "[/跳跳]", "[/发抖]", "[/怄火]", "[/转圈]", "[/磕头]",
            "[/回头]", "[/跳绳]", "[/挥手]", "[/激动]", "[/街舞]", "[/献吻]", "[/左太极]", "[/右太极]"};

    static {
        emojiIdList.add(emojID);
        emojiNameList.add(emojName);
    }

    /**
     * 由表情名称获得表情图片id
     *
     * @param name
     * @return
     */
    public static int getIdAsName(String name) {
        for (int i = 0; i < emojName.length; i++) {
            if (emojName[i].equals(name)) {
                return emojID[i];
            }
        }
        return 0;
    }

    /**
     * @param name
     * @return
     */
    public static String changeKeys(String name) {
        for (int i = 0; i < emojName.length; i++) {
            if (emojName[i].equals(name)) {
                i++;
                return "emoji_" + i;
            }
        }
        return null;
    }
}
