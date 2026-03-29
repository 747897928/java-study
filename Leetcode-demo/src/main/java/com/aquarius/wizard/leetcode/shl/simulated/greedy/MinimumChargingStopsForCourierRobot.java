package com.aquarius.wizard.leetcode.shl.simulated.greedy;

import java.util.PriorityQueue;

/**
 * Simulated Problem
 *
 * Difficulty
 *
 * Medium
 *
 * Question
 *
 * A courier company uses a robot to deliver medical packages between towns. The robot must travel
 * exactly D kilometers to reach the destination. Initially, the robot has C units of charge, and
 * each kilometer consumes exactly one unit of charge.
 *
 * Along the route, there are N charging stations. The ith station is located at distance
 * stationPos[i] from the starting point and can provide stationCharge[i] units of charge.
 * When the robot reaches a station, it may choose to stop there and take all the charge from that
 * station.
 *
 * Write an algorithm to find the minimum number of charging stops required for the robot to reach
 * the destination. If it is impossible, print -1.
 *
 * Input
 *
 * The first line of the input consists of an integer targetDistance, representing D.
 * The second line consists of an integer initialCharge, representing C.
 * The third line consists of an integer stationCount, representing N.
 * The next N lines each consist of two space-separated integers stationPos and stationCharge,
 * representing the position of a charging station and the charge it provides, respectively.
 *
 * Output
 *
 * Print an integer representing the minimum number of charging stops required. Print -1 if the
 * robot cannot reach the destination.
 *
 * Constraints
 *
 * 1 <= targetDistance <= 10^9
 * 0 <= initialCharge <= 10^9
 * 0 <= stationCount <= 2 * 10^5
 * 0 < stationPos[i] < targetDistance
 * 1 <= stationCharge[i] <= 10^9
 * stationPos is given in strictly increasing order
 *
 * Example
 *
 * Input:
 * 100
 * 10
 * 4
 * 10 60
 * 20 30
 * 30 30
 * 60 40
 *
 * Output:
 * 2
 *
 * Explanation:
 *
 * The robot first uses the initial charge to reach the station at distance 10.
 * It takes 60 units of charge there.
 * Later, it reaches the station at distance 60 and takes 40 units of charge.
 * Thus, the robot reaches the destination with a minimum of 2 charging stops.
 *
 * 我的备注
 *
 * 这是我补的模拟题，不是原 SHL 题。
 *
 * 相似题：
 * 1. MinimumJuiceStallStops
 * 2. LeetCode 871
 *
 * 学习重点：
 *
 * 这题的本质不是“每到一个站就决定停不停”，
 * 而是“当你发现走不动时，回头从已经路过的站里挑一个最赚的补给站”。
 *
 * 所以核心数据结构是大根堆。
 */
public class MinimumChargingStopsForCourierRobot {

    public static void main(String[] args) {
        int targetDistance = 100;
        int initialCharge = 10;
        int[] stationPosition = {10, 20, 30, 60};
        int[] stationCharge = {60, 30, 30, 40};

        MinimumChargingStopsForCourierRobot solver = new MinimumChargingStopsForCourierRobot();
        System.out.println(solver.minimumStops(targetDistance, initialCharge, stationPosition, stationCharge));
    }

    /*
     * Practice Mode
     *
     * Try to solve this problem yourself before reading the method below.
     * Think about:
     *
     * 1. Why local greedy "stop whenever you can" is not enough
     * 2. Why a max heap is the right structure
     * 3. What exactly the heap stores
     */

    public int minimumStops(int targetDistance, int initialCharge, int[] stationPosition, int[] stationCharge) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        long reachable = initialCharge;
        int stationIndex = 0;
        int stops = 0;

        while (reachable < targetDistance) {
            while (stationIndex < stationPosition.length && stationPosition[stationIndex] <= reachable) {
                maxHeap.offer(stationCharge[stationIndex]);
                stationIndex++;
            }

            if (maxHeap.isEmpty()) {
                return -1;
            }

            reachable += maxHeap.poll();
            stops++;
        }

        return stops;
    }
}

