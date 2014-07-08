package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AUnionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAUnionExpression extends MyExpressionDecorator {

	
	private AUnionExpression union;


	public MyAUnionExpression(AUnionExpression union) {
		this.union = union;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.union;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.union.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.union.getRight());
		
		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.union.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.union.getRight());
		return left.toString() + " \\/ " + right.toString();
	}

}
