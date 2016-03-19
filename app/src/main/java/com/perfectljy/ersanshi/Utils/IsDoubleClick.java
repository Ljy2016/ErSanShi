package com.perfectljy.ersanshi.Utils;

/**
 * Created by PerfectLjy on 2016/3/19.
 */
public class IsDoubleClick {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD > 0 && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
