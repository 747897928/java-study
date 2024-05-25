package com.aquarius.wizard.leetcode.sort;

import java.util.Arrays;

/*
 * <p>description:  </p>
 * <p>create:  2020/11/20 15:32</p>
 * @author: zhaoyijie(AquariusGenius)
 */
public class QuickSort {
    /**
     * 快速排序排序效率在同为O(N*logN)的几种排序方法中效率较高,加上快速排序思想----分治法也确实实用
     * 该方法的基本思想是：
     * 1．先从数列中取出一个数作为基准数。
     * 2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
     * 3．再对左右区间重复第二步，直到各区间只有一个数。
     * 虽然快速排序称为分治法，但分治法这三个字显然无法很好的概括快速排序的全部步骤。因此我的对快速排序作
     * 了进一步的说明：挖坑填数+分治法：
     */
    public static void main(String[] args) {
        int[] arr = {14, 12, 15, 13, 11, 16};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] s) {
        quickSort(s, 0, s.length - 1);
    }
    //快速排序
    public static void quickSort(int[] s, int l, int r) {
        if (l < r) {
            //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
            int i = l, j = r, x = s[l];
            while (i < j) {
                while (i < j && s[j] >= x) // 从右向左找第一个小于x的数
                    j--;
                if (i < j)
                    s[i++] = s[j];

                while (i < j && s[i] < x) // 从左向右找第一个大于等于x的数
                    i++;
                if (i < j)
                    s[j--] = s[i];
            }
            s[i] = x;
            quickSort(s, l, i - 1); // 递归调用
            quickSort(s, i + 1, r);
        }
    }
}
