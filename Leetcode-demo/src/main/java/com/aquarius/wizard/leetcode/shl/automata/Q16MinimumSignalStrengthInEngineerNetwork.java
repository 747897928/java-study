package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 16
 *
 * An organization has assigned X engineers to work on a project. The engineers need a way to
 * connect with each other and share data. Austin, the network administrator, has built a
 * hierarchical network that allows an engineer to connect to two engineers at most in the
 * network. He establishes all full duplex connections in the network (i.e. if there is a
 * connection between A and B, then data can be transferred from A to B and from B to A). The
 * strength of the signal decreases by one unit upon each transmission between directly connected
 * engineer. Therefore, Austin needs to determine the minimum strength at which the signal must be
 * sent so that it will reach everyone.
 *
 * Write an algorithm to help Austin find the minimum strength at which the signal must be sent so
 * that the data will reach everyone.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. engineerCount
 * 2. engineerCount - 1 lines: u v
 *
 * This version assumes the data is broadcast from engineer 1, so the required signal strength is
 * the farthest number of transmissions from engineer 1.
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q16MinimumSignalStrengthInEngineerNetwork {

    private List<Integer>[] graph;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int engineerCount = scanner.nextInt();
        int[][] edges = new int[Math.max(0, engineerCount - 1)][2];
        for (int i = 0; i < engineerCount - 1; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }

        Q16MinimumSignalStrengthInEngineerNetwork solver =
            new Q16MinimumSignalStrengthInEngineerNetwork();
        System.out.println(solver.minimumSignalStrength(engineerCount, edges));
    }

    public int minimumSignalStrength(int engineerCount, int[][] edges) {
        if (engineerCount <= 1) {
            return 0;
        }
        graph = new ArrayList[engineerCount + 1];
        for (int i = 1; i <= engineerCount; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        return dfs(1, 0);
    }

    private int dfs(int node, int parent) {
        int best = 0;
        for (int next : graph[node]) {
            if (next == parent) {
                continue;
            }
            best = Math.max(best, 1 + dfs(next, node));
        }
        return best;
    }
}
