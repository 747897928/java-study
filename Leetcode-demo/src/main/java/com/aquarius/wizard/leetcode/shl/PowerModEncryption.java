package com.aquarius.wizard.leetcode.shl;

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
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：快速幂、模运算、运算顺序。
 * 校对：示例输出 4096 已由 docx 原文和代码校验确认。
 * 易错点：不是直接算 S^(N*M)，而是先算 S^N % 10，再对这个结果做 M 次幂。
 * 相似题：任何 a^b mod m 的题都和这里同型，例如 LeetCode 50 / 372。
 *
 * 学习这题时，你要真的建立下面这个反应：
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
        long secretCode = 2L;
        long firstKey = 3L;
        long secondKey = 4L;

        PowerModEncryption solver = new PowerModEncryption();
        System.out.println(solver.encryptCode(secretCode, firstKey, secondKey));
        System.out.println(solver.encryptCode2(secretCode, firstKey, secondKey));
    }

    /**
     * 这一层只做“按题目括号顺序拆表达式”。
     *
     * 你可以把它读成：
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
     * 你问的这个公式是对的：
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
     * 你现在先记住这一句就够了：
     *
     * “把 exponent 写成若干个 2 的幂之和，
     * 然后把对应的 base^(2^k) 乘起来”
     *
     * 这样时间复杂度就从 O(exponent) 变成 O(log exponent)。
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
        long result = 1L % mod;
        /*
         * result 表示“目前已经确定要乘进答案里的那一部分”。
         *
         * 一开始为什么是 1？
         *
         * 因为乘法单位元就是 1。
         * 你可以把它理解成：答案一开始是“空乘积”。
         */
        long value = base % mod;
        /*
         * value 表示“当前这一轮的 base 块”。
         *
         * 第一轮：
         * value = base^1 % mod
         *
         * 第二轮平方后：
         * value = base^2 % mod
         *
         * 第三轮再平方：
         * value = base^4 % mod
         *
         * 第四轮再平方：
         * value = base^8 % mod
         *
         * 所以 value 会依次变成：
         *
         * base^1, base^2, base^4, base^8, base^16 ...
         */
        long power = exponent;
        /*
         * power 是还没处理完的指数部分。
         * 我们会不停看它的二进制最低位，然后把它右移。
         */
        while (power > 0) {
            /*
             * (power & 1L) == 1L
             *
             * 这是在问：
             *
             * “power 的二进制最低位是不是 1？”
             *
             * 如果最低位是 1，说明当前这一轮的 value
             * 对答案是有贡献的，要乘进 result。
             *
             * 例如 power = 13，二进制是 1101。
             *
             * 第 1 轮最低位是 1，对应 base^1，要乘
             * 第 2 轮最低位是 0，对应 base^2，不乘
             * 第 3 轮最低位是 1，对应 base^4，要乘
             * 第 4 轮最低位是 1，对应 base^8，要乘
             */
            if ((power & 1L) == 1L) {
                result = (result * value) % mod;
            }
            /*
             * 当前这一块处理完后，把 value 平方。
             *
             * 也就是：
             *
             * base^1 -> base^2 -> base^4 -> base^8 -> ...
             *
             * 这就是“快速幂”里“平方求幂”的来源。
             */
            value = (value * value) % mod;
            /*
             * power >>= 1
             *
             * 表示把二进制整体右移一位，
             * 也可以先理解成“把 power 除以 2，向下取整”。
             *
             * 这样下一轮就会去检查原来二进制的下一位。
             */
            power >>= 1;
        }
        return result;
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
