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
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
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
        /*
         * 如果需要核对更“展开版”的写法，可以临时打开下面这行：
         * System.out.println(solver.removeVowels2(source));
         */
    }

    public String removeVowels(String input) {
        /*
         * 这是典型的“字符过滤”题。
         *
         * 思路不是去想“怎么删除字符”，而是换个角度：
         * 从左到右扫描原串，只把“我想保留的字符”收集到答案里。
         *
         * 这样写有两个好处：
         * 1. 不需要在原字符串里做删除操作，逻辑更直。
         * 2. String 不可变，StringBuilder 逐步 append 更自然。
         */
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
        /*
         * 这里把“元音判断”单独抽成一个方法，
         * 主流程就只保留“不是元音就加入答案”这一层意思，读起来更像题意本身。
         */
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
        /*
         * 这个版本和 removeVowels 的本质一样，
         * 只是把 isVowel(ch) 的判断完全展开写在循环里。
         *
         * 学习时可以对照着看：
         * - 第一个版本更适合提交和复用
         * - 第二个版本更像“把条件判断摊开写给自己看”
         */
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
