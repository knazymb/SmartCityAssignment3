import java.util.*;

public class Connectivity {

    public static List<Integer> topologicalSort(Graph graph) {
        int n = graph.vertices;
        int[] indegree = new int[n];

        for (Edge edge : graph.edges) {
            indegree[edge.to]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            for (Edge edge : graph.adjacencyList.get(current)) {
                indegree[edge.to]--;

                if (indegree[edge.to] == 0) {
                    queue.add(edge.to);
                }
            }
        }

        if (order.size() != n) {
            return new ArrayList<>();
        }

        return order;
    }

    public static List<List<Integer>> kosaraju(Graph graph) {
        int n = graph.vertices;
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfsOrder(graph, i, visited, stack);
            }
        }

        Graph reversed = new Graph(n);

        for (Edge edge : graph.edges) {
            reversed.addDirectedEdge(edge.to, edge.from, edge.weight);
        }

        Arrays.fill(visited, false);
        List<List<Integer>> components = new ArrayList<>();

        while (!stack.isEmpty()) {
            int vertex = stack.pop();

            if (!visited[vertex]) {
                List<Integer> component = new ArrayList<>();
                dfsComponent(reversed, vertex, visited, component);
                components.add(component);
            }
        }

        return components;
    }

    private static void dfsOrder(
            Graph graph, int vertex, boolean[] visited, Stack<Integer> stack) {
        visited[vertex] = true;

        for (Edge edge : graph.adjacencyList.get(vertex)) {
            if (!visited[edge.to]) {
                dfsOrder(graph, edge.to, visited, stack);
            }
        }

        stack.push(vertex);
    }

    private static void dfsComponent(
            Graph graph, int vertex, boolean[] visited, List<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);

        for (Edge edge : graph.adjacencyList.get(vertex)) {
            if (!visited[edge.to]) {
                dfsComponent(graph, edge.to, visited, component);
            }
        }
    }
}