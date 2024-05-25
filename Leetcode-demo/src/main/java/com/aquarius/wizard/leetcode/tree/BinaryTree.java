package com.aquarius.wizard.leetcode.tree;

import java.util.List;

/**
 * <p>description:  </p>
 * <p>create: 2020/10/6 22:00</p>
 * @author zhaoyijie
 * @version v1.0
 */
public class BinaryTree<T> {

    private TreeNode<T> rootNode;

    public BinaryTree() {
    }

    public TreeNode<T> getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode<T> rootNode) {
        this.rootNode = rootNode;
    }

    public void buildTree(List<T> inorder, List<T> postorder) {
        if (inorder == null || inorder.size() == 0)
            return;
        this.rootNode = createNode(inorder, 0, inorder.size() - 1, postorder, 0, postorder.size() - 1);
    }

    public TreeNode<T> createNode(List<T> inorder, int i, int j, List<T> postorder, int k, int l) {
        if (i > j)    //临界条件
            return null;
        TreeNode<T> node = new TreeNode<T>(postorder.get(l));    //后序遍历的最后一个结点是根节点
        int border = 0;            //在中序遍历中找到左右子树的分界点
        for (int p = i; p <= j; p++) {
            if (inorder.get(p) == postorder.get(l)) {
                border = p;
                break;
            }
        }
        int leftl = border - i;        //左子树的长度
        int rightl = j - border;    //右子树的长度
        node.setLeft(createNode(inorder, i, border - 1, postorder, k, k + leftl - 1));
        /*对于中序遍历应该是边界的左边，对于后序遍历应该是从开头长度为左子树长度*/
        node.setRight(createNode(inorder, border + 1, j, postorder, l - rightl, l - 1));
        /*对于中序遍历应该是边界的右边，对于后序遍历应该是从结尾-右子树长度到结尾-1*/
        return node;
    }
}
