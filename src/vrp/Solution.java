package vrp;

import org.graphstream.graph.Node;

/**
 * A solution of the VRP. This interface provides several basic methods to
 * explore solutions of the VRP.
 * 
 * @author Stefan Balev
 * 
 */
public interface Solution {
	/**
	 * The instance of the VRP
	 * 
	 * @return The VRP graph
	 */
	VrpGraph getGraph();

	/**
	 * Computes a solution
	 */
	void compute();

	/**
	 * The path length of a vehicle
	 * 
	 * @param vehicle
	 *            the index of the vehicle, must be between 0 and
	 *            <code>getVehicleCount() - 1</code>
	 * @return The path length of a vehicle
	 */
	double getVehiclePathLength(int vehicle);

	/**
	 * The total length (objective function) of this solution
	 * 
	 * @return the sum of the path lengths of all vehicles
	 */
	double getTotalPathLength();

	/**
	 * The number of vehicles used
	 * 
	 * @return The number of vehicles used
	 */
	int getVehicleCount();

	/**
	 * The number of clients served by a vehicle
	 * 
	 * @param vehicle
	 *            the index of the vehicle, must be between 0 and
	 *            <code>getVehicleCount() - 1</code>
	 * @return The number of clients served by this vehicle
	 */
	int getClientCount(int vehicle);

	/**
	 * The i-th client served by a vehicle
	 * 
	 * @param vehicle
	 *            the index of the vehicle, must be between 0 and
	 *            <code>getVehicleCount() - 1</code>
	 * @param i
	 *            the index of the client, must be between 0 and
	 *            <code>getClientCount(vehicle) - 1</code>
	 * @return
	 */
	Node getClient(int vehicle, int i);

	/**
	 * The vehicle which serves given client
	 * 
	 * @param client
	 *            The client node
	 * @return The index of the vehicle which serves the client
	 */
	int getVehicle(Node client);

	/**
	 * Sets attribute on the edges for viewing this solution
	 */
	void setUIClasses();
}
