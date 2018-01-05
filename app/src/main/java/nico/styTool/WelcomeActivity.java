package nico.styTool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import dump.z.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        android.support.design.widget.CollapsingToolbarLayout toolbar0 = (android.support.design.widget.CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        toolbar0.setTitle("描述时间：2017-12-02");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "妮哩是一款覆盖功能齐全的工具软件。也可以说是一款集成了许多实用功能的工具箱，例如提取APK、root工具、王者荣耀是也修改、百度云直链提取、网易云音乐启动背景替换、身份证信息查询、快递查询、QQ极速红包等功能\n支付微信支付宝扫码收付款的功能快捷使用\n\n还可以快速领红包现金...\n https://www.coolapk.com/apk/nico.styTool");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        final SharedPreferences i = getSharedPreferences("Hello50", 0);
        Boolean o0 = i.getBoolean("FIRST", true);
        if (o0) {//第一次
            i.edit().putBoolean("FIRST", false).apply();
            ViewTooltip
                    .on(fab)
                    .autoHide(false, 100)
                    .position(ViewTooltip.Position.BOTTOM)
                    .corner(30)
                    .align(ViewTooltip.ALIGN.CENTER)
                    .clickToHide(true)
                    .text("这里分享软件")
                    .show();

        } else {

        }
        TextView mTv_html = (TextView) findViewById(R.id.text_mar);
        String s = "<big>软件介绍:</big><br/><HR></HR>";
        s += "<a>源代码 https://github.com/stytooldex/stynico:</a><br/>覆盖功能齐全的工具软件。也可以说是一款集成了许多实用功能的工具箱，例如提取APK、百度云直链提取、网易云音乐启动背景替换、身份证信息查询、快递查询、QQ极速红包等功能。<br/>微信支付宝扫一扫、付款码功能需要手机ROOT之后才能使用。<br/>应用的UI设计以简洁和直观为主<br/>应用有50个基本功能<br/>将从50个功能中挑选出8个进行简单介绍。<br/>•提取APK<br/><small>正如它的字面意思那样，这个功能将列出我们的手机当中安装的所有应用程序，并允许我们将其提取并保存成APK文件（应用的安装包），该功能对系统应用同样有效（比如提取Mokee默认的Lawnchair启动器的APK）</small><br/>•QQ空间蓝色动态<br/><small>这个功能虽然不见得有多大的实用价值，也不见得有多高的技术含量，不过能发表蓝色字体的QQ空间动态，看起来也是非常炫酷的。它的使用方法非常简单，我们只需在妮哩Q空间蓝色动态功能的输入框中输入我们要发表的文字内容，然后点击生成，随后在QQ或者QQ空间客户端里新建一条说说，在输入框内长按，点击粘贴，再点击发送</small><br/>•女闺蜜Ai智障<br/><small>妮哩中内置了一个自动对话机器人，使用了图灵机器人的接口，这个机器人对自然语义的分析和响应还是十分到位的，感兴趣的读者不妨调戏她一番。</small><br/>•Wi-Fi密码查看器<br/><small>有时我们使用合法的WiFi万能钥匙连接到某一加密的公共WiFI后，并无法直接得知它的密码，而妮哩的WiFi密码查看器功能则可以将此WiFi的密码直接展示出来。此外，你可以将你手机中保存的WiFi密码以二维码为媒介分享给你的朋友。</small><br/>•设备信息<br/><small>妮哩的设备信息功能可以列出你设备型号、系统版本、当前用户等，不过这还不够刺激，厉害的是，它可以将你的手机变成FTP服务器</small><br/>•各省今日油价<br/><small>这个功能对有车一族来说应该比较重要，妮哩可以帮助各位老司机查询各省的0号柴油、90号汽油、93号汽油和97号汽油的油价，虽然界面看起来比较简陋，不过油价信息一目了然</small><br/>•天气预报查询<br/><small>天气预报功能依然画风奇特，信息展现以文字为主，你可以在此选择你要查询天气的城市，妮哩给出的天气信息还算全面</small><br/>•历史上的今天查询<br/><small>除了折腾手机之外，如果没什么时间和精力读长篇大论，看看历史上的今天打发打发时间还是不错的。</small>";
        s += "<br/><br/><big>更新历史:</big><br/><HR></HR><small>_支持 直接启动妹纸资源<br/>xposed:去掉部分软件启动广告<br/>_新功能 QQ抢红包<br/>_抢红包支持自动回复信息(免root)<br/><br/>_免费看vip付费电视 匹配(youku|iqiyi|tudou|qq|mgtv|letv|le|sohu|acfun|pptv|yinyuetai|yy|bilibili|wasu|fun|xunyingwang|meitudata|toutiao|tangdou)<br/>_主页 妹子区{支持开关}<br/>_图片 支持下载<br/>{热更新}<br/>_修复 无法下载<br/>新功能 相册处理(禁止再次生成图片到设备<br/><br/>_修复以及调整部分功能<br/>_新功能 嗶哩嗶哩封面<br/>_优化 部分布局细节<br/>_调整 启动界面<br/>{热更新}<br/>优化 服务端部分问题<br/>_提高动态数据速度<br/>动态功能优化<br/>_新模块 妹子图片><br/>_优化 百度云直链提取<br/>_优化 部分布局<br/>_动态 支持显示多少N天<br/>_修复 状态栏颜色问题<br/>_修复 原生状态栏设备无效<br/><br/>_重写 主页ui<br/>恢复 老版本主页动态<br/>优化一些地方<br/><br/>{热更新}<br/>修复滑动Fc<br/><br/>_修复 一些问题<br/>_视频校验修改>文件校验修改<br/>修改默认颜色<br/>_优化 强制清除RAM内存<br/>_优化 抽奖>_调整文字<br/>_新功能 多进制转换<br/><br/>抽奖加入 王者荣耀可选皮肤：<br/>千年之狐 永久<br/>地狱火 永久<br/>微信现金80元<br/>_修复帐号登录问题及异常<br/>_修复抽奖bug<br/>_一个记事本获取数据调整 默认>1000<br/>热更新<br/>_调整金额<br/>_一些优化<br/><br/>_新功能 一个日记<br/>_新功能 主页快捷功能(微信,支付宝)<br/>_修复 一个开关功能无法正常运行<br/>_修复 无法弹出下载窗口<br/>_启动时间调整 1000L>500L<br/>热更新：<br/>_修复无法调用支付宝<br/>_优化辅助功能<br/><br/>_新功能 王者荣耀视野修改<br/>_新功能 三星设备[noroot]修改dpi<br/>_优化 root工具<br/><br/>_新功能 我的日记<br/>_新功能 快递查询<br/>_新功能 root工具<br/>_优化 记事木文字隔离<br/>_优化 部分root功能黑屏<br/>_优化 妮哩Ai智障[布局,代码]</small>";
        Spanned spanned = Html.fromHtml(s);
        mTv_html.setMovementMethod(LinkMovementMethod.getInstance());
        mTv_html.setText(spanned);
    }
}
