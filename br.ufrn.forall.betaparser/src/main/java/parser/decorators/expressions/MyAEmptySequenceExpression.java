package parser.decorators.expressions;

import de.be4.classicalb.core.parser.node.AEmptySequenceExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAEmptySequenceExpression extends MyExpressionDecorator {

	
	private AEmptySequenceExpression emptySequenceExpression;
	
	
	public MyAEmptySequenceExpression(AEmptySequenceExpression emptySequenceExpression) {
		this.emptySequenceExpression = emptySequenceExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.emptySequenceExpression;
	}

	
	
	@Override
	public String toString() {
		return "[]";
	}
	
}
