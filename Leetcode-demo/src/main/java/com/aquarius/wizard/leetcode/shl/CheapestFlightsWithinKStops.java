package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 787
 * Cheapest Flights Within K Stops
 *
 * 原题链接：
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/description/?envType=problem-list-v2&envId=dynamic-programming
 *
 * 题意（按原题补全）：
 * 有 n 个城市，若干条航班 flights，
 * 其中 flights[i] = [fromi, toi, pricei] 表示从 fromi 飞到 toi 的票价是 pricei。
 * 现在给定出发城市 src、目的城市 dst 和最多允许的中转次数 k，
 * 返回从 src 到 dst 的最便宜价格。
 * 如果不存在满足要求的路线，就返回 -1。
 *
 * 示例 1：
 * Input: n = 4, flights = [[0,1,100],[1,2,100],[2,3,100],[0,2,500]], src = 0, dst = 3, k = 1
 * Output: 600
 *
 * 示例 2：
 * Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
 * Output: 200
 *
 * 示例 3：
 * Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 0
 * Output: 500
 *
 * 约束：
 * 1 <= n <= 100
 * 0 <= flights.length <= n * (n - 1) / 2
 * flights[i].length == 3
 * 0 <= fromi, toi < n
 * fromi != toi
 * 1 <= pricei <= 10^4
 * 0 <= src, dst, k < n
 * src != dst
 *
 * 笔记
 *
 * 这题最容易卡住的点是：
 * 普通最短路只看“到某点的最小费用”不够，
 * 因为这里还多了一个限制：中转次数不能超过 k。
 *
 * 所以状态必须至少带上：
 *
 * - 当前在哪个城市
 * - 已经用了多少条边 / 中转了多少次
 *
 * 常见两种写法：
 *
 * 1. Bellman-Ford / 分层 DP
 *    每轮只允许多用一条边
 *
 * 2. 带状态的最短路
 *    把 (城市, 已用边数) 当成新状态
 *
 * 正式主解我这里用 Bellman-Ford 版，
 * 因为它最贴近“最多只能走 k + 1 条边”这个限制。
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class CheapestFlightsWithinKStops {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] flights = new int[m][3];
        for (int i = 0; i < m; i++) {
            flights[i][0] = scanner.nextInt();
            flights[i][1] = scanner.nextInt();
            flights[i][2] = scanner.nextInt();
        }
        int src = scanner.nextInt();
        int dst = scanner.nextInt();
        int k = scanner.nextInt();

        CheapestFlightsWithinKStops solver = new CheapestFlightsWithinKStops();
        System.out.println(solver.findCheapestPrice(n, flights, src, dst, k));
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        return findCheapestPriceBellmanFord(n, flights, src, dst, k);
    }

    public int findCheapestPriceBellmanFord(int n, int[][] flights, int src, int dst, int k) {
        final int inf = Integer.MAX_VALUE / 4;
        int[] cost = new int[n];
        Arrays.fill(cost, inf);
        cost[src] = 0;

        for (int edgesUsed = 0; edgesUsed <= k; edgesUsed++) {
            int[] next = Arrays.copyOf(cost, n);
            for (int[] flight : flights) {
                int from = flight[0];
                int to = flight[1];
                int price = flight[2];
                if (cost[from] != inf) {
                    next[to] = Math.min(next[to], cost[from] + price);
                }
            }
            cost = next;
        }

        return cost[dst] == inf ? -1 : cost[dst];
    }

    public int findCheapestPriceStateDijkstra(int n, int[][] flights, int src, int dst, int k) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<int[]>();
        }
        for (int[] flight : flights) {
            graph[flight[0]].add(new int[] {flight[1], flight[2]});
        }

        final int inf = Integer.MAX_VALUE / 4;
        int[][] dist = new int[k + 2][n];
        for (int i = 0; i < dist.length; i++) {
            Arrays.fill(dist[i], inf);
        }
        dist[0][src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> Integer.compare(a[0], b[0]));
        pq.offer(new int[] {0, src, 0});

        while (!pq.isEmpty()) {
            int[] state = pq.poll();
            int cost = state[0];
            int city = state[1];
            int edgesUsed = state[2];

            if (cost != dist[edgesUsed][city]) {
                continue;
            }
            if (city == dst) {
                return cost;
            }
            if (edgesUsed == k + 1) {
                continue;
            }

            for (int[] edge : graph[city]) {
                int nextCity = edge[0];
                int nextCost = cost + edge[1];
                int nextEdges = edgesUsed + 1;
                if (nextCost < dist[nextEdges][nextCity]) {
                    dist[nextEdges][nextCity] = nextCost;
                    pq.offer(new int[] {nextCost, nextCity, nextEdges});
                }
            }
        }

        return -1;
    }
}
