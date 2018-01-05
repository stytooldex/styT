package nico.styTool;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Audio  {
    static final int SAMPLE_RATE_IN_HZ = 8000; 
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,  
																AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);  
    AudioRecord mAudioRecord;//用于获取数据
    final Object mLock;//为了使用wait函数
    Handler myHandler;//用于向主线程传递数据
    public Audio(Handler handler) {
    	/*
    	 * 初始化。
    	 * 这里我选择了把handler传递过来，应该也可以在主线程中把handler设为static直接使用。
    	 */
        mLock = new Object();
        myHandler=handler;
    }
    public void getNoiseLevel() {
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
									   SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
									   AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        //新建一个线程，录音并处理数据
        new Thread(new Runnable() {
				@Override  
				public void run() {
					mAudioRecord.startRecording();  
					short[] buffer = new short[BUFFER_SIZE];
					//像这样不给退出条件好像不太好...不管了。
					while (true) {
						//读取数据，返回值是数据长度
						int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
						long v = 0;  
						//下面是数据处理，涉及声学的一些知识
						//这里是算出一小段时间的平均值
						//读到的数据应该是振幅值之类的，求得平方平均数然后取对数再乘一个系数可以得到分贝值。
						for (short aBuffer : buffer) {
							v += aBuffer * aBuffer;
						}    
						double mean = v / (double) r;  
						double volume = 10 * Math.log10(mean);
						/*
						 * 将数据传递给主线程
						 */
						Message msg = new Message();
						//bundle是一个key-value对，读数据的时候我写出key就可以读到对应的value
						Bundle b=new Bundle();
						//key设为"sound"，value设为分贝值，保留两位小数
						b.putString("sound",String.format("%.2f", volume));
						msg.setData(b);
						myHandler.sendMessage(msg);
						//停100ms
						synchronized (mLock) {  
							try {  
								mLock.wait(100);  
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}  
			}).start();  
    }
} 