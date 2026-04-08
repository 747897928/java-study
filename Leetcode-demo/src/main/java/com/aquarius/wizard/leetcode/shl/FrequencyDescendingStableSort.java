package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Question
 *
 * Design a way to sort the list of positive integers in the descending order according to frequency
 * of the elements. The elements with higher frequency come before those with lower frequency.
 * Elements with the same frequency come in the same order as they appear in the given list.
 *
 * Input
 *
 * The first line of the input consists of an integer num, representing the number of elements in
 * the list (N).
 * The second line consists of N space-separated integers representing the elements in the list.
 *
 * Output
 *
 * Print N space-separated integers representing the elements of the list sorted according to the
 * frequency of elements present in the given list.
 *
 * Example
 *
 * Input:
 * 19
 * 1 2 2 3 3 3 4 4 5 5 5 5 6 6 6 7 8 9 10
 *
 * Output:
 * 5 5 5 5 3 3 3 6 6 6 2 2 4 4 1 7 8 9 10
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：频次统计、稳定排序。
 * 校对：题意稳定。
 * 提示：同频元素保持原始出现顺序，不能再按数值大小排。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class FrequencyDescendingStableSort {

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
         * int listSize = 19;
         * int[] numbers = {1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7, 8, 9, 10};
         */

        FrequencyDescendingStableSort solver = new FrequencyDescendingStableSort();
        System.out.println(format(solver.sortByFrequency(numbers)));
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

    public int[] sortByFrequency(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        Map<Integer, Integer> firstIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
            firstIndex.putIfAbsent(nums[i], i);
        }

        List<Integer> values = new ArrayList<>(count.keySet());
        values.sort(Comparator
                .<Integer>comparingInt(count::get).reversed()
                .thenComparingInt(firstIndex::get));

        int[] result = new int[nums.length];
        int index = 0;
        for (int value : values) {
            for (int i = 0; i < count.get(value); i++) {
                result[index++] = value;
            }
        }
        return result;
    }
}
