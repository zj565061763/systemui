package com.sd.lib.systemui.navigationbar.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.sd.lib.systemui.navigationbar.FNavigationBarUtils;

public class FNavigationBarPaddingLayout extends FrameLayout
{
    private boolean mPaddingNavigationBar = true;
    private int mBottomPadding;

    public FNavigationBarPaddingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mBottomPadding = getPaddingBottom();
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
            final int barHeight = FNavigationBarUtils.getNavigationBarHeight(getContext());
            super.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), barHeight);
        } else
        {
            super.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), mBottomPadding);
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom)
    {
        mBottomPadding = bottom;
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
