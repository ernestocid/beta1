package criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.Operation;
import parser.decorators.predicates.MyPredicate;

public class PredicateCoverage extends LogicalCoverage {

	
	public PredicateCoverage(Operation operationUnderTest) {
		super(operationUnderTest);
	}

	
	
	/**
	 * This method a Set of test formulas which intend to satisfy
	 * the Predicate Coverage criterion. When generating the formulas
	 * special formulas for the precondition, one where the precondition
	 * holds true and another where the precondition holds false. For the 
	 * remainder of the predicates it creates one formula concatenating  
	 * precondition + predicate and another formula concatenating 
	 * precondition + not(predicate).
	 * 
	 * @return a Set for test formulas for Predicate Coverage.
	 */
	public Set<String> getTestFormulas() {
		Set<String> testFormulas = new HashSet<String>();

		MyPredicate precondition = getOperationUnderTest().getPrecondition();
		
		for(MyPredicate predicate : getPredicates()) {
			boolean operationHasPrecondition = getOperationUnderTest().getPrecondition() != null;
			
			if(operationHasPrecondition && comparePredicates(predicate, precondition)) {
				testFormulas.addAll(createPreconditionFormulas(predicate));
			} else {
				testFormulas.addAll(createPredicateFormulas(predicate));
			}
		}

		return testFormulas;
	}



	private Set<String> createPredicateFormulas(MyPredicate predicate) {
		Set<String> testFormulas = new HashSet<String>();
		
		testFormulas.add(createPostiveTestFormulaForPredicate(predicate));
		testFormulas.add(createNegativeTestFormulaForPredicate(predicate));
		
		return testFormulas;
	}



	private String createNegativeTestFormulaForPredicate(MyPredicate predicate) {
		StringBuffer negativeTestFormula = new StringBuffer("");
		
		negativeTestFormula.append(varListForExistential() + "(");
		negativeTestFormula.append(invariant());
		negativeTestFormula.append(precondition());
		negativeTestFormula.append(clausesRelatedTo(predicate));
		negativeTestFormula.append("not(" + predicate.toString() + ")");
		negativeTestFormula.append(")");
		
		return negativeTestFormula.toString();
	}



	private String createPostiveTestFormulaForPredicate(MyPredicate predicate) {
		StringBuffer positiveTestFormula = new StringBuffer("");
		
		positiveTestFormula.append(varListForExistential() + "(");
		positiveTestFormula.append(invariant());
		positiveTestFormula.append(precondition());
		positiveTestFormula.append(clausesRelatedTo(predicate));
		positiveTestFormula.append("(" + predicate.toString() + ")");
		positiveTestFormula.append(")");
		
		return positiveTestFormula.toString();
	}



	private String clausesRelatedTo(MyPredicate predicate) {
		List<MyPredicate> relatedClauses = new ArrayList<MyPredicate>(getRelatedClauses(predicate));
		StringBuffer relatedClausesFormula = new StringBuffer("");
		
		if(!relatedClauses.isEmpty()) {
			relatedClausesFormula.append("(");
			
			for(int i = 0; i < relatedClauses.size(); i++) {
				if(i < relatedClauses.size() - 1) {
					relatedClausesFormula.append(relatedClauses.get(i).toString() + " & ");
				} else {
					relatedClausesFormula.append(relatedClauses.get(i).toString());
				}
			}
			
			relatedClausesFormula.append(")");
		}
		
		return relatedClausesFormula.toString();
	}



	private Set<String> createPreconditionFormulas(MyPredicate predicate) {
		Set<String> testFormulas = new HashSet<String>();
		
		testFormulas.add(varListForExistential() + "(" + invariant() + "(" + predicate.toString() + "))");
		testFormulas.add(varListForExistential() + "(" + invariant() + "not(" + predicate.toString() + ")" + ")");
		
		return testFormulas;
	}
	
}
