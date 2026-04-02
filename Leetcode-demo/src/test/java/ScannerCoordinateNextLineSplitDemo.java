import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 演示坐标题也可以用 nextLine() + split() 去读。
 *
 * 这不是说这种写法错，
 * 而是想让你看到它为什么比 nextInt() 更绕：
 *
 * 1. 前面先读了 n 之后，要补一个 nextLine()
 * 2. 每一行还要自己 split
 * 3. split 之后还要自己 parseInt
 *
 * 这里最容易误会的点是：
 *
 * 很多人会想：
 * nextInt() 不是本来就认识空格、换行、Tab 吗？
 * 那为什么它没有把换行一起“吃干净”？
 *
 * 要把这件事分成两层看：
 *
 * 1. nextInt() 确实会把空格、换行、Tab 当成分隔符
 * 2. 但它的目标只是“拿到下一个整数”
 * 3. 一旦整数已经读完了，它的任务就结束了
 *
 * 也就是说：
 * nextInt() 会利用空白字符来判断“数字到这里结束了”，
 * 但它不会替你再额外做一次“把这一整行文本读走”的动作。
 *
 * 所以：
 * - nextInt() 和 nextInt() 连着用，通常没问题
 * - nextInt() 后面如果切到 nextLine()，就容易错位
 *
 * 因为 nextLine() 读的是“从当前位置到本行结束”，
 * 而不是“下一个有效 token”。
 *
 * 所以纯数字输入时，
 * 它通常不是更好的第一选择。
 */
public class ScannerCoordinateNextLineSplitDemo {

    public static void main(String[] args) {
        // 如果你手动跑 main，输入仍然可以和上一个 demo 一模一样：
        //
        // 3
        // 1 4
        // 2 3
        // 5 7
        //
        // 这个类只是为了演示：
        // 同样一份输入，换成 nextLine() + split() 也能读，
        // 但写法会更绕。

        String input = "3\n1 4\n2 3\n5 7\n";
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );

        // 下面这两行建议配合“指针走位图”一起看：
        //
        // 原始输入：
        // 3\n
        // 1 4\n
        // 2 3\n
        // 5 7\n
        //
        // 一开始指针在最左边：
        // [3]\n
        //  1 4\n
        //  2 3\n
        //  5 7\n
        //
        // 执行 nextInt() 之后：
        // 3[\n]
        //  1 4\n
        //  2 3\n
        //  5 7\n
        //
        // 注意这里最关键的一点：
        // nextInt() 已经把数字 3 交给了变量 n，
        // 但那一个换行还留在当前位置。
        //
        // 所以如果这里直接 nextLine()，
        // 读到的会是空串 ""，
        // 因为它读的就是“当前位置到这一行结束”，
        // 而当前位置刚好就在换行前面。
        int n = scanner.nextInt();

        // 这一句就是专门把上面残留的换行读掉。
        //
        // 执行完这句之后，指针才会来到下一行开头：
        // 3\n
        // [1] 4\n
        //  2 3\n
        //  5 7\n
        //
        // 这时候后面的 nextLine() 才会真正读到 "1 4"。
        scanner.nextLine();

        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            String[] parts = scanner.nextLine().trim().split("\\s+");
            points[i][0] = Integer.parseInt(parts[0]);
            points[i][1] = Integer.parseInt(parts[1]);
        }

        System.out.println("points = " + Arrays.deepToString(points));
        System.out.println();
        System.out.println("结论：");
        System.out.println("这套写法能用，但对纯数字题来说通常没有 nextInt() 直接。");
    }
}
