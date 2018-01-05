package dump.v;

import android.view.Gravity;

public enum Seat {
  TOP_RIGHT(Gravity.TOP | Gravity.END),
  TOP_LEFT(Gravity.TOP | Gravity.START),
  TOP_CENTER(Gravity.TOP | Gravity.CENTER_HORIZONTAL),

  RIGHT_CENTER(Gravity.END | Gravity.CENTER_VERTICAL),
  LEFT_CENTER(Gravity.START | Gravity.CENTER_VERTICAL),
  CENTER(Gravity.CENTER),

  BOTTOM_RIGHT(Gravity.BOTTOM | Gravity.END),
  BOTTOM_LEFT(Gravity.BOTTOM | Gravity.START),
  BOTTOM_CENTER(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

  private int gravity;

  Seat(int gravity) {
    this.gravity = gravity;
  }

  public int getGravity() {
    return gravity;
  }
}
