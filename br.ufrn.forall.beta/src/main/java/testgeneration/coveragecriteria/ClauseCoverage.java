package testgeneration.coveragecriteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.Invariant;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;

public class ClauseCoverage extends LogicalCoverage {

	public ClauseCoverage(Operation operationUnderTest) {
		super(operationUnderTest);
	}



	/**
	 * This method create a Set of test formulas that satisfy the Clause
	 * Coverage criterion. It creates special formulas for the precondition
	 * clauses first (if the operation has a precondition): one formula where
	 * all clauses are true and then formulas negating each precondition clause
	 * individually. Then, for the remainder of the clauses, it creates one
	 * formula in the form of invariant + precondition + clause and another one
	 * in the form of invariant + precondition + not(clause). Both invariant and
	 * precondition are only added to the formula when available. If a clause is
	 * a typing clause we do not try to generate a false value for it. If the operation
	 * has no precondition and no clauses in its body a single formula that is equal
	 * to the invariant is added to the set of test formulas.
	 * 
	 * @return a Set of test formulas that satisfy Clause Coverage.
	 */
	public Set<String> getTestFormulas() {
		Set<String> testFormulas = new HashSet<String>();

		MyPredicate precondition = getOperationUnderTest().getPrecondition();

		// if operation has no precondition and no clauses in its body
		
		if(!operationHasPrecondition() && getClauses().isEmpty()) {
			Invariant invariant = getOperationUnderTest().getMachine().getInvariant();
			
			// but machine has invariant
			
			if(invariant != null) {
				testFormulas.add(invariant.getPredicate().toString());
			}
		}
		
		if (operationHasPrecondition()) {
			testFormulas.addAll(createTestFormulasForPrecondition(precondition));
		}

		for (MyPredicate clause : getClauses()) {
			if (operationHasPrecondition()) {
				testFormulas.addAll(createFormulasForOtherClausesWithPrecondition(precondition, clause));
			} else {
				testFormulas.addAll(createFormulasForOtherClausesWithoutPrecondition(clause));
			}
		}

		return testFormulas;
	}



	private Set<String> createFormulasForOtherClausesWithoutPrecondition(MyPredicate clause) {
		Set<String> testFormulas = new HashSet<String>();

		List<String> testFormulaQueue = new ArrayList<String>();
		
		if(!invariant().equals("")) {
			testFormulaQueue.add(invariant());
		}
		
		if(!getTypesFormulaWithoutPrecondition(clause).equals("")) {
			testFormulaQueue.add("(" + getTypesFormulaWithoutPrecondition(clause) + ")");
		}
		
		testFormulaQueue.add("(" + clause.toString() + ")");
		
		StringBuffer testFormula = new StringBuffer("");
		
		testFormula.append("(");
		
		for(int i = 0; i < testFormulaQueue.size(); i++) {
			if(i < testFormulaQueue.size() - 1) {
				testFormula.append(testFormulaQueue.get(i) + " & ");
			} else {
				testFormula.append(testFormulaQueue.get(i));
			}
		}
		
		testFormula.append(")");
		
		testFormulas.add(testFormula.toString());
		
		
		if (!clause.isTypingClause()) {
			List<String> testFormulaWithNegationQueue = new ArrayList<String>();
			
			if(!invariant().equals("")) {
				testFormulaWithNegationQueue.add(invariant());
			}
			
			if(!getTypesFormulaWithoutPrecondition(clause).equals("")) {
				testFormulaWithNegationQueue.add("(" + getTypesFormulaWithoutPrecondition(clause) + ")");
			}
			
			testFormulaWithNegationQueue.add("not(" + clause.toString() + ")");
			
			StringBuffer testFormulaWithOneClauseNegated = new StringBuffer("");
			
			testFormulaWithOneClauseNegated.append("(");
			
			for(int i = 0; i < testFormulaWithNegationQueue.size(); i++) {
				if(i < testFormulaWithNegationQueue.size() - 1) {
					testFormulaWithOneClauseNegated.append(testFormulaWithNegationQueue.get(i) + " & ");
				} else {
					testFormulaWithOneClauseNegated.append(testFormulaWithNegationQueue.get(i));
				}
			}
			
			testFormulaWithOneClauseNegated.append(")");
			
			testFormulas.add(testFormulaWithOneClauseNegated.toString());
		}

		return testFormulas;
	}



	private Set<String> createFormulasForOtherClausesWithPrecondition(MyPredicate precondition, MyPredicate clause) {
		Set<String> testFormulas = new HashSet<String>();

		if (!clauseBelongsToPredicate(clause, precondition)) {
			List<String> testFormulaQueue = new ArrayList<String>();
			
			if(!invariant().equals("")) {
				testFormulaQueue.add(invariant());
			}
			
			if(!precondition().equals("")) {
				testFormulaQueue.add(precondition());
			}
			
			if(!getTypesFormulaWithPrecondition(precondition, clause).equals("")) {
				testFormulaQueue.add("(" + getTypesFormulaWithPrecondition(precondition, clause) + ")");
			}
			
			testFormulaQueue.add("(" + clause.toString() + ")");
			
			StringBuffer testFormula = new StringBuffer("");
			
			testFormula.append("(");
			
			for(int i = 0; i < testFormulaQueue.size(); i++) {
				if(i < testFormulaQueue.size() - 1) {
					testFormula.append(testFormulaQueue.get(i) + " & ");
				} else {
					testFormula.append(testFormulaQueue.get(i));
				}
			}
			
			testFormula.append(")");
			
			testFormulas.add(testFormula.toString());
			
			if (!clause.isTypingClause()) {
				List<String> testFormulaWithNegationQueue = new ArrayList<String>();
				
				if(!invariant().equals("")) {
					testFormulaWithNegationQueue.add(invariant());
				}
				
				if(!precondition().equals("")) {
					testFormulaWithNegationQueue.add(precondition());
				}
				
				if(!getTypesFormulaWithPrecondition(precondition, clause).equals("")) {
					testFormulaWithNegationQueue.add("(" + getTypesFormulaWithPrecondition(precondition, clause) + ")");
				}
				
				testFormulaWithNegationQueue.add("not(" + clause.toString() + ")");
				
				StringBuffer testFormulaWithOneClauseNegated = new StringBuffer("");
				
				testFormulaWithOneClauseNegated.append("(");
				
				for(int i = 0; i < testFormulaWithNegationQueue.size(); i++) {
					if(i < testFormulaWithNegationQueue.size() - 1) {
						testFormulaWithOneClauseNegated.append(testFormulaWithNegationQueue.get(i) + " & ");
					} else {
						testFormulaWithOneClauseNegated.append(testFormulaWithNegationQueue.get(i));
					}
				}
				
				testFormulaWithOneClauseNegated.append(")");
				
				testFormulas.add(testFormulaWithOneClauseNegated.toString());
			}
		}

		return testFormulas;
	}



	private Set<String> createTestFormulasForPrecondition(MyPredicate precondition) {
		Set<String> testFormulas = new HashSet<String>();

		List<String> testFormulaQueue = new ArrayList<String>();
		
		if(!invariant().equals("")) {
			testFormulaQueue.add(invariant());
		}
		
		testFormulaQueue.add("(" + precondition.toString() + ")");
		
		StringBuffer allPositiveFormula = new StringBuffer("");
		
		allPositiveFormula.append("(");
		
		for(int i = 0; i < testFormulaQueue.size(); i++) {
			if(i < testFormulaQueue.size() - 1) {
				allPositiveFormula.append(testFormulaQueue.get(i) + " & ");
			} else {
				allPositiveFormula.append(testFormulaQueue.get(i));
			}
		}
		
		allPositiveFormula.append(")");
		
		testFormulas.add(allPositiveFormula.toString());
		
		// add test formulas with one clause negated each

		List<MyPredicate> sortedPreconditionClauses = sortPredicates(precondition.getClauses());

		
		for (int i = 0; i < sortedPreconditionClauses.size(); i++) {
			
			List<String> testFormulaWithNegationQueue = new ArrayList<String>();

			if(!invariant().equals("")) {
				testFormulaWithNegationQueue.add(invariant());
			}
			
			testFormulaWithNegationQueue.add("(" + createTestFormulaNegatingAClause(sortedPreconditionClauses, i)  + ")");
			
			StringBuffer formulaWithOneClauseNegated = new StringBuffer("");
			
			formulaWithOneClauseNegated.append("(");
			
			for(int j = 0; j < testFormulaWithNegationQueue.size(); j++) {
				if(j < testFormulaWithNegationQueue.size() - 1) {
					formulaWithOneClauseNegated.append(testFormulaWithNegationQueue.get(j) + " & ");
				} else {
					formulaWithOneClauseNegated.append(testFormulaWithNegationQueue.get(j));
				}
			}
			
			formulaWithOneClauseNegated.append(")");
			
			testFormulas.add(formulaWithOneClauseNegated.toString());
		}

		return testFormulas;
	}



	private String createTestFormulaNegatingAClause(List<MyPredicate> clauses, int clauseIndex) {
		StringBuffer testFomula = new StringBuffer("");

		for (int i = 0; i < clauses.size(); i++) {
			boolean clauseIsATypingClause = clauses.get(i).isTypingClause();

			if (i == clauseIndex && !clauseIsATypingClause) {
				testFomula.append("not(" + clauses.get(i).toString() + ")");
			} else {
				testFomula.append(clauses.get(i).toString());
			}

			if (i < clauses.size() - 1) {
				testFomula.append(" & ");
			}
		}

		return testFomula.toString();
	}

	
	
	private String getTypesFormulaWithPrecondition(MyPredicate precondition, MyPredicate currentClause) {
		Set<MyPredicate> clauses = getClauses();
		Set<MyPredicate> typingClausesThatAreNotFromPrecondition = new HashSet<MyPredicate>();
		
		for(MyPredicate clause : clauses) {
			if (!clauseBelongsToPredicate(clause, precondition) && !clause.equals(currentClause) && clause.isTypingClause()) {
				typingClausesThatAreNotFromPrecondition.add(clause);
			}
		}
		
		StringBuffer typesFormula = new StringBuffer("");
		
		int count = 0;
		
		for(MyPredicate clause : typingClausesThatAreNotFromPrecondition) {
			
			if(count < typingClausesThatAreNotFromPrecondition.size() - 1) {
				typesFormula.append(clause.toString() + " & ");
			} else {
				typesFormula.append(clause.toString());
			}
			
			count++;
		}
		
		return typesFormula.toString();
	}
	
	
	
	private String getTypesFormulaWithoutPrecondition(MyPredicate currentClause) {
		Set<MyPredicate> clauses = getClauses();
		Set<MyPredicate> typingClausesThatAreNotFromPrecondition = new HashSet<MyPredicate>();
		
		for(MyPredicate clause : clauses) {
			if (!clause.equals(currentClause) && clause.isTypingClause()) {
				typingClausesThatAreNotFromPrecondition.add(clause);
			}
		}
		
		StringBuffer typesFormula = new StringBuffer("");
		
		int count = 0;
		
		for(MyPredicate clause : typingClausesThatAreNotFromPrecondition) {
			
			if(count < typingClausesThatAreNotFromPrecondition.size() - 1) {
				typesFormula.append(clause.toString() + " & ");
			} else {
				typesFormula.append(clause.toString());
			}
			
			count++;
		}
		
		return typesFormula.toString();
	}

	

	@Override
	public String getName() {
		return "Clause Coverage";
	}



	@Override
	public String getAcronym() {
		return "LC-CC";
	}
}
