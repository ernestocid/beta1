package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AIntervalExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAIntervalExpression extends MyExpressionDecorator {

	
	private AIntervalExpression interval;
	private MyExpression leftExpression;
	private MyExpression rightExpression;


	public MyAIntervalExpression(AIntervalExpression interval) {
		this.interval = interval;
		this.leftExpression = MyExpressionFactory.convertExpression(interval.getLeftBorder());
		this.rightExpression = MyExpressionFactory.convertExpression(interval.getRightBorder());
	}

	
	
	public MyExpression getLeftExpression() {
		return this.leftExpression;
	}
	
	
	
	public MyExpression getRightExpression() {
		return this.rightExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.interval;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		variables.addAll(getLeftExpression().getVariables());
		variables.addAll(getRightExpression().getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		String leftExpression = getLeftExpression().toString();
		String rightExpression = getRightExpression().toString();
		
		if(getLeftExpression() instanceof MyAAddExpression ||
				getLeftExpression() instanceof MyAMinusOrSetSubtractExpression ||
				getLeftExpression() instanceof MyAMultOrCartExpression) {
			leftExpression = "(" + leftExpression + ")";
		}
		
		if(getRightExpression() instanceof MyAAddExpression ||
				getRightExpression() instanceof MyAMinusOrSetSubtractExpression ||
				getRightExpression() instanceof MyAMultOrCartExpression) {
			rightExpression = "(" + rightExpression + ")";
		}
		
		return leftExpression + ".." + rightExpression;
	}
	
	
	
	@Override
	public boolean isInterval() {
		return true;
	}
	
}
