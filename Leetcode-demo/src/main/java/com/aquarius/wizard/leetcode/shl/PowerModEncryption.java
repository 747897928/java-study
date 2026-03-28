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
 */
public class PowerModEncryption {

    private static final long MOD = 1_000_000_007L;

    public long encryptCode(long secretCode, long firstKey, long secondKey) {
        long first = modPow(secretCode, firstKey, 10L);
        return modPow(first, secondKey, MOD);
    }

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
