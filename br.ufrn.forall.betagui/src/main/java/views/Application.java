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
import actions.GenerateCBCTestMachineAction;
import actions.GenerateTestsAction;
import actions.LoadMachineAction;

public class Application {

	private Machine machine;
	private JFrame frmBtest;
	private JFileChooser fileChooser;
	private final Action openFileAction = new LoadMachineAction(this);
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


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Application window = new Application();
					window.frmBtest.setVisible(true);
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

		frmBtest = new JFrame();
		frmBtest.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("betaico.png")));
		frmBtest.setTitle("BETA");
		frmBtest.setBounds(100, 100, 400, 600);
		frmBtest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new ExtensionFileFilter("B Machines", "mch"));



		/**************************************************
		 * Begin: Controls Panel
		 **************************************************/

		JPanel controlsPanel = new JPanel();
		controlsPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		controlsPanel.setLayout(new GridLayout(0, 1));
		controlsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmBtest.getContentPane().add(controlsPanel, BorderLayout.NORTH);

		// Adding Testing Strategy ComboBox to controls panel

		JLabel testingStrategyLabel = new JLabel("Choose a Testing Strategy:");
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

		JLabel coverageCriterionLabel = new JLabel("Choose a Coverage Criterion:");
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

		JPanel chooseOperationPanel = new JPanel();
		chooseOperationPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		chooseOperationPanel.setLayout(new BorderLayout(0, 0));
		frmBtest.getContentPane().add(chooseOperationPanel, BorderLayout.CENTER);

		operationsListLabel = new JLabel("Operations (no machine loaded):");
		operationsListLabel.setBorder(new EmptyBorder(10, 10, 10, 5));
		operationsListLabel.setHorizontalAlignment(SwingConstants.LEFT);
		chooseOperationPanel.add(operationsListLabel, BorderLayout.NORTH);

		JScrollPane operationsScrollPane = new JScrollPane();
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

		JPanel actionsPanel = new JPanel();
		actionsPanel.setLayout(new FlowLayout());
		actionsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmBtest.getContentPane().add(actionsPanel, BorderLayout.SOUTH);

		JButton generateCBCTestMachineButton = new JButton("Generate CBC Test Machine");
		generateCBCTestMachineButton.setAction(generateCBCTestMachineAction);
		generateCBCTestMachineButton.setText("Generate CBC Test Machine");
		generateCBCTestMachineButton.setHorizontalAlignment(SwingConstants.TRAILING);
		actionsPanel.add(generateCBCTestMachineButton);

		JButton generateTestsButton = new JButton("Create Test Report");
		generateTestsButton.setAction(generateTestsAction);
		generateTestsButton.setText("Generate Report");
		generateTestsButton.setHorizontalAlignment(SwingConstants.TRAILING);
		actionsPanel.add(generateTestsButton);

		/**************************************************
		 * End: Actions Panel
		 **************************************************/



		/**************************************************
		 * Begin: Menu Bar
		 **************************************************/

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		frmBtest.setJMenuBar(menuBar);

		JMenu betaMenu = new JMenu("BETA");
		betaMenu.setBackground(Color.LIGHT_GRAY);
		menuBar.add(betaMenu);

		JMenuItem betaMenuLoadMachineItem = new JMenuItem("Load Machine");
		betaMenuLoadMachineItem.setAction(openFileAction);
		betaMenu.add(betaMenuLoadMachineItem);

		JMenuItem betaMenuSettingsItem = new JMenuItem("Settings");
		betaMenuSettingsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				final JFrame configFrame = new JFrame("Settings");
				
				JPanel configPanel = new JPanel(new GridBagLayout());
				configPanel.setBackground(Color.WHITE);
				configPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
				
				GridBagConstraints c = new GridBagConstraints();
				
				JLabel probPathLabel = new JLabel("ProB installation path: ");
				final JTextField probPathField = new JTextField(Configurations.getProBPath(), 25);
				
				JLabel oracleStrategy = new JLabel("Oracle Strategy: ");
				
				final JCheckBox stateVariablesOracCB = new JCheckBox("State variables");
				stateVariablesOracCB.setSelected(Configurations.isStateVariablesActive());
				
				final JCheckBox returnVariablesOracCB = new JCheckBox("Return Variables");
				returnVariablesOracCB.setSelected(Configurations.isReturnVariablesActive());
				
				final JCheckBox invariantOKOracCB = new JCheckBox("Invariant OK");
				invariantOKOracCB.setSelected(Configurations.isInvariantOKActive());
				
				
				JLabel deleteTempFilesLabel = new JLabel("Delete Temporary Files: ");
				final JCheckBox deleteTempFilesCB = new JCheckBox("Delete Files");
				deleteTempFilesCB.setSelected(Configurations.isDeleteTempFiles());
				
				JLabel automaticOracleEvaluationLabel = new JLabel("Automatic Oracle Evaluation: ");
				final JCheckBox automaticOracleEvaluationCB = new JCheckBox("Generate Oracle Values (experimental)");
				automaticOracleEvaluationCB.setSelected(Configurations.isAutomaticOracleEvaluation());
				
				JLabel useProBApiToSolvePredicatesLabel = new JLabel("Use ProB API to solve predicates: ");
				final JCheckBox useProBApiToSolvePredicatesCB = new JCheckBox("Use ProB API (experimental)");
				useProBApiToSolvePredicatesCB.setSelected(Configurations.isUseProBApiToSolvePredicates());
				
				JLabel useKodkodLabel = new JLabel("Use Kodkod: ");
				final JCheckBox useKodkodCB = new JCheckBox("Use Kodkod (experimental)");
				useKodkodCB.setSelected(Configurations.getUseKodkod());
				
				JLabel useProbRandomEnumerationLabel = new JLabel("Use ProB's random enumeration: ");
				final JCheckBox useProbRandomEnumerationCB = new JCheckBox("Random enumeration (experimental)");
				useProbRandomEnumerationCB.setSelected(Configurations.getRandomiseEnumerationOrder());
				
				
				JLabel minintLabel = new JLabel("MININT Value: ");
				final JTextField minintField = new JTextField(String.valueOf(Configurations.getMinIntProperties()), 5);
				
				JLabel maxintLabel = new JLabel("MININT Value: ");
				final JTextField maxintField = new JTextField(String.valueOf(Configurations.getMaxIntProperties()), 5);
				
				JLabel probTimeoutLabel = new JLabel("ProB timeout: ");
				final JTextField probTimeoutField = new JTextField(String.valueOf(Configurations.getProBTimeout()), 5);
				
				
				JLabel downloadProBCliLabel = new JLabel("Download latest probcli binaries: ");
				JButton downloadProBClitButton = new JButton("Download probcli");
				downloadProBClitButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Downloading probcli");
						
						Api probApi = Main.getInjector().getInstance(Api.class);
						probApi.upgrade("latest");
						
						System.out.println("probcli download finished");
					}
					
				});
				
				
				
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						File probcli = new File(probPathField.getText() + "probcli");
						File probcliexe = new File(probPathField.getText() + "probcli.exe");

						// Checking if ProB directory is valid and contains probcli
						
						if(probcli.exists() || probcliexe.exists()) {
							System.out.println("Setting ProB path to: " + probPathField.getText());
							Configurations.setProBPath(probPathField.getText());
							configFrame.dispose();
						} else {
							JOptionPane.showMessageDialog(null, 
									"This is not a valid ProB directory.\nWe could not find \"probcli\" on this directory.",
									"Error",
									JOptionPane.ERROR_MESSAGE);
						}
						
						// Set Oracles strategies
						
						Configurations.setOracleStrategy(stateVariablesOracCB.isSelected(), 
														returnVariablesOracCB.isSelected(), 
														invariantOKOracCB.isSelected());
						
						
						// Set Delete temporary files
						
						Configurations.setDeleteTempFiles(deleteTempFilesCB.isSelected());
						
						// Set Automatic Oracle Evaluation
						
						Configurations.setAutomaticOracleEvaluation(automaticOracleEvaluationCB.isSelected());
						
						// Set Use ProBApi
						
						Configurations.setUseProBApiToSolvePredicates(useProBApiToSolvePredicatesCB.isSelected());
						
						// Set Use Kodkod
						
						Configurations.setUseKodkod(useKodkodCB.isSelected());
						
						// Set Use ProB random enumeration
						
						Configurations.setRandomiseEnumerationOrder(useProbRandomEnumerationCB.isSelected());
						
						// Set MININT and MAXINT values
						
						Configurations.setMinIntProperties(Integer.valueOf(minintField.getText()));
						Configurations.setMaxIntProperties(Integer.valueOf(maxintField.getText()));
						Configurations.setProBTimeout(Integer.valueOf(probTimeoutField.getText()));
					}
				});
				

				c.gridy = 0;
				
				configPanel.add(probPathLabel, c);
				configPanel.add(probPathField, c);

				c.gridy = 1;
				
				configPanel.add(downloadProBCliLabel, c);
				configPanel.add(downloadProBClitButton, c);
				
				c.gridy = 2;
				
				configPanel.add(oracleStrategy, c);
				configPanel.add(stateVariablesOracCB, c);
				configPanel.add(returnVariablesOracCB, c);
				configPanel.add(invariantOKOracCB, c);
				
				c.gridy = 3;
				
				configPanel.add(deleteTempFilesLabel, c);
				configPanel.add(deleteTempFilesCB, c);

				c.gridy = 4;
				
				configPanel.add(useKodkodLabel, c);
				configPanel.add(useKodkodCB, c);
				
				c.gridy = 5;
				
				configPanel.add(useProbRandomEnumerationLabel, c);
				configPanel.add(useProbRandomEnumerationCB, c);
				
				c.gridy = 6;
				
				configPanel.add(automaticOracleEvaluationLabel, c);
				configPanel.add(automaticOracleEvaluationCB, c);
				
				c.gridy = 7;
				
				configPanel.add(useProBApiToSolvePredicatesLabel, c);
				configPanel.add(useProBApiToSolvePredicatesCB, c);
				
				c.gridy = 8;
				
				configPanel.add(minintLabel, c);
				configPanel.add(minintField, c);
				
				configPanel.add(maxintLabel, c);
				configPanel.add(maxintField, c);
				
				c.gridy = 9;
				
				configPanel.add(probTimeoutLabel, c);
				configPanel.add(probTimeoutField, c);
								
				c.gridy = 10;
				
				configPanel.add(saveButton, c);
				
				configFrame.getContentPane().add(configPanel);
				configFrame.setLocationRelativeTo(null);
				configFrame.pack();
				configFrame.setVisible(true);
			}
		});

		betaMenu.add(betaMenuSettingsItem);
	}

	/**************************************************
	 * End: Menu Bar
	 **************************************************/



	public Machine getMachine() {
		return machine;
	}



	public void setMachine(Machine machine) {
		this.machine = machine;
	}



	public JList<String> getOperationList() {
		return operationList;
	}



	public JFrame getfrmBtest() {
		return frmBtest;
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
