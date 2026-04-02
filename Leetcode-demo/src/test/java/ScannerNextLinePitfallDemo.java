import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 演示 nextInt() 后面直接接 nextLine() 的常见坑。
 *
 * 这个坑你后面写题很容易遇到，所以单独拆成一个类更方便反复试。
 *
 * 关键点：
 * nextInt() 只会把整数读走，
 * 但它不会顺手把这一行末尾的换行也一起按“整行文本”处理掉。
 *
 * 所以后面紧接着第一下 nextLine()，
 * 很可能只会读到一个空串。
 */
public class ScannerNextLinePitfallDemo {

    public static void main(String[] args) {
        // 如果你手动跑 main，可以这样想：
        //
        // 第一行输入：
        // 3
        //
        // 第二行输入：
        // hello world
        //
        // 然后你会发现：
        // nextInt() 读走 3 之后，
        // 第一下一定程度上“看起来没干活”的 nextLine()，
        // 实际上只是把 3 后面的换行读掉了。

        String input = "3\nhello world\n";
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );

        int number = scanner.nextInt();
        String firstLine = scanner.nextLine();
        String secondLine = scanner.nextLine();

        System.out.println("number = " + number);
        System.out.println("firstLine = [" + firstLine + "]");
        System.out.println("secondLine = [" + secondLine + "]");
        System.out.println();
        System.out.println("结论：");
        System.out.println("第一下 nextLine() 其实只是把整数后面残留的换行吃掉了。");
    }
}
