package fibonacciHeap.model.algorithms;

import java.util.HashMap;
import java.util.Stack;

/**
 * Clase que implementa la estructura de datos del monticulo de Fibonacci
 * 
 * @author Javier Cortés Tejada
 *
 */
public class FibonacciHeap {

	/**
	 * Referencia del nodo con la menor clave del monticulo
	 */
	private FibonacciNode min;

	/**
	 * Número de elementos del monticulo
	 */
	private int cardinal;

	/**
	 * Decrece la clave de un nodo dada su referencia y su nuevo valor.
	 * 
	 * <p>
	 * Coste en O(1)
	 * </p>
	 * 
	 * @param node
	 *            nodo cuya clave va a ser decrecida
	 * @param key
	 *            nueva clave para el nodo node
	 * 
	 * @exception IllegalArgumentException
	 *                Lanzada en caso de que la nueva clave sea mayor que la
	 *                actual.
	 */
	public FibonacciNode decreaseKey(FibonacciNode node, double key) throws IllegalArgumentException {
		/*
		 * Si la clave introducida es mayor que la actual lanzamos un error
		 */

		if (key >= node.key) {
			throw new IllegalArgumentException("New key value is higher that current");
		}
		node.key = (int) key;
		FibonacciNode parent = node.parentNode;
		/*
		 * Si node tiene padre y su clave es menor que la de su padre llamamos
		 * lo desvinculamos de su padre, llamamos a cascadingCut por si fuera
		 * necesario hacer alguna desvinculación más (si el padre ya estaba
		 * mascado por ejemplo) y actualizamos el mínimo
		 */
		if ((parent != null) && (node.key < parent.key)) {
			cut(node, parent);
			cascadingCut(parent);
		}
		if (node.key < this.min.key) {
			this.min = node;
		}
		return node;
	}

	/**
	 * Eliminar todos los elementos del monticulo
	 */
	public void clear() {
		this.min = null;
		this.cardinal = 0;
	}

	public FibonacciNode getMin() {
		return this.min;
	}

	/**
	 * Comrueba si el montículo está vacío
	 * 
	 * <p>
	 * Coste en O(1)
	 * </p>
	 *
	 * @return true si el onticulo está vacío, false en cualquier otro caso
	 */
	public boolean isEmpty() {
		return this.min == null;
	}

	/**
	 * Inserta un nuevo nodo
	 * 
	 * <p>
	 * Coste en O(1)
	 * </p>
	 * 
	 * @param node
	 *            nodo a insertar en el montículo
	 */
	public void insert(FibonacciNode node) {
		/*
		 * Si el montículo está vacío node pasa a ser el mínimo, en otro caso se
		 * añade a la lista de raices por la derecha del mínimo e incrementamos
		 * el número de elementos del montículo
		 */
		if (this.min != null) {
			node.leftNode = this.min;
			node.rightNode = this.min.rightNode;
			this.min.rightNode = node;
			node.rightNode.leftNode = node;

			if (node.key < this.min.key) {
				this.min = node;
			}
		} else {
			this.min = node;
		}

		cardinal++;
	}

	/**
	 * Elimina el nodo con menor clave del montículo. Necesita consolidar el
	 * montículo
	 * 
	 * <p>
	 * Coste amortizado en O(log n)
	 * </p>
	 * 
	 * @return nodo con la clave máspequeña
	 */
	public FibonacciNode removeMin() {
		FibonacciNode tempMin = this.min;
		if (tempMin != null) {
			int minDegree = tempMin.degree;
			FibonacciNode minChildNode = tempMin.childNode;
			FibonacciNode tempRight;
			/*
			 * Mientras el mínimo tenga hijos, aislamos uno de ellos, lo
			 * añadimos a la lista de raices, justo a la derecha del mínimo y
			 * reasignamos los punteros de sus hermanos
			 */
			while (minDegree > 0) {
				tempRight = minChildNode.rightNode;
				minChildNode.leftNode.rightNode = minChildNode.rightNode;
				minChildNode.rightNode.leftNode = minChildNode.leftNode;
				minChildNode.leftNode = this.min;
				minChildNode.rightNode = this.min.rightNode;
				this.min.rightNode = minChildNode;
				minChildNode.rightNode.leftNode = minChildNode;
				minChildNode.parentNode = null;
				minChildNode = tempRight;
				minDegree--;
			}
			/*
			 * Una vez hayamos añadido todos los hijos del mínimo a la lista de
			 * raices, aislamos el minimo y lo ponemos a null. Por último
			 * llamanos a consolidate para restaurar el invariante
			 */
			tempMin.leftNode.rightNode = tempMin.rightNode;
			tempMin.rightNode.leftNode = tempMin.leftNode;
			if (tempMin == tempMin.rightNode) {
				this.min = null;
			} else {
				this.min = tempMin.rightNode;
				consolidate();
			}
			cardinal--;
		}

		return tempMin;
	}

	/**
	 * Representación visual del montículo
	 * 
	 * @return cadena con la representación del montículo
	 */
	public String treeVisualization() {
		if (this.min == null) {
			return "Empty Fibonacci Heap\n";
		}

		/*
		 * Creamos una pila y metemos el mínimo en ella
		 */
		Stack<Object> stack = new Stack<Object>();
		stack.push(this.min);

		StringBuffer buffer = new StringBuffer("");
		buffer.append("Fibonacci Heap console log:");

		/*
		 * Recorremos el árbol en profundidad y almacenamos su información en un
		 * buffer, para ello mientras la pila no ente vacia sacamos el primer
		 * elemento y metemos el hijo al que referencie en caso de que tenga uno
		 */
		while (!stack.empty()) {
			FibonacciNode currentNode = (FibonacciNode) stack.pop();
			buffer.append(currentNode);

			/*
			 * Si tiene al menos un hijo lo guardamos en la pila (solo nos
			 * quedamos con el hijo que tenga la referencia del padre) para
			 * obtener la información de todos ellos más adelante
			 */
			if (currentNode.childNode != null) {
				stack.push(currentNode.childNode);
			}

			FibonacciNode initialNode = currentNode;
			currentNode = currentNode.rightNode;

			/*
			 * Recorremos la lista doblemente enlazada de nodos hijos hasta
			 * llegar al de partida
			 */
			while (currentNode != initialNode) {
				buffer.append(currentNode);
				/*
				 * Al igual que antes, para cada nodo si tiene hijos lo
				 * guardamos en la pila y pasamos al siguiente
				 */
				if (currentNode.childNode != null) {
					stack.push(currentNode.childNode);
				}

				currentNode = currentNode.rightNode;
			}
		}
		buffer.append("\n");
		return buffer.toString();
	}

	/**
	 * Crea una cadena con la información de todos los nodos del montículo
	 * 
	 * @return
	 */
	public String nodeVisualization() {
		if (this.min == null) {
			return "Empty Fibonacci Heap\n";
		}

		Stack<Object> stack = new Stack<Object>();
		FibonacciNode nod = this.min.rightNode;
		HashMap<FibonacciNode, Integer> hasMap = new HashMap<FibonacciNode, Integer>();

		/*
		 * Metemos en la pila todos los nodos raiz, y también en el hash map
		 * junto con su nivel de profundidad dentro de la estructura
		 */
		while (nod != this.min) {
			stack.push(nod);
			hasMap.put(nod, 0);
			nod = nod.rightNode;
		}
		stack.push(this.min);
		hasMap.put(this.min, 0);

		StringBuffer buffer = new StringBuffer("");
		buffer.append("Fibonacci Heap console log:\n");

		/*
		 * Mientras la pila no se vacie procesamos todos los hijos de cada nodo
		 * sacado, obteniendo su información
		 */
		while (!stack.empty()) {
			FibonacciNode starNode = (FibonacciNode) stack.pop();
			if (hasMap.get(starNode) != 0)
				buffer.append(getIdentation(hasMap.get(starNode)));
			buffer.append(starNode.key + "\n");
			FibonacciNode initialNode = starNode;
			if (starNode.childNode != null) {
				initialNode = starNode.childNode.rightNode;
				while (initialNode != starNode.childNode) {
					stack.push(initialNode);

					hasMap.put(initialNode, hasMap.get(starNode) + 1);

					initialNode = initialNode.rightNode;
				}
				stack.push(starNode.childNode);

				hasMap.put(starNode.childNode, hasMap.get(starNode) + 1);

			}
			hasMap.remove(starNode);
		}
		return buffer.toString();
	}

	/**
	 * Devuelve una cadena apropiada en función del nivel de identación del nodo
	 * 
	 * @param i
	 *            nivel del nodo
	 * @return cadena con su representación
	 */
	private String getIdentation(Integer i) {
		StringBuffer buff = new StringBuffer("|");
		for (int x = 0; x <= i - 2; x++) {
			buff.append("   |");
		}
		buff.append("---");

		return buff.toString();
	}

	/**
	 * 
	 * Desvincula sucesivamente nodos hijos de sus nodos padre
	 * 
	 * <p>
	 * Coste en O(1)
	 * </p>
	 * 
	 * @param child
	 *            nodo a desvincular
	 */
	private void cascadingCut(FibonacciNode child) {
		FibonacciNode parent = child.parentNode;
		/*
		 * Si parent está a null y el hijo no está marcado lo marcamos, y en
		 * caso de que el hijo no este marcado llamamos a cut y cascadingCut
		 */
		if (parent != null) {
			if (!child.mark) {
				child.mark = true;
			} else {
				cut(child, parent);
				cascadingCut(parent);
			}
		}
	}

	/**
	 * 
	 * Consolida los árboles del montículo uniendo los de igual grado hasta que
	 * no haya ninguno con el mismo grado en la lista de raices
	 * 
	 * <p>
	 * Coste amortizado en O(log n)
	 * </p>
	 * 
	 */
	protected void consolidate() {

		// Creamos el array de grados y la inicializamos a null
		int size = cardinal;
		FibonacciNode[] nodeArray = new FibonacciNode[size];
		for (int i = 0; i < size; i++) {
			nodeArray[i] = null;
		}

		// Calculamos el número de nodos que tenemos en la raíz del montículo
		int rootsNode = 0;
		FibonacciNode min = this.min;
		if (min != null) {
			rootsNode++;
			min = min.rightNode;
			while (min != this.min) {
				rootsNode++;
				min = min.rightNode;
			}
		}

		/*
		 * En este bucle procesamos por completo todos los nodos de la raíz con
		 * el fin de aplicar la operación de linkado para así tener nodos de
		 * diferente grado
		 */
		while (rootsNode > 0) {

			/*
			 * Partimos del mínimo, para ello tomanos su grado y el nodo a su
			 * derecha
			 */
			int tempDegree = min.degree;
			FibonacciNode nextNode = min.rightNode;

			/*
			 * Entramos en el bucle en caso de que haya otro nodo con el mismo
			 * grado que el actual
			 */
			while (nodeArray[tempDegree] != null) {

				/*
				 * En caso de que haya un nodo con el mismo grado, hacemos que
				 * el de mayor clave pase a ser hijo del de menor clave
				 */
				FibonacciNode tempNode = nodeArray[tempDegree];

				// Si tenemos un nuevo mínimo actualizamos su valor
				if (min.key > tempNode.key) {
					FibonacciNode auxNode = tempNode;
					tempNode = min;
					min = auxNode;
				}

				/*
				 * Eliminarmos tempNode de la lista de nodos raíz y pasa a ser
				 * hijo de min
				 */
				link(tempNode, min);

				/*
				 * Al haber linkado los nodos anteriores, el grado del padre
				 * habrá aumentado en 1 luego ponemos a null la posición que
				 * ocupaba en el array de grados
				 */
				nodeArray[tempDegree] = null;
				tempDegree++;
			}

			/*
			 * Guardamos el nodo actualizado en el array de grados por si
			 * posteriormente nos encontramos con otro nodo con el mismo grado.
			 */
			nodeArray[tempDegree] = min;

			/*
			 * Pasamos al siguiente nodo y reducimos en 1 el contador.
			 */
			min = nextNode;
			rootsNode--;
		}

		/*
		 * Una vez hemos procesado todos los nodos de la raíz podemos poner el
		 * mínimo a null para actualizar su valor en el siguiente bucle, donde
		 * reconstruiremos la lista de nodos de la raíz (poner a null el mínimo
		 * implica perder la referencia a la lista de raices, aunque no importa
		 * pues construiremos la nueva en base al array de grados).
		 */

		this.min = null;

		for (int i = 0; i < size; i++) {
			/*
			 * Para cada posición del array de grados añadimos un nuevo elemento
			 * a la lista de raices y actualizamos el mínimo
			 */
			if (nodeArray[i] != null) {
				if (this.min != null) {
					/*
					 * Tomamos la posición i-esima ocupada del array de grados,
					 * eliminamos las referencias hecia el elemento previo y el
					 * siguiente en la lista
					 */
					nodeArray[i].leftNode.rightNode = nodeArray[i].rightNode;
					nodeArray[i].rightNode.leftNode = nodeArray[i].leftNode;
					/*
					 * Colocamos el nuevo elemento a la derecha del mínimo, y
					 * reasignamos los punteros del mínimo, el elemento que
					 * había antes a su derecha y el nuevo elemento que vamos a
					 * introducir
					 */
					nodeArray[i].leftNode = this.min;
					nodeArray[i].rightNode = this.min.rightNode;
					this.min.rightNode = nodeArray[i];
					nodeArray[i].rightNode.leftNode = nodeArray[i];
					/*
					 * Actualizamos el mínimo si es necesario
					 */
					if (nodeArray[i].key < this.min.key) {
						this.min = nodeArray[i];
					}
				} else {
					this.min = nodeArray[i];
				}
			}
		}
	}

	/**
	 * Desvincula un nodo hijo de su padre
	 * 
	 * <p>
	 * Coste en O(1)
	 * </p>
	 * 
	 * @param child
	 *            nodo hijo
	 * @param parent
	 *            nodo padre
	 */
	protected void cut(FibonacciNode child, FibonacciNode parent) {
		/*
		 * Aíslamos el hijo respecto de sus hermanos y decrecemos el grado del
		 * padre en 1
		 */
		child.leftNode.rightNode = child.rightNode;
		child.rightNode.leftNode = child.leftNode;
		parent.degree--;
		/*
		 * Reasignamos el puntero del padre a otro de sus hijo, y en caso de que
		 * no tenga más lo ponemos a null
		 */
		if (parent.childNode == child) {
			parent.childNode = child.rightNode;
		}
		if (parent.degree == 0) {
			parent.childNode = null;
		}
		/*
		 * Añadimos el nodo hijo a la lista de raices y reasignamos sus
		 * referencias
		 */
		child.leftNode = this.min;
		child.rightNode = this.min.rightNode;
		this.min.rightNode = child;
		child.rightNode.leftNode = child;
		child.parentNode = null;
		child.mark = false;
	}

	/**
	 * Hace a child hijo de parent
	 * 
	 * <p>
	 * Coste en O(1)
	 * </p>
	 * 
	 * @param child
	 *            nuevo nodo hijo
	 * @param parent
	 *            nodo padre
	 */
	protected void link(FibonacciNode child, FibonacciNode parent) {
		/*
		 * Desvinculamos child de sus hermanos y lo hacemos hijo de parent
		 */
		child.leftNode.rightNode = child.rightNode;
		child.rightNode.leftNode = child.leftNode;
		child.parentNode = parent;
		/*
		 * Si el padre no tenías hijos hacemos que child sea su nuevo hijo, en
		 * otro caso lo añadimos a la lista de hijos e incrementamos el grado
		 * del padre
		 */
		if (parent.childNode == null) {
			parent.childNode = child;
			child.rightNode = child;
			child.leftNode = child;
		} else {
			child.leftNode = parent.childNode;
			child.rightNode = parent.childNode.rightNode;
			parent.childNode.rightNode = child;
			child.rightNode.leftNode = child;
		}
		parent.degree++;
		child.mark = false;
	}

	/**
	 * Clase que define la estructura de los nodos usados en la implementación
	 * del monticulo de Fibonacci
	 * 
	 * @author Javier Cortés Tejada
	 *
	 */
	public static class FibonacciNode {

		/**
		 * Nodo referenciado de la lista de nodos hijos
		 */
		FibonacciNode childNode;

		/**
		 * Nodo hermano situado a la izquierda
		 */
		FibonacciNode leftNode;

		/**
		 * Nodo padre
		 */
		FibonacciNode parentNode;

		/**
		 * Nodo hermano situado a la derecha
		 */
		FibonacciNode rightNode;

		/**
		 * Indica si se ha perdido un hijo (true) o no (false)
		 */
		boolean mark;

		/**
		 * Valor del nodo
		 */
		int key;

		/**
		 * Número de hijos (solo se tienen en cuanta la lista doblemente
		 * enlazada que contiene el nodo hijo que se está referenciando)
		 */
		int degree;

		/**
		 * Constructor por defecto que devuelve un nodo con el valor key e
		 * inicializa sus punteros laterales a sí mismo, es decir, forma una
		 * lista doblemente enlazada de un único elemento
		 * 
		 * @param key
		 *            valor del nodo creado
		 */
		public FibonacciNode(int key) {
			this.rightNode = this;
			this.leftNode = this;
			this.key = key;
		}

		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\nNode = [");
			buffer.append("parent =  " + getKeyChain(parentNode));
			buffer.append(", key = " + Integer.toString(key));
			buffer.append(", degree = " + Integer.toString(degree));
			buffer.append(", right = " + getKeyChain(rightNode));
			buffer.append(", left = " + getKeyChain(leftNode));
			buffer.append(", child = " + getKeyChain(childNode));
			buffer.append(", mark = " + Boolean.toString(mark));
			buffer.append("]");
			return buffer.toString();

		}

		/**
		 * Devuelve un objeto String con el valor de la clave del nodo. Si el
		 * nodo está a null, devuelve ---
		 * 
		 * <p>
		 * Coste en O(1)
		 * </p>
		 * 
		 * @param node
		 *            nodo del que pedimos la clave
		 * @return valor de la clave
		 */
		private String getKeyChain(FibonacciNode node) {
			if (node != null) {
				return Integer.toString(node.key);
			} else {
				return "---";
			}
		}

		public int getKey() {
			return this.key;
		}
	}
}