package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.APartialInjectionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAPartialInjectionExpression extends MyExpressionDecorator {

	
	private APartialInjectionExpression partialInjection;


	public MyAPartialInjectionExpression(APartialInjectionExpression partialInjection) {
		this.partialInjection = partialInjection;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.partialInjection;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.partialInjection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.partialInjection.getRight());
		
		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.partialInjection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.partialInjection.getRight());
		return "(" + left.toString() + " >+> " + right.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}
