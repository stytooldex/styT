package nico;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.Toast;

import java.io.File;

/**
 * Created by castle on 16-8-30.
 * 文件工具类
 */
public class FileUtils {

    /**
     * 删除单个文件
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean deleteFileb(File file) {
        // Log.w(Util.TAG, file.getName() + "删除结果：" + isDeleted);
        return file.exists() && file.delete();
// Log.w(Util.TAG, "文件删除失败：文件不存在！");
    }

    /**
     * 删除单个文件
     *
     * @param path     文件所在路径名
     * @param fileName 文件名
     * @return 删除成功则返回true
     */
    public static boolean deleteFilea(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        return file.exists() && file.delete();
    }


    /**
     * 删除文件
     */
    public static boolean deleteFilestytool(String filePath) {

        if (!TextUtils.isEmpty(filePath)) {
            final File file = new File(filePath);

            if (file.exists()) {
                return file.delete();
            }
        }
        return false;
    }

    /**
     * 删除文件夹下所有文件
     *
     * @return
     */
    public static void deleteDirectoryAllFile(String directoryPath) {
        final File file = new File(directoryPath);
        deleteDirectoryAllFile(file);
    }

    public static void deleteDirectoryAllFile(File file) {
        if (!file.exists()) {
            return;
        }

        boolean rslt = true;// 保存中间结果
        if (!(rslt = file.delete())) {// 先尝试直接删除
            // 若文件夹非空。枚举、递归删除里面内容
            final File subs[] = file.listFiles();
            final int size = subs.length - 1;
            for (int i = 0; i <= size; i++) {
                if (subs[i].isDirectory())
                    deleteDirectoryAllFile(subs[i]);// 递归删除子文件夹内容
                rslt = subs[i].delete();// 删除子文件夹本身
            }
            // rslt = file.delete();// 删除此文件夹本身
        }

        if (!rslt) {
            ///if (LogUtils.DEBUG) {
            //     LogUtils.w("无法删除:" + file.getName());
            // }
            return;
        }
    }


    /**
     * 获取版本号
     * String
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            //LogUtil.e("info", "versionName" + versionName);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 如果文件不存在，就创建文件
     *
     * @param path 文件路径
     * @return
     */
    public static String createIfNotExist(Context context, String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                //Toast.makeText(context, "收藏确定", Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "删除目录失败", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "请清除本程序数据\n" + "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return path;
    }

    /**
     * 递归删除一个文件，如果是目录删除所有子文件及目录
     */
    public static boolean delete(File file) {
        if (file.isFile()) {
            return file.delete();
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                return file.delete();
            }

            for (File childFile : childFiles) {
                delete(childFile);
            }
            return file.delete();
        }
        return false;
    }

    /**
     * 根据后缀判断文件是否是一张合法的图片
     *
     * @param filename 文件名
     * @return 是否是指定格式的有效图片
     */
    public static boolean isImage(String filename) {
        return filename.toLowerCase().matches(".*\\.(jpg|jpeg|bmp|gif|png|webp)$");
    }

    /**
     * 根据后缀判断文件是否是zip文件
     *
     * @param filename 文件名
     * @return 是否是zip格式的文件
     */
    public static boolean isZip(String filename) {
        return filename.toLowerCase().matches(".*\\.(zip|cbz)$");
    }

    /**
     * 根据后缀判断文件是否是rar文件
     *
     * @param filename 文件名
     * @return 是否是rar格式的文件
     */
    public static boolean isRar(String filename) {
        return filename.toLowerCase().matches(".*\\.(rar|cbr)$");
    }

    /**
     * 根据后缀判断文件是否是cbt文件
     *
     * @param filename 文件名
     * @return 是否是cbt格式的文件
     */

    public static boolean isTarball(String filename) {
        return filename.toLowerCase().matches(".*\\.(cbt)$");
    }

    /**
     * 根据后缀判断文件是否是cb7或者7z文件
     *
     * @param filename 文件名
     * @return 是否是cb7或者7z格式的文件
     */
    public static boolean isSevenZ(String filename) {
        return filename.toLowerCase().matches(".*\\.(cb7|7z)$");
    }

    /**
     * 根据后缀判断文件是否是压缩文件，如果文件名后缀为zip,rar,cbt,7z,cb7,cbr,cbz其中之一则为压缩文件
     *
     * @param filename 文件名
     * @return 是否是压缩文件
     */
    public static boolean isArchive(String filename) {
        return isZip(filename) || isRar(filename) || isTarball(filename) || isSevenZ(filename);
    }


    /**
     * 获得SD卡总大小
     *
     * @return
     */
    public static String getSDTotalSize(Context ontext) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(ontext, blockSize * totalBlocks);
    }


    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    public static String getSDAvailableSize(Context ontext) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(ontext, blockSize * availableBlocks);
    }
}