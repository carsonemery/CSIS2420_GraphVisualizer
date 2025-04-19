package graphVisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GraphWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Graph graph = new Graph(false); 

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GraphWindow frame = new GraphWindow();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public GraphWindow() {
        setTitle("Graph Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center window

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // ---------- TOP PANEL ----------
        JPanel topPanel = new JPanel(new BorderLayout());

        // Top Left: Instructions
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));
        instructionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5)); // padding

        JLabel instruction1 = new JLabel("Double Click to draw a vertex");
        JLabel instruction2 = new JLabel("Click and Hold to connect with an edge");

        instructionsPanel.add(instruction1);
        instructionsPanel.add(instruction2);

        topPanel.add(instructionsPanel, BorderLayout.WEST);

        // Top Right: Dropdown for algorithms
        String[] algorithms = { "BFS", "DFS", "Dijkstra", "Topological Sort" };
        JComboBox<String> algorithmDropdown = new JComboBox<>(algorithms);
        JPanel algoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        algoPanel.add(new JLabel("Choose an algorithm:"));
        algoPanel.add(algorithmDropdown);
        topPanel.add(algoPanel, BorderLayout.EAST);

        // ---------- BOTTOM PANEL ----------
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // ---------- CENTER PANEL (GraphCanvas Placeholder) ----------
//      JPanel centerPanel = new JPanel(); // Placeholder for GraphCanvas
      GraphCanvas graphCanvas = new GraphCanvas(graph);
      graphCanvas.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//      centerPanel.setBackground(Color.WHITE);
//      centerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Bottom Left: Clear button
        JButton clearButton = new JButton("Clear Graph");
        JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clearPanel.add(clearButton);
        bottomPanel.add(clearPanel, BorderLayout.WEST);
        clearButton.addActionListener(e -> {
        	graph.getVertices().clear();
        	graph.getEdges().clear();
        	graphCanvas.repaint();
        });

        // Assemble all
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(graphCanvas, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
	}

}
