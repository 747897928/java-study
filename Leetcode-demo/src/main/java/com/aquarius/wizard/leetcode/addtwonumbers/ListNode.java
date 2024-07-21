package com.aquarius.wizard.leetcode.addtwonumbers;

/**
 * <p>description:  </p>
 * <p>create: 2020/10/30 19:46</p>
 *
 * @author zhaoyijie
 * @version v1.0
 */

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，
 * 并且它们的每个节点只能存储 一位 数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * <p>
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class ListNode {

    public int val;

    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode createList(ListNode... listNode) {
        if (listNode != null && listNode.length > 0) {
            ListNode head = listNode[0];
            ListNode cur = head;
            for (int i = 1; i < listNode.length; i++) {
                cur.next = listNode[i];
                cur = cur.next;
            }
            return head;
        } else {
            return new ListNode();
        }
    }

    /*@Override
    public String toString() {
        return val + "->" + next;
    }*/

    public static void printList(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val);
            cur = cur.next;
            if (cur != null) {
                System.out.print("->");
            }
        }
        System.out.println();
    }

    public static class Solution {
        /*public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ArrayList<Integer> list1 = new ArrayList<>();
            list1.add(l1.val);
            while ((l1 = l1.next) != null) {
                int val = l1.val;
                list1.add(val);
            }
            ArrayList<Integer> list2 = new ArrayList<>();
            list2.add(l2.val);
            while ((l2 = l2.next) != null) {
                int val = l2.val;
                list2.add(val);
            }

            ArrayList<Integer> list3 = new ArrayList<>();

            int maxSize = Math.max(list1.size(), list2.size());
            int carry = 0;
            for (int i = maxSize - 1; i >= 0; i--) {
                int k1;
                try {
                    k1 = list1.get(i);
                } catch (Exception e) {
                    k1 = 0;
                }
                int k2;
                try {
                    k2 = list2.get(i);
                } catch (Exception e) {
                    k2 = 0;
                }
                int i1 = k1 + k2 + carry;
                int i2;
                if (i1 >= 10) {
                    i2 = i1 - 10;
                    carry = 1;
                } else {
                    carry = 0;
                    i2 = i1;
                }
                list3.add(i2);
            }
            if (carry > 0) {
                list3.add(carry);
            }
            ListNode head = null;
            ListNode tail = null;
            for (int i = 0; i < list3.size(); i++) {
                if (head == null) {
                    head = tail = new ListNode(list3.get(i));
                } else {
                    tail.next = new ListNode(list3.get(i));
                    tail = tail.next;
                }
            }
            return head;
        }*/
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode head = null, tail = null;
            int carry = 0;
            while (l1 != null || l2 != null) {
                int n1 = l1 != null ? l1.val : 0;
                int n2 = l2 != null ? l2.val : 0;
                int sum = n1 + n2 + carry;
                if (head == null) {
                    head = tail = new ListNode(sum % 10);
                } else {
                    tail.next = new ListNode(sum % 10);
                    tail = tail.next;
                }
                carry = sum / 10;
                if (l1 != null) {
                    l1 = l1.next;
                }
                if (l2 != null) {
                    l2 = l2.next;
                }
            }
            if (carry > 0) {
                tail.next = new ListNode(carry);
            }
            return head;
        }

        public static void main(String[] args) {
            Solution solution = new Solution();
            ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
            ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
            ListNode listNode = solution.addTwoNumbers(l1, l2);
            //System.out.println(listNode);
            System.out.print(listNode.val);
            while ((listNode = listNode.next) != null) {
                System.out.print("->" + listNode.val);
            }
        }
    }
}


