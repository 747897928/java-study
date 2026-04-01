package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 4
 *
 * Theon is an energy engineer. His job is to transport oil from the base refinery to the main
 * storage units through a network of pipes. The network is in the form of a tree, where the base
 * refinery is the root and the main storage units are the leaves. The pipes are connected to the
 * storage units via internal connecting stations. The pipes can have different transfer rates
 * [liter of oil per unit of time]. He wishes to determine the maximum amount of oil that can be
 * transported via the network at any given time.
 *
 * Write an algorithm to help Theon find the maximum amount of oil that can be transported via the
 * network at any given time.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. nodeCount
 * 2. nodeCount - 1 lines: from to transferRate
 *
 * This version assumes refinery/root is node 1.
 */
public class Q04MaximumOilTransportInTree {

    private static final long INF = Long.MAX_VALUE / 4;
    private List<Edge>[] graph;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int nodeCount = scanner.nextInt();
        int[][] edges = new int[Math.max(0, nodeCount - 1)][3];
        for (int i = 0; i < nodeCount - 1; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
            edges[i][2] = scanner.nextInt();
        }

        Q04MaximumOilTransportInTree solver = new Q04MaximumOilTransportInTree();
        System.out.println(solver.maximumOil(nodeCount, edges));
    }

    public long maximumOil(int nodeCount, int[][] edges) {
        if (nodeCount <= 1) {
            return 0L;
        }
        graph = new ArrayList[nodeCount + 1];
        for (int i = 1; i <= nodeCount; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(new Edge(edge[1], edge[2]));
            graph[edge[1]].add(new Edge(edge[0], edge[2]));
        }
        long answer = dfs(1, 0);
        return answer >= INF ? 0L : answer;
    }

    private long dfs(int node, int parent) {
        long sum = 0L;
        int childCount = 0;
        for (Edge edge : graph[node]) {
            if (edge.to == parent) {
                continue;
            }
            childCount++;
            long childCapacity = dfs(edge.to, node);
            sum += Math.min(edge.rate, childCapacity);
        }
        return childCount == 0 ? INF : sum;
    }

    private static class Edge {
        private final int to;
        private final long rate;

        private Edge(int to, long rate) {
            this.to = to;
            this.rate = rate;
        }
    }
}
