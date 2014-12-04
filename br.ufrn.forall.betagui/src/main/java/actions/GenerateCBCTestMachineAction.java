package actions;

import general.CombinatorialCriterias;
import general.PartitionStrategy;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import parser.Operation;
import machinebuilder.CBCMachineBuilder;
import testgeneration.BETATestSuite;
import views.Application;

public class GenerateCBCTestMachineAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Application application;



	public GenerateCBCTestMachineAction(Application application) {
		this.application = application;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		boolean isNotReadyToGenerateMachine = this.application.getSelectedOperation() < 0 || this.application.getMachine() == null
				|| this.application.getCombinations().isEmpty();
		if (isNotReadyToGenerateMachine) {
			JOptionPane
					.showMessageDialog(
							null,
							"Can't generate CBC test machine. You should load a machine, select an operation to test, the criteria to use and click on the Analyze button.",
							"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			Operation operationUnderTest = this.application.getMachine().getOperation(this.application.getSelectedOperation());
			PartitionStrategy partitionStrategy = PartitionStrategy.get(this.application.getChosenPartitionStrategy());
			CombinatorialCriterias combinatorialCriterion = CombinatorialCriterias.get(this.application.getChosenCombinatorialCriteria());

			BETATestSuite testSuite = new BETATestSuite(operationUnderTest, partitionStrategy, combinatorialCriterion);
			List<String> testCasePredicates = testSuite.getFeasbileTestCaseFormulasWithoutInvariant();

			CBCMachineBuilder cbcMchBuilder = new CBCMachineBuilder(operationUnderTest, testCasePredicates);
			String cbcTestMachinePath = cbcMchBuilder.getBuiltMachine().getAbsolutePath();
			
			JOptionPane
			.showMessageDialog(
					null,
					"CBC Test Machine generated at: " + cbcTestMachinePath,
					"Machine Generated", JOptionPane.INFORMATION_MESSAGE);
		}

	}
}
