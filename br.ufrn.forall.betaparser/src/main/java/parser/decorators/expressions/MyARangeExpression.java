package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ARangeExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyARangeExpression extends MyExpressionDecorator {

	
	private ARangeExpression range;


	public MyARangeExpression(ARangeExpression range) {
		this.range = range;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.range;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyExpression expression = MyExpressionFactory.convertExpression(this.range.getExpression());
		variables.addAll(expression.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.range.getExpression());
		return "ran(" + expression.toString() + ")";
	}

}
