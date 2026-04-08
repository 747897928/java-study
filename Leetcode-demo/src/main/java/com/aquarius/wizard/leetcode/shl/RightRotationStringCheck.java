package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 *
 * Charlie has a magic mirror that shows the right-rotated versions of a given word. To generate
 * different right rotations of a word, the word is written in a circle in a clockwise order and
 * read it starting from any given character in a clockwise order until all the characters are
 * covered. For example, in the word "sample", if we start with 'p', we get the right rotated word
 * as "plesam".
 *
 * Write an algorithm to output 1 if the word1 is a right rotation of word2 otherwise output -1.
 *
 * Input
 *
 * The first line of the input consists of a string word1, representing the first word.
 * The second line consists of a string word2, representing the second word.
 *
 * Output
 *
 * Print 1 if the word1 is a right rotation of word2 otherwise print -1.
 *
 * Example
 *
 * Input:
 * sample
 * plesam
 *
 * Output:
 * 1
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：字符串旋转。
 * 校对：题面稳定。
 * 提示：长度相同且 word1 是 word2+word2 的子串即可。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class RightRotationStringCheck {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String word1 = scanner.nextLine();
        String word2 = scanner.nextLine();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * String word1 = "sample";
         * String word2 = "plesam";
         */

        RightRotationStringCheck solver = new RightRotationStringCheck();
        System.out.println(solver.isRightRotation(word1, word2));
    }

    public int isRightRotation(String word1, String word2) {
        /*
         * 旋转字符串题最经典的判断方式就是：
         * 如果 word1 是 word2 的某种旋转结果，
         * 那么 word1 一定会出现在 word2 + word2 里面。
         *
         * 直观脑补：
         * word2 = "plesam"
         * word2 + word2 = "plesamplesam"
         *
         * 把一个环形字符串摊平之后，所有旋转结果都会成为这条长串的某个连续子串。
         *
         * 另外，长度不同可以直接排除，
         * 因为旋转只会改变起点，不会改变字符总数。
         */
        if (word1.length() != word2.length()) {
            return -1;
        }
        String doubled = word2 + word2;
        return doubled.contains(word1) ? 1 : -1;
    }
}
