package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AIseqExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAIseqExpression extends MyExpressionDecorator {

	
	private AIseqExpression iseqExpression;
	
	
	public MyAIseqExpression(AIseqExpression iseqExpression) {
		this.iseqExpression = iseqExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.iseqExpression;
	}

	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyExpression expression = MyExpressionFactory.convertExpression(iseqExpression.getExpression());
		variables.addAll(expression.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(iseqExpression.getExpression());
		return "iseq(" + expression.toString() + ")";
	}
	
}
