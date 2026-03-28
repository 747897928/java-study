package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Question
 *
 * In a state, N cities with unique city codes from 1 to N are connected by N-1 roads. The road
 * network is in the form of a tree of which each road connects two cities. A path is a road, or a
 * combination of roads, connecting any two cities. Each road has a toll booth that collects a toll
 * equal to the maximum number of paths of which that particular road is part. The state
 * transportation authority wishes to identify the road on which the maximum toll revenue is
 * collected.
 *
 * Write an algorithm to help the transportation authority identify the pair of cities connected by
 * the road on which the maximum toll revenue is collected. The output should be sorted in
 * increasing order. If more than one road collects the same total revenue, then output the pair of
 * cities that have the smaller city code.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers - N and R, representing the
 * number of cities in the state and the number of roads, respectively.
 * The next R lines consist of two space-separated integers - city1 and city2, representing the
 * cities connected by a road.
 *
 * Output
 *
 * Print two space-separated sorted integers representing the cities connected by the road on which
 * the maximum toll revenue is collected. If two or more toll booths collect the same total revenue,
 * then print the pair of cities with lexicographically smaller codes.
 *
 * Example
 *
 * Input:
 * 4 3
 * 1 2
 * 2 3
 * 3 4
 *
 * Output:
 * 2 3
 *
 * Explanation:
 * Road (2,3) lies between the pairs of cities (1,3), (1,4), (2,3), and (2,4).
 * So, the maximum toll collected by the road connecting cities 2 and 3 is 4.
 *
 * 我的备注
 *
 * 难度：中等。
 *
 * 考点：树边贡献、子树规模。
 * 校对：样例已做代码校验。
 * 核心公式：若边把树拆成大小为 size 和 n-size 的两块，则该边贡献为 size * (n-size)。
 * 相似题：所有“删一条树边后，左右两部分怎样贡献答案”的题。
 *
 * 这题最值得学会的不是 DFS 本身，而是这个树题通用反应：
 *
 * “某条边对答案的贡献，往往等于删掉它之后，两边子树规模的乘积”
 *
 * 为什么是乘积：
 *
 * 如果一条边把树切成了：
 *
 * leftSize = size
 * rightSize = n - size
 *
 * 那么任何一条跨越这条边的路径，
 * 都必须一端在左边，一端在右边。
 *
 * 左边有 size 个点可以选，
 * 右边有 n - size 个点可以选，
 * 所以一共有：
 *
 * size * (n - size)
 *
 * 条路径会经过这条边。
 *
 * 这就是公式的来源，不是死记硬背出来的。
 */
public class RoadWithMaximumTollRevenue {

    public static void main(String[] args) {
        int cityCount = 4;
        int roadCount = 3;
        int[][] roads = {
                {1, 2},
                {2, 3},
                {3, 4}
        };

        if (roads.length != roadCount) {
            throw new IllegalArgumentException("roads.length must equal roadCount");
        }

        RoadWithMaximumTollRevenue solver = new RoadWithMaximumTollRevenue();
        System.out.println(format(solver.findBestRoad(cityCount, roads)));
    }

    private static String format(int[] nums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(nums[i]);
        }
        return builder.toString();
    }

    /**
     * 这段实现分两步：
     *
     * 1. 先用一次树遍历拿到 parent 和访问顺序
     * 2. 再按逆后序回推 subtreeSize
     *
     * 当我们从叶子往上回推时，
     * 就能知道“以 node 为根的子树有多大”。
     *
     * 一旦知道 subtreeSize[node]，
     * 连接 node 和 parent[node] 的那条边的贡献就直接出来了：
     *
     * subtreeSize[node] * (n - subtreeSize[node])
     *
     * 所以这题的关键不是枚举路径，
     * 而是把“多少条路径经过这条边”翻译成子树规模问题。
     */
    public int[] findBestRoad(int cityCount, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>(cityCount + 1);
        for (int i = 0; i <= cityCount; i++) {
            graph.add(new ArrayList<Integer>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] parent = new int[cityCount + 1];
        int[] order = new int[cityCount];
        int index = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        parent[1] = -1;
        while (!stack.isEmpty()) {
            int node = stack.pop();
            order[index++] = node;
            for (int next : graph.get(node)) {
                if (next == parent[node]) {
                    continue;
                }
                parent[next] = node;
                stack.push(next);
            }
        }

        long[] subtreeSize = new long[cityCount + 1];
        Arrays.fill(subtreeSize, 1L);
        long bestRevenue = -1L;
        int[] bestEdge = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int i = index - 1; i > 0; i--) {
            int node = order[i];
            int par = parent[node];
            subtreeSize[par] += subtreeSize[node];
            long revenue = subtreeSize[node] * (cityCount - subtreeSize[node]);
            int a = Math.min(node, par);
            int b = Math.max(node, par);
            if (revenue > bestRevenue || (revenue == bestRevenue && (a < bestEdge[0] || (a == bestEdge[0] && b < bestEdge[1])))) {
                bestRevenue = revenue;
                bestEdge[0] = a;
                bestEdge[1] = b;
            }
        }
        return bestEdge;
    }
}
