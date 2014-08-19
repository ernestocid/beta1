package criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.Operation;
import parser.decorators.predicates.MyPredicate;



public class CombinatorialCoverage extends LogicalCoverage {

	
	public CombinatorialCoverage(Operation operationUnderTest) {
		super(operationUnderTest);
	}

	
	
	/**
	 * This method creates a set of test formulas that satisfy the 
	 * Combinatorial Coverage criterion. The requirements for this criterion are:
	 * for each predicate, we cover all combinations of truth values for its clauses.
	 * 
	 * First it creates separate formulas for the precondition, combining all possible 
	 * truth values for its clauses. Then, for the remainder of the predicates, it creates
	 * all the possible combinations of truth values for the clauses of the predicate and then
	 * append each of the created formulas to the precondition. 
	 * 
	 * @return a Set of test formulas that satisfy Combinatorial Coverage.
	 */
	@Override
	public Set<String> getTestFormulas() {
		Set<String> testFormulas = new HashSet<String>();
		
		MyPredicate precondition = getOperationUnderTest().getPrecondition();
		Set<String> preconditionTestFormulas = createAllCombinationsOfClauses(precondition); 
		testFormulas.addAll(preconditionTestFormulas);
		
		for(MyPredicate predicate : getPredicates()) {
			if(!comparePredicates(predicate, precondition)) {
				Set<String> allCombinationsForPredicate = createAllCombinationsOfClauses(predicate);
				
				for (String combination : allCombinationsForPredicate) {
					testFormulas.add(precondition.toString() + " & " + combination);
				}
			}
		}

		return testFormulas;
	}



	private Set<String> createAllCombinationsOfClauses(MyPredicate precondition) {
		List<MyPredicate> sortedClauses = sortPredicates(getPredicateClauses(precondition));
		List<List<String>> testRequirements = createTestRequirements(sortedClauses);

		AllCombinations<String> allComb = new AllCombinations<String>(testRequirements);
		
		return allComb.getCombinationsAsStrings();
	}



	private List<List<String>> createTestRequirements(List<MyPredicate> predicate) {
		List<List<String>> testRequirements = new ArrayList<List<String>>();
		
		for(MyPredicate clause : predicate) {
			List<String> requirementsForClause = new ArrayList<String>();
			
			requirementsForClause.add(clause.toString());
			
			if(!clause.isTypingClause()) {
				requirementsForClause.add("not(" + clause.toString() + ")");
			}
			
			testRequirements.add(requirementsForClause);
		}
		
		return testRequirements;
	}

}