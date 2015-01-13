package testgeneration;

import general.TestingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import parser.Operation;
import parser.decorators.predicates.MyPredicate;
import testgeneration.coveragecriteria.CoverageCriterion;
import testgeneration.predicateevaluators.AuxiliarMachinePredicateEvaluator;
import testgeneration.predicateevaluators.IPredicateEvaluator;
import testgeneration.predicateevaluators.ProBApiPredicateEvaluator;
import animator.Animation;
import configurations.Configurations;

public class BETATestSuite {

	private List<BETATestCase> testCases;
	private List<String> unsolvableFormulas;
	private CoverageCriterion coverageCriterion;



	public BETATestSuite(CoverageCriterion coverageCriterion) {
		this.coverageCriterion = coverageCriterion;
		this.testCases = new ArrayList<BETATestCase>();
		this.unsolvableFormulas = new ArrayList<String>();
		generateTestCases();
	}



	private void generateTestCases() {

		// Getting combinations according to the Coverage Criterion

		Set<List<Block>> combinations = coverageCriterion.getCombinationsAsListsOfBlocks();

		if (!combinations.isEmpty()) {

			// Evaluating combinations to get test case values for parameters
			// and variables. The the predicates obtained from the combinations
			// can be evaluated using either the ProB Api or an auxiliary
			// machine trick. The user can choose which method to use in the
			// tool preferences window.

			IPredicateEvaluator predicateEvaluator;

			if (Configurations.isUseProBApiToSolvePredicates()) {
				predicateEvaluator = new ProBApiPredicateEvaluator(getOperationUnderTest(), combinations);
			} else {
				predicateEvaluator = new AuxiliarMachinePredicateEvaluator(getOperationUnderTest(), combinations);
			}

			// Identifying infeasible combinations
			setUnsolvableTestFormulas(predicateEvaluator.getInfeasiblePredicates());

			// Creating test cases
			createTestCases(predicateEvaluator.getSolutions(), predicateEvaluator.getFormulaAndTestTypeMap());
		}
	}



	private void createTestCases(List<Animation> animations, Map<String, Boolean> mappingOfPositiveAndNegativeTestFormulas) {
		for (Animation animation : animations) {

			// One animation contains a list of possible combinations of values
			// for the test. We pick the first element since they are all
			// equivalent tests.

			Map<String, String> animationValues = animation.getValues().get(0);

			// Setting up test case and adding to test suite.

			BETATestCase testCase = new BETATestCase(animation.getFormula(), generateTestFormulaWithoutInvariant(animation.getPredicate()),
					getAttributeValues(animationValues), getParamValues(animationValues), isNegativeTest(animation, mappingOfPositiveAndNegativeTestFormulas),
					this);

			testCases.add(testCase);

		}

		Collections.sort(testCases);
	}



	private boolean isNegativeTest(Animation animation, Map<String, Boolean> mappingCombinationToTypeOfTest) {
		String formula = animation.getFormula().replaceAll("[()]", "");
		Boolean isNegative = mappingCombinationToTypeOfTest.get(formula);
		return isNegative.booleanValue();
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



	private Set<String> getClausesFromInvariant() {
		Set<MyPredicate> condensedInvariantFromAllMachines = getOperationUnderTest().getMachine().getCondensedInvariantFromAllMachines();
		Set<String> condesendInvariant = new HashSet<String>();

		for (MyPredicate myPredicate : condensedInvariantFromAllMachines) {
			condesendInvariant.add(myPredicate.toString());
		}

		return condesendInvariant;
	}



	private List<String> getClausesFromFormula(MyPredicate testFormula) {
		Set<MyPredicate> clauses = testFormula.getClauses();
		List<String> clausesAsStrings = new ArrayList<String>();

		for (MyPredicate clause : clauses) {
			clausesAsStrings.add(clause.toString().replaceAll("i__", ""));
		}

		return clausesAsStrings;
	}



	private HashMap<String, String> getAttributeValues(Map<String, String> animation) {
		HashMap<String, String> attributeValues = new HashMap<String, String>();

		Set<String> variables = getOperationUnderTest().getMachine().getVariablesFromAllMachines();

		for (String variable : variables) {
			if (animationHasValueForVariable(animation, variable)) {
				attributeValues.put(variable, animation.get(variable));
			}
		}

		return attributeValues;
	}



	private boolean animationHasValueForVariable(Map<String, String> animation, String variable) {
		return animation.get(variable) != null;
	}



	private HashMap<String, String> getParamValues(Map<String, String> animation) {
		HashMap<String, String> parameterValues = new HashMap<String, String>();
		List<String> parameters = getOperationUnderTest().getParameters();

		for (String param : parameters) {
			parameterValues.put(param, animation.get(param));
		}

		return parameterValues;
	}



	public List<BETATestCase> getTestCases() {
		return this.testCases;
	}



	public Operation getOperationUnderTest() {
		return this.coverageCriterion.getOperationUnderTest();
	}



	public List<String> getUnsolvableFormulas() {
		return unsolvableFormulas;
	}



	/**
	 * Returns a list containing all feasible test cases formulas in the test
	 * suite. These formulas are extracted from the test cases, after they are
	 * created when building the test suite.
	 * 
	 * @return a List containing all feasbile test formulas.
	 */
	public List<String> getFeasbileTestCaseFormulasWithoutInvariant() {
		List<String> feasibleTestCaseFormulas = new ArrayList<String>();

		for (BETATestCase testCase : getTestCases()) {
			feasibleTestCaseFormulas.add(testCase.getTestFormulaWithoutInvariant());
		}

		return feasibleTestCaseFormulas;
	}



	private void setUnsolvableTestFormulas(List<String> unsolvableFormulas) {
		this.unsolvableFormulas = unsolvableFormulas;
	}



	public CoverageCriterion getCoverageCriterion() {
		return coverageCriterion;
	}



//	public TestingStrategy getTestingStrategy() {
//		return this.coverageCriterion;
//	}

}
