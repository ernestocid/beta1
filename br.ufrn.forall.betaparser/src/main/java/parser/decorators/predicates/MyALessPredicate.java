package parser.decorators.predicates;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.ALessPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyALessPredicate extends MyPredicateDecorator {

	
	private ALessPredicate lessPredicate;
	private MyExpression leftExpression;
	private MyExpression rightExpression;
	
	
	public MyALessPredicate(ALessPredicate lessPredicate) {
		this.lessPredicate = lessPredicate;
		this.leftExpression = MyExpressionFactory.convertExpression(lessPredicate.getLeft());
		this.rightExpression = MyExpressionFactory.convertExpression(lessPredicate.getRight());
	}
	
	
	
	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}
	
	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}

	
	
	@Override
	public PPredicate getNode() {
		return lessPredicate;
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
		return getLeftExpression().toString() + " < " + getRightExpression().toString();
	}

}
