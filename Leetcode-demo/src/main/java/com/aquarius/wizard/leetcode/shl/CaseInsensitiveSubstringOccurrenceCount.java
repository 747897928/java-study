package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;

/**
 * Question
 *
 * You are given two strings containing only English letters. Write an algorithm to count the
 * number of occurrences of the second string in the first string. (You may disregard the case of
 * the letters.)
 *
 * Input
 *
 * The first line of the input consists of a string parent, representing the first string.
 * The second line consists of a string sub, representing the second string.
 *
 * Output
 *
 * Print an integer representing the number of occurrences of sub in parent. If no occurrence of
 * sub is found in parent, then print 0.
 *
 * Example
 *
 * Input:
 * TimisplayinginthehouseofTimwiththetoysofTim
 * Tim
 *
 * Output:
 * 3
 *
 * Explanation:
 * Tim occurs 3 times in the first string.
 * So, the output is 3.
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：字符串匹配、大小写归一化。
 * 校对：样例已做代码校验。
 * 当前实现按所有起点计数，允许重叠匹配；这也是我认为最稳妥的题意理解。
 */
public class CaseInsensitiveSubstringOccurrenceCount {

    public static void main(String[] args) {
        /*
         * 这题输入是字符串，
         * 而且更自然的理解就是“按整行读 parent，再按整行读 sub”，
         * 所以这里用 nextLine()。
         *
         * 顺手记一个很容易踩坑的点：
         *
         * 如果前面刚用过 nextInt() / nextLong() / next()，
         * 后面马上接 nextLine()，
         * 那 nextLine() 很可能先读到一个空字符串。
         *
         * 原因不是 nextLine() 坏了，
         * 而是前一个 nextInt() 这类方法通常只把当前 token 读走，
         * 行尾那个换行符还留在输入流里。
         *
         * 所以后面的 nextLine() 会先把那个“剩下的空行”吃掉。
         *
         * 简单记法：
         *
         * 1. 纯数字数组、矩阵、坐标题，优先 nextInt() / nextLong()
         * 2. 整行文本题，再用 nextLine()
         * 3. 如果前面是 nextInt()，后面要切 nextLine()，通常先补一行空的 nextLine()
         */
        Scanner scanner = new Scanner(System.in);
        String parentString = scanner.nextLine();
        String subString = scanner.nextLine();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * String parentString = "TimisplayinginthehouseofTimwiththetoysofTim";
         * String subString = "Tim";
         */

        CaseInsensitiveSubstringOccurrenceCount solver = new CaseInsensitiveSubstringOccurrenceCount();
        System.out.println(solver.countOccurrences(parentString, subString));
        /*
         * 如果需要核对其他写法，可以临时打开下面这几行：
         * System.out.println(solver.countOccurrencesByRegionMatches(parentString, subString));
         * System.out.println(solver.countOccurrencesByCharArray(parentString, subString));
         */
    }

    public int countOccurrences(String parent, String sub) {
        String source = parent.toLowerCase();
        String target = sub.toLowerCase();
        int count = 0;
        for (int i = 0; i + target.length() <= source.length(); i++) {
            if (source.startsWith(target, i)) {
                count++;
            }
        }
        return count;
    }

    public int countOccurrencesByRegionMatches(String parent, String sub) {
        if (parent == null || sub == null || sub.isEmpty() || parent.length() < sub.length()) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i + sub.length() <= parent.length(); i++) {
            //regionMatches(true, i, sub, 0, sub.length()) 的意思是：
            //true：忽略大小写
            //i：从 parent 的第 i 位开始比
            //0：从 sub 的第 0 位开始比
            //sub.length()：比较这么长一段
            if (parent.regionMatches(true, i, sub, 0, sub.length())) {
                count++;
            }
        }
        return count;
    }


    public void countOccurrences2() {
        Scanner scanner = new Scanner(System.in);
        String parentString = scanner.nextLine();
        String subString = scanner.nextLine();
        System.out.println(countOccurrencesByCharArray(parentString, subString));
    }

    public int countOccurrencesByCharArray(String parent, String sub) {
        if (parent == null || sub == null || sub.isEmpty() || parent.length() < sub.length()) {
            return 0;
        }

        char[] parentChars = parent.toLowerCase().toCharArray();
        char[] subChars = sub.toLowerCase().toCharArray();
        int count = 0;

        for (int i = 0; i + subChars.length <= parentChars.length; i++) {
            boolean matched = true;
            for (int j = 0; j < subChars.length; j++) {
                if (parentChars[i + j] != subChars[j]) {
                    matched = false;
                    break;
                }
            }
            if (matched) {
                count++;
            }
        }
        return count;
    }
}
