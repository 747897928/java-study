package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 96
 * Unique Binary Search Trees
 *
 * 原题链接：
 * https://leetcode.com/problems/unique-binary-search-trees/description/?envType=problem-list-v2&envId=dynamic-programming
 *
 * 原题：
 * Given an integer n, return the number of structurally unique BST's (binary search trees) which has exactly n nodes of unique values from 1 to n.
 *
 * Example 1:
 * <pre>
 *     1         1           2           3         3
 *      \         \         / \         /         /
 *
 *       3         2       1   3       2         1
 *      /           \                 /           \
 *     2             3               1             2
 *
 * Input: n = 3
 * Output: 5
 * </pre>
 *
 * Example 2:
 * Input: n = 1
 * Output: 1
 *
 * Constraints:
 * 1 <= n <= 19
 *
 * 笔记
 *
 * 这题是很典型的“先枚举根，再把左右子树方案数乘起来”的 DP。
 *
 * 如果把某个数字 root 选成根：
 *
 * - 左子树只能用比 root 小的那些数
 * - 右子树只能用比 root 大的那些数
 *
 * 并且左右子树的结构选择彼此独立，
 * 所以：
 *
 * 以 root 为根的方案数 = 左子树方案数 * 右子树方案数
 *
 * 所以这题的核心不是“BST 怎么建”，
 * 而是“按根拆分区间，左右独立相乘”。
 *
 * 这题常见两种写法：
 *
 * 1. DP
 *    dp[i] = 用 i 个节点能组成多少种 BST
 *
 * 2. Catalan 数公式
 *    这题答案就是第 n 个 Catalan 数
 *
 * 正式主解我这里用 DP，因为更贴近“我是怎么推出来的”。
 *
 * 方法一：动态规划
 *
 * 设：
 * - G(n)：长度为 n 的有序序列能组成多少种不同 BST
 * - F(i, n)：以 i 为根、序列长度为 n 时，不同 BST 的个数
 *
 * 总方案数就是把每个位置都当一次根后再求和：
 * Unicode: G(n) = ∑(i=1..n) F(i, n)
 * ASCII:   G(n) = sum(i=1..n, F(i, n))
 *
 * 当 i 作为根时：
 * - 左子树只能使用 [1 .. i-1]，一共有 G(i - 1) 种
 * - 右子树只能使用 [i+1 .. n]，一共有 G(n - i) 种
 *
 * 左右子树彼此独立，所以：
 * Unicode: F(i, n) = G(i - 1) × G(n - i)
 * ASCII:   F(i, n) = G(i - 1) * G(n - i)
 *
 * 代回去得到 DP 递推式：
 * Unicode: G(n) = ∑(i=1..n) G(i - 1) × G(n - i)
 * ASCII:   G(n) = sum(i=1..n, G(i - 1) * G(n - i))
 *
 * 边界条件：
 * Unicode: G(0) = 1, G(1) = 1
 * ASCII:   G(0) = 1, G(1) = 1
 *
 * 方法二：数学
 *
 * 上面的 G(n) 在数学上就是第 n 个 Catalan 数。
 *
 * Unicode: C₀ = 1, Cₙ₊₁ = 2(2n + 1)Cₙ / (n + 2)
 * ASCII:   C0 = 1, C(n+1) = 2 * (2n + 1) * Cn / (n + 2)
 *
 * 对应代码里的迭代式：
 * Unicode: C ← C × 2 × (2i + 1) / (i + 2)
 * ASCII:   C = C * 2 * (2 * i + 1) / (i + 2)
 *
 * 复杂度分析：
 * - DP
 *   时间复杂度：O(n²)
 *   空间复杂度：O(n)
 * - Catalan 公式
 *   时间复杂度：O(n)
 *   空间复杂度：O(1)
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class UniqueBinarySearchTrees {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int n = 3;
         */

        UniqueBinarySearchTrees solver = new UniqueBinarySearchTrees();
        System.out.println(solver.numTrees(n));

        /*
         * 如果想顺手对照 Catalan 公式版，可以临时打开下面这行：
         * System.out.println(solver.numTreesCatalan(n));
         */
    }

    public int numTrees(int n) {
        return numTreesDp(n);
    }

    /**
     * DP 写法。
     *
     * dp[i] 表示：恰好用 i 个节点时，一共有多少种不同 BST。
     *
     * 枚举根节点时，左子树用了 leftCount 个节点，
     * 那右子树就自动用了 i - 1 - leftCount 个节点。
     *
     * 核心递推：
     * Unicode: G(n) = ∑(i=1..n) G(i - 1) × G(n - i)
     * ASCII:   G(n) = sum(i=1..n, G(i - 1) * G(n - i))
     *
     * 映射到当前循环变量后：
     * Unicode: dp[nodes] += dp[leftCount] × dp[rightCount]
     * ASCII:   dp[nodes] += dp[leftCount] * dp[rightCount]
     *
     * 如果你是第一次学这种题，可以先抓住 3 个关键点：
     *
     * 1. dp 不是在存“节点值”，而是在存“节点个数对应的方案数”
     *    - dp[0]：0 个节点时有多少种 BST
     *    - dp[1]：1 个节点时有多少种 BST
     *    - dp[2]：2 个节点时有多少种 BST
     *    - ...
     *
     * 2. 这题不是在真的“建树”，而是在“数方案”
     *    每次固定一个根节点后，左边能形成多少种、右边能形成多少种，
     *    两边相乘，就是“这个根”能贡献的总方案数。
     *
     * 3. 外层循环算“总节点数”，内层循环算“左子树分到几个节点”
     *    一旦左子树节点数确定，右子树节点数就自动确定。
     *
     * 你看到的题解写法：
     *
     *   for (int i = 2; i <= n; ++i) {
     *       for (int j = 1; j <= i; ++j) {
     *           G[i] += G[j - 1] * G[i - j];
     *       }
     *   }
     *
     * 和我这里的写法本质完全一样，只是“循环变量的定义方式”不同：
     *
     * - 别人的 j：表示“第几个数被选为根”，所以 j 从 1 开始
     * - 我的 leftCount：表示“左子树拿了几个节点”，所以从 0 开始
     *
     * 两者对应关系：
     * - root = j
     * - leftCount = j - 1
     * - rightCount = i - j
     *
     * 所以：
     * - 别人的 G[j - 1]  == 我的 dp[leftCount]
     * - 别人的 G[i - j]  == 我的 dp[rightCount]
     *
     * 只是“看问题的角度不同”，不是算法不同。
     */
    public int numTreesDp(int n) {
        // 为什么是 n + 1，不是 n？
        //
        // 因为我们要使用下标 0 到 n。
        //
        // dp[k] 的含义是：“恰好 k 个节点时，有多少种 BST”。
        // 题目最后要求的是 n 个节点的答案，所以必须能访问 dp[n]。
        //
        // Java 数组如果写成 new int[n]，合法下标只有 0..n-1，
        // 那就放不下 dp[n]，会越界。
        //
        // 所以这里必须开到 n + 1。
        int[] dp = new int[n + 1];

        // dp[0] = 1 表示“空树也算 1 种情况”。
        //
        // 这点非常关键，很多初学者会觉得 0 个节点应该是 0 种。
        // 但在这题里，dp 存的是“组合时的方案数”。
        //
        // 比如某个根节点左边没有节点，那左子树并不是“没法组成”，
        // 而是“有且仅有 1 种组成方式：空树”。
        //
        // 如果这里写成 0，后面做乘法时整项都会变成 0，递推就错了。
        dp[0] = 1;

        // dp[1] = 1 表示只有 1 个节点时，只能组成 1 种 BST。
        //
        // 题目节点值虽然可能写成 1，或者别的某个值，
        // 但对“结构”来说，单节点树永远只有 1 种。
        if (n >= 1) {
            dp[1] = 1;
        }

        // 为什么外层从 2 开始？
        //
        // 因为 0 个节点和 1 个节点的答案我们已经提前知道了：
        // - dp[0] = 1
        // - dp[1] = 1
        //
        // 所以真正需要“通过递推去计算”的，最小就是 2 个节点。
        //
        // nodes 表示：当前正在计算“nodes 个节点时”的答案。
        for (int nodes = 2; nodes <= n; nodes++) {
            // leftCount 表示：左子树分到几个节点。
            //
            // 为什么从 0 开始？
            // 因为左子树允许为空。
            //
            // 比如 nodes = 3 时：
            // - leftCount = 0，表示根节点左边一个节点都不放
            // - leftCount = 1，表示左边放 1 个节点
            // - leftCount = 2，表示左边放 2 个节点
            //
            // 注意：一共只有 nodes - 1 个节点可以分给左右子树，
            // 因为还要留 1 个节点给“根节点自己”。
            //
            // 所以 leftCount 的最大值不是 nodes，而是 nodes - 1。
            for (int leftCount = 0; leftCount < nodes; leftCount++) {
                // 根节点已经占掉 1 个节点，
                // 左子树又拿走了 leftCount 个节点，
                // 那右子树剩下的节点数只能是：
                //
                // rightCount = nodes - 1 - leftCount
                int rightCount = nodes - 1 - leftCount;

                // 这行是整道题最核心的一行。
                //
                // 含义：
                // 当前这种“左边 leftCount 个节点，右边 rightCount 个节点”的分法，
                // 能贡献多少种 BST？
                //
                // 答案是：
                // 左子树方案数 * 右子树方案数
                //
                // 为什么可以乘？
                // 因为左子树怎么长、右子树怎么长，彼此独立。
                //
                // 举例：
                // 如果左边有 2 种结构，右边有 3 种结构，
                // 那左右组合起来就有 2 * 3 = 6 种。
                //
                // 这正是乘法原理。
                dp[nodes] += dp[leftCount] * dp[rightCount];
            }
        }

        // 循环结束后，dp[n] 就是“恰好 n 个节点时”的总方案数。
        return dp[n];
    }

    /**
     * Catalan 公式写法。
     * 事实上我们在方法一中推导出的 G(n)函数的值在数学上被称为卡塔兰数
     *
     * Catalan 数递推：
     * Unicode: C₀ = 1, Cₙ₊₁ = 2(2n + 1)Cₙ / (n + 2)
     * ASCII:   C0 = 1, C(n+1) = 2 * (2n + 1) * Cn / (n + 2)
     *
     * 对应当前代码的逐步更新：
     * Unicode: C ← C × 2 × (2i + 1) / (i + 2)
     * ASCII:   C = C * 2 * (2 * i + 1) / (i + 2)
     *
     * 如果你把 DP 推导看明白了，这里可以这样理解：
     *
     * 1. DP 是“从定义出发，一步一步推出来”的写法
     * 2. Catalan 是“数学家已经把这个递推整理成了更短的公式”
     *
     * 也就是说：
     * - numTreesDp() 是按“左右子树拆分”去算
     * - numTreesCatalan() 是直接利用这个问题对应的数学结论去算
     *
     * 两个方法算的是同一个东西，只是路径不同：
     * - 一个更好理解
     * - 一个更省空间、也更快
     *
     * 这里用 long 只是为了中间乘法更稳，
     * 最终题目范围内答案仍然能装回 int。
     */
    public int numTreesCatalan(int n) {
        // catalan 表示“当前已经算到的 Catalan 数”。
        //
        // 一开始先放 C0 = 1。
        // 这就像 DP 里的 dp[0] = 1 一样，是整个递推的起点。
        long catalan = 1L;

        // 这个循环本质上是在做：
        // C0 -> C1 -> C2 -> ... -> Cn
        //
        // 为什么 i 从 0 开始？
        // 因为我们已知的是 C0，接下来第一步要用 C0 推出 C1。
        //
        // 当 i = 0 时，下面那条式子算出的正好是 C1。
        // 当 i = 1 时，算出的是 C2。
        // ...
        // 当 i = n - 1 时，算出的是 Cn。
        for (int i = 0; i < n; i++) {
            // 这行对应的就是卡塔兰数递推式：
            //
            // C(i+1) = C(i) * 2 * (2i + 1) / (i + 2)
            //
            // 你可以把当前变量名对应起来看：
            // - 当前的 catalan：就是 C(i)
            // - 更新后的 catalan：就是 C(i+1)
            //
            // 也就是说，每循环一次，都把“上一个 Catalan 数”
            // 推到“下一个 Catalan 数”。
            //
            // 这里看起来像会出现除不尽的小数，
            // 但对 Catalan 数这个公式来说，每一步结果都会刚好是整数。
            //
            // 例如：
            // i = 0:
            // C1 = C0 * 2 * (2*0 + 1) / (0 + 2)
            //    = 1 * 2 * 1 / 2
            //    = 1
            //
            // i = 1:
            // C2 = C1 * 2 * (2*1 + 1) / (1 + 2)
            //    = 1 * 2 * 3 / 3
            //    = 2
            //
            // i = 2:
            // C3 = C2 * 2 * (2*2 + 1) / (2 + 2)
            //    = 2 * 2 * 5 / 4
            //    = 5
            //
            // 这就得到：
            // C0 = 1, C1 = 1, C2 = 2, C3 = 5 ...
            catalan = catalan * 2 * (2L * i + 1) / (i + 2);
        }

        // 循环结束后，catalan 已经从 C0 一路更新到了 Cn。
        //
        // 题目要求返回 n 个节点时的 BST 个数，
        // 而这个值正好就是第 n 个 Catalan 数，所以直接返回即可。
        return (int) catalan;
    }
}
