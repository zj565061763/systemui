package com.sd.systemui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.systemui.statusbar.FStatusBarPadding;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;
import com.sd.systemui.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private ActivityMainBinding mBinding;

    private FStatusBarPadding mStatusBarPadding;
    private boolean mDarkStatusBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mStatusBarPadding = new FStatusBarPadding(getWindow());
        mStatusBarPadding.addView(mBinding.getRoot());

        FStatusBarUtils.setTransparent(getWindow(), mDarkStatusBar);
    }

    @Override
    public void onClick(View v)
    {
        mDarkStatusBar = !mDarkStatusBar;
        FStatusBarUtils.setTransparent(getWindow(), mDarkStatusBar);
    }
}