# 算法题英语阅读学习笔记

---

# 1. 这份笔记的目标

这份笔记不是单纯记“中文翻译”，而是帮你建立一种**读算法题英语题面的方式**：

1. 不再逐词硬翻。  
2. 先看句子骨架，再看修饰。  
3. 遇到多义词，先抓“核心感觉”，再落到具体语境。  
4. 遇到 `of / in / after / representing / given / sorted` 这类高频结构时，知道它们在句子里扮演什么角色。  
5. 能把英文题面“重组”为自然中文，而不是照着英文顺序硬贴中文。

---


# 2. 原题全文

## 2.1 原题原文

```text
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
```

## 2.2 原题去掉平台废话后的干净版本

```text
Question

An alternate sort of a list consists of alternate elements (starting from the first position) of the given list after sorting it in an ascending order. You are given a list of unsorted elements. Write an algorithm to find the alternate sort of the given list.

Input
The first line of the input consists of an integer size, representing the size of the given list (N).
The second line consists of N space-separated integers arr[0], arr[1], ..., arr[N-1], representing the elements of the input list.

Output
Print space-separated integers representing the alternately sorted elements of the given list.

Constraints
0 < size ≤ 10^6
-10^6 ≤ arr[i] ≤ 10^6
0 ≤ i < size

Example
Input:
8
3 5 1 5 9 10 2 6

Output:
1 3 5 9
```

## 2.3 为什么笔记里必须保留原题

做题笔记至少应保留这 3 层：

1. **原题全文**：方便回看原始表述。  
2. **去噪版题目**：把平台废话拿掉，只保留规则。  
3. **讲解版**：再讲单词、语法、句子结构、题意。

缺少第 1 层，笔记就容易变成“脱离原文的二次总结”，复盘时不够稳。

# 2. 原题核心意思

原题的意思是：

1. 给你一个乱序数组。  
2. 先把它按从小到大排序。  
3. 然后从排好序的数组里，从第 1 个开始，每隔一个取一个。  
4. 也就是取第 1、3、5、7…… 个元素。  
5. 输出这些元素。

例子：

原数组：

```text
3 5 1 5 9 10 2 6
```

排序后：

```text
1 2 3 5 5 6 9 10
```

从第一个开始隔一个取一个：

```text
1 3 5 9
```

所以输出：

```text
1 3 5 9
```

---

# 3. 你一开始卡住的 6 个词

你最先问的是：

- `alternate`
- `consists`
- `ascending`
- `representing`
- `separated`
- `alternately`

下面把它们系统化整理。

---

## 3.1 `alternate`

### 基本意思
常见意思有：

- 交替的
- 轮流的
- 间隔的
- 备用的（某些语境下）

### 这道题里是什么意思
这题里不是“备用的”。

这题里的 `alternate elements` 更接近：

- 交替取出的元素
- 隔一个取一个的元素
- every other element

### 为什么会从“交替”落到“隔一个取一个”
`alternate` 的更底层感觉是：

> A、B、A、B 地轮换出现，不连续重复同一种。

放到有序序列里，从第一个开始交替取，实际操作就是：

- 取一个
- 跳一个
- 再取一个
- 再跳一个

所以题里最终落地成：

> 隔一个取一个

### 重要提醒
`alternate` 可以先翻成“交替”，但在这道题里如果只停在“交替”，你还是不知道具体操作。  
所以要做两步：

1. 字面：交替的  
2. 结合题意：隔一个取一个

### 例子
- Take alternate elements from the array.  
  从数组中隔一个取一个元素。
- Alternate red and blue.  
  红蓝交替。
- Alternate days.  
  隔一天 / 每隔一天。

---

## 3.2 `alternately`

### 词性
`alternately` 是**副词**。

### 基本意思
- 交替地
- 轮流地
- 间隔地

### 例子
- The lights blink alternately.  
  灯交替闪烁。

### 这题里怎么理解
如果题面写到与 `alternately` 相关的表达，通常要理解成：

> 按交替/隔一个的方式

不过算法题有时英语写得不够地道，所以不能每次都机械按词性直译，要结合题意落到“操作规则”上。

---

## 3.3 `consists`

### 固定搭配
`consist of` = 由……组成

### 题面里怎么理解
- The first line consists of an integer.  
  第一行由一个整数构成。  
  更自然：第一行是一个整数。

### 高频理解
在算法题里看到 `consists of`，常常直接理解为：

- 由……组成
- 包含……
- 是……

### 例子
- The team consists of 5 members.  
  团队由 5 名成员组成。
- The string consists of lowercase letters.  
  字符串由小写字母组成。

---

## 3.4 `ascending`

### 基本意思
- 上升的
- 递增的

### 高频搭配
`ascending order` = 升序 = 从小到大

### 反义词
`descending order` = 降序 = 从大到小

### 例子
- Sort the array in ascending order.  
  把数组按升序排序。
- Arrange the numbers in descending order.  
  把数字按降序排列。

### 记忆法
- ascending = 往上走 = 越来越大  
- descending = 往下走 = 越来越小

---

## 3.5 `representing`

### 来自动词
`represent` = 表示、代表

### `representing` 在题面里的作用
它常常不是主句的核心动作，而是一个**补充说明块**。

例如：

```text
an integer size, representing the size of the given list
```

自然理解：

> 一个整数 size，它表示给定列表的大小。

### 它可以还原成什么
经常可以还原成：

```text
an integer size, which represents the size of the given list
```

也就是：

- `which represents ...` 是从句版
- `representing ...` 是压缩版

### 常见理解方式
看到：

```text
名词, representing ...
```

先翻成：

- ……，表示……
- ……，用来表示……

---

## 3.6 `separated`

### 来自动词
`separate` = 分开

### `separated` 在题面里的高频样子
- `space-separated` = 用空格分隔的
- `comma-separated` = 用逗号分隔的
- `tab-separated` = 用制表符分隔的

### 例子
- N space-separated integers  
  N 个用空格分隔的整数
- Print space-separated integers.  
  输出用空格分隔的整数。

### 本质
这里的 `separated` 是过去分词，常常像形容词一样修饰后面的名词。

---

# 4. 为什么你直译后会觉得怪

你多次遇到这个问题：

- 单词都认识一点
- 拼起来却完全不像中文
- 甚至越翻越怪

根本原因是：

> 你当时还在逐词对号入座，而不是先看句子骨架。

比如：

```text
Arrange the numbers in descending order.
```

如果逐词贴：

- Arrange = 分配/安排
- the numbers = 数字
- in = 在里面
- descending order = 降序

就会很怪。

正确方式是：

1. 先看动作：`Arrange`
2. 再看对象：`the numbers`
3. 再看方式：`in descending order`

自然就是：

> 把这些数字按降序排列。

---

# 5. 多义词怎么记：不是背一串中文，而是抓“核心感觉”

你很在意这个问题：

> 一个词好多意思，我是不是要全部背住？

核心结论：

> 不要先背“很多中文义项”，而要先抓这个词的**核心动作感 / 核心图像**。

---

## 5.1 以 `arrange` 为例

### 不好的记法
- arrange = 安排
- arrange = 分配
- arrange = 排列
- arrange = 整理

这样背，到句子里容易混乱。

### 更好的记法
先抓一个底层感觉：

> 把东西整理到一个合适的位置、顺序或状态

然后落到不同语境：

- arrange a meeting  
  安排会议
- arrange the books  
  整理书 / 把书排好
- arrange the numbers in descending order  
  按降序排列数字

---

## 5.2 以 `find` 为例

### 日常语义
- find my keys  
  找到钥匙

### 算法题里的语义
- find the answer  
  得到答案
- find the maximum  
  求出最大值
- find the sum  
  求和

### 核心感觉
> 从未知/混乱中，把目标结果弄到手

所以算法题里常译成：

- 找到
- 求出
- 得出
- 计算出

本质没有变。

---

## 5.3 以 `alternate` 为例

### 不好的记法
- 备用的
- 交替的
- 间隔的
- 轮流的

### 更好的记法
先抓核心：

> A/B 轮着来，不连续重复同一种

再落地：

- alternate colors  
  交替的颜色
- alternate days  
  隔一天
- alternate elements  
  隔一个取一个的元素

---

# 6. `Arrange the numbers in descending order.` 为什么不是“分配数字在降序里”

---

## 6.1 句子骨架
- `Arrange` = 动作
- `the numbers` = 对象
- `in descending order` = 方式

### 自然理解
> 把这些数字按降序排列。

---

## 6.2 `in` 为什么不能老理解成“在……里面”

你后来很明确地意识到：

> 我卡住的不一定是大词，可能是 `in` 这种介词。

这是对的。

`in` 不只有“在……里面”的空间含义。

它还有很多抽象用法：

- in English = 用英语
- in silence = 默默地
- in a hurry = 匆忙地
- in ascending order = 按升序
- in descending order = 按降序

这里的 `in` 更接近：

- 以……方式
- 按……形式
- 处于……顺序状态

所以：

```text
in descending order
```

不是：

> 在降序里面

而是：

> 按降序 / 以降序的方式

---

# 7. `given` 为什么不是 `give`：`the given list`

你问得很关键：

> `the given list` 里的 `given` 是被动吗？为什么不是 `the give list`？

---

## 7.1 结论
`given` 不是普通原形动词，而是：

> `give` 的过去分词，用来像形容词一样修饰名词。

所以：

- `the given list` = 给定的列表
- `the given array` = 给定数组
- `the given string` = 给定字符串

---

## 7.2 为什么不是 `give list`

因为英语里动词原形不能直接这样修饰名词。

你不能说：

- `the give list` ❌

因为 `give` 只是“给”这个动作本身，不是“给定的”这个状态。

---

## 7.3 过去分词当形容词是什么意思

像这些：

- given list
- sorted array
- repeated elements
- fixed value

都可以理解成：

> 原本是动词的过去分词形式，现在像形容词一样修饰名词。

通常带有：

- 被……的
- ……后的状态
- 已经……过的

在题面里，你可以先粗略理解成：

> 带“……的”意思的修饰词

例如：

- given list = 给定的列表
- sorted array = 排好序的数组 / 已排序数组
- repeated elements = 重复元素 / 被重复的元素

---

# 8. `representing` 和 `which represents` 为什么都行

你一直追问这个，我这里系统整理。

---

## 8.1 完整版

```text
an integer size, which represents the size of the given list
```

这里：

- `which` 指代前面的 `size`
- `represents` 是主句之外的新从句里的谓语动词
- 因为 `size` 按单数看，所以用 `represents`

---

## 8.2 压缩版

```text
an integer size, representing the size of the given list
```

这里的 `representing` 是**现在分词短语**，用来压缩前面的从句。

你可以理解成：

- `which represents ...` 是展开版
- `representing ...` 是压缩版

---

## 8.3 为什么英语要这样写

因为英语喜欢压缩信息。  
尤其在题面、说明文、科技文里，经常会把：

```text
名词, which does ...
```

压成：

```text
名词, doing ...
```

这样更紧凑。

---

## 8.4 什么时候用 `represents`

当它是句子的主要动作之一时：

- This value represents the answer.
- N represents the number of elements.

这里 `represents` 是句子的核心谓语动词。

---

## 8.5 什么时候用 `representing`

当句子主干已经完整，后面只是补充说明时：

- The first line contains an integer N, representing the number of elements.

主干已经是：

- The first line contains an integer N.

后面的 `representing ...` 只是补充说明 N 是干什么的。

---

# 9. `represents` 为什么加 `s`

你追问得很细：

> 这里为什么用 `s`？是因为不可数吗？是第三人称单数吗？`value` 是可数还是不可数？

---

## 9.1 结论
`represents` 加 `s`，是因为：

- 这是一般现在时
- 主语按单数处理

和“可数不可数”不是同一个问题。

---

## 9.2 例子
- This value represents the answer.  
  `value` 是单数，所以 `represents`
- N represents the number of elements.  
  `N` 这里表示一个值，按单数处理，所以 `represents`
- These values represent the answers.  
  `values` 是复数，所以不用 `s`

---

## 9.3 `value` 可数吗
在这类题面语境里，`value` 通常按**可数名词**理解：

- a value
- two values

但它有时也可能在抽象语境里表示“价值”。你在算法题里先按可数处理即可。

---

# 10. `s / ing / ed` 到底怎么从本质理解

你非常在意这一块，因为你觉得老靠死记记不住。

最实用的底层理解是：

> 英语通过词形变化，告诉你这个词现在在句子里扮演什么角色。

---

## 10.1 动词原形
表示动作概念本身：

- give
- sort
- represent
- find

---

## 10.2 `s`
常用于：

> 主句里的主要动作，主语是单数

例如：

- N represents the size.
- This method works.
- The array contains numbers.

---

## 10.3 `ing`
不要只理解成“正在进行”或“未来”。

`ing` 常常有三类重要作用：

### A. 进行中的动作
- He is running.

### B. 当“事情/动作本身”来用（动名词感）
- Swimming is fun.
- After sorting the array...

### C. 当补充说明块来用
- representing the size
- starting from the first position
- using this method

所以：

> `ing` 很多时候是在把动作“变软”，变成一个可以补充说明、修饰、或者当事情来谈的东西。

---

## 10.4 `ed`
也不要只理解成“过去”。

它常见地表示：

- 动作完成后的状态
- 被动感
- 修饰名词

例如：

- given list
- sorted array
- repeated elements
- fixed value

这些在题面中往往不是在强调“过去某时发生了一次”，而是在强调：

> 现在处于“被给出 / 被排序 / 被重复 / 被固定”的状态

---

# 11. 什么是“谓语动词”和“非谓语”

你对这一点一直想弄明白。

---

## 11.1 谓语动词
就是句子的主要动作核心。

例如：

- N represents the number of elements.  
  这里 `represents` 是谓语动词。

- The first line contains an integer N.  
  这里 `contains` 是谓语动词。

---

## 11.2 非谓语
不是句子的主要动作核心，而是挂在句子边上的说明、修饰、压缩结构。

例如：

- representing the number of elements
- starting from the first position
- sorted in ascending order

你可以粗略理解成：

> 它们不是在独立“成句”，而是在给主句补信息。

---

# 12. `after doing sth`：为什么介词后面常接 `ing`

你自己想到了一个很好的点：

> 介词后面的动词是不是要用 `ing`，因为要让它变成动名词？

这是对的，而且是非常好的底层理解。

---

## 12.1 `after` 是介词
介词后面通常接“一个对象 / 一件事 / 一个块”。

比如：

- after class
- after dinner
- after work

如果后面想接动作，就不能直接用原形动词，因为原形太像“执行动作”，不像“一个东西/一件事”。

所以要改成：

- after sorting
- after reading
- after finishing

---

## 12.2 底层逻辑
你可以把：

- after sorting the array

理解成：

> 在“排序数组这件事”之后

也就是说：

> `sorting` 把动词变成了一个能放在介词后面的“事件/事情”。

这是很接近动名词逻辑的。

---

## 12.3 这和时态不是一回事
你之前会把：

- ing = 未来
- ed = 过去

混起来。

这里要明确：

```text
after doing
```

里的 `doing` 不是在表示未来，而是在表示：

> 做这件事

所以：

- after sorting the array  
  排序数组后

这里是结构问题，不是时态问题。

---

# 13. `of` 的本质是什么

你后来非常敏锐地发现，真正绊住阅读的，常常不是大词，而是像 `of` 这种小词。

核心结论：

> `of` 不是固定等于某一个中文词。  
> 它的本质是：建立前后两个名词之间的关系。

你可以先把：

```text
A of B
```

理解成：

> 一个 A，它和 B 有关系。

然后再根据语境翻译成：

- B 的 A
- B 中的 A
- 关于 B 的 A
- 由 B 组成的 A
- 来自 B 的 A

---

## 13.1 这题里的几个 `of`

### `An alternate sort of a list`
这里不是“从里面出来”，而是：

> 一个列表的 alternate sort  
> 针对一个列表定义的一种排序结果

### `elements of the given list`
这里更接近：

> 给定列表中的元素 / 给定列表的元素

### `the first line of the input`
这里是整体-部分关系：

> 输入的第一行

### `the size of the given list`
这里是属性归属关系：

> 给定列表的大小

### `the elements of the input list`
这里也是整体-部分关系：

> 输入列表中的元素

---

## 13.2 `a list of unsorted elements`
这个很典型。

它不是：

> 一个列表的未排序元素

而是：

> 一个由未排序元素组成的列表  
> 一个未排序元素列表

这里 `of` 表示：

- 内容
- 构成

类似：

- a bag of rice = 一袋米
- a group of students = 一群学生
- a list of integers = 一个整数列表

---

# 14. `the first line of the input` 为什么不是 `the input which is first line`

你专门追问了这一点。

---

## 14.1 先回答对错

```text
the input which is first line
```

不对。

更准确地说，它表达成了：

> 这个 input 本身就是 first line

但你真正想表达的是：

> input 这个整体里的第一行

所以正确表达是：

- `the first line of the input`
- `the input's first line`

---

## 14.2 为什么你会下意识写成从句

因为你更习惯：

> 先说一个东西，再用补充说明解释它

但这里不是“这个 input 是 first line”，而是“first line 属于 input 这个整体”。

所以这里需要的是：

> 名词和名词之间的整体-部分关系

而不是：

> 主语 + 系动词 + 表语

---

## 14.3 这不是倒装
`the first line of the input` 不是倒装，它只是普通的名词短语。

结构是：

- the first line = 第一行
- of the input = 输入的 / 输入中的

合起来就是：

> 输入的第一行

---

## 14.4 英语为什么喜欢这样说
英语常常把很多信息压进一个名词短语里，而不是每次都开从句。

例如：

- the first line of the input
- the size of the array
- the result of the algorithm

这是英语非常常见的名词短语搭法。

---

# 15. `alternate sort of a list` 到底怎么切

你后面非常认真地追问了这一点，还担心 `sort of` 会不会是固定搭配。

---

## 15.1 正确切法

```text
[an alternate sort] [of a list]
```

不是：

```text
[alternate] [sort of a list]
```

更不是：

```text
[alternate sort of] [a list]
```

---

## 15.2 这里 `alternate` 是什么词
这里：

- `alternate` 是形容词
- `sort` 是名词
- `of` 是介词
- `a list` 是名词短语

所以结构就是：

> 形容词 + 名词 + of + 名词

---

## 15.3 `sort` 在这里是动词吗
不是。这里 `sort` 是**名词**。

它更接近：

- 一种排序结果
- 一种排序方式
- 一种类型/类别

---

## 15.4 `sort of` 不是这里那个固定搭配
你想到的 `sort of` 的确存在，但那是另一个东西，口语里常表示：

- 有点
- 某种程度上
- 算是

例如：

- I sort of understand.  
  我有点懂。

但这道题里不是这个意思。

这里的结构中心是 `sort`，`alternate` 修饰它，`of a list` 再补充说明“这是哪个东西的 sort”。

---

## 15.5 这句怎么自然理解

```text
An alternate sort of a list
```

自然理解为：

> 一个列表的 alternate sort

也就是：

> 针对一个列表定义出来的一种结果

---

# 16. `You are given ...` / `are given` 为什么是被动，又为什么题面老这么写

你也问到了：

> `are given` 不是 `be + 过去分词` 吗？这不是被动？

对，完全正确。

---

## 16.1 语法上

```text
You are given a list of unsorted elements.
```

是标准被动语态：

- You = 主语
- are = be 动词
- given = 过去分词

也就是：

> 你被给了一个由未排序元素组成的列表

---

## 16.2 题面里怎么自然理解
算法题里通常不自然地翻成“你被给了……”，而是理解成：

- 给你……
- 现给定……
- 现有……

例如：

- You are given an array.  
  给你一个数组。
- You are given two integers N and M.  
  给你两个整数 N 和 M。

---

## 16.3 为什么英语喜欢这样写
因为出题人不关心“谁给你”，只关心“你现在手上有什么”。

也就是：

- 施动者不重要
- 接收者和给出的对象更重要

这正是被动语态常见的使用场景。

---

# 17. `An alternate sort of a list consists of ...` 怎么读才不会怪

这句是整题里最绕的一句。

原句：

```text
An alternate sort of a list consists of alternate elements (starting from the first position) of the given list after sorting it in an ascending order.
```

---

## 17.1 先找主干

主干是：

```text
An alternate sort of a list consists of alternate elements
```

直译会绕：

> 一个列表的 alternate sort 由 alternate elements 组成

这句本身像“在定义术语”。

---

## 17.2 再看补充信息

### `(starting from the first position)`
表示：

> 从第一个位置开始

### `of the given list`
表示：

> 来自给定列表 / 给定列表中的

### `after sorting it in an ascending order`
表示：

> 在把它按升序排序之后

---

## 17.3 按中文重组
英语喜欢：

> 先说核心名词，再往后不断挂修饰

中文更自然的是：

> 先把条件讲完，再说结果

所以这句重组后更自然的中文是：

> 先把给定列表按升序排序，然后从第一个位置开始，每隔一个取一个元素；这些元素就构成了这个列表的 alternate sort。

---

# 18. 为什么你会翻成“一个间隔排序的列表”

你当时翻成：

> 一个间隔排序的列表由间隔元素组成……

之所以怪，是因为你把：

```text
alternate sort of a list
```

误切成了“一个被间隔排序的列表”。

但它并不是在修饰 `list`，而是在说：

> 一个列表的某种排序结果

也就是：

- `sort` 才是中心词
- `alternate` 修饰 `sort`
- `of a list` 再说明“这是哪个东西的 sort”

---

# 19. `Print space-separated integers representing the result.` 到底是什么结构

你后来也问到了这个经典结构。

---

## 19.1 先拆句子角色

- `Print` = 主动作（祈使句，省略主语 you）
- `space-separated integers` = 宾语
- `representing the result` = 修饰前面的 `integers`

---

## 19.2 为什么不是 `represents`
因为主句的核心动词已经有了：

- `Print`

如果你再直接塞一个 `represents`，会形成两个核心动作硬撞在一起。

所以这里要用补充说明结构：

- `representing the result`

---

## 19.3 为什么通常没有逗号
这里的 `representing the result` 是在**限定哪些 integers**，相当于：

> 表示结果的那些整数

它是紧贴名词的限制性修饰，常常不加逗号。

类似：

- the man wearing a hat
- the students waiting outside
- integers representing the result

---

## 19.4 能不能写成 `which` 从句
可以改写，但注意单复数和语义。

例如：

```text
Print the integers that represent the result.
```

这个很自然。

如果写成：

```text
Print space-separated integers, which represents the result.
```

有两个问题：

1. `which` 如果指代 `integers`，那应该用 `represent`，不是 `represents`。  
2. 加逗号后语义会偏向补充说明，未必是最自然的限制性表达。

---

# 20. `find` 在算法题里为什么常译成“求出”

你明确说过：

> 我看到 `find` 就只会想到“找到”，不会往“求出、计算出、得到”去想。

这个现象非常正常。

---

## 20.1 日常语义
- find my key = 找到钥匙

---

## 20.2 算法题语义
- find the maximum = 求出最大值
- find the answer = 得出答案
- find the shortest path = 求最短路径

### 本质没变
依然是“找到”，只是算法题里的“找到”往往是通过：

- 计算
- 比较
- 推理
- 枚举

得到结果

所以中文自然会落成：

- 求出
- 计算出
- 得到

---

# 21. 题面里最常见的固定区块词

---

## 21.1 `Input`
输入格式

## 21.2 `Output`
输出格式

## 21.3 `Constraints`
数据范围 / 约束条件

## 21.4 `Explanation`
样例解释

### 建议阅读顺序
以后读题建议：

1. 先看题意第一段  
2. 再看 example  
3. 再看 input / output  
4. 最后看 constraints

很多时候 example 比题干更容易看懂。

---

# 22. 常见高频模板

下面这些句式，你以后会在算法题里反复看到。

---

## 22.1 `The first line of the input consists of ...`
输入的第一行是……

---

## 22.2 `The second line consists of N space-separated integers ...`
第二行包含 N 个用空格分隔的整数……

---

## 22.3 `representing ...`
表示…… / 用来表示……

---

## 22.4 `in ascending order`
按升序 / 从小到大

---

## 22.5 `starting from the first position`
从第一个位置开始

---

## 22.6 `Print space-separated integers ...`
输出以空格分隔的整数……

---

# 23. 原题逐句精拆（适合复习）

---

## 23.1 `You are given a list of unsorted elements.`

### 自然中文
给你一个由未排序元素组成的列表。

### 结构
- `You are given ...` = 题面常见被动句型，常读成“给你……”
- `a list of unsorted elements` = 一个由未排序元素组成的列表

---

## 23.2 `Write an algorithm to find the alternate sort of the given list.`

### 自然中文
写一个算法，求出给定列表的 alternate sort。

### 结构
- `Write an algorithm` = 写一个算法
- `to find` = 去求 / 用来求
- `the alternate sort of the given list` = 给定列表的 alternate sort

---

## 23.3 `The first line of the input consists of an integer size, representing the size of the given list (N).`

### 自然中文
输入的第一行是一个整数 size，表示给定列表的大小（N）。

### 结构
- `The first line of the input` = 输入的第一行
- `consists of an integer size` = 是一个整数 size
- `representing ...` = 表示……

---

## 23.4 `The second line consists of N space-separated integers ...`

### 自然中文
第二行包含 N 个用空格分隔的整数……

---

## 23.5 `Print space-separated integers representing the alternately sorted elements of the given list.`

### 自然中文
输出用空格分隔的整数，它们表示给定列表按题目规则得到的结果元素。

### 注意
这句英语本身略有不够自然的感觉，但意思是能懂的。  
更清晰的自然英语可以写成：

- Print the alternate elements of the sorted list.
- Print the elements obtained by alternate sorting of the given list.

---

# 24. 关于“英语为什么这么麻烦”的一个总解释

你多次表达过一种真实感受：

> 中文可以很轻松表达，为什么英语搞这么复杂？

核心解释是：

1. 不是英语一定比中文复杂，而是你对中文的结构已经自动化了。  
2. 英语更喜欢通过词形变化告诉你：谁是主动作，谁是修饰，谁是状态，谁是“这件事”。  
3. 中文更多靠语境和语序，英语更多靠形式变化。  
4. 所以你会觉得英语“花样很多”，其实它是在强标记词语角色。

换句话说：

> 英语很多变化，不是在故意为难你，而是在提示“这个词现在是干什么的”。

---

# 25. 学习方法总结：以后怎么自己拆算法题

---

## 第一步：先找主动作
题目让你干什么？

高频词：

- find
- print
- return
- sort
- arrange
- determine
- count
- calculate

---

## 第二步：找对象
给了你什么？你要处理谁？

例如：

- a list
- an array
- a string
- N integers
- a tree

---

## 第三步：找规则和条件
例如：

- in ascending order
- starting from the first position
- after sorting
- each line contains

---

## 第四步：中文重组，不要按英文原顺序硬翻
例如：

```text
Print space-separated integers representing the result.
```

不要翻：

> 打印空格分隔整数表示结果

而要重组：

> 输出表示结果的整数，并用空格分隔。

---

## 第五步：遇到 `of`、`in`、`after` 时先判“关系”，不要急着判中文词
比如：

- `of` = 建立名词之间的关系  
- `in` = 不一定是“在里面”，可能是“按……方式”  
- `after` = 建立时间顺序关系

---

# 26. 新增补充：`the alternately sorted elements of the given list` 怎么看

这是你在本轮最新补问的句子，这里单独整理。

---

## 26.1 你的拆法
你写的是：

> 冠词 + 副词 + sorted(这是什么词？) + 名词复数 + 介词 + 冠词 +（动词过去分词，但不知道在这里是什么词）+ 名词

这个方向是对的，下面把它讲清楚。

---

## 26.2 逐个成分看

### `the`
冠词。

### `alternately`
副词。  
意思：交替地 / 间隔地 / 按交替方式。

### `sorted`
这里最适合理解成：

> 过去分词，带有形容词性质

它和前面的 `alternately` 合在一起，形成一种“副词 + 过去分词”的修饰块：

- alternately sorted

你可以先粗略理解为：

> 被按交替方式处理/排序过的

### `elements`
名词复数，元素。

### `of the given list`
介词短语，修饰前面的 `elements`。

其中：

- `given` = `give` 的过去分词，当形容词用  
- `the given list` = 给定列表

所以整个 `of the given list` 是：

> 给定列表中的 / 给定列表的

---

## 26.3 形式上合理吗
形式上，**是可以成立的**：

- 副词 `alternately` 可以修饰过去分词 `sorted`
- 过去分词 `sorted` 可以像形容词一样修饰 `elements`

所以语法上不是完全错。

---

## 26.4 为什么你会觉得怪
因为它**不太自然**，有点算法题平台英语味。

更准确地说：

- 语法上能读通
- 但表达不够漂亮、不够地道
- 容易让人多想一层“到底是 alternately 修饰 sorted，还是整块另有所指”

也就是说，这种写法**可懂，但不优雅**。

---

## 26.5 更自然的说法
如果要写得更自然一点，可以改成：

- the alternate elements of the sorted list  
  排序后列表中的交替元素

或者：

- the elements obtained by alternate sorting of the given list  
  通过对给定列表做 alternate sort 得到的元素

或者：

- the elements selected alternately from the sorted list  
  从已排序列表中交替选出的元素

---

## 26.6 这句在这道题里怎么理解最稳

```text
the alternately sorted elements of the given list
```

不要机械翻成：

> 给定列表的被交替地排序过的元素

而要按题意重组为：

> 给定列表按题目规则处理后得到的元素

或者更具体：

> 给定列表升序排序后，从第一个开始隔一个取一个得到的元素

---

# 27. 你最该反复记的底层结论（速记版）

## 27.1 读题不是逐词翻译
先找：

- 主动作
- 对象
- 条件/方式/修饰

---

## 27.2 多义词不要背成“中文词条表”
先抓核心感觉，再进句子选最适合的中文。

---

## 27.3 `of`
不是固定等于“的”，而是建立两个名词的关系。

---

## 27.4 `in`
不一定是“在里面”，很多时候表示“按……方式 / 处于……状态”。

---

## 27.5 `given / sorted / repeated`
很多时候是过去分词当形容词，不是在强调“过去时”，而是在表示“……的 / 被……的 / ……后的状态”。

---

## 27.6 `representing / starting / using`
很多时候是非谓语说明块，不一定表示“正在”或“未来”。

---

## 27.7 `represents`
常常是句子的主动作，单数主语时用 `s`。

---

## 27.8 `after doing`
因为介词后面常需要“一个事情/事件”当对象，`doing` 把动作变成可以被当作“这件事”的形式。

---

## 27.9 `You are given ...`
语法上是被动，题面里自然读成：

- 给你……
- 现给定……

---

## 27.10 `alternate`
先抓“轮换、隔着来”的核心，再根据题意落到“隔一个取一个”。

---

# 28. 以后继续维护这份笔记的方式

以后你再问算法题英语，我建议继续按这 4 类往里补：

1. **词汇层**：单词的核心感觉 + 在题面里的具体落地  
2. **语法层**：`s / ing / ed / which / of / in / after` 这些结构角色  
3. **题面层**：把具体题目逐句重组成人话  
4. **模板层**：积累常见输入输出句型

这样你就不是在记“碎片答案”，而是在积累一套可复用的读题系统。


---

# 29. 新题补充：pairs transmitted 这道题

> 这一节是后续增量维护的新题。  
> 题目大意：给你一串正整数。每次可以从中选出两个相同的整数，组成一对并传输出去。最后要输出：
> 1. 一共传输了多少对  
> 2. 还剩多少个没有配成对的整数

## 29.1 去掉注释和平台包装后的完整题目

```text
Question

A company is transmitting a message containing N positive integers to a new server in a secure manner. They have identified a procedure for transmission where, each time, a pair of identical integers is selected from the message and transmitted. This process continues until all pairs are sent. The procedure will then indicate the number of pairs transmitted and the number of remaining integers that were not paired and transmitted to the new server.

Write an algorithm to find the number of pairs transmitted and the number of integers remaining after transmitting the pairs to the new server.

Input
The first line of input consists of an integer message_size, representing the total number of integers in the message (N).
The second line consists of N space-separated integers, representing the integers of the message.

Output
Print two space-separated integers representing the number of pairs transmitted and the number of integers remaining after transmitting the pairs to the new server.

Constraints
1 <= message_size <= 100
Each integer in the message is positive
The pair size is always equal to 2

Example
Input:
8
12 10 6 12 10 12 1 21

Output:
2 4
```

## 29.2 这题的人话版

题目核心就是：

1. 给你一堆正整数。  
2. 只要两个数一样，就能组成一对。  
3. 每组成一对，就把这两个数“传输走”。  
4. 最后输出：
   - 一共有多少对
   - 还剩多少个没配成对的数

## 29.3 这题你卡住的词

### `transmitting`
来自 `transmit`，意思是：
- 传输
- 发送
- 传送

题里：
> transmitting a message  
= 传输一条消息

---

### `secure`
意思：
- 安全的
- 可靠的

题里：
> in a secure manner  
= 以安全的方式

注意：这里基本是**背景包装**，对算法规则几乎没影响。

---

### `identified`
来自 `identify`，题里更自然不是“身份识别”，而是：
- 找到
- 确定
- 想出

题里：
> They have identified a procedure  
= 他们找到了一种方法 / 确定了一种流程

---

### `procedure`
意思：
- 程序
- 步骤
- 流程
- 方法

题里最自然：
> 流程 / 方法 / 处理规则

---

### `transmission`
名词，来自 `transmit`

意思：
- 传输
- 传送过程

可以和 `transmit` 对比记：
- `transmit`：动词，传输
- `transmission`：名词，传输这件事

---

### `a pair of`
高频固定结构：

> 一对……

例如：
- a pair of shoes = 一双鞋
- a pair of identical integers = 一对相同的整数

---

### `identical`
意思：
- 相同的
- 一模一样的

题里：
> identical integers  
= 相同的整数

---

### `remaining`
来自 `remain`

意思：
- 剩下的
- 剩余的

题里：
> remaining integers  
= 剩余的整数 / 没能配成对的整数

---

### `process`
意思：
- 过程
- 流程
- 处理过程

题里：
> This process continues ...  
= 这个过程继续进行……

这里最自然理解成：
> 这个“不断配对并传输”的过程会继续

## 29.4 重点句子精拆

### 句子 1
> They have identified a procedure for transmission

自然中文：
> 他们找到了一种传输规则 / 传输方法。

---

### 句子 2
> each time, a pair of identical integers is selected from the message and transmitted

自然中文：
> 每次都会从消息中选出一对相同的整数，并把它传输出去。

结构拆解：
- `each time` = 每次
- `a pair of identical integers` = 一对相同的整数
- `is selected` = 被选出来
- `from the message` = 从消息中
- `and transmitted` = 并被传输出去

---

### 句子 3
> This process continues until all pairs are sent.

自然中文：
> 这个过程会持续进行，直到所有能组成的 pair 都被发送出去。

其中：
- `process` = 过程
- `continues` = 持续 / 继续
- `all pairs are sent` = 所有 pair 都被发送出去

这里的 `are sent` 是**被动语态**，所以你读成“pairs 被……”这个方向其实是对的。

---

### 句子 4
> The procedure will then indicate the number of pairs transmitted and the number of remaining integers

自然中文：
> 然后这个流程会给出两个结果：传输了多少对，以及还剩多少个整数。

这里 `indicate` 不要死翻成“指示”，在题面里更接近：
- 给出
- 表示
- 告诉你

## 29.5 这题哪里有点奇怪 / 容易误导

### 1）`secure manner` 基本是废话背景
它会影响阅读负担，但对算法规则没贡献。

---

### 2）`until all pairs are sent` 写得有点模糊
它更准确真正想说的是：

> 直到所有**能组成的相同整数对**都被发送完

因为不是所有整数都会发走，而是只有能配成对的那些。

---

### 3）有些版本的解释会把“剩余数组”写错
例如输入：

```text
12 10 6 12 10 12 1 21
```

频率是：
- 12 -> 3
- 10 -> 2
- 6 -> 1
- 1 -> 1
- 21 -> 1

所以：
- pair 数 = `3 // 2 + 2 // 2 = 1 + 1 = 2`
- remaining 数 = `3 % 2 + 2 % 2 + 1 + 1 + 1 = 1 + 0 + 1 + 1 + 1 = 4`

如果题目只问**剩余个数**，那么答案 `2 4` 没问题。

但是如果某个解释还进一步写出“剩下的具体数组是某某某”，那就有可能写错，或者至少写得不严谨。  
因为“选哪两个相同的数先配成对”如果要追踪**具体剩余序列**，还会牵涉到下标和顺序问题；而题目本身根本不需要你输出剩余序列，只需要输出**剩余个数**。

### 4）你怀疑“是不是 12 10 6 1，还是 6 12 1 21”
你的怀疑是合理的，不是你理解错了。

- 如果题目只问**剩余个数**，那么无论你觉得剩下的是 `12 10 6 1`，还是 `6 12 1 21`，都不影响最终答案，因为个数都是 `4`。
- 如果题目或解释试图明确写出“剩余列表的具体内容和顺序”，那就必须说明“配对时按什么下标规则移除”，否则就会不严谨，甚至写错。

所以这里更准确的结论是：

> **题目的输出 `2 4` 是对的。**  
> **你对解释里“剩余具体序列”产生怀疑，也是对的。**  
> 问题不在你，而在题目解释如果去写“剩余数组长什么样”，就容易写得不严谨。

## 29.6 这题真正考什么

本质上就是统计频率：

- 对数 = `freq // 2`
- 剩余数 = `freq % 2`

把所有数字的结果加起来即可。

## 29.7 这题可以顺手记住的词

- `transmit` = 传输
- `transmitting` = 传输中 / 传输这个过程里
- `transmission` = 传输（名词）
- `secure` = 安全的
- `identify` = 找到、确定
- `procedure` = 流程、方法
- `pair` = 一对
- `a pair of` = 一对……
- `identical` = 相同的
- `remaining` = 剩余的
- `process` = 过程
- `continue` = 继续
- `indicate` = 给出、表示
- `selected` = 被选出的
- `transmitted` = 被传输的

## 29.8 这类题的读法提醒

以后再遇到这类“故事壳”题，可以先分两层：

### A. 背景壳（可以快速略过）
- company
- secure manner
- new server

### B. 真规则（必须抓住）
- pair of identical integers
- continues until all pairs are sent
- number of pairs transmitted
- number of integers remaining

---

# 30. 新增补充：`Could we present it later?`

> 这是你新增的工作场景例句。  
> 这一节专门解决几个问题：  
> 1. `present` 在这句话里是什么意思、是什么词性  
> 2. 为什么它会有那么多词性和意思  
> 3. 你怎么判断一个多义词在句子里的词性和含义  
> 4. `-ly / -ed / 原词多词性` 到底怎么理解  
> 5. 这种词怎么学，才不是死记硬背

## 30.1 句子先做人话版

原句：

```text
Could we present it later?
```

最自然的中文通常是：

- 我们可以晚一点再讲这个吗？
- 我们可以稍后再汇报这个吗？
- 我们能不能晚点再展示它？

具体翻成“讲 / 汇报 / 展示”，要看工作场景里的 `it` 是什么：

- 如果 `it` 是一个方案、PPT、项目更新，常常是：**汇报 / 讲 / 过**
- 如果 `it` 是一个产品、demo、设计稿，常常是：**展示 / 演示**

---

## 30.2 这句话里 `present` 是什么词性

在这句话里，`present` 是 **动词原形**。

为什么？

因为句子结构是：

- `Could` = 情态动词
- `we` = 主语
- `present` = 动词原形
- `it` = 宾语
- `later` = 副词

也就是：

```text
Could + 主语 + 动词原形 + ...
```

这是一个非常高频的英语句型。

例如：

- Could we **go** later?
- Could we **discuss** this tomorrow?
- Could we **present** it later?

所以这里你不需要先查词典一堆词性。  
你先看句子骨架，就知道这里 `present` 一定先按**动词**处理。

---

## 30.3 这句话里 `present` 是什么意思

这里最合适的意思不是“礼物”，也不是“目前的”，而是动词：

- 展示
- 讲解
- 汇报
- 呈现

工作语境里最常见是：

- present a plan = 汇报一个方案
- present a deck = 讲 PPT / 过 deck
- present the results = 汇报结果
- present a proposal = 提交并讲解一个提案

所以：

```text
Could we present it later?
```

通常就是：

> 我们能晚点再汇报 / 展示这个吗？

---

## 30.4 `present` 为什么会这么多意思

学习多义词时，不要把它想成：

> 一个英文词 = 十几个互不相关的中文意思

更好的想法是：

> 一个英文词通常有一个**核心感觉**，不同场景把这个核心感觉“展开”成不同含义。

### `present` 的核心感觉
你可以先抓成：

> **把某物带到别人面前 / 放到当前场景里**

有了这个核心感觉，几个常见意思就连起来了：

### A. 动词：展示、呈现、汇报
- present an idea  
  把想法拿到别人面前
- present a report  
  把报告讲给别人

### B. 名词：礼物
- a birthday present  
  一个被“送到别人面前”的东西  
  中文就变成：礼物

### C. 形容词：在场的；当前的
- everyone present  
  每个人都“在场”
- the present situation  
  当前情况，也就是“现在眼前这个情况”

所以它看起来像很多意思，其实可以围着一个比较统一的感觉理解。

---

## 30.5 一个特别实用的小点：重音也能帮你分辨

`present` 这个词很实用，因为**重音变化常常和词性有关**：

### 名词 / 形容词
- **PRE**sent  
- a present = 礼物
- the present situation = 当前情况
- all members are present = 所有人都在场

### 动词
- pre**SENT**
- present the report = 汇报报告
- present the idea = 介绍想法

这个现象不是所有词都这样，但 `present` 正好是一个很好用的例子。

---

## 30.6 这类多义词怎么判断词性

不要先盯词典，先看它**在句子里的位置**。

### 方法一：看前面是什么

#### 1）前面如果是情态动词
比如：
- can
- could
- may
- might
- should
- would
- must

那后面通常接**动词原形**

例如：
- could go
- should try
- may happen
- could present

所以：

```text
Could we present it later?
```

这里的 `present` 必然先按动词看。

---

### 方法二：看后面接了什么

`present` 后面接了：

- `it`

这说明它在“对一个东西做动作”：

> present it

这很像动词带宾语。

例如：
- discuss it
- explain it
- show it
- present it

---

### 方法三：看整个句子槽位

句子是：

- Could / we / present / it / later

你可以把它理解为：

- 情态动词 / 主语 / 动词 / 宾语 / 时间副词

这个槽位本身就决定了 `present` 该是动词。

---

## 30.7 副词不是只修饰形容词

你之前的印象需要纠正一下。

副词经常可以修饰：

1. **动词**
   - run quickly
2. **形容词**
   - very good
3. **另一个副词**
   - very quickly
4. **整个句子**
   - Fortunately, we finished on time.

所以不是“副词主要修饰形容词”。  
它能修饰的范围比你原来记的更大。

---

## 30.8 你之前关于 `-ly` 的印象，哪里对，哪里不对

你说你以前以为：

> 后面加 `-ly` 的大概率是副词，去掉 `-ly` 就是形容词

这个想法**只对一半**。

### 对的地方
很多副词确实长这样：

- quick → quickly
- slow → slowly
- careful → carefully
- usual → usually

---

### 不对的地方 1：不是所有副词都有 `-ly`
例如：

- fast
- hard
- late
- later
- early

这些都能作副词，但不一定长得像 `quickly`。

例如：
- work hard
- come early
- talk later

所以你不能看到不是 `-ly` 就觉得它一定不是副词。

---

### 不对的地方 2：不是所有 `-ly` 词都是副词
例如：

- friendly
- lovely
- lively
- lonely

这些看着像副词，但通常其实是**形容词**。

例如：
- a friendly person
- a lovely day

所以：

> `-ly` 是一个很有帮助的线索，但不是万能规则。

---

## 30.9 `later` 在你这个句子里是什么词

在：

```text
Could we present it later?
```

里，`later` 是**副词**，表示：

- 稍后
- 晚一点
- 之后

它修饰的是整个动作 `present`：

> 晚一点再 present

这里刚好也能顺手帮你纠正一个常见误区：

- `late` 可以作形容词，也可以作副词
- `later` 是比较级形式，也常当时间副词用，表示“更晚些 / 稍后”

所以这里不要因为它没有 `-ly` 就不敢把它当副词。

---

## 30.10 你之前关于 `-ed` 的印象，怎么纠正

你提到：

> 好像有些动词加 `ed` 变成形容词？好像不对，我一定是记错了

你其实不是完全记错，而是**记了一半**。

### 对的部分
有很多动词的 `-ed` 形式，确实可以带形容词性质：

- tired
- interested
- excited
- broken
- closed
- finished

例如：
- I am tired.
- She is interested in math.
- the broken window

这里它们都带有“状态”或“被……之后的结果”的感觉。

---

### 但别把 `-ed` 简化成“加了就变形容词”
因为 `-ed` 还可能是：

- 过去式
- 过去分词
- 过去分词当形容词

所以你还是要看句子位置。

例如：

#### A. 过去式
- He closed the door.

#### B. 过去分词，用于被动
- The door was closed.

#### C. 过去分词当形容词
- the closed door

所以不是“见到 `-ed` 就知道词性”，而是：

> `-ed` 先提示你：这跟“完成 / 被动 / 结果状态”有关  
> 具体是动词还是形容词，再看句子槽位。

---

## 30.11 那像 `present` 这种原词就多词性的，怎么学

### 不建议的学法
看到词典就背：

- present n. 礼物；现在
- present v. 展示；提交；授予
- present adj. 当前的；在场的

这样背，短期看很完整，长期很容易崩。

---

### 更好的学法：一词三层

#### 第一层：核心感觉
`present` 的核心感觉：
> 把东西带到眼前 / 放到当前场景里

#### 第二层：高频词性
- v. 展示 / 汇报 / 提出
- n. 礼物
- adj. 当前的；在场的

#### 第三层：高频场景例句
- Could we present it later?  
  我们能稍后汇报这个吗？
- I have a present for you.  
  我有个礼物给你。
- the present situation  
  当前情况
- everyone present  
  在场的所有人

这样记，既不死，也不散。

---

## 30.12 这类词你怎么“学到就用”

### A. 工作场景里的 `present`
- Could we present it later?  
  我们能晚点再汇报这个吗？
- I will present the findings tomorrow.  
  我明天汇报这些发现。
- She presented the roadmap to the team.  
  她向团队汇报了路线图。
- We need to present a clearer plan.  
  我们需要拿出一个更清晰的方案。

### B. 生活场景里的 `present`
- I bought a present for my friend.  
  我给朋友买了个礼物。

### C. “当前的 / 在场的”
- the present situation is difficult  
  当前情况比较困难
- All members are present.  
  所有成员都在场。

---

## 30.13 给你一个“多义词判断模板”

以后你再遇到一个你很怕的词，比如：

- present
- record
- report
- change
- project
- file

你先不要慌，也别先翻词典一大片。

按这个顺序看：

### 第一步：看句子骨架
它现在在句子里处于哪个槽位？

### 第二步：看前面的词
- 情态动词后？大概率动词原形
- 冠词后？大概率名词
- 系动词后？可能形容词/名词/分词

### 第三步：看后面的词
- 后面接宾语？常是动词
- 后面接名词？可能是形容词
- 自己前面有 `a / the`？可能是名词

### 第四步：再从几个常见意思里选最通顺的
不是“全背”，而是“选最符合当前句子结构和场景的那个”。

---

## 30.14 这句你可以直接收进脑子里的结论

```text
Could we present it later?
```

你可以直接记成：

- `present` 在这里是**动词**
- 因为它在 `could + we + ___ + it` 这个槽位里
- 意思通常是：**展示 / 汇报 / 讲**
- 整句通常是：**我们能晚点再汇报这个吗？**

---

## 30.15 这一节和你之前学到的知识，怎么串起来

这节其实把你前面零散问过的很多点串起来了：

- 不靠词典堆义项，而靠**核心感觉**
- 不先死抠词性，而先看**句子槽位**
- `-ly` 是线索，不是铁律
- `-ed` 可能跟完成/被动/状态有关，但不是自动等于“形容词”
- 很多词会一词多性，不稀奇；关键是看它在当前句子里**扮演什么角色**

所以这次的 `present`，你可以把它当成一个“综合练习题”。

---

# 31. 新增补充：`identical / identified / indicate / for / is transmitting`

> 这一节记录你这次新增的内容，并系统回答下面几个问题：  
> 1. `identical` 和 `identified` 很像，怎么记，怎么防混  
> 2. `indicate` 到底有什么用，为什么不用 `give / show / represent / display / point out`  
> 3. `for` 的本质是什么意思，怎么系统学  
> 4. `is transmitting` 是不是现在进行时

## 31.1 先把你补充的内容记进笔记

### `identical`
- 词性：形容词（adj.）
- 常见意思：
  - 完全相同的
  - 一模一样的
  - 同一的
  - （双胞胎）同卵的
  - 恒等的

在算法题里最常见、最该优先记的是：

> **identical = 完全相同的 / 一模一样的**

例如：
- identical numbers = 相同的数字
- identical integers = 相同的整数

---

### `identified`
- 词性：
  - 过去式 / 过去分词（来自 `identify`）
  - 有时也带形容词性质
- 常见意思：
  - 被识别的
  - 被确认的
  - 被找出的
  - 已确定的

例如：
- They have identified a procedure.  
  他们已经确定出一种流程/方法。

这里最自然的理解是：

> **identified = 已经找出来的 / 已经确定下来的**

---

### `indicate`
- 词性：动词（v.）
- 常见意思：
  - 表明
  - 显示
  - 暗示
  - 指出
  - 标示
  - （英式）打转向灯

题目原句：

```text
The procedure will then indicate the number of pairs transmitted and the number of remaining integers.
```

更自然的读法：

> 然后这个流程会给出两个结果：传输了多少对，以及还剩多少个整数。

这里 `indicate` 不要死翻成“指示”，在题里更接近：

- 给出
- 表示
- 告诉你
- 表明

---

## 31.2 `identical` 和 `identified` 怎么区分，怎么记

这是典型的“长得像，但不是一回事”的词。

### 核心区别先抓住

#### `identical`
是形容词，核心是：

> **两个东西一模一样**

例如：
- identical twins  
  同卵双胞胎
- identical values  
  完全相同的值

你可以把它脑补成：

> identity 很像 → 身份/特征完全一样 → identical

---

#### `identified`
来自动词 `identify`，核心是：

> **把某个东西识别出来、确认出来**

例如：
- identified issues  
  已识别出的问题
- identified procedure  
  已经确定出来的流程

你可以把它脑补成：

> identify = 识别/确认  
> identified = 已经被识别/被确认了

---

### 一个特别实用的防混方法：不要只看拼写，要看“句子槽位”

#### 如果它在名词前面，描述“这个东西是不是一样”
那常常是 `identical`

- identical integers
- identical items
- identical results

#### 如果它带有“被找出来 / 被确认出来”的感觉
那常常是 `identified`

- identified problem
- identified procedure
- identified suspect

---

### 最实用的记忆法：记一对“对照例句”

- These two numbers are **identical**.  
  这两个数完全相同。

- We have **identified** the issue.  
  我们已经识别出这个问题。

你要记的不是“差几个字母”，而是：

- `identical` = 一样
- `identified` = 认出来 / 确认出来

---

## 31.3 `indicate` 到底有什么用

你这个问题问得特别好，因为你不是在问词典义项，而是在问：

> **为什么英语已经有 give / show / represent / display / point out 了，还要有 indicate？**

这才是学词最值钱的问法。

### 先给结论
`indicate` 的核心感觉不是单纯“给你看”，也不是单纯“指出来”，而是：

> **让某个信息显露出来，让你能据此知道某个事实**

你可以先抓成：

> **表明、显示出、暗示出**

它比 `give` 更抽象，比 `display` 更像“信息层面的显示”，比 `point out` 更弱、更书面。

---

### 为什么这里不用 `give`

你写的句子：

```text
The procedure will then give you the number of pairs transmitted and the number of remaining integers.
```

语法上不是完全错，别人也能懂。  
但它更口语、更直白，也更像“流程把数字递给你”。

而 `indicate` 更像：

> 这个流程/结果**会表明**有多少对、剩多少个

所以题面里用 `indicate`，语气更书面、更像说明文。

---

### 为什么这里不用 `represent`

`represent` 的核心感觉更像：

> **A 代表 B / A 表示 B**

例如：
- N represents the number of elements.
- This symbol represents speed.

它强调的是：

> 一个东西和另一个意义之间的“对应关系”

而 `indicate` 更像：

> 某种结果、迹象、数据，让你知道某件事

例如：
- The result indicates a problem.  
  这个结果表明有问题。
- The numbers indicate growth.  
  这些数字表明在增长。

所以：

- `represent` = 代表、对应
- `indicate` = 表明、显示出、暗示出

两者不一样。

---

### 为什么这里不用 `display`

`display` 更偏：

> 把东西直接展示在表面/屏幕上

例如：
- The screen displays the result.
- The chart displays the data.

它更像“视觉展示”。

而 `indicate` 更偏：

> 信息本身在表明什么

例如：
- The data indicate a trend.  
  数据表明一种趋势。
- The symptoms indicate infection.  
  症状表明感染。

所以：

- `display` = 展示出来
- `indicate` = 显示出某种信息含义

---

### 为什么这里不用 `point out`

`point out` 更偏：

> 人主动指出来、点出来

例如：
- He pointed out the mistake.
- She pointed out an inconsistency.

而 `indicate` 可以不强调“谁主动指”，而是更客观：

- The report indicates that sales are down.  
  报告表明销售下降。

这就是说明文和正式表达里很爱用 `indicate` 的原因。

---

### 那 `indicate` 最值钱的场景是什么

#### 1）数据 / 结果 / 报告 / 图表
- The results indicate improvement.
- The data indicate a problem.
- The chart indicates a downward trend.

#### 2）信号 / 标志 / 迹象
- A red light indicates danger.
- These signs indicate stress.

#### 3）题面 / 说明文 / 正式表达
- The procedure will indicate ...
- The output indicates ...
- The value indicates ...

也就是说：

> `indicate` 很适合“客观地表明某个信息”。

---

### 你能不能硬翻成“指示”

多数时候不建议。

因为中文“指示”很容易让人想到：
- 上级给下级命令
- 路牌指示
- 方向指引

但英文 `indicate` 在大量场景里其实更自然是：

- 表明
- 显示
- 说明
- 暗示
- 给出

所以不要被词典第一页那个“指示”绑死。

---

## 31.4 `for` 的本质是什么意思

这是个非常大的问题。你这次提得特别好。

### 一个实用版本的结论
`for` 的核心感觉你可以先抓成：

> **朝向某个对象 / 为了某个对象 / 适用于某个对象**

也就是：

> 某事和“后面那个东西”有目标、用途、对象、对应关系。

---

### 在这题里：`a procedure for transmission`

不要死翻成“为了传输”。

更自然理解是：

> **用于传输的流程**
> **传输用的流程**
> **关于传输的流程**

这里的 `for` 更接近：

> **用途 / 对象**

---

### `for` 的高频几类用法

#### 1）目的 / 用途
- a tool for cutting  
  用来切东西的工具
- medicine for pain  
  止痛药
- a procedure for transmission  
  用于传输的流程

#### 2）对象 / 适用对象
- This gift is for you.  
  这个礼物是给你的。
- a room for guests  
  给客人住的房间
- software for children  
  适合儿童的软件

#### 3）原因 / 交换 / 代价
- Thank you for your help.  
  因为你的帮助，谢谢你。
- I bought it for ten dollars.  
  我花十美元买的。
- punish someone for lying  
  因撒谎而惩罚某人

#### 4）时间长度
- for two hours  
  持续两小时
- for a long time  
  很久

---

### 你问：`for` 前后接名词吗？

可以，完全可以。

例如：
- a plan for growth
- a tool for cooking
- a procedure for transmission

这里都是：

- 名词 + for + 名词/动名词

也可以接代词：
- for you
- for me

也可以接动名词：
- a machine for cutting wood

---

### 一个帮助你理解 `for` 的办法

看到 `for` 时，你先不要急着翻成某一个中文词。  
先问：

> 后面的东西，是“目的”？“对象”？“用途”？“原因”？“持续时间”？

这样更稳。

---

## 31.5 `is transmitting` 是不是现在进行时

对，是的。

### 结构
- `is` = be 动词
- `transmitting` = transmit 的 `-ing` 形式

所以：

```text
A company is transmitting a message ...
```

就是：

> 一家公司正在传输一条消息……

这是**现在进行时**。

---

### 为什么这里用现在进行时

因为题面在讲一个“当前设定中的动作/场景”：

> 有一家公司正在做这件事

它在给你搭一个故事场景。

不过在算法题里，这种时态很多时候只是叙述包装。  
你做题时真正抓的是规则，不是它到底“正在传”还是“一般会传”。

---

## 31.6 这一节最值得你带走的几个结论

### 关于 `identical / identified`
不要按“只差几个字母”记，要按核心动作区别：

- `identical` = 一模一样
- `identified` = 被识别出来 / 被确认出来

### 关于 `indicate`
不要只死记成“指示”。  
更常用、更值钱的理解是：

- 表明
- 显示出
- 说明
- 给出（在题面语境里）

### 关于 `for`
先别问它固定翻成什么。  
先抓核心：

> 朝向某个对象 / 为了某个对象 / 适用于某个对象

然后再按场景落地。

### 关于 `is transmitting`
是现在进行时。  
但在算法题里，时态常常只是背景包装，不是解题重点。\n\n---

# 32. 新增补充：`occurrence / letters / disregard / sub`

> 这一节对应新的字符串题，重点解决：  
> 1. `letters`  
> 2. `occurrences`  
> 3. `disregard`  
> 4. `sub`  
> 5. 为什么 `occurrence(s) of ...` 很常见，它和 `happen / appear / show up` 这些词有什么区别

## 32.1 去掉注释后的完整题目

### Question
You are given two strings containing only English letters. Write an algorithm to count the number of occurrences of the second string in the first string. (You may disregard the case of the letters.)

### Input
The first line of the input consists of a string `parent`, representing the first string.  
The second line consists of a string `sub`, representing the second string.

### Output
Print an integer representing the number of occurrences of `sub` in `parent`. If no occurrence of `sub` is found in `parent`, then print `0`.

### Example

**Input**
```text
TimisplayinginthehouseofTimwiththetoysofTim
Tim
```

**Output**
```text
3
```

**Explanation**
Tim occurs 3 times in the first string.  
So, the output is 3.

---

## 32.2 这道题到底在说什么

题目的意思很简单：

- 给你两个字符串
- 在第一个字符串里找第二个字符串出现了多少次
- 不区分大小写

例如：

```text
parent = TimisplayinginthehouseofTimwiththetoysofTim
sub = Tim
```

那么 `Tim` 一共出现了 3 次，所以输出：

```text
3
```

---

## 32.3 `letters`

### 基本意思
`letter` 最常见有两个意思：

1. 字母
2. 信件

在这题里当然是：

> **字母**

所以：

```text
English letters
```

就是：

> 英文字母

---

## 32.4 `occurrence / occurrences`

### 基本意思
`occurrence` 是名词，最核心的意思是：

> **出现这件事 / 发生这件事**

在这道题里最自然的是：

> **出现次数里的“一次出现”**
> 或
> **一次匹配**

所以：

```text
the number of occurrences of the second string in the first string
```

就是：

> 第二个字符串在第一个字符串中出现的次数

---

### 为什么你会觉得这个词难
因为中文里“出现”很常见，但英文里你更熟的是动词：

- happen
- appear
- occur
- show up

而 `occurrence` 是把“出现”这件事变成了一个**名词**。

也就是说：

- occur = 出现（动词）
- occurrence = 一次出现 / 出现这件事（名词）

这就是它的本质。

---

### 最实用的理解
你在算法题里可以先直接把它收成：

> `occurrence` = 一次出现 / 一次匹配  
> `occurrences` = 出现次数 / 多次出现

这比背一堆抽象词典义项更有用。

---

### 高频搭配：`occurrence(s) of ...`
这不是那种必须死背的“固定短语”，但它是一个**非常常见的表达模式**。

结构是：

```text
occurrence(s) of + 某个东西
```

意思就是：

> 某个东西的出现（一次或多次）

例如：

- occurrences of a word  
  一个单词的出现次数
- occurrences of an error  
  错误出现的次数
- occurrences of `sub` in `parent`  
  `sub` 在 `parent` 里的出现次数

所以它不是“成语型固定搭配”，但它**非常高频，非常值得熟悉**。

---

### 和其他“出现”类词有什么区别

#### 1）`occur`
这是动词，本身就是“发生 / 出现”

- The error occurred yesterday.  
  错误昨天发生了。
- The word occurs three times.  
  这个单词出现了三次。

#### 2）`occurrence`
这是名词，强调“一次出现”或“出现这件事”

- the occurrence of an error  
  一个错误的发生
- the number of occurrences  
  出现次数

#### 3）`happen`
更口语、更常见于一般事件发生

- What happened?  
  发生了什么？

#### 4）`appear`
更像“显现出来、出现”

- A pattern appears in the data.  
  数据中出现了一种模式。

所以：

- `occur` / `occurrence` 更常见于书面、技术、统计、说明文
- `happen` 更日常
- `appear` 更偏“显现出来”

---

### 为什么题目爱用 `occurrence`
因为它很适合这种“统计出现次数”的表达。

例如：
- count the number of occurrences
- number of occurrences of a substring
- if no occurrence is found

这些都是算法题和技术文档里的高频写法。

---

### 你怎么学会用这个词造句
你不需要一开始就会很多复杂句。  
先记 5 句最值钱的：

- This word occurs twice.  
  这个词出现了两次。
- The number of occurrences is 5.  
  出现次数是 5。
- We counted the occurrences of "Tim".  
  我们统计了 "Tim" 的出现次数。
- No occurrence was found.  
  没找到任何一次出现。
- This error occurs often.  
  这个错误经常出现。

你真正要掌握的是两个核心模板：

```text
X occurs N times.
the number of occurrences of X
```

---

## 32.5 `disregard`

### 基本意思
`disregard` = 不理会、忽略、不考虑

在题里：

```text
You may disregard the case of the letters.
```

最自然就是：

> 你可以忽略字母的大小写。

这里的 `case` 不是“案例”，而是：

> 大小写

所以整句意思是：

> 可以不区分大小写。

---

### `disregard` 和 `ignore` 的关系
很多时候它们可以很接近。

- disregard = 不予考虑、忽略
- ignore = 忽视、不理

在题面里，`disregard` 常常比 `ignore` 更书面一点。

所以这句也可以用人话理解成：

> Ignore uppercase/lowercase differences.

---

## 32.6 `sub`

### 为什么你会觉得这个词奇怪
因为 `sub` 在这题里不像普通自然英语单词，更像一个**变量名**。

它通常是：

> `substring` 的缩写

也就是：

- parent = 母串 / 主字符串
- sub = 子串 / 要查找的字符串

所以这里不要把 `sub` 当成普通词汇硬背。  
它更像题目作者取的一个简写名字。

---

### 你可以这样理解这两个变量
- `parent`：被搜索的那个大字符串
- `sub`：要去里面找的小字符串

---

## 32.7 题面里还有个值得顺手记的表达

### `containing only English letters`
意思是：

> 只包含英文字母

这里：
- `containing` = 包含着
- `only` = 只
- `English letters` = 英文字母

这是算法题高频模板之一。

例如：
- a string containing only digits  
  一个只包含数字的字符串
- a string containing lowercase letters  
  一个只包含小写字母的字符串

---

## 32.8 这一题里哪里有点不自然

### 1）`sub` 很像程序变量名
它不是很自然的日常英语，而是题面风格里的缩写变量名。

### 2）`disregard the case of the letters`
语法没问题，但题面里更常见的人话是：

- case-insensitive
- ignore the case
- treat uppercase and lowercase as the same

不过它现在这个写法仍然是能读懂的。

---

## 32.9 这一节最值得你带走的结论

### `letters`
先优先记：
> 英文字母

### `occurrence`
不要抽象背成“发生、出现、存在”。  
在算法题里优先记：
> **一次出现 / 一次匹配 / 出现次数**

### `occurrences of ...`
不是那种死固定短语，但它是很高频的技术表达模式：
> 某个东西的出现次数

### `disregard`
在题里通常很实用地理解为：
> 忽略 / 不考虑

### `sub`
通常不是普通词，而是 `substring` 的缩写变量名\n

------

# 33. 新题补充：`vowels / alphabet / eliminate` 这道题

> 这一节对应新的字符串题。
> 重点解决：
>
> 1. `vowels`
> 2. `alphabet`
> 3. `eliminate`
> 4. 顺手补上这题里很值得一起学的结构：`from`、`given string`、`after removing ...`

------

## 33.1 原题去掉包装后的干净版本

```text
Question

The vowels in the English alphabet are: (a, e, i, o, u, A, E, I, O, U). Write an algorithm to eliminate all vowels from a given string.

Input
The input consists of the given string.

Output
Print a string after removing all the vowels from the given string.

Example

Input:
MynameisAnthony

Output:
Mynmsnthny
```

------

## 33.2 这道题的人话版

题目的意思非常直接：

1. 英文字母里的元音包括 `a e i o u` 和它们的大写。
2. 给你一个字符串。
3. 把里面所有元音字母删掉。
4. 输出删掉元音之后的新字符串。

例如：

```text
MynameisAnthony
```

去掉：

- `a`
- `e`
- `i`
- `A`
- `o`

这些元音以后，剩下：

```text
Mynmsnthny
```

------

## 33.3 你这次卡住的 3 个词

这次最关键的确实就是：

- `vowels`
- `alphabet`
- `eliminate`

下面我不只给中文，还会给你“核心感觉 + 使用场景 + 举一反三”。

------

## 33.4 `vowels`

### 基本形式

- 单数：`vowel`
- 复数：`vowels`

### 这题里的意思

这题里就是：

> 元音字母

题目已经直接告诉你：

```text
(a, e, i, o, u, A, E, I, O, U)
```

所以这里完全不用猜。

------

### 更底层怎么理解

`vowel` 严格说先是“元音”这个概念。
但在算法题这种题面里，通常直接落到：

> 元音字母

所以你在这类题里可以先稳稳记成：

- `vowel` = 元音字母
- `vowels` = 元音字母们 / 元音字母（复数）

------

### 反义搭配

你之后很可能还会遇到：

- `consonant` = 辅音字母
- `consonants` = 辅音字母（复数）

所以以后常见题型会是：

- count vowels
- count consonants
- separate vowels and consonants

------

### 高频场景例句

- The string contains only vowels.
  这个字符串只包含元音字母。
- Count the number of vowels in the word.
  统计这个单词中元音字母的个数。
- Remove all vowels from the sentence.
  从句子中删除所有元音字母。

------

### 一个很值钱的补充

以后你看到：

```text
vowel(s) in a string
```

大概率就是：

> 字符串中的元音字母

而不是很抽象的语言学讨论。

也就是说，在算法题里它通常非常“落地”。

------

## 33.5 `alphabet`

### 基本意思

`alphabet` 最常见就是：

> 字母表

所以：

```text
the English alphabet
```

就是：

> 英文字母表

------

### 这题里怎么理解最自然

原句：

```text
The vowels in the English alphabet are ...
```

自然中文就是：

> 英文字母表中的元音字母有……

这里的 `alphabet` 不是“字母”本身，而是：

> 整套字母系统 / 整个字母表

------

### `alphabet` 和 `letters` 的区别

这个点很重要。

#### `letters`

更像：

> 一个一个具体的字母

例如：

- lowercase letters = 小写字母
- English letters = 英文字母

#### `alphabet`

更像：

> 整套字母表

例如：

- learn the alphabet = 学字母表
- the English alphabet = 英文字母表

所以你可以粗略记成：

- `letters` = 字母（一个个具体单位）
- `alphabet` = 字母表（整套系统）

------

### 高频扩展

你以后很可能还会见到：

#### `alphabetical order`

> 按字母顺序

例如：

- Sort the words in alphabetical order.
  把这些单词按字母顺序排序。

#### `alphabetically`

> 按字母顺序地

例如：

- The names are arranged alphabetically.
  这些名字是按字母顺序排列的。

这个词组很值得顺手记住，因为它在题面、文档、表格场景里都常见。

------

## 33.6 `eliminate`

### 基本意思

`eliminate` 最核心的感觉是：

> 去掉，使它不再留下来

所以它常见可落成：

- 消除
- 去除
- 删除
- 淘汰
- 排除

------

### 这题里是什么意思

原句：

```text
Write an algorithm to eliminate all vowels from a given string.
```

这里最自然绝对不是“消灭元音”那种很硬的翻法，
而是：

> 写一个算法，从给定字符串中去掉所有元音字母。

也就是：

- `eliminate all vowels` = 去掉所有元音
- `from a given string` = 从给定字符串中

------

### `eliminate` 的核心感觉

你最好不要把它背成很多中文义项，而是抓这个底层感觉：

> 把某个东西从系统、集合、范围里清出去

然后再按场景落地：

#### 在算法/数据题里

- eliminate duplicates
  去重 / 去掉重复项
- eliminate spaces
  去掉空格
- eliminate vowels
  去掉元音

#### 在比赛场景里

- eliminate a team
  淘汰一支队伍

#### 在抽象表达里

- eliminate risk
  消除风险
- eliminate errors
  消除错误
- eliminate the possibility
  排除这种可能性

------

### 和 `remove` 的关系

这题里，`eliminate` 和 `remove` 很接近。

例如：

- eliminate all vowels
- remove all vowels

都能理解成：

> 去掉所有元音

但语气上通常有一点区别：

#### `remove`

更直接、更日常：

> 拿掉、移除

#### `eliminate`

更书面一点，带一点：

> 清除掉、排除掉、不让它留下来

所以算法题里出题人有时会用 `eliminate`，但你脑中可以先落地成：

> remove

这样更好懂。

------

## 33.7 这题里顺手必须补的 3 个结构

这题真正要读顺，不只是认出三个词，还要顺手把这些结构看懂：

- `from`
- `given string`
- `after removing ...`

------

## 33.8 `from`：这题里非常关键

原句：

```text
eliminate all vowels from a given string
```

这里 `from` 不能只是机械理解成“来自”。

这里它更接近：

> 从……中拿掉 / 从……里去除

所以：

- remove A from B
  从 B 中去掉 A
- eliminate A from B
  从 B 中消除 A
- delete A from B
  从 B 中删除 A

这是一个特别常见的模板。

------

### 很值得你直接记住的模式

```text
remove X from Y
eliminate X from Y
delete X from Y
```

都可以先理解为：

> 从 Y 里去掉 X

例如：

- Remove all spaces from the string.
  从字符串中删除所有空格。
- Eliminate duplicate numbers from the array.
  从数组中去掉重复数字。
- Delete invalid characters from the input.
  从输入中删除无效字符。

------

## 33.9 `given string`

这个你前面已经学过一次，这里刚好复习。

```text
a given string
```

意思是：

> 一个给定的字符串

这里的 `given` 不是普通动词原形，而是过去分词，像形容词一样修饰 `string`。

所以：

- given string = 给定字符串
- given array = 给定数组
- given number = 给定数字

在算法题里几乎是超高频模板。

------

## 33.10 `after removing ...`

输出部分原句：

```text
Print a string after removing all the vowels from the given string.
```

这里最该注意的是：

```text
after removing ...
```

这正好和你前面学过的 `after doing` 完全连起来。

------

### 为什么用 `removing`

因为 `after` 后面常常接“事情”。

所以：

- after sorting
- after reading
- after removing

都可以理解成：

> 在“做完这件事”之后

也就是：

- after removing all the vowels
  在去掉所有元音之后

------

### 底层逻辑

你可以这样想：

- `remove` 是动作本身
- `removing` 是“去掉这件事”

所以：

```text
after removing all the vowels
```

就是：

> 在“去掉所有元音这件事”之后

这个理解方式非常稳。

------

## 33.11 重点句子精拆

------

### 句子 1

```text
The vowels in the English alphabet are: (a, e, i, o, u, A, E, I, O, U).
```

### 自然中文

英文字母表中的元音字母有：`a, e, i, o, u` 以及它们的大写形式。

### 结构拆解

- `The vowels` = 元音字母
- `in the English alphabet` = 在英文字母表中 / 英文字母表中的
- `are` = 是
- 后面冒号列举具体内容

### 你真正要学会的不是翻译，而是这个结构

以后你会常见：

```text
The X in Y are ...
```

它通常就是：

> Y 中的 X 有……

例如：

- The digits in the string are ...
  字符串中的数字有……
- The elements in the array are ...
  数组中的元素有……

------

### 句子 2

```text
Write an algorithm to eliminate all vowels from a given string.
```

### 自然中文

写一个算法，从给定字符串中去掉所有元音字母。

### 结构拆解

- `Write an algorithm` = 写一个算法
- `to eliminate` = 用来去掉 / 去消除
- `all vowels` = 所有元音字母
- `from a given string` = 从给定字符串中

### 这里最值钱的点

`to eliminate` 在这里表示“目的”。

也就是：

> 写一个算法，目的是去掉……

这个 `to do` 结构在题面里非常常见。

例如：

- Write an algorithm to find the maximum.
  写一个算法求最大值。
- Write an algorithm to reverse the string.
  写一个算法翻转字符串。

------

### 句子 3

```text
The input consists of the given string.
```

### 自然中文

输入就是给定的字符串。

### 为什么不必硬翻成“由……组成”

`consists of` 固然是“由……组成”，
但在题面里经常可以直接自然化成：

- 输入是……
- 第一行是……
- 第二行包含……

所以这里最自然就是：

> 输入 consists of the given string
> = 输入就是这个给定字符串

------

### 句子 4

```text
Print a string after removing all the vowels from the given string.
```

### 自然中文

输出删去所有元音字母之后得到的字符串。

### 这句为什么有点“平台英语味”

因为它写得能懂，但不算特别漂亮。

更自然一点的英语会像：

- Print the string after removing all vowels.
- Print the resulting string after removing all vowels from the input.

也就是说，原题这种句子你要学会：

> 抓意思，不要要求它每一句都像教材英语那样漂亮。

------

## 33.12 这题真正考你的，不只是单词，而是“压缩理解”

你这次会被卡住，其实很正常。
因为这题虽然短，但里面有几层压缩：

1. `vowels` 你得知道是“元音字母”
2. `alphabet` 你得知道是“字母表”
3. `eliminate ... from ...` 你得知道是“从……中去掉……”
4. `after removing ...` 你得知道是“去掉……之后”

所以它不是“单词会不会”的问题，而是：

> 你能不能把短句子一口气整体吃下去。

这正是你这份笔记一直在训练的能力。

------

## 33.13 这题的“人话重组”示范

如果你不按英文顺序硬翻，这题最自然可以直接重组为：

> 已知元音字母是 `a e i o u` 和它们的大写。
> 现在给你一个字符串，请把其中所有元音字母删除，并输出删除后的结果字符串。

这就是你以后要训练的能力：

> 不是逐词贴中文，
> 而是把英文里的信息重新排成自然中文。

------

## 33.14 这题还可以顺手记住的高频词

这三个之外，我建议你顺手再带走几个很值钱的词：

### `string`

- 字符串
- 算法题超高频词

### `remove`

- 删除
- 去掉
- 移除

### `output`

- 输出

### `input`

- 输入

### `given`

- 给定的

这些词和 `vowels / eliminate / from / after removing` 经常一起出现。

------

## 33.15 举一反三：这类题以后常怎么变形

你学一个词，最好马上扩展到相邻题型。

------

### 变形 1：去掉所有空格

```text
Remove all spaces from a string.
```

意思：

> 从字符串中删除所有空格。

这里你复用了：

- remove
- from
- string

------

### 变形 2：去掉所有数字

```text
Eliminate all digits from the input string.
```

意思：

> 从输入字符串中去掉所有数字。

这里你复用了：

- eliminate
- digits
- input string

------

### 变形 3：统计元音个数

```text
Count the number of vowels in the string.
```

意思：

> 统计字符串中元音字母的数量。

这里你复用了：

- vowels
- in the string

------

### 变形 4：只保留元音

```text
Keep only the vowels in the string.
```

意思：

> 只保留字符串中的元音字母。

这和“去掉元音”正好相反。

------

### 变形 5：不区分大小写地去掉元音

```text
Remove all vowels regardless of case.
```

意思：

> 不区分大小写地删除所有元音字母。

这里你又能连到你之前学过的：

- case
- disregard
- ignore case

------

## 33.16 `eliminate / remove / delete / exclude` 怎么区分

这一步是扩展，你学会以后会很有感觉。

### `remove`

最通用、最日常：

> 拿掉、移除

- remove spaces
- remove vowels

------

### `eliminate`

更像：

> 清除掉、排除掉、不让它留下

- eliminate duplicates
- eliminate errors
- eliminate vowels

------

### `delete`

更像：

> 删掉一个已经存在的内容

- delete a file
- delete a character
- delete this record

------

### `exclude`

更像：

> 排除在外，不算进去

- exclude invalid values
  排除无效值
- exclude the first element
  不把第一个元素算进去

------

### 你在算法题里先怎么用

最稳的做法是：

- `remove` 先记成最通用
- `eliminate` 先记成较书面的 remove
- `delete` 先记成“删除具体对象”
- `exclude` 先记成“排除在统计或范围之外”

这样不会乱。

------

## 33.17 这题的算法本身其实很简单

如果只讲思路，就是：

1. 遍历字符串里的每个字符。
2. 判断它是不是元音。
3. 如果不是元音，就保留下来。
4. 最后把保留下来的字符拼成新字符串输出。

------

## 33.18 这题最朴素的 Java 写法

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ("aeiouAEIOU".indexOf(c) == -1) {
                ans.append(c);
            }
        }

        System.out.print(ans.toString());
    }
}
```

------

## 33.19 代码里也能顺手学英语

### `indexOf`

表示：

> 查找某个字符/字符串第一次出现的位置

### `append`

表示：

> 追加

所以：

```java
ans.append(c);
```

就是：

> 把字符 `c` 追加到答案后面

这两个词以后写字符串题会常见。

------

## 33.20 这题最值得你记住的 8 个结论

### 1.

`vowel` = 元音字母
`vowels` = 元音字母（复数）

### 2.

`alphabet` = 字母表
`letters` = 一个个具体字母

### 3.

`eliminate` 的核心感觉不是死记“消灭”，而是：

> 去掉、清除、排除，使它不再留下

### 4.

`eliminate A from B`
很常见，先稳稳读成：

> 从 B 中去掉 A

### 5.

`given string`
就是：

> 给定字符串

### 6.

`after removing ...`
就是：

> 去掉……之后
> 背后逻辑仍然是你已经学过的 `after doing`

### 7.

题面里的英语不一定都很漂亮，但你要学会抓主干：

- 做什么
- 对谁做
- 用什么规则做

### 8.

以后再见到这类题，你可以快速套模板：

- count vowels
- remove vowels
- keep vowels
- ignore case
- remove X from Y