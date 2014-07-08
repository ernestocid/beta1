package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AUnaryMinusExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAUnaryMinusExpression extends MyExpressionDecorator {

	
	private AUnaryMinusExpression unaryMinus;
	
	
	public MyAUnaryMinusExpression(AUnaryMinusExpression unaryMinus) {
		this.unaryMinus = unaryMinus;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.unaryMinus;
	}

	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();

		variables.addAll(MyExpressionFactory.convertExpression(this.unaryMinus.getExpression()).getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		String expString = MyExpressionFactory.convertExpression(this.unaryMinus.getExpression()).toString();
		
		return "-" + expString;
	}

}
