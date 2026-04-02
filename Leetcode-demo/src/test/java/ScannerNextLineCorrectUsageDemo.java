import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 演示 nextInt() 之后如何正确切到 nextLine()。
 *
 * 正确写法的核心就一句：
 * 如果前面刚用了 nextInt() / nextLong() / next()，
 * 后面又想按整行读取，
 * 往往先补一行 scanner.nextLine() 把残留换行吃掉。
 *
 * 这里要特别记住：
 *
 * - nextInt() 读的是“下一个整数”
 * - nextLine() 读的是“当前位置到本行结束”
 *
 * 所以混用时，必须先把它们的读取边界对齐。
 *
 * 这个类建议和 ScannerNextLinePitfallDemo 对着看。
 * 两个类一起跑，差别会非常直观。
 */
public class ScannerNextLineCorrectUsageDemo {

    public static void main(String[] args) {
        // 这题如果你想手动跑 main，控制台输入可以还是：
        //
        // 3
        // hello world
        //
        // 和上一个 demo 的区别，不在输入，
        // 而在于这里中间多补了一句 scanner.nextLine()。
        // 所以两个 demo 最适合连着跑，对比看。
        //
        // 指针走位图如下：
        //
        // 初始：
        // [3]\n
        //  hello world\n
        //
        // nextInt() 之后：
        // 3[\n]
        //  hello world\n
        //
        // 补一行 nextLine() 之后：
        // 3\n
        // [h]ello world\n
        //
        // 再 nextLine()：
        // 读到的才是 hello world。

        String input = "3\nhello world\n";
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );

        int number = scanner.nextInt();
        scanner.nextLine();
        String line = scanner.nextLine();

        System.out.println("number = " + number);
        System.out.println("line = [" + line + "]");
        System.out.println();
        System.out.println("结论：");
        System.out.println("补这一句 scanner.nextLine() 之后，后面的 nextLine() 才是在读真正那一行。");
    }
}
