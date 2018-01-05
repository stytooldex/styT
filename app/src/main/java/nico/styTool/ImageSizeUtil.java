package nico.styTool;


import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by luxin on 15-12-15.
 *  http://luxin.gitcafe.io
 */
public class ImageSizeUtil {

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int caculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        int width=options.outWidth;
        int height=options.outHeight;

        int inSampleSize=1;
        if(width>reqWidth||height>reqHeight){
            int withRadio=Math.round(width*1.0f/reqWidth);
            int heightRadio=Math.round(height*1.0f/reqHeight);
            inSampleSize=Math.max(withRadio,heightRadio);
        }
        return inSampleSize;
    }

    /**
     * 根据ImageView获适当的压缩的宽和高
     * @param imageView
     * @return
     */
    public static ImageSize getImageViewSize(ImageView imageView){
        ViewGroup.LayoutParams lp=imageView.getLayoutParams();
        DisplayMetrics displayMetrics= imageView.getContext().getResources().getDisplayMetrics();
        int width=imageView.getWidth();
        if(width<=0){
            width=lp.width;
        }
        if(width<=0){
            width=getImageViewFieltValue(imageView,"mMaxWidth");
        }
        if(width<=0){
            width=displayMetrics.widthPixels;
        }
        int height=imageView.getHeight();
        if(height<=0){
            height=lp.height;
        }
        if(height<=0){
            height=getImageViewFieltValue(imageView,"mMaxHeight");
        }
        if(height<=0){
            height=displayMetrics.heightPixels;
        }
        ImageSize imageSize=new ImageSize();
        imageSize.width=width;
        imageSize.height=height;
        return imageSize;
    }

    /**
     * 通过反射获取imageview的某个属性值
     * @param object
     * @param fileName
     * @return
     */
    public static int getImageViewFieltValue(Object object,String fileName){
        int value=0;
        try{
            Field field=ImageView.class.getDeclaredField(fileName);
            field.setAccessible(true);
            int fieldValue=field.getInt(object);
            if(fieldValue>0&&fieldValue<Integer.MAX_VALUE){
                value=fieldValue;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public static class ImageSize{
        int width;
        int height;
    }
}
