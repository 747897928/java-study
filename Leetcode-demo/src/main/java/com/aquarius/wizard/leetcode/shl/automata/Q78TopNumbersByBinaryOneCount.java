package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 78
 *
 * An alien mothership is trying to communicate with Earth. It follows a particular handshake
 * mechanism to initiate the conversation. It sends a stream of numbers to Earth and expects a
 * particular set of numbers in return, to complete the handshake. If the stream contains N
 * numbers, then Earth needs to return the top M numbers from the stream, such that those M numbers
 * contain the highest number of 1s when represented in their binary form. If two numbers contain
 * the same number of 1s in their binary form, the larger number (in magnitude) should be selected
 * first.
 *
 * Design an algorithm that will help carry out this communication.
 *
 * Notes
 *
 * This learning version uses:
 * 1. numberCount takeCount
 * 2. numberCount integers
 */
public class Q78TopNumbersByBinaryOneCount {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberCount = scanner.nextInt();
        int takeCount = scanner.nextInt();
        Integer[] numbers = new Integer[numberCount];
        for (int i = 0; i < numberCount; i++) {
            numbers[i] = scanner.nextInt();
        }

        Q78TopNumbersByBinaryOneCount solver = new Q78TopNumbersByBinaryOneCount();
        System.out.println(solver.selectTop(numbers, takeCount));
    }

    public String selectTop(Integer[] numbers, int takeCount) {
        Arrays.sort(numbers, (a, b) -> {
            int bitA = Integer.bitCount(Math.abs(a));
            int bitB = Integer.bitCount(Math.abs(b));
            if (bitA != bitB) {
                return Integer.compare(bitB, bitA);
            }
            return Integer.compare(Math.abs(b), Math.abs(a));
        });

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < Math.min(takeCount, numbers.length); i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(numbers[i]);
        }
        return builder.toString();
    }
}
