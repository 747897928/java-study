package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 37
 *
 * Today is Max's birthday. He has ordered a rectangular fruit cake which is divided into N x M
 * pieces. Each piece of the cake contains a different fruit numbered from 1 to N*M. He has
 * invited K friends, each of whom have brought a list of their favorite fruit choices. A friend
 * goes home happy if the piece he receives is of his favorite fruit. Note that each friend can
 * receive only one piece of cake.
 *
 * Design a way for Max to find the maximum number of friends he can make happy.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. rows cols friendCount
 * 2. for each friend: favoriteCount followed by that many fruit IDs
 */
public class Q37MaximumHappyFriendsWithFruitCake {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int friendCount = scanner.nextInt();
        List<int[]> favoriteLists = new ArrayList<>();
        for (int i = 0; i < friendCount; i++) {
            int favoriteCount = scanner.nextInt();
            int[] favorites = new int[favoriteCount];
            for (int j = 0; j < favoriteCount; j++) {
                favorites[j] = scanner.nextInt();
            }
            favoriteLists.add(favorites);
        }

        Q37MaximumHappyFriendsWithFruitCake solver = new Q37MaximumHappyFriendsWithFruitCake();
        System.out.println(solver.maximumHappyFriends(rows * cols, favoriteLists));
    }

    public int maximumHappyFriends(int fruitCount, List<int[]> favoriteLists) {
        int[] matchedFriend = new int[fruitCount + 1];
        int happyFriends = 0;
        for (int friend = 0; friend < favoriteLists.size(); friend++) {
            boolean[] visited = new boolean[fruitCount + 1];
            if (augment(friend, favoriteLists, matchedFriend, visited)) {
                happyFriends++;
            }
        }
        return happyFriends;
    }

    private boolean augment(int friend, List<int[]> favoriteLists, int[] matchedFriend,
                            boolean[] visited) {
        for (int fruitId : favoriteLists.get(friend)) {
            if (fruitId <= 0 || fruitId >= visited.length || visited[fruitId]) {
                continue;
            }
            visited[fruitId] = true;
            if (matchedFriend[fruitId] == 0
                || augment(matchedFriend[fruitId] - 1, favoriteLists, matchedFriend, visited)) {
                matchedFriend[fruitId] = friend + 1;
                return true;
            }
        }
        return false;
    }
}
