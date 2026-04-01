package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 20
 *
 * In a connected graph, a path runs between every node. This path does not need to be an edge
 * directly connecting the nodes. An adjacency matrix for a graph with n vertices is an n x n
 * two-dimensional matrix with i j entry as 1 if there is an edge from the ith vertex to the jth
 * vertex; otherwise it is 0.
 *
 * An undirected connected graph is given in the adjacency matrix form. Write an algorithm to
 * determine whether it is a tree.
 *
 * For example, the result for the adjacency matrix given below should be 1 as it represents a
 * tree.
 *
 * 0 1 0 1
 * 1 0 1 0
 * 0 1 0 0
 * 1 0 0 0
 */
public class Q20IsAdjacencyMatrixATree {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] adjacencyMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjacencyMatrix[i][j] = scanner.nextInt();
            }
        }

        Q20IsAdjacencyMatrixATree solver = new Q20IsAdjacencyMatrixATree();
        System.out.println(solver.isTree(adjacencyMatrix) ? 1 : 0);
    }

    public boolean isTree(int[][] adjacencyMatrix) {
        int n = adjacencyMatrix.length;
        int edgeCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    edgeCount++;
                }
            }
        }
        if (edgeCount != n - 1) {
            return false;
        }

        boolean[] visited = new boolean[n];
        dfs(0, adjacencyMatrix, visited);
        for (boolean state : visited) {
            if (!state) {
                return false;
            }
        }
        return true;
    }

    private void dfs(int node, int[][] adjacencyMatrix, boolean[] visited) {
        visited[node] = true;
        for (int next = 0; next < adjacencyMatrix.length; next++) {
            if (adjacencyMatrix[node][next] == 1 && !visited[next]) {
                dfs(next, adjacencyMatrix, visited);
            }
        }
    }
}
