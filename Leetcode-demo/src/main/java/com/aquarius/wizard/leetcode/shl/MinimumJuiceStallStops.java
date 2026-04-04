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
 * 推荐先看的方法：minStops(...)。
 *
 * 这个类里保留了三种写法：
 *
 * 1. minStops(...)：最大堆贪心，主解
 * 2. minStopsDp(...)：迭代 DP
 * 3. minStopsMemo(...)：记忆化搜索
 *
 * 详细思路、PriorityQueue 的理解、以及样例手推，都放在对应方法上面了，
 * 这样阅读时可以直接在“看这个解法”时同步看这段解法的注释。
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

    /**
     * 保留这个方法名只是为了兼容你之前可能写过的调用。
     *
     * 它和 minStops(...) 是同一个最大堆贪心，不再重复写一份同构代码了，
     * 否则这个文件会更乱。
     */
    public int minStops2(int[] distances, int[] liters, int destination, int initialEnergy) {
        return minStops(distances, liters, destination, initialEnergy);
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
     * 主解：最大堆贪心。
     *
     * 这题最容易卡住的不是代码，而是脑补方式。
     * 很多人会下意识这样想：
     *
     * - “走到某个摊位时，我到底要不要立刻喝？”
     * - “如果现在不喝，会不会以后后悔？”
     * - “如果后面突然需要连续喝两个以前的摊位怎么办？”
     *
     * 这三个问题，本质上都在把它想成“在线实时决策”。
     * 这种想法非常容易把自己绕晕。
     *
     * 正确的脑补是：
     *
     * - 先把所有已经路过的摊位都放进候选集合
     * - 真正走不动时，再回头决定到底把哪些摊位算作停过
     *
     * 注意：
     *
     * 代码里的 poll() 不是字面意义上的“现在走回去喝”。
     * 它是在做一种“事后结算”：
     *
     * - 这些摊位你都已经路过了
     * - 现在只是决定：回头看整段路径，到底把哪些摊位记作“我停下来喝过”
     *
     * 对这题来说，真正重要的是：
     *
     * - 你总共停了几次
     * - 你从已经路过的摊位里一共拿到了多少补给
     *
     * 而不是“那一口到底是在第几秒喝下去的”。
     *
     * 只要那个摊位在你当前目标位置之前，
     * 那么把它先放进候选集合，等真正缺能量时再决定用不用它，是完全等价的。
     *
     * 也正因为这样，前面路过但当时没喝的“大补给站”不会丢。
     * 它会一直留在堆里，等你后面真的缺时再拿出来用。
     *
     * 第一层脑补：
     *
     * John 不是“走到一个摊位就必须马上决定喝不喝”。
     *
     * 更好的脑补是：
     *
     * - 先一路往前走
     * - 每经过一个摊位，就把它记进“候选集合”
     * - 真正走不动时，再从候选集合里挑要用哪些
     *
     * 也就是说，代码里的堆不是“我现在手里已经喝下去的补给”，
     * 而是“我已经路过、以后随时可以拿来结算的补给候选”。
     *
     * 第二层脑补：
     *
     * while (energy < needed) 不是“我只考虑喝一个摊位”。
     *
     * 恰恰相反，它表示：
     *
     * - 如果喝一个还不够
     * - 那就继续喝第二个
     * - 第二个还不够，就继续喝第三个
     *
     * 直到：
     *
     * - 终于够走到 currentDistance
     * - 或者历史候选已经全用完，仍然不够
     *
     * 也就是说，你刚才最担心的这种情况：
     *
     * “我现在喝一个以前的摊位还不够，必须连喝两个甚至三个才够”
     *
     * 这段代码本来就在处理，而且正是靠 while 来处理的。
     *
     * 举个非常直接的例子：
     *
     * - 当前剩余能量 energy = 3
     * - 从 previousDistance 走到 currentDistance 需要 needed = 8
     * - 堆里历史候选补给量是 [5, 4, 2]
     *
     * 第一次 poll()，拿到 5：
     *
     * - energy 从 3 变成 8
     * - 这时刚好够，循环结束
     *
     * 如果改成堆里是 [4, 3, 2]：
     *
     * 第一次 poll() 拿 4：
     * - energy 从 3 变成 7
     * - 还不够
     *
     * while 不会停，会继续第二次 poll()：
     *
     * 第二次 poll() 拿 3：
     * - energy 从 7 变成 10
     * - 这时才够
     *
     * 所以这里从来都不是“只喝一个最大补给站”，
     * 而是“每次优先拿当前最大的，必要时连续拿多个”。
     *
     * 第三层脑补：
     *
     * 为什么“每次都先拿历史最大补给量”是对的？
     *
     * 因为题目要优化的是“停靠次数”，不是“喝得均不均匀”。
     * 每喝一个摊位，代价都是 +1 次停靠。
     *
     * 如果你已经确定：
     *
     * “我现在至少还得再喝一个历史摊位，不然过不去”
     *
     * 那么这一次当然应该优先拿补给量最大的那个。
     * 因为：
     *
     * - 拿 5 升，停靠次数 +1
     * - 拿 3 升，停靠次数也还是 +1
     *
     * 两者付出的代价完全一样，
     * 那当然先拿 5 更值。
     *
     * 进一步说，如果当前想用恰好 k 次停靠去补能量，
     * 那么“从历史候选里挑最大的 k 个”，拿到的总能量一定最多。
     *
     * 所以：
     *
     * - 如果最大的 k 个都不够你走到下一个目标
     * - 那任何其他 k 个只会更差
     *
     * 这就是贪心正确性的直观原因。
     *
     * 你担心的这种情况：
     *
     * “前面那个大补给站当时没喝，后来在更远处突然发现自己很需要它”
     *
     * 不会破坏算法。
     * 因为那个大补给站根本没有消失，它一直留在堆里。
     *
     * 后面一旦卡住，poll() 第一个拿出来的往往就是它。
     *
     * 所以这题不是：
     *
     * “前面到底喝不喝，必须当场决定”
     *
     * 而是：
     *
     * “前面先都记着，后面缺的时候，再从已经路过的摊位里倒着结算”
     *
     * PriorityQueue 在这里的作用也要顺手记住：
     *
     * Java 里的 PriorityQueue 可以先简单理解成“标准库帮你写好的堆”。
     *
     * - 默认是最小堆
     * - 这题想随时拿最大补给量，所以要传 reverseOrder()
     *
     * 写法就是：
     *
     * PriorityQueue<Integer> maxHeap =
     *     new PriorityQueue<>(Collections.reverseOrder());
     *
     * 常用操作：
     *
     * - offer(x)：把一个补给量放进候选集合
     * - peek()：只看当前最大值，不删除
     * - poll()：取出当前最大值，并从集合里删掉
     *
     * 时间复杂度：
     *
     * - offer / poll：O(log n)
     * - peek：O(1)
     *
     * 如果你对堆本身还没直觉，可以配合看：
     * BinaryMinHeapLearningDemo
     *
     * 下面再看这道题里代码变量的物理含义。
     *
     * John 从 previousDistance 走到 currentDistance，
     * 这段路需要 needed 单位能量。
     *
     * 如果当前 energy 不够，
     * 就说明必须从“之前已经路过的摊位”里补能量。
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
     * “从我之前所有见过的可选补给里，先拿补给量最大的那个出来结算；
     *  如果一个不够，就继续拿第二个、第三个。”
     *
     * 最后把样例手推一遍：
     *
     * distances   = [5, 7, 8, 10]
     * liters      = [2, 3, 1, 5]
     * destination = 15
     * initial     = 5
     *
     * 初始：
     *
     * - previousDistance = 0
     * - energy = 5
     * - heap = []
     * - stops = 0
     *
     * 目标 1：先去位置 5
     *
     * - needed = 5 - 0 = 5
     * - energy = 5，够走
     * - 走完后 energy = 0
     * - 把位置 5 的补给 2 放进堆
     * - heap = [2]
     *
     * 目标 2：去位置 7
     *
     * - needed = 7 - 5 = 2
     * - energy = 0，不够
     * - 从堆里 poll() 拿最大补给 2
     * - energy = 2，stops = 1
     * - 现在够走了，走到位置 7 后 energy = 0
     * - 把位置 7 的补给 3 放进堆
     * - heap = [3]
     *
     * 目标 3：去位置 8
     *
     * - needed = 8 - 7 = 1
     * - energy = 0，不够
     * - poll() 拿 3
     * - energy = 3，stops = 2
     * - 走到位置 8 后 energy = 2
     * - 把位置 8 的补给 1 放进堆
     * - heap = [1]
     *
     * 目标 4：去位置 10
     *
     * - needed = 10 - 8 = 2
     * - energy = 2，刚好够
     * - 走到位置 10 后 energy = 0
     * - 把位置 10 的补给 5 放进堆
     * - heap = [5, 1]
     *
     * 最后目标：去学校 15
     *
     * - needed = 15 - 10 = 5
     * - energy = 0，不够
     * - poll() 拿 5
     * - energy = 5，stops = 3
     * - 走到学校后 energy = 0
     *
     * 最终答案就是 3。
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
            // 这里是整个贪心最关键的一行。
            //
            // 它不是“最多补一次”。
            // 它表示：只要当前能量还不够，就持续从历史候选里拿最大的补给量来补。
            //
            // 所以如果一个摊位不够，就会继续拿第二个、第三个。
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
