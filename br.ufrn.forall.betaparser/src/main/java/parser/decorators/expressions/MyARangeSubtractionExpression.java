package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ARangeSubtractionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyARangeSubtractionExpression extends MyExpressionDecorator {

	
	private ARangeSubtractionExpression rangeSubtraction;
	
	
	public MyARangeSubtractionExpression(ARangeSubtractionExpression rangeSubtraction) {
		this.rangeSubtraction = rangeSubtraction;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.rangeSubtraction;
	}

	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.rangeSubtraction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.rangeSubtraction.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.rangeSubtraction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.rangeSubtraction.getRight());
		return "(" + left.toString() + " |>> " + right.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}
}
