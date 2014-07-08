package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AEmptySetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAEmptySetExpression extends MyExpressionDecorator {

	
	private AEmptySetExpression emptySet;

	
	public MyAEmptySetExpression(AEmptySetExpression emptySet) {
		this.emptySet = emptySet;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.emptySet;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "{}";
	}

}
