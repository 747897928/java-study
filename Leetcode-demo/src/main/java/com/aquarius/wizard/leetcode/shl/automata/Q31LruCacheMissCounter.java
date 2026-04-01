package com.aquarius.wizard.leetcode.shl.automata;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 31
 *
 * A virtual memory management system in an operating system uses Least Recently Used (LRU) cache.
 * When a requested memory page is not in the cache and the cache is full, the page that was least
 * recently used should be removed from the cache to make room for the requested page. If the cache
 * is not full, then the requested page is added to the cache and considered to be the most
 * recently used element in the cache. A given page should occur once in the cache at most.
 *
 * Given the maximum size of the cache and an array of page requests, calculate the number of cache
 * misses. A cache miss occurs when a page is requested but is not found in the cache.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. cacheSize
 * 2. requestCount
 * 3. requestCount space-separated page IDs
 */
public class Q31LruCacheMissCounter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cacheSize = scanner.nextInt();
        int requestCount = scanner.nextInt();
        int[] requests = new int[requestCount];
        for (int i = 0; i < requestCount; i++) {
            requests[i] = scanner.nextInt();
        }

        /*
         * Local practice input:
         *
         * int cacheSize = 3;
         * int[] requests = {1, 2, 3, 1, 4, 5};
         */

        Q31LruCacheMissCounter solver = new Q31LruCacheMissCounter();
        System.out.println(solver.countMisses(cacheSize, requests));
    }

    public int countMisses(int cacheSize, int[] requests) {
        if (cacheSize <= 0) {
            return requests.length;
        }

        Map<Integer, Boolean> cache = new LinkedHashMap<>(16, 0.75f, true);
        int misses = 0;
        for (int page : requests) {
            if (cache.containsKey(page)) {
                cache.get(page);
                continue;
            }

            misses++;
            if (cache.size() == cacheSize) {
                int lru = cache.keySet().iterator().next();
                cache.remove(lru);
            }
            cache.put(page, Boolean.TRUE);
        }
        return misses;
    }
}
