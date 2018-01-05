package dump.taptargetview;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

class UiUtil {
  UiUtil() {
  }

  /** Returns the given pixel value in dp **/
  static int dp(Context context, int val) {
    return (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, val, context.getResources().getDisplayMetrics());
  }

  /** Returns the given pixel value in sp **/
  static int sp(Context context, int val) {
    return (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, val, context.getResources().getDisplayMetrics());
  }

  /** Returns the value of the desired theme integer attribute, or -1 if not found **/
  static int themeIntAttr(Context context, String attr) {
    final Resources.Theme theme = context.getTheme();
    if (theme == null) {
      return -1;
    }

    final TypedValue value = new TypedValue();
    final int id = context.getResources().getIdentifier(attr, "attr", context.getPackageName());

    if (id == 0) {
      // Not found
      return -1;
    }

    theme.resolveAttribute(id, value, true);
    return value.data;
  }

  /** Modifies the alpha value of the given ARGB color **/
  static int setAlpha(int argb, float alpha) {
    if (alpha > 1.0f) {
      alpha = 1.0f;
    } else if (alpha <= 0.0f) {
      alpha = 0.0f;
    }

    return ((int) ((argb >>> 24) * alpha) << 24) | (argb & 0x00FFFFFF);
  }
}
