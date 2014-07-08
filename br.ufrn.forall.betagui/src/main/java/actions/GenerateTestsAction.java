package actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import views.Application;
import views.GenerateReportPanel;
import configurations.Configurations;

public class GenerateTestsAction extends AbstractAction {


	private Application application;

	
	public GenerateTestsAction(Application application) {
		this.application = application;
	}

	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!isValidProBDirectory()) {
			JOptionPane.showMessageDialog(null, 
					"The ProB directory you informed is not valid. " +
					"We could not find \"probcli\" on this directory.\n" +
					"Please, go to Settings and inform a valid ProB directory before you proceed.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} else if(this.application.getMachine() == null || this.application.getCombinations().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Can't generate report. No tests cases found.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			displayGenerateReportPanel();
		}
	}



	private void displayGenerateReportPanel() {
		GenerateReportPanel.createAndShowGUI(this.application);
	}



	private boolean isValidProBDirectory() {
		
		// Check if ProB directory settings is empty
		
		if(!Configurations.getProBPath().equals("")) {
			File probcli = new File(Configurations.getProBPath() + "probcli");
			File probcliexe = new File(Configurations.getProBPath() + "probcli.exe");
		
			// Check if directory contains probcli
			
			if(probcli.exists() || probcliexe.exists()) {
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
	}
}
