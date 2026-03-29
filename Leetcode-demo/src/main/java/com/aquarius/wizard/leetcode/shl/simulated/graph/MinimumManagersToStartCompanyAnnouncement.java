package com.aquarius.wizard.leetcode.shl.simulated.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Simulated Problem
 *
 * Difficulty
 *
 * Hard
 *
 * Question
 *
 * A company has N managers. There are directed communication channels between them. If manager A
 * can directly inform manager B, then once A receives an announcement, A can forward it to B.
 *
 * The company wants every manager to receive an important announcement. However, the company does
 * not want to call every manager directly. Instead, it wants to choose a minimum number of managers
 * to receive the initial call, so that after the forwarding process, every manager eventually gets
 * the announcement.
 *
 * Write an algorithm to find the minimum number of managers who must receive the initial call.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers - managers and channels,
 * representing the number of managers (N) and the number of directed communication channels (M),
 * respectively.
 *
 * The next M lines each consist of two space-separated integers from and to, representing a
 * directed communication channel from manager from to manager to.
 *
 * Output
 *
 * Print an integer representing the minimum number of managers that must receive the initial call.
 *
 * Constraints
 *
 * 1 <= managers <= 2 * 10^5
 * 0 <= channels <= 3 * 10^5
 * 0 <= from, to < managers
 *
 * Example
 *
 * Input:
 * 6 7
 * 0 1
 * 1 0
 * 1 2
 * 2 3
 * 3 4
 * 4 3
 * 5 4
 *
 * Output:
 * 2
 *
 * Explanation:
 *
 * Managers {0,1} form one strongly connected group.
 * Managers {3,4} form another strongly connected group.
 * The announcement must start from one manager in the group {0,1} and also from manager 5.
 * Therefore, the minimum number of initial calls is 2.
 *
 * 我的备注
 *
 * 这是我补的模拟题，不是原 SHL 题。
 *
 * 相似题：
 * 1. BestStartingUserForMaximumReach
 * 2. 强连通分量缩点
 * 3. “最少从几个点出发才能覆盖整个有向图”
 *
 * 学习重点：
 *
 * 这题表面在讲公司通知，
 * 本质在问：
 *
 * “有向图缩成 SCC DAG 之后，有多少个入度为 0 的强连通分量？”
 */
public class MinimumManagersToStartCompanyAnnouncement {

    public static void main(String[] args) {
        int managers = 6;
        int[][] channels = {
                {0, 1},
                {1, 0},
                {1, 2},
                {2, 3},
                {3, 4},
                {4, 3},
                {5, 4}
        };

        MinimumManagersToStartCompanyAnnouncement solver =
                new MinimumManagersToStartCompanyAnnouncement();
        System.out.println(solver.minimumInitialCalls(managers, channels));
    }

    /*
     * Practice Mode
     *
     * Try to solve this problem before reading the code below.
     * Ask yourself:
     *
     * 1. If two managers can reach each other, do they really need separate initial calls?
     * 2. After compressing cycles into one node, what does the remaining graph look like?
     * 3. In that new graph, which nodes must be chosen as starting points?
     */

    public int minimumInitialCalls(int managers, int[][] channels) {
        List<Integer>[] graph = buildGraph(managers, channels, false);
        List<Integer>[] reverseGraph = buildGraph(managers, channels, true);

        boolean[] visited = new boolean[managers];
        List<Integer> order = new ArrayList<>(managers);
        for (int node = 0; node < managers; node++) {
            if (!visited[node]) {
                fillOrder(node, graph, visited, order);
            }
        }

        int[] componentId = new int[managers];
        for (int i = 0; i < managers; i++) {
            componentId[i] = -1;
        }

        int componentCount = 0;
        for (int index = order.size() - 1; index >= 0; index--) {
            int node = order.get(index);
            if (componentId[node] == -1) {
                markComponent(node, componentCount, reverseGraph, componentId);
                componentCount++;
            }
        }

        int[] indegree = new int[componentCount];
        for (int from = 0; from < managers; from++) {
            for (int to : graph[from]) {
                if (componentId[from] != componentId[to]) {
                    indegree[componentId[to]]++;
                }
            }
        }

        int answer = 0;
        for (int degree : indegree) {
            if (degree == 0) {
                answer++;
            }
        }
        return answer;
    }

    private List<Integer>[] buildGraph(int managers, int[][] channels, boolean reverse) {
        List<Integer>[] graph = new ArrayList[managers];
        for (int i = 0; i < managers; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : channels) {
            int from = edge[0];
            int to = edge[1];
            if (reverse) {
                graph[to].add(from);
            } else {
                graph[from].add(to);
            }
        }
        return graph;
    }

    private void fillOrder(int start, List<Integer>[] graph, boolean[] visited, List<Integer> order) {
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[] {start, 0});

        while (!stack.isEmpty()) {
            int[] state = stack.pop();
            int node = state[0];
            int phase = state[1];

            if (phase == 0) {
                if (visited[node]) {
                    continue;
                }
                visited[node] = true;
                stack.push(new int[] {node, 1});
                for (int i = graph[node].size() - 1; i >= 0; i--) {
                    int next = graph[node].get(i);
                    if (!visited[next]) {
                        stack.push(new int[] {next, 0});
                    }
                }
            } else {
                order.add(node);
            }
        }
    }

    private void markComponent(int start, int component, List<Integer>[] reverseGraph, int[] componentId) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        componentId[start] = component;

        while (!stack.isEmpty()) {
            int node = stack.pop();
            for (int next : reverseGraph[node]) {
                if (componentId[next] == -1) {
                    componentId[next] = component;
                    stack.push(next);
                }
            }
        }
    }
}
