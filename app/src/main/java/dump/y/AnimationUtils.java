package dump.y;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import java.io.ByteArrayOutputStream;


/**
 * Animations util
 * Created by caik on 16/9/22.
 */

public class AnimationUtils
{

    public interface AnimationListener
	{
        void onFinish();
    }

    public static void slideToUp(View view)
	{
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
												 Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
												 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation)
				{

				}

				@Override
				public void onAnimationEnd(Animation animation)
				{

				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{

				}
			});

    }

	/*
     * 将字符串编码成16进制数字,适用于所有字符（包括中文） 
     */ 
    /* 
     * 将16进制数字解码成字符串,适用于所有字符（包括中文） 
     */ 
    public static String jiemi(String st1) 
    { 
		//解码
		String bytes="";
		String rreg[] ={"a","b","c","d","e","f","0","1","2","3","4","5","6","7","8","9"};
		String rre[] = {".",":","+","}","?","$","……","%","_","～","&","#","•",">","、","￥"};

		for (int a=0;a < 16;a++)
		{

			if (a == 0)
			{
				bytes = st1.replace(rre[a], rreg[a]);
			}
			bytes = bytes.replace(rre[a], rreg[a]);

		}
		ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length() / 2); 
//将每2位16进制整数组装成一个字节 
		String hexString = "0123456789abcdef";
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1)))); 
		return new String(baos.toByteArray()); 
    } 
    public static void slideToDown(View view, final AnimationListener listener)
	{
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
												 Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
												 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation)
				{

				}

				@Override
				public void onAnimationEnd(Animation animation)
				{
					if (listener != null)
					{
						listener.onFinish();
					}
				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{

				}
			});

    }
}
