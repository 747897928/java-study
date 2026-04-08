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
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. kidCount pairCount
 * 2. kidCount strengths
 * 3. pairCount lines: positionA positionB
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
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

    /**
     * 这题的本质不是“随机抽卡”，而是“这些配对关系把哪些孩子连成了同一个连通块”。
     *
     * 题面说：
     *
     * - [1, 4] 表示 1 和 4 在同一队
     * - [4, 3] 表示 4 和 3 在同一队
     *
     * 那么根据传递性：
     *
     * - 1、4、3 就都在同一队
     *
     * 这其实就是并查集最典型的应用场景：
     *
     * “不断给我一些连边关系，问最后哪些点属于同一个集合”
     *
     * 所以这题可以拆成两步：
     *
     * 1. 先用并查集把所有属于同一队的孩子合并起来
     * 2. 再按每个集合的代表元，把力量值累加，取最大值
     *
     * 为什么单独出现、从来没出现在配对里的孩子也没问题？
     *
     * 因为并查集初始化时每个人自己就是自己的父节点，
     * 也就是说默认每个人都是一个单独集合。
     *
     * 如果他没参与任何 union，
     * 那他最后就自然保持为“一人队”。
     */
    public long maximumTeamPower(int[] strengths, int[][] pairs) {
        // 并查集先把“同队关系”统一合并。
        DisjointSet dsu = new DisjointSet(strengths.length);
        for (int[] pair : pairs) {
            // Question位置编号从 1 开始，数组下标从 0 开始，所以要减 1。
            dsu.union(pair[0] - 1, pair[1] - 1);
        }

        // teamPower[root] 表示“以 root 为代表元的那个队伍，目前总力量是多少”。
        long[] teamPower = new long[strengths.length];
        long best = 0L;
        for (int i = 0; i < strengths.length; i++) {
            // 找到第 i 个孩子最终属于哪个集合。
            int root = dsu.find(i);
            // 把他的力量加到对应队伍上。
            teamPower[root] += strengths[i];
            // 维护当前最大队伍力量。
            best = Math.max(best, teamPower[root]);
        }
        return best;
    }

    /**
     * 并查集 / Union-Find。
     *
     * parent[x]：
     * 表示 x 当前指向的父节点
     *
     * rank[x]：
     * 是一种近似“树高”的信息，用来做按秩合并，避免树退化太深
     *
     * 你可以先把它理解成：
     *
     * - find(x)：问 x 现在属于哪个集合
     * - union(a, b)：把 a 和 b 所在集合合并起来
     */
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

        /**
         * find 的目标是找到“集合代表元”。
         *
         * 如果 parent[x] == x，
         * 说明 x 自己就是这个集合当前的代表元。
         *
         * 路径压缩的含义是：
         *
         * 在递归返回时，顺手把沿途节点都直接挂到代表元下面，
         * 这样后面再 find 会更快。
         */
        private int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        /**
         * union 把两个集合合并。
         *
         * 先各自找根，如果根相同，
         * 说明本来就在同一个集合，不需要再做事。
         *
         * 如果根不同，就把其中一棵树挂到另一棵树下面。
         *
         * 这里用了按秩合并：
         *
         * - 矮树挂高树
         * - 如果一样高，随便挂，并把新的根 rank + 1
         *
         * 这样可以让并查集整体更扁，后续 find 更快。
         */
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
