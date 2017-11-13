package vrp;

/**
 * Simple test class. Loads a VRP graph from a file, computes a greedy solution
 * and displays it.
 * 
 * @author Stefan Balev
 * 
 */
public class Test {
	public static void main(String[] args) {
		VrpGraph graph = new VrpGraph("Le Havre");
		try {
			graph.read("data/lh_020_005.dgs");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		graph.setUIClasses();
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.quality");
		graph.display(false);

		Solution sol = new GreedySolution(graph);
		sol.compute();
		sol.setUIClasses();

		System.out.printf(
				"This solution uses %d vehicles. Total length: %.2f%n",
				sol.getVehicleCount(), sol.getTotalPathLength());
		for (int i = 0; i < sol.getVehicleCount(); i++) {
			System.out.printf(
					"Vehilce %d: Path length %10.2f. Clients served:", i,
					sol.getVehiclePathLength(i));
			for (int j = 0; j < sol.getClientCount(i); j++)
				System.out.printf("%4s",
						(String)(sol.getClient(i, j).getAttribute("ui.label")));
			System.out.println();
		}
	}
}
