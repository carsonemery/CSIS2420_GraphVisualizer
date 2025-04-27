package graphVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	private void dfs(int v) {
		onStack[v] = true;
		marked[v] = true;

		for (int w : adj(v)) {
			// If we've already completely explored this vertex, skip it
			if (marked[w] && !onStack[w]) {
				continue;
			}

			// Found new vertex so we recurse
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(w);
			}
			// Found vertex on recursion stack - cycle detected
			else if (onStack[w]) {
				// Create a new cycle
				List<Vertex> cycle = new ArrayList<>();

				// Add vertices in the cycle (from v back to w)
				for (int x = v; x != w; x = edgeTo[x]) {
					cycle.add(indexToVertex.get(x));
				}

				// Add w to close the cycle
				cycle.add(indexToVertex.get(w));

				// A true cycle needs at least 3 vertices
				if (cycle.size() < 3) {
					continue;
				}

				// Validate the cycle - make sure all edges exist
				boolean validCycle = true;
				for (int i = 0; i < cycle.size() - 1; i++) {
					Vertex from = cycle.get(i);
					Vertex to = cycle.get(i + 1);
					if (graph.findEdge(from, to) == null) {
						validCycle = false;
						break;
					}
				}

				// Check the closing edge from last vertex back to first
				Vertex last = cycle.get(cycle.size() - 1);
				Vertex first = cycle.get(0);
				if (graph.findEdge(last, first) == null) {
					validCycle = false;
				}

				// Only add valid cycles and avoid duplicates
				if (validCycle && !containsSameCycle(cycles, cycle)) {
					System.out.println("Found valid cycle:");
					for (Vertex vtx : cycle) {
						System.out.println(" - " + vtx.getLabel());
					}
					cycles.add(cycle);
				}
			}
		}

		onStack[v] = false;
	}

	// Helper method to check if we already have this cycle
	private boolean containsSameCycle(List<List<Vertex>> cycles, List<Vertex> newCycle) {
		// Sort vertex labels for comparison
		Set<String> newCycleLabels = new HashSet<>();
		for (Vertex v : newCycle) {
			newCycleLabels.add(v.getLabel());
		}

		for (List<Vertex> existingCycle : cycles) {
			// Quick size check first
			if (existingCycle.size() != newCycle.size()) {
				continue;
			}

			// Check if they contain the same vertices
			Set<String> existingLabels = new HashSet<>();
			for (Vertex v : existingCycle) {
				existingLabels.add(v.getLabel());
			}

			if (existingLabels.equals(newCycleLabels)) {
				return true; // Found a duplicate
			}
		}
		return false;
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
