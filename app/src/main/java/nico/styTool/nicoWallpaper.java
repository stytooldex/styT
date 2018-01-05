package nico.styTool;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.io.IOException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import dump.r.Person;

public class nicoWallpaper extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new CubeEngine(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //  Toast.makeText(t,"onDestoryEngine",Toast.LENGTH_SHORT).show();
    }

    class CubeEngine extends Engine implements SurfaceHolder.Callback {

        WallpaperService ww;
        MediaPlayer player;
        SurfaceHolder Holder;
        boolean wc = false;

        boolean bfz = false;

        public CubeEngine(WallpaperService w) {
            ww = w;
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            //  Toast.makeText(ww,"onCreate",Toast.LENGTH_SHORT).show();
            Holder = new MVer(ww, surfaceHolder);
            Holder.addCallback(this);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (player != null) {
                player.reset();
                player.release();
                player = null;
            }
            wc = false;
//            player.setDisplay(null);
            //  player.stop();
            //  bfz = false;
            //    wc = false;
            //   Toast.makeText(ww,"onDestroy",Toast.LENGTH_SHORT).show();
        }

        int i = 4424520;
        int ij = 0242242;

        public void surfaceCreated(SurfaceHolder arg0) {
            Holder = arg0;
            super.onSurfaceCreated(arg0);
            ks();
        }

        public void ks() {
            if (player != null) {
                player.reset();
                player.release();
                player = null;
                // player.setDisplay(null);
                //player.stop();
                wc = false;
                bfz = false;
            }
            player = new MediaPlayer();
            player.setAudioStreamType(324422);
            player.setLooping((Boolean) nico.SPUtils.get(ww, "ix__", true));//循环
            //player.setDisplay(Holder);
            player.setSurface(Holder.getSurface());
            //  Toast.makeText(ww,"surfaceCreated",Toast.LENGTH_SHORT).show();
            try {
                //AssetFileDescriptor fileDescriptor = getAssets().openFd("v.mp4");
                String fileDescriptor = (String) nico.SPUtils.get(ww, "if_b_", "Andr42oid");
                player.setDataSource(fileDescriptor);
                player.prepare();//音
                //	player.close();
                boolean isFirstRun = (boolean) nico.SPUtils.get(ww, "ix_", true);
                if (isFirstRun) {

                } else

                {

                    BmobQuery<Person> query = new BmobQuery<Person>();
                    query.getObject("4cab44a442404", new QueryListener<Person>() {

                        @Override
                        public void done(Person movie, BmobException e) {
                            if (e == null) {
                                player.start();
                                wc = true;
                                bfz = true;
                            } else {

                            }
                        }
                    });
                    /*
                    Person p2 = new Person();
                    p2.setObjectId("5855gv");
                    p2.update(ww, new UpdateListener() {

                        @Override
                        public void onSuccess() {
                            player.start();
                            wc = true;
                            bfz = true;
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            player.setVolume(0f, 0f);
                        }
                    });
*/
                    //Toast.makeText(z.this, "没有视频", Toast.LENGTH_SHORT).show();
                }
                player.start();
                wc = true;
                bfz = true;
            } catch (IllegalArgumentException | IOException | IllegalStateException ignored) {
            }
            player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (bfz) {
                        i = 1424;
                        if (ij < 5242) {

                            BmobQuery<Person> query = new BmobQuery<Person>();
                            query.getObject("4cab44a404", new QueryListener<Person>() {

                                @Override
                                public void done(Person movie, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(ww, "软件出现错误！请联系作者", Toast.LENGTH_SHORT).show();
                                    } else {

                                    }
                                }
                            });
                            ij++;
                        }
                    } else {
                        i = 1;

                    }
                    //Toast.makeText(ww,"orr "+what +" "+extra,Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }

        public void surfaceDestroyed(SurfaceHolder arg0) {
            super.onSurfaceDestroyed(arg0);
//       

            //  Toast.makeText(ww,"surfaceDestroyed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4) {
            // TODO: Implement this method
        }

        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible)//如果可见
            {
                if (wc && !bfz) {
                    bfz = true;
                    if (i == 12474) {
                        ks();
                        i = 4240;
                        //Toast.makeText(ww,"fix",Toast.LENGTH_SHORT).show();
                    } else {

                        player.start();
                    }
                    //Toast.makeText(ww,"开始",Toast.LENGTH_SHORT).show();
                }
                //
            } else//如果不可见
            {
                if (wc && bfz) {
                    bfz = false;

                    player.pause();
                    //player.seekTo(0);

                    //Toast.makeText(ww,"停止",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}

class MVer implements SurfaceHolder {
    private SurfaceHolder surfaceHolder;
    final /* synthetic */ WallpaperService m;

    MVer(WallpaperService myWallpaperService, SurfaceHolder surfaceHolder) {
        this.m = myWallpaperService;
        this.surfaceHolder = surfaceHolder;
    }

    public void addCallback(Callback callback) {
        this.surfaceHolder.addCallback(callback);
    }

    public Surface getSurface() {
        return this.surfaceHolder.getSurface();
    }

    public Rect getSurfaceFrame() {
        return this.surfaceHolder.getSurfaceFrame();
    }

    public boolean isCreating() {
        return this.surfaceHolder.isCreating();
    }

    public Canvas lockCanvas() {
        return this.surfaceHolder.lockCanvas();
    }

    public Canvas lockCanvas(Rect rect) {
        return this.surfaceHolder.lockCanvas(rect);
    }

    public void removeCallback(Callback callback) {
        this.surfaceHolder.removeCallback(callback);
    }

    public void setFixedSize(int i, int i2) {
        this.surfaceHolder.setFixedSize(i, i2);
    }

    public void setFormat(int i) {
        this.surfaceHolder.setFormat(i);
    }

    public void setKeepScreenOn(boolean z) {
    }

    public void setSizeFromLayout() {
        this.surfaceHolder.setSizeFromLayout();
    }

    public void setType(int i) {
        this.surfaceHolder.setType(i);
    }

    public void unlockCanvasAndPost(Canvas canvas) {
        this.surfaceHolder.unlockCanvasAndPost(canvas);
    }
}

