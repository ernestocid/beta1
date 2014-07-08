package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AVariablesMachineClause;
import de.be4.classicalb.core.parser.node.PExpression;

public class Variables {

	private AVariablesMachineClause variablesClause;

	public Variables(AVariablesMachineClause variablesClause) {
		this.variablesClause = variablesClause;
	}
	
	public Set<String> getAll() {
		Set<String> variables = new HashSet<String>();
		LinkedList<PExpression> identifiers = variablesClause.getIdentifiers();
		
		for (PExpression exp : identifiers) {
			MyExpression myExpression = MyExpressionFactory.convertExpression(exp);
			variables.add(myExpression.toString());
		}
		
		return variables;
	}
	
	@Override
	public String toString() {
		StringBuffer variablesDefinition = new StringBuffer("VARIABLES\n");
		List<String> variables = new ArrayList<String>(getAll());
		int varCount = 1;
		for(String variable : variables) {
			if(varCount < variables.size()) {
				variablesDefinition.append(variable + ",\n");
			} else {
				variablesDefinition.append(variable + "\n");
			}
			varCount++;
		}
		return variablesDefinition.toString();
	}
	
}
