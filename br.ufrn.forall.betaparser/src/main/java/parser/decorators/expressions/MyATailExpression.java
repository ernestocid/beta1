package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ATailExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyATailExpression extends MyExpressionDecorator {

	
	private ATailExpression tailExpression;
	
	
	public MyATailExpression(ATailExpression tailExpression) {
		this.tailExpression = tailExpression;
	}



	@Override
	public PExpression getNode() {
		return this.tailExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyExpression expression = MyExpressionFactory.convertExpression(tailExpression.getExpression());
		variables.addAll(expression.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(tailExpression.getExpression());
		return "tail(" + expression.toString() + ")";
	}

}
