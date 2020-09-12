package com.sd.systemui;

import android.app.Activity;

import com.sd.lib.dialoger.impl.FDialoger;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;
import com.sd.lib.utils.context.FResUtil;

public class TestDialog extends FDialoger
{
    public TestDialog(Activity activity)
    {
        super(activity, R.style.lib_dialoger_default);
        setPadding(0, 0, 0, 0);
        setContentView(R.layout.dialog_test);

        FStatusBarUtils.setTransparent(getWindow(), false);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getWindow().getAttributes().height = FResUtil.getScreenHeight();
    }

    @Override
    public void show()
    {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
//        getWindow().getDecorView().setSystemUiVisibility(getOwnerActivity().getWindow().getDecorView().getSystemUiVisibility());
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}
