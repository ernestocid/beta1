package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AGreaterEqualPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyAGreaterEqualPredicate extends MyPredicateDecorator {

	
	private AGreaterEqualPredicate greaterEqualPredicate;
	private MyExpression leftExpression;
	private MyExpression rightExpression;
	
	
	public MyAGreaterEqualPredicate(AGreaterEqualPredicate greaterEqualPredicate) {
		this.greaterEqualPredicate = greaterEqualPredicate;
		this.leftExpression = MyExpressionFactory.convertExpression(greaterEqualPredicate.getLeft());
		this.rightExpression = MyExpressionFactory.convertExpression(greaterEqualPredicate.getRight());
	}
	
	

	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}
	
	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}
	
	
	
	@Override
	public PPredicate getNode() {
		return this.greaterEqualPredicate;
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
	
	
	
	@Override
	public String toString() {
		return getLeftExpression().toString() + " >= " + getRightExpression().toString();
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MyAGreaterEqualPredicate) {
			MyAGreaterEqualPredicate predicate = (MyAGreaterEqualPredicate) obj;
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
