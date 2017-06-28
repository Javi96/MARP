package model.graph;

/**
 * Class that represents nodes of the graph
 * 
 * @author Javi
 *
 */
public class Node implements Comparable<Node> {

	/**
	 * Determines the number of nodes used in the solution
	 */
	private int k;

	/**
	 * Id value of the node
	 */
	public int id;

	/**
	 * X axis coordinate
	 */
	public int x;

	/**
	 * Y axis coordinate
	 */
	public int y;

	/**
	 * Cost of the solution
	 */
	private double cost;

	/**
	 * Estimated cost of the solution
	 */
	private double estCost;

	/**
	 * Mark used nodes during the algorithm
	 */
	private boolean[] used;

	/**
	 * Solution of each node
	 */
	private int[] sol;

	/**
	 * Length of the solution
	 */
	private int length;

	/**
	 * Node radius
	 */
	public static final int RADIUS = 25;

	/**
	 * Public constructor
	 */
	public Node() {
		this.id = 1;
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Public constructor
	 * @param length length of the solution
	 */
	public Node(int length) {
		this.id = 1;
		this.x = 0;
		this.y = 0;
		this.sol = new int[length + 1];
		this.used = new boolean[length + 1];
		for (int j = 0; j < length + 1; j++) {
			this.used[j] = false;
			this.sol[j] = 0;
		}
		this.length = length;
	}
	/**
	 * Public constructor
	 * @param length length of the solution
	 * @param id nodes identifier
	 * @param x x axis coordinate
	 * @param y y axis coordinate
	 */
	public Node(int length, int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.sol = new int[length + 1];
		this.used = new boolean[length + 1];
		for (int j = 0; j < length + 1; j++) {
			this.used[j] = false;
			this.sol[j] = 0;
		}
		this.length = length;
	}

	/**
	 * Get a portion of node solution
	 * @param index index of the element to return
	 * @return element request
	 */
	public int getSol(int index) {
		return sol[index];
	}

	/**
	 * Get the hole solution of the node
	 * @return node solution vector
	 */
	public int[] getSol() {
		return sol;
	}

	/**
	 * Set a portion of node solution
	 * @param value value to set
	 * @param index index to be used
	 */
	public void setSol(int value, int index) {
		this.sol[index] = value;
	}

	/**
	 * Set the hole nodes solution
	 * @param sol solution of the node
	 */
	public void setSol(int[] sol) {
		this.sol = sol;
	}

	/**
	 * Return the length of node´s partial solution
	 * @return length of current solution
	 */
	public int getK() {
		return k;
	}

	/**
	 * Set nodes length
	 * @param k length of the node
	 */
	public void setK(int k) {
		this.k = k;
	}

	/**
	 * Return the cost of node solution
	 * @return cost of node solution
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Set the cost of node solution
	 * @param cost new cost for node solution
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Get the estimated cost of the node
	 * @return estimated node cost
	 */
	public double getEstCost() {
		return estCost;
	}

	/**
	 * Set node estimated cost
	 * @param estCost estimated node cost
	 */
	public void setEstCost(double estCost) {
		this.estCost = estCost;
	}

	/**
	 * Return if the node determined by parameter have been used
	 * @param index node index
	 * @return true if the node is used, false otherwise
	 */
	public boolean getUsed(int index) {
		return used[index];
	}

	/**
	 * Get used nodes vector
	 * @return vector of used nodes
	 */
	public boolean[] getUsed() {
		return this.used;
	}

	/**
	 * Set the used node vector
	 * @param used used node vector
	 */
	public void setUsed(boolean[] used) {
		this.used = used;
	}

	/**
	 * Set a portion of the node used vector
	 * @param index index to be changed
	 * @param value new value of the vector
	 */
	public void setUsed(int index, boolean value) {
		this.used[index] = value;
	}

	/**
	 * Get the node id
	 * @return node id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set node id
	 * @param id node identifier
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(Node node) {
		if (this.estCost < node.getEstCost()) {
			return -1;
		} else if (this.estCost > node.getEstCost()) {
			return 1;
		}
		return 0;
	}

	@Override
	public Object clone() {
		Node newNode = new Node();
		newNode.cost = this.cost;
		newNode.estCost = this.estCost;
		newNode.id = this.id;
		newNode.k = this.k;
		newNode.x = this.x;
		newNode.y = this.y;
		newNode.sol = new int[this.length + 1];
		newNode.used = new boolean[this.length + 1];
		newNode.length = this.length + 1;
		for (int i = 1; i < this.length + 1; i++) {
			newNode.sol[i] = this.sol[i];
			newNode.used[i] = this.used[i];
		}
		return newNode;
	}
}