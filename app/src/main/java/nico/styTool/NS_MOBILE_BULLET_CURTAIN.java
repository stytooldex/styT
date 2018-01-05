package nico.styTool;
//微信适配

public class NS_MOBILE_BULLET_CURTAIN {

    public static String receiveUIFunctionName = "d";
    public static String receiveUIParamName = "com.tencent.mm.u.k";
    public static String networkRequest = "com.tencent.mm.model.ak";
    public static String getNetworkByModelMethod = "vy";
    public static String getMessageClass = "com.tencent.mm.e.b.by";
    public static boolean hasTimingIdentifier = false;

    public static void init(String version) {
        switch (version) {
	    
            case "6.5.4":
                receiveUIFunctionName = "d";
                receiveUIParamName = "com.tencent.mm.u.k";
                networkRequest = "com.tencent.mm.model.ak";
                getNetworkByModelMethod = "vy";
                getMessageClass = "com.tencent.mm.e.b.by";
                hasTimingIdentifier = true;
                break;
            default:
                receiveUIFunctionName = "d";
                receiveUIParamName = "com.tencent.mm.u.k";
                networkRequest = "com.tencent.mm.model.ak";
                getNetworkByModelMethod = "vy";
                getMessageClass = "com.tencent.mm.e.b.by";
                hasTimingIdentifier = true;
        }
    }
}

