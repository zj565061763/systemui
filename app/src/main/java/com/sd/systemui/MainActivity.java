package com.sd.systemui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.systemui.navigationbar.FNavigationBarUtils;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;
import com.sd.systemui.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
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

        FStatusBarUtils.setTransparent(getWindow());
        FNavigationBarUtils.setTransparent(getWindow());

        Log.i(TAG, "isStatusBarVisible:" + FStatusBarUtils.isStatusBarVisible(getWindow())
                + " statusBarHeight:" + FStatusBarUtils.getStatusBarHeight(this)
                + " isNavigationBarVisible:" + FNavigationBarUtils.isNavigationBarVisible(this)
                + " navigationBarHeight:" + FNavigationBarUtils.getNavigationBarHeight(this));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        FStatusBarUtils.setBrightness(getWindow(), mDarkStatusBar);
        FNavigationBarUtils.setBrightness(getWindow(), mDarkNavigationBar);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mBinding.btnChangeStatusBar)
        {
            mDarkStatusBar = !mDarkStatusBar;
            FStatusBarUtils.setBrightness(getWindow(), mDarkStatusBar);
        } else if (v == mBinding.btnChangeNavigationBar)
        {
            mDarkNavigationBar = !mDarkNavigationBar;
            FNavigationBarUtils.setBrightness(getWindow(), mDarkNavigationBar);
        } else if (v == mBinding.btnDialog)
        {
            if (mTestDialog == null)
                mTestDialog = new TestDialog(this);
            mTestDialog.show();
        }
    }
}