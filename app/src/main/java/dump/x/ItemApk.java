package dump.x;

import android.graphics.drawable.Drawable;

import java.util.Comparator;

class ItemApk {
    private Drawable dIcon;
    private String dataDir;
    private String displayName;
    private String packageName;
    private boolean running;
    private int size;
    private String sourceDir;
    private int versionCode;
    private String versionName;

    static class DisplayNameAscendComparator implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return apk1.getDisplayName().compareTo(apk2.getDisplayName());
        }
    }

    static class DisplayNameAscendComparatorIC implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return apk1.getDisplayName().compareToIgnoreCase(apk2.getDisplayName());
        }
    }

    static class DisplayNameDescendComparator implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return -apk1.getDisplayName().compareTo(apk2.getDisplayName());
        }
    }

    static class DisplayNameDescendComparatorIC implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return -apk1.getDisplayName().compareToIgnoreCase(apk2.getDisplayName());
        }
    }

    static class SizeAscendComparator implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            int diff = apk1.getSize() - apk2.getSize();
            if (diff < 0) {
                return -1;
            }
            if (diff > 0) {
                return 1;
            }
            return 0;
        }
    }

    static class SizeDescendComparator implements Comparator<ItemApk> {
        public int compare(ItemApk apk1, ItemApk apk2) {
            return new SizeAscendComparator().compare(apk2, apk1);
        }
    }

    public String getPackageName() {
        return this.packageName;
    }

    String getDisplayName() {
        return this.displayName;
    }

    String getSourceDir() {
        return this.sourceDir;
    }

    public String getDataDir() {
        return this.dataDir;
    }

    String getVersionName() {
        return this.versionName;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public Drawable getIcon() {
        return this.dIcon;
    }

    public int getSize() {
        return this.size;
    }

    boolean getRunning() {
        return this.running;
    }

    String getStrSize() {
        if (this.size > 1048576) {
            return String.valueOf(String.valueOf(this.size / 1048576)) + " MB";
        }
        if (this.size > 1024) {
            return String.valueOf(String.valueOf(this.size / 1024)) + " KB";
        }
        return String.valueOf(String.valueOf(this.size / 1024)) + " B";
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setIcon(Drawable dIcon) {
        this.dIcon = dIcon;
    }

    void setRunning(boolean running) {
        this.running = running;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
