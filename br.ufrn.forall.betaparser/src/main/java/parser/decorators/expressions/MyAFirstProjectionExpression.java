package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AFirstProjectionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAFirstProjectionExpression extends MyExpressionDecorator {


	
	private AFirstProjectionExpression firstProjectionExpression;
	
	
	public MyAFirstProjectionExpression(AFirstProjectionExpression firstProjectionExpression) {
		this.firstProjectionExpression = firstProjectionExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.firstProjectionExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyExpression expression1 = MyExpressionFactory.convertExpression(firstProjectionExpression.getExp1());
		MyExpression expression2 = MyExpressionFactory.convertExpression(firstProjectionExpression.getExp2());
		
		variables.addAll(expression1.getVariables());
		variables.addAll(expression2.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression1 = MyExpressionFactory.convertExpression(firstProjectionExpression.getExp1());
		MyExpression expression2 = MyExpressionFactory.convertExpression(firstProjectionExpression.getExp2());
		
		return "prj1(" + expression1.toString() + ", " + expression2.toString() + ")";
	}

}
