package com.aquarius.wizard.leetcode.shl;

import java.util.*;

/**
 * 题目
 * 
 * LeetCode 76
 * Minimum Window Substring
 * 原题链接：https://leetcode.com/problems/minimum-window-substring/description/
 * 
 * Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".
 * 
 * The testcases will be generated such that the answer is unique.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: s = "ADOBECODEBANC", t = "ABC"
 * Output: "BANC"
 * Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
 * Example 2:
 * 
 * Input: s = "a", t = "a"
 * Output: "a"
 * Explanation: The entire string s is the minimum window.
 * Example 3:
 * 
 * Input: s = "a", t = "aa"
 * Output: ""
 * Explanation: Both 'a's from t must be included in the window.
 * Since the largest window of s only has one 'a', return empty string.
 * 
 * 
 * Constraints:
 * 
 * m == s.length
 * n == t.length
 * 1 <= m, n <= 105
 * s and t consist of uppercase and lowercase English letters.
 * 
 * 
 * Follow up: Could you find an algorithm that runs in O(m + n) time?
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
 * 共同点：都是滑动窗口，都是 right 扩、left 缩
 * 区别：那题是“最多 K 个坏字符”，这题是“必须覆盖所有需求字符”
 * 
 * - LongestStableSensorWindowAfterRepairingKFailures
 * 共同点：也是连续窗口 + 维护窗口内部统计量
 * 
 * 如果你以后在新题里看到这些信号：
 * 
 * - 题目对象是子串 / 子数组 / 连续段
 * - 要找最短 / 最长满足条件的连续窗口
 * - 窗口内某种统计量可以增量维护
 * 
 * 那就优先怀疑是滑动窗口。
 * 
 * create: 2026-04-16 22:26:15</p>
 *
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

        /*String source = "ADOBECODEBANC";
        String target = "ABC";

        MinimumWindowSubstring solver = new MinimumWindowSubstring();
        System.out.println(solver.minWindow2(source, target));*/

        /*String source = "a";
        String target = "a";

        MinimumWindowSubstring solver = new MinimumWindowSubstring();
        System.out.println(solver.minWindow2(source, target));*/

        /*String source = "a";
        String target = "aa";

        MinimumWindowSubstring solver = new MinimumWindowSubstring();
        System.out.println(solver.minWindow2(source, target));*/
    }


    public String minWindow2(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();
        if (sourceLength < targetLength) {
            return "";
        }

        char[] targetChars = target.toCharArray();
        Map<Character, List<Integer>> targetCharIndexMap = new HashMap<>();
        for (int i = 0; i < targetChars.length; i++) {
            char targetChar = targetChars[i];
            List<Integer> indexList = targetCharIndexMap.getOrDefault(targetChar, new ArrayList<>());
            indexList.add(i);
            targetCharIndexMap.put(targetChar, indexList);
        }
        String result = "";
        for (int i = 0; i < sourceLength; i++) {
            // 以 i 为起点，向右扩展窗口，如果当前i的字符在 target 中，则定为起点，否则继续向右扩展，直到找到 target 中的任意一个字符为止。
            char currentLeftChar = source.charAt(i);
            List<Integer> indexList = targetCharIndexMap.get(currentLeftChar);
            if (indexList == null) {
                continue;
            } else {
                List<Integer> tempIndexList = new ArrayList<>(indexList.size());
                //加入元素代表该元素被占用。后续右指针扫描的时候自动跳过这个元素，至于为什么用List，
                // 因为存在输入：s = "a", t = "aa" 输出："", 所以我理解重复的元素个数也要算进去。
                for (int j = i; j < sourceLength; j++) {
                    char currentRightChar = source.charAt(j);
                    List<Integer> rightIndexList = targetCharIndexMap.get(currentRightChar);
                    if (rightIndexList == null) {
                        continue;
                    } else {
                        for (Integer index : rightIndexList) {
                            if (!tempIndexList.contains(index)) {
                                tempIndexList.add(index);
                                break;
                            }
                        }
                    }
                    if (tempIndexList.size() == targetChars.length) {
                        String tempResult = source.substring(i, j + 1);
                        System.out.println(tempResult);
                        if (result.equals("")) {
                            result = tempResult;
                        } else {
                            result = (result.length() >= tempResult.length()) ? tempResult : result;
                        }
                    }

                }
            }
        }
        return result;
    }

    /**
     * 基于 minWindow2 思路的“新手友好优化版”。
     *
     * 这个方法故意不直接写成后面的标准滑动窗口，
     * 也不和现有 minWindow3 用同一套变量组织方式。
     *
     * 它保留你最自然会想到的主框架：
     *
     * 1. 枚举每一个左端点 start
     * 2. 让右端点 end 从 start 一直向右扩
     * 3. 一边扩，一边判断“当前窗口是不是已经覆盖了 target”
     * 4. 当前 start 一旦找到第一个合法窗口，就立刻停止
     *
     * 这个版本主要是在 minWindow2 的基础上，做了 3 个局部优化：
     *
     * 1. 不再用“target 里每个字符对应哪些下标”来表示需求
     *    那种写法能做，但会比较绕。
     *    对这题来说，真正想表达的是：
     *    “每种字符还缺几个？”
     *
     * 2. 不再用 List.contains(...) 判断一个下标有没有被占用
     *    contains 是线性查找，放在双层循环里会比较慢。
     *    这里直接改成 remaining[c]：
     *    - remaining[c] > 0 说明当前窗口还缺字符 c
     *    - remaining[c] <= 0 说明字符 c 已经够了，甚至多了
     *
     * 3. 同一个 start 只找“第一个合法窗口”
     *    因为 start 固定时，end 再继续向右只会让窗口更长，
     *    不可能比第一个合法窗口更优，所以可以直接 break。
     *
     * 这个版本的时间复杂度仍然不是 O(m + n)。
     * 它本质上还是“枚举左端点 + 向右找第一个合法窗口”，
     * 所以最坏情况下依然接近 O(n^2)。
     *
     * 但它比原来的 minWindow2 更适合继续往下升级，
     * 因为“还缺几个字符”这个表达和后续很多字符串题是通用的。
     *
     * 你可以把它理解成：
     * minWindow2 和正式滑动窗口之间，一个更顺手、更稳定的中间版本。
     */
    public String minWindow2Optimized(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();
        if (sourceLength < targetLength) {
            return "";
        }

        char[] sourceChars = source.toCharArray();
        char[] targetChars = target.toCharArray();

        /*
         * 题目明确说 s 和 t 只由英文字母组成：
         * - 'A' 到 'Z' 的 ASCII 编码是 65 到 90
         * - 'a' 到 'z' 的 ASCII 编码是 97 到 122
         *
         * 所以开一个长度 128 的数组就够了，
         * 因为 128 已经覆盖了所有标准 ASCII 字符。
         *
         * 这里用字符本身当下标：
         * need['A'] 表示字符 'A' 需要几个
         * need['a'] 表示字符 'a' 需要几个
         */
        int[] need = new int[128];
        for (char targetChar : targetChars) {
            need[targetChar]++;
        }
        int bestStart = -1;
        int bestLength = Integer.MAX_VALUE;

        for (int start = 0; start < sourceLength; start++) {
            /*
             * 如果从当前 start 到结尾连 target 的总长度都放不下，
             * 那后面的 start 就更不可能找到合法窗口了。
             */
            if (sourceLength - start < targetLength) {
                break;
            }

            char leftChar = sourceChars[start];

            /*
             * 如果左端点这个字符根本不是 target 需要的字符，
             * 那么以它开头的窗口一定不会比去掉它之后更短。
             *
             * 例如：
             * source = "XABC", target = "ABC"
             * 以 X 开头的最短合法窗口是 "XABC"
             * 但去掉 X 后直接就是更短的 "ABC"
             */
            if (need[leftChar] == 0) {
                continue;
            }

            /*
             * remaining[c] 表示：
             * 对当前这个 start 而言，窗口还缺几个字符 c。
             *
             * 一开始窗口是空的，所以 remaining 就等于 need。
             * 每当 end 扫到一个字符 current：
             *
             * - 如果 remaining[current] > 0
             *   说明这个字符正好补上了一个“还缺的位置”
             *   那 matchedCharacters++
             *
             * - 然后 remaining[current]--
             *   表示当前窗口里多放进来了一个 current
             *
             * 为什么即使字符已经够了，也还要 --？
             *
             * 因为 remaining 允许变成负数：
             * - 0 表示刚好够
             * - 负数表示这个字符已经多出来了
             *
             * 这样表达“够了没”会比下标占位更直接。
             */
            int[] remaining = Arrays.copyOf(need, need.length);
            int matchedCharacters = 0;

            for (int end = start; end < sourceLength; end++) {
                char current = sourceChars[end];

                /*
                 * 对当前这个“新手版框架”来说，
                 * target 不需要的字符可以留在窗口里，
                 * 但没必要参与 remaining 的增减。
                 */
                if (need[current] == 0) {
                    continue;
                }

                if (remaining[current] > 0) {
                    matchedCharacters++;
                }
                remaining[current]--;

                /*
                 * matchedCharacters == targetLength
                 * 表示 target 里的每一个字符位置都已经被当前窗口补齐了。
                 *
                 * 注意这里统计的是“匹配到的字符总数”，不是“字符种类数”。
                 * 所以 target = "AABC" 时：
                 * - 第一个 A 进来，matchedCharacters + 1
                 * - 第二个 A 进来，matchedCharacters 再 + 1
                 * - 第三个 A 进来，不再增加，因为 remaining['A'] 已经 <= 0 了
                 */
                if (matchedCharacters == targetLength) {
                    int candidateLength = end - start + 1;
                    if (candidateLength < bestLength) {
                        bestStart = start;
                        bestLength = candidateLength;

                        /*
                         * 窗口长度不可能短于 target 自身长度。
                         * 一旦命中，就已经是理论最优解，可以直接返回。
                         */
                        if (bestLength == targetLength) {
                            return source.substring(bestStart, bestStart + bestLength);
                        }
                    }

                    /*
                     * 这是当前 start 下的第一个合法窗口。
                     * end 再继续向右，只会更长，不会更短。
                     */
                    break;
                }
            }
        }

        return bestStart == -1 ? "" : source.substring(bestStart, bestStart + bestLength);
    }

    /**
     * int[] need = new int[128]; 的原因是直接拿字符 c 当下标用，need[c] 不需要做任何映射。既然题目保证只包含英文字母，理论上你确实可以压到更小：
     * •
     * 只考虑大小写字母，一共 52 种字符
     * •
     * 可以自己写映射：
     * ◦
     * 'A'..'Z' 映射到 0..25
     * ◦
     * 'a'..'z' 映射到 26..51
     * 然后用 int[] need = new int[52];
     * 但这类优化收益很小，代价是代码更绕：
     * •
     * 128 个 int 只占 128 * 4 = 512 字节
     * •
     * 52 个 int 只占 208 字节
     * •
     * 只省了 304 字节，几乎可以忽略
     * •
     * 还额外增加了字符映射逻辑，代码可读性变差，常数时间也未必更好
     *
     * @param c
     * @return
     */
    private int indexMapping(char c) {
        if (c >= 'A' && c <= 'Z') return c - 'A';
        return c - 'a' + 26;
    }

    /**
     * 这是“右指针扫到目标字符，就把它从 target 里踢掉”的滑动窗口写法。
     *
     * 这个思路和“维护窗口里每种字符数量是否达标”是同一类解法，
     * 只是这里换了一个更直观的理解方式：
     *
     * 1. 先把 target 里每个字符需要几个，记到 need[c] 里
     * 2. right 向右扫时，如果当前字符正好还是 target 需要的，就把它“踢掉”
     * 3. 所有目标字符都被踢掉后，说明当前窗口已经覆盖 target
     * 4. 然后移动 left，不断缩小窗口
     * 5. left 每移走一个字符，就相当于把这个字符“还回去”
     * 6. 一旦某个必要字符被还回去后变成重新缺少，窗口就不合法了，停止缩窗
     *
     * 为什么不能真的拷贝一个 target 再做删除？
     *
     * 因为那样会频繁创建新对象或删除字符，代价很高。
     * 真正高效的做法是：用频次数组模拟“踢掉/补回”。
     *
     * 比如 target = "AABC"：
     * - need['A'] = 2
     * - need['B'] = 1
     * - need['C'] = 1
     *
     * 右指针扫到一个 A：
     * - 如果 need['A'] > 0，说明这个 A 还是需要的
     * - 那么它就成功“踢掉”了 target 里的一个 A
     * - 接着执行 need['A']--
     *
     * 左指针移走一个 A：
     * - 执行 need['A']++
     * - 如果加完后 need['A'] > 0，说明现在又缺 A 了
     * - 也就是窗口不再覆盖 target
     */
    public String minWindow3(String source, String target) {
        if (source.length() < target.length()) {
            return "";
        }

        int[] need = new int[128];
        for (int i = 0; i < target.length(); i++) {
            /*
             * target.charAt(i) 返回的是 char。
             * 在 Java 里，char 会自动转换成对应的整数编码后再作为数组下标使用。
             *
             * 例如：
             * - 'A' 会转成 65
             * - 'a' 会转成 97
             *
             * 所以 need[target.charAt(i)]++ 的意思就是：
             * 用这个字符的编码值作为下标，把对应位置的计数加一。
             */
            need[target.charAt(i)]++;
        }

        /*
         * remain 表示：target 里总共还有多少个“位置”没有被匹配到。
         *
         * 例如 target = "AABC" 时，初始 remain = 4。
         * 这里统计的是“字符总数”，不是“字符种类数”。
         */
        int remain = target.length();
        int left = 0;
        int right = 0;
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;

        while (right < source.length()) {
            char rightChar = source.charAt(right);

            /*
             * 你可以把 need[rightChar] > 0 理解成：
             * 这个字符现在仍然是 target 还需要的，所以右指针扫到它时，
             * 就等于把 target 里的一个字符“踢掉”了。
             */
            if (need[rightChar] > 0) {
                remain--;
            }

            /*
             * 无论是不是目标字符，都先把它记进窗口影响里。
             *
             * - 目标字符：可能把 remain 减少
             * - 非目标字符：会把 need[rightChar] 变成负数，表示它只是多余字符
             */
            need[rightChar]--;
            right++;

            /*
             * remain == 0 说明 target 已经被“踢空”，
             * 当前窗口已经覆盖了 target，可以开始缩左边。
             */
            while (remain == 0) {
                if (right - left < bestLength) {
                    bestStart = left;
                    bestLength = right - left;
                }

                char leftChar = source.charAt(left);

            /*
             * 左指针右移时，相当于把这个字符重新“还回去”。
             * 如果还回去之后 need[leftChar] > 0，
             * 说明这个字符变成缺少了，窗口不再合法。
             *
             * 这里最容易担心的问题是：
             * “右边扩窗时我已经一直在 need[rightChar]-- 了，
             * 那左边这里 need[leftChar]++ 会不会把那些不属于 target 的字符也算进去？”
             *
             * 不会。因为 need[c] 本身就同时表达了三种状态：
             *
             * 1. need[c] > 0
             *    说明字符 c 还缺。
             *
             * 2. need[c] == 0
             *    说明字符 c 当前刚刚好。
             *
             * 3. need[c] < 0
             *    说明字符 c 在当前窗口里是多余的。
             *
             * 例如 target = "ABC"：
             *
             * - 如果字符 X 根本不在 target 里，
             *   那它初始 need['X'] = 0
             *   右指针扫到它后 need['X']--，会变成 -1
             *   这表示：X 只是窗口里的多余字符
             *
             *   之后左指针移走 X：
             *   need['X']++，-1 会回到 0
             *   但它不会 > 0，所以 remain 不会增加
             *
             * - 如果字符 A 在 target 里，但当前窗口里 A 已经多了，
             *   比如 need['A'] = -1，
             *   那左边移走一个 A 后，need['A']++ 只会回到 0
             *   依然不会 > 0，所以 remain 也不会增加
             *
             * 只有一种情况才需要 remain++：
             * 移走的这个字符原本是“刚刚好够用”的。
             *
             * 例如移走前 need['A'] == 0，
             * 执行 need['A']++ 后变成 1，
             * 这才说明窗口现在真的缺了一个 A。
             *
             * 所以这里完全不需要再 for 一遍 target 去判断
             * leftChar 是不是目标字符。
             * 这个信息已经被 need 数组自己记住了。
             */
            need[leftChar]++;
            if (need[leftChar] > 0) {
                remain++;
            }
                left++;
            }
        }

        return bestLength == Integer.MAX_VALUE ? "" : source.substring(bestStart, bestStart + bestLength);
    }

    /**
     * 面向提交的精简性能版。
     *
     * 核心逻辑和 minWindow3 一样，都是“右边扫到就踢掉，左边收缩时再补回”，
     * 但这里尽量压缩热点循环里的状态和判断，方便直接贴到题解或力扣提交。
     */
    public String minWindowFast(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();
        if (sLength < tLength) {
            return "";
        }

        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        int[] need = new int[128];
        for (char c : tChars) {
            need[c]++;
        }

        int remain = tLength;
        int left = 0;
        int right = 0;
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;

        while (right < sLength) {
            char rightChar = sChars[right++];
            if (need[rightChar]-- > 0) {
                remain--;
            }

            while (remain == 0) {
                int windowLength = right - left;
                if (windowLength < bestLength) {
                    bestLength = windowLength;
                    bestStart = left;
                }

                char leftChar = sChars[left++];
                if (++need[leftChar] > 0) {
                    remain++;
                }
            }
        }

        return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
    }

    /**
     * 面向提交的差分数组版。
     *
     * arr[c - 'A'] 表示：
     * 当前窗口相对于 target，在字符 c 上还差多少。
     *
     * - arr[x] > 0：还缺这种字符
     * - arr[x] == 0：这种字符刚好够
     * - arr[x] < 0：这种字符多了
     *
     * diff 表示当前还有多少种字符没有达标。
     */
    public String minWindowDiffFast(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();
        if (sLength < tLength) {
            return "";
        }

        int[] diffArray = new int[58];
        for (int i = 0; i < tLength; i++) {
            diffArray[t.charAt(i) - 'A']++;
            diffArray[s.charAt(i) - 'A']--;
        }

        int diff = 0;
        for (int value : diffArray) {
            if (value > 0) {
                diff++;
            }
        }

        if (diff == 0) {
            return s.substring(0, tLength);
        }

        int left = 0;
        int bestLeft = 0;
        int bestRight = sLength;

        for (int right = tLength; right < sLength; right++) {
            int rightIndex = s.charAt(right) - 'A';
            if (--diffArray[rightIndex] == 0) {
                diff--;
            }

            while (diff == 0) {
                int leftIndex = s.charAt(left) - 'A';
                if (++diffArray[leftIndex] == 1) {
                    if (right - left < bestRight - bestLeft) {
                        bestLeft = left;
                        bestRight = right;
                    }
                    diff++;
                }
                left++;
            }
        }

        return bestRight == sLength ? "" : s.substring(bestLeft, bestRight + 1);
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

        int[] need = new int[128];
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }
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
     * t 里字符 c 需要多少个
     * 
     * 2. window[c]
     * 当前窗口里字符 c 有多少个
     * 
     * 3. needKinds
     * t 里一共有多少种“需要关心的字符”
     * 
     * 4. validKinds
     * 当前窗口里，已经“数量达标”的字符种类数
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

        int[] need = new int[128];
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }
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
        int[] need = new int[128];
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }
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
