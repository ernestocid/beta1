package criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.prob.scripting.Api;
import animation.FormulaEvaluation;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;

public class ActiveClauseCoverage extends LogicalCoverage {

	
	public static final String TRUE = "1=1";
	public static final String FALSE = "1=2";
	
	private Api probApi;

	
	public ActiveClauseCoverage(Operation operationUnderTest, Api probApi) {
		super(operationUnderTest);
		this.probApi = probApi;
	}

	
	
	/**
	 * This methods creates test formulas that satisfy the Active Clause Coverage (ACC) criterion.
	 * To do this, for each predicate, it defines one clause of the predicate as a major clause at a time.
	 * Once a major clause is chosen we find values for the minor clauses in a way that the major clause
	 * will define the output of the predicate. To find this values for the minor clauses we use an intermediate
	 * formula. Then we replace the values for the minor clauses in the actual test formulas.
	 * 
	 * @return a Set with test formulas that satisfy  Active Clause Coverage.
	 */
	public Set<String> getTestFormulas() {
		Set<String> testFormulas = new HashSet<String>();

		for(MyPredicate predicate : getPredicates()) {
			if(!comparePredicates(predicate, getOperationUnderTest().getPrecondition())) {
				testFormulas.addAll(createACCFormulasForPredicate(predicate));
			}
		}
		
		return testFormulas;
	}



	private Set<String> createACCFormulasForPredicate(MyPredicate predicate) {
		Set<String> testFormulas = new HashSet<String>();
		List<MyPredicate> clauses = new ArrayList<MyPredicate>(getPredicateClauses(predicate));
		
		for(int i = 0; i < clauses.size(); i++) {
			MyPredicate majorClause = clauses.get(i);
			testFormulas.addAll(createTestFormulas(majorClause, clauses, predicate));
		}
		
		return testFormulas;
	}



	private Map<String, String> findVariableReplacementsForMinorClauses(String formula) {
		FormulaEvaluation fe = new FormulaEvaluation(getOperationUnderTest(), formula, getProBApi());
		return fe.getFreeVariablesValues();
	}

	

	private Set<String> createTestFormulas(MyPredicate majorClause, List<MyPredicate> clauses, MyPredicate predicate) {
		String formulaToFindValuesForMinorClauses = createFormulaToFindValuesForMinorClauses(majorClause, predicate);
		Map<String, String> variableReplacements = findVariableReplacementsForMinorClauses(formulaToFindValuesForMinorClauses);
		String precondition = getOperationUnderTest().getPrecondition().toString();

		Set<String> testFormulas = new HashSet<String>();
		
		StringBuffer majorClauseTrueFormula = new StringBuffer("");
		majorClauseTrueFormula.append(precondition + " & " + majorClause.toString());
		appendMinorClauses(majorClause, clauses, variableReplacements, majorClauseTrueFormula);
		
		StringBuffer majorClauseFalseFormula = new StringBuffer("");
		majorClauseFalseFormula.append(precondition + " & " + "not(" + majorClause.toString() + ")");
		appendMinorClauses(majorClause, clauses, variableReplacements, majorClauseFalseFormula);
		
		testFormulas.add(majorClauseTrueFormula.toString());
		testFormulas.add(majorClauseFalseFormula.toString());
		
		return testFormulas;
	}



	private void appendMinorClauses(MyPredicate majorClause, List<MyPredicate> clauses, Map<String, String> variableReplacements, StringBuffer initialFormula) {
		for(MyPredicate clause : clauses) {
			if(!comparePredicates(clause, majorClause)) {
				String clauseWithReplacements = replaceValues(clause, variableReplacements);
				initialFormula.append(" & " + clauseWithReplacements);
			}
		}
	}

	

	private String replaceValues(MyPredicate predicate, Map<String, String> values) {
		String predicateFormula = predicate.toString();
		String updatedFormula = null;
		
		for(Entry<String, String> entry : values.entrySet()) {
			updatedFormula = predicateFormula.replace(entry.getKey(), entry.getValue());
		}
		
		return updatedFormula;
	}

	

	public String createFormulaToFindValuesForMinorClauses(MyPredicate majorClause, MyPredicate predicate) {
		StringBuffer majorClauseFormula = new StringBuffer("");
		
		String precondition = getOperationUnderTest().getPrecondition().toString();

		String initialFormula = precondition + " & " + predicate.toString(); 
		
		String trueFormula = initialFormula.replace(majorClause.toString(), TRUE);
		String falseFormula = initialFormula.replace(majorClause.toString(), FALSE);
		
		majorClauseFormula.append("(" + "(" + trueFormula + ")" + " or " + "(" + falseFormula + ")" + ")");
		majorClauseFormula.append(" & ");
		majorClauseFormula.append("not(" + "(" + trueFormula + ")" + " & " + "(" + falseFormula + ")" + ")");
		
		return majorClauseFormula.toString();
	}
	
	
	
	private Api getProBApi() {
		return probApi;
	}

}
