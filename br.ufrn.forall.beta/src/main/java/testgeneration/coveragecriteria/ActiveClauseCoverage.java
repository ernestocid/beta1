package testgeneration.coveragecriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.Operation;
import parser.decorators.predicates.MyPredicate;

public class ActiveClauseCoverage extends LogicalCoverage {

	
	public static final String TRUE = "1=1";
	public static final String FALSE = "1=2";
	
	
	public ActiveClauseCoverage(Operation operationUnderTest) {
		super(operationUnderTest);
	}

	
	
	/**
	 * This methods creates test formulas that satisfy the Active Clause Coverage (ACC) criterion.
	 * To do this, for each predicate, it defines one clause of the predicate as a major clause at a time.
	 * Once a major clause is chosen we find values for the minor clauses in a way that the major clause
	 * will define the output of the predicate. To find these values for the minor clauses we use an intermediate
	 * formula that is appended to the final formula. We also append to the formula some conditions related to 
	 * reachability for a predicate. If the predicate is nested inside another conditional, the previous conditions 
	 * must be true so the predicate we are trying to cover can be reached.
	 * 
	 * @return a Set with test formulas that satisfy Active Clause Coverage for an operation.
	 */
	public Set<String> getTestFormulas() {
		Set<String> testFormulas = new HashSet<String>();
		Set<MyPredicate> predicatesToCover = getPredicates();
		
		if(predicatesToCover.isEmpty()) {
			testFormulas.addAll(createACCFormulasForInvariant());
		} else {
			for(MyPredicate predicate : getPredicates()) {
				testFormulas.addAll(createACCFormulasForPredicate(predicate));
			}
		}
		
		return testFormulas;
	}



	private Set<String> createACCFormulasForInvariant() {
		Set<String> testFormulas = new HashSet<String>();
		
		if(getOperationUnderTest().getMachine().getInvariant() != null) {
			String testInvariantFormula = getOperationUnderTest().getMachine().getInvariant().getPredicate().toString();
			testFormulas.add(testInvariantFormula);
		}
		
		return testFormulas;
	}



	private Set<String> createACCFormulasForPredicate(MyPredicate predicate) {
		Set<String> testFormulas = new HashSet<String>();
		List<MyPredicate> clauses = new ArrayList<MyPredicate>(predicate.getClauses());
		
		if(clauses.size() > 1) {
			testFormulas.addAll(createACCFormulasForPredicateWithMoreThanOneClause(predicate, clauses));
		} else {
			testFormulas.addAll(createACCFormulasForPredicateWithSingleClause(predicate, clauses));	
		}
		
		return testFormulas;
	}



	private Set<String> createACCFormulasForPredicateWithSingleClause(MyPredicate predicate, List<MyPredicate> clauses) {
		Set<String> testFormulas = new HashSet<String>();
		
		if(operationHasPrecondition() && comparePredicates(predicate, getOperationUnderTest().getPrecondition())) {
			List<String> testFormulaQueue = new ArrayList<String>();
			List<String> testFormulaWithNegationQueue = new ArrayList<String>();
			
			if(!invariant().equals("")) {
				testFormulaQueue.add(invariant());
				testFormulaWithNegationQueue.add(invariant());
			}
			
			if(!getReachabiltyPredicate(predicate).equals("")) {
				testFormulaQueue.add(getReachabiltyPredicate(predicate));
				testFormulaWithNegationQueue.add(getReachabiltyPredicate(predicate));
			}
			
			testFormulaQueue.add("(" + clauses.get(0) + ")");
			testFormulaWithNegationQueue.add("(not(" + clauses.get(0) + "))");
			
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
			
			
			StringBuffer testFormulaWithNegation = new StringBuffer("");
			
			testFormulaWithNegation.append("(");
			
			for(int i = 0; i < testFormulaWithNegationQueue.size(); i++) {
				if(i < testFormulaWithNegationQueue.size() - 1) {
					testFormulaWithNegation.append(testFormulaWithNegationQueue.get(i) + " & ");
				} else {
					testFormulaWithNegation.append(testFormulaWithNegationQueue.get(i));
				}
			}
			
			testFormulaWithNegation.append(")");
			testFormulas.add(testFormulaWithNegation.toString());			

		} else {
			List<String> testFormulaQueue = new ArrayList<String>();
			List<String> testFormulaWithNegationQueue = new ArrayList<String>();
			
			if(!invariant().equals("")) {
				testFormulaQueue.add(invariant());
				testFormulaWithNegationQueue.add(invariant());
			}
			
			if(!precondition().equals("")) {
				testFormulaQueue.add(precondition());
				testFormulaWithNegationQueue.add(precondition());
			}
			
			if(!getReachabiltyPredicate(predicate).equals("")) {
				testFormulaQueue.add(getReachabiltyPredicate(predicate));
				testFormulaWithNegationQueue.add(getReachabiltyPredicate(predicate));
			}
			
			testFormulaQueue.add("(" + clauses.get(0) + ")");
			testFormulaWithNegationQueue.add("(not(" + clauses.get(0) + "))");
			
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
			
			
			StringBuffer testFormulaWithNegation = new StringBuffer("");
			
			testFormulaWithNegation.append("(");
			
			for(int i = 0; i < testFormulaWithNegationQueue.size(); i++) {
				if(i < testFormulaWithNegationQueue.size() - 1) {
					testFormulaWithNegation.append(testFormulaWithNegationQueue.get(i) + " & ");
				} else {
					testFormulaWithNegation.append(testFormulaWithNegationQueue.get(i));
				}
			}
			
			testFormulaWithNegation.append(")");
			testFormulas.add(testFormulaWithNegation.toString());	
		}
		
		return testFormulas;
	}



	private Set<String> createACCFormulasForPredicateWithMoreThanOneClause(MyPredicate predicate, List<MyPredicate> clauses) {
		Set<String> testFormulas = new HashSet<String>();
		
		for(int i = 0; i < clauses.size(); i++) {
			if(!clauses.get(i).isTypingClause()) {
				MyPredicate majorClause = clauses.get(i);
				testFormulas.addAll(createTestFormulasForMajorClause(majorClause, clauses, predicate));
			}
		}
		
		return testFormulas;
	}

	

	private String getReachabiltyPredicate(MyPredicate predicate) {
		StringBuffer reachabilityPredicate = new StringBuffer("");
		List<String> guards = getReachabilityGuards(predicate);

		if(guards.isEmpty()) {
			return "";
		} else {
			for(int i = 0; i < guards.size(); i++) {
				if(i < guards.size() - 1) {
					reachabilityPredicate.append(guards.get(i).toString() + " & ");
				} else {
					reachabilityPredicate.append(guards.get(i).toString());
				}
			}

			return "(" + reachabilityPredicate.toString() + ")";
		}
	}



	private List<String> getReachabilityGuards(MyPredicate predicate) {
		List<String> guards = new ArrayList<String>();
		
		for(MyPredicate guard : getOperationUnderTest().getGuardsThatLeadToPredicate(predicate)) {
			guards.add(guard.toString());
		}
		
		Collections.sort(guards);
		return guards;
	}



	private List<String> createTestFormulasForMajorClause(MyPredicate majorClause, List<MyPredicate> clauses, MyPredicate predicate) {
		List<String> testFormulas = new ArrayList<String>();
		
		if(operationHasPrecondition() && comparePredicates(predicate, getOperationUnderTest().getPrecondition())) {
			
			String majorClauseTrueFormula = createMajorClauseTrueFormulaWithoutPrecondition(majorClause, predicate);
			String majorClauseFalseFormula = createMajorClauseFalseFormulaWithoutPrecondition(majorClause, predicate);
			
			testFormulas.add("(" + majorClauseTrueFormula.toString() + ")");
			testFormulas.add("(" + majorClauseFalseFormula.toString() + ")");
			
		} else {
			
			String majorClauseTrueFormula = createMajorClauseTrueFormulaWithPrecondition(majorClause, predicate);
			String majorClauseFalseFormula = createMajorClauseFalseFormulaWithPrecondition(majorClause, predicate);

			testFormulas.add("(" + majorClauseTrueFormula.toString() + ")");
			testFormulas.add("(" + majorClauseFalseFormula.toString() + ")");
			
		}
		
		return testFormulas;
	}

	

	public String createFormulaToFindValuesForMinorClauses(MyPredicate majorClause, MyPredicate predicate) {
		StringBuffer majorClauseFormula = new StringBuffer("");
		
		String initialFormula = predicate.toString(); 
		
		String trueFormula = initialFormula.replace(majorClause.toString(), TRUE);
		String falseFormula = initialFormula.replace(majorClause.toString(), FALSE);
		
		majorClauseFormula.append("((" + trueFormula + ") <=> not(" + falseFormula + "))");
		
		return majorClauseFormula.toString();
	}
	
	
	
	private String createMajorClauseFalseFormulaWithPrecondition(MyPredicate majorClause, MyPredicate predicate) {
		StringBuffer majorClauseFalseFormula = new StringBuffer("");
		List<String> testFormulaQueue = new ArrayList<String>();
		
		if(!invariant().equals("")) {
			testFormulaQueue.add(invariant());
		}
		
		if(!precondition().equals("")) {
			testFormulaQueue.add(precondition());
		}
		
		testFormulaQueue.add("not(" + majorClause.toString() + ")");
		testFormulaQueue.add(createFormulaToFindValuesForMinorClauses(majorClause, predicate));
		
		for(int i = 0; i < testFormulaQueue.size(); i++) {
			if(i < testFormulaQueue.size() - 1) {
				majorClauseFalseFormula.append(testFormulaQueue.get(i) + " & ");
			} else {
				majorClauseFalseFormula.append(testFormulaQueue.get(i));
			}
		}
		
		return majorClauseFalseFormula.toString();
	}



	private String createMajorClauseTrueFormulaWithPrecondition(MyPredicate majorClause, MyPredicate predicate) {
		StringBuffer majorClauseTrueFormula = new StringBuffer("");
		List<String> testFormulaQueue = new ArrayList<String>();
		
		if(!invariant().equals("")) {
			testFormulaQueue.add(invariant());
		}
		
		if(!precondition().equals("")) {
			testFormulaQueue.add(precondition());
		}
		
		testFormulaQueue.add("(" + majorClause.toString() + ")");
		testFormulaQueue.add(createFormulaToFindValuesForMinorClauses(majorClause, predicate));
		
		for(int i = 0; i < testFormulaQueue.size(); i++) {
			if(i < testFormulaQueue.size() - 1) {
				majorClauseTrueFormula.append(testFormulaQueue.get(i) + " & ");
			} else {
				majorClauseTrueFormula.append(testFormulaQueue.get(i));
			}
		}
		
		return majorClauseTrueFormula.toString();
	}
	
	
	
	private String createMajorClauseFalseFormulaWithoutPrecondition(MyPredicate majorClause, MyPredicate predicate) {
		StringBuffer majorClauseFalseFormula = new StringBuffer("");
		List<String> testFormulaQueue = new ArrayList<String>();
		
		if(!invariant().equals("")) {
			testFormulaQueue.add(invariant());
		}
		
		testFormulaQueue.add("not(" + majorClause.toString() + ")");
		testFormulaQueue.add(createFormulaToFindValuesForMinorClauses(majorClause, predicate));
		
		for(int i = 0; i < testFormulaQueue.size(); i++) {
			if(i < testFormulaQueue.size() - 1) {
				majorClauseFalseFormula.append(testFormulaQueue.get(i) + " & ");
			} else {
				majorClauseFalseFormula.append(testFormulaQueue.get(i));
			}
		}
		
		return majorClauseFalseFormula.toString();
	}



	private String createMajorClauseTrueFormulaWithoutPrecondition(MyPredicate majorClause, MyPredicate predicate) {
		StringBuffer majorClauseTrueFormula = new StringBuffer("");
		List<String> testFormulaQueue = new ArrayList<String>();
		
		if(!invariant().equals("")) {
			testFormulaQueue.add(invariant());
		}
		
		testFormulaQueue.add("(" + majorClause.toString() + ")");
		testFormulaQueue.add(createFormulaToFindValuesForMinorClauses(majorClause, predicate));
		
		for(int i = 0; i < testFormulaQueue.size(); i++) {
			if(i < testFormulaQueue.size() - 1) {
				majorClauseTrueFormula.append(testFormulaQueue.get(i) + " & ");
			} else {
				majorClauseTrueFormula.append(testFormulaQueue.get(i));
			}
		}
		
		return majorClauseTrueFormula.toString();
	}



	@Override
	public String getName() {
		return "Active Clause Coverage";
	}



	@Override
	public String getAcronym() {
		return "LC-ACC";
	}

}
