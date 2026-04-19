import com.aquarius.wizard.leetcode.shl.NumberOfWaysToObtainTheLongestConsecutiveOnes;

/**
 * 这个 demo 不服务于刷题提交，只服务于学习。
 *
 * 目标只有一个：
 * 把“暴力 -> 滑动窗口 -> 前缀和”这条推导链固定下来。
 *
 * 如果我明天已经忘了聊天里说过什么，
 * 只要打开这个文件，再对照
 * NumberOfWaysToObtainTheLongestConsecutiveOnes
 * 去看，就能重新把思路捡回来。
 */
public class SlidingWindowAndPrefixSumLearningDemo {

    public static void main(String[] args) {
        String binaryString = "1010101";
        int changeK = 1;

        NumberOfWaysToObtainTheLongestConsecutiveOnes solver =
            new NumberOfWaysToObtainTheLongestConsecutiveOnes();

        /*
         * 第一层：最朴素的想法
         *
         * 我先不想优化，只想：
         * “怎么把所有子串都枚举出来？”
         *
         * 子串由 start 和 end 两个端点决定，
         * 所以最自然的暴力法通常就是两层 for。
         *
         * 这一步没毛病。
         */
        int bruteForceMaxLength = solver.findMaximumLengthBruteForce(binaryString, changeK);
        int bruteForceWays = solver.countWaysBruteForce(binaryString, changeK);

        /*
         * 第二层：从暴力法里找重复
         *
         * 这题的重复主要有两种：
         *
         * 1. 在求最长长度时，相邻窗口里的 0 被反复统计
         * 2. 在长度已经确定后，又反复问很多个固定长度窗口里有几个 0
         *
         * 于是优化会自然拆成两件事：
         *
         * - 用滑动窗口求最长长度
         * - 用前缀和求固定长度窗口计数
         */
        int optimizedWays = solver.countWays(binaryString, changeK);
        int slidingWindowMaxLength = solver.findMaximumLengthForLearning(binaryString, changeK);

        System.out.println("binaryString = " + binaryString);
        System.out.println("changeK = " + changeK);
        System.out.println("bruteForceMaxLength = " + bruteForceMaxLength);
        System.out.println("slidingWindowMaxLength = " + slidingWindowMaxLength);
        System.out.println("bruteForceWays = " + bruteForceWays);
        System.out.println("optimizedWays = " + optimizedWays);

        /*
         * 第三层：把识别信号记住
         *
         * 一、什么时候优先想滑动窗口
         * - 题目对象是连续段
         * - 约束是“最多 / 至少 / 不超过多少个坏东西”
         * - 窗口信息可以增量维护
         * - 不合法时，只能靠挪左边界修复
         *
         * 二、什么时候优先想前缀和
         * - 很多次问“某段里有几个什么”
         * - 如果每次都重新数，会很重复
         * - 可以先把前缀累计出来，再相减得到区间答案
         *
         * 这题就是两者组合：
         * - 最长长度：滑动窗口
         * - 固定长度计数：前缀和
         */
    }
}
