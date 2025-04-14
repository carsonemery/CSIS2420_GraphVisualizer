package graphVisualizer;

/**
 * class Edge and its fields, constructor, getters and setters
 * Will define what an edge will be given a 'from' Vertex and a 'to' Vertex
 * 
 * @author lincoln
 */
public class Edge {
	private Vertex from; //Starting point (source) of the edge
	private Vertex to; //Ending point (destination) of the edge
	private boolean isDirected; //whether this will be directed or not
	
	/**
	 * Constructor for class Edge
	 * instantiates all fields
	 * @param from source of edge
	 * @param to destination of the edge
	 * @param isDirected directed or not directed
	 */
	public Edge(Vertex from, Vertex to, boolean isDirected) {
		this.from = from;
		this.to = to;
		this.isDirected = isDirected;
	}
	
	/**
	 * getter method for field from
	 * @return from the source of the edge
	 */
	public Vertex getFrom() {
		return from;
	}
	
	/**
	 * getter method for field to
	 * @return to the destination of the edge
	 */
	public Vertex getTo() {
		return to;
	}
	
	/**
	 * getter method for field isDirected
	 * @return isDirected boolean value whether the edge is directed or not
	 */
	public boolean getIsDirected() {
		return isDirected;
	}
	
	/**
	 * setter method for field from
	 * @param from the to-be from value
	 */
	public void setFrom(Vertex from) {
		this.from = from;
	}
	
	/**
	 * setter method for field to
	 * @param to the to-be to value
	 */
	public void setTo(Vertex to) {
		this.to = to;
	}
	
	/**
	 * setter method for field isDirected
	 * @param isDirected the to-be isDirected value
	 */
	public void setIsDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}
}
