import java.util.Scanner;

/**
 * Scanner.nextInt() 最基础的顺序读取演示。
 *
 * 这个类只做一件事：
 * 连续调用 4 次 nextInt()，看它到底是怎么从控制台把整数一个个读出来的。
 *
 * 适合一开始最先跑这个类。
 * 因为它最直观，能先帮你建立一个最基本的感觉：
 *
 * 1. nextInt() 是“读下一个整数 token”
 * 2. 它不要求你必须一行只写一个数
 * 3. 空格、换行、Tab 都能把数字隔开
 *
 * 你可以手动试几组输入：
 *
 * 情况 1：一行里连续输入
 * 3 5 10 11
 *
 * 输出：
 * first = 3
 * second = 5
 * third = 10
 * fourth = 11
 *
 * 情况 2：分多行输入
 * 3
 * 5
 * 10 11
 *
 * 输出仍然一样。
 *
 * 情况 3：中途混入非整数
 * 3 5 abc 11
 *
 * 这时 nextInt() 读到 abc 会直接报 InputMismatchException，
 * 因为它期待的是整数，不会自动帮你跳过普通字符串。
 */
public class ScannerNextIntBasicDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 下面这些注释，记录的是手动运行 main 时我自己在控制台输入的内容，
        // 以及程序当时真实打印出来的结果。
        // 这种记录方式保留着会更方便以后自己反复试，不需要每次都重新想测试数据。

        // 3 5 10 11 abc
        // first = 3
        // second = 5
        // third = 10
        // fourth = 11

        // 3 5 abc 123 456
        // first = 3
        // second = 5
        // Exception in thread "main" java.util.InputMismatchException
        //     at java.util.Scanner.throwFor(Scanner.java:864)
        //     at java.util.Scanner.next(Scanner.java:1485)
        //     at java.util.Scanner.nextInt(Scanner.java:2117)
        //     at java.util.Scanner.nextInt(Scanner.java:2076)
        //     at ScannerNextIntBasicDemo.main(ScannerNextIntBasicDemo.java:55)

        // 3
        // first = 3
        // 5
        // second = 5
        // 5 10 11
        // third = 5
        // fourth = 10

        int first = scanner.nextInt();
        System.out.println("first = " + first);

        int second = scanner.nextInt();
        System.out.println("second = " + second);

        int third = scanner.nextInt();
        System.out.println("third = " + third);

        int fourth = scanner.nextInt();
        System.out.println("fourth = " + fourth);
    }
}
