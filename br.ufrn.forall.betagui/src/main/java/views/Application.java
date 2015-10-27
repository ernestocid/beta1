package views;

import general.InputSpaceCoverageCriteria;
import general.LogicalCoverageCriteria;
import general.TestingStrategy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import configurations.Configurations;
import de.prob.Main;
import de.prob.scripting.Api;
import parser.Machine;
import actions.DisplayInfoAction;
import actions.DisplaySettingsAction;
import actions.GenerateCBCTestMachineAction;
import actions.GenerateTestsAction;
import actions.LoadMachineAction;
import actions.WindowForTestDataConcretizationAction;

public class Application {

	private Machine machine;
	private JFrame mainFrame;
	private JFileChooser fileChooser;
	private final Action loadMachineAction = new LoadMachineAction(this);
	private final Action concretizeTestDataAction = new WindowForTestDataConcretizationAction(this);
	private JList<String> operationList;
	private JTable inputSpaceTable;
	private JTable characteristicsTable;
	private JTable combinations;
	private JPanel machineInfo;
	private JComboBox<TestingStrategy> testingStrategyComboBox;
	private JComboBox coverageCriteriaComboBox;
	private JLabel operationsListLabel;
	private final Action displayAction = new DisplayInfoAction(this);
	private final Action generateTestsAction = new GenerateTestsAction(this);
	private final Action generateCBCTestMachineAction = new GenerateCBCTestMachineAction(this);
	private Set<String> combinationsValues = new HashSet<String>();
	private int selectedOperation = -1;
	private JPanel controlsPanel;
	private JLabel testingStrategyLabel;
	private JLabel coverageCriterionLabel;
	private JPanel chooseOperationPanel;
	private JScrollPane operationsScrollPane;
	private JPanel actionsPanel;
	private JButton generateCBCTestMachineButton;
	private JButton generateTestsButton;
	private JMenuBar menuBar;
	private JMenu betaMenu;
	private JMenuItem betaMenuLoadMachineItem;
	private JMenuItem betaMenuConcretizeTestDataItem;
	private JMenuItem betaMenuSettingsItem;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Application window = new Application();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		mainFrame = new JFrame();
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("betaico.png")));
		mainFrame.setTitle("BETA");
		mainFrame.setBounds(50, 50, 350, 600);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileChooser = new JFileChooser();
		
		fileChooser.setFileFilter(new ExtensionFileFilter("B Machines", "mch"));



		/**************************************************
		 * Begin: Controls Panel
		 **************************************************/

		controlsPanel = new JPanel();
		controlsPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		controlsPanel.setLayout(new GridLayout(0, 1));
		controlsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainFrame.getContentPane().add(controlsPanel, BorderLayout.NORTH);

		// Adding Testing Strategy ComboBox to controls panel

		testingStrategyLabel = new JLabel("Choose a Testing Strategy:");
		testingStrategyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		testingStrategyLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		controlsPanel.add(testingStrategyLabel);

		testingStrategyComboBox = new JComboBox<TestingStrategy>();
		testingStrategyComboBox.setModel(new DefaultComboBoxModel<TestingStrategy>(TestingStrategy.values()));

		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();

				if(state == ItemEvent.SELECTED) {
					if(itemEvent.getItem() == TestingStrategy.INPUT_SPACE_COVERAGE) {
						coverageCriteriaComboBox.setModel(new DefaultComboBoxModel(InputSpaceCoverageCriteria.values()));
					} else if (itemEvent.getItem() == TestingStrategy.LOGICAL_COVERAGE) {
						coverageCriteriaComboBox.setModel(new DefaultComboBoxModel(LogicalCoverageCriteria.values()));
					} else {
						System.out.println("Testing Strategy Unknown");
					}
				}
			}
		};

		testingStrategyComboBox.addItemListener(itemListener);
		controlsPanel.add(testingStrategyComboBox);

		// Adding Coverage Criterion ComboBox to controls panel

		coverageCriterionLabel = new JLabel("Choose a Coverage Criterion:");
		coverageCriterionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		coverageCriterionLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		controlsPanel.add(coverageCriterionLabel);

		coverageCriteriaComboBox = new JComboBox();

		if (testingStrategyComboBox.getSelectedItem() == TestingStrategy.INPUT_SPACE_COVERAGE) {
			coverageCriteriaComboBox.setModel(new DefaultComboBoxModel(InputSpaceCoverageCriteria.values()));
		} else if (testingStrategyComboBox.getSelectedItem() == TestingStrategy.LOGICAL_COVERAGE) {
			coverageCriteriaComboBox.setModel(new DefaultComboBoxModel(LogicalCoverageCriteria.values()));
		} else {
			System.out.println("Unknown Testing Strategy");
		}

		controlsPanel.add(coverageCriteriaComboBox);

		/**************************************************
		 * End: Controls Panel
		 **************************************************/



		/**************************************************
		 * Begin: Choose Operation Panel
		 **************************************************/

		chooseOperationPanel = new JPanel();
		chooseOperationPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		chooseOperationPanel.setLayout(new BorderLayout(0, 0));
		mainFrame.getContentPane().add(chooseOperationPanel, BorderLayout.CENTER);

		operationsListLabel = new JLabel("Operations (no machine loaded):");
		operationsListLabel.setBorder(new EmptyBorder(10, 10, 10, 5));
		operationsListLabel.setHorizontalAlignment(SwingConstants.LEFT);
		chooseOperationPanel.add(operationsListLabel, BorderLayout.NORTH);

		operationsScrollPane = new JScrollPane();
		operationsScrollPane.setPreferredSize(new Dimension(200, operationsScrollPane.getPreferredSize().height));
		operationsScrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		operationsListLabel.setLabelFor(operationsScrollPane);
		chooseOperationPanel.add(operationsScrollPane);

		operationList = new JList<String>();
		operationList.setValueIsAdjusting(true);
		operationList.setVisibleRowCount(1);
		operationList.setModel(new DefaultListModel<String>());
		operationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		operationList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedOperation = operationList.getSelectedIndex();
			}
		});

		operationsScrollPane.setViewportView(operationList);

		/**************************************************
		 * End: Choose Operation Panel
		 **************************************************/



		/**************************************************
		 * Begin: Actions Panel
		 **************************************************/

		actionsPanel = new JPanel();
		actionsPanel.setLayout(new FlowLayout());
		actionsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainFrame.getContentPane().add(actionsPanel, BorderLayout.SOUTH);

//		generateCBCTestMachineButton = new JButton("Generate CBC Machine");
//		generateCBCTestMachineButton.setAction(generateCBCTestMachineAction);
//		generateCBCTestMachineButton.setText("Generate CBC Machine");
//		generateCBCTestMachineButton.setHorizontalAlignment(SwingConstants.TRAILING);
//		actionsPanel.add(generateCBCTestMachineButton);

		generateTestsButton = new JButton("Generate Tests");
		generateTestsButton.setAction(generateTestsAction);
		generateTestsButton.setText("Generate Tests");
		generateTestsButton.setHorizontalAlignment(SwingConstants.TRAILING);
		actionsPanel.add(generateTestsButton);

		/**************************************************
		 * End: Actions Panel
		 **************************************************/



		/**************************************************
		 * Begin: Menu Bar
		 **************************************************/

		menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		mainFrame.setJMenuBar(menuBar);

		betaMenu = new JMenu("Options");
		betaMenu.setBackground(Color.LIGHT_GRAY);
		menuBar.add(betaMenu);

		betaMenuLoadMachineItem = new JMenuItem("Load Machine");
		betaMenuLoadMachineItem.setAction(loadMachineAction);
		betaMenu.add(betaMenuLoadMachineItem);
		
		betaMenuConcretizeTestDataItem = new JMenuItem("Concretize Test Data");
		betaMenuConcretizeTestDataItem.setAction(concretizeTestDataAction);
		betaMenu.add(betaMenuConcretizeTestDataItem);

		betaMenuSettingsItem = new JMenuItem("Settings");
		betaMenuSettingsItem.setText("Settings");
		betaMenuSettingsItem.setAction(new DisplaySettingsAction());
		betaMenu.add(betaMenuSettingsItem);

		/**************************************************
		 * End: Menu Bar
		 **************************************************/

	}



	public Machine getMachine() {
		return machine;
	}



	public void setMachine(Machine machine) {
		this.machine = machine;
	}



	public JList<String> getOperationList() {
		return operationList;
	}



	public JFrame getMainFrame() {
		return mainFrame;
	}



	public JFileChooser getFileChooser() {
		return fileChooser;
	}



	public JPanel getMachineInfo() {
		return machineInfo;
	}



	public JTable getInputSpaceTable() {
		return inputSpaceTable;
	}



	public JTable getCharacteristicsAndBlocksTable() {
		return characteristicsTable;
	}



	public JTable getCombinationsTable() {
		return combinations;
	}



	public JComboBox getTestingStrategy() {
		return testingStrategyComboBox;
	}



	public JComboBox getCoverageCriteria() {
		return coverageCriteriaComboBox;
	}



	public JLabel getOperationsListLabel() {
		return operationsListLabel;
	}



	public void setCombinations(Set<String> combinationsValues) {
		this.combinationsValues = combinationsValues;
	}



	public Set<String> getCombinations() {
		return combinationsValues;
	}



	public void setSelectedOperation(int selectedOperation) {
		this.selectedOperation = selectedOperation;
	}



	public int getSelectedOperation() {
		return selectedOperation;
	}



	public int getChosenTestingStrategy() {
		return this.testingStrategyComboBox.getSelectedIndex();
	}



	public int getChosenCoverageCriteria() {
		return this.coverageCriteriaComboBox.getSelectedIndex();
	}

}
