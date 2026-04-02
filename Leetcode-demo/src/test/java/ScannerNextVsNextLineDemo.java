import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 演示 next() 和 nextLine() 的区别。
 *
 * 这个类主要是为了帮你建立一个直觉：
 *
 * 1. next() 是按空白分词
 * 2. nextLine() 是整行读取
 *
 * 什么时候用哪个，很多时候就看你要的是：
 *
 * - 下一个字段 / 下一个单词
 * - 还是整行原样文本
 *
 * 如果题目里是纯数字、坐标、数组，
 * 基本就不该优先想到 nextLine()。
 * 如果题目里是一整句文本、带空格的内容，
 * 那 nextLine() 才更自然。
 */
public class ScannerNextVsNextLineDemo {

    public static void main(String[] args) {
        // 如果你手动跑 main，可以在控制台里这样输入两行：
        //
        // hello world
        // java study
        //
        // 然后你可以自己改代码，只保留 next() 或只保留 nextLine()，
        // 分别观察它们到底拿到的是什么。

        String input = "hello world\njava study";

        Scanner tokenScanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );
        System.out.println("next() 第一次读到：" + tokenScanner.next());
        System.out.println("next() 第二次读到：" + tokenScanner.next());
        System.out.println("next() 第三次读到：" + tokenScanner.next());
        System.out.println("next() 第四次读到：" + tokenScanner.next());

        //next() 是按空白分词的，所以它会把空格、换行、Tab 都当成分隔符，
        String input2 = "hello  world\njava\tstudy";

        Scanner tokenScanner2 = new Scanner(
                new ByteArrayInputStream(input2.getBytes(StandardCharsets.UTF_8))
        );
        System.out.println("next() 第五次读到：" + tokenScanner2.next());
        System.out.println("next() 第六次读到：" + tokenScanner2.next());
        System.out.println("next() 第七次读到：" + tokenScanner2.next());
        System.out.println("next() 第八次读到：" + tokenScanner2.next());

        System.out.println();

        Scanner lineScanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );
        System.out.println("nextLine() 第一次读到：" + lineScanner.nextLine());
        System.out.println("nextLine() 第二次读到：" + lineScanner.nextLine());
    }
}
