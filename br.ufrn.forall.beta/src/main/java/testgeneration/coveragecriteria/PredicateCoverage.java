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
		
		
		Set<MyPredicate> typingClauses = getTypingClases(predicate);
		
		StringBuffer typingFormula = new StringBuffer("");
		
		int countTyping = 0;
		
		for(MyPredicate typingClause : typingClauses) {
			if(countTyping < typingClauses.size() - 1) {
				typingFormula.append(typingClause.toString() + " & ");
			} else {
				typingFormula.append(typingClause.toString());
			}
			countTyping++;
		}
		
		Set<MyPredicate> nonTypingClauses = getNonTypingClases(predicate);
		
		StringBuffer nonTypingFormula = new StringBuffer("");
		
		int countNonTyping = 0;
		
		for(MyPredicate nonTypingClause : nonTypingClauses) {
			if(countNonTyping < nonTypingClauses.size() - 1) {
				nonTypingFormula.append(nonTypingClause.toString() + " & ");
			} else {
				nonTypingFormula.append(nonTypingClause.toString());
			}
			countNonTyping++;
		}
		
		List<String> negativeTestFormulaQueue = new ArrayList<String>();
		
		if(!invariant().equals("")) {
			negativeTestFormulaQueue.add(invariant());
		}

		if(!precondition().equals("")) {
			negativeTestFormulaQueue.add(precondition());
		}

		if(!clausesRelatedTo(predicate).equals("")) {
			negativeTestFormulaQueue.add(clausesRelatedTo(predicate));
		}
		
		if(!typingFormula.toString().equals("")) {
			negativeTestFormulaQueue.add(typingFormula.toString());
		}
		
		if(!nonTypingFormula.toString().equals("")) {
			negativeTestFormulaQueue.add("not(" + nonTypingFormula.toString() + ")");
		}
		
		negativeTestFormula.append("(");
		
		for(int i = 0; i < negativeTestFormulaQueue.size(); i++) {
			if(i < negativeTestFormulaQueue.size() - 1 && !negativeTestFormulaQueue.get(i).equals("")) {
				negativeTestFormula.append(negativeTestFormulaQueue.get(i) + " & ");
			} else {
				negativeTestFormula.append(negativeTestFormulaQueue.get(i));
			}
		}
		
		negativeTestFormula.append(")");
		
		return negativeTestFormula.toString();
	}



	private Set<MyPredicate> getNonTypingClases(MyPredicate predicate) {
		Set<MyPredicate> typingClauses = new HashSet<MyPredicate>();
		
		for(MyPredicate clause : predicate.getClauses()) {
			if(!clause.isTypingClause()) {
				typingClauses.add(clause);
			}
		}
		
		return typingClauses;
	}



	private Set<MyPredicate> getTypingClases(MyPredicate predicate) {
		Set<MyPredicate> typingClauses = new HashSet<MyPredicate>();
		
		for(MyPredicate clause : predicate.getClauses()) {
			if(clause.isTypingClause()) {
				typingClauses.add(clause);
			}
		}
		
		return typingClauses;
	}



	private String createPostiveTestFormulaForPredicate(MyPredicate predicate) {
		StringBuffer positiveTestFormula = new StringBuffer("");
		
		List<String> positiveTestFormulaQueue = new ArrayList<String>();
		
		if(!invariant().equals("")) {
			positiveTestFormulaQueue.add(invariant());
		}

		if(!precondition().equals("")) {
			positiveTestFormulaQueue.add(precondition());
		}

		if(!clausesRelatedTo(predicate).equals("")) {
			positiveTestFormulaQueue.add(clausesRelatedTo(predicate));
		}
		
		positiveTestFormulaQueue.add("(" + predicate.toString() + ")");
		
		positiveTestFormula.append("(");
		
		for(int i = 0; i < positiveTestFormulaQueue.size(); i++) {
			if(i < positiveTestFormulaQueue.size() - 1 && !positiveTestFormulaQueue.get(i).equals("")) {
				positiveTestFormula.append(positiveTestFormulaQueue.get(i) + " & ");
			} else {
				positiveTestFormula.append(positiveTestFormulaQueue.get(i));
			}
		}
		
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
		
		List<String> positiveFormulaQueue = new ArrayList<String>();

		if(!invariant().equals("")) {
			positiveFormulaQueue.add(invariant());
		}
		
		if(!typingClausesFormula.equals("")) {
			positiveFormulaQueue.add(typingClausesFormula);
		}
		
		if(!remainderClausesFormula.equals("")) {
			positiveFormulaQueue.add(remainderClausesFormula);
		}
		
		positiveFormula.append("(");
		
		for(int i = 0; i < positiveFormulaQueue.size(); i++) {
			if(i < positiveFormulaQueue.size() - 1 && !positiveFormulaQueue.get(i).equals("")) {
				positiveFormula.append(positiveFormulaQueue.get(i) + " & ");
			} else if (!positiveFormulaQueue.get(i).equals("")) {
				positiveFormula.append(positiveFormulaQueue.get(i));
			}
		}

		positiveFormula.append(")");
		
		testFormulas.add(positiveFormula.toString());
		
		// creating negative formula
		
		List<String> negativeFormulaQueue = new ArrayList<String>();
		
		if(!invariant().equals("")) {
			negativeFormulaQueue.add(invariant());
		}

		if(!typingClausesFormula.equals("")) {
			negativeFormulaQueue.add(typingClausesFormula);
		}
		
		if(!remainderClausesFormula.equals("")) {
			negativeFormulaQueue.add("not(" + remainderClausesFormula + ")");
		}
		
		negativeFormula.append("(");
		
		for(int i = 0; i < negativeFormulaQueue.size(); i++) {
			if(i < negativeFormulaQueue.size() - 1 && !negativeFormulaQueue.get(i).equals("")) {
				negativeFormula.append(negativeFormulaQueue.get(i) + " & ");
			} else if (!negativeFormulaQueue.get(i).equals("")) {
				negativeFormula.append(negativeFormulaQueue.get(i));
			}
		}
		
		negativeFormula.append(")");
		
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
