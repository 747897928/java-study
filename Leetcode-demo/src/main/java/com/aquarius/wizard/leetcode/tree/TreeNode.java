package com.aquarius.wizard.leetcode.tree;

/**
 * <p>description:  </p>
 * <p>create: 2020/10/6 21:55</p>
 * @author zhaoyijie
 * @version v1.0
 */
public class TreeNode <T>{

    private T data;

    private TreeNode<T> left;

    private TreeNode<T> right;

    public TreeNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "tree.TreeNode{" +
                "data=" + data +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
