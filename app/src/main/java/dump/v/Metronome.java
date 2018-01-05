package dump.v;

import android.view.Choreographer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Metronome implements Choreographer.FrameCallback {

  private Choreographer choreographer;

  private long frameStartTime = 0;
  private int framesRendered = 0;

  private List<Audience> listeners = new ArrayList<>();
  private int interval = 500;

  public Metronome() {
    choreographer = Choreographer.getInstance();
  }

  public void start() {
    choreographer.postFrameCallback(this);
  }

  public void stop() {
    frameStartTime = 0;
    framesRendered = 0;
    choreographer.removeFrameCallback(this);
  }

  public void addListener(Audience l) {
    listeners.add(l);
  }

  public void setInterval(int interval) {
    this.interval = interval;
  }

  @Override public void doFrame(long frameTimeNanos) {
    long currentTimeMillis = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos);

    if (frameStartTime > 0) {
      // take the span in milliseconds
      final long timeSpan = currentTimeMillis - frameStartTime;
      framesRendered++;

      if (timeSpan > interval) {
        final double fps = framesRendered * 1000 / (double) timeSpan;

        frameStartTime = currentTimeMillis;
        framesRendered = 0;

        for (Audience audience : listeners) {
          audience.heartbeat(fps);
        }
      }
    } else {
      frameStartTime = currentTimeMillis;
    }

    choreographer.postFrameCallback(this);
  }
}
