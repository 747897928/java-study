package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 29
 *
 * Allie is working on a system that can allocate resources to the applications in a manner
 * efficient enough to allow the maximum number of applications to be executed. There are N number
 * of applications and each application is identified by a unique integer ID (1 to N). Only M
 * types of resources are available with a unique resourceID. Each application sends a request
 * message to the system. The request message includes the information regarding the request time,
 * the execution ending time, and the type of resource required for execution. Time is in the MMSS
 * format where MM is minutes and SS is seconds.
 *
 * If more than one application sends a request at the same time then only one application will be
 * approved by the system. The denied requests are automatically destroyed by the system. When
 * approving the request, the system ensures that the request will be granted to the application
 * in a way that will maximize the number of executions. The system can execute only one
 * application at a time with a given resource. It will deny all other requests for that resource
 * until the previous application has finished. Allie wants to know the maximum number of
 * applications that have been executed successfully.
 *
 * Write an algorithm to help Allie calculate the maximum number of applications that are executed
 * successfully by the system.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. applicationCount resourceTypeCount
 * 2. applicationCount lines: requestTime endTime resourceId
 *
 * About the ambiguous sentence:
 *
 * The original wording says requests with the same request time cannot all be approved, but the
 * commonly circulated sample explanation for this OA groups requests by resource type and solves
 * each resource independently with interval scheduling. This implementation follows that sample
 * behavior:
 *
 * 1. applications using different resource types can run in parallel
 * 2. for the same resource type, choose a maximum set of non-overlapping intervals
 * 3. when two applications for the same resource overlap, prefer the one that finishes earlier
 *
 * In other words, this is the classic "interval scheduling per resource, then sum the answers"
 * version.
 */
public class Q29MaximumExecutedApplicationsByResource {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int applicationCount = scanner.nextInt();
        int resourceTypeCount = scanner.nextInt();
        int[][] applications = new int[applicationCount][3];
        for (int i = 0; i < applicationCount; i++) {
            applications[i][0] = scanner.nextInt();
            applications[i][1] = scanner.nextInt();
            applications[i][2] = scanner.nextInt();
        }

        /*
         * Local practice input:
         *
         * 6 3
         * 1210 1300 1
         * 1215 1240 1
         * 1230 1315 1
         * 1250 1330 2
         * 1330 1340 2
         * 1340 1345 2
         *
         * Explanation under this interpretation:
         * resource 1 -> take [1215,1240]
         * resource 2 -> take [1250,1330], [1330,1340], [1340,1345]
         * total = 4
         */

        Q29MaximumExecutedApplicationsByResource solver =
            new Q29MaximumExecutedApplicationsByResource();
        System.out.println(solver.maximumExecutedApplications(resourceTypeCount, applications));
    }

    /**
     * 这题最麻烦的不是算法，而是题面歧义。
     *
     * 当前实现采用的解释口径已经写在类注释里：
     *
     * - 不同资源类型之间互不影响，可以并行执行
     * - 同一种资源内部，不能同时跑两个应用
     * - 所以问题被拆成：
     *   “对每一种资源，分别求最多能选几个互不重叠的区间”
     *
     * 一旦接受这个解释，题目就退化成经典区间调度：
     *
     * 1. 按 resourceId 分组
     * 2. 每组按结束时间从早到晚排序
     * 3. 贪心选择尽量早结束的区间
     * 4. 最后把各组答案相加
     *
     * 为什么“结束时间早的优先”是对的？
     *
     * 因为当前如果两个区间重叠，
     * 选结束更早的那个，后面给其他区间留下的可用时间只会更多，不会更少。
     * 这就是经典 interval scheduling 的贪心正确性。
     */
    public int maximumExecutedApplications(int resourceTypeCount, int[][] applications) {
        // 先把应用按资源类型分组。
        Map<Integer, List<Application>> groupedByResource = new HashMap<>();
        for (int resourceId = 1; resourceId <= resourceTypeCount; resourceId++) {
            groupedByResource.put(resourceId, new ArrayList<Application>());
        }
        for (int[] application : applications) {
            int resourceId = application[2];
            if (!groupedByResource.containsKey(resourceId)) {
                groupedByResource.put(resourceId, new ArrayList<Application>());
            }
            groupedByResource.get(resourceId).add(new Application(
                // 输入时间是 MMSS，需要先转成秒数，后面比较区间才稳。
                toSeconds(application[0]),
                toSeconds(application[1]),
                resourceId
            ));
        }

        // 每种资源分别做一次“最多不重叠区间数”统计，再把结果相加。
        int totalExecuted = 0;
        for (List<Application> resourceApplications : groupedByResource.values()) {
            totalExecuted += maxNonOverlappingCount(resourceApplications);
        }
        return totalExecuted;
    }

    /**
     * 经典区间调度：
     *
     * - 先按结束时间升序排
     * - 再一路扫，能接上的就接
     *
     * lastEndTime 记录上一条已经被选中的应用结束时间。
     * 只要新应用的开始时间 >= lastEndTime，就说明它能无冲突接上。
     */
    private int maxNonOverlappingCount(List<Application> resourceApplications) {
        if (resourceApplications.isEmpty()) {
            return 0;
        }
        Collections.sort(resourceApplications, Comparator
            .comparingInt((Application app) -> app.endTime)
            .thenComparingInt(app -> app.startTime));

        int count = 0;
        int lastEndTime = -1;
        for (Application application : resourceApplications) {
            if (application.startTime >= lastEndTime) {
                count++;
                lastEndTime = application.endTime;
            }
        }
        return count;
    }

    /**
     * 把 MMSS 转成“总秒数”。
     *
     * 比如：
     *
     * 1215 -> 12 分 15 秒 -> 735 秒
     *
     * 这样后面比较开始时间 / 结束时间时，就不会受十进制格式影响。
     */
    private int toSeconds(int mmss) {
        int minutes = mmss / 100;
        int seconds = mmss % 100;
        return minutes * 60 + seconds;
    }

    private static class Application {
        private final int startTime;
        private final int endTime;
        private final int resourceId;

        private Application(int startTime, int endTime, int resourceId) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.resourceId = resourceId;
        }
    }
}
