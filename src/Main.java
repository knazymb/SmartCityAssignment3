public class Main {
    public static void main(String[] args) {
        System.out.println("=== Smart City ===");

        Graph graph = new Graph(4);

        graph.addUndirectedEdge(0, 1, 4);
        graph.addUndirectedEdge(0, 2, 3);
        graph.addUndirectedEdge(1, 2, 2);
        graph.addUndirectedEdge(1, 3, 5);
        graph.addUndirectedEdge(2, 3, 7);

        System.out.println("\n=== MST ===");
        System.out.println("Kruskal: " + MST.kruskal(graph));
        System.out.println("Prim: " + MST.prim(graph));

        System.out.println("\n=== Dijkstra ===");
        Dijkstra.shortestPaths(graph, 0);

        System.out.println("\n=== Topological Sort ===");
        Graph directed = new Graph(4);
        directed.addDirectedEdge(0, 1, 1);
        directed.addDirectedEdge(0, 2, 1);
        directed.addDirectedEdge(1, 3, 1);
        directed.addDirectedEdge(2, 3, 1);

        System.out.println(
                Connectivity.topologicalSort(directed));

        System.out.println("\n=== Strongly Connected Components ===");
        Graph sccGraph = new Graph(4);
        sccGraph.addDirectedEdge(0, 1, 1);
        sccGraph.addDirectedEdge(1, 0, 1);
        sccGraph.addDirectedEdge(1, 2, 1);
        sccGraph.addDirectedEdge(2, 3, 1);
        sccGraph.addDirectedEdge(3, 2, 1);

        System.out.println(Connectivity.kosaraju(sccGraph));
    }
}