package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 64
 *
 * A state has a number of city clusters in which the cities are connected by a network of roads.
 * These roads are bidirectional (i.e., traffic can move in either direction). There are no
 * connections between clusters. Within a cluster, assume that two cities are connected by one road
 * at most.
 *
 * Write an algorithm to determine the total number of clusters of internally connected cities for
 * one such network of cities and roads.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. cityCount roadCount
 * 2. roadCount lines of: from to
 * Cities are treated as 0-based.
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q64CountCityClusters {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cityCount = scanner.nextInt();
        int roadCount = scanner.nextInt();
        int[][] roads = new int[roadCount][2];
        for (int i = 0; i < roadCount; i++) {
            roads[i][0] = scanner.nextInt();
            roads[i][1] = scanner.nextInt();
        }

        /*
         * 本地自测输入：
         *
         * int cityCount = 6;
         * int[][] roads = {
         *     {0, 1},
         *     {1, 2},
         *     {3, 4}
         * };
         */

        Q64CountCityClusters solver = new Q64CountCityClusters();
        System.out.println(solver.countClusters(cityCount, roads));
    }

    public int countClusters(int cityCount, int[][] roads) {
        List<Integer>[] graph = new ArrayList[cityCount];
        for (int i = 0; i < cityCount; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            int a = road[0];
            int b = road[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        boolean[] visited = new boolean[cityCount];
        int clusters = 0;
        for (int city = 0; city < cityCount; city++) {
            if (visited[city]) {
                continue;
            }
            clusters++;
            dfs(city, graph, visited);
        }
        return clusters;
    }

    private void dfs(int city, List<Integer>[] graph, boolean[] visited) {
        visited[city] = true;
        for (int next : graph[city]) {
            if (!visited[next]) {
                dfs(next, graph, visited);
            }
        }
    }
}
