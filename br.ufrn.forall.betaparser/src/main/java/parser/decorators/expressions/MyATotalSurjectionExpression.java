package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ATotalSurjectionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyATotalSurjectionExpression extends MyExpressionDecorator {

	
	private ATotalSurjectionExpression totalSurjection;
	
	
	public MyATotalSurjectionExpression(ATotalSurjectionExpression totalSurjection) {
		this.totalSurjection = totalSurjection;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.totalSurjection;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.totalSurjection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.totalSurjection.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.totalSurjection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.totalSurjection.getRight());
		return "(" + left.toString() + " -->> " + right.toString() + ")";
	}

	
	
	@Override
	public boolean isBasicType() {
		return true;
	}
}
