package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AIdentifierExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAIdentifierExpression extends MyExpressionDecorator {
	
	
	private AIdentifierExpression identifier;


	public MyAIdentifierExpression(AIdentifierExpression identifier) {
		this.identifier = identifier;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.identifier;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		String variable = this.identifier.getIdentifier().getFirst().getText();
		if (!isConstant(variable)) {
			variables.add(variable);
		}
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		return this.identifier.getIdentifier().getFirst().getText();
	}

	
	
	@Override
	public boolean isBasicType() {
		if(this.toString().matches("[A-Z]+")) {
			return true;
		} else {
			return false;
		}
	}

}
