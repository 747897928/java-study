package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
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
 * 备注
 *
 * 难度：简单。
 *
 * 考点：哈希集合、成员判断。
 * 校对：样例能证明这题并不是 multiset difference。只要某个值在另一边出现过，那么这个值在本边的所有出现都不算“不公共”。
 * 提示：更准确的理解是“统计两边那些值不在对方值集合中的元素个数”。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class CountElementsNotCommonToBothLists {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int firstListSize = scanner.nextInt();
        int[] firstList = new int[firstListSize];
        for (int i = 0; i < firstListSize; i++) {
            firstList[i] = scanner.nextInt();
        }
        int secondListSize = scanner.nextInt();
        int[] secondList = new int[secondListSize];
        for (int i = 0; i < secondListSize; i++) {
            secondList[i] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int firstListSize = 11;
         * int[] firstList = {1, 1, 2, 3, 4, 5, 5, 7, 6, 9, 10};
         * int secondListSize = 10;
         * int[] secondList = {11, 12, 13, 4, 5, 6, 7, 18, 19, 20};
         */

        CountElementsNotCommonToBothLists solver = new CountElementsNotCommonToBothLists();
        System.out.println(solver.countNotCommon(firstList, secondList));
    }

    /**
     * 这题最容易误读的地方是“not common”。
     *
     * 它不是在做 multiset difference，
     * 也不是做“去重后的对称差元素个数”。
     *
     * 按样例反推，正确理解是：
     *
     * - 先看一个元素的“值”是否在另一边出现过
     * - 如果这个值根本没在另一边出现，那这个元素就算“不公共”
     * - 如果这个值在另一边出现过，那么本边所有这个值都不算“不公共”
     *
     * 所以这题最自然的做法就是：
     *
     * 1. 先各自把“出现过哪些值”存进 HashSet
     * 2. 再扫原数组，逐个元素判断“它的值是否在对方集合里”
     *
     * 注意这里虽然用了 Set，
     * 但最终计数时仍然是扫原数组，
     * 所以重复元素会按出现次数计入答案。
     */
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
            // 这个元素的值如果从没在 second 出现过，
            // 那它就是一个“不公共元素”。
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
