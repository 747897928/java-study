package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 74
 *
 * Mary, a physical education teacher, divides her students into different groups and assigns an
 * ID to each group. For the group ID, she asks the students to stand in a queue. Each student in
 * the class has a performance factor (PFR). She assigns scores to the students in an unusual way
 * based on their PFR. She gives a score of 5 to a student behind whom is standing at least one
 * student with a higher PFR, behind whom is standing at least one student with a smaller PFR.
 * Next, she gives a score of 10 to a student behind whom is standing a student with a higher PFR,
 * behind whom no student with smaller PFR is standing. Finally, she gives a score of 15 to a
 * student behind whom is standing no student with a higher PFR. The group ID is the sum of scores
 * of the students in the group.
 *
 * Write an algorithm to find the group ID of a group of students.
 *
 * Notes
 *
 * This learning version uses:
 * 1. studentCount
 * 2. studentCount performance factors
 */
public class Q74QueuePerformanceGroupId {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int studentCount = scanner.nextInt();
        int[] performance = new int[studentCount];
        for (int i = 0; i < studentCount; i++) {
            performance[i] = scanner.nextInt();
        }

        Q74QueuePerformanceGroupId solver = new Q74QueuePerformanceGroupId();
        System.out.println(solver.groupId(performance));
    }

    public int groupId(int[] performance) {
        int n = performance.length;
        int[] suffixMax = new int[n];
        int[] suffixMin = new int[n];
        suffixMax[n - 1] = performance[n - 1];
        suffixMin[n - 1] = performance[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixMax[i] = Math.max(performance[i], suffixMax[i + 1]);
            suffixMin[i] = Math.min(performance[i], suffixMin[i + 1]);
        }

        int total = 0;
        for (int i = 0; i < n; i++) {
            boolean hasHigherBehind = i + 1 < n && suffixMax[i + 1] > performance[i];
            boolean hasSmallerBehind = i + 1 < n && suffixMin[i + 1] < performance[i];
            if (!hasHigherBehind) {
                total += 15;
            } else if (!hasSmallerBehind) {
                total += 10;
            } else {
                total += 5;
            }
        }
        return total;
    }
}
