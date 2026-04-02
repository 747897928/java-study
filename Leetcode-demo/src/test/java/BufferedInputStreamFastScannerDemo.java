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
        // 这里用固定输入把它写死，
        // 这样我们更容易把注意力放在 nextInt() 的实现本身。

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
            // read() 每次只读 1 个字符。
            // 这里读到的不是 "整数" 这个概念，而是一个字符对应的编码值。
            //
            // 例如：
            // 输入里如果当前字符是 '1'，那 ch 里装的是字符 '1'
            // 输入里如果当前字符是 空格 或 换行，也一样只是一个普通字符。
            int ch = read();

            // 先跳过前面的空白字符。
            //
            // 为什么写 ch <= ' ' ?
            // 因为常见空白字符的编码都不大：
            // 空格 ' '、换行 '\n'、回车 '\r'、Tab '\t'
            // 在这种简化版快读里，通常直接把这些都当成“分隔符”。
            //
            // 所以这个 while 的意思就是：
            // 只要当前还没走到真正的数字，就继续往后读。
            while (ch <= ' ' && ch != -1) {
                ch = read();
            }

            // sign 用来处理负数。
            // 默认当成正数，所以先设为 1。
            int sign = 1;

            // 如果遇到 '-'，说明后面这个整数是负数。
            // 那就先把 sign 改成 -1，
            // 然后继续往后读，去拿真正的数字字符。
            if (ch == '-') {
                sign = -1;
                ch = read();
            }

            // value 就是我们最后要拼出来的整数值。
            // 一开始还没读到任何数字，所以是 0。
            int value = 0;

            // 这里开始真正“拼整数”。
            //
            // 条件写 ch > ' '，
            // 本质上就是：只要当前还不是空格 / 换行 / Tab / 结尾，
            // 就继续把它当成这个数字的一部分来处理。
            while (ch > ' ') {
                // 这一句是整个 nextInt() 最关键的地方：
                //
                // value = value * 10 + (ch - '0');
                //
                // 它的意思不是“什么神秘公式”，
                // 本质上只是把十进制数字一位一位拼起来。
                //
                // 先说 (ch - '0') 是什么：
                //
                // 字符 '0'、'1'、'2' ... '9' 在底层其实都有对应编码。
                // 而且它们的编码是连续的。
                //
                // 所以：
                // '0' - '0' = 0
                // '1' - '0' = 1
                // '7' - '0' = 7
                // '9' - '0' = 9
                //
                // 也就是说：
                // (ch - '0') 这一步，就是把“字符数字”转成“真正的数值”。
                //
                // 再说为什么要 value * 10：
                //
                // 假设我们目前已经拼出了 12，
                // 现在又读到一个新字符 '3'。
                //
                // 旧 value = 12
                // 新数字位 = 3
                //
                // 那新的整数应该变成 123。
                // 怎么从 12 变成 123？
                //
                // 先把 12 左移一位十进制，也就是乘 10：
                // 12 * 10 = 120
                //
                // 再把个位的 3 补上：
                // 120 + 3 = 123
                //
                // 所以：
                // value = value * 10 + (ch - '0')
                //
                // 你可以自己跟一下输入 456 时的变化：
                //
                // 初始：value = 0
                // 读到 '4'：value = 0 * 10 + 4 = 4
                // 读到 '5'：value = 4 * 10 + 5 = 45
                // 读到 '6'：value = 45 * 10 + 6 = 456
                value = value * 10 + (ch - '0');

                // 继续读下一个字符，
                // 看它是不是这个整数的下一位。
                ch = read();
            }

            // 如果前面遇到过负号，就在这里统一乘回去。
            // 正数时 sign = 1，不影响结果。
            // 负数时 sign = -1，结果就会变成负数。
            return value * sign;
        }

        private int read() throws IOException {
            return inputStream.read();
        }
    }
}
