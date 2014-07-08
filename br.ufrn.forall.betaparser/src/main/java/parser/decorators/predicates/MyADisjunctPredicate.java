package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ADisjunctPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyADisjunctPredicate extends MyPredicateDecorator {

	
	private ADisjunctPredicate disjunctPredicate;
	
	
	
	public MyADisjunctPredicate(ADisjunctPredicate disjunctPredicate) {
		this.disjunctPredicate = disjunctPredicate;
	}

	
	
	public MyPredicate getLeft() {
		return MyPredicateFactory.convertPredicate(disjunctPredicate.getLeft());
	}
	
	
	
	public MyPredicate getRight() {
		return MyPredicateFactory.convertPredicate(disjunctPredicate.getRight());
	}
	
	
	
	@Override
	public PPredicate getNode() {
		return disjunctPredicate;
	}

	
	
	@Override
	public void createClausesList(Set<MyPredicate> clauses) {
		getLeft().createClausesList(clauses);
		getRight().createClausesList(clauses);
	}

	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		variables.addAll(getLeft().getVariables());
		variables.addAll(getRight().getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		return getLeft().toString() + " or " + getRight().toString();
	}

}
