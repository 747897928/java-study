package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Arrays;

/**
 * Question
 *
 * John misses his bus and has to walk all his way from home to school. The distance between his
 * school and home is D units. He starts his journey with an initial energy of K units. His energy
 * decreases by 1 unit for every unit of distance walked. On his way to school, there are N juice
 * stalls. Each stall has a specific amount of juice in liters. His energy increases by 1 unit for
 * every liter of juice he consumes. Note that in order to keep him walking he should have non-zero
 * energy.
 *
 * Write an algorithm to help John figure out the minimum number of juice stalls at which he should
 * stop to successfully reach the school. In case he can't reach the school, the output will be -1.
 *
 * Input
 *
 * The first line of the input consists of an integer N, representing the number of juice stalls.
 * The second line consists of N space-separated integers - dist1, dist2, ..., distn representing
 * the distance of the i-th stall from John's home.
 * The third line consists of N space-separated integers - lit1, lit2, ..., litn representing the
 * liters of juice available at the i-th stall.
 * The last line consists of two space-separated integers - D and K representing the distance of the
 * school from John's home and his initial energy, respectively.
 *
 * Output
 *
 * Print an integer representing the minimum number of juice stalls at which John should stop to
 * reach the school successfully.
 *
 * Example
 *
 * Input:
 * 4
 * 5 7 8 10
 * 2 3 1 5
 * 15 5
 *
 * Output:
 * 3
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：最大堆贪心。
 * 校对：样例已做代码校验。
 * 做法：先尽量往前走，走不动时，从已经路过的摊位里选补给量最大的一个。
 * 相似题：LeetCode 871 Minimum Number of Refueling Stops。
 *
 * 这题真正难的地方不是堆，而是“贪心时机”。
 *
 * 很多人第一次会想：
 *
 * “我应该提前决定在哪几个摊位停下。”
 *
 * 这个想法很容易把自己绕进状态设计。
 *
 * 更好的想法是：
 *
 * “先一路往前走，等到真的走不动了，再回头问：
 *  我之前路过的摊位里，补哪一个最划算？”
 *
 * 答案显然是：
 *
 * 补给量最大的那个。
 *
 * 所以这题的核心反应应该是：
 *
 * 1. 已经过了的补给站，先放进最大堆
 * 2. 真正缺油/缺能量的时候，再从堆里拿最大的补
 *
 * 这类题的本质是“延迟决策”：
 *
 * 不是先决定停哪里，
 * 而是把所有可选补给先缓存起来，等真的不够时再做最优选择。
 *
 * 为什么我一眼会把它归到“堆贪心”：
 *
 * 因为这题同时出现了两个很强的信号：
 *
 * 1. 每停一次都很贵，目标是“停的次数最少”
 *    这说明每次一旦决定停，就应该尽量让这次停带来最大的收益。
 *
 * 2. 可选补给不是固定一个，而是“之前已经路过的所有摊位”
 *    这说明我们需要一个数据结构，能把“历史候选”暂时存起来，
 *    并且在需要时立刻拿到其中最大的那一个。
 *
 * 这两个信号合起来，就是典型的：
 *
 * - 贪心：每次做当前最划算的选择
 * - 堆：在一堆动态候选里，快速拿最优值
 *
 * 如果把这句话说得再接地气一点：
 *
 * - “贪心”负责回答：现在既然必须停一次，那停哪一个更值？
 * - “堆”负责回答：我怎么从一堆候选补给里，立刻拿到最大的那个？
 *
 * 更直白一点：
 *
 * 如果已经决定“现在必须停一次”，
 * 那么停在补 5 升的摊位，永远不比停在补 3 升的摊位差，
 * 因为两者付出的代价都是“1 次停靠”，但前者拿到的能量更多。
 *
 * 所以在“必须停一次”这个前提下，选历史最大补给量就是正确贪心。
 *
 * PriorityQueue 是什么：
 *
 * Java 里的 PriorityQueue 可以把它先简单理解成“带优先级的队列”。
 * 如果你以前没用过它，也可以先把它理解成：
 *
 * “Java 标准库已经帮你写好的堆”
 *
 * 所以平时做题时，很多题其实不是“我要不要会手写堆”的问题，
 * 而是“我能不能认出来这里需要一个堆结构”。
 *
 * 它和普通队列不一样：
 *
 * - 普通队列：先进先出
 * - PriorityQueue：谁优先级更高，谁先出来
 *
 * 默认情况下，PriorityQueue 是最小堆：
 *
 * - peek() / poll() 拿到的是当前最小值
 *
 * 但这题我们想要“随时拿到最大的补给量”，
 * 所以写成：
 *
 * PriorityQueue<Integer> maxHeap =
 *     new PriorityQueue<>(Collections.reverseOrder());
 *
 * 这就把它变成了最大堆。
 *
 * 常用操作：
 *
 * - offer(x)：放进去一个候选值
 * - peek()：只看当前最优值，但不删除
 * - poll()：取出当前最优值，并从堆里删掉
 *
 * 时间复杂度通常是：
 *
 * - offer / poll：O(log n)
 * - peek：O(1)
 *
 * 它为什么能这么快：
 *
 * 因为底层通常是“二叉堆”。
 * 你可以先把它简单脑补成一棵特殊的完全二叉树：
 *
 * - 最大堆：父节点 >= 子节点
 * - 最小堆：父节点 <= 子节点
 *
 * 所以“最优元素”永远在堆顶，
 * 这就是为什么它特别适合：
 *
 * - 动态维护最大值 / 最小值
 * - Top K
 * - Dijkstra 最短路
 * - 会议室 / 调度类问题
 * - 这题这种“历史候选里随时拿最优补给”的场景
 *
 * 如果你现在对堆还是没有直觉，可以去看：
 * BinaryMinHeapLearningDemo
 *
 * 那个类里专门手写了一个最小堆，并把：
 *
 * - 父节点 / 左孩子 / 右孩子下标怎么算
 * - offer 为什么要上浮
 * - poll 为什么要下沉
 * - PriorityQueue 为什么本质上就是堆
 *
 * 都拆开写成注释了。
 */
public class MinimumJuiceStallStops {

    /**
     * 这是第二种写法：动态规划。
     *
     * 它和最大堆贪心的思路完全不一样。
     *
     * 这版不去问：
     * “我现在要不要在第 i 个摊位补给？”
     *
     * 因为这个问题很容易把人卡住。
     * 你刚才卡的“我现在不补，也能到下一个，但到不了下下一个”就是典型例子。
     *
     * 这种问法的问题在于：
     * 它是一个局部决策问题，但真正的最优性要看更远的未来。
     * 只看“下一个 / 下下一个”通常不够。
     *
     * 所以这版换一个状态表达方式：
     *
     * dp[t] = 恰好停 t 次之后，最远能走到哪里
     *
     * 一旦状态这样定义，问题就变成：
     *
     * - 如果当前最远能到第 i 个摊位
     * - 那我就有资格选择“在这个摊位停一次”
     * - 停完之后，最远距离就能再往前增加 liters[i]
     *
     * 这样写出来就不会再纠结：
     *
     * - “当前应不应该补”
     * - “补给时机是不是要看下下个点”
     *
     * 因为 DP 会把“停”这件事当成一种可选转移，
     * 最后统一比较“停 0 次 / 1 次 / 2 次 ...”哪个最先能到终点。
     */
    public void answer2() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] stallDistances = new int[n];
        for (int i = 0; i < n; i++) {
            stallDistances[i] = scanner.nextInt();
        }
        int[] juiceLiters = new int[n];
        for (int i = 0; i < n; i++) {
            juiceLiters[i] = scanner.nextInt();
        }
        int distanceToSchool = scanner.nextInt();
        int initialEnergy = scanner.nextInt();

        System.out.println(minStopsDp(stallDistances, juiceLiters, distanceToSchool, initialEnergy));
    }

    /**
     * DP 写法：
     *
     * dp[t] 表示“恰好停 t 次时，当前最远能到的距离”。
     *
     * 初始：
     * dp[0] = initialEnergy
     * 表示一次都不停时，最远就是靠初始能量走这么远。
     *
     * 然后依次看每个摊位：
     *
     * 如果某个状态 dp[t] 已经能到这个摊位 distances[i]，
     * 那就说明“停 t 次的这条路径”有资格选择在这里再停一次。
     *
     * 停完以后：
     * dp[t + 1] = max(dp[t + 1], dp[t] + liters[i])
     *
     * 为什么要倒序枚举 t：
     *
     * 因为同一个摊位只能用一次。
     * 如果正序更新，就可能让一个摊位在同一轮里被重复使用。
     *
     * 下面用样例手推一次：
     *
     * distances = [5, 7, 8, 10]
     * liters    = [2, 3, 1, 5]
     * destination = 15
     * initialEnergy = 5
     *
     * 初始：
     * dp[0] = 5
     * 说明一次都不停时，最远只能走到 5
     *
     * 所以：
     * dp = [5, -1, -1, -1, -1]
     *
     * 看第 0 个摊位，位置 5，补给 2：
     * dp[0] = 5 >= 5，说明能到这个摊位
     * 那么停 1 次之后最远能到 7
     *
     * dp = [5, 7, -1, -1, -1]
     *
     * 看第 1 个摊位，位置 7，补给 3：
     * dp[1] = 7 >= 7，说明“已经停 1 次”这条路线也能到这里
     * 所以可以转移到 dp[2] = 10
     *
     * dp = [5, 7, 10, -1, -1]
     *
     * 看第 2 个摊位，位置 8，补给 1：
     * dp[2] = 10 >= 8，可以转移到 dp[3] = 11
     *
     * dp = [5, 7, 10, 11, -1]
     *
     * 看第 3 个摊位，位置 10，补给 5：
     * dp[2] = 10 >= 10，可以把 dp[3] 更新成 15
     * dp[3] = 11 >= 10，也可以把 dp[4] 更新成 16
     *
     * dp = [5, 7, 10, 15, 16]
     *
     * 最后第一个 >= 15 的位置是 dp[3]
     * 所以答案就是 3 次。
     */
    public int minStopsDp(int[] distances, int[] liters, int destination, int initialEnergy) {
        int n = distances.length;
        long[] dp = new long[n + 1];
        Arrays.fill(dp, -1L);
        dp[0] = initialEnergy;

        for (int i = 0; i < n; i++) {
            for (int stops = i; stops >= 0; stops--) {
                if (dp[stops] < distances[i]) {
                    continue;
                }
                dp[stops + 1] = Math.max(dp[stops + 1], dp[stops] + liters[i]);
            }
        }

        for (int stops = 0; stops <= n; stops++) {
            if (dp[stops] >= destination) {
                return stops;
            }
        }
        return -1;
    }

    /**
     * 第三种写法：记忆化搜索（自顶向下 DP）。
     *
     * 这版和上面的 minStopsDp 本质上是同一个状态模型，
     * 只是表达方式从“for 循环递推”变成了“递归 + 记忆化”。
     *
     * 它的好处是：
     *
     * 1. 如果你本来就更习惯递归，这版会更好理解
     * 2. 它能帮助你看出来：DP 和记忆化搜索很多时候只是写法不同
     *
     * 这里定义：
     *
     * maxReach(i, t)
     * = 只考虑前 i 个摊位，并且恰好停 t 次时，最远能走到哪里
     *
     * 转移有两种：
     *
     * 1. 不在第 i 个摊位停
     *    那就是 maxReach(i - 1, t)
     *
     * 2. 在第 i 个摊位停
     *    前提是 maxReach(i - 1, t - 1) 已经能到这个摊位
     *    如果能到，就把 liters[i] 加上去
     */
    public int minStopsMemo(int[] distances, int[] liters, int destination, int initialEnergy) {
        int n = distances.length;
        long[][] memo = new long[n + 1][n + 1];
        boolean[][] visited = new boolean[n + 1][n + 1];

        for (int stops = 0; stops <= n; stops++) {
            long farthest = maxReachMemo(n, stops, distances, liters, initialEnergy, memo, visited);
            if (farthest >= destination) {
                return stops;
            }
        }
        return -1;
    }

    private long maxReachMemo(int consideredStalls, int stops, int[] distances, int[] liters,
                              int initialEnergy, long[][] memo, boolean[][] visited) {
        if (stops < 0 || stops > consideredStalls) {
            return -1L;
        }
        if (consideredStalls == 0) {
            return stops == 0 ? initialEnergy : -1L;
        }
        if (visited[consideredStalls][stops]) {
            return memo[consideredStalls][stops];
        }
        visited[consideredStalls][stops] = true;

        long best = maxReachMemo(
            consideredStalls - 1, stops, distances, liters, initialEnergy, memo, visited
        );

        long reachBeforeStop = maxReachMemo(
            consideredStalls - 1, stops - 1, distances, liters, initialEnergy, memo, visited
        );

        int stallIndex = consideredStalls - 1;
        if (reachBeforeStop >= distances[stallIndex]) {
            best = Math.max(best, reachBeforeStop + liters[stallIndex]);
        }

        memo[consideredStalls][stops] = best;
        return best;
    }

    public int minStops2(int[] distances, int[] liters, int destination, int initialEnergy) {
        int n = distances.length;
        int previousDistance = 0;
        int energy = initialEnergy;
        int stops = 0;
        // PriorityQueue 默认是最小堆。
        // 这里传入 reverseOrder()，就是让堆顶永远是“当前最大的补给量”。
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i <= n; i++) {
            int currentDistance = i == n ? destination : distances[i];
            int needed = currentDistance - previousDistance;
            while (energy < needed && !maxHeap.isEmpty()) {
                energy += maxHeap.poll();
                stops++;
            }
            if (energy < needed) {
                return -1;
            }
            energy -= needed;
            previousDistance = currentDistance;
            if (i < n) {
                maxHeap.offer(liters[i]);
            }
        }
        return stops;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int stallCount = scanner.nextInt();
        int[] stallDistances = new int[stallCount];
        for (int i = 0; i < stallCount; i++) {
            stallDistances[i] = scanner.nextInt();
        }
        int[] juiceLiters = new int[stallCount];
        for (int i = 0; i < stallCount; i++) {
            juiceLiters[i] = scanner.nextInt();
        }
        int destinationDistance = scanner.nextInt();
        int initialEnergy = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int stallCount = 4;
         * int[] stallDistances = {5, 7, 8, 10};
         * int[] juiceLiters = {2, 3, 1, 5};
         * int destinationDistance = 15;
         * int initialEnergy = 5;
         */

        MinimumJuiceStallStops solver = new MinimumJuiceStallStops();
        System.out.println(solver.minStops(stallDistances, juiceLiters, destinationDistance, initialEnergy));
    }

    /**
     * 这段代码的正确脑补方式是：
     *
     * John 从 previousDistance 走到 currentDistance，
     * 这段路需要 needed 单位能量。
     *
     * 如果当前 energy 不够：
     *
     * 就说明必须从“之前已经路过的摊位”里补能量。
     *
     * 为什么一定从最大堆里取最大的：
     *
     * 因为题目要的是“最少停几次”，不是“剩余能量最平滑”。
     * 每停一次都很贵，所以每次停下时都应该尽量补最多。
     *
     * 这也是为什么这里用 while (energy < needed)：
     *
     * 说明我们不是“每到一个摊位就立即决定喝不喝”，
     * 而是等到真的走不到下一个位置时，才回头从历史可选项里拿最好的。
     *
     * 这里的 PriorityQueue<Integer> maxHeap 可以直接理解成：
     *
     * “我路上已经见过、但还没决定要不要喝的那些摊位补给量集合”
     *
     * 更准确一点说：
     *
     * - 堆里放的是“候选补给量”
     * - 堆顶永远是“当前最大的补给量”
     * - 所以一旦发现 energy 不够，就能立刻补最值钱的那个
     *
     * 如果你把它和手写堆对起来看：
     *
     * - offer(...) 约等于“把一个新值插进堆里，然后做上浮”
     * - poll()    约等于“取走堆顶，再把最后一个元素放到堆顶做下沉”
     *
     * 只是这些细节，PriorityQueue 已经帮我们封装好了。
     *
     * 每次走到一个新摊位：
     *
     * maxHeap.offer(liters[i])
     *
     * 就表示：
     *
     * “这个摊位我先记下来，先不急着决定喝不喝。”
     *
     * 等到真的不够走时：
     *
     * maxHeap.poll()
     *
     * 就表示：
     *
     * “从我之前所有见过的可选补给里，拿补给量最大的那个出来喝。”
     */
    public int minStops(int[] distances, int[] liters, int destination, int initialEnergy) {
        int n = distances.length;
        int previousDistance = 0;
        int energy = initialEnergy;
        int stops = 0;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i <= n; i++) {
            int currentDistance = i == n ? destination : distances[i];
            int needed = currentDistance - previousDistance;
            while (energy < needed && !maxHeap.isEmpty()) {
                energy += maxHeap.poll();
                stops++;
            }
            if (energy < needed) {
                return -1;
            }
            energy -= needed;
            previousDistance = currentDistance;
            if (i < n) {
                maxHeap.offer(liters[i]);
            }
        }
        return stops;
    }
}
