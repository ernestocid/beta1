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
	public Set<MyPredicate> getClauses() {
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();
		
		clauses.addAll(getLeft().getClauses());
		clauses.addAll(getRight().getClauses());

		return clauses;
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
	
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MyADisjunctPredicate) {
			MyADisjunctPredicate predicate = (MyADisjunctPredicate) obj;
			if(predicate.toString().equals(this.toString())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

}
