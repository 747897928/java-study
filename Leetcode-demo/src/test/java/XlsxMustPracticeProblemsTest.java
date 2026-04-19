import com.aquarius.wizard.leetcode.shl.CheapestFlightsWithinKStops;
import com.aquarius.wizard.leetcode.shl.CherryPickup;
import com.aquarius.wizard.leetcode.shl.FairDistributionOfCookies;
import com.aquarius.wizard.leetcode.shl.MinimumCostToReachDestinationInTime;
import com.aquarius.wizard.leetcode.shl.NumberOfWaysOfCuttingAPizza;
import com.aquarius.wizard.leetcode.shl.ReducingDishes;
import com.aquarius.wizard.leetcode.shl.RussianDollEnvelopes;
import com.aquarius.wizard.leetcode.shl.ShortestPathVisitingAllNodes;
import com.aquarius.wizard.leetcode.shl.UniqueBinarySearchTrees;
import org.junit.Assert;
import org.junit.Test;

public class XlsxMustPracticeProblemsTest {

    @Test
    public void uniqueBinarySearchTreesShouldMatchCatalanAndDp() {
        UniqueBinarySearchTrees solver = new UniqueBinarySearchTrees();

        Assert.assertEquals(5, solver.numTrees(3));
        Assert.assertEquals(1, solver.numTrees(1));
        Assert.assertEquals(solver.numTreesDp(5), solver.numTreesCatalan(5));
    }

    @Test
    public void russianDollEnvelopesShouldMatchQuadraticAndLisVersions() {
        RussianDollEnvelopes solver = new RussianDollEnvelopes();
        int[][] envelopes = {{5, 4}, {6, 4}, {6, 7}, {2, 3}};

        Assert.assertEquals(3, solver.maxEnvelopes(envelopes));
        Assert.assertEquals(solver.maxEnvelopesQuadratic(envelopes), solver.maxEnvelopesLis(envelopes));
    }

    @Test
    public void cherryPickupShouldMatchMemoAndBottomUp() {
        CherryPickup solver = new CherryPickup();
        int[][] grid = {{0, 1, -1}, {1, 0, -1}, {1, 1, 1}};

        Assert.assertEquals(5, solver.cherryPickup(grid));
        Assert.assertEquals(solver.cherryPickupMemo(grid), solver.cherryPickupBottomUp(grid));
    }

    @Test
    public void cheapestFlightsShouldMatchBellmanFordAndStateDijkstra() {
        CheapestFlightsWithinKStops solver = new CheapestFlightsWithinKStops();
        int[][] flights = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};

        Assert.assertEquals(200, solver.findCheapestPrice(3, flights, 0, 2, 1));
        Assert.assertEquals(
            solver.findCheapestPriceBellmanFord(3, flights, 0, 2, 1),
            solver.findCheapestPriceStateDijkstra(3, flights, 0, 2, 1)
        );
    }

    @Test
    public void shortestPathVisitingAllNodesShouldMatchBfsAndDp() {
        ShortestPathVisitingAllNodes solver = new ShortestPathVisitingAllNodes();
        int[][] graph = {{1, 2, 3}, {0}, {0}, {0}};

        Assert.assertEquals(4, solver.shortestPathLength(graph));
        Assert.assertEquals(solver.shortestPathLengthBfs(graph), solver.shortestPathLengthDp(graph));
    }

    @Test
    public void reducingDishesShouldMatchGreedyAndDp() {
        ReducingDishes solver = new ReducingDishes();
        int[] satisfaction = {-1, -8, 0, 5, -9};

        Assert.assertEquals(14, solver.maxSatisfaction(satisfaction));
        Assert.assertEquals(solver.maxSatisfactionGreedy(satisfaction), solver.maxSatisfactionDp(satisfaction));
    }

    @Test
    public void cuttingPizzaShouldMatchTopDownAndBottomUp() {
        NumberOfWaysOfCuttingAPizza solver = new NumberOfWaysOfCuttingAPizza();
        String[] pizza = {"A..", "AAA", "..."};

        Assert.assertEquals(3, solver.ways(pizza, 3));
        Assert.assertEquals(solver.waysTopDown(pizza, 3), solver.waysBottomUp(pizza, 3));
    }

    @Test
    public void minimumCostToReachDestinationInTimeShouldMatchDpAndStateDijkstra() {
        MinimumCostToReachDestinationInTime solver = new MinimumCostToReachDestinationInTime();
        int[][] edges = {{0, 1, 10}, {1, 2, 10}, {2, 5, 10}, {0, 3, 1}, {3, 4, 10}, {4, 5, 15}};
        int[] fees = {5, 1, 2, 20, 20, 3};

        Assert.assertEquals(11, solver.minCost(30, edges, fees));
        Assert.assertEquals(solver.minCostDp(30, edges, fees), solver.minCostStateDijkstra(30, edges, fees));
    }

    @Test
    public void fairDistributionOfCookiesShouldMatchBacktrackingAndBitmaskDp() {
        FairDistributionOfCookies solver = new FairDistributionOfCookies();
        int[] cookies = {8, 15, 10, 20, 8};

        Assert.assertEquals(31, solver.distributeCookies(cookies, 2));
        Assert.assertEquals(solver.distributeCookiesBacktracking(cookies, 2), solver.distributeCookiesBitmaskDp(cookies, 2));
    }
}
