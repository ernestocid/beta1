package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AEqualPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyAEqualPredicate extends MyPredicateDecorator {

	
	private AEqualPredicate equalPredicate;
	private MyExpression leftExpression;
	private MyExpression rightExpression;
	
	
	public MyAEqualPredicate(AEqualPredicate equalPredicate) {
		this.equalPredicate = equalPredicate;
		this.leftExpression = MyExpressionFactory.convertExpression(equalPredicate.getLeft());
		this.rightExpression = MyExpressionFactory.convertExpression(equalPredicate.getRight());
	}

	
	
	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}
	
	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}

	
	
	@Override
	public PPredicate getNode() {
		return this.equalPredicate;
	}
	
	
	
	@Override
	public String toString() {
		return getLeftExpression().toString() + " = " + getRightExpression().toString();
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
	public boolean equals(Object obj) {
		if(obj instanceof MyAEqualPredicate) {
			MyAEqualPredicate predicate = (MyAEqualPredicate) obj;
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
