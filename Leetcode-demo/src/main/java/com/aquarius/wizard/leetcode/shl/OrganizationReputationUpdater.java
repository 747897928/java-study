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

    /**
     * 这题真正的难点不是“每天删一个人”，
     * 而是：
     *
     * “每天还要在被开除的人所属团队里，再自动离职 K 个效率最小的人”
     *
     * 所以最自然的数据结构就是：
     *
     * - 每个团队维护一个最小堆
     * - 堆里按效率从小到大排
     *
     * 这样每天只要找到被开除员工所在团队，
     * 就能不断弹出该团队里当前效率最小的人。
     *
     * 为什么这里需要懒删除？
     *
     * 因为一个人离职以后，
     * 他之前可能还残留在团队堆里，并不会自动从 PriorityQueue 的中间消失。
     *
     * 所以每次真正使用堆顶前，都要先 prune 一下：
     *
     * - 如果堆顶这个人已经不 active 了
     * - 就把他继续弹掉
     * - 直到堆顶真的是当前仍在职的人
     */
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

        // 逐天处理开除和连带离职。
        long[] answer = new long[dailyActions.length];
        for (int day = 0; day < dailyActions.length; day++) {
            int firedId = dailyActions[day][0] - 1;
            int resignCount = dailyActions[day][1];

            // 先处理当天被直接开除的人。
            if (active[firedId]) {
                active[firedId] = false;
                reputation -= efficiencies[firedId];
            }

            PriorityQueue<Integer> queue = teamMins[teamIds[firedId]];
            prune(queue, active);
            for (int i = 0; i < resignCount && !queue.isEmpty(); i++) {
                int resign = queue.poll();
                if (!active[resign]) {
                    // 理论上 prune 后通常不会撞到，但保留这层防御更稳。
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

    /**
     * 懒删除：
     *
     * PriorityQueue 不适合“随便删中间元素”，
     * 所以我们不主动去堆里找某个已经离职的人删掉。
     *
     * 而是在每次真正需要堆顶时，
     * 把所有已经 inactive 的旧元素顺手清掉。
     */
    private void prune(PriorityQueue<Integer> queue, boolean[] active) {
        while (!queue.isEmpty() && !active[queue.peek()]) {
            queue.poll();
        }
    }
}
