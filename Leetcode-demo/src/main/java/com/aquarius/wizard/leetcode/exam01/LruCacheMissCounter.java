package com.aquarius.wizard.leetcode.exam01;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class LruCacheMissCounter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cacheSize = sc.nextInt();
        int n = sc.nextInt();

        Set<Integer> cache = new LinkedHashSet<>();
        int misses = 0;

        for (int i = 0; i < n; i++) {
            int page = sc.nextInt();

            if (cache.contains(page)) {
                // Move the page to the end, because it becomes the most recently used page.
                cache.remove(page);
            } else {
                // Cache miss: the requested page is not currently in cache.
                misses++;
                if (cache.size() == cacheSize) {
                    // The first element is the least recently used page.
                    Iterator<Integer> it = cache.iterator();
                    it.next();
                    it.remove();
                }
            }
            // Add the current page as the most recently used page.
            cache.add(page);
        }

        System.out.print(misses);
    }
}
