package model.graph;

/**
 * Class that represent connections between nodes
 * 
 * @author Javi
 *
 */
public class Edge {

	/**
	 * Source node
	 */
	private Node nodeA;

	/**
	 * Destination node
	 */
	private Node nodeB;

	/**
	 * Distance between source and destination node
	 */
	private double distance;

	/**
	 * Determines if this edge is used in the solution
	 */
	private boolean marked;

	/**
	 * Determines if the edge have been used during the algorithm execution
	 */
	private boolean used;

	/**
	 * Public constructor of the class
	 * 
	 * @param nodeA
	 *            source node
	 * @param nodeB
	 *            destination node
	 * @param distance
	 *            distance between both nodes
	 */
	public Edge(Node nodeA, Node nodeB, double distance) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.distance = distance;
		this.marked = false;
		this.used = false;
	}

	/**
	 * @return the nodeA
	 */
	public Node getNodeA() {
		return nodeA;
	}

	/**
	 * @param nodeA
	 *            the nodeA to set
	 */
	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}

	/**
	 * @return the nodeB
	 */
	public Node getNodeB() {
		return nodeB;
	}

	/**
	 * @param nodeB
	 *            the nodeB to set
	 */
	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the marked
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * @param marked
	 *            the marked to set
	 */
	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	/**
	 * @return the used
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * @param used
	 *            the used to set
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}

}