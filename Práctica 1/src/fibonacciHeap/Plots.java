package fibonacciHeap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import fibonacciHeap.model.algorithms.FibonacciHeap;
import fibonacciHeap.model.algorithms.FibonacciHeap.FibonacciNode;

/**
 * Generador de archivos .dat para gráficas
 * 
 * @author Javier Cortés Tejada
 *
 */
public class Plots {

	public static final int N = 100000;

	public static void main(String[] args) {
		Plots.insertPlot();
		Plots.removeMinPlot();
		Plots.decresaseKeyPlot();
	}

	private static void removeMinPlot() {
		FibonacciHeap heap = new FibonacciHeap();
		double xs, ys, t = 0;
		for (int i = 0; i <= N; i++) {
			heap.insert(new FibonacciNode((int) (Math.random() * (2 * N))));
		}
		try {
			FileWriter fichero = new FileWriter("src/removemin" + N + ".dat");
			for (int i = 0; i <= N; i++) {

				xs = System.nanoTime();
				heap.removeMin();
				ys = System.nanoTime();
				t += (ys - xs) / 1000;
				fichero.write(i + " " + t + "\n");
			}
			fichero.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static void insertPlot() {
		FibonacciHeap heap = new FibonacciHeap();
		double xs, ys, t = 0;
		try {
			FileWriter fichero = new FileWriter("src/insert" + N + ".dat");
			for (int i = 0; i <= N; i++) {
				xs = System.nanoTime();
				heap.insert(new FibonacciNode((int) (Math.random() * (2 * N))));
				ys = System.nanoTime();
				t += (ys - xs) / 1000;
				fichero.write(i + " " + t + "\n");
			}
			fichero.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static void decresaseKeyPlot() {
		FibonacciHeap heap = new FibonacciHeap();
		ArrayList<FibonacciNode> nodes = new ArrayList<FibonacciNode>();
		FibonacciNode node = null;
		double xs, ys, t = 0;
		for (int i = 0; i <= N; i++) {
			node = new FibonacciNode((int) (Math.random() * (2 * N)));
			nodes.add(node);
			heap.insert(node);
		}
		try {
			FileWriter fichero = new FileWriter("src/decreaseKey" + N + ".dat");
			for (int i = 0; i <= N; i++) {
				xs = System.nanoTime();
				heap.decreaseKey(nodes.get(i), (int) Double.NEGATIVE_INFINITY);
				ys = System.nanoTime();
				t += (ys - xs) / 1000;
				fichero.write(i + " " + t + "\n");
			}
			fichero.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
