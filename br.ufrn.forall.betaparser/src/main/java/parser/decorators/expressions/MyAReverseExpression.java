package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AReverseExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAReverseExpression extends MyExpressionDecorator {

	
	private AReverseExpression reverse;


	public MyAReverseExpression(AReverseExpression reverse) {
		this.reverse = reverse;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.reverse;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.reverse.getExpression());
		return expression.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.reverse.getExpression());
		return "(" + expression.toString() + "~)";
	}

}
