package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.AStringSetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAStringSetExpression extends MyExpressionDecorator {

	
	private AStringSetExpression stringSet;


	public MyAStringSetExpression(AStringSetExpression stringSet) {
		this.stringSet = stringSet;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.stringSet;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		return super.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		return "STRING";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}
