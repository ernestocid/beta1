package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AMinIntExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAMinIntExpression extends MyExpressionDecorator {

	
	private AMinIntExpression minInt;


	public MyAMinIntExpression(AMinIntExpression minInt) {
		this.minInt = minInt;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.minInt;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "MININT";
	}
	
}
