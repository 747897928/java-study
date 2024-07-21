package com.aquarius.wizard.leetcode.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaoyijie
 * @since 2024/7/15 21:57
 */
public class NAryTreeLevelOrderTraversal {

    // Definition for a Node.
    public class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    //N 叉树的层序遍历
    //给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。
    //树的序列化输入是用层序遍历，每组子节点都由 null 值分隔（参见示例）。
    //示例 1：
    //输入：root = [1,null,3,2,4,null,5,6]
    //输出：[[1],[3,2,4],[5,6]]
    //示例 2：
    //输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
    //输出：[[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]
    //提示：
    //树的高度不会超过 1000
    //树的节点总数在 [0, 104] 之间
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        levelMapping(Collections.singletonList(root), result, 0);
        return result;
    }

    public void levelMapping(List<Node> roots, List<List<Integer>> result, int level) {
        if (roots == null || roots.isEmpty()) {
            return;
        } else {
            if (result.size() - 1 < level) {
                result.add(new ArrayList<>());
            }
            for (Node root : roots) {
                if (root == null) {
                    continue;
                }
                result.get(level).add(root.val);
                List<Node> children = root.children;
                levelMapping(children, result, level + 1);
            }
        }
    }

    public List<List<Integer>> levelOrder2(Node root) {
        if (root == null) {
            return new ArrayList<List<Integer>>();
        }

        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        Queue<Node> queue = new ArrayDeque<Node>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int cnt = queue.size();
            List<Integer> level = new ArrayList<Integer>();
            for (int i = 0; i < cnt; ++i) {
                Node cur = queue.poll();
                level.add(cur.val);
                for (Node child : cur.children) {
                    queue.offer(child);
                }
            }
            ans.add(level);
        }

        return ans;
    }
}
