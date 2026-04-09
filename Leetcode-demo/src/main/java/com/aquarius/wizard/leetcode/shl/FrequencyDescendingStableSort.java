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
        /*
         * 先做两张表：
         *
         * 1. count[value]
         *    这个数一共出现了几次。
         *
         * 2. firstIndex[value]
         *    这个数第一次出现在原数组的哪个位置。
         *
         * 为什么要 firstIndex？
         * 因为题目要求“同频元素保持原始出现顺序”。
         * 这里的“原始出现顺序”，本质上就是谁第一次出现得更早，谁排得更靠前。
         */
        Map<Integer, Integer> count = new HashMap<>();
        Map<Integer, Integer> firstIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
            firstIndex.putIfAbsent(nums[i], i);
        }

        /*
         * values 这里不是原数组本身，而是“去重后的数字列表”。
         * 比如原数组里有很多个 5、很多个 3，
         * 到这一步 values 里只会各保留一个 5、一个 3。
         *
         * 接下来只需要把这些“不同的数字”排好顺序，
         * 后面再按频次把它们展开回结果数组。
         */
        List<Integer> values = new ArrayList<>(count.keySet());

        /*
         * 这一段 JDK Comparator 链式写法，拆开理解就不绕了：
         *
         * Comparator.comparingInt(count::get)
         * 意思是：
         * “先按 count.get(value) 这个整数来比较”
         * 也就是先按频次比较。
         *
         * count::get 是方法引用，等价于：
         * value -> count.get(value)
         *
         * 所以这一小段也可以脑补成：
         * Comparator.comparingInt(value -> count.get(value))
         *
         * .reversed()
         * 表示把刚才这个比较规则反过来。
         * 原本 comparingInt 默认是升序，
         * 加了 reversed() 之后就变成“频次大的排前面”。
         *
         * .thenComparingInt(firstIndex::get)
         * 表示：
         * 如果前面的频次比较结果相同，再按 firstIndex.get(value) 比较。
         * 也就是同频时，谁第一次出现得更早，谁排前面。
         *
         * 所以整句连起来就是：
         * 1. 先按频次降序排
         * 2. 如果频次相同，再按首次出现位置升序排
         *
         * 你也可以把它读成一个更口语化的规则：
         * “谁出现得多谁先出场；如果出现次数一样，就按原数组里第一次露面的先后顺序排。”
         */
        values.sort(Comparator
                .<Integer>comparingInt(count::get).reversed()
                .thenComparingInt(firstIndex::get));

        int[] result = new int[nums.length];
        int index = 0;

        /*
         * values 现在已经排好了“不同数字”的先后顺序，
         * 但题目最后要的是完整数组，不是去重后的列表。
         *
         * 所以这里要做的事情其实就是：
         * 把每个数字按它出现的次数，再重新铺回 result 里。
         *
         * 举个例子：
         * 如果 values 当前顺序是 [5, 3, 6, 2, 4, 1, 7, 8, 9, 10]
         * 并且 count[5] = 4，count[3] = 3，count[6] = 3 ...
         *
         * 那么这段双循环就在做：
         * - 先把 5 连续写 4 次
         * - 再把 3 连续写 3 次
         * - 再把 6 连续写 3 次
         * ...
         *
         * 最终 result 才会恢复成题目要求的完整长度。
         *
         * index++
         * 表示每写进一个位置，结果数组的写入指针就往后挪一格。
         */
        for (int value : values) {
            for (int i = 0; i < count.get(value); i++) {
                result[index++] = value;
            }
        }
        return result;
    }
}
