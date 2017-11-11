package vrp;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

/**
 * <p>
 * A simple greedy algorithm that constructs a feasible solution of a VRP. The
 * path of each vehicle is constructed as follows. The vehicle starts from the
 * warehouse. At each iteration it chooses the closest client not yet served. If
 * the closest client is farther than the warehouse or if its capacity is
 * exceeded, the vehicle returns to the warehouse.
 * 
 * @author Stefan Balev
 * 
 */
public class GreedySolution implements Solution {
	protected VrpGraph graph;
	protected List<List<Node>> paths;
	protected List<Node> clientsLeft;

	public GreedySolution(VrpGraph graph) {
		this.graph = graph;
		paths = new ArrayList<List<Node>>();
		clientsLeft = new ArrayList<Node>(graph.clients);
	}

	protected Node getClosestClient(Node from) {
		Node closest = null;
		double distance = Double.POSITIVE_INFINITY;
		for (Node client : clientsLeft) {
			double d = graph.getDistanceBetween(from, client);
			if (d < distance) {
				distance = d;
				closest = client;
			}
		}
		return closest;
	}

	public void compute() {
		Node warehouse = graph.getWarehouse();
		while (!clientsLeft.isEmpty()) {
			List<Node> path = new ArrayList<Node>();
			Node current = warehouse;
			while (path.size() < graph.getVehicleCapacity()) {
				Node next = getClosestClient(current);
				double d = graph.getDistanceBetween(current, next);
				if (current != warehouse
						&& d > graph.getDistanceBetween(current, warehouse))
					break;
				path.add(next);
				clientsLeft.remove(next);
				if (clientsLeft.isEmpty())
					break;
				current = next;
			}
			paths.add(path);
		}
	}

	public Node getClient(int vehicle, int i) {
		return paths.get(vehicle).get(i);
	}

	public int getClientCount(int vehicle) {
		return paths.get(vehicle).size();
	}

	public VrpGraph getGraph() {
		return graph;
	}

	public double getTotalPathLength() {
		double total = 0;
		for (int i = 0; i < paths.size(); i++)
			total += getVehiclePathLength(i);
		return total;
	}

	public int getVehicle(Node client) {
		for (int i = 0; i < paths.size(); i++)
			if (paths.get(i).contains(client))
				return i;
		return -1;
	}

	public int getVehicleCount() {
		return paths.size();
	}

	public double getVehiclePathLength(int vehicle) {
		Node warehouse = graph.getWarehouse();
		List<Node> path = paths.get(vehicle);
		double d = graph.getDistanceBetween(warehouse, path.get(0));
		for (int i = 1; i < path.size(); i++)
			d += graph.getDistanceBetween(path.get(i - 1), path.get(i));
		d += graph.getDistanceBetween(path.get(path.size() - 1), warehouse);
		return d;
	}

	protected void markPathBetween(Node from, Node to, double color) {
		Path path = graph.getPathBetween(from, to);
		for (Edge e : path.getEachEdge()) {
			e.addAttribute("ui.class", "path");
			e.addAttribute("ui.color", color);
		}
	}

	public void setUIClasses() {
		for (Edge e : graph.getEachEdge()) {
			e.removeAttribute("ui.class");
			e.removeAttribute("ui.color");
		}
		for (int i = 0; i < paths.size(); i++) {
			List<Node> path = paths.get(i);
			double c = i / (paths.size() - 1.0);
			markPathBetween(graph.getWarehouse(), path.get(0), c);
			for (int j = 1; j < path.size(); j++)
				markPathBetween(path.get(j - 1), path.get(j), c);
			markPathBetween(path.get(path.size() - 1), graph.getWarehouse(), c);
		}
	}
}
