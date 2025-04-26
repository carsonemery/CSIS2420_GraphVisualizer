package graphVisualizer;

/**
 * class Vertex and its fields, constructors, getters and setters Will define
 * what a vertex will be upon user input
 * 
 * @author Lincoln Bunker & Carson Emery
 */
public class Vertex {
	private int x; // x position on screen
	private int y; // y position on screen
	private String label; // label of the vertex, would be a string representation of an int
	private int value; // label of what the vertex will be
	private double radius; // radius of a vertex, helps for proximity calculations and connecting via
							// edges,

	/**
	 * constructor for class vertex instantiates all fields
	 * 
	 * @param x     the x position
	 * @param y     the y position
	 * @param label the label of the vertex
	 */
	public Vertex(int x, int y, String label) {
		this.x = x;
		this.y = y;
		this.label = label;
		this.radius = 15; // currently set a default size of the radius

		try {
			this.value = Integer.parseInt(label);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Vertex label must be a positive or valid integer (no decimal)");
		}

	}

	/**
	 * getter method returns the label of the vertex
	 * 
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * setter method set the label of the vertex and sets the value if the string
	 * label is a valid integer
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		try {
			this.value = Integer.parseInt(label);
			this.label = label;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Vertex must be a valid integer");
		}
	}

	/**
	 * getter method for the field x
	 * 
	 * @return x the x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * getter method for the field y
	 * 
	 * @return y the y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * getter method for the field label
	 * 
	 * @return label the label of the vertex
	 */
	public int getValue() {
		return value;
	}

	/**
	 * setter method for the field x
	 * 
	 * @param x the inputted x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * setter method for the field y
	 * 
	 * @param y the inputted y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * setter method for the field label
	 * 
	 * @param label the to-be label
	 */
	public void setValue(int value) {
		this.value = value;
		this.label = String.valueOf(value);
	}

	/**
	 * getter method that gets the radius of the vertex
	 * 
	 * @return vertex size
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * setter method to set the size of the radius
	 * 
	 * @param the size of the radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
}
