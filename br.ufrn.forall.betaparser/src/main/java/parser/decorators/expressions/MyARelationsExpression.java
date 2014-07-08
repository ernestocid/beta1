package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.ARelationsExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyARelationsExpression extends MyExpressionDecorator {

	
	private ARelationsExpression relation;
	
	
	public MyARelationsExpression(ARelationsExpression relation) {
		this.relation = relation;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.relation;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		MyExpression left = MyExpressionFactory.convertExpression(this.relation.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.relation.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.relation.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.relation.getRight());
		return "(" + left.toString() + " <-> " + right.toString() + ")";
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}

}
