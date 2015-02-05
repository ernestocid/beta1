package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.BParser;
import de.be4.classicalb.core.parser.exceptions.BException;
import de.be4.classicalb.core.parser.node.AConcreteVariablesMachineClause;
import de.be4.classicalb.core.parser.node.AImplementationMachineParseUnit;
import de.be4.classicalb.core.parser.node.AInvariantMachineClause;
import de.be4.classicalb.core.parser.node.AMachineHeader;
import de.be4.classicalb.core.parser.node.AValuesMachineClause;
import de.be4.classicalb.core.parser.node.PExpression;
import de.be4.classicalb.core.parser.node.PMachineClause;
import de.be4.classicalb.core.parser.node.PMachineHeader;
import de.be4.classicalb.core.parser.node.Start;

public class Implementation {

	private File implementation;
	private boolean parsed;
	private AImplementationMachineParseUnit parsedImplementation;
	private Values values;
	private Invariant invariant;



	public Implementation(File implementation) {
		this.implementation = implementation;

		Start startNode = parseImplementation();

		if (startNode == null) {
			// If machine was not parsed
			this.parsed = false;
		} else if (startNode.getPParseUnit() instanceof AImplementationMachineParseUnit) {
			// If machine was parsed
			this.parsedImplementation = (AImplementationMachineParseUnit) startNode.getPParseUnit();
			setUpImplementation();
			this.parsed = true;
		}
	}



	private Start parseImplementation() {
		BParser parser = new BParser();
		Start start = null;
		try {
			start = parser.parseFile(getImplementation(), false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BException e) {
			System.err.println("Error while parsing the implementation: " + e.getMessage());
			return null;
		}
		return start;
	}



	private void setUpImplementation() {
		AValuesMachineClause valuesClause = getValuesClause(getParsedImplementation());
		AInvariantMachineClause invariantClause = getInvariantClause(getParsedImplementation());

		if (valuesClause != null) {
			this.values = new Values(valuesClause, this);
		}

		if (invariantClause != null) {
			this.invariant = new Invariant(invariantClause);
		}

	}



	private AInvariantMachineClause getInvariantClause(AImplementationMachineParseUnit parsedImplementation2) {
		for (PMachineClause clause : getParsedImplementation().getMachineClauses()) {
			if (clause instanceof AInvariantMachineClause) {
				return (AInvariantMachineClause) clause;
			}
		}

		return null;
	}



	private AValuesMachineClause getValuesClause(AImplementationMachineParseUnit parsedImplementation) {
		for (PMachineClause clause : getParsedImplementation().getMachineClauses()) {
			if (clause instanceof AValuesMachineClause) {
				return (AValuesMachineClause) clause;
			}
		}

		return null;
	}



	public String getName() {
		PMachineHeader header = getParsedImplementation().getHeader();

		if (header instanceof AMachineHeader) {
			AMachineHeader machineHeader = (AMachineHeader) header;
			return machineHeader.getName().getFirst().getText();
		}

		return null;
	}



	public File getImplementation() {
		return implementation;
	}



	public boolean isParsed() {
		return parsed;
	}



	public AImplementationMachineParseUnit getParsedImplementation() {
		return parsedImplementation;
	}



	public Values getValues() {
		return values;
	}



	public List<String> getConcreteVariables() {
		List<String> concreteVariables = new ArrayList<String>();

		for (PMachineClause clause : getParsedImplementation().getMachineClauses()) {
			if (clause instanceof AConcreteVariablesMachineClause) {
				AConcreteVariablesMachineClause concreteVariablesClause = (AConcreteVariablesMachineClause) clause;
				LinkedList<PExpression> variableIdentifiers = concreteVariablesClause.getIdentifiers();

				for (PExpression variableId : variableIdentifiers) {
					concreteVariables.add(MyExpressionFactory.convertExpression(variableId).toString());
				}
			}
		}

		return concreteVariables;
	}



	public Invariant getInvariant() {
		return invariant;
	}
}
