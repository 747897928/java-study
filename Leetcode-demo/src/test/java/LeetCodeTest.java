import com.aquarius.wizard.leetcode.IntersectLinkList;
import com.aquarius.wizard.leetcode.sort.SelectionSort;
import com.aquarius.wizard.leetcode.sort.SortBase;
import com.aquarius.wizard.leetcode.twopointer.ContainerWithMostWater;
import com.aquarius.wizard.leetcode.twopointer.LongNoRepeatChar;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author zhaoyijie
 * @since 2024/5/25 13:48
 */
public class LeetCodeTest {

    @Test
    public void test1() {
        int[] arr = new int[]{9, 6, 3, 4, 5, 2, 7, 8, 1};
        SortBase sort = new SelectionSort();
        int[] ints = sort.sort(arr);
        System.out.println(Arrays.toString(ints));
    }

    @Test
    public void test2() {
        IntersectLinkList intersectLinkList = new IntersectLinkList();
        IntersectLinkList.ListNode node1 = intersectLinkList.new ListNode(1);
        IntersectLinkList.ListNode node2 = intersectLinkList.new ListNode(2);
        IntersectLinkList.ListNode node3 = intersectLinkList.new ListNode(3);
        IntersectLinkList.ListNode node4 = intersectLinkList.new ListNode(4);
        IntersectLinkList.ListNode node5 = intersectLinkList.new ListNode(5);
        IntersectLinkList.ListNode node6 = intersectLinkList.new ListNode(6);
        IntersectLinkList.ListNode node7 = intersectLinkList.new ListNode(7);
        IntersectLinkList.ListNode node8 = intersectLinkList.new ListNode(8);
        IntersectLinkList.ListNode headA = intersectLinkList.createList(node6, node7, node1, node2, node3);
        IntersectLinkList.ListNode headB = intersectLinkList.createList(node8, node4, node5, node1);
        intersectLinkList.printList(headA);
        intersectLinkList.printList(headB);
        IntersectLinkList.ListNode intersectionNode = IntersectLinkList.getIntersectionNode(headA, headB);
        intersectLinkList.printList(intersectionNode);
    }

    @Test
    public void test3() {
        IntersectLinkList intersectLinkList = new IntersectLinkList();
        IntersectLinkList.ListNode node1 = intersectLinkList.new ListNode(1);
        IntersectLinkList.ListNode node2 = intersectLinkList.new ListNode(2);
        IntersectLinkList.ListNode node3 = intersectLinkList.new ListNode(3);
        IntersectLinkList.ListNode node4 = intersectLinkList.new ListNode(4);
        IntersectLinkList.ListNode node5 = intersectLinkList.new ListNode(5);
        IntersectLinkList.ListNode node6 = intersectLinkList.new ListNode(6);
        IntersectLinkList.ListNode node7 = intersectLinkList.new ListNode(7);
        IntersectLinkList.ListNode node8 = intersectLinkList.new ListNode(8);
        IntersectLinkList.ListNode headA = intersectLinkList.createList(node6, node7, node1, node2, node3);
        IntersectLinkList.ListNode headB = intersectLinkList.createList(node8, node4, node5, node1);
        intersectLinkList.printList(headA);
        intersectLinkList.printList(headB);
        IntersectLinkList.ListNode intersectionNode = IntersectLinkList.getIntersectionNodeByDoublePointer(headA, headB);
        intersectLinkList.printList(intersectionNode);

    }

    @Test
    public void test4() {
        ContainerWithMostWater containerWithMostWater = new ContainerWithMostWater();
        System.out.println(containerWithMostWater.maxArea(new int[]{1, 1}));
        System.out.println(containerWithMostWater.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        System.out.println(containerWithMostWater.maxArea2(new int[]{1, 1}));
        System.out.println(containerWithMostWater.maxArea2(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    @Test
    public void test5() {
        LongNoRepeatChar longNoRepeatChar = new LongNoRepeatChar();
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring("pwwkew"));
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring("abba"));
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring(" "));

        System.out.println(longNoRepeatChar.lengthOfLongestSubstring2("abcabcbb"));
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring2("pwwkew"));
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring2("abba"));
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring2(" "));

        System.out.println(longNoRepeatChar.lengthOfLongestSubstring3("abcabcbb"));
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring3("pwwkew"));
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring3("abba"));
        System.out.println(longNoRepeatChar.lengthOfLongestSubstring3(" "));
    }
}
