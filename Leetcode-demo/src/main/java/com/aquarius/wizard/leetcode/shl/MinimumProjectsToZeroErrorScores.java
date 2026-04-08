package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 *
 * Ethan is the leader of a team with N members. He has assigned an error score to each member in
 * his team based on the bugs that he has found in that particular team member's task. Because the
 * error score has increased to a significantly large value, he wants to give all the team members a
 * chance to improve their error scores, thereby improving their reputation in the organization. He
 * introduces a new rule that whenever a team member completes a project successfully, the error
 * score of that member decreases by a count P and the error score of all the other team members
 * whose score is greater than zero decreases by a count Q.
 *
 * Write an algorithm to help Ethan find the minimum number of projects that the team must complete
 * in order to make the error score of all the team members zero.
 *
 * Input
 *
 * The first line of the input consists of an integer - errorScore_size, representing the total
 * number of team members (N).
 * The second line consists of N space-separated integers - errorScore, representing the initial
 * error scores of the team members.
 * The third line consists of an integer - compP, representing the count by which the error score of
 * the team member who completes a project successfully decreases (P).
 * The last line consists of an integer - othQ, representing the count by which the error score of
 * the team member whose error score is greater than zero decreases (Q).
 *
 * Output
 *
 * Print an integer representing the minimum number of projects that the team must complete in order
 * to make the error score of all the team members zero. If no project need to be completed then
 * print 0.
 *
 * Constraints
 *
 * 1 <= errorScore_size <= 2*10^5
 * 1 <= othQ <= compP <= 10^9
 * 0 <= errorScore <= 10^9
 *
 * Example
 *
 * Input:
 * 3
 * 6 4 1
 * 4
 * 1
 *
 * Output:
 * 3
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：答案二分、可行性判断。
 * 校对：OCR 与 docx 的规模上限冲突；这里按 OCR 的 2*10^5 理解实现，样例已做代码校验。
 * 做法：二分项目次数，把每个人在做了 mid 次“全体减 Q”后剩余的部分，用 P-Q 去补。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class MinimumProjectsToZeroErrorScores {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int teamMemberCount = scanner.nextInt();
        long[] errorScores = new long[teamMemberCount];
        for (int i = 0; i < teamMemberCount; i++) {
            errorScores[i] = scanner.nextLong();
        }
        long projectDecrease = scanner.nextLong();
        long otherDecrease = scanner.nextLong();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int teamMemberCount = 3;
         * long[] errorScores = {6L, 4L, 1L};
         * long projectDecrease = 4L;
         * long otherDecrease = 1L;
         */

        MinimumProjectsToZeroErrorScores solver = new MinimumProjectsToZeroErrorScores();
        System.out.println(solver.minimumProjects(errorScores, projectDecrease, otherDecrease));
    }

    /**
     * 这题的难点不在模拟，而在“最少做多少个项目”这个问法。
     *
     * 一看到“最少多少次”，并且数据范围很大，
     * 就应该优先想：
     *
     * - 答案二分
     * - 然后写一个 canFinish(mid) 去判断“做 mid 次够不够”
     *
     * 为什么这里适合二分？
     *
     * 因为它有明显单调性：
     *
     * - 如果做 x 次项目已经能把所有人清零
     * - 那么做 x + 1 次、x + 2 次也一定能清零
     *
     * 所以答案集合一定形如：
     *
     * false false false true true true ...
     *
     * 这就是标准二分模板可以上的信号。
     *
     * canFinish(projects) 怎么判断？
     *
     * 先假设总共做了 projects 次。
     * 那么不管谁是“被重点照顾的人”，
     * 每个人至少都会被“其他人完成项目”影响到 projects 次，
     * 也就是先统一减去：
     *
     * projects * Q
     *
     * 如果某个人减完以后还大于 0，
     * 说明他还需要额外被选中若干次。
     *
     * 每当他自己完成一次项目，
     * 相比“只吃到别人带来的 Q”，
     * 他会额外多减：
     *
     * P - Q
     *
     * 所以剩余值 remaining 需要多少次额外重点照顾，
     * 就是：
     *
     * ceil(remaining / (P - Q))
     *
     * 把所有人这些“额外需要的重点次数”加起来，
     * 如果总数不超过 projects，
     * 说明 projects 次项目足够安排。
     */
    public long minimumProjects(long[] errorScores, long projectDecrease, long otherDecrease) {
        // 特判 P == Q：
        // 这时不存在“额外重点照顾收益”，每次对所有人效果都完全一样。
        // 所以答案就是把最大 errorScore 除以 Q 后向上取整。
        if (projectDecrease == otherDecrease) {
            long max = 0L;
            for (long errorScore : errorScores) {
                max = Math.max(max, (errorScore + otherDecrease - 1) / otherDecrease);
            }
            return max;
        }

        // 先倍增找到一个一定可行的右边界。
        long left = 0L;
        long right = 1L;
        while (!canFinish(errorScores, projectDecrease, otherDecrease, right)) {
            right <<= 1;
        }

        // 标准二分最小可行答案。
        while (left < right) {
            long middle = left + ((right - left) >> 1);
            if (canFinish(errorScores, projectDecrease, otherDecrease, middle)) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }
        return left;
    }

    /**
     * 判断“总共做 projects 次项目”是否足够把所有 errorScore 清零。
     */
    private boolean canFinish(long[] errorScores, long projectDecrease, long otherDecrease, long projects) {
        // 每次如果一个人自己做项目，
        // 相比“只吃到别人带来的 Q”，会额外多减 extra = P - Q。
        long extra = projectDecrease - otherDecrease;
        long needed = 0L;
        for (long errorScore : errorScores) {
            // 假设先统一吃满 projects 次“全体减 Q”的效果。
            long remaining = errorScore - projects * otherDecrease;
            if (remaining > 0) {
                // remaining 还没清零，就要额外分配若干次“这个人自己做项目”的机会。
                needed += (remaining + extra - 1) / extra;

                // 提前剪枝：
                // 如果额外需要的次数已经超过总项目数，就不可能安排成功。
                if (needed > projects) {
                    return false;
                }
            }
        }
        return needed <= projects;
    }
}
