package parser.decorators.expressions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import de.be4.classicalb.core.parser.node.PExpression;

public abstract class MyExpressionDecorator implements MyExpression {
	
	
	@Override
	public boolean isInterval() {
		return false;
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return false;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return new HashSet<String>();
	}
	
	
	
	public static String createFunctionParameterList(LinkedList<PExpression> parameters) {
		StringBuffer params = new StringBuffer("");
		
		for (int i = 0; i < parameters.size(); i++) {
			MyExpression myExpression = MyExpressionFactory.convertExpression(parameters.get(i));
			
			if(i < parameters.size() - 1) {
				params.append(myExpression.toString() + ",");
			} else {
				params.append(myExpression.toString());
			}
		}
		
		return params.toString();
	}
	
	
	
	public static String createVariablesList(LinkedList<PExpression> expressions) {
		StringBuffer variables = new StringBuffer("");
		int varCount = 1;
		
		for (PExpression pExpression : expressions) {
			MyExpression myExpression = MyExpressionFactory.convertExpression(pExpression);
			if (varCount < expressions.size()) {
				variables.append(myExpression.toString() + ", ");
			} else {
				variables.append(myExpression.toString());
			}
			varCount++;
		}
		
		return variables.toString();
	}
	
	
	
	public static boolean isConstant(String variable) {
		if (variable.equals(variable.toUpperCase())) {
			return true;
		} else {
			return false;
		}
	}
	
}
