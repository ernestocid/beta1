package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ACompositionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyACompositionExpression extends MyExpressionDecorator {

	
	private ACompositionExpression compositionExpression;
	
	
	public MyACompositionExpression(ACompositionExpression compositionExpression) {
		this.compositionExpression = compositionExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.compositionExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression leftExpression = MyExpressionFactory.convertExpression(compositionExpression.getLeft());
		MyExpression rightExpression = MyExpressionFactory.convertExpression(compositionExpression.getRight());
		
		variables.addAll(leftExpression.getVariables());
		variables.addAll(rightExpression.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression leftExpression = MyExpressionFactory.convertExpression(compositionExpression.getLeft());
		MyExpression rightExpression = MyExpressionFactory.convertExpression(compositionExpression.getRight());
		
		return "(" + leftExpression.toString() + "; " + rightExpression.toString() + ")";
	}

}
