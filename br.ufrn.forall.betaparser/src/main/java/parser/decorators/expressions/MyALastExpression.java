package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ALastExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyALastExpression extends MyExpressionDecorator {

	
	
	private ALastExpression lastExpression;
	
	
	public MyALastExpression(ALastExpression lastExpression) {
		this.lastExpression = lastExpression;
	}

	
	
	@Override
	public PExpression getNode() {
		return this.lastExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyExpression expression = MyExpressionFactory.convertExpression(lastExpression.getExpression());
		variables.addAll(expression.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(lastExpression.getExpression());
		return "last(" + expression.toString() + ")";
	}

}
