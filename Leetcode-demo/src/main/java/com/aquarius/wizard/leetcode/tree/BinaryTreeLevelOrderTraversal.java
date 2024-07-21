package com.aquarius.wizard.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author zhaoyijie
 * @since 2024/7/15 17:50
 */
public class BinaryTreeLevelOrderTraversal {

    //https://leetcode.cn/problems/binary-tree-level-order-traversal/
    //102. 二叉树的层序遍历
    //给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
    //  3
    //9   20
    //  15   7
    //输入：root = [3,9,20,null,null,15,7]
    //输出：[[3],[9,20],[15,7]]
    //示例 2：
    //输入：root = [1]
    //输出：[[1]]
    //示例 3：
    //输入：root = []
    //输出：[]
    //提示：
    //树中节点数目在范围 [0, 2000] 内
    //-1000 <= Node.val <= 1000

    //Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        levelMapping(root, result, 0);
        return result;
    }

    public void levelMapping(TreeNode root, List<List<Integer>> result, int level) {
        if (root == null) {
            return;
        } else {
            if (result.size() - 1 < level) {
                result.add(new ArrayList<>());
            }
            result.get(level).add(root.val);
            TreeNode left = root.left;
            levelMapping(left, result, level + 1);
            TreeNode right = root.right;
            levelMapping(right, result, level + 1);
        }
    }

}
