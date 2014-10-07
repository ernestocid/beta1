package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.ANotSubsetStrictPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyANotSubsetStrictPredicate extends MyPredicateDecorator {

	
	private ANotSubsetStrictPredicate notSubsetStrictPredicate;
	private MyExpression leftExpression;
	private MyExpression rightExpression;
	
	
	
	public MyANotSubsetStrictPredicate(ANotSubsetStrictPredicate notSubsetStrictPredicate) {
		this.notSubsetStrictPredicate = notSubsetStrictPredicate;
		this.leftExpression = MyExpressionFactory.convertExpression(notSubsetStrictPredicate.getLeft());
		this.rightExpression = MyExpressionFactory.convertExpression(notSubsetStrictPredicate.getRight());
	}
	
	
	
	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}
	
	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}
	
	
	
	@Override
	public PPredicate getNode() {
		return this.notSubsetStrictPredicate;
	}

	
	
	@Override
	public String toString() {
		return getLeftExpression().toString() + " /<<: " + getRightExpression().toString();
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
