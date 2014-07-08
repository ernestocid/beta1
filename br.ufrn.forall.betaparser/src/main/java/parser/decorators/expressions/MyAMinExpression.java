package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AMinExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAMinExpression extends MyExpressionDecorator {

	
	private AMinExpression minExpression;
	
	
	public MyAMinExpression(AMinExpression minExpression) {
		this.minExpression = minExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.minExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>(); 
		
		MyExpression expression = MyExpressionFactory.convertExpression(this.minExpression.getExpression());
		variables.addAll(expression.getVariables());
		
		return variables;
	}

	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.minExpression.getExpression());
		return "min(" + expression.toString() + ")";
	}
	
}
