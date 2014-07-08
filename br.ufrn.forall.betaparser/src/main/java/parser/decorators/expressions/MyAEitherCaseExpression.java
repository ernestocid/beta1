package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.PExpression;

public class MyAEitherCaseExpression extends MyExpressionDecorator {

	
	private MyExpression expression;
	private String caseVariable;

	
	public MyAEitherCaseExpression(MyExpression expression, String caseVariable) {
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
		return caseVariable + " : " + "{" + this.expression.toString() + "}";
	}
	
}
