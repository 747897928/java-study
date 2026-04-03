package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 * 
 * The vowels in the English alphabet are: (a, e, i, o, u, A, E, I, O, U). Write an algorithm to
 * eliminate all vowels from a given string.
 * 
 * Input
 * 
 * The input consists of the given string.
 * 
 * Output
 * 
 * Print a string after removing all the vowels from the given string.
 * 
 * Example
 * 
 * Input:
 * MynameisAnthony
 * 
 * Output:
 * Mynmsnthny
 * 
 * 备注
 * 
 * 难度：简单。
 * 
 * 考点：字符过滤。
 * 校对：题面稳定。
 * 提示：这是非常适合拿来熟悉英文题面动词 remove / eliminate / filter 的题。
 */
public class RemoveVowelsFromString {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String source = scanner.nextLine();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * String source = "MynameisAnthony";
         */

        RemoveVowelsFromString solver = new RemoveVowelsFromString();
        System.out.println(solver.removeVowels(source));
        System.out.println(solver.removeVowels2(source));
    }

    public String removeVowels(String input) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (!isVowel(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    private boolean isVowel(char ch) {
        switch (ch) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
                return true;
            default:
                return false;
        }
    }

    public String removeVowels2(String input) {
        StringBuilder builder = new StringBuilder();
        char inputChars[] = input.toCharArray();
        for (int i = 0; i < inputChars.length; i++) {
            char currentChars = inputChars[i];
            if (currentChars == 'a') {
                continue;
            } else if (currentChars == 'e') {
                continue;
            } else if (currentChars == 'i') {
                continue;
            } else if (currentChars == 'o') {
                continue;
            } else if (currentChars == 'u') {
                continue;
            } else if (currentChars == 'A') {
                continue;
            } else if (currentChars == 'E') {
                continue;
            } else if (currentChars == 'I') {
                continue;
            } else if (currentChars == 'O') {
                continue;
            } else if (currentChars == 'U') {
                continue;
            } else {
                builder.append(currentChars);
            }
        }
        return builder.toString();
    }

}
