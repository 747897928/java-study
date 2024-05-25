package com.aquarius.wizard.leetcode;

/**
 * <p>description:  </p>
 * <p>create:  2020/11/13 21:28</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class ReverseStringOrder {
    public static void main(String[] args) {
        String s = "夜雨";
        String s1 = reverseString(s);
        System.out.println(s1);
    }

    /**
     *
     * 242.有效的字母异位词
     * 给定两个字符串s和t，编写一个函数来判断t是否是s的字母异位词。
     * 说明:
     * 你可以假设字符串只包含小写字母。
     */

    /**
     * 字符串倒置
     *
     * @param s
     * @return
     */
    public static String reverseString(String s) {
        char[] chars = s.toCharArray();
        char tempChar;
        int length = chars.length;
        int i1 = length - 1;
        for (int i = 0; i < length; i++) {
            int i2 = i1 - i;
            if (i2 == i || i > i2) {
                break;
            }
            tempChar = chars[i];
            chars[i] = chars[i2];
            chars[i2] = tempChar;
        }
        return new String(chars);
    }
}
