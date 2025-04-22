package graphVisualizer;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Lincoln Bunker & Carson Emery
 */
public class Algs4Adapter {

	private Graph graph;

	// BFS Variables
	private Map<Vertex, Boolean> marked; // tracks visited vertices
	private Map<Vertex, Vertex> edgeTo; // tracks path
	private Map<Vertex, Integer> distTo; // tracks distances

	// DFS Variables

	// Dijkstra Variables

	public Algs4Adapter(Graph graph) {
		this.graph = graph;
	}

	/**
	 * Calls the public BFS Paths algo in BFSAdapter, returns a list of vertices
	 * that map the shortest path from the start to end vertices
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Vertex> runBFS(Vertex start, Vertex end) {
		BFSAdapter bfsAdapter = new BFSAdapter(graph);

		return bfsAdapter.findShortestPath(start, end);

	}

	public List<Vertex> runDFS(Vertex start, Vertex end) {
		return null;
	}

	public List<Vertex> runDijkstra(Vertex start, Vertex end) {
		return null;
	}

}
