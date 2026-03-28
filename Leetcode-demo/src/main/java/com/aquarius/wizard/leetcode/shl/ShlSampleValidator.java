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
