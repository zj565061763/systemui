package com.sd.systemui;

import android.app.Activity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.sd.lib.dialoger.impl.FDialoger;
import com.sd.lib.systemui.navigationbar.FNavigationBarUtils;
import com.sd.lib.systemui.statusbar.FStatusBarUtils;
import com.sd.lib.utils.context.FResUtil;

public class TestDialog extends FDialoger
{
    public TestDialog(Activity activity)
    {
        super(activity, R.style.lib_dialoger_default);
        setPadding(0, 0, 0, 0);
        setContentView(R.layout.dialog_test);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        setTransparent();
    }

    public void setTransparent()
    {
        FStatusBarUtils.setTransparent(getWindow());

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.TOP;
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = FResUtil.getScreenHeight() ;
        getWindow().setAttributes(params);
//        getWindow().getDecorView().setSystemUiVisibility(getOwnerActivity().getWindow().getDecorView().getSystemUiVisibility());
    }

//    @Override
//    public void show()
//    {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//        super.show();
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//    }
}
