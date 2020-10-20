package com.sd.lib.systemui.statusbar.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.FrameLayout;

import com.sd.lib.systemui.statusbar.FStatusBarUtils;

public class FStatusBarPaddingLayout extends FrameLayout
{
    private boolean mIsCheckPadding = true;
    private int mSavePadding;

    public FStatusBarPaddingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mSavePadding = getPaddingTop();
    }

    /**
     * 设置是否检查状态栏padding
     *
     * @param padding
     */
    public void setCheckPadding(boolean padding)
    {
        if (mIsCheckPadding != padding)
        {
            mIsCheckPadding = padding;
            checkPaddingStatusBar();
        }
    }

    private void checkPaddingStatusBar()
    {
        int padding = mSavePadding;

        if (mIsCheckPadding)
        {
            final Context context = getContext();
            if (context instanceof Activity)
            {
                final Activity activity = (Activity) context;
                final Window window = activity.getWindow();
                final boolean isBarVisible = FStatusBarUtils.isBarVisible(window);
                final boolean isContentExtension = FStatusBarUtils.isContentExtension(window);
                if (isBarVisible && isContentExtension)
                {
                    padding = FStatusBarUtils.getBarHeight(context);
                }
            } else
            {
                padding = FStatusBarUtils.getBarHeight(context);
            }
        }

        if (getPaddingTop() != padding)
            super.setPadding(getPaddingLeft(), padding, getPaddingRight(), getPaddingBottom());
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom)
    {
        mSavePadding = top;

        if (mIsCheckPadding)
            top = getPaddingTop();
        super.setPadding(left, top, right, bottom);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        checkPaddingStatusBar();
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        checkPaddingStatusBar();
    }
}
