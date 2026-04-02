import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 演示“坐标输入”为什么通常直接用 nextInt() 就够了。
 *
 * 题面常见描述：
 * The next N lines each consist of two space-separated integers x and y.
 *
 * 很多人第一次看到这句会下意识觉得：
 * 那我是不是必须一行一行 nextLine() 再 split？
 *
 * 其实不是。
 * 这句话描述的是输入长什么样，
 * 不是强制你必须按整行读取。
 *
 * 如果你最终只是想拿到 2N 个整数，
 * 那 nextInt() 往往是最直接、最稳的写法。
 */
public class ScannerCoordinateNextIntDemo {

    public static void main(String[] args) {
        // 如果你手动跑 main，可以直接在控制台输：
        //
        // 3
        // 1 4
        // 2 3
        // 5 7
        //
        // 这就是很多 OJ 题常见的样子：
        // 第一行给 n，后面 n 行每行一组坐标。
        // 但代码里并不需要强行一行一行 nextLine() 去拆。

        String input = "3\n1 4\n2 3\n5 7\n";
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );

        int n = scanner.nextInt();
        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            points[i][0] = scanner.nextInt();
            points[i][1] = scanner.nextInt();
        }

        System.out.println("points = " + Arrays.deepToString(points));
        System.out.println();
        System.out.println("结论：");
        System.out.println("即使题面说 next N lines，每行两个整数，也完全可以按 token 连续 nextInt()。");
    }
}
