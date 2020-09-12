package com.sd.systemui;

import android.app.Activity;

import com.sd.lib.dialoger.impl.FDialoger;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;
import com.sd.lib.utils.context.FResUtil;

public class TestDialog extends FDialoger
{
    private int mSystemUiVisibility;

    public TestDialog(Activity activity)
    {
        super(activity);
        setPadding(0, 0, 0, 0);
        setContentView(R.layout.dialog_test);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getWindow().getAttributes().height = FResUtil.getScreenHeight();
        FStatusBarUtils.setTransparent(getWindow());
        FStatusBarUtils.setBrightness(getOwnerActivity().getWindow(), false);
    }

    @Override
    public void show()
    {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}
