package com.aquarius.wizard.leetcode.shl;

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
 * 我的备注
 *
 * 难度：困难。
 *
 * 考点：最大堆贪心。
 * 校对：样例已做代码校验。
 * 做法：先尽量往前走，走不动时，从已经路过的摊位里选补给量最大的一个。
 */
public class MinimumJuiceStallStops {

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
