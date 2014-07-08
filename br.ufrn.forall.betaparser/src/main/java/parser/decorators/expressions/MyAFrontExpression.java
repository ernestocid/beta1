package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AFrontExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAFrontExpression extends MyExpressionDecorator {

	
	private AFrontExpression frontExpression;
	
	
	public MyAFrontExpression(AFrontExpression frontExpression) {
		this.frontExpression = frontExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.frontExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyExpression expression = MyExpressionFactory.convertExpression(frontExpression.getExpression());
		variables.addAll(expression.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(frontExpression.getExpression());
		return "front(" + expression.toString() + ")";
	}

}
