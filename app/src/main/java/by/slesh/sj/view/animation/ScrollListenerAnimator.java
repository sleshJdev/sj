package by.slesh.sj.view.animation;

import android.view.View;
import android.view.animation.Animation;
import android.widget.AbsListView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by slesh on 06.09.2015.
 */
public class ScrollListenerAnimator implements android.widget.AbsListView.OnScrollListener {
    private final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();
    private View mTargetView;
    private int mInitialHeight;
    private boolean mIsDown;//false-expand, false-collapse

    public ScrollListenerAnimator(View targetView) {
        this.mTargetView = targetView;
    }

    private void animate(final boolean isDown) {
        DropDownAnimation animation = new DropDownAnimation(mTargetView, mInitialHeight, isDown);
        animation.setDuration(TimeUnit.SECONDS.toMillis(2));
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsDown = !isDown;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mTargetView.startAnimation(animation);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (view.getChildCount() > 0) {
            mInitialHeight = mInitialHeight > 0 ? mInitialHeight : mTargetView.getHeight();
            int listItemHeight = view.getChildAt(0).getHeight();
            int firstVisiblePosition = view.getFirstVisiblePosition();
            if (mIsDown && firstVisiblePosition * listItemHeight > mInitialHeight / 2) {
                animate(mIsDown = false);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!mIsDown && firstVisibleItem == 0 && visibleItemCount < totalItemCount) {
            mIsDown = true;
            EXECUTOR.schedule(new Runnable() {
                @Override
                public void run() {
                    animate(mIsDown);
                }
            }, 5, TimeUnit.SECONDS);
        }
    }
}
