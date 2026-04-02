import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 演示 BufferedInputStream 常见快读的本质。
 *
 * 很多人看到 FastScanner / 快读模板时，
 * 会以为它和 Scanner 是完全不同的一套逻辑。
 *
 * 其实核心思想没有变：
 *
 * 1. 先跳过空白字符
 * 2. 再把下一个 token 一位一位读出来
 * 3. 如果这个 token 表示整数，就拼成 int
 *
 * 也就是说，
 * 常见快读本质上仍然是在“按 token 读”，
 * 只是它为了性能，自己手写了解析过程。
 */
public class BufferedInputStreamFastScannerDemo {

    public static void main(String[] args) throws IOException {
        // 如果你想手动类比 main 输入，可以把它理解成控制台里输入：
        //
        // 4
        // 10 20
        // 30 40
        //
        // 这里只是为了演示快读常见模板内部到底在做什么，
        // 所以我还是用固定输入把它写死，
        // 这样你更容易把注意力放在 nextInt() 的实现本身。

        String input = "4\n10 20\n30 40\n";
        FastScanner fastScanner = new FastScanner(input);

        int count = fastScanner.nextInt();
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            values[i] = fastScanner.nextInt();
        }

        System.out.println("values = " + Arrays.toString(values));
        System.out.println();
        System.out.println("结论：");
        System.out.println("快读并不是按整行读，它通常也是跳过空白后按 token 读取。");
    }

    /**
     * 这是一个最小演示版快读。
     *
     * 这里只保留 nextInt()，
     * 目的就是让结构尽量简单，方便你自己单步看。
     */
    private static final class FastScanner {
        private final BufferedInputStream inputStream;

        private FastScanner(String input) {
            this.inputStream = new BufferedInputStream(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
            );
        }

        private int nextInt() throws IOException {
            int ch = read();
            while (ch <= ' ' && ch != -1) {
                ch = read();
            }

            int sign = 1;
            if (ch == '-') {
                sign = -1;
                ch = read();
            }

            int value = 0;
            while (ch > ' ') {
                value = value * 10 + (ch - '0');
                ch = read();
            }
            return value * sign;
        }

        private int read() throws IOException {
            return inputStream.read();
        }
    }
}
