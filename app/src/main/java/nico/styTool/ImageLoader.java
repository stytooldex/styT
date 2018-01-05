package nico.styTool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by luxin on 15-12-10.
 * http://luxin.gitcafe.io
 * 观看鸿阳大神视频造的轮子
 * http://www.imooc.com/learn/489 有兴趣可以去看看，
 */
public class ImageLoader {

    private static ImageLoader mInstance = null;

    private LruCache<String, Bitmap> mLruCache;
    private ExecutorService mThreadPool;
    private static final int DEAFULT_Thread_COUNT = 1;

    private Type mType = Type.LIFO;
    private LinkedList<Runnable> mTaskQueue;

    private Handler mPoolThreadHandler;

    private Handler mUiHandler;


    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;


    private boolean isDiskCacheEnable = true;

    private ImageLoader(int threadCount, Type type) {
        init(threadCount, type);
    }


    public static ImageLoader getmInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(1, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    public static ImageLoader getInstance(int threadCount, Type type) {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(threadCount, type);
                }
            }
        }
        return mInstance;
    }

    private void init(int threadCount, Type type) {
        Thread mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ;
                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        };
        mPoolThread.start();
        ;

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;
        mSemaphoreThreadPool = new Semaphore(threadCount);

    }

    private Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTaskQueue.removeLast();
        }
        return null;
    }

    public enum Type {
        FIFO, LIFO;
    }


    public void loaderImage(final String path, final ImageView imageView, boolean isFromNet) {
        imageView.setTag(path);
        if (mUiHandler == null) {
            mUiHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
                    Bitmap bm = holder.bitmap;
                    String path = holder.path;
                    ImageView imageView1 = holder.imageView;
                    if (imageView1.getTag().toString().equals(path)) {
                        imageView1.setImageBitmap(bm);
                    }
                }
            };
        }

        Bitmap bm = getBitmapLruCache(path);
        if (bm != null) {
            refreashBitmap(path, imageView, bm);
        } else {
//            addTask(new Runnable(){
//                @Override
//                public void run() {
//                    ImageSize imageSize=getImageViewSize(imageView);
//                    Bitmap bm=decodeSampleBitmapFromPath(path,imageSize.width,imageSize.height);
//                    addBitmapToLruCache(path,bm);
//                    refreashBitmap(path, imageView, bm);
//                    mSemaphoreThreadPool.release();
//                }
//            });

            addTask(buildTask(path, imageView, isFromNet));
        }
    }

    private Runnable buildTask(final String path, final ImageView imageView, final boolean isFromNet) {
        return new Runnable() {
            @Override
            public void run() {
                Bitmap bm = null;
                if (isFromNet) {
                    File file = getDiskCacheDir(imageView.getContext(), md5(path));
                    if (file.exists()) {
                        bm = loadImgFromlocal(file.getAbsolutePath(), imageView);
                    } else {
                        if (isDiskCacheEnable) {
                            boolean downloadState = DownLoadImgUtil.downloadImgByUrl(path, file);
                            if (downloadState) {
                                bm = loadImgFromlocal(file.getAbsolutePath(), imageView);
                            }
                        } else {
                            // 直接从网络加载
                            bm = DownLoadImgUtil.downloadImgByUrl(path, imageView);
                        }
                    }
                } else {
                    bm = loadImgFromlocal(path, imageView);
                }
                addBitmapToLruCache(path, bm);
                refreashBitmap(path, imageView, bm);
                mSemaphoreThreadPool.release();
            }
        };
    }


    /**
     * @param absolutePath
     * @param imageView
     * @return
     */
    private Bitmap loadImgFromlocal(String absolutePath, ImageView imageView) {
        Bitmap bitmap = null;
        ImageSize imageSize = getImageViewSize(imageView);
        bitmap = decodeSampleBitmapFromPath(absolutePath, imageSize.width, imageSize.height);
        return bitmap;
    }

    /**
     * 获得缓存图片的地址
     *
     * @param context
     * @param uniqueName
     * @return
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private String md5(String path) {
        byte[] digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            digest = md.digest(path.getBytes());
            return bytes2hex02(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param digest
     * @return
     */
    private String bytes2hex02(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        String tem = null;
        for (byte b : digest) {
            tem = Integer.toHexString(0xFF & b);
            if (tem.length() == 1) {
                tem = "0" + tem;
            }
            sb.append(tem);
        }
        return sb.toString();
    }

    private void refreashBitmap(String path, ImageView imageView, Bitmap bm) {
        Message message = Message.obtain();
        ImageBeanHolder holder = new ImageBeanHolder();
        holder.bitmap = bm;
        holder.imageView = imageView;
        holder.path = path;
        message.obj = holder;
        mUiHandler.sendMessage(message);
    }

    private void addBitmapToLruCache(String path, Bitmap bm) {
        if (getBitmapLruCache(path) == null) {
            if (bm != null) {
                mLruCache.put(path, bm);
            }
        }
    }

    private Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    private ImageSize getImageViewSize(ImageView imageView) {

        ImageSize imageSize = new ImageSize();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        int width = imageView.getWidth();
        if (width <= 0) {
            width = lp.width;
        }
        if (width <= 0) {
            width = getImageViewFieldValue(imageView, "mMaxWidth");
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();
        if (height <= 0) {
            height = lp.height;
        }
        if (height <= 0) {
            height = getImageViewFieldValue(imageView, "mMaxHeight");
        }
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }


    private static int getImageViewFieldValue(Object obj, String fieldName) {
        int value = 0;
        Field field;
        try {
            field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(obj);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private synchronized void addTask(Runnable runnable) {
        mTaskQueue.add(runnable);
        if (mPoolThreadHandler == null) {
            try {
                mSemaphorePoolThreadHandler.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    private Bitmap getBitmapLruCache(String path) {
        return mLruCache.get(path);
    }


    private class ImageBeanHolder {
        ImageView imageView;
        Bitmap bitmap;
        String path;
    }

    private class ImageSize {
        int width;
        int height;
    }

}
