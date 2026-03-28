package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Question
 *
 * A state consists of n cities numbered from 0 to n-1. All the roads in the state are
 * bidirectional. Each city is connected to another city by one direct road only. A magician
 * travels to these cities to perform shows. He knows a magic spell that can completely eliminate
 * the distance between any two directly connected cities. But he must be very careful because this
 * magic spell can be performed only K number of times.
 *
 * Write an algorithm to find the length of the shortest route between two given cities after
 * performing the magic spell K number of times. The output is -1 if no path exists.
 *
 * Input
 *
 * The first line of the input consists of five space-separated integers n, m, p, q and K,
 * representing the number of cities, the number of roads, the city A, the city B, and the number
 * of times the magician can perform the magic spell, respectively.
 * The next m lines consist of three space-separated integers u, v and w, where u and v represent
 * the cities and w represents the length of the bidirectional road between the cities.
 *
 * Output
 *
 * Print an integer representing the length of the shortest route between the two given cities after
 * performing the magic spell K number of times. Print -1 if no path exists.
 *
 * Constraints
 *
 * 1 <= n <= 1000
 * 0 <= K <= n
 * 0 <= A, B < n
 * 0 <= m <= 10^4
 * 1 <= w <= 1000
 *
 * Example
 *
 * Input:
 * 5 5 0 3 1
 * 0 1 1
 * 0 4 1
 * 1 2 2
 * 2 3 4
 * 4 3 7
 *
 * Output:
 * 1
 *
 * Explanation:
 * There are two possible routes between 0 and 3: 0->1->2->3 and 0->4->3. After reducing the
 * distance of edge 4->3 to zero, the second route becomes optimal and the minimum distance is 1.
 *
 * 我的备注
 *
 * 难度：困难。
 *
 * 考点：分层图最短路、Dijkstra。
 * 校对：题面里“Each city is connected to another city by one direct road only”这句英文不自然，但样例和输入格式都明显说明这是一般无向图，不是树。
 * 提示：状态要带上“已经用了几次法术”，也就是 dist[node][used]。
 */
public class ShortestPathWithUpToKZeroCostSpells {

    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) {
        int cityCount = 5;
        int roadCount = 5;
        int sourceCity = 0;
        int targetCity = 3;
        int maxSpellCount = 1;
        int[][] roads = {
                {0, 1, 1},
                {0, 4, 1},
                {1, 2, 2},
                {2, 3, 4},
                {4, 3, 7}
        };

        if (roads.length != roadCount) {
            throw new IllegalArgumentException("roads.length must equal roadCount");
        }

        ShortestPathWithUpToKZeroCostSpells solver = new ShortestPathWithUpToKZeroCostSpells();
        System.out.println(solver.shortestPath(cityCount, roads, sourceCity, targetCity, maxSpellCount));
    }

    public long shortestPath(int cityCount, int[][] roads, int source, int target, int maxSpells) {
        List<int[]>[] graph = buildGraph(cityCount, roads);
        long[][] distance = new long[cityCount][maxSpells + 1];
        for (long[] row : distance) {
            Arrays.fill(row, INF);
        }
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingLong(state -> state.distance));
        distance[source][0] = 0L;
        queue.add(new State(source, 0, 0L));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.distance != distance[current.node][current.usedSpells]) {
                continue;
            }
            for (int[] edge : graph[current.node]) {
                int next = edge[0];
                int weight = edge[1];
                long normalDistance = current.distance + weight;
                if (normalDistance < distance[next][current.usedSpells]) {
                    distance[next][current.usedSpells] = normalDistance;
                    queue.add(new State(next, current.usedSpells, normalDistance));
                }
                if (current.usedSpells < maxSpells && current.distance < distance[next][current.usedSpells + 1]) {
                    distance[next][current.usedSpells + 1] = current.distance;
                    queue.add(new State(next, current.usedSpells + 1, current.distance));
                }
            }
        }

        long answer = INF;
        for (int used = 0; used <= maxSpells; used++) {
            answer = Math.min(answer, distance[target][used]);
        }
        return answer == INF ? -1L : answer;
    }

    private List<int[]>[] buildGraph(int cityCount, int[][] roads) {
        @SuppressWarnings("unchecked")
        List<int[]>[] graph = new List[cityCount];
        for (int i = 0; i < cityCount; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }
        return graph;
    }

    private static final class State {
        private final int node;
        private final int usedSpells;
        private final long distance;

        private State(int node, int usedSpells, long distance) {
            this.node = node;
            this.usedSpells = usedSpells;
            this.distance = distance;
        }
    }
}
