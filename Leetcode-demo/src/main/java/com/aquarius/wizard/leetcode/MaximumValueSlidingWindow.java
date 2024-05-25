package com.aquarius.wizard.leetcode;


import java.util.Arrays;

/**
 * <p>description:  </p>
 * <p>create:  2020/11/15 12:10</p>
 * @author: zhaoyijie(AquariusGenius)
 */
public class MaximumValueSlidingWindow {
    /**
     * 广度优先遍历经常用到队列
     * <p>
     * 基本实现
     * 可以利用一个双链表
     * 队列的头尾两端能在O(1)的时间内进行数据的查看、添加和删除
     * 常用的场景
     * 实现一个长度动态变化的窗口或者连续区间
     * 239.滑动窗口最大值
     * 示例:
     * 给定一个数组nums，有一个大小为k的滑动窗口从数组的最
     * <p>
     * 左侧移动到数组的最右侧。你只可以看到在滑动窗口k内的数
     * 字，滑动窗口每次只向右移动一位。返回滑动窗口最大值。
     * 输入: nums = [1,3,-1,-3,5,3,6,7],和k= 3
     * 输出:[3,3,5,5,6,7]
     * 注意:你可以假设k总是有效的，1 ≤k≤输入数组的大小，且输入数组不为空。
     */
    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        int[] result = maxSlidingWindow(nums,k);
        System.out.println(Arrays.toString(result));

    }


    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0)
            return new int[0];
        int[] result = new int[nums.length - k + 1];
        Queue heap = new Queue();
        int index = 0;
        for (int i = 0; i < k; ++i) {
            heap.add(nums[i]);
        }
        result[index++] = heap.head.value;
        for (int i = 1; i < result.length; ++i) {
            heap.remove(nums[i - 1]);
            heap.add(nums[i + k - 1]);
            result[index++] = heap.head.value;
        }
        return result;
    }

    /*public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0)
            return new int[0];
        int[] result = new int[nums.length - k + 1];
        int index = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < k; ++i) {
            max = Math.max(nums[i], max);
        }
        result[index++] = Math.max(nums[0], max);
        for (; index < result.length; ++index) {
            result[index] = Math.max(max, nums[index + k - 1]);
            // max被窗口舍弃,重新选择max
            if (result[index] == nums[index]) {
                int temp = Integer.MIN_VALUE;
                for (int i = 1; i < k; ++i) {
                    temp = Math.max(temp, nums[index + i]);
                }
                max = temp;
            } else {
                max = result[index];
            }
        }
        return result;
    }
*/

    static class Queue {
        static class Node {
            int value;
            Node next;

            public Node() {
            }

            public Node(int value) {
                this.value = value;
            }

        }

        Node head;

        public Queue() {
        }

        public void remove(int value) {
            Node temp = head, pre = null;
            while (temp.next != null) {
                if (temp.value == value) {
                    break;
                } else {
                    pre = temp;
                    temp = temp.next;
                }
            }
            if (pre == null) {// head
                head = head.next;
            } else {
                pre.next = temp.next;
            }

        }

        public void add(int value) {
            Node newn = new Node(value);
            if (head == null) {
                head = newn;
            } else {
                Node temp = head, pre = null;
                while (temp != null) {
                    if (value > temp.value)
                        break;
                    else {
                        pre = temp;
                        temp = temp.next;
                    }
                }
                if (pre == null) {// head位置
                    newn.next = head;
                    head = newn;
                } else {
                    pre.next = newn;
                    newn.next = temp;
                }
            }

        }

        void print() {
            Node temp = head;
            while (temp != null) {
                System.out.println(temp.value);
                temp = temp.next;
            }
        }
    }
}
