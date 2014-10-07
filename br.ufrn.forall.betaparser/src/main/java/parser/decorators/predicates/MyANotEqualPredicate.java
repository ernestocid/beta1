package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.ANotEqualPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyANotEqualPredicate extends MyPredicateDecorator {

	
	private ANotEqualPredicate notEqualPredicate;
	private MyExpression leftExpression;
	private MyExpression rightExpression;
	
	
	public MyANotEqualPredicate(ANotEqualPredicate notEqualPredicate) {
		this.notEqualPredicate = notEqualPredicate;
		this.leftExpression = MyExpressionFactory.convertExpression(notEqualPredicate.getLeft());
		this.rightExpression = MyExpressionFactory.convertExpression(notEqualPredicate.getRight());
	}
	
	
	
	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}
	
	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}

	
	
	@Override
	public PPredicate getNode() {
		return notEqualPredicate;
	}

	
	
	@Override
	public String toString() {
		return getLeftExpression().toString() + " /= " + getRightExpression().toString();
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
		variables.addAll(getLeftExpression().getVariables());
		variables.addAll(getRightExpression().getVariables());
		return variables;
	}
	
}
