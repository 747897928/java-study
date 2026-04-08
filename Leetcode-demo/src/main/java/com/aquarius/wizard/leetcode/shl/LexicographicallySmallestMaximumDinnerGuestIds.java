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
 * A University has invited N alumni for a dinner. The dinner table has a circular shape. Each
 * alumnus is assigned an invitation ID from 1 to N. Each alumnus likes exactly one fellow alumnus
 * and will attend the dinner only if he/she can be seated next to the person he/she likes.
 *
 * Write an algorithm to find the IDs of the alumni in a lexicographical order so that maximum
 * number of alumni attend the dinner. If more than one such seating arrangement exists, then output
 * the one that is lexicographically smaller.
 *
 * Input
 *
 * The first line of the input consists of an integer num, representing the number of alumni (N).
 * The second line consists of N space-separated integers, alumniID[0], alumniID[1], ...
 * alumniID[N-1] representing the ID of the person whom the ith alumnus likes.
 *
 * Output
 *
 * Print space-separated integers representing the IDs of the alumni who will attend the dinner.
 *
 * Note
 *
 * One alumnus can be liked by multiple alumni.
 *
 * Constraints
 *
 * 1 <= num <= 10^5
 * 0 <= i < num
 *
 * Example
 *
 * Input:
 * 4
 * 2 3 4 1
 *
 * Output:
 * 1 2 3 4
 *
 * Explanation:
 * The first alumnus likes the person whose ID is 2.
 * The second alumnus likes the person whose ID is 3.
 * The third alumnus likes the person whose ID is 4.
 * The fourth alumnus likes the person whose ID is 1.
 * A maximum of 4 alumni can be seated around the circular table in the following manner: 1-2-3-4.
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：函数图、环检测、链长 DP、字典序集合比较。
 * 校对：这是 A10 的恢复版。当前实现沿用了同型问题（类似 LeetCode 2127）的标准结论，并输出“字典序最小的参会 ID 集合（升序）”。
 * 提示：这里我默认Question要输出的是“参会人员 ID 的升序列表”，不是具体环上的座位顺序。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class LexicographicallySmallestMaximumDinnerGuestIds {

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

        LexicographicallySmallestMaximumDinnerGuestIds solver = new LexicographicallySmallestMaximumDinnerGuestIds();
        int[] guestIds = solver.findGuestIds(likesOneBased);
        System.out.println(joinSpaceSeparated(guestIds));
    }

    /**
     * 这题和 CircularDinnerMaximumAttendees 是同一个函数图模型，
     * 区别只是这里不再只输出“最多能来几个人”，
     * 而是要输出“哪一批人来”，并且在人数相同的候选里取字典序更小的集合。
     *
     * 所以整体结构还是那两个分支：
     *
     * 1. 选一个最大大环
     * 2. 选所有二元环，并给每个二元环两边各接一条最长链
     *
     * 但现在除了要算人数，还要真正把人名单恢复出来。
     *
     * 当前实现采用的输出口径是：
     *
     * - 输出参会人员 ID 的升序列表
     * - 不去输出圆桌上的具体座位顺序
     *
     * 在这个口径下：
     *
     * - 大环候选：就是环上的点集
     * - 二元环候选：就是所有二元环方案合并后的点集
     *
     * 最后比较：
     *
     * - 谁人数更多
     * - 如果人数一样，谁的升序列表字典序更小
     */
    public int[] findGuestIds(int[] likesOneBased) {
        int n = likesOneBased.length;
        int[] likes = new int[n];
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) {
            likes[i] = likesOneBased[i] - 1;
            indegree[likes[i]]++;
        }

        // bestLength[x]：
        // 流进 x 的最长链长度（包含 x 本人）。
        //
        // bestPrev[x]：
        // 在这条“最优链”里，x 前一个节点是谁，用来后面恢复链上人员。
        //
        // bestRemainderMin[x]：
        // 用来做同长度链之间的字典序打破平局。
        // 当前实现保留了这份信息，使得选择更稳定。
        int[] bestLength = new int[n];
        int[] bestPrev = new int[n];
        int[] bestRemainderMin = new int[n];
        Arrays.fill(bestLength, 1);
        Arrays.fill(bestPrev, -1);
        for (int i = 0; i < n; i++) {
            bestRemainderMin[i] = i + 1;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        int[] workingIndegree = Arrays.copyOf(indegree, n);
        for (int i = 0; i < n; i++) {
            if (workingIndegree[i] == 0) {
                queue.offer(i);
            }
        }

        // 拓扑剥叶：
        // 先把所有不在环上的链剥掉，并顺手维护“流向某个点的最佳链”。
        while (!queue.isEmpty()) {
            int node = queue.poll();
            int next = likes[node];
            int candidateLength = bestLength[node] + 1;
            int candidateRemainderMin = bestRemainderMin[node];
            if (candidateLength > bestLength[next]
                    || (candidateLength == bestLength[next] && candidateRemainderMin < bestRemainderMin[next])) {
                bestLength[next] = candidateLength;
                bestPrev[next] = node;
                bestRemainderMin[next] = candidateRemainderMin;
            }
            if (--workingIndegree[next] == 0) {
                queue.offer(next);
            }
        }

        // 剥叶结束后，workingIndegree > 0 的点就是环上点。
        boolean[] cycleNode = new boolean[n];
        for (int i = 0; i < n; i++) {
            cycleNode[i] = workingIndegree[i] > 0;
        }

        // 分支 1：找“人数最多的大环候选”，并在同长度时取字典序更小的集合。
        List<Integer> bestCycleSet = new ArrayList<>();
        boolean[] visitedCycle = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!cycleNode[i] || visitedCycle[i]) {
                continue;
            }
            List<Integer> cycle = new ArrayList<>();
            int current = i;
            while (!visitedCycle[current]) {
                visitedCycle[current] = true;
                cycle.add(current + 1);
                current = likes[current];
            }
            cycle.sort(Integer::compareTo);
            if (cycle.size() > 1
                    && (cycle.size() > bestCycleSet.size() || (cycle.size() == bestCycleSet.size() && lexLess(cycle, bestCycleSet)))) {
                bestCycleSet = cycle;
            }
        }

        // 分支 2：收集所有二元环，以及它们两边能接上的最佳链。
        //
        // pairUnion 最后会变成“所有二元环方案合并后的参会人员集合”。
        boolean[] pairVisited = new boolean[n];
        List<Integer> pairUnion = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int liked = likes[i];
            if (liked == i || likes[liked] != i || pairVisited[i] || pairVisited[liked]) {
                continue;
            }
            pairVisited[i] = true;
            pairVisited[liked] = true;
            addChainNodes(i, bestPrev, pairUnion);
            addChainNodes(liked, bestPrev, pairUnion);
        }
        pairUnion.sort(Integer::compareTo);

        // 最后比较两个候选：
        // 1. 最优大环方案
        // 2. 所有二元环方案之和
        if (pairUnion.size() > bestCycleSet.size()) {
            return toArray(pairUnion);
        }
        if (pairUnion.size() < bestCycleSet.size()) {
            return toArray(bestCycleSet);
        }
        return toArray(lexLess(pairUnion, bestCycleSet) ? pairUnion : bestCycleSet);
    }

    /**
     * 从二元环某个端点往外，沿着 bestPrev 恢复那条“最佳链”上的所有人。
     *
     * 因为 bestPrev 是在拓扑剥叶阶段维护出来的，
     * 所以这里恢复出来的，就是能挂到这个端点上的最长链。
     */
    private void addChainNodes(int root, int[] bestPrev, List<Integer> target) {
        int current = root;
        while (current != -1) {
            target.add(current + 1);
            current = bestPrev[current];
        }
    }

    /**
     * 字典序比较：
     * 默认列表里元素已经按升序排好。
     */
    private boolean lexLess(List<Integer> first, List<Integer> second) {
        if (second.isEmpty()) {
            return true;
        }
        for (int i = 0; i < Math.min(first.size(), second.size()); i++) {
            int left = first.get(i);
            int right = second.get(i);
            if (left != right) {
                return left < right;
            }
        }
        return first.size() < second.size();
    }

    private int[] toArray(List<Integer> values) {
        int[] result = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    private static String joinSpaceSeparated(int[] nums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(nums[i]);
        }
        return builder.toString();
    }
}
