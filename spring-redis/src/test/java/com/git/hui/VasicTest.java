package com.git.hui;

/**
 * Created by @author yihui in 11:31 18/6/26.
 */
public class VasicTest {
    public static final int MS_IN_MINUTE = 60 * 1000;
    /**
     * compare date
     *
     * @param left
     * @param right
     * @return
     */
    public static int compareMinute(long left, long right) {
        return (int) (left / MS_IN_MINUTE - right / MS_IN_MINUTE);
    }



}
