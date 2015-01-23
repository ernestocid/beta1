package actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import configurations.Configurations;

public class SaveSettingsAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DisplaySettingsAction displaySettings;



	public SaveSettingsAction(DisplaySettingsAction displaySettings) {
		this.displaySettings = displaySettings;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		File probcli = new File(displaySettings.getProbPathField().getText() + "probcli");
		File probcliexe = new File(displaySettings.getProbPathField().getText() + "probcli.exe");

		// Checking if ProB directory is valid and contains probcli

		if (probcli.exists() || probcliexe.exists()) {
			System.out.println("Setting ProB path to: " + displaySettings.getProbPathField().getText());
			Configurations.setProBPath(displaySettings.getProbPathField().getText());
			displaySettings.getSettingsFrame().dispose();
		} else {
			JOptionPane.showMessageDialog(null, "This is not a valid ProB directory.\nWe could not find \"probcli\" on this directory.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		// Set Oracles strategies

		Configurations.setOracleStrategy(displaySettings.getStateVariablesOracCB().isSelected(), displaySettings.getReturnVariablesOracCB().isSelected(),
				displaySettings.getInvariantOKOracCB().isSelected());

		// Set Delete temporary files

		Configurations.setDeleteTempFiles(displaySettings.getDeleteTempFilesCB().isSelected());

		// Set Automatic Oracle Evaluation

		Configurations.setAutomaticOracleEvaluation(displaySettings.getAutomaticOracleEvaluationCB().isSelected());

		// Set Use ProBApi

		Configurations.setUseProBApiToSolvePredicates(displaySettings.getUseProBApiToSolvePredicatesCB().isSelected());

		// Set Use Kodkod

		Configurations.setUseKodkod(displaySettings.getUseKodkodCB().isSelected());

		// Set Use ProB random enumeration

		Configurations.setRandomiseEnumerationOrder(displaySettings.getUseProbRandomEnumerationCB().isSelected());

		// Set MININT and MAXINT values

		Configurations.setMinIntProperties(Integer.valueOf(displaySettings.getMinintField().getText()));
		Configurations.setMaxIntProperties(Integer.valueOf(displaySettings.getMaxintField().getText()));
		Configurations.setProBTimeout(Integer.valueOf(displaySettings.getProbTimeoutField().getText()));

		// Set find_preamble

		Configurations.setFindPreamble(displaySettings.getFindPreambleCB().isSelected());

		// Set CBC depth

		Configurations.setCBCDepth(Integer.valueOf(displaySettings.getCbcDepthField().getText()));
	}

}
