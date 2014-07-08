package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import parser.Operation;


import views.Application;

import parser.Machine;


public class LoadMachineAction extends AbstractAction{

	
	private Application application;
	
	
	public LoadMachineAction(Application application) {
		this.application = application;
		putValue(NAME, "Load Machine");
		putValue(SHORT_DESCRIPTION, "Some short description");
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int response = this.application.getFileChooser().showOpenDialog(application.getfrmBtest());
		
		if(response == JFileChooser.APPROVE_OPTION) {
			File mch = this.application.getFileChooser().getSelectedFile();
			Machine loadedMachine = new Machine(mch);
			
			clearOperationsList();
			clearInputSpaceField();
			clearCharacteristicsField();
			clearCombinationsTable();
			
			if(loadedMachine.isParsed()) {
				application.setMachine(new Machine(mch));
				
				String machineName = application.getMachine().getName();
				application.getLabelMachineLoaded().setText("Machine Loaded: " + machineName);
				
				List<Operation> operations = application.getMachine().getOperations();
				
				if(!operations.isEmpty()) {
					DefaultListModel listModel = new DefaultListModel();
					
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
		DefaultListModel listModel = new DefaultListModel();
		application.getOperationList().setModel(listModel);
		application.getOperationList().validate();		
	}



	private void clearCombinationsTable() {
		String[] tableHeader = { "Num:", "Combination:" };
		TableModel dataModel = new DefaultTableModel(new String[1][1], tableHeader);
		
		application.getCombinationsTable().setModel(dataModel);
	}



	private void clearCharacteristicsField() {
		String[] tableHeader = { "Characteristics:", "Blocks:" };
		TableModel dataModel = new DefaultTableModel(new String[1][1], tableHeader);
		
		application.getCharacteristicsAndBlocksTable().setModel(dataModel);
	}



	private void clearInputSpaceField() {
		String[] tableHeader = {"Input Space:"};
		
		TableModel dataModel = new DefaultTableModel(new String[1][1], tableHeader);
		application.getInputSpaceTable().setModel(dataModel);
	}	
}
