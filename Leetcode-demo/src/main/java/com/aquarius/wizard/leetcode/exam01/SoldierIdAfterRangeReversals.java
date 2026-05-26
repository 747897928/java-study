package com.aquarius.wizard.leetcode.exam01;

import java.util.Scanner;

/**
 * There are N soldiers standing in a line, their personal IDs are from 1 to N.
 * The positions they stand on are also marked from 1 to N.
 * They will participate in an exercise. In this exercise, these soldiers will swap positions in pairs in order.
 * Based on the command of the major (but the ID of each soldier does not change),
 * the order of exchange position in the i-th action is as follows:
 * 1. The Major calls two numbers Li and Ri. Here, Li <= Ri.
 * 2. Soldiers stand at Li and Ri swap their positions.
 * 3. Soldiers stand at Li+1 and Ri-1 swap their positions.
 * 4. Soldiers stand at Li+2 and Ri-2 swap their positions.
 * 5. and so on. Generally, soldiers stand at Li+m and Ri-m swap their positions.
 * This process continues until (Li+m) >= (Ri-m).
 * Write an algorithm to find the ID of the soldier at the Kth position in the line after all the actions are completed.
 *
 * Input
 * The first line consists of three space-separated integers N, Q and K representing the number of soldiers,
 * the number of swap rounds and the queried position respectively.
 * The next Q lines each consist of two space-separated integers Li and Ri representing one reversal range.
 *
 * Output
 * Print an integer representing the ID of the soldier at the Kth position after Q rounds.
 *
 * Constraints
 * 1 <= K <= N <= 10^9
 * 1 <= Q <= 10^5
 * 1 <= Li <= Ri <= N
 * 1 <= i <= Q
 *
 * Case 1
 * Input:
 * ```
 * 10 2 1
 * 1 5
 * 6 10
 * ```
 * Expected Output:
 * ```
 * 5
 * ```
 *
 * Case 2
 * Input:
 * ```
 * 10 2 10
 * 5 9
 * 2 3
 * ```
 * Expected Output:
 * ```
 * 10
 * ```
 */
public class SoldierIdAfterRangeReversals {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long n = sc.nextLong();
        int q = sc.nextInt();
        long k = sc.nextLong();

        long[] left = new long[q];
        long[] right = new long[q];

        for (int i = 0; i < q; i++) {
            left[i] = sc.nextLong();
            right[i] = sc.nextLong();
        }

        // Trace the final position back through all reversals.
        long pos = k;
        for (int i = q - 1; i >= 0; i--) {
            if (left[i] <= pos && pos <= right[i]) {
                // In a reversed range, mirrored positions satisfy old + new = left + right.
                pos = left[i] + right[i] - pos;
            }
        }

        // Initially, each soldier's ID is the same as his position.
        System.out.print(pos);
    }
}
