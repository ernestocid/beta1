package criteria;

import java.util.HashSet;
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
		
		testFormulas.add(invariant() + precondition() + predicate.toString());
		testFormulas.add(invariant() + precondition() + "not(" + predicate.toString() + ")");
		
		return testFormulas;
	}



	private Set<String> createPreconditionFormulas(MyPredicate predicate) {
		Set<String> testFormulas = new HashSet<String>();
		
		testFormulas.add(invariant() + predicate.toString());
		testFormulas.add(invariant() + "not(" + predicate.toString() + ")");
		
		return testFormulas;
	}
	
}
