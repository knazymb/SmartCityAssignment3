import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    public static class Result {
        public final int[] distances;
        public final int[] previous;
        public final long operations;

        public Result(int[] distances, int[] previous, long operations) {
            this.distances = distances;
            this.previous = previous;
            this.operations = operations;
        }
    }

    public static Result compute(Graph graph, int source) {
        if (source < 0 || source >= graph.vertices) {
            throw new IllegalArgumentException("Invalid source vertex");
        }

        int[] distances = new int[graph.vertices];
        int[] previous = new int[graph.vertices];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        distances[source] = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1])
        );

        queue.offer(new int[]{source, 0});

        long operations = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            int vertex = current[0];
            int distance = current[1];

            operations++;

            if (distance != distances[vertex]) {
                continue;
            }

            for (Edge edge : graph.adjacencyList.get(vertex)) {
                operations++;

                if (edge.weight < 0) {
                    throw new IllegalArgumentException(
                            "Dijkstra does not support negative weights"
                    );
                }

                if (distances[vertex] != Integer.MAX_VALUE
                        && distances[vertex] + edge.weight < distances[edge.to]) {

                    distances[edge.to] = distances[vertex] + edge.weight;
                    previous[edge.to] = vertex;

                    queue.offer(new int[]{
                            edge.to,
                            distances[edge.to]
                    });
                }
            }
        }

        return new Result(distances, previous, operations);
    }

    public static List<Integer> getPath(
            int source,
            int target,
            int[] previous
    ) {
        List<Integer> path = new ArrayList<>();

        if (target < 0 || target >= previous.length) {
            throw new IllegalArgumentException("Invalid target vertex");
        }

        int current = target;

        while (current != -1) {
            path.add(current);

            if (current == source) {
                break;
            }

            current = previous[current];
        }

        if (path.get(path.size() - 1) != source) {
            return Collections.emptyList();
        }

        Collections.reverse(path);
        return path;
    }

    public static void shortestPaths(Graph graph, int source) {
        Result result = compute(graph, source);

        for (int vertex = 0; vertex < graph.vertices; vertex++) {
            if (result.distances[vertex] == Integer.MAX_VALUE) {
                System.out.println(
                        "Vertex " + vertex + ": unreachable"
                );
            } else {
                List<Integer> path = getPath(
                        source,
                        vertex,
                        result.previous
                );

                System.out.println(
                        "Vertex " + vertex
                                + ", distance = " + result.distances[vertex]
                                + ", path = " + path
                );
            }
        }
    }
}