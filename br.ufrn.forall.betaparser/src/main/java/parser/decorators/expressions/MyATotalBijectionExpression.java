package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ATotalBijectionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyATotalBijectionExpression extends MyExpressionDecorator {

	
	private ATotalBijectionExpression totalBijection;
	
	
	public MyATotalBijectionExpression(ATotalBijectionExpression totalBijection) {
		this.totalBijection = totalBijection;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.totalBijection;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.totalBijection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.totalBijection.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.totalBijection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.totalBijection.getRight());
		return "(" + left.toString() + " >->> " + right.toString() + ")";
	}

}
