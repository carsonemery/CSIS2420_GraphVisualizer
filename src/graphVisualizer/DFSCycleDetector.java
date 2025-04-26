package graphVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DFSCycleDetector {
	private boolean[] marked;
	private boolean[] onStack;
	private List<List<Vertex>> cycles;
	private HashMap<Integer, Vertex> indexToVertex;
	private HashMap<Vertex, Integer> vertexToIndex;
	private Graph graph;
	private int[] edgeTo;

	/**
	 * Allows us to highlight different cycles in a graph
	 * 
	 * @param graph
	 */
	public DFSCycleDetector(Graph graph) {
		this.graph = graph;
		cycles = new ArrayList<>();

		// set up conversion maps like in BFS adapter
		vertexToIndex = new HashMap<>();
		indexToVertex = new HashMap<>();

		List<Vertex> vertices = graph.getVertices();
		for (int i = 0; i < vertices.size(); i++) {
			vertexToIndex.put(vertices.get(i), i);
			indexToVertex.put(i, vertices.get(i));
		}

		// initialize arrays
		int n = vertices.size();
		marked = new boolean[n];
		onStack = new boolean[n];
		edgeTo = new int[n];

		// run DFS from each unmarked vertex
		for (int v = 0; v < n; v++) {
			if (!marked[v]) {
				dfs(v);
			}

		}
	}

	//
	private void dfs(int v) {
		onStack[v] = true;
		marked[v] = true;

		for (int w : adj(v)) {
			// found new vertex so we recurse
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(w);
			}
			// found vertex on recursion stack cycle detected
			else if (onStack[w]) {
				List<Vertex> cycle = new ArrayList<>();
				for (int x = v; x != w; x = edgeTo[x]) {
					cycle.add(indexToVertex.get(x));
				}
				cycle.add(indexToVertex.get(w));
				cycle.add(indexToVertex.get(v));
				cycles.add(cycle);

			}

		}

		onStack[v] = false;

	}

	private Iterable<Integer> adj(int v) {
		Vertex vertex = indexToVertex.get(v);
		List<Vertex> adj = graph.getAdjacentVertices(vertex);
		List<Integer> vertexIndices = new ArrayList<>();

		for (Vertex vert : adj) {
			vertexIndices.add(vertexToIndex.get(vert));

		}

		return vertexIndices;

	}

	public List<List<Vertex>> getCycles() {
		return cycles;
	}

	public boolean hasCycle() {
		return !cycles.isEmpty();
	}

}
