package com.aquarius.wizard.leetcode;

/**
 * <p>description:  </p>
 * <p>create:  2020/11/13 23:41</p>
 * @author: zhaoyijie(AquariusGenius)
 */
public class KReverseList {

    public static void main(String[] args) {
        ListNode<Integer> listNode = buildListNode(1, 2, 3, 4, 5);
        //System.out.println(listNode);
        ListNode<Integer> kReverseList = kReverseList(3, listNode);
        System.out.println(kReverseList);
    }

    /**
     * 两个排序链表，进行整合排序
     * 将链表的奇偶数按原定顺序分离，生成前半部分为奇数，后半部分为偶数的链表
     * <p>
     * 25.K个一组翻转链表
     * 给你一个链表，每k个节点一组进行翻转，请你返回翻转后的链表。
     * k是一个正整数，它的值小于或等于链表的长度。
     * 如果节点总数不是k的整数倍，那么请将最后剩余的节点保持原有顺序。
     * 说明:
     * 你的算法只能使用常数的额外空间。
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 示例:
     * 给定这个链表:1->2->3->4->5
     * 当k=2时，应当返回:2->1->4->3->5
     * 当k=3时，应当返回:3->2->1->4->5
     */
    public static <T> ListNode<T> kReverseList(int k, ListNode<T> headNode) {
        ListNode<T> preListNode = headNode;
        ListNode<T> headNodeNext = headNode.next;
        ListNode<T> currListNode = preListNode.next;
        ListNode<T> nextListNode;
        while (k-- > 0) {
            nextListNode = currListNode.next;
            currListNode.next = preListNode;
            preListNode = currListNode;
            currListNode = nextListNode;
        }
        headNodeNext.next = currListNode;
        return preListNode;
    }

    public static <T> ListNode<T> buildListNode(T... ts) {
        ListNode<T> headNode = new ListNode<>();
        ListNode<T> tailNode = headNode;
        for (T t : ts) {
            ListNode<T> currNode = new ListNode<>(t);
            if (headNode == tailNode) {
                headNode.next = currNode;
            } else {
                tailNode.next = currNode;
            }
            tailNode = currNode;
        }
        return headNode;
    }
    public static class ListNode<T> {

        T data;

        ListNode<T> next;

        public ListNode() {
        }

        public ListNode(T data) {
            this.data = data;
        }

        public ListNode(ListNode<T> next) {
            this.next = next;
        }

        public ListNode(T data, ListNode<T> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            if (next != null && next.next != null && next.next.equals(this)) {
                return "";
            }
            return data + "->" + next;
        }
    }
}
