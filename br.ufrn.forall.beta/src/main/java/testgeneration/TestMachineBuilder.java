package testgeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import animator.ConventionTools;

import parser.Machine;
import parser.Operation;

/**
 * This class is responsible for generating the auxiliary B Machine 
 * used to animate the test formulas 
 * 
 * @author ernestocid
 */
public class TestMachineBuilder {

	
	private Operation operation;
	private Set<List<Block>> combinations;
	private Partitioner partitioner;


	
	public TestMachineBuilder(Operation operation, Set<List<Block>> combinations) {
		this.operation = operation;
		this.combinations = combinations;
		this.partitioner = new Partitioner(operation);
	}
	
	
	
	/**
	 * Generates the auxiliary B machine in String form 
	 * @return auxiliary B machine in String form
	 */
	public String generateTestMachine() {
		StringBuffer testMachine = new StringBuffer("");
		
		addHeader(testMachine);
		addSets(testMachine);
		addConstants(testMachine);
		addProperties(testMachine);
		addDefinitions(testMachine);
		addOperations(testMachine);
		addFooter(testMachine);
		
		return testMachine.toString();
	}
	


	private void addHeader(StringBuffer testMachine) {
		testMachine.append("MACHINE " + ConventionTools.getTestMachineName(this.operation) + "\n\n");
	}
	
	
	
	private void addSees(StringBuffer testMachine) {
		if (this.operation.getMachine().getSees() != null) {
			testMachine.append(this.operation.getMachine().getSees().toString() + "\n");
		}
	}
	
	
	
	private void addIncludes(StringBuffer testMachine) {
		if (this.operation.getMachine().getIncludes() != null) {
			testMachine.append(this.operation.getMachine().getIncludes().toString() + "\n");
		}
	}

	
	
	private void addUses(StringBuffer testMachine) {
		if (this.operation.getMachine().getUses() != null) {
			testMachine.append(this.operation.getMachine().getUses().toString() + "\n");
		}
	}
	
	
	
	private void addSets(StringBuffer testMachine) {
		if (this.operation.getMachine().getSets() != null) {
			testMachine.append(this.operation.getMachine().getSets().toString());
		}
	}
	
	
	
	// TODO: complete the method with SEES, USES, etc
	// TODO: refactor!
	private void addConstants(StringBuffer testMachine) {
		List<String> constants = new ArrayList<String>();

		// Collecting constants from main machine
		if(this.operation.getMachine().getConstants() != null) {
			constants.addAll(this.operation.getMachine().getConstants().getAll());
		}
		
		// Collecting constants from included machines
		if(this.operation.getMachine().getIncludes() != null) {
			List<Machine> machinesIncluded = this.operation.getMachine().getIncludes().getMachinesIncluded();
			
			for(Machine machineIncluded : machinesIncluded) {
				if(machineIncluded.getConstants() != null) {
					constants.addAll(machineIncluded.getConstants().getAll());
				}
			}
		}
		
		
		if(!constants.isEmpty()) {
			StringBuffer constantsClause = new StringBuffer();
			
			constantsClause.append("CONSTANTS ");
			
			for (int i = 0; i < constants.size(); i++) {
				if(i < constants.size() - 1) {
					constantsClause.append(constants.get(i) + ", ");
				} else {
					constantsClause.append(constants.get(i) + "\n\n");
				}
			}
			
			testMachine.append(constantsClause.toString());
		}
	}
	
	
	
	private void addProperties(StringBuffer testMachine) {
		List<String> properties = new ArrayList<String>();
		
		// Look for properties from the main machine
		if (this.operation.getMachine().getProperties() != null) {
			properties.addAll(this.operation.getMachine().getProperties().getPropertiesClausesList());
		}
		
		// Look for properties from machines included
		if (this.operation.getMachine().getIncludes() != null) {
			List<Machine> machinesIncluded = this.operation.getMachine().getIncludes().getMachinesIncluded();
			
			for (Machine machineIncluded : machinesIncluded) {
				if(machineIncluded.getProperties() != null) {
					properties.addAll(machineIncluded.getProperties().getPropertiesClausesList());
				}
			}
			
		}
		
		if(!properties.isEmpty()) {
			StringBuffer propertiesClause = new StringBuffer("PROPERTIES\n");
			
			for (int i = 0; i < properties.size(); i++) {
				if(i < properties.size() - 1) {
					propertiesClause.append(properties.get(i) + " &\n");
				} else {
					propertiesClause.append(properties.get(i) + "\n\n");
				}
			}
			
			testMachine.append(propertiesClause.toString());
		}
		
	}
	
	
	
	private void addDefinitions(StringBuffer testMachine) {
		if (this.operation.getMachine().getDefinitions() != null) {
			testMachine.append(this.operation.getMachine().getDefinitions().toString());
		}
	}
	
	
	
	private String addOperations(StringBuffer testMachine) {
		StringBuffer testOperation = new StringBuffer("");

		testOperation.append("OPERATIONS\n");
		int operationIndex = 1;
		
		List<String> combinationsFormulas = convertCombinationsToStringFormulas(this.combinations); 
		
		Collections.sort(combinationsFormulas);
		
		for (String combination : combinationsFormulas) {
			addOperationForCombination(testOperation, operationIndex, combination);
			if(operationIndex < this.combinations.size()) { testOperation.append(";\n"); }
			operationIndex++;
		}

		testMachine.append(testOperation.toString());
		return testOperation.toString().trim();
	}

	

	private List<String> convertCombinationsToStringFormulas(Set<List<Block>> combinations) {
		List<String> combinationsFormulas = new ArrayList<String>();
		
		for(List<Block> combination : combinations) {
			combinationsFormulas.add(convertCombinationToStringConcatenation(combination));
		}
		
		return combinationsFormulas;
	}



	private String convertCombinationToStringConcatenation(List<Block> combination) {
		StringBuffer concatenation = new StringBuffer("");
		
		for (int i = 0; i < combination.size(); i++) {
			if(i == combination.size() - 1) {
				concatenation.append(combination.get(i).toString());
			} else {
				concatenation.append(combination.get(i).toString() + " & ");
			}
		}
		
		if(hasNegativeBlock(combination)) {
			concatenation.append(" /* NEGATIVE */");
		} else {
			concatenation.append(" /* POSITIVE */");
		}
		
		return concatenation.toString();
	}



	private boolean hasNegativeBlock(List<Block> combination) {
		for (Block block : combination) {
			if(block.isNegative()) {
				return true;
			}
		}
		
		return false;
	}



	private void addOperationForCombination(StringBuffer testOperation, int operationIndex, String combination) {
		addOpComment(testOperation);
		addOpName(testOperation, operationIndex);
		addOperationParameters(testOperation);
		addOperationPrecondition(testOperation, combination);
		addOperationBody(testOperation);
	}
	
	
	private void addOpComment(StringBuffer testOperation) {
		testOperation.append("/* Equivalence Class test data for " + this.operation.getName() + " */\n");
	}
	
	
	private void addOpName(StringBuffer testOperation, int index) {
		testOperation.append(this.operation.getName() + "_test" + index);
	}
	
	
	private void addOperationParameters(StringBuffer testOperation) {
		Set<String> operationInputSpace = this.partitioner.getOperationInputSpace();
		
		operationInputSpace.addAll(this.partitioner.getOperation().getStateVariablesUsedOnBody());
		
		testOperation.append(formatOperationParameters(operationInputSpace));
		testOperation.append(" =\n");
	}
	
	
	private void addOperationPrecondition(StringBuffer testOperation, String formula) {
		testOperation.append("PRE\n" + formula + "\n");
	}
	
	
	private void addOperationBody(StringBuffer testOperation) {
		testOperation.append("THEN\n" + "skip\n" + "END\n");
	}
	
	
	private void addFooter(StringBuffer testMachine) {
		testMachine.append("END");
	}

	
	private String formatOperationParameters(Set<String> variables) {
		StringBuffer returnVariables = new StringBuffer("(\n");
		int varCounter = 1;
		for (String variable : variables) {
			if (varCounter == variables.size()) {
				returnVariables.append(variable + "\n)");
			} else {
				returnVariables.append(variable + ",\n");
			}
			varCounter++;
		}
		return returnVariables.toString();
	}
	
}
