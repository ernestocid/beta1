package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AMaxIntExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAMaxIntExpression extends MyExpressionDecorator {


	private AMaxIntExpression maxInt;


	public MyAMaxIntExpression(AMaxIntExpression maxInt) {
		this.maxInt = maxInt;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.maxInt;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "MAXINT";
	}

}
