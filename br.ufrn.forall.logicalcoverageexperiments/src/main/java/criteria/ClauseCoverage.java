package criteria;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.Operation;
import parser.decorators.predicates.MyPredicate;

public class ClauseCoverage extends LogicalCoverage {

	
	public ClauseCoverage(Operation operationUnderTest) {
		super(operationUnderTest);
	}

	
	
	/**
	 * This method create a Set of test formulas that satisfy the Clause Coverage criterion.
	 * It creates special formulas for the precondition clauses first: one formula where all 
	 * clauses are true and then formulas negating each precondition clause individually. Then,
	 * for the remainder of the clauses, it creates one formula in the form of precondition + clause
	 * and another one in the form of precondition + not(clause). If a clause is a typing clause 
	 * we do not try to generate a false value for it.
	 *  
	 * @return a Set of test formulas that satisfy Clause Coverage.
	 */
	public Set<String> getTestFormulas() {
		Set<String> testFormulas = new HashSet<String>();
		
		MyPredicate precondition = getOperationUnderTest().getPrecondition();
		testFormulas.addAll(createTestFormulasForPrecondition(precondition));
		
		for(MyPredicate clause : getClauses()) {
			if(!clauseBelongsToPredicate(clause, precondition)) {
				testFormulas.add(precondition.toString() + " & " + clause.toString());

				if(!clause.isTypingClause()) {
					testFormulas.add(precondition.toString() + " & " + "not(" + clause.toString() + ")");
				}
			}
		}
		
		return testFormulas;
	}



	private Set<String> createTestFormulasForPrecondition(MyPredicate precondition) {
		Set<String> testFormulas = new HashSet<String>();
		
		testFormulas.add(precondition.toString());
		
		Set<MyPredicate> preconditionClauses = getPredicateClauses(precondition);
		List<MyPredicate> sortedPreconditionClauses = sortPredicates(preconditionClauses);
		
		String testFormula;
		
		for(int i = 0; i < sortedPreconditionClauses.size(); i++) {
			testFormula = createTestFormulaNegatingAClause(sortedPreconditionClauses, i);
			testFormulas.add(testFormula);
		}
		
		return testFormulas;
	}



	private String createTestFormulaNegatingAClause(List<MyPredicate> clauses, int clauseIndex) {
		StringBuffer testFomula = new StringBuffer("");
		
		for (int i = 0; i < clauses.size(); i++) {
			boolean clauseIsATypingClause = clauses.get(i).isTypingClause();
			
			if(i == clauseIndex && !clauseIsATypingClause) { 
				testFomula.append("not(" + clauses.get(i).toString() + ")");
			} else {
				testFomula.append(clauses.get(i).toString());
			}
			
			if(i < clauses.size() - 1) {
				testFomula.append(" & ");
			}
		}
		
		return testFomula.toString();
	}

}
