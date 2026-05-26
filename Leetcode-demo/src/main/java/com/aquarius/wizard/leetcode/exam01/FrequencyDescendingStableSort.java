package com.aquarius.wizard.leetcode.exam01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * Input
 * The first line of the input consists of an integer num, representing the number of elements in the list(N).
 * The second line consists of N space-separated integers representing the elements in the list.
 *
 * Output
 * Print N space-separated integers representing the elements of the list sorted according to the frequency of elements present in the given list.
 *
 * Example
 * Input:
 * 19
 * 1 2 2 3 3 3 4 4 5 5 5 5 6 6 6 7 8 9 10
 *
 * Output:
 * 5 5 5 5 3 3 3 6 6 6 2 2 4 4 1 7 8 9 10
 *
 *
 * Case 1
 * Input:
 * ```
 * 10
 * 20 40 26 25 40 20 40 20 40 25
 * ```
 * Expected Output:
 * ```
 * 40 40 40 40 20 20 20 25 25 26
 * ```
 *
 * Case 2
 * Input:
 * ```
 * 6
 * 4 1 2 3 4 5
 * ```
 * Expected Output:
 * ```
 * 4 4 1 2 3 5
 * ```
 *
 * Sort positive integers by descending frequency.
 * If two values have the same frequency, keep the order of their first appearance.
 */
public class FrequencyDescendingStableSort {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int[] nums = new int[num];
        for (int i = 0; i < num; i++) {
            nums[i] = scanner.nextInt();
        }

        System.out.println(format(sortByFrequency(nums)));
    }

    public static int[] sortByFrequency(int[] nums) {
        Map<Integer, Integer> frequency = new HashMap<>();
        Map<Integer, Integer> firstIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int value = nums[i];
            frequency.put(value, frequency.getOrDefault(value, 0) + 1);
            firstIndex.putIfAbsent(value, i);
        }

        List<Integer> distinctValues = new ArrayList<>(frequency.keySet());
        distinctValues.sort(Comparator
                .<Integer>comparingInt(frequency::get).reversed()
                .thenComparingInt(firstIndex::get));

        int[] result = new int[nums.length];
        int index = 0;
        for (int value : distinctValues) {
            int count = frequency.get(value);
            for (int i = 0; i < count; i++) {
                result[index++] = value;
            }
        }
        return result;
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
}
