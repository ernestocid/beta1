package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.APartialBijectionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAPartialBijectionExpression extends MyExpressionDecorator {

	
	private APartialBijectionExpression partialBijection;
	
	
	public MyAPartialBijectionExpression(APartialBijectionExpression partialBijection) {
		this.partialBijection = partialBijection;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.partialBijection;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.partialBijection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.partialBijection.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.partialBijection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.partialBijection.getRight());
		return "(" + left.toString() + " >+>> " + right.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}
