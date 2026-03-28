package com.aquarius.wizard.leetcode;

/**
 * 题目：Equal Matrix Partition
 *
 * 给你一个只包含正整数的 m x n 矩阵。
 * 你可以画一条水平切线，或者画一条竖直切线，把矩阵分成两个非空部分。
 * 如果这两个部分的元素和相等，返回 true，否则返回 false。
 *
 * 这题最值得学会的核心只有一句话：
 *
 * 如果一条切线把矩阵分成 A 和 B 两块，并且 sum(A) == sum(B)，
 * 那么其中任意一块的和都一定等于 total / 2。
 *
 * 学习顺序建议：
 *
 * 1. 先看 canPartitionGridBruteForce，先把题意写对。
 * 2. 再看 canPartitionGrid，这是考试里最值得优先写的版本。
 * 3. 最后看 canPartitionGridWith2DPrefixSum，用它真正理解二维前缀和。
 * 4. canPartitionGridByRotation 只是思路拓展，不是首选写法。
 *
 * 如果你以后又把二维前缀和忘了，先回来看这 4 句：
 *
 * 1. prefix[r][c] 不是坐标点，它表示“前 r 行、前 c 列”的整块区域和。
 * 2. prefix 开成 rows + 1 和 cols + 1，是为了多留一圈 0，统一处理边界。
 * 3. 竖切看 prefix[rows][cutCol]，横切看 prefix[cutRow][cols]。
 * 4. 只要某一侧的和 * 2 == total，就说明切分成功。
 *
 * 如果你是“看代码时脑子里很难自动出图”的类型，就把这题强行分成两层来学：
 *
 * 第一层：先学推荐写法 canPartitionGrid
 *
 * 你只需要牢牢记住：
 *
 * 1. 先求 total。
 * 2. 横切时看“上半部分”的和。
 * 3. 竖切时看“左半部分”的和。
 * 4. 只要某一侧 * 2 == total，就成立。
 *
 * 第二层：再学 canPartitionGridWith2DPrefixSum
 *
 * 这一层不要一开始就去背公式。
 * 你要先把 prefix 理解成“面积表”，不是“坐标表”。
 * 一旦这个感觉对了，后面的公式、切线、区域和，都会自然很多。
 */
public class EqualMatrixPartition {

    public static void main(String[] args) {
        int[][] exampleCanSplit = {{1, 4}, {2, 3}};
        int[][] exampleCannotSplit = {{1, 3}, {2, 4}};

        runExample("exampleCanSplit", exampleCanSplit);
        runExample("exampleCannotSplit", exampleCannotSplit);
    }

    private static void runExample(String name, int[][] grid) {
        System.out.println(name);
        System.out.println("recommended      = " + canPartitionGrid(grid));
        System.out.println("brute force      = " + canPartitionGridBruteForce(grid));
        System.out.println("2d prefix sum    = " + canPartitionGridWith2DPrefixSum(grid));
        System.out.println("rotation variant = " + canPartitionGridByRotation(grid));
        System.out.println();
    }

    /**
     * 推荐写法：总和 + 一维前缀扫描
     *
     * 这是最适合考试现场写出来的方法，因为它最短，也最不容易写乱。
     * 如果你真的在考试里做这题，我建议你先只想这一版。
     *
     * 这一版的本质不是“前缀和模板”，而是下面这个等价变形：
     *
     * 假设某条切线把矩阵分成 left 和 right 两块：
     *
     * sum(left) == sum(right)
     *
     * 因为：
     *
     * sum(left) + sum(right) = total
     *
     * 所以只要：
     *
     * sum(left) * 2 == total
     *
     * 就够了。
     *
     * 这就是为什么这题根本不需要你每次都同时算两边。
     * 你只需要盯住其中一边。
     *
     * 步骤只有两步：
     *
     * 1. 先算整个矩阵总和 total。
     * 2. 枚举每一条合法切线，看切线一侧的和是否等于 total / 2。
     *
     * 横切时，维护“上半部分”的和。
     * 竖切时，维护“左半部分”的和。
     *
     * 先看横切，你脑子里应该浮现这个图：
     *
     * a  b  c
     * d  e  f
     * -------
     * g  h  i
     * j  k  l
     *
     * 这条横线表示：在第 2 行和第 3 行之间切开。
     *
     * 那么上半部分就是：
     *
     * a  b  c
     * d  e  f
     *
     * 下半部分就是：
     *
     * g  h  i
     * j  k  l
     *
     * 这时如果你已经知道 total，
     * 那么你只要一路累加“上半部分”的和 topSum。
     *
     * 如果某一刻：
     *
     * topSum * 2 == total
     *
     * 那就说明：
     *
     * topSum == total - topSum
     *
     * 也就是上半部分和下半部分相等。
     *
     * 竖切完全一样，只不过把“上半部分”换成“左半部分”。
     *
     * 竖切示意图：
     *
     * a  b | c  d
     * e  f | g  h
     * i  j | k  l
     *
     * 如果切线画在第 1 列和第 2 列之间，
     * 那么左半部分就是：
     *
     * a  b
     * e  f
     * i  j
     *
     * 只要左半部分的和 * 2 == total，
     * 就说明右半部分的和也一样，切分成立。
     *
     * 你可以把代码里的 leftSum 理解成：
     *
     * “切线从左往右扫的时候，左边已经被扫过的所有列之和”
     *
     * 你可以把 topSum 理解成：
     *
     * “切线从上往下扫的时候，上边已经被扫过的所有行之和”
     *
     * 为什么这版适合考试：
     *
     * 1. 代码短。
     * 2. 推导短。
     * 3. 不用额外二维数组。
     * 4. 你脑子里只要维护“一边的和”。
     *
     * 这题为什么一定要用 long：
     *
     * 题目允许 m * n <= 10^5，grid[i][j] <= 10^5，
     * 所以总和最大可能到 10^10，int 不够。
     *
     * 所以如果你以后再忘了这题，先别急着想二维前缀和。
     * 先问自己一句：
     *
     * “我能不能只维护切线一边的和，然后用 * 2 == total 判断？”
     *
     * 这句话才是这题最本质的反应。
     */
    public static boolean canPartitionGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        long total = computeTotalSum(grid);

        if ((total & 1L) == 1L) {
            return false;
        }

        long topSum = 0L;
        for (int row = 0; row < rows - 1; row++) {
            topSum += sumRow(grid, row);
            if (topSum * 2 == total) {
                return true;
            }
        }

        long leftSum = 0L;
        for (int col = 0; col < cols - 1; col++) {
            leftSum += sumColumn(grid, col);
            if (leftSum * 2 == total) {
                return true;
            }
        }

        return false;
    }

    /**
     * 暴力写法：把每一条合法切线都试一遍。
     *
     * 这版的意义不在于快，而在于它最贴近题意：
     *
     * 1. 先枚举横切线，再把上半部分和下半部分真的加出来。
     * 2. 再枚举竖切线，再把左半部分和右半部分真的加出来。
     *
     * 它的缺点是重复计算很多区域，所以会慢。
     * 但它很适合做“第一反应”和“小数据对拍版本”。
     */
    public static boolean canPartitionGridBruteForce(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int cutRow = 0; cutRow < rows - 1; cutRow++) {
            long topSum = 0L;
            long bottomSum = 0L;

            for (int row = 0; row <= cutRow; row++) {
                for (int col = 0; col < cols; col++) {
                    topSum += grid[row][col];
                }
            }

            for (int row = cutRow + 1; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    bottomSum += grid[row][col];
                }
            }

            if (topSum == bottomSum) {
                return true;
            }
        }

        for (int cutCol = 0; cutCol < cols - 1; cutCol++) {
            long leftSum = 0L;
            long rightSum = 0L;

            for (int col = 0; col <= cutCol; col++) {
                for (int row = 0; row < rows; row++) {
                    leftSum += grid[row][col];
                }
            }

            for (int col = cutCol + 1; col < cols; col++) {
                for (int row = 0; row < rows; row++) {
                    rightSum += grid[row][col];
                }
            }

            if (leftSum == rightSum) {
                return true;
            }
        }

        return false;
    }

    /**
     * 二维前缀和版本：这版的重点不是“套模板”，而是先把 prefix 的定义想清楚。
     *
     * 定义：
     *
     * prefix[r][c] 表示左上角子矩阵 grid[0..r-1][0..c-1] 的元素和。
     *
     * 你可以直接把它读成人话：
     *
     * prefix[r][c] = 前 r 行、前 c 列，这一整块区域的和
     *
     * 这一句非常重要。
     * 因为很多人一看到 prefix[3][2]，会本能地把它看成“第 3 行第 2 列那个位置”。
     * 这是错的。
     *
     * 它不是一个“点”的含义，而是一个“整块面积”的含义。
     *
     * 注意：
     *
     * 1. r 和 c 说的是“前几行、前几列”，不是原矩阵里的直接下标。
     * 2. 所以 prefix 要开成 [rows + 1][cols + 1]。
     *
     * 1. 为什么是 prefix[rows + 1][cols + 1]
     *
     * 因为我们故意多开一行 0 和一列 0，让：
     *
     * prefix[0][*] = 0
     * prefix[*][0] = 0
     *
     * 这样第一行、第一列也能直接套统一公式，不需要额外写边界判断。
     *
     * 你可以把这一圈理解成“全 0 边框”：
     *
     * prefix:
     *
     *      0   1   2   3
     *   +---+---+---+---+
     * 0 | 0 | 0 | 0 | 0 |
     *   +---+---+---+---+
     * 1 | 0 |   |   |   |
     *   +---+---+---+---+
     * 2 | 0 |   |   |   |
     *   +---+---+---+---+
     * 3 | 0 |   |   |   |
     *   +---+---+---+---+
     *
     * 有了这一圈 0，第一行和第一列就不再特殊。
     * 所有位置都能用同一条公式。
     *
     * 你可以把 prefix 想成“面积表”，不是“坐标表”。
     *
     * 例子：
     *
     * 原矩阵下标：
     *
     *     0  1  2  3
     * 0   a  b  c  d
     * 1   e  f  g  h
     * 2   i  j  k  l
     *
     * prefix[3][2] 的意思不是“第 3 行第 2 列”。
     * 它真正的意思是：
     *
     * 前 3 行、前 2 列的整块区域之和，也就是：
     *
     *     0  1 | 2  3
     * 0   a  b | .  .
     * 1   e  f | .  .
     * 2   i  j | .  .
     *
     * 3. 为什么看 prefix[rows][cutCol] 就能判断竖切
     *
     * 因为它刚好就是“左半部分的和”。
     *
     * 所以，当我们想判断“能不能在 cutCol 这一列前面竖着切开”时，
     * 左半部分的和就是 prefix[rows][cutCol]。
     *
     * 这里最容易读错的地方是：
     *
     * prefix[rows][cutCol]
     * 不是“第 rows 行第 cutCol 列那个点”
     * 而是“整个矩阵前 rows 行、前 cutCol 列的那一大块”
     *
     * 例如：
     *
     * a  b | c  d
     * e  f | g  h
     * i  j | k  l
     *
     * 左半部分一共有前 2 列，因此左半部分之和就是 prefix[3][2]。
     * 只要 prefix[3][2] * 2 == total，就说明能切。
     *
     * 2. 为什么是：
     *
     * prefix[i + 1][j + 1]
     * = prefix[i + 1][j] + prefix[i][j + 1] - prefix[i][j] + grid[i][j]
     *
     * 可以看这个小图：
     *
     * +-----+---+
     * |  A  | B |
     * +-----+---+
     * |  C  | X |
     * +-----+---+
     *
     * 其中：
     *
     * prefix[i + 1][j] = A + C
     * prefix[i][j + 1] = A + B
     * prefix[i][j]     = A
     *
     * 也就是说：
     *
     * (A + C) + (A + B) - A + X
     * = A + B + C + X
     *
     * 这刚好就是左上角大矩形的和。
     *
     * 如果你想把它翻译得更口语一点，就是：
     *
     * 1. 左边整块先拿来
     * 2. 上边整块再拿来
     * 3. 左上重叠了一次，所以减掉一次
     * 4. 当前格子还没算进去，再补上
     *
     * 这就是那条公式的全部本质。
     *
     * 所以这题里的二维前缀和，不要背成抽象公式。
     * 你要真的能在脑子里看到：
     *
     * “我现在拿了左边一块，又拿了上边一块，左上重叠了，所以减一次，最后补当前格子”
     *
     * 一旦这个画面能脑补出来，公式就不容易忘。
     */
    public static boolean canPartitionGridWith2DPrefixSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        long[][] prefix = buildPrefixSum(grid);
        long total = prefix[rows][cols];

        if ((total & 1L) == 1L) {
            return false;
        }

        for (int cutRow = 1; cutRow < rows; cutRow++) {
            long topSum = prefix[cutRow][cols];
            if (topSum * 2 == total) {
                return true;
            }
        }

        for (int cutCol = 1; cutCol < cols; cutCol++) {
            long leftSum = prefix[rows][cutCol];
            if (leftSum * 2 == total) {
                return true;
            }
        }

        return false;
    }

    /**
     * 旋转版本：只写“横切判断”，把矩阵旋转一次后再复用。
     *
     * 它的优点是思路统一：
     *
     * 1. 先判断原矩阵是否存在横切。
     * 2. 再把矩阵顺时针旋转 90 度。
     * 3. 旋转后的“横切”，就等价于原矩阵的“竖切”。
     *
     * 这版也能做，但实战里不如推荐写法直接。
     */
    public static boolean canPartitionGridByRotation(int[][] grid) {
        long total = computeTotalSum(grid);

        if ((total & 1L) == 1L) {
            return false;
        }

        int[][] current = grid;
        for (int round = 0; round < 2; round++) {
            if (hasEqualHorizontalCut(current, total)) {
                return true;
            }
            current = rotateClockwise(current);
        }

        return false;
    }

    /**
     * 兼容旧命名：等价于 canPartitionGridBruteForce。
     */
    public static boolean exhaustionCanPartitionGrid(int[][] grid) {
        return canPartitionGridBruteForce(grid);
    }

    /**
     * 兼容旧命名：等价于 canPartitionGridWith2DPrefixSum。
     */
    public static boolean canPartitionGrid2(int[][] grid) {
        return canPartitionGridWith2DPrefixSum(grid);
    }

    /**
     * 兼容旧命名：等价于 canPartitionGridByRotation。
     */
    public static boolean canPartitionGrid3(int[][] grid) {
        return canPartitionGridByRotation(grid);
    }

    /**
     * 兼容旧命名：等价于 rotateClockwise。
     */
    public static int[][] rotation(int[][] grid) {
        return rotateClockwise(grid);
    }

    private static long computeTotalSum(int[][] grid) {
        long total = 0L;
        for (int[] row : grid) {
            for (int value : row) {
                total += value;
            }
        }
        return total;
    }

    private static long sumRow(int[][] grid, int row) {
        long sum = 0L;
        for (int value : grid[row]) {
            sum += value;
        }
        return sum;
    }

    private static long sumColumn(int[][] grid, int col) {
        long sum = 0L;
        for (int[] row : grid) {
            sum += row[col];
        }
        return sum;
    }

    private static boolean hasEqualHorizontalCut(int[][] grid, long total) {
        long topSum = 0L;
        for (int row = 0; row < grid.length - 1; row++) {
            topSum += sumRow(grid, row);
            if (topSum * 2 == total) {
                return true;
            }
        }
        return false;
    }

    /**
     * buildPrefixSum 是二维前缀和真正落地的地方。
     *
     * 如果你觉得 canPartitionGridWith2DPrefixSum 上面的长注释还是抽象，
     * 那就盯着这一个方法看，把它理解成“面积表是怎么一格一格填出来的”。
     *
     * 看这一段时，脑子里只保留下面这张图就够了：
     *
     * +-----+---+
     * |  A  | B |
     * +-----+---+
     * |  C  | X |
     * +-----+---+
     *
     * 当我们要算右下角这个大矩形 A+B+C+X 时：
     *
     * 1. 左边大块 prefix[row + 1][col] 给了 A + C
     * 2. 上边大块 prefix[row][col + 1] 给了 A + B
     * 3. 左上角小块 prefix[row][col] 被重复算了一次，所以要减掉
     * 4. 最后再把当前格子 grid[row][col] 也就是 X 补回来
     *
     * 所以公式就是：
     *
     * prefix[row + 1][col + 1]
     * = prefix[row + 1][col]
     * + prefix[row][col + 1]
     * - prefix[row][col]
     * + grid[row][col]
     *
     * 之所以用 row + 1 / col + 1，
     * 就是因为 prefix 比原矩阵多了一圈 0 边框。
     *
     * 也就是说：
     *
     * 原矩阵的 grid[row][col]
     * 会被放到 prefix[row + 1][col + 1]
     *
     * 这样 prefix 的第 0 行和第 0 列就专门用来放 0，
     * 真正的数据从第 1 行、第 1 列开始。
     */
    private static long[][] buildPrefixSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        long[][] prefix = new long[rows + 1][cols + 1];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                prefix[row + 1][col + 1] =
                        prefix[row + 1][col]
                                + prefix[row][col + 1]
                                - prefix[row][col]
                                + grid[row][col];
            }
        }

        return prefix;
    }

    private static int[][] rotateClockwise(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] rotated = new int[cols][rows];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                rotated[col][rows - 1 - row] = grid[row][col];
            }
        }

        return rotated;
    }
}
