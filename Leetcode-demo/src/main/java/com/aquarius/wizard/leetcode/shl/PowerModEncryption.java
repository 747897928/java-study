package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;

/**
 * Question
 *
 * Bob has to send a secret code S to his boss. He designs a method to encrypt the code using two
 * key values N and M. The formula that he uses to develop the encrypted code is shown below:
 *
 * (((S^N % 10)^M) % 1000000007)
 *
 * Write an algorithm to help Bob encrypt the code.
 *
 * Input
 *
 * The first line of the input consists of an integer secretCode, representing the secret code (S).
 * The second line consists of an integer firstKey, representing the first key value (N).
 * The third line consists of an integer secondKey, representing the second key value (M).
 *
 * Output
 *
 * Print an integer representing the code encrypted by Bob.
 *
 * Constraints
 *
 * 1 <= secretCode <= 10^9
 * 0 <= firstKey, secondKey <= 1000000007
 *
 * Example
 *
 * Input:
 * 2
 * 3
 * 4
 *
 * Output:
 * 4096
 *
 * Explanation:
 * S = 2, N = 3, M = 4
 * (((2^3 % 10)^4) % 1000000007) = 4096
 *
 * 笔记
 *
 * 难度：简单。
 *
 * 考点：快速幂、模运算、运算顺序。
 * 校对：示例输出 4096 已由 docx 原文和代码校验确认。
 * 易错点：不是直接算 S^(N*M)，而是先算 S^N % 10，再对这个结果做 M 次幂。
 * 相似题：任何 a^b mod m 的题都和这里同型，例如 LeetCode 50 / 372。
 *
 * 做这题时，我希望自己形成下面这个反应：
 *
 * 1. 看到大指数，不要想着真的把幂算出来。
 * 2. 先看题目是不是允许你一边乘一边取模。
 * 3. 再看整个表达式是不是分成“先做一步，再拿结果做下一步”。
 *
 * 这题最容易读错的地方是公式结构：
 *
 * (((S^N % 10)^M) % 1000000007)
 *
 * 它不是：
 *
 * S^(N*M)
 *
 * 也不是：
 *
 * (S^N)^M % 1000000007
 *
 * 真正的顺序是：
 *
 * 1. 先算 S^N，但不是直接算出来，而是用快速幂算到模 10 的结果
 * 2. 得到 first = S^N % 10
 * 3. 再算 first^M % 1000000007
 *
 * 所以这题表面像数学题，本质上其实是在练：
 *
 * “我能不能把一个大表达式按题目给的括号顺序拆成两段”
 *
 * formula n.计划，方案；配方，处方；公式，方程式；分子式，结构式；配方奶，代乳品；（特定场合的）惯用词语，套话；（影片、书籍等的）套路
 */
public class PowerModEncryption {

    private static final long MOD = 1_000_000_007L;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long secretCode = scanner.nextLong();
        long firstKey = scanner.nextLong();
        long secondKey = scanner.nextLong();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * long secretCode = 2L;
         * long firstKey = 3L;
         * long secondKey = 4L;
         */

        PowerModEncryption solver = new PowerModEncryption();
        System.out.println(solver.encryptCode(secretCode, firstKey, secondKey));
        /*
         * 如果需要核对朴素写法，可以临时打开下面这行：
         * System.out.println(solver.encryptCode2(secretCode, firstKey, secondKey));
         */
    }

    /**
     * 这一层只做“按题目括号顺序拆表达式”。
     *
     * 可以把它读成：
     *
     * 1. 先做第一段加密，得到一个 0..9 之间的结果
     * 2. 再拿这个小结果去做第二段加密
     *
     * 这也是考试里很常见的套路：
     *
     * 题目给你一个看起来很长的式子，
     * 但代码里往往不需要一次性处理整个式子，
     * 而是拆成几步，每一步都只做一个清晰的小目标。
     */
    public long encryptCode(long secretCode, long firstKey, long secondKey) {
        long first = modPow(secretCode, firstKey, 10L);
        return modPow(first, secondKey, MOD);
    }

    /**
     * 快速幂的本质：
     *
     * 当 exponent 的二进制某一位是 1，
     * 就把当前的 base 贡献乘进答案里。
     *
     * 这套方法有名字：
     *
     * 1. 快速幂
     * 2. 二进制快速幂
     * 3. Exponentiation by Squaring（平方求幂）
     *
     * 这里顺手记一下这个拆分为什么成立：
     *
     * base^13 = base^8 * base^4 * base^1
     *
     * 它来自两件事：
     *
     * 1. 13 的二进制是 1101，也就是 13 = 8 + 4 + 1
     * 2. 幂有这个基本性质：a^(x + y) = a^x * a^y
     *
     * 所以：
     *
     * base^13 = base^(8 + 4 + 1)
     *         = base^8 * base^4 * base^1
     *
     * 例如：
     *
     * 2^5 = 2^(4 + 1) = 2^4 * 2^1 = 16 * 2 = 32
     *
     * while 循环做的事情就是不断把这些“2 的幂次块”准备出来：
     *
     * base^1, base^2, base^4, base^8, ...
     *
     * 然后根据 exponent 的二进制位，
     * 决定哪些块要乘进结果里。
     *
     * 先记住这一句就够了：
     *
     * “把 exponent 写成若干个 2 的幂之和，
     * 然后把对应的 base^(2^k) 乘起来”
     *
     * 这样时间复杂度就从 O(exponent) 变成 O(log exponent)。
     *
     * 如果你第一次学这题，先不要急着想二进制。
     * 先只看这个例子：
     *
     * base = 2, exponent = 13
     *
     * 13 = 8 + 4 + 1
     *
     * 所以：
     *
     * 2^13 = 2^8 * 2^4 * 2^1
     *
     * 注意这里没有 2^2。
     *
     * 这就是为什么某一轮如果对应的是 2^2，而当前位是 0，
     * 那一轮就“不乘”。
     *
     * 不是因为什么神秘位运算规则，
     * 而是因为 13 的拆分里本来就没有那个块。
     *
     * 下面这个 while，本质上就是在做两件事：
     *
     * 1. 看当前这个幂次块要不要乘进答案
     * 2. 把当前幂次块升级到下一块
     *
     * 例如：
     *
     * 第 1 轮准备的是 base^1
     * 第 2 轮准备的是 base^2
     * 第 3 轮准备的是 base^4
     * 第 4 轮准备的是 base^8
     */
    private long modPow(long base, long exponent, long mod) {
        /*
         * 这里的目标是求：
         *
         * (base^exponent) % mod
         *
         * 但我们不会真的先算出 base^exponent，
         * 因为指数可能非常大，直接算会又慢又溢出。
         */
        long answer = 1L % mod;
        /*
         * answer 表示“目前已经拼出来的答案”。
         *
         * 一开始为什么是 1？
         *
         * 因为乘法单位元就是 1。
         *
         * 这个知识不是二进制知识，
         * 是最基础的乘法知识：
         *
         * 1 * x = x
         *
         * 你可以把它类比成：
         *
         * - 做加法时，初始值常常是 0
         * - 做乘法时，初始值常常是 1
         *
         * 所以这里让 answer 从 1 开始，
         * 表示“我现在还什么都没乘进去”。
         *
         * 这里顺手解释一下为什么一开始就写：
         *
         * 1L % mod
         *
         * 而不是直接写 1L。
         *
         * 因为这个方法的目标始终是“在 mod 意义下工作”。
         * 提前写成 % mod，表示 answer 这个变量从一开始就保存“模 mod 之后的值”。
         *
         * 虽然在这题里 1 % mod 还是 1，
         * 但这种写法能让整段代码风格保持一致。
         */
        long currentPowerBlock = base % mod;
        /*
         * currentPowerBlock 表示“当前这一轮手里拿着的幂次块”。
         *
         * 第一轮：
         * currentPowerBlock = base^1 % mod
         *
         * 第二轮平方后：
         * currentPowerBlock = base^2 % mod
         *
         * 第三轮再平方：
         * currentPowerBlock = base^4 % mod
         *
         * 第四轮再平方：
         * currentPowerBlock = base^8 % mod
         *
         * 所以它会依次变成：
         *
         * base^1, base^2, base^4, base^8, base^16 ...
         *
         * 这里一开始先 % mod，
         * 也是因为我们只关心“模 mod 之后的结果”。
         *
         * 数学依据是：
         *
         * (a * b) % mod = ((a % mod) * (b % mod)) % mod
         *
         * 所以一个数先取模，再参与后续乘法，
         * 和最后统一取模，结果是一致的。
         */
        long remainingExponent = exponent;
        /*
         * remainingExponent 表示“还没处理完的指数”。
         *
     * 这里开始会用到一点二进制，
     * 先别把它想复杂。
         *
         * 例如 exponent = 13：
         *
         * 十进制：13
         * 二进制：1101
         *
         * 这个 1101 的意思就是：
         *
         * 1 * 8 + 1 * 4 + 0 * 2 + 1 * 1 = 13
         *
         * 所以它其实就是在告诉你：
         *
         * 13 需要 8、4、1 这三个块
         * 13 不需要 2 这个块
         */
        while (remainingExponent > 0) {
            /*
             * (remainingExponent & 1L) == 1L
             *
             * 这是在问：
             *
             * “remainingExponent 的二进制最低位是不是 1？”
             *
             * 这里的最低位，你可以先把它理解成“当前最小的那一块要不要用”。
             *
             * 例如 exponent = 13，二进制是 1101：
             *
             * 第 1 轮看最低位，最低位是 1
             * 说明 2^1 这个块要用，所以要把 base^1 乘进答案
             *
             * 第 2 轮最低位是 0
             * 说明 2^2 这个块不用
             * 也就是 13 = 8 + 4 + 1 里没有 2
             * 所以当前这一轮对应的 base^2 不乘
             *
             * 第 3 轮最低位是 1，对应 base^4，要乘
             * 第 4 轮最低位是 1，对应 base^8，也要乘
             */
            if (remainingExponent % 2 == 1) {
                /*
                 * 这里的意思就是：
                 *
                 * “当前这一块确实属于 exponent 的拆分，
                 *  所以把它乘进答案里”
                 *
                 * 为什么要写：
                 *
                 * answer = answer * currentPowerBlock
                 *
                 * 因为答案就是由这些需要的块乘起来的。
                 *
                 * 还是拿 13 举例：
                 *
                 * answer 最后要变成：
                 *
                 * base^1 * base^4 * base^8
                 *
                 * 这里只是按轮次一点点把它拼出来。
                 *
                 * 为什么这里要写：
                 *
                 * answer = (answer * currentPowerBlock) % mod
                 *
                 * 而不是只写：
                 *
                 * answer = answer * currentPowerBlock
                 *
                 * 有两个原因：
                 *
                 * 1. 题目最终要的本来就是“模 mod 之后的结果”
                 * 2. 不立刻取模，数字会越来越大，容易溢出
                 *
                 * 公式依据还是这一条：
                 *
                 * (a * b) % mod = ((a % mod) * (b % mod)) % mod
                 * 举个例子：
                 * a = 17, b = 23, mod = 5
                 * 左边：
                 * (17 * 23) % 5 = 391 % 5 = 1
                 * 右边：
                 * ((17 % 5) * (23 % 5)) % 5 = (2 * 3) % 5 = 6 % 5 = 1
                 *
                 * 所以：
                 *
                 * 先乘再模
                 * 和
                 * 每次乘完都立刻模
                 *
                 * 结果一样，但后者更安全。
                 */
                answer = (answer * currentPowerBlock) % mod;
            }
            /*
             * 当前这一块处理完后，把 currentPowerBlock 平方。
             *
             * 这不是“向上取整”，
             * 也不是因为 right shift 要配套 square。
             *
             * 这里单纯是在准备“下一轮更大的幂次块”。
             *
             * 例如：
             *
             * base^1 -> base^2 -> base^4 -> base^8 -> ...
             *
             * 为什么平方就能跳到下一块？
             *
             * 因为：
             *
             * (base^1)^2 = base^2
             * (base^2)^2 = base^4
             * (base^4)^2 = base^8
             *
             * 也就是：
             *
             * a^(2k) = (a^k)^2
             *
             * 这就是“平方求幂”这 4 个字真正指的数学依据。
             *
             * 为什么这里也要再写一次 % mod？
             *
             * currentPowerBlock = (currentPowerBlock * currentPowerBlock) % mod
             *
             * 因为 currentPowerBlock 这个变量本身就表示：
             *
             * “当前幂次块在 mod 意义下的值”
             *
             * 下一轮我们还要继续拿它参与乘法，
             * 所以也必须保持它始终是“取模后的安全值”。
             *
             * 所以这里的两次 % mod 不是重复做同一件事：
             *
             * 1. 第一次 % mod 是在更新答案 answer
             * 2. 第二次 % mod 是在准备下一轮的 currentPowerBlock
             *
             * 一个管“已经选进答案的部分”，
             * 一个管“下一轮待选的幂次块”。
             */
            currentPowerBlock = (currentPowerBlock * currentPowerBlock) % mod;
            /*
             * remainingExponent /= 2
             *
     * 这里我先不用位运算写，直接写除以 2。
             * 这样更容易和“13 = 8 + 4 + 1”这个拆分联系起来。
             *
             * 为什么是“向下取整”？
             *
             * 因为 remainingExponent 是 long 整数。
             * Java 里的整数除法不会保留小数部分。
             *
             * 例如：
             *
             * 13 / 2 = 6
             *  3 / 2 = 1
             *  1 / 2 = 0
             *
             * 这里丢掉的小数部分，
             * 本质上就是“刚才已经处理过的最低位”。
             *
             * 这样做的目的不是计算答案，
             * 而是“把刚才已经判断过的那一位丢掉，去看下一位”。
             *
             * 还是看 13 的变化：
             *
             * 1101  -> 110 -> 11 -> 1 -> 0
             *
             * 也就是：
             *
             * 13 -> 6 -> 3 -> 1 -> 0
             *
             * 你如果更熟一点，也可以把这句写成：
             *
             * remainingExponent >>= 1
             *
             * 对这里这种非负整数来说：
             *
             * - / 2
             * - >>= 1
             *
             * 效果一样，都是“除以 2，向下取整”。
             *
             * 顺手补一句：
             *
             * - 右移一位，通常可以理解成除以 2
             * - 左移一位，通常可以理解成乘以 2
             *
             * 但那是对整数二进制位的移动解释。
             *
             * 这题真正关键的不是“位运算更快”，
             * 而是“指数每次缩小一半，所以循环次数从 n 级别降到 log n 级别”。
             */
            remainingExponent /= 2;
        }
        return answer;
    }

    /**
     * 这是“按最原始思路硬算”的保留版。
     *
     * 我没有继续用 Math.pow，
     * 因为 Math.pow 返回 double，会有两个大问题：
     *
     * 1. 指数一大就会溢出成 Infinity
     * 2. 即使没溢出，double 也会丢精度
     *
     * 所以原来的写法：
     *
     * double first = Math.pow(secretCode, firstKey) % 10L;
     *
     * 对这题的大约束并不可靠。
     *
     * 这里保留“原始办法”的意思是：
     *
     * 按定义做 firstKey 次乘法，再按定义做 secondKey 次乘法。
     *
     * 这种写法更慢，但逻辑非常直。
     * 适合你刚理解题意时，用来对照快速幂版到底在优化什么。
     *
     * 这里顺手记一个实际开发里的知识点：
     *
     * Java 里确实有 Math.pow(a, b) 这个方法可以用，
     * 它返回的是 double。
     *
     * 所以：
     *
     * 1. 小数据时，它可以拿来快速验证结果
     * 2. 但像这题这种“大整数幂 + 精确取模”的场景，不适合依赖它
     *
     * 原因还是那两个：
     *
     * - double 会丢精度
     * - 指数大时可能溢出
     */
    public long encryptCode2(long secretCode, long firstKey, long secondKey) {
        long first = modPowNaive(secretCode, firstKey, 10L);
        return modPowNaive(first, secondKey, MOD);
    }

    /**
     * 最朴素的“乘 exponent 次”版本。
     *
     * 例如求 base^5 % mod：
     *
     * result = 1
     * 第 1 次乘 base
     * 第 2 次再乘 base
     * ...
     * 第 5 次再乘 base
     *
     * 每乘一次都立刻取模，
     * 这样数值不会无限膨胀。
     *
     * 公式依据还是这一条：
     *
     * (a * b) % mod = ((a % mod) * (b % mod)) % mod
     * 举个例子：
     * a = 17, b = 23, mod = 5
     * 左边：
     * (17 * 23) % 5 = 391 % 5 = 1
     * 右边：
     * ((17 % 5) * (23 % 5)) % 5 = (2 * 3) % 5 = 6 % 5 = 1
     *
     * 所以：
     *
     * 先乘再模
     * 和
     * 每次乘完都立刻模
     *
     * 结果一样，但后者更安全。
     *
     * 这版的复杂度是 O(exponent)。
     * 如果 exponent 很大，会比快速幂慢很多。
     */
    private long modPowNaive(long base, long exponent, long mod) {
        long result = 1L % mod;
        long value = base % mod;
        for (long i = 0; i < exponent; i++) {
            result = (result * value) % mod;
        }
        return result;
    }
}
