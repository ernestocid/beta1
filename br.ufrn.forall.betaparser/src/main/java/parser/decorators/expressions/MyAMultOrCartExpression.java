package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AMultOrCartExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAMultOrCartExpression extends MyExpressionDecorator {

	
	private AMultOrCartExpression multOrCart;

	
	public MyAMultOrCartExpression(AMultOrCartExpression multOrCart) {
		this.multOrCart = multOrCart;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.multOrCart;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.multOrCart.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.multOrCart.getRight());
		return "(" + left.toString() + " * " + right.toString() + ")";
	}
	
}
