package testgeneration.coveragecriteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import criteria.AllCombinations;
import parser.Invariant;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;


public class CombinatorialClauseCoverage extends LogicalCoverage {

	
	public CombinatorialClauseCoverage(Operation operationUnderTest) {
		super(operationUnderTest);
	}

	
	/**
	 * This method creates a set of test formulas that satisfy the 
	 * Combinatorial Coverage criterion. The requirements for this criterion are:
	 * for each predicate, we cover all combinations of truth values for its clauses.
	 * 
	 * First it creates separate formulas for the precondition (if the operation has one), 
	 * combining all possible truth values for its clauses. Then, for the remainder of the predicates, 
	 * it creates all the possible combinations of truth values for the clauses of the predicate and then
	 * append each of the created formulas to the invariant and the precondition (if possible). 
	 * 
	 * If the operation has no precondition and the operation has no predicates in its body
	 * then a single formula that is equal to the invariant is added to the set of formulas.
	 * 
	 * @return a Set of test formulas that satisfy Combinatorial Coverage.
	 */
	@Override
	public Set<String> getTestFormulas() {
		Set<String> testFormulas = new HashSet<String>();
		MyPredicate precondition = getOperationUnderTest().getPrecondition();

		// if operation has no precondition and has no predicates inside its body
		
		if(!operationHasPrecondition() && getPredicates().isEmpty()) {
			Invariant invariant = getOperationUnderTest().getMachine().getInvariant();
			
			// but machine has invariant
			
			if(invariant != null) {
				testFormulas.add(invariant.getPredicate().toString());
			}
		}
		
		if(operationHasPrecondition()) {
			testFormulas.addAll(createPreconditionTestFormulas(precondition));
		}
		
		for(MyPredicate predicate : getPredicates()) {
			if(operationHasPrecondition()) {
				testFormulas.addAll(createFormulasForOtherPredicatesWithPrecondition(precondition, predicate));
			} else {
				testFormulas.addAll(createFormulasForOtherPredicatesWithoutPrecondition(predicate));
			}
		}

		return testFormulas;
	}



	private Set<String> createFormulasForOtherPredicatesWithoutPrecondition(MyPredicate predicate) {
		Set<String> testFormulas = new HashSet<String>();
		
		Set<String> allCombinationsForPredicate = createAllCombinationsOfClauses(predicate);
		
		for (String combination : allCombinationsForPredicate) {
			StringBuffer testFormula = new StringBuffer("");
			List<String> testFormulaQueue = new ArrayList<String>();
			
			if(!invariant().equals("")) {
				testFormulaQueue.add(invariant());
			}
			
			testFormulaQueue.add("(" + combination + ")");
			
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
		}
		
		return testFormulas;
	}



	private Set<String> createFormulasForOtherPredicatesWithPrecondition(MyPredicate precondition, MyPredicate predicate) {
		Set<String> testFormulas = new HashSet<String>();
		
		if(!comparePredicates(predicate, precondition)) {
			Set<String> allCombinationsForPredicate = createAllCombinationsOfClauses(predicate);
			
			for (String combination : allCombinationsForPredicate) {
				List<String> testFormulaQueue = new ArrayList<String>();
				
				if(!invariant().equals("")) {
					testFormulaQueue.add(invariant());
				}
				
				if(!precondition().equals("")) {
					testFormulaQueue.add(precondition());
				}
				
				testFormulaQueue.add("(" + combination + ")");
				
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
			}
		}
		
		return testFormulas;
	}



	private Set<String> createPreconditionTestFormulas(MyPredicate precondition) {
		Set<String> testFormulas = new HashSet<String>();
		
		testFormulas.addAll(createFormulasForOtherPredicatesWithoutPrecondition(precondition));
		
		return testFormulas;
	}



	private Set<String> createAllCombinationsOfClauses(MyPredicate precondition) {
		List<MyPredicate> sortedClauses = sortPredicates(precondition.getClauses());
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


	@Override
	public String getName() {
		return "Combinatorial Clause Coverage";
	}


	@Override
	public String getAcronym() {
		return "LC-CoC";
	}

}