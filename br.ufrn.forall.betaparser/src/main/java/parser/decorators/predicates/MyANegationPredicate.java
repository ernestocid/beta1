package parser.decorators.predicates;

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
	public void createClausesList(Set<MyPredicate> clauses) {
		clauses.add(this);
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
