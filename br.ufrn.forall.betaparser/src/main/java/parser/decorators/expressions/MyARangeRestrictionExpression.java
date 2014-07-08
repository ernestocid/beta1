package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ARangeRestrictionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyARangeRestrictionExpression extends MyExpressionDecorator {

	
	private ARangeRestrictionExpression rangeRestriction;
	
	
	public MyARangeRestrictionExpression(ARangeRestrictionExpression rangeRestriction) {
		this.rangeRestriction = rangeRestriction;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.rangeRestriction;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.rangeRestriction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.rangeRestriction.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.rangeRestriction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.rangeRestriction.getRight());
		return "(" + left.toString() + " |> " + right.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}
