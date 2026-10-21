import java.util.*;

public class Dijkstra {

    public static void shortestPaths(Graph graph, int source) {
        int n = graph.vertices;
        int[] distance = new int[n];
        int[] previous = new int[n];

        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        for (Edge edge : graph.edges) {
            if (edge.weight < 0) {
                throw new IllegalArgumentException(
                        "Dijkstra does not support negative weights");
            }
        }

        distance[source] = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>(
                Comparator.comparingInt(a -> a[1]));

        queue.add(new int[]{source, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int vertex = current[0];
            int currentDistance = current[1];

            if (currentDistance != distance[vertex]) {
                continue;
            }

            for (Edge edge : graph.adjacencyList.get(vertex)) {
                int newDistance = distance[vertex] + edge.weight;

                if (newDistance < distance[edge.to]) {
                    distance[edge.to] = newDistance;
                    previous[edge.to] = vertex;
                    queue.add(new int[]{edge.to, newDistance});
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (distance[i] == Integer.MAX_VALUE) {
                System.out.println(i + ": unreachable");
            } else {
                System.out.println(i + ": distance = "
                        + distance[i] + ", path = "
                        + getPath(previous, i));
            }
        }
    }

    private static List<Integer> getPath(int[] previous, int vertex) {
        List<Integer> path = new ArrayList<>();

        while (vertex != -1) {
            path.add(vertex);
            vertex = previous[vertex];
        }

        Collections.reverse(path);
        return path;
    }
}