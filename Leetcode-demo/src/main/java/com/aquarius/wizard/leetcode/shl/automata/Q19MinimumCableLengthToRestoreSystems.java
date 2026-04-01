package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 19
 *
 * The computer systems of N employees of a company are arranged in a row. A technical fault in
 * the power supply has caused some of the systems to turn OFF while the others remain ON. Because
 * of this, the employees whose systems are OFF are unable to work. The company does not like to
 * see its employees sitting idle. So until the technical team can find the actual cause of the
 * breakdown, the technical head Adam has devised a temporary workaround for the OFF systems at a
 * minimum cost. Adam decides to connect all the OFF systems to the nearest ON system with the
 * shortest possible length of cable. To make this happen, he calculates the distance of each
 * system from the first system.
 *
 * Write an algorithm to help Adam find the minimum length of cable he needs to turn all the
 * systems ON.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. systemCount
 * 2. systemCount positions in increasing order
 * 3. systemCount states, where 1 means ON and 0 means OFF
 */
public class Q19MinimumCableLengthToRestoreSystems {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int systemCount = scanner.nextInt();
        int[] positions = new int[systemCount];
        int[] states = new int[systemCount];
        for (int i = 0; i < systemCount; i++) {
            positions[i] = scanner.nextInt();
        }
        for (int i = 0; i < systemCount; i++) {
            states[i] = scanner.nextInt();
        }

        Q19MinimumCableLengthToRestoreSystems solver =
            new Q19MinimumCableLengthToRestoreSystems();
        System.out.println(solver.minimumCableLength(positions, states));
    }

    public long minimumCableLength(int[] positions, int[] states) {
        boolean hasOn = false;
        for (int state : states) {
            if (state == 1) {
                hasOn = true;
                break;
            }
        }
        if (!hasOn) {
            return -1L;
        }

        long total = 0L;
        int i = 0;
        while (i < states.length) {
            if (states[i] == 1) {
                i++;
                continue;
            }

            int start = i;
            while (i < states.length && states[i] == 0) {
                i++;
            }
            int end = i - 1;

            long leftCost = Long.MAX_VALUE / 4;
            long rightCost = Long.MAX_VALUE / 4;
            if (start - 1 >= 0 && states[start - 1] == 1) {
                leftCost = positions[end] - positions[start - 1];
            }
            if (i < states.length && states[i] == 1) {
                rightCost = positions[i] - positions[start];
            }
            total += Math.min(leftCost, rightCost);
        }
        return total;
    }
}
