package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 56
 *
 * A company sells its products at N outlets. All the outlets are connected to each other by a
 * series of roads. There is only one way to reach from one outlet to another. Each outlet of the
 * company has a unique outlet ID. Whenever the inventory of a certain product reaches a minimum
 * limit then these K outlets make a request for extra inventory. The company sends the requested
 * products from its warehouse to the outlets. In order to save on fuel, the warehouse supervisor
 * directs the driver Mike to deliver the products to the outlets along the shortest and most
 * direct path possible, without traveling any single road twice.
 *
 * Write an algorithm to help Mike deliver his inventory to the maximum number of outlets without
 * traveling any road twice.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. outletCount requestedCount
 * 2. outletCount - 1 lines: u v
 * 3. requestedCount outlet IDs
 *
 * This version assumes the warehouse is outlet 1 and Mike starts from outlet 1.
 * Because no road may be reused, the delivery route is treated as one simple path starting from
 * the warehouse.
 */
public class Q56MaximumOutletsVisitedWithoutRepeatingRoad {

    private List<Integer>[] graph;
    private boolean[] requested;
    private int best;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int outletCount = scanner.nextInt();
        int requestedCount = scanner.nextInt();
        int[][] roads = new int[Math.max(0, outletCount - 1)][2];
        for (int i = 0; i < outletCount - 1; i++) {
            roads[i][0] = scanner.nextInt();
            roads[i][1] = scanner.nextInt();
        }
        int[] requestedOutlets = new int[requestedCount];
        for (int i = 0; i < requestedCount; i++) {
            requestedOutlets[i] = scanner.nextInt();
        }

        Q56MaximumOutletsVisitedWithoutRepeatingRoad solver =
            new Q56MaximumOutletsVisitedWithoutRepeatingRoad();
        System.out.println(solver.maximumVisitedOutlets(outletCount, roads, requestedOutlets));
    }

    public int maximumVisitedOutlets(int outletCount, int[][] roads, int[] requestedOutlets) {
        graph = new ArrayList[outletCount + 1];
        requested = new boolean[outletCount + 1];
        for (int i = 1; i <= outletCount; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(road[1]);
            graph[road[1]].add(road[0]);
        }
        for (int outlet : requestedOutlets) {
            if (outlet >= 1 && outlet <= outletCount) {
                requested[outlet] = true;
            }
        }

        dfs(1, 0, requested[1] ? 1 : 0);
        return best;
    }

    private void dfs(int node, int parent, int collected) {
        best = Math.max(best, collected);
        for (int next : graph[node]) {
            if (next == parent) {
                continue;
            }
            dfs(next, node, collected + (requested[next] ? 1 : 0));
        }
    }
}
