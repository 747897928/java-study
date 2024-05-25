package com.aquarius.wizard.leetcode;


/**
 * <p>description: </p>
 * <p>create: 2021/1/23 16:43 </p>
 *
 * @author :zhaoyijie
 */
public class SparseArraySearch {
    /**
     * 稀疏数组搜索。有个排好序的字符串数组，其中散布着一些空字符串，编写一种方法，找出给定字符串的位置。
     * 示例1:输入: words = ["at", "", "", "", "ball", "", "", "car", "", "","dad","", ""], s = "ta"
     * 输出：-1 说明: 不存在返回-1。
     * 示例2:输入：words = ["at", "", "", "", "ball", "", "", "car", "", "","dad", "", ""], s = "ball"
     * 输出：4 提示: words的长度在[1, 1000000]之间。
     */
    public int findString(String[] words, String s) {
        int left = 0;
        int right = words.length - 1;
        return find(words, left, right, s);
    }

    int find(String[] words, int left, int right, String s) {
        while (left < right && words[left].equals("")) // 1.左右缩界，直到找到非空String
        {
            left++;
        }

        while (left < right && words[right].equals("")) {
            right--;
        }
        if (s.compareTo(words[right]) > 0 || s.compareTo(words[left]) < 0) return -1;
        //1.1若左边界的值都大于String，说明数组中不存在此s
        //1.2同理，若右边界的值都小于String，说明数组中不存在此s

        int mid = left + (right - left) / 2;//2.寻找中间节点，促使二分法进行

        while (mid > left && words[mid].equals("")) //2.1防止中间节点为空，向左找一直找到非空的word【mid】
        {
            mid--;
        }
        if (words[mid].equals(s)) return mid; //2.2若与s相同，则说明找到了解，返回mid

        int co = s.compareTo(words[mid]);

        if (co < 0) //3 根据比较结果决定向左或向右查找
            return find(words, left, mid - 1, s);
        if (co > 0)
            return find(words, mid + 1, right, s);

        return -1;//左右都没有返回值，说明无解，返回
    }
}
