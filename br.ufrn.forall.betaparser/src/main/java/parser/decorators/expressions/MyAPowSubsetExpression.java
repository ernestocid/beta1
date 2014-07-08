package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.APowSubsetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAPowSubsetExpression extends MyExpressionDecorator {

	
	private APowSubsetExpression powSubset;


	public MyAPowSubsetExpression(APowSubsetExpression powSubset) {
		this.powSubset = powSubset;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.powSubset;
	}
	

	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.powSubset.getExpression());
		return "POW(" + expression.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}
