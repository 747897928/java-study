package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 *
 * Moche Goldberg is a traveling salesman. He works in N towns. Each day he sells his products in
 * one of the towns. The towns that are chosen on any two successive days should be different and a
 * town I can be chosen at most ci times. Write an algorithm to determine the number of days when he
 * can sell in the given towns following the above-mentioned rules.
 *
 * Input
 *
 * The first line of the input consists of an integer num, representing the number of towns (N).
 * The next line consists of N space-separated integers - countTown0, countTown1, ...,
 * countTownN-1 representing the number of times each town can be chosen.
 *
 * Output
 *
 * Print an integer representing the maximum number of days during which the salesman can work.
 *
 * Constraints
 *
 * 1 <= num <= 5 * 10^4
 * 1 <= countTown_i <= num
 * sum(countTown_i) <= 10^5
 * 0 <= i < N
 *
 * Example
 *
 * Input:
 * 3
 * 7 2 3
 *
 * Output:
 * 11
 *
 * Explanation:
 * The first, second and third towns are chosen 7, 2 and 3 times respectively.
 * The different towns are selected on successive days in a sequence:
 * first, second, first, third, first, second, first, third, first, third, first.
 * So, the maximum number of days during which a salesman can sell is 11.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：贪心结论。
 * 校对：约束来自 docx 截图公式区；最后一行截图肉眼略模糊，我按题意修正为更合理的 0 <= i < N。
 * 核心结论：若最大次数 max 大于其余次数总和加一，则答案是 2*others + 1；否则可以全部排满。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class TravelingSalesmanMaximumWorkingDays {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int townCount = scanner.nextInt();
        int[] townVisitLimits = new int[townCount];
        for (int i = 0; i < townCount; i++) {
            townVisitLimits[i] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int townCount = 3;
         * int[] townVisitLimits = {7, 2, 3};
         */

        TravelingSalesmanMaximumWorkingDays solver = new TravelingSalesmanMaximumWorkingDays();
        System.out.println(solver.maxWorkingDays(townVisitLimits));
    }

    public long maxWorkingDays(int[] townCounts) {
        long total = 0L;
        int max = 0;
        for (int count : townCounts) {
            total += count;
            max = Math.max(max, count);
        }
        long others = total - max;
        return max > others + 1 ? others * 2 + 1 : total;
    }
}
