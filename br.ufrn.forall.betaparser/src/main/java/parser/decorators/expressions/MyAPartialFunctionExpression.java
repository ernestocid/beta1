package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.APartialFunctionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAPartialFunctionExpression extends MyExpressionDecorator {

	
	private APartialFunctionExpression partialFunction;
	
	
	public MyAPartialFunctionExpression(APartialFunctionExpression partialFunction) {
		this.partialFunction = partialFunction;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.partialFunction;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.partialFunction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.partialFunction.getRight());
		
		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.partialFunction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.partialFunction.getRight());
		return "(" + left.toString() + " +-> " + right.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}