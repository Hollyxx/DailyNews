package com.xx.invoker.dailynews.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by invoker on 2017/3/4.
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    private View mChildView;

    public void setChildView(View view) {
        mChildView = view;
    }

    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mChildView != null) {
            if (android.os.Build.VERSION.SDK_INT < 14) {
                if (mChildView instanceof AbsListView) {
                    final AbsListView absListView = (AbsListView) mChildView;
                    return absListView.getChildCount() > 0
                            && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                            .getTop() < absListView.getPaddingTop());
                } else {
                    return ViewCompat.canScrollVertically(mChildView, -1) || mChildView.getScrollY() > 0;
                }
            } else {
                return ViewCompat.canScrollVertically(mChildView, -1);
            }
        }
        return super.canChildScrollUp();
    }
}
