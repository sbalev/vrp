package vrp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;

/**
 * An instance of the VRP. This class provides several useful methods to access
 * to the warehouse node, client nodes and vehicle capacity.
 * 
 * @author Stefan Balev
 * 
 */
public class VrpGraph extends SingleGraph {
	protected Node warehouse;
	protected List<Node> clients;
	protected int vehicleCapacity;

	/**
	 * Creates an empty graph
	 * 
	 * @param id
	 *            the name of the graph
	 */
	public VrpGraph(String id) {
		super(id);
		warehouse = null;
		clients = new ArrayList<Node>();
		vehicleCapacity = 0;
	}

	@Override
	public void read(String filename) throws IOException, GraphParseException,
			ElementNotFoundException {
		super.read(filename);
		if (hasAttribute("vehicleCapacity"))
			vehicleCapacity = (int) getNumber("vehicleCapacity");
		else
			vehicleCapacity = 0;
		for (Node node : this) {
			if (node.hasAttribute("warehouse"))
				warehouse = node;
			if (node.hasAttribute("client"))
				clients.add(node);
		}
	}

	/**
	 * Creates a random instance of the VRP. Chooses randomly the warehouse node
	 * and the client nodes in a graph.
	 * 
	 * @param clientCount
	 *            The number of client nodes
	 * @param vehicleCapacity
	 *            Vehicle capacity
	 * @param rnd
	 *            random Random number generator. Useful to reproduce an
	 *            instance.
	 */
	public void generateRandomInstance(int clientCount, int vehicleCapacity,
			Random rnd) {
		if (warehouse != null)
			warehouse.removeAttribute("warehouse");
		for (Node node : clients)
			node.removeAttribute("client");

		addAttribute("vehicleCapacity", vehicleCapacity);
		warehouse = Toolkit.randomNode(this, rnd);
		warehouse.addAttribute("warehouse");
		clients.clear();
		for (int i = 0; i < clientCount; i++) {
			Node client = Toolkit.randomNode(this, rnd);
			while (client == warehouse || clients.contains(client))
				client = Toolkit.randomNode(this, rnd);
			clients.add(client);
			client.addAttribute("client");
		}
	}

	/**
	 * Creates a random instance of the VRP. Chooses randomly the warehouse node
	 * and the client nodes in a graph.
	 * 
	 * @param clientCount
	 *            The number of client nodes
	 * @param vehicleCapacity
	 *            Vehicle capacity
	 */
	public void generateRandomInstance(int clientCount, int vehicleCapacity) {
		generateRandomInstance(clientCount, vehicleCapacity, new Random());
	}

	/**
	 * Sets UI attributes to the warehouse and the client nodes. Useful when the
	 * graph is displayed in a viewer.
	 */
	public void setUIClasses() {
		addAttribute("ui.stylesheet", "url('data/stylesheet.css')");
		warehouse.addAttribute("ui.class", "warehouse");
		warehouse.addAttribute("ui.label", "W");
		int i = 0;
		for (Node client : clients) {
			client.addAttribute("ui.class", "client");
			client.addAttribute("ui.label", i + "");
			i++;
		}
	}

	/**
	 * Returns the warehouse node.
	 * 
	 * @return The warehouse node
	 */
	public Node getWarehouse() {
		return warehouse;
	}

	/**
	 * Returns the vehicle capacity. The vehicles are considered identical.
	 * 
	 * @return the vehicle capacity
	 */
	public int getVehicleCapacity() {
		return vehicleCapacity;
	}

	/**
	 * Returns the number of clients.
	 * 
	 * @return The number of clients
	 */
	public int getClientCount() {
		return clients.size();
	}

	/**
	 * Returns the i-th client node.
	 * 
	 * @param i
	 *            The index of the client. Must be between 0 and
	 *            <code>getClientCount() - 1</code>
	 * @return The i-th client
	 */
	public Node getClient(int i) {
		return clients.get(i);
	}

	/**
	 * Returns the length of the shortest path between two nodes
	 * 
	 * @param from
	 *            A node
	 * @param to
	 *            A node
	 * @return The distance between <code>from</code> and <code>to</code>
	 */
	public double getDistanceBetween(Node from, Node to) {
		return getDijkstra(from).getPathLength(to);
	}

	/**
	 * Returns the shortest path between two nodes
	 * 
	 * @param from
	 *            A node
	 * @param to
	 *            A node
	 * @return
	 */
	public Path getPathBetween(Node from, Node to) {
		return getDijkstra(from).getPath(to);
	}

	protected Dijkstra getDijkstra(Node node) {
		Dijkstra dijkstra = node.getAttribute("dijkstra");
		if (dijkstra == null) {
			dijkstra = new Dijkstra(Dijkstra.Element.EDGE, "dijkstra_"
					+ node.getId(), "length");
			dijkstra.init(this);
			dijkstra.setSource(node);
			dijkstra.compute();
			node.addAttribute("dijkstra", dijkstra);
		}
		return dijkstra;
	}
}
