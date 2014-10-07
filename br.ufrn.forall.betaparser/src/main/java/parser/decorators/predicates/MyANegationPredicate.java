package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ANegationPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyANegationPredicate extends MyPredicateDecorator {

	
	private ANegationPredicate negationPredicate;
	
	
	public MyANegationPredicate(ANegationPredicate negationPredicate) {
		this.negationPredicate = negationPredicate;
	}

	
	
	@Override
	public PPredicate getNode() {
		return negationPredicate;
	}

	
	
	@Override
	public Set<MyPredicate> getClauses() {
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();
		clauses.add(this);
		return clauses;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		MyPredicate myPredicate = MyPredicateFactory.convertPredicate(negationPredicate.getPredicate());
		return myPredicate.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		MyPredicate myPredicate = MyPredicateFactory.convertPredicate(negationPredicate.getPredicate());
		return "not("+ myPredicate.toString() + ")";
	}

}
