package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 6
 *
 * Dr. Jackson, a researcher, wishes to perform an experiment. He has a variety of toxic
 * chemicals. Each chemical has some vapor rate. When two chemicals are mixed, then the vapor rate
 * of the mixture is the multiplication of their respective vapor rates.
 *
 * Dr. Jackson picks two equal-sized sets of non-overlapping, consecutively-placed chemicals from
 * a series of chemicals in his lab. He reverses the positions of the chemicals in the second set.
 * He then mixes each chemical from the first set with the correspondingly-placed chemical of the
 * second set. The total vapor rate at the end of the experiment is the sum of the products of the
 * respective vapor rates of the chemicals that he mixed from both sets. If the total vapor rate
 * is negative, he will not pick any set.
 *
 * Write an algorithm to find the maximum vapor rate obtainable after the experiment.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. chemicalCount
 * 2. chemicalCount vapor rates
 */
public class Q06MaximumVaporRateFromReversedChemicalPairs {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int chemicalCount = scanner.nextInt();
        int[] vaporRates = new int[chemicalCount];
        for (int i = 0; i < chemicalCount; i++) {
            vaporRates[i] = scanner.nextInt();
        }

        Q06MaximumVaporRateFromReversedChemicalPairs solver =
            new Q06MaximumVaporRateFromReversedChemicalPairs();
        System.out.println(solver.maximumVaporRate(vaporRates));
    }

    public long maximumVaporRate(int[] vaporRates) {
        long best = 0L;
        for (int center = 0; center < vaporRates.length - 1; center++) {
            long sum = 0L;
            for (int left = center, right = center + 1;
                 left >= 0 && right < vaporRates.length;
                 left--, right++) {
                sum += (long) vaporRates[left] * vaporRates[right];
                best = Math.max(best, sum);
            }
        }
        return best;
    }
}
