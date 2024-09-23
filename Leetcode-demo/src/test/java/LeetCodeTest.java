import com.aquarius.wizard.leetcode.IntersectLinkList;
import com.aquarius.wizard.leetcode.sort.SelectionSort;
import com.aquarius.wizard.leetcode.sort.SortBase;
import com.aquarius.wizard.leetcode.twopointer.ContainerWithMostWater;
import com.aquarius.wizard.leetcode.twopointer.LongNoRepeatChar;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    @Test
    public void test6() {
        Supplier<String> supplier = () -> "Car";
        Consumer<String> consumer = x -> System.out.print(x.toLowerCase());
        Consumer<String> consumer2 = x -> System.out.print(x.toUpperCase());
        Consumer<String> consumer3 = x -> {
            System.out.print(x.toString());
        };
        consumer.andThen(consumer2).accept(supplier.get());
        System.out.println();
    }

    @Test
    public void test7() {
        Long x = 1L;
        Long y = x;
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println(x == y);
        x++;
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println(x == y);

        System.out.println("******");
        long k = 1L;
        long j = k;
        System.out.println("k = " + k);
        System.out.println("j = " + j);
        System.out.println(k == j);
        k++;
        System.out.println("k = " + k);
        System.out.println("j = " + j);
        System.out.println(k == j);

        System.out.println("******");
        boolean b = false;
        int n = 5;
        System.out.println(b || n == 5);
        System.out.println(b = true && n == 5);
        boolean b1 = (b = true);
        System.out.println(b1);
        System.out.println(b);
    }

    /*public static class A {
        static int total = 10;
        public void print() {
            int total = 5;
            //Static member 'LeetCodeTest.A.total' accessed via instance reference
            System.out.println(this.total);//10
        }
    }*/

    @Test
    public void test8() throws IOException {
        File file = new File("../StudyNode/面试话术.md");
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write("Hello World");
        fileWriter.write(new char[]{'0', '1', '2'});
        fileWriter.flush();
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println("Hello World");
        printWriter.flush();
        fileWriter.close();
        printWriter.close();
    }

    @Test
    public void test9() throws InterruptedException {
        int count = 0;
        //identity在累加器中的作用是提供一个初始值,这里初始值我们给count，
        // 也就是0，如果给1，那么得到的结果是21，
        // 如果给0，那么得到的结果是20
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, count);
        int threadCount = 20;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                accumulator.accumulate(1);
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(accumulator.get());
    }

    /*interface MonthDayHelper {
        default MonthDay constructor(int month, int day) {
            return MonthDay.of(month, day);
        }
    }

    interface LocalTimeHelper {
        default LocalTime constructor(int hour, int minute, int second) {
            return LocalTime.of(hour, minute, second);
        }
    }*/

    //'constructor(int, int)' in 'LeetCodeTest.MonthDayHelper' clashes with 'constructor(int, int)'
    // in 'LeetCodeTest.LocalTimeHelper'; attempting to use incompatible return type
    //同时实现两个接口，且两个接口中存在相同的方法，那么就会报错

    /*public static class DateTimeClass implements MonthDayHelper, LocalTimeHelper {

        public void printDate() {
            MonthDay monthDay = constructor(1, 2);
            LocalTime localTime = constructor(3, 4, 5);

            MonthDay monthDay1 = MonthDayHelper.super.constructor(1, 2);
            LocalTime localTime2 = LocalTimeHelper.super.constructor(3, 4, 5);

        }
    }*/

}
