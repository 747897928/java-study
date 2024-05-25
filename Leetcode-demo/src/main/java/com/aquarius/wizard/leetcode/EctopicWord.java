package com.aquarius.wizard.leetcode;

import java.util.Arrays;

/**
 * <p>description:  </p>
 * <p>create:  2020/11/13 22:44</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class EctopicWord {
    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        /*String s = "car";
        String t= "rat";*/
        System.out.println(isEctopicWord(s, t));
    }

    /**
     * 有效的字符异位词
     * 242.有效的字母异位词
     * 给定两个字符串s和t，编写一个函数来判断t是否是s 的字母异位词。
     * 说明:
     * 你可以假设字符串只包含小写字母。
     *
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     *
     * 输入: s = "rat", t = "car"
     * 输出: false
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isEctopicWord(String s, String t) {
        char[] chars = s.toCharArray();
        char[] chart = t.toCharArray();
        int l1 = chars.length;
        int l2 = chart.length;
        if (l1 != l2) {
            return false;
        }
        int[] x = new int[26];
        for (int i = 0; i < l1; i++) {
            int c = chars[i];
            int i1 = c - 97;
            x[i1] = x[i1] + 1;
            int ts = chart[i];
            int i2 = ts - 97;
            x[i2] = x[i2] - 1;
        }
        for (int y : x) {
            if (y != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagram1(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] table = new int[26];
        for (int i = 0; i < s.length(); i++) {
            table[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            table[t.charAt(i) - 'a']--;
            if (table[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1, str2);
    }
}
