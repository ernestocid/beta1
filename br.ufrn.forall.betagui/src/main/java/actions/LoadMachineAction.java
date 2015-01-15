package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import parser.Operation;


import views.Application;

import parser.Machine;


public class LoadMachineAction extends AbstractAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Application application;


	public LoadMachineAction(Application application) {
		this.application = application;
		putValue(NAME, "Load Machine");
		putValue(SHORT_DESCRIPTION, "Loads the machine to be tested");
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		int response = this.application.getFileChooser().showOpenDialog(application.getMainFrame());

		if(response == JFileChooser.APPROVE_OPTION) {
			File mch = this.application.getFileChooser().getSelectedFile();
			Machine loadedMachine = new Machine(mch);

			clearOperationsList();

			if(loadedMachine.isParsed()) {
				application.setMachine(new Machine(mch));

				String machineName = application.getMachine().getName();
				application.getOperationsListLabel().setText("Choose an operation from " + machineName + ".mch to test:");

				List<Operation> operations = application.getMachine().getOperations();

				if(!operations.isEmpty()) {
					DefaultListModel<String> listModel = new DefaultListModel<String>();

					int indexCount = 0;
					for (Operation op : operations) {
						listModel.add(indexCount, op.getName());
						indexCount ++;
					}

					application.getOperationList().setModel(listModel);
					application.getOperationList().validate();	
				} else {
					JOptionPane.showMessageDialog(null, "The machine you tried to load has no operations.\nThere is no information to display.", "Error", JOptionPane.ERROR_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "The machine you tried to load contains specifications errors.\nFix it and load it again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}



	private void clearOperationsList() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		application.getOperationList().setModel(listModel);
		application.getOperationList().validate();
	}

}
