package com.sd.systemui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.systemui.navigationbar.FNavigationBarUtils;
import com.sd.lib.systemui.statusbar.FStatusBarPadding;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;
import com.sd.systemui.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    private FStatusBarPadding mStatusBarPadding;
    private boolean mDarkStatusBar = true;
    private boolean mDarkNavigationBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mStatusBarPadding = new FStatusBarPadding(getWindow());
        mStatusBarPadding.addView(mBinding.getRoot());

        FStatusBarUtils.setTransparent(getWindow(), mDarkStatusBar);
        FNavigationBarUtils.setTransparent(getWindow(), mDarkNavigationBar);

        Log.i(TAG, "isStatusBarVisible:" + FStatusBarUtils.isStatusBarVisible(getWindow())
                + " statusBarHeight:" + FStatusBarUtils.getStatusBarHeight(this)
                + " isNavigationBarVisible:" + FNavigationBarUtils.isNavigationBarVisible(this)
                + " navigationBarHeight:" + FNavigationBarUtils.getNavigationBarHeight(this));
    }

    @Override
    public void onClick(View v)
    {
        if (v == mBinding.btnChangeStatusBar)
        {
            mDarkStatusBar = !mDarkStatusBar;
            FStatusBarUtils.setTransparent(getWindow(), mDarkStatusBar);
        } else if (v == mBinding.btnChangeNavigationBar)
        {
            mDarkNavigationBar = !mDarkNavigationBar;
            FNavigationBarUtils.setTransparent(getWindow(), mDarkNavigationBar);
        }
    }
}