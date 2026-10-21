import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class Experiments {

    static final int REPETITIONS = 5;
    static final int SEED = 42;

    public static void main(String[] args) throws Exception {
        int[][] sizes = {
                {10, 30},
                {100, 500},
                {1000, 5000}
        };

        File folder = new File("results/tables");

        if (!folder.exists() && !folder.mkdirs()) {
            throw new IllegalStateException(
                    "Could not create results/tables"
            );
        }

        File csvFile = new File(folder, "results.csv");

        try (PrintWriter writer = new PrintWriter(csvFile)) {
            writer.println(
                    "Algorithm,V,E,AverageTimeNs,Operations"
            );

            for (int[] size : sizes) {
                int vertices = size[0];
                int edges = size[1];

                Graph undirected = generateUndirectedGraph(
                        vertices, edges, SEED
                );

                Graph dag = generateDAG(
                        vertices, edges, SEED
                );

                Graph cyclicDirected = generateCyclicGraph(
                        vertices, edges, SEED
                );

                runExperiment(writer, "Kruskal", undirected);
                runExperiment(writer, "Prim", undirected);
                runExperiment(writer, "Dijkstra", undirected);
                runExperiment(writer, "SCC", cyclicDirected);
                runExperiment(writer, "TopologicalSort", dag);

                System.out.println(
                        "Finished V=" + vertices
                                + ", E=" + edges
                );
            }
        }

        System.out.println(
                "Results saved to " + csvFile.getPath()
        );
    }

    static Graph generateUndirectedGraph(
            int vertices, int edges, int seed
    ) {
        if (vertices < 2
                || edges < vertices - 1
                || edges > vertices * (vertices - 1) / 2) {
            throw new IllegalArgumentException(
                    "Invalid undirected graph size"
            );
        }

        Graph graph = new Graph(vertices);
        Random random = new Random(seed + vertices);

        for (int i = 1; i < vertices; i++) {
            graph.addUndirectedEdge(
                    i - 1, i, random.nextInt(20) + 1
            );
        }

        while (graph.edges.size() < edges) {
            int from = random.nextInt(vertices);
            int to = random.nextInt(vertices);

            if (from == to || hasEdge(graph, from, to)) {
                continue;
            }

            graph.addUndirectedEdge(
                    from, to, random.nextInt(20) + 1
            );
        }

        return graph;
    }

    static Graph generateDAG(
            int vertices, int edges, int seed
    ) {
        if (vertices < 2
                || edges < 0
                || edges > vertices * (vertices - 1) / 2) {
            throw new IllegalArgumentException(
                    "Invalid DAG size"
            );
        }

        Graph graph = new Graph(vertices);
        Random random = new Random(seed + vertices + 10000);

        while (graph.edges.size() < edges) {
            int from = random.nextInt(vertices);
            int to = random.nextInt(vertices);

            if (from >= to || hasEdge(graph, from, to)) {
                continue;
            }

            graph.addDirectedEdge(
                    from, to, random.nextInt(20) + 1
            );
        }

        return graph;
    }

    static Graph generateCyclicGraph(
            int vertices, int edges, int seed
    ) {
        if (vertices < 2
                || edges < vertices
                || edges > vertices * (vertices - 1)) {
            throw new IllegalArgumentException(
                    "Invalid cyclic graph size"
            );
        }

        Graph graph = new Graph(vertices);
        Random random = new Random(seed + vertices + 20000);

        for (int i = 0; i < vertices; i++) {
            int next = (i + 1) % vertices;

            graph.addDirectedEdge(
                    i, next, random.nextInt(20) + 1
            );
        }

        while (graph.edges.size() < edges) {
            int from = random.nextInt(vertices);
            int to = random.nextInt(vertices);

            if (from == to || hasEdge(graph, from, to)) {
                continue;
            }

            graph.addDirectedEdge(
                    from, to, random.nextInt(20) + 1
            );
        }

        return graph;
    }

    static boolean hasEdge(
            Graph graph, int from, int to
    ) {
        for (Edge edge : graph.adjacencyList.get(from)) {
            if (edge.to == to) {
                return true;
            }
        }

        return false;
    }

    static void runExperiment(
            PrintWriter writer,
            String algorithm,
            Graph graph
    ) {
        runAlgorithm(algorithm, graph);

        long totalTime = 0;
        long totalOperations = 0;

        for (int i = 0; i < REPETITIONS; i++) {
            long start = System.nanoTime();

            long operations = runAlgorithm(algorithm, graph);

            long end = System.nanoTime();

            totalTime += end - start;
            totalOperations += operations;
        }

        double averageTime =
                totalTime / (double) REPETITIONS;

        double averageOperations =
                totalOperations / (double) REPETITIONS;

        writer.println(
                algorithm + ","
                        + graph.vertices + ","
                        + graph.edges.size() + ","
                        + averageTime + ","
                        + averageOperations
        );

        writer.flush();

        System.out.println(
                algorithm
                        + " | V=" + graph.vertices
                        + " | E=" + graph.edges.size()
                        + " | Average time=" + averageTime + " ns"
                        + " | Operations=" + averageOperations
        );
    }

    static long runAlgorithm(
            String algorithm, Graph graph
    ) {
        switch (algorithm) {
            case "Kruskal":
                return MST.kruskalWithStats(graph).operations;

            case "Prim":
                return MST.primWithStats(graph).operations;

            case "Dijkstra":
                return Dijkstra.compute(graph, 0).operations;

            case "SCC":
                return Connectivity.kosarajuWithStats(
                        graph
                ).operations;

            case "TopologicalSort":
                return Connectivity.topologicalSortWithStats(
                        graph
                ).operations;

            default:
                throw new IllegalArgumentException(
                        "Unknown algorithm: " + algorithm
                );
        }
    }
}