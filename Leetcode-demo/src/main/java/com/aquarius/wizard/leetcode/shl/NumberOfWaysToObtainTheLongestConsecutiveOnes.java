package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 *
 * Given a binary string S consisting only of 0s and 1s, you may change at most K zeros inside a
 * substring into ones. Let L be the maximum possible length of a substring that can be turned into
 * all ones in this way.
 *
 * Write an algorithm to find the number of substrings whose length is exactly L and that contain at
 * most K zeros.
 *
 * Input
 *
 * The first line of the input consists of the string S.
 * The second line consists of an integer changeK, representing the maximum number of zeros that can
 * be changed (K).
 *
 * Output
 *
 * Print an integer representing the number of substrings whose length is equal to the maximum
 * achievable value L and that contain at most K zeros.
 *
 * Constraints
 *
 * 1 <= size of string <= 2*10^5
 * 0 <= changeK <= size
 *
 * Example
 *
 * Input:
 * 1010101
 * 1
 *
 * Output:
 * 3
 *
 * Explanation:
 * The maximum achievable length is 3.
 * There are exactly three substrings of length 3 that contain at most one 0: the three occurrences
 * of 101. Each such substring can be turned into 111 by changing one 0. So, the output is 3.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：滑动窗口。
 * 校对：原题里的 different ways 有明显歧义。这里直接按当前代码改写为“统计最优窗口数”的学习版定稿，不再保留会把读者带向“结果串去重”的原句。
 * 提示：校验器里保留了 `S = 101, K = 0` 这种能区分“窗口数”和“结果串数”的反例，方便后续继续考证。
 * 相似题：LeetCode 1004 Max Consecutive Ones III、所有“最多 K 个坏字符”的最长子串题。
 *
 * 这题不要一上来就纠结“ways”是什么意思。
 * 当前学习版已经定稿成：
 *
 * 1. 先求最长可行窗口长度 L
 * 2. 再统计有多少个长度恰好为 L 的窗口满足“0 的个数 <= K”
 *
 * 所以这里其实是两个小问题拼起来：
 *
 * 问题 A：最长窗口多长
 * 问题 B：这种最长窗口有几个
 *
 * A 用滑动窗口最自然。
 * B 在知道 L 之后，再扫一遍计数就行。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class NumberOfWaysToObtainTheLongestConsecutiveOnes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String binaryString = scanner.next();
        int changeK = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * String binaryString = "1010101";
         * int changeK = 1;
         */

        NumberOfWaysToObtainTheLongestConsecutiveOnes solver = new NumberOfWaysToObtainTheLongestConsecutiveOnes();
        System.out.println(solver.countWays(binaryString, changeK));
    }

    /**
     * 这一题之所以分两步写，是因为：
     *
     * 你在第一次扫描时，并不知道最终的最优长度 L 是多少。
     *
     * 所以正确的思路是：
     *
     * 第一步：先只关心“最长能到多少”
     * 第二步：长度 L 已经确定后，再专门去数“有多少个这样的窗口”
     *
     * 这也是很多面试题会用到的套路：
     *
     * 先求最优值，再求达到最优值的方案数 / 窗口数 / 区间数。
     */
    public int countWays(String binaryString, int changeK) {
        // 第一步：先求“最长合法窗口长度”到底是多少。
        int maxLength = findMaximumLength(binaryString, changeK);

        // 这里其实是防御性写法。
        // 正常只要字符串非空，maxLength 至少会是 1。
        // 保留这句只是为了让方法在极端边界下也有定义。
        if (maxLength == 0) {
            return 1;
        }

        // prefixZeros[i] 表示：前 i 个字符里一共有多少个 0。
        //
        // 注意这里的“前 i 个”是左闭右开思路：
        // prefixZeros[0] = 0，表示一个字符都没看时，0 的个数是 0。
        //
        // 这样一来，任意子串 [start, end] 里的 0 的数量，
        // 都可以用前缀和快速算出来。
        int[] prefixZeros = new int[binaryString.length() + 1];
        for (int i = 0; i < binaryString.length(); i++) {
            // 先继承前一个位置的 0 的总数，
            // 如果当前字符本身是 0，就再加 1。
            prefixZeros[i + 1] = prefixZeros[i] + (binaryString.charAt(i) == '0' ? 1 : 0);
        }

        // 第二步：长度 maxLength 已经确定后，
        // 现在只需要数“有多少个长度恰好为 maxLength 的窗口是合法的”。
        int ways = 0;

        // start 表示窗口起点。
        // 终点自然就是 start + maxLength - 1。
        //
        // 所以这里的循环条件 start + maxLength <= binaryString.length()
        // 等价于“这个长度为 maxLength 的窗口不能越界”。
        for (int start = 0; start + maxLength <= binaryString.length(); start++) {
            // 利用前缀和求子串 [start, start + maxLength - 1] 里 0 的个数。
            //
            // prefixZeros[start + maxLength]
            // 表示前 start + maxLength 个字符里 0 的总数。
            //
            // prefixZeros[start]
            // 表示前 start 个字符里 0 的总数。
            //
            // 两者相减，剩下的正好就是这个窗口内部的 0 的个数。
            int zeroCount = prefixZeros[start + maxLength] - prefixZeros[start];

            // 如果这个窗口里的 0 的个数不超过 K，
            // 说明它可以通过“最多改 K 个 0”为 1 变成全 1，
            // 所以它是一个有效的最优窗口。
            if (zeroCount <= changeK) {
                ways++;
            }
        }

        // ways 统计的就是：
        // “长度恰好等于最优长度 maxLength，并且窗口内 0 的个数 <= K”的窗口数量。
        return ways;
    }

    /**
     * 这是标准的“窗口内坏字符数量不超过 K”的滑动窗口。
     *
     * 这里的“坏字符”就是 0。
     * 因为Question允许我们把最多 K 个 0 改成 1，
     * 所以一个窗口只要满足：
     *
     * zeroCount <= K
     *
     * 它就是合法窗口。
     *
     * 这类题为什么适合滑动窗口？
     *
     * 因为窗口是否合法，只和“当前这一段里有几个 0”有关，
     * 不需要重新枚举整段内容。
     *
     * 你最困惑的点是：
     *
     * “为什么如果 0 的数量超了，就一定要移动 left 缩窗？”
     *
     * 核心原因是：
     *
     * 1. 当前 right 已经固定了
     *    也就是说，我们现在研究的是“所有以 right 结尾的子串”。
     *
     * 2. 如果当前窗口里 0 太多了，
     *    那么这个窗口已经不合法。
     *
     * 3. 在 right 固定不动的前提下，
     *    想让窗口重新合法，唯一办法就是把左边的一些字符扔掉。
     *
     * 为什么只能扔左边，不能动右边？
     *
     * 因为这轮 for 正在讨论“以这个 right 作为结尾”的窗口。
     * 如果你把 right 往回退，那就不是当前这轮的问题了。
     *
     * 所以：
     *
     * - right 负责扩张，尝试让答案变长
     * - left 负责在窗口失效时修复合法性
     *
     * 这就是滑动窗口的典型分工。
     *
     * 再往深一点理解：
     *
     * 当 right 固定时，
     * 我们希望找到“以 right 结尾的最长合法窗口”。
     *
     * 如果当前 [left, right] 已经不合法，
     * 那么任何更靠左的起点只会让窗口更长、0 更多或至少不少，
     * 不可能突然变合法。
     *
     * 所以这时 left 必须右移，直到窗口重新满足 zeroCount <= K。
     *
     * 一旦 while 结束，就说明：
     *
     * - 当前 [left, right] 是合法的
     * - 并且它是“以 right 结尾时，left 能做到的最靠左的合法位置”
     *
     * 于是 right - left + 1
     * 就是“以 right 结尾的最长合法窗口长度”。
     *
     * 这就是为什么每轮都可以放心写：
     *
     * maxLength = Math.max(maxLength, right - left + 1)
     *
     * 你可以用样例 S = 1010101, K = 1 来手推：
     *
     * right = 0，窗口 = "1"，0 的个数 = 0，合法，长度 = 1
     * right = 1，窗口 = "10"，0 的个数 = 1，合法，长度 = 2
     * right = 2，窗口 = "101"，0 的个数 = 1，合法，长度 = 3
     * right = 3，窗口 = "1010"，0 的个数 = 2，不合法
     *
     * 这时 right 已经固定在 3。
     * 想让窗口重新合法，只能移动 left：
     *
     * - 去掉左边的 '1'，窗口变 "010"，0 的个数还是 2，仍不合法
     * - 再去掉左边的 '0'，窗口变 "10"，0 的个数变 1，重新合法
     *
     * 所以最后以 right = 3 结尾时，
     * 最长合法窗口就是 "10"，长度 2。
     */
    private int findMaximumLength(String binaryString, int changeK) {
        // left 表示当前窗口左边界。
        int left = 0;
        // zeroCount 记录当前窗口 [left, right] 里一共有多少个 0。
        int zeroCount = 0;
        // maxLength 记录扫描过程中见过的最大合法窗口长度。
        int maxLength = 0;
        for (int right = 0; right < binaryString.length(); right++) {
            // right 每次向右扩一步，把新字符纳入窗口。
            if (binaryString.charAt(right) == '0') {
                zeroCount++;
            }

            // 如果 0 的数量已经超过 K，说明当前窗口不合法。
            //
            // 这时 right 不能回退，因为这轮正在研究“以 right 结尾”的窗口。
            // 唯一能做的就是不断右移 left，把左边字符移出窗口，直到重新合法。
            while (zeroCount > changeK) {
                // 如果移出的是一个 0，那么窗口内 0 的数量要同步减 1。
                if (binaryString.charAt(left) == '0') {
                    zeroCount--;
                }
                // left 右移，表示把原来的最左字符扔出窗口。
                left++;
            }

            // while 结束后，当前窗口一定合法。
            //
            // 并且因为 left 是在“刚刚合法”为止才停下来的，
            // 所以 [left, right] 就是“以 right 结尾时能取得的最长合法窗口”。
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}
