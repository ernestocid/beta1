package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ADomainSubtractionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyADomainSubtractionExpression extends MyExpressionDecorator {

	
	private ADomainSubtractionExpression domainSubtraction;
	
	
	public MyADomainSubtractionExpression(ADomainSubtractionExpression domainSubtraction) {
		this.domainSubtraction = domainSubtraction;
	}
	

	
	@Override
	public PExpression getNode() {
		return this.domainSubtraction;
	}

	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.domainSubtraction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.domainSubtraction.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.domainSubtraction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.domainSubtraction.getRight());
		return "(" + left.toString() + " <<| " + right.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}
}
