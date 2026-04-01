package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 88
 *
 * Every member of a family owns at least one mobile phone. The head of the family keeps track of
 * all the phone accounts. She has sketched out a tree figure wherein each node of the tree
 * represents a member of the family and the value of the node represents the number of phones
 * owned by that person. The head of the family assigns herself to be the root of this tree. She
 * wishes to find the maximum number of phones owned by any single generation of family members.
 * [She wishes to find which generation of the family owns the most phones.] All the members who
 * belong to the same level of the tree are considered as belonging to the same generation.
 *
 * Write an algorithm to find the maximum number of phones owned by a single generation of family
 * members.
 *
 * Notes
 *
 * This learning version uses:
 * 1. memberCount
 * 2. memberCount phone counts
 * 3. memberCount - 1 lines of parent child
 * Root is node 0.
 */
public class Q88MaximumPhonesInSingleGeneration {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int memberCount = scanner.nextInt();
        int[] phones = new int[memberCount];
        for (int i = 0; i < memberCount; i++) {
            phones[i] = scanner.nextInt();
        }
        int[][] edges = new int[Math.max(0, memberCount - 1)][2];
        for (int i = 0; i < edges.length; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }

        Q88MaximumPhonesInSingleGeneration solver = new Q88MaximumPhonesInSingleGeneration();
        System.out.println(solver.maximumPhonesAtOneLevel(phones, edges));
    }

    public int maximumPhonesAtOneLevel(int[] phones, int[][] edges) {
        List<Integer>[] tree = new ArrayList[phones.length];
        for (int i = 0; i < phones.length; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            tree[edge[0]].add(edge[1]);
            tree[edge[1]].add(edge[0]);
        }

        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[phones.length];
        queue.offer(0);
        visited[0] = true;

        int answer = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            int levelSum = 0;
            for (int i = 0; i < size; i++) {
                int node = queue.poll();
                levelSum += phones[node];
                for (int next : tree[node]) {
                    if (!visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
            answer = Math.max(answer, levelSum);
        }
        return answer;
    }
}
