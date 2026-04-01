package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 34
 *
 * There are N soldiers standing in a line, with IDs from 1 to N, in ascending order. They are
 * participating in an exercise consisting of Q actions. During the ith action, the Major calls S
 * numbers row and col . The soldiers at the row th and col th positions swap places; then the
 * soldiers at (row +1)th and (col -1)th positions swap places, and so on until (row +m)< (col
 * -m). Each of the soldier’s IDs will be covered in the range [row , col ] for at most one
 * action.
 *
 * Write an algorithm to find the ID of the soldier at Kth position in the line after all the
 * actions are completed.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. soldierCount actionCount kthPosition
 * 2. actionCount lines: left right
 */
public class Q34SoldierIdAfterRangeReversals {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int soldierCount = scanner.nextInt();
        int actionCount = scanner.nextInt();
        int kthPosition = scanner.nextInt();
        int[][] actions = new int[actionCount][2];
        for (int i = 0; i < actionCount; i++) {
            actions[i][0] = scanner.nextInt();
            actions[i][1] = scanner.nextInt();
        }

        Q34SoldierIdAfterRangeReversals solver = new Q34SoldierIdAfterRangeReversals();
        System.out.println(solver.soldierIdAtPosition(soldierCount, kthPosition, actions));
    }

    public int soldierIdAtPosition(int soldierCount, int kthPosition, int[][] actions) {
        int answer = kthPosition;
        for (int[] action : actions) {
            if (action[0] <= kthPosition && kthPosition <= action[1]) {
                answer = action[0] + action[1] - kthPosition;
                break;
            }
        }
        return Math.min(Math.max(answer, 1), soldierCount);
    }
}
