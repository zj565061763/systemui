package com.sd.lib.systemui.statusbar.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.sd.lib.systemui.statusbar.FStatusBarUtils;

public class FStatusBarPaddingLayout extends FrameLayout
{
    private boolean mPaddingStatusBar = true;
    private int mTopPadding;

    public FStatusBarPaddingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mTopPadding = getPaddingTop();
    }

    public void setPaddingStatusBar(boolean padding)
    {
        if (mPaddingStatusBar != padding)
        {
            mPaddingStatusBar = padding;
            checkPaddingStatusBar();
        }
    }

    private void checkPaddingStatusBar()
    {
        if (mPaddingStatusBar)
        {
            final int statusBarHeight = getStatusBarHeight();
            super.setPadding(getPaddingLeft(), statusBarHeight, getPaddingRight(), getPaddingBottom());
        } else
        {
            super.setPadding(getPaddingLeft(), mTopPadding, getPaddingRight(), getPaddingBottom());
        }
    }

    private int getStatusBarHeight()
    {
        return FStatusBarUtils.getActivityStatusBarHeight(getContext());
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom)
    {
        mTopPadding = top;
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
