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
 * 这题更重要的不是把答案背下来，而是把“我到底应该怎么想”固定住。
 *
 * 如果我在考场上第一次看到这题，我最自然的第一反应应该是：
 *
 * 1. 题目在问子串，那我先想怎么把所有子串都覆盖到。
 * 2. 一个子串由 start 和 end 两个端点决定，所以最原始写法通常就是两层 for。
 * 3. 每拿到一个子串，再去数里面有几个 0，看它合不合法。
 *
 * 也就是说，这题最朴素的起点根本不是滑动窗口，
 * 而是“先用两层 for 把所有连续段枚举出来”。
 *
 * 这个起点是对的，不是错的。
 *
 * 我现在最需要练出来的，不是“秒出最优解”，
 * 而是下面这条固定思路：
 *
 * 1. 先写暴力法
 * 2. 再找重复计算
 * 3. 再看哪种工具正好能消掉这类重复
 *
 * 对这题来说：
 *
 * - 暴力法：两层 for 枚举所有子串
 * - 重复计算 1：很多窗口里的 0 被反复统计
 * - 重复计算 2：最长长度找到之后，又要反复问“固定长度窗口里有几个 0”
 *
 * 所以优化才会分成两步：
 *
 * 1. 问题 A 用滑动窗口
 *    因为它是“连续窗口 + 最多 K 个坏字符”的典型模型
 *
 * 2. 问题 B 用前缀和
 *    因为它是“很多次问某个固定区间里有几个 0”的典型模型
 *
 * 以后看到新题时，可以先用下面这张简化判断表：
 *
 * 一、什么时候优先想滑动窗口
 *
 * - 题目对象是子串 / 子数组 / 连续段
 * - 条件是“最多 K 个坏东西 / 至少 K 个坏东西 / 不超过某个上限”
 * - 右边界往右扩一格时，窗口信息可以增量维护
 * - 窗口不合法时，只能靠移动左边界修复
 *
 * 满足这些信号时，就优先怀疑是滑动窗口。
 *
 * 二、什么时候优先想前缀和
 *
 * - 题目会反复问“某个区间里有几个什么 / 区间和是多少”
 * - 如果每次都重新扫这段区间，会产生大量重复工作
 * - 这个统计量可以先累积起来，再通过“两份前缀相减”得到
 *
 * 满足这些信号时，就优先怀疑是前缀和。
 *
 * 在 shl 题库里，可以顺手拿这些题互相对照：
 *
 * - 滑动窗口味道很明显的：
 *   LongestStableSensorWindowAfterRepairingKFailures
 *   NumberOfWaysToObtainTheLongestConsecutiveOnes
 *
 * - 前缀和味道很明显的：
 *   PivotIndex
 *   RangeSumQueryImmutable
 *   SubarraySumEqualsK
 *
 * - 明显不是滑动窗口的：
 *   CountElementsStrictlyLessThanK
 *   ReplaceValuesWithTheirIndexPositions
 *
 * 这样对照着看，比单独背“滑动窗口定义”更容易真的形成感觉。
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
        /*
         * 如果想先看最朴素的做法，可以临时打开下面这些：
         *
         * System.out.println(solver.findMaximumLengthBruteForce(binaryString, changeK));
         * System.out.println(solver.countWaysBruteForce(binaryString, changeK));
         */
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
     *
     * 你刚开始刷题时，很自然会先想到：
     *
     * 1. 我怎么把所有子串都找出来？
     * 2. 哪些子串满足“0 的个数 <= K”？
     * 3. 最长的是多长？
     * 4. 这种最长子串有几个？
     *
     * 这个想法其实没有错，它对应的就是暴力解法。
     *
     * 最原始的暴力法通常会写成两层 for：
     *
     * - 外层枚举 start
     * - 内层枚举 end
     *
     * 这样就能把所有子串 [start, end] 全部覆盖到。
     *
     * 所以你以后如果再遇到“子串 / 子数组”题，不要先怕。
     * 第一反应先固定成：
     *
     * “我要不要先用两层 for，把所有连续段枚举出来？”
     *
     * 这是一个很好的起点。
     *
     * 这题从暴力法往后推，会自然拆成两个小问题：
     *
     * A. 最长合法子串长度是多少
     * B. 长度等于这个最优值的合法子串有多少个
     *
     * 当前这个方法就是：
     * - 先用 findMaximumLength(...) 解决 A
     * - 再用前缀和高效解决 B
     *
     * 你刚才提到一种很自然的想法：
     * “我能不能用 Map，key 是长度，value 是这个长度下符合条件的子串或数量？”
     *
     * 这个想法不是完全错，而是它并没有打到这题真正的瓶颈。
     *
     * 这题真正难的不是“怎么存结果”，
     * 而是“怎么高效判断一个窗口里有几个 0”。
     *
     * Map 更像是在解决“结果归档”的问题，
     * 但当前性能压力主要来自“区间统计”。
     *
     * 所以这里优先考虑的工具不是 Map，
     * 而是更贴合问题本质的：
     *
     * - 滑动窗口：解决最长长度
     * - 前缀和：解决固定长度窗口计数
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
        //
        // 为什么数组长度要 +1？
        //
        // 因为我们想让“空前缀”也有一个位置。
        // 这样 prefixZeros[0] = 0 才有意义。
        //
        // 例如字符串是：
        // index:      0 1 2 3
        // string:     1 0 1 0
        //
        // 那么 prefixZeros 会写成：
        // 下标:       0 1 2 3 4
        // 含义:   前0个 前1个 前2个 前3个 前4个
        // 数值:       0 0 1 1 2
        //
        // 有了这个“多出来的第 0 格”，
        // 任意窗口 [start, end] 里的 0 的个数都能统一写成：
        //
        // prefixZeros[end + 1] - prefixZeros[start]
        //
        // 不需要再对 start = 0 单独写特殊判断。
        //
        // 这一点其实非常重要：
        // 前缀和数组多出来的那一格，不是“浪费空间”，
        // 而是故意留给“空前缀”用的。
        //
        // 以后只要看到 prefix 长度开成 n + 1，
        // 你脑子里就可以直接翻译成：
        // “第 0 格表示前 0 个元素。”
        //
        // 为什么这里会想到前缀和？
        //
        // 因为第二步里，我们要枚举很多个“固定长度 = maxLength”的窗口。
        // 如果每个窗口都再从头扫一遍数 0 的个数，
        // 那就又回到两层 for 甚至更慢了。
        //
        // 前缀和的作用就是：
        // 把“一个窗口里有几个 0”这个问题，
        // 从“每次重新数”变成“两个前缀相减，一下得到”。
        //
        // 所以这里不是为了炫技才用前缀和，
        // 而是因为“同一个统计问题会被重复问很多次”，
        // 这正是前缀和最适合出场的信号。
        //
        // 如果你明天再看这段代码时还是有点晕，
        // 就先回头看 countWaysBruteForce(...)：
        // 那个版本是“每个窗口重新数一遍 0”；
        // 当前这个版本只是把“重新数一遍”换成了“两份前缀相减”。
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
     * 这个版本是你最容易先想到的暴力法。
     *
     * 它不优化，只做一件事：
     * 把所有子串都枚举出来，然后看哪个合法，最后取最大长度。
     *
     * 写法上最自然就是两层 for：
     *
     * 1. 外层枚举起点 start
     * 2. 内层枚举终点 end
     *
     * 为什么不是三层 for？
     *
     * 因为“所有子串”只需要两个端点就能确定：
     * [start, end]
     *
     * 这也是你以后做连续段题时最先可以固定下来的模板：
     * “要枚举所有子串，先写两层 for。”
     */
    public int findMaximumLengthBruteForce(String binaryString, int changeK) {
        int maxLength = 0;
        for (int start = 0; start < binaryString.length(); start++) {
            int zeroCount = 0;
            for (int end = start; end < binaryString.length(); end++) {
                if (binaryString.charAt(end) == '0') {
                    zeroCount++;
                }
                if (zeroCount <= changeK) {
                    maxLength = Math.max(maxLength, end - start + 1);
                }
            }
        }
        return maxLength;
    }

    /**
     * 这个方法只是把当前滑动窗口主解开放成 public，
     * 方便和暴力版做一一对照。
     */
    public int findMaximumLengthForLearning(String binaryString, int changeK) {
        return findMaximumLength(binaryString, changeK);
    }

    /**
     * 这是完整的暴力版：
     *
     * 1. 先暴力求最长长度
     * 2. 再暴力数有多少个长度等于这个最长值的合法子串
     *
     * 这个版本的价值不在效率，而在于帮你看清楚：
     * 当前优化版到底省掉了哪些重复工作。
     *
     * 你可以把两者对应起来：
     *
     * - 这里的 findMaximumLengthBruteForce
     *   对应优化版里的滑动窗口
     *
     * - 这里第二轮再枚举所有长度为 maxLength 的窗口、重新数 0
     *   对应优化版里的“前缀和快速算 0 的个数”
     *
     * 这个方法其实很适合你现在的阶段：
     * 如果以后你在考场上先只能想到这个版本，不要慌。
     *
     * 真正更值得训练的是：
     * 先把这个版本写出来，
     * 再去问自己：
     *
     * “我是不是在很多窗口上反复数同一种东西？”
     *
     * 一旦答案是“是”，就开始往前缀和或滑动窗口想。
     */
    public int countWaysBruteForce(String binaryString, int changeK) {
        int maxLength = findMaximumLengthBruteForce(binaryString, changeK);
        int ways = 0;
        for (int start = 0; start + maxLength <= binaryString.length(); start++) {
            int zeroCount = 0;
            for (int i = start; i < start + maxLength; i++) {
                if (binaryString.charAt(i) == '0') {
                    zeroCount++;
                }
            }
            if (zeroCount <= changeK) {
                ways++;
            }
        }
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
     * 最困惑的点是：
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
     * 你如果在考场上不知道能不能上滑窗，
     * 可以先从最笨的两层 for 开始想：
     *
     * for (int start = 0; start < n; start++) {
     *     int zeroCount = 0;
     *     for (int end = start; end < n; end++) {
     *         if (s.charAt(end) == '0') {
     *             zeroCount++;
     *         }
     *         if (zeroCount <= k) {
     *             // 更新答案
     *         }
     *     }
     * }
     *
     * 从这个暴力法往后推，你会发现：
     *
     * 1. 当 start 固定时，end 是一直往右走的
     * 2. zeroCount 也不需要重新数，可以边走边维护
     * 3. 如果当前窗口不合法，除了把左端点往右挪，没有别的修复办法
     *
     * 这三句话合在一起，其实就是滑动窗口。
     *
     * 所以以后你看到这种题，
     * 不要要求自己“一眼想到滑窗”。
     * 更实用的做法是：
     *
     * 先想到两层 for 的暴力法，
     * 再问自己：
     *
     * - 我维护的是不是一个连续窗口？
     * - 窗口信息能不能增量更新？
     * - 不合法时是不是只能丢左边？
     *
     * 如果这三句都能回答“是”，那大概率就是滑动窗口题。
     *
     * 换句话说，
     * 你不是要训练自己“看到题马上背出滑动窗口”；
     * 你要训练的是：
     *
     * “我能不能先写出两层 for 的暴力法，
     * 然后发现它其实就是一个连续窗口在移动。”
     *
     * 这是更稳、更可复制的能力。
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
     *
     * 另外你担心的这一句会不会越界：
     *
     * if (binaryString.charAt(left) == '0')
     *
     * 这里其实是安全的。
     *
     * 原因是：
     *
     * 1. 进入 while 的前提是 zeroCount > changeK
     * 2. 这说明当前窗口 [left, right] 里确实还有字符，而且窗口不为空
     * 3. 只要窗口不为空，就有 left <= right
     * 4. 而 right 本来就永远小于 binaryString.length()
     *
     * 所以在 while 体里访问 charAt(left) 时，
     * 实际上始终满足：
     *
     * 0 <= left <= right < binaryString.length()
     *
     * 真正的顺序是：
     * - 先读取当前 left 位置的字符
     * - 再决定 zeroCount 要不要减
     * - 最后 left++
     *
     * 不会发生“left 已经跑到字符串外面了，才去 charAt(left)”这种情况。
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
