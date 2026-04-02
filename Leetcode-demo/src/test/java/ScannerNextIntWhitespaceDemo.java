import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 演示 nextInt() 会自动跳过空白字符。
 *
 * 这个点很重要，因为很多 OJ 题面会写：
 * space-separated integers
 * next N lines
 * each line contains ...
 *
 * 但对 nextInt() 来说，
 * 它真正关心的是“下一个整数 token 在哪里”，
 * 而不是“这个整数原本位于第几行”。
 *
 * 这里会故意把输入写得很乱：
 * 空格、换行、Tab 混在一起。
 *
 * 你运行后重点看结果，
 * 只要这几个整数还在，nextInt() 仍然能按顺序读出来。
 */
public class ScannerNextIntWhitespaceDemo {

    public static void main(String[] args) {
        // 如果你想手动跑 main，也可以直接把下面这段想象成自己敲到控制台里的输入：
        //
        // 3
        // 10 20
        // 30
        //
        // 或者故意输得更乱一点：
        // 3  10
        // 20      30
        //
        // 这类测试的重点不是“必须长得多整齐”，
        // 而是验证 nextInt() 会不会把空格、换行、Tab 都当成普通分隔符。

        String input = "3 \n 10\t20   \n30";
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );

        int first = scanner.nextInt();
        int second = scanner.nextInt();
        int third = scanner.nextInt();
        int fourth = scanner.nextInt();

        System.out.println("原始输入：");
        System.out.println(showInvisible(input));
        System.out.println("读取结果：");
        System.out.println("first = " + first);
        System.out.println("second = " + second);
        System.out.println("third = " + third);
        System.out.println("fourth = " + fourth);
        System.out.println();
        System.out.println("结论：");
        System.out.println("nextInt() 会自动跳过空格、Tab、换行，然后去读下一个整数。");
    }

    private static String showInvisible(String input) {
        return input
            .replace("\t", "\\t")
            .replace("\r", "\\r")
            .replace("\n", "\\n\n");
    }
}
