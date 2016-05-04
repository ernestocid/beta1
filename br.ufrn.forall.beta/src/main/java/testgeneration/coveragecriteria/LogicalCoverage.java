package testgeneration.coveragecriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import de.be4.classicalb.core.parser.node.APredicateParseUnit;
import de.be4.classicalb.core.parser.node.PParseUnit;
import de.prob.animator.domainobjects.ClassicalB;
import parser.Definitions;
import parser.Operation;
import parser.decorators.expressions.MyExpression;
import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;
import testgeneration.Block;

public abstract class LogicalCoverage extends CoverageCriterion {

	private Operation operationUnderTest;



	public abstract Set<String> getTestFormulas();



	public LogicalCoverage(Operation operationUnderTest) {
		this.operationUnderTest = operationUnderTest;
	}



	public Operation getOperationUnderTest() {
		return this.operationUnderTest;
	}



	/**
	 * This method returns the String representation of the machine's invariant.
	 * If the machine has no invariant it returns an empty String "".
	 * 
	 * @return String representation of the machine's invariant.
	 */
	public String getMachineInvariant() {
//		boolean machineHasInvariant = this.operationUnderTest.getMachine().getInvariant() != null;

		List<String> invariantsFromAllMachines = new ArrayList<String>();
		
		for(MyPredicate p : this.operationUnderTest.getMachine().getCondensedInvariantFromAllMachines()) {
			invariantsFromAllMachines.add(p.toString());
		}

		Collections.sort(invariantsFromAllMachines);
		
		StringBuffer invariantText = new StringBuffer("");
		
		for (int i = 0; i < invariantsFromAllMachines.size() - 1; i++) {
			invariantText.append(invariantsFromAllMachines.get(i).toString() + " & ");
		}
		
		if (!invariantsFromAllMachines.isEmpty()) {
			invariantText.append(invariantsFromAllMachines.get(invariantsFromAllMachines.size() - 1));
		}

//		if (machineHasInvariant) {
//			Invariant invariant = getOperationUnderTest().getMachine().getInvariant();
//			invariantText.append(invariant.toString().trim());
//		}

		return invariantText.toString();
	}



	/**
	 * This method return the String representation of the operation's
	 * precondition. If the operation has no precondition it returns an empty
	 * String "".
	 * 
	 * @return String representation of the operation's precondition.
	 */
	public String getOperationPrecondition() {
		boolean operationHasPrecondition = getOperationUnderTest().getPrecondition() != null;

		StringBuffer preconditionText = new StringBuffer("");

		if (operationHasPrecondition) {
			MyPredicate precondition = getOperationUnderTest().getPrecondition();
			preconditionText.append(precondition.toString());
		}

		return preconditionText.toString();
	}



	/**
	 * This methods builds a Set containing all the predicates related to the
	 * operation under test. This predicates are found on: - Operation's
	 * precondition; - If-Elsif-Else statements.
	 * 
	 * @return a Set containing all the predicates related to the operation
	 *         under test.
	 */
	public Set<MyPredicate> getPredicates() {
		boolean operationHasPrecondition = getOperationUnderTest().getPrecondition() != null;

		Set<MyPredicate> predicates = new HashSet<MyPredicate>();

		if (operationHasPrecondition) {
			predicates.add(getOperationUnderTest().getPrecondition());
		}

		predicates.addAll(getOperationUnderTest().getPredicatesFromOperationBody());

		return predicates;
	}



	/**
	 * This method organizes the predicates in an ordered List. It uses a simple
	 * bubblesort algorithm to order the predicates according to their String
	 * values.
	 * 
	 * @return a List containing the predicates in order.
	 */
	public List<MyPredicate> getOrderedPredicates() {
		return sortPredicates(getPredicates());
	}



	/**
	 * This method builds a set containing all the clauses for the set of
	 * predicates obtained using the method getPredicates(). The result is a
	 * union of all the clauses that belong to these predicates.
	 * 
	 * @return a Set containing the union of all the clauses that compose the
	 *         predicates related to the operation under test.
	 */
	public Set<MyPredicate> getClauses() {
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();

		for (MyPredicate predicate : getPredicates()) {
			clauses.addAll(predicate.getClauses());
		}

		return clauses;
	}



	/**
	 * This methods returns the clauses related to the operation under test in
	 * an ordered List. It uses a simple bubblesort algorithm to sort the
	 * predicates according to their String values.
	 * 
	 * @return a List containing the clauses in order.
	 */
	public List<MyPredicate> getOrderedClauses() {
		return sortPredicates(getClauses());
	}



	/**
	 * This methods receives a Set of MyPredicate and sorts them in a ordered
	 * List. It uses a simple bubblesort algorithm that orders the predicates
	 * according to their String values.
	 * 
	 * @param a
	 *            Set of MyPredicate that you want to sort.
	 * @return a List containing the predicates in order.
	 */
	public List<MyPredicate> sortPredicates(Set<MyPredicate> predicates) {
		List<MyPredicate> sortedPredicates = new ArrayList<MyPredicate>(predicates);
		bubblesort(sortedPredicates);
		return sortedPredicates;
	}



	/**
	 * This methods checks if a clause belongs to a predicate.
	 * 
	 * @param clause
	 * @param predicate
	 * @return returns true if the clause belongs to the predicate or false if
	 *         otherwise.
	 */
	public boolean clauseBelongsToPredicate(MyPredicate clause, MyPredicate predicate) {
		for (MyPredicate predicateClause : predicate.getClauses()) {
			if (clause.toString().equals(predicateClause.toString())) {
				return true;
			}
		}

		return false;
	}



	/**
	 * This methods compares to predicates by its String values.
	 * 
	 * @param predicateOne
	 * @param predicateTwo
	 * @return returns true if the predicates are equal or false if otherwise.
	 */
	public boolean comparePredicates(MyPredicate predicateOne, MyPredicate predicateTwo) {
		return predicateOne.toString().equals(predicateTwo.toString());
	}



	/**
	 * This method returns all clauses that are related to the clause passed as
	 * a parameter. Clauses are related if they mention the same variable. This
	 * is necessary when covering predicates within nested substitutions. A
	 * predicate in a substitution might need another predicate in a outer
	 * substitution to make sense when creating a test formula.
	 * 
	 * @param predicate
	 *            a MyPredicate for which we want to find related clauses.
	 * @return a Set of MyPredicate containing all the predicates related to the
	 *         given predicate.
	 */
	public Set<MyPredicate> getRelatedClauses(MyPredicate predicate) {
		Set<MyPredicate> relatedClauses = getOperationUnderTest().getGuardsThatLeadToPredicate(predicate);

		return relatedClauses;
	}



	private void bubblesort(List<MyPredicate> orderedPredicates) {
		MyPredicate elementOne;
		MyPredicate elementTwo;
		boolean elementOneIsGreaterThanElementTwo;

		for (int reps = 1; reps < orderedPredicates.size(); reps++) {
			for (int i = 0; i < orderedPredicates.size() - reps; i++) {
				elementOne = orderedPredicates.get(i);
				elementTwo = orderedPredicates.get(i + 1);
				elementOneIsGreaterThanElementTwo = elementOne.toString().compareTo(elementTwo.toString()) > 0;

				if (elementOneIsGreaterThanElementTwo) {
					orderedPredicates.set(i, elementTwo);
					orderedPredicates.set(i + 1, elementOne);
				}
			}
		}
	}



	protected String invariant() {
		if (getMachineInvariant().equals("")) {
			return "";
		} else {
			return "(" + getMachineInvariant() + ")";
		}
	}



	protected String precondition() {
		if (getOperationPrecondition().equals("")) {
			return "";
		} else {
			return "(" + getOperationPrecondition() + ")";
		}
	}



	protected boolean operationHasPrecondition() {
		if (getOperationUnderTest().getPrecondition() != null) {
			return true;
		} else {
			return false;
		}
	}



	@Override
	public Set<List<Block>> getCombinationsAsListsOfBlocks() {
		Set<List<Block>> combinations = new HashSet<List<Block>>();

		Set<String> formulas = getTestFormulas();

		for (String formula : formulas) {
			List<Block> comb = new ArrayList<Block>();

			PParseUnit parseUnit = new ClassicalB(formula).getAst().getPParseUnit();

			if (parseUnit instanceof APredicateParseUnit) {
				APredicateParseUnit predUnit = (APredicateParseUnit) parseUnit;
				MyPredicate myPredicate = MyPredicateFactory.convertPredicate(predUnit.getPredicate());

				Set<MyPredicate> clauses = myPredicate.getClauses();

				for (MyPredicate clause : clauses) {
					comb.add(new Block(clause.toString(), false));
				}

			}

			if (!comb.isEmpty()) {
				combinations.add(comb);
			}
		}

		return combinations;
	}
	
	
	
	protected Set<String> expandDefinitionsTestFormulas(Set<String> testFormulas) {
		Definitions definitions = getOperationUnderTest().getMachine().getDefinitions();
		
		if(definitions != null) {
			Set<String> expandedFormulas = new HashSet<String>();
			
			for(String formula : testFormulas) {
				String expandedFormula = expandDefinitions(formula, definitions);
				expandedFormulas.add(expandedFormula);
			}	
			
			return expandedFormulas;
		}
		
		return testFormulas;
	}


	
	private String expandDefinitions(String formula, Definitions definitions) {
		if(definitions != null) {
			String oldFormula = "";
			String newFormula = formula;
			
			while(!oldFormula.equals(newFormula)) {
				oldFormula = newFormula;
				
				for(Entry<String, MyExpression> entry : definitions.getDefinitions().entrySet()) {
					newFormula = newFormula.replace(entry.getKey(), entry.getValue().toString());
				}
				
			}

			return newFormula;
		}
		
		return formula;
	}
}