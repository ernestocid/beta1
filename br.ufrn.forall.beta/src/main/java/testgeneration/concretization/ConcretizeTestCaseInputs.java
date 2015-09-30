package testgeneration.concretization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.be4.classicalb.core.parser.node.APredicateParseUnit;
import de.be4.classicalb.core.parser.node.PParseUnit;
import de.prob.animator.domainobjects.ClassicalB;
import parser.Implementation;
import parser.Invariant;
import parser.Operation;
import parser.decorators.expressions.MyExpression;
import parser.decorators.predicates.MyAExistsPredicate;
import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;
import testgeneration.Block;
import testgeneration.predicateevaluators.ProBApiPredicateEvaluator;


/**
 * This class is responsible for test data concretization. Based on the 
 * implementations glue invariant, we find the relation between the concrete
 * variables and the abstract variables, and translate the abstract data generated
 * for the test cases into concrete data that is suitable for the implementation.
 *  
 * @author ernestocid
 *
 */
public class ConcretizeTestCaseInputs {

	private String testFormula;
	private Operation operationUnderTest;
	private Implementation implementation;



	public ConcretizeTestCaseInputs(String testFormula, Operation operationUnderTest, Implementation implementation) {
		this.testFormula = testFormula;
		this.operationUnderTest = operationUnderTest;
		this.implementation = implementation;
	}

	

	/**
	 * Concretizes the abstract test data and returns a Map where each entry
	 * is a concrete variable and its respective value for the test case.
	 * 
	 * @return a Map<String,String> where each entry key is a concrete variable
	 * and its value is the value for the concrete variable.
	 */
	public Map<String, String> getConcreteInputValues() {
		String concretizationFormula = createConcretizationFormula(convertTestFormulaToPredicate(getTestFormula()));
		return evaluateConcreteVariables(concretizationFormula);
	}



	private Map<String, String> evaluateConcreteVariables(String concretizationFormula) {
		Map<String, String> concreteVariableValues = new HashMap<String, String>();

		ProBApiPredicateEvaluator ev = new ProBApiPredicateEvaluator(getOperationUnderTest(), new HashSet<List<Block>>());
		Map<String, String> solutions = ev.evaluateFormula(concretizationFormula);

		for (String concreteVariable : implementation.getConcreteVariables()) {
			if (solutions.containsKey(concreteVariable)) {
				concreteVariableValues.put(concreteVariable, solutions.get(concreteVariable));
			}
		}

		return concreteVariableValues;
	}



	private String createConcretizationFormula(MyPredicate testFormulaPredicate) {
		String concretizationFormula = "";
		StringBuffer formulaBuffer = new StringBuffer("");
		MyAExistsPredicate testCaseFormulaPredicate = null;
		
		if ((testCaseFormulaPredicate = checksTestCasePredicateValidity(testFormulaPredicate)) != null) {
			formulaBuffer.append("#" + createAbstractVariablesList(testCaseFormulaPredicate) + createConcreteVariablesList());
			formulaBuffer.append("(" + getTestCasePredicate(testCaseFormulaPredicate) + "&" + getImplementationInvariant() + ")");
			concretizationFormula = replaceDeferredSetsForValues(formulaBuffer);
		}
		
		return concretizationFormula;
	}

	

	private String getImplementationInvariant() {
		if(getImplementation().getInvariant() != null) {
			return getImplementation().getInvariant().toString().trim();
		} else {
			return "";
		}
	}



	private String getTestCasePredicate(MyAExistsPredicate testCaseFormulaPredicate) {
		return testCaseFormulaPredicate.getQuantifiedPredicate().toString();
	}



	private String createConcreteVariablesList() {
		StringBuffer concreteVariablesList = new StringBuffer("");

		List<String> concreteVariables = getImplementation().getConcreteVariables();

		for (int i = 0; i < concreteVariables.size(); i++) {
			if (i < concreteVariables.size() - 1) {
				concreteVariablesList.append(concreteVariables.get(i) + ", ");
			} else {
				concreteVariablesList.append(concreteVariables.get(i) + ".");
			}
		}
		
		return concreteVariablesList.toString();
	}



	private String createAbstractVariablesList(MyAExistsPredicate testCaseFormulaPredicate) {
		StringBuffer abstractVariablesList = new StringBuffer("");
		
		for (String var : testCaseFormulaPredicate.getQuantifiedVariables()) {
			abstractVariablesList.append(var + ", ");
		}
		
		return abstractVariablesList.toString();
	}



	private MyAExistsPredicate checksTestCasePredicateValidity(MyPredicate testFormulaPredicate) {
		if(testFormulaPredicate instanceof MyAExistsPredicate) {
			return (MyAExistsPredicate) testFormulaPredicate;
		} else {
			return null;
		}
	}



	private String replaceDeferredSetsForValues(StringBuffer concretizationFormula) {
		Map<String, MyExpression> valueClauseEntries = implementation.getValues().getEntries();

		String concretizationFormulaWithSetsReplaced = concretizationFormula.toString();

		for (Entry<String, MyExpression> valuesEntry : valueClauseEntries.entrySet()) {
			concretizationFormulaWithSetsReplaced = concretizationFormulaWithSetsReplaced.replaceAll(valuesEntry.getKey(), valuesEntry.getValue().toString());
		}

		return concretizationFormulaWithSetsReplaced;
	}



	private MyPredicate convertTestFormulaToPredicate(String testFormula2) {
		MyPredicate predicate = null;
		PParseUnit parseUnit = new ClassicalB(getTestFormula()).getAst().getPParseUnit();

		if (parseUnit instanceof APredicateParseUnit) {
			APredicateParseUnit predicateUnit = (APredicateParseUnit) parseUnit;
			predicate = MyPredicateFactory.convertPredicate(predicateUnit.getPredicate());
		}

		return predicate;
	}



	public String getTestFormula() {
		return testFormula;
	}



	public Operation getOperationUnderTest() {
		return operationUnderTest;
	}



	public Implementation getImplementation() {
		return implementation;
	}

}
