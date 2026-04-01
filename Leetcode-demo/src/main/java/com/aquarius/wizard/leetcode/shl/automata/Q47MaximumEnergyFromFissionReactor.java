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
        int[][] dp = new int[reactorVolume + 1][criticalMass + 1];
        for (int[] vial : vials) {
            int volume = vial[0];
            int mass = vial[1];
            int energy = vial[2];
            for (int usedVolume = reactorVolume; usedVolume >= volume; usedVolume--) {
                for (int usedMass = criticalMass; usedMass >= mass; usedMass--) {
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
                best = Math.max(best, dp[usedVolume][usedMass]);
            }
        }
        return best;
    }
}
