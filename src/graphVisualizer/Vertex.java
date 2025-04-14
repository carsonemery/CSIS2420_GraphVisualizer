package graphVisualizer;

/**
 * class Vertex and its fields, contructors, getters and setters
 * Will define what a vertext will be upon user input
 * 
 * @author lincoln
 */
public class Vertex {
	public int x; //x positon on screen
	public int y; //y position on screen
	public String label; //label of what the vertex will be
	
	/**
	 * constructor for class vertex
	 * instantiates all fields
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param label the label of the vertex
	 */
	public Vertex(int x, int y, String label) {
		this.x = x;
		this.y = y;
		this.label = label;
	}
	
	/**
	 * getter method for the field x
	 * @return x the x position
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * getter method for the field y
	 * @return y the y position
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * getter method for the field label
	 * @return label the label of the vertex
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * setter method for the field x
	 * @param x the inputted x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * setter method for the field y
	 * @param y the inputted y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * setter method for the field label
	 * @param label the to-be label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}
