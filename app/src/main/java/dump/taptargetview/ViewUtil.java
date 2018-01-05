package dump.taptargetview;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewManager;
import android.view.ViewTreeObserver;

class ViewUtil {
    ViewUtil() {
    }

    /**
     * Returns whether or not the view has been laid out
     **/
    private static boolean isLaidOut(View view) {
        return ViewCompat.isLaidOut(view) && view.getWidth() > 0 && view.getHeight() > 0;
    }

    /**
     * Executes the given {@link java.lang.Runnable} when the view is laid out
     **/
    static void onLaidOut(final View view, final Runnable runnable) {
        if (isLaidOut(view)) {
            runnable.run();
            return;
        }

        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final ViewTreeObserver trueObserver;

                if (observer.isAlive()) {
                    trueObserver = observer;
                } else {
                    trueObserver = view.getViewTreeObserver();
                }

                removeOnGlobalLayoutListener(trueObserver, this);

                runnable.run();
            }
        });
    }

    @SuppressWarnings("deprecation")
    static void removeOnGlobalLayoutListener(ViewTreeObserver observer,
                                             ViewTreeObserver.OnGlobalLayoutListener listener) {
        observer.removeOnGlobalLayoutListener(listener);
    }

    static void removeView(ViewManager parent, View child) {
        if (parent == null || child == null) {
            return;
        }

        try {
            parent.removeView(child);
        } catch (NullPointerException ignored) {
            // This catch exists for modified versions of Android that have a buggy ViewGroup
            // implementation. See b.android.com/77639, #121 and #49
        }
    }
}
