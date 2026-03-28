package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.List;

/**
 * Question
 *
 * The assistant sales manager in the head office of a company 'Jotuway' receives the list of sales
 * data from the offices of the company in different cities. The assistant sales manager has to
 * compile the data and share the list with the sales manager. The shared list should be the
 * longest palindromic list of the sales data of different cities. He/she can sum up any two
 * consecutive elements of the list to form a single element. The result thus obtained can be reused
 * further and this process can be repeated any number of times to convert the given list into a
 * palindromic of maximum length.
 *
 * Write an algorithm to help the assistant sales manager convert the given list into a palindromic
 * list of maximum length.
 *
 * Input
 *
 * The first line of the input consists of an integer N, representing the number of elements in the
 * list.
 * The second line consists of N space-separated positive integers representing the sales data.
 *
 * Output
 *
 * Print space-separated positive integers representing the palindromic list of maximum length.
 *
 * Constraints
 *
 * 0 <= N <= 10^3
 * 1 <= S <= 10^6; where S represents the sales data from a city
 *
 * Example
 *
 * Input:
 * 6
 * 15 10 15 34 25 15
 *
 * Output:
 * 15 25 34 25 15
 *
 * 我的备注
 *
 * 难度：中等。
 *
 * 考点：双指针、贪心合并、回文构造。
 * 校对：题面完整，样例稳定。
 * 提示：目标是让“合并次数最少”，因为每合并一次，最终列表长度就减少 1。
 */
public class LongestPalindromicListByMergingAdjacentValues {

    public static void main(String[] args) {
        int listSize = 6;
        int[] salesData = {15, 10, 15, 34, 25, 15};

        if (salesData.length != listSize) {
            throw new IllegalArgumentException("salesData.length must equal listSize");
        }

        LongestPalindromicListByMergingAdjacentValues solver = new LongestPalindromicListByMergingAdjacentValues();
        System.out.println(format(solver.longestPalindromicList(salesData)));
    }

    private static String format(long[] nums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(nums[i]);
        }
        return builder.toString();
    }

    public long[] longestPalindromicList(int[] salesData) {
        List<Long> values = new ArrayList<>(salesData.length);
        for (int value : salesData) {
            values.add((long) value);
        }
        int left = 0;
        int right = values.size() - 1;
        while (left < right) {
            long leftValue = values.get(left);
            long rightValue = values.get(right);
            if (leftValue == rightValue) {
                left++;
                right--;
                continue;
            }
            if (leftValue < rightValue) {
                values.set(left + 1, leftValue + values.get(left + 1));
                values.remove(left);
                right--;
            } else {
                values.set(right - 1, values.get(right - 1) + rightValue);
                values.remove(right);
                right--;
            }
        }

        long[] answer = new long[values.size()];
        for (int i = 0; i < values.size(); i++) {
            answer[i] = values.get(i);
        }
        return answer;
    }
}
