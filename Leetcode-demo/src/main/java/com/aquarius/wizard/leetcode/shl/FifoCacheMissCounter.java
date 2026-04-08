package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
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
 * 备注
 *
 * 难度：简单。
 *
 * 考点：队列模拟、缓存状态维护。
 * 校对：题面稳定。
 * 提示：FIFO 命中时顺序不更新，这一点和 LRU 不同。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class FifoCacheMissCounter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int requestCount = scanner.nextInt();
        int[] pageRequests = new int[requestCount];
        for (int i = 0; i < requestCount; i++) {
            pageRequests[i] = scanner.nextInt();
        }
        int cacheSize = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int requestCount = 6;
         * int[] pageRequests = {1, 2, 1, 3, 1, 2};
         * int cacheSize = 2;
         */

        FifoCacheMissCounter solver = new FifoCacheMissCounter();
        System.out.println(solver.countMisses(pageRequests, cacheSize));
    }

    /**
     * 这题最容易和 LRU 搞混。
     *
     * FIFO 的意思是：
     *
     * - 谁最早进入缓存
     * - 谁就最早被淘汰
     *
     * 它完全不关心“最近有没有再次访问过”。
     *
     * 所以如果某个页面命中了：
     *
     * - miss 数不变
     * - 它在队列里的先后顺序也不更新
     *
     * 这正是它和 LRU 最大的区别。
     *
     * 这个实现用了两个结构配合：
     *
     * 1. Set<Integer> cache
     *    负责 O(1) 判断“页面在不在缓存里”
     *
     * 2. Queue<Integer> order
     *    负责记录“谁最早进入缓存”
     *
     * 这样当缓存满了又来新页时，
     * 只要从队头弹出最老的页面即可。
     */
    public int countMisses(int[] pageRequests, int maxCacheSize) {
        // 缓存容量是 0，说明任何请求都必 miss。
        if (maxCacheSize <= 0) {
            return pageRequests.length;
        }

        Set<Integer> cache = new HashSet<>();
        Queue<Integer> order = new ArrayDeque<>();
        int misses = 0;
        for (int page : pageRequests) {
            // 命中：FIFO 下顺序不更新，直接跳过。
            if (cache.contains(page)) {
                continue;
            }

            // 走到这里就是 miss。
            misses++;
            if (cache.size() == maxCacheSize) {
                // 缓存已满，淘汰最早进入缓存的那个页面。
                int evict = order.poll();
                cache.remove(evict);
            }

            // 把新页面加入缓存，并记录它进入缓存的先后顺序。
            cache.add(page);
            order.offer(page);
        }
        return misses;
    }
}
