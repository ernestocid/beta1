package testgeneration.coveragecriteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.Invariant;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;

public class PredicateCoverage extends LogicalCoverage {

	
	public PredicateCoverage(Operation operationUnderTest) {
		super(operationUnderTest);
	}

	
	
	/**
	 * This method returns a Set of test formulas which intend to satisfy
	 * the Predicate Coverage criterion. When generating the formulas
	 * special formulas for the precondition, one where the precondition
	 * holds true and another where the precondition holds false (Typing 
	 * clauses are not negated). 
	 * 
	 * For the remainder of the predicates it creates one formula concatenating  
	 * precondition + predicate and another formula concatenating 
	 * precondition + not(predicate). If the operation under test has no
	 * precondition and no predicates in its body a single formula that is
	 * equal to the invariant is added to the set of test formulas.
	 * 
	 * @return a Set for test formulas for Predicate Coverage.
	 */
	public Set<String> getTestFormulas() {
		Set<String> testFormulas = new HashSet<String>();

		MyPredicate precondition = getOperationUnderTest().getPrecondition();
	
		// if operation has no precondition and no predicates in the body
		
		if(precondition == null && getPredicates().isEmpty()) {
			Invariant invariant = getOperationUnderTest().getMachine().getInvariant();

			// but machine has invariant
			
			if(invariant != null) {
				testFormulas.add(invariant.getPredicate().toString());
			}
		}
		
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
		
		negativeTestFormula.append("(");
		negativeTestFormula.append(invariant());
		negativeTestFormula.append(precondition());
		negativeTestFormula.append(clausesRelatedTo(predicate));
		negativeTestFormula.append("not(" + predicate.toString() + ")");
		negativeTestFormula.append(")");
		
		return negativeTestFormula.toString();
	}



	private String createPostiveTestFormulaForPredicate(MyPredicate predicate) {
		StringBuffer positiveTestFormula = new StringBuffer("");
		
		positiveTestFormula.append("(");
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
		
		Set<MyPredicate> preconditionClauses = predicate.getClauses();
		
		String typingClausesFormula = getTypingClausesFromPrecondtion(preconditionClauses);
		String remainderClausesFormula = getNonTypingClausesFromPrecondtion(preconditionClauses);
		
		StringBuffer positiveFormula = new StringBuffer("");
		StringBuffer negativeFormula = new StringBuffer("");
		
		// creating positive formula
		
		positiveFormula.append("(");
		positiveFormula.append(invariant());
		
		if(!typingClausesFormula.equals("")) {
			positiveFormula.append(typingClausesFormula);
		}
		
		if(!remainderClausesFormula.equals("")) {
			positiveFormula.append(" & " + remainderClausesFormula);
		}
		
		positiveFormula.append(")");
		
		// creating negative formula
		
		negativeFormula.append("(");
		negativeFormula.append(invariant());
		
		if(!typingClausesFormula.equals("")) {
			negativeFormula.append(typingClausesFormula);
		}
		
		if(!remainderClausesFormula.equals("")) {
			negativeFormula.append(" & " + "not(" + remainderClausesFormula + ")");
		}
		
		negativeFormula.append(")");
		
		// adding formulas
		
		testFormulas.add(positiveFormula.toString());
		testFormulas.add(negativeFormula.toString());
		
		return testFormulas;
	}



	private String getNonTypingClausesFromPrecondtion(Set<MyPredicate> preconditionClauses) {
		Set<MyPredicate> nonTypingClauses = new HashSet<MyPredicate>();
		
		for(MyPredicate clause : preconditionClauses) {
			if(!clause.isTypingClause()) {
				nonTypingClauses.add(clause);
			}
		}
		
		StringBuffer formula = new StringBuffer("");
		
		int count = 0;
		
		for(MyPredicate nonTypingClause : nonTypingClauses) {
			if(count < nonTypingClauses.size() - 1) {
				formula.append(nonTypingClause.toString() + " & ");
			} else {
				formula.append(nonTypingClause.toString());
			}

			count++;
		}
		
		return formula.toString();
	}



	private String getTypingClausesFromPrecondtion(Set<MyPredicate> preconditionClauses) {
		Set<MyPredicate> typingClauses = new HashSet<MyPredicate>();
		
		for(MyPredicate clause : preconditionClauses) {
			if(clause.isTypingClause()) {
				typingClauses.add(clause);
			}
		}
		
		StringBuffer formula = new StringBuffer("");
		
		int count = 0;
		
		for(MyPredicate typingClause : typingClauses) {
			if(count < typingClauses.size() - 1) {
				formula.append(typingClause.toString() + " & ");
			} else {
				formula.append(typingClause.toString());
			}

			count++;
		}
		
		return formula.toString();
	}



	@Override
	public String getName() {
		return "Predicate Coverage";
	}



	@Override
	public String getAcronym() {
		return "LC-PC";
	}

}
