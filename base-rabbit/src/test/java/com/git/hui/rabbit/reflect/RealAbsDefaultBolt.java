package com.git.hui.rabbit.reflect;

/**
 * Created by yihui in 18:52 18/5/29.
 */
public class RealAbsDefaultBolt extends AbsDefaultBolt<Boolean> {
    public void print() {
        System.out.println(this.getClass().getSimpleName() + " Boolean ");
    }
}
