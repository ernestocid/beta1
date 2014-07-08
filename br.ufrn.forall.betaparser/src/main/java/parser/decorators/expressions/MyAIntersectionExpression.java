package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AIntersectionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAIntersectionExpression extends MyExpressionDecorator {

	
	private AIntersectionExpression intersection;


	public MyAIntersectionExpression(AIntersectionExpression intersection) {
		this.intersection = intersection;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.intersection;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.intersection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.intersection.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.intersection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.intersection.getRight());
		return left.toString() + " /\\ " + right.toString();
	}
	
}
