package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 42
 * Trapping Rain Water
 *
 * 给定一个非负整数数组 height。
 * 数组里的每个数都表示一堵墙的高度，每堵墙的宽度都按 1 来看。
 * 下雨后，问这些墙之间一共能接住多少单位的雨水。
 *
 * 示例：
 *
 * height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * answer = 6
 *
 * 笔记
 *
 * 这题很适合拿来练“同一道题可以从多个角度切进去”。
 *
 * 如果第一次见到这题，最自然的起点通常不是双指针，而是：
 *
 * 1. 先按行看，数第 1 层有多少水、第 2 层有多少水……
 * 2. 或者按列看，枚举每一列，问它上面能装多少水
 *
 * 这两个起点都没问题。
 * 真正要训练的能力不是“一上来就想到最优解”，
 * 而是：
 *
 * - 先把最笨但正确的思路落下来
 * - 再观察哪里有重复计算
 * - 再决定怎么优化
 *
 * 这题一共有 5 种很经典的写法：
 *
 * 1. 按行求
 *    以“第 i 层是不是能积水”为单位去数
 *    时间复杂度 O(maxHeight * n)
 *    好理解，但高度特别大时容易慢
 *
 * 2. 按列暴力
 *    对每一列，都重新找左边最高墙和右边最高墙
 *    时间复杂度 O(n^2)
 *
 * 3. 前后缀最大值
 *    把“每列左边最高、右边最高”先预处理出来
 *    时间复杂度 O(n)，空间复杂度 O(n)
 *
 * 4. 双指针
 *    在前后缀最大值的基础上进一步省空间
 *    时间复杂度 O(n)，空间复杂度 O(1)
 *
 * 5. 单调栈
 *    把“低洼处”和“两边边界”用栈结构维护起来
 *    时间复杂度 O(n)，空间复杂度 O(n)
 *
 * 很多人第一眼会把这题往“前缀和”上靠，但这里真正需要的不是某段区间的和，
 * 而是“当前位置左边最高墙”和“当前位置右边最高墙”。
 *
 * 所以这题和前缀和不是一类题。
 * 更准确地说，它和：
 *
 * - 前缀最大值 / 后缀最大值
 * - 双指针
 * - 单调栈
 *
 * 这些工具更相关。
 *
 * 以后如果你看到这种味道的题：
 *
 * - 每个位置的答案都依赖“左边最强边界”和“右边最强边界”
 * - 当前位置真正能取的值，取决于两边较小的那个边界
 *
 * 那就优先怀疑：
 * “是不是要维护左右最大值，而不是做前缀和。”
 *
 * <p>create: 2026-04-19 09:29:32</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class TrappingRainWater {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] height = new int[n];
        for (int i = 0; i < n; i++) {
            height[i] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
         */

        TrappingRainWater solver = new TrappingRainWater();
        System.out.println(solver.trap(height));

        /*
         * 如果想对照别的写法，可以临时打开下面这些：
         *
         * System.out.println(solver.trapByRows(height));
         * System.out.println(solver.trapByColumns(height));
         * System.out.println(solver.trapByPrefixSuffixMax(height));
         * System.out.println(solver.trapByMonotonicStack(height));
         */
    }

    /**
     * 正式主解：双指针。
     *
     * 先把按列的核心公式写出来：
     *
     * 对第 i 列来说，
     * 能装多少水，只取决于：
     *
     * 1. 它左边最高的墙
     * 2. 它右边最高的墙
     *
     * 设：
     * leftMax = 第 i 列左边最高墙
     * rightMax = 第 i 列右边最高墙
     *
     * 那么第 i 列上方的水量就是：
     *
     * min(leftMax, rightMax) - height[i]
     *
     * 如果这个值是负数，就按 0 处理。
     *
     * 为什么是 min(leftMax, rightMax)？
     *
     * 因为木桶能装多少水，取决于更矮的那块挡板。
     * 左边再高，如果右边很低，水还是会从右边溢出去。
     *
     * 双指针的关键想法是：
     *
     * 我们不用真的把每个位置的 leftMax / rightMax 都存在数组里，
     * 而是让 left 从左往右走，right 从右往左走，
     * 同时维护：
     *
     * - leftMax：当前 left 这边看过的最高墙
     * - rightMax：当前 right 这边看过的最高墙
     *
     * 然后每次优先结算“较矮边界”这一侧。
     *
     * 为什么较矮边界这一侧可以先结算？
     *
     * 如果 leftMax <= rightMax，
     * 那么 left 当前位置最终能接多少水，
     * 已经只由 leftMax 决定了。
     *
     * 因为右边至少有 rightMax 这么高，
     * 而 rightMax 又不小于 leftMax，
     * 所以真正卡住 left 这一列的，一定是 leftMax。
     *
     * 对称地：
     * 如果 rightMax < leftMax，
     * 那就优先结算右边这一列。
     *
     * 这就是这题双指针的核心，不是“随便两头夹”，
     * 而是“每次处理当前较矮边界一侧”。
     */
    public int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;

        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);

            if (leftMax <= rightMax) {
                // 当前左侧的最高边界更矮，所以 left 这一列的上限已经确定了。
                water += leftMax - height[left];
                left++;
            } else {
                // 当前右侧的最高边界更矮，所以 right 这一列的上限已经确定了。
                water += rightMax - height[right];
                right--;
            }
        }

        return water;
    }

    /**
     * 解法一：按行求。
     *
     * 这是最容易从图上直接想到的写法。
     *
     * 不是按“每一列上面有多少水”来算，
     * 而是按“第 1 层一共有多少水，第 2 层一共有多少水……”来算。
     *
     * 第 level 层怎么判断某个位置有没有水？
     *
     * 只要这个位置本身的高度小于 level，
     * 并且它左边出现过高度 >= level 的墙，
     * 后面又再次遇到了高度 >= level 的墙，
     * 那么这中间累计的格子就都能装这一层的水。
     *
     * 这版的优点是图像感很强，
     * 缺点是如果最高墙特别高，就要一层一层扫很多遍。
     */
    public int trapByRows(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int totalWater = 0;
        int maxHeight = getMaxHeight(height);

        for (int level = 1; level <= maxHeight; level++) {
            boolean started = false;
            int pendingWater = 0;

            for (int currentHeight : height) {
                if (started && currentHeight < level) {
                    pendingWater++;
                }

                if (currentHeight >= level) {
                    totalWater += pendingWater;
                    pendingWater = 0;
                    started = true;
                }
            }
        }

        return totalWater;
    }

    /**
     * 解法二：按列暴力。
     *
     * 如果把关注点放回“每一列上方有多少水”，
     * 那么第 i 列只需要看两件事：
     *
     * - 左边最高墙
     * - 右边最高墙
     *
     * 然后取较矮边界减去当前列高度。
     *
     * 这版的缺点很明显：
     * 每到一列，都要重新往左扫、往右扫。
     *
     * 所以虽然思路已经很接近最优解，
     * 但还有很多重复计算。
     */
    public int trapByColumns(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int water = 0;
        for (int i = 1; i < height.length - 1; i++) {
            int maxLeft = 0;
            for (int j = i - 1; j >= 0; j--) {
                maxLeft = Math.max(maxLeft, height[j]);
            }

            int maxRight = 0;
            for (int j = i + 1; j < height.length; j++) {
                maxRight = Math.max(maxRight, height[j]);
            }

            int boundary = Math.min(maxLeft, maxRight);
            if (boundary > height[i]) {
                water += boundary - height[i];
            }
        }

        return water;
    }

    /**
     * 解法三：前后缀最大值。
     *
     * 这是从“按列暴力”自然优化出来的一步。
     *
     * 按列暴力的问题是：
     * 对每个位置 i，我们都在重复找：
     *
     * - i 左边最高是谁
     * - i 右边最高是谁
     *
     * 既然这个量会被反复问，那就提前存起来。
     *
     * 这里特意按“左边 / 右边都不包含自己”来定义：
     *
     * maxLeft[i]  = 第 i 列左边最高的墙
     * maxRight[i] = 第 i 列右边最高的墙
     *
     * 这样更贴近“当前列两边的挡板”这个直觉。
     *
     * 转移也很好理解：
     *
     * maxLeft[i] = max(maxLeft[i - 1], height[i - 1])
     * 因为第 i 列左边最高墙，
     * 要么来自更左边的 maxLeft[i - 1]，
     * 要么就是紧挨着它左边这堵 height[i - 1]
     *
     * maxRight 同理。
     *
     * 很多人第一眼会说“这是不是前缀和”。
     * 不是。
     *
     * 这里用的是：
     * - 前缀最大值
     * - 后缀最大值
     *
     * 因为我们关心的是边界最高，不是区间总和。
     */
    public int trapByPrefixSuffixMax(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int n = height.length;
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];

        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i - 1]);
        }

        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i + 1]);
        }

        int water = 0;
        for (int i = 1; i < n - 1; i++) {
            int boundary = Math.min(maxLeft[i], maxRight[i]);
            if (boundary > height[i]) {
                water += boundary - height[i];
            }
        }

        return water;
    }

    /**
     * 解法五：单调栈。
     *
     * 这版更像是在“找一个低洼坑什么时候被右边界补齐”。
     *
     * 栈里存的是下标，并且保持对应高度单调不升。
     *
     * 当当前高度比栈顶更高时，说明：
     *
     * - 之前那个低位置的右边界终于出现了
     * - 可以把栈顶当成“坑底”弹出来结算
     *
     * 结算时有三个关键位置：
     *
     * 1. current          当前新来的右边界
     * 2. stack.peek()     弹栈后新的栈顶，作为左边界
     * 3. bottom           刚弹出的那个坑底
     *
     * 水量就是：
     *
     * 宽度 * 有效高度
     *
     * 其中：
     * 宽度 = current - leftBoundary - 1
     * 高度 = min(leftBoundaryHeight, currentHeight) - bottomHeight
     *
     * 这版如果第一次看不顺，完全正常。
     * 建议先理解按列和双指针，再回来看单调栈。
     */
    public int trapByMonotonicStack(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int water = 0;
        Deque<Integer> stack = new ArrayDeque<Integer>();

        for (int current = 0; current < height.length; current++) {
            while (!stack.isEmpty() && height[current] > height[stack.peek()]) {
                int bottomIndex = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }

                int leftBoundaryIndex = stack.peek();
                int width = current - leftBoundaryIndex - 1;
                int boundedHeight =
                    Math.min(height[leftBoundaryIndex], height[current]) - height[bottomIndex];
                water += width * boundedHeight;
            }
            stack.push(current);
        }

        return water;
    }

    private int getMaxHeight(int[] height) {
        int max = 0;
        for (int currentHeight : height) {
            max = Math.max(max, currentHeight);
        }
        return max;
    }
}
