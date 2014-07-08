package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.ASubsetStrictPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyASubsetStrictPredicate extends MyPredicateDecorator {

	
	private ASubsetStrictPredicate subsetStrict;
	private MyExpression leftExpression;
	private MyExpression rightExpression;

	
	public MyASubsetStrictPredicate(ASubsetStrictPredicate subsetStrict) {
		this.subsetStrict = subsetStrict;
		this.leftExpression = MyExpressionFactory.convertExpression(subsetStrict.getLeft());
		this.rightExpression = MyExpressionFactory.convertExpression(subsetStrict.getRight());
	}
	
	
	
	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}
	
	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}

	
	
	@Override
	public PPredicate getNode() {
		return this.subsetStrict;
	}

	
	
	@Override
	public String toString() {
		return getLeftExpression().toString() + " <<: " + getRightExpression().toString();
	}

	
	
	@Override
	public void createClausesList(Set<MyPredicate> clauses) {
		clauses.add(this);
	}

	
	
	@Override
	public Set<String> getVariables() {
		HashSet<String> variables = new HashSet<String>();
		variables.addAll(getLeftExpression().getVariables());
		variables.addAll(getRightExpression().getVariables());
		return variables;
	}
	
	
	
	@Override
	public boolean isTypingClause() {
		if(getRightExpression().isBasicType()) {
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
