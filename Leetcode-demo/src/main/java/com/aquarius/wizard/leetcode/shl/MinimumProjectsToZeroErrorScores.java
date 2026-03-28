package com.aquarius.wizard.leetcode.shl;

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
 * 我的备注
 *
 * 难度：困难。
 *
 * 考点：答案二分、可行性判断。
 * 校对：OCR 与 docx 的规模上限冲突；这里按 OCR 的 2*10^5 理解实现，样例已做代码校验。
 * 做法：二分项目次数，把每个人在做了 mid 次“全体减 Q”后剩余的部分，用 P-Q 去补。
 */
public class MinimumProjectsToZeroErrorScores {

    public long minimumProjects(long[] errorScores, long projectDecrease, long otherDecrease) {
        if (projectDecrease == otherDecrease) {
            long max = 0L;
            for (long errorScore : errorScores) {
                max = Math.max(max, (errorScore + otherDecrease - 1) / otherDecrease);
            }
            return max;
        }

        long left = 0L;
        long right = 1L;
        while (!canFinish(errorScores, projectDecrease, otherDecrease, right)) {
            right <<= 1;
        }
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

    private boolean canFinish(long[] errorScores, long projectDecrease, long otherDecrease, long projects) {
        long extra = projectDecrease - otherDecrease;
        long needed = 0L;
        for (long errorScore : errorScores) {
            long remaining = errorScore - projects * otherDecrease;
            if (remaining > 0) {
                needed += (remaining + extra - 1) / extra;
                if (needed > projects) {
                    return false;
                }
            }
        }
        return needed <= projects;
    }
}
