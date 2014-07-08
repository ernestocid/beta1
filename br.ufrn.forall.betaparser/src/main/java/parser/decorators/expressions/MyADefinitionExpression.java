package parser.decorators.expressions;

import de.be4.classicalb.core.parser.node.ADefinitionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyADefinitionExpression extends MyExpressionDecorator {

	
	private ADefinitionExpression definitionExpression;
	
	
	public MyADefinitionExpression(ADefinitionExpression definitionExpression) {
		this.definitionExpression = definitionExpression;
	}

	
	
	@Override
	public PExpression getNode() {
		return this.definitionExpression;
	}
	
	
	
	@Override
	public String toString() {
		return this.definitionExpression.getDefLiteral().toString().trim();
	}

}
