package criteria;

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
	 * formula.
	 * 
	 * @return a Set with test formulas that satisfy  Active Clause Coverage.
	 */
	public Set<String> getTestFormulas() {
		Set<String> testFormulas = new HashSet<String>();
		Set<MyPredicate> predicatesToCover = getPredicates();
		
		if(predicatesToCover.isEmpty()) {
			createTestFormulaForInvariant(testFormulas);
		} else {
			for(MyPredicate predicate : getPredicates()) {
				testFormulas.addAll(createACCFormulasForPredicate(predicate));
			}
		}
		
		return testFormulas;
	}



	private void createTestFormulaForInvariant(Set<String> testFormulas) {
		if(getOperationUnderTest().getMachine().getInvariant() != null) {
			String testInvariantFormula = getOperationUnderTest().getMachine().getInvariant().getPredicate().toString();
			testFormulas.add(testInvariantFormula);
		}
	}



	private Set<String> createACCFormulasForPredicate(MyPredicate predicate) {
		Set<String> testFormulas = new HashSet<String>();
		List<MyPredicate> clauses = new ArrayList<MyPredicate>(getPredicateClauses(predicate));
		
		if(clauses.size() > 1) {
			for(int i = 0; i < clauses.size(); i++) {
				MyPredicate majorClause = clauses.get(i);
				testFormulas.addAll(createTestFormulas(majorClause, clauses, predicate));
			}
		} else {
			if(operationHasPrecondition() && comparePredicates(predicate, getOperationUnderTest().getPrecondition())) {
				testFormulas.add(varListForExistential() + "(" + invariant() + getReachabiltyPredicate(predicate) + "(" + clauses.get(0) + "))");
				testFormulas.add(varListForExistential() + "(" + invariant() + getReachabiltyPredicate(predicate) + "(" + "not(" + clauses.get(0) + ")" + "))");
			} else {
				testFormulas.add(varListForExistential() + "(" + invariant() + precondition() + getReachabiltyPredicate(predicate) + "(" + clauses.get(0) + "))");
				testFormulas.add(varListForExistential() + "(" + invariant() + precondition() + getReachabiltyPredicate(predicate) + "(" + "not(" + clauses.get(0) + ")" + "))");	
			}	
		}
		
		return testFormulas;
	}

	

	private String getReachabiltyPredicate(MyPredicate predicate) {
		StringBuffer reachabilityPredicate = new StringBuffer("");
		List<String> guards = getReachabilityGuards(predicate);
		
		int counter = 0;
		
		for(String guard : guards) {
			if(counter < guards.size() - 1) {
				reachabilityPredicate.append(guard.toString() + " & ");
			} else {
				reachabilityPredicate.append(guard.toString());
			}
			counter++;
		}
		
		if(reachabilityPredicate.toString().equals("")) {
			return "";
		} else {
			return "(" + reachabilityPredicate.toString() + ") & ";
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



	private List<String> createTestFormulas(MyPredicate majorClause, List<MyPredicate> clauses, MyPredicate predicate) {
		List<String> testFormulas = new ArrayList<String>();
		
		if(operationHasPrecondition() && comparePredicates(predicate, getOperationUnderTest().getPrecondition())) {
			
			StringBuffer majorClauseTrueFormula = new StringBuffer("");
			
			majorClauseTrueFormula.append(invariant());
			majorClauseTrueFormula.append("(" + majorClause.toString() + ") & ");
			majorClauseTrueFormula.append(createFormulaToFindValuesForMinorClauses(majorClause, predicate));
			
			StringBuffer majorClauseFalseFormula = new StringBuffer("");
			
			majorClauseFalseFormula.append(invariant());
			majorClauseFalseFormula.append("not(" + majorClause.toString() + ")" + " & ");
			majorClauseFalseFormula.append(createFormulaToFindValuesForMinorClauses(majorClause, predicate));
			
			testFormulas.add(varListForExistential() + "(" + majorClauseTrueFormula.toString() + ")");
			testFormulas.add(varListForExistential() + "(" + majorClauseFalseFormula.toString() + ")");
			
		} else {
			
			StringBuffer majorClauseTrueFormula = new StringBuffer("");
			
			majorClauseTrueFormula.append(invariant());
			majorClauseTrueFormula.append(precondition());
			majorClauseTrueFormula.append("(" + majorClause.toString() + ") & ");
			majorClauseTrueFormula.append(createFormulaToFindValuesForMinorClauses(majorClause, predicate));
			
			StringBuffer majorClauseFalseFormula = new StringBuffer("");
			
			majorClauseFalseFormula.append(invariant());
			majorClauseFalseFormula.append(precondition());
			majorClauseFalseFormula.append("not(" + majorClause.toString() + ")" + " & ");
			majorClauseFalseFormula.append(createFormulaToFindValuesForMinorClauses(majorClause, predicate));
			
			testFormulas.add(varListForExistential() + "(" + majorClauseTrueFormula.toString() + ")");
			testFormulas.add(varListForExistential() + "(" + majorClauseFalseFormula.toString() + ")");
			
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

}
