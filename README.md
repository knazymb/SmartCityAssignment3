Smart City Infrastructure and Route Planning

Project Description

This project implements graph algorithms for smart city infrastructure and route planning.

The project includes minimum spanning tree algorithms, shortest path search, topological sorting, and strongly connected components.

Implemented Algorithms

1. Kruskal's Algorithm
Kruskal's algorithm finds a minimum spanning tree by sorting edges by weight and selecting edges that do not create cycles. It uses a Disjoint Set Union (DSU) structure.

Time complexity: O(E log E).

2. Prim's Algorithm
Prim's algorithm builds a minimum spanning tree by starting from a vertex and repeatedly selecting the smallest available edge that connects a visited vertex to an unvisited vertex.

Time complexity: O(E log E) for the priority-queue implementation used in this project.

3. Dijkstra's Algorithm
Dijkstra's algorithm finds the shortest paths from a source vertex to all reachable vertices in a graph with non-negative edge weights.

Time complexity: O((V + E) log E) for the priority-queue implementation used in this project.

4. Topological Sorting
Topological sorting arranges the vertices of a directed acyclic graph so that every directed edge goes from an earlier vertex to a later vertex. The implementation uses Kahn's algorithm.

Time complexity: O(V + E).

5. Strongly Connected Components
Kosaraju's algorithm finds strongly connected components using depth-first search and a reversed graph.

Time complexity: O(V + E).



Experimental Analysis

The algorithms were tested on graphs of different sizes.

| Vertices (V) | Edges (E) |
|---:|---:|
| 10 | 30 |
| 100 | 500 |
| 1000 | 5000 |

Each algorithm was measured five times for each graph size. The average execution time was recorded using Java's System.nanoTime().

The graph generator uses a fixed random seed to make the generated graphs reproducible.

Experimental results are saved in:

results/tables/results.csv

The CSV file contains the algorithm name, number of vertices, number of edges, average execution time in nanoseconds, and counted operations.

The operation counters record selected operations in each implementation. Their definitions differ between algorithms, so they should not be interpreted as identical units of computational work.

Experimental Results

The results show that execution time generally increases as graph size increases.

The measured performance also depends on the graph structure, Java Virtual Machine, hardware, and implementation details.

The Excel charts in results/algorithm_charts.xlsx show:
- Average execution time for each algorithm.
- Counted operations for each algorithm.

Testing

AlgorithmTests.java contains tests for:
- Kruskal's and Prim's minimum spanning tree weights.
- Disconnected graphs.
- Dijkstra's shortest path distances.
- Topological sorting.
- Cycle detection in topological sorting.
- Strongly connected components.

Run AlgorithmTests.main() to execute the tests.

Project Structure

- Edge.java - graph edge representation.
- Graph.java - graph representation and edge insertion.
- DSU.java - Disjoint Set Union structure.
- MST.java - Kruskal's and Prim's algorithms.
- Dijkstra.java - shortest path algorithm.
- Connectivity.java - topological sorting and Kosaraju's algorithm.
- Experiments.java - performance experiments and CSV generation.
- AlgorithmTests.java - correctness tests.
- results/tables/results.csv - experimental data.
- results/algorithm_charts.xlsx - Excel charts.

How to Run

1. Open the project in IntelliJ IDEA.
2. Use a compatible JDK.
3. Run Main.java to see the algorithm demonstration.
4. Run AlgorithmTests.java to test algorithm correctness.
5. Run Experiments.java to generate experimental results.

Limitations

The experimental directed graph generator creates directed acyclic graphs. SCC correctness is also tested separately on a graph containing cycles.

The measured times are experimental observations and may vary between runs and machines.