package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 36
 *
 * You are performing a science experiment in a research laboratory. You are attempting to form a
 * new compound. A compound is made up of molecules and the mass of the compound is the sum of the
 * masses of the molecules that compose the compound. For this experiment, you have identified
 * four types of molecules: A, B, C and D. From these four molecules, A and B are monatomic, but C
 * and D are diatomic. A monoatomic molecule is made up of one atom, but a diatomic molecule is
 * made up of two atoms. So the mass of a diatomic molecule is twice its atomic mass while the
 * mass of a monoatomic molecule is equal to its atomic mass. You have to form a compound X of
 * mass Q using the
 *
 * maximum number of molecules.
 *
 * Write an algorithm to find the maximum number of molecules that can be used to form compound X.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. atomMassA atomMassB atomMassC atomMassD targetMass
 */
public class Q36MaximumMoleculeCountForCompoundMass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int atomMassA = scanner.nextInt();
        int atomMassB = scanner.nextInt();
        int atomMassC = scanner.nextInt();
        int atomMassD = scanner.nextInt();
        int targetMass = scanner.nextInt();

        Q36MaximumMoleculeCountForCompoundMass solver =
            new Q36MaximumMoleculeCountForCompoundMass();
        System.out.println(solver.maximumMoleculeCount(
            atomMassA, atomMassB, atomMassC, atomMassD, targetMass));
    }

    public int maximumMoleculeCount(int atomMassA, int atomMassB, int atomMassC, int atomMassD,
                                    int targetMass) {
        int[] masses = {atomMassA, atomMassB, atomMassC * 2, atomMassD * 2};
        int[] dp = new int[targetMass + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int mass = 1; mass <= targetMass; mass++) {
            for (int moleculeMass : masses) {
                if (mass >= moleculeMass && dp[mass - moleculeMass] != -1) {
                    dp[mass] = Math.max(dp[mass], dp[mass - moleculeMass] + 1);
                }
            }
        }
        return dp[targetMass];
    }
}
