import java.util.Arrays;
import java.util.List;

public class AlgorithmTests {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        testKruskal();
        testPrim();
        testDisconnectedMST();
        testDijkstra();
        testTopologicalSort();
        testTopologicalSortWithCycle();
        testSCC();

        System.out.println();
        System.out.println("Tests passed: " + passed);
        System.out.println("Tests failed: " + failed);

        if (failed > 0) {
            throw new RuntimeException("Some tests failed");
        }
    }

    static void check(String name, boolean condition) {
        if (condition) {
            System.out.println("PASS: " + name);
            passed++;
        } else {
            System.out.println("FAIL: " + name);
            failed++;
        }
    }

    static void testKruskal() {
        Graph graph = createMSTGraph();
        int result = MST.kruskal(graph);

        check("Kruskal MST weight", result == 6);
    }

    static void testPrim() {
        Graph graph = createMSTGraph();
        int result = MST.prim(graph);

        check("Prim MST weight", result == 6);
    }

    static void testDisconnectedMST() {
        Graph graph = new Graph(3);
        graph.addUndirectedEdge(0, 1, 5);

        check(
                "Kruskal disconnected graph",
                MST.kruskal(graph) == -1
        );

        check(
                "Prim disconnected graph",
                MST.prim(graph) == -1
        );
    }

    static Graph createMSTGraph() {
        Graph graph = new Graph(4);

        graph.addUndirectedEdge(0, 1, 1);
        graph.addUndirectedEdge(1, 2, 2);
        graph.addUndirectedEdge(2, 3, 3);
        graph.addUndirectedEdge(0, 3, 10);
        graph.addUndirectedEdge(0, 2, 5);

        return graph;
    }

    static void testDijkstra() {
        Graph graph = new Graph(4);

        graph.addDirectedEdge(0, 1, 4);
        graph.addDirectedEdge(0, 2, 1);
        graph.addDirectedEdge(2, 1, 2);
        graph.addDirectedEdge(1, 3, 1);
        graph.addDirectedEdge(2, 3, 5);

        Dijkstra.Result result = Dijkstra.compute(graph, 0);

        int[] expected = {0, 3, 1, 4};

        check(
                "Dijkstra distances",
                Arrays.equals(result.distances, expected)
        );
    }

    static void testTopologicalSort() {
        Graph graph = new Graph(4);

        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(0, 2, 1);
        graph.addDirectedEdge(1, 3, 1);
        graph.addDirectedEdge(2, 3, 1);

        List<Integer> order = Connectivity.topologicalSort(graph);

        check(
                "Topological sort contains all vertices",
                order.size() == 4
        );

        check(
                "Topological sort respects edge directions",
                isValidTopologicalOrder(graph, order)
        );
    }

    static boolean isValidTopologicalOrder(
            Graph graph,
            List<Integer> order
    ) {
        if (order.size() != graph.vertices) {
            return false;
        }

        int[] position = new int[graph.vertices];

        for (int i = 0; i < order.size(); i++) {
            int vertex = order.get(i);

            if (vertex < 0 || vertex >= graph.vertices) {
                return false;
            }

            position[vertex] = i;
        }

        for (int from = 0; from < graph.vertices; from++) {
            for (Edge edge : graph.adjacencyList.get(from)) {
                if (position[from] >= position[edge.to]) {
                    return false;
                }
            }
        }

        return true;
    }

    static void testTopologicalSortWithCycle() {
        Graph graph = new Graph(3);

        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(1, 2, 1);
        graph.addDirectedEdge(2, 0, 1);

        List<Integer> order = Connectivity.topologicalSort(graph);

        check(
                "Topological sort detects cycle",
                order.isEmpty()
        );
    }

    static void testSCC() {
        Graph graph = new Graph(4);

        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(1, 0, 1);
        graph.addDirectedEdge(1, 2, 1);
        graph.addDirectedEdge(2, 3, 1);
        graph.addDirectedEdge(3, 2, 1);

        List<List<Integer>> components =
                Connectivity.kosaraju(graph);

        check(
                "SCC finds two components",
                components.size() == 2
        );

        check(
                "SCC contains all vertices",
                components.stream()
                        .mapToInt(List::size)
                        .sum() == 4
        );
    }
}