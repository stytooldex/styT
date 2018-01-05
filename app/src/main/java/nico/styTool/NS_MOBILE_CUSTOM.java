package nico.styTool;

public class NS_MOBILE_CUSTOM
{

//QQ适配
    public static String QQPluginClass = "com.tenpay.android.qqplugin.a.q";

    public static void init(int version)
    {
        if (version < 260)
	{
	    QQPluginClass = "com.tenpay.android.qqplugin.a.o";
        }
	else if (version <= 312)
	{
	    QQPluginClass = "com.tenpay.android.qqplugin.a.p";
        }
	else if (version <= 482)
	{
	    QQPluginClass = "com.tenpay.android.qqplugin.a.q";
        }
    }}
