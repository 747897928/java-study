package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 1928
 * Minimum Cost to Reach Destination in Time
 *
 * 给定城市费用 passingFees、道路耗时 edges 和总时间上限 maxTime。
 * 从 0 出发到 n-1，问在总时间不超过 maxTime 的前提下最小费用是多少。
 *
 * 笔记
 *
 * 这题和普通最短路的区别在于：
 * 费用最小和时间最小不是同一维。
 *
 * 所以只记“到某个点的最小费用”不够，
 * 只记“到某个点的最短时间”也不够。
 *
 * 常见两种写法：
 *
 * 1. 按时间做 DP
 *    dp[t][city] = 恰好在时间 t 到达 city 的最小费用
 *
 * 2. 状态最短路
 *    把 (city, time) 当成新图里的节点
 *
 * 正式主解我这里用状态最短路，
 * 因为它更贴近“在扩展状态图上跑 Dijkstra”这个理解。
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class MinimumCostToReachDestinationInTime {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxTime = scanner.nextInt();
        int edgeCount = scanner.nextInt();
        int[][] edges = new int[edgeCount][3];
        for (int i = 0; i < edgeCount; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
            edges[i][2] = scanner.nextInt();
        }
        int n = scanner.nextInt();
        int[] fees = new int[n];
        for (int i = 0; i < n; i++) {
            fees[i] = scanner.nextInt();
        }

        MinimumCostToReachDestinationInTime solver = new MinimumCostToReachDestinationInTime();
        System.out.println(solver.minCost(maxTime, edges, fees));
    }

    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        return minCostStateDijkstra(maxTime, edges, passingFees);
    }

    public int minCostDp(int maxTime, int[][] edges, int[] passingFees) {
        int n = passingFees.length;
        int inf = Integer.MAX_VALUE / 4;
        int[][] dp = new int[maxTime + 1][n];
        for (int t = 0; t <= maxTime; t++) {
            Arrays.fill(dp[t], inf);
        }
        dp[0][0] = passingFees[0];

        for (int time = 1; time <= maxTime; time++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int costTime = edge[2];
                if (time >= costTime) {
                    if (dp[time - costTime][u] != inf) {
                        dp[time][v] = Math.min(dp[time][v], dp[time - costTime][u] + passingFees[v]);
                    }
                    if (dp[time - costTime][v] != inf) {
                        dp[time][u] = Math.min(dp[time][u], dp[time - costTime][v] + passingFees[u]);
                    }
                }
            }
        }

        int answer = inf;
        for (int time = 0; time <= maxTime; time++) {
            answer = Math.min(answer, dp[time][n - 1]);
        }
        return answer == inf ? -1 : answer;
    }

    public int minCostStateDijkstra(int maxTime, int[][] edges, int[] passingFees) {
        int n = passingFees.length;
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<int[]>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[] {edge[1], edge[2]});
            graph[edge[1]].add(new int[] {edge[0], edge[2]});
        }

        int inf = Integer.MAX_VALUE / 4;
        int[][] dist = new int[maxTime + 1][n];
        for (int t = 0; t <= maxTime; t++) {
            Arrays.fill(dist[t], inf);
        }
        dist[0][0] = passingFees[0];

        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> Integer.compare(a[0], b[0]));
        pq.offer(new int[] {passingFees[0], 0, 0});

        while (!pq.isEmpty()) {
            int[] state = pq.poll();
            int cost = state[0];
            int city = state[1];
            int time = state[2];

            if (cost != dist[time][city]) {
                continue;
            }
            if (city == n - 1) {
                return cost;
            }

            for (int[] edge : graph[city]) {
                int nextCity = edge[0];
                int nextTime = time + edge[1];
                if (nextTime > maxTime) {
                    continue;
                }
                int nextCost = cost + passingFees[nextCity];
                if (nextCost < dist[nextTime][nextCity]) {
                    dist[nextTime][nextCity] = nextCost;
                    pq.offer(new int[] {nextCost, nextCity, nextTime});
                }
            }
        }

        return -1;
    }
}
