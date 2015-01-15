package actions;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import configurations.Configurations;
import de.prob.Main;
import de.prob.scripting.Api;

public class DisplaySettingsAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public DisplaySettingsAction() {
		putValue(NAME, "Settings");
		putValue(SHORT_DESCRIPTION, "Shows the Settings window");
	}



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

				if (probcli.exists() || probcliexe.exists()) {
					System.out.println("Setting ProB path to: " + probPathField.getText());
					Configurations.setProBPath(probPathField.getText());
					configFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "This is not a valid ProB directory.\nWe could not find \"probcli\" on this directory.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				// Set Oracles strategies

				Configurations.setOracleStrategy(stateVariablesOracCB.isSelected(), returnVariablesOracCB.isSelected(), invariantOKOracCB.isSelected());

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

}
