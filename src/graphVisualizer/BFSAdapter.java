package graphVisualizer;

import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Queue;

public class BFSAdapter {

	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked; // marked[v] = is there an s-v path
	private int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v path
	private int[] distTo; // distTo[v] = number of edges shortest s-v path
	private Graph graph;
	private HashMap<Integer, Vertex> indexToVertex; // to convert indices to vertex objects
	private HashMap<Vertex, Integer> vertexToIndex; // to convert vertex objects to indices

	/**
	 * Adapted constructor that takes a graph object, initializes two maps fills the
	 * maps with the vertices and their index
	 */
	public BFSAdapter(graphVisualizer.Graph graph) {
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
	 * returns the number of vertices in the graph
	 * 
	 * @return
	 */
	private int V() {
		return graph.getVertices().size();
	}

	/**
	 * @param v
	 * @return
	 */
	private Iterable<Integer> adj(int v) {
		// find the vertex object that corresponds to index v
		Vertex vertex = indexToVertex.get(v);

		// get the adjacent vertices to the given vertex
		List<Vertex> adj = new ArrayList<>();
		adj = graph.getAdjacentVertices(vertex);

		List<Integer> vertexIndices = new ArrayList<>();
		// loop through each adjacent vertex and convert it to its index
		for (Vertex vert : adj) {
			vertexIndices.add(vertexToIndex.get(vert));
		}

		return vertexIndices;

	}

	public List<Vertex> findShortestPath(Vertex start, Vertex end) {
		// convert start and end vertex to indices
		int startIndex = vertexToIndex.get(start);
		int endIndex = vertexToIndex.get(end);

		// run BFS algo
		bfs(startIndex, endIndex);

		// check if path exists
		if (!marked[endIndex]) {
			return null;
		}

		// path exists
		List<Vertex> path = new ArrayList<>();

		// start from the end index and work backward
		for (int i = endIndex; i != startIndex; i = edgeTo[i]) {
			path.add(indexToVertex.get(i));
		}

		return null;
	}

	// breadth-first search from a single vertex to
	private void bfs(int startIndex, int endIndex) {
		// initialization for BFS
		int n = vertexToIndex.size(); // Total number of vertices

		// initialize a queue and add the startIndex
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(startIndex);

		// create arrays sized for all vertices
		marked = new boolean[n];
		edgeTo = new int[n];
		distTo = new int[n];

		// set initial values for all vertices
		for (int i = 0; i < n; i++) {
			marked[i] = false;
			distTo[i] = INFINITY;
		}

		// set start vertex values
		marked[startIndex] = true;
		distTo[startIndex] = 0;

		while (!q.isEmpty() && marked[endIndex] != true) {
			int v = q.dequeue();

			for (int w : adj(v)) {
				// if this adjacent vertex hasn't been visited yet
				if (!marked[w]) {
					// mark it as visited
					marked[w] = true;

					// record how we got to that edge
					edgeTo[w] = v;

					// calculate distance
					distTo[w] = distTo[v] + 1;

					// add to queue for further exploration
					q.enqueue(w);

				}
			}

		}

	}

	/**
	 * Is there a path between the source vertex {@code s} (or sources) and vertex
	 * {@code v}?
	 * 
	 * @param v the vertex
	 * @return {@code true} if there is a path, and {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}

	/**
	 * Returns the number of edges in a shortest path between the source vertex
	 * {@code s} (or sources) and vertex {@code v}?
	 * 
	 * @param v the vertex
	 * @return the number of edges in such a shortest path (or
	 *         {@code Integer.MAX_VALUE} if there is no such path)
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public int distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}

	/**
	 * Returns a shortest path between the source vertex {@code s} (or sources) and
	 * {@code v}, or {@code null} if no such path.
	 * 
	 * @param v the vertex
	 * @return the sequence of vertices on a shortest path, as an Iterable
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public Iterable<Integer> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v))
			return null;
		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x])
			path.push(x);
		path.push(x);
		return path;
	}

	// check optimality conditions for single source
	private boolean check(Graph G, int s) {

		// check that the distance of s = 0
		if (distTo[s] != 0) {
			System.out.println("distance of source " + s + " to itself = " + distTo[s]);
			return false;
		}

		// check that for each edge v-w dist[w] <= dist[v] + 1
		// provided v is reachable from s
		for (int v = 0; v < G.V(); v++) {
			for (int w : G.adj(v)) {
				if (hasPathTo(v) != hasPathTo(w)) {
					System.out.println("edge " + v + "-" + w);
					System.out.println("hasPathTo(" + v + ") = " + hasPathTo(v));
					System.out.println("hasPathTo(" + w + ") = " + hasPathTo(w));
					return false;
				}
				if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
					System.out.println("edge " + v + "-" + w);
					System.out.println("distTo[" + v + "] = " + distTo[v]);
					System.out.println("distTo[" + w + "] = " + distTo[w]);
					return false;
				}
			}
		}

		// check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
		// provided v is reachable from s
		for (int w = 0; w < G.V(); w++) {
			if (!hasPathTo(w) || w == s)
				continue;
			int v = edgeTo[w];
			if (distTo[w] != distTo[v] + 1) {
				System.out.println("shortest path edge " + v + "-" + w);
				System.out.println("distTo[" + v + "] = " + distTo[v]);
				System.out.println("distTo[" + w + "] = " + distTo[w]);
				return false;
			}
		}

		return true;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	// throw an IllegalArgumentException if vertices is null, has zero vertices,
	// or has a vertex not between 0 and V-1
	private void validateVertices(Iterable<Integer> vertices) {
		if (vertices == null) {
			throw new IllegalArgumentException("argument is null");
		}
		int vertexCount = 0;
		for (Integer v : vertices) {
			vertexCount++;
			if (v == null) {
				throw new IllegalArgumentException("vertex is null");
			}
			validateVertex(v);
		}
		if (vertexCount == 0) {
			throw new IllegalArgumentException("zero vertices");
		}
	}

}
