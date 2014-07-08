package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AModuloExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAModuloExpression extends MyExpressionDecorator {

	
	private AModuloExpression moduloExpression;
	
	
	public MyAModuloExpression(AModuloExpression moduloExpression) {
		this.moduloExpression = moduloExpression;
	}



	@Override
	public PExpression getNode() {
		return this.moduloExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		variables.addAll(MyExpressionFactory.convertExpression(moduloExpression.getLeft()).getVariables());
		variables.addAll(MyExpressionFactory.convertExpression(moduloExpression.getRight()).getVariables());
		
		return variables;
	}

	
	
	@Override
	public String toString() {
		MyExpression leftExp = MyExpressionFactory.convertExpression(moduloExpression.getLeft());
		MyExpression rightExp = MyExpressionFactory.convertExpression(moduloExpression.getRight());
		
		return "(" + leftExp.toString() + " mod " + rightExp.toString() + ")";
	}
}
