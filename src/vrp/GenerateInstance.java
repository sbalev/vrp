package vrp;

/**
 * Reads a graph from a file, then randomly chooses warehouse and client nodes
 * and writes the result to another file.
 * 
 * @author Stefan Balev
 * 
 */
public class GenerateInstance {
	public static void main(String[] args) {
		int clientCount = 100;
		int vehicleCapacity = 20;
		try {
			VrpGraph graph = new VrpGraph("Le Havre");
			graph.read("data/lh.dgs");
			graph.generateRandomInstance(clientCount, vehicleCapacity);
			graph.write(String.format("data/lh_%03d_%03d.dgs", clientCount,
					vehicleCapacity));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
