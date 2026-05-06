package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 题目
 * <p>
 * LeetCode 354
 * Russian Doll Envelopes
 * <p>
 * 原题链接：
 * https://leetcode.com/problems/russian-doll-envelopes/description/?envType=problem-list-v2&envId=sorting
 * <p>
 * 题意（按原题补全）：
 * 给你一个二维数组 envelopes，
 * 其中 envelopes[i] = [wi, hi] 表示第 i 个信封的宽度和高度。
 * 只有当一个信封的宽和高都严格小于另一个信封时，前者才能装进后者。
 * 返回最多能套多少层信封。
 * 注意：信封不能旋转。
 * <p>
 * 示例 1：
 * Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * Output: 3
 * 解释：最多可以套 3 层，[2,3] => [5,4] => [6,7]。
 * <p>
 * 示例 2：
 * Input: envelopes = [[1,1],[1,1],[1,1]]
 * Output: 1
 * <p>
 * 约束：
 * 1 <= envelopes.length <= 10^5
 * envelopes[i].length == 2
 * 1 <= wi, hi <= 10^5
 * <p>
 * 笔记
 * <p>
 * 这题如果从“人脑枚举套娃过程”的角度想，
 * 很容易走到下面这条路：
 * <p>
 * - 先挑一个信封当起点
 * - 再找后面哪些能套
 * - 维护当前已经套了几层
 * - 希望通过剪枝把无用分支砍掉
 * <p>
 * 这个方向的问题在于：
 * “当前已经套了几层”不是一个足够完整的中间状态。
 * <p>
 * 为什么？
 * 因为后面还能不能继续套，不只取决于你已经套了多少层，
 * 还取决于“你最后停在哪个信封上”。
 * <p>
 * 举个抽象一点的例子：
 * 两条链长度都等于 3，
 * 但第一条链最后一个信封是 [8, 8]，
 * 第二条链最后一个信封是 [5, 5]。
 * 显然后者更容易继续接上后面的信封。
 * <p>
 * 所以只维护“数量”，信息不够；
 * 如果再补链表、补去重、补剪枝，代码就会越来越乱。
 * 不是你不会写，而是状态设计本身不顺。
 * <p>
 * 这题真正稳定的做法有两种：
 * <p>
 * 1. O(n^2) DP
 *    - 先排序
 *    - 再做“以第 i 个信封结尾的最长链”
 *    - 这里的状态是 dp[i]，含义完整，好写也好想
 *    - 这不是前缀和，而是“以某个位置结尾的最优值”这一类 DP
 * <p>
 * 2. 排序 + 高度 LIS，时间复杂度 O(n log n)
 *    - 本质上是对 O(n^2) DP 再做状态压缩
 * <p>
     * 继续优化时，排序细节非常关键：
     * <p>
     * - 宽度升序
     * - 宽度相同的时候，高度降序
     * <p>
 * 注意这里“宽度升序、同宽时高度降序”是两个独立条件：
 * <p>
 * - 第一关键字：宽度，按升序排
 * - 第二关键字：如果宽度相同，再看高度，按降序排
 * <p>
 * 所以不是简单一句“整体升序”或者“整体降序”，
 * 而是一个二级排序规则。
 * <p>
 * 这个排序能保证的是：
 * - 宽度整体上不会从大跳回小，宽度一定是升序的大方向
 * - 但高度不保证整体升序，也不保证整体降序
 * - 只有在“宽度相同”的那一小段里，我们才强制高度降序
 * <p>
 * 所以它不是“两个维度都排成升序”，
 * 而是“先把宽度排好，再在同宽内部处理高度顺序”。
 * <p>
 * 为什么同宽要按高度降序？
 * <p>
 * 因为同宽的两个信封不能互相套。
 * 如果同宽时高度也升序，
 * 后面只看“高度递增”时，
 * 就可能把同宽的信封错误地串起来。
 * <p>
 * 所以必须让同宽时高度倒过来排，
 * 这样同一宽度的一段数据，不会被 LIS 误判成可套的递增链。
 * <p>
 * 这个文件里：
 * <p>
 * - maxEnvelopes2：保留为“适合学习和复盘”的 O(n^2) DP 版
 * - maxEnvelopesLis：保留为面试/刷题常用的 O(n log n) 版
 *
 * <p>create: 2026-04-19 10:02:00</p>
 *
 * @author zhaoyijie(AquariusGenius)
 */
public class RussianDollEnvelopes {

    public static void main(String[] args) {
        /*Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] envelopes = new int[n][2];
        for (int i = 0; i < n; i++) {
            envelopes[i][0] = scanner.nextInt();
            envelopes[i][1] = scanner.nextInt();
        }*/

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         */


        int[][] envelopes = {
                {5, 4},
                {6, 4},
                {6, 7},
                {2, 3}
        };

        RussianDollEnvelopes solver = new RussianDollEnvelopes();
        System.out.println(solver.maxEnvelopes(envelopes));

        /*
         * 如果想顺手核对 O(n^2) 版，可以临时打开下面这行：
         * System.out.println(solver.maxEnvelopesQuadratic(envelopes));
         */

        System.out.println(solver.maxEnvelopes2(envelopes));
    }

    public int maxEnvelopes(int[][] envelopes) {
        return maxEnvelopesLis(envelopes);
    }

    /**
     * 传统二维 DP 写法。
     *
     * 这个版本和 maxEnvelopes2 的核心逻辑一样，
     * 只是注释更少一些，适合作为精简版对照。
     *
     * 这不是前缀和。
     *
     * 前缀和通常长这样：
     * prefix[i] 表示前 0..i 这一段元素和
     * 它强调的是“连续区间的累计信息”。
     *
     * 这里的 dp[i] 完全不是这个意思。
     * dp[i] 表示：
     * “以第 i 个信封作为最后一个信封时，最多能套多少层。”
     *
     * 关键词是：
     * - 以 i 结尾
     * - 最优值
     *
     * 所以这题属于：
     * - 动态规划
     * - 更具体一点说，是“最长递增子序列（LIS）同类题”
     * - 再更具体一点说，是“先排序，再做以某位置结尾的最优转移”
     *
     * 这个方法最值得盯住的一行是：
     * dp[i] = Math.max(dp[i], dp[j] + 1);
     *
     * 含义不是“我随便从 j 转移一下试试”，
     * 而是：
     * “如果 j 能套进 i，
     * 那么以 j 结尾的最优链，后面可以再接上 i，
     * 于是得到一个候选答案 dp[j] + 1。”
     *
     * 因为能转移到 i 的 j 可能有很多个，
     * 我们当然要把这些候选答案里最大的那个留下来，
     * 所以要用 Math.max。
     */
    public int maxEnvelopesQuadratic(int[][] envelopes) {
        if (envelopes.length == 0) {
            return 0;
        }

        int[][] sorted = copyAndSort(envelopes);
        int n = sorted.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int answer = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (sorted[j][0] < sorted[i][0] && sorted[j][1] < sorted[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            answer = Math.max(answer, dp[i]);
        }

        return answer;
    }

    /**
     * 主解：排序后对高度做 LIS。
     *
     * 如果你已经理解了 O(n^2) 的 dp[i]：
     * dp[i] = 以第 i 个信封结尾，最多能套多少层
     * 那这个方法可以理解成：
     * “我不再为每个位置都保留一个状态，
     *  而是只保留每种长度下，最优的结尾高度。”
     *
     * tails[len] 的含义：
     * 长度为 len + 1 的递增链中，最小 possible 结尾高度。
     *
     * 为什么只保留“最小结尾”就够了？
     * 因为结尾越小，后面越容易接新的高度。
     * 这和前面类注释里说的一样：
     * 同样长度的链，结尾更小的那条更有扩展价值。
     */
    public int maxEnvelopesLis(int[][] envelopes) {
        if (envelopes.length == 0) {
            return 0;
        }

        int[][] sorted = copyAndSort(envelopes);
        int[] tails = new int[sorted.length];
        int size = 0;

        for (int[] envelope : sorted) {
            int height = envelope[1];
            int left = 0;
            int right = size;

            /*
             * 在 tails 里找“第一个 >= height 的位置”。
             *
             * - 如果找到了，就用更小/更合适的结尾去替换它
             * - 如果没找到，说明 height 比当前所有结尾都大，可以把长度再加 1
             */
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tails[mid] < height) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            tails[left] = height;
            if (left == size) {
                size++;
            }
        }

        return size;
    }

    private int[][] copyAndSort(int[][] envelopes) {
        int[][] copy = new int[envelopes.length][2];
        for (int i = 0; i < envelopes.length; i++) {
            copy[i][0] = envelopes[i][0];
            copy[i][1] = envelopes[i][1];
        }

        Arrays.sort(copy, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
                /*
                 * compare(a, b) 的返回值规则：
                 * - 小于 0：a 排前面
                 * - 等于 0：谁前谁后都行
                 * - 大于 0：b 排前面
                 *
                 * 所以：
                 * Integer.compare(first[0], second[0])
                 * 表示按宽度升序。
                 *
                 * 例如：
                 * first[0] = 2, second[0] = 5
                 * compare(2, 5) < 0
                 * 所以 first 在前，也就是升序。
                 */
                if (first[0] != second[0]) {
                    return Integer.compare(first[0], second[0]);
                }

                /*
                 * 这里故意反过来写：
                 * Integer.compare(second[1], first[1])
                 *
                 * 这表示“高度降序”。
                 *
                 * 例如：
                 * first[1] = 4, second[1] = 7
                 * compare(7, 4) > 0
                 * 返回正数，说明 second 要排到前面，
                 * 于是结果变成 7 在前，4 在后，也就是降序。
                 *
                 * 为什么同宽时必须按高度降序？
                 * 因为同宽的信封不能互相嵌套。
                 *
                 * 假设同宽时我们反而按高度升序：
                 * [5, 4], [5, 5]
                 * 后面只对高度做 LIS 时，
                 * 就会错误地把 4 -> 5 当成一个递增链，
                 * 但实际上它们宽度相同，根本不能套。
                 *
                 * 改成降序后：
                 * [5, 5], [5, 4]
                 * 高度不再递增，就不会被 LIS 错误选中。
                 */
                return Integer.compare(second[1], first[1]);
            }
        });
        return copy;
    }

    /**
     * 这个方法专门保留成“从你原本思路过渡过来”的学习版。
     *
     * 你原本的雏形大概是在想：
     * 1. 选一个信封
     * 2. 看后面有哪些能接
     * 3. 只维护当前层数
     * 4. 再想办法剪枝/去重
     *
     * 真正卡住的地方不在语法，也不在链表这种容器选型，
     * 而在“中间状态应该存什么”。
     *
     * 你这次问“这是不是前缀和”，这个疑问很典型。
     * 因为它们表面上都有一个一维数组 dp，
     * 而且也都是从前往后算。
     *
     * 但要区分它们，关键不是看“长得像不像”，
     * 而是看 dp[i] 的定义。
     *
     * - 如果 dp[i] 表示“前 i 个元素累计起来的某种信息”，
     *   这更像前缀型思路
     * - 如果 dp[i] 表示“以第 i 个位置结尾/达到第 i 个位置时的最优值”，
     *   这就是典型动态规划
     *
     * 这里显然属于后者。
     *
     * 这题最稳的中间状态不是：
     * - 当前用了哪些信封
     * - 当前链表长什么样
     * - 当前层数是多少
     *
     * 而是：
     * dp[i] = 以 sorted[i] 这个信封作为最外层(最后一个)时，
     *         最多可以套多少层
     *
     * 这句话一定要逐字看懂：
     *
     * - “以 sorted[i] 作为最后一个”
     *   表示当前这条链必须停在 i
     *
     * - “最多可以套多少层”
     *   表示在所有停在 i 的方案里，我只保留最优数量
     *
     * 所以 dp 数组里每个格子存的不是“到这里为止一共看过多少个”，
     * 也不是“前面那一段的总和”，
     * 而是“停在这个点时，最好的答案是多少”。
     *
     * 这个状态一旦确定：
     * - 不需要链表
     * - 不需要显式去重
     * - 不需要回溯所有分支
     *
     * 因为“以第 i 个信封结尾的最优答案”只会有一个数。
     * 前面所有能接到 i 的情况，都统一拿 max 即可。
     *
     * 这就是动态规划最常见的一个转变：
     * 从“枚举路径”转成“定义每个位置的最优子问题”。
     *
     * 你这次追问的另一个卡点也正是在这里：
     * “如果后面的宽更大，但高更小，会不会排序以后漏掉？”
     *
     * 不会漏，但这种情况本来就不能转移。
     *
     * 比如：
     * [2, 8] 和 [3, 5]
     *
     * 排序后它们会按宽度放成：
     * [2, 8], [3, 5]
     *
     * 虽然宽度满足 2 < 3，
     * 但高度不满足 8 < 5，
     * 所以 canNest([2, 8], [3, 5]) == false
     * 这条转移自然不会发生。
     *
     * 也就是说：
     * 排序的作用只是把“可能的前驱”整理到前面，
     * 不是说“排完序以后前面的就一定能接到后面的”。
     *
     * 真正决定能不能接，仍然要靠：
     * inner[0] < outer[0] && inner[1] < outer[1]
     *
     * 所以你可以把排序理解成：
     * “先把宽这个维度处理掉一半，让我们只需要从左往右考虑。”
     *
     * 但高这个维度并没有被排序直接解决掉，
     * 它仍然需要在转移时逐个判断。
     */
    public int maxEnvelopes2(int[][] envelopes) {
        if (envelopes.length == 0) {
            return 0;
        }

        /*
         * 第一步先排序。
         *
         * 为什么必须排序？
         * 因为如果不排序，
         * 你在做“前面的结果能不能转移到后面”时，
         * 前后顺序没有任何业务含义，状态会非常混乱。
         *
         * 排完序之后，至少可以保证：
         * 我们只需要考虑 j < i 的信封能不能接到 i 前面。
         *
         * 注意这里只能保证“宽度的大方向有序”：
         * - sorted[j][0] <= sorted[i][0] 对很多后续判断是有帮助的
         * - 但并不能保证高度也有序
         * - 更不能保证 j 一定能接到 i
         */
        int[][] sorted = copyAndSort(envelopes);

        /*
         * dp[i] 初始为 1：
         * 至少我自己单独就是 1 层。
         */
        int[] dp = new int[sorted.length];
        Arrays.fill(dp, 1);

        /*
         * answer 记录全局最优解。
         */
        int answer = 1;

        /*
         * 外层枚举“当前要作为结尾的信封 i”。
         */
        for (int i = 0; i < sorted.length; i++) {
            /*
             * 内层枚举所有可能接在 i 前面的信封 j。
             *
             * 如果 sorted[j] 能放进 sorted[i]，
             * 那么“以 i 结尾”的答案，
             * 至少可以由“以 j 结尾”的答案再加 1 得到。
             *
             * 这里一定要理解：
             * j < i 只代表“j 在排序后排在前面”，
             * 不代表 j 一定能套进 i。
             *
             * 所以每次都还要再做 canNest 判断。
             *
             * 这正是你担心的那种情况：
             * 前面宽更小，但高度不更小。
             * 这种情况排序不会帮你自动过滤，
             * 只能由 canNest 来过滤。
             */
            for (int j = 0; j < i; j++) {
                if (canNest(sorted[j], sorted[i])) {
                    /*
                     * 如果有多个 j 都能转移到 i：
                     *
                     * - 可以从 j1 转来，得到 dp[j1] + 1
                     * - 也可以从 j2 转来，得到 dp[j2] + 1
                     * - ...
                     *
                     * 那我们当然只保留其中最大的那个。
                     *
                     * 所以这里不是“会不会漏”，
                     * 而是“我把所有可能的前驱都试一遍，再取最大值”。
                     */
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            /*
             * 每算完一个 i，就顺手更新全局答案。
             */
            answer = Math.max(answer, dp[i]);
        }

        return answer;
    }

    /**
     * 判断 inner 能否放进 outer。
     *
     * 这里必须是严格小于，不是小于等于。
     * 题目要求宽和高都要严格更小。
     */
    private boolean canNest(int[] inner, int[] outer) {
        return inner[0] < outer[0] && inner[1] < outer[1];
    }

}
