package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AIntegerSetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAIntegerSetExpression extends MyExpressionDecorator {

	
	private AIntegerSetExpression integerSetExpression;
	
	
	public MyAIntegerSetExpression(AIntegerSetExpression integerSetExpression) {
		this.integerSetExpression = integerSetExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.integerSetExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "INTEGER";
	}
	
	
	
	@Override
	public boolean isInterval() {
		return true;
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}
	
}
