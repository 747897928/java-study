package com.aquarius.wizard.leetcode;

import java.util.concurrent.Executors;

/**
 * <p>description:  </p>
 * <p>create: 2020/10/14 21:29</p>
 *
 * @author :zhaoyijie
 */


public class JosephRing {
    public static void main(String[] args) {
        lastLeaver(6, 5, 3);
    }

    /**
     * 1 2 3 4 5 6  -> 5      5   5
     * 1 2   4   6  <- 3      3   3
     *              -> 5      1   4
     *              <- 3      2   1
     *              -> 5      4   2
     *              <- 3      6   6
     */
    static void lastLeaver(int n, int m, int k) {
        int[] num = new int[n];
        for (int index = 0; index < num.length; index++) {
            num[index] = index + 1;//从1－nums给每个人编号
        }
        int q = 0; //q为退出人数
        int r = 1;
        int len1 = n - 1;//长度-1
        int currindex = 0;
        int conut;
        while (q < len1) {
            if (r % 2 == 0) {
                conut = 0;
                while (true) {
                    if (num[currindex] != 0) {
                        conut++;
                    }
                    if (conut == k) {
                        break;
                    }
                    currindex = currindex - 1;
                    if (currindex < 0) {
                        currindex = len1;
                    }
                }
                //k<-
            } else {
                conut = 0;
                while (true) {
                    if (num[currindex] != 0) {
                        conut++;
                    }
                    if (conut == m) {
                        break;
                    }
                    currindex = (currindex + 1) % n;
                }
                //m->
            }
            int i3 = num[currindex];
            System.out.print(i3 + " ");
            num[currindex] = 0;
            currindex = (currindex + 1) % n;
            while (num[currindex] == 0) {
                currindex = (currindex + 1) % n;
            }
            q++;
            r++;
        }
        for (int j = 0; j < n; j++) {
            if (num[j] != 0) {
                System.out.print((j + 1) + " ");
            }
        }
    }

}
