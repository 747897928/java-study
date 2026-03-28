package com.aquarius.wizard.leetcode.shl;

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
 * 我的备注
 *
 * 难度：困难。
 *
 * 考点：函数图、环检测、链长 DP、字典序集合比较。
 * 校对：这是 A10 的恢复版。当前实现沿用了同型问题（类似 LeetCode 2127）的标准结论，并输出“字典序最小的参会 ID 集合（升序）”。
 * 提示：这里我默认题目要输出的是“参会人员 ID 的升序列表”，不是具体环上的座位顺序。
 */
public class LexicographicallySmallestMaximumDinnerGuestIds {

    public int[] findGuestIds(int[] likesOneBased) {
        int n = likesOneBased.length;
        int[] likes = new int[n];
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) {
            likes[i] = likesOneBased[i] - 1;
            indegree[likes[i]]++;
        }

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

        boolean[] cycleNode = new boolean[n];
        for (int i = 0; i < n; i++) {
            cycleNode[i] = workingIndegree[i] > 0;
        }

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

        if (pairUnion.size() > bestCycleSet.size()) {
            return toArray(pairUnion);
        }
        if (pairUnion.size() < bestCycleSet.size()) {
            return toArray(bestCycleSet);
        }
        return toArray(lexLess(pairUnion, bestCycleSet) ? pairUnion : bestCycleSet);
    }

    private void addChainNodes(int root, int[] bestPrev, List<Integer> target) {
        int current = root;
        while (current != -1) {
            target.add(current + 1);
            current = bestPrev[current];
        }
    }

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
}
