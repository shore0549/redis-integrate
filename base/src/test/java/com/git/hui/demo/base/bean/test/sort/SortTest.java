package com.git.hui.demo.base.bean.test.sort;

import org.junit.Test;
import sun.security.util.Length;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yihui in 22:07 18/5/15.
 */
public class SortTest {

    public void maopao(int[] arrays) {
        int len = arrays.length;
        int tmp;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (arrays[j] > arrays[j + 1]) {
                    tmp = arrays[j];
                    arrays[j] = arrays[j + 1];
                    arrays[j + 1] = tmp;
                }
            }
        }
    }


    // 选择排序
    // 每次选择最大的，放在最后
    public void xuanze(int[] arrays) {
        int max;
        int last = 0;
        int len = arrays.length;
        for (int i = 0; i < len; i++) {
            max = Integer.MIN_VALUE;
            for (int j = 0; j < len - i; j++) {
                if (arrays[j] > max) {
                    max = arrays[j];
                    last = j;
                }
            }

            if (max == Integer.MIN_VALUE) {
                continue;
            }

            // 交换
            arrays[last] = arrays[len - i - 1];
            arrays[len - i - 1] = max;
        }
    }


    // 选择最小值，放在最前面
    public void xuanze2(int[] arrays) {
        int len = arrays.length;
        int min = Integer.MIN_VALUE;
        int last = 0;
        for (int i = 0; i < len; i++) {
            min = arrays[i];
            last = i;

            // 遍历选择最小的值
            for (int j = i + 1; j < len; j++) {
                if (arrays[j] < min) {
                    min = arrays[j];
                    last = j;
                }
            }

            if (last == i) {
                continue;
            }

            // 最小的与前面的进行互换
            arrays[last] = arrays[i];
            arrays[i] = min;
        }
    }


    // 插入排序
    // 每次选择一个数，与前面排好序的进行排，插入到合适的位置
    public void insertSort(int[] arrays) {
        int len = arrays.length;
        int tmp;
        for (int i = 1; i < len; i++) {
            tmp = arrays[i];
            for (int j = i - 1; j >= 0; j--) {
                if (arrays[j] > tmp) { // 后移动一位
                    arrays[j + 1] = arrays[j];
                } else { // 可以确定插入的位置了
                    arrays[j + 1] = tmp;
                    break;
                }
            }

            if (tmp < arrays[0]) {
                arrays[0] = tmp;
            }
        }
    }

    private int[] randAry(int num) {
        int[] arys = new int[num];
        for (int i = 0; i < num; i++) {
            arys[i] = (int) (Math.random() * 100);
        }
        return arys;
    }


    private void judgeArrySort(int[] arys) {
        boolean judge = true;
        for (int j = 1; j < arys.length; j++) {
            if (arys[j - 1] > arys[j]) {
                judge = false;
                break;
            }
        }
        System.out.println("judge : " + judge);
    }

    @Test
    public void testSort() {
        int[] arys = randAry(10);

        for (int i = 0; i < 10; i++) {

            System.out.println("\n------冒泡排序-----------");
            System.out.println("before: " + Arrays.toString(arys));
            maopao(arys);
            System.out.println("after: " + Arrays.toString(arys));
            judgeArrySort(arys);


            System.out.println("\n------选择排序-----------");


            arys = randAry(10);
            System.out.println("before: " + Arrays.toString(arys));
            xuanze2(arys);
            System.out.println("after: " + Arrays.toString(arys));
            judgeArrySort(arys);


            System.out.println("\n------插入排序-----------");


            arys = randAry(10);
            System.out.println("before: " + Arrays.toString(arys));
            insertSort(arys);
            System.out.println("after: " + Arrays.toString(arys));
            judgeArrySort(arys);
        }
    }

}
