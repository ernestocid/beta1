package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AIntegerExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAIntegerExpression extends MyExpressionDecorator {

	
	private AIntegerExpression integer;


	public MyAIntegerExpression(AIntegerExpression integer) {
		this.integer = integer;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.integer;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return this.integer.toString().trim();
	}

}
