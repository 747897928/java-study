package com.aquarius.wizard.leetcode.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>description:  </p>
 * <p>create: 2020/10/6 21:55</p>
 * @author zhaoyijie
 * @version v1.0
 */
public class TreeExam {

    /**
     根据—棵树的中序遍历与后序遍历构造二叉树。你可以假设树中没有重复的元素。
     中序遍历inorder =[9,3,15,20,7]
     后序遍历postorder=[9,15,7,20,3]
     3
     9     20
     15   7
     */
    public static void main(String[] args) {
        BinaryTree<Integer> binaryTree = new <Integer>BinaryTree();
        List<Integer> inorder = Arrays.asList(9, 3, 15, 20, 7);
        List<Integer> postorder = Arrays.asList(9, 15, 7, 20, 3);
        binaryTree.buildTree(inorder, postorder);

        //--------------------------

        List<String> list = new ArrayList<>(6);
        list.add("陈");
        list.add("红");
        list.add("白");
        list.add("绿");
        list.add("兔");
        list.add("杨");
        List<String> list1 = new ArrayList<>(4);

        list1.add("陈.txt");
        list1.add("白.txt");
        list1.add("绿.txt");
        list1.add("红.txt");

        long startTime = System.currentTimeMillis();
        for (int j = 0; j < list1.size(); j++) {
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                String s1 = list1.get(j);
                if (s1.contains(s)) {
                    System.out.println(i + "--" + j);
                    System.out.println(s + "---" + s1);
                    list.remove(i);
                    list1.remove(j);
                    j--;
                    break;
                }
            }
        }
        /*for (int i=0;i<list.size();i++){
            for (int j=0;j<list1.size();j++){
                String s = list.get(i);
                String s1 = list1.get(j);
                if (s1.contains(s)){
                    System.out.println(i+"--"+j);
                    System.out.println(s+"---"+s1);
                    list.remove(i);
                    list1.remove(j);
                    i--;
                    break;
                }
            }
        }*/
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println(list);
        System.out.println(list1);
    }

}
