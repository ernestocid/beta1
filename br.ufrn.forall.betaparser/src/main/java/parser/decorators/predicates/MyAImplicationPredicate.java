package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AImplicationPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyAImplicationPredicate extends MyPredicateDecorator {

	
	private AImplicationPredicate implicationPredicate;
	
	
	public MyAImplicationPredicate(AImplicationPredicate implicationPredicate) {
		this.implicationPredicate = implicationPredicate;
	}

	
	
	public MyPredicate getLeft() {
		return MyPredicateFactory.convertPredicate(implicationPredicate.getLeft());
	}
	
	
	
	public MyPredicate getRight() {
		return MyPredicateFactory.convertPredicate(implicationPredicate.getRight());
	}
	
	
	
	@Override
	public PPredicate getNode() {
		return implicationPredicate;
	}
	
	
	
	@Override
	public String toString() {
		return "((" + getLeft().toString() + ")" + " => " + "(" + getRight().toString() + "))";	
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

}
