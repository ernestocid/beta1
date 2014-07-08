package parser.decorators.expressions;

import de.be4.classicalb.core.parser.node.AExpressionDefinitionDefinition;

public class MyAExpressionDefinitionDefinition {

	private AExpressionDefinitionDefinition expressionDefinition;
	private String definitionName = null;
	private MyExpression rhs = null;
	
	public MyAExpressionDefinitionDefinition(AExpressionDefinitionDefinition expressionDefinition) {
		this.expressionDefinition = expressionDefinition;
		this.definitionName = expressionDefinition.getName().toString().trim();
		this.rhs = MyExpressionFactory.convertExpression(expressionDefinition.getRhs());
	}

	

	public AExpressionDefinitionDefinition getNode() {
		return expressionDefinition;
	}
	
	
	
	public String getName() {
		return this.definitionName;
	}
	
	
	
	public String getRhs() {
		return rhs.toString();
	}
	
	
	
	@Override
	public String toString() {
		return getName() + " == " + getRhs();
	}
	
}
