package model.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import view.View;

/**
 * Representation of the graph used by the TSP algorithm
 * 
 * @author Javi
 *
 */
public class Graph extends Observable {

	/**
	 * Adjacency matrix representation
	 */
	private int[][] matrix;

	/**
	 * Node list
	 */
	protected ArrayList<Node> nodes;

	/**
	 * Edge list
	 */
	protected ArrayList<Edge> edges;

	/**
	 * Public constructor of the class
	 */
	public Graph() {
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
	}

	/**
	 * Initializes the graph by using the file given by path
	 * @param path path of the file to be used
	 */
	public void run(String path) {
		try {
			this.readMatrixFromFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.generateNodesSphere();
		this.generateEdges();
	}

	/**
	 * Generate nodes relative position into the view
	 */
	public void generateNodesSphere() {
		double angle = 2 * Math.PI / matrix.length;
		for (int i = 0; i < matrix.length; i++) {
			nodes.add(i, new Node(matrix.length, i + 1, 0, 0));
			nodes.get(i).x = (int) (View.SCREEN_HEIGTH / 2.5 * Math.cos(angle * i) + View.SCREEN_HEIGTH / 2);
			nodes.get(i).y = (int) (View.SCREEN_HEIGTH / 2.5 * Math.sin(angle * i) + View.SCREEN_HEIGTH / 2);
		}
	}

	/**
	 * Generates edges from matrix
	 */
	public void generateEdges() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (i != j && matrix[i][j] != 0) {
					this.edges.add(new Edge(nodes.get(i), nodes.get(j), matrix[i][j]));
				}
			}
		}
	}

	/**
	 * Read the graph from the given path
	 * @param path path of the file to be used
	 * @throws IOException if the file does not exits
	 */
	public void readMatrixFromFile(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		int size = Integer.parseInt(br.readLine());
		matrix = new int[size][size];
		String[] chain;
		for (int i = 0; i < size; i++) {
			chain = br.readLine().toString().split(" ");
			for (int j = 0; j < chain.length; j++) {
				this.matrix[i][j] = Integer.parseInt(chain[j]);
			}
		}
		br.close();
		setChanged();
		notifyObservers(this.matrix);
	}

	/**
	 * Return the adjacency matrix
	 * @return adjacency matrix
	 */
	public int[][] getMatrix() {
		return matrix;
	}

	/**
	 * Return the nodes list
	 * @return list of nodes
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	/**
	 * Return the edges list
	 * @return list of edges
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}
}
