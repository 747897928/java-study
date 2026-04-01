package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 72
 *
 * A distributor distributes energy drinks in a 34 oz can to customers who each have a unique ID.
 * He distributes the drink according to the demand for the drink. He has developed a system that
 * handles all the transaction queries of the store. The system will handle two types of queries
 * (i.e. query 1 and query 2). The first query determines if the customer’s ID has already been
 * logged, and if so, it adds the quantity of the new sale to the previous quantity and updates
 * the record and if not logged in before, then it will make a fresh entry. The query 2 retrieves
 * the quantity of cans purchased by the given range of the customer ID. At the end of the day,
 * the distributer must submit a report for all the type 2 queries.
 *
 * Write an algorithm to help the distributer find the answer for all the type 2 queries.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. queryCount
 * 2. Each query is either:
 *    type 1 customerId quantity
 *    type 2 leftCustomerId rightCustomerId
 */
public class Q72CustomerPurchaseRangeQuerySystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int queryCount = scanner.nextInt();
        long[][] queries = new long[queryCount][3];
        long[] ids = new long[queryCount * 2];
        int idCount = 0;
        for (int i = 0; i < queryCount; i++) {
            int type = scanner.nextInt();
            queries[i][0] = type;
            queries[i][1] = scanner.nextLong();
            queries[i][2] = scanner.nextLong();
            ids[idCount++] = queries[i][1];
            ids[idCount++] = queries[i][2];
        }

        Q72CustomerPurchaseRangeQuerySystem solver = new Q72CustomerPurchaseRangeQuerySystem();
        System.out.print(solver.processQueries(queries, Arrays.copyOf(ids, idCount)));
    }

    public String processQueries(long[][] queries, long[] ids) {
        Arrays.sort(ids);
        int uniqueCount = 0;
        for (int i = 0; i < ids.length; i++) {
            if (i == 0 || ids[i] != ids[i - 1]) {
                ids[uniqueCount++] = ids[i];
            }
        }
        long[] sortedIds = Arrays.copyOf(ids, uniqueCount);
        Fenwick fenwick = new Fenwick(uniqueCount);

        StringBuilder builder = new StringBuilder();
        boolean firstAnswer = true;
        for (long[] query : queries) {
            if (query[0] == 1L) {
                int index = exactIndex(sortedIds, query[1]);
                fenwick.add(index + 1, query[2]);
            } else {
                long left = Math.min(query[1], query[2]);
                long right = Math.max(query[1], query[2]);
                long answer = fenwick.prefixSum(upperBound(sortedIds, right))
                    - fenwick.prefixSum(upperBound(sortedIds, left - 1));
                if (!firstAnswer) {
                    builder.append('\n');
                }
                firstAnswer = false;
                builder.append(answer);
            }
        }
        return builder.toString();
    }

    private int exactIndex(long[] values, long target) {
        int index = Arrays.binarySearch(values, target);
        return Math.max(index, 0);
    }

    private int upperBound(long[] values, long target) {
        int left = 0;
        int right = values.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (values[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private static class Fenwick {
        private final long[] tree;

        private Fenwick(int size) {
            tree = new long[size + 1];
        }

        private void add(int index, long delta) {
            while (index < tree.length) {
                tree[index] += delta;
                index += index & -index;
            }
        }

        private long prefixSum(int index) {
            long sum = 0L;
            while (index > 0) {
                sum += tree[index];
                index -= index & -index;
            }
            return sum;
        }
    }
}
