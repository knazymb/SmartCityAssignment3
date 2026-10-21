import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Connectivity {

    public static class TopologicalResult {
        public final List<Integer> order;
        public final long operations;

        public TopologicalResult(List<Integer> order, long operations) {
            this.order = order;
            this.operations = operations;
        }
    }

    public static class SCCResult {
        public final List<List<Integer>> components;
        public final long operations;

        public SCCResult(
                List<List<Integer>> components,
                long operations
        ) {
            this.components = components;
            this.operations = operations;
        }
    }

    public static List<Integer> topologicalSort(Graph graph) {
        return topologicalSortWithStats(graph).order;
    }

    public static TopologicalResult topologicalSortWithStats(
            Graph graph
    ) {
        int vertices = graph.vertices;

        int[] indegree = new int[vertices];
        long operations = 0;

        for (int vertex = 0; vertex < vertices; vertex++) {
            for (Edge edge : graph.adjacencyList.get(vertex)) {
                indegree[edge.to]++;
                operations++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int vertex = 0; vertex < vertices; vertex++) {
            operations++;

            if (indegree[vertex] == 0) {
                queue.offer(vertex);
            }
        }

        List<Integer> order = new ArrayList<>();

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            order.add(vertex);

            for (Edge edge : graph.adjacencyList.get(vertex)) {
                operations++;

                indegree[edge.to]--;

                if (indegree[edge.to] == 0) {
                    queue.offer(edge.to);
                }
            }
        }

        if (order.size() != vertices) {
            order.clear();
        }

        return new TopologicalResult(order, operations);
    }

    public static List<List<Integer>> kosaraju(Graph graph) {
        return kosarajuWithStats(graph).components;
    }

    public static SCCResult kosarajuWithStats(Graph graph) {
        int vertices = graph.vertices;

        boolean[] visited = new boolean[vertices];
        List<Integer> finishOrder = new ArrayList<>();
        long[] operations = {0};

        for (int vertex = 0; vertex < vertices; vertex++) {
            operations[0]++;

            if (!visited[vertex]) {
                dfsOrder(
                        graph,
                        vertex,
                        visited,
                        finishOrder,
                        operations
                );
            }
        }

        Graph reversed = new Graph(vertices);

        for (int vertex = 0; vertex < vertices; vertex++) {
            for (Edge edge : graph.adjacencyList.get(vertex)) {
                reversed.addDirectedEdge(
                        edge.to,
                        vertex,
                        edge.weight
                );

                operations[0]++;
            }
        }

        for (int vertex = 0; vertex < vertices; vertex++) {
            visited[vertex] = false;
        }

        List<List<Integer>> components = new ArrayList<>();

        Collections.reverse(finishOrder);

        for (int vertex : finishOrder) {
            operations[0]++;

            if (!visited[vertex]) {
                List<Integer> component = new ArrayList<>();

                dfsComponent(
                        reversed,
                        vertex,
                        visited,
                        component,
                        operations
                );

                components.add(component);
            }
        }

        return new SCCResult(components, operations[0]);
    }

    private static void dfsOrder(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> finishOrder,
            long[] operations
    ) {
        visited[vertex] = true;
        operations[0]++;

        for (Edge edge : graph.adjacencyList.get(vertex)) {
            operations[0]++;

            if (!visited[edge.to]) {
                dfsOrder(
                        graph,
                        edge.to,
                        visited,
                        finishOrder,
                        operations
                );
            }
        }

        finishOrder.add(vertex);
    }

    private static void dfsComponent(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> component,
            long[] operations
    ) {
        visited[vertex] = true;
        component.add(vertex);
        operations[0]++;

        for (Edge edge : graph.adjacencyList.get(vertex)) {
            operations[0]++;

            if (!visited[edge.to]) {
                dfsComponent(
                        graph,
                        edge.to,
                        visited,
                        component,
                        operations
                );
            }
        }
    }
}