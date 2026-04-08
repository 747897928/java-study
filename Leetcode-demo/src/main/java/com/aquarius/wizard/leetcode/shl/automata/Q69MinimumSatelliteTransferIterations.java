package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 69
 *
 * A space organization has N satellites in orbit. The satellites are assigned IDs from 0 to N-1.
 * A satellite can transfer data to any other satellite that lies within its bandwidth range. A
 * team at organization is working on a project involving satellite communications. In this
 * project, they wish to transfer data from the main satellite with ID 0 to all the other
 * satellites. But because the transfer of data requires an enormous
 *
 * amount of power, a satellite can transfer data to only one other satellite at a time. Then, the
 * satellite can connect to the server at organization which will help to connect this satellite
 * to other satellites near it. Only a fixed number of satellites can be in the bandwidth range of
 * a satellite at a time. The team wishes to determine the minimum number of iterations of data
 * transfer necessary to connect all the satellites.
 *
 * Write an algorithm to help the team to determine the minimum number of iterations of data
 * transfer necessary to connect all the satellites.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. satelliteCount
 * 2. satelliteCount - 1 lines: u v
 *
 * This version models the communication network as a tree rooted at satellite 0.
 * In one iteration, an informed satellite may forward data to at most one child satellite.
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q69MinimumSatelliteTransferIterations {

    private List<Integer>[] graph;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int satelliteCount = scanner.nextInt();
        int[][] links = new int[Math.max(0, satelliteCount - 1)][2];
        for (int i = 0; i < satelliteCount - 1; i++) {
            links[i][0] = scanner.nextInt();
            links[i][1] = scanner.nextInt();
        }

        Q69MinimumSatelliteTransferIterations solver =
            new Q69MinimumSatelliteTransferIterations();
        System.out.println(solver.minimumIterations(satelliteCount, links));
    }

    public int minimumIterations(int satelliteCount, int[][] links) {
        if (satelliteCount <= 1) {
            return 0;
        }
        graph = new ArrayList[satelliteCount];
        for (int i = 0; i < satelliteCount; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] link : links) {
            graph[link[0]].add(link[1]);
            graph[link[1]].add(link[0]);
        }
        return dfs(0, -1);
    }

    private int dfs(int node, int parent) {
        List<Integer> childTimes = new ArrayList<>();
        for (int next : graph[node]) {
            if (next == parent) {
                continue;
            }
            childTimes.add(dfs(next, node));
        }
        Collections.sort(childTimes, Collections.reverseOrder());

        int answer = 0;
        for (int i = 0; i < childTimes.size(); i++) {
            answer = Math.max(answer, childTimes.get(i) + i + 1);
        }
        return answer;
    }
}
