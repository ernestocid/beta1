package parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import parser.decorators.expressions.MyAExpressionDefinitionDefinition;

import de.be4.classicalb.core.parser.node.ADefinitionsMachineClause;
import de.be4.classicalb.core.parser.node.AExpressionDefinitionDefinition;
import de.be4.classicalb.core.parser.node.PDefinition;

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
	
}
