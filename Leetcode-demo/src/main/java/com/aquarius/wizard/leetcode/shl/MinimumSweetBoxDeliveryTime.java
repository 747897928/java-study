package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Question
 *
 * William is the owner of a sweet shop. His current machine takes some time to prepare one box of
 * sweets. He receives an order for a fixed number of boxes and wants to finish the order as soon
 * as possible within a limited budget.
 *
 * To fulfill the order, William may:
 * 1. continue using his old machine, or
 * 2. buy exactly one new machine and use it instead of the old one, and
 * 3. buy ready-made sweet boxes from shops.
 *
 * Boxes bought from shops are available instantly. Each shop offer can be used at most once. If
 * William buys a new machine, he cannot use the old machine.
 *
 * Write an algorithm to find the minimum time in which William can complete the order.
 *
 * Input
 *
 * The first line of the input consists of three space-separated integers - numOfBox, prepTime and
 * money, representing the number of boxes William has to deliver, the time required to prepare one
 * box using William's current machine, and the total budget, respectively.
 * The second line consists of two space-separated integers - M and S, representing the number of
 * machines available for purchase and the number of shop offers available, respectively.
 * The next M lines consist of two space-separated integers - mTime and mCost, representing the time
 * taken by that machine to create one box and the cost of buying that machine, respectively.
 * The last S lines consist of two space-separated integers - sNum and sCost, representing the
 * number of boxes available in that shop offer and the cost to buy them, respectively.
 *
 * Output
 *
 * Print an integer representing the minimum time in which William can complete the order.
 *
 * Constraints
 *
 * 1 <= numOfBox, prepTime <= 10^9
 * 1 <= money <= 10^5
 * 1 <= M, S <= 10^3
 * 1 <= mTime, mCost, sNum, sCost <= 10^5
 *
 * Note
 *
 * Boxes purchased from shops are obtained instantly.
 * Each shop offer can be used at most once.
 * William may choose not to buy any machine and continue using the old one.
 * William may buy at most one new machine.
 *
 * Example
 *
 * Input:
 * 20 10 20
 * 3 2
 * 2 30
 * 3 25
 * 4 10
 * 5 10
 * 15 80
 *
 * Explanation:
 * With a budget of 20, William cannot afford the first two machines and cannot afford the second
 * shop offer. He may buy the third machine for 10 and the first shop offer for 10, receiving 5
 * boxes instantly. He then produces the remaining 15 boxes using the new machine in 15 * 4 = 60
 * units of time. So the minimum time is 60.
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：预算优化、枚举机器、0/1 背包。
 * 校对：原 docx 样例尾部缺失，而且“最小时间对 1000007 取模”这句逻辑上可疑，所以这里明确改写为与当前实现一致的学习版定稿，不再保留那条规则。
 * 提示：当前学习版把每个 shop offer 视为最多使用一次；机器方案在“旧机器或某一台新机器”之间二选一。
 */
public class MinimumSweetBoxDeliveryTime {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int boxCount = scanner.nextInt();
        int oldMachineTime = scanner.nextInt();
        int budget = scanner.nextInt();
        int machineOptionCount = scanner.nextInt();
        int shopOfferCount = scanner.nextInt();
        int[][] machineOptions = new int[machineOptionCount][2];
        for (int i = 0; i < machineOptionCount; i++) {
            for (int j = 0; j < 2; j++) {
                machineOptions[i][j] = scanner.nextInt();
            }
        }
        int[][] shopOffers = new int[shopOfferCount][2];
        for (int i = 0; i < shopOfferCount; i++) {
            for (int j = 0; j < 2; j++) {
                shopOffers[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int boxCount = 20;
         * int oldMachineTime = 10;
         * int budget = 20;
         * int machineOptionCount = 3;
         * int shopOfferCount = 2;
         * int[][] machineOptions = {{2, 30}, {3, 25}, {4, 10}};
         * int[][] shopOffers = {{5, 10}, {15, 80}};
         */

        MinimumSweetBoxDeliveryTime solver = new MinimumSweetBoxDeliveryTime();
        System.out.println(solver.minimumTime(boxCount, oldMachineTime, budget, machineOptions, shopOffers));
    }

    public long minimumTime(int boxCount, int oldMachineTime, int budget, int[][] machineOptions, int[][] shopOffers) {
        long best = (long) boxCount * oldMachineTime;
        List<int[]> affordableMachines = new ArrayList<>();
        affordableMachines.add(new int[]{oldMachineTime, 0});
        for (int[] machine : machineOptions) {
            if (machine[1] <= budget) {
                affordableMachines.add(machine);
            }
        }

        for (int[] machine : affordableMachines) {
            int machineTime = machine[0];
            int machineCost = machine[1];
            int remainingBudget = budget - machineCost;
            int boughtBoxes = maximumBoxesFromShops(remainingBudget, shopOffers);
            int remainingBoxes = Math.max(0, boxCount - boughtBoxes);
            best = Math.min(best, (long) remainingBoxes * machineTime);
        }
        return best;
    }

    private int maximumBoxesFromShops(int budget, int[][] shopOffers) {
        int[] dp = new int[budget + 1];
        for (int[] offer : shopOffers) {
            int boxes = offer[0];
            int cost = offer[1];
            for (int money = budget; money >= cost; money--) {
                dp[money] = Math.max(dp[money], dp[money - cost] + boxes);
            }
        }
        int best = 0;
        for (int value : dp) {
            best = Math.max(best, value);
        }
        return best;
    }
}
