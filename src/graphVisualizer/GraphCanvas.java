package graphVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * this class creates a canvas for displaying and interacting with a graph. it
 * supports mouse based operations for creating vertices and edges and
 * visualizes graph algorithms BFS, DFS, and Dijkstras
 * 
 * @author Carson Emery & Lincoln Bunker
 */
public class GraphCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private Graph graph;
	private Vertex selectedVertex; // used when drawing edges
	private List<Vertex> path;
	private boolean selectionMode = false;
	private Vertex startVertex;
	private Vertex endVertex;
	private List<List<Vertex>> cycles;
	private Color[] cycleColors = { Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.YELLOW};
	private String selectedAlgorithm; // used to determine whether or not to have the pop up for edge weights.

	public void setSelectedAlgorithm(String selectedAlgorithm) {
		this.selectedAlgorithm = selectedAlgorithm;
	}

	/**
	 * constructs a new canvas for the specified graph
	 * 
	 * @param graph
	 */
	public GraphCanvas(Graph graph) {
		this.graph = graph;
		setBackground(Color.WHITE);

		// Mouse listener for click interactions
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// check that we are not in selectionMode so that we can draw
				if (selectionMode == false) {
					// Double-click to add a vertex
					if (e.getClickCount() == 2) {
						String label = JOptionPane.showInputDialog("Enter vertex label:");
						if (label != null && !label.trim().isEmpty()) {
							
								Vertex vertex = new Vertex(e.getX(), e.getY(), label);
								graph.addVertex(vertex);
								repaint();
							
							
						}
					} 
				} else {

					// For DFS, no vertices need to be selected
					if ("DFS".equals(selectedAlgorithm)) {
				        // Skip vertex selection for DFS
				        return;
				    }

					// set the start and end vertices
					Vertex clickedVertex = getVertexAtPosition(e.getX(), e.getY());
					if (clickedVertex != null) {
						if (startVertex == null) {
							setStartVertex(clickedVertex);
						} else if (endVertex == null && !clickedVertex.equals(startVertex)) {
							setEndVertex(clickedVertex);

							// Only run BFS when both are set
							if (startVertex != null && endVertex != null) {
//								path = graph.runAlgorithm("BFS", startVertex, endVertex);
								repaint();
							}
						}
					}

				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				selectedVertex = getVertexAtPosition(e.getX(), e.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("mouseReleased called, selectedAlgorithm = " + selectedAlgorithm);
				Vertex target = getVertexAtPosition(e.getX(), e.getY());
				if (selectedVertex != null && target != null && selectedVertex != target) {
					Edge edge = new Edge(selectedVertex, target, 1); // default weight
					graph.getEdges().add(edge);
				

					if (!graph.isDirected()) {
						Edge reverse = new Edge(target, selectedVertex, 1);
						edge.setReverseOf(reverse);
						graph.getEdges().add(reverse);
					}
				//if the selection mode dikstra is selected, show another input dialog when edges are connected
				//this will be for edge weights, will display the number either on or above the edge line
				if ("Dijkstra".equals(selectedAlgorithm)) {
					String label = JOptionPane.showInputDialog("Enter edge weight of type double:");
				    if (label != null && !label.trim().isEmpty()) {
				        try {
				            double weight = Double.parseDouble(label.trim());
				            edge.setWeight(weight);
				        } catch (NumberFormatException ex) {
				            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for the edge weight.");
				        }
				    }
				}

					repaint();
				}
				selectedVertex = null;
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// Get current mouse position
				int x = e.getX();
				int y = e.getY();

				// Check if mouse is over any vertex
				Vertex hoverVertex = getVertexAtPosition(x, y);

				// Change cursor based on whether it's over a vertex
				if (hoverVertex != null) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(Cursor.getDefaultCursor());
				}
			}
		});
	}

	// helper method to get the vertex at a given position of a mouse
	private Vertex getVertexAtPosition(int x, int y) {
		for (Vertex v : graph.getVertices()) {
			double dx = x - v.getX();
			double dy = y - v.getY();
			double radiusSquared = v.getRadius() * v.getRadius();

			if (dx * dx + dy * dy <= radiusSquared) {
				return v;
			}
		}
		return null;
	}

	/**
	 * paints the graph on this canvas, renders vertices, edges, and algorithm
	 * visualizations
	 *
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// cast to graphics2D for better rendering
		Graphics2D g2d = (Graphics2D) g;

		// enable antialiasing
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw edges
		g2d.setStroke(new BasicStroke(1.5f));
		for (Edge edge : graph.getEdges()) {
			Vertex from = edge.getFrom();
			Vertex to = edge.getTo();
			g.setColor(Color.BLACK);
			g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
			
			if ("Dijkstra".equals(selectedAlgorithm)) {
				int midX = (from.getX() + to.getX()) / 2;
				int midY = (from.getY() + to.getY()) / 2;
				
				// Draw the edge weight slightly offset so it doesn't sit exactly on the line
				g.setColor(Color.BLUE); // (Optional) Different color for weight text
				String weightStr = String.valueOf(edge.getWeight());
				g.drawString(weightStr, midX + 5, midY - 5);	
			}
		}

		// draw vertices
		for (Vertex v : graph.getVertices()) {
			g2d.setColor(Color.BLUE);
			int r = (int) v.getRadius();
			g.fillOval(v.getX() - r, v.getY() - r, 2 * r, 2 * r);

			// draw a slight boarder
			g.setColor(Color.DARK_GRAY);
			g2d.drawOval(v.getX() - r, v.getY() - r, 2 * r, 2 * r);

			// draw label
			g2d.setColor(Color.WHITE);
			FontMetrics fm = g2d.getFontMetrics();
			String label = v.getLabel();
			int textWidth = fm.stringWidth(label);
			g2d.drawString(label, v.getX() - textWidth / 2, v.getY() + fm.getAscent() / 2);
		}

		// draw paths
		if (path != null) {
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(3.0f));
			drawPath(g2d, path);
		}

		// draw cycles
		if (cycles != null) {
			for (int i = 0; i < cycles.size(); i++) {
				g2d.setColor(cycleColors[i % cycleColors.length]);
				g2d.setStroke(new BasicStroke(3.0f));
				List<Vertex> currentCycle = cycles.get(i);
				drawCycle(g2d, currentCycle);
			}
		}

		// Highlight selected vertices
		if (startVertex != null) {
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(2.0f));
			int r = (int) startVertex.getRadius();
			g.drawOval(startVertex.getX() - r, startVertex.getY() - r, 2 * r, 2 * r);
		}
		if (endVertex != null) {
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(2.0f));
			int r = (int) endVertex.getRadius();
			g.drawOval(endVertex.getX() - r, endVertex.getY() - r, 2 * r, 2 * r);
		}

	}

	/**
	 * helper method to draw paths
	 * 
	 * @param g2d  the graphics context
	 * @param path the path to draw as a list of vertices
	 */
	private void drawPath(Graphics2D g2d, List<Vertex> path) {
		for (int i = 0; i < path.size() - 1; i++) {
			Vertex from = path.get(i);
			Vertex to = path.get(i + 1);
			g2d.drawLine(from.getX(), from.getY(), to.getX(), to.getY());

			// highlight the vertices in the path
			int r = (int) from.getRadius();
			g2d.drawOval(from.getX() - r - 2, from.getY() - r - 2, 2 * r + 4, 2 * r + 4);

		}

		// highlight the last vertex in the path
		if (path.size() > 0) {
			Vertex last = path.get(path.size() - 1);
			int r = (int) last.getRadius();
			g2d.drawOval(last.getX() - r - 2, last.getY() - r - 2, 2 * r + 4, 2 * r + 4);

		}

	}

	/**
	 * helper method to draw cycles
	 * 
	 * @param g2d   the graphics context
	 * @param cycle the cycle to draw as a list of sub lists of vertices
	 */
	private void drawCycle(Graphics2D g2d, List<Vertex> cycle) {
		for (int i = 0; i < cycle.size() - 1; i++) {
			Vertex from = cycle.get(i);
			Vertex to = cycle.get(i + 1);
			g2d.drawLine(from.getX(), from.getY(), to.getX(), to.getY());

			// Highlight vertices in the cycle
			int r = (int) from.getRadius();
			g2d.drawOval(from.getX() - r - 2, from.getY() - r - 2, 2 * r + 4, 2 * r + 4);

			// If this is the last edge, also highlight the last vertex
			if (i == cycle.size() - 2) {
				r = (int) to.getRadius();
				g2d.drawOval(to.getX() - r - 2, to.getY() - r - 2, 2 * r + 4, 2 * r + 4);
			}
		}
	}

	public void setSelectionMode(boolean mode) {
		this.selectionMode = mode;
	}

	public Vertex getStartVertex() {
		return this.startVertex;
	}

	public Vertex getEndVertex() {
		return this.endVertex;
	}

	public void setStartVertex(Vertex start) {
		this.startVertex = start;
	}

	public void setEndVertex(Vertex end) {
		this.endVertex = end;
	}

	public void resetSelections() {
		this.startVertex = null;
		this.endVertex = null;
	}

	public void setPath(List<Vertex> path) {
		this.path = path;
		repaint();
	}

	public void setCycles(List<List<Vertex>> cycle) {
		this.cycles = cycle;
	}

	public List<List<Vertex>> getCycles() {
		return this.cycles;
	}

}
