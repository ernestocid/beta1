package parser;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AAbstractConstantsMachineClause;
import de.be4.classicalb.core.parser.node.PExpression;

public class AbstractConstants {

	
	private AAbstractConstantsMachineClause abstractConstantsClause;
	private Machine machine;
	
	
	public AbstractConstants(AAbstractConstantsMachineClause abstractConstantsClause, Machine machine) {
		this.abstractConstantsClause = abstractConstantsClause;
		this.machine = machine;
	}
	
	
	
	public Machine getMachine() {
		return this.machine;
	}
	
	
	
	public Set<String> getAll() {
		Set<String> abstractConstants = new HashSet<String>();
		
		for (PExpression constExpr : this.abstractConstantsClause.getIdentifiers()) {
			MyExpression expression = MyExpressionFactory.convertExpression(constExpr);
			abstractConstants.add(expression.toString());
		}
		
		return abstractConstants;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer constantsClauseText = new StringBuffer("");
		constantsClauseText.append("ABSTRACT_CONSTANTS ");
		
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
