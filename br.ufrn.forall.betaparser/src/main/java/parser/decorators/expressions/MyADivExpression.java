package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ADivExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyADivExpression extends MyExpressionDecorator {

	
	private ADivExpression divExpression;
	
	
	public MyADivExpression(ADivExpression divExpression) {
		this.divExpression = divExpression;
	}



	@Override
	public PExpression getNode() {
		return this.divExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		variables.addAll(MyExpressionFactory.convertExpression(divExpression.getLeft()).getVariables());
		variables.addAll(MyExpressionFactory.convertExpression(divExpression.getRight()).getVariables());
		
		return variables;
	}

	
	
	@Override
	public String toString() {
		MyExpression leftExp = MyExpressionFactory.convertExpression(divExpression.getLeft());
		MyExpression rightExp = MyExpressionFactory.convertExpression(divExpression.getRight());
		
		return "(" + leftExp.toString() + "/" + rightExp.toString() + ")";
	}

}
