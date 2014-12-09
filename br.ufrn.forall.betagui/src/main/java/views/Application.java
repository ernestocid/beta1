package views;

import general.CombinatorialCriterias;
import general.FunctionalCriterias;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

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
	private JList operationList;
	private JTable inputSpaceTable;
	private JTable characteristicsTable;
	private JTable combinations;
	private JPanel machineInfo;
	private JLabel lblCombinatorialCriteria;
	private JComboBox partitionStrategyComboBox;
	private JComboBox combinatorialCriteriaComboBox;
	private JLabel lblMachineLoaded;
	private final Action displayAction = new DisplayInfoAction(this);
	private final Action generateTestsAction = new GenerateTestsAction(this);
	private final Action generateCBCTestMachineAction = new GenerateCBCTestMachineAction(this);
	private Set<String> combinationsValues = new HashSet<String>();
	private int selectedOperation;
	
	
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
//		frmBtest.setIconImage(Toolkit.getDefaultToolkit().getImage(Application.class.getResource("/views/betaico.png")));
		frmBtest.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("betaico.png")));
		frmBtest.setTitle("BETA");
		frmBtest.setBounds(100, 100, 1100, 600);
		frmBtest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frmBtest.setLocationRelativeTo(null);
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new ExtensionFileFilter("B Machines", "mch"));
		
		JPanel controls = new JPanel();
		controls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmBtest.getContentPane().add(controls, BorderLayout.NORTH);
		controls.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblMachineLoaded = new JLabel("Machine Loaded: <None>");
		lblMachineLoaded.setBorder(new EmptyBorder(0, 0, 0, 40));
		controls.add(lblMachineLoaded);
		
		JLabel lblFunctionalCriteria = new JLabel("Partition Strategy:");
		lblFunctionalCriteria.setHorizontalAlignment(SwingConstants.RIGHT);
		controls.add(lblFunctionalCriteria);
		
		partitionStrategyComboBox = new JComboBox();
		partitionStrategyComboBox.setModel(new DefaultComboBoxModel(FunctionalCriterias.values()));
		controls.add(partitionStrategyComboBox);
		
		lblCombinatorialCriteria = new JLabel("Combinatorial Criteria:");
		lblCombinatorialCriteria.setHorizontalAlignment(SwingConstants.RIGHT);
		controls.add(lblCombinatorialCriteria);
		
		combinatorialCriteriaComboBox = new JComboBox();
		combinatorialCriteriaComboBox.setModel(new DefaultComboBoxModel(CombinatorialCriterias.values()));
		controls.add(combinatorialCriteriaComboBox);
		
		JButton btnDisplayInfo = new JButton();
		btnDisplayInfo.setAction(displayAction);
		btnDisplayInfo.setText("Analyze");
		controls.add(btnDisplayInfo);
		
		JPanel opNavigation = new JPanel();
		opNavigation.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmBtest.getContentPane().add(opNavigation, BorderLayout.WEST);
		opNavigation.setLayout(new BorderLayout(0, 0));
		
		JLabel lblOperations = new JLabel("Operations:");
		lblOperations.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblOperations.setHorizontalAlignment(SwingConstants.LEFT);
		opNavigation.add(lblOperations, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, scrollPane.getPreferredSize().height));
		lblOperations.setLabelFor(scrollPane);
		scrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		opNavigation.add(scrollPane);
		
		operationList = new JList();
		operationList.setValueIsAdjusting(true);
		operationList.setVisibleRowCount(1);
		operationList.setModel(new DefaultListModel());
		operationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(operationList);
		
		JPanel actions = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) actions.getLayout();
		flowLayout_2.setAlignment(FlowLayout.TRAILING);
		actions.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmBtest.getContentPane().add(actions, BorderLayout.SOUTH);
		
		JButton generateCBCTestMachineButton = new JButton("Generate CBC Test Machine");
		generateCBCTestMachineButton.setAction(generateCBCTestMachineAction);
		generateCBCTestMachineButton.setText("Generate CBC Test Machine");
		generateCBCTestMachineButton.setHorizontalAlignment(SwingConstants.TRAILING);
		actions.add(generateCBCTestMachineButton);
		
		JButton generateTestButton = new JButton("Create Test Report");
		generateTestButton.setAction(generateTestsAction);
		generateTestButton.setText("Generate Report");
		generateTestButton.setHorizontalAlignment(SwingConstants.TRAILING);
		actions.add(generateTestButton);
		
		machineInfo = new JPanel();
		frmBtest.getContentPane().add(machineInfo, BorderLayout.CENTER);
		machineInfo.setLayout(new BoxLayout(machineInfo, BoxLayout.X_AXIS));
		
		JPanel inputSpacePanel = new JPanel();
		inputSpacePanel.setLayout(new BoxLayout(inputSpacePanel, BoxLayout.Y_AXIS));
		
		JScrollPane inputSpaceScroll = new JScrollPane();
		inputSpacePanel.add(inputSpaceScroll);
		
		inputSpaceTable = new JTable();
		inputSpaceScroll.setViewportView(inputSpaceTable);
		inputSpaceTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Input Space Variables:"
			}
		));
		
		JScrollPane characteristicsScroll = new JScrollPane();
		inputSpacePanel.add(characteristicsScroll);
		
		characteristicsTable = new JTable();
		characteristicsScroll.setViewportView(characteristicsTable);
		characteristicsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Characteristics and Blocks"
			}
		));
		
		JScrollPane combinationsScroll = new JScrollPane();
		inputSpacePanel.add(combinationsScroll);
		
		combinations = new JTable();
		combinationsScroll.setViewportView(combinations);
		combinations.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Num:", "Combinations (Test Cases):"
			}
		));
		TableColumn firstColumn = combinations.getColumnModel().getColumn(0);
		firstColumn.setMaxWidth(45);
		machineInfo.add(inputSpacePanel);
		
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		frmBtest.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("BETA");
		mnFile.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mnFile);
		
		JMenuItem mntmLoadMachine = new JMenuItem("Load Machine");
		mntmLoadMachine.setAction(openFileAction);
		mnFile.add(mntmLoadMachine);
		
		// Configurations Menu
		
		JMenuItem mntmConfigureProBPath = new JMenuItem("Settings");
		mntmConfigureProBPath.addActionListener(new ActionListener() {
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
		mnFile.add(mntmConfigureProBPath);
	}

	public Machine getMachine() {
		return machine;
	}
	
	public void setMachine(Machine machine) {
		this.machine = machine;
	}
	
	public JList getOperationList() {
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

	public JComboBox getPartitionStrategy() {
		return partitionStrategyComboBox;
	}
	
	public JComboBox getCombinatorialCriteria() {
		return combinatorialCriteriaComboBox;
	}
	
	public JLabel getLabelMachineLoaded() {
		return lblMachineLoaded;
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
	
	public int getChosenPartitionStrategy() {
		return this.partitionStrategyComboBox.getSelectedIndex();
	}
	
	public int getChosenCombinatorialCriteria() {
		return this.combinatorialCriteriaComboBox.getSelectedIndex();
	}
	
}
