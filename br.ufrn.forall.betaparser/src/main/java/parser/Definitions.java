package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import parser.decorators.expressions.MyAExpressionDefinitionDefinition;
import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.ADefinitionsMachineClause;
import de.be4.classicalb.core.parser.node.AExpressionDefinitionDefinition;
import de.be4.classicalb.core.parser.node.AExpressionParseUnit;
import de.be4.classicalb.core.parser.node.PDefinition;
import de.be4.classicalb.core.parser.node.PParseUnit;
import de.prob.animator.domainobjects.ClassicalB;

public class Definitions {

	
	private ADefinitionsMachineClause definitionsClause;

	
	public Definitions(ADefinitionsMachineClause definitionsClause) {
		this.definitionsClause = definitionsClause;
	}


	
	public List<String> getDefinitionClauses() {
		List<String> definitions = new ArrayList<String>();
		
		LinkedList<PDefinition> pDefinitions = this.definitionsClause.getDefinitions();
		for (PDefinition pDefinition : pDefinitions) {
			if(pDefinition instanceof AExpressionDefinitionDefinition) {
				AExpressionDefinitionDefinition expressionDefinition = (AExpressionDefinitionDefinition) pDefinition;
				MyAExpressionDefinitionDefinition expressionDef = new MyAExpressionDefinitionDefinition(expressionDefinition);
				definitions.add(expressionDef.toString());
			}
		}
		
		return definitions;
	}

	
	
	@Override
	public String toString() {
		StringBuffer definitionsText = new StringBuffer("");
		definitionsText.append("DEFINITIONS\n");
		
		for(int i = 0; i < getDefinitionClauses().size(); i++) {
			if(i < getDefinitionClauses().size() - 1) {
				definitionsText.append(getDefinitionClauses().get(i) + ";\n");
			} else {
				definitionsText.append(getDefinitionClauses().get(i) + "\n");
			}
		}
		
		definitionsText.append("\n");
		
		return definitionsText.toString();
	}



	public Map<String, MyExpression> getDefinitions() {
		Map<String, MyExpression> definitions = new HashMap<String, MyExpression>();
		
		LinkedList<PDefinition> pDefinitions = this.definitionsClause.getDefinitions();
		for (PDefinition pDefinition : pDefinitions) {
			if(pDefinition instanceof AExpressionDefinitionDefinition) {
				AExpressionDefinitionDefinition expressionDefinition = (AExpressionDefinitionDefinition) pDefinition;
				MyAExpressionDefinitionDefinition expressionDef = new MyAExpressionDefinitionDefinition(expressionDefinition);
				definitions.put(expressionDef.getName(), convertStringToMyExpression(expressionDef.getRhs()));
			}
		}
		
		return definitions;
	}



	private MyExpression convertStringToMyExpression(String predicate) {
		PParseUnit pu = new ClassicalB(predicate).getAst().getPParseUnit();
		
		if(pu instanceof AExpressionParseUnit) {
			AExpressionParseUnit expressionUnit = (AExpressionParseUnit) pu;
			return MyExpressionFactory.convertExpression(expressionUnit.getExpression());
		}
		
		return null;
	}
	
}