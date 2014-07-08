package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ABooleanTrueExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyABooleanTrueExpression extends MyExpressionDecorator {

	
	private ABooleanTrueExpression booleanTrueExpression;
	
	
	public MyABooleanTrueExpression(ABooleanTrueExpression booleanTrueExpression) {
		this.booleanTrueExpression = booleanTrueExpression;
	}



	@Override
	public PExpression getNode() {
		return this.booleanTrueExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	

	
	@Override
	public String toString() {
		return "TRUE";
	}
	
}
