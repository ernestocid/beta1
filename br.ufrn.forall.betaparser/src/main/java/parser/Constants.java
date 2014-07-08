package parser;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AConstantsMachineClause;
import de.be4.classicalb.core.parser.node.PExpression;

public class Constants {

	
	private AConstantsMachineClause constants;
	private Machine machine;
	
	
	
	public Constants(AConstantsMachineClause constants, Machine machine) {
		this.constants = constants;
		this.machine = machine;
	}



	public Machine getMachine() {
		return this.machine;
	}
	
	
	
	public Set<String> getAll() {
		Set<String> constantsVariables = new HashSet<String>();
		
		for (PExpression constExpr : this.constants.getIdentifiers()) {
			MyExpression expression = MyExpressionFactory.convertExpression(constExpr);
			constantsVariables.add(expression.toString());
		}
		
		return constantsVariables;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer constantsClauseText = new StringBuffer("");
		constantsClauseText.append("CONSTANTS ");
		
		int constantCounter = 1;
		for (String constant : getAll()) {
			if (constantCounter < getAll().size()) {
				constantsClauseText.append(constant + ", ");
			} else {
				constantsClauseText.append(constant + "\n");
			}
			constantCounter++;
		}
		
		return constantsClauseText.toString();
	}
	
}
