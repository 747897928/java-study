# SHL Final Full Problems

> 说明
> 1. 这份文档是当前唯一主版本，也是当前最终工作版。
> 2. 这份文档保留完整题面，不做摘要化压缩。
> 3. 第一部分为 OCR 主文档中的 31 道题，第二部分为 docx 中额外补出的完整题面。
> 4. 只有在源文件之间明显冲突时，才做最小必要校对，并在题目附近注明。
> 5. 对于无法稳定恢复原题、但已经通过代码和样例校验形成自洽版本的题，会明确标注为“学习版定稿”。

## 1. 基本的幂运算和取模运算算法

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

Bob has to send a secret code **S** to his boss. He designs a method to encrypt the code using two key values **N** and **M**. The formula that he uses to develop the encrypted code is shown below.

`(((S^N % 10)^M) % 1000000007)`

Write an algorithm to help Bob encrypt the code.

**Input**

The input consists of an integer **secretCode**, representing the secret code (**S**).  
The second line consists of an integer **firstKey**, representing the first key value (**N**).  
The third line consists of an integer **secondKey**, representing the second key value (**M**).

**Output**

Print an integer representing the code encrypted by Bob.

**Constraints**

`1 ≤ secretCode ≤ 10^9`  
`0 ≤ firstKey, secondKey ≤ 1000000007`

**Example**

Input:
```text
2
3
4
```

Output:
```text
4096
```

**Explanation:**

S = 2, N = 3, M = 4 and the formula of the encrypted code is:

(((2^3 % 10)^4) % 1000000007) = 4096

> 校对说明：该示例输出已由 docx 源文件补全确认。


### 校对与理解备注
- 考点：快速幂、模运算、运算顺序。
- 难度：简单。
- 校对：示例输出 `4096` 已由 docx 原文和代码校验确认。
- 提示：题目不是直接算 `S^(N*M)`，而是先算 `S^N mod 10`，再对结果做 `M` 次幂。


### 代码实现与校验
- 对应类：[PowerModEncryption.java](../src/main/java/com/aquarius/wizard/leetcode/shl/PowerModEncryption.java)
- 关键方法：`encryptCode(long secretCode, long firstKey, long secondKey)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：题面稳定。

---

## 2. 基于优先队列（堆）的模拟算法（组织声誉更新）

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

In an organization, **N** employees with employee IDs from 1 to N, are working in different teams. Each employee shares a bond of great understanding with his/her fellow team members. Each employee is assigned an integer **X** that represents the employee's efficiency. The sum of efficiencies of all the employees indicates the reputation of the organization.

Edwin is appointed manager of the organization for **Q** days. Edwin, being short-tempered, fires one employee each day. Because the team members have a close relationship, **K** colleagues of the fired employee resign in protest. (These **K** colleagues have the least efficiency of the remaining team members.)

Kevin is the head of the database management system and has to update the reputation of the organization at the end of each day. Write an algorithm to help him determine the reputation of the organization at the end of each day for **Q** number of days.

**Input**

The first line of the input consists of an integer - **num**, representing the number of employees in the efficiency list (**num** is always equal to given number of employees **N**).  
The second line consists of **N** space-separated integers - **eff0, eff1, ...... effN-1**, representing the efficiency of the employees.  
The third line consists of an integer - **numT**, representing the number of employees with a team ID (**numT** is always equal to given number of employees **N**).  
The fourth line consists of **N** space-separated integers - **idE0, idE1, ...... idEN-1**, representing the team ID of the employees.  
The fifth line consists of two space-separated integers - **numDays** and **num**, representing the number of days for which Edwin is manager of the organization (**Q**) and the number of elements in each **Q** lines (**num/P** is always equal to 2).  
The last **Q** lines consist of **P** space-separated integers - **idFire** and **numResign**, representing the ID of the employee that was fired each day and the number of employees who resign along with the fired employee (**K**), respectively.

**Output**

Print **Q** space-separated integers representing the reputation of the organization at the end of each day.

**Constraints**

`1 ≤ num ≤ 10^4`  
`-10^9 ≤ eff0, eff1, ...... effN-1 ≤ 10^9`  
`1 ≤ idE0, idE1, ...... idEM-1 ≤ numT`  
`num = numT`  
`1 ≤ numDays ≤ num`  
`1 ≤ idFire ≤ num`  
`0 ≤ numResign ≤ c`, where **c** is the number of employees in the team on the current day

**Note**

The ID of a fired employee cannot be the ID of a person who has already resigned.

**Example**

Input:
```text
5
1 2 3 4 5
5
1 2 1 1 2
2 2
3 2
2 0
```

Output:
```text
7 5
```

**Explanation:**

There are two teams (ID's 1 and 2).  
The initial reputation of the organization is `1 + 2 + 3 + 4 + 5 = 15`.  
At the end of day 1, employee 3 is fired. Employee 1 and employee 4 resign. The revised reputation is `7` (`15 - 3 - 1 - 4`).  
At the end of day 2, employee 2 is fired but nobody resigns. The revised reputation is `5` (`7 - 2`).


### 校对与理解备注
- 考点：分组最小堆、懒删除、动态维护总和。
- 难度：困难。
- 校对：样例与题意一致，已做代码校验。
- 提示：每天离职的 `K` 个人只从“被开除员工所在团队”里选，且要跳过之前已经离职或被开除的人。


### 代码实现与校验
- 对应类：[OrganizationReputationUpdater.java](../src/main/java/com/aquarius/wizard/leetcode/shl/OrganizationReputationUpdater.java)
- 关键方法：`updateReputation(int[] efficiency, int[] teamIds, int[][] queries)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前实现按“每次只在对应团队里移除当前最小效率员工”理解。

---

## 3. 基于优先级队列（堆）的模拟算法（Shortest Job First）

**Question**

The current selected programming language is **Python**. We emphasize the submission of a fully working code over partially correct but efficient code. Use of certain header files is restricted. Once **submitted**, you cannot review this problem again. You can use `print` to debug your code. The `print` may not work in case of syntax/runtime error. The version of Python being used is **2.7**.

Shortest Job First (**SJF**) is a system for scheduling task requests.

Each task request is characterized by its request time (i.e., the time at which the task is submitted to the system) and its duration (i.e., the time needed to complete the task).

When the SJF system completes a task, it selects the task with the smallest duration to execute next. If multiple tasks have the same smallest duration, SJF selects the task with the earliest request time. The waiting time for a task is the difference between the request time and the actual start time (i.e., the time that it spends waiting for the system to execute it). You may assume that the tasks arrive in such frequency that the system executes tasks constantly and is never idle.

Given a list of request times and duration times, calculate the average task waiting time when scheduled using the Shortest Job First (**SJF**) algorithm.

**Input**

The first line of input consists of a positive integer - **req_size**, representing the number of tasks (**N**).  
The second line consists of **N** space-separated integers - **req[1], req[2], .... req[N]** representing the request time of the tasks.  
The third line consists of a positive integer - **dur_size**, representing the number of tasks (**M**).  
The last line consists of **M** space-separated integers - **[截图缺失，截图底部未完整显示，应为 duration 数组]**.

**Output**

**[截图缺失]**

**Constraints**

**[截图缺失]**

**Example**

**[截图缺失]**


### 校对与理解备注
- 考点：按到达时间排序 + 最小堆选最短任务。
- 难度：中等。
- 校对：这题的输出格式、约束和样例在 OCR 中缺失，属于高风险重建题。
- 提示：实现时建议兼容“当前没有可执行任务时把时钟跳到下一到达时间”，不要完全依赖题面里“系统永不空闲”的一句话。

---

## 4. 遍历计数算法（统计小于 K 的元素个数）

**Question**

The current selected programming language is **Java**.  
We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again.  
You can use `System.out.println()` to debug your code.  
The `System.out.println()` may not work in case of syntax/runtime error.  
The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

You are given a list of integers and an integer **K**. Write an algorithm to find the number of elements in the list that are strictly less than **K**.

**Input**

The first line of the input consists of an integer - **element_size**, representing the number of elements in the list (**N**).  
The second line consists of **N** space-separated integers - **element[1], element[2], ......, element[N]**, representing the list of integers.  
The last line consists of an integer - **num**, representing the integer to be compared (**K**).

**Output**

Print a positive integer representing the number of elements in the list that are strictly less than **num**.

**Constraints**

`-10^9 ≤ num ≤ 10^9`  
`-10^9 ≤ element[1], element[2], .........., element[N] ≤ 10^9`

**Example**

Input:
```text
7
1 7 4 5 6 3 2
5
```

Output:
```text
4
```

**Explanation:**

The numbers that are less than 5 are 1, 2, 3, 4.  
So, the output is 4.


### 校对与理解备注
- 考点：线性扫描计数。
- 难度：简单。
- 校对：题面稳定，没有明显歧义。
- 提示：这是非常基础的遍历题，刷的时候更适合练输入输出和边界值意识。


### 代码实现与校验
- 对应类：[CountElementsStrictlyLessThanK.java](../src/main/java/com/aquarius/wizard/leetcode/shl/CountElementsStrictlyLessThanK.java)
- 关键方法：`countLessThan(int[] nums, int target)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：题面稳定。

---

## 5. 遍历交换算法（将值替换为其索引）

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

You are given a list of **N** unique positive numbers ranging from 0 to (**N - 1**). Write an algorithm to replace the value of each number with its corresponding index value in the list.

**Input**

The first line of the input consists of an integer **size**, representing the size of the list (**N**).  
The next line consists of **N** space-separated integers, **arr[0], arr[1], ... arr[N-1]** representing the given list of numbers.

**Output**

Print **N** space-separated integers representing the list obtained by replacing the values of the numbers with their corresponding index values in the list.

**Constraints**

`0 < size ≤ 10^5`  
`0 ≤ arr[i] ≤ 10^5`  
`0 ≤ i < size`

**Note**

All the numbers in the list are unique.

**Example**

Input:
```text
4
3 2 0 1
```

Output:
```text
2 3 1 0
```

**Explanation:**

Before the change, the elements of the list are:  
`arr[0]=3`, `arr[1]=2`, `arr[2]=0` and `arr[3]=1`

After the change, the elements are:  
`arr[0]=2`, `arr[1]=3`, `arr[2]=1` and `arr[3]=0`


### 校对与理解备注
- 考点：数组映射、逆排列。
- 难度：简单。
- 校对：题意明确，和 docx 同类题面一致。
- 提示：因为元素是 `0..N-1` 且互不重复，所以本质是在求排列的逆。


### 代码实现与校验
- 对应类：[ReplaceValuesWithTheirIndexPositions.java](../src/main/java/com/aquarius/wizard/leetcode/shl/ReplaceValuesWithTheirIndexPositions.java)
- 关键方法：`replaceWithIndices(int[] nums)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：本质是 inverse permutation。

---

## 6. 遍历算法（统计数字出现次数）

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

Write an algorithm to find the number of occurrences of **needle** in a given positive number **haystack**.

**Input**

The first line of the input consists of an integer **needle**, representing a digit.  
The second line consists of an integer **haystack**, representing the positive number.

**Output**

Print an integer representing the number of occurrences of **needle** in **haystack**.

**Constraints**

`0 ≤ needle ≤ 9`  
`0 ≤ haystack ≤ 99999999`

**Example**

Input:
```text
2
123228
```

Output:
```text
3
```

**Explanation:**

**needle** 2 occurs 3 times in the **haystack**.


### 校对与理解备注
- 考点：按位统计、字符串法或数学法都可以。
- 难度：简单。
- 校对：题面稳定。
- 提示：别漏掉 `haystack = 0` 的边界，这种情况下如果 `needle = 0`，通常应该输出 `1`。


### 代码实现与校验
- 对应类：[CountDigitOccurrencesInNumber.java](../src/main/java/com/aquarius/wizard/leetcode/shl/CountDigitOccurrencesInNumber.java)
- 关键方法：`countOccurrences(int digit, int number)`
- 校验：ShlSampleValidator 样例和 `haystack = 0` 边界已覆盖。
- 说明：题面稳定。

---

## 7. 广度优先搜索（BFS）算法（字符串转换）

**Question**

**[截图顶部有遮挡，编程语言说明部分未完整显示，但可确认 main class 需为 "Solution"]**

**Note:** The main class name must be **"Solution"**.

Emerson is very fond of strings, and he keeps trying to reverse them. His mother gives him two binary strings and asks him to convert the binary string **str1** into **str2** by applying the following rules:

Step 1: Reverse any substring of length 2 (of **str1**) and get **str1'** (**str1 != str1'**).  
Step 2: Reverse any substring of length 3 (of **str1'**) and get **str1''** (**str1' != str1''**).  
Step 3: Reverse any substring of length 4 (of **str1''**) and get **str1'''** (**str1'' != str1'''**).  
Step 4, Step 5 and so on.

Write an algorithm to help Emerson convert the binary string **str1** into **str2**, in the minimum number of steps.

**Input**

The first line of the input consists of a binary string - **str1**.  
The second line consists of a binary string - **str2**.

**Output**

Print an integer representing the minimum number of steps required to convert **str1** into **str2**. If there is no such way of conversion, then print `"-1"`.

**Constraints**

`2 < N ≤ 30`, where **N** is the length of the strings.

**Note**

At any step Emerson can reverse only one substring **[截图缺失，后文未完整显示]**.

**Example**

**[截图缺失]**


### 校对与理解备注
- 考点：状态搜索、BFS、字符串变换。
- 难度：中等。
- 校对：原图能确认 Note 至少包含“每一步只能翻转一个子串”，但样例缺失。
- 提示：状态不仅是当前字符串，还要带上“当前是第几步、当前允许翻转的长度是多少”。

---

## 8. 哈希表（字典）算法（价格差为 K 的商品对）

**Question**

The manager of a supermarket wishes to hold an event at which he will distribute gift baskets to lucky customers. Each gift basket contains a pair of products. Each basket contains different product pairs, but the overall value of the baskets may be the same. There are **N** types of products and each product has a price. The gift baskets will be awarded to the customers that pick a product pair that has a difference in price equal to the given integer value **K**.

Write an algorithm to help the Manager find the total numbers of lucky customers who will win a gift basket.

**Input**

The first line of the input consists of an integer - **list_input_size**, representing the types of products (**N**).  
The second line consists of **N** space-separated integers - **list_input[0], list_input[1], ........ list_input[N-1]**, representing the price of the products.  
The last line consists of an integer - **K_input**, representing the given value **K**.

**Output**

Print an integer representing the total number of lucky customers who will win a gift basket.

**Constraints**

**[截图缺失]**

**Example**

**[截图缺失]**


### 校对与理解备注
- 考点：哈希查找、差值配对。
- 难度：简单。
- 校对：这是当前题库里歧义较大的题之一，样例和约束都缺失。
- 提示：我当前采用的是“不同商品类型组成的无序对都计数”的解释，所以若同价商品有多个，也会形成多个有效对。


### 代码实现与校验
- 对应类：[ProductPairsWithPriceDifferenceK.java](../src/main/java/com/aquarius/wizard/leetcode/shl/ProductPairsWithPriceDifferenceK.java)
- 关键方法：`countPairs(int[] prices, int difference)`
- 校验：ShlSampleValidator 用暴力对拍了一个小样例。
- 说明：当前实现采用“统计所有商品下标对/类型对”的解释；如果后续发现原题要求按不同价格值去重，需要再改。

---

## 9. 哈希集合（Hash Set）算法（统计两列表中不公共的元素）

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

You are given two lists of different lengths of positive integers. Write an algorithm to count the number of elements that are not common to each list.

**Input**

The first line of the input consists of an integer - **listInput1_size**, an integer representing the number of elements in the first list (**N**).  
The second line consists of **N** space-separated integers representing the first list of positive integers.  
The third line consists of an integer - **listInput2_size**, representing the number of elements in the second list (**M**).  
The last line consists of **M** space-separated integers representing the second list of positive integers.

**Output**

Print a positive integer representing the count of elements that are not common to both the lists of integers.

**Example**

Input:
```text
11
1 1 2 3 4 5 5 7 6 9 10
10
11 12 13 4 5 6 7 18 19 20
```

Output:
```text
12
```

**Explanation:**

The numbers that are not common to both lists are `[1, 1, 2, 3, 9, 10, 11, 12, 13, 18, 19, 20]`.  
So, the output is 12.

**补充：输入格式变体**

另一张重复截图显示了同一道题的另一种输入格式：  
第一行直接给出两个整数 `length1 length2`，分别表示两个列表的长度；  
第二行给出第一个列表；第三行给出第二个列表。  
题意与输出要求不变。


### 校对与理解备注
- 考点：哈希集合、成员判断。
- 难度：简单。
- 校对：代码校验后确认，这题不是多重集差集。只要某个值在另一边出现过，那么该值在本边的所有出现都不算“不公共”。
- 提示：更准确的理解是“统计两边那些值不在对方值集合中的元素个数”。


### 代码实现与校验
- 对应类：[CountElementsNotCommonToBothLists.java](../src/main/java/com/aquarius/wizard/leetcode/shl/CountElementsNotCommonToBothLists.java)
- 关键方法：`countNotCommon(int[] first, int[] second)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：代码校验后已修正：这题不是多重集差集，而是按“值是否出现在对方列表中”来统计。

---

## 10. 环检测及最长环寻找算法（圆桌就座）

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

A University has invited **N** alumni to a dinner. The dinner table is circular in shape. The university has assigned each alumnus an invitation ID from 1 to N. Each alumnus likes exactly one fellow alumnus and will attend the dinner only if he/she can be seated next to that person.

You are asked to plan the seating arrangement. Write an algorithm to find the maximum number of alumni who will attend the dinner.

**Input**

The first line of the input consists of an integer - **personId_size**, representing the number of alumni (**N**).  
The second line consists of **N** space-separated integers representing the ID of the person whom the **i-th** alumnus likes.

**Output**

Print an integer representing the maximum number of alumni who can attend the dinner.

**Note**

One alumnus can be liked by more than one alumni.

**Constraints**

`1 ≤ personId_size ≤ 10^5`  
`1 ≤ s[i] ≤ personId_size`

**Example**

Input:
```text
4
2 3 4 1
```

Output:
```text
4
```

**Explanation:**

1st alumnus likes the person with ID 2  
2nd likes the person with ID 3  
3rd likes the person with ID 4  
4th likes the person with ID 1

A maximum of 4 alumni can be seated around the circular table in the following manner:  
`1-2-3-4`


### 校对与理解备注
- 考点：函数图、环检测、最长环或双元环链条拼接。
- 难度：困难。
- 校对：题面与样例稳定，已做代码校验。
- 提示：这类题和 LeetCode 2127 非常像，本质是“每个点出度为 1”的图。


### 代码实现与校验
- 对应类：[CircularDinnerMaximumAttendees.java](../src/main/java/com/aquarius/wizard/leetcode/shl/CircularDinnerMaximumAttendees.java)
- 关键方法：`maxAttendees(int[] likedPerson)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：实现思路与 LeetCode 2127 相同。

---


## 11. 缓存淘汰模拟算法（FIFO Cache Misses）

**Question**

The current selected programming language is **Swift**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `print()` to debug your code. The `print()` may not work in case of syntax/runtime error. The version of **Swift** being used is **4.0.3**.

A virtual memory management system in an operating system uses First-In-First-Out (**FIFO**) cache.

When a requested memory page is not in the cache and the cache is full, the page that has been in the cache for the longest duration is removed to make room for the requested page. If the cache is not full, then the requested page can simply be added to the cache. A given page should occur once in the cache at most.

Given the maximum size of the cache and an array of page requests, calculate the number of cache misses. A cache miss occurs when a page is requested but is not found in the cache. Initially, the cache is empty.

**Input**

The first line of the input consists of a positive integer - **page_requests_size**, representing the total number of pages (**N**).  
The second line contains **N** space-separated positive integers - **page_requests[0], page_requests[1], ..., page_requests[N-1]**, representing the page requests for **N** pages.  
The last line consists of an integer - **max_cache_size**, representing the size of the cache.

**Output**

Print an integer representing the number of cache misses.

**Note**

Assume that the array **page_requests** contains pages numbered in the range `1-50`. A page at index `i` in **page_requests** is requested before a page at index `i+1`.

**Example**

Input:
```text
6
1 2 1 3 1 2
2
```

Output:
```text
5
```

**Explanation:**

Cache state as requests come in, ordered from longest-time-in-cache to shortest-time-in-cache:

```text
1 *
1 2 *
1 2
2 3 *
3 1 *
1 2 *
```

Asterisk (`*`) represents a cache miss, so the total number of cache misses is 5.


### 校对与理解备注
- 考点：队列模拟、缓存状态维护。
- 难度：简单。
- 校对：题面稳定。
- 提示：命中时 FIFO 顺序不更新，这一点和 LRU 不同，是这题最容易写错的地方。


### 代码实现与校验
- 对应类：[FifoCacheMissCounter.java](../src/main/java/com/aquarius/wizard/leetcode/shl/FifoCacheMissCounter.java)
- 关键方法：`countMisses(int[] requests, int cacheSize)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：命中时不会更新 FIFO 顺序。

---

## 12. 部分排序算法（前 K 个升序，后续降序）

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

You are given a list of integers of size **N**. Write an algorithm to sort the first **K** elements (from `list[0]` to `list[K-1]`) of the list in ascending order and the remaining (`list[K]` to `list[N-1]`) elements in descending order.

**Input**

The first line of the input consists of an integer - **inputList_size**, representing the number of elements in the list (**N**).  
The next line consists of **N** space-separated integers representing the elements of the list.  
The last line consists of an integer - **num**, representing the number of elements to be sorted in ascending order (**K**).

**Output**

Print **N** space-separated integers representing the sorted list.

**Constraints**

`num ≤ inputList_size`

**Example**

Input:
```text
8
11 7 5 10 46 23 16 8
3
```

Output:
```text
5 7 11 46 23 16 10 8
```

**Explanation:**

The first three elements must be arranged in increasing order and the remaining elements in decreasing order.  
So, the final list is `[5, 7, 11, 46, 23, 16, 10, 8]`.


### 校对与理解备注
- 考点：分段排序。
- 难度：简单。
- 校对：题面稳定。
- 提示：不是“先整体排序再切开”，而是前后两段各自独立排序。


### 代码实现与校验
- 对应类：[PartialSortWithAscendingPrefixAndDescendingSuffix.java](../src/main/java/com/aquarius/wizard/leetcode/shl/PartialSortWithAscendingPrefixAndDescendingSuffix.java)
- 关键方法：`partialSort(int[] nums, int splitIndex)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：按题面理解为前段单独升序、后段单独降序。

---

## 13. 排序 + 间隔取值算法（Alternate Sort）

**Question**

The current selected programming language is **Java 21**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **Java 21** being used is **21.0.4**.

An alternate sort of a list consists of alternate elements (starting from the first position) of the given list after sorting it in an ascending order. You are given a list of unsorted elements. Write an algorithm to find the alternate sort of the given list.

**Input**

The first line of the input consists of an integer **size**, representing the size of the given list (**N**).  
The second line consists of **N** space-separated integers **arr[0], arr[1], ..., arr[N-1]**, representing the elements of the input list.

**Output**

Print space-separated integers representing the alternately sorted elements of the given list.

**Constraints**

`0 < size ≤ 10^6`  
`-10^6 ≤ arr[i] ≤ 10^6`  
`0 ≤ i < size`

**Example**

Input:
```text
8
3 5 1 5 9 10 2 6
```

Output:
```text
1 3 5 9
```

**Explanation:**

After sorting, the list is `[1, 2, 3, 5, 5, 6, 9, 10]`.  
So, the alternate elements of the sorted list are `[1, 3, 5, 9]`.


### 校对与理解备注
- 考点：排序后按下标间隔取值。
- 难度：简单。
- 校对：题意明确。
- 提示：排序后取第 `0,2,4,...` 位元素，重复值照常保留。


### 代码实现与校验
- 对应类：[AlternateSortedElements.java](../src/main/java/com/aquarius/wizard/leetcode/shl/AlternateSortedElements.java)
- 关键方法：`alternateSort(int[] nums)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：取排序后的第 0,2,4,... 项。

---

## 14. 简单的遍历求和算法（公交线路覆盖距离）

**Question**

**[截图顶部缺失，编程语言说明未完整显示]**

The city bus stations are located at equal distances (unit distance) from each other along a straight road. Each station has a unique station ID. The buses do not travel to all of the bus stations. The highway administration needs to determine the total distance that the buses cover.

Given the IDs of the bus stations that have a bus operating between them, write an algorithm to help the administration find the distance covered by all the city buses.

**Input**

The first line of the input consists of two space-separated integers **num** and **constM**, representing the number of buses (**N**) and **constM** is always `2`.  
Next **N** lines consist of **constM** space-separated integers - **busStop0** and **busStop1** representing the IDs of the bus stations that have a bus operating between them.

**Output**

Print an integer representing the distance covered by the buses.

**Constraints**

`0 ≤ num ≤ 10^5`  
`1 ≤ busStop0 < busStop1 ≤ 10^6`

**Example**

Input:
```text
3 2
2 4
3 5
6 7
```

Output:
```text
4
```

**Explanation:**

From the diagram in the screenshot, the covered station segments are between `2-4`, `3-5`, and `6-7`.  
Their union on the road is `[2,5]` and `[6,7]`, so the total covered distance is `3 + 1 = 4`.


### 校对与理解备注
- 考点：区间合并。
- 难度：简单。
- 校对：题干顶部语言说明缺失，但核心语义稳定。
- 提示：站点等距意味着区间 `[l,r]` 的覆盖距离就是 `r-l`，不是包含端点个数。


### 代码实现与校验
- 对应类：[TotalBusRouteCoverageDistance.java](../src/main/java/com/aquarius/wizard/leetcode/shl/TotalBusRouteCoverageDistance.java)
- 关键方法：`totalDistance(int[][] routes)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：实现是标准区间合并。

---

## 15. 枚举算法（Martin 与父亲脚步重合最大化）

**Question**

**[截图顶部缺失，题干前半段未完整显示]**  
`[可见残句]` ... constant speed of **V1** meters per step for **N** steps.

Martin is standing at **X2** meters away from his home. He wonders how fast he must run at some constant speed of **V2** meters per step so as to maximize **F**, where **F** equals the number of his father's footsteps that Martin will land on during his run. It is given that the first step that Martin will land on, from his starting position, will have been landed on by his father.

Note that if more than one prospective velocity results in the same number of maximum common steps, output the highest prospective velocity as **V2**.

Write an algorithm to help Martin calculate **F** and **V2**.

**Input**

The first line of the input consists of an integer **fatherPos**, representing the initial position of Martin's father (**X1**).  
The second line consists of an integer **martinPos**, representing the initial position of Martin (**X2**).  
The third line consists of an integer **velFather**, representing the velocity of the father (**V1**).  
The last line consists of an integer **steps**, representing the number of steps taken by the father (**N**).

**Output**

Print two space-separated integers as the maximum number of common footsteps **F** and the corresponding speed **V2**.

**Constraints**

**[截图缺失]**

**Example**

**[截图缺失]**


### 校对与理解备注
- 考点：等差数列重合、数论枚举。
- 难度：中等。
- 校对：题干前半段、约束和样例都不完整，属于高风险题。
- 提示：可以把父亲和 Martin 的落脚点都看成等差数列，问题转成“两个等差数列的交点数最大化”。


### 代码实现与校验
- 对应类：[MaximumCommonFootstepsWithFather.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MaximumCommonFootstepsWithFather.java)
- 关键方法：`maximizeCommonSteps(long fatherPos, long martinPos, long fatherVelocity, long steps)`
- 校验：ShlSampleValidator 用小规模暴力做了对拍。
- 说明：这是基于可见英文重建出的数论模型，不是 100% 原题确认版。

---

## 16. 模拟算法（街道路灯状态更新）

**Question**

The current selected programming language is **Java 11**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `system.out.println` to debug your code. In case of syntax/runtime error, `system.out.println()` may not work. The version of **Java 11** being used is **11.0.2**.

Mr. Woods, an electrician has made some faulty connections of eight street lights in Timberland city. The connections are such that if the street lights adjacent to a particular light are both ON (represented as 1) or are both OFF (represented as 0), then that street light goes OFF the next night. Otherwise, it remains ON the next night. The two street lights at the end of the road have only a single adjacent street light, so the other adjacent light can be assumed to be always OFF. The state of the lights on a particular day is considered for the next day and not for the same day.

Due to this fault, the people of the city are facing difficulty in driving on the road at night. So, they have filed a complaint about this to the Head of the Federal Highway Administration. Based on this complaint the head has asked for the report of the state of street lights after **M** days.

Write an algorithm to output the state of the street lights after the given **M** days.

**Input**

The first line of input consists of an integer - **currentState_size**, representing the number of street lights (**N**).  
The next line consists of **N** space-separated integers - **currentState**, representing the current state of the street lights (i.e., either 0 or 1).  
The last line consists of an integer - **days**, representing the number of days (**M**).

**Output**

Print eight space-separated integers representing the state of the street lights after **M** days.

**Constraints**

`1 ≤ days ≤ 10^6`

**Example**

Input:
```text
8
1 1 1 0 1 1 1 1
2
```

Output:
```text
0 0 0 0 0 1 1 0
```

**Explanation**

The screenshot explanation is partially visible. It indicates that for the light at position 0, the adjacent lights are assumed to be `0` and `1`, so on the next day it becomes `1`; the remaining lights are updated using the same rule day by day.


### 校对与理解备注
- 考点：状态转移、细胞自动机、小状态找周期。
- 难度：中等。
- 校对：样例解释虽然不完整，但规则本身清楚。
- 提示：只有 8 盏灯，状态总数很小，`days` 很大时可以直接做周期压缩。


### 代码实现与校验
- 对应类：[StreetLightStateAfterMDays.java](../src/main/java/com/aquarius/wizard/leetcode/shl/StreetLightStateAfterMDays.java)
- 关键方法：`stateAfterDays(int[] currentState, int days)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：实现里做了状态找周期，便于处理大 days。

---

## 17. 树算法（叶子到叶子路径最大乘积）

**Question**

**[截图顶部编程语言说明未完整显示，可判断为 Java 11 题面变体]**

Arya is attempting to solve a math problem. In this problem, she is given a tree with **N** nodes, indexed from `1` to `N` where the root node is indexed as `1`. Each node of the tree has a defined value. She wants to trace a path from one leaf to another leaf in such a way that will award her the maximum score for that path. The score of a path is defined as the product of node values along the path.

**Input**

The first line of the input consists of an integer **N**, representing the number of nodes in the tree.  
The second line consists **N** space-separated integers representing the value of each node in the tree.  
The next **N-1** lines consist of two space-separated integers - **a** and **b**, representing the indices of the starting node and ending node of an edge of the tree.

**Output**

Print an integer representing the maximum possible score.

**Constraints**

`1 ≤ N ≤ 10^4`  
`-10^3 ≤ value ≤ 10^3`, where **value** is the value of a node

**Example**

Input:
```text
4
-1 2 3 2
1 2
1 3
3 4
```

Output:
```text
-12
```

**Explanation:**

There is only one route from leaf 2 to leaf 4, as there are only 2 leaves.

```text
(2)->(1) : Score = 2 * -1 = -2
(2)->(1)->(3) : Score = -2 * 3 = -6
(2)->(1)->(3)->(4) : Score = -6 * 2 = -12
```

So the best possible answer is `-12`.

**补充（另一截图中的输入格式变体）**

另一版本题面在边信息前增加了一行：

- 第三行 consists of two space-separated integers - **numEdges** and **numNodes**, representing the number of edges (**E**) and the number of nodes forming an edge (where **V = 2 always**), respectively.
- 接下来 **E** 行再给出边：**start** and **end**.

对应示例输入写法如下：

```text
4
-1 2 3 2
3 2
1 2
1 3
3 4
```

该变体与本题核心含义一致，只是把“接下来 N-1 行是边”改写成了“先给 E 和 V，再给 E 行边”。


### 校对与理解备注
- 考点：树形 DP、正负数乘积、叶子到叶子路径。
- 难度：困难。
- 校对：题意基本稳定，但要注意它求的是“乘积最大”，不是和最大。
- 提示：由于节点值可为负，DP 时要同时维护“最大乘积”和“最小乘积”候选。

---

## 18. 字符串算法（判断是否为右旋字符串）

**Question**

The current selected programming language is **Java 11**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `system.out.println` to debug your code. In case of syntax/runtime error, `system.out.println()` may not work. The version of **Java 11** being used is **11.0.2**.

Charlie has a magic mirror that shows the right-rotated versions of a given word. To generate different right rotations of a word, the word is written in a circle in a clockwise order and read it starting from any given character in a clockwise order until all the characters are covered. For example, in the word `"sample"`, if we start with `'p'`, we get the right rotated word as `"plesam"`.

Write an algorithm to output `1` if the **word1** is a right rotation of **word2** otherwise output `-1`.

**Input**

The first line of the input consists of a string **word1**, representing the first word.  
The second line consists of a string **word2**, representing the second word.

**Output**

Print `1` if the **word1** is a right rotation of **word2** otherwise print `-1`.

**Note**

Both **word1** and **word2** contain only lowercase letters between `a-z`.

**Example**

Input:
```text
sample
plesam
```

Output:
```text
1
```

**Explanation:**

For the word `"sample"`, if we start with `"p"`, we get the right-rotated word as `"plesam"`. There are six such right rotations of `"sample"`, including the original word. So, the output is `1`.


### 校对与理解备注
- 考点：字符串旋转、拼接包含判断。
- 难度：简单。
- 校对：题面稳定。
- 提示：若长度相同，则 `word1` 是否为 `word2` 的右旋，等价于 `word1` 是否是 `word2 + word2` 的子串。


### 代码实现与校验
- 对应类：[RightRotationStringCheck.java](../src/main/java/com/aquarius/wizard/leetcode/shl/RightRotationStringCheck.java)
- 关键方法：`isRightRotation(String word1, String word2)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：按 `word2 + word2` 包含 `word1` 的标准判定。

---

## 19. 排序 / 哈希算法（按频次降序排序）

**Question**

The current selected programming language is **C**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `printf()` to debug your code. The `printf()` may not work in case of syntax/runtime error. The version of **GCC** being used is **5.5.0**.

> 备注：同一道题在另一张重复截图中显示为 **Python 3.12**，题意一致。

Design a way to sort the list of positive integers in the descending order according to frequency of the elements. The elements with higher frequency come before those with lower frequency. Elements with the same frequency come in the same order as they appear in the given list.

**Input**

The first line of the input consists of an integer **num**, representing the number of elements in the list (**N**).  
The second line consists of **N** space-separated integers representing the elements in the list.

**Output**

Print **N** space-separated integers representing the elements of the list sorted according to the frequency of elements present in the given list.

**Example**

Input:
```text
19
1 2 2 3 3 3 4 4 5 5 5 5 6 6 6 7 8 9 10
```

Output:
```text
5 5 5 5 3 3 3 6 6 6 2 2 4 4 1 7 8 9 10
```

**Explanation:**

The element `5` has highest frequency.  
The elements `3` and `6` have same frequencies. So, their original order has been maintained in the output.  
Similarly the frequencies of rest of elements will be found and arranged.  
So, the output will be: `5 5 5 5 3 3 3 6 6 6 2 2 4 4 1 7 8 9 10`


### 校对与理解备注
- 考点：频次统计、稳定排序。
- 难度：简单。
- 校对：语言版本在截图中不一致，但题意一致。
- 提示：同频元素保持原出现顺序，不能按值大小再排序。


### 代码实现与校验
- 对应类：[FrequencyDescendingStableSort.java](../src/main/java/com/aquarius/wizard/leetcode/shl/FrequencyDescendingStableSort.java)
- 关键方法：`sortByFrequency(int[] nums)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：同频元素保持原出现顺序。

---

## 20. 线性扫描 / 排序算法（找最大空地的两栋房子）

**Question**

The current selected programming language is **Python3.12**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `print` to debug your code. The `print` may not work in case of syntax/runtime error. The version of **Python3.12** being used is **3.12**.

In a city there are **N** houses. Noddy is looking for a plot of land in the city on which to build his house. He wants to buy the largest plot of land that will allow him to build the largest possible house. All the houses in the city lie in a straight line and all of them have a house number and a second number indicating the position of the house from the entry point in the city. Noddy wants to find the houses between which he can build the largest possible house.

Write an algorithm to help Noddy find the house numbers between which he can build his largest possible house.

**Input**

The first line of the input consists of two space-separated integers - **num** and **val**, representing the number of houses (**N**) and the value **val** (where **val** is always equal to `2` representing the house number (**H**) and the position of houses (**P**) for **N** houses).  
The next **N** lines consist of two space-separated integers representing the house number (**Hᵢ**) and the position (**Pᵢ**), respectively.

**Output**

Print two space-separated integers representing the house numbers in ascending order between which the largest plot is available.

**Constraints**

`2 ≤ num ≤ 10^6`  
`1 ≤ Hᵢ ≤ 100`  
`0 ≤ Pᵢ < 10^6`  
`0 ≤ i < num`

**Note**

No two houses have the same position. In the case of multiple possibilities, print the one with the least distance from the reference point.

**Example**

Input:
```text
5 2
3 7
1 9
2 0
5 15
4 30
```

Output:
```text
4 5
```

**Explanation:**

The largest land area (size = 15 units) is available between the houses numbered 4 and 5. So, the output contains these house numbers in ascending order.


### 校对与理解备注
- 考点：按位置排序、相邻差值最大化。
- 难度：中等。
- 校对：题面稳定。
- 提示：先按位置排序，再看相邻两栋房子的间隔；若并列，取更靠近入口的那一段。


### 代码实现与校验
- 对应类：[LargestPlotBetweenHouses.java](../src/main/java/com/aquarius/wizard/leetcode/shl/LargestPlotBetweenHouses.java)
- 关键方法：`findHouseNumbers(int[][] houseLocations)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：并列时取字典序更小的房屋编号对。

---

## 21. 贪心 / 堆算法（到学校的最少果汁摊停靠次数）

**Question**

The current selected programming language is **Python3.12**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `print` to debug your code. The `print` may not work in case of syntax/runtime error. The version of **Python3.12** being used is **3.12**.

John misses his bus and has to walk all his way from home to school. The distance between his school and home is **D** units. He starts his journey with an initial energy of **K** units. His energy decreases by `1` unit for every unit of distance walked. On his way to school, there are **N** juice stalls. Each stall has a specific amount of juice in liters. His energy increases by `1` unit for every liter of juice he consumes. Note that in order to keep him walking he should have non-zero energy.

Write an algorithm to help John figure out the minimum number of juice stalls at which he should stop to successfully reach the school. In case he can't reach the school, the output will be `-1`.

**Input**

The first line of the input consists of an integer **N**, representing the number of juice stalls.  
The second line consists of **N** space-separated integers - **dist₁, dist₂, ..., distₙ** representing the distance of the `iᵗʰ` stall from John's home.  
The third line consists of **N** space-separated integers - **lit₁, lit₂, ..., litₙ** representing the liters of juice available at the `iᵗʰ` stall.  
The last line consists of two space-separated integers - **D** and **K** representing the distance of the school from John's home and his initial energy, respectively.

**Output**

Print an integer representing the minimum number of juice stalls at which John should stop to reach the school successfully.

**Constraints**

`1 ≤ N ≤ 10^4`  
`1 ≤ distᵢ < D ≤ 10^5`  
`1 ≤ litᵢ ≤ 1000`  
`1 ≤ i ≤ N`  
`0 ≤ K ≤ 10^5`

**Example**

Input:
```text
4
5 7 8 10
2 3 1 5
15 5
```

Output:
```text
3
```

**Explanation**

The screenshot explanation is only partially visible. It shows that John starts with energy 5, reaches the first stall, refuels, and continues greedily until he reaches the school after stopping at 3 stalls.


### 校对与理解备注
- 考点：最少补给次数、最大堆贪心。
- 难度：困难。
- 校对：样例已做代码校验。
- 提示：经典做法是“能走就先走，走不动时从路过的摊位里选补给量最大的一个回补”。


### 代码实现与校验
- 对应类：[MinimumJuiceStallStops.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MinimumJuiceStallStops.java)
- 关键方法：`minStops(int[] distances, int[] juices, int destination, int initialJuice)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：实现是经典最大堆贪心。

---

## 22. 枚举 / 数学算法（购买 N 个苹果的最小成本）

**Question**

**[截图仅保留了 Input/Output/Constraints/Example，下列题意根据可见字段整理]**

Josh wants to buy exactly **N** apples.  
Shop **A** sells apples only in lots of **M1** apples, and each such lot costs **P1**.  
Shop **B** sells apples only in lots of **M2** apples, and each such lot costs **P2**.  

Write an algorithm to find the minimum price at which Josh can buy the apples.

**Input**

The first line of input consists of an integer - **N**, representing the total number of apples that Josh wants to buy.  
The second line consists of two space-separated positive integers - **M1** and **P1**, representing the number of apples in a lot and the lot's price at shop A, respectively.  
The third line consists of two space-separated positive integers - **M2** and **P2**, representing the number of apples in a lot and the lot's price at shop B, respectively.

**Output**

Print a positive integer representing the minimum price at which Josh can buy the apples.

**Constraints**

`0 ≤ N, P1, P2`  
`1 ≤ M1, M2`

**Note**

There will always be at least one solution.  
There is only one lot size for a particular shop.  
There is an unlimited supply of apples for both the shops.

**Example**

Input:
```text
19
3 10
4 15
```

Output:
```text
65
```

**Explanation:**

Josh can buy five lots from the first shop and one lot from the second shop.  
So the total price is `(5 * 10 + 15) = 65`.


### 校对与理解备注
- 考点：枚举、线性丢番图方程。
- 难度：中等。
- 校对：题干前半段缺失，但输入输出和示例足以稳定重建。
- 提示：因为只有两种批量，通常枚举其中一种购买次数，再检查剩余是否能被另一种整除。


### 代码实现与校验
- 对应类：[MinimumApplePurchaseCost.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MinimumApplePurchaseCost.java)
- 关键方法：`minimumCost(int appleCount, int bundleOneSize, int bundleOneCost, int bundleTwoSize, int bundleTwoCost)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：通过枚举一类套餐次数求最优。

---

## 23. 字符串算法（不在首尾的最大连续信号长度）

**Question**

The current selected programming language is **Python3.12**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `print` to debug your code. The `print` may not work in case of syntax/runtime error. The version of **Python3.12** being used is **3.12**.

A digital machine generates binary data which consists of a string of `0`s and `1`s. A maximum signal **M**, in the data consists of the maximum number of either `1`s or `0`s appearing consecutively in the data but **M** can't be at the beginning or end of the string.

Design a way to find the length of the maximum signal.

**Input**

The first line of the input consists of an integer **N**, representing the length of the binary string.  
The second line consists of a string of length **N** consisting of `0`s and `1`s only.

**Output**

Print an integer representing the length of the maximum signal.

**Example 1**

Input:
```text
6
101000
```

Output:
```text
1
```

**Explanation:**

For `101000`, **M** can be `0` at second index or `1` at third index, so in both the cases `maxlength = 1`.

**Example 2**

Input:
```text
9
101111110
```

Output:
```text
6
```

**Explanation:**

For `101111110`, `M = 111111` so `maxlength = 6`.


### 校对与理解备注
- 考点：连续段统计。
- 难度：简单。
- 校对：约束缺失，但题意稳定。
- 提示：只统计“不接触字符串首尾”的连续段，所以前缀段和后缀段即使很长也不能直接算。


### 代码实现与校验
- 对应类：[MaximumInteriorSignalLength.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MaximumInteriorSignalLength.java)
- 关键方法：`maximumLength(String signal)`
- 校验：ShlSampleValidator 两个样例已覆盖。
- 说明：只统计完全位于中间的连续段。

---

## 24. 树算法（道路最大过路费收益）

**Question**

The current selected programming language is **Python3.12**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `print` to debug your code. The `print` may not work in case of syntax/runtime error. The version of **Python3.12** being used is **3.12**.

In a state, **N** cities with unique city codes from `1` to `N` are connected by `N-1` roads. The road network is in the form of a tree of which each road connects two cities. A path is a road, or a combination of roads, connecting any two cities. Each road has a toll booth that collects a toll equal to the maximum number of paths of which that particular road is part. The state transportation authority wishes to identify the road on which the maximum toll revenue is collected.

Write an algorithm to help the transportation authority identify the pair of cities connected by the road on which the maximum toll revenue is collected. The output should be sorted in increasing order. If more than one road collects the same total revenue, then output the pair of cities that have the smaller city code.

**Input**

The first line of the input consists of two space-separated integers - **N** and **R**, representing the number of cities in the state and the number of roads, respectively.  
The next **R** lines consist of two space-separated integers - **city1** and **city2**, representing the cities connected by a road.

**Output**

Print two space-separated sorted integers representing the cities connected by the road on which the maximum toll revenue is collected. If two or more toll booths collect the same total revenue, then print the pair of cities with lexicographically smaller codes.

**Constraints**

`2 ≤ N ≤ 10^5`  
`R = N - 1`  
`1 ≤ city1, city2 ≤ N`

**Note**

There is only one path between any two cities.  
One city can be connected to at most 10 other cities.

**Example**

Input:
```text
4 3
1 2
2 3
3 4
```

Output:
```text
2 3
```

**Explanation:**

Road `(2,3)` lies between the pairs of cities `(1,3)`, `(1,4)`, `(2,3)`, and `(2,4)`.  
So, the maximum toll collected by the road connecting cities `2` and `3` is `4`.


### 校对与理解备注
- 考点：树边贡献、子树规模计算。
- 难度：中等。
- 校对：样例已做代码校验。
- 提示：一条边被经过的路径数等于 `size * (N - size)`，其中 `size` 是这条边一侧连通块的节点数。


### 代码实现与校验
- 对应类：[RoadWithMaximumTollRevenue.java](../src/main/java/com/aquarius/wizard/leetcode/shl/RoadWithMaximumTollRevenue.java)
- 关键方法：`findBestRoad(int cityCount, int[][] roads)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前实现用树边贡献 `size * (N - size)`。

---

## 25. 几何 / 直线覆盖算法（覆盖所有 pickup locations 的最少直线路线）

> 原截图样例缺失。以下保留 OCR 主题语义，但明确改写为“学习版定稿题面”，不再把不可信的 `N ≤ 10^3` 继续留在公开题面里。

**Question**

The current selected programming language is **Python3.12**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `print` to debug your code. The `print` may not work in case of syntax/runtime error. The version of **Python3.12** being used is **3.12**.

A transportation company has begun service in a new city. They have identified several pickup locations in crowded areas of the city. A straight-line route can cover every pickup location that lies on that same straight line.

Write an algorithm to calculate the minimum number of straight-line routes required to cover all the pickup locations.

**Input**

The first line of the input consists of an integer **N**, representing the number of pickup locations.  
The next **N** lines each consist of two space-separated integers **x** and **y**, representing the coordinates of a pickup location.

**Output**

Print an integer representing the minimum number of straight-line routes required to cover all the pickup locations.

**Constraints**

`0 ≤ N ≤ 20`  
`-10^3 ≤ x, y ≤ 10^3`

**Note**

If multiple pickup locations have identical coordinates, they may be treated as one location.

**Example**

Input:
```text
8
1 4
2 3
2 1
3 2
4 1
5 0
4 3
5 4
```

Output:
```text
2
```

**Explanation**

The points `(2, 1)`, `(3, 2)`, `(4, 3)`, `(5, 4)` lie on one straight line.  
The points `(1, 4)`, `(2, 3)`, `(3, 2)`, `(4, 1)`, `(5, 0)` lie on another straight line.  
Therefore, the minimum number of straight-line routes is `2`.


### 校对与理解备注
- 考点：点集直线覆盖、状态压缩 DP。
- 难度：困难。
- 校对：这是当前题库里最不稳的一题之一。原 OCR 字面更像“最少直线覆盖所有点”，但原文中的 `N ≤ 10^3` 与精确解模型不匹配，因此这里明确改写为学习版定稿，不再把原约束继续保留为公开题面。
- 提示：当前学习版按“去重后点数不超过 20”理解，这样才能有精确、可验证的状态压缩解。

### 学习版定稿
- 题意：按 OCR 字面理解，求覆盖所有 pickup locations 所需的最少直线条数。
- 约束修正：当前学习版按“去重点数 `≤ 20`”定稿。
- 保留说明：另有候选重建版 `MinimumStraightLineRoutesFromBaseToPickupLocations.java`，更像某个“从 base 出发按方向计数”的同源题，但它不是 A25 当前主版本。


### 代码实现与校验
- 对应类：[MinimumStraightLineCoverForPickupLocations.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MinimumStraightLineCoverForPickupLocations.java)
- 关键方法：`minimumRoutes(int[][] pickupLocations)`
- 校验：ShlSampleValidator 覆盖了外部补证样例和几个小边界。
- 说明：这是 A25 当前采用的最终学习版代码；候选重建版 `MinimumStraightLineRoutesFromBaseToPickupLocations.java` 仅作为线索保留。

---

## 26. 深度优先搜索（DFS）算法（网格中最大房屋面积）

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

The city authorities conduct a study of the houses in a residential area for a city planning scheme. The area is depicted in an aerial view and divided into an `N x M` grid. If a grid cell contains some part of a house roof, then it is assigned the value `1`; otherwise, the cell represents a vacant plot and is assigned the value `0`. Clusters of adjacent grid cells with value `1` represent a single house. Diagonally placed grids with value `1` do not represent a single house. The area of a house is the number of `1`s that it spans.

Write an algorithm to find the area of the largest house.

**Input**

The first line of the input consists of two space-separated integers - **rows** and **cols** representing the number of rows (**N**) and the number of columns in the grid (**M**), respectively.  
The next **N** lines consist of **M** space-separated integers representing the grid.

**Output**

Print an integer representing the area of the largest house.

**Constraints**

The elements of the grid consist of `0`s and `1`s only.

**Example**

Input:
```text
5 5
0 0 0 0 0
0 1 1 0 0
0 0 0 0 0
0 0 1 1 0
0 0 1 0 0
```

Output:
```text
3
```

**Explanation:**

The area of the biggest house is `3`.  
So, the output is `3`.


### 校对与理解备注
- 考点：网格连通块、DFS/BFS。
- 难度：中等。
- 校对：题面稳定。
- 提示：注意是四连通，不算对角线相连。


### 代码实现与校验
- 对应类：[LargestHouseAreaInGrid.java](../src/main/java/com/aquarius/wizard/leetcode/shl/LargestHouseAreaInGrid.java)
- 关键方法：`largestArea(int[][] grid)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：按四连通统计最大连通块。

---

## 27. 数值判断 + 遍历算法（统计账单金额为完全平方数的客户数）

**Question**

**[截图顶部编程语言说明缺失]**

A company is planning a big sale at which they will give their customers a special promotional discount. Each customer that purchases a product from the company has a unique **customerID** numbered from `0` to `N-1`. Andy, the marketing head of the company, has selected bill amounts of the `N` customers for the promotional scheme. The discount will be given to the customers whose bill amounts are perfect squares. The customers may use this discount on a future purchase.

Write an algorithm to help Andy find the number of customers that will be given discounts.

**Input**

The first line of the input consists of an integer **numOfCust** representing the number of customers whose bills are selected for the promotional discount (**N**).  
The second line consists of **N** space-separated integers - **bill0, bill1, ......, billN-1** representing the bill amounts of the `N` customers selected for the promotional discount.

**Output**

Print an integer representing the number of customers that will be given discounts.

**Constraints**

`0 ≤ numOfCust ≤ 10^6`  
`0 ≤ bill_i ≤ 10^6`  
`0 ≤ i < numOfCust`

**Example**

Input:
```text
6
25 77 54 81 48 34
```

Output:
```text
2
```


### 校对与理解备注
- 考点：完全平方数判断。
- 难度：简单。
- 校对：题面稳定。
- 提示：如果数据里可能出现 `0`，它也是完全平方数。


### 代码实现与校验
- 对应类：[PerfectSquareBillCount.java](../src/main/java/com/aquarius/wizard/leetcode/shl/PerfectSquareBillCount.java)
- 关键方法：`countPerfectSquares(int[] bills)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前实现把 0 也视作完全平方数。

---

## 28. 稳定的双指针算法（偶数在前，奇数在后）

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

You are playing an online game. In the game, a list of `N` numbers is given. The player has to arrange the numbers so that all the odd numbers of the list come after the even numbers.

Write an algorithm to arrange the given list such that all the odd numbers of the list come after the even numbers.

**Input**

The first line of the input consists of an integer **num**, representing the size of the list (**N**).  
The second line of the input consists of **N** space-separated integers representing the values of the list.

**Output**

Print **N** space-separated integers such that all the odd numbers of the list come after the even numbers.

**Note**

The relative order of odd numbers and the relative order of even numbers in the output should be same as given in the input.

**Example**

Input:
```text
8
10 98 3 33 12 22 21 11
```

Output:
```text
10 98 12 22 3 33 21 11
```

**Explanation:**

All the even numbers are placed before all the odd numbers.


### 校对与理解备注
- 考点：稳定分组、双数组或稳定双指针。
- 难度：简单。
- 校对：题面稳定。
- 提示：题目明确要求“相对顺序不变”，所以不能直接做普通原地交换分区。


### 代码实现与校验
- 对应类：[StableEvenOddPartition.java](../src/main/java/com/aquarius/wizard/leetcode/shl/StableEvenOddPartition.java)
- 关键方法：`rearrange(int[] nums)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：保持偶数内部和奇数内部原顺序不变。

---

## 29. 线性扫描 / 数学算法（最少项目使所有错误分数归零）

**Question**

The current selected programming language is **Java**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **JDK** being used is **1.8**.

**Note:** The main class name must be **"Solution"**.

Ethan is the leader of a team with `N` members. He has assigned an error score to each member in his team based on the bugs that he has found in that particular team member's task. Because the error score has increased to a significantly large value, he wants to give all the team members a chance to improve their error scores, thereby improving their reputation in the organization. He introduces a new rule that whenever a team member completes a project successfully, the error score of that member decreases by a count `P` and the error score of all the other team members whose score is greater than zero decreases by a count `Q`.

Write an algorithm to help Ethan find the minimum number of projects that the team must complete in order to make the error score of all the team members zero.

**Input**

The first line of the input consists of an integer - **errorScore_size**, representing the total number of team members (**N**).  
The second line consists of **N** space-separated integers - **errorScore**, representing the initial error scores of the team members.  
The third line consists of an integer - **compP**, representing the count by which the error score of the team member who completes a project successfully decreases (**P**).  
The last line consists of an integer - **othQ**, representing the count by which the error score of the team member whose error score is greater than zero decreases (**Q**).

**Output**

Print an integer representing the minimum number of projects that the team must complete in order to make the error score of all the team members zero. If no project need to be completed then print `0`.

**Constraints**

`1 ≤ errorScore_size ≤ 2*10^5`  
`1 ≤ othQ ≤ compP ≤ 10^9`  
`0 ≤ errorScore ≤ 10^9`

**Note**

The error score of any team member can never be less than zero.

**Example**

Input:
```text
3
6 4 1
4
1
```

Output:
```text
3
```


### 校对与理解备注
- 考点：答案二分、贪心可行性判断。
- 难度：困难。
- 校对：约束在 OCR 和 docx 中有冲突，我保留了更可信的 `2*10^5` 版本；样例已代码校验。
- 提示：常见做法是二分项目数 `mid`，判断在做了 `mid` 次“全体减 Q”之后，还需要多少次“额外减 (P-Q)”才能清零。


### 代码实现与校验
- 对应类：[MinimumProjectsToZeroErrorScores.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MinimumProjectsToZeroErrorScores.java)
- 关键方法：`minimumProjects(long[] errorScores, long projectDecrease, long otherDecrease)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前实现是答案二分 + 可行性判断。

---

## 30. 环检测 / 构造算法（圆桌晚宴输出字典序最小参会 ID）

**Question**

The current selected programming language is **Java 11**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `system.out.println` to debug your code. The `system.out.println()` may not work in case of syntax/runtime error. The version of **Java 11** being used is **11.0.2**.

A University has invited `N` alumni for a dinner. The dinner table has a circular shape. Each alumnus is assigned an invitation ID from `1` to `N`. Each alumnus likes exactly one fellow alumnus and will attend the dinner only if he/she can be seated next to the person he/she likes.

Write an algorithm to find the IDs of the alumni in a lexicographical order so that maximum number of alumni attend the dinner. If more than one such seating arrangement exists, then output the one that is lexicographically smaller.

**Input**

The first line of the input consists of an integer **num**, representing the number of alumni (**N**).  
The second line consists of **N** space-separated integers, **alumniID[0], alumniID[1], ...... alumniID[N-1]** representing the ID of the person whom the `i`th alumnus likes.

**Output**

Print space-separated integers representing the IDs of the alumni who will attend the dinner.

**Note**

One alumnus can be liked by multiple alumni.

**Constraints**

`1 ≤ num ≤ 10^5`  
`0 ≤ i < num`

**Example**

Input:
```text
4
2 3 4 1
```

Output:
```text
1 2 3 4
```

**Explanation:**

The first alumnus likes the person whose ID is `2`.  
The second alumnus likes the person whose ID is `3`.  
The third alumnus likes the person whose ID is `4`.  
The fourth alumnus likes the person whose ID is `1`.  
A maximum of `4` alumni can be seated around the circular table in the following manner: `1-2-3-4`.


### 校对与理解备注
- 考点：函数图构造、最优方案恢复、字典序比较。
- 难度：困难。
- 校对：我已把编号从题面里可疑的 `0..N-1` 修正为与示例一致的 `1..N`；同时根据原图，示例输入明确是两行：第一行 `N`，第二行 likes 数组，不是一行扁平输入。
- 提示：这题比 A10 难，因为不仅要算最多人数，还要恢复“字典序最小”的那组参会 ID。


### 代码实现与校验
- 对应类：[LexicographicallySmallestMaximumDinnerGuestIds.java](../src/main/java/com/aquarius/wizard/leetcode/shl/LexicographicallySmallestMaximumDinnerGuestIds.java)
- 关键方法：`findGuestIds(int[] likesOneBased)`
- 校验：ShlSampleValidator 覆盖了手写样例，并对小规模随机图做了暴力真值比对。
- 说明：当前实现默认输出的是“字典序最小的参会 ID 升序集合”，不是具体座位顺序。

---

## 31. 字符串算法（移除元音字母）

**Question**

The current selected programming language is **Python3.12**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `print` to debug your code. The `print` may not work in case of syntax/runtime error. The version of **Python3.12** being used is **3.12**.

The vowels in the English alphabet are: `(a, e, i, o, u, A, E, I, O, U)`. Write an algorithm to eliminate all vowels from a given string.

**Input**

The input consists of the given string.

**Output**

Print a string after removing all the vowels from the given string.

**Constraints**

The given string contains English alphabets only.

**Example**

Input:
```text
MynameisAnthony
```

Output:
```text
Mynmsnthny
```

**Explanation:**

After removing the vowels, the string is `Mynmsnthny`.

**补充说明**

另一张截图使用了“房屋翻修”叙事版本：只有标记为元音字母的房屋会被翻修，要求输出不会被翻修的房屋列表。其输入输出本质与本题相同，也就是“从字符串中移除所有元音字母”。

---




### 校对与理解备注
- 考点：字符过滤。
- 难度：简单。
- 校对：题面稳定，另一个“房屋翻修”版本本质相同。
- 提示：这是纯字符串题，适合你练英文题面里“eliminate/remove/filter”这类常见表达。


### 代码实现与校验
- 对应类：[RemoveVowelsFromString.java](../src/main/java/com/aquarius/wizard/leetcode/shl/RemoveVowelsFromString.java)
- 关键方法：`removeVowels(String text)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：题面稳定。

---

## 二、docx 补充题面（完整原文保留）


---

## B01. 实习期最大工资 / Maximum Internship Salary

**Question**

The current selected programming language is Java 11. We emphasize the submission of a fully working code over partially correct but efficient code. Once submitted, you cannot review this problem again. You can use System.out.println() to debug your code. The System.out.println() may not work in case of syntax/runtime error. The version of Java 11 being used is 11.0.2.

Note: The main class name must be "Solution".

Stephen is doing an internship in a company for N days. Each day, he may choose an easy task or a difficult task. He may also choose to perform no task at all. He chooses a difficult task on days when and only when he did not perform any work the previous day. The amounts paid by the company for both the easy and difficult tasks can vary each day, but the company always pays more for difficult tasks.

Write an algorithm to help Stephen earn the maximum salary.

**Input**

The first line of the input consists of two space-separated integers: num and type, representing the number of days of the internship (N) and types of tasks available for each day (M is always equal to 2, respectively).

The next N lines consist of M space-separated integers: easy and hard, representing the amount set to be paid for the easy task and the difficult task on that day, respectively.

**Output**

Print an integer representing the maximum salary that Stephen can earn.

**Constraints**

1 ≤ num ≤ 10⁵

type = 2

2 ≤ hard ≤ 10⁴

1 ≤ easy < hard

**Example**

Input:
```text
4 2
1 2
4 10
20 21
2 23
```

Output:
```text
33
```

**Explanation**

To earn the maximum salary, select the difficult task (10) from the 2nd row and the difficult task (23) from the 4th row. The maximum salary earned = 10 + 23 = 33.



### 校对与理解备注
- 考点：状态 DP。
- 难度：中等。
- 校对：样例已做代码校验。
- 提示：每天至少要区分“昨天休息/昨天做简单/昨天做困难”这几种状态，困难任务只能从“昨天休息”转移过来。


### 代码实现与校验
- 对应类：[MaximumInternshipSalary.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MaximumInternshipSalary.java)
- 关键方法：`maxSalary(int[][] pay)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前实现按“困难任务前一天必须休息”理解。

---

## B02. 书展购书最大星级 / Maximum Total Rating with Horror and Sci-Fi Books

> docx 在样例末尾被截断，以下保留当前能提取到的完整原文。

**Question**

The current selected programming language is Java. We emphasize the submission of a fully working code over partially correct but efficient code. Once submitted, you cannot review this problem again. You can use System.out.println() to debug your code. The System.out.println() may not work in case of syntax/runtime error. The version of JDK being used is 1.8.

Note: The main class name must be "Solution".

Sheldon is going to a book fair where all the books are star-rated. As he is interested in just two types of books, Horror and Sci-fi, so he would buy the books from these two categories only. He would want to buy at least one book from each category so as to maximize the total star-rating of his books. Also, the total price of the books should not exceed the amount of money that he can spend. The output is -1 if it is not possible to buy at least one book from both the categories with the money that he has.

Write an algorithm to help Sheldon buy the books from both the categories.

**Input**

The first line of the input consists of an integer - amount, representing the amount of money Sheldon can spend.

The second line consists of two integers - numHorror and numH, representing the number of Horror books (H) and the number of values given for every horror book (X is always equal to 2, respectively).

The next H lines consist of X space-separated integers - hrating and hprice, representing the star-rating and the price of each Horror book, respectively.

The next line consists of two space-separated integers - numSciFi and numS, representing the number of Sci-fi books (S) and the number of values given for every Sci-fi book (P is always equal to 2, respectively).

The last S lines consist of P space-separated integers - srating and sprice, representing the star-rating and the price of each Sci-fi book, respectively.

**Output**

Print an integer representing the total maximum star-rating of books bought by Sheldon. If he cannot buy at least one book from both the categories then print -1.

**Constraints**

1 ≤ numHorror ≤ 1000

1 ≤ numSciFi ≤ 1000

1 ≤ amount ≤ 10⁵

1 ≤ hrating, srating ≤ 10⁶

1 ≤ hprice, sprice ≤ 10⁵

numH = 2

numS = 2

**Example**

Input:
```text
50
3 2
5 10
3 30
6 20
3 2
6 30
2 10
```



### 校对与理解备注
- 考点：分组背包或预算 DP。
- 难度：困难。
- 校对：样例末尾在 docx 中被截断，输出缺失。
- 提示：从题意看不是“各选一本”而是“总花费不超预算且两类都至少买一本”，所以更像带分组约束的 0/1 背包。


### 代码实现与校验
- 对应类：[MaximumTotalRatingWithHorrorAndSciFiBooks.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MaximumTotalRatingWithHorrorAndSciFiBooks.java)
- 关键方法：`maximumRating(int amount, int[][] horrorBooks, int[][] sciFiBooks)`
- 校验：ShlSampleValidator 用小规模暴力对拍了一个样例。
- 说明：当前实现把每本书视为最多购买一次。

---

## B03. 翻 K 个 0 后得到最长连续 1 的最优窗口数 / Number of Ways to Obtain the Longest Consecutive Ones

> 原始英文存在明显歧义。以下为与当前代码一致的“学习版定稿题面”。

**Question**

The current selected programming language is Java. We emphasize the submission of a fully working code over partially correct but efficient code. Once submitted, you cannot review this problem again. You can use System.out.println() to debug your code. The System.out.println() may not work in case of syntax/runtime error. The version of JDK being used is 1.8.

Note: The main class name must be "Solution".

Given a binary string **S** consisting only of `0`s and `1`s, you may change at most **K** zeros inside a substring into ones. Let **L** be the maximum possible length of a substring that can be turned into all ones in this way.

Write an algorithm to find the number of substrings whose length is exactly **L** and that contain at most **K** zeros.

**Input**

The first line of the input consists of the string **S**.  
The second line consists of an integer **changeK**, representing the maximum number of zeros that can be changed (**K**).

**Output**

Print an integer representing the number of substrings whose length is equal to the maximum achievable value **L** and that contain at most **K** zeros.

**Constraints**

`1 ≤ size of string ≤ 2*10^5`  
`0 ≤ changeK ≤ size`

**Example**

Input:
```text
1010101
1
```

Output:
```text
3
```

**Explanation**

The maximum achievable length is `3`.  
There are exactly three substrings of length `3` that contain at most one `0`: the three occurrences of `101`.  
Each such substring can be turned into `111` by changing one `0`.  
So, the output is `3`.


### 校对与理解备注
- 考点：滑动窗口。
- 难度：中等。
- 校对：原题里的 `different ways` 有明显歧义，且样例 `1010101 / 1 -> 3` 无法区分“最优窗口数”和“不同结果串数”两种解释。为了让题面和代码稳定一致，这里明确改写为“统计最优窗口数”的学习版定稿。
- 提示：当前学习版不是统计不同结果串，也不是统计翻转位置集合，而是统计“长度等于最优值、且窗口内 `0` 的个数不超过 `K`”的窗口数量。

### 学习版定稿
- 题意：统计长度等于最优值、且窗口内 `0` 的个数不超过 `K` 的窗口数量。
- 关键区别：不是统计不同结果串，也不是统计翻转方案集合。
- 采用原因：这个解释与 `2*10^5` 约束匹配，能用标准滑动窗口在线求解，也与当前代码和校验器完全一致。


### 代码实现与校验
- 对应类：[NumberOfWaysToObtainTheLongestConsecutiveOnes.java](../src/main/java/com/aquarius/wizard/leetcode/shl/NumberOfWaysToObtainTheLongestConsecutiveOnes.java)
- 关键方法：`countWays(String binaryString, int changeK)`
- 校验：ShlSampleValidator 覆盖了样例，并对小规模随机串做了“窗口数解释”的暴力对拍。
- 说明：这是 B03 当前采用的最终学习版代码；校验器里保留了 `S = 101, K = 0` 这种能区分“窗口数”和“结果串数”的反例，方便后续继续考证。

---

## B04. 第 K 小股票相对价格 / K-th Smallest Relative Stock Price

**Question**

The current selected programming language is Java. We emphasize the submission of a fully working code over partially correct but efficient code. Once submitted, you cannot review this problem again. You can use System.out.println() to debug your code. The System.out.println() may not work in case of syntax/runtime error. The version of JDK being used is 1.8.

Note: The main class name must be "Solution".

Andrew is a stock trader who trades in N selected stocks. He has calculated the relative stock price changes in the N stocks from the previous day stock prices. Now, his lucky number is K, so he wishes to invest in the particular stock that has the Kth smallest relative stock value.

Write an algorithm for Andrew to find the Kth smallest stock price out of the selected N stocks.

**Input**

The first line of the input consists of an integer - stock_size, representing the number of selected stocks (N).

The second line consists of N space-separated integers - stock₀, stock₁, …, stockₙ₋₁, representing the relative stock prices of the selected stocks.

The third line consists of an integer - valueK, representing the value K for which he wishes to find the stock price.

**Output**

Print an integer representing the Kth smallest stock price of selected N stocks.

**Constraints**

0 < valueK ≤ stock_size ≤ 10⁶

0 ≤ stockᵢ ≤ 10⁶

0 ≤ i < stock_size

**Example**

Input:
```text
5
10 5 7 88 19
3
```

Output:
```text
10
```

**Explanation**

The sorted relative stock prices are [5, 7, 10, 19, 88].

So, the 3rd smallest stock price is 10.



### 校对与理解备注
- 考点：排序、快速选择。
- 难度：简单。
- 校对：样例合理。
- 提示：如果只是做题训练，先排序最直接；如果追求更优复杂度，再考虑 quickselect。


### 代码实现与校验
- 对应类：[KthSmallestRelativeStockPrice.java](../src/main/java/com/aquarius/wizard/leetcode/shl/KthSmallestRelativeStockPrice.java)
- 关键方法：`kthSmallest(int[] stockPrices, int k)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前先用排序实现。

---

## B05. 推销员最多工作天数 / Maximum Working Days for the Traveling Salesman

**Question**

Moche Goldberg is a traveling salesman. He works in N towns. Each day he sells his products in one of the towns. The towns that are chosen on any two successive days should be different and a town I can be chosen at most ci times. Write an algorithm to determine the number of days when he can sell in the given towns following the above-mentioned rules.

**Input**

The first line of the input consists of an integer num, representing the number of towns (N).

The next line consists of N space-separated integers - countTown0, countTown1, ..., countTownN-1 representing the number of times each town can be chosen.

**Output**

Print an integer representing the maximum number of days during which the salesman can work.

**Constraints**

`1 ≤ num ≤ 5 × 10^4`

`1 ≤ countTown_i ≤ num`

`∑ countTown_i ≤ 10^5`

`0 ≤ i < N`

> 校对说明：以上约束来自 docx 中的截图公式区域；最后一行截图肉眼略模糊，我按题意修正为更合理的 `0 ≤ i < N`。

**Example**

Input:
```text
3
7 2 3
```

Output:
```text
11
```

**Explanation**

The first, second and third towns are chosen 7, 2 and 3 times respectively.

The different towns are selected on successive days in a sequence: first, second, first, third, first, second, first, third, first, third, first.

So, the maximum number of days during which a salesman can sell is 11.



### 校对与理解备注
- 考点：贪心结论、最大值与其余元素和的关系。
- 难度：中等。
- 校对：样例已做代码校验。
- 提示：结论通常是：若最大次数 `max` 不超过其余总和加一，则全都能排；否则答案是 `2*others + 1`。


### 代码实现与校验
- 对应类：[TravelingSalesmanMaximumWorkingDays.java](../src/main/java/com/aquarius/wizard/leetcode/shl/TravelingSalesmanMaximumWorkingDays.java)
- 关键方法：`maxWorkingDays(int[] townCounts)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：使用 `max <= others + 1 ? total : 2 * others + 1` 结论。

---

## B06. 网络传输中丢失的字符 / Missing Character During Transmission

**Question**

The current selected programming language is Java. We emphasize the submission of a fully working code over partially correct but efficient code. Once submitted, you cannot review this problem again. You can use System.out.println() to debug your code. The System.out.println() may not work in case of syntax/runtime error. The version of JDK being used is 1.8.

Note: The main class name must be "Solution".

A company provides network encryption for secure data transfer. The data string is encrypted prior to transmission and gets decrypted at the receiving end. But due to some technical error, the encrypted data is lost and the received string is different from the original string by 1 character. Arnold, a network administrator, is tasked with finding the character that got lost in the network so that the bug does not harm other data that is being transferred through the network.

Write an algorithm to help Arnold find the character that was missing at the receiving end but present at the sending end.

**Input**

The first line of the input consists of a string - stringSent, representing the string that was sent through the network.

The next line consists of a string - stringRec, representing the string that was received at the receiving end of the network.

**Output**

Print a character representing the character that was lost in the network during transmission.

**Note**

The input strings stringSent and stringRec consist of lowercase and uppercase English alphabets (i.e. a-z, A-Z).

**Example**

Input:
```text
abcdfigerj
abcdfiger
```

Output:
```text
j
```

**Explanation**

The character j at the end of the sent string was lost in the network during transmission.



### 校对与理解备注
- 考点：异或、计数。
- 难度：简单。
- 校对：样例已做代码校验。
- 提示：这类“一个字符串比另一个少一个字符”的题，用 XOR 很简洁。


### 代码实现与校验
- 对应类：[MissingCharacterDuringTransmission.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MissingCharacterDuringTransmission.java)
- 关键方法：`findMissingCharacter(String sent, String received)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前用 XOR 实现。

---

## B07. 忽略大小写统计子串出现次数 / Count Case-Insensitive Occurrences of a Substring

**Question**

The current selected programming language is Java. We emphasize the submission of a fully working code over partially correct but efficient code. Once submitted, you cannot review this problem again. You can use System.out.println() to debug your code. The System.out.println() may not work in case of syntax/runtime error. The version of JDK being used is 1.8.

Note: The main class name must be "Solution".

You are given two strings containing only English letters. Write an algorithm to count the number of occurrences of the second string in the first string. (You may disregard the case of the letters.)

**Input**

The first line of the input consists of a string parent, representing the first string.

The second line consists of a string sub, representing the second string.

**Output**

Print an integer representing the number of occurrences of sub in parent. If no occurrence of sub is found in parent, then print 0.

**Example**

Input:
```text
TimisplayinginthehouseofTimwiththetoysofTim
Tim
```

Output:
```text
3
```

**Explanation**

Tim occurs 3 times in the first string.

So, the output is 3.



### 校对与理解备注
- 考点：字符串匹配、大小写归一化。
- 难度：简单。
- 校对：题面完整，样例已做代码校验。
- 提示：更稳妥的理解是“统计所有起点上的匹配次数”，也就是允许重叠匹配。


### 代码实现与校验
- 对应类：[CaseInsensitiveSubstringOccurrenceCount.java](../src/main/java/com/aquarius/wizard/leetcode/shl/CaseInsensitiveSubstringOccurrenceCount.java)
- 关键方法：`countOccurrences(String parent, String sub)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前实现允许重叠匹配。

---

## B08. 糖果盒交付最短时间 / Minimum Time to Deliver Sweet Boxes

> docx 在样例部分被截断。以下为与当前实现一致的学习版定稿，不继续沿用不可信的大规模约束，也不保留“最小时间对 1000007 取模”这一可疑规则。

**Question**

The current selected programming language is **Java 11**. We emphasize the submission of a fully working code over partially correct but efficient code. Once submitted, you cannot review this problem again. You can use `System.out.println()` to debug your code. The `System.out.println()` may not work in case of syntax/runtime error. The version of **Java 11** being used is **11.0.2**.

William is the owner of a sweet shop. His current machine takes some time to prepare one box of sweets. He receives an order for a fixed number of boxes and wants to finish the order as soon as possible within a limited budget.

To fulfill the order, William may:
1. continue using his old machine, or
2. buy exactly one new machine and use it instead of the old one, and
3. buy ready-made sweet boxes from shops.

Boxes bought from shops are available instantly. Each shop offer can be used at most once. If William buys a new machine, he cannot use the old machine.

Write an algorithm to find the minimum time in which William can complete the order.

**Input**

The first line of the input consists of three space-separated integers - **numOfBox**, **prepTime** and **money**, representing the number of boxes William has to deliver, the time required to prepare one box using William's current machine, and the total budget, respectively.

The second line consists of two space-separated integers - **M** and **S**, representing the number of machines available for purchase and the number of shop offers available, respectively.

The next **M** lines each consist of two space-separated integers - **mTime** and **mCost**, representing the time taken by that machine to create one box and the cost of buying that machine, respectively.

The last **S** lines each consist of two space-separated integers - **sNum** and **sCost**, representing the number of boxes available in that shop offer and the cost to buy them, respectively.

**Output**

Print an integer representing the minimum time in which William can complete the order.

**Constraints**

`1 ≤ numOfBox, prepTime ≤ 10^9`  
`1 ≤ money ≤ 10^5`  
`1 ≤ M, S ≤ 10^3`  
`1 ≤ mTime, mCost, sNum, sCost ≤ 10^5`

**Note**

- Boxes purchased from shops are obtained instantly.
- Each shop offer can be used at most once.
- William may choose not to buy any machine and continue using the old machine.
- William may buy at most one new machine.

**Example**

Input:
```text
20 10 20
3 2
2 30
3 25
4 10
5 10
15 80
```

**Explanation**

With a budget of `20`, William cannot afford the first two machines and cannot afford the second shop offer.  
He may buy the third machine for `10` and the first shop offer for `10`, receiving `5` boxes instantly.  
He then produces the remaining `15` boxes using the new machine in `15 × 4 = 60` units of time.  
So the minimum time is `60`.


### 校对与理解备注
- 考点：预算优化、枚举机器、0/1 背包。
- 难度：困难。
- 校对：原 docx 样例尾部缺失，且“最小时间对 `1000007` 取模”这一句逻辑上明显可疑，因此这里明确改写为学习版定稿，不再保留那条规则。
- 提示：当前学习版把每个 shop offer 视为最多使用一次；机器方案在“旧机器或某一台新机器”之间二选一。

### 学习版定稿
- 题意：在预算约束下，枚举“旧机器/一台新机器”，再用 0/1 背包求能即时买到的最多盒数，从而最小化剩余生产时间。
- 关键限制：每个 shop offer 最多使用一次；最多购买一台新机器。
- 采用原因：这一定义与当前代码完全一致，也能自然解释可见样例。


### 代码实现与校验
- 对应类：[MinimumSweetBoxDeliveryTime.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MinimumSweetBoxDeliveryTime.java)
- 关键方法：`minimumTime(int boxCount, int oldMachineTime, int budget, int[][] machineOptions, int[][] shopOffers)`
- 校验：ShlSampleValidator 用可见样例做了校验。
- 说明：当前实现采用“shop offer 为 0/1 选择、最多选一台新机器”的学习版解释。

---

## B09. 社交网络最大传播起点 / Best Starting User for Maximum Reach

**Question**

On a social networking site, each user can have a group of friends. Each user possesses a unique profile ID. A company wants to promote its product on the social networking site in a particular way. It plans to give rewards to any user who promotes its product on his/her wall. The company will give extra reward points to users who refer other users. The company will ask one of the users to promote its product by posting the product message on his/her wall. The user can then share this message with their friends, asking them to post on their walls as well.

The company will share the promo message with the user in such a way that the promo message is posted on the maximum number of walls.

Write an algorithm to help the company find the userID of the user to whom they should send the promo request so that the request may reach the maximum number of walls.

**Input**

The first line of the input consists of two space-separated integers - **users** and **pairs**, representing the number of users and the number of pairs of friendships on the social networking site, respectively.

The next **pairs** lines consist of two space-separated integers - **user1** and **user2**, representing the profile IDs of users such that `user2` is a friend of `user1`.

**Output**

Print an integer representing the userID of the user to whom they should send the promo request so that the request may reach the maximum number of walls.

**Constraints**

`1 < users ≤ 1000`  
`1 < pairs < 10000`  
`0 ≤ user1, user2 < users`

**Note**

- If `user2` is a friend of `user1`, then it is not necessary that `user1` is also a friend of `user2`.
- A user cannot share the product message with a friend who has already received the product message.
- If multiple users can reach the same maximum number of walls, print the smaller userID.

**Example**

Input:
```text
5 4
0 1
3 4
1 2
2 1
```

Output:
```text
0
```

**Explanation**

To get the optimal result, the company should share the product message with the user whose profile ID is `0`.

The order in which the message is posted is:  
`0 -> 1 -> 2`



### 校对与理解备注
- 考点：有向图可达性、从每个起点做 BFS/DFS。
- 难度：中等。
- 校对：原题中的编号范围与样例冲突；当前版本按样例统一为 `0..users-1`。另外补充了并列时取更小 userID 的规则，使题面与代码一致。
- 提示：在 `users ≤ 1000` 的规模下，直接从每个起点做 BFS/DFS 即可。


### 代码实现与校验
- 对应类：[BestStartingUserForMaximumReach.java](../src/main/java/com/aquarius/wizard/leetcode/shl/BestStartingUserForMaximumReach.java)
- 关键方法：`bestUserId(int users, int[][] friendships)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：并列时当前实现返回更小 userID。

---

## B10. 通过合并相邻元素得到最长回文列表 / Longest Palindromic List by Merging Adjacent Values

**Question**

The assistant sales manager in the head office of a company 'Jotuway' receives the list of sales data from the offices of the company in different cities. The assistant sales manager has to compile the data and share the list with the sales manager. The shared list should be the longest palindromic list of the sales data of different cities. He/she can sum up any two consecutive elements of the list to form a single element. The result thus obtained can be reused further and this process can be repeated any number of times to convert the given list into a palindromic of maximum length.

Write an algorithm to help the assistant sales manager convert the given list into a palindromic list of maximum length.

**Input**

The first line of the input consists of an integer N, representing the number of elements in the list.

The second line consists of N space-separated positive integers representing the sales data.

**Output**

Print space-separated positive integers representing the palindromic list of maximum length.

**Constraints**

0 ≤ N ≤ 10³

1 ≤ S ≤ 10⁶; where S represents the sales data from a city

**Example**

Input:
```text
6
15 10 15 34 25 15
```

Output:
```text
15 25 34 25 15
```



### 校对与理解备注
- 考点：双指针、区间合并。
- 难度：中等。
- 校对：题意完整。
- 提示：这是经典题，左右两端比大小，哪边小就把哪边向内合并，目标是“尽量少合并，从而让回文长度尽量长”。


### 代码实现与校验
- 对应类：[LongestPalindromicListByMergingAdjacentValues.java](../src/main/java/com/aquarius/wizard/leetcode/shl/LongestPalindromicListByMergingAdjacentValues.java)
- 关键方法：`longestPalindromicList(int[] salesData)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前实现采用双指针贪心合并。

---

## B11. 最多 K 次把边权变成 0 的最短路 / Shortest Path with Up to K Zero-Cost Spells

**Question**

当前选择的编程语言是 Java 11。要求提交完全有效的代码，而不是部分正确但有效的代码。一旦提交，便无法再检查该题。可使用system.out.println()调试代码。在有语法 / 运行时错误时，system.out.println()可能不起作用。所使用的 Java 11 版本是 11.0.2。

A state consists of n cities numbered from 0 to n-1. All the roads in the state are bidirectional. Each city is connected to another city by one direct road only. A magician travels to these cities to perform shows. He knows a magic spell that can completely eliminate the distance between any two directly connected cities. But he must be very careful because this magic spell can be performed only K number of times.

Write an algorithm to find the length of the shortest route between two given cities after performing the magic spell K number of times. The output is -1 if no path exists.

**Input**

The first line of the input consists of five space-separated integers - n, m, p, q and K, representing the number of cities, the number of roads, the city A, the city B, and the number of times the magician can perform the magic spell, respectively.

The next m lines consist of three space-separated integers - u, v and w, where u and v represent the cities and w represents the length of the bidirectional road between the cities.

**Output**

Print an integer representing the length of the shortest route between the two given cities after performing the magic spell K number of times. Print -1 if no path exists.

**Constraints**

1≤n≤1000

0≤K≤n

0≤A,B<n; where A, B representing a city

0≤m≤104

1≤w≤1000

**Example**

Input:
```text
5 5 0 3 1
0 1 1
0 4 1
1 2 2
2 3 4
4 3 7
```

Output:
```text
1
```

**Explanation**

There are two possible routes between 0 and 3:0->1->2->3 and 0->4->3After reducing the distance of edge 4->3 to zero, the second route becomes 0->(4,3) and thus the minimum distance is 1.



### 校对与理解备注
- 考点：分层图最短路、Dijkstra。
- 难度：困难。
- 校对：样例合理。
- 提示：常见建模是 `dist[node][used]`，表示到达 `node` 且已经使用了 `used` 次法术时的最短路。


### 代码实现与校验
- 对应类：[ShortestPathWithUpToKZeroCostSpells.java](../src/main/java/com/aquarius/wizard/leetcode/shl/ShortestPathWithUpToKZeroCostSpells.java)
- 关键方法：`shortestPath(int cityCount, int[][] roads, int source, int target, int maxSpells)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：实现是分层图 Dijkstra。

---

## B12. 两圆相交面积 / Area of Intersection of Two Circles

**Question**

当前选择的编程语言是 Java 11。要求提交完全有效的代码，而不是部分正确而有效的代码。一旦提交，便无法再检查该题。可使用system.out.println()调试代码。在有语法 / 运行时错误时，system.out.println()可能不起作用。所使用的 Java 11 版本是 11.0.2。

A student must solve an entire workbook of problems related to finding the area of intersection of two circles. Because the problems are all very similar, the student decides to write a program that can solve all these similar problems.

**Input**

The first line of the input consists of three space-separated integers - x1​, y1​ and r1​ where x1​ and y1​ represents the x and y coordinates of the center of the first circle and r1​ represents the radius of the first circle.

The second line of the input consists of three space-separated integers - x2​, y2​ and r2​ where x2​ and y2​ represents the x and y coordinates of the center of the second circle and r2​ represents the radius of the second circle.

**Output**

Print a real number representing the area of intersection of two circles rounded up to 6 decimal places.

**Constraints**

0<r1​,r2​<104



### 校对与理解备注
- 考点：几何公式分类讨论。
- 难度：困难。
- 校对：没有样例，只有题干和部分约束。
- 提示：至少要分三类：相离、包含、普通相交；普通相交用扇形面积减三角形面积。


### 代码实现与校验
- 对应类：[AreaOfIntersectionOfTwoCircles.java](../src/main/java/com/aquarius/wizard/leetcode/shl/AreaOfIntersectionOfTwoCircles.java)
- 关键方法：`intersectionArea(double x1, double y1, double r1, double x2, double y2, double r2)`
- 校验：ShlSampleValidator 校验了“包含关系”这一类边界情况。
- 说明：没有官方样例，当前实现使用标准圆交面积公式。

---

## B13. 输出 2 到 n 的所有素数 / Print All Primes from 2 to n

**Question**

当前选择的编程语言是 Java 11。要求提交完全有效的代码，而不是部分正确而有效的代码。一旦提交，便无法再检查该题。可使用system.out.println()调试代码。在有语法 / 运行时错误时，system.out.println()可能不起作用。所使用的 Java 11 版本是 11.0.2。

A prime number is divisible only by 1 and itself. You are given a positive integer. Write an algorithm to find all the prime numbers from 2 to the given positive number.

**Input**

The input consists of an integer num, representing the number written on the board.

**Output**

Print space-separated integers representing the numbers required by the teacher.

**Constraints**

1<num<109

**Example**

Input:
```text
11
```

Output:
```text
2 3 5 7 11
```

**Explanation**

For the given number, the list of special numbers is [2, 3, 5, 7, 11].



### 校对与理解备注
- 考点：质数筛或试除法。
- 难度：中等。
- 校对：题面里给出的 `num < 10^9` 和“输出所有质数”的任务量有点不协调，这一点存疑。
- 提示：如果真实数据不大，用埃氏筛；如果题库真按超大上限出，截图很可能漏掉了更合理的限制。


### 代码实现与校验
- 对应类：[PrintAllPrimesFrom2ToN.java](../src/main/java/com/aquarius/wizard/leetcode/shl/PrintAllPrimesFrom2ToN.java)
- 关键方法：`listPrimes(int limit)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前实现使用分段筛，以缓和题面里可疑的大上限。

---

## B14. 图书馆学生完成作业的最优顺序 / Optimal Student Completion Order in a Library

**Question**

当前选择的编程语言是 Java 11。要求提交完全有效的代码，而不是部分正确而有效的代码。一旦提交，便无法再检查该题。可使用system.out.println()调试代码。在有语法 / 运行时错误时，system.out.println()可能不起作用。所使用的 Java 11 版本是 11.0.2。

Stephen runs a small library with N number of students as its members. All members have their unique studentID. The library has the certain number of books on M different subjects. Each student is given an individual assignment to complete by taking help from different books as per their requirement. The library has already issued some books to its members prior to this. The students can still issue required number of books from the library to complete their respective assignments. Each student submits the book issued to the library after completing their assignment. Only when the books have been submitted to the library can another student issue that book. Also, while assigning books, Stephen starts assigning books to the student with the smallest studentID and proceed to the student with the higher studentID. Once he reaches to the student with the largest studentID then again goes back to the smallest studentID to whom the book was not assigned and follow the same process.

Stephen wants to find the sequence of studentIDs in which the students optimally complete their assignments.

Write an algorithm to help Stephen find the sequence of studentIDs in which the students optimally complete their assignments. If all students can't complete their assignment, output a list of length 1 with content -1.

**Input**

The first line of the input consists of an integer booksNum, representing the number of different subjects (M).

The second line consists of M space-separated integers - avail[0], avail[1] ... avail[M-1], representing the books in the library that have not been issued to any student.

The third line consists of two space-separated integers - studentNum and reqBooks, representing the number of students (N) and number of different books required by each student (it is always equal to the number of different subjects M, respectively).

The next N lines consist of M space-separated integers representing the books required by the students to complete their assignments.

The next line consists of two space-separated integers - studentsIssuedBooks and issuedBooks, representing the number of students with books issued (it is always equal to number of students N) and number of different books issued to each student (it is always equal to the number of different subjects M, respectively).

The next N lines consist of M space-separated integers representing the books already issued to the students.

> 校对说明：根据示例和解释，这两组 `N` 行矩阵在原题文字版里很可能写反了。更自洽的读取顺序应为：先给“已经 issued 的书”，再给“required books”。

**Output**

Print space-separated integers representing the sequence of studentIDs that is optimal for the students to complete their assignments. If it is not possible for all students to complete their assignments, output a list of length 1 with content -1.

**Constraints**

1≤booksNum−reqBooks−issuedBooks≤100

1≤studentNum≤100

studentNum=studentsIssuedBooks

**Example**

Input:
```text
3
2 2 3
3 3
2 4 0
0 0 1
0 1 3
3 3
3 5 4
1 3 4
2 3 5
```

Output:
```text
2 0 1
```

**Explanation**

The available Books = [2, 2, 3]

studentIDIssued BooksRequired BooksNeeds

02 4 03 5 41 1 4

10 0 11 3 41 3 3

20 1 32 3 52 2 2

The needs of the student with ID 2 can be met directly (needs [2,2,2], available [2,2,3]). After completion, they return [0,1,3]. Library stock becomes [2,2,3] + [0,1,3] = [2,3,6].

Students 0 and 1 can now complete their assignments. Prefer smaller ID first:

Student 0: Returns [2,4,0]. Library stock becomes [2,3,6] + [2,4,0] = [4,7,6].

Student 1: Returns [0,0,1]. Library stock becomes [4,7,6] + [0,0,1] = [4,7,7].

The optimal order is [2, 0, 1].



### 校对与理解备注
- 考点：资源可行性模拟、类似银行家算法/拓扑过程。
- 难度：困难。
- 校对：示例虽然排版较乱，但核心机制是稳定的；同时原文字版里“required books”和“already issued books”两组矩阵的顺序很可能写反了。
- 提示：关键不是“已经借了多少”，而是“还缺多少”；谁当前缺口都能被库中现有书满足，谁就可以先完成并归还已借书。


### 代码实现与校验
- 对应类：[OptimalStudentCompletionOrderInLibrary.java](../src/main/java/com/aquarius/wizard/leetcode/shl/OptimalStudentCompletionOrderInLibrary.java)
- 关键方法：`findOptimalOrder(int[] availableBooks, int[][] issuedBooks, int[][] requiredBooks)`
- 校验：ShlSampleValidator 样例已覆盖。
- 说明：当前代码按“题面文字里的两组矩阵顺序写反了”这一纠错来实现。

---

## B15. 从第 K 个零售点出发访问全部零售点的最短路径 / Minimum Path to Visit All Retailers in Cartesia

> docx 在该题后半段开始混入其他题目碎片，以下只保留该题自身可识别的完整原文。

**Question**

The current selected programming language is Python 3.12. We emphasize the submission of a fully working code over partially correct but efficient code. Once submitted, you cannot review this problem again. You can use print() to debug your code. The print() may not work in case of syntax/runtime error. The version of Python 3.12 being used is 3.12.

Gregor is a salesperson employed in the city of Cartesia, which is an infinite plane whose locations follow the Cartesian coordinate system. There are N+1 retailers in the city out of which N retailers, with position 1 to N, have the coordinates (X₁, 0), (X₂, 0) to (Xₙ, 0). The head retailer, with position N+1, is located at the coordinate (Xₙ₊₁, Yₙ₊₁).

Gregor needs to find a path such that starting from the given Kᵗʰ retailer, he can visit all the other retailers covering the shortest total distance. Gregor can visit a retailer twice along his route and the distance between any two retailers is the same as the distance between the two points in the Cartesian coordinate system.

Write an algorithm to help Gregor to find the minimum distance of the path to visit all the given retailers.

**Input**

The first line of the input consists of two space-separated integers - N and K, representing the number of retailers on the X-axis and the position of the starting retailer, respectively.

The second line consists of N space-separated integers, representing the coordinates of retailers on the X-axis.

The third line consists of two space-separated integers - Xₙ₊₁, Yₙ₊₁, representing the coordinates of the head retailer.

**Output**

Print a real number representing the minimum possible length of the path after traveling through all the given points, rounded up to 6 decimal places.

**Constraints**

1≤K≤N+1



### 校对与理解备注
- 考点：几何路径、区间覆盖、起点选择。
- 难度：困难。
- 校对：题目后半段混入了别的题目碎片，约束和样例都不完整，属于高风险题。
- 提示：从可见题意看，`N` 个点都在 x 轴上，只有总部点可能离轴，这通常意味着存在把问题压缩成“覆盖 x 轴区间 + 是否额外插入一次离轴点”的几何做法；当前代码也按这个模型实现，并已做小规模暴力对拍。


### 代码实现与校验
- 对应类：[MinimumPathToVisitAllRetailersInCartesia.java](../src/main/java/com/aquarius/wizard/leetcode/shl/MinimumPathToVisitAllRetailersInCartesia.java)
- 关键方法：`minimumDistance(long[] retailerX, long headX, long headY, int startRetailerPosition)`
- 校验：ShlSampleValidator 已对小规模随机数据做暴力对拍。
- 说明：当前实现采用“覆盖 x 轴区间，并把唯一离轴点作为一次插入”的几何模型。

---

## 三、仅有碎片、暂未纳入完整题库的条目

- LRU Cache Misses：docx 尾部只有题意碎片，没有完整输入输出。
- Minimum Rotations for the Longest Common Prefix：只保留了 Peter 左旋/右旋字符串的题干碎片，没有完整输入输出与样例。



