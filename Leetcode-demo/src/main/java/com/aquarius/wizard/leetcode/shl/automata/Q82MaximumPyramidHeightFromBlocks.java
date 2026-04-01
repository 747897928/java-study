package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 82
 *
 * The government of UtmostLand wants to create the highest and strongest pyramid. The civil
 * engineer has ordered a special type of cuboid rock block. These blocks were very costly, so the
 * government approved the purchase of only N number of rock blocks. The blocks are of various
 * widths, and each block has a unit height. The design team will arrange these blocks in such a
 * way that the total width and number of blocks at one level is less than that of the level below
 * it. With these conditions, the team must construct the pyramid to reach the highest height in
 * accordance with the government decree.
 *
 * Write an algorithm to find the height of the pyramid that the team can build.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. blockCount
 * 2. blockCount widths
 */
public class Q82MaximumPyramidHeightFromBlocks {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int blockCount = scanner.nextInt();
        int[] widths = new int[blockCount];
        for (int i = 0; i < blockCount; i++) {
            widths[i] = scanner.nextInt();
        }

        Q82MaximumPyramidHeightFromBlocks solver = new Q82MaximumPyramidHeightFromBlocks();
        System.out.println(solver.maximumHeight(widths));
    }

    public int maximumHeight(int[] widths) {
        Arrays.sort(widths);
        int height = 0;
        int previousCount = 0;
        long previousWidth = 0L;
        int currentCount = 0;
        long currentWidth = 0L;

        for (int width : widths) {
            currentCount++;
            currentWidth += width;
            if (currentCount > previousCount && currentWidth > previousWidth) {
                height++;
                previousCount = currentCount;
                previousWidth = currentWidth;
                currentCount = 0;
                currentWidth = 0L;
            }
        }
        return height;
    }
}
