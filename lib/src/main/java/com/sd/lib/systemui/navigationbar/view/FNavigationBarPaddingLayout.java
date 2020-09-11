package com.sd.lib.systemui.navigationbar.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.sd.lib.systemui.navigationbar.FNavigationBarUtils;

public class FNavigationBarPaddingLayout extends FrameLayout
{
    private boolean mPaddingNavigationBar = true;
    private int mTopPadding;

    public FNavigationBarPaddingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mTopPadding = getPaddingTop();
    }

    public void setPaddingNavigationBar(boolean padding)
    {
        if (mPaddingNavigationBar != padding)
        {
            mPaddingNavigationBar = padding;
            checkPaddingNavigationBar();
        }
    }

    private void checkPaddingNavigationBar()
    {
        if (mPaddingNavigationBar)
        {
            final int barHeight = getBarHeight();
            super.setPadding(getPaddingLeft(), barHeight, getPaddingRight(), getPaddingBottom());
        } else
        {
            super.setPadding(getPaddingLeft(), mTopPadding, getPaddingRight(), getPaddingBottom());
        }
    }

    private int getBarHeight()
    {
        return FNavigationBarUtils.getNavigationBarHeight(getContext());
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
        checkPaddingNavigationBar();
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        checkPaddingNavigationBar();
    }
}
