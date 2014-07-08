package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AFunctionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAFunctionExpression extends MyExpressionDecorator {

	
	private AFunctionExpression function;


	public MyAFunctionExpression(AFunctionExpression function) {
		this.function = function;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.function;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression indentifierExpression = MyExpressionFactory.convertExpression(this.function.getIdentifier());
		variables.add(indentifierExpression.toString());
		
		for (PExpression param : this.function.getParameters()) {
			MyExpression myExpression = MyExpressionFactory.convertExpression(param);
			variables.addAll(myExpression.getVariables());
		}
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		String functionName = this.function.getIdentifier().toString().trim();
		String parametersList = createFunctionParameterList(function.getParameters());
		return functionName + "(" + parametersList + ")";
	}

}
