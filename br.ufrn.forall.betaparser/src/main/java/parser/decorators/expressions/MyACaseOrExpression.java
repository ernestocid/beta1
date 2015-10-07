package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.PExpression;

public class MyACaseOrExpression extends MyExpressionDecorator {

	
	private MyExpression expression;
	private String caseVariable;
	
	
	public MyACaseOrExpression(MyExpression expression, String caseVariable) {
		this.expression = expression;
		this.caseVariable = caseVariable;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.expression.getNode();
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		variables.addAll(this.expression.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		return this.expression.toString();
	}
	
	
	
	public String getCaseVariable() {
		return this.caseVariable;
	}
}
