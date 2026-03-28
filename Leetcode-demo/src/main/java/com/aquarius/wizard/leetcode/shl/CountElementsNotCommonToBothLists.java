package com.aquarius.wizard.leetcode.shl;

import java.util.HashSet;
import java.util.Set;

/**
 * Question
 *
 * You are given two lists of different lengths of positive integers. Write an algorithm to count
 * the number of elements that are not common to each list.
 *
 * Input
 *
 * The first line of the input consists of an integer listInput1_size, an integer representing the
 * number of elements in the first list (N).
 * The second line consists of N space-separated integers representing the first list of positive
 * integers.
 * The third line consists of an integer listInput2_size, representing the number of elements in
 * the second list (M).
 * The last line consists of M space-separated integers representing the second list of positive
 * integers.
 *
 * Output
 *
 * Print a positive integer representing the count of elements that are not common to both the lists
 * of integers.
 *
 * Example
 *
 * Input:
 * 11
 * 1 1 2 3 4 5 5 7 6 9 10
 * 10
 * 11 12 13 4 5 6 7 18 19 20
 *
 * Output:
 * 12
 *
 * Explanation:
 * The numbers that are not common to both lists are [1, 1, 2, 3, 9, 10, 11, 12, 13, 18, 19, 20].
 *
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：哈希集合、成员判断。
 * 校对：样例能证明这题并不是 multiset difference。只要某个值在另一边出现过，那么这个值在本边的所有出现都不算“不公共”。
 * 提示：更准确的理解是“统计两边那些值不在对方值集合中的元素个数”。
 */
public class CountElementsNotCommonToBothLists {

    public int countNotCommon(int[] first, int[] second) {
        Set<Integer> firstValues = new HashSet<>();
        Set<Integer> secondValues = new HashSet<>();
        for (int value : first) {
            firstValues.add(value);
        }
        for (int value : second) {
            secondValues.add(value);
        }
        int count = 0;
        for (int value : first) {
            if (!secondValues.contains(value)) {
                count++;
            }
        }
        for (int value : second) {
            if (!firstValues.contains(value)) {
                count++;
            }
        }
        return count;
    }
}
