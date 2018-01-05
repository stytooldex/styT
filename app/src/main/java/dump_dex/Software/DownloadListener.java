package dump_dex.Software;

/**
 * Created by lum on 2017/9/25.
 */

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onPaused();

    void onFailed();

    void onCancel();
}