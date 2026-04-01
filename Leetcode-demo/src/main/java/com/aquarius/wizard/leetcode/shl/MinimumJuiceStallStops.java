package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Question
 *
 * John misses his bus and has to walk all his way from home to school. The distance between his
 * school and home is D units. He starts his journey with an initial energy of K units. His energy
 * decreases by 1 unit for every unit of distance walked. On his way to school, there are N juice
 * stalls. Each stall has a specific amount of juice in liters. His energy increases by 1 unit for
 * every liter of juice he consumes. Note that in order to keep him walking he should have non-zero
 * energy.
 *
 * Write an algorithm to help John figure out the minimum number of juice stalls at which he should
 * stop to successfully reach the school. In case he can't reach the school, the output will be -1.
 *
 * Input
 *
 * The first line of the input consists of an integer N, representing the number of juice stalls.
 * The second line consists of N space-separated integers - dist1, dist2, ..., distn representing
 * the distance of the i-th stall from John's home.
 * The third line consists of N space-separated integers - lit1, lit2, ..., litn representing the
 * liters of juice available at the i-th stall.
 * The last line consists of two space-separated integers - D and K representing the distance of the
 * school from John's home and his initial energy, respectively.
 *
 * Output
 *
 * Print an integer representing the minimum number of juice stalls at which John should stop to
 * reach the school successfully.
 *
 * Example
 *
 * Input:
 * 4
 * 5 7 8 10
 * 2 3 1 5
 * 15 5
 *
 * Output:
 * 3
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：最大堆贪心。
 * 校对：样例已做代码校验。
 * 做法：先尽量往前走，走不动时，从已经路过的摊位里选补给量最大的一个。
 * 相似题：LeetCode 871 Minimum Number of Refueling Stops。
 *
 * 这题真正难的地方不是堆，而是“贪心时机”。
 *
 * 很多人第一次会想：
 *
 * “我应该提前决定在哪几个摊位停下。”
 *
 * 这个想法很容易把自己绕进状态设计。
 *
 * 更好的想法是：
 *
 * “先一路往前走，等到真的走不动了，再回头问：
 *  我之前路过的摊位里，补哪一个最划算？”
 *
 * 答案显然是：
 *
 * 补给量最大的那个。
 *
 * 所以这题的核心反应应该是：
 *
 * 1. 已经过了的补给站，先放进最大堆
 * 2. 真正缺油/缺能量的时候，再从堆里拿最大的补
 *
 * 这类题的本质是“延迟决策”：
 *
 * 不是先决定停哪里，
 * 而是把所有可选补给先缓存起来，等真的不够时再做最优选择。
 */
public class MinimumJuiceStallStops {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int stallCount = scanner.nextInt();
        int[] stallDistances = new int[stallCount];
        for (int i = 0; i < stallCount; i++) {
            stallDistances[i] = scanner.nextInt();
        }
        int[] juiceLiters = new int[stallCount];
        for (int i = 0; i < stallCount; i++) {
            juiceLiters[i] = scanner.nextInt();
        }
        int destinationDistance = scanner.nextInt();
        int initialEnergy = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int stallCount = 4;
         * int[] stallDistances = {5, 7, 8, 10};
         * int[] juiceLiters = {2, 3, 1, 5};
         * int destinationDistance = 15;
         * int initialEnergy = 5;
         */

        MinimumJuiceStallStops solver = new MinimumJuiceStallStops();
        System.out.println(solver.minStops(stallDistances, juiceLiters, destinationDistance, initialEnergy));
    }

    /**
     * 这段代码的正确脑补方式是：
     *
     * John 从 previousDistance 走到 currentDistance，
     * 这段路需要 needed 单位能量。
     *
     * 如果当前 energy 不够：
     *
     * 就说明必须从“之前已经路过的摊位”里补能量。
     *
     * 为什么一定从最大堆里取最大的：
     *
     * 因为题目要的是“最少停几次”，不是“剩余能量最平滑”。
     * 每停一次都很贵，所以每次停下时都应该尽量补最多。
     *
     * 这也是为什么这里用 while (energy < needed)：
     *
     * 说明我们不是“每到一个摊位就立即决定喝不喝”，
     * 而是等到真的走不到下一个位置时，才回头从历史可选项里拿最好的。
     */
    public int minStops(int[] distances, int[] liters, int destination, int initialEnergy) {
        int n = distances.length;
        int previousDistance = 0;
        int energy = initialEnergy;
        int stops = 0;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i <= n; i++) {
            int currentDistance = i == n ? destination : distances[i];
            int needed = currentDistance - previousDistance;
            while (energy < needed && !maxHeap.isEmpty()) {
                energy += maxHeap.poll();
                stops++;
            }
            if (energy < needed) {
                return -1;
            }
            energy -= needed;
            previousDistance = currentDistance;
            if (i < n) {
                maxHeap.offer(liters[i]);
            }
        }
        return stops;
    }
}
