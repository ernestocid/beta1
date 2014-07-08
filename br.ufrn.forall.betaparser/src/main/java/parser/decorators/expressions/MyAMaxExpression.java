package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AMaxExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAMaxExpression extends MyExpressionDecorator {

	
	private AMaxExpression maxExpression;
	
	
	public MyAMaxExpression(AMaxExpression maxExpression) {
		this.maxExpression = maxExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.maxExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>(); 
		
		MyExpression expression = MyExpressionFactory.convertExpression(this.maxExpression.getExpression());
		variables.addAll(expression.getVariables());
		
		return variables;
	}

	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.maxExpression.getExpression());
		return "max(" + expression.toString() + ")";
	}
	
}
