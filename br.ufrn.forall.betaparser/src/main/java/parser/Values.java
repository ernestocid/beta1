package parser;

import java.util.HashMap;
import java.util.Map;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AValuesEntry;
import de.be4.classicalb.core.parser.node.AValuesMachineClause;
import de.be4.classicalb.core.parser.node.PValuesEntry;

public class Values {

	private Implementation implementation;
	private AValuesMachineClause valuesClause;
	private Map<String, MyExpression> entries;



	public Values(AValuesMachineClause valuesClause, Implementation implementation) {
		this.implementation = implementation;
		this.valuesClause = valuesClause;
		this.entries = new HashMap<String, MyExpression>();
		setUpValues();
	}



	private void setUpValues() {
		for (PValuesEntry entry : getValuesClause().getEntries()) {
			if (entry instanceof AValuesEntry) {
				AValuesEntry valuesEntry = (AValuesEntry) entry;

				String valueId = valuesEntry.getIdentifier().getFirst().getText();
				MyExpression valueExpression = MyExpressionFactory.convertExpression(valuesEntry.getValue());

				getEntries().put(valueId, valueExpression);
			}
		}
	}



	public Implementation getImplementation() {
		return implementation;
	}



	public AValuesMachineClause getValuesClause() {
		return valuesClause;
	}



	public Map<String, MyExpression> getEntries() {
		return entries;
	}

}
