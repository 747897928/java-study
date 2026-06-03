package com.aquarius.wizard.leetcode.exam01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * It is Max's birthday today. He ordered a rectangular fruit cake which is divided into N x M pieces.
 * Each piece of the cake contains a different fruit numbered from 1 to N*M.
 * He invited K friends, each having a list of fruit choices.
 * A friend goes back happy if he gets a cake piece having a fruit of his choice.
 * Each friend can receive only one piece of cake.
 *
 * Design a way for Max to find out the maximum number of friends who can be made happy.
 *
 * Input
 * The first line consists of three space-separated integers N, M and K representing the number of rows,
 * the number of columns and the number of friends, respectively.
 * The next K lines consist of X+1 space-separated integers, where the first integer represents the count
 * of choices of the i-th friend followed by X space-separated integers representing the fruits he likes.
 *
 * Output
 * Print an integer representing the maximum number of friends who can be made happy.
 *
 * Constraints
 * 1 <= N, M <= 50
 * 0 <= K <= 3000
 * 0 <= X <= N*M
 * 1 <= i <= K
 *
 * Example
 * Input:
 * ```
 * 2 2 3
 * 3 1 2 3
 * 1 2
 * 1 1
 * ```
 * Output:
 * ```
 * 3
 * ```
 *
 * Case 1
 * Input:
 * ```
 * 3 2 6
 * 1 1
 * 1 2
 * 1 3
 * 1 4
 * 1 5
 * 1 6
 * ```
 * Expected Output:
 * ```
 * 6
 * ```
 *
 * Case 2
 * Input:
 * ```
 * 2 2 3
 * 3 1 2 3
 * 1 2
 * 1 1
 * ```
 * Expected Output:
 * ```
 * 3
 * ```
 */
public class MaximumHappyFriendsWithFruitCake {

    static List<Integer>[] choices;
    static int[] fruitOwner;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        int fruitCount = n * m;

        choices = new ArrayList[k];
        for (int i = 0; i < k; i++) {
            choices[i] = new ArrayList<>();
            int x = sc.nextInt();
            for (int j = 0; j < x; j++) {
                choices[i].add(sc.nextInt());
            }
        }

        fruitOwner = new int[fruitCount + 1];
        int happy = 0;

        for (int friend = 0; friend < k; friend++) {
            visited = new boolean[fruitCount + 1];
            // Try to assign one liked fruit to this friend.
            if (match(friend)) {
                happy++;
            }
        }

        System.out.print(happy);
    }

    static boolean match(int friend) {
        for (int fruit : choices[friend]) {
            if (visited[fruit]) {
                continue;
            }
            visited[fruit] = true;

            // Use this fruit if it is free, or move its current owner to another fruit.
            if (fruitOwner[fruit] == 0 || match(fruitOwner[fruit] - 1)) {
                fruitOwner[fruit] = friend + 1;
                return true;
            }
        }
        return false;
    }
}
