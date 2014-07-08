package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ADomainExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyADomainExpression extends MyExpressionDecorator {

	
	private ADomainExpression domain;


	public MyADomainExpression(ADomainExpression domain) {
		this.domain = domain;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.domain;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>(); 
		
		MyExpression expression = MyExpressionFactory.convertExpression(this.domain.getExpression());
		variables.addAll(expression.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.domain.getExpression());
		return "dom(" + expression.toString() + ")";
	}
	
}
