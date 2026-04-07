package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * Question
 *
 * A University has invited N alumni to a dinner. The dinner table is circular in shape. The
 * university has assigned each alumnus an invitation ID from 1 to N. Each alumnus likes exactly
 * one fellow alumnus and will attend the dinner only if he/she can be seated next to that person.
 *
 * You are asked to plan the seating arrangement. Write an algorithm to find the maximum number of
 * alumni who will attend the dinner.
 *
 * Input
 *
 * The first line of the input consists of an integer - personId_size, representing the number of
 * alumni (N).
 * The second line consists of N space-separated integers representing the ID of the person whom the
 * i-th alumnus likes.
 *
 * Output
 *
 * Print an integer representing the maximum number of alumni who can attend the dinner.
 *
 * Note
 *
 * One alumnus can be liked by more than one alumni.
 *
 * Example
 *
 * Input:
 * 4
 * 2 3 4 1
 *
 * Output:
 * 4
 *
 * Explanation:
 * 1st alumnus likes the person with ID 2
 * 2nd likes the person with ID 3
 * 3rd likes the person with ID 4
 * 4th likes the person with ID 1
 * A maximum of 4 alumni can be seated around the circular table in the following manner: 1-2-3-4
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：函数图、环检测、链长统计。
 * 校对：样例已做代码校验。
 * 本题与 LeetCode 2127 同型：答案是“最长大环”和“所有二元环加两侧最长链”的较大者。
 * 相似题：
 * 1. [LexicographicallySmallestMaximumDinnerGuestIds]：同一个函数图模型，但输出要求更难。
 * 2. LeetCode 2127：模型完全同型。
 *
 * 学这题时一定要先接受一个事实：
 *
 * “每个人只喜欢一个人”
 *
 * 这一句意味着图不是普通图，而是函数图：
 *
 * 每个点出度都恰好为 1。
 *
 * 函数图的结构非常固定：
 *
 * 若干条链，最后一定流进某个环。
 *
 * 所以这题最后只会有两种有效答案来源：
 *
 * 1. 一个长度 >= 3 的大环
 * 2. 一个长度恰好为 2 的二元环，再加两边能接进来的最长链
 *
 * 为什么二元环特殊：
 *
 * a <-> b
 *
 * 这种结构的两边都还能继续接链：
 *
 * x -> ... -> a <-> b <- ... <- y
 *
 * 但长度 >= 3 的环不行，因为环上每个人两边座位都已经被环内邻居占掉了。
 */
public class CircularDinnerMaximumAttendees {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int alumniCount = scanner.nextInt();
        int[] likesOneBased = new int[alumniCount];
        for (int i = 0; i < alumniCount; i++) {
            likesOneBased[i] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int alumniCount = 4;
         * int[] likesOneBased = {2, 3, 4, 1};
         */

        CircularDinnerMaximumAttendees solver = new CircularDinnerMaximumAttendees();
        System.out.println(solver.maxAttendees(likesOneBased));
    }

    /**
     * 这道题要拆成两阶段理解：
     *
     * 第一阶段：把所有不在环上的链剥掉
     *
     * indegree 为 0 的点一定不在环里，
     * 所以可以像拓扑排序一样不断删除。
     * 在删除链的过程中，顺手维护“流到某个点的最长链长度”。
     *
     * 第二阶段：只剩环
     *
     * 1. 如果遇到大环，答案候选是环长
     * 2. 如果遇到二元环，答案候选是 longestChain[a] + longestChain[b]
     *
     * 最终答案取：
     *
     * max(最大大环, 所有二元环贡献之和)
     *
     * 这题的本质不是图搜索本身，
     * 而是先看懂函数图结构，再把不同类型的环分开处理。
     */
    public int maxAttendees(int[] likesOneBased) {
        int n = likesOneBased.length;
        int[] likes = new int[n];
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) {
            likes[i] = likesOneBased[i] - 1;
            indegree[likes[i]]++;
        }

        int[] longestChain = new int[n];
        Arrays.fill(longestChain, 1);
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        // 拓扑剥叶：
        // 把所有不在环上的点先删掉，并维护“流进某个点的最长链长度”。
        while (!queue.isEmpty()) {
            int node = queue.poll();
            int next = likes[node];
            longestChain[next] = Math.max(longestChain[next], longestChain[node] + 1);
            if (--indegree[next] == 0) {
                queue.offer(next);
            }
        }

        int maxCycle = 0;
        int pairContribution = 0;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (indegree[i] <= 0 || visited[i]) {
                continue;
            }
            // 现在能进到这里的点，已经保证在某个环里。
            int current = i;
            List<Integer> cycle = new ArrayList<>();
            while (!visited[current]) {
                visited[current] = true;
                cycle.add(current);
                current = likes[current];
            }
            if (cycle.size() == 2) {
                int a = cycle.get(0);
                int b = cycle.get(1);
                // 二元环可以把两边最长链一起接上。
                pairContribution += longestChain[a] + longestChain[b];
            } else {
                // 大环只能单独成环，不能再外挂链。
                maxCycle = Math.max(maxCycle, cycle.size());
            }
        }
        return Math.max(maxCycle, pairContribution);
    }
}
