package com.sd.lib.systemui.common;

public class FFlagUtils
{
    public static int addFlag(int original, int flag)
    {
        return original | flag;
    }

    public static int clearFlag(int original, int flag)
    {
        return original & (~flag);
    }

    public static boolean hasFlag(int original, int flag)
    {
        return (original & flag) == flag;
    }
}
