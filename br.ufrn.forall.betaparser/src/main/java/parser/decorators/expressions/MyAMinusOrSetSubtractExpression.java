package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AMinusOrSetSubtractExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAMinusOrSetSubtractExpression extends MyExpressionDecorator {

	
	private AMinusOrSetSubtractExpression minusOrSetSubtract;


	public MyAMinusOrSetSubtractExpression(AMinusOrSetSubtractExpression minusOrSetSubtract) {
		this.minusOrSetSubtract = minusOrSetSubtract;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.minusOrSetSubtract;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.minusOrSetSubtract.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.minusOrSetSubtract.getRight());
		return "(" + left.toString() + " - " + right.toString() + ")";
	}
	
}
