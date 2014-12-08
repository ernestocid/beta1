package testgeneration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;
import tools.FileTools;
import animator.Animation;
import animator.Animator;
import animator.ConventionTools;
import configurations.Configurations;

public class AuxiliarMachinePredicateEvaluator extends AbstractPredicateEvaluator {

	public AuxiliarMachinePredicateEvaluator(Operation operationUnderTest, Set<List<Block>> combinations) {
		super(operationUnderTest, combinations);

		Machine auxiliarTestMachine = new Machine(createAuxiliarTestMachineFile(getCombinations()));
		Animator animator = new Animator(auxiliarTestMachine);
		getAnimations().addAll(animator.animateAllOperations());
		addInfeasibleFormulasToList(animator);
		deleteTempFiles();
	}



	private void deleteTempFiles() {
		if (Configurations.isDeleteTempFiles() == true) {
			String parentPathDirectory = getOperationUnderTest().getMachine().getFile().getParent();
			deleteTempFilesFromDirectory(parentPathDirectory);
		}
	}



	private void deleteTempFilesFromDirectory(String parentPathDirectory) {
		File mchFile = new File(parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(getOperationUnderTest())
				+ ".mch");
		mchFile.delete();
		File plFile = new File(parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(getOperationUnderTest())
				+ ".mch.pl");
		plFile.delete();
		File probFile = new File(parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(getOperationUnderTest())
				+ ".prob");
		probFile.delete();
	}



	@Override
	public List<Animation> getSolutions() {
		return super.getAnimations();
	}



	private File createAuxiliarTestMachineFile(Set<List<Block>> combinations) {
		TestMachineBuilder testMachineBuilder = new TestMachineBuilder(getOperationUnderTest(), combinations);
		String testMachineContent = testMachineBuilder.generateTestMachine();
		String parentPathDirectory = getOperationUnderTest().getMachine().getFile().getParent();
		String testMachineFilePath = parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(getOperationUnderTest())
				+ ".mch";
		File testMachineFile = FileTools.createFileWithContent(testMachineFilePath, testMachineContent);

		return testMachineFile;
	}



	private void addInfeasibleFormulasToList(Animator animator) {
		for (Operation op : animator.getOperationsWithInfeasiblePreconditions()) {
			getInfeasibleFormulas().add(generateTestFormulaWithoutInvariant(op.getPrecondition()));
		}
	}



	private String generateTestFormulaWithoutInvariant(MyPredicate testFormula) {

		// Creating clauses list from test formula

		List<String> testFormulaClauses = getClausesFromFormula(testFormula);

		// Creating set of invariant clauses

		Set<String> invariantClauses = getClausesFromInvariant();

		// Removing invariant clauses from the list of clauses of the test
		// formula and sorting the test formulas again

		testFormulaClauses.removeAll(invariantClauses);
		Collections.sort(testFormulaClauses);

		// Rebuild the formula making a conjunction with the filtered clauses

		String testFormulaWithoutInvariant = createConjunctionOfClauses(testFormulaClauses);

		return testFormulaWithoutInvariant;

	}



	private List<String> getClausesFromFormula(MyPredicate testFormula) {
		Set<MyPredicate> clauses = testFormula.getClauses();
		List<String> clausesAsStrings = new ArrayList<String>();

		for (MyPredicate clause : clauses) {
			clausesAsStrings.add(clause.toString().replaceAll("i__", ""));
		}

		return clausesAsStrings;
	}



	private Set<String> getClausesFromInvariant() {
		Set<MyPredicate> condensedInvariantFromAllMachines = getOperationUnderTest().getMachine().getCondensedInvariantFromAllMachines();
		Set<String> condesendInvariant = new HashSet<String>();

		for (MyPredicate myPredicate : condensedInvariantFromAllMachines) {
			condesendInvariant.add(myPredicate.toString());
		}

		return condesendInvariant;
	}



	private String createConjunctionOfClauses(List<String> testFormulaClauses) {
		StringBuffer testFormulaWithoutInvariant = new StringBuffer();

		for (int i = 0; i < testFormulaClauses.size(); i++) {
			if (i < (testFormulaClauses.size() - 1)) {
				testFormulaWithoutInvariant.append(testFormulaClauses.get(i) + " & ");
			} else {
				testFormulaWithoutInvariant.append(testFormulaClauses.get(i));
			}
		}

		String cleanTestFormula = testFormulaWithoutInvariant.toString().replaceAll("i__", "");

		return cleanTestFormula;
	}



	@Override
	public List<String> getInfeasiblePredicates() {
		return super.getInfeasibleFormulas();
	}

}
