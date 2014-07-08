package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AFirstExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAFirstExpression extends MyExpressionDecorator {

	
	private AFirstExpression firstExpression;

	
	public MyAFirstExpression(AFirstExpression firstExpression) {
		this.firstExpression = firstExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.firstExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyExpression expression = MyExpressionFactory.convertExpression(firstExpression.getExpression());
		variables.addAll(expression.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(firstExpression.getExpression());
		return "first(" + expression.toString() + ")";
	}
	
}
