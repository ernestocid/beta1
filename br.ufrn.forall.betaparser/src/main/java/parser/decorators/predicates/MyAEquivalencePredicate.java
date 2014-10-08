package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AEquivalencePredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyAEquivalencePredicate extends MyPredicateDecorator {

	
	private AEquivalencePredicate equivalence;
	
	
	public MyAEquivalencePredicate(AEquivalencePredicate equivalence) {
		this.equivalence = equivalence;
	}
	
	
	
	@Override
	public PPredicate getNode() {
		return this.equivalence;
	}
	
	
	
	@Override
	public String toString() {
		return "((" + getLeft().toString() + ")" + " <=> " + "(" + getRight().toString() + "))";	
	}
	
	
	
	public MyPredicate getLeft() {
		return MyPredicateFactory.convertPredicate(equivalence.getLeft());
	}
	
	
	
	public MyPredicate getRight() {
		return MyPredicateFactory.convertPredicate(equivalence.getRight());
	}
	
	
	
	@Override
	public Set<MyPredicate> getClauses() {
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();
		clauses.add(this);
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
	public boolean equals(Object obj) {
		if(obj instanceof MyAEquivalencePredicate) {
			MyAEquivalencePredicate predicate = (MyAEquivalencePredicate) obj;
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
