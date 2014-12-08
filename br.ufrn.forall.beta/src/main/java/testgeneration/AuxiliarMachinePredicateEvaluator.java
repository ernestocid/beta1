package testgeneration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import configurations.Configurations;
import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;
import tools.FileTools;
import animator.Animation;
import animator.Animator;
import animator.ConventionTools;

public class AuxiliarMachinePredicateEvaluator implements PredicateEvaluator {

	private Operation operationUnderTest;
	private Set<List<Block>> combinations;
	private Map<String, Boolean> formulaAndTestTypeMap;
	private List<Animation> animations = new ArrayList<Animation>();
	private List<String> infeasibleFormulas = new ArrayList<String>();



	public AuxiliarMachinePredicateEvaluator(Operation operationUnderTest, Set<List<Block>> combinations) {
		this.operationUnderTest = operationUnderTest;
		this.combinations = combinations;
		this.formulaAndTestTypeMap = mapFormulaToTypeOfTest(this.combinations);

		Machine auxiliarTestMachine = new Machine(createAuxiliarTestMachineFile(getCombinations()));
		Animator animator = new Animator(auxiliarTestMachine);
		this.animations.addAll(animator.animateAllOperations());
		addInfeasibleFormulasToList(animator);
		deleteTempFiles();
	}



	private void deleteTempFiles() {
		if (Configurations.isDeleteTempFiles() == true) {
			String parentPathDirectory = operationUnderTest.getMachine().getFile().getParent();
			deleteTempFilesFromDirectory(parentPathDirectory);
		}
	}



	private void deleteTempFilesFromDirectory(String parentPathDirectory) {
		File mchFile = new File(parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(operationUnderTest) + ".mch");
		mchFile.delete();
		File plFile = new File(parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(operationUnderTest) + ".mch.pl");
		plFile.delete();
		File probFile = new File(parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(operationUnderTest) + ".prob");
		probFile.delete();
	}



	@Override
	public List<Animation> getSolutions() {
		return this.animations;
	}



	private File createAuxiliarTestMachineFile(Set<List<Block>> combinations) {
		TestMachineBuilder testMachineBuilder = new TestMachineBuilder(getOperationUnderTest(), combinations);
		String testMachineContent = testMachineBuilder.generateTestMachine();
		String parentPathDirectory = operationUnderTest.getMachine().getFile().getParent();
		String testMachineFilePath = parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(operationUnderTest)
				+ ".mch";
		File testMachineFile = FileTools.createFileWithContent(testMachineFilePath, testMachineContent);

		return testMachineFile;
	}



	public Operation getOperationUnderTest() {
		return operationUnderTest;
	}



	public Set<List<Block>> getCombinations() {
		return combinations;
	}



	private Map<String, Boolean> mapFormulaToTypeOfTest(Set<List<Block>> combinations) {
		Map<String, Boolean> mapping = new HashMap<String, Boolean>();

		for (List<Block> combination : combinations) {
			String formula = convertCombinationToStringConcatenation(combination);
			formula = formula.replaceAll("i__", "");
			Boolean isNegative = hasNegativeBlock(combination);

			mapping.put(formula.replaceAll("[()]", ""), isNegative);
		}

		return mapping;
	}



	private String convertCombinationToStringConcatenation(List<Block> combination) {
		StringBuffer concatenation = new StringBuffer("");

		for (int i = 0; i < combination.size(); i++) {
			if (i == combination.size() - 1) {
				concatenation.append(combination.get(i).getBlock());
			} else {
				concatenation.append(combination.get(i).getBlock() + " & ");
			}
		}

		return concatenation.toString();
	}



	private boolean hasNegativeBlock(List<Block> combination) {
		for (Block block : combination) {
			if (block.isNegative()) {
				return true;
			}
		}

		return false;
	}



	@Override
	public Map<String, Boolean> getFormulaAndTestTypeMap() {
		return formulaAndTestTypeMap;
	}



	@Override
	public List<String> getInfeasiblePredicates() {
		return infeasibleFormulas;
	}



	private void addInfeasibleFormulasToList(Animator animator) {
		for (Operation op : animator.getOperationsWithInfeasiblePreconditions()) {
			this.infeasibleFormulas.add(generateTestFormulaWithoutInvariant(op.getPrecondition()));
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
		Set<MyPredicate> condensedInvariantFromAllMachines = this.operationUnderTest.getMachine().getCondensedInvariantFromAllMachines();
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

}
