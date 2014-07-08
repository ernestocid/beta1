package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AIntSetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAIntSetExpression extends MyExpressionDecorator {

	
	private AIntSetExpression intSet;


	public MyAIntSetExpression(AIntSetExpression intSet) {
		this.intSet = intSet;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.intSet;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "INT";
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
