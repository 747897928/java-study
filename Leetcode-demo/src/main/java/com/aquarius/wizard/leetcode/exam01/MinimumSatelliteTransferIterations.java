package com.aquarius.wizard.leetcode.exam01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The space organization NAISRO has N satellites in orbit. The satellites are assigned IDs from 0 to N-1.
 * A satellite can transfer data to any other satellite that lies within its bandwidth range.
 *
 * A team at NAISRO is working on a project involving satellite communications.
 * They wish to transfer data from the main satellite with ID 0 to all the other satellites.
 * Because the transfer of data requires an enormous amount of power, a satellite can transfer data to only one
 * other satellite at a time. Only a fixed number of satellites can be in the bandwidth range of a satellite at a time.
 * The team wishes to determine the minimum number of iterations of data transfer necessary to connect all the satellites.
 *
 * Write an algorithm to help the NAISRO team determine the minimum number of iterations of data transfer necessary
 * to connect all the satellites.
 *
 * Input
 * The first line consists of two space-separated integers N and M, representing the number of satellites and the
 * maximum number of satellites that can be connected to one satellite.
 * The next N-1 lines consist of two space-separated integers representing the satellite that can transfer data
 * and the satellite that can receive the data, respectively.
 *
 * Output
 * Return an integer representing the minimum number of iterations of data transfer necessary to connect all the satellites.
 *
 * Constraints
 * 0 < N <= 10^4
 * 0 < M < N
 *
 * Example
 * Input:
 * ```
 * 9 3
 * 0 1
 * 0 2
 * 0 3
 * 1 4
 * 2 5
 * 2 6
 * 2 7
 * 3 8
 * ```
 * Output:
 * ```
 * 4
 * ```
 *
 * Case 1
 * Input:
 * ```
 * 8 3
 * 0 1
 * 0 2
 * 0 3
 * 1 4
 * 2 5
 * 2 6
 * 3 7
 * ```
 * Expected Output:
 * ```
 * 4
 * ```
 *
 * Case 2
 * Input:
 * ```
 * 13 3
 * 0 1
 * 0 2
 * 0 3
 * 1 4
 * 1 5
 * 2 6
 * 2 7
 * 2 8
 * 7 9
 * 7 10
 * 7 11
 * 8 12
 * ```
 * Expected Output:
 * ```
 * 5
 * ```
 */
public class MinimumSatelliteTransferIterations {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph[a].add(b);
            graph[b].add(a);
        }

        int[] parent = new int[n];
        int[] order = new int[n];
        Arrays.fill(parent, -1);

        // Build a traversal order from satellite 0.
        int size = 0;
        order[size++] = 0;
        parent[0] = -2;
        for (int i = 0; i < size; i++) {
            int node = order[i];
            for (int next : graph[node]) {
                if (parent[next] == -1) {
                    parent[next] = node;
                    order[size++] = next;
                }
            }
        }

        int[] time = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int node = order[i];
            List<Integer> childTimes = new ArrayList<>();

            for (int next : graph[node]) {
                if (parent[next] == node) {
                    childTimes.add(time[next]);
                }
            }

            // Send data first to children that need more time later.
            childTimes.sort(Collections.reverseOrder());
            for (int j = 0; j < childTimes.size(); j++) {
                time[node] = Math.max(time[node], childTimes.get(j) + j + 1);
            }
        }

        System.out.print(time[0]);
    }
}
