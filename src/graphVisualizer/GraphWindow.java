package graphVisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

public class GraphWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Graph graph = new Graph(false);
	private GraphCanvas graphCanvas;

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
		JLabel instruction3 = new JLabel("Use Mode Toggle to switch between draw and select");

		instructionsPanel.add(instruction1);
		instructionsPanel.add(instruction2);
		instructionsPanel.add(instruction3);

		topPanel.add(instructionsPanel, BorderLayout.WEST);

		// RIGHT SIDE CONTROLS
		// create container panel for all right side controls
		JPanel rightControlPanel = new JPanel();
		rightControlPanel.setLayout(new BoxLayout(rightControlPanel, BoxLayout.Y_AXIS));

		// mode toggle panel
		JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JToggleButton modeToggle = new JToggleButton("Drawing Mode");

		// algorithm selection dropdown
		JPanel algoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		String[] algorithms = { "BFS", "DFS", "Dijkstra", "Topological Sort" };
		JComboBox<String> algorithmDropdown = new JComboBox<>(algorithms);
		algoPanel.add(new JLabel("Choose an algorithm:"));
		algoPanel.add(algorithmDropdown);

		// run algos button panel
		JPanel runPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton runButton = new JButton("Run Algorithm");
		runPanel.add(runButton);

		// ---------- CENTER PANEL (GraphCanvas Placeholder) ----------
		graphCanvas = new GraphCanvas(graph);
		graphCanvas.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// ---------- BOTTOM PANEL ----------
		JPanel bottomPanel = new JPanel(new BorderLayout());

		// Status Label used to show messages to the user
		JLabel statusLabel = new JLabel("Ready");
		bottomPanel.add(statusLabel, BorderLayout.EAST);

		// Bottom Left: Clear button
		JButton clearButton = new JButton("Clear Graph");
		JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		clearPanel.add(clearButton);
		bottomPanel.add(clearPanel, BorderLayout.WEST);

		// --------- ADD ACTION LISTENERS (in one area) ----------

		// Mode Toggle action listener
		modeToggle.addActionListener(e -> {
			boolean isSelectionMode = modeToggle.isSelected();
			if (isSelectionMode) {
				modeToggle.setText("Selection Mode");
				statusLabel.setText("Select start and end vertices");
				// implement in graphCanvas
				graphCanvas.setSelectionMode(true);
			} else {
				modeToggle.setText("Drawing Mode");
				statusLabel.setText("Draw mode: Double-click to add vertex, drag to connect");
				graphCanvas.setSelectionMode(false);
			}
		});

		// run algos button listener
		runButton.addActionListener(e -> {
			// Get selected algorithm
			String selectedAlgorithm = (String) algorithmDropdown.getSelectedItem();

			// graphCanvas methods
			Vertex start = graphCanvas.getStartVertex();
			Vertex end = graphCanvas.getEndVertex();

			if (start == null || end == null) {
				statusLabel.setText("Please select start and end vertices first");
				return;
			}

			// Run the algorithm
			try {
				Graph graph = new Graph();

				List<Vertex> path = graph.runAlgorithm(selectedAlgorithm, start, end);

				if (path != null) {
					graphCanvas.setPath(path);
					statusLabel.setText("Path found! Length: " + path.size());
				} else {
					statusLabel.setText("No path exists between selected vertices");
				}
			} catch (Exception ex) {
				statusLabel.setText("Error: " + ex.getMessage());
			}
		});

		// clear button listener
		clearButton.addActionListener(e -> {
			graph.clear();
			graphCanvas.resetSelections(); 
			statusLabel.setText("Graph cleared");
			graphCanvas.repaint();
		});

		// ---- ASSEMBLE PANELS -----

		// add components to mode panel
		modePanel.add(modeToggle);

		// add all control panels to right side container
		rightControlPanel.add(modePanel);
		rightControlPanel.add(algoPanel);
		rightControlPanel.add(runPanel);

		// add right controls to top panel
		topPanel.add(rightControlPanel, BorderLayout.EAST);

		// assemble main components
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(graphCanvas, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		// add to frame
		add(mainPanel);
		setVisible(true);
	}

}
