package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.ANotSubsetPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyANotSubsetPredicate extends MyPredicateDecorator {

	
	private ANotSubsetPredicate notSubsetPredicate;
	private MyExpression leftExpression;
	private MyExpression rightExpression;
	
	
	
	public MyANotSubsetPredicate(ANotSubsetPredicate notSubsetPredicate) {
		this.notSubsetPredicate = notSubsetPredicate;
		this.leftExpression = MyExpressionFactory.convertExpression(notSubsetPredicate.getLeft());
		this.rightExpression = MyExpressionFactory.convertExpression(notSubsetPredicate.getRight());
	}
	
	
	
	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}
	
	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}
	
	
	
	@Override
	public PPredicate getNode() {
		return this.notSubsetPredicate;
	}

	
	
	@Override
	public void createClausesList(Set<MyPredicate> clauses) {
		clauses.add(this);
	}

	
	
	@Override
	public String toString() {
		return getLeftExpression().toString() + " /<: " + getRightExpression().toString();
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		variables.addAll(getLeftExpression().getVariables());
		variables.addAll(getRightExpression().getVariables());
		return variables;
	}
	
}
