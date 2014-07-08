package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ANatSetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyANatSetExpression extends MyExpressionDecorator {

	
	private ANatSetExpression natSet;

	
	public MyANatSetExpression(ANatSetExpression natSet) {
		this.natSet = natSet;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.natSet;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "NAT";
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
