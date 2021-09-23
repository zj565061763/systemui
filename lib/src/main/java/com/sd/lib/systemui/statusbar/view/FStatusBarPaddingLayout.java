package com.sd.lib.systemui.statusbar.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.FrameLayout;

import com.sd.lib.systemui.statusbar.FStatusBarUtils;

public class FStatusBarPaddingLayout extends FrameLayout {
    private boolean mIsPaddingBar = true;
    private int mSavePadding;

    public FStatusBarPaddingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSavePadding = getPaddingTop();
    }

    /**
     * 设置是否padding状态栏高度
     */
    public void setPaddingBar(boolean padding) {
        if (mIsPaddingBar != padding) {
            mIsPaddingBar = padding;
            checkPaddingBar();
        }
    }

    private void checkPaddingBar() {
        int padding = mSavePadding;
        if (mIsPaddingBar) {
            final Context context = getContext();
            if (context instanceof Activity) {
                final Activity activity = (Activity) context;
                final Window window = activity.getWindow();
                final boolean isBarVisible = FStatusBarUtils.isBarVisible(window);
                final boolean isContentExtension = FStatusBarUtils.isContentExtension(window);
                if (isBarVisible && isContentExtension) {
                    padding = FStatusBarUtils.getBarHeight(context);
                }
            } else {
                padding = FStatusBarUtils.getBarHeight(context);
            }
        }

        if (getPaddingTop() != padding) {
            super.setPadding(getPaddingLeft(), padding, getPaddingRight(), getPaddingBottom());
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        mSavePadding = top;
        if (mIsPaddingBar) {
            // 当前padding为状态栏高度，不允许修改
        } else {
            super.setPadding(left, top, right, bottom);
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        checkPaddingBar();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        checkPaddingBar();
    }
}
