package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 47
 *
 * Dr. Victor Frankenstein has quit the monster creation business. Nuclear energy now strikes his
 * fantasy. He has created a fission reactor that takes radioactive materials in a liquid state.
 * The capacity of the reactor is 'V' gallons. He has 'N' vials of radioactive liquids, each with
 * some mass and some volume. Some units of energy are produced when a liquid is poured into the
 * reactor. Victor would like to maximize the energy output. However, there is a catch. Upon
 * studying the physics and history of atomic elements, he realizes that the combined mass of the
 * radioactive liquids inside the reactor must not exceed a certain critical mass 'M' or else the
 * reaction would get out of control and cause a violent explosion.
 *
 * Write an algorithm that will help Victor get the maximum energy from the reactor without losing
 * his life.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. vialCount reactorVolume criticalMass
 * 2. vialCount lines: vialVolume vialMass energyProduced
 */
public class Q47MaximumEnergyFromFissionReactor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int vialCount = scanner.nextInt();
        int reactorVolume = scanner.nextInt();
        int criticalMass = scanner.nextInt();
        int[][] vials = new int[vialCount][3];
        for (int i = 0; i < vialCount; i++) {
            vials[i][0] = scanner.nextInt();
            vials[i][1] = scanner.nextInt();
            vials[i][2] = scanner.nextInt();
        }

        Q47MaximumEnergyFromFissionReactor solver = new Q47MaximumEnergyFromFissionReactor();
        System.out.println(solver.maximumEnergy(vials, reactorVolume, criticalMass));
    }

    public int maximumEnergy(int[][] vials, int reactorVolume, int criticalMass) {
        /*
         * 这题不是普通的一维背包，而是两个限制同时存在：
         * - 总体积不能超过 reactorVolume
         * - 总质量不能超过 criticalMass
         *
         * 所以状态自然要写成二维：
         * dp[usedVolume][usedMass]
         * 表示在占用了这些体积和质量之后，最多能得到多少能量。
         *
         * 每个 vial 最多只能选一次，因此这是“二维 0/1 背包”。
         * 所以内层循环必须倒着枚举，防止同一个 vial 在一轮里被重复使用。
         */
        int[][] dp = new int[reactorVolume + 1][criticalMass + 1];
        for (int[] vial : vials) {
            int volume = vial[0];
            int mass = vial[1];
            int energy = vial[2];
            for (int usedVolume = reactorVolume; usedVolume >= volume; usedVolume--) {
                for (int usedMass = criticalMass; usedMass >= mass; usedMass--) {
                    // 选或不选当前 vial，取能量更大的方案。
                    dp[usedVolume][usedMass] = Math.max(
                        dp[usedVolume][usedMass],
                        dp[usedVolume - volume][usedMass - mass] + energy
                    );
                }
            }
        }

        int best = 0;
        for (int usedVolume = 0; usedVolume <= reactorVolume; usedVolume++) {
            for (int usedMass = 0; usedMass <= criticalMass; usedMass++) {
                // 只要没有超过两个上限，这个状态就是合法状态。
                best = Math.max(best, dp[usedVolume][usedMass]);
            }
        }
        return best;
    }
}
