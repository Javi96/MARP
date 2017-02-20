package fibonacciHeap.model.algorithms;

import java.util.HashMap;

import fibonacciHeap.model.algorithms.FibonacciHeap.FibonacciNode;

/**
 * Work with data application and sent it to controller
 * 
 * @author Javier Cortés Tejada
 *
 */
public class Model {

	/**
	 * Data structure to be used
	 */
	private FibonacciHeap heap;

	/**
	 * Contains all heap nodes to avoid key repeated
	 */
	private HashMap<Integer, FibonacciNode> nodes;

	/**
	 * Public constructor
	 */
	public Model() {
		this.heap = new FibonacciHeap();
		this.nodes = new HashMap<Integer, FibonacciNode>();
	}

	/**
	 * Add a node to the heap
	 * @param key key of the new node
	 * @throws Exception the key is used
	 */
	public void addNode(int key) throws Exception {
		if (this.nodes.get(key) != null) {
			throw new Exception("Used key");
		}
		FibonacciNode node = new FibonacciNode(key);
		this.heap.insert(node);
		this.nodes.put(key, node);
	}

	/**
	 * Remove minimum element of the heap
	 * @throws Exception the heap is empty
	 */
	public void removeMinNode() throws Exception {
		if (heap.isEmpty()) {
			throw new Exception("Empty heap");
		}
		this.nodes.remove(this.heap.getMin().getKey());
		this.heap.removeMin();
	}

	/**
	 * Decrease a node value
	 * @param nodeKey node to be decreased
	 * @param newKey new key for node
	 * @throws Exception current key is higher than new one
	 */
	public void decreseKeyNode(int nodeKey, int newKey) throws Exception {
		if (nodes.get(nodeKey) == null) {
			throw new Exception("Node not found");
		}
		if (nodes.get(newKey) != null) {
			throw new Exception("Current key used");
		}
		FibonacciNode decreaseNode = this.nodes.get(nodeKey);
		this.nodes.remove(nodeKey);
		this.nodes.put(newKey, this.heap.decreaseKey(decreaseNode, newKey));

	}

	/**
	 * Reset the heap
	 */
	public void clear() {
		heap.clear();
		this.nodes = new HashMap<Integer, FibonacciNode>();
	}

	/**
	 * Request for heap visualization information
	 * @return heap visual representation
	 */
	public String getHeapInfo() {
		return this.heap.nodeVisualization();
	}

	/**
	 * Request for nodes information
	 * @return nodes information
	 */
	public String getNodesInfo() {
		return this.heap.treeVisualization();
	}
}
