package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AImageExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAImageExpression extends MyExpressionDecorator {

	
	private AImageExpression image;
	
	
	public MyAImageExpression(AImageExpression image) {
		this.image = image;
	}
	
	
	@Override
	public PExpression getNode() {
		return this.image;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.image.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.image.getRight());
		
		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.image.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.image.getRight());
		return left.toString() + "[" + right.toString() + "]";
	}
	
}
