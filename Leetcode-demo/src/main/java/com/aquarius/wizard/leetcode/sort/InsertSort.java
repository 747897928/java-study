package com.aquarius.wizard.leetcode.sort;

import java.util.Arrays;

/**
 * <p>create: 2020/11/20 16:37</p>
 *
 * @author zhaoyijie(AquariusGenius)
 *
 * 插入排序（Insertion Sort）
 *
 * 你原来整理的原理保留：
 *
 * 插入排序（Insertion-Sort）算法的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，
 * 找到相应位置并插入。插入排序在实现上通常采用 in-place 排序（只需 O(1) 额外空间），
 * 因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。
 *
 * 过程可以概括为：
 *
 * ① 从第一个元素开始，该元素可以认为已经被排序
 * ② 取出下一个元素，在已经排序的元素序列中从后向前扫描
 * ③ 如果该元素大于新元素，将该元素移到下一位置
 * ④ 重复步骤③，直到找到已排序元素小于或等于新元素的位置
 * ⑤ 将新元素插入到该位置后
 * ⑥ 重复步骤②~⑤
 *
 * 平均时间复杂度：O(n^2)
 * 最坏时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 * 排序方式：In-place
 * 稳定性：稳定
 *
 * 这题最适合你训练“我能不能脑补数组是怎么移动的”。
 *
 * 核心画面不是“交换”，而是：
 *
 * 1. 左边已经有序
 * 2. 右边还没处理
 * 3. 每次从右边拿一个新元素，往左边有序区里插
 *
 * 例如：
 *
 * [5, 2, 4, 6, 1, 3]
 *
 * 当 i = 2 时，表示要把 arr[2] = 4 插入到前面的有序区：
 *
 * [2, 5 | 4, 6, 1, 3]
 *        ^
 *       key
 *
 * 这时不是立刻交换 5 和 4，
 * 而是先把比 4 大的元素往右挪，给 4 腾位置：
 *
 * [2, 5, 5, 6, 1, 3]
 *
 * 然后把 4 塞回去：
 *
 * [2, 4, 5, 6, 1, 3]
 *
 * 所以插入排序的本质是“挪位 + 插空”，不是不停交换。
 *
 * 相似题：
 * 1. 二分插入排序
 * 2. 维护一个始终有序的前缀
 * 3. 扑克牌摸牌后往手里插的过程
 */
public class InsertSort implements SortBase {

    public static void main(String[] args) {
        int[] numbers = {5, 2, 4, 6, 1, 3};

        InsertSort solver = new InsertSort();
        System.out.println("input  = " + Arrays.toString(numbers));
        System.out.println("output = " + Arrays.toString(solver.sort(numbers)));
    }

    @Override
    public int[] sort(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] copy = Arrays.copyOf(arr, arr.length);
        insertSort(copy);
        return copy;
    }

    /**
     * 标准插入排序。
     *
     * 你原来的“从后往前扫描”这句话很关键，要保留：
     *
     * 插入排序不是从前往后找位置，而是从 i-1 开始往左扫。
     * 因为左边这一段已经有序，越往左越小，所以一旦遇到 <= key 的元素，就可以停。
     *
     * 外层循环变量 i 的含义：
     *
     * arr[0..i-1] 已经有序
     * 现在要把 arr[i] 插入到这个有序区里
     *
     * 例如：
     *
     * i = 4
     *
     * [1, 2, 4, 6 | 3, ...]
     *              ^
     *             key
     *
     * 这时 key = 3，要插入到左边有序区 [1, 2, 4, 6] 中。
     *
     * 内层 j 从 i - 1 开始往左扫：
     *
     * 1. 如果 arr[j] > key，说明 arr[j] 应该往右挪一格
     * 2. 如果 arr[j] <= key，说明 key 应该插在 j 的后面
     *
     * 所以最后写：
     *
     * arr[j + 1] = key
     *
     * 这是因为：
     *
     * - 如果 break 退出，j 停在“最后一个 <= key 的位置”
     * - 如果一路扫到 -1，说明 key 比前面所有数都小，此时应该插到 0 号位
     *
     * 所以统一写 j + 1 刚好成立。
     */
    public void insertionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    /**
     * 保留你原来的方法名，方便和旧笔记、旧记忆对齐。
     */
    public void insertSort(int[] arr) {
        insertionSort(arr);
    }

    /**
     * 二分插入排序。
     *
     * 这是你原来“插入排序改进版”的保留版。
     *
     * 它优化的是“找插入位置”的比较次数，
     * 但元素搬移次数并没有变，整体时间复杂度仍然是 O(n^2)。
     *
     * 适合理解：
     *
     * 1. 有序区里可以用二分找位置
     * 2. 找到位置之后，右侧元素仍然要整体后移
     *
     * 这里有一个很容易忘掉的位运算写法：
     *
     * int mid = left + ((right - left) >>> 1);
     *
     * 先把它当成：
     *
     * int mid = left + (right - left) / 2;
     *
     * 就够了。
     *
     * 为什么这样写？
     *
     * 因为这里是在二分查找中间位置。
     * `right - left` 表示当前搜索区间的长度差，
     * “右移 1 位”可以先理解成“除以 2”。
     *
     * 对小白来说，先记这两句：
     *
     * 1. `>>`  是带符号右移
     * 2. `>>>` 是无符号右移
     *
     * 最直白的区别：
     *
     * - `>>` 右移时，会保留符号信息
     * - `>>>` 右移时，左边空出来的位置统一补 0
     *
     * 但在这里 `right - left` 一定 >= 0，
     * 所以 `>> 1` 和 `>>> 1` 你现在都可以先脑补成“除以 2”。
     *
     * 我这里写 `>>> 1`，
     * 是为了让“取一半”这个动作更直观，也避免你以后看到这种写法时发懵。
     *
     * 另外，这里写成：
     *
     * left + ((right - left) >>> 1)
     *
     * 而不是：
     *
     * (left + right) / 2
     *
     * 是因为前一种写法更安全。
     * 如果 left 和 right 都很大，直接相加有理论上的整数溢出风险。
     */
    public void binaryInsertionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int left = 0;
            int right = i - 1;

            while (left <= right) {
                int mid = left + ((right - left) >>> 1);
                if (arr[mid] > key) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            for (int j = i - 1; j >= left; j--) {
                arr[j + 1] = arr[j];
            }
            arr[left] = key;
        }
    }

    /**
     * 保留你原来的方法名，方便和旧笔记、旧记忆对齐。
     *
     * 这里也重复强调一次：
     *
     * int mid = left + ((right - left) >>> 1);
     *
     * 可以先理解成：
     *
     * int mid = left + (right - left) / 2;
     *
     * 也就是“在 left 和 right 之间取中点”。
     *
     * 对于这里这种非负区间长度：
     *
     * - `>> 1`  可以先当成除以 2
     * - `>>> 1` 也可以先当成除以 2
     *
     * 真正的区别主要出现在负数上。
     */
    public void binaryInsertSort(int[] arr, int len) {
        if (arr == null) {
            return;
        }
        if (len < 0 || len > arr.length) {
            throw new IllegalArgumentException("len is out of range");
        }
        if (len <= 1) {
            return;
        }

        for (int i = 1; i < len; i++) {
            int key = arr[i];
            int left = 0;
            int right = i - 1;

            while (left <= right) {
                int mid = left + ((right - left) >>> 1);
                if (arr[mid] > key) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            for (int j = i - 1; j >= left; j--) {
                arr[j + 1] = arr[j];
            }
            arr[left] = key;
        }
    }
}
