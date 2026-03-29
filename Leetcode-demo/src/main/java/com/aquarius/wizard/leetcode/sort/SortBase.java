package com.aquarius.wizard.leetcode.sort;

/**
 * @author zhaoyijie
 * @since 2024/7/8 12:28
 *
 * 排序类统一入口。
 *
 * 约定：
 *
 * 1. sort 不直接修改调用者传进来的原数组
 * 2. 返回一个排好序的新数组
 */
public interface SortBase {

    int[] sort(int[] arr);
}
