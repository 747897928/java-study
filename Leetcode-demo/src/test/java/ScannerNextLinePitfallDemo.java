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
 * 更准确地说：
 *
 * 1. nextInt() 会把换行当成“数字结束的边界”
 * 2. 但 nextInt() 不负责“返回这一整行”
 * 3. nextLine() 才是专门负责“把当前位置到行尾读出来”的方法
 *
 * 所以问题不是 nextInt() 不认识换行，
 * 而是它认识换行的方式，和 nextLine() 处理换行的方式不是一回事。
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
        //
        // 可以把它想成下面这个“指针走位图”：
        //
        // 初始：
        // [3]\n
        //  hello world\n
        //
        // nextInt() 之后：
        // 3[\n]
        //  hello world\n
        //
        // 第一下 nextLine() 之后：
        // 3\n
        // [h]ello world\n
        //
        // 所以第一下 nextLine() 返回的是空串，
        // 第二下 nextLine() 才会真正读到 hello world。

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
