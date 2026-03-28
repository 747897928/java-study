package com.aquarius.wizard.leetcode.shl;

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
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：字符串旋转。
 * 校对：题面稳定。
 * 提示：长度相同且 word1 是 word2+word2 的子串即可。
 */
public class RightRotationStringCheck {

    public int isRightRotation(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return -1;
        }
        String doubled = word2 + word2;
        return doubled.contains(word1) ? 1 : -1;
    }
}
