package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.APartialSurjectionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAPartialSurjectionExpression extends MyExpressionDecorator {


	private APartialSurjectionExpression partialSurjection;



	public MyAPartialSurjectionExpression(APartialSurjectionExpression partialSurjection) {
		this.partialSurjection = partialSurjection;
	}



	@Override
	public PExpression getNode() {
		return this.partialSurjection;
	}



	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();

		MyExpression left = MyExpressionFactory.convertExpression(this.partialSurjection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.partialSurjection.getRight());

		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());

		return variables;
	}



	@Override
	public String toString() {
		MyExpression left = MyExpressionFactory.convertExpression(this.partialSurjection.getLeft());
		MyExpression right = MyExpressionFactory.convertExpression(this.partialSurjection.getRight());
		return "(" + left.toString() + " +->> " + right.toString() + ")";
	}



	@Override
	public boolean isBasicType() {
		return true;
	}

}
