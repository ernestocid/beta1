package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ANatural1SetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyANatural1SetExpression extends MyExpressionDecorator {

	
	private ANatural1SetExpression natural1SetExpression;
	
	
	public MyANatural1SetExpression(ANatural1SetExpression natural1SetExpression) {
		this.natural1SetExpression = natural1SetExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.natural1SetExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "NATURAL1";
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