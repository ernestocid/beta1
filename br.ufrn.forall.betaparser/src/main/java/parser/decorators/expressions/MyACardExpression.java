package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ACardExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyACardExpression extends MyExpressionDecorator {

	
	private ACardExpression card;

	
	public MyACardExpression(ACardExpression card) {
		this.card = card;
	}

	
	
	@Override
	public PExpression getNode() {
		return this.card;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.card.getExpression());
		return expression.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.card.getExpression());
		return "card(" + expression.toString() + ")";
	}

}
