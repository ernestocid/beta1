package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ANat1SetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyANat1SetExpression extends MyExpressionDecorator {

	
	private ANat1SetExpression nat1Set;


	public MyANat1SetExpression(ANat1SetExpression nat1Set) {
		this.nat1Set = nat1Set;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.nat1Set;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "NAT1";
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
