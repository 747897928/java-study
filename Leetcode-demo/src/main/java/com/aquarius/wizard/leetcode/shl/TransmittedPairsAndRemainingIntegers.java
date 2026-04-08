package com.aquarius.wizard.leetcode.shl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Simulated Problem
 * <p>
 * Difficulty
 * <p>
 * Easy
 * <p>
 * Question
 * <p>
 * A company is transmitting a message containing N positive integers to a new server in a secure
 * manner. They have identified a procedure for transmission where, each time, a pair of identical
 * integers is selected from the message and transmitted. This process continues until all pairs are
 * sent. The procedure will then indicate the number of pairs transmitted and the number of
 * remaining integers that were not paired and transmitted to the new server.
 * <p>
 * Write an algorithm to find the number of pairs transmitted and the number of integers remaining
 * after transmitting the pairs to the new server.
 * <p>
 * Input
 * <p>
 * The first line of input consists of an integer message_size, representing the total number of
 * integers in the message (N).
 * The second line consists of N space-separated integers, representing the integers of the message.
 * <p>
 * Output
 * <p>
 * Print two space-separated integers representing the number of pairs transmitted and the number of
 * integers remaining after transmitting the pairs to the new server.
 * <p>
 * Constraints
 * <p>
 * 1 <= message_size <= 100
 * Each integer in the message is positive
 * The pair size is always equal to 2
 * <p>
 * Example
 * <p>
 * Input:
 * 8
 * 12 10 6 12 10 12 1 21
 * <p>
 * Output:
 * 2 4
 * <p>
 * Explanation:
 * <p>
 * The frequencies are:
 * 12 -> 3
 * 10 -> 2
 * 6  -> 1
 * 1  -> 1
 * 21 -> 1
 * <p>
 * So:
 * - one pair of 12 can be transmitted
 * - one pair of 10 can be transmitted
 * <p>
 * Total transmitted pairs = 2.
 * Remaining integers count = 1 + 0 + 1 + 1 + 1 = 4.
 * <p>
 * 备注
 * <p>
 * 这是你刚刷到的模拟题，不是原 SHL 题。
 * <p>
 * 这里顺手记一下：
 * 如果Question解释还想写“剩余数组具体是什么”，原截图里的解释很可能写错了，或者至少不严谨。
 * <p>
 * 为什么？
 * <p>
 * 因为这题真正要求输出的是：
 * 1. pair 的数量
 * 2. remaining 的数量
 * <p>
 * 它根本没有要求输出“剩下的数组顺序”。
 * <p>
 * 对样例：
 * [12, 10, 6, 12, 10, 12, 1, 21]
 * <p>
 * 只要统计频次，就能唯一确定：
 * <p>
 * pairs = 2
 * remainingCount = 4
 * <p>
 * 但是如果你硬要问“剩下的 4 个数按什么顺序排”，
 * Question并没有把“优先删哪两个 12、是否保持原顺序”定义清楚。
 * <p>
 * 如果按“删除最早能凑成的一对，并保持原相对顺序”去理解，
 * 一个自然的剩余序列会是：
 * <p>
 * [6, 12, 1, 21]
 * <p>
 * 所以你感觉“更像 6 12 1 21，而不是别的奇怪顺序”，这个判断是合理的。
 * <p>
 * 但因为Question输出不要求这个序列，
 * 所以我们真正该做的是“只算数量，不碰重建顺序”。
 * <p>
 * 学习重点：
 * <p>
 * 1. 先分清Question要的是“数量”还是“具体方案”
 * 2. 如果只要数量，频次统计往往就够了
 * 3. 不要被样例解释里多余又含糊的“剩余数组”带偏
 *
 * <p>create: 2026-03-29 23:32:14</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class TransmittedPairsAndRemainingIntegers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int messageSize = scanner.nextInt();
        int[] message = new int[messageSize];
        for (int i = 0; i < messageSize; i++) {
            message[i] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int messageSize = 8;
         * int[] message = {12, 10, 6, 12, 10, 12, 1, 21};
         */

        TransmittedPairsAndRemainingIntegers solver = new TransmittedPairsAndRemainingIntegers();
        int[] answer = solver.countPairsAndRemaining(message);
        System.out.println(answer[0] + " " + answer[1]);
        /*
         * 如果需要核对其他写法，可以临时打开下面这几行：
         * System.out.println(solver.countPairsAndRemaining2(message));
         * System.out.println(solver.countPairsAndRemaining3(message));
         * System.out.println(solver.countPairsAndRemaining4(message));
         */
    }

    /**
     * 平台/OA 风格输入版本。
     * <p>
     * Question模板就是 Scanner。
     * 所以这里单独留了一个 Scanner 版本，临时切回提交模式时也能直接用。
     * <p>
     * 输入格式：
     * 第一行：messageSize
     * 第二行：N 个整数
     * <p>
     * 例如：
     * <p>
     * 8
     * 12 10 6 12 10 12 1 21
     */
    public void runWithScannerInput() {
        Scanner scanner = new Scanner(System.in);
        int messageSize = scanner.nextInt();
        int[] message = new int[messageSize];

        /*
         * 这里不要在 nextInt() 后面立刻接 nextLine()。
         *
         * 因为 nextInt() 只会读走那个整数本身，
         * 不会把这一行末尾的换行符一起吃掉。
         *
         * 所以如果马上 nextLine()，
         * 很容易先读到一个空字符串。
         *
         * 这题既然输入本身就是整数序列，
         * 最稳的写法就是继续 nextInt() 读满 N 个数。
         */
        for (int i = 0; i < messageSize; i++) {
            message[i] = scanner.nextInt();
        }

        int[] answer = countPairsAndRemaining(message);
        System.out.println(answer[0] + " " + answer[1]);
    }

    /**
     * 这题只需要统计每个数出现了多少次。
     * <p>
     * 如果某个数出现 frequency 次：
     * <p>
     * - 可以组成的 pair 数量 = frequency / 2
     * - 最后剩下的单个数量 = frequency % 2
     * <p>
     * 例如：
     * <p>
     * 12 出现 3 次：
     * 3 / 2 = 1    -> 说明可以组成 1 对
     * 3 % 2 = 1    -> 说明最后还剩 1 个
     * <p>
     * 所以整题本质就是“频次表 + 除 2 / 取余”。
     */
    public int[] countPairsAndRemaining(int[] message) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int value : message) {
            frequency.put(value, frequency.getOrDefault(value, 0) + 1);
        }

        int pairs = 0;
        int remaining = 0;
        for (int count : frequency.values()) {
            pairs += count / 2;
            remaining += count % 2;
        }
        return new int[]{pairs, remaining};
    }

    /**
     * 这是按你截图里的手写思路补完的版本：
     * <p>
     * 1. 先用 HashMap 统计频次
     * 2. 再遍历频次表，累计 pairs 和 remaining
     * <p>
     * 和上面的推荐版本质一样，
     * 只是这里保留成“你当时手写代码的思路延续版”。
     */
    public String countPairsAndRemaining2(int[] message) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int value : message) {
            if (map.containsKey(value)) {
                map.put(value, map.get(value) + 1);
            } else {
                map.put(value, 1);
            }
        }

        int pairs = 0;
        int remaining = 0;
        for (int count : map.values()) {
            pairs += count / 2;
            remaining += count % 2;
        }

        return pairs + " " + remaining;
    }

    public String countPairsAndRemaining3(int[] message) {
        Map<Integer, Integer> map = new HashMap<>();
        int pairsCount = 0;
        for (int value : message) {
            if (map.containsKey(value)) {
                pairsCount++;
                map.remove(value);
            } else {
                map.put(value, 1);
            }
        }

        return pairsCount + " " + (message.length - pairsCount * 2);
    }

    /**
     * 这是 countPairsAndRemaining3 的优化版。
     * <p>
     * 你第 3 版的核心思想其实已经对了：
     * <p>
     * 它不是在真正统计“频次”，
     * 而是在维护“哪些数当前出现了奇数次”。
     * <p>
     * 所以这里用 Set 比 Map 更贴切。
     * <p>
     * 规则非常简单：
     * <p>
     * 1. 如果 oddValues 里还没有这个数，说明它目前出现了奇数次前的那一步，
     * 现在把它放进去，表示“它现在出现了奇数次”
     * <p>
     * 2. 如果 oddValues 里已经有这个数，说明之前已经有一个同样的数在等配对，
     * 现在正好可以配成一对：
     * - pairCount++
     * - 把这个值从 oddValues 里删掉
     * <p>
     * 为什么删掉？
     * <p>
     * 因为：
     * <p>
     * - 出现 1 次：奇数次，留在 Set 里
     * - 出现 2 次：偶数次，可以配掉，从 Set 里删掉
     * - 出现 3 次：又变成奇数次，再放回 Set
     * - 出现 4 次：又变成偶数次，再删掉
     * <p>
     * 所以 Set 里最后留下来的，
     * 正好就是“出现了奇数次、最终还剩 1 个没配对的值”。
     * <p>
     * 对这题来说，pair size 固定是 2，
     * 所以“维护奇偶性”就够了。
     * <p>
     * 但如果以后Question变成：
     * <p>
     * - 每 3 个相同数组成一组
     * - 每 k 个相同数组成一组
     * <p>
     * 那么只维护奇偶性就不够了，
     * 那时还是要回到真正的频次统计。
     */
    public String countPairsAndRemaining4(int[] message) {
        Set<Integer> oddValues = new HashSet<>();
        int pairCount = 0;

        for (int value : message) {
            if (oddValues.contains(value)) {
                pairCount++;
                oddValues.remove(value);
            } else {
                oddValues.add(value);
            }
        }

        int remainingCount = oddValues.size();
        return pairCount + " " + remainingCount;
    }
}
