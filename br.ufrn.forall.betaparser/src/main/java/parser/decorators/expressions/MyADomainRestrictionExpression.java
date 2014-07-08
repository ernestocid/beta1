package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ADomainRestrictionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyADomainRestrictionExpression extends MyExpressionDecorator {

	
	private ADomainRestrictionExpression domainRestriction;
	
	
	public MyADomainRestrictionExpression(ADomainRestrictionExpression domainRestriction) {
		this.domainRestriction = domainRestriction;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.domainRestriction;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.domainRestriction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.domainRestriction.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.domainRestriction.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.domainRestriction.getRight());
		return "(" + left.toString() + " <| " + right.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}
