package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Question
 *
 * On a social networking site, each user can have a group of friends. Each user possesses a unique
 * profile ID. A company wants to promote its product on the social networking site in a particular
 * way. It plans to give rewards to any user who promotes its product on his/her wall. The company
 * will give extra reward points to users who refer other users. The company will ask one of the
 * users to promote its product by posting the product message on his/her wall. The user can then
 * share this message with their friends, asking them to post on their walls as well.
 *
 * The company will share the promo message with the user in such a way that the promo message is
 * posted on the maximum number of walls.
 *
 * Write an algorithm to help the company find the userID of the user to whom they should send the
 * promo request so that the request may reach the maximum number of walls.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers users and pairs,
 * representing the number of users and the number of pairs of friends on the social networking
 * site, respectively.
 * The next pairs lines consist of two space-separated integers user1 and user2, representing the
 * profile ID of users such that user2 is a friend of user1.
 *
 * Output
 *
 * Print an integer representing the userID of the user to whom they should send the promo request
 * so that the request may reach the maximum number of walls.
 *
 * Constraints
 *
 * 1 < users <= 1000
 * 1 < pairs < 10000
 * 0 <= user1, user2 < users
 *
 * Note
 *
 * If user2 is a friend of user1, then it is not necessary that user1 is also a friend of user2.
 * A user cannot share the product message with his/her friend if the friend has already received
 * the product message.
 * If multiple users can reach the same maximum number of walls, print the smaller userID.
 *
 * Example
 *
 * Input:
 * 5 4
 * 0 1
 * 3 4
 * 1 2
 * 2 1
 *
 * Output:
 * 0
 *
 * Explanation:
 * To get the optimal result, the company will share the product message with the user with profile
 * ID 0.
 * The order in which the message is posted by users is given as follows:
 * 0 -> 1 -> 2.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：有向图可达性、从每个起点做 BFS/DFS。
 * 校对：原题中的编号范围和样例冲突，这里按样例统一为 0..users-1，并把并列时取更小 userID 的规则写明。
 * 提示：users 只有 1000，直接从每个点跑一次搜索就够了，不必先上 SCC 压缩。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class BestStartingUserForMaximumReach {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userCount = scanner.nextInt();
        int friendshipPairCount = scanner.nextInt();
        int[][] friendships = new int[friendshipPairCount][2];
        for (int i = 0; i < friendshipPairCount; i++) {
            for (int j = 0; j < 2; j++) {
                friendships[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int userCount = 5;
         * int friendshipPairCount = 4;
         * int[][] friendships = {{0, 1}, {3, 4}, {1, 2}, {2, 1}};
         */

        BestStartingUserForMaximumReach solver = new BestStartingUserForMaximumReach();
        System.out.println(solver.bestUserId(userCount, friendships));
    }

    /**
     * 这题不要被“分享消息”“奖励积分”这些业务词带偏。
     * 翻译成算法语言以后，本质就是：
     *
     * “在一张有向图里，找一个起点，使得从它出发能到达的点数最多。”
     *
     * 由于 users 只有 1000，
     * 所以最直接的做法就是：
     *
     * - 枚举每个用户当起点
     * - 从这个起点做一次 BFS
     * - 统计能到多少人
     * - 取 reach 最大的那个起点
     *
     * 这题没有必要一上来就想 SCC、拓扑压缩之类更重的做法，
     * Question规模已经允许直接从每个点扫一遍。
     */
    public int bestUserId(int users, int[][] friendships) {
        // 先把输入边表建成邻接表。
        List<Integer>[] graph = buildGraph(users, friendships);
        int bestUserId = 0;
        int bestReach = -1;

        // 枚举每个用户作为起点。
        for (int start = 0; start < users; start++) {
            // 计算从 start 出发一共能触达多少个用户。
            int reach = bfsReachCount(graph, start);

            // reach 更大时直接更新。
            // 如果 reach 相同，Question要求返回更小的 userID。
            if (reach > bestReach || (reach == bestReach && start < bestUserId)) {
                bestReach = reach;
                bestUserId = start;
            }
        }
        return bestUserId;
    }

    /**
     * 邻接表建图。
     *
     * Question是有向关系：
     * user1 -> user2
     *
     * 所以这里只加单向边，不加反向边。
     */
    private List<Integer>[] buildGraph(int users, int[][] friendships) {
        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new List[users];
        for (int i = 0; i < users; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : friendships) {
            graph[edge[0]].add(edge[1]);
        }
        return graph;
    }

    /**
     * 从固定起点做一次 BFS，统计可达点数。
     *
     * 为什么这里 BFS/DFS 都可以？
     *
     * 因为这题只问“能到多少个点”，不问最短路长度。
     * 所以 BFS 和 DFS 在正确性上都一样。
     *
     * 这里用 BFS 只是因为队列版写起来更直观。
     */
    private int bfsReachCount(List<Integer>[] graph, int start) {
        boolean[] visited = new boolean[graph.length];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.addLast(start);
        visited[start] = true;
        int count = 1;
        while (!queue.isEmpty()) {
            // 取出当前已经收到消息的用户。
            int current = queue.removeFirst();
            for (int next : graph[current]) {
                // 已经收到过消息的人，不重复进队。
                if (visited[next]) {
                    continue;
                }
                visited[next] = true;
                queue.addLast(next);
                count++;
            }
        }
        return count;
    }
}
