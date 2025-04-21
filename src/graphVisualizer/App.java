package graphVisualizer;

/**
 * class App runs the main method for this application, and starts the GUI
 * 
 * @author lincoln
 */
public class App {
	
	/**
	 * main method for class app
	 * creates a new GraphWindow GUI
	 * @param args placeholder params (unused)
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			new GraphWindow();
		});
	}

}
