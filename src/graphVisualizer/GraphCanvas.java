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

    public GraphCanvas(Graph graph) {
        this.graph = graph;
        setBackground(Color.WHITE);

        // Mouse listener for click interactions
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Double-click to add a vertex
                if (e.getClickCount() == 2) {
                    String label = JOptionPane.showInputDialog("Enter vertex label:");
                    if (label != null && !label.trim().isEmpty()) {
                        Vertex vertex = new Vertex(e.getX(), e.getY(), label);
                        graph.addVertex(vertex);
                        repaint();
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
    }

    // Helper to get vertex at mouse position
    private Vertex getVertexAtPosition(int x, int y) {
        for (Vertex v : graph.getVertices()) {
            double dx = x - v.getX();
            double dy = y - v.getY();
            if (Math.sqrt(dx * dx + dy * dy) <= v.getRadius()) {
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
    }
    
    public void setSelectionMode(boolean mode) {
    	
    }
    
    public Vertex getStartVertex() {
    	return null;
    }
    
    public Vertex getEndVertex() {
    	return null;
    }
    
    public void resetSelections() {
    	
    }
    
    public void setPath(List<Vertex> path) {
    	this.path = path;
    }
    
    
}
