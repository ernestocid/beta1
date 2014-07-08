package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ARevExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyARevExpression extends MyExpressionDecorator {


	
	private ARevExpression revExpression;
	
	
	public MyARevExpression(ARevExpression revExpression) {
		this.revExpression = revExpression;
	}



	@Override
	public PExpression getNode() {
		return this.revExpression;
	}
	
	

	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyExpression expression = MyExpressionFactory.convertExpression(revExpression.getExpression());
		variables.addAll(expression.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(revExpression.getExpression());
		return "rev(" + expression.toString() + ")";
	}

}
