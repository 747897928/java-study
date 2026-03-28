package com.aquarius.wizard.leetcode.shl;

import java.util.HashMap;
import java.util.Map;

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
 * 我的备注
 *
 * 难度：中等。
 *
 * 考点：状态模拟、找周期。
 * 校对：题面稳定。
 * 提示：规则等价于“下一天是否为 1 = 左右邻居是否不同”。
 */
public class StreetLightStateAfterMDays {

    public static void main(String[] args) {
        int lightCount = 8;
        int[] currentState = {1, 1, 1, 0, 1, 1, 1, 1};
        int dayCount = 2;

        if (currentState.length != lightCount) {
            throw new IllegalArgumentException("currentState.length must equal lightCount");
        }

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
        int next = 0;
        for (int i = 0; i < length; i++) {
            int left = i == 0 ? 0 : ((state >> (length - i)) & 1);
            int right = i == length - 1 ? 0 : ((state >> (length - i - 2)) & 1);
            if (left != right) {
                next |= 1 << (length - i - 1);
            }
        }
        return next;
    }

    private int encode(int[] state) {
        int encoded = 0;
        for (int value : state) {
            encoded = (encoded << 1) | value;
        }
        return encoded;
    }

    private int[] decode(int state, int length) {
        int[] decoded = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            decoded[i] = state & 1;
            state >>= 1;
        }
        return decoded;
    }
}
