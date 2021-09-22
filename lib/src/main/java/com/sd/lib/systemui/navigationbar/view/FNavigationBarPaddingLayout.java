package com.sd.lib.systemui.navigationbar.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.FrameLayout;

import com.sd.lib.systemui.navigationbar.FNavigationBarUtils;

public class FNavigationBarPaddingLayout extends FrameLayout {
    private boolean mIsCheckPadding = true;
    private int mSavePadding;

    public FNavigationBarPaddingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSavePadding = getPaddingBottom();
    }

    /**
     * 设置是否检查底部导航栏padding
     *
     * @param padding
     */
    public void setCheckPadding(boolean padding) {
        if (mIsCheckPadding != padding) {
            mIsCheckPadding = padding;
            checkPaddingNavigationBar();
        }
    }

    private void checkPaddingNavigationBar() {
        int padding = mSavePadding;

        if (mIsCheckPadding) {
            final Context context = getContext();
            if (context instanceof Activity) {
                final Activity activity = (Activity) context;
                final Window window = activity.getWindow();
                final boolean isBarVisible = FNavigationBarUtils.isBarVisible(context);
                final boolean isContentExtension = FNavigationBarUtils.isContentExtension(window);
                if (isBarVisible && isContentExtension) {
                    padding = FNavigationBarUtils.getBarHeight(context);
                }
            } else {
                padding = FNavigationBarUtils.getBarHeight(context);
            }
        }

        if (getPaddingBottom() != padding) {
            super.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), padding);
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        mSavePadding = bottom;

        if (mIsCheckPadding) {
            bottom = getPaddingBottom();
        }
        super.setPadding(left, top, right, bottom);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        checkPaddingNavigationBar();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        checkPaddingNavigationBar();
    }
}
