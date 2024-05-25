package com.aquarius.wizard.leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * <p>description:  </p>
 * <p>create:  2020/11/15 11:03</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class DailyTemperature {
    /**
     * 739.每日温度
     * 根据每日气温列表，请重新生成一个列表，对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。
     * 如果之后都不会升高，请在该位置用О来代替。
     * 示例: temperatures = [23,24,25,21,19,22,26,23]
     * 提示: 输出:[1,1,4,2,1,1,0,0]
     * 气温列表 temperatures 长度的范围是 (1，30000]。
     */
    public static void main(String[] args) {
        int[] temperatures = {23, 24, 25, 21, 19, 22, 26, 23};
        int[] result = new int[temperatures.length];
        Stack<StackItem> stack = new Stack<>();
        for (int i = 0; i < temperatures.length; i++) {
            int i1 = temperatures[i];
            while (!stack.empty() && i1 > stack.peek().data) {
                StackItem stackItem = stack.pop();
                int index = stackItem.index;
                int i2 = i - index;
                result[index] = i2;
            }
            stack.push(new StackItem(i1, i));
        }
        System.out.println(Arrays.toString(result));
    }
    public static class StackItem {

        int data;

        int index;

        public StackItem() {
        }

        public StackItem(int data, int index) {
            this.data = data;
            this.index = index;
        }

        @Override
        public String toString() {
            return "{" +
                    "data=" + data +
                    ", index=" + index +
                    '}';
        }
    }
}
