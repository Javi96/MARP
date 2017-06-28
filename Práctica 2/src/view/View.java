package view;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.graph.Edge;
import model.graph.Graph;
import model.graph.Node;
import processing.core.PApplet;

/**
 * 
 * Shows the user the evolution of the graph during the execution of the
 * algorithm, besides the adjacency matrix and several results of said execution
 * 
 * @author Javi
 *
 */
public class View implements Observer {

	/**
	 * Basic color of the view
	 */
	public static int BACKGROUND_COLOR = 192;

	/**
	 * Width of the screen
	 */
	public final static int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;

	/**
	 * Height of the screen
	 */
	public final static int SCREEN_HEIGTH = Toolkit.getDefaultToolkit().getScreenSize().height;

	/**
	 * Path of the file to be used
	 */
	private StringBuilder path = new StringBuilder();

	/**
	 * Number of nodes explored during the execution of the algorithm
	 */
	private int totalNodes;

	/**
	 * Total execution time, in milliseconds
	 */
	private int totalTime;

	/**
	 * Adjacency matrix of the graph
	 */
	private int[][] matrix = new int[0][0];

	/**
	 * List of nodes
	 */
	private ArrayList<Node> nodes = new ArrayList<Node>();

	/**
	 * List of edges
	 */
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	/**
	 * Component in charge of the graph view
	 */
	private PApplet papplet = new PApplet() {

		public void settings() {
			papplet.fullScreen();
			papplet.setSize(View.SCREEN_WIDTH, View.SCREEN_HEIGTH);
		}

		public void setup() {
			papplet.background(192, 192, 192);
			papplet.frameRate = 60;
			papplet.ellipseMode(PApplet.CENTER);
		}

		public void draw() {
			drawEdges();
			drawNodes();
			drawMatrix();
			moveNodes();
			drawConsole();
		}

	};

	/**
	 * Draw the nodes into the view
	 */
	public void drawNodes() {
		for (int i = 0; i < this.nodes.size(); i++) {
			papplet.textAlign(PApplet.CENTER, PApplet.CENTER);
			papplet.fill(255);
			papplet.ellipse(this.nodes.get(i).x, this.nodes.get(i).y, Node.RADIUS * 2, Node.RADIUS * 2);
			papplet.fill(0);
			papplet.textSize(16);
			papplet.text(this.nodes.get(i).id, this.nodes.get(i).x, this.nodes.get(i).y);
		}

	}

	/**
	 * Draw edges into the view
	 */
	public void drawEdges() {
		papplet.background(BACKGROUND_COLOR);
		papplet.fill(BACKGROUND_COLOR);
		papplet.stroke(1, 1, 1);
		papplet.beginShape(PApplet.LINES);
		for (Edge edge : edges) {
			if (edge.isMarked()) {
				papplet.strokeWeight(8);
				papplet.stroke(036, 231, 017);
				papplet.line(edge.getNodeA().x, edge.getNodeA().y, edge.getNodeB().x, edge.getNodeB().y);
			} else {
				papplet.stroke(1, 1, 1);
				papplet.strokeWeight(1);
				papplet.line(edge.getNodeA().x, edge.getNodeA().y, edge.getNodeB().x, edge.getNodeB().y);

			}
		}
		papplet.stroke(1, 1, 1);
		papplet.endShape();
		papplet.strokeWeight(2);

	}

	/**
	 * Draw adjacency matrix into the view
	 */
	public void drawMatrix() {
		int initial = (papplet.width - papplet.height) / (matrix.length + 2);
		int xValue = (papplet.width - papplet.height) / (matrix.length + 1) / 2 + initial;
		int yValue = (papplet.width - papplet.height) / (matrix.length + 1) / 2 + initial;
		int xRect = xValue - initial / 2;
		int yRect = yValue - initial / 2;
		int nodesRow = xRect - initial;
		int AUX = xValue;
		int AUY = yValue;
		papplet.fill(BACKGROUND_COLOR);
		papplet.stroke(1, 1, 1);
		papplet.translate(papplet.height + initial / 2, initial / 2);
		papplet.beginShape();
		papplet.stroke(1, 1, 1);

		for (int i = 1; i <= this.matrix.length; i++) {
			nodesRow += initial;
			papplet.fill(200, 150, 100);
			papplet.rect(nodesRow, 0, initial, initial, 6);
			papplet.rect(0, nodesRow, initial, initial, 6);
			papplet.fill(65);
			papplet.textSize(18);
			papplet.text(i, nodesRow + initial / 2, initial / 2);
			papplet.text(i, initial / 2, nodesRow + initial / 2);
		}
		for (int i = 1; i <= this.matrix.length; i++) {
			for (int j = 1; j <= this.matrix.length; j++) {
				papplet.fill(100, 150, 200);
				papplet.rect(xRect, yRect, initial, initial, 6);
				papplet.textAlign(PApplet.CENTER, PApplet.CENTER);
				if (i == j) {
					papplet.fill(0, 0, 0);
					papplet.text("-", AUX, AUY);
				} else {
					papplet.fill(0, 0, 0);
					papplet.textSize(18);
					papplet.text(matrix[j - 1][i - 1], AUX, AUY);
				}
				AUY += initial;
				yRect += initial;
			}
			AUY = yValue;
			yRect = yValue - initial / 2;
			AUX += initial;
			xRect += initial;
		}
		papplet.endShape();
	}

	/**
	 * Draw console login into the view
	 */
	public void drawConsole() {
		papplet.translate(0, papplet.width - papplet.height);
		papplet.fill(1);
		papplet.textSize(20);
		papplet.textAlign(PApplet.LEFT);
		papplet.text("Path: " + path.toString(), 0, 0);
		papplet.text("Explored nodes: " + totalNodes, 0, 40);
		papplet.text("Total exploration time: " + totalTime + " milliseconds", 0, 80);
		if (totalNodes != 0) {
			papplet.text("Medium exploration time: " + totalTime / totalNodes + " milliseconds", 0, 120);
		} else {
			papplet.text("Medium exploration time: " + 0 + " milliseconds", 0, 120);
		}
	}

	/**
	 * Allows nodes movement over the view
	 */
	public void moveNodes() {
		boolean cond = true;
		if (papplet.mousePressed && papplet.mouseButton == PApplet.LEFT) {
			for (int i = 0; i < matrix.length && cond; i++) {
				if (Math.abs(papplet.mouseX - nodes.get(i).x) < Node.RADIUS
						&& Math.abs(papplet.mouseY - nodes.get(i).y) < Node.RADIUS) {
					int bound = i;
					for (int j = 0; j < matrix.length && cond; j++) {
						if (i != j && checkCollision(nodes.get(i).x, nodes.get(i).y, nodes.get(j).x, nodes.get(j).y)) {
							bound = j;
							cond = false;
						}
					}
					Node collider = nodes.get(i);
					Node colision = nodes.get(bound);
					if (cond) {
						collider.x += papplet.mouseX - collider.x;
						collider.y += papplet.mouseY - collider.y;
					} else {
						float xDist = (float) Math.pow(papplet.mouseX - colision.x, 2);
						float yDist = (float) Math.pow(papplet.mouseY - colision.y, 2);
						float distance = (float) Math.sqrt(xDist + yDist);
						if (distance > 50) {
							collider.x += papplet.mouseX - collider.x;
							collider.y += papplet.mouseY - collider.y;
						}
					}
				}
			}
		}
	}

	/**
	 * Checks collision between nodes
	 * 
	 * @param x1
	 *            x axis coordinate from node 1
	 * @param y1
	 *            y axis coordinate from node 1
	 * @param x2
	 *            x axis coordinate from node 2
	 * @param y2
	 *            y axis coordinate from node 1
	 * @return true if collision exits, false otherwise
	 */
	boolean checkCollision(int x1, int y1, int x2, int y2) {
		float xDist = (float) Math.pow(x1 - x2, 2);
		float yDist = (float) Math.pow(y1 - y2, 2);
		float distance = (float) Math.sqrt(xDist + yDist);
		if (distance < Node.RADIUS * 3) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.updateSolution(arg);
		this.updateNodes(arg);
		this.updateEdges(arg);
		this.updateMatrix(arg);

	}

	/**
	 * Update the view with the new solution
	 * 
	 * @param arg
	 *            solution represented into the view
	 */
	private void updateSolution(Object arg) {
		int[] bestSol = null;
		try {
			bestSol = (int[]) arg;
		} catch (Exception exception) {
			return;
		}
		for (Edge edge : edges) {
			edge.setMarked(false);
		}
		this.path = new StringBuilder("");
		for (int i = 1; i < bestSol.length; i++) {
			this.path.append(bestSol[i] + " ");
			for (Edge edge : edges) {
				if (edge.getNodeA().id == bestSol[i] && edge.getNodeB().id == bestSol[(i + 1) % bestSol.length]) {
					edge.setMarked(true);
				}
				if (edge.getNodeB().id == bestSol[i] && edge.getNodeA().id == bestSol[(i + 1) % bestSol.length]) {
					edge.setMarked(true);
				}
			}
		}
		for (Edge edge : edges) {
			if (edge.getNodeA().id == bestSol[1] && edge.getNodeB().id == bestSol[bestSol.length - 1]) {
				edge.setMarked(true);
				this.path.append(bestSol[1]);
			}
			if (edge.getNodeB().id == bestSol[1] && edge.getNodeA().id == bestSol[bestSol.length - 1]) {
				edge.setMarked(true);
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update nodes representation
	 * 
	 * @param arg
	 *            new nodes
	 */
	private void updateNodes(Object arg) {
		Graph tempGraph;
		try {
			tempGraph = (Graph) arg;
			this.nodes = tempGraph.getNodes();
		} catch (Exception e) {

		}
	}

	/**
	 * Update edges representation
	 * 
	 * @param arg
	 *            new edges
	 */
	private void updateEdges(Object arg) {
		Graph tempGraph;
		try {
			tempGraph = (Graph) arg;
			this.edges = tempGraph.getEdges();
		} catch (Exception e) {

		}
	}

	/**
	 * Update matrix representation
	 * 
	 * @param arg
	 *            new matrix
	 */
	private void updateMatrix(Object arg) {
		Graph tempGraph;
		try {
			tempGraph = (Graph) arg;

			this.matrix = tempGraph.getMatrix();
		} catch (Exception e) {

		}
	}

	/**
	 * Set total nodes obtained during the algorithm execution
	 * 
	 * @param totalNodes
	 *            total nodes of the execution
	 */
	public void setTotalNodes(int totalNodes) {
		this.totalNodes = totalNodes;
	}

	/**
	 * Set total time obtained during the algorithm execution
	 * 
	 * @param totalTime
	 *            total time of the execution
	 */
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * Get the object representation of the graph
	 * @return graph representation
	 */
	public PApplet getPapplet() {
		return papplet;
	}
}
