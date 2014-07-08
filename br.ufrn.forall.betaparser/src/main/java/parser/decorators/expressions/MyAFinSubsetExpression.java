package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AFinSubsetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAFinSubsetExpression extends MyExpressionDecorator {

	
	private AFinSubsetExpression finSubset;


	public MyAFinSubsetExpression(AFinSubsetExpression finSubset) {
		this.finSubset = finSubset;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.finSubset;
	}

	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		MyExpression myExpression = MyExpressionFactory.convertExpression(this.finSubset.getExpression());
		return "FIN(" + myExpression.toString() + ")";
	}
	
}
