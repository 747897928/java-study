package com.aquarius.wizard.leetcode;

import com.aquarius.wizard.leetcode.addtwonumbers.ListNode;

/**
 * @author zhaoyijie
 * @since 2024/7/12 17:28
 */
public class IntersectLinkList {

    public class ListNode {
        int val;
        ListNode next;

        public ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * https://cloud.tencent.com/developer/article/2368281
     * <p>
     * 方法四：双指针：记录链表长度
     * 同时遍历两个链表到尾部，同时记录两个链表的长度。若两个链表最后的一个节点相同，则两个链表相交。
     * 有两个链表的长度后，我们就可以知道哪个链表长，设较长的链表长度为 len1，短的链表长度为 len2。
     * 则先让较长的链表向后移动 len1-len2 个长度。然后开始从当前位置同时遍历两个链表，当遍历到的链表的节点相同时，则这个节点就是第一个相交的节点。
     * 时间复杂度：O(m+n)，两个指针同时遍历两个链表，然后再遍历两个链表至相同结点。
     * <p>
     * 方法二：哈希表
     * 判断两个链表是否相交，可以使用哈希集合存储链表节点。
     * <p>
     * 首先遍历链表 headA，将链表中的每个节点加入哈希表中。然后遍历链表 headB，判断节点是否在哈希表中。
     * <p>
     * 如果链表 headB 中的所有节点都不在哈希集合中，则两个链表不相交，返回 null。
     * <p>
     * 时间复杂度： O(m+n)，需要遍历两个链表各一次。
     * <p>
     * 空间复杂度： O(m)，需要使用哈希表存储链表 headA 的全部节点。
     * <p>
     * 方法三：双栈
     * 我们可以从头遍历两个链表。创建两个栈，第一个栈存储第一个链表的节点，第二个栈存储第二个链表的节点。每遍历到一个节点时，就将该节点入栈。两个链表都入栈结束后。
     * <p>
     * 则通过判断栈顶的节点是否相等即可判断两个单链表是否相交。因为我们知道，若两个链表相交，则从第一个相交节点开始，后面的节点都相交。
     * <p>
     * 若两链表相交，则循环出栈，直到遇到两个出栈的节点不相同，则这个节点的后一个节点就是第一个相交的节点。
     * <p>
     * 时间复杂度： O(m+n)。需要遍历两个链表各两次，一次是入栈，一次是出栈。
     * <p>
     * 空间复杂度： O(m+n)，需要使用两个栈存储链表 headA 和 headB 的所有结点。
     * 链表相交
     *
     * @param headA
     * @param headB
     * @return
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode a = headA;
        ListNode b = headB;
        //算出两链表的长度
        int lengthA = 0;
        while (a.next != null) {
            lengthA++;
            a = a.next;
        }
        int lengthB = 0;
        while (b.next != null) {
            lengthB++;
            b = b.next;
        }
        a = headA;
        b = headB;
        if (lengthA > lengthB) {
            for (int i = 0; i < lengthA - lengthB; i++) {
                a = a.next;
            }
        } else {
            for (int i = 0; i < lengthB - lengthA; i++) {
                b = b.next;
            }
        }
        while (a != null && b != null) {
            if (a == b) {
                return a;
            }
            a = a.next;
            b = b.next;
        }
        return null;
    }

    /**
     * 双指针互换遍历步骤
     * 初始化指针：创建两个指针 pA 和 pB，分别指向链表 headA 和 headB 的头节点。
     * 遍历与调整：
     * 遍历链表，每次循环更新 pA 和 pB：
     * 如果 pA 不为空，则 pA = pA.next。
     * 如果 pB 不为空，则 pB = pB.next。
     * 如果 pA 为空，则将其重置为 headB（即从头开始遍历 headB）。
     * 如果 pB 为空，则将其重置为 headA（即从头开始遍历 headA）。
     * 结束条件：当 pA 和 pB 指向同一个节点时，返回该节点（即相交节点）；如果它们都为空，则返回 null（表示链表不相交）。
     * 3. 为什么互换从头遍历后会同时到达相交结点？
     * 长度差异消除：通过互换指针并从头开始遍历，实际上是在“对齐”两个链表的遍历进度。由于链表相交后部分完全相同，无论之前两个链表如何不同，一旦进入相交部分，它们的遍历进度就会同步。
     * 数学解释：假设链表 headA 长度为 a + c，链表 headB 长度为 b + c，其中 c 是相交部分的长度。当第一次遍历完成后，如果 pA 先遍历完 headA，则它从头开始遍历 headB，此时它已经遍历了 a 个节点；
     * 同理，如果 pB 先遍历完 headB，则它从头开始遍历 headA，此时它已经遍历了 b 个节点。由于两个链表都还有 c 个节点未遍历（即相交部分），因此它们会在接下来的遍历中同时到达相交节点。
     * 4. 处理不相交链表
     * 逻辑完备性：如果两个链表不相交，则按照上述步骤，pA 和 pB 最终都会遍历完整个链表并同时到达 null。此时，可以返回 null 以表示链表不相交。
     * 5. 总结关键点
     * 使用双指针法可以有效解决链表相交问题。
     * 通过互换指针并从头遍历，可以消除链表长度差异对查找相交节点的影响。
     * 当 pA 和 pB 指向同一节点或同时到达 null 时，即可判断链表是否相交并找到相交节点。
     * https://zhuanlan.zhihu.com/p/616350156
     */

    public static ListNode getIntersectionNodeByDoublePointer(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode a = headA;
        ListNode b = headB;

        while (a != null || b != null) {
            if (a == b) {
                return a;
            }
            if (a == null) {
                a = headB;
            } else {
                a = a.next;
            }
            if (b == null) {
                b = headA;
            } else {
                b = b.next;
            }
        }
        return null;
    }

    public ListNode createList(ListNode... listNode) {
        if (listNode != null && listNode.length > 0) {
            ListNode head = listNode[0];
            ListNode cur = head;
            for (int i = 1; i < listNode.length; i++) {
                cur.next = listNode[i];
                cur = cur.next;
            }
            return head;
        } else {
            return new ListNode(0);
        }
    }

    /*@Override
    public String toString() {
        return val + "->" + next;
    }*/

    public void printList(ListNode head) {
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
}
