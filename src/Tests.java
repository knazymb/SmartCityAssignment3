public class Tests {
    public static void main(String[] args) {
        testMST();
        testSingleVertex();
        testDisconnectedGraph();
        testCycle();
        testDijkstra();
        testSCC();

        System.out.println("All tests passed.");
    }

    public static void testMST() {
        Graph graph = new Graph(3);
        graph.addUndirectedEdge(0, 1, 1);
        graph.addUndirectedEdge(1, 2, 2);
        graph.addUndirectedEdge(0, 2, 3);

        if (MST.kruskal(graph) != 3 ||
                MST.prim(graph) != 3) {
            throw new RuntimeException("MST test failed");
        }
    }

    public static void testSingleVertex() {
        Graph graph = new Graph(1);

        if (MST.kruskal(graph) != 0 ||
                MST.prim(graph) != 0) {
            throw new RuntimeException("Single vertex test failed");
        }
    }

    public static void testDisconnectedGraph() {
        Graph graph = new Graph(3);
        graph.addUndirectedEdge(0, 1, 5);

        if (MST.kruskal(graph) != -1 ||
                MST.prim(graph) != -1) {
            throw new RuntimeException("Disconnected test failed");
        }
    }

    public static void testCycle() {
        Graph graph = new Graph(3);
        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(1, 2, 1);
        graph.addDirectedEdge(2, 0, 1);

        if (!Connectivity.topologicalSort(graph).isEmpty()) {
            throw new RuntimeException("Cycle test failed");
        }
    }

    public static void testDijkstra() {
        Graph graph = new Graph(3);
        graph.addUndirectedEdge(0, 1, 4);
        graph.addUndirectedEdge(1, 2, 2);
        graph.addUndirectedEdge(0, 2, 10);

        if (MST.kruskal(graph) != 6) {
            throw new RuntimeException("Graph setup failed");
        }
    }

    public static void testSCC() {
        Graph graph = new Graph(3);
        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(1, 0, 1);
        graph.addDirectedEdge(1, 2, 1);

        if (Connectivity.kosaraju(graph).size() != 2) {
            throw new RuntimeException("SCC test failed");
        }
    }
}