package controller;

import java.util.Observable;
import java.util.Observer;

import model.TSP;
import processing.core.PApplet;
import view.SelectorFrame;
import view.View;

/**
 * Controller class of the MVC that organize the communication between model and
 * view.
 * 
 * @author Javi
 *
 */
public class Controller implements Observer {

	/**
	 * Allows the user to configure each execution
	 */
	private SelectorFrame congif;

	/**
	 * Application data
	 */
	private TSP model;

	/**
	 * Application view
	 */
	private View view;

	/**
	 * Type of estimation for the execution
	 */
	private String estimation = "";

	/**
	 * Path file of the adjacency matrix
	 */
	private String file = "";

	/**
	 * Public constructor of the controller
	 * 
	 * @param view
	 *            view of the application
	 * @param tsp
	 *            model of the application
	 * @param configLaunch
	 *            configuration panel of the application
	 */
	public Controller(View view, TSP tsp, SelectorFrame configLaunch) {
		this.view = view;
		this.model = tsp;
		this.congif = configLaunch;
	}

	/**
	 * Launch the execution of the algorithm
	 * 
	 * @throws InterruptedException
	 */
	private void run() throws InterruptedException {
		PApplet.runSketch(new String[] { "--display=1", "--location=0,0",
				"--sketch-path=" + getView().getPapplet().getClass(), "" }, getView().getPapplet());
		this.model.TSPAlgorithm(this.file, this.estimation);
		getView().setTotalTime(this.model.getTotalCost());
		getView().setTotalNodes(this.model.getExpandedNodes());
	}

	/**
	 * Get the model of the application
	 * 
	 * @return model of the application
	 */
	public TSP getModel() {
		return model;
	}

	/**
	 * Get the view of the application
	 * 
	 * @return view of the application
	 */
	public View getView() {
		return view;
	}

	/**
	 * Get the configuration frame of the application
	 * 
	 * @return configuration frame of the application
	 */
	public SelectorFrame getConfig() {
		return congif;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String[] chain = (String[]) arg1;
		this.estimation = chain[0];
		this.file = chain[1];
		congif.getFrame().dispose();
		try {
			Thread.sleep(1000);
			this.run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
