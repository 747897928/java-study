package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Question
 *
 * In an organization, N employees with employee IDs from 1 to N, are working in different teams.
 * Each employee shares a bond of great understanding with his/her fellow team members. Each
 * employee is assigned an integer X that represents the employee's efficiency. The sum of
 * efficiencies of all the employees indicates the reputation of the organization.
 *
 * Edwin is appointed manager of the organization for Q days. Edwin, being short-tempered, fires
 * one employee each day. Because the team members have a close relationship, K colleagues of the
 * fired employee resign in protest. (These K colleagues have the least efficiency of the remaining
 * team members.)
 *
 * Kevin is the head of the database management system and has to update the reputation of the
 * organization at the end of each day. Write an algorithm to help him determine the reputation of
 * the organization at the end of each day for Q number of days.
 *
 * Input
 *
 * The first line of the input consists of an integer - num, representing the number of employees in
 * the efficiency list (num is always equal to given number of employees N).
 * The second line consists of N space-separated integers - eff0, eff1, ...... effN-1, representing
 * the efficiency of the employees.
 * The third line consists of an integer - numT, representing the number of employees with a team ID
 * (numT is always equal to given number of employees N).
 * The fourth line consists of N space-separated integers - idE0, idE1, ...... idEN-1, representing
 * the team ID of the employees.
 * The fifth line consists of two space-separated integers - numDays and num, representing the
 * number of days for which Edwin is manager of the organization (Q) and the number of elements in
 * each Q lines (num/P is always equal to 2).
 * The last Q lines consist of P space-separated integers - idFire and numResign, representing the
 * ID of the employee that was fired each day and the number of employees who resign along with the
 * fired employee (K), respectively.
 *
 * Output
 *
 * Print Q space-separated integers representing the reputation of the organization at the end of
 * each day.
 *
 * Note
 *
 * The ID of a fired employee cannot be the ID of a person who has already resigned.
 *
 * Example
 *
 * Input:
 * 5
 * 1 2 3 4 5
 * 5
 * 1 2 1 1 2
 * 2 2
 * 3 2
 * 2 0
 *
 * Output:
 * 7 5
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：按团队维护最小堆、懒删除、动态总和。
 * 校对：样例已做代码校验。
 * 实现关键：被开除和已离职的人都还可能残留在堆里，因此需要懒删除。
 */
public class OrganizationReputationUpdater {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int employeeCount = scanner.nextInt();
        int[] efficiencies = new int[employeeCount];
        for (int i = 0; i < employeeCount; i++) {
            efficiencies[i] = scanner.nextInt();
        }
        int teamIdCount = scanner.nextInt();
        int[] teamIds = new int[teamIdCount];
        for (int i = 0; i < teamIdCount; i++) {
            teamIds[i] = scanner.nextInt();
        }
        int dayCount = scanner.nextInt();
        int actionWidth = scanner.nextInt();
        int[][] dailyActions = new int[dayCount][actionWidth];
        for (int i = 0; i < dayCount; i++) {
            for (int j = 0; j < actionWidth; j++) {
                dailyActions[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int employeeCount = 5;
         * int[] efficiencies = {1, 2, 3, 4, 5};
         * int teamIdCount = 5;
         * int[] teamIds = {1, 2, 1, 1, 2};
         * int dayCount = 2;
         * int actionWidth = 2;
         * int[][] dailyActions = {{3, 2}, {2, 0}};
         */

        OrganizationReputationUpdater solver = new OrganizationReputationUpdater();
        System.out.println(format(solver.updateReputation(efficiencies, teamIds, dailyActions)));
    }

    private static String format(long[] nums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(nums[i]);
        }
        return builder.toString();
    }

    public long[] updateReputation(int[] efficiencies, int[] teamIds, int[][] dailyActions) {
        int n = efficiencies.length;
        boolean[] active = new boolean[n];
        Arrays.fill(active, true);
        long reputation = 0L;
        int maxTeamId = 0;
        for (int teamId : teamIds) {
            maxTeamId = Math.max(maxTeamId, teamId);
        }

        @SuppressWarnings("unchecked")
        PriorityQueue<Integer>[] teamMins = new PriorityQueue[maxTeamId + 1];
        for (int i = 0; i <= maxTeamId; i++) {
            teamMins[i] = new PriorityQueue<>((a, b) -> {
                if (efficiencies[a] != efficiencies[b]) {
                    return Integer.compare(efficiencies[a], efficiencies[b]);
                }
                return Integer.compare(a, b);
            });
        }
        for (int i = 0; i < n; i++) {
            reputation += efficiencies[i];
            teamMins[teamIds[i]].offer(i);
        }

        long[] answer = new long[dailyActions.length];
        for (int day = 0; day < dailyActions.length; day++) {
            int firedId = dailyActions[day][0] - 1;
            int resignCount = dailyActions[day][1];
            if (active[firedId]) {
                active[firedId] = false;
                reputation -= efficiencies[firedId];
            }

            PriorityQueue<Integer> queue = teamMins[teamIds[firedId]];
            prune(queue, active);
            for (int i = 0; i < resignCount && !queue.isEmpty(); i++) {
                int resign = queue.poll();
                if (!active[resign]) {
                    i--;
                    prune(queue, active);
                    continue;
                }
                active[resign] = false;
                reputation -= efficiencies[resign];
                prune(queue, active);
            }
            answer[day] = reputation;
        }
        return answer;
    }

    private void prune(PriorityQueue<Integer> queue, boolean[] active) {
        while (!queue.isEmpty() && !active[queue.peek()]) {
            queue.poll();
        }
    }
}
