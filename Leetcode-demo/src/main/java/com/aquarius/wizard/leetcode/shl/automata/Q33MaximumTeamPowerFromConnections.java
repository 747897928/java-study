package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 33
 *
 * A random game is being played in teams by N kids, each with strength Xi. The kids stand in a
 * line with the first kid at position 1, the second at 2, and so on. A person draws M cards
 * randomly from a box, each card containing a pair of numbers that represents the position of
 * kids belonging to the same team. For example, if a card contains [1, 4] and another contains
 * [4, 3], then the kids at positions [1, 4, 3] belong to the same team. The kids whose positions
 * do not come up on any of the cards participate as one-person teams. Each team's power is
 * determined by the sum of the strengths of the kids on the team. The team with the highest power
 * wins.
 *
 * Design an algorithm that outputs the power of the winning team.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. kidCount pairCount
 * 2. kidCount strengths
 * 3. pairCount lines: positionA positionB
 */
public class Q33MaximumTeamPowerFromConnections {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int kidCount = scanner.nextInt();
        int pairCount = scanner.nextInt();
        int[] strengths = new int[kidCount];
        for (int i = 0; i < kidCount; i++) {
            strengths[i] = scanner.nextInt();
        }
        int[][] pairs = new int[pairCount][2];
        for (int i = 0; i < pairCount; i++) {
            pairs[i][0] = scanner.nextInt();
            pairs[i][1] = scanner.nextInt();
        }

        Q33MaximumTeamPowerFromConnections solver = new Q33MaximumTeamPowerFromConnections();
        System.out.println(solver.maximumTeamPower(strengths, pairs));
    }

    public long maximumTeamPower(int[] strengths, int[][] pairs) {
        DisjointSet dsu = new DisjointSet(strengths.length);
        for (int[] pair : pairs) {
            dsu.union(pair[0] - 1, pair[1] - 1);
        }

        long[] teamPower = new long[strengths.length];
        long best = 0L;
        for (int i = 0; i < strengths.length; i++) {
            int root = dsu.find(i);
            teamPower[root] += strengths[i];
            best = Math.max(best, teamPower[root]);
        }
        return best;
    }

    private static class DisjointSet {
        private final int[] parent;
        private final int[] rank;

        private DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        private int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        private void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) {
                return;
            }
            if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }
        }
    }
}
