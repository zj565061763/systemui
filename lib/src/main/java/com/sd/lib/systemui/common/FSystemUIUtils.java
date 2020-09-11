package com.sd.lib.systemui.common;

public class FSystemUIUtils
{
    public static int addFlag(int original, int flag)
    {
        return original | flag;
    }

    public static int clearFlag(int original, int flag)
    {
        return original & (~flag);
    }
}
