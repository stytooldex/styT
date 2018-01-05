package dump.taptargetview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.view.View;

class ViewTapTarget extends TapTarget {
    private final View view;

    ViewTapTarget(View view, CharSequence title, @Nullable CharSequence description) {
        super(title, description);
        if (view == null) {
            throw new IllegalArgumentException("Given null view to target");
        }
        this.view = view;
    }

    @Override
    public void onReady(final Runnable runnable) {
        ViewUtil.onLaidOut(view, new Runnable() {
            @Override
            public void run() {
                // Cache bounds
                final int[] location = new int[2];
                view.getLocationOnScreen(location);
                bounds = new Rect(location[0], location[1],
                        location[0] + view.getWidth(), location[1] + view.getHeight());

                if (icon == null && view.getWidth() > 0 && view.getHeight() > 0) {
                    final Bitmap viewBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                    final Canvas canvas = new Canvas(viewBitmap);
                    view.draw(canvas);
                    icon = new BitmapDrawable(view.getContext().getResources(), viewBitmap);
                    icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                }

                runnable.run();
            }
        });
    }
}
