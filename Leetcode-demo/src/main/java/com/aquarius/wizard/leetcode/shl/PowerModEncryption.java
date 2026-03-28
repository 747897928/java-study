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
 */
public class PowerModEncryption {

    private static final long MOD = 1_000_000_007L;

    public static void main(String[] args) {
        long secretCode = 2L;
        long firstKey = 3L;
        long secondKey = 4L;

        PowerModEncryption solver = new PowerModEncryption();
        System.out.println(solver.encryptCode(secretCode, firstKey, secondKey));
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
     * 快速幂的本质，不是“背模板”，而是：
     *
     * 当 exponent 的二进制某一位是 1，
     * 就把当前的 base 贡献乘进答案里。
     *
     * 例如 exponent = 13，它的二进制是 1101。
     *
     * 13 = 8 + 4 + 1
     *
     * 所以：
     *
     * base^13 = base^8 * base^4 * base^1
     *
     * while 循环做的事情就是不断把：
     *
     * base, base^2, base^4, base^8 ...
     *
     * 这些“2 的幂次块”准备出来。
     *
     * 这样时间复杂度就从 O(exponent) 变成 O(log exponent)。
     */
    private long modPow(long base, long exponent, long mod) {
        long result = 1L % mod;
        long value = base % mod;
        long power = exponent;
        while (power > 0) {
            if ((power & 1L) == 1L) {
                result = (result * value) % mod;
            }
            value = (value * value) % mod;
            power >>= 1;
        }
        return result;
    }
}
