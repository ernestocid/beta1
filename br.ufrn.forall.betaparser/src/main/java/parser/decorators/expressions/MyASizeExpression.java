package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ASizeExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyASizeExpression extends MyExpressionDecorator {

	
	private ASizeExpression sizeExpression;
	
	
	public MyASizeExpression(ASizeExpression sizeExpression) {
		this.sizeExpression = sizeExpression;
	}

	

	@Override
	public PExpression getNode() {
		return this.sizeExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.sizeExpression.getExpression());
		return expression.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.sizeExpression.getExpression());
		return "size(" + expression.toString() + ")";
	}
	
}
