package nico.styTool;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Video.Media;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class FileUtils {
    public static List<File> getFileListByDirPath(String path, FileFilter filter) {
        File directory = new File(path);
        File[] files = directory.listFiles(filter);

        if (files == null) {
            return new ArrayList<>();
        }

        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());
        return result;
    }

    public static String cutLastSegmentOfPath(String path) {
        return path.substring(0, path.lastIndexOf("/"));
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
    public static String getPath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String string;
        if (uri.getScheme().compareTo("content") == 0) {
            Cursor query = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            if (query == null || !query.moveToFirst()) {
                return null;
            }
            string = query.getString(query.getColumnIndexOrThrow("_data"));
            query.close();
            return string;
        } else if (uri.getScheme().compareTo("file") != 0) {
            return null;
        } else {
            string = uri.toString();
            return uri.toString().replace("file://", "");
        }
    }
}
