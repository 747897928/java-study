import java.util.Arrays;

/**
 * 这个类的目标不是刷题，而是把“堆”这件事拆开看清楚。
 *
 * 如果以后你看到 PriorityQueue，却不知道它到底在帮你做什么，
 * 可以先跑这个类。
 *
 * 先记一句最核心的话：
 *
 * PriorityQueue 默认就是“最小堆”的库实现。
 *
 * 也就是说：
 *
 * - 你手写的最小堆，目标是让最小值永远待在堆顶
 * - Java 自带的 PriorityQueue，默认也正是在做这件事
 *
 * 所以这个类的价值是：
 *
 * 1. 先把“堆的底层动作”看懂
 * 2. 再回头看 PriorityQueue，就不会只会背 API
 *
 * 你可以把堆先脑补成：
 *
 * “用数组存的一棵完全二叉树”
 *
 * 为什么能用数组存树？
 *
 * 因为完全二叉树非常整齐，
 * 所以父子下标可以直接算出来，不需要真的 new 一堆节点对象。
 *
 * 下标关系要背熟：
 *
 * - parent(i) = (i - 1) / 2
 * - left(i)   = i * 2 + 1
 * - right(i)  = i * 2 + 2
 *
 * 最小堆的约束只有一句：
 *
 * “每个父节点都 <= 它的两个孩子”
 *
 * 这句话不要求整棵树完全有序。
 * 它只保证一件事：
 *
 * - 根节点一定是全局最小值
 *
 * 这就够了。
 *
 * 因为很多题只需要：
 *
 * - 快速拿最小值
 * - 插入新值
 * - 删除最小值
 *
 * 这正是堆最擅长的事。
 */
public class BinaryMinHeapLearningDemo {

    public static void main(String[] args) {
        IntMinHeap heap = new IntMinHeap();

        /*
         * 手动看运行过程时，可以先观察下面这组数：
         *
         * 7, 3, 10, 1, 5, 8
         *
         * 它不是有序输入，这样更容易看出“上浮 / 下沉”到底在修什么。
         *
         * 下面先手推一遍 offer 过程。
         * 你可以一边看注释，一边对照程序输出。
         *
         * 第 1 次插入 7：
         *
         * 数组: [7]
         *
         * 树:
         *      7
         *
         * 只有一个点，不需要上浮。
         *
         * 第 2 次插入 3：
         *
         * 先放到数组尾部:
         * [7, 3]
         *
         * 树:
         *      7
         *     /
         *    3
         *
         * 这时 3 比父节点 7 小，违反最小堆，
         * 所以交换，变成:
         * [3, 7]
         *
         * 树:
         *      3
         *     /
         *    7
         *
         * 第 3 次插入 10：
         *
         * 先放到尾部:
         * [3, 7, 10]
         *
         * 树:
         *      3
         *     / \
         *    7  10
         *
         * 10 >= 3，不需要上浮。
         *
         * 第 4 次插入 1：
         *
         * 先放到尾部:
         * [3, 7, 10, 1]
         *
         * 树:
         *        3
         *       / \
         *      7  10
         *     /
         *    1
         *
         * 先和父节点 7 比，1 更小，交换:
         * [3, 1, 10, 7]
         *
         * 树:
         *        3
         *       / \
         *      1  10
         *     /
         *    7
         *
         * 再和新的父节点 3 比，1 更小，再交换:
         * [1, 3, 10, 7]
         *
         * 树:
         *        1
         *       / \
         *      3  10
         *     /
         *    7
         *
         * 这就是“上浮”的典型过程：
         * 新插入的值，顺着父链一路往上找自己的位置。
         *
         * 第 5 次插入 5：
         *
         * 先放到尾部:
         * [1, 3, 10, 7, 5]
         *
         * 树:
         *        1
         *       / \
         *      3  10
         *     / \
         *    7   5
         *
         * 5 >= 3，不需要上浮。
         *
         * 第 6 次插入 8：
         *
         * 先放到尾部:
         * [1, 3, 10, 7, 5, 8]
         *
         * 树:
         *        1
         *       / \
         *      3  10
         *     / \  /
         *    7  5 8
         *
         * 8 < 10，所以和父节点交换:
         * [1, 3, 8, 7, 5, 10]
         *
         * 树:
         *        1
         *       / \
         *      3   8
         *     / \ /
         *    7  5 10
         *
         * 继续往上看，8 >= 1，停止。
         *
         * 所以 offer 全部结束后，堆数组会是:
         * [1, 3, 8, 7, 5, 10]
         *
         * 注意：
         * 它不是整体有序数组。
         * 例如 7 和 5 的位置就不是升序。
         *
         * 堆只保证：
         * 每个父节点 <= 孩子节点。
         * 这已经足够让“最小值永远在堆顶”。
         */
        int[] numbers = {7, 3, 10, 1, 5, 8};

        System.out.println("=== offer 演示 ===");
        for (int number : numbers) {
            heap.offer(number);
            System.out.println("插入 " + number + " 后，堆数组 = " + heap.debugString());
        }

        System.out.println();
        System.out.println("堆顶最小值 peek = " + heap.peek());

        System.out.println();
        System.out.println("=== poll 演示 ===");
        while (!heap.isEmpty()) {
            int value = heap.poll();
            System.out.println("取出 " + value + " 后，堆数组 = " + heap.debugString());
        }

        /*
         * 预期观察：
         *
         * 1. 每次 offer 之后，不一定整体有序
         *    但堆顶一定是当前最小值
         *
         * 2. 连续 poll 的输出顺序会是升序：
         *    1, 3, 5, 7, 8, 10
         *
         * 这正是最小堆的效果。
         *
         * 下面再手推一次第一次 poll：
         *
         * 假设当前堆是:
         * [1, 3, 8, 7, 5, 10]
         *
         * 树:
         *        1
         *       / \
         *      3   8
         *     / \ /
         *    7  5 10
         *
         * poll 的目标是“删掉堆顶最小值 1”。
         *
         * 不能直接把 1 擦掉不管，
         * 因为那样数组表示的完全二叉树会在最前面留下一个洞。
         *
         * 所以标准做法是：
         *
         * 1. 先把最后一个元素 10 搬到堆顶
         * 2. 再做下沉
         *
         * 搬完后数组先变成:
         * [10, 3, 8, 7, 5]
         *
         * 树:
         *       10
         *      /  \
         *     3    8
         *    / \
         *   7   5
         *
         * 这时 10 太大了，破坏最小堆。
         * 所以看它的两个孩子 3 和 8，挑更小的 3 来交换：
         *
         * [3, 10, 8, 7, 5]
         *
         * 树:
         *        3
         *       / \
         *     10   8
         *     / \
         *    7   5
         *
         * 现在 10 继续往下看。
         * 它的两个孩子是 7 和 5，挑更小的 5 交换：
         *
         * [3, 5, 8, 7, 10]
         *
         * 树:
         *        3
         *       / \
         *      5   8
         *     / \
         *    7  10
         *
         * 这时 10 已经没有更小的孩子需要交换，停止。
         *
         * 这就是“下沉”的本质：
         * 一个暂时放错位置的大值，从上往下找到它该待的位置。
         */
    }

    /**
     * 这里只做 int 版本，目的是把原理讲清楚。
     * 不去写泛型，不去追求工业化。
     *
     * 先把一版最核心的堆操作看明白，比一开始就写复杂泛型类更有学习价值。
     */
    static class IntMinHeap {
        private int[] heap = new int[8];
        private int size = 0;

        /**
         * 插入一个新值。
         *
         * 插入动作分成两步：
         *
         * 1. 先把新值放到数组最后面
         * 2. 再不断和父节点比较，必要时往上交换
         *
         * 第 2 步就叫“上浮”（sift up / bubble up）。
         *
         * 为什么要上浮？
         *
         * 因为前面的部分原本已经是合法堆了，
         * 真正可能破坏最小堆性质的，只有这个刚插进来的新节点。
         *
         * 所以不需要全局重排，
         * 只需要让这个新节点沿着“父链”往上修即可。
         */
        public void offer(int value) {
            ensureCapacity();
            heap[size] = value;
            siftUp(size);
            size++;
        }

        /**
         * 只看堆顶最小值，不删除。
         */
        public int peek() {
            if (size == 0) {
                throw new IllegalStateException("heap is empty");
            }
            return heap[0];
        }

        /**
         * 删除并返回堆顶最小值。
         *
         * poll 是整个堆里最值得反复看的一步。
         *
         * 过程是：
         *
         * 1. 先记住堆顶最小值，后面要返回它
         * 2. 把最后一个元素搬到堆顶
         * 3. size--
         * 4. 从堆顶开始往下修
         *
         * 第 4 步就叫“下沉”（sift down）。
         *
         * 为什么是把最后一个元素搬到堆顶？
         *
         * 因为数组表示的完全二叉树要求结构尽量紧凑。
         * 如果直接把堆顶删了，中间会留下洞。
         *
         * 所以正确做法是：
         *
         * - 用最后一个元素补上这个洞
         * - 再让它往下找到正确位置
         */
        public int poll() {
            if (size == 0) {
                throw new IllegalStateException("heap is empty");
            }
            int result = heap[0];
            size--;
            if (size > 0) {
                heap[0] = heap[size];
                siftDown(0);
            }
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * 上浮：
         *
         * 当前节点如果比父节点更小，
         * 就违反了“父节点 <= 子节点”的最小堆性质，
         * 所以要交换。
         *
         * 交换后继续往上看，
         * 直到：
         *
         * - 到达根节点
         * - 或者父节点已经 <= 当前节点
         *
         * 可以拿数组 [3, 7, 10, 1] 来看：
         *
         * - index = 3，值是 1
         * - parent = (3 - 1) / 2 = 1，父节点值是 7
         * - 1 < 7，所以交换
         *
         * 交换后:
         * [3, 1, 10, 7]
         *
         * 此时 index 变成 1，继续往上看：
         *
         * - parent = (1 - 1) / 2 = 0，父节点值是 3
         * - 1 < 3，所以再交换
         *
         * 最终:
         * [1, 3, 10, 7]
         */
        private void siftUp(int index) {
            while (index > 0) {
                int parent = (index - 1) / 2;
                if (heap[parent] <= heap[index]) {
                    break;
                }
                swap(parent, index);
                index = parent;
            }
        }

        /**
         * 下沉：
         *
         * 现在 index 位置的值可能过大，
         * 所以要和它的两个孩子里“更小的那个”比较。
         *
         * 注意这里一定要和更小的孩子比，
         * 因为我们要保证堆顶方向始终是“更小的值往上”。
         *
         * 如果当前值已经 <= 更小的孩子，
         * 说明它放在这里没问题，停止。
         *
         * 否则就交换，并继续往下修。
         *
         * 可以拿数组 [10, 3, 8, 7, 5] 来看：
         *
         * - index = 0，值是 10
         * - 左孩子是 3，右孩子是 8
         * - 先挑更小的 3
         * - 10 > 3，所以交换
         *
         * 交换后:
         * [3, 10, 8, 7, 5]
         *
         * 然后 index 变成 1，再继续：
         *
         * - 当前值是 10
         * - 左孩子是 7，右孩子是 5
         * - 挑更小的 5
         * - 10 > 5，所以交换
         *
         * 最终:
         * [3, 5, 8, 7, 10]
         */
        private void siftDown(int index) {
            while (true) {
                int left = index * 2 + 1;
                int right = index * 2 + 2;
                int smallest = index;

                if (left < size && heap[left] < heap[smallest]) {
                    smallest = left;
                }
                if (right < size && heap[right] < heap[smallest]) {
                    smallest = right;
                }
                if (smallest == index) {
                    break;
                }
                swap(index, smallest);
                index = smallest;
            }
        }

        private void ensureCapacity() {
            if (size == heap.length) {
                heap = Arrays.copyOf(heap, heap.length * 2);
            }
        }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        public String debugString() {
            return Arrays.toString(Arrays.copyOf(heap, size));
        }
    }
}
