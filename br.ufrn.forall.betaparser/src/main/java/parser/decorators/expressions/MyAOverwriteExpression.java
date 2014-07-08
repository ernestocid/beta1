package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AOverwriteExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAOverwriteExpression extends MyExpressionDecorator {

	
	private AOverwriteExpression overwrite;
	
	
	public MyAOverwriteExpression(AOverwriteExpression overwrite) {
		this.overwrite = overwrite;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.overwrite;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.overwrite.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.overwrite.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.overwrite.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.overwrite.getRight());
		return "(" + left.toString() + " <+ " + right.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}
