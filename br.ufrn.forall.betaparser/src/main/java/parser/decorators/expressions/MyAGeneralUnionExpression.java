package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AGeneralUnionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAGeneralUnionExpression extends MyExpressionDecorator {

	
	private AGeneralUnionExpression generalUnionExpression;
	
	
	public MyAGeneralUnionExpression(AGeneralUnionExpression generalUnionExpression) {
		this.generalUnionExpression =  generalUnionExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.generalUnionExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables  = new HashSet<String>();
		MyExpression expression = MyExpressionFactory.convertExpression(generalUnionExpression.getExpression());
		variables.addAll(expression.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(generalUnionExpression.getExpression());
		return "union(" + expression.toString() + ")";
	}
	
}
