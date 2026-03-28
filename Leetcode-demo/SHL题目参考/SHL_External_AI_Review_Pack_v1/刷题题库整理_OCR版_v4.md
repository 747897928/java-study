# 刷题题库整理（根据截图 OCR/人工校对）

> 说明  
> 1. 以下内容根据你提供的截图整理，尽量保留原题英文原文。  
> 2. 个别截图底部被截断，缺失部分我用 **[截图缺失]** 标注。  
> 3. 第 1 题示例输出在截图中未完整出现，我按题目公式推导为 **4096**，并已标注为“推断”。  

---

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

> 注：该输出为根据题目公式推断，原截图底部未完整显示。

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

---

## 25. 几何 / 直线覆盖算法（覆盖所有 pickup locations 的最少直线路线）

**Question**

The current selected programming language is **Python3.12**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `print` to debug your code. The `print` may not work in case of syntax/runtime error. The version of **Python3.12** being used is **3.12**.

A transportation company has begun service in a new city. Their specialty is affordable fares. They have identified some pickup locations in the crowded areas of the city. Servicing these locations will yield them the most customers. To maximize their profitability, they wish to determine the minimum number of straight-line routes that will connect all the pickup locations.

Write an algorithm to calculate the minimum number of straight-line routes that will cover all the pickup locations.

**Input**

The first line of the input consists of an integer **N** representing the number of pickup locations.  
The next **N** line of the input consists of two space-separated integers representing the **x** and **y** coordinates of the pickup locations.

**Output**

Print an integer representing the minimum number of straight-line routes that will cover all the pickup locations.

**Constraints**

`0 ≤ N ≤ 10^3`  
`-10^3 ≤ x, y ≤ 10^3`, where `x, y` are the coordinates of the pickup locations.

**Example**

**[截图缺失]**

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

---

## 30. 环检测 / 构造算法（圆桌晚宴输出字典序最小参会 ID）

**Question**

The current selected programming language is **Java 11**. We emphasize the submission of a fully working code over partially correct but efficient code. Once **submitted**, you cannot review this problem again. You can use `system.out.println` to debug your code. The `system.out.println()` may not work in case of syntax/runtime error. The version of **Java 11** being used is **11.0.2**.

A University has invited `N` alumni for a dinner. The dinner table has a circular shape. Each alumnus is assigned an invitation ID from `0` to `N-1`. Each alumnus likes exactly one fellow alumnus and will attend the dinner only if he/she can be seated next to the person he/she likes.

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


## 缺失内容汇总

以下题目因截图不完整，存在缺失字段或重建字段：

1. **第 1 题（幂运算加密）**：示例输出为根据题目公式推断，不是直接从截图完整读出。  
2. **第 3 题（SJF）**：示例部分仍缺失。  
3. **第 7 题（字符串转换）**：缺少 Note 后半句与 Example。  
4. **第 8 题（商品价格差 K）**：缺少 Constraints 与 Example。  
5. **第 14 题（公交线路覆盖距离）**：顶部编程语言说明缺失。  
6. **第 15 题（Martin 与父亲脚步重合）**：题干前半段、Constraints、Example 缺失。  
7. **第 16 题（街道路灯状态更新）**：解释部分仅能看清前半句。  
8. **第 17 题（叶子到叶子路径最大乘积）**：顶部编程语言说明未完整显示；另已补充一个输入格式变体。  
9. **第 19 题（按频次降序排序）**：Constraints 未出现在截图中。  
10. **第 22 题（购买 N 个苹果的最小成本）**：题干上半部分未出现在截图中，已依据可见 Input/Output/Example 做保守重建。  
11. **第 23 题（最大连续信号长度）**：Constraints 未出现在截图中。  
12. **第 25 题（最少直线路线覆盖 pickup locations）**：示例部分缺失。  
13. **第 27 题（完全平方账单客户统计）**：顶部编程语言说明缺失。  
14. **第 29 题（最少项目使错误分数归零）**：示例解释未出现在截图中。  
15. **第 31 题（移除元音字母）**：另有“房屋翻修”叙事版本截图，但未给出完整示例；已用完整题面版本合并整理。  
