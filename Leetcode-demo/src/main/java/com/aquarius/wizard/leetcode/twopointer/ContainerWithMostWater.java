package com.aquarius.wizard.leetcode.twopointer;

import java.util.concurrent.Executors;

/**
 * @author zhaoyijie
 * @since 2024/7/16 22:49
 */
public class ContainerWithMostWater {

    //https://leetcode.cn/problems/container-with-most-water/?envType=study-plan-v2&envId=top-100-liked
    //盛最多水的容器
    //给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
    //找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
    //返回容器可以储存的最大水量。
    //说明：你不能倾斜容器。
    //输入：[1,8,6,2,5,4,8,3,7]
    //输出：49
    //解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
    //示例 2：
    //输入：height = [1,1]
    //输出：1
    //提示：
    //n == height.length
    //2 <= n <= 105
    //0 <= height[i] <= 104
    //暴力法
    public int maxArea(int[] height) {
        int maxArea = 0;
        int minHeight = 0;
        int width = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                minHeight = Math.min(height[i], height[j]);
                width = j - i;
                maxArea = Math.max(width * minHeight, maxArea);
            }
        }
        return maxArea;
    }

    //双指针，如果右指针的值比左边指针的值长，那么我们应该固定右指针移动左指针，
    //如果右指针的值比左边指针的值短，那么我们应该固定左指针移动右指针，
    //因为短的边限制了面积增大的可能性，就是移动较短边，看看面积是否存在增大的可能
    public int maxArea2(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            if (height[left] > height[right]) {
                maxArea = Math.max((right - left) * height[right], maxArea);
                right--;
            } else {
                maxArea = Math.max((right - left) * height[left], maxArea);
                left++;
            }
        }
        return maxArea;
    }
}
