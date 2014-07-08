package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ASetExtensionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyASetExtensionExpression extends MyExpressionDecorator {

	
	private ASetExtensionExpression setExtension;

	
	public MyASetExtensionExpression(ASetExtensionExpression setExtension) {
		this.setExtension = setExtension;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.setExtension;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "{" + createVariablesList(setExtension.getExpressions()) + "}";
	}

}
