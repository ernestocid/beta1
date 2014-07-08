package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ASecondProjectionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyASecondProjectionExpression extends MyExpressionDecorator {

	
	
	private ASecondProjectionExpression secondProjectionExpression;
	
	
	public MyASecondProjectionExpression(ASecondProjectionExpression secondProjectionExpression) {
		this.secondProjectionExpression = secondProjectionExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.secondProjectionExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyExpression expression1 = MyExpressionFactory.convertExpression(secondProjectionExpression.getExp1());
		MyExpression expression2 = MyExpressionFactory.convertExpression(secondProjectionExpression.getExp2());
		
		variables.addAll(expression1.getVariables());
		variables.addAll(expression2.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression1 = MyExpressionFactory.convertExpression(secondProjectionExpression.getExp1());
		MyExpression expression2 = MyExpressionFactory.convertExpression(secondProjectionExpression.getExp2());
		
		return "prj2(" + expression1.toString() + ", " + expression2.toString() + ")";
	}
	
}
