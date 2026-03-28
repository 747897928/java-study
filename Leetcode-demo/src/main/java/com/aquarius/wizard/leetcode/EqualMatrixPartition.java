package com.aquarius.wizard.leetcode;

public class EqualMatrixPartition {

    /**
     * 给你一个由正整数组成的 m x n 矩阵 grid。你的任务是判断是否可以通过 一条水平或一条垂直分割线 将矩阵分割成两部分，使得：
     *
     * 分割后形成的每个部分都是 非空 的。
     * 两个部分中所有元素的和 相等 。
     * 如果存在这样的分割，返回 true；否则，返回 false。
     *
     *
     *
     * 示例 1：
     *
     * 输入： grid = [[1,4],[2,3]]
     *
     * 输出： true
     *
     * 解释：
     *
     *
     *
     * 在第 0 行和第 1 行之间进行水平分割，得到两个非空部分，每部分的元素之和为 5。因此，答案是 true。
     *
     * 示例 2：
     *
     * 输入： grid = [[1,3],[2,4]]
     *
     * 输出： false
     *
     * 解释：
     *
     * 无论是水平分割还是垂直分割，都无法使两个非空部分的元素之和相等。因此，答案是 false。
     *
     *
     *
     * 提示：
     *
     * 1 <= m == grid.length <= 105
     * 1 <= n == grid[i].length <= 105
     * 2 <= m * n <= 105
     * 1 <= grid[i][j] <= 105
     * @param args
     */
    public static void main(String[] args) {
        int[][] grid = {{1, 2}, {4, 3}};
        int[][] grid2 = {{1, 4}, {2, 3}};
        System.out.println(canPartitionGrid(grid));
        System.out.println(canPartitionGrid(grid2));
    }


    /**
     * 方法一：二维前缀和 + 枚举边界元素
     * 思路与算法
     *
     * 题目要求我们通过 一条 水平或竖直的分割线将矩阵分割为两个部分，然后判断是否存在这样一条分割线，使得每个部分中的元素之和相等。
     *
     * 看到要求矩阵某个部分中所有元素之和，很容易想到使用前缀和进行预处理。
     *
     * 那么我们只需要用二维前缀和预处理，得到一个二维前缀和矩阵 sum[m][n]，然后按照以下方式进行判断：
     *
     * 竖直分割线：
     * 想要判断是否存在竖直分割线，我们只需要枚举前缀和矩阵下边界上的元素 sum[m][i]，这个元素的值代表了以 grid[0][0] 为左上角，以 grid[m−1][i−1] 为右下角的矩阵中所有元素之和，如果这个值的两倍等于总和，那么就说明存在这样一条竖直分割线。
     * 水平分割线：
     * 同理于竖直分割线，枚举前缀和矩阵右边界上的元素进行判断即可。
     *
     * 复杂度分析
     *
     * 时间复杂度：O(mn)，其中 m 为 grid 矩阵的行数，n 为 grid 矩阵的列数。
     *
     * 空间复杂度：O(mn)，其中 m 为 grid 矩阵的行数，n 为 grid 矩阵的列数。
     *
     *
     * @param grid
     * @return
     */
    public boolean canPartitionGrid2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        long[][] sum = new long[m + 1][n + 1];
        long total = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum[i + 1][j + 1] = sum[i + 1][j] + sum[i][j + 1] - sum[i][j] + grid[i][j];
                total += grid[i][j];
            }
        }
        for (int i = 0; i < m - 1; i++) {
            if (total == sum[i + 1][n] * 2) {
                return true;
            }
        }
        for (int i = 0; i < n - 1; i++) {
            if (total == sum[m][i + 1] * 2) {
                return true;
            }
        }
        return false;
    }


    /**
     * 方法二：旋转矩阵 + 枚举上半矩阵之和
     * 思路与算法
     *
     * 题目要求判断两种分割线，我们可以将原矩阵 grid 旋转 90 度之后复用旋转之前的代码，这样只需要判断一种分割线了，本方法判断的是水平分割线，竖直分割线的判断方法类似，在此不再赘述。
     *
     * 判断方式如下：
     *
     * 枚举矩阵 grid 每一行的元素，并维护一个 sum 来保存当前行及之前行所有元素的和，在遍历完一行后进行判断，如果 sum 的值的两倍等于总和，那么分割线存在。
     *
     * 复杂度分析
     *
     * 时间复杂度：O(mn)，其中 m 为 grid 矩阵的行数，n 为 grid 矩阵的列数。
     *
     * 空间复杂度：O(mn)，其中 m 为 grid 矩阵的行数，n 为 grid 矩阵的列数
     *
     * @param grid
     * @return
     */
    public boolean canPartitionGrid3(int[][] grid) {
        long total = 0;
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                total += grid[i][j];
            }
        }
        for (int k = 0; k < 2; k++) {
            long sum = 0;
            m = grid.length;
            n = grid[0].length;
            for (int i = 0; i < m - 1; i++) {
                for (int j = 0; j < n; j++) {
                    sum += grid[i][j];
                }
                if (sum * 2 == total) {
                    return true;
                }
            }
            grid = rotation(grid);
        }
        return false;
    }

    public int[][] rotation(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] tmp = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                tmp[j][m - 1 - i] = grid[i][j];
            }
        }
        return tmp;
    }


    public static boolean canPartitionGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        long total = 0L;

        for (int[] row : grid) {
            for (int value : row) {
                total += value;
            }
        }

        if ((total & 1L) == 1L) {
            return false;
        }

        long prefix = 0L;
        for (int i = 0; i < rows - 1; i++) {
            long rowSum = 0L;
            for (int j = 0; j < cols; j++) {
                rowSum += grid[i][j];
            }
            prefix += rowSum;
            if (prefix * 2 == total) {
                return true;
            }
        }

        prefix = 0L;
        for (int j = 0; j < cols - 1; j++) {
            long colSum = 0L;
            for (int i = 0; i < rows; i++) {
                colSum += grid[i][j];
            }
            prefix += colSum;
            if (prefix * 2 == total) {
                return true;
            }
        }

        return false;
    }


    /**
     * 暴力枚举所有合法切分方式，计算两部分的和进行比较。
     *
     * int 溢出了
     *
     * 题目里：
     *
     * m * n <= 10^5
     * grid[i][j] <= 10^5
     *
     * 所以矩阵元素总和最大可以到：
     *
     * 10^5 * 10^5 = 10^10
     *
     * 但 int 最大只有：
     *
     * 2147483647
     *
     * 也就是大约 2.1 * 10^9。
     * 因此，在计算矩阵元素总和时，可能会发生 int 溢出，必须用long类型来存储和计算。
     *
     * @param grid
     * @return
     */
    public static boolean exhaustionCanPartitionGrid(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m - 1; i++) {   // 只枚举合法横切
            long sum1 = 0;
            long sum2 = 0;

            for (int k = 0; k <= i; k++) {
                for (int j = 0; j < n; j++) {
                    sum1 += grid[k][j];
                }
            }

            for (int k = i + 1; k < m; k++) {
                for (int j = 0; j < n; j++) {
                    sum2 += grid[k][j];
                }
            }

            if (sum1 == sum2) {
                return true;
            }
        }

        for (int i = 0; i < n - 1; i++) {   // 只枚举合法竖切
            long sum1 = 0;
            long sum2 = 0;

            for (int k = 0; k <= i; k++) {
                for (int j = 0; j < m; j++) {
                    sum1 += grid[j][k];
                }
            }

            for (int k = i + 1; k < n; k++) {
                for (int j = 0; j < m; j++) {
                    sum2 += grid[j][k];
                }
            }

            if (sum1 == sum2) {
                return true;
            }
        }

        return false;
    }


}
