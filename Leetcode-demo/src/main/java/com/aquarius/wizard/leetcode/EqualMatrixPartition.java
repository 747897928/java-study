package com.aquarius.wizard.leetcode;

/**
 * 题目：Equal Matrix Partition
 * <p>
 * 给你一个只包含正整数的 m x n 矩阵。
 * 你可以画一条水平切线，或者画一条竖直切线，把矩阵分成两个非空部分。
 * 如果这两个部分的元素和相等，返回 true，否则返回 false。
 * <p>
 * 这题最值得学会的核心只有一句话：
 * <p>
 * 如果一条切线把矩阵分成 A 和 B 两块，并且 sum(A) == sum(B)，
 * 那么其中任意一块的和都一定等于 total / 2。
 * <p>
 * 学习顺序建议：
 * <p>
 * 1. 先看 canPartitionGridBruteForce，先把题意写对。
 * 2. 再看 canPartitionGrid，这是考试里最值得优先写的版本。
 * 3. 最后看 canPartitionGridWith2DPrefixSum，用它真正理解二维前缀和。
 * 4. canPartitionGridByRotation 只是思路拓展，不是首选写法。
 * <p>
 * 如果你以后又把二维前缀和忘了，先回来看这 4 句：
 * <p>
 * 1. prefix[r][c] 不是坐标点，它表示“前 r 行、前 c 列”的整块区域和。
 * 2. prefix 开成 rows + 1 和 cols + 1，是为了多留一圈 0，统一处理边界。
 * 3. 竖切看 prefix[rows][cutCol]，横切看 prefix[cutRow][cols]。
 * 4. 只要某一侧的和 * 2 == total，就说明切分成功。
 * <p>
 * 如果你是“看代码时脑子里很难自动出图”的类型，就把这题强行分成两层来学：
 * <p>
 * 第一层：先学推荐写法 canPartitionGrid
 * <p>
 * 你只需要牢牢记住：
 * <p>
 * 1. 先求 total。
 * 2. 横切时看“上半部分”的和。
 * 3. 竖切时看“左半部分”的和。
 * 4. 只要某一侧 * 2 == total，就成立。
 * <p>
 * 第二层：再学 canPartitionGridWith2DPrefixSum
 * <p>
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

        System.out.println((3L & 1L) == 1L); // true，3 是奇数
        System.out.println((4L & 1L) == 1L); // false，4 是偶数
        System.out.println(5 / 2);//2
        System.out.println(5 >> 1);//2
        //5 0101 右移一位 0010 就是 2
        //2的三次方*0 + 2的二次方*1 + 2的一次方*0 + 2的零次方*1 = 2
        //5 的二进制表示是 0101。每一位代表一个二的幂次方，从右到左分别是 2^0、2^1、2^2、2^3 等等。对于 5 来说：

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
     * <p>
     * 这是最适合考试现场写出来的方法，因为它最短，也最不容易写乱。
     * 如果你真的在考试里做这题，我建议你先只想这一版。
     * <p>
     * 这一版的本质不是“前缀和模板”，而是下面这个等价变形：
     * <p>
     * 假设某条切线把矩阵分成 left 和 right 两块：
     * <p>
     * sum(left) == sum(right)
     * <p>
     * 因为：
     * <p>
     * sum(left) + sum(right) = total
     * <p>
     * 所以只要：
     * <p>
     * sum(left) * 2 == total
     * <p>
     * 就够了。
     * <p>
     * 这就是为什么这题根本不需要你每次都同时算两边。
     * 你只需要盯住其中一边。
     * <p>
     * 步骤只有两步：
     * <p>
     * 1. 先算整个矩阵总和 total。
     * 2. 枚举每一条合法切线，看切线一侧的和是否等于 total / 2。
     * <p>
     * 横切时，维护“上半部分”的和。
     * 竖切时，维护“左半部分”的和。
     * <p>
     * 先看横切，你脑子里应该浮现这个图：
     * <p>
     * a  b  c
     * d  e  f
     * -------
     * g  h  i
     * j  k  l
     * <p>
     * 这条横线表示：在第 2 行和第 3 行之间切开。
     * <p>
     * 那么上半部分就是：
     * <p>
     * a  b  c
     * d  e  f
     * <p>
     * 下半部分就是：
     * <p>
     * g  h  i
     * j  k  l
     * <p>
     * 这时如果你已经知道 total，
     * 那么你只要一路累加“上半部分”的和 topSum。
     * <p>
     * 如果某一刻：
     * <p>
     * topSum * 2 == total
     * <p>
     * 那就说明：
     * <p>
     * topSum == total - topSum
     * <p>
     * 也就是上半部分和下半部分相等。
     * <p>
     * 竖切完全一样，只不过把“上半部分”换成“左半部分”。
     * <p>
     * 竖切示意图：
     * <p>
     * a  b | c  d
     * e  f | g  h
     * i  j | k  l
     * <p>
     * 如果切线画在第 1 列和第 2 列之间，
     * 那么左半部分就是：
     * <p>
     * a  b
     * e  f
     * i  j
     * <p>
     * 只要左半部分的和 * 2 == total，
     * 就说明右半部分的和也一样，切分成立。
     * <p>
     * 你可以把代码里的 leftSum 理解成：
     * <p>
     * “切线从左往右扫的时候，左边已经被扫过的所有列之和”
     * <p>
     * 你可以把 topSum 理解成：
     * <p>
     * “切线从上往下扫的时候，上边已经被扫过的所有行之和”
     * <p>
     * 为什么这版适合考试：
     * <p>
     * 1. 代码短。
     * 2. 推导短。
     * 3. 不用额外二维数组。
     * 4. 你脑子里只要维护“一边的和”。
     * <p>
     * 这题为什么一定要用 long：
     * <p>
     * 题目允许 m * n <= 10^5，grid[i][j] <= 10^5，
     * 所以总和最大可能到 10^10，int 不够。
     * <p>
     * 另外，下面这句也要看懂：
     * <p>
     * if ((total & 1L) == 1L) {
     * return false;
     * }
     * <p>
     * 它是在判断 total 是不是奇数。
     * <p>
     * 因为如果总和是奇数，就绝对不可能平分成两个相等的整数和。
     * <p>
     * 例如 total = 11：
     * <p>
     * 如果左右两边要相等，就必须有：
     * <p>
     * leftSum == rightSum
     * leftSum + rightSum == 11
     * <p>
     * 两式合起来就是：
     * <p>
     * 2 * leftSum == 11
     * <p>
     * 这显然不可能。
     * <p>
     * `(total & 1L) == 1L` 和 `total % 2 != 0` 是一个意思。
     * 只是这里用位运算去看最低位：
     * 8*1+4*1+2*0+1*1=13
     * 13 1101  2的三次方*1 + 2的二次方*1 + 2的一次方*0 + 2的零次方*1
     * 12 1100  2的三次方*1 + 2的二次方*1 + 2的一次方*0 + 2的零次方*0
     * <p>
     * <p>
     * 最低位是 1 -> 奇数
     * 最低位是 0 -> 偶数
     * <p>
     * 所以如果你以后再忘了这题，先别急着想二维前缀和。
     * 先问自己一句：
     * <p>
     * “我能不能只维护切线一边的和，然后用 * 2 == total 判断？”
     * <p>
     * 这句话才是这题最本质的反应。
     */
    public static boolean canPartitionGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        long total = computeTotalSum(grid);

        //判断 total 是不是奇数。用位运算去看最低位：最低位是 1 -> 奇数，最低位是 0 -> 偶数
        if ((total & 1L) == 1L) {
            return false;
        }

        long topSum = 0L;
        for (int row = 0; row < rows - 1; row++) {
            // 切线每向下移动一行，上半部分就多包含一整行。
            topSum += sumRow(grid, row);
            if (topSum * 2 == total) {
                return true;
            }
        }

        long leftSum = 0L;
        for (int col = 0; col < cols - 1; col++) {
            // 切线每向右移动一列，左半部分就多包含一整列。
            leftSum += sumColumn(grid, col);
            if (leftSum * 2 == total) {
                return true;
            }
        }

        return false;
    }

    /**
     * 暴力写法：把每一条合法切线都试一遍。
     * <p>
     * 这版的意义不在于快，而在于它最贴近题意：
     * <p>
     * 1. 先枚举横切线，再把上半部分和下半部分真的加出来。
     * 2. 再枚举竖切线，再把左半部分和右半部分真的加出来。
     * <p>
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
     * <p>
     * 定义：
     * <p>
     * prefix[r][c] 表示左上角子矩阵 grid[0..r-1][0..c-1] 的元素和。
     * <p>
     * 你可以直接把它读成人话：
     * <p>
     * prefix[r][c] = 前 r 行、前 c 列，这一整块区域的和
     * <p>
     * 这一句非常重要。
     * 因为很多人一看到 prefix[3][2]，会本能地把它看成“第 3 行第 2 列那个位置”。
     * 这是错的。
     * <p>
     * 它不是一个“点”的含义，而是一个“整块面积”的含义。
     * <p>
     * 注意：
     * <p>
     * 1. r 和 c 说的是“前几行、前几列”，不是原矩阵里的直接下标。
     * 2. 所以 prefix 要开成 [rows + 1][cols + 1]。
     * <p>
     * 1. 为什么是 prefix[rows + 1][cols + 1]
     * <p>
     * 因为我们故意多开一行 0 和一列 0，让：
     * <p>
     * prefix[0][*] = 0
     * prefix[*][0] = 0
     * <p>
     * 这样第一行、第一列也能直接套统一公式，不需要额外写边界判断。
     * <p>
     * 你可以把这一圈理解成“全 0 边框”：
     * <p>
     * prefix:
     * <p>
     * 0   1   2   3
     * +---+---+---+---+
     * 0 | 0 | 0 | 0 | 0 |
     * +---+---+---+---+
     * 1 | 0 |   |   |   |
     * +---+---+---+---+
     * 2 | 0 |   |   |   |
     * +---+---+---+---+
     * 3 | 0 |   |   |   |
     * +---+---+---+---+
     * <p>
     * 有了这一圈 0，第一行和第一列就不再特殊。
     * 所有位置都能用同一条公式。
     * <p>
     * 如果你不故意多开这一圈 0，也不是不能做。
     * 只是会马上遇到边界判断问题。
     * <p>
     * 因为统一公式里会用到“左边、上边、左上角”。
     * 但如果当前位置就在第一行或第一列，这些位置有些就不存在了。
     * <p>
     * 例如原矩阵左上角第一个格子，如果不多开边框，
     * 你在套公式时会想访问“不存在的上边、左边、左上角”。
     * <p>
     * 这时你就只能写很多 if：
     * <p>
     * 1. 左上角单独处理
     * 2. 第一行单独处理
     * 3. 第一列单独处理
     * 4. 其他格子再套通用公式
     * <p>
     * 所以“多开一圈 0”不是数学上必须，
     * 而是为了把所有格子的计算统一掉。
     * <p>
     * 以后你一看到前缀和数组开成 n + 1 / m + 1，
     * 就应该想到：
     * <p>
     * “这是在主动买一个全 0 边框，换掉一堆边界判断。”
     * <p>
     * 你可以把 prefix 想成“面积表”，不是“坐标表”。
     * <p>
     * 例子：
     * <p>
     * 原矩阵下标：
     * <p>
     * 0  1  2  3
     * 0   a  b  c  d
     * 1   e  f  g  h
     * 2   i  j  k  l
     * <p>
     * prefix[3][2] 的意思不是“第 3 行第 2 列”。
     * 它真正的意思是：
     * <p>
     * 前 3 行、前 2 列的整块区域之和，也就是：
     * <p>
     * 0  1 | 2  3
     * 0   a  b | .  .
     * 1   e  f | .  .
     * 2   i  j | .  .
     * <p>
     * 3. 为什么看 prefix[rows][cutCol] 就能判断竖切
     * <p>
     * 因为它刚好就是“左半部分的和”。
     * <p>
     * 所以，当我们想判断“能不能在 cutCol 这一列前面竖着切开”时，
     * 左半部分的和就是 prefix[rows][cutCol]。
     * <p>
     * 这里最容易读错的地方是：
     * <p>
     * prefix[rows][cutCol]
     * 不是“第 rows 行第 cutCol 列那个点”
     * 而是“整个矩阵前 rows 行、前 cutCol 列的那一大块”
     * <p>
     * 例如：
     * <p>
     * a  b | c  d
     * e  f | g  h
     * i  j | k  l
     * <p>
     * 左半部分一共有前 2 列，因此左半部分之和就是 prefix[3][2]。
     * 只要 prefix[3][2] * 2 == total，就说明能切。
     * <p>
     * 2. 为什么是：
     * <p>
     * prefix[i + 1][j + 1]
     * = prefix[i + 1][j] + prefix[i][j + 1] - prefix[i][j] + grid[i][j]
     * <p>
     * 可以看这个小图：
     * <p>
     * +-----+---+
     * |  A  | B |
     * +-----+---+
     * |  C  | X |
     * +-----+---+
     * <p>
     * 其中：
     * <p>
     * prefix[i + 1][j] = A + C
     * prefix[i][j + 1] = A + B
     * prefix[i][j]     = A
     * <p>
     * 也就是说：
     * <p>
     * (A + C) + (A + B) - A + X
     * = A + B + C + X
     * <p>
     * 这刚好就是左上角大矩形的和。
     * <p>
     * 如果你想把它翻译得更口语一点，就是：
     * <p>
     * 1. 左边整块先拿来
     * 2. 上边整块再拿来
     * 3. 左上重叠了一次，所以减掉一次
     * 4. 当前格子还没算进去，再补上
     * <p>
     * 这就是那条公式的全部本质。
     * <p>
     * 所以这题里的二维前缀和，不要背成抽象公式。
     * 你要真的能在脑子里看到：
     * <p>
     * “我现在拿了左边一块，又拿了上边一块，左上重叠了，所以减一次，最后补当前格子”
     * <p>
     * 一旦这个画面能脑补出来，公式就不容易忘。
     * <p>
     * 这里还有一个边界细节一定要会自己解释：
     * <p>
     * for (int cutRow = 1; cutRow < rows; cutRow++)
     * <p>
     * 为什么从 1 开始？
     * <p>
     * 因为 cutRow 表示“上半部分拿前几行”。
     * 如果 cutRow = 0，就表示上半部分一行都不拿，上半部分为空，不合法。
     * <p>
     * 为什么到 cutRow < rows 为止，而不是 <= rows？
     * <p>
     * 因为如果 cutRow = rows，就表示前 rows 行全拿走，
     * 也就是下半部分为空，同样不合法。
     * <p>
     * 所以合法切线只可能是：
     * <p>
     * 1 <= cutRow <= rows - 1
     * <p>
     * 写成 for 循环就是：
     * <p>
     * cutRow = 1; cutRow < rows; cutRow++
     * <p>
     * cutCol 也完全一样：
     * <p>
     * 1 <= cutCol <= cols - 1
     * <p>
     * 这类边界思考以后要形成固定反应：
     * <p>
     * “题目要求切完两边都非空，所以切线只能落在中间，不能贴边。”
     */
    public static boolean canPartitionGridWith2DPrefixSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        long[][] prefix = buildPrefixSum(grid);
        long total = prefix[rows][cols];

        //判断 total 是不是奇数。用位运算去看最低位：最低位是 1 -> 奇数，最低位是 0 -> 偶数
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
     * <p>
     * 它的优点是思路统一：
     * <p>
     * 1. 先判断原矩阵是否存在横切。
     * 2. 再把矩阵顺时针旋转 90 度。
     * 3. 旋转后的“横切”，就等价于原矩阵的“竖切”。
     * <p>
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
     * <p>
     * 如果你觉得 canPartitionGridWith2DPrefixSum 上面的长注释还是抽象，
     * 那就盯着这一个方法看，把它理解成“面积表是怎么一格一格填出来的”。
     * <p>
     * 看这一段时，脑子里只保留下面这张图就够了：
     * <p>
     * +-----+---+
     * |  A  | B |
     * +-----+---+
     * |  C  | X |
     * +-----+---+
     * <p>
     * 当我们要算右下角这个大矩形 A+B+C+X 时：
     * <p>
     * 1. 左边大块 prefix[row + 1][col] 给了 A + C
     * 2. 上边大块 prefix[row][col + 1] 给了 A + B
     * 3. 左上角小块 prefix[row][col] 被重复算了一次，所以要减掉
     * 4. 最后再把当前格子 grid[row][col] 也就是 X 补回来
     * <p>
     * 所以公式就是：
     * <p>
     * prefix[row + 1][col + 1]
     * = prefix[row + 1][col]
     * + prefix[row][col + 1]
     * - prefix[row][col]
     * + grid[row][col]
     * <p>
     * 之所以用 row + 1 / col + 1，
     * 就是因为 prefix 比原矩阵多了一圈 0 边框。
     * <p>
     * 也就是说：
     * <p>
     * 原矩阵的 grid[row][col]
     * 会被放到 prefix[row + 1][col + 1]
     * <p>
     * 这样 prefix 的第 0 行和第 0 列就专门用来放 0，
     * 真正的数据从第 1 行、第 1 列开始。
     */
    private static long[][] buildPrefixSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        long[][] prefix = new long[rows + 1][cols + 1];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                prefix[row + 1][col + 1] = prefix[row + 1][col] + prefix[row][col + 1] - prefix[row][col] + grid[row][col];
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
