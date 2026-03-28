package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Question
 *
 * A virtual memory management system in an operating system uses First-In-First-Out (FIFO) cache.
 *
 * When a requested memory page is not in the cache and the cache is full, the page that has been in
 * the cache for the longest duration is removed to make room for the requested page. If the cache is
 * not full, then the requested page can simply be added to the cache. A given page should occur once
 * in the cache at most.
 *
 * Given the maximum size of the cache and an array of page requests, calculate the number of cache
 * misses. A cache miss occurs when a page is requested but is not found in the cache. Initially, the
 * cache is empty.
 *
 * Input
 *
 * The first line of the input consists of a positive integer - page_requests_size, representing the
 * total number of pages (N).
 * The second line contains N space-separated positive integers - page_requests[0], page_requests[1],
 * ..., page_requests[N-1], representing the page requests for N pages.
 * The last line consists of an integer - max_cache_size, representing the size of the cache.
 *
 * Output
 *
 * Print an integer representing the number of cache misses.
 *
 * Example
 *
 * Input:
 * 6
 * 1 2 1 3 1 2
 * 2
 *
 * Output:
 * 5
 *
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：队列模拟、缓存状态维护。
 * 校对：题面稳定。
 * 提示：FIFO 命中时顺序不更新，这一点和 LRU 不同。
 */
public class FifoCacheMissCounter {

    public int countMisses(int[] pageRequests, int maxCacheSize) {
        if (maxCacheSize <= 0) {
            return pageRequests.length;
        }

        Set<Integer> cache = new HashSet<>();
        Queue<Integer> order = new ArrayDeque<>();
        int misses = 0;
        for (int page : pageRequests) {
            if (cache.contains(page)) {
                continue;
            }
            misses++;
            if (cache.size() == maxCacheSize) {
                int evict = order.poll();
                cache.remove(evict);
            }
            cache.add(page);
            order.offer(page);
        }
        return misses;
    }
}
