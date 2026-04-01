import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Scanner 输入练习类。
 *
 * 这个类不是为了刷题本身，
 * 而是专门拿来练下面这几件事：
 *
 * 1. nextInt() / next() / nextLine() 到底分别读什么
 * 2. 为什么 nextInt() 会自动跳过空格、Tab、换行
 * 3. 为什么 nextInt() 后面直接接 nextLine() 很容易先读到空串
 * 4. 为什么纯数字题通常更推荐 nextInt()
 * 5. BufferedInputStream 常见快读为什么本质上也是“按 token 读”
 *
 * 用法有两种：
 *
 * 1. 直接右键运行 main，看控制台输出
 * 2. 直接跑 JUnit 测试
 */
public class ScannerInputLearningTest {

    public static void main(String[] args) throws Exception {
        ScannerInputLearningTest demo = new ScannerInputLearningTest();
        demo.demoNextIntSkipsWhitespace();
        demo.demoNextVsNextLine();
        demo.demoNextLinePitfallAfterNextInt();
        demo.demoNextLineAfterConsumingNewline();
        demo.demoCoordinateInputWithNextInt();
        demo.demoCoordinateInputWithNextLineSplit();
        demo.demoFastScannerByBufferedInputStream();
    }

    @Test
    public void testNextIntSkipsWhitespace() {
        demoNextIntSkipsWhitespace();
    }

    @Test
    public void testNextVsNextLine() {
        demoNextVsNextLine();
    }

    @Test
    public void testNextLinePitfallAfterNextInt() {
        demoNextLinePitfallAfterNextInt();
    }

    @Test
    public void testNextLineAfterConsumingNewline() {
        demoNextLineAfterConsumingNewline();
    }

    @Test
    public void testCoordinateInputWithNextInt() {
        demoCoordinateInputWithNextInt();
    }

    @Test
    public void testCoordinateInputWithNextLineSplit() {
        demoCoordinateInputWithNextLineSplit();
    }

    @Test
    public void testFastScannerByBufferedInputStream() throws IOException {
        demoFastScannerByBufferedInputStream();
    }

    private void demoNextIntSkipsWhitespace() {
        printTitle("1. nextInt() 会自动跳过空白字符");

        String input = "3 \n 10\t20   \n30";
        Scanner scanner = scannerOf(input);

        int first = scanner.nextInt();
        int second = scanner.nextInt();
        int third = scanner.nextInt();
        int fourth = scanner.nextInt();

        System.out.println("原始输入：");
        System.out.println(showInvisible(input));
        System.out.println("nextInt() 依次读到："
                + first + ", " + second + ", " + third + ", " + fourth);
        System.out.println("说明：空格、Tab、换行都会被 nextInt() 当成分隔符跳过。");
        System.out.println();

        Assert.assertEquals(3, first);
        Assert.assertEquals(10, second);
        Assert.assertEquals(20, third);
        Assert.assertEquals(30, fourth);
    }

    private void demoNextVsNextLine() {
        printTitle("2. next() 和 nextLine() 的区别");

        String input = "hello world\njava study";

        Scanner tokenScanner = scannerOf(input);
        String firstToken = tokenScanner.next();
        String secondToken = tokenScanner.next();
        String thirdToken = tokenScanner.next();
        String fourthToken = tokenScanner.next();

        Scanner lineScanner = scannerOf(input);
        String firstLine = lineScanner.nextLine();
        String secondLine = lineScanner.nextLine();

        System.out.println("原始输入：");
        System.out.println(showInvisible(input));
        System.out.println("next() 读取结果："
                + Arrays.asList(firstToken, secondToken, thirdToken, fourthToken));
        System.out.println("nextLine() 读取结果："
                + Arrays.asList(firstLine, secondLine));
        System.out.println("说明：next() 按空白分词，nextLine() 读整行。");
        System.out.println();

        Assert.assertEquals("hello", firstToken);
        Assert.assertEquals("world", secondToken);
        Assert.assertEquals("hello world", firstLine);
        Assert.assertEquals("java study", secondLine);
    }

    private void demoNextLinePitfallAfterNextInt() {
        printTitle("3. nextInt() 后面直接接 nextLine() 的坑");

        String input = "3\nhello world\n";
        Scanner scanner = scannerOf(input);

        int number = scanner.nextInt();
        String line1 = scanner.nextLine();
        String line2 = scanner.nextLine();

        System.out.println("原始输入：");
        System.out.println(showInvisible(input));
        System.out.println("先 nextInt() 读到：" + number);
        System.out.println("第一下 nextLine() 读到：[" + line1 + "]");
        System.out.println("第二下 nextLine() 读到：[" + line2 + "]");
        System.out.println("说明：第一下 nextLine() 先把整数 3 后面残留的换行吃掉了。");
        System.out.println();

        Assert.assertEquals(3, number);
        Assert.assertEquals("", line1);
        Assert.assertEquals("hello world", line2);
    }

    private void demoNextLineAfterConsumingNewline() {
        printTitle("4. 正确切换到 nextLine() 的写法");

        String input = "3\nhello world\n";
        Scanner scanner = scannerOf(input);

        int number = scanner.nextInt();
        scanner.nextLine();
        String line = scanner.nextLine();

        System.out.println("原始输入：");
        System.out.println(showInvisible(input));
        System.out.println("先 nextInt() 读到：" + number);
        System.out.println("补一行 scanner.nextLine() 之后，再读到：[" + line + "]");
        System.out.println("说明：这时候 nextLine() 才是在读真正那一整行文本。");
        System.out.println();

        Assert.assertEquals(3, number);
        Assert.assertEquals("hello world", line);
    }

    private void demoCoordinateInputWithNextInt() {
        printTitle("5. 坐标题用 nextInt() 读取");

        String input = "3\n1 4\n2 3\n5 7\n";
        Scanner scanner = scannerOf(input);

        int n = scanner.nextInt();
        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            points[i][0] = scanner.nextInt();
            points[i][1] = scanner.nextInt();
        }

        System.out.println("原始输入：");
        System.out.println(showInvisible(input));
        System.out.println("按 nextInt() 读出来的坐标："
                + Arrays.deepToString(points));
        System.out.println("说明：题面虽然说 next N lines each consist of two integers，");
        System.out.println("但程序完全可以按 token 连续读，不必强行 nextLine() + split()。");
        System.out.println();

        Assert.assertArrayEquals(new int[]{1, 4}, points[0]);
        Assert.assertArrayEquals(new int[]{2, 3}, points[1]);
        Assert.assertArrayEquals(new int[]{5, 7}, points[2]);
    }

    private void demoCoordinateInputWithNextLineSplit() {
        printTitle("6. 坐标题用 nextLine() + split() 也能读，但更容易绕");

        String input = "3\n1 4\n2 3\n5 7\n";
        Scanner scanner = scannerOf(input);

        int n = scanner.nextInt();
        scanner.nextLine();

        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            String[] parts = scanner.nextLine().trim().split("\\s+");
            points[i][0] = Integer.parseInt(parts[0]);
            points[i][1] = Integer.parseInt(parts[1]);
        }

        System.out.println("原始输入：");
        System.out.println(showInvisible(input));
        System.out.println("按 nextLine() + split() 读出来的坐标："
                + Arrays.deepToString(points));
        System.out.println("说明：这种写法能用，但前面必须额外补一个 nextLine() 吃掉换行。");
        System.out.println("所以纯数字输入里，一般 nextInt() 更直接、更稳。");
        System.out.println();

        Assert.assertArrayEquals(new int[]{1, 4}, points[0]);
        Assert.assertArrayEquals(new int[]{2, 3}, points[1]);
        Assert.assertArrayEquals(new int[]{5, 7}, points[2]);
    }

    private void demoFastScannerByBufferedInputStream() throws IOException {
        printTitle("7. BufferedInputStream 常见快读本质上也是按 token 读");

        String input = "4\n10 20\n30 40\n";
        FastScanner fastScanner = new FastScanner(input);

        int count = fastScanner.nextInt();
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            values[i] = fastScanner.nextInt();
        }

        System.out.println("原始输入：");
        System.out.println(showInvisible(input));
        System.out.println("FastScanner 读到的结果："
                + Arrays.toString(values));
        System.out.println("说明：BufferedInputStream 自己不会帮你读整数。");
        System.out.println("常见写法是自己封装一个 nextInt()，本质仍然是按 token 读。");
        System.out.println();

        Assert.assertArrayEquals(new int[]{10, 20, 30, 40}, values);
    }

    private static Scanner scannerOf(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    private static void printTitle(String title) {
        System.out.println("==================================================");
        System.out.println(title);
    }

    private static String showInvisible(String input) {
        return input
                .replace("\t", "\\t")
                .replace("\r", "\\r")
                .replace("\n", "\\n\n");
    }

    /**
     * 一个很小的快读演示版。
     *
     * 这里故意只保留 nextInt()，
     * 就是想让你看到：
     *
     * 1. BufferedInputStream 本身只是字节流
     * 2. 真正“会读整数”的是你自己封装的 nextInt()
     * 3. 它和 Scanner.nextInt() 一样，也是在跳过空白后按 token 读
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
