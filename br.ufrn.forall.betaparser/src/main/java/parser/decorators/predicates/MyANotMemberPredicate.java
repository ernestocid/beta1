package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.ANotMemberPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyANotMemberPredicate extends MyPredicateDecorator {

	
	private ANotMemberPredicate notMemberPredicate;
	private MyExpression leftExpression;
	private MyExpression rightExpression;

	
	public MyANotMemberPredicate(ANotMemberPredicate notMemberPredicate) {
		this.notMemberPredicate = notMemberPredicate;
		this.leftExpression = MyExpressionFactory.convertExpression(notMemberPredicate.getLeft());
		this.rightExpression = MyExpressionFactory.convertExpression(notMemberPredicate.getRight());
	}
	
	
	
	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}

	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}

	
	
	@Override
	public PPredicate getNode() {
		return this.notMemberPredicate;
	}

	
	
	@Override
	public String toString() {
		return getLeftExpression().toString() + " /: " + getRightExpression().toString();
	}

	
	
	@Override
	public void createClausesList(Set<MyPredicate> clauses) {
		clauses.add(this);
	}

	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		variables.addAll(getLeftExpression().getVariables());
		variables.addAll(getRightExpression().getVariables());
		return variables;
	}

}
