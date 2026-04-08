package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
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
 * 备注
 *
 * 难度：困难。
 *
 * 考点：分层图最短路、Dijkstra。
 * 校对：题面里“Each city is connected to another city by one direct road only”这句英文不自然，但样例和输入格式都明显说明这是一般无向图，不是树。
 * 提示：状态要带上“已经用了几次法术”，也就是 dist[node][used]。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class ShortestPathWithUpToKZeroCostSpells {

    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cityCount = scanner.nextInt();
        int roadCount = scanner.nextInt();
        int sourceCity = scanner.nextInt();
        int targetCity = scanner.nextInt();
        int maxSpellCount = scanner.nextInt();
        int[][] roads = new int[roadCount][3];
        for (int i = 0; i < roadCount; i++) {
            for (int j = 0; j < 3; j++) {
                roads[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int cityCount = 5;
         * int roadCount = 5;
         * int sourceCity = 0;
         * int targetCity = 3;
         * int maxSpellCount = 1;
         * int[][] roads = {{0, 1, 1}, {0, 4, 1}, {1, 2, 2}, {2, 3, 4}, {4, 3, 7}};
         */

        ShortestPathWithUpToKZeroCostSpells solver = new ShortestPathWithUpToKZeroCostSpells();
        System.out.println(solver.shortestPath(cityCount, roads, sourceCity, targetCity, maxSpellCount));
    }

    /**
     * 这题的关键不是普通最短路，而是“同一个城市，法术用了几次，会影响后面的选择”。
     *
     * 所以状态不能只写成：
     *
     * dist[node]
     *
     * 而必须写成：
     *
     * dist[node][usedSpells]
     *
     * 含义是：
     *
     * “到达 node，并且已经用了 usedSpells 次法术时，当前最短路是多少”
     *
     * 为什么要这样分层？
     *
     * 因为：
     *
     * - 到同一个城市
     * - 但一个状态还剩很多法术
     * - 另一个状态法术已经快用完了
     *
     * 这两个状态后续能走出来的最优答案，可能完全不同。
     *
     * 所以它们绝对不能被压成同一个 dist[node]。
     *
     * 这就是“分层图最短路”。
     * 你可以把它脑补成：
     *
     * - 第 0 层：还没用过法术
     * - 第 1 层：已经用了 1 次法术
     * - ...
     * - 第 K 层：已经用了 K 次法术
     *
     * 同一个城市在不同层，其实是不同状态点。
     *
     * 然后每条边会产生两种转移：
     *
     * 1. 正常走这条边
     *    代价 +w，层数不变
     *
     * 2. 对这条边使用法术
     *    代价 +0，层数 +1
     *
     * 因为边权非负，所以这里继续用 Dijkstra 最自然。
     */
    public long shortestPath(int cityCount, int[][] roads, int source, int target, int maxSpells) {
        // 先把边表转成邻接表，方便后面从一个城市快速枚举所有相邻边。
        List<int[]>[] graph = buildGraph(cityCount, roads);

        // distance[node][used]
        // 表示“到达 node，且已经用了 used 次法术时”的最短距离。
        long[][] distance = new long[cityCount][maxSpells + 1];
        for (long[] row : distance) {
            Arrays.fill(row, INF);
        }

        // 小根堆，按当前最短距离从小到大弹状态。
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingLong(state -> state.distance));

        // 起点状态：人在 source，还没用过法术，距离是 0。
        distance[source][0] = 0L;
        queue.add(new State(source, 0, 0L));

        while (!queue.isEmpty()) {
            // 取出当前“距离最短”的那个状态。
            State current = queue.poll();

            // 如果这不是最新最优状态，就跳过。
            // 这是 Dijkstra 常见写法，用来过滤堆里已经过期的旧状态。
            if (current.distance != distance[current.node][current.usedSpells]) {
                continue;
            }

            // 从当前城市出发，尝试所有相邻边。
            for (int[] edge : graph[current.node]) {
                int next = edge[0];
                int weight = edge[1];

                // 转移 1：这条边正常走，不使用法术。
                long normalDistance = current.distance + weight;
                if (normalDistance < distance[next][current.usedSpells]) {
                    distance[next][current.usedSpells] = normalDistance;
                    queue.add(new State(next, current.usedSpells, normalDistance));
                }

                // 转移 2：如果法术还没用满，可以把这条边直接变成 0 代价。
                //
                // 注意这里的新距离不是 current.distance + 0 再写一遍，
                // 而是直接就是 current.distance。
                //
                // 同时 usedSpells 要 +1，表示法术次数真的消耗掉了一次。
                if (current.usedSpells < maxSpells && current.distance < distance[next][current.usedSpells + 1]) {
                    distance[next][current.usedSpells + 1] = current.distance;
                    queue.add(new State(next, current.usedSpells + 1, current.distance));
                }
            }
        }

        // 终点答案不一定出现在某一层固定位置。
        // 因为最优解可能用了 0 次法术，也可能用了 1 次、2 次...K 次。
        long answer = INF;
        for (int used = 0; used <= maxSpells; used++) {
            answer = Math.min(answer, distance[target][used]);
        }
        return answer == INF ? -1L : answer;
    }

    /**
     * 邻接表建图。
     *
     * graph[u] 里每个 int[] 都是：
     *
     * - edge[0] = 相邻城市 v
     * - edge[1] = 边权 w
     *
     * 因为Question说是双向路，所以两边都要加。
     */
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

    /**
     * Dijkstra 堆里的状态对象。
     *
     * node：
     * 当前所在城市
     *
     * usedSpells：
     * 到目前为止已经用了多少次法术
     *
     * distance：
     * 走到这个状态的当前最短距离
     */
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
