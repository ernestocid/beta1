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

public class ConcretizeTestCaseInputs {

	private String testFormula;
	private Operation operationUnderTest;
	private Implementation implementation;



	public ConcretizeTestCaseInputs(String testFormula, Operation operationUnderTest, Implementation implementation) {
		this.testFormula = testFormula;
		this.operationUnderTest = operationUnderTest;
		this.implementation = implementation;
	}



	public Map<String, String> getConcreteInputValues() {
		MyPredicate testPredicate = convertTestFormulaToPredicate(getTestFormula());
		String concretizationFormula = createConcretizationFormula(testPredicate);

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



	private String createConcretizationFormula(MyPredicate testPredicate) {
		StringBuffer concretizationFormula = new StringBuffer("");

		if (testPredicate instanceof MyAExistsPredicate) {
			MyAExistsPredicate exists = (MyAExistsPredicate) testPredicate;

			concretizationFormula.append("#");

			for (String var : exists.getQuantifiedVariables()) {
				concretizationFormula.append(var + ", ");
			}

			List<String> concreteVariables = getImplementation().getConcreteVariables();

			for (int i = 0; i < concreteVariables.size(); i++) {
				if (i < concreteVariables.size() - 1) {
					concretizationFormula.append(concreteVariables.get(i) + ", ");
				} else {
					concretizationFormula.append(concreteVariables.get(i) + ".");
				}
			}

			Invariant implementationInvariant = implementation.getInvariant();

			concretizationFormula.append("(");
			concretizationFormula.append(exists.getQuantifiedPredicate().toString());
			concretizationFormula.append(" & ");
			concretizationFormula.append(implementationInvariant.toString().trim());
			concretizationFormula.append(")");
		}

		return replaceDeferredSetsForValues(concretizationFormula);
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
