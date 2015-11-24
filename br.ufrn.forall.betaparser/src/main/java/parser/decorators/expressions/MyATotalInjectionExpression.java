package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ATotalInjectionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyATotalInjectionExpression extends MyExpressionDecorator {

	
	private ATotalInjectionExpression totalInjection;
	
	
	public MyATotalInjectionExpression(ATotalInjectionExpression totalInjection) {
		this.totalInjection = totalInjection;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.totalInjection;
	}
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.totalInjection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.totalInjection.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.totalInjection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.totalInjection.getRight());
		return "(" + left.toString() + " >-> " + right.toString() + ")";
	}

	
	
	@Override
	public boolean isBasicType() {
		return true;
	}
}
