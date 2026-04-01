import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Scanner 输入练习类。
 *
 * 这个类不是为了刷题本身，
 * 而是专门拿来练下面这几件事：
 *
 * 1. nextInt() / next() / nextLine() 到底分别读什么
 * 2. 为什么 nextInt() 会自动跳过空格、Tab、换行
 * 3. 为什么 nextInt() 后面直接接 nextLine() 很容易先读到空串
 * 4. 为什么纯数字题通常更推荐 nextInt()
 * 5. BufferedInputStream 常见快读为什么本质上也是“按 token 读”
 *
 * 用法有两种：
 *
 * 1. 直接右键运行 main，看控制台输出
 * 2. 直接跑 JUnit 测试
 */
public class ScannerInputLearningTest2 {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //3 5 10 11 abc
        //first = 3
        //second = 5
        //third = 10
        //fourth = 11

        //3 5 abc 123 456
        //first = 3
        //second = 5
        //Exception in thread "main" java.util.InputMismatchException
        //	at java.util.Scanner.throwFor(Scanner.java:864)
        //	at java.util.Scanner.next(Scanner.java:1485)
        //	at java.util.Scanner.nextInt(Scanner.java:2117)
        //	at java.util.Scanner.nextInt(Scanner.java:2076)
        //	at ScannerInputLearningTest2.main(ScannerInputLearningTest2.java:41)

        //3
        //first = 3
        //5
        //second = 5
        //5 10 11
        //third = 5
        //fourth = 10
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
