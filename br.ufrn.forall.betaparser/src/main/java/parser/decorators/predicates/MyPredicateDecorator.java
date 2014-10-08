package parser.decorators.predicates;


public abstract class MyPredicateDecorator implements MyPredicate {
	
	
	public boolean isTypingClause() {
		return false;
	}
	
	public boolean isInterval() {
		return false;
	}
	
}
