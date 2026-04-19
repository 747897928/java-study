package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 76
 * Minimum Window Substring
 * 原题链接：https://leetcode.com/problems/minimum-window-substring/description/
 *
 * 给你两个字符串 s 和 t。
 * 请你返回 s 中涵盖 t 所有字符的最小子串。
 * 如果 s 中不存在这样的子串，则返回空字符串 ""。
 *
 * 注意：
 *
 * - 对于 t 中重复出现的字符，窗口里该字符的数量也必须不少于 t 里的数量。
 * - 如果 s 中存在这样的最小覆盖子串，题目保证答案唯一。
 *
 * 示例 1：
 *
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * 解释："BANC" 是最短的覆盖子串。
 *
 * 示例 2：
 *
 * 输入：s = "a", t = "a"
 * 输出："a"
 *
 * 示例 3：
 *
 * 输入：s = "a", t = "aa"
 * 输出：""
 *
 * 约束：
 *
 * - 1 <= s.length, t.length <= 10^5
 * - s 和 t 由英文字母组成
 *
 * 笔记
 *
 * 这题很适合拿来练“从暴力法推到滑动窗口”。
 *
 * 如果我是第一次见到这题，最自然的起点其实不是滑动窗口，
 * 而是：
 *
 * 1. 枚举 s 的所有子串
 * 2. 检查这个子串能不能覆盖 t
 * 3. 在所有合法子串里找最短的那个
 *
 * 这个起点是对的。
 * 真正要训练的能力不是“一眼秒出最优解”，
 * 而是：
 *
 * - 先把暴力法写出来
 * - 再看看重复计算在哪里
 * - 再决定用什么工具消掉重复
 *
 * 对这题来说，重复主要有两个：
 *
 * 1. 很多相邻子串高度重叠
 * 2. “当前窗口是否已经覆盖 t”会被反复判断
 *
 * 所以最后会落到经典滑动窗口：
 *
 * - right 右移，扩窗口，直到刚好满足覆盖条件
 * - left 右移，缩窗口，直到刚好不满足覆盖条件
 * - 在每个满足条件的窗口里更新最短答案
 *
 * 这题最容易卡住的点通常有 4 个：
 *
 * 1. 为什么 t 里有重复字符时，不能只看“出现过没有”
 * 2. 为什么这里要统计“满足需求的字符种类数”，而不是字符总数
 * 3. 为什么缩窗时必须用 while，不能只缩一次
 * 4. 为什么要先判断 window[d] == need[d]，再做 window[d]--
 *
 * 下面的方法注释会把这些点都展开。
 *
 * 文字版滑动窗口示意：
 *
 * s = A D O B E C O D E B A N C
 * t = A B C
 *
 * 第一次满足条件时：
 * [A D O B E C]
 * 这是一个可行窗口，长度 6
 *
 * 然后 left 开始缩：
 * 去掉 A 之后，窗口不再覆盖 A、B、C
 * 所以这次缩窗结束
 *
 * 继续扩到右边：
 * A D O B E C O D E B A
 * 这时又重新满足条件
 *
 * 再缩：
 * 最后会得到更短的
 * [B A N C]
 *
 * 这就是这题反复做的事情：
 * 扩大 -> 满足 -> 缩小 -> 刚好失效 -> 再扩大
 *
 * 为什么图片不能完全固化到代码里？
 *
 * 因为图本身容易丢，但“窗口推进过程”和“每一步在判断什么”是能长期留在注释里的。
 * 所以这里把图转成了：
 *
 * 1. 文字版示意
 * 2. traceMinWindow(...) 的过程打印
 *
 * 以后就算看不到原图，也能靠代码把过程重新跑出来。
 *
 * 和 shl 里哪些题能互相类比？
 *
 * - NumberOfWaysToObtainTheLongestConsecutiveOnes
 *   共同点：都是滑动窗口，都是 right 扩、left 缩
 *   区别：那题是“最多 K 个坏字符”，这题是“必须覆盖所有需求字符”
 *
 * - LongestStableSensorWindowAfterRepairingKFailures
 *   共同点：也是连续窗口 + 维护窗口内部统计量
 *
 * 如果你以后在新题里看到这些信号：
 *
 * - 题目对象是子串 / 子数组 / 连续段
 * - 要找最短 / 最长满足条件的连续窗口
 * - 窗口内某种统计量可以增量维护
 *
 * 那就优先怀疑是滑动窗口。
 *
 * <p>create: 2026-04-16 22:26:15</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class MinimumWindowSubstring {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String source = scanner.next();
        String target = scanner.next();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * String source = "ADOBECODEBANC";
         * String target = "ABC";
         */

        MinimumWindowSubstring solver = new MinimumWindowSubstring();
        System.out.println(solver.minWindow(source, target));

        /*
         * 如果想看窗口是怎么一步步移动的，可以临时打开下面这行：
         * System.out.println(solver.traceMinWindow(source, target));
         *
         * 如果想先看最朴素的暴力版，也可以临时打开下面这行：
         * System.out.println(solver.minWindowBruteForce(source, target));
         */
    }

    /**
     * 最朴素的暴力法。
     *
     * 这版只适合学习，不适合大数据量。
     *
     * 思路非常直接：
     *
     * 1. 枚举所有子串 [start, end]
     * 2. 判断这个子串是不是覆盖了 t
     * 3. 如果覆盖，就试着更新最短答案
     *
     * 为什么这里最自然是两层 for？
     *
     * 因为一个子串就由两个端点决定：
     * - 左端点 start
     * - 右端点 end
     *
     * 以后你如果看到“我怎么把所有子串都找出来”这种题，
     * 第一反应就先用两层 for 去兜底。
     */
    public String minWindowBruteForce(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }

        int[] need = buildNeed(t);
        int bestStart = -1;
        int bestLength = Integer.MAX_VALUE;

        for (int start = 0; start < s.length(); start++) {
            int[] window = new int[128];
            for (int end = start; end < s.length(); end++) {
                window[s.charAt(end)]++;
                if (covers(window, need) && end - start + 1 < bestLength) {
                    bestStart = start;
                    bestLength = end - start + 1;
                }
            }
        }

        return bestStart == -1 ? "" : s.substring(bestStart, bestStart + bestLength);
    }

    /**
     * 正式解法：滑动窗口。
     *
     * 先说窗口里要维护哪些东西：
     *
     * 1. need[c]
     *    t 里字符 c 需要多少个
     *
     * 2. window[c]
     *    当前窗口里字符 c 有多少个
     *
     * 3. needKinds
     *    t 里一共有多少种“需要关心的字符”
     *
     * 4. validKinds
     *    当前窗口里，已经“数量达标”的字符种类数
     *
     * 这里为什么统计“种类数”，而不是“字符总数”？
     *
     * 因为 t 可能有重复字符。
     * 例如：
     * t = "AABC"
     *
     * 这时需要的是：
     * - A 至少 2 个
     * - B 至少 1 个
     * - C 至少 1 个
     *
     * 真正重要的是：
     * “A 这类字符达标了吗？B 这类字符达标了吗？C 这类字符达标了吗？”
     *
     * 所以这里更自然的计数方式是：
     * 统计当前有几种字符已经达标。
     *
     * 当 validKinds == needKinds 时，
     * 说明窗口已经覆盖了 t。
     *
     * 整个滑窗过程就是：
     *
     * 1. right 右移，把新字符加进窗口
     * 2. 如果这个字符刚好让某一种字符达标，validKinds++
     * 3. 一旦窗口已经覆盖 t，就开始移动 left 缩窗
     * 4. 缩窗过程中不断尝试更新最短答案
     * 5. 如果某种字符被缩到不达标了，validKinds--，停止缩窗
     *
     * 为什么内层必须是 while，不是 if？
     *
     * 因为一次缩一个字符通常不够。
     *
     * 比如：
     * s = "AAAABC", t = "ABC"
     *
     * 当窗口第一次满足时，前面有很多多余的 A。
     * 这时要一路连续缩，直到窗口刚好不能再缩为止。
     * 所以必须是 while。
     *
     * 为什么缩窗时要先判断：
     * if (window[d] == need[d]) { validKinds--; }
     * 再 window[d]-- ？
     *
     * 因为我们关心的是：
     * “移除这个字符之前，它是不是刚好达标？”
     *
     * 如果它原本刚好达标，那移掉一个后就会变成不达标。
     * 所以必须先判断，再减。
     *
     * 如果先减，再判断，就看不到“减之前刚好达标”这个事实了。
     */
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }

        int[] need = buildNeed(t);
        int[] window = new int[128];
        int needKinds = countNeedKinds(need);
        int validKinds = 0;

        int left = 0;
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);

            if (need[current] > 0) {
                window[current]++;
                if (window[current] == need[current]) {
                    validKinds++;
                }
            }

            while (validKinds == needKinds) {
                if (right - left + 1 < bestLength) {
                    bestStart = left;
                    bestLength = right - left + 1;
                }

                char removed = s.charAt(left);
                if (need[removed] > 0) {
                    if (window[removed] == need[removed]) {
                        validKinds--;
                    }
                    window[removed]--;
                }
                left++;
            }
        }

        return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
    }

    /**
     * 这个方法专门把窗口变化过程打印出来。
     *
     * 作用不是提交题目，而是把文章里的图改成代码里能长期保存的“可运行版过程说明”。
     *
     * 你以后如果忘了窗口到底怎么扩、怎么缩，
     * 直接跑这个方法，看输出就行。
     */
    public String traceMinWindow(String s, String t) {
        StringBuilder trace = new StringBuilder();
        int[] need = buildNeed(t);
        int[] window = new int[128];
        int needKinds = countNeedKinds(need);
        int validKinds = 0;
        int left = 0;
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;

        trace.append("source = ").append(s).append('\n');
        trace.append("target = ").append(t).append('\n');
        trace.append("needKinds = ").append(needKinds).append('\n');
        trace.append('\n');

        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);
            trace.append("right -> ").append(right).append(", add '").append(current).append("'\n");

            if (need[current] > 0) {
                window[current]++;
                if (window[current] == need[current]) {
                    validKinds++;
                }
            }

            trace.append("window = [").append(left).append(", ").append(right).append("] -> ")
                .append(s.substring(left, right + 1)).append('\n');
            trace.append("validKinds = ").append(validKinds).append(" / ").append(needKinds).append('\n');

            while (validKinds == needKinds) {
                if (right - left + 1 < bestLength) {
                    bestStart = left;
                    bestLength = right - left + 1;
                    trace.append("update best = ")
                        .append(s.substring(bestStart, bestStart + bestLength))
                        .append(", length = ").append(bestLength).append('\n');
                }

                char removed = s.charAt(left);
                trace.append("left -> ").append(left).append(", remove '").append(removed).append("'\n");
                if (need[removed] > 0) {
                    if (window[removed] == need[removed]) {
                        validKinds--;
                    }
                    window[removed]--;
                }
                left++;
                if (left <= right) {
                    trace.append("window becomes [").append(left).append(", ").append(right).append("] -> ")
                        .append(s.substring(left, right + 1)).append('\n');
                } else {
                    trace.append("window becomes empty\n");
                }
                trace.append("validKinds = ").append(validKinds).append(" / ").append(needKinds).append('\n');
            }
            trace.append('\n');
        }

        trace.append("final answer = ");
        if (bestLength == Integer.MAX_VALUE) {
            trace.append("\"\"");
        } else {
            trace.append(s.substring(bestStart, bestStart + bestLength));
        }
        return trace.toString();
    }

    private int[] buildNeed(String t) {
        int[] need = new int[128];
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }
        return need;
    }

    private int countNeedKinds(int[] need) {
        int kinds = 0;
        for (int freq : need) {
            if (freq > 0) {
                kinds++;
            }
        }
        return kinds;
    }

    private boolean covers(int[] window, int[] need) {
        for (int i = 0; i < need.length; i++) {
            if (window[i] < need[i]) {
                return false;
            }
        }
        return true;
    }
}
