package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ANaturalSetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyANaturalSetExpression extends MyExpressionDecorator {

	
	private ANaturalSetExpression naturalSetExpression;
	
	
	public MyANaturalSetExpression(ANaturalSetExpression naturalSetExpression) {
		this.naturalSetExpression = naturalSetExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.naturalSetExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "NATURAL";
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
