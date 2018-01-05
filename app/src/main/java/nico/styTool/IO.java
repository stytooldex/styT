package nico.styTool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
public class IO
{
	public static Bitmap getImageFromPath(String filePath)
	{  
		Bitmap bitmap = null;  
		BitmapFactory.Options opts = new BitmapFactory.Options();  
		opts.inJustDecodeBounds = true;  
		BitmapFactory.decodeFile(filePath, opts);  

		//缩放图片，避免内存不足
		opts.inSampleSize = computeSampleSize(opts, -1, 250 * 250);  
		opts.inJustDecodeBounds = false;  

		try
		{  
			bitmap = BitmapFactory.decodeFile(filePath, opts);  
		}
		catch (Exception e)
		{  
			// TODO: handle exception  
		}  
		return bitmap;  
	}  
	//缩放图片算法
	private static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
	{
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8)
		{
			roundedSize = 1;
			while (roundedSize < initialSize)
			{
				roundedSize <<= 1;
			}
		}
		else
		{
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
	{
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound)
		{
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1))
		{
			return 1;
		}
		else if (minSideLength == -1)
		{
			return lowerBound;
		}
		else
		{
			return upperBound;
		}
	} 

	//读取流，返回Byte数组
	public static byte[] readStream(String file)
	{

		InputStream inStream=null;
		byte[] buffer = new byte[1024];
		int len = -1;
		byte[] data = null;
		try
		{
			inStream = new FileInputStream(file);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			while ((len = inStream.read(buffer)) != -1)
			{
				outStream.write(buffer, 0, len);
			}
			data = outStream.toByteArray();
			outStream.close();
			inStream.close();
		}
		catch (IOException ignored)
		{}
		return data;

	}

	//保存图片
	public static void saveBitmap(Bitmap bitmap, String path)
	{    
		FileOutputStream mFileOutputStream = null;    
		//如果文件夹不存在则创建文件夹
		if (!new File(new File(path).getParent()).exists())
		{
			File f=new File(path);
			new File(f.getParent()).mkdirs();
		}
		try
		{    
			File mFile = new File(path);    
			//创建文件
			mFile.createNewFile();    
			//创建文件输出流    
			mFileOutputStream = new FileOutputStream(mFile);    
			//保存Bitmap到PNG文件    
			//图片压缩质量为100，对于PNG来说这个参数会被忽略    
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, mFileOutputStream);    
			mFileOutputStream.flush();    
		}
		catch (IOException e)
		{    
			// TODO Auto-generated catch block    
			e.printStackTrace();    
		}
		finally
		{    
			try
			{    
				mFileOutputStream.close();    
			}
			catch (IOException e)
			{    
				// TODO: handle exception    
				e.printStackTrace();    
			}    

		}   

	}


}
