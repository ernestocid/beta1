package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ASeqExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyASeqExpression extends MyExpressionDecorator{

	
	private ASeqExpression seqExpression;
	
	
	public MyASeqExpression(ASeqExpression seqExpression) {
		this.seqExpression = seqExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.seqExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.seqExpression.getExpression());
		return expression.getVariables();
	}
	
	
	
	@Override
	public String toString() {
		MyExpression expression = MyExpressionFactory.convertExpression(this.seqExpression.getExpression());
		return "seq(" + expression.toString() + ")";
	}

}
