package actions;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import configurations.Configurations;

public class DisplaySettingsAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField probPathField;
	private JFrame settingsFrame;
	private JCheckBox stateVariablesOracCB;
	private JCheckBox returnVariablesOracCB;
	private JCheckBox invariantOKOracCB;
	private JCheckBox deleteTempFilesCB;
	private JCheckBox automaticOracleEvaluationCB;
	private JCheckBox useProBApiToSolvePredicatesCB;
	private JCheckBox useKodkodCB;
	private JCheckBox useProbRandomEnumerationCB;
	private JTextField minintField;
	private JTextField maxintField;
	private JTextField probTimeoutField;
	private JButton saveButton;
	private JPanel configPanel;
	private JLabel probPathLabel;
	private JLabel oracleStrategyLabel;
	private JPanel oracleStrategyOptions;
	private JLabel deleteTempFilesLabel;
	private JLabel automaticOracleEvaluationLabel;
	private JLabel useProBApiToSolvePredicatesLabel;
	private JLabel useKodkodLabel;
	private JLabel useProbRandomEnumerationLabel;
	private JLabel minintLabel;
	private JLabel maxintLabel;
	private JLabel probTimeoutLabel;
	private JLabel downloadProBCliLabel;
	private JButton downloadProBClitButton;
	private JLabel cbcDepthLabel;
	private JTextField cbcDepthField;
	private JLabel findPreambleLabel;
	private JCheckBox findPreambleCB;



	public DisplaySettingsAction() {
		putValue(NAME, "Settings");
		putValue(SHORT_DESCRIPTION, "Shows the Settings window");
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		settingsFrame = new JFrame("Settings");

		configPanel = new JPanel(new GridBagLayout());
		configPanel.setBackground(Color.WHITE);
		configPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));

		probPathLabel = createLabel("ProB installation path: ");
		probPathField = new JTextField(Configurations.getProBPath(), 25);

		oracleStrategyLabel = createLabel("Oracle Strategy: ");
		oracleStrategyOptions = new JPanel(new FlowLayout());
		oracleStrategyOptions.setBackground(Color.WHITE);
		oracleStrategyOptions.setBorder(new EmptyBorder(0, -5, 0, 0));

		stateVariablesOracCB = new JCheckBox("State variables");
		stateVariablesOracCB.setSelected(Configurations.isStateVariablesActive());

		returnVariablesOracCB = new JCheckBox("Return Variables");
		returnVariablesOracCB.setSelected(Configurations.isReturnVariablesActive());

		invariantOKOracCB = new JCheckBox("Invariant OK");
		invariantOKOracCB.setSelected(Configurations.isInvariantOKActive());

		oracleStrategyOptions.add(stateVariablesOracCB);
		oracleStrategyOptions.add(returnVariablesOracCB);
		oracleStrategyOptions.add(invariantOKOracCB);

		deleteTempFilesLabel = createLabel("Delete Temporary Files: ");
		deleteTempFilesCB = new JCheckBox("Delete Files");
		deleteTempFilesCB.setSelected(Configurations.isDeleteTempFiles());

		automaticOracleEvaluationLabel = createLabel("Automatic Oracle Evaluation: ");
		automaticOracleEvaluationCB = new JCheckBox("Generate Oracle Values (experimental)");
		automaticOracleEvaluationCB.setSelected(Configurations.isAutomaticOracleEvaluation());

		useProBApiToSolvePredicatesLabel = createLabel("Use ProB API to solve predicates: ");
		useProBApiToSolvePredicatesCB = new JCheckBox("Use ProB API (experimental)");
		useProBApiToSolvePredicatesCB.setSelected(Configurations.isUseProBApiToSolvePredicates());

		useKodkodLabel = createLabel("Use Kodkod: ");
		useKodkodCB = new JCheckBox("Use Kodkod (experimental)");
		useKodkodCB.setSelected(Configurations.getUseKodkod());

		useProbRandomEnumerationLabel = createLabel("Use ProB's random enumeration: ");
		useProbRandomEnumerationCB = new JCheckBox("Random enumeration");
		useProbRandomEnumerationCB.setSelected(Configurations.getRandomiseEnumerationOrder());

		minintLabel = createLabel("MININT Value: ");
		minintField = new JTextField(String.valueOf(Configurations.getMinIntProperties()), 4);

		maxintLabel = createLabel("MAXINT Value: ");
		maxintField = new JTextField(String.valueOf(Configurations.getMaxIntProperties()), 4);

		probTimeoutLabel = createLabel("ProB timeout: ");
		probTimeoutField = new JTextField(String.valueOf(Configurations.getProBTimeout()), 4);

		downloadProBCliLabel = createLabel("Download latest probcli binaries: ");
		downloadProBClitButton = new JButton("Download probcli");
		downloadProBClitButton.addActionListener(new DownloadProBBinariesAction());

		saveButton = new JButton("Save Settings");
		saveButton.addActionListener(new SaveSettingsAction(this));

		findPreambleLabel = createLabel("Find preamble: ");
		findPreambleCB = new JCheckBox("Use ProB's CBC to find test preamble (experimental)");
		findPreambleCB.setSelected(Configurations.isFindPreamble());

		cbcDepthLabel = createLabel("CBC Search Depth: ");
		cbcDepthField = new JTextField(String.valueOf(Configurations.getCBCDepth()), 4);

		configPanel.add(probPathLabel, gbc(0, 0, GridBagConstraints.EAST));
		configPanel.add(getProbPathField(), gbc(1, 0, GridBagConstraints.WEST));

		configPanel.add(downloadProBCliLabel, gbc(0, 1, GridBagConstraints.EAST));
		configPanel.add(downloadProBClitButton, gbc(1, 1, GridBagConstraints.WEST));

		configPanel.add(oracleStrategyLabel, gbc(0, 2, GridBagConstraints.EAST));
		configPanel.add(oracleStrategyOptions, gbc(1, 2, GridBagConstraints.WEST));

		configPanel.add(deleteTempFilesLabel, gbc(0, 3, GridBagConstraints.EAST));
		configPanel.add(deleteTempFilesCB, gbc(1, 3, GridBagConstraints.WEST));

		configPanel.add(useKodkodLabel, gbc(0, 4, GridBagConstraints.EAST));
		configPanel.add(useKodkodCB, gbc(1, 4, GridBagConstraints.WEST));

		configPanel.add(useProbRandomEnumerationLabel, gbc(0, 5, GridBagConstraints.EAST));
		configPanel.add(useProbRandomEnumerationCB, gbc(1, 5, GridBagConstraints.WEST));

		configPanel.add(automaticOracleEvaluationLabel, gbc(0, 6, GridBagConstraints.EAST));
		configPanel.add(automaticOracleEvaluationCB, gbc(1, 6, GridBagConstraints.WEST));

		configPanel.add(useProBApiToSolvePredicatesLabel, gbc(0, 7, GridBagConstraints.EAST));
		configPanel.add(useProBApiToSolvePredicatesCB, gbc(1, 7, GridBagConstraints.WEST));

		configPanel.add(minintLabel, gbc(0, 8, GridBagConstraints.EAST));
		configPanel.add(minintField, gbc(1, 8, GridBagConstraints.WEST));

		configPanel.add(maxintLabel, gbc(0, 9, GridBagConstraints.EAST));
		configPanel.add(maxintField, gbc(1, 9, GridBagConstraints.WEST));

		configPanel.add(probTimeoutLabel, gbc(0, 10, GridBagConstraints.EAST));
		configPanel.add(probTimeoutField, gbc(1, 10, GridBagConstraints.WEST));

		configPanel.add(findPreambleLabel, gbc(0, 11, GridBagConstraints.EAST));
		configPanel.add(findPreambleCB, gbc(1, 11, GridBagConstraints.WEST));

		configPanel.add(cbcDepthLabel, gbc(0, 12, GridBagConstraints.EAST));
		configPanel.add(cbcDepthField, gbc(1, 12, GridBagConstraints.WEST));

		configPanel.add(saveButton, gbc(1, 13, GridBagConstraints.EAST));

		settingsFrame.getContentPane().add(configPanel);
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(null);
		settingsFrame.setVisible(true);
	}



	private JLabel createLabel(String name) {
		JLabel label = new JLabel(name);
		label.setBorder(new EmptyBorder(10, 10, 10, 10));
		return label;
	}



	private GridBagConstraints gbc(int x, int y, int anchor) {
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = x;
		gbc.gridy = y;
		gbc.anchor = anchor;

		return gbc;
	}



	public JTextField getProbPathField() {
		return probPathField;
	}



	public JFrame getSettingsFrame() {
		return settingsFrame;
	}



	public JCheckBox getDeleteTempFilesCB() {
		return deleteTempFilesCB;
	}



	public JCheckBox getAutomaticOracleEvaluationCB() {
		return automaticOracleEvaluationCB;
	}



	public JCheckBox getUseProBApiToSolvePredicatesCB() {
		return useProBApiToSolvePredicatesCB;
	}



	public JCheckBox getUseKodkodCB() {
		return useKodkodCB;
	}



	public JTextField getMinintField() {
		return minintField;
	}



	public JTextField getMaxintField() {
		return maxintField;
	}



	public JTextField getProbTimeoutField() {
		return probTimeoutField;
	}



	public JCheckBox getUseProbRandomEnumerationCB() {
		return useProbRandomEnumerationCB;
	}



	public JCheckBox getStateVariablesOracCB() {
		return stateVariablesOracCB;
	}



	public JCheckBox getReturnVariablesOracCB() {
		return returnVariablesOracCB;
	}



	public JCheckBox getInvariantOKOracCB() {
		return invariantOKOracCB;
	}



	public JCheckBox getFindPreambleCB() {
		return findPreambleCB;
	}



	public JTextField getCbcDepthField() {
		return cbcDepthField;
	}

}
