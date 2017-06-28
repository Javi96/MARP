package main;

import java.io.IOException;

import controller.Controller;
import model.TSP;
import view.SelectorFrame;
import view.View;

/**
 * Main class of the application. This launches the application and allows the
 * user to generate new files with which to do more test, uncommenting the code
 * below
 * 
 * @author Javi
 *
 */
public class Main {

	// public static void main(String[] args) {
	// int size = 5;
	// try {
	// System.setOut(new PrintStream(new FileOutputStream("matrix" + size +
	// ".txt")));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// int[][] matrix = new int[size][size];
	// for (int i = 0; i < size; i++) {
	// for (int j = 0; j < i; j++) {
	// if (i == j) {
	// matrix[i][j] = 0;
	// } else {
	// int rand = (int) (Math.random() * 100 + 1);
	// matrix[i][j] = rand;
	// matrix[j][i] = rand;
	// if (Math.random() < 0) {
	// matrix[i][j] = 0;
	// matrix[j][i] = 0;
	// }
	// }
	// }
	// }
	// System.out.println(size);
	// for (int i = 0; i < matrix.length; i++) {
	// for (int j = 0; j < matrix.length; j++) {
	// System.out.print(matrix[i][j] + " ");
	// }
	// System.out.println("");
	// }
	// }

	public static void main(String[] args) throws IOException, InterruptedException {
		Controller controller = new Controller(new View(), new TSP(), new SelectorFrame());
		controller.getModel().getGraph().addObserver(controller.getView());
		controller.getModel().addObserver(controller.getView());
		controller.getModel().addObserver(controller.getView());
		controller.getConfig().addObserver(controller);
	}

}
