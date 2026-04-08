package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 51
 *
 * Adison works for a Client servicing company whose head office is located in zone ‘1’ in a city.
 * He has to provide service to the customer in a zone and return back to head office after
 * servicing each complaint. Since he is not well, he likes to optimize the rest time after each
 * service he provides. Hence, he tries to locate the minimum distance between the head office and
 * the target zone. There are N zones, numbered 1 to N, connected by M bidirectional roads such
 * that every zone is connected to the head office either directly or via any other zone. Adison
 * is given a list of M pairs of two zones [A, B] and the time C needed to reach from zone A to
 * zone B. To service Q complaints in the order in which they have been assigned, he is given the
 * name of the zone X and the time limit K in which he can service a complaint to that zone and
 * return to the head office. If he is able to find the shortest route from zone ‘1’ and thus save
 * time, then he can take good rest. If K units of time are not sufficient to visit the zone X and
 * return back to the head office, he will not be able to service a complaint in that zone and the
 * rest time will also be zero.
 *
 * Write an algorithm to compute Adison’s maximum rest time in each service for Q complaints given
 * in a day.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. zoneCount roadCount queryCount
 * 2. roadCount lines: zoneA zoneB time
 * 3. queryCount lines: targetZone timeLimit
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q51MaximumRestTimeForServiceComplaints {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int zoneCount = scanner.nextInt();
        int roadCount = scanner.nextInt();
        int queryCount = scanner.nextInt();
        int[][] roads = new int[roadCount][3];
        for (int i = 0; i < roadCount; i++) {
            roads[i][0] = scanner.nextInt();
            roads[i][1] = scanner.nextInt();
            roads[i][2] = scanner.nextInt();
        }
        int[][] queries = new int[queryCount][2];
        for (int i = 0; i < queryCount; i++) {
            queries[i][0] = scanner.nextInt();
            queries[i][1] = scanner.nextInt();
        }

        Q51MaximumRestTimeForServiceComplaints solver =
            new Q51MaximumRestTimeForServiceComplaints();
        System.out.print(solver.restTimes(zoneCount, roads, queries));
    }

    public String restTimes(int zoneCount, int[][] roads, int[][] queries) {
        List<Edge>[] graph = new ArrayList[zoneCount + 1];
        for (int i = 1; i <= zoneCount; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new Edge(road[1], road[2]));
            graph[road[1]].add(new Edge(road[0], road[2]));
        }

        long[] distance = dijkstra(graph);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < queries.length; i++) {
            int zone = queries[i][0];
            int limit = queries[i][1];
            long needed = distance[zone] * 2;
            long rest = needed <= limit ? limit - needed : 0L;
            if (i > 0) {
                builder.append('\n');
            }
            builder.append(rest);
        }
        return builder.toString();
    }

    private long[] dijkstra(List<Edge>[] graph) {
        long[] distance = new long[graph.length];
        Arrays.fill(distance, Long.MAX_VALUE / 4);
        distance[1] = 0L;
        PriorityQueue<State> queue = new PriorityQueue<>((a, b) -> Long.compare(a.dist, b.dist));
        queue.offer(new State(1, 0L));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.dist != distance[current.node]) {
                continue;
            }
            for (Edge edge : graph[current.node]) {
                long nextDistance = current.dist + edge.time;
                if (nextDistance < distance[edge.to]) {
                    distance[edge.to] = nextDistance;
                    queue.offer(new State(edge.to, nextDistance));
                }
            }
        }
        return distance;
    }

    private static class Edge {
        private final int to;
        private final int time;

        private Edge(int to, int time) {
            this.to = to;
            this.time = time;
        }
    }

    private static class State {
        private final int node;
        private final long dist;

        private State(int node, long dist) {
            this.node = node;
            this.dist = dist;
        }
    }
}
