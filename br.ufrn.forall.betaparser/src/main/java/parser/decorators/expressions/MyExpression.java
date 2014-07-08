package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.PExpression;

public interface MyExpression {

	PExpression getNode();
	String toString();
	Set<String> getVariables();
	boolean isInterval();
	boolean isBasicType();
}
