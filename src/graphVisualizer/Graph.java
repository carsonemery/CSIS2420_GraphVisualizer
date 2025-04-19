package graphVisualizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Graph and its fields, constructors, and methods Will manage collections
 * of vertices and edges and implement graph operations
 *
 * @author Lincoln Bunker and Carson Emery
 */
public class Graph {

	private List<Vertex> vertices;
	private List<Edge> edges;
	private boolean isDirected;

	/**
	 * constructor for class Graph
	 * 
	 * @param isDirected whether the graph is directed
	 */
	public Graph(boolean isDirected) {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
		this.isDirected = isDirected;
	}

	/**
	 * default constructor to create an undirected graph
	 */
	public Graph() {
		this(false);
	}

	/**
	 * getter for isDirected property
	 * 
	 * @return whether the graph is directed
	 */
	public boolean isDirected() {
		return isDirected;
	}

	/**
	 * setter for isDirected property
	 * 
	 * @param directed the new directed value
	 */
	public void setDirected(boolean directed) {
		this.isDirected = directed;
	}

	/**
	 * adds a vertex to the graph
	 * 
	 * @param vertex the vertex to add
	 */
	public void addVertex(Vertex vertex) {
		vertices.add(vertex);
	}

	/**
	 * removes a vertex and all its connected edges from the graph
	 * 
	 * @param vertex the vertex to remove
	 */
	public void removeVertex(Vertex vertex) {
		// remove all edges connected to this vertex
		List<Edge> edgesToRemove = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getFrom() == vertex || edge.getTo() == vertex) {
				edgesToRemove.add(edge);
			}
		}

		edges.removeAll(edgesToRemove);

		// remove the vertex itself
		vertices.remove(vertex);
	}

	/**
	 * removes an edge from the graph
	 * 
	 * @param edge the edge to remove
	 */
	public void removeEdge(Edge edge) {
		edges.remove(edge);

		// for an undirected graph, remove the reverse edge as well
		if (!isDirected) {
			for (Edge e : new ArrayList<>(edges)) {
				if (e.getFrom() == edge.getTo() && e.getTo() == edge.getFrom()) {
					edges.remove(e);
					break;
				}
			}
		}
	}

	/**
	 * creates and adds an edge between two vertices
	 * 
	 * @param from   source vertex
	 * @param to     target vertex
	 * @param weight weight of the edge
	 */
	public void addEdge(Vertex from, Vertex to, double weight) {
		Edge edge = new Edge(from, to, weight);
		edges.add(edge);

		// for undirected graphs, add the reverse edge as well
		if (!isDirected) {
			Edge reverseEdge = new Edge(to, from, weight);
			edges.add(reverseEdge);

			// link the edges as reverse pairs
			 edge.setReverseOf(reverseEdge);
		}
	}

	/**
	 * adds a pre-constructed edge to the graph
	 * 
	 * @param edge the edge to add
	 */
	public void addEdge(Edge edge) {
		edges.add(edge);

		// for undirected graphs, add the reverse edge as well
		if (!isDirected) {
			Edge reverseEdge = new Edge(edge.getTo(), edge.getFrom(), edge.getWeight());
			edges.add(reverseEdge);

			// link the edges as reverse pairs
			edge.setReverseOf(reverseEdge);
		}
	}

	/**
	 * returns a vertex at the given coordinates, if any
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return vertex at the given location, or null if none
	 */
	public Vertex getVertexAt(int x, int y) {
		// find a vertex at the given coordinates (within some tolerance)
		for (Vertex v : vertices) {
			double distance = Math.sqrt(Math.pow(v.getX() - x, 2) + Math.pow(v.getY() - y, 2));
			if (distance <= v.getRadius()) {
				return v;
			}
		}
		return null;
	}

	/**
	 * gets all vertices in the graph
	 * 
	 * @return list of vertices
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * gets all edges in the graph
	 * 
	 * @return list of edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}

	/**
	 * finds an edge between two vertices, if it exists
	 * 
	 * @param from source vertex
	 * @param to   target vertex
	 * @return the edge or null if not found
	 */
	public Edge findEdge(Vertex from, Vertex to) {
		for (Edge edge : edges) {
			if (edge.getFrom() == from && edge.getTo() == to) {
				return edge;
			}
		}
		return null;
	}

	/**
	 * gets all edges connected to a vertex
	 * 
	 * @param vertex the vertex
	 * @return list of connected edges
	 */
	public List<Edge> getConnectedEdges(Vertex vertex) {
		List<Edge> connectedEdges = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getFrom() == vertex || edge.getTo() == vertex) {
				connectedEdges.add(edge);
			}
		}
		return connectedEdges;
	}

	/**
	 * gets adjacent vertices (neighbors) of a vertex
	 * 
	 * @param vertex the vertex
	 * @return list of adjacent vertices
	 */
	public List<Vertex> getAdjacentVertices(Vertex vertex) {
		List<Vertex> adjacentVertices = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getFrom() == vertex) {
				adjacentVertices.add(edge.getTo());
			} else if (!isDirected && edge.getTo() == vertex) {
				adjacentVertices.add(edge.getFrom());
			}
		}
		return adjacentVertices;
	}

	/**
	 * method to call algorithm operations through an adapter
	 * 
	 * @param algorithmName name of the algorithm to run
	 * @param start         starting vertex
	 * @param end           ending vertex (if applicable)
	 */
	 public void runAlgorithm(String algorithmName, Vertex start, Vertex end) {
	 	Algs4Adapter adapter = new Algs4Adapter(this);
	 	switch (algorithmName) {
	 	case "BFS":
	 		adapter.runBFS(start, end);
	 		break;
	 	case "DFS":
	 		adapter.runDFS(start, end);
	 		break;
	 	case "Dijkstra":
	 		adapter.runDijkstra(start, end);
	 		break;
	 	// we can add more algs here
	 	}
	 }

	/**
	 * clears all vertices and edges from the graph
	 */
	public void clear() {
		vertices.clear();
		edges.clear();
	}
}
