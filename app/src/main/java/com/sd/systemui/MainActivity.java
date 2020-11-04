package com.sd.systemui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.systemui.common.FSystemUIUtils;
import com.sd.lib.systemui.navigationbar.FNavigationBarUtils;
import com.sd.lib.systemui.statusbar.FStatusBar;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;
import com.sd.systemui.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements FStatusBar.Config, View.OnClickListener
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    private boolean mDarkStatusBar = true;
    private boolean mDarkNavigationBar = true;

    private TestDialog mTestDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        FStatusBarUtils.setTransparent(this);
        FNavigationBarUtils.setTransparent(this);

        Log.i(TAG, "StatusBar"
                + " isBarVisible:" + FStatusBarUtils.isBarVisible(getWindow())
                + " isContentExtension:" + FStatusBarUtils.isContentExtension(getWindow())
                + " getBarHeight:" + FStatusBarUtils.getBarHeight(this));

        Log.i(TAG, "NavigationBar"
                + " isBarVisible:" + FNavigationBarUtils.isBarVisible(this)
                + " isContentExtension:" + FNavigationBarUtils.isContentExtension(getWindow())
                + " getBarHeight:" + FNavigationBarUtils.getBarHeight(this));

        Log.i(TAG, "hasNotch:" + FSystemUIUtils.hasNotch(this));

        FStatusBar.of(this).setDefaultConfig(this);
    }

    @Override
    public FStatusBar.Brightness getStatusBarBrightness()
    {
        return mDarkStatusBar ? FStatusBar.Brightness.dark : FStatusBar.Brightness.light;
    }

    @Override
    public void onClick(View v)
    {
        if (v == mBinding.btnChangeStatusBar)
        {
            mDarkStatusBar = !mDarkStatusBar;
            FStatusBarUtils.setBrightness(this, mDarkStatusBar);
        } else if (v == mBinding.btnChangeNavigationBar)
        {
            mDarkNavigationBar = !mDarkNavigationBar;
            FNavigationBarUtils.setBrightness(this, mDarkNavigationBar);
        } else if (v == mBinding.btnDialog)
        {
            if (mTestDialog == null)
                mTestDialog = new TestDialog(this);
            mTestDialog.show();
        }
    }
}