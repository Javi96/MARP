package fibonacciHeap.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fibonacciHeap.controller.Controller;

/**
 * This class defines the visualization of Fibonacci Heap
 * 
 * @author Javier Cortés Tejada
 *
 */
public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Node key input
	 */
	private JTextField inputNode;
	
	/**
	 * Key value input
	 */
	private JTextField inputKey;
	
	/**
	 * Add a node to the heap
	 */
	private Button addNode;
	
	/**
	 * Remove minimum from the heap
	 */
	private Button removeMinNode;
	
	/**
	 * Decrease a node key by other given
	 */
	private Button decreaseKeyNode;
	
	/**
	 * Reset the heap
	 */
	private Button clear;

	/**
	 * Title of the view
	 */
	private JTextArea title;
	
	/**
	 * Status information
	 */
	private JTextArea consoleLog;
	
	/**
	 * Heap visual representation
	 */
	private JTextArea heapVisualization;
	
	/**
	 * Node information representation
	 */
	private JTextArea nodeInformation;

	/**
	 * Heap operations
	 */
	private JPanel commands;

	/**
	 * Main visual component container
	 */
	private JPanel mainContainer;

	/**
	 * Application controller
	 */
	private Controller controller;

	/**
	 * Public constructor
	 */
	public View() {
		this.initGUI();
	}

	private void initGUI() {
		
		/*
		 * Frame configuration
		 */
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setExtendedState(MAXIMIZED_BOTH);

		/*
		 * Main container configuration
		 */
		this.mainContainer = new JPanel();
		this.mainContainer.setLayout(new BoxLayout(this.mainContainer, BoxLayout.Y_AXIS));

		/*
		 * Title
		 */
		this.title = new JTextArea();
		this.generateTextPane(title, "Fibonacci Heap", 35, Color.BLACK);
		this.title.setPreferredSize(new Dimension(50, 50));

		/*
		 * Buttons
		 */
		this.addNode = new Button("Insert");
		addNode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				disableComponents();
				try {
					controller.addNode(Integer.parseInt(inputNode.getText()));
					consoleLog.append("\nInserted node with key = " + Integer.parseInt(inputNode.getText()));
				} catch (Exception exception) {
					consoleLog.append("\nInvalid input for Insert");
				}
				enableComponents();
				resetInput();
				repaintComponents();
			}
		});
		this.removeMinNode = new Button("Remove min");
		removeMinNode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				disableComponents();
				try {
					controller.removeMinNode();
					consoleLog.append("\nRemoved minimun node");
				} catch (Exception exception) {
					consoleLog.append("\nEmpty heap");
				}
				enableComponents();
				resetInput();
				repaintComponents();
			}
		});
		this.decreaseKeyNode = new Button("Decrease key");
		decreaseKeyNode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				disableComponents();
				try {
					controller.decreseKeyNode(Integer.parseInt(inputNode.getText()),
							Integer.parseInt(inputKey.getText()));
					consoleLog.append("\nDecreased node " + Integer.parseInt(inputNode.getText()) + " to "
							+ Integer.parseInt(inputKey.getText()));
				} catch (Exception exception) {
					consoleLog.append("\nInvalid input for Decrease key");

				}
				enableComponents();
				resetInput();
				repaintComponents();
			}
		});
		this.clear = new Button("Clear");
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				disableComponents();
				controller.clear();
				enableComponents();
				resetInput();
				consoleLog.setText("Console log:");
				repaintComponents();
			}
		});
		
		/*
		 * Input fields
		 */
		this.inputKey = new JTextField("");
		this.inputKey.setFont(new Font("Monospaced", Font.PLAIN, 12));
		this.inputKey.setPreferredSize(new Dimension(50, 25));

		this.inputNode = new JTextField("");
		this.inputNode.setFont(new Font("Monospaced", Font.PLAIN, 12));
		this.inputNode.setPreferredSize(new Dimension(50, 25));

		/*
		 * Auxiliary containers
		 */
		JPanel verticalContainer = new JPanel();
		verticalContainer.setVisible(true);
		verticalContainer.setLayout(new BoxLayout(verticalContainer, BoxLayout.Y_AXIS));

		this.heapVisualization = new JTextArea();
		this.generateTextPane(heapVisualization, "", 20, Color.BLACK);
		this.heapVisualization.setPreferredSize(new Dimension(1000, 1000));

		JScrollPane heapVisualizationScroll = new JScrollPane(heapVisualization);
		verticalContainer.add(heapVisualizationScroll);

		this.nodeInformation = new JTextArea();
		this.generateTextPane(nodeInformation, "", 20, Color.BLACK);
		this.nodeInformation.setPreferredSize(new Dimension(1000, 1000));

		JScrollPane nodeInformationScroll = new JScrollPane(nodeInformation);
		verticalContainer.add(nodeInformationScroll);

		JPanel horizontalContainer = new JPanel();
		horizontalContainer.setVisible(true);

		horizontalContainer.setLayout(new BoxLayout(horizontalContainer, BoxLayout.X_AXIS));

		this.consoleLog = new JTextArea();
		this.generateTextPane(consoleLog, "Console log:", 20, Color.BLACK);

		JScrollPane consoleLogScroll = new JScrollPane(consoleLog);
		horizontalContainer.add(consoleLogScroll);
		horizontalContainer.add(verticalContainer);

		/*
		 * Commands panel
		 */
		this.commands = new JPanel();
		this.commands.setLayout(new FlowLayout());
		this.commands.setPreferredSize(new Dimension(50, 50));
		this.commands.add(this.addNode);
		this.commands.add(this.inputNode);
		this.commands.add(this.removeMinNode);
		this.commands.add(this.decreaseKeyNode);
		this.commands.add(this.inputKey);
		this.commands.add(this.clear);
		this.mainContainer.add(title);
		this.mainContainer.add(commands);
		this.mainContainer.add(horizontalContainer);
		this.add(mainContainer);
	}

	/**
	 * Enable component after operations execution
	 */
	private void enableComponents() {
		addNode.setEnabled(true);
		removeMinNode.setEnabled(true);
		decreaseKeyNode.setEnabled(true);
		clear.setEnabled(true);
	}

	/**
	 * Disable component while operations execution
	 */
	private void disableComponents() {
		addNode.setEnabled(false);
		removeMinNode.setEnabled(false);
		decreaseKeyNode.setEnabled(false);
		clear.setEnabled(false);
	}

	/**
	 * Reset input field value
	 */
	private void resetInput() {
		this.inputKey.setText("");
		this.inputNode.setText("");
	}

	/**
	 * Configured a JTextArea component
	 * @param component component to be configured
	 * @param text component text
	 * @param fontSize component font size
	 * @param fontColor component font color
	 * @return modified component
	 */
	private JTextArea generateTextPane(JTextArea component, String text, int fontSize, Color fontColor) {
		component.setText(text);
		component.setBorder(null);
		component.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
		component.setForeground(fontColor);
		component.setOpaque(false);
		component.setEditable(false);
		return component;
	}

	/**
	 * Repaint application logs
	 */
	private void repaintComponents() {
		this.heapVisualization.setText(this.controller.getHeapInfo() + "\n");
		this.nodeInformation.setText(this.controller.getNodesInfo() + "\n");
		this.heapVisualization.repaint();
		this.nodeInformation.repaint();
	}

	/*
	 * Establish view controller
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
}
