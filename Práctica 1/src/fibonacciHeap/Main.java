package fibonacciHeap;

import fibonacciHeap.controller.Controller;
import fibonacciHeap.model.algorithms.Model;
import fibonacciHeap.view.View;

/**
 * Main class
 * @author Javier Cortés Tejada
 *
 */
public class Main {

	public static void main(String[] args) {
		new Controller(new Model(), new View());
		
	}
}
