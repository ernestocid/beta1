package parser;

import java.util.Set;

import parser.decorators.expressions.MyExpression;

public class ExpressionCharacteristic extends Characteristic {

	
	private MyExpression expression;
	
	
	public ExpressionCharacteristic(MyExpression expression, CharacteristicType type) {
		super(type, expression.toString());
		this.expression = expression;
	}
	
	

	@Override
	public Set<String> getVariables() {
		return this.expression.getVariables();
	}

	
	
	@Override
	public boolean isTypingCharacteristic() {
		return this.expression.isBasicType();
	}

	
	
	@Override
	public boolean isIntervalCharacteristic() {
		return this.expression.isInterval();
	}

	
	
	@Override
	public String toString() {
		return this.expression.toString();
	}

	
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}



	@Override
	public boolean isRelationalCharacteristic() {
		return false;
	}
	
}
