package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AConjunctPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyAConjunctPredicate extends MyPredicateDecorator {

	
	private AConjunctPredicate conjunctPredicate;

	
	public MyAConjunctPredicate(AConjunctPredicate conjunctPredicate) {
		this.conjunctPredicate = conjunctPredicate;
	}

	
	
	public MyPredicate getLeft() {
		return MyPredicateFactory.convertPredicate(conjunctPredicate.getLeft());
	}
	
	
	
	public MyPredicate getRight() {
		return MyPredicateFactory.convertPredicate(conjunctPredicate.getRight());
	}

	
	
	@Override
	public PPredicate getNode() {
		return conjunctPredicate;
	}
	
	
	
	@Override
	public String toString() {
		return getLeft().toString() + " & " + getRight();
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
	
}
