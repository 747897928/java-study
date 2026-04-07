package com.aquarius.wizard.leetcode.shl.automata;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 35
 *
 * Arya is attempting to solve a math problem. In this problem, she is given a tree with N nodes,
 * indexed from 1 to N where the root node is indexed as 1. Each node of the tree has a defined
 * value. She wants to trace a path from one leaf to another leaf in such a way that will award
 * her the maximum score for that path. The score of a path is defined as the product of node
 * values along the path.
 *
 * Write an algorithm to find the maximum possible score.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. nodeCount
 * 2. nodeCount node values
 * 3. nodeCount - 1 lines: u v
 *
 * This version assumes node values are non-negative integers and the tree is rooted at node 1.
 */
public class Q35MaximumLeafToLeafProductInTree {

    private List<Integer>[] graph;
    private BigInteger[] values;
    private BigInteger bestPath;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int nodeCount = scanner.nextInt();
        long[] nodeValues = new long[nodeCount + 1];
        for (int i = 1; i <= nodeCount; i++) {
            nodeValues[i] = scanner.nextLong();
        }
        int[][] edges = new int[Math.max(0, nodeCount - 1)][2];
        for (int i = 0; i < nodeCount - 1; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }

        Q35MaximumLeafToLeafProductInTree solver = new Q35MaximumLeafToLeafProductInTree();
        System.out.println(solver.maximumScore(nodeValues, edges));
    }

    public BigInteger maximumScore(long[] nodeValues, int[][] edges) {
        /*
         * 这题要的是“叶子到叶子”的路径最大乘积。
         *
         * 树上的这类题，常见拆法是：
         * 1. 先定义“从当前节点往下走，到某个叶子为止”的最优值
         * 2. 再考虑“最佳路径是否刚好穿过当前节点”
         *
         * 对当前节点 node 来说：
         * - 如果只打算继续往下延伸给父节点用，那么只需要保留一个最好的子树贡献
         * - 如果想在 node 这里形成一条“叶 -> node -> 叶”的完整路径，
         *   就需要取两个最好的子树贡献，再乘上当前节点的值
         *
         * 由于是乘积，而且数值可能很大，所以这里用 BigInteger。
         */
        int nodeCount = nodeValues.length - 1;
        graph = new ArrayList[nodeCount + 1];
        values = new BigInteger[nodeCount + 1];
        for (int i = 1; i <= nodeCount; i++) {
            graph[i] = new ArrayList<>();
            values[i] = BigInteger.valueOf(nodeValues[i]);
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        BigInteger bestDown = dfs(1, 0);
        return bestPath == null ? bestDown : bestPath;
    }

    private BigInteger dfs(int node, int parent) {
        /*
         * 返回值含义：
         * 从 node 出发向下走，直到某个叶子，能够得到的最大乘积。
         *
         * 这不是完整答案，只是“向下的一条链”的最优值。
         * 真正的叶到叶答案在 bestPath 里单独维护。
         */
        BigInteger bestChild = null;
        BigInteger firstChild = null;
        BigInteger secondChild = null;
        boolean leaf = true;

        for (int next : graph[node]) {
            if (next == parent) {
                continue;
            }
            leaf = false;
            BigInteger childProduct = dfs(next, node);
            if (bestChild == null || childProduct.compareTo(bestChild) > 0) {
                bestChild = childProduct;
            }
            if (firstChild == null || childProduct.compareTo(firstChild) > 0) {
                secondChild = firstChild;
                firstChild = childProduct;
            } else if (secondChild == null || childProduct.compareTo(secondChild) > 0) {
                secondChild = childProduct;
            }
        }

        if (leaf) {
            return values[node];
        }

        if (firstChild != null && secondChild != null) {
            // 两条最优向下链在当前节点拼起来，就形成了一条完整的叶到叶路径。
            BigInteger throughNode = values[node].multiply(firstChild).multiply(secondChild);
            if (bestPath == null || throughNode.compareTo(bestPath) > 0) {
                bestPath = throughNode;
            }
        }
        // 给父节点返回时，只能选择一条向下链继续往上连。
        return values[node].multiply(bestChild);
    }
}
