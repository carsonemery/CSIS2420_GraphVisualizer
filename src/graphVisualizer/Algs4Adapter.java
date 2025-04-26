package graphVisualizer;

import java.util.List;
import java.util.Map;

/**
 * adapter class that provides a bridge between the graph structure and various
 * graph algorithms
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
	private List<List<Vertex>> cycles;

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

	/**
	 * calls DFSCycleDetector and then returns the detected cycles from the
	 * DFSCycleDetector class
	 * 
	 * @return
	 */
	public List<List<Vertex>> runDFS() {
		DFSCycleDetector dfsCycleDetector = new DFSCycleDetector(graph);

		cycles = dfsCycleDetector.getCycles();

		return cycles;
	}

	public List<Vertex> runDijkstra(Vertex start, Vertex end) {
		DijkstraAdapter dijkstraAdapter = new DijkstraAdapter(graph);
		return dijkstraAdapter.findShortestPath(start, end);

	}

}
