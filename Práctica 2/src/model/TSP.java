package model;

import java.util.Observable;
import java.util.PriorityQueue;

import model.graph.Edge;
import model.graph.Graph;
import model.graph.Node;

/**
 * Class that represent the TSP algorithm
 * 
 * @author Javi
 *
 */
public class TSP extends Observable {

	/**
	 * Total cost of the execution
	 */
	private int totalCost;

	/**
	 * Number of expanded nodes
	 */
	private int expandedNodes;

	/**
	 * Graph used during the execution
	 */
	private Graph graph;

	/**
	 * Public constructor
	 */
	public TSP() {
		this.graph = new Graph();
		this.totalCost = 0;
		this.expandedNodes = 0;
	}

	/**
	 * Algoritmo que resuelve el problem del viajante
	 * 
	 * @param path
	 *            ruta del fichero a ser empleado
	 * @param estimation
	 *            tipo de estimacion elejida por el usuario
	 * @throws InterruptedException
	 */
	public void TSPAlgorithm(String path, String estimation) throws InterruptedException {

		// cargamos la information desde el fichero proporcionado por path y
		// notificamos a los objetos observadores para que realicen los cambios
		// oportuno en la vista
		this.graph.run(path);
		setChanged();
		this.notifyObservers(graph);
		Thread.sleep(5000);

		// instanciamos las variables que usaremos durante la ejecucion y
		// buscamos la arista con menor valor de todo el grafo
		double ini, fin;
		int[] bestSol = new int[graph.getNodes().size()];
		PriorityQueue<Node> prioQueue = new PriorityQueue<>();
		Node x = null, y = null;
		double minG = Double.MAX_VALUE;
		for (Edge egde : graph.getEdges()) {
			if (egde.getDistance() < minG) {
				minG = egde.getDistance();
				y = egde.getNodeA();
			}
		}

		// actualizamos el primer nodo (fijado por nosotros para comenzar la
		// ejecucion) con los valores oportumos, para ello lo marcamos como
		// usado, lo añadimos al vector solucion y actualizamos los costes de
		// dicho nodo
		y.setK(1);
		y.setSol(1, 1);
		y.setUsed(1, true);
		y.setCost(0);
		y.setEstCost(minG * graph.getNodes().size());
		prioQueue.add(y);
		double bestCost = Double.POSITIVE_INFINITY;

		while (!prioQueue.isEmpty() && prioQueue.peek().getEstCost() < bestCost) {

			// popeamos el nodo que se encuentre en cabeza de la cola y
			// notificamos de nuevo a los observadores. Ademas actualizamos el
			// numero de nodos expandidos e iniciamos la medicion de tiempos
			y = prioQueue.poll();
			setChanged();
			this.notifyObservers(y.getSol());
			this.expandedNodes++;
			ini = System.currentTimeMillis();

			// tomamos un nuevo nodo, el cual construimos en base a la
			// informacion que obtuvimos del que extraimos anteriormente en la
			// cola
			x = new Node(graph.getNodes().size(), 1, 0, 0);
			x.setK(y.getK() + 1);
			x.setSol(y.getSol());
			x.setUsed(y.getUsed());
			int last = x.getSol(x.getK() - 1);

			// entramos a un bucle donde miramos si algun nodo de los disponible
			// es valido para continuar formando la solucion
			for (int vertex = 2; vertex < graph.getNodes().size() + 1; vertex++) {

				// si el nodo es valido, actualizamos su solucion, su coste y
				// ademas lo marcamos como usado
				if (isValid(x, last, vertex)) {
					x.setSol(vertex, x.getK());
					x.setUsed(vertex, true);
					x.setCost(y.getCost() + findEdgeValue(last, vertex));

					// si es solucion buscamos una arista que conecte dicho nodo
					// con el primero del vector solucion y comprobamos que el
					// coste de dicha solucion mas el coste de la arista que
					// cierra el circuito no excede el mejor coste encontrado
					// hasta el momento
					if (isSolution(x)) {
						if (findEdge(x.getSol(graph.getNodes().size()), 1)
								&& x.getCost() + findEdgeValue(x.getSol(graph.getNodes().size()), 1) < bestCost) {

							// en caso que que la nueva solucion sea mejor que
							// la anterior, guardamos tanto el vector de
							// solucion como su coste
							bestSol = x.getSol();
							bestCost = x.getCost() + findEdgeValue(x.getSol(graph.getNodes().size()), 1);
						}
					} else {

						// encaso de que el nodo sea valido pero no sea solucion
						// tenemos que comprobar si nos interesa seguir
						// trabajando sobre el. Para ello, recalculamos el coste
						// estimado de dicho nodo en base a la estimacion
						// elegida por el usuario, y en caso de ser este coste
						// menor que el mejor coste obtenido hasta el momento,
						// tenemos un nodo prometedor que metemos en la cola de
						// prioridad para ser explorado mas adelante
						this.applyEstimation(estimation, x, minG);
						if (x.getEstCost() < bestCost) {
							prioQueue.add((Node) x.clone());
						}
					}

					// desmarcamos el nodo que hemos estado usando para
					// continuar explorando el resto de nodos
					x.setUsed(vertex, false);
				}
			}

			// finalizamos la medicion de tiempo y la almacenamos en un atributo
			// de clase
			fin = System.currentTimeMillis();
			totalCost += (fin - ini);
		}

		// notificamos a los observadores la solucion final para que quede
		// reflejado en la vista
		this.setChanged();
		this.notifyObservers(bestSol);
	}

	/**
	 * Determina si un nodo es solucion
	 * 
	 * @param x
	 *            nodo a comprobar
	 * @return true si la longitud de la solucion es igual al numero de nodos
	 *         disponibles, false en otro caso
	 */
	private boolean isSolution(Node x) {
		return x.getK() == graph.getNodes().size();
	}

	/**
	 * Determina si un nodo es valido
	 * 
	 * @param x
	 *            nodo a evaluar
	 * @param last
	 *            id del nodo origen de la arista
	 * @param vertex
	 *            id del nodo destino de la arista
	 * @return true si el nodo con id vertex aun no ha sido usado en la solucion
	 *         y si encuentra una arista de la forma last -> vertex, false en
	 *         otro caso
	 */
	private boolean isValid(Node x, int last, int vertex) {
		return !x.getUsed(vertex) && findEdge(last, vertex);
	}

	/**
	 * Aplica la funcion de estumacion elegida por el usuario
	 * 
	 * @param estimation
	 *            tipo de estimacion a aplicar
	 * @param x
	 *            nodo cuyo coste se ha de estimar
	 * @param minG
	 *            valor de la arista con menor coste
	 */
	public void applyEstimation(String estimation, Node x, double minG) {
		switch (estimation) {

		// en caso de haber seleccionado la estimacion ingenua, aplicamos una
		// estimacion poco costosa y simple donde el coste estimado de un nodo
		// esta determinado por el coste de la solucion parcial mas el coste del
		// resto de nodos no determinados aun por minG
		case "naive":
			x.setEstCost(x.getCost() + (graph.getNodes().size() - x.getK() + 1) * minG);
			break;

		// en caso de la estimacion avanzada, el coste estimado viene
		// determinado por el valor de la solucion parcial mas el coste del
		// resto de nodos no determinados por el minimo valor de las aristas que
		// aun se pueden asignar, es decir, en vez de usar todas las aristas
		// tomamos solo las que vamos a poder usar para construir la solucion
		// restante, luego obtenemos una estiacion mas aproximada al coste real
		// ya que en dichos valores se encuentran las distancias que formaran
		// aprte de la solucion, cosa que no ocurre con la estimacion ingenua
		case "advanced":
			this.markEdge(x.getSol());
			x.setEstCost(x.getCost() + (graph.getNodes().size() - x.getK() + 1) * findMinDistance(x.getSol()));
			this.unMarkEdge(x.getSol());
			break;
		}
	}

	/**
	 * Busca una arista determinada por dos identificadores
	 * @param last nodo origen
	 * @param vertex nodo destino
	 * @return true si la arista existe, false en otro caso
	 */
	public boolean findEdge(int last, int vertex) {
		for (Edge egde : graph.getEdges()) {
			if (egde.getNodeA().getId() == last && egde.getNodeB().getId() == vertex) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Busca el valor de una arista determinada por dos identificadores
	 * @param last nodo origen
	 * @param vertex nodo destino
	 * @return valor de la arista que conecta last con vertex
	 */
	public double findEdgeValue(int last, int vertex) {
		for (Edge egde : graph.getEdges()) {
			if (egde.getNodeA().getId() == last && egde.getNodeB().getId() == vertex) {
				return egde.getDistance();
			}
		}
		return 0;
	}

	/**
	 * Busca y devuelve la menor distancia de todas las aristas
	 * @param solution vector de solucion
	 * @return distancia minima
	 */
	public double findMinDistance(int[] solution) {
		double distance = Double.POSITIVE_INFINITY;
		for (Edge edge : graph.getEdges()) {
			if (distance > edge.getDistance() && !edge.isUsed()) {
				distance = edge.getDistance();
			}
		}
		return distance;
	}

	/**
	 * Marca las aristas que son usadas o no pueden ser usadas para determinar la estimacion
	 * @param solution solucion parcial
	 */
	public void markEdge(int[] solution) {
		for (int i = 1; i < solution.length; i++) {
			for (Edge edge : graph.getEdges()) {
				if (edge.getNodeA().getId() == solution[i]) {
					edge.setUsed(true);
				}
			}
		}
	}

	/**
	 * Desmarca las aristas usadas
	 * @param solution solucion parcial
	 */
	public void unMarkEdge(int[] solution) {
		for (int i = 1; i < solution.length; i++) {
			for (Edge edge : graph.getEdges()) {
				if (edge.getNodeA().getId() == solution[i]) {
					edge.setUsed(false);
				}
			}
		}
	}

	/**
	 * Get the application graph
	 * @return application graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * Get the total cost of the execution
	 * @return execution cost
	 */
	public int getTotalCost() {
		return totalCost;
	}

	/**
	 * Set the total coste of the execution
	 * @param totalCost total cost of the execution
	 */
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * Get the number of expanded nodes
	 * @return expanded nodes
	 */
	public int getExpandedNodes() {
		return expandedNodes;
	}

	/**
	 * Set the number of expanded nodes
	 * @param expandedNodes number of expanded nodes
	 */
	public void setExpandedNodes(int expandedNodes) {
		this.expandedNodes = expandedNodes;
	}
}
