package graphVisualizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Adapter class for Dijkstra's algorithm
 * 
 * @author Lincoln Bunker & Carson Emery
 */
public class DijkstraAdapter {

	private Graph graph;
	private HashMap<Integer, Vertex> indexToVertex; // to convert indices to vertex objects
	private HashMap<Vertex, Integer> vertexToIndex; // to convert vertex objects to indices
	private double[] distTo; // distTo[v] = distance of shortest s->v path
	private int[] edgeTo; // edgeTo[v] = last edge on shortest s->v path
	private boolean[] visited; // keeps track of processed vertices

	/**
	 * Constructor initializes the maps for vertex-index conversion
	 * 
	 * @param graph the graph to run Dijkstra's algorithm on
	 */
	public DijkstraAdapter(Graph graph) {
		this.graph = graph;

		vertexToIndex = new HashMap<>();
		indexToVertex = new HashMap<>();

		List<Vertex> vertices = graph.getVertices();

		for (int i = 0; i < vertices.size(); i++) {
			vertexToIndex.put(vertices.get(i), i);
			indexToVertex.put(i, vertices.get(i));
		}
	}

	/**
	 * Finds the shortest path between two vertices considering edge weights
	 * 
	 * @param start start vertex
	 * @param end   end vertex
	 * @return List of vertices representing the shortest path or null if no path
	 *         exists
	 */
	public List<Vertex> findShortestPath(Vertex start, Vertex end) {
		// convert start and end vertex to indices
		int startIndex = vertexToIndex.get(start);
		int endIndex = vertexToIndex.get(end);

		// run Dijkstra's algorithm
		dijkstra(startIndex);

		// check if path exists
		if (distTo[endIndex] == Double.POSITIVE_INFINITY) {
			return null;
		}

		// path exists - construct it
		List<Vertex> path = new ArrayList<>();

		// start from the end index and work backward
		for (int i = endIndex; i != startIndex; i = edgeTo[i]) {
			path.add(indexToVertex.get(i));
		}

		// add the start vertex at the end
		path.add(indexToVertex.get(startIndex));

		// reverse the path
		Collections.reverse(path);

		return path;
	}

	/**
	 * Implementation of Dijkstra's algorithm
	 * 
	 * @param start index of the starting vertex
	 */
	private void dijkstra(int start) {
		int n = vertexToIndex.size();

		// Initialize arrays
		distTo = new double[n];
		edgeTo = new int[n];
		visited = new boolean[n];

		// Set all distances to infinity initially
		Arrays.fill(distTo, Double.POSITIVE_INFINITY);
		distTo[start] = 0.0;
//		for (int v = 0; v < n; v++) {
//			distTo[v] = Double.POSITIVE_INFINITY;
//		}
		System.out.println("Initial distTo: " + Arrays.toString(distTo));

		// Create priority queue with custom comparator to order by distance
		PriorityQueue<Integer> pq = new PriorityQueue<>((v, w) -> Double.compare(distTo[v], distTo[w]));
		pq.add(start);

		while (!pq.isEmpty()) {
			int v = pq.poll();

			// Skip if we've already processed this vertex
			if (visited[v])
				continue;
			visited[v] = true;

			// Process each adjacent vertex
			for (int w : adj(v)) {
				// Get the edge between v and w
				Edge e = findEdge(v, w);
				if (e == null)
					continue;

				// Edge relaxation: if we found a shorter path to w through v
				double weight = e.getWeight();
				System.out.println(weight);
				if (distTo[w] > distTo[v] + weight) {
					distTo[w] = distTo[v] + weight;
					edgeTo[w] = v;

					// Add or update in priority queue
					pq.add(w);
				}
			}
		}
	}

	/**
	 * Finds the edge between two vertices given their indices
	 * 
	 * @param v from vertex index
	 * @param w to vertex index
	 * @return the Edge object or null if no edge exists
	 */
	private Edge findEdge(int v, int w) {
		Vertex from = indexToVertex.get(v);
		Vertex to = indexToVertex.get(w);
		return graph.findEdge(from, to);
	}

	/**
	 * Gets all adjacent vertices as indices
	 * 
	 * @param v the vertex index
	 * @return list of adjacent vertex indices
	 */
	private Iterable<Integer> adj(int v) {
		Vertex vertex = indexToVertex.get(v);
		List<Vertex> adj = graph.getAdjacentVertices(vertex);
		List<Integer> vertexIndices = new ArrayList<>();
		for (Vertex vert : adj) {
			vertexIndices.add(vertexToIndex.get(vert));
		}
		return vertexIndices;
	}

	/**
	 * Gets the total distance to a vertex (for display purposes)
	 * 
	 * @param end the destination vertex
	 * @return the total distance to the vertex
	 */
	public double getPathDistance(Vertex end) {
		int endIndex = vertexToIndex.get(end);
		return distTo[endIndex];
	}
}