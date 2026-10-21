import java.util.*;

public class MST {

    public static int kruskal(Graph graph) {
        List<Edge> sortedEdges = new ArrayList<>(graph.edges);
        sortedEdges.sort(Comparator.comparingInt(e -> e.weight));

        DSU dsu = new DSU(graph.vertices);
        int totalWeight = 0;
        int edgesUsed = 0;

        for (Edge edge : sortedEdges) {
            if (dsu.union(edge.from, edge.to)) {
                totalWeight += edge.weight;
                edgesUsed++;

                if (edgesUsed == graph.vertices - 1) {
                    break;
                }
            }
        }

        if (edgesUsed != graph.vertices - 1) {
            return -1;
        }

        return totalWeight;
    }

    public static int prim(Graph graph) {
        boolean[] visited = new boolean[graph.vertices];
        PriorityQueue<Edge> queue =
                new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        queue.add(new Edge(0, 0, 0));

        int totalWeight = 0;
        int visitedCount = 0;

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int current = edge.to;

            if (visited[current]) {
                continue;
            }

            visited[current] = true;
            visitedCount++;
            totalWeight += edge.weight;

            for (Edge next : graph.adjacencyList.get(current)) {
                if (!visited[next.to]) {
                    queue.add(next);
                }
            }
        }

        if (visitedCount != graph.vertices) {
            return -1;
        }

        return totalWeight;
    }
}