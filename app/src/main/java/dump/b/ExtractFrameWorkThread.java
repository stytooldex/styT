package dump.b;

import android.os.Handler;

public class ExtractFrameWorkThread extends Thread {
    public static final int MSG_SAVE_SUCCESS = 0;
    private String videoPath;
    private String OutPutFileDirPath;
    private long startPosition;
    private long endPosition;
    private int thumbnailsCount;
    private VideoExtractFrameAsyncUtils mVideoExtractFrameAsyncUtils;

    public ExtractFrameWorkThread(int extractW, int extractH, Handler mHandler, String videoPath, String OutPutFileDirPath,
                                  long startPosition, long endPosition, int thumbnailsCount) {
        this.videoPath = videoPath;
        this.OutPutFileDirPath = OutPutFileDirPath;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.thumbnailsCount = thumbnailsCount;
        this.mVideoExtractFrameAsyncUtils = new VideoExtractFrameAsyncUtils(extractW, extractH, mHandler);
    }

    @Override
    public void run() {
        super.run();
        mVideoExtractFrameAsyncUtils.getVideoThumbnailsInfoForEdit(
                videoPath,
                OutPutFileDirPath,
                startPosition,
                endPosition,
                thumbnailsCount);
    }

    public void stopExtract() {
        if (mVideoExtractFrameAsyncUtils != null) {
            mVideoExtractFrameAsyncUtils.stopExtract();
        }
    }
}
