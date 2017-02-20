package fibonacciHeap.controller;

import fibonacciHeap.model.algorithms.Model;
import fibonacciHeap.view.View;

/**
 * Work between view and model carrying information between them
 * @author Javier Cortés Tejada
 *
 */
public class Controller {

	/**
	 * Application model
	 */
	private Model model;

	/**
	 * Application view
	 */
	private View view;

	/**
	 * Public constructor
	 * @param model Application model
	 * @param view Application view
	 */
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		this.view.setController(this);
		this.view.setVisible(true);
	}

	/**
	 * Add a node to the heap
	 * @param key key of the new node
	 * @throws Exception the key is used
	 */
	public void addNode(int key) throws Exception {
		this.model.addNode(key);
	}

	/**
	 * Remove minimum element of the heap
	 * @throws Exception the heap is empty
	 */
	public void removeMinNode() throws Exception {
		this.model.removeMinNode();
	}

	/**
	 * Decrease a node value
	 * @param node node to be decreased
	 * @param key new key for node
	 * @throws Exception current key is higher than new one
	 */
	public void decreseKeyNode(int node, int key) throws Exception {
		this.model.decreseKeyNode(node, key);
	}

	/**
	 * Reset the heap
	 */
	public void clear() {
		this.model.clear();
	}

	/**
	 * Request for heap visualization information
	 * @return heap visual representation
	 */
	public String getHeapInfo(){
		return this.model.getHeapInfo();
	}
	
	/**
	 * Request for nodes information
	 * @return nodes information
	 */
	public String getNodesInfo(){
		return this.model.getNodesInfo();
	}
}
