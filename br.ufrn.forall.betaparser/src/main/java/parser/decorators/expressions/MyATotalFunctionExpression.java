package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ATotalFunctionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyATotalFunctionExpression extends MyExpressionDecorator {

	
	private ATotalFunctionExpression totalFunction;


	public MyATotalFunctionExpression(ATotalFunctionExpression totalFunction) {
		this.totalFunction = totalFunction;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.totalFunction;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.totalFunction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.totalFunction.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.totalFunction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.totalFunction.getRight());
		return "(" + left.toString() + " --> " + right.toString() + ")";
	}

}
