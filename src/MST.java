import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MST {

    public static class Result {
        public final int totalWeight;
        public final long operations;

        public Result(int totalWeight, long operations) {
            this.totalWeight = totalWeight;
            this.operations = operations;
        }
    }

    public static int kruskal(Graph graph) {
        return kruskalWithStats(graph).totalWeight;
    }

    public static Result kruskalWithStats(Graph graph) {
        List<Edge> sortedEdges = new ArrayList<>(graph.edges);

        long[] operations = {0};

        sortedEdges.sort((a, b) -> {
            operations[0]++;
            return Integer.compare(a.weight, b.weight);
        });

        DSU dsu = new DSU(graph.vertices);

        int totalWeight = 0;
        int edgesUsed = 0;

        for (Edge edge : sortedEdges) {
            operations[0]++;

            if (dsu.union(edge.from, edge.to)) {
                totalWeight += edge.weight;
                edgesUsed++;

                if (edgesUsed == graph.vertices - 1) {
                    break;
                }
            }
        }

        if (graph.vertices > 0 && edgesUsed != graph.vertices - 1) {
            return new Result(-1, operations[0]);
        }

        return new Result(totalWeight, operations[0]);
    }

    public static int prim(Graph graph) {
        return primWithStats(graph).totalWeight;
    }

    public static Result primWithStats(Graph graph) {
        if (graph.vertices == 0) {
            return new Result(0, 0);
        }

        boolean[] visited = new boolean[graph.vertices];

        PriorityQueue<Edge> queue = new PriorityQueue<>(
                Comparator.comparingInt(edge -> edge.weight)
        );

        queue.offer(new Edge(-1, 0, 0));

        int totalWeight = 0;
        int verticesVisited = 0;
        long operations = 0;

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            operations++;

            int vertex = edge.to;

            if (visited[vertex]) {
                continue;
            }

            visited[vertex] = true;
            verticesVisited++;
            totalWeight += edge.weight;

            for (Edge next : graph.adjacencyList.get(vertex)) {
                operations++;

                if (!visited[next.to]) {
                    queue.offer(next);
                }
            }
        }

        if (verticesVisited != graph.vertices) {
            return new Result(-1, operations);
        }

        return new Result(totalWeight, operations);
    }
}