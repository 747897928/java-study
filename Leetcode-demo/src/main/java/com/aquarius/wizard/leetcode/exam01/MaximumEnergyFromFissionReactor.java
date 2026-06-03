package com.aquarius.wizard.leetcode.exam01;

import java.util.Scanner;


/**
 *
 * Dr. Victor Frankenstein has quit the monster creation business. Nuclear energy now strikes his fantasy. He has created a fission reactor that takes radioactive materials in a liquid state. The capacity of the reactor is 'V' gallons. He has 'N' vials of radioactive liquids, each with some mass and some volume. Some units of energy are produced when a liquid is poured into the reactor. Victor would like to maximize the energy output. However, there is a catch. Upon studying the physics and history of atomic elements, he realizes that the combined mass of the radioactive liquids inside the reactor must not exceed a certain critical mass 'M' or else the reaction would get out of control and cause a violent explosion.
 * Write an algorithm that will help Victor get the maximum energy from the reactor without losing his life.
 * <p>
 * Input
 * The first line of the input consists of three space-separated integers - radioCap, numLiq and mass, representing the reactor capacity(RC), the number of radioactive liquids(N) and the critical mass of the reactor(CM), respectively.
 * The next line consists of N space-separated integers representing the volumes of N liquids in order.
 * The next line consists of N space-separated integers representing the masses of N liquids in order.
 * The next line consists of N space-separated integers representing the energy produced by N liquids in order.
 * <p>
 * Output
 * Print an integer which is the maximum energy that can be generated from the reactor under the given constraints.
 * <p>
 * Constraints
 * 1 ≤ numLiq ≤ 10⁴
 * <p>
 * Example
 * Input:
 * <p>
 * text
 * 100 5 15
 * 50 40 30 20 10
 * 1 2 3 9 5
 * 300 480 270 200 180
 * Output:
 * <p>
 * text
 * 960
 * Explanation:
 * By selecting liquids from vials number- 1, 2, 5, the energy produced is= 300+480+180=960.
 * This combination of liquid contributed to the total volume=50+40+10=100, which is not greater than reactor capacity; and contributed to the total mass in the reactor=1+2+5=8, which is not greater than criticalMass.
 *
 * @author zhaoyijie
 * @since 2026/5/26 22:32
 */
public class MaximumEnergyFromFissionReactor {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int radioCap = sc.nextInt();
        int numLiq = sc.nextInt();
        int mass = sc.nextInt();

        int[] volume = new int[numLiq];
        int[] weight = new int[numLiq];
        int[] energy = new int[numLiq];

        for (int i = 0; i < numLiq; i++) {
            volume[i] = sc.nextInt();
        }
        for (int i = 0; i < numLiq; i++) {
            weight[i] = sc.nextInt();
        }
        for (int i = 0; i < numLiq; i++) {
            energy[i] = sc.nextInt();
        }

        System.out.print(maxEnergy(radioCap, mass, volume, weight, energy));
    }

    public static int maxEnergy(int radioCap, int mass, int[] volume, int[] weight, int[] energy) {
        int[][] dp = new int[radioCap + 1][mass + 1];

        for (int i = 0; i < volume.length; i++) {
            for (int v = radioCap; v >= volume[i]; v--) {
                for (int m = mass; m >= weight[i]; m--) {
                    dp[v][m] = Math.max(dp[v][m], dp[v - volume[i]][m - weight[i]] + energy[i]);
                }
            }
        }

        return dp[radioCap][mass];
    }


    public void solution2() {
        Scanner scanner = new Scanner(System.in);

        // 读取：反应堆容量、液体数量、临界质量
        int reactorCap = scanner.nextInt();
        int numLiq = scanner.nextInt();
        int criticalMass = scanner.nextInt();

        int[] volumes = new int[numLiq];
        int[] masses = new int[numLiq];
        int[] energies = new int[numLiq];

        // 读取三个数组
        for (int i = 0; i < numLiq; i++) volumes[i] = scanner.nextInt();
        for (int i = 0; i < numLiq; i++) masses[i] = scanner.nextInt();
        for (int i = 0; i < numLiq; i++) energies[i] = scanner.nextInt();

        // 二维 DP 数组：dp[容量][质量] = 最大能量
        int[][] dp = new int[reactorCap + 1][criticalMass + 1];

        for (int i = 0; i < numLiq; i++) {
            int v = volumes[i];
            int m = masses[i];
            int e = energies[i];
            // 从后往前遍历（0-1背包）
            for (int cap = reactorCap; cap >= v; cap--) {
                for (int mass = criticalMass; mass >= m; mass--) {
                    dp[cap][mass] = Math.max(dp[cap][mass], dp[cap - v][mass - m] + e);
                }
            }
        }

        System.out.println(dp[reactorCap][criticalMass]);
        scanner.close();
    }
}
