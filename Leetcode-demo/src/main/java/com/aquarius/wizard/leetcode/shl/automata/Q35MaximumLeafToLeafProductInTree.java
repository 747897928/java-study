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
            BigInteger throughNode = values[node].multiply(firstChild).multiply(secondChild);
            if (bestPath == null || throughNode.compareTo(bestPath) > 0) {
                bestPath = throughNode;
            }
        }
        return values[node].multiply(bestChild);
    }
}
