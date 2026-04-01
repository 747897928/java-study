package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 * <p>
 * You are given a list of N unique positive numbers ranging from 0 to (N - 1). Write an algorithm
 * to replace the value of each number with its corresponding index value in the list.
 * <p>
 * Input
 * <p>
 * The first line of the input consists of an integer size, representing the size of the list (N).
 * The next line consists of N space-separated integers, arr[0], arr[1], ... arr[N-1] representing
 * the given list of numbers.
 * <p>
 * Output
 * <p>
 * Print N space-separated integers representing the list obtained by replacing the values of the
 * numbers with their corresponding index values in the list.
 * <p>
 * Constraints
 * <p>
 * 0 < size <= 10^5
 * 0 <= arr[i] <= 10^5
 * 0 <= i < size
 * <p>
 * Note
 * <p>
 * All the numbers in the list are unique.
 * <p>
 * Example
 * <p>
 * Input:
 * 4
 * 3 2 0 1
 * <p>
 * Output:
 * 2 3 1 0
 * <p>
 * Explanation:
 * Before the change, the elements of the list are:
 * arr[0]=3, arr[1]=2, arr[2]=0 and arr[3]=1
 * After the change, the elements are:
 * arr[0]=2, arr[1]=3, arr[2]=1 and arr[3]=0
 * <p>
 * 备注
 * <p>
 * 难度：简单。
 * <p>
 * 考点：数组映射、逆排列。
 * 校对：题面稳定。
 * 提示：因为值域刚好是 0..N-1 且互不重复，所以本质上是在求排列的 inverse permutation。
 */
public class ReplaceValuesWithTheirIndexPositions {

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
         * int listSize = 4;
         * int[] numbers = {3, 2, 0, 1};
         */

        ReplaceValuesWithTheirIndexPositions solver = new ReplaceValuesWithTheirIndexPositions();
        System.out.println(format(solver.replaceWithIndices(numbers)));
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

    public int[] replaceWithIndices(int[] nums) {
        int[] indices = new int[nums.length];
        for (int index = 0; index < nums.length; index++) {
            indices[nums[index]] = index;
        }
        return indices;
    }
}
