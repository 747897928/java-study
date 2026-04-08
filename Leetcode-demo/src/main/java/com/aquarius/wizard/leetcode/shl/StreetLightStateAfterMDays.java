package com.aquarius.wizard.leetcode.shl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Question
 *
 * Mr. Woods, an electrician has made some faulty connections of eight street lights in Timberland
 * city. The connections are such that if the street lights adjacent to a particular light are both
 * ON (represented as 1) or are both OFF (represented as 0), then that street light goes OFF the
 * next night. Otherwise, it remains ON the next night. The two street lights at the end of the road
 * have only a single adjacent street light, so the other adjacent light can be assumed to be
 * always OFF. The state of the lights on a particular day is considered for the next day and not
 * for the same day.
 *
 * Due to this fault, the people of the city are facing difficulty in driving on the road at night.
 * So, they have filed a complaint about this to the Head of the Federal Highway Administration.
 * Based on this complaint the head has asked for the report of the state of street lights after M
 * days.
 *
 * Write an algorithm to output the state of the street lights after the given M days.
 *
 * Input
 *
 * The first line of input consists of an integer currentState_size, representing the number of
 * street lights (N).
 * The next line consists of N space-separated integers currentState, representing the current state
 * of the street lights.
 * The last line consists of an integer days, representing the number of days (M).
 *
 * Output
 *
 * Print eight space-separated integers representing the state of the street lights after M days.
 *
 * Constraints
 *
 * 1 <= days <= 10^6
 *
 * Example
 *
 * Input:
 * 8
 * 1 1 1 0 1 1 1 1
 * 2
 *
 * Output:
 * 0 0 0 0 0 1 1 0
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：状态模拟、找周期。
 * 校对：题面稳定。
 * 提示：规则等价于“下一天是否为 1 = 左右邻居是否不同”。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class StreetLightStateAfterMDays {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int lightCount = scanner.nextInt();
        int[] currentState = new int[lightCount];
        for (int i = 0; i < lightCount; i++) {
            currentState[i] = scanner.nextInt();
        }
        int dayCount = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int lightCount = 8;
         * int[] currentState = {1, 1, 1, 0, 1, 1, 1, 1};
         * int dayCount = 2;
         */

        StreetLightStateAfterMDays solver = new StreetLightStateAfterMDays();
        System.out.println(format(solver.stateAfterDays(currentState, dayCount)));
    }

    private static String format(int[] nums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(nums[i]);
        }
        return builder.toString();
    }

    public int[] stateAfterDays(int[] currentState, int days) {
        /*
         * 这题如果老老实实模拟 M 天，days 最多能到 10^6，虽然 8 个灯其实也能跑，
         * 但更值得学的是“状态会重复，所以可以找周期”这个思路。
         *
         * 这里先把整个灯泡数组编码成一个 int：
         * 例如 [1, 0, 1, 1] -> 二进制 1011。
         *
         * 这样做之后：
         * - 一个完整状态就可以用一个整数表示
         * - HashMap 里记录“某个状态第一次出现在第几天”会很方便
         *
         * 一旦某个状态再次出现，就说明后面进入循环了。
         * 之后不需要一天一天跑到 days，只要对周期长度取模即可。
         */
        int state = encode(currentState);
        Map<Integer, Integer> seenAt = new HashMap<>();
        int day = 0;
        while (day < days && !seenAt.containsKey(state)) {
            seenAt.put(state, day);
            state = nextState(state, currentState.length);
            day++;
        }
        if (day < days) {
            int cycleStart = seenAt.get(state);
            int cycleLength = day - cycleStart;
            int remaining = (days - day) % cycleLength;
            for (int i = 0; i < remaining; i++) {
                state = nextState(state, currentState.length);
            }
        }
        return decode(state, currentState.length);
    }

    private int nextState(int state, int length) {
        /*
         * 题面说：
         * - 左右邻居都相同 -> 下一天变 0
         * - 左右邻居不同 -> 下一天变 1
         *
         * 所以规则其实就是：
         * next[i] = 1，当且仅当 left != right
         *
         * 这里直接在位运算层面上逐位构造下一个状态。
         */
        int next = 0;
        for (int i = 0; i < length; i++) {
            // 边界灯泡缺失的一侧按 0 处理。
            int left = i == 0 ? 0 : ((state >> (length - i)) & 1);
            int right = i == length - 1 ? 0 : ((state >> (length - i - 2)) & 1);
            if (left != right) {
                // 如果下一天这一位要亮，就把 next 对应的 bit 位置成 1。
                next |= 1 << (length - i - 1);
            }
        }
        return next;
    }

    private int encode(int[] state) {
        /*
         * 从左到右把数组拼成一个二进制整数。
         * 例如 [1, 0, 1]：
         * 初始 0
         * -> (0 << 1) | 1 = 1
         * -> (1 << 1) | 0 = 2
         * -> (2 << 1) | 1 = 5
         */
        int encoded = 0;
        for (int value : state) {
            encoded = (encoded << 1) | value;
        }
        return encoded;
    }

    private int[] decode(int state, int length) {
        /*
         * encode 的逆过程：从最低位开始一位一位拆回来。
         */
        int[] decoded = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            decoded[i] = state & 1;
            state >>= 1;
        }
        return decoded;
    }
}
