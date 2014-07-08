package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ABoolSetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyABoolSetExpression extends MyExpressionDecorator {

	
	private ABoolSetExpression boolSet;


	public MyABoolSetExpression(ABoolSetExpression boolSet) {
		this.boolSet = boolSet;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.boolSet;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
 		return "BOOL";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}
