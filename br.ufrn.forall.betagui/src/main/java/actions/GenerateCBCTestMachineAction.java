package actions;

import general.CombinatorialCriteria;
import general.PartitionStrategy;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import parser.Operation;
import machinebuilder.CBCMachineBuilder;
import testgeneration.BETATestSuite;
import testgeneration.coveragecriteria.BoundaryValueAnalysis;
import testgeneration.coveragecriteria.CoverageCriterion;
import testgeneration.coveragecriteria.EquivalenceClasses;
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
		boolean isNotReadyToGenerateMachine = this.application.getSelectedOperation() < 0 || this.application.getMachine() == null;
		if (isNotReadyToGenerateMachine) {
			JOptionPane
					.showMessageDialog(
							null,
							"Can't generate CBC test machine. You should load a machine, select an operation to test, \nthe test strategy and the coverage criterion before you can generate it.",
							"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			Operation operationUnderTest = this.application.getMachine().getOperation(this.application.getSelectedOperation());

			CoverageCriterion coverageCriterion;
			
			if(PartitionStrategy.get(application.getChosenTestingStrategy()) == PartitionStrategy.EQUIVALENT_CLASSES) {
            	coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.get(application.getChosenCoverageCriteria()));
            } else if (PartitionStrategy.get(application.getChosenTestingStrategy()) == PartitionStrategy.BOUNDARY_VALUES) {
            	coverageCriterion = new BoundaryValueAnalysis(operationUnderTest, CombinatorialCriteria.get(application.getChosenCoverageCriteria()));
            } else {
            	coverageCriterion = null;
            }
			
			BETATestSuite testSuite = new BETATestSuite(coverageCriterion);
			List<String> testCasePredicates = testSuite.getFeasbileTestCaseFormulasWithoutInvariant();

			CBCMachineBuilder cbcMchBuilder = new CBCMachineBuilder(operationUnderTest, "1=1");
			String cbcTestMachinePath = cbcMchBuilder.getBuiltMachine().getAbsolutePath();
			
			JOptionPane
			.showMessageDialog(
					null,
					"CBC Test Machine generated at: " + cbcTestMachinePath,
					"Machine Generated", JOptionPane.INFORMATION_MESSAGE);
		}

	}
}
