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
        /*
         * 题目要的是：
         * 在总质量恰好等于 targetMass 的前提下，分子个数尽量多。
         *
         * 这和“凑金额时硬币数量最多/最少”是同一类完全背包题。
         * 区别只是这里的“物品”变成了 4 种分子，它们可以重复使用。
         *
         * 先把四种分子的实际质量统一算出来：
         * - A、B 是单原子，质量就是 atomMass
         * - C、D 是双原子，质量要乘 2
         *
         * dp[mass] 表示：
         * 恰好凑出 mass 时，最多能用多少个分子。
         *
         * 如果某个质量无法凑出，就记成 -1。
         */
        int[] masses = {atomMassA, atomMassB, atomMassC * 2, atomMassD * 2};
        int[] dp = new int[targetMass + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int mass = 1; mass <= targetMass; mass++) {
            for (int moleculeMass : masses) {
                if (mass >= moleculeMass && dp[mass - moleculeMass] != -1) {
                    // 先凑出更小的质量，再补上当前这个分子。
                    dp[mass] = Math.max(dp[mass], dp[mass - moleculeMass] + 1);
                }
            }
        }
        return dp[targetMass];
    }
}
