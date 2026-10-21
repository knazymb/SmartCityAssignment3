import java.util.ArrayList;
import java.util.List;

public class Graph {
    int vertices;
    List<Edge> edges;
    List<List<Edge>> adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        edges = new ArrayList<>();
        adjacencyList = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addUndirectedEdge(int from, int to, int weight) {
        Edge edge = new Edge(from, to, weight);
        edges.add(edge);

        adjacencyList.get(from).add(edge);
        adjacencyList.get(to).add(new Edge(to, from, weight));
    }

    public void addDirectedEdge(int from, int to, int weight) {
        Edge edge = new Edge(from, to, weight);
        edges.add(edge);
        adjacencyList.get(from).add(edge);
    }
}