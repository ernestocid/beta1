package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AMemberPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyAMemberPredicate extends MyPredicateDecorator {

	
	private AMemberPredicate memberPredicate;
	private MyExpression leftExpression;
	private MyExpression rightExpression; 
	
	
	public MyAMemberPredicate(AMemberPredicate memberPredicate) {
		this.memberPredicate = memberPredicate;
		this.leftExpression = MyExpressionFactory.convertExpression(memberPredicate.getLeft());
		this.rightExpression = MyExpressionFactory.convertExpression(memberPredicate.getRight());
	}

	
	
	@Override
	public PPredicate getNode() {
		return memberPredicate;
	}
	
	
	
	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}

	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}
	
	

	@Override
	public String toString() {
		return getLeftExpression().toString() + " : " + getRightExpression().toString();
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
	public boolean isTypingClause() {
		if (getRightExpression().isBasicType()) {
			return true;
		} else {
			return false;
		}
	}

	
	
	@Override
	public boolean isInterval() {
		if (getRightExpression().isInterval()) {
			return true;
		} else {
			return false;
		}
	}

}
