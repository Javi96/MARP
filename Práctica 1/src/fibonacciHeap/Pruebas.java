package fibonacciHeap;

import fibonacciHeap.model.algorithms.FibonacciHeap;
import fibonacciHeap.model.algorithms.FibonacciHeap.FibonacciNode;

/**
 * Pruebas usadas para la memoria de la práctica Se recomienda descommentar las
 * líneas necesarias para cada caso como viene indicado en el código, ya que en
 * otro caso los resultados podrían no coincidir
 * 
 * @author Javier Cortés Tejada
 *
 */
public class Pruebas {

	public static void main(String[] args) {
		FibonacciNode[] map = new FibonacciNode[20];
		FibonacciHeap heap = new FibonacciHeap();
		FibonacciNode node = null;

		for (double i = 5; i <= 14; i++) {
			node = new FibonacciNode((int) i);
			map[(int) i] = node;
			heap.insert(node);
		}

		heap.removeMin();
		map[5] = null;
		heap.decreaseKey(node, 1.0);
		map[14] = null;
		map[1] = node;

		// INSERTAR APARTADO A

		// heap.insert(new FibonacciNode(0));

		// INSERTAR APARTADO B

		// heap.insert(new FibonacciNode(100));

		// ELIMINAR MINIMO

		// heap.removeMin();
		// heap.removeMin();
		// heap.insert(new FibonacciNode(100));
		// heap.removeMin();

		// DECRECER CLAVE APARTADO A

		// heap.decreaseKey(map[10], 0);
		// map[0]=map[10];
		// map[10]=null;

		// DECRECER CLAVE APARTADO B

		// heap.decreaseKey(map[12], 0);
		// map[0]=map[12];
		// map[12]=null;

		// heap.decreaseKey(map[13], 2);
		// map[2]=map[13];
		// map[13]=null;

		System.out.println(heap.nodeVisualization());
		System.out.println(heap.treeVisualization());

	}

}
