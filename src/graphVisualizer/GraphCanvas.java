package graphVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GraphCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private Graph graph;
	private Vertex selectedVertex; // used when drawing edges
	private List<Vertex> path;
	private boolean selectionMode = false;
	private Vertex startVertex;
	private Vertex endVertex;

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
				Vertex target = getVertexAtPosition(e.getX(), e.getY());
				if (selectedVertex != null && target != null && selectedVertex != target) {
					Edge edge = new Edge(selectedVertex, target, 1); // default weight
					graph.getEdges().add(edge);

					if (!graph.isDirected()) {
						Edge reverse = new Edge(target, selectedVertex, 1);
						edge.setReverseOf(reverse);
						graph.getEdges().add(reverse);
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw edges
		for (Edge edge : graph.getEdges()) {
			Vertex from = edge.getFrom();
			Vertex to = edge.getTo();
			g.setColor(Color.BLACK);
			g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
		}

		// Draw vertices
		for (Vertex v : graph.getVertices()) {
			g.setColor(Color.BLUE);
			int r = (int) v.getRadius();
			g.fillOval(v.getX() - r, v.getY() - r, 2 * r, 2 * r);

			g.setColor(Color.WHITE);
			g.drawString(v.getLabel(), v.getX() - r / 2, v.getY() + 4);

		}

		// Highlight BFS path if available
		if (path != null) {
			g.setColor(Color.RED); // Use red to highlight the path
			for (int i = 0; i < path.size() - 1; i++) {
				Vertex from = path.get(i);
				Vertex to = path.get(i + 1);
				g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
			}
		}

		// Highlight selected vertices
		if (startVertex != null) {
			g.setColor(Color.RED);
			int r = (int) startVertex.getRadius();
			g.drawOval(startVertex.getX() - r, startVertex.getY() - r, 2 * r, 2 * r);
		}
		if (endVertex != null) {
			g.setColor(Color.RED);
			int r = (int) endVertex.getRadius();
			g.drawOval(endVertex.getX() - r, endVertex.getY() - r, 2 * r, 2 * r);
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
	}

}
