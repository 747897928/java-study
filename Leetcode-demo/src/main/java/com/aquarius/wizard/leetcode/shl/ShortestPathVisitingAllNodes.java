package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 847
 * Shortest Path Visiting All Nodes
 *
 * 原题链接：
 * https://leetcode.com/problems/shortest-path-visiting-all-nodes/description/?envType=problem-list-v2&envId=dynamic-programming
 *
 * 题意（按原题补全）：
 * 给定一个无向连通图 graph，
 * graph[i] 中列出了与节点 i 相连的所有节点。
 * 你可以从任意节点出发，也可以重复访问节点和边。
 * 返回访问完所有节点所需要经过的最少边数。
 *
 * 示例 1：
 * Input: graph = [[1,2,3],[0],[0],[0]]
 * Output: 4
 *
 * 示例 2：
 * Input: graph = [[1],[0,2,4],[1,3,4],[2],[1,2]]
 * Output: 4
 *
 * 约束：
 * n == graph.length
 * 1 <= n <= 12
 * 0 <= graph[i].length < n
 * graph[i] 不包含 i
 * 如果 graph[i] 包含 j，则 graph[j] 也包含 i
 * 图是连通的
 *
 * 笔记
 *
 * 这题第一层难点是：
 * 普通 BFS 只看“我在哪个点”不够，
 * 因为同一个点，在“已经访问过哪些节点”不同的时候，意义完全不同。
 *
 * 所以状态应该是：
 *
 * - 当前所在节点 node
 * - 已访问集合 mask
 *
 * 这就是标准的状态压缩 BFS。
 *
 * 另一种常见写法是：
 * 先算任意两点最短路，再做子集 DP。
 *
 * 正式主解我这里用 BFS，
 * 因为它最贴近“每走一步边数 +1”的最短步数语义。
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class ShortestPathVisitingAllNodes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] graph = new int[n][];
        for (int i = 0; i < n; i++) {
            int degree = scanner.nextInt();
            graph[i] = new int[degree];
            for (int j = 0; j < degree; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }

        ShortestPathVisitingAllNodes solver = new ShortestPathVisitingAllNodes();
        System.out.println(solver.shortestPathLength(graph));
    }

    public int shortestPathLength(int[][] graph) {
        return shortestPathLengthBfs(graph);
    }

    public int shortestPathLengthBfs(int[][] graph) {
        int n = graph.length;
        int targetMask = (1 << n) - 1;
        boolean[][] visited = new boolean[1 << n][n];
        Queue<int[]> queue = new ArrayDeque<int[]>();

        for (int node = 0; node < n; node++) {
            int mask = 1 << node;
            queue.offer(new int[] {node, mask, 0});
            visited[mask][node] = true;
        }

        while (!queue.isEmpty()) {
            int[] state = queue.poll();
            int node = state[0];
            int mask = state[1];
            int distance = state[2];

            if (mask == targetMask) {
                return distance;
            }

            for (int next : graph[node]) {
                int nextMask = mask | (1 << next);
                if (!visited[nextMask][next]) {
                    visited[nextMask][next] = true;
                    queue.offer(new int[] {next, nextMask, distance + 1});
                }
            }
        }

        return 0;
    }

    public int shortestPathLengthDp(int[][] graph) {
        int n = graph.length;
        int[][] dist = allPairsShortestPath(graph);
        int fullMask = 1 << n;
        int inf = Integer.MAX_VALUE / 4;
        int[][] dp = new int[fullMask][n];
        for (int mask = 0; mask < fullMask; mask++) {
            Arrays.fill(dp[mask], inf);
        }

        for (int i = 0; i < n; i++) {
            dp[1 << i][i] = 0;
        }

        for (int mask = 1; mask < fullMask; mask++) {
            for (int end = 0; end < n; end++) {
                if ((mask & (1 << end)) == 0) {
                    continue;
                }
                int previousMask = mask ^ (1 << end);
                if (previousMask == 0) {
                    continue;
                }
                for (int prev = 0; prev < n; prev++) {
                    if ((previousMask & (1 << prev)) != 0) {
                        dp[mask][end] = Math.min(dp[mask][end], dp[previousMask][prev] + dist[prev][end]);
                    }
                }
            }
        }

        int answer = inf;
        int targetMask = fullMask - 1;
        for (int end = 0; end < n; end++) {
            answer = Math.min(answer, dp[targetMask][end]);
        }
        return answer;
    }

    private int[][] allPairsShortestPath(int[][] graph) {
        int n = graph.length;
        int[][] dist = new int[n][n];
        int inf = Integer.MAX_VALUE / 4;
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], inf);
            dist[i][i] = 0;
            Queue<Integer> queue = new ArrayDeque<Integer>();
            queue.offer(i);
            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int next : graph[node]) {
                    if (dist[i][next] == inf) {
                        dist[i][next] = dist[i][node] + 1;
                        queue.offer(next);
                    }
                }
            }
        }
        return dist;
    }
}
