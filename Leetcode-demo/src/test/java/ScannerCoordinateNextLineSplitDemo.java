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

        int n = scanner.nextInt();
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
