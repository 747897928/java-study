package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Question
 *
 * An alternate sort of a list consists of alternate elements (starting from the first position) of
 * the given list after sorting it in an ascending order. You are given a list of unsorted elements.
 * Write an algorithm to find the alternate sort of the given list.
 *
 * Input
 *
 * The first line of the input consists of an integer size, representing the size of the given list
 * (N).
 * The second line consists of N space-separated integers arr[0], arr[1], ..., arr[N-1],
 * representing the elements of the input list.
 *
 * Output
 *
 * Print space-separated integers representing the alternately sorted elements of the given list.
 *
 * Constraints
 *
 * 0 < size <= 10^6
 * -10^6 <= arr[i] <= 10^6
 * 0 <= i < size
 *
 * Example
 *
 * Input:
 * 8
 * 3 5 1 5 9 10 2 6
 *
 * Output:
 * 1 3 5 9
 *
 * Explanation:
 * After sorting, the list is [1, 2, 3, 5, 5, 6, 9, 10].
 * So, the alternate elements of the sorted list are [1, 3, 5, 9].
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：排序后按间隔取值。
 * 校对：题面稳定，示例无歧义。
 * 提示：这题不是交替输出最小最大，而是排序后取下标 0,2,4,... 的元素。
 *
 * positive积极的，正，正面，肯定的。
 * positive integer正整数
 *
 * alternate(在这里指隔一个取一个)
 * adj.轮流的，交替的；间隔的；供选择的，备用的；（叶，芽）互生的；另类的
 * v.（使）交替，（使）轮流
 * n.替补者，候补者
 *
 * ascending
 * adj.上升的，增长的；升（序）的
 * v.上升；攀登（ascend 的现在分词）
 *
 * representing
 * v.代表；为……代言（辩护）；等于，相当于；（符号或象征）代表，表示（represent 的现在分词）
 *
 * consists v.由……构成；由……组成（consist 的三单形式）
 * consists of 包含；由……组成；充斥着
 */
public class AlternateSortedElements {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int listSize = scanner.nextInt();
        int[] numbers = new int[listSize];
        for (int i = 0; i < listSize; i++) {
            numbers[i] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int listSize = 8;
         * int[] numbers = {3, 5, 1, 5, 9, 10, 2, 6};
         */

        System.out.println(format(alternateSort(numbers)));
        /*
         * 如果需要核对另一种写法，可以临时打开下面这行：
         * System.out.println(format(alternateSort2(numbers)));
         */
    }

    private static String format(int[] nums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(nums[i]);
        }
        return builder.toString();
    }

    public static int[] alternateSort(int[] nums) {
        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);
        int[] answer = new int[(sorted.length + 1) / 2];
        for (int i = 0, index = 0; i < sorted.length; i += 2) {
            answer[index++] = sorted[i];
        }
        return answer;
    }

    public static int[] alternateSort2(int[] nums) {
        int[] copyNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copyNums);
        int[] result = new int[(copyNums.length + 1) / 2];
        for (int i = 0; i < copyNums.length; i++) {
            if (i % 2 == 0) {
                result[i / 2] = copyNums[i];
            }
        }
        return result;
    }
}
