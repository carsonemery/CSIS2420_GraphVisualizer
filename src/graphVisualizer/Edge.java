package graphVisualizer;

/**
 * class Edge and its fields, constructor, getters and setters Will define what
 * an edge will be given a 'from' Vertex and a 'to' Vertex
 *
 * @author Lincoln Bunker & Carson Emery
 */
public class Edge {
	private Vertex from; // Starting point (source) of the edge
	private Vertex to; // Ending point (destination) of the edge
	private double weight; // allow user to give a weight to the edges, possibly choose random weights etc
	private Edge reverseEdge; // optional reference to the reverse edge for undirected graphs

	/**
	 * Constructor for class Edge instantiates all fields
	 * 
	 * @param from   source of edge
	 * @param to     destination of the edge
	 * @param weight the weight of the edge
	 */
	public Edge(Vertex from, Vertex to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	/**
	 * getter method for field from
	 * 
	 * @return from the source of the edge
	 */
	public Vertex getFrom() {
		return from;
	}

	/**
	 * getter method for field to
	 * 
	 * @return to the destination of the edge
	 */
	public Vertex getTo() {
		return to;
	}

	/**
	 * getter method for getting the weight of each edge
	 * 
	 * @return the weight of each edge
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * setter method that sets the weight of an edge
	 * 
	 * @param weight a given weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;

		// update the reverse edge weight if it exits
		if (reverseEdge != null) {
			reverseEdge.weight = weight;
		}
	}

	/**
	 * setter method for field from
	 * 
	 * @param from the to-be from value
	 */
	public void setFrom(Vertex from) {
		this.from = from;
	}

	/**
	 * setter method for field to
	 * 
	 * @param to the to-be to value
	 */
	public void setTo(Vertex to) {
		this.to = to;
	}

	/**
	 * Links this edge with its reverse edge (for undirected graphs)
	 * 
	 * @param edge the reverse edge to link with
	 */
	public void setReverseOf(Edge edge) {
		this.reverseEdge = edge;
		edge.reverseEdge = this;
	}

	/**
	 * Gets the reverse edge of this edge (if set)
	 * 
	 * @return the reverse edge or null
	 */
	public Edge getReverseEdge() {
		return reverseEdge;
	}
}
