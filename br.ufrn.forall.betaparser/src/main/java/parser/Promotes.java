package parser;

import java.util.ArrayList;
import java.util.List;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.APromotesMachineClause;
import de.be4.classicalb.core.parser.node.PExpression;

public class Promotes {

	
	private APromotesMachineClause promotes;
	private Machine machine;

	
	
	public Promotes(APromotesMachineClause promotes, Machine machine) {
		this.promotes = promotes;
		this.machine = machine;
	}

	
	
	public List<Operation> getPromotedOperations() {
		List<Operation> promotedOperations = new ArrayList<Operation>();
		
		if (this.promotes != null) {
			for (PExpression opNameExpr : this.promotes.getOperationNames()) {
				MyExpression expression = MyExpressionFactory.convertExpression(opNameExpr);
				String promotedOpName = expression.toString();
				for (Machine machineIncluded : this.machine.getIncludes().getMachinesIncluded()) {
					for(Operation op : machineIncluded.getOperations()) {
						if(op.getName().equals(promotedOpName)) {
							promotedOperations.add(op);
						}
					}
				}
			}
		}
		
		return promotedOperations;
	}
}
