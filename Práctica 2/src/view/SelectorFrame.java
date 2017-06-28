package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * Selector frame used by the user to configure the application execution
 * @author Javi
 *
 */
public class SelectorFrame extends Observable {

	/**
	 * Screen width
	 */
	public final static int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width / 4;

	/**
	 * Screen heigth
	 */
	public final static int HEIGTH = Toolkit.getDefaultToolkit().getScreenSize().height / 4;

	/**
	 * Background color
	 */
	public static Color BACKGROUND_COLOR = new Color(214, 217, 223);

	/**
	 * Main application frame
	 */
	private JFrame frame;

	/**
	 * Main container
	 */
	private JPanel main;

	/**
	 * Branch selection box
	 */
	private JComboBox<String> branchOption;

	/**
	 * Display text components
	 */
	private ArrayList<JTextArea> text;

	/**
	 * Application button launcher
	 */
	private JButton run;

	/**
	 * File selector
	 */
	private JButton selectFile;

	/**
	 * Estimation branch
	 */
	private String estimation = "naive";

	/**
	 * File path
	 */
	private String file = "matrix17.txt";

	/**
	 * Public constuctor
	 */
	public SelectorFrame() {
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.initGUI();
		this.settings();
	}

	/**
	 * Basic component configuration
	 */
	private void settings() {
		this.configComboBox();
		this.configButtons();
		this.main.setBackground(BACKGROUND_COLOR);
		this.main.setSize(SelectorFrame.WIDTH, SelectorFrame.HEIGTH);

		this.main.setLayout(new BoxLayout(this.main, BoxLayout.Y_AXIS));

		this.text.add(new JTextArea());
		this.configTextArea(" Select estimation", 0);
		this.text.add(new JTextArea());
		this.configTextArea(" Select file", 1);

		this.main.add(new JSeparator());
		this.main.add(Box.createRigidArea(new Dimension(0, 10)));

		this.main.add(this.text.get(0));
		this.main.add(Box.createRigidArea(new Dimension(0, 10)));

		this.main.add(branchOption);
		this.main.add(Box.createRigidArea(new Dimension(0, 15)));
		this.main.add(new JSeparator());
		
		JPanel auxPanel = new JPanel();
		auxPanel.setLayout(new BoxLayout(auxPanel, BoxLayout.X_AXIS));
		auxPanel.add(selectFile);
		auxPanel.add(run);		this.main.add(Box.createRigidArea(new Dimension(0, 30)));

		this.main.add(auxPanel);
		this.main.add(Box.createRigidArea(new Dimension(0, 30)));

		this.frame.setTitle("TSP - Traveler Search Problem resolutor");
		this.frame.setSize(SelectorFrame.WIDTH, SelectorFrame.HEIGTH);
		this.frame.setMinimumSize(new Dimension(SelectorFrame.WIDTH, SelectorFrame.HEIGTH));
		this.frame.setBackground(BACKGROUND_COLOR);
		this.frame.add(main);
		this.frame.setVisible(true);
		this.frame.setLocationRelativeTo(null);
		this.frame.pack();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Text components configuration
	 * @param name component name
	 * @param id component id
	 */
	private void configTextArea(String name, int id) {
		this.text.get(id).setText(name);
		this.text.get(id).setFont(new Font("Arial", 1, 20));
		this.text.get(id).setBackground(BACKGROUND_COLOR);
		this.text.get(id).setEditable(false);
		this.text.get(id).setFocusable(false);
		this.text.get(id).setBorder(null);
		this.text.get(id).setOpaque(true);
	}

	/**
	 * Combo box component configuration
	 */
	private void configComboBox() {
		this.branchOption.addItem("Naive estimation");
		this.branchOption.addItem("Advanced estimation");
		this.branchOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (branchOption.getSelectedIndex() == 0) {
					estimation = "naive";
				} else {
					estimation = "advanced";

				}
			}
		});
	}

	/**
	 * Button component configuration
	 */
	private void configButtons() {
		this.selectFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileSelector = new JFileChooser("Select file");
				int result = fileSelector.showOpenDialog(fileSelector);
				if (result == JFileChooser.APPROVE_OPTION) {
					file = fileSelector.getSelectedFile().getAbsolutePath();
				} else if (result == JFileChooser.CANCEL_OPTION) {
					file = "matrix17.txt";
				}
			}
		});
		this.run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] chain = new String[3];
				chain[0] = estimation;
				chain[1] = file;
				setChanged();
				notifyObservers(chain);
			}
		});
	}

	/**
	 * Initialize GUI
	 */
	private void initGUI() {
		this.frame = new JFrame("");
		this.main = new JPanel();
		this.branchOption = new JComboBox<String>();
		this.text = new ArrayList<>();
		this.run = new JButton("Run");
		this.selectFile = new JButton("Select file");
	}

	/**
	 * Get main frame
	 * @return application frame
	 */
	public JFrame getFrame() {
		return frame;
	}
}
