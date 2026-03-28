package com.aquarius.wizard.leetcode.shl;

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
 * 我的备注
 *
 * 难度：困难。
 *
 * 考点：函数图、环检测、链长统计。
 * 校对：样例已做代码校验。
 * 本题与 LeetCode 2127 同型：答案是“最长大环”和“所有二元环加两侧最长链”的较大者。
 */
public class CircularDinnerMaximumAttendees {

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
                pairContribution += longestChain[a] + longestChain[b];
            } else {
                maxCycle = Math.max(maxCycle, cycle.size());
            }
        }
        return Math.max(maxCycle, pairContribution);
    }
}
