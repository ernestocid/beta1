package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AAddExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAAddExpression extends MyExpressionDecorator {

	
	private AAddExpression add;


	public MyAAddExpression(AAddExpression add) {
		this.add = add;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.add;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(add.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(add.getRight());
		
		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(add.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(add.getRight());
		return "(" + left.toString() + " + " + right.toString() + ")";
	}
	
}
