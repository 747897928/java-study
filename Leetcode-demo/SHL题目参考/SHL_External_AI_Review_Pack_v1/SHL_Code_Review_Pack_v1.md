# SHL Code Review Pack

这份文档用于给外部 AI 统一复核当前 `com.aquarius.wizard.leetcode.shl` 目录下的 Java 实现。

## 文件列表

- `AlternateSortedElements.java`
- `AreaOfIntersectionOfTwoCircles.java`
- `BestStartingUserForMaximumReach.java`
- `CaseInsensitiveSubstringOccurrenceCount.java`
- `CircularDinnerMaximumAttendees.java`
- `CountDigitOccurrencesInNumber.java`
- `CountElementsNotCommonToBothLists.java`
- `CountElementsStrictlyLessThanK.java`
- `FifoCacheMissCounter.java`
- `FrequencyDescendingStableSort.java`
- `KthSmallestRelativeStockPrice.java`
- `LargestHouseAreaInGrid.java`
- `LargestPlotBetweenHouses.java`
- `LexicographicallySmallestMaximumDinnerGuestIds.java`
- `LongestPalindromicListByMergingAdjacentValues.java`
- `MaximumCommonFootstepsWithFather.java`
- `MaximumInteriorSignalLength.java`
- `MaximumInternshipSalary.java`
- `MaximumTotalRatingWithHorrorAndSciFiBooks.java`
- `MinimumApplePurchaseCost.java`
- `MinimumJuiceStallStops.java`
- `MinimumPathToVisitAllRetailersInCartesia.java`
- `MinimumProjectsToZeroErrorScores.java`
- `MinimumStraightLineCoverForPickupLocations.java`
- `MinimumStraightLineRoutesFromBaseToPickupLocations.java`
- `MinimumSweetBoxDeliveryTime.java`
- `MissingCharacterDuringTransmission.java`
- `NumberOfWaysToObtainTheLongestConsecutiveOnes.java`
- `OptimalStudentCompletionOrderInLibrary.java`
- `OrganizationReputationUpdater.java`
- `PartialSortWithAscendingPrefixAndDescendingSuffix.java`
- `PerfectSquareBillCount.java`
- `PowerModEncryption.java`
- `PrintAllPrimesFrom2ToN.java`
- `ProductPairsWithPriceDifferenceK.java`
- `RemoveVowelsFromString.java`
- `ReplaceValuesWithTheirIndexPositions.java`
- `RightRotationStringCheck.java`
- `RoadWithMaximumTollRevenue.java`
- `ShlSampleValidator.java`
- `ShortestPathWithUpToKZeroCostSpells.java`
- `StableEvenOddPartition.java`
- `StreetLightStateAfterMDays.java`
- `TotalBusRouteCoverageDistance.java`
- `TravelingSalesmanMaximumWorkingDays.java`

---

## AlternateSortedElements.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * An alternate sort of a list consists of alternate elements (starting from the first position) of
 * the given list after sorting it in an ascending order. You are given a list of unsorted elements.
 * Write an algorithm to find the alternate sort of the given list.
 *
 * Input
 *
 * The first line of the input consists of an integer size, representing the size of the given list
 * (N).
 * The second line consists of N space-separated integers arr[0], arr[1], ..., arr[N-1],
 * representing the elements of the input list.
 *
 * Output
 *
 * Print space-separated integers representing the alternately sorted elements of the given list.
 *
 * Constraints
 *
 * 0 < size <= 10^6
 * -10^6 <= arr[i] <= 10^6
 * 0 <= i < size
 *
 * Example
 *
 * Input:
 * 8
 * 3 5 1 5 9 10 2 6
 *
 * Output:
 * 1 3 5 9
 *
 * Explanation:
 * After sorting, the list is [1, 2, 3, 5, 5, 6, 9, 10].
 * So, the alternate elements of the sorted list are [1, 3, 5, 9].
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：排序后按间隔取值。
 * 校对：题面稳定，示例无歧义。
 * 提示：这题不是交替输出最小最大，而是排序后取下标 0,2,4,... 的元素。
 */
public class AlternateSortedElements {

    public int[] alternateSort(int[] nums) {
        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);
        int[] answer = new int[(sorted.length + 1) / 2];
        for (int i = 0, index = 0; i < sorted.length; i += 2) {
            answer[index++] = sorted[i];
        }
        return answer;
    }
}
```

---

## AreaOfIntersectionOfTwoCircles.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Question
 *
 * A student must solve an entire workbook of problems related to finding the area of intersection
 * of two circles. Because the problems are all very similar, the student decides to write a program
 * that can solve all these similar problems.
 *
 * Input
 *
 * The first line of the input consists of three space-separated integers x1, y1 and r1 where x1
 * and y1 represent the x and y coordinates of the center of the first circle and r1 represents the
 * radius of the first circle.
 * The second line of the input consists of three space-separated integers x2, y2 and r2 where x2
 * and y2 represent the x and y coordinates of the center of the second circle and r2 represents
 * the radius of the second circle.
 *
 * Output
 *
 * Print a real number representing the area of intersection of two circles rounded up to 6 decimal
 * places.
 *
 * Constraints
 *
 * 0 < r1, r2 < 10^4
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：几何分类讨论、圆相交面积公式。
 * 校对：当前题库没有样例，所以实现时只能依赖标准公式；“rounded up”更像平台常见的“保留 6 位小数”，这里按 HALF_UP 处理。
 * 提示：要先分清三类情况：相离、包含、普通相交。
 */
public class AreaOfIntersectionOfTwoCircles {

    public double intersectionArea(double x1, double y1, double r1, double x2, double y2, double r2) {
        double distance = Math.hypot(x1 - x2, y1 - y2);
        if (distance >= r1 + r2) {
            return 0.0;
        }
        if (distance <= Math.abs(r1 - r2)) {
            double radius = Math.min(r1, r2);
            return Math.PI * radius * radius;
        }

        double cos1 = clamp((distance * distance + r1 * r1 - r2 * r2) / (2.0 * distance * r1));
        double cos2 = clamp((distance * distance + r2 * r2 - r1 * r1) / (2.0 * distance * r2));
        double angle1 = 2.0 * Math.acos(cos1);
        double angle2 = 2.0 * Math.acos(cos2);

        double area1 = 0.5 * r1 * r1 * (angle1 - Math.sin(angle1));
        double area2 = 0.5 * r2 * r2 * (angle2 - Math.sin(angle2));
        return area1 + area2;
    }

    public String formatArea(double area) {
        return BigDecimal.valueOf(area).setScale(6, RoundingMode.HALF_UP).toPlainString();
    }

    private double clamp(double value) {
        return Math.max(-1.0, Math.min(1.0, value));
    }
}
```

---

## BestStartingUserForMaximumReach.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Question
 *
 * On a social networking site, each user can have a group of friends. Each user possesses a unique
 * profile ID. A company wants to promote its product on the social networking site in a particular
 * way. It plans to give rewards to any user who promotes its product on his/her wall. The company
 * will give extra reward points to users who refer other users. The company will ask one of the
 * users to promote its product by posting the product message on his/her wall. The user can then
 * share this message with their friends, asking them to post on their walls as well.
 *
 * The company will share the promo message with the user in such a way that the promo message is
 * posted on the maximum number of walls.
 *
 * Write an algorithm to help the company find the userID of the user to whom they should send the
 * promo request so that the request may reach the maximum number of walls.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers users and pairs,
 * representing the number of users and the number of pairs of friends on the social networking
 * site, respectively.
 * The next pairs lines consist of two space-separated integers user1 and user2, representing the
 * profile ID of users such that user2 is a friend of user1.
 *
 * Output
 *
 * Print an integer representing the userID of the user to whom they should send the promo request
 * so that the request may reach the maximum number of walls.
 *
 * Constraints
 *
 * 1 < users <= 1000
 * 1 < pairs < 10000
 * 0 <= user1, user2 < users
 *
 * Note
 *
 * If user2 is a friend of user1, then it is not necessary that user1 is also a friend of user2.
 * A user cannot share the product message with his/her friend if the friend has already received
 * the product message.
 * If multiple users can reach the same maximum number of walls, print the smaller userID.
 *
 * Example
 *
 * Input:
 * 5 4
 * 0 1
 * 3 4
 * 1 2
 * 2 1
 *
 * Output:
 * 0
 *
 * Explanation:
 * To get the optimal result, the company will share the product message with the user with profile
 * ID 0.
 * The order in which the message is posted by users is given as follows:
 * 0 -> 1 -> 2.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：有向图可达性、从每个起点做 BFS/DFS。
 * 校对：原题中的编号范围和样例冲突，这里按样例统一为 0..users-1，并把并列时取更小 userID 的规则写明。
 * 提示：users 只有 1000，直接从每个点跑一次搜索就够了，不必先上 SCC 压缩。
 */
public class BestStartingUserForMaximumReach {

    public int bestUserId(int users, int[][] friendships) {
        List<Integer>[] graph = buildGraph(users, friendships);
        int bestUserId = 0;
        int bestReach = -1;
        for (int start = 0; start < users; start++) {
            int reach = bfsReachCount(graph, start);
            if (reach > bestReach || (reach == bestReach && start < bestUserId)) {
                bestReach = reach;
                bestUserId = start;
            }
        }
        return bestUserId;
    }

    private List<Integer>[] buildGraph(int users, int[][] friendships) {
        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new List[users];
        for (int i = 0; i < users; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : friendships) {
            graph[edge[0]].add(edge[1]);
        }
        return graph;
    }

    private int bfsReachCount(List<Integer>[] graph, int start) {
        boolean[] visited = new boolean[graph.length];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.addLast(start);
        visited[start] = true;
        int count = 1;
        while (!queue.isEmpty()) {
            int current = queue.removeFirst();
            for (int next : graph[current]) {
                if (visited[next]) {
                    continue;
                }
                visited[next] = true;
                queue.addLast(next);
                count++;
            }
        }
        return count;
    }
}
```

---

## CaseInsensitiveSubstringOccurrenceCount.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * You are given two strings containing only English letters. Write an algorithm to count the
 * number of occurrences of the second string in the first string. (You may disregard the case of
 * the letters.)
 *
 * Input
 *
 * The first line of the input consists of a string parent, representing the first string.
 * The second line consists of a string sub, representing the second string.
 *
 * Output
 *
 * Print an integer representing the number of occurrences of sub in parent. If no occurrence of
 * sub is found in parent, then print 0.
 *
 * Example
 *
 * Input:
 * TimisplayinginthehouseofTimwiththetoysofTim
 * Tim
 *
 * Output:
 * 3
 *
 * Explanation:
 * Tim occurs 3 times in the first string.
 * So, the output is 3.
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：字符串匹配、大小写归一化。
 * 校对：样例已做代码校验。
 * 当前实现按所有起点计数，允许重叠匹配；这也是我认为最稳妥的题意理解。
 */
public class CaseInsensitiveSubstringOccurrenceCount {

    public int countOccurrences(String parent, String sub) {
        String source = parent.toLowerCase();
        String target = sub.toLowerCase();
        int count = 0;
        for (int i = 0; i + target.length() <= source.length(); i++) {
            if (source.startsWith(target, i)) {
                count++;
            }
        }
        return count;
    }
}
```

---

## CircularDinnerMaximumAttendees.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * Question
 *
 * A University has invited N alumni to a dinner. The dinner table is circular in shape. The
 * university has assigned each alumnus an invitation ID from 1 to N. Each alumnus likes exactly
 * one fellow alumnus and will attend the dinner only if he/she can be seated next to that person.
 *
 * You are asked to plan the seating arrangement. Write an algorithm to find the maximum number of
 * alumni who will attend the dinner.
 *
 * Input
 *
 * The first line of the input consists of an integer - personId_size, representing the number of
 * alumni (N).
 * The second line consists of N space-separated integers representing the ID of the person whom the
 * i-th alumnus likes.
 *
 * Output
 *
 * Print an integer representing the maximum number of alumni who can attend the dinner.
 *
 * Note
 *
 * One alumnus can be liked by more than one alumni.
 *
 * Example
 *
 * Input:
 * 4
 * 2 3 4 1
 *
 * Output:
 * 4
 *
 * Explanation:
 * 1st alumnus likes the person with ID 2
 * 2nd likes the person with ID 3
 * 3rd likes the person with ID 4
 * 4th likes the person with ID 1
 * A maximum of 4 alumni can be seated around the circular table in the following manner: 1-2-3-4
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：函数图、环检测、链长统计。
 * 校对：样例已做代码校验。
 * 本题与 LeetCode 2127 同型：答案是“最长大环”和“所有二元环加两侧最长链”的较大者。
 */
public class CircularDinnerMaximumAttendees {

    public int maxAttendees(int[] likesOneBased) {
        int n = likesOneBased.length;
        int[] likes = new int[n];
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) {
            likes[i] = likesOneBased[i] - 1;
            indegree[likes[i]]++;
        }

        int[] longestChain = new int[n];
        Arrays.fill(longestChain, 1);
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            int next = likes[node];
            longestChain[next] = Math.max(longestChain[next], longestChain[node] + 1);
            if (--indegree[next] == 0) {
                queue.offer(next);
            }
        }

        int maxCycle = 0;
        int pairContribution = 0;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (indegree[i] <= 0 || visited[i]) {
                continue;
            }
            int current = i;
            List<Integer> cycle = new ArrayList<>();
            while (!visited[current]) {
                visited[current] = true;
                cycle.add(current);
                current = likes[current];
            }
            if (cycle.size() == 2) {
                int a = cycle.get(0);
                int b = cycle.get(1);
                pairContribution += longestChain[a] + longestChain[b];
            } else {
                maxCycle = Math.max(maxCycle, cycle.size());
            }
        }
        return Math.max(maxCycle, pairContribution);
    }
}
```

---

## CountDigitOccurrencesInNumber.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Write an algorithm to find the number of occurrences of needle in a given positive number
 * haystack.
 *
 * Input
 *
 * The first line of the input consists of an integer needle, representing a digit.
 * The second line consists of an integer haystack, representing the positive number.
 *
 * Output
 *
 * Print an integer representing the number of occurrences of needle in haystack.
 *
 * Constraints
 *
 * 0 <= needle <= 9
 * 0 <= haystack <= 99999999
 *
 * Example
 *
 * Input:
 * 2
 * 123228
 *
 * Output:
 * 3
 *
 * Explanation:
 * needle 2 occurs 3 times in the haystack.
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：按位遍历。
 * 校对：题面稳定。
 * 提示：别漏掉 haystack = 0 的特殊情况。
 */
public class CountDigitOccurrencesInNumber {

    public int countOccurrences(int digit, int number) {
        if (number == 0) {
            return digit == 0 ? 1 : 0;
        }
        int count = 0;
        int value = number;
        while (value > 0) {
            if (value % 10 == digit) {
                count++;
            }
            value /= 10;
        }
        return count;
    }
}
```

---

## CountElementsNotCommonToBothLists.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.HashSet;
import java.util.Set;

/**
 * Question
 *
 * You are given two lists of different lengths of positive integers. Write an algorithm to count
 * the number of elements that are not common to each list.
 *
 * Input
 *
 * The first line of the input consists of an integer listInput1_size, an integer representing the
 * number of elements in the first list (N).
 * The second line consists of N space-separated integers representing the first list of positive
 * integers.
 * The third line consists of an integer listInput2_size, representing the number of elements in
 * the second list (M).
 * The last line consists of M space-separated integers representing the second list of positive
 * integers.
 *
 * Output
 *
 * Print a positive integer representing the count of elements that are not common to both the lists
 * of integers.
 *
 * Example
 *
 * Input:
 * 11
 * 1 1 2 3 4 5 5 7 6 9 10
 * 10
 * 11 12 13 4 5 6 7 18 19 20
 *
 * Output:
 * 12
 *
 * Explanation:
 * The numbers that are not common to both lists are [1, 1, 2, 3, 9, 10, 11, 12, 13, 18, 19, 20].
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：哈希集合、成员判断。
 * 校对：样例能证明这题并不是 multiset difference。只要某个值在另一边出现过，那么这个值在本边的所有出现都不算“不公共”。
 * 提示：更准确的理解是“统计两边那些值不在对方值集合中的元素个数”。
 */
public class CountElementsNotCommonToBothLists {

    public int countNotCommon(int[] first, int[] second) {
        Set<Integer> firstValues = new HashSet<>();
        Set<Integer> secondValues = new HashSet<>();
        for (int value : first) {
            firstValues.add(value);
        }
        for (int value : second) {
            secondValues.add(value);
        }
        int count = 0;
        for (int value : first) {
            if (!secondValues.contains(value)) {
                count++;
            }
        }
        for (int value : second) {
            if (!firstValues.contains(value)) {
                count++;
            }
        }
        return count;
    }
}
```

---

## CountElementsStrictlyLessThanK.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * You are given a list of integers and an integer K. Write an algorithm to find the number of
 * elements in the list that are strictly less than K.
 *
 * Input
 *
 * The first line of the input consists of an integer element_size, representing the number of
 * elements in the list (N).
 * The second line consists of N space-separated integers element[1], element[2], ..., element[N],
 * representing the list of integers.
 * The last line consists of an integer num, representing the integer to be compared (K).
 *
 * Output
 *
 * Print a positive integer representing the number of elements in the list that are strictly less
 * than num.
 *
 * Constraints
 *
 * -10^9 <= num <= 10^9
 * -10^9 <= element[i] <= 10^9
 *
 * Example
 *
 * Input:
 * 7
 * 1 7 4 5 6 3 2
 * 5
 *
 * Output:
 * 4
 *
 * Explanation:
 * The numbers that are less than 5 are 1, 2, 3, 4.
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：线性扫描、基础计数。
 * 校对：题面稳定。
 * 提示：这是最适合练输入输出和边界值的题之一。
 */
public class CountElementsStrictlyLessThanK {

    public int countLessThan(int[] nums, int target) {
        int count = 0;
        for (int num : nums) {
            if (num < target) {
                count++;
            }
        }
        return count;
    }
}
```

---

## FifoCacheMissCounter.java

```java
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
 * 备注
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
```

---

## FrequencyDescendingStableSort.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Question
 *
 * Design a way to sort the list of positive integers in the descending order according to frequency
 * of the elements. The elements with higher frequency come before those with lower frequency.
 * Elements with the same frequency come in the same order as they appear in the given list.
 *
 * Input
 *
 * The first line of the input consists of an integer num, representing the number of elements in
 * the list (N).
 * The second line consists of N space-separated integers representing the elements in the list.
 *
 * Output
 *
 * Print N space-separated integers representing the elements of the list sorted according to the
 * frequency of elements present in the given list.
 *
 * Example
 *
 * Input:
 * 19
 * 1 2 2 3 3 3 4 4 5 5 5 5 6 6 6 7 8 9 10
 *
 * Output:
 * 5 5 5 5 3 3 3 6 6 6 2 2 4 4 1 7 8 9 10
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：频次统计、稳定排序。
 * 校对：题意稳定。
 * 提示：同频元素保持原始出现顺序，不能再按数值大小排。
 */
public class FrequencyDescendingStableSort {

    public int[] sortByFrequency(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        Map<Integer, Integer> firstIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
            firstIndex.putIfAbsent(nums[i], i);
        }

        List<Integer> values = new ArrayList<>(count.keySet());
        values.sort(Comparator
                .<Integer>comparingInt(count::get).reversed()
                .thenComparingInt(firstIndex::get));

        int[] result = new int[nums.length];
        int index = 0;
        for (int value : values) {
            for (int i = 0; i < count.get(value); i++) {
                result[index++] = value;
            }
        }
        return result;
    }
}
```

---

## KthSmallestRelativeStockPrice.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * Andrew is a stock trader who trades in N selected stocks. He has calculated the relative stock
 * price changes in the N stocks from the previous day stock prices. Now, his lucky number is K, so
 * he wishes to invest in the particular stock that has the Kth smallest relative stock value.
 *
 * Write an algorithm for Andrew to find the Kth smallest stock price out of the selected N stocks.
 *
 * Input
 *
 * The first line of the input consists of an integer stock_size, representing the number of
 * selected stocks (N).
 * The second line consists of N space-separated integers stock[0], stock[1], ..., stock[N-1],
 * representing the relative stock prices of the selected stocks.
 * The third line consists of an integer valueK, representing the value K for which he wishes to
 * find the stock price.
 *
 * Output
 *
 * Print an integer representing the Kth smallest stock price of selected N stocks.
 *
 * Constraints
 *
 * 0 < valueK <= stock_size <= 10^6
 * 0 <= stock[i] <= 10^6
 * 0 <= i < stock_size
 *
 * Example
 *
 * Input:
 * 5
 * 10 5 7 88 19
 * 3
 *
 * Output:
 * 10
 *
 * Explanation:
 * The sorted relative stock prices are [5, 7, 10, 19, 88].
 * So, the 3rd smallest stock price is 10.
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：排序、选择算法。
 * 校对：题面稳定。
 * 提示：学习阶段先写排序版最直观，后面再补 quickselect 也不迟。
 */
public class KthSmallestRelativeStockPrice {

    public int kthSmallest(int[] stockPrices, int k) {
        int[] sorted = Arrays.copyOf(stockPrices, stockPrices.length);
        Arrays.sort(sorted);
        return sorted[k - 1];
    }
}
```

---

## LargestHouseAreaInGrid.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * The city authorities conduct a study of the houses in a residential area for a city planning
 * scheme. The area is depicted in an aerial view and divided into an N x M grid. If a grid cell
 * contains some part of a house roof, then it is assigned the value 1; otherwise, the cell
 * represents a vacant plot and is assigned the value 0. Clusters of adjacent grid cells with value
 * 1 represent a single house. Diagonally placed grids with value 1 do not represent a single
 * house. The area of a house is the number of 1s that it spans.
 *
 * Write an algorithm to find the area of the largest house.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers - rows and cols representing
 * the number of rows (N) and the number of columns in the grid (M), respectively.
 * The next N lines consist of M space-separated integers representing the grid.
 *
 * Output
 *
 * Print an integer representing the area of the largest house.
 *
 * Example
 *
 * Input:
 * 5 5
 * 0 0 0 0 0
 * 0 1 1 0 0
 * 0 0 0 0 0
 * 0 0 1 1 0
 * 0 0 1 0 0
 *
 * Output:
 * 3
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：网格连通块、DFS/BFS。
 * 校对：题面稳定。
 * 提示：只算四连通，不算对角线。
 */
public class LargestHouseAreaInGrid {

    private static final int[][] DIRECTIONS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public int largestArea(int[][] grid) {
        int rows = grid.length;
        int cols = rows == 0 ? 0 : grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int best = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 1 && !visited[row][col]) {
                    best = Math.max(best, dfs(grid, visited, row, col));
                }
            }
        }
        return best;
    }

    private int dfs(int[][] grid, boolean[][] visited, int row, int col) {
        visited[row][col] = true;
        int area = 1;
        for (int[] direction : DIRECTIONS) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];
            if (nextRow < 0 || nextRow >= grid.length || nextCol < 0 || nextCol >= grid[0].length) {
                continue;
            }
            if (grid[nextRow][nextCol] == 1 && !visited[nextRow][nextCol]) {
                area += dfs(grid, visited, nextRow, nextCol);
            }
        }
        return area;
    }
}
```

---

## LargestPlotBetweenHouses.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * In a city there are N houses. Noddy is looking for a plot of land in the city on which to build
 * his house. He wants to buy the largest plot of land that will allow him to build the largest
 * possible house. All the houses in the city lie in a straight line and all of them have a house
 * number and a second number indicating the position of the house from the entry point in the city.
 * Noddy wants to find the houses between which he can build the largest possible house.
 *
 * Write an algorithm to help Noddy find the house numbers between which he can build his largest
 * possible house.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers - num and val, representing
 * the number of houses (N) and the value val (where val is always equal to 2 representing the house
 * number (H) and the position of houses (P) for N houses).
 * The next N lines consist of two space-separated integers representing the house number (H_i) and
 * the position (P_i), respectively.
 *
 * Output
 *
 * Print two space-separated integers representing the house numbers in ascending order between which
 * the largest plot is available.
 *
 * Note
 *
 * No two houses have the same position. In the case of multiple possibilities, print the one with
 * the least distance from the reference point.
 *
 * Example
 *
 * Input:
 * 5 2
 * 3 7
 * 1 9
 * 2 0
 * 5 15
 * 4 30
 *
 * Output:
 * 4 5
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：按位置排序、相邻差值最大化。
 * 校对：题面稳定。
 * 提示：若间隔并列，优先选更靠近入口的那一对。
 */
public class LargestPlotBetweenHouses {

    public int[] findHouseNumbers(int[][] houses) {
        int[][] sorted = Arrays.copyOf(houses, houses.length);
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));

        int bestGap = -1;
        int[] bestPair = new int[2];
        int bestLeftPosition = Integer.MAX_VALUE;
        for (int i = 1; i < sorted.length; i++) {
            int gap = sorted[i][1] - sorted[i - 1][1];
            int leftPosition = sorted[i - 1][1];
            if (gap > bestGap || (gap == bestGap && leftPosition < bestLeftPosition)) {
                bestGap = gap;
                bestLeftPosition = leftPosition;
                bestPair[0] = Math.min(sorted[i - 1][0], sorted[i][0]);
                bestPair[1] = Math.max(sorted[i - 1][0], sorted[i][0]);
            }
        }
        return bestPair;
    }
}
```

---

## LexicographicallySmallestMaximumDinnerGuestIds.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * Question
 *
 * A University has invited N alumni for a dinner. The dinner table has a circular shape. Each
 * alumnus is assigned an invitation ID from 1 to N. Each alumnus likes exactly one fellow alumnus
 * and will attend the dinner only if he/she can be seated next to the person he/she likes.
 *
 * Write an algorithm to find the IDs of the alumni in a lexicographical order so that maximum
 * number of alumni attend the dinner. If more than one such seating arrangement exists, then output
 * the one that is lexicographically smaller.
 *
 * Input
 *
 * The first line of the input consists of an integer num, representing the number of alumni (N).
 * The second line consists of N space-separated integers, alumniID[0], alumniID[1], ...
 * alumniID[N-1] representing the ID of the person whom the ith alumnus likes.
 *
 * Output
 *
 * Print space-separated integers representing the IDs of the alumni who will attend the dinner.
 *
 * Note
 *
 * One alumnus can be liked by multiple alumni.
 *
 * Constraints
 *
 * 1 <= num <= 10^5
 * 0 <= i < num
 *
 * Example
 *
 * Input:
 * 4
 * 2 3 4 1
 *
 * Output:
 * 1 2 3 4
 *
 * Explanation:
 * The first alumnus likes the person whose ID is 2.
 * The second alumnus likes the person whose ID is 3.
 * The third alumnus likes the person whose ID is 4.
 * The fourth alumnus likes the person whose ID is 1.
 * A maximum of 4 alumni can be seated around the circular table in the following manner: 1-2-3-4.
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：函数图、环检测、链长 DP、字典序集合比较。
 * 校对：这是 A10 的恢复版。当前实现沿用了同型问题（类似 LeetCode 2127）的标准结论，并输出“字典序最小的参会 ID 集合（升序）”。
 * 提示：这里我默认题目要输出的是“参会人员 ID 的升序列表”，不是具体环上的座位顺序。
 */
public class LexicographicallySmallestMaximumDinnerGuestIds {

    public int[] findGuestIds(int[] likesOneBased) {
        int n = likesOneBased.length;
        int[] likes = new int[n];
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) {
            likes[i] = likesOneBased[i] - 1;
            indegree[likes[i]]++;
        }

        int[] bestLength = new int[n];
        int[] bestPrev = new int[n];
        int[] bestRemainderMin = new int[n];
        Arrays.fill(bestLength, 1);
        Arrays.fill(bestPrev, -1);
        for (int i = 0; i < n; i++) {
            bestRemainderMin[i] = i + 1;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        int[] workingIndegree = Arrays.copyOf(indegree, n);
        for (int i = 0; i < n; i++) {
            if (workingIndegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            int next = likes[node];
            int candidateLength = bestLength[node] + 1;
            int candidateRemainderMin = bestRemainderMin[node];
            if (candidateLength > bestLength[next]
                    || (candidateLength == bestLength[next] && candidateRemainderMin < bestRemainderMin[next])) {
                bestLength[next] = candidateLength;
                bestPrev[next] = node;
                bestRemainderMin[next] = candidateRemainderMin;
            }
            if (--workingIndegree[next] == 0) {
                queue.offer(next);
            }
        }

        boolean[] cycleNode = new boolean[n];
        for (int i = 0; i < n; i++) {
            cycleNode[i] = workingIndegree[i] > 0;
        }

        List<Integer> bestCycleSet = new ArrayList<>();
        boolean[] visitedCycle = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!cycleNode[i] || visitedCycle[i]) {
                continue;
            }
            List<Integer> cycle = new ArrayList<>();
            int current = i;
            while (!visitedCycle[current]) {
                visitedCycle[current] = true;
                cycle.add(current + 1);
                current = likes[current];
            }
            cycle.sort(Integer::compareTo);
            if (cycle.size() > 1
                    && (cycle.size() > bestCycleSet.size() || (cycle.size() == bestCycleSet.size() && lexLess(cycle, bestCycleSet)))) {
                bestCycleSet = cycle;
            }
        }

        boolean[] pairVisited = new boolean[n];
        List<Integer> pairUnion = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int liked = likes[i];
            if (liked == i || likes[liked] != i || pairVisited[i] || pairVisited[liked]) {
                continue;
            }
            pairVisited[i] = true;
            pairVisited[liked] = true;
            addChainNodes(i, bestPrev, pairUnion);
            addChainNodes(liked, bestPrev, pairUnion);
        }
        pairUnion.sort(Integer::compareTo);

        if (pairUnion.size() > bestCycleSet.size()) {
            return toArray(pairUnion);
        }
        if (pairUnion.size() < bestCycleSet.size()) {
            return toArray(bestCycleSet);
        }
        return toArray(lexLess(pairUnion, bestCycleSet) ? pairUnion : bestCycleSet);
    }

    private void addChainNodes(int root, int[] bestPrev, List<Integer> target) {
        int current = root;
        while (current != -1) {
            target.add(current + 1);
            current = bestPrev[current];
        }
    }

    private boolean lexLess(List<Integer> first, List<Integer> second) {
        if (second.isEmpty()) {
            return true;
        }
        for (int i = 0; i < Math.min(first.size(), second.size()); i++) {
            int left = first.get(i);
            int right = second.get(i);
            if (left != right) {
                return left < right;
            }
        }
        return first.size() < second.size();
    }

    private int[] toArray(List<Integer> values) {
        int[] result = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }
        return result;
    }
}
```

---

## LongestPalindromicListByMergingAdjacentValues.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.List;

/**
 * Question
 *
 * The assistant sales manager in the head office of a company 'Jotuway' receives the list of sales
 * data from the offices of the company in different cities. The assistant sales manager has to
 * compile the data and share the list with the sales manager. The shared list should be the
 * longest palindromic list of the sales data of different cities. He/she can sum up any two
 * consecutive elements of the list to form a single element. The result thus obtained can be reused
 * further and this process can be repeated any number of times to convert the given list into a
 * palindromic of maximum length.
 *
 * Write an algorithm to help the assistant sales manager convert the given list into a palindromic
 * list of maximum length.
 *
 * Input
 *
 * The first line of the input consists of an integer N, representing the number of elements in the
 * list.
 * The second line consists of N space-separated positive integers representing the sales data.
 *
 * Output
 *
 * Print space-separated positive integers representing the palindromic list of maximum length.
 *
 * Constraints
 *
 * 0 <= N <= 10^3
 * 1 <= S <= 10^6; where S represents the sales data from a city
 *
 * Example
 *
 * Input:
 * 6
 * 15 10 15 34 25 15
 *
 * Output:
 * 15 25 34 25 15
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：双指针、贪心合并、回文构造。
 * 校对：题面完整，样例稳定。
 * 提示：目标是让“合并次数最少”，因为每合并一次，最终列表长度就减少 1。
 */
public class LongestPalindromicListByMergingAdjacentValues {

    public long[] longestPalindromicList(int[] salesData) {
        List<Long> values = new ArrayList<>(salesData.length);
        for (int value : salesData) {
            values.add((long) value);
        }
        int left = 0;
        int right = values.size() - 1;
        while (left < right) {
            long leftValue = values.get(left);
            long rightValue = values.get(right);
            if (leftValue == rightValue) {
                left++;
                right--;
                continue;
            }
            if (leftValue < rightValue) {
                values.set(left + 1, leftValue + values.get(left + 1));
                values.remove(left);
                right--;
            } else {
                values.set(right - 1, values.get(right - 1) + rightValue);
                values.remove(right);
                right--;
            }
        }

        long[] answer = new long[values.size()];
        for (int i = 0; i < values.size(); i++) {
            answer[i] = values.get(i);
        }
        return answer;
    }
}
```

---

## MaximumCommonFootstepsWithFather.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * [The top part of the screenshot is missing.]
 * Visible fragment:
 * ... constant speed of V1 meters per step for N steps.
 *
 * Martin is standing at X2 meters away from his home. He wonders how fast he must run at some
 * constant speed of V2 meters per step so as to maximize F, where F equals the number of his
 * father's footsteps that Martin will land on during his run. It is given that the first step that
 * Martin will land on, from his starting position, will have been landed on by his father.
 *
 * Note that if more than one prospective velocity results in the same number of maximum common
 * steps, output the highest prospective velocity as V2.
 *
 * Write an algorithm to help Martin calculate F and V2.
 *
 * Input
 *
 * The first line of the input consists of an integer fatherPos, representing the initial position
 * of Martin's father (X1).
 * The second line consists of an integer martinPos, representing the initial position of Martin
 * (X2).
 * The third line consists of an integer velFather, representing the velocity of the father (V1).
 * The last line consists of an integer steps, representing the number of steps taken by the father
 * (N).
 *
 * Output
 *
 * Print two space-separated integers as the maximum number of common footsteps F and the
 * corresponding speed V2.
 *
 * Constraints
 *
 * [The screenshot for constraints is missing.]
 *
 * Example
 *
 * [The screenshot for the example is missing.]
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：等差数列交集、数论、gcd。
 * 校对：题面缺得比较多，所以这题的代码是基于可见英文重新推导的。我把父亲的落脚点看成有限等差数列，把 Martin 的落脚点看成从第一步开始的无限等差数列。
 * 提示：先枚举 Martin 第一步对应父亲的第 t 个脚印，此时 V2 就唯一确定；之后公共脚印会按固定步长继续出现。
 */
public class MaximumCommonFootstepsWithFather {

    public long[] maximizeCommonSteps(long fatherPos, long martinPos, long fatherVelocity, long steps) {
        long delta = fatherPos - martinPos;
        long bestCommon = 0L;
        long bestVelocity = -1L;
        for (long fatherStepIndex = 1; fatherStepIndex <= steps; fatherStepIndex++) {
            long martinVelocity = delta + fatherStepIndex * fatherVelocity;
            if (martinVelocity <= 0) {
                continue;
            }
            long gcd = gcd(fatherVelocity, martinVelocity);
            long strideOnFatherSteps = martinVelocity / gcd;
            long common = 1L + (steps - fatherStepIndex) / strideOnFatherSteps;
            if (common > bestCommon || (common == bestCommon && martinVelocity > bestVelocity)) {
                bestCommon = common;
                bestVelocity = martinVelocity;
            }
        }
        return new long[]{bestCommon, bestVelocity};
    }

    private long gcd(long a, long b) {
        long x = Math.abs(a);
        long y = Math.abs(b);
        while (y != 0) {
            long next = x % y;
            x = y;
            y = next;
        }
        return x;
    }
}
```

---

## MaximumInteriorSignalLength.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * A digital machine generates binary data which consists of a string of 0s and 1s. A maximum
 * signal M, in the data consists of the maximum number of either 1s or 0s appearing consecutively
 * in the data but M can't be at the beginning or end of the string.
 *
 * Design a way to find the length of the maximum signal.
 *
 * Input
 *
 * The first line of the input consists of an integer N, representing the length of the binary
 * string.
 * The second line consists of a string of length N consisting of 0s and 1s only.
 *
 * Output
 *
 * Print an integer representing the length of the maximum signal.
 *
 * Example 1
 *
 * Input:
 * 6
 * 101000
 *
 * Output:
 * 1
 *
 * Example 2
 *
 * Input:
 * 9
 * 101111110
 *
 * Output:
 * 6
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：连续段统计。
 * 校对：题面稳定。
 * 提示：只有完全处于字符串内部的连续段才算，所以前缀段和后缀段都不能直接计入答案。
 */
public class MaximumInteriorSignalLength {

    public int maximumLength(String signal) {
        int best = 0;
        int left = 0;
        while (left < signal.length()) {
            int right = left;
            while (right < signal.length() && signal.charAt(right) == signal.charAt(left)) {
                right++;
            }
            if (left > 0 && right < signal.length()) {
                best = Math.max(best, right - left);
            }
            left = right;
        }
        return best;
    }
}
```

---

## MaximumInternshipSalary.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Stephen is doing an internship in a company for N days. Each day, he may choose an easy task or
 * a difficult task. He may also choose to perform no task at all. He chooses a difficult task on
 * days when and only when he did not perform any work the previous day. The amounts paid by the
 * company for both the easy and difficult tasks can vary each day, but the company always pays more
 * for difficult tasks.
 *
 * Write an algorithm to help Stephen earn the maximum salary.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers: num and type, representing
 * the number of days of the internship (N) and types of tasks available for each day (M is always
 * equal to 2, respectively).
 * The next N lines consist of M space-separated integers: easy and hard, representing the amount
 * set to be paid for the easy task and the difficult task on that day, respectively.
 *
 * Output
 *
 * Print an integer representing the maximum salary that Stephen can earn.
 *
 * Constraints
 *
 * 1 <= num <= 10^5
 * type = 2
 * 2 <= hard <= 10^4
 * 1 <= easy < hard
 *
 * Example
 *
 * Input:
 * 4 2
 * 1 2
 * 4 10
 * 20 21
 * 2 23
 *
 * Output:
 * 33
 *
 * Explanation:
 * To earn the maximum salary, select the difficult task (10) from the 2nd row and the difficult
 * task (23) from the 4th row. The maximum salary earned = 10 + 23 = 33.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：状态 DP。
 * 校对：样例已做代码校验。
 * 思路：区分“今天休息 / 今天做简单任务 / 今天做困难任务”三种状态，困难任务只能从“昨天休息”转移过来。
 */
public class MaximumInternshipSalary {

    public long maxSalary(int[][] payByDay) {
        long prevIdle = 0L;
        long prevEasy = Long.MIN_VALUE / 4;
        long prevHard = Long.MIN_VALUE / 4;
        for (int[] day : payByDay) {
            long nextIdle = Math.max(prevIdle, Math.max(prevEasy, prevHard));
            long nextEasy = Math.max(prevIdle, prevEasy) + day[0];
            long nextHard = prevIdle + day[1];
            prevIdle = nextIdle;
            prevEasy = nextEasy;
            prevHard = nextHard;
        }
        return Math.max(prevIdle, Math.max(prevEasy, prevHard));
    }
}
```

---

## MaximumTotalRatingWithHorrorAndSciFiBooks.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * Sheldon is going to a book fair where all the books are star-rated. As he is interested in just
 * two types of books, Horror and Sci-fi, so he would buy the books from these two categories only.
 * He would want to buy at least one book from each category so as to maximize the total star-rating
 * of his books. Also, the total price of the books should not exceed the amount of money that he
 * can spend. The output is -1 if it is not possible to buy at least one book from both the
 * categories with the money that he has.
 *
 * Write an algorithm to help Sheldon buy the books from both the categories.
 *
 * Input
 *
 * The first line of the input consists of an integer amount, representing the amount of money
 * Sheldon can spend.
 * The second line consists of two integers numHorror and numH, representing the number of Horror
 * books (H) and the number of values given for every horror book (X is always equal to 2,
 * respectively).
 * The next H lines consist of X space-separated integers hrating and hprice, representing the
 * star-rating and the price of each Horror book, respectively.
 * The next line consists of two space-separated integers numSciFi and numS, representing the number
 * of Sci-fi books (S) and the number of values given for every Sci-fi book (P is always equal to 2,
 * respectively).
 * The last S lines consist of P space-separated integers srating and sprice, representing the
 * star-rating and the price of each Sci-fi book, respectively.
 *
 * Output
 *
 * Print an integer representing the total maximum star-rating of books bought by Sheldon. If he
 * cannot buy at least one book from both the categories then print -1.
 *
 * Constraints
 *
 * 1 <= numHorror <= 1000
 * 1 <= numSciFi <= 1000
 * 1 <= amount <= 10^5
 * 1 <= hrating, srating <= 10^6
 * 1 <= hprice, sprice <= 10^5
 * numH = 2
 * numS = 2
 *
 * Visible example input from docx:
 * 50
 * 3 2
 * 5 10
 * 3 30
 * 6 20
 * 3 2
 * 6 30
 * 2 10
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：0/1 背包、状态压缩、预算约束下的最优选择。
 * 校对：docx 样例在末尾被截断，输出和最后若干行缺失；但核心题意足够稳定，可以按“每本书最多买一次”来建模。
 * 提示：关键不只是花费不超预算，还要保证 Horror 和 Sci-fi 两类都至少选到一本。
 */
public class MaximumTotalRatingWithHorrorAndSciFiBooks {

    private static final long NEGATIVE_INF = Long.MIN_VALUE / 4;

    public long maximumRating(int amount, int[][] horrorBooks, int[][] sciFiBooks) {
        long[][] dp = new long[4][amount + 1];
        for (long[] row : dp) {
            Arrays.fill(row, NEGATIVE_INF);
        }
        dp[0][0] = 0L;
        addBooks(dp, horrorBooks, 1);
        addBooks(dp, sciFiBooks, 2);

        long best = NEGATIVE_INF;
        for (int money = 0; money <= amount; money++) {
            best = Math.max(best, dp[3][money]);
        }
        return best == NEGATIVE_INF ? -1L : best;
    }

    private void addBooks(long[][] dp, int[][] books, int categoryMask) {
        for (int[] book : books) {
            long rating = book[0];
            int price = book[1];
            for (int mask = 3; mask >= 0; mask--) {
                for (int money = dp[mask].length - 1; money >= price; money--) {
                    long previous = dp[mask][money - price];
                    if (previous == NEGATIVE_INF) {
                        continue;
                    }
                    int nextMask = mask | categoryMask;
                    dp[nextMask][money] = Math.max(dp[nextMask][money], previous + rating);
                }
            }
        }
    }
}
```

---

## MinimumApplePurchaseCost.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Josh wants to buy exactly N apples.
 * Shop A sells apples only in lots of M1 apples, and each such lot costs P1.
 * Shop B sells apples only in lots of M2 apples, and each such lot costs P2.
 *
 * Write an algorithm to find the minimum price at which Josh can buy the apples.
 *
 * Input
 *
 * The first line of input consists of an integer - N, representing the total number of apples that
 * Josh wants to buy.
 * The second line consists of two space-separated positive integers - M1 and P1, representing the
 * number of apples in a lot and the lot's price at shop A, respectively.
 * The third line consists of two space-separated positive integers - M2 and P2, representing the
 * number of apples in a lot and the lot's price at shop B, respectively.
 *
 * Output
 *
 * Print a positive integer representing the minimum price at which Josh can buy the apples.
 *
 * Note
 *
 * There will always be at least one solution.
 * There is only one lot size for a particular shop.
 * There is an unlimited supply of apples for both the shops.
 *
 * Example
 *
 * Input:
 * 19
 * 3 10
 * 4 15
 *
 * Output:
 * 65
 *
 * Explanation:
 * Josh can buy five lots from the first shop and one lot from the second shop.
 * So the total price is (5 * 10 + 15) = 65.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：枚举、整除判断。
 * 校对：题干前半段来自 OCR 重建，但输入输出和样例是稳定的，样例已做代码校验。
 * 做法：枚举其中一种批量购买次数，检查剩余苹果是否能被另一种批量整除。
 */
public class MinimumApplePurchaseCost {

    public long minimumCost(int targetApples, int lotSizeA, int priceA, int lotSizeB, int priceB) {
        long best = Long.MAX_VALUE;
        for (int countA = 0; countA * lotSizeA <= targetApples; countA++) {
            int remaining = targetApples - countA * lotSizeA;
            if (remaining % lotSizeB == 0) {
                long cost = (long) countA * priceA + (long) (remaining / lotSizeB) * priceB;
                best = Math.min(best, cost);
            }
        }
        return best;
    }
}
```

---

## MinimumJuiceStallStops.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Question
 *
 * John misses his bus and has to walk all his way from home to school. The distance between his
 * school and home is D units. He starts his journey with an initial energy of K units. His energy
 * decreases by 1 unit for every unit of distance walked. On his way to school, there are N juice
 * stalls. Each stall has a specific amount of juice in liters. His energy increases by 1 unit for
 * every liter of juice he consumes. Note that in order to keep him walking he should have non-zero
 * energy.
 *
 * Write an algorithm to help John figure out the minimum number of juice stalls at which he should
 * stop to successfully reach the school. In case he can't reach the school, the output will be -1.
 *
 * Input
 *
 * The first line of the input consists of an integer N, representing the number of juice stalls.
 * The second line consists of N space-separated integers - dist1, dist2, ..., distn representing
 * the distance of the i-th stall from John's home.
 * The third line consists of N space-separated integers - lit1, lit2, ..., litn representing the
 * liters of juice available at the i-th stall.
 * The last line consists of two space-separated integers - D and K representing the distance of the
 * school from John's home and his initial energy, respectively.
 *
 * Output
 *
 * Print an integer representing the minimum number of juice stalls at which John should stop to
 * reach the school successfully.
 *
 * Example
 *
 * Input:
 * 4
 * 5 7 8 10
 * 2 3 1 5
 * 15 5
 *
 * Output:
 * 3
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：最大堆贪心。
 * 校对：样例已做代码校验。
 * 做法：先尽量往前走，走不动时，从已经路过的摊位里选补给量最大的一个。
 */
public class MinimumJuiceStallStops {

    public int minStops(int[] distances, int[] liters, int destination, int initialEnergy) {
        int n = distances.length;
        int previousDistance = 0;
        int energy = initialEnergy;
        int stops = 0;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i <= n; i++) {
            int currentDistance = i == n ? destination : distances[i];
            int needed = currentDistance - previousDistance;
            while (energy < needed && !maxHeap.isEmpty()) {
                energy += maxHeap.poll();
                stops++;
            }
            if (energy < needed) {
                return -1;
            }
            energy -= needed;
            previousDistance = currentDistance;
            if (i < n) {
                maxHeap.offer(liters[i]);
            }
        }
        return stops;
    }
}
```

---

## MinimumPathToVisitAllRetailersInCartesia.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * Gregor is a salesperson employed in the city of Cartesia, which is an infinite plane whose
 * locations follow the Cartesian coordinate system. There are N+1 retailers in the city out of
 * which N retailers, with position 1 to N, have the coordinates (X1, 0), (X2, 0) to (Xn, 0). The
 * head retailer, with position N+1, is located at the coordinate (Xn+1, Yn+1).
 *
 * Gregor needs to find a path such that starting from the given Kth retailer, he can visit all the
 * other retailers covering the shortest total distance. Gregor can visit a retailer twice along his
 * route and the distance between any two retailers is the same as the distance between the two
 * points in the Cartesian coordinate system.
 *
 * Write an algorithm to help Gregor to find the minimum distance of the path to visit all the given
 * retailers.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers N and K, representing the
 * number of retailers on the X-axis and the position of the starting retailer, respectively.
 * The second line consists of N space-separated integers, representing the coordinates of retailers
 * on the X-axis.
 * The third line consists of two space-separated integers Xn+1, Yn+1, representing the coordinates
 * of the head retailer.
 *
 * Output
 *
 * Print a real number representing the minimum possible length of the path after traveling through
 * all the given points, rounded up to 6 decimal places.
 *
 * Constraints
 *
 * 1 <= K <= N + 1
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：几何、排序、路径构造。
 * 校对：题面没有样例和完整约束，因此这是基于“所有普通零售点都在 x 轴上，只有总部点离轴”这一可见结构推导出的实现。
 * 当前理解：最优路径不会在 x 轴上的点之间来回折返多次；它等价于先按某个方向覆盖整段 x 轴区间，再把唯一的离轴点作为一次插入。
 * 提示：这个实现已经用小规模暴力枚举做过对拍，但仍建议后续继续复核题面。
 */
public class MinimumPathToVisitAllRetailersInCartesia {

    public double minimumDistance(long[] retailerX, long headX, long headY, int startRetailerPosition) {
        if (retailerX.length == 0) {
            return 0.0;
        }

        long[] sortedX = uniqueSorted(retailerX);
        if (startRetailerPosition == retailerX.length + 1) {
            long left = sortedX[0];
            long right = sortedX[sortedX.length - 1];
            return (right - left) + Math.min(distance(headX, headY, left), distance(headX, headY, right));
        }

        long startX = retailerX[startRetailerPosition - 1];
        int startIndex = findFirstIndex(sortedX, startX);
        double leftThenRight = evaluate(buildLeftThenRight(sortedX, startIndex), headX, headY);
        double rightThenLeft = evaluate(buildRightThenLeft(sortedX, startIndex), headX, headY);
        return Math.min(leftThenRight, rightThenLeft);
    }

    private int findFirstIndex(long[] sortedX, long target) {
        for (int i = 0; i < sortedX.length; i++) {
            if (sortedX[i] == target) {
                return i;
            }
        }
        throw new IllegalArgumentException("Start x-coordinate not found in retailer list.");
    }

    private long[] uniqueSorted(long[] retailerX) {
        long[] copy = Arrays.copyOf(retailerX, retailerX.length);
        Arrays.sort(copy);
        int uniqueCount = 0;
        for (long value : copy) {
            if (uniqueCount == 0 || value != copy[uniqueCount - 1]) {
                copy[uniqueCount++] = value;
            }
        }
        return Arrays.copyOf(copy, uniqueCount);
    }

    private long[] buildLeftThenRight(long[] sortedX, int startIndex) {
        long[] order = new long[sortedX.length];
        int write = 0;
        order[write++] = sortedX[startIndex];
        for (int i = startIndex - 1; i >= 0; i--) {
            order[write++] = sortedX[i];
        }
        for (int i = startIndex + 1; i < sortedX.length; i++) {
            order[write++] = sortedX[i];
        }
        return order;
    }

    private long[] buildRightThenLeft(long[] sortedX, int startIndex) {
        long[] order = new long[sortedX.length];
        int write = 0;
        order[write++] = sortedX[startIndex];
        for (int i = startIndex + 1; i < sortedX.length; i++) {
            order[write++] = sortedX[i];
        }
        for (int i = startIndex - 1; i >= 0; i--) {
            order[write++] = sortedX[i];
        }
        return order;
    }

    private double evaluate(long[] order, long headX, long headY) {
        double baseline = 0.0;
        for (int i = 0; i + 1 < order.length; i++) {
            baseline += Math.abs(order[i + 1] - order[i]);
        }

        double best = baseline + distance(headX, headY, order[order.length - 1]);
        for (int i = 0; i + 1 < order.length; i++) {
            double extra = distance(headX, headY, order[i])
                    + distance(headX, headY, order[i + 1])
                    - Math.abs(order[i + 1] - order[i]);
            best = Math.min(best, baseline + extra);
        }
        return best;
    }

    private double distance(long headX, long headY, long axisX) {
        return Math.hypot(headX - axisX, headY);
    }
}
```

---

## MinimumProjectsToZeroErrorScores.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Ethan is the leader of a team with N members. He has assigned an error score to each member in
 * his team based on the bugs that he has found in that particular team member's task. Because the
 * error score has increased to a significantly large value, he wants to give all the team members a
 * chance to improve their error scores, thereby improving their reputation in the organization. He
 * introduces a new rule that whenever a team member completes a project successfully, the error
 * score of that member decreases by a count P and the error score of all the other team members
 * whose score is greater than zero decreases by a count Q.
 *
 * Write an algorithm to help Ethan find the minimum number of projects that the team must complete
 * in order to make the error score of all the team members zero.
 *
 * Input
 *
 * The first line of the input consists of an integer - errorScore_size, representing the total
 * number of team members (N).
 * The second line consists of N space-separated integers - errorScore, representing the initial
 * error scores of the team members.
 * The third line consists of an integer - compP, representing the count by which the error score of
 * the team member who completes a project successfully decreases (P).
 * The last line consists of an integer - othQ, representing the count by which the error score of
 * the team member whose error score is greater than zero decreases (Q).
 *
 * Output
 *
 * Print an integer representing the minimum number of projects that the team must complete in order
 * to make the error score of all the team members zero. If no project need to be completed then
 * print 0.
 *
 * Constraints
 *
 * 1 <= errorScore_size <= 2*10^5
 * 1 <= othQ <= compP <= 10^9
 * 0 <= errorScore <= 10^9
 *
 * Example
 *
 * Input:
 * 3
 * 6 4 1
 * 4
 * 1
 *
 * Output:
 * 3
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：答案二分、可行性判断。
 * 校对：OCR 与 docx 的规模上限冲突；这里按 OCR 的 2*10^5 理解实现，样例已做代码校验。
 * 做法：二分项目次数，把每个人在做了 mid 次“全体减 Q”后剩余的部分，用 P-Q 去补。
 */
public class MinimumProjectsToZeroErrorScores {

    public long minimumProjects(long[] errorScores, long projectDecrease, long otherDecrease) {
        if (projectDecrease == otherDecrease) {
            long max = 0L;
            for (long errorScore : errorScores) {
                max = Math.max(max, (errorScore + otherDecrease - 1) / otherDecrease);
            }
            return max;
        }

        long left = 0L;
        long right = 1L;
        while (!canFinish(errorScores, projectDecrease, otherDecrease, right)) {
            right <<= 1;
        }
        while (left < right) {
            long middle = left + ((right - left) >> 1);
            if (canFinish(errorScores, projectDecrease, otherDecrease, middle)) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }
        return left;
    }

    private boolean canFinish(long[] errorScores, long projectDecrease, long otherDecrease, long projects) {
        long extra = projectDecrease - otherDecrease;
        long needed = 0L;
        for (long errorScore : errorScores) {
            long remaining = errorScore - projects * otherDecrease;
            if (remaining > 0) {
                needed += (remaining + extra - 1) / extra;
                if (needed > projects) {
                    return false;
                }
            }
        }
        return needed <= projects;
    }
}
```

---

## MinimumStraightLineCoverForPickupLocations.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Question
 *
 * A transportation company has begun service in a new city. They have identified several pickup
 * locations in crowded areas of the city. A straight-line route can cover every pickup location
 * that lies on that same straight line.
 *
 * Write an algorithm to calculate the minimum number of straight-line routes required to cover all
 * the pickup locations.
 *
 * Input
 *
 * The first line of the input consists of an integer N, representing the number of pickup
 * locations.
 * The next N lines each consist of two space-separated integers x and y, representing the
 * coordinates of a pickup location.
 *
 * Output
 *
 * Print an integer representing the minimum number of straight-line routes required to cover all
 * the pickup locations.
 *
 * Constraints
 *
 * 0 <= N <= 20
 * -10^3 <= x, y <= 10^3
 *
 * Note
 *
 * If multiple pickup locations have identical coordinates, they may be treated as one location.
 *
 * Example
 *
 * Input:
 * 8
 * 1 4
 * 2 3
 * 2 1
 * 3 2
 * 4 1
 * 5 0
 * 4 3
 * 5 4
 *
 * Output:
 * 2
 *
 * Explanation:
 * The points (2, 1), (3, 2), (4, 3), (5, 4) lie on one straight line.
 * The points (1, 4), (2, 3), (3, 2), (4, 1), (5, 0) lie on another straight line.
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：几何、状态压缩 DP、点集最少直线覆盖。
 * 校对：这是按 OCR 字面题意写出的学习版定稿。原 OCR 里的 `N <= 10^3` 与精确解模型不匹配，所以这里直接把公开约束改成了学习版可验证规模。
 * 提示：当前主版本按“去重后点数不超过 20”理解。更像同源题的候选重建版见 MinimumStraightLineRoutesFromBaseToPickupLocations。
 */
public class MinimumStraightLineCoverForPickupLocations {

    private static final int MAX_REFERENCE_POINTS = 20;

    public int minimumRoutes(int[][] pickupLocations) {
        Point[] points = uniquePoints(pickupLocations);
        int pointCount = points.length;
        if (pointCount == 0) {
            return 0;
        }
        if (pointCount == 1) {
            return 1;
        }
        if (pointCount > MAX_REFERENCE_POINTS) {
            throw new IllegalArgumentException(
                    "Reference solver supports at most " + MAX_REFERENCE_POINTS + " unique points."
            );
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] optionsByPoint = new List[pointCount];
        for (int i = 0; i < pointCount; i++) {
            optionsByPoint[i] = new ArrayList<>();
        }

        Set<Integer> masks = new HashSet<>();
        for (int i = 0; i < pointCount; i++) {
            masks.add(1 << i);
        }
        for (int i = 0; i < pointCount; i++) {
            for (int j = i + 1; j < pointCount; j++) {
                int mask = 0;
                for (int k = 0; k < pointCount; k++) {
                    if (areCollinear(points[i], points[j], points[k])) {
                        mask |= 1 << k;
                    }
                }
                masks.add(mask);
            }
        }

        for (int mask : masks) {
            for (int i = 0; i < pointCount; i++) {
                if (((mask >> i) & 1) != 0) {
                    optionsByPoint[i].add(mask);
                }
            }
        }

        int fullMask = (1 << pointCount) - 1;
        int[] memo = new int[1 << pointCount];
        Arrays.fill(memo, -1);
        return search(0, fullMask, optionsByPoint, memo);
    }

    private int search(int coveredMask, int fullMask, List<Integer>[] optionsByPoint, int[] memo) {
        if (coveredMask == fullMask) {
            return 0;
        }
        if (memo[coveredMask] != -1) {
            return memo[coveredMask];
        }

        int firstUncovered = Integer.numberOfTrailingZeros(fullMask ^ coveredMask);
        int best = Integer.MAX_VALUE;
        for (int lineMask : optionsByPoint[firstUncovered]) {
            best = Math.min(best, 1 + search(coveredMask | lineMask, fullMask, optionsByPoint, memo));
        }
        memo[coveredMask] = best;
        return best;
    }

    private Point[] uniquePoints(int[][] pickupLocations) {
        Set<Point> unique = new LinkedHashSet<>();
        for (int[] location : pickupLocations) {
            unique.add(new Point(location[0], location[1]));
        }
        return unique.toArray(new Point[0]);
    }

    private boolean areCollinear(Point first, Point second, Point third) {
        long abx = second.x - first.x;
        long aby = second.y - first.y;
        long acx = third.x - first.x;
        long acy = third.y - first.y;
        return abx * acy - aby * acx == 0L;
    }

    private static final class Point {
        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Point)) {
                return false;
            }
            Point that = (Point) other;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
```

---

## MinimumStraightLineRoutesFromBaseToPickupLocations.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Candidate reconstruction for the corrupted A25 problem.
 *
 * Higher-confidence external evidence from an SHL Automata Pro code replay shows a sibling question
 * with the same business theme, but with one crucial detail that is missing from the OCR text: all
 * straight-line routes start from a base location, and every pickup point is covered by exactly one
 * route if it lies on that same ray/line through the base.
 *
 * Reconstructed question
 *
 * A transportation company has begun service in a new city. The company has a base location where
 * it parks all its vehicles. It has identified some pickup locations where the vehicles will
 * collect passengers. The company wants to identify the minimum number of straight-line routes from
 * the base location to the pickup locations, ensuring that all pickup locations are covered.
 *
 * Input
 *
 * The first line contains an integer N, representing the number of pickup locations.
 * The next N lines contain two space-separated integers x and y, representing one pickup location.
 * The last two inputs are baseX and baseY, representing the base location.
 *
 * Output
 *
 * Print an integer representing the minimum number of straight-line routes needed from the base to
 * cover all pickup locations.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：几何、方向归一化、gcd。
 * 校对：这不是当前题库 A25 的直接定稿，而是基于更高可信外部证据补出的“候选原题版本”。
 * 提示：如果两个 pickup 点和 base 共线且在同一方向上，它们可以由同一条 route 覆盖。
 */
public class MinimumStraightLineRoutesFromBaseToPickupLocations {

    public int minimumRoutes(int[][] pickupLocations, int baseX, int baseY) {
        Set<Direction> directions = new HashSet<>();
        for (int[] point : pickupLocations) {
            int dx = point[0] - baseX;
            int dy = point[1] - baseY;
            if (dx == 0 && dy == 0) {
                continue;
            }
            int g = gcd(Math.abs(dx), Math.abs(dy));
            directions.add(new Direction(dx / g, dy / g));
        }
        return directions.size();
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int next = a % b;
            a = b;
            b = next;
        }
        return a;
    }

    private static final class Direction {
        private final int dx;
        private final int dy;

        private Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Direction)) {
                return false;
            }
            Direction that = (Direction) other;
            return dx == that.dx && dy == that.dy;
        }

        @Override
        public int hashCode() {
            return Objects.hash(dx, dy);
        }
    }
}
```

---

## MinimumSweetBoxDeliveryTime.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.List;

/**
 * Question
 *
 * William is the owner of a sweet shop. His current machine takes some time to prepare one box of
 * sweets. He receives an order for a fixed number of boxes and wants to finish the order as soon
 * as possible within a limited budget.
 *
 * To fulfill the order, William may:
 * 1. continue using his old machine, or
 * 2. buy exactly one new machine and use it instead of the old one, and
 * 3. buy ready-made sweet boxes from shops.
 *
 * Boxes bought from shops are available instantly. Each shop offer can be used at most once. If
 * William buys a new machine, he cannot use the old machine.
 *
 * Write an algorithm to find the minimum time in which William can complete the order.
 *
 * Input
 *
 * The first line of the input consists of three space-separated integers - numOfBox, prepTime and
 * money, representing the number of boxes William has to deliver, the time required to prepare one
 * box using William's current machine, and the total budget, respectively.
 * The second line consists of two space-separated integers - M and S, representing the number of
 * machines available for purchase and the number of shop offers available, respectively.
 * The next M lines consist of two space-separated integers - mTime and mCost, representing the time
 * taken by that machine to create one box and the cost of buying that machine, respectively.
 * The last S lines consist of two space-separated integers - sNum and sCost, representing the
 * number of boxes available in that shop offer and the cost to buy them, respectively.
 *
 * Output
 *
 * Print an integer representing the minimum time in which William can complete the order.
 *
 * Constraints
 *
 * 1 <= numOfBox, prepTime <= 10^9
 * 1 <= money <= 10^5
 * 1 <= M, S <= 10^3
 * 1 <= mTime, mCost, sNum, sCost <= 10^5
 *
 * Note
 *
 * Boxes purchased from shops are obtained instantly.
 * Each shop offer can be used at most once.
 * William may choose not to buy any machine and continue using the old one.
 * William may buy at most one new machine.
 *
 * Example
 *
 * Input:
 * 20 10 20
 * 3 2
 * 2 30
 * 3 25
 * 4 10
 * 5 10
 * 15 80
 *
 * Explanation:
 * With a budget of 20, William cannot afford the first two machines and cannot afford the second
 * shop offer. He may buy the third machine for 10 and the first shop offer for 10, receiving 5
 * boxes instantly. He then produces the remaining 15 boxes using the new machine in 15 * 4 = 60
 * units of time. So the minimum time is 60.
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：预算优化、枚举机器、0/1 背包。
 * 校对：原 docx 样例尾部缺失，而且“最小时间对 1000007 取模”这句逻辑上可疑，所以这里明确改写为与当前实现一致的学习版定稿，不再保留那条规则。
 * 提示：当前学习版把每个 shop offer 视为最多使用一次；机器方案在“旧机器或某一台新机器”之间二选一。
 */
public class MinimumSweetBoxDeliveryTime {

    public long minimumTime(int boxCount, int oldMachineTime, int budget, int[][] machineOptions, int[][] shopOffers) {
        long best = (long) boxCount * oldMachineTime;
        List<int[]> affordableMachines = new ArrayList<>();
        affordableMachines.add(new int[]{oldMachineTime, 0});
        for (int[] machine : machineOptions) {
            if (machine[1] <= budget) {
                affordableMachines.add(machine);
            }
        }

        for (int[] machine : affordableMachines) {
            int machineTime = machine[0];
            int machineCost = machine[1];
            int remainingBudget = budget - machineCost;
            int boughtBoxes = maximumBoxesFromShops(remainingBudget, shopOffers);
            int remainingBoxes = Math.max(0, boxCount - boughtBoxes);
            best = Math.min(best, (long) remainingBoxes * machineTime);
        }
        return best;
    }

    private int maximumBoxesFromShops(int budget, int[][] shopOffers) {
        int[] dp = new int[budget + 1];
        for (int[] offer : shopOffers) {
            int boxes = offer[0];
            int cost = offer[1];
            for (int money = budget; money >= cost; money--) {
                dp[money] = Math.max(dp[money], dp[money - cost] + boxes);
            }
        }
        int best = 0;
        for (int value : dp) {
            best = Math.max(best, value);
        }
        return best;
    }
}
```

---

## MissingCharacterDuringTransmission.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * A company provides network encryption for secure data transfer. The data string is encrypted
 * prior to transmission and gets decrypted at the receiving end. But due to some technical error,
 * the encrypted data is lost and the received string is different from the original string by 1
 * character. Arnold, a network administrator, is tasked with finding the character that got lost
 * in the network so that the bug does not harm other data that is being transferred through the
 * network.
 *
 * Write an algorithm to help Arnold find the character that was missing at the receiving end but
 * present at the sending end.
 *
 * Input
 *
 * The first line of the input consists of a string - stringSent, representing the string that was
 * sent through the network.
 * The next line consists of a string - stringRec, representing the string that was received at the
 * receiving end of the network.
 *
 * Output
 *
 * Print a character representing the character that was lost in the network during transmission.
 *
 * Note
 *
 * The input strings stringSent and stringRec consist of lowercase and uppercase English alphabets
 * (i.e. a-z, A-Z).
 *
 * Example
 *
 * Input:
 * abcdfigerj
 * abcdfiger
 *
 * Output:
 * j
 *
 * Explanation:
 * The character j at the end of the sent string was lost in the network during transmission.
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：异或、字符计数。
 * 校对：样例已做代码校验。
 * 做法：把两个字符串所有字符异或起来，剩下的就是丢失字符。
 */
public class MissingCharacterDuringTransmission {

    public char findMissingCharacter(String sent, String received) {
        int xor = 0;
        for (int i = 0; i < sent.length(); i++) {
            xor ^= sent.charAt(i);
        }
        for (int i = 0; i < received.length(); i++) {
            xor ^= received.charAt(i);
        }
        return (char) xor;
    }
}
```

---

## NumberOfWaysToObtainTheLongestConsecutiveOnes.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Given a binary string S consisting only of 0s and 1s, you may change at most K zeros inside a
 * substring into ones. Let L be the maximum possible length of a substring that can be turned into
 * all ones in this way.
 *
 * Write an algorithm to find the number of substrings whose length is exactly L and that contain at
 * most K zeros.
 *
 * Input
 *
 * The first line of the input consists of the string S.
 * The second line consists of an integer changeK, representing the maximum number of zeros that can
 * be changed (K).
 *
 * Output
 *
 * Print an integer representing the number of substrings whose length is equal to the maximum
 * achievable value L and that contain at most K zeros.
 *
 * Constraints
 *
 * 1 <= size of string <= 2*10^5
 * 0 <= changeK <= size
 *
 * Example
 *
 * Input:
 * 1010101
 * 1
 *
 * Output:
 * 3
 *
 * Explanation:
 * The maximum achievable length is 3.
 * There are exactly three substrings of length 3 that contain at most one 0: the three occurrences
 * of 101. Each such substring can be turned into 111 by changing one 0. So, the output is 3.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：滑动窗口。
 * 校对：原题里的 different ways 有明显歧义。这里直接按当前代码改写为“统计最优窗口数”的学习版定稿，不再保留会把读者带向“结果串去重”的原句。
 * 提示：校验器里保留了 `S = 101, K = 0` 这种能区分“窗口数”和“结果串数”的反例，方便后续继续考证。
 */
public class NumberOfWaysToObtainTheLongestConsecutiveOnes {

    public int countWays(String binaryString, int changeK) {
        int maxLength = findMaximumLength(binaryString, changeK);
        if (maxLength == 0) {
            return 1;
        }

        int[] prefixZeros = new int[binaryString.length() + 1];
        for (int i = 0; i < binaryString.length(); i++) {
            prefixZeros[i + 1] = prefixZeros[i] + (binaryString.charAt(i) == '0' ? 1 : 0);
        }

        int ways = 0;
        for (int start = 0; start + maxLength <= binaryString.length(); start++) {
            int zeroCount = prefixZeros[start + maxLength] - prefixZeros[start];
            if (zeroCount <= changeK) {
                ways++;
            }
        }
        return ways;
    }

    private int findMaximumLength(String binaryString, int changeK) {
        int left = 0;
        int zeroCount = 0;
        int maxLength = 0;
        for (int right = 0; right < binaryString.length(); right++) {
            if (binaryString.charAt(right) == '0') {
                zeroCount++;
            }
            while (zeroCount > changeK) {
                if (binaryString.charAt(left) == '0') {
                    zeroCount--;
                }
                left++;
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}
```

---

## OptimalStudentCompletionOrderInLibrary.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * Stephen runs a small library with N number of students as its members. All members have their
 * unique studentID. The library has the certain number of books on M different subjects. Each
 * student is given an individual assignment to complete by taking help from different books as per
 * their requirement. The library has already issued some books to its members prior to this. The
 * students can still issue required number of books from the library to complete their respective
 * assignments. Each student submits the book issued to the library after completing their
 * assignment. Only when the books have been submitted to the library can another student issue that
 * book. Also, while assigning books, Stephen starts assigning books to the student with the
 * smallest studentID and proceed to the student with the higher studentID. Once he reaches to the
 * student with the largest studentID then again goes back to the smallest studentID to whom the
 * book was not assigned and follow the same process.
 *
 * Stephen wants to find the sequence of studentIDs in which the students optimally complete their
 * assignments.
 *
 * Write an algorithm to help Stephen find the sequence of studentIDs in which the students
 * optimally complete their assignments. If all students can't complete their assignment, output a
 * list of length 1 with content -1.
 *
 * Input
 *
 * The first line of the input consists of an integer booksNum, representing the number of
 * different subjects (M).
 * The second line consists of M space-separated integers avail[0], avail[1] ... avail[M-1],
 * representing the books in the library that have not been issued to any student.
 * The third line consists of two space-separated integers studentNum and reqBooks, representing the
 * number of students (N) and number of different books required by each student (it is always
 * equal to the number of different subjects M, respectively).
 * The next N lines consist of M space-separated integers representing the books required by the
 * students.
 * The next line consists of two space-separated integers studentsIssuedBooks and issuedBooks,
 * representing the number of students with books issued (it is always equal to number of students
 * N) and number of different books issued to each student (it is always equal to the number of
 * different subjects M, respectively).
 * The next N lines consist of M space-separated integers representing the books already issued to
 * the students.
 *
 * Output
 *
 * Print space-separated integers representing the sequence of studentIDs that is optimal for the
 * students to complete their assignments. If it is not possible for all students to complete their
 * assignments, output a list of length 1 with content -1.
 *
 * Constraints
 *
 * 1 <= booksNum, reqBooks, issuedBooks <= 100
 * 1 <= studentNum <= 100
 * studentNum = studentsIssuedBooks
 *
 * Example
 *
 * Input:
 * 3
 * 2 2 3
 * 3 3
 * 2 4 0
 * 0 0 1
 * 0 1 3
 * 3 3
 * 3 5 4
 * 1 3 4
 * 2 3 5
 *
 * Output:
 * 2 0 1
 *
 * Explanation:
 * The available books are [2, 2, 3].
 * Student 2 can finish first because the remaining needed books [2, 2, 2] can be satisfied by the
 * current stock. After completion, student 2 returns [0, 1, 3]. Then students 0 and 1 can finish,
 * and the smallest feasible ID is always chosen first, so the order is [2, 0, 1].
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：资源可行性模拟，和银行家算法很像。
 * 校对：原 docx 的文字版把“required books”和“already issued books”两个矩阵顺序写反了；示例和解释都只能按“先 issued，后 required”才能自洽。
 * 提示：真正决定能否完成的是 need = required - issued，而不是 required 本身。
 */
public class OptimalStudentCompletionOrderInLibrary {

    public int[] findOptimalOrder(int[] availableBooks, int[][] issuedBooks, int[][] requiredBooks) {
        int studentCount = issuedBooks.length;
        int subjectCount = availableBooks.length;
        int[] available = Arrays.copyOf(availableBooks, subjectCount);
        int[] order = new int[studentCount];
        boolean[] finished = new boolean[studentCount];
        int completed = 0;

        while (completed < studentCount) {
            boolean progressed = false;
            for (int student = 0; student < studentCount; student++) {
                if (finished[student] || !canFinish(available, issuedBooks[student], requiredBooks[student])) {
                    continue;
                }
                finished[student] = true;
                order[completed++] = student;
                progressed = true;
                for (int subject = 0; subject < subjectCount; subject++) {
                    available[subject] += issuedBooks[student][subject];
                }
            }
            if (!progressed) {
                return new int[]{-1};
            }
        }
        return order;
    }

    public int[] findOptimalOrderUsingPromptBlockOrder(int[] availableBooks, int[][] promptRequiredBooks,
                                                       int[][] promptIssuedBooks) {
        return findOptimalOrder(availableBooks, promptIssuedBooks, promptRequiredBooks);
    }

    private boolean canFinish(int[] available, int[] issued, int[] required) {
        for (int subject = 0; subject < available.length; subject++) {
            int needed = required[subject] - issued[subject];
            if (needed > available[subject]) {
                return false;
            }
        }
        return true;
    }
}
```

---

## OrganizationReputationUpdater.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Question
 *
 * In an organization, N employees with employee IDs from 1 to N, are working in different teams.
 * Each employee shares a bond of great understanding with his/her fellow team members. Each
 * employee is assigned an integer X that represents the employee's efficiency. The sum of
 * efficiencies of all the employees indicates the reputation of the organization.
 *
 * Edwin is appointed manager of the organization for Q days. Edwin, being short-tempered, fires
 * one employee each day. Because the team members have a close relationship, K colleagues of the
 * fired employee resign in protest. (These K colleagues have the least efficiency of the remaining
 * team members.)
 *
 * Kevin is the head of the database management system and has to update the reputation of the
 * organization at the end of each day. Write an algorithm to help him determine the reputation of
 * the organization at the end of each day for Q number of days.
 *
 * Input
 *
 * The first line of the input consists of an integer - num, representing the number of employees in
 * the efficiency list (num is always equal to given number of employees N).
 * The second line consists of N space-separated integers - eff0, eff1, ...... effN-1, representing
 * the efficiency of the employees.
 * The third line consists of an integer - numT, representing the number of employees with a team ID
 * (numT is always equal to given number of employees N).
 * The fourth line consists of N space-separated integers - idE0, idE1, ...... idEN-1, representing
 * the team ID of the employees.
 * The fifth line consists of two space-separated integers - numDays and num, representing the
 * number of days for which Edwin is manager of the organization (Q) and the number of elements in
 * each Q lines (num/P is always equal to 2).
 * The last Q lines consist of P space-separated integers - idFire and numResign, representing the
 * ID of the employee that was fired each day and the number of employees who resign along with the
 * fired employee (K), respectively.
 *
 * Output
 *
 * Print Q space-separated integers representing the reputation of the organization at the end of
 * each day.
 *
 * Note
 *
 * The ID of a fired employee cannot be the ID of a person who has already resigned.
 *
 * Example
 *
 * Input:
 * 5
 * 1 2 3 4 5
 * 5
 * 1 2 1 1 2
 * 2 2
 * 3 2
 * 2 0
 *
 * Output:
 * 7 5
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：按团队维护最小堆、懒删除、动态总和。
 * 校对：样例已做代码校验。
 * 实现关键：被开除和已离职的人都还可能残留在堆里，因此需要懒删除。
 */
public class OrganizationReputationUpdater {

    public long[] updateReputation(int[] efficiencies, int[] teamIds, int[][] dailyActions) {
        int n = efficiencies.length;
        boolean[] active = new boolean[n];
        Arrays.fill(active, true);
        long reputation = 0L;
        int maxTeamId = 0;
        for (int teamId : teamIds) {
            maxTeamId = Math.max(maxTeamId, teamId);
        }

        @SuppressWarnings("unchecked")
        PriorityQueue<Integer>[] teamMins = new PriorityQueue[maxTeamId + 1];
        for (int i = 0; i <= maxTeamId; i++) {
            teamMins[i] = new PriorityQueue<>((a, b) -> {
                if (efficiencies[a] != efficiencies[b]) {
                    return Integer.compare(efficiencies[a], efficiencies[b]);
                }
                return Integer.compare(a, b);
            });
        }
        for (int i = 0; i < n; i++) {
            reputation += efficiencies[i];
            teamMins[teamIds[i]].offer(i);
        }

        long[] answer = new long[dailyActions.length];
        for (int day = 0; day < dailyActions.length; day++) {
            int firedId = dailyActions[day][0] - 1;
            int resignCount = dailyActions[day][1];
            if (active[firedId]) {
                active[firedId] = false;
                reputation -= efficiencies[firedId];
            }

            PriorityQueue<Integer> queue = teamMins[teamIds[firedId]];
            prune(queue, active);
            for (int i = 0; i < resignCount && !queue.isEmpty(); i++) {
                int resign = queue.poll();
                if (!active[resign]) {
                    i--;
                    prune(queue, active);
                    continue;
                }
                active[resign] = false;
                reputation -= efficiencies[resign];
                prune(queue, active);
            }
            answer[day] = reputation;
        }
        return answer;
    }

    private void prune(PriorityQueue<Integer> queue, boolean[] active) {
        while (!queue.isEmpty() && !active[queue.peek()]) {
            queue.poll();
        }
    }
}
```

---

## PartialSortWithAscendingPrefixAndDescendingSuffix.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * You are given a list of integers of size N. Write an algorithm to sort the first K elements (from
 * list[0] to list[K-1]) of the list in ascending order and the remaining (list[K] to list[N-1])
 * elements in descending order.
 *
 * Input
 *
 * The first line of the input consists of an integer - inputList_size, representing the number of
 * elements in the list (N).
 * The next line consists of N space-separated integers representing the elements of the list.
 * The last line consists of an integer - num, representing the number of elements to be sorted in
 * ascending order (K).
 *
 * Output
 *
 * Print N space-separated integers representing the sorted list.
 *
 * Example
 *
 * Input:
 * 8
 * 11 7 5 10 46 23 16 8
 * 3
 *
 * Output:
 * 5 7 11 46 23 16 10 8
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：分段排序。
 * 校对：题面稳定。
 * 提示：不是整体排序后切两段，而是两段各自独立排序。
 */
public class PartialSortWithAscendingPrefixAndDescendingSuffix {

    public int[] partialSort(int[] nums, int k) {
        int[] result = Arrays.copyOf(nums, nums.length);
        Arrays.sort(result, 0, k);
        Arrays.sort(result, k, result.length);
        reverse(result, k, result.length - 1);
        return result;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
```

---

## PerfectSquareBillCount.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * A company is planning a big sale at which they will give their customers a special promotional
 * discount. Each customer that purchases a product from the company has a unique customerID numbered
 * from 0 to N-1. Andy, the marketing head of the company, has selected bill amounts of the N
 * customers for the promotional scheme. The discount will be given to the customers whose bill
 * amounts are perfect squares. The customers may use this discount on a future purchase.
 *
 * Write an algorithm to help Andy find the number of customers that will be given discounts.
 *
 * Input
 *
 * The first line of the input consists of an integer numOfCust representing the number of customers
 * whose bills are selected for the promotional discount (N).
 * The second line consists of N space-separated integers - bill0, bill1, ......, billN-1
 * representing the bill amounts of the N customers selected for the promotional discount.
 *
 * Output
 *
 * Print an integer representing the number of customers that will be given discounts.
 *
 * Example
 *
 * Input:
 * 6
 * 25 77 54 81 48 34
 *
 * Output:
 * 2
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：完全平方数判断。
 * 校对：题面稳定。
 * 提示：0 也是完全平方数。
 */
public class PerfectSquareBillCount {

    public int countPerfectSquares(int[] bills) {
        int count = 0;
        for (int bill : bills) {
            int root = (int) Math.sqrt(bill);
            if (root * root == bill) {
                count++;
            }
        }
        return count;
    }
}
```

---

## PowerModEncryption.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Bob has to send a secret code S to his boss. He designs a method to encrypt the code using two
 * key values N and M. The formula that he uses to develop the encrypted code is shown below:
 *
 * (((S^N % 10)^M) % 1000000007)
 *
 * Write an algorithm to help Bob encrypt the code.
 *
 * Input
 *
 * The first line of the input consists of an integer secretCode, representing the secret code (S).
 * The second line consists of an integer firstKey, representing the first key value (N).
 * The third line consists of an integer secondKey, representing the second key value (M).
 *
 * Output
 *
 * Print an integer representing the code encrypted by Bob.
 *
 * Constraints
 *
 * 1 <= secretCode <= 10^9
 * 0 <= firstKey, secondKey <= 1000000007
 *
 * Example
 *
 * Input:
 * 2
 * 3
 * 4
 *
 * Output:
 * 4096
 *
 * Explanation:
 * S = 2, N = 3, M = 4
 * (((2^3 % 10)^4) % 1000000007) = 4096
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：快速幂、模运算、运算顺序。
 * 校对：示例输出 4096 已由 docx 原文和代码校验确认。
 * 易错点：不是直接算 S^(N*M)，而是先算 S^N % 10，再对这个结果做 M 次幂。
 */
public class PowerModEncryption {

    private static final long MOD = 1_000_000_007L;

    public long encryptCode(long secretCode, long firstKey, long secondKey) {
        long first = modPow(secretCode, firstKey, 10L);
        return modPow(first, secondKey, MOD);
    }

    private long modPow(long base, long exponent, long mod) {
        long result = 1L % mod;
        long value = base % mod;
        long power = exponent;
        while (power > 0) {
            if ((power & 1L) == 1L) {
                result = (result * value) % mod;
            }
            value = (value * value) % mod;
            power >>= 1;
        }
        return result;
    }
}
```

---

## PrintAllPrimesFrom2ToN.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.List;

/**
 * Question
 *
 * A prime number is divisible only by 1 and itself. You are given a positive integer. Write an
 * algorithm to find all the prime numbers from 2 to the given positive number.
 *
 * Input
 *
 * The input consists of an integer num, representing the number written on the board.
 *
 * Output
 *
 * Print space-separated integers representing the numbers required by the teacher.
 *
 * Constraints
 *
 * 1 < num < 10^9
 *
 * Example
 *
 * Input:
 * 11
 *
 * Output:
 * 2 3 5 7 11
 *
 * Explanation:
 * For the given number, the list of special numbers is [2, 3, 5, 7, 11].
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：质数筛、试除法、分段筛。
 * 校对：题面把上限写到了 10^9，但又要求“输出全部质数”，这在真实面试里通常意味着截图漏了更合理的限制。
 * 提示：这里我用分段筛，至少在空间上更稳；但如果平台真的给到 10^9 级别并要求完整输出，I/O 本身都会很重。
 */
public class PrintAllPrimesFrom2ToN {

    public int[] listPrimes(int limit) {
        if (limit < 2) {
            return new int[0];
        }
        int root = (int) Math.sqrt(limit);
        List<Integer> basePrimes = simpleSieve(root);
        List<Integer> result = new ArrayList<>();
        int segmentSize = Math.max(root + 1, 32_768);

        for (int low = 2; low <= limit; low += segmentSize) {
            int high = Math.min(limit, low + segmentSize - 1);
            boolean[] composite = new boolean[high - low + 1];
            for (int prime : basePrimes) {
                long start = Math.max((long) prime * prime, ((low + (long) prime - 1) / prime) * (long) prime);
                for (long value = start; value <= high; value += prime) {
                    composite[(int) (value - low)] = true;
                }
            }
            for (int value = low; value <= high; value++) {
                if (!composite[value - low]) {
                    result.add(value);
                }
            }
        }
        int[] primes = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            primes[i] = result.get(i);
        }
        return primes;
    }

    private List<Integer> simpleSieve(int limit) {
        boolean[] composite = new boolean[limit + 1];
        List<Integer> primes = new ArrayList<>();
        for (int value = 2; value <= limit; value++) {
            if (composite[value]) {
                continue;
            }
            primes.add(value);
            if ((long) value * value > limit) {
                continue;
            }
            for (int multiple = value * value; multiple <= limit; multiple += value) {
                composite[multiple] = true;
            }
        }
        return primes;
    }
}
```

---

## ProductPairsWithPriceDifferenceK.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.HashMap;
import java.util.Map;

/**
 * Question
 *
 * The manager of a supermarket wishes to hold an event at which he will distribute gift baskets to
 * lucky customers. Each gift basket contains a pair of products. Each basket contains different
 * product pairs, but the overall value of the baskets may be the same. There are N types of
 * products and each product has a price. The gift baskets will be awarded to the customers that
 * pick a product pair that has a difference in price equal to the given integer value K.
 *
 * Write an algorithm to help the manager find the total numbers of lucky customers who will win a
 * gift basket.
 *
 * Input
 *
 * The first line of the input consists of an integer list_input_size, representing the types of
 * products (N).
 * The second line consists of N space-separated integers list_input[0], list_input[1], ...
 * list_input[N-1], representing the price of the products.
 * The last line consists of an integer K_input, representing the given value K.
 *
 * Output
 *
 * Print an integer representing the total number of lucky customers who will win a gift basket.
 *
 * Constraints
 *
 * [The screenshot for constraints is missing.]
 *
 * Example
 *
 * [The screenshot for the example is missing.]
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：哈希计数、差值配对。
 * 校对：题目缺样例和约束，是高风险题。我这里采用的解释是“统计所有不同商品类型组成的无序对”，所以如果同价商品有多个，它们也会形成多个有效对。
 * 提示：若 K = 0，要统计同价商品之间的组合数；若 K > 0，可以用 freq[x] * freq[x + K]。
 */
public class ProductPairsWithPriceDifferenceK {

    public long countPairs(int[] prices, int difference) {
        if (difference < 0) {
            return 0L;
        }
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int price : prices) {
            frequency.merge(price, 1, Integer::sum);
        }

        long pairs = 0L;
        if (difference == 0) {
            for (int count : frequency.values()) {
                pairs += (long) count * (count - 1) / 2;
            }
            return pairs;
        }

        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            int price = entry.getKey();
            int nextCount = frequency.getOrDefault(price + difference, 0);
            pairs += (long) entry.getValue() * nextCount;
        }
        return pairs;
    }
}
```

---

## RemoveVowelsFromString.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * The vowels in the English alphabet are: (a, e, i, o, u, A, E, I, O, U). Write an algorithm to
 * eliminate all vowels from a given string.
 *
 * Input
 *
 * The input consists of the given string.
 *
 * Output
 *
 * Print a string after removing all the vowels from the given string.
 *
 * Example
 *
 * Input:
 * MynameisAnthony
 *
 * Output:
 * Mynmsnthny
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：字符过滤。
 * 校对：题面稳定。
 * 提示：这是非常适合拿来熟悉英文题面动词 remove / eliminate / filter 的题。
 */
public class RemoveVowelsFromString {

    public String removeVowels(String input) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (!isVowel(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    private boolean isVowel(char ch) {
        switch (ch) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
                return true;
            default:
                return false;
        }
    }
}
```

---

## ReplaceValuesWithTheirIndexPositions.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * You are given a list of N unique positive numbers ranging from 0 to (N - 1). Write an algorithm
 * to replace the value of each number with its corresponding index value in the list.
 *
 * Input
 *
 * The first line of the input consists of an integer size, representing the size of the list (N).
 * The next line consists of N space-separated integers, arr[0], arr[1], ... arr[N-1] representing
 * the given list of numbers.
 *
 * Output
 *
 * Print N space-separated integers representing the list obtained by replacing the values of the
 * numbers with their corresponding index values in the list.
 *
 * Constraints
 *
 * 0 < size <= 10^5
 * 0 <= arr[i] <= 10^5
 * 0 <= i < size
 *
 * Note
 *
 * All the numbers in the list are unique.
 *
 * Example
 *
 * Input:
 * 4
 * 3 2 0 1
 *
 * Output:
 * 2 3 1 0
 *
 * Explanation:
 * Before the change, the elements of the list are:
 * arr[0]=3, arr[1]=2, arr[2]=0 and arr[3]=1
 * After the change, the elements are:
 * arr[0]=2, arr[1]=3, arr[2]=1 and arr[3]=0
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：数组映射、逆排列。
 * 校对：题面稳定。
 * 提示：因为值域刚好是 0..N-1 且互不重复，所以本质上是在求排列的 inverse permutation。
 */
public class ReplaceValuesWithTheirIndexPositions {

    public int[] replaceWithIndices(int[] nums) {
        int[] indices = new int[nums.length];
        for (int index = 0; index < nums.length; index++) {
            indices[nums[index]] = index;
        }
        return indices;
    }
}
```

---

## RightRotationStringCheck.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Charlie has a magic mirror that shows the right-rotated versions of a given word. To generate
 * different right rotations of a word, the word is written in a circle in a clockwise order and
 * read it starting from any given character in a clockwise order until all the characters are
 * covered. For example, in the word "sample", if we start with 'p', we get the right rotated word
 * as "plesam".
 *
 * Write an algorithm to output 1 if the word1 is a right rotation of word2 otherwise output -1.
 *
 * Input
 *
 * The first line of the input consists of a string word1, representing the first word.
 * The second line consists of a string word2, representing the second word.
 *
 * Output
 *
 * Print 1 if the word1 is a right rotation of word2 otherwise print -1.
 *
 * Example
 *
 * Input:
 * sample
 * plesam
 *
 * Output:
 * 1
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：字符串旋转。
 * 校对：题面稳定。
 * 提示：长度相同且 word1 是 word2+word2 的子串即可。
 */
public class RightRotationStringCheck {

    public int isRightRotation(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return -1;
        }
        String doubled = word2 + word2;
        return doubled.contains(word1) ? 1 : -1;
    }
}
```

---

## RoadWithMaximumTollRevenue.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Question
 *
 * In a state, N cities with unique city codes from 1 to N are connected by N-1 roads. The road
 * network is in the form of a tree of which each road connects two cities. A path is a road, or a
 * combination of roads, connecting any two cities. Each road has a toll booth that collects a toll
 * equal to the maximum number of paths of which that particular road is part. The state
 * transportation authority wishes to identify the road on which the maximum toll revenue is
 * collected.
 *
 * Write an algorithm to help the transportation authority identify the pair of cities connected by
 * the road on which the maximum toll revenue is collected. The output should be sorted in
 * increasing order. If more than one road collects the same total revenue, then output the pair of
 * cities that have the smaller city code.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers - N and R, representing the
 * number of cities in the state and the number of roads, respectively.
 * The next R lines consist of two space-separated integers - city1 and city2, representing the
 * cities connected by a road.
 *
 * Output
 *
 * Print two space-separated sorted integers representing the cities connected by the road on which
 * the maximum toll revenue is collected. If two or more toll booths collect the same total revenue,
 * then print the pair of cities with lexicographically smaller codes.
 *
 * Example
 *
 * Input:
 * 4 3
 * 1 2
 * 2 3
 * 3 4
 *
 * Output:
 * 2 3
 *
 * Explanation:
 * Road (2,3) lies between the pairs of cities (1,3), (1,4), (2,3), and (2,4).
 * So, the maximum toll collected by the road connecting cities 2 and 3 is 4.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：树边贡献、子树规模。
 * 校对：样例已做代码校验。
 * 核心公式：若边把树拆成大小为 size 和 n-size 的两块，则该边贡献为 size * (n-size)。
 */
public class RoadWithMaximumTollRevenue {

    public int[] findBestRoad(int cityCount, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>(cityCount + 1);
        for (int i = 0; i <= cityCount; i++) {
            graph.add(new ArrayList<Integer>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] parent = new int[cityCount + 1];
        int[] order = new int[cityCount];
        int index = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        parent[1] = -1;
        while (!stack.isEmpty()) {
            int node = stack.pop();
            order[index++] = node;
            for (int next : graph.get(node)) {
                if (next == parent[node]) {
                    continue;
                }
                parent[next] = node;
                stack.push(next);
            }
        }

        long[] subtreeSize = new long[cityCount + 1];
        Arrays.fill(subtreeSize, 1L);
        long bestRevenue = -1L;
        int[] bestEdge = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int i = index - 1; i > 0; i--) {
            int node = order[i];
            int par = parent[node];
            subtreeSize[par] += subtreeSize[node];
            long revenue = subtreeSize[node] * (cityCount - subtreeSize[node]);
            int a = Math.min(node, par);
            int b = Math.max(node, par);
            if (revenue > bestRevenue || (revenue == bestRevenue && (a < bestEdge[0] || (a == bestEdge[0] && b < bestEdge[1])))) {
                bestRevenue = revenue;
                bestEdge[0] = a;
                bestEdge[1] = b;
            }
        }
        return bestEdge;
    }
}
```

---

## ShlSampleValidator.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Runs sample checks for the SHL classes that have already been split into standalone learning files.
 */
public final class ShlSampleValidator {

    private ShlSampleValidator() {
    }

    public static void main(String[] args) {
        validateEncryptCode();
        validateInternshipSalary();
        validateBookFairRatings();
        validateCountLessThanK();
        validateReplaceWithIndices();
        validateCountDigitOccurrences();
        validateNotCommonElements();
        validateTravelingSalesmanDays();
        validateMissingCharacter();
        validateCaseInsensitiveOccurrenceCount();
        validateSweetDeliveryTime();
        validateBestStartingUser();
        validateLongestPalindromicList();
        validateShortestPathWithSpells();
        validateCircleIntersectionArea();
        validatePrimeListing();
        validateStreetLightState();
        validateMaximumInteriorSignal();
        validateKthSmallestStockPrice();
        validateLongestConsecutiveOnesWays();
        validateStraightLineCoverForPickupLocations();
        validateStraightLineRoutesFromBaseCandidate();
        validateOptimalStudentOrder();
        validateRetailerPathInCartesia();
        validateOrganizationReputation();
        validateCircularDinnerAttendees();
        validateLexicographicallySmallestDinnerGuests();
        validateFifoCacheMisses();
        validatePartialSort();
        validateBusRouteCoverage();
        validateRightRotation();
        validateFrequencyStableSort();
        validateLargestPlotBetweenHouses();
        validateLargestHouseArea();
        validatePerfectSquareBillCount();
        validateStableEvenOddPartition();
        validateRemoveVowels();
        validatePriceDifferencePairs();
        validateMaximumCommonFootsteps();
        validateAppleCost();
        validateAlternateSortedElements();
        validateMinimumJuiceStops();
        validateRoadWithMaximumRevenue();
        validateMinimumProjects();
        System.out.println("All SHL sample validations passed.");
    }

    private static void validateEncryptCode() {
        PowerModEncryption solver = new PowerModEncryption();
        assertEquals(4096L, solver.encryptCode(2L, 3L, 4L), "encryptCode");
    }

    private static void validateInternshipSalary() {
        MaximumInternshipSalary solver = new MaximumInternshipSalary();
        int[][] pay = {
                {1, 2},
                {4, 10},
                {20, 21},
                {2, 23}
        };
        assertEquals(33L, solver.maxSalary(pay), "maxSalary");
    }

    private static void validateCountLessThanK() {
        CountElementsStrictlyLessThanK solver = new CountElementsStrictlyLessThanK();
        assertEquals(4L, solver.countLessThan(new int[]{1, 7, 4, 5, 6, 3, 2}, 5), "countLessThan");
    }

    private static void validateReplaceWithIndices() {
        ReplaceValuesWithTheirIndexPositions solver = new ReplaceValuesWithTheirIndexPositions();
        assertArrayEquals(new long[]{2L, 3L, 1L, 0L}, toLongArray(solver.replaceWithIndices(new int[]{3, 2, 0, 1})),
                "replaceWithIndices");
    }

    private static void validateCountDigitOccurrences() {
        CountDigitOccurrencesInNumber solver = new CountDigitOccurrencesInNumber();
        assertEquals(3L, solver.countOccurrences(2, 123228), "countOccurrencesInNumber");
        assertEquals(1L, solver.countOccurrences(0, 0), "countOccurrencesInZero");
    }

    private static void validateNotCommonElements() {
        CountElementsNotCommonToBothLists solver = new CountElementsNotCommonToBothLists();
        assertEquals(12L, solver.countNotCommon(
                new int[]{1, 1, 2, 3, 4, 5, 5, 7, 6, 9, 10},
                new int[]{11, 12, 13, 4, 5, 6, 7, 18, 19, 20}
        ), "countNotCommon");
    }

    private static void validateBookFairRatings() {
        MaximumTotalRatingWithHorrorAndSciFiBooks solver = new MaximumTotalRatingWithHorrorAndSciFiBooks();
        int[][] horror = {
                {5, 10},
                {4, 12},
                {8, 20}
        };
        int[][] sciFi = {
                {6, 9},
                {3, 8},
                {10, 25}
        };
        long expected = bruteMaximumRating(30, horror, sciFi);
        assertEquals(expected, solver.maximumRating(30, horror, sciFi), "maximumRating");
    }

    private static void validateTravelingSalesmanDays() {
        TravelingSalesmanMaximumWorkingDays solver = new TravelingSalesmanMaximumWorkingDays();
        assertEquals(11L, solver.maxWorkingDays(new int[]{7, 2, 3}), "maxWorkingDays");
    }

    private static void validateMissingCharacter() {
        MissingCharacterDuringTransmission solver = new MissingCharacterDuringTransmission();
        assertEquals('j', solver.findMissingCharacter("abcdfigerj", "abcdfiger"), "findMissingCharacter");
    }

    private static void validateCaseInsensitiveOccurrenceCount() {
        CaseInsensitiveSubstringOccurrenceCount solver = new CaseInsensitiveSubstringOccurrenceCount();
        String parent = "TimisplayinginthehouseofTimwiththetoysofTim";
        assertEquals(3L, solver.countOccurrences(parent, "Tim"), "countOccurrences");
    }

    private static void validateSweetDeliveryTime() {
        MinimumSweetBoxDeliveryTime solver = new MinimumSweetBoxDeliveryTime();
        int[][] machines = {
                {2, 30},
                {3, 25},
                {4, 10}
        };
        int[][] shops = {
                {5, 10},
                {15, 80}
        };
        assertEquals(60L, solver.minimumTime(20, 10, 20, machines, shops), "minimumTime");
    }

    private static void validateBestStartingUser() {
        BestStartingUserForMaximumReach solver = new BestStartingUserForMaximumReach();
        assertEquals(0L, solver.bestUserId(5, new int[][]{
                {0, 1},
                {3, 4},
                {1, 2},
                {2, 1}
        }), "bestUserId");
    }

    private static void validateLongestPalindromicList() {
        LongestPalindromicListByMergingAdjacentValues solver = new LongestPalindromicListByMergingAdjacentValues();
        assertArrayEquals(new long[]{15L, 25L, 34L, 25L, 15L},
                solver.longestPalindromicList(new int[]{15, 10, 15, 34, 25, 15}),
                "longestPalindromicList");
    }

    private static void validateShortestPathWithSpells() {
        ShortestPathWithUpToKZeroCostSpells solver = new ShortestPathWithUpToKZeroCostSpells();
        assertEquals(1L, solver.shortestPath(5, new int[][]{
                {0, 1, 1},
                {0, 4, 1},
                {1, 2, 2},
                {2, 3, 4},
                {4, 3, 7}
        }, 0, 3, 1), "shortestPath");
    }

    private static void validateCircleIntersectionArea() {
        AreaOfIntersectionOfTwoCircles solver = new AreaOfIntersectionOfTwoCircles();
        assertDoubleEquals(Math.PI, solver.intersectionArea(0, 0, 1, 0, 0, 2), 1e-9, "intersectionArea");
    }

    private static void validatePrimeListing() {
        PrintAllPrimesFrom2ToN solver = new PrintAllPrimesFrom2ToN();
        assertArrayEquals(new long[]{2L, 3L, 5L, 7L, 11L}, toLongArray(solver.listPrimes(11)), "listPrimes");
    }

    private static void validateStreetLightState() {
        StreetLightStateAfterMDays solver = new StreetLightStateAfterMDays();
        assertArrayEquals(new long[]{0L, 0L, 0L, 0L, 0L, 1L, 1L, 0L},
                toLongArray(solver.stateAfterDays(new int[]{1, 1, 1, 0, 1, 1, 1, 1}, 2)),
                "stateAfterDays");
    }

    private static void validateMaximumInteriorSignal() {
        MaximumInteriorSignalLength solver = new MaximumInteriorSignalLength();
        assertEquals(1L, solver.maximumLength("101000"), "maximumSignalExample1");
        assertEquals(6L, solver.maximumLength("101111110"), "maximumSignalExample2");
    }

    private static void validateKthSmallestStockPrice() {
        KthSmallestRelativeStockPrice solver = new KthSmallestRelativeStockPrice();
        assertEquals(10L, solver.kthSmallest(new int[]{10, 5, 7, 88, 19}, 3), "kthSmallest");
    }

    private static void validateLongestConsecutiveOnesWays() {
        NumberOfWaysToObtainTheLongestConsecutiveOnes solver = new NumberOfWaysToObtainTheLongestConsecutiveOnes();
        assertEquals(3L, solver.countWays("1010101", 1), "countWays");
        assertEquals(1L, solver.countWays("00", 0), "countWays_allZeroNoChange");

        Random random = new Random(17L);
        for (int trial = 0; trial < 120; trial++) {
            int length = 1 + random.nextInt(10);
            StringBuilder builder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                builder.append(random.nextBoolean() ? '1' : '0');
            }
            String binary = builder.toString();
            int changeK = random.nextInt(length + 1);
            assertEquals(
                    bruteLongestConsecutiveOnesWindowWays(binary, changeK),
                    solver.countWays(binary, changeK),
                    "countWays_random"
            );
        }

        assertEquals(2L, bruteLongestConsecutiveOnesWindowWays("101", 0), "countWays_windowInterpretationExample");
        assertEquals(1L, bruteLongestConsecutiveOnesDistinctResultWays("101", 0), "countWays_distinctResultInterpretationExample");
    }

    private static void validateStraightLineCoverForPickupLocations() {
        MinimumStraightLineCoverForPickupLocations solver = new MinimumStraightLineCoverForPickupLocations();
        assertEquals(0L, solver.minimumRoutes(new int[][]{}), "minimumStraightLineCover_empty");
        assertEquals(1L, solver.minimumRoutes(new int[][]{
                {1, 1},
                {2, 2},
                {3, 3}
        }), "minimumStraightLineCover_singleLine");
        assertEquals(2L, solver.minimumRoutes(new int[][]{
                {1, 4},
                {2, 3},
                {2, 1},
                {3, 2},
                {4, 1},
                {5, 0},
                {4, 3},
                {5, 4}
        }), "minimumStraightLineCover_externalSample");
    }

    private static void validateStraightLineRoutesFromBaseCandidate() {
        MinimumStraightLineRoutesFromBaseToPickupLocations solver = new MinimumStraightLineRoutesFromBaseToPickupLocations();
        assertEquals(1L, solver.minimumRoutes(new int[][]{
                {1, 1},
                {2, 2},
                {3, 3}
        }, 0, 0), "minimumRoutesFromBase_sameDirection");
        assertEquals(2L, solver.minimumRoutes(new int[][]{
                {1, 1},
                {2, 2},
                {2, 1}
        }, 0, 0), "minimumRoutesFromBase_twoDirections");
    }

    private static void validateOptimalStudentOrder() {
        OptimalStudentCompletionOrderInLibrary solver = new OptimalStudentCompletionOrderInLibrary();
        int[] result = solver.findOptimalOrder(
                new int[]{2, 2, 3},
                new int[][]{
                        {2, 4, 0},
                        {0, 0, 1},
                        {0, 1, 3}
                },
                new int[][]{
                        {3, 5, 4},
                        {1, 3, 4},
                        {2, 3, 5}
                }
        );
        assertArrayEquals(new long[]{2L, 0L, 1L}, toLongArray(result), "findOptimalOrder");
    }

    private static void validateRetailerPathInCartesia() {
        MinimumPathToVisitAllRetailersInCartesia solver = new MinimumPathToVisitAllRetailersInCartesia();
        assertDoubleEquals(
                bruteRetailerPath(new long[]{0L, 10L}, 5L, 4L, 1),
                solver.minimumDistance(new long[]{0L, 10L}, 5L, 4L, 1),
                1e-9,
                "minimumDistance_fixed1"
        );
        assertDoubleEquals(
                bruteRetailerPath(new long[]{-2L, 3L, 9L}, 1L, 6L, 4),
                solver.minimumDistance(new long[]{-2L, 3L, 9L}, 1L, 6L, 4),
                1e-9,
                "minimumDistance_startHead"
        );

        Random random = new Random(7L);
        for (int trial = 0; trial < 80; trial++) {
            int n = 1 + random.nextInt(5);
            long[] axis = new long[n];
            for (int i = 0; i < n; i++) {
                axis[i] = random.nextInt(11) - 5;
            }
            long headX = random.nextInt(11) - 5;
            long headY = 1 + random.nextInt(6);
            int start = 1 + random.nextInt(n + 1);
            double expected = bruteRetailerPath(axis, headX, headY, start);
            double actual = solver.minimumDistance(axis, headX, headY, start);
            if (Math.abs(expected - actual) > 1e-9) {
                throw new IllegalStateException(
                        "minimumDistance_random expected=" + expected
                                + " actual=" + actual
                                + " axis=" + Arrays.toString(axis)
                                + " head=(" + headX + "," + headY + ")"
                                + " start=" + start
                );
            }
        }
    }

    private static void validateOrganizationReputation() {
        OrganizationReputationUpdater solver = new OrganizationReputationUpdater();
        long[] result = solver.updateReputation(
                new int[]{1, 2, 3, 4, 5},
                new int[]{1, 2, 1, 1, 2},
                new int[][]{
                        {3, 2},
                        {2, 0}
                }
        );
        assertArrayEquals(new long[]{7L, 5L}, result, "updateReputation");
    }

    private static void validateCircularDinnerAttendees() {
        CircularDinnerMaximumAttendees solver = new CircularDinnerMaximumAttendees();
        assertEquals(4L, solver.maxAttendees(new int[]{2, 3, 4, 1}), "maxAttendees");
    }

    private static void validateLexicographicallySmallestDinnerGuests() {
        LexicographicallySmallestMaximumDinnerGuestIds solver = new LexicographicallySmallestMaximumDinnerGuestIds();
        assertArrayEquals(new long[]{1L, 2L, 3L, 4L},
                toLongArray(solver.findGuestIds(new int[]{2, 3, 4, 1})),
                "findGuestIds_cycle");
        assertArrayEquals(new long[]{1L, 2L, 3L},
                toLongArray(solver.findGuestIds(new int[]{2, 1, 2})),
                "findGuestIds_pairWithChain");
        assertArrayEquals(new long[]{1L, 2L, 3L, 4L},
                toLongArray(solver.findGuestIds(new int[]{2, 1, 4, 3})),
                "findGuestIds_twoPairs");

        Random random = new Random(11L);
        for (int trial = 0; trial < 30; trial++) {
            int n = 2 + random.nextInt(6);
            int[] likes = new int[n];
            for (int i = 0; i < n; i++) {
                int liked;
                do {
                    liked = 1 + random.nextInt(n);
                } while (liked == i + 1);
                likes[i] = liked;
            }
            int[] expected = bruteDinnerGuests(likes);
            int[] actual = solver.findGuestIds(likes);
            if (!Arrays.equals(expected, actual)) {
                throw new IllegalStateException(
                        "findGuestIds_random expected=" + Arrays.toString(expected)
                                + " actual=" + Arrays.toString(actual)
                                + " likes=" + Arrays.toString(likes)
                );
            }
        }
    }

    private static void validateFifoCacheMisses() {
        FifoCacheMissCounter solver = new FifoCacheMissCounter();
        assertEquals(5L, solver.countMisses(new int[]{1, 2, 1, 3, 1, 2}, 2), "countMisses");
    }

    private static void validatePartialSort() {
        PartialSortWithAscendingPrefixAndDescendingSuffix solver = new PartialSortWithAscendingPrefixAndDescendingSuffix();
        int[] result = solver.partialSort(new int[]{11, 7, 5, 10, 46, 23, 16, 8}, 3);
        assertArrayEquals(new long[]{5L, 7L, 11L, 46L, 23L, 16L, 10L, 8L}, toLongArray(result), "partialSort");
    }

    private static void validateBusRouteCoverage() {
        TotalBusRouteCoverageDistance solver = new TotalBusRouteCoverageDistance();
        assertEquals(4L, solver.totalDistance(new int[][]{
                {2, 4},
                {3, 5},
                {6, 7}
        }), "totalDistance");
    }

    private static void validateRightRotation() {
        RightRotationStringCheck solver = new RightRotationStringCheck();
        assertEquals(1L, solver.isRightRotation("plesam", "sample"), "isRightRotation");
    }

    private static void validateFrequencyStableSort() {
        FrequencyDescendingStableSort solver = new FrequencyDescendingStableSort();
        int[] result = solver.sortByFrequency(new int[]{1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7, 8, 9, 10});
        assertArrayEquals(new long[]{5L, 5L, 5L, 5L, 3L, 3L, 3L, 6L, 6L, 6L, 2L, 2L, 4L, 4L, 1L, 7L, 8L, 9L, 10L},
                toLongArray(result), "sortByFrequency");
    }

    private static void validateLargestPlotBetweenHouses() {
        LargestPlotBetweenHouses solver = new LargestPlotBetweenHouses();
        int[] result = solver.findHouseNumbers(new int[][]{
                {3, 7},
                {1, 9},
                {2, 0},
                {5, 15},
                {4, 30}
        });
        assertArrayEquals(new long[]{4L, 5L}, toLongArray(result), "findHouseNumbers");
    }

    private static void validateLargestHouseArea() {
        LargestHouseAreaInGrid solver = new LargestHouseAreaInGrid();
        assertEquals(3L, solver.largestArea(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0},
                {0, 0, 1, 0, 0}
        }), "largestArea");
    }

    private static void validatePerfectSquareBillCount() {
        PerfectSquareBillCount solver = new PerfectSquareBillCount();
        assertEquals(2L, solver.countPerfectSquares(new int[]{25, 77, 54, 81, 48, 34}), "countPerfectSquares");
    }

    private static void validateStableEvenOddPartition() {
        StableEvenOddPartition solver = new StableEvenOddPartition();
        int[] result = solver.rearrange(new int[]{10, 98, 3, 33, 12, 22, 21, 11});
        assertArrayEquals(new long[]{10L, 98L, 12L, 22L, 3L, 33L, 21L, 11L}, toLongArray(result), "rearrange");
    }

    private static void validateRemoveVowels() {
        RemoveVowelsFromString solver = new RemoveVowelsFromString();
        if (!"Mynmsnthny".equals(solver.removeVowels("MynameisAnthony"))) {
            throw new IllegalStateException("removeVowels expected=Mynmsnthny actual=" + solver.removeVowels("MynameisAnthony"));
        }
    }

    private static void validatePriceDifferencePairs() {
        ProductPairsWithPriceDifferenceK solver = new ProductPairsWithPriceDifferenceK();
        int[] prices = {1, 5, 3, 4, 2, 1};
        long expected = bruteCountPairs(prices, 2);
        assertEquals(expected, solver.countPairs(prices, 2), "countPairs");
    }

    private static void validateMaximumCommonFootsteps() {
        MaximumCommonFootstepsWithFather solver = new MaximumCommonFootstepsWithFather();
        long[] expected = bruteMaximumCommonFootsteps(5, 1, 2, 5);
        assertArrayEquals(expected, solver.maximizeCommonSteps(5, 1, 2, 5), "maximizeCommonSteps");
    }

    private static void validateAppleCost() {
        MinimumApplePurchaseCost solver = new MinimumApplePurchaseCost();
        assertEquals(65L, solver.minimumCost(19, 3, 10, 4, 15), "minimumCost");
    }

    private static void validateAlternateSortedElements() {
        AlternateSortedElements solver = new AlternateSortedElements();
        int[] result = solver.alternateSort(new int[]{3, 5, 1, 5, 9, 10, 2, 6});
        assertArrayEquals(new long[]{1L, 3L, 5L, 9L}, toLongArray(result), "alternateSort");
    }

    private static void validateMinimumJuiceStops() {
        MinimumJuiceStallStops solver = new MinimumJuiceStallStops();
        assertEquals(3L, solver.minStops(
                new int[]{5, 7, 8, 10},
                new int[]{2, 3, 1, 5},
                15,
                5
        ), "minStops");
    }

    private static void validateRoadWithMaximumRevenue() {
        RoadWithMaximumTollRevenue solver = new RoadWithMaximumTollRevenue();
        int[] edge = solver.findBestRoad(4, new int[][]{
                {1, 2},
                {2, 3},
                {3, 4}
        });
        assertArrayEquals(new long[]{2L, 3L}, new long[]{edge[0], edge[1]}, "findBestRoad");
    }

    private static void validateMinimumProjects() {
        MinimumProjectsToZeroErrorScores solver = new MinimumProjectsToZeroErrorScores();
        assertEquals(3L, solver.minimumProjects(new long[]{6, 4, 1}, 4, 1), "minimumProjects");
    }

    private static void assertEquals(long expected, long actual, String label) {
        if (expected != actual) {
            throw new IllegalStateException(label + " expected=" + expected + " actual=" + actual);
        }
    }

    private static void assertEquals(char expected, char actual, String label) {
        if (expected != actual) {
            throw new IllegalStateException(label + " expected=" + expected + " actual=" + actual);
        }
    }

    private static void assertArrayEquals(long[] expected, long[] actual, String label) {
        if (!Arrays.equals(expected, actual)) {
            throw new IllegalStateException(label + " expected=" + Arrays.toString(expected) + " actual=" + Arrays.toString(actual));
        }
    }

    private static void assertDoubleEquals(double expected, double actual, double tolerance, String label) {
        if (Math.abs(expected - actual) > tolerance) {
            throw new IllegalStateException(label + " expected=" + expected + " actual=" + actual);
        }
    }

    private static long[] toLongArray(int[] nums) {
        long[] result = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            result[i] = nums[i];
        }
        return result;
    }

    private static long bruteMaximumRating(int budget, int[][] horrorBooks, int[][] sciFiBooks) {
        int horrorCount = horrorBooks.length;
        int sciFiCount = sciFiBooks.length;
        long best = -1L;
        for (int horrorMask = 1; horrorMask < (1 << horrorCount); horrorMask++) {
            for (int sciFiMask = 1; sciFiMask < (1 << sciFiCount); sciFiMask++) {
                int cost = 0;
                long rating = 0L;
                for (int i = 0; i < horrorCount; i++) {
                    if (((horrorMask >> i) & 1) == 0) {
                        continue;
                    }
                    rating += horrorBooks[i][0];
                    cost += horrorBooks[i][1];
                }
                for (int i = 0; i < sciFiCount; i++) {
                    if (((sciFiMask >> i) & 1) == 0) {
                        continue;
                    }
                    rating += sciFiBooks[i][0];
                    cost += sciFiBooks[i][1];
                }
                if (cost <= budget) {
                    best = Math.max(best, rating);
                }
            }
        }
        return best;
    }

    private static long bruteCountPairs(int[] prices, int difference) {
        long count = 0L;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (Math.abs(prices[i] - prices[j]) == difference) {
                    count++;
                }
            }
        }
        return count;
    }

    private static long bruteLongestConsecutiveOnesWindowWays(String binaryString, int changeK) {
        int bestLength = 0;
        long ways = 1L;
        for (int start = 0; start < binaryString.length(); start++) {
            int zeroCount = 0;
            for (int end = start; end < binaryString.length(); end++) {
                if (binaryString.charAt(end) == '0') {
                    zeroCount++;
                }
                if (zeroCount > changeK) {
                    break;
                }
                int length = end - start + 1;
                if (length > bestLength) {
                    bestLength = length;
                    ways = 1L;
                } else if (length == bestLength) {
                    ways++;
                }
            }
        }
        return ways;
    }

    private static long bruteLongestConsecutiveOnesDistinctResultWays(String binaryString, int changeK) {
        int[] zeroIndices = new int[binaryString.length()];
        int zeroCount = 0;
        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '0') {
                zeroIndices[zeroCount++] = i;
            }
        }
        Set<String> bestResults = new HashSet<>();
        int bestLength = -1;
        int combinations = 1 << zeroCount;
        for (int mask = 0; mask < combinations; mask++) {
            if (Integer.bitCount(mask) > changeK) {
                continue;
            }
            char[] chars = binaryString.toCharArray();
            for (int bit = 0; bit < zeroCount; bit++) {
                if (((mask >> bit) & 1) != 0) {
                    chars[zeroIndices[bit]] = '1';
                }
            }
            String candidate = new String(chars);
            int runLength = longestRun(candidate);
            if (runLength > bestLength) {
                bestLength = runLength;
                bestResults.clear();
                bestResults.add(candidate);
            } else if (runLength == bestLength) {
                bestResults.add(candidate);
            }
        }
        return bestResults.size();
    }

    private static int longestRun(String binaryString) {
        int best = 0;
        int current = 0;
        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '1') {
                current++;
                best = Math.max(best, current);
            } else {
                current = 0;
            }
        }
        return best;
    }

    private static long[] bruteMaximumCommonFootsteps(long fatherPos, long martinPos, long fatherVelocity, long steps) {
        long[] footprints = new long[(int) steps];
        for (int i = 0; i < steps; i++) {
            footprints[i] = fatherPos + (long) (i + 1) * fatherVelocity;
        }
        long bestCommon = 0L;
        long bestVelocity = -1L;
        long delta = fatherPos - martinPos;
        for (long fatherStepIndex = 1; fatherStepIndex <= steps; fatherStepIndex++) {
            long velocity = delta + fatherStepIndex * fatherVelocity;
            if (velocity <= 0) {
                continue;
            }
            long common = 0L;
            long martinPosition = martinPos + velocity;
            long maxFootprint = footprints[footprints.length - 1];
            while (martinPosition <= maxFootprint) {
                for (long footprint : footprints) {
                    if (footprint == martinPosition) {
                        common++;
                        break;
                    }
                }
                martinPosition += velocity;
            }
            if (common > bestCommon || (common == bestCommon && velocity > bestVelocity)) {
                bestCommon = common;
                bestVelocity = velocity;
            }
        }
        return new long[]{bestCommon, bestVelocity};
    }

    private static double bruteRetailerPath(long[] axis, long headX, long headY, int startRetailerPosition) {
        int n = axis.length;
        double[][] points = new double[n + 1][2];
        for (int i = 0; i < n; i++) {
            points[i][0] = axis[i];
            points[i][1] = 0.0;
        }
        points[n][0] = headX;
        points[n][1] = headY;
        int startIndex = startRetailerPosition - 1;
        boolean[] used = new boolean[n + 1];
        used[startIndex] = true;
        return bruteRetailerPathDfs(points, startIndex, used, 1);
    }

    private static double bruteRetailerPathDfs(double[][] points, int previous, boolean[] used, int visitedCount) {
        if (visitedCount == points.length) {
            return 0.0;
        }
        double best = Double.POSITIVE_INFINITY;
        for (int next = 0; next < points.length; next++) {
            if (used[next]) {
                continue;
            }
            used[next] = true;
            double candidate = pointDistance(points[previous], points[next])
                    + bruteRetailerPathDfs(points, next, used, visitedCount + 1);
            best = Math.min(best, candidate);
            used[next] = false;
        }
        return best;
    }

    private static double pointDistance(double[] a, double[] b) {
        return Math.hypot(a[0] - b[0], a[1] - b[1]);
    }

    private static int[] bruteDinnerGuests(int[] likesOneBased) {
        int n = likesOneBased.length;
        int[] best = new int[0];
        for (int mask = 1; mask < (1 << n); mask++) {
            int[] subset = subsetFromMask(mask, n);
            if (!isDinnerSubsetFeasible(subset, likesOneBased)) {
                continue;
            }
            if (subset.length > best.length || (subset.length == best.length && lexLess(subset, best))) {
                best = subset;
            }
        }
        return best;
    }

    private static int[] subsetFromMask(int mask, int n) {
        int[] temp = new int[Integer.bitCount(mask)];
        int write = 0;
        for (int i = 0; i < n; i++) {
            if (((mask >> i) & 1) != 0) {
                temp[write++] = i + 1;
            }
        }
        return temp;
    }

    private static boolean isDinnerSubsetFeasible(int[] subset, int[] likesOneBased) {
        if (subset.length < 2) {
            return false;
        }
        boolean[] used = new boolean[subset.length];
        int[] order = new int[subset.length];
        order[0] = subset[0];
        used[0] = true;
        return permuteDinner(order, used, 1, subset, likesOneBased);
    }

    private static boolean permuteDinner(int[] order, boolean[] used, int depth, int[] subset, int[] likesOneBased) {
        if (depth == subset.length) {
            return satisfiesDinner(order, likesOneBased);
        }
        for (int i = 1; i < subset.length; i++) {
            if (used[i]) {
                continue;
            }
            used[i] = true;
            order[depth] = subset[i];
            if (permuteDinner(order, used, depth + 1, subset, likesOneBased)) {
                return true;
            }
            used[i] = false;
        }
        return false;
    }

    private static boolean satisfiesDinner(int[] order, int[] likesOneBased) {
        int m = order.length;
        for (int i = 0; i < m; i++) {
            int left = order[(i - 1 + m) % m];
            int right = order[(i + 1) % m];
            int liked = likesOneBased[order[i] - 1];
            if (liked != left && liked != right) {
                return false;
            }
        }
        return true;
    }

    private static boolean lexLess(int[] first, int[] second) {
        if (second.length == 0) {
            return true;
        }
        for (int i = 0; i < Math.min(first.length, second.length); i++) {
            if (first[i] != second[i]) {
                return first[i] < second[i];
            }
        }
        return first.length < second.length;
    }
}
```

---

## ShortestPathWithUpToKZeroCostSpells.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Question
 *
 * A state consists of n cities numbered from 0 to n-1. All the roads in the state are
 * bidirectional. Each city is connected to another city by one direct road only. A magician
 * travels to these cities to perform shows. He knows a magic spell that can completely eliminate
 * the distance between any two directly connected cities. But he must be very careful because this
 * magic spell can be performed only K number of times.
 *
 * Write an algorithm to find the length of the shortest route between two given cities after
 * performing the magic spell K number of times. The output is -1 if no path exists.
 *
 * Input
 *
 * The first line of the input consists of five space-separated integers n, m, p, q and K,
 * representing the number of cities, the number of roads, the city A, the city B, and the number
 * of times the magician can perform the magic spell, respectively.
 * The next m lines consist of three space-separated integers u, v and w, where u and v represent
 * the cities and w represents the length of the bidirectional road between the cities.
 *
 * Output
 *
 * Print an integer representing the length of the shortest route between the two given cities after
 * performing the magic spell K number of times. Print -1 if no path exists.
 *
 * Constraints
 *
 * 1 <= n <= 1000
 * 0 <= K <= n
 * 0 <= A, B < n
 * 0 <= m <= 10^4
 * 1 <= w <= 1000
 *
 * Example
 *
 * Input:
 * 5 5 0 3 1
 * 0 1 1
 * 0 4 1
 * 1 2 2
 * 2 3 4
 * 4 3 7
 *
 * Output:
 * 1
 *
 * Explanation:
 * There are two possible routes between 0 and 3: 0->1->2->3 and 0->4->3. After reducing the
 * distance of edge 4->3 to zero, the second route becomes optimal and the minimum distance is 1.
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：分层图最短路、Dijkstra。
 * 校对：题面里“Each city is connected to another city by one direct road only”这句英文不自然，但样例和输入格式都明显说明这是一般无向图，不是树。
 * 提示：状态要带上“已经用了几次法术”，也就是 dist[node][used]。
 */
public class ShortestPathWithUpToKZeroCostSpells {

    private static final long INF = Long.MAX_VALUE / 4;

    public long shortestPath(int cityCount, int[][] roads, int source, int target, int maxSpells) {
        List<int[]>[] graph = buildGraph(cityCount, roads);
        long[][] distance = new long[cityCount][maxSpells + 1];
        for (long[] row : distance) {
            Arrays.fill(row, INF);
        }
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingLong(state -> state.distance));
        distance[source][0] = 0L;
        queue.add(new State(source, 0, 0L));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.distance != distance[current.node][current.usedSpells]) {
                continue;
            }
            for (int[] edge : graph[current.node]) {
                int next = edge[0];
                int weight = edge[1];
                long normalDistance = current.distance + weight;
                if (normalDistance < distance[next][current.usedSpells]) {
                    distance[next][current.usedSpells] = normalDistance;
                    queue.add(new State(next, current.usedSpells, normalDistance));
                }
                if (current.usedSpells < maxSpells && current.distance < distance[next][current.usedSpells + 1]) {
                    distance[next][current.usedSpells + 1] = current.distance;
                    queue.add(new State(next, current.usedSpells + 1, current.distance));
                }
            }
        }

        long answer = INF;
        for (int used = 0; used <= maxSpells; used++) {
            answer = Math.min(answer, distance[target][used]);
        }
        return answer == INF ? -1L : answer;
    }

    private List<int[]>[] buildGraph(int cityCount, int[][] roads) {
        @SuppressWarnings("unchecked")
        List<int[]>[] graph = new List[cityCount];
        for (int i = 0; i < cityCount; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }
        return graph;
    }

    private static final class State {
        private final int node;
        private final int usedSpells;
        private final long distance;

        private State(int node, int usedSpells, long distance) {
            this.node = node;
            this.usedSpells = usedSpells;
            this.distance = distance;
        }
    }
}
```

---

## StableEvenOddPartition.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * You are playing an online game. In the game, a list of N numbers is given. The player has to
 * arrange the numbers so that all the odd numbers of the list come after the even numbers.
 *
 * Write an algorithm to arrange the given list such that all the odd numbers of the list come after
 * the even numbers.
 *
 * Input
 *
 * The first line of the input consists of an integer num, representing the size of the list (N).
 * The second line of the input consists of N space-separated integers representing the values of the
 * list.
 *
 * Output
 *
 * Print N space-separated integers such that all the odd numbers of the list come after the even
 * numbers.
 *
 * Note
 *
 * The relative order of odd numbers and the relative order of even numbers in the output should be
 * same as given in the input.
 *
 * Example
 *
 * Input:
 * 8
 * 10 98 3 33 12 22 21 11
 *
 * Output:
 * 10 98 12 22 3 33 21 11
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：稳定分组。
 * 校对：题面稳定。
 * 提示：不能直接做普通原地交换，因为题目要求相对顺序不变。
 */
public class StableEvenOddPartition {

    public int[] rearrange(int[] nums) {
        int[] result = new int[nums.length];
        int index = 0;
        for (int num : nums) {
            if ((num & 1) == 0) {
                result[index++] = num;
            }
        }
        for (int num : nums) {
            if ((num & 1) == 1) {
                result[index++] = num;
            }
        }
        return result;
    }
}
```

---

## StreetLightStateAfterMDays.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.HashMap;
import java.util.Map;

/**
 * Question
 *
 * Mr. Woods, an electrician has made some faulty connections of eight street lights in Timberland
 * city. The connections are such that if the street lights adjacent to a particular light are both
 * ON (represented as 1) or are both OFF (represented as 0), then that street light goes OFF the
 * next night. Otherwise, it remains ON the next night. The two street lights at the end of the road
 * have only a single adjacent street light, so the other adjacent light can be assumed to be
 * always OFF. The state of the lights on a particular day is considered for the next day and not
 * for the same day.
 *
 * Due to this fault, the people of the city are facing difficulty in driving on the road at night.
 * So, they have filed a complaint about this to the Head of the Federal Highway Administration.
 * Based on this complaint the head has asked for the report of the state of street lights after M
 * days.
 *
 * Write an algorithm to output the state of the street lights after the given M days.
 *
 * Input
 *
 * The first line of input consists of an integer currentState_size, representing the number of
 * street lights (N).
 * The next line consists of N space-separated integers currentState, representing the current state
 * of the street lights.
 * The last line consists of an integer days, representing the number of days (M).
 *
 * Output
 *
 * Print eight space-separated integers representing the state of the street lights after M days.
 *
 * Constraints
 *
 * 1 <= days <= 10^6
 *
 * Example
 *
 * Input:
 * 8
 * 1 1 1 0 1 1 1 1
 * 2
 *
 * Output:
 * 0 0 0 0 0 1 1 0
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：状态模拟、找周期。
 * 校对：题面稳定。
 * 提示：规则等价于“下一天是否为 1 = 左右邻居是否不同”。
 */
public class StreetLightStateAfterMDays {

    public int[] stateAfterDays(int[] currentState, int days) {
        int state = encode(currentState);
        Map<Integer, Integer> seenAt = new HashMap<>();
        int day = 0;
        while (day < days && !seenAt.containsKey(state)) {
            seenAt.put(state, day);
            state = nextState(state, currentState.length);
            day++;
        }
        if (day < days) {
            int cycleStart = seenAt.get(state);
            int cycleLength = day - cycleStart;
            int remaining = (days - day) % cycleLength;
            for (int i = 0; i < remaining; i++) {
                state = nextState(state, currentState.length);
            }
        }
        return decode(state, currentState.length);
    }

    private int nextState(int state, int length) {
        int next = 0;
        for (int i = 0; i < length; i++) {
            int left = i == 0 ? 0 : ((state >> (length - i)) & 1);
            int right = i == length - 1 ? 0 : ((state >> (length - i - 2)) & 1);
            if (left != right) {
                next |= 1 << (length - i - 1);
            }
        }
        return next;
    }

    private int encode(int[] state) {
        int encoded = 0;
        for (int value : state) {
            encoded = (encoded << 1) | value;
        }
        return encoded;
    }

    private int[] decode(int state, int length) {
        int[] decoded = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            decoded[i] = state & 1;
            state >>= 1;
        }
        return decoded;
    }
}
```

---

## TotalBusRouteCoverageDistance.java

```java
package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * The city bus stations are located at equal distances (unit distance) from each other along a
 * straight road. Each station has a unique station ID. The buses do not travel to all of the bus
 * stations. The highway administration needs to determine the total distance that the buses cover.
 *
 * Given the IDs of the bus stations that have a bus operating between them, write an algorithm to
 * help the administration find the distance covered by all the city buses.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers num and constM, representing
 * the number of buses (N) and constM is always 2.
 * Next N lines consist of constM space-separated integers - busStop0 and busStop1 representing the
 * IDs of the bus stations that have a bus operating between them.
 *
 * Output
 *
 * Print an integer representing the distance covered by the buses.
 *
 * Example
 *
 * Input:
 * 3 2
 * 2 4
 * 3 5
 * 6 7
 *
 * Output:
 * 4
 *
 * Explanation:
 * Their union on the road is [2,5] and [6,7], so the total covered distance is 3 + 1 = 4.
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：区间合并。
 * 校对：题意稳定。
 * 提示：站点等距时区间 [l,r] 的覆盖距离是 r-l，不是端点数量。
 */
public class TotalBusRouteCoverageDistance {

    public int totalDistance(int[][] busRoutes) {
        if (busRoutes.length == 0) {
            return 0;
        }

        int[][] intervals = new int[busRoutes.length][2];
        for (int i = 0; i < busRoutes.length; i++) {
            int left = Math.min(busRoutes[i][0], busRoutes[i][1]);
            int right = Math.max(busRoutes[i][0], busRoutes[i][1]);
            intervals[i][0] = left;
            intervals[i][1] = right;
        }
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        int total = 0;
        int currentLeft = intervals[0][0];
        int currentRight = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= currentRight) {
                currentRight = Math.max(currentRight, intervals[i][1]);
            } else {
                total += currentRight - currentLeft;
                currentLeft = intervals[i][0];
                currentRight = intervals[i][1];
            }
        }
        total += currentRight - currentLeft;
        return total;
    }
}
```

---

## TravelingSalesmanMaximumWorkingDays.java

```java
package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Moche Goldberg is a traveling salesman. He works in N towns. Each day he sells his products in
 * one of the towns. The towns that are chosen on any two successive days should be different and a
 * town I can be chosen at most ci times. Write an algorithm to determine the number of days when he
 * can sell in the given towns following the above-mentioned rules.
 *
 * Input
 *
 * The first line of the input consists of an integer num, representing the number of towns (N).
 * The next line consists of N space-separated integers - countTown0, countTown1, ...,
 * countTownN-1 representing the number of times each town can be chosen.
 *
 * Output
 *
 * Print an integer representing the maximum number of days during which the salesman can work.
 *
 * Constraints
 *
 * 1 <= num <= 5 * 10^4
 * 1 <= countTown_i <= num
 * sum(countTown_i) <= 10^5
 * 0 <= i < N
 *
 * Example
 *
 * Input:
 * 3
 * 7 2 3
 *
 * Output:
 * 11
 *
 * Explanation:
 * The first, second and third towns are chosen 7, 2 and 3 times respectively.
 * The different towns are selected on successive days in a sequence:
 * first, second, first, third, first, second, first, third, first, third, first.
 * So, the maximum number of days during which a salesman can sell is 11.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：贪心结论。
 * 校对：约束来自 docx 截图公式区；最后一行截图肉眼略模糊，我按题意修正为更合理的 0 <= i < N。
 * 核心结论：若最大次数 max 大于其余次数总和加一，则答案是 2*others + 1；否则可以全部排满。
 */
public class TravelingSalesmanMaximumWorkingDays {

    public long maxWorkingDays(int[] townCounts) {
        long total = 0L;
        int max = 0;
        for (int count : townCounts) {
            total += count;
            max = Math.max(max, count);
        }
        long others = total - max;
        return max > others + 1 ? others * 2 + 1 : total;
    }
}
```

---

