package com.aquarius.wizard.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>description:  </p>
 * <p>create:  2020/11/10 16:59</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class MostElements {

    /**
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于[n/2]的元素。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * 示例1: 输入: [3,2,3] 输出: 3
     * 示例2: 输入: [2,2,1,1,1,2,2] 输出: 2
     */
    private Map<Integer, Integer> countNums(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (int num : nums) {
            if (!counts.containsKey(num)) {
                counts.put(num, 1);
            } else {
                counts.put(num, counts.get(num) + 1);
            }
        }
        return counts;
    }

    public int majorityElement(int[] nums) {
        Map<Integer, Integer> counts = countNums(nums);

        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) {
                majorityEntry = entry;
            }
        }

        return majorityEntry.getKey();
    }

    //排序找出
        /*public int majorityElement(int[] nums) {
            Arrays.sort(nums);
            return nums[nums.length / 2];
        }*/


    /**
     * 分治法
     */
    class Solution1 {
        private int countInRange(int[] nums, int num, int lo, int hi) {
            int count = 0;
            for (int i = lo; i <= hi; i++) {
                if (nums[i] == num) {
                    count++;
                }
            }
            return count;
        }

        private int majorityElementRec(int[] nums, int lo, int hi) {
            // base case; the only element in an array of size 1 is the majority
            // element.
            if (lo == hi) {
                return nums[lo];
            }

            // recurse on left and right halves of this slice.
            int mid = (hi - lo) / 2 + lo;
            int left = majorityElementRec(nums, lo, mid);
            int right = majorityElementRec(nums, mid + 1, hi);

            // if the two halves agree on the majority element, return it.
            if (left == right) {
                return left;
            }

            // otherwise, count each element and return the "winner".
            int leftCount = countInRange(nums, left, lo, hi);
            int rightCount = countInRange(nums, right, lo, hi);

            return leftCount > rightCount ? left : right;
        }

        public int majorityElement(int[] nums) {
            return majorityElementRec(nums, 0, nums.length - 1);
        }
    }


    /**
     * 方法五：Boyer-Moore 投票算法
     * 思路
     * 如果我们把众数记为 +1+1，把其他数记为 -1−1，将它们全部加起来，显然和大于 0，
     * 从结果本身我们可以看出众数比其他数多。
     *
     * 复杂度分析
     * 时间复杂度：O(n)O(n)。Boyer-Moore 算法只对数组进行了一次遍历。
     *
     * 空间复杂度：O(1)O(1)。Boyer-Moore 算法只需要常数级别的额外空间。
     * 投票算法证明：
     * 如果候选人不是maj 则 maj,会和其他非候选人一起反对 会反对候选人,所以候选人一定会下台(maj==0时发生换届选举)
     * 如果候选人是maj , 则maj 会支持自己，其他候选人会反对，同样因为maj 票数超过一半，所以maj 一定会成功当选
     */
    class Solution2 {
        public int majorityElement(int[] nums) {
            int count = 0;
            Integer candidate = null;

            for (int num : nums) {
                if (count == 0) {
                    candidate = num;
                }
                count += (num == candidate) ? 1 : -1;
            }

            return candidate;
        }
    }
}
