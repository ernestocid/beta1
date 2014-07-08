package utils;

import java.util.ArrayList;
import java.util.List;

import de.be4.classicalb.core.parser.node.AAbstractMachineParseUnit;
import de.be4.classicalb.core.parser.node.AConstantsMachineClause;
import de.be4.classicalb.core.parser.node.ADefinitionsMachineClause;
import de.be4.classicalb.core.parser.node.AExtendsMachineClause;
import de.be4.classicalb.core.parser.node.AIncludesMachineClause;
import de.be4.classicalb.core.parser.node.AInitialisationMachineClause;
import de.be4.classicalb.core.parser.node.AInvariantMachineClause;
import de.be4.classicalb.core.parser.node.AMachineHeader;
import de.be4.classicalb.core.parser.node.AOperation;
import de.be4.classicalb.core.parser.node.AOperationsMachineClause;
import de.be4.classicalb.core.parser.node.APromotesMachineClause;
import de.be4.classicalb.core.parser.node.APropertiesMachineClause;
import de.be4.classicalb.core.parser.node.ASeesMachineClause;
import de.be4.classicalb.core.parser.node.ASetsMachineClause;
import de.be4.classicalb.core.parser.node.AUsesMachineClause;
import de.be4.classicalb.core.parser.node.AVariablesMachineClause;
import de.be4.classicalb.core.parser.node.PMachineClause;
import de.be4.classicalb.core.parser.node.PMachineHeader;
import de.be4.classicalb.core.parser.node.POperation;

public class MachineClausesTool {

	
	
	public static AMachineHeader getMachineHeader(AAbstractMachineParseUnit parsedMachine) {
		PMachineHeader header = parsedMachine.getHeader();
		AMachineHeader machineHeader = null;

		if(header instanceof AMachineHeader) {
			machineHeader = (AMachineHeader) header;
		}
		
		return machineHeader;
	}
	
	
	
	public static ASeesMachineClause getSeesClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof ASeesMachineClause) {
				return (ASeesMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static AUsesMachineClause getUsesClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof AUsesMachineClause) {
				return (AUsesMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static AIncludesMachineClause getIncludesClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof AIncludesMachineClause) {
				return (AIncludesMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static AExtendsMachineClause getExtendsClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if (mchClause instanceof AExtendsMachineClause) {
				return (AExtendsMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static APromotesMachineClause getPromotesClause(AAbstractMachineParseUnit parsedMachine) {
		for(PMachineClause clause :	parsedMachine.getMachineClauses()) {
			if (clause instanceof APromotesMachineClause) {
				return (APromotesMachineClause) clause;
			}
		}
		return null;
	}
	
	
	
	public static ASetsMachineClause getSetsClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof ASetsMachineClause) {
				return (ASetsMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static AConstantsMachineClause getConstantsClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof AConstantsMachineClause) {
				return (AConstantsMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static APropertiesMachineClause getPropertiesClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof APropertiesMachineClause) {
				return (APropertiesMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static AVariablesMachineClause getVariablesClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof AVariablesMachineClause) {
				return (AVariablesMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static AInvariantMachineClause getInvariantClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof AInvariantMachineClause) {
				return (AInvariantMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static AInitialisationMachineClause getInitialisationClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof AInitialisationMachineClause) {
				return (AInitialisationMachineClause) mchClause;
			}
		}
		return null;
	}
	
	
	
	public static AOperationsMachineClause getOperationsClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause clause : parsedMachine.getMachineClauses()) {
			if(clause instanceof AOperationsMachineClause) {
				return (AOperationsMachineClause) clause;
			}
		}
		return null;
	}



	public static List<AOperation> getAOperations(AAbstractMachineParseUnit parsedMachine) {
		AOperationsMachineClause operationsClause = MachineClausesTool.getOperationsClause(parsedMachine);
		List<AOperation> operations = createOperationsList(operationsClause);
		return operations;
	}

	
	
	private static List<AOperation> createOperationsList(AOperationsMachineClause operationsClause) {
		List<AOperation> operations = new ArrayList<AOperation>();
		for(POperation operation : operationsClause.getOperations()) {
			if(operation instanceof AOperation)
				operations.add((AOperation) operation);
		}
		return operations;
	}



	public static ADefinitionsMachineClause getDefinitionsClause(AAbstractMachineParseUnit parsedMachine) {
		for (PMachineClause mchClause : parsedMachine.getMachineClauses()) {
			if(mchClause instanceof ADefinitionsMachineClause) {
				return (ADefinitionsMachineClause) mchClause;
			}
		}
		return null;
	}

}
