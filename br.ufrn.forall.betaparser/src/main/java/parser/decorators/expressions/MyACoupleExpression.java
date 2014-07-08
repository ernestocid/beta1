package parser.decorators.expressions;

import de.be4.classicalb.core.parser.node.ACoupleExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyACoupleExpression extends MyExpressionDecorator {

	
	private ACoupleExpression couple;
	
	
	public MyACoupleExpression(ACoupleExpression couple) {
		this.couple = couple;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.couple;
	}
	
	
	
	@Override
	public String toString() {
		String leftMember = MyExpressionFactory.convertExpression(this.couple.getList().get(0)).toString();
		String rightMember = MyExpressionFactory.convertExpression(this.couple.getList().get(1)).toString();
		
		return leftMember + " |-> " + rightMember;
	}
	
	
	
	@Override
	public boolean isBasicType() {
		return true;
	}
}
