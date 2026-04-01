package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 11
 *
 * In a car racing game, the participating cars must be registered online prior to the game. A car
 * is assigned a registration number that is stored in a database. The registration number
 * consists of digits from 0-9. The registration number can be positive or negative. A negative
 * registration number denotes that the car is already registered online whereas a positive
 * registration number denotes that the car is a newly registered car. Before the game starts, the
 * system automatically assigns a track number to each car. The track number is the smallest
 * permutation of the car registration number and never starts with zero.
 *
 * Write an algorithm to generate the track number.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. registrationNumber
 */
public class Q11SmallestPermutationTrackNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long registrationNumber = scanner.nextLong();

        /*
         * Local practice input:
         *
         * long registrationNumber = -31045L;
         */

        Q11SmallestPermutationTrackNumber solver = new Q11SmallestPermutationTrackNumber();
        System.out.println(solver.smallestPermutation(registrationNumber));
    }

    public long smallestPermutation(long registrationNumber) {
        if (registrationNumber == 0) {
            return 0L;
        }

        boolean negative = registrationNumber < 0;
        char[] digits = Long.toString(Math.abs(registrationNumber)).toCharArray();
        Arrays.sort(digits);

        if (negative) {
            reverse(digits);
            return -Long.parseLong(new String(digits));
        }

        int firstNonZero = 0;
        while (firstNonZero < digits.length && digits[firstNonZero] == '0') {
            firstNonZero++;
        }
        char first = digits[firstNonZero];
        digits[firstNonZero] = digits[0];
        digits[0] = first;
        return Long.parseLong(new String(digits));
    }

    private void reverse(char[] digits) {
        int left = 0;
        int right = digits.length - 1;
        while (left < right) {
            char tmp = digits[left];
            digits[left] = digits[right];
            digits[right] = tmp;
            left++;
            right--;
        }
    }
}
