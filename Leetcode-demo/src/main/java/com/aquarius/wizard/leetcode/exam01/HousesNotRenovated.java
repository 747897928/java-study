package com.aquarius.wizard.leetcode.exam01;

import java.util.Scanner;

/**
 * In a town, the houses are marked with English letters.
 * A town committee wants to renovate each house.
 * Because funds are limited, they decide to renovate only the houses marked with vowels.
 * The committee head gives the list of houses to the members and asks them to identify the houses that will not be renovated.
 *
 * Write an algorithm to help the committee members find the list of houses that will not be renovated.
 *
 * Input
 * The input consists of a string housesy representing the sequence of house markings.
 *
 * Output
 * Print a string representing the list of houses that will not be renovated.
 *
 * Constraints
 * All the house markings are English letters.
 *
 * Example
 * Input:
 * ```
 * MynameisAnthony
 * ```
 * Output:
 * ```
 * Mynmsnthny
 * ```
 *
 * Case 1
 * Input:
 * ```
 * bacdefghijklmnopqrstu
 * ```
 * Expected Output:
 * ```
 * bcdfghjklmnpqrst
 * ```
 *
 * Case 2
 * Input:
 * ```
 * bacdefgh
 * ```
 * Expected Output:
 * ```
 * bcdfgh
 * ```
 */
public class HousesNotRenovated {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String houses = sc.next();
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < houses.length(); i++) {
            char ch = houses.charAt(i);
            // Houses marked with vowels will be renovated, so skip them.
            if (!isVowel(ch)) {
                ans.append(ch);
            }
        }

        System.out.print(ans);
    }

    static boolean isVowel(char ch) {
        // Check both lowercase and uppercase English vowels.
        ch = Character.toLowerCase(ch);
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }
}
