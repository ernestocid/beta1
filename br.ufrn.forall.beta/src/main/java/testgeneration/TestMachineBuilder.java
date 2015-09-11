package testgeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import animator.ConventionTools;
import parser.Machine;
import parser.Operation;

/**
 * This class is responsible for generating the auxiliary B Machine used to
 * animate the test formulas
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
	 * 
	 * @return auxiliary B machine in String form
	 */
	public String generateTestMachine() {
		StringBuffer testMachine = new StringBuffer("");

		addHeader(testMachine);
		addSees(testMachine);
		addUses(testMachine);
		addIncludes(testMachine);
		addExtends(testMachine);
		addSets(testMachine);
		addConstants(testMachine);
		addProperties(testMachine);
		addDefinitions(testMachine);
		addOperations(testMachine);
		addFooter(testMachine);

		return testMachine.toString();
	}



	private void addDefinitions(StringBuffer testMachine) {
		if (this.operation.getMachine().getDefinitions() != null) {
			testMachine.append(this.operation.getMachine().getDefinitions().toString());
		}
	}



	private void addProperties(StringBuffer testMachine) {
		if (this.operation.getMachine().getProperties() != null) {
			testMachine.append(this.operation.getMachine().getProperties().toString());
		}
	}



	private void addConstants(StringBuffer testMachine) {
		List<String> constants = new ArrayList<String>();

		if (this.operation.getMachine().getAbstractConstants() != null) {
			constants.addAll(this.operation.getMachine().getAbstractConstants().getAll());
		}

		if (this.operation.getMachine().getConstants() != null) {
			constants.addAll(this.operation.getMachine().getConstants().getAll());
		}

		if (!constants.isEmpty()) {
			testMachine.append("CONSTANTS ");
		}

		for (int i = 0; i < constants.size(); i++) {
			if (i < constants.size() - 1) {
				testMachine.append(constants.get(i) + ", ");
			} else {
				testMachine.append(constants.get(i) + "\n\n");
			}
		}
	}



	private void addSets(StringBuffer testMachine) {
		if (this.operation.getMachine().getSets() != null) {
			testMachine.append(this.operation.getMachine().getSets().toString());
		}
	}



	private void addExtends(StringBuffer testMachine) {
		if (this.operation.getMachine().getExtends() != null) {
			testMachine.append(this.operation.getMachine().getExtends().toString());
			testMachine.append("\n");
		}
	}



	private void addIncludes(StringBuffer testMachine) {
		if (this.operation.getMachine().getIncludes() != null) {
			testMachine.append(this.operation.getMachine().getIncludes().toString());
			testMachine.append("\n");
		}
	}



	private void addUses(StringBuffer testMachine) {
		if (this.operation.getMachine().getUses() != null) {
			testMachine.append(this.operation.getMachine().getUses().toString());
			testMachine.append("\n");
		}
	}



	private void addSees(StringBuffer testMachine) {
		if (this.operation.getMachine().getSees() != null) {
			testMachine.append(this.operation.getMachine().getSees().toString());
			testMachine.append("\n");
		}
	}



	private void addHeader(StringBuffer testMachine) {
		testMachine.append("MACHINE " + ConventionTools.getTestMachineName(this.operation) + "\n\n");
	}

	

	private void addPropertiesFromAllDependantMachines(Machine machine, Set<String> properties) {
		if (machine.getProperties() != null) {
			properties.addAll(machine.getProperties().getPropertiesClausesList());
		}

		if (machine.getSees() != null) {
			List<Machine> machinesSeen = machine.getSees().getMachinesSeen();

			for (Machine machineSeen : machinesSeen) {
				addPropertiesFromAllDependantMachines(machineSeen, properties);
			}
		}

		if (machine.getUses() != null) {
			List<Machine> machinesUsed = machine.getUses().getMachinesUsed();

			for (Machine machineUsed : machinesUsed) {
				addPropertiesFromAllDependantMachines(machineUsed, properties);
			}
		}

		if (machine.getIncludes() != null) {
			List<Machine> machinesIncluded = machine.getIncludes().getMachinesIncluded();

			for (Machine machineIncluded : machinesIncluded) {
				addPropertiesFromAllDependantMachines(machineIncluded, properties);
			}
		}

		if (machine.getExtends() != null) {
			List<Machine> machinesExtended = machine.getExtends().getMachinesExtended();

			for (Machine machineExtended : machinesExtended) {
				addPropertiesFromAllDependantMachines(machineExtended, properties);
			}
		}
	}



	private void addConstantsFromAllDependantMachines(Machine machine, Set<String> constants) {
		constants.addAll(machine.getAllMachineConstants());

		if (machine.getSees() != null) {
			List<Machine> machinesSeen = machine.getSees().getMachinesSeen();

			for (Machine machineSeen : machinesSeen) {
				addConstantsFromAllDependantMachines(machineSeen, constants);
			}
		}

		if (machine.getUses() != null) {
			List<Machine> machinesUsed = machine.getUses().getMachinesUsed();

			for (Machine machineUsed : machinesUsed) {
				addConstantsFromAllDependantMachines(machineUsed, constants);
			}
		}

		if (machine.getIncludes() != null) {
			List<Machine> machinesIncluded = machine.getIncludes().getMachinesIncluded();

			for (Machine machineIncluded : machinesIncluded) {
				addConstantsFromAllDependantMachines(machineIncluded, constants);
			}
		}

		if (machine.getExtends() != null) {
			List<Machine> machinesExtended = machine.getExtends().getMachinesExtended();

			for (Machine machineExtended : machinesExtended) {
				addConstantsFromAllDependantMachines(machineExtended, constants);
			}
		}
	}



	private void addSetsFromAllDependantMachines(Machine machine, Set<String> sets) {
		if (machine.getSets() != null) {
			sets.addAll(machine.getSets().getDeferredSets());
			sets.addAll(machine.getSets().getEnumeratedSetsWithElements());
		}

		if (machine.getSees() != null) {
			List<Machine> machinesSeen = machine.getSees().getMachinesSeen();

			for (Machine machineSeen : machinesSeen) {
				addSetsFromAllDependantMachines(machineSeen, sets);
			}
		}

		if (machine.getUses() != null) {
			List<Machine> machinesUsed = machine.getUses().getMachinesUsed();

			for (Machine machineUsed : machinesUsed) {
				addSetsFromAllDependantMachines(machineUsed, sets);
			}
		}

		if (machine.getIncludes() != null) {
			List<Machine> machinesIncluded = machine.getIncludes().getMachinesIncluded();

			for (Machine machineIncluded : machinesIncluded) {
				addSetsFromAllDependantMachines(machineIncluded, sets);
			}
		}

		if (machine.getExtends() != null) {
			List<Machine> machinesExtended = machine.getExtends().getMachinesExtended();

			for (Machine machineExtended : machinesExtended) {
				addSetsFromAllDependantMachines(machineExtended, sets);
			}
		}
	}



	private void addSetsFromExtendedMachines(List<String> sets) {
		if (this.operation.getMachine().getExtends() != null) {
			for (Machine machineExtended : this.operation.getMachine().getExtends().getMachinesExtended()) {
				getSetsForExtendedMachine(machineExtended, sets);
			}
		}
	}



	private void addSetsFromIncludedMachines(List<String> sets) {
		if (this.operation.getMachine().getIncludes() != null) {
			for (Machine machineIncluded : this.operation.getMachine().getIncludes().getMachinesIncluded()) {
				getSetsForIncludedMachine(machineIncluded, sets);
			}
		}
	}



	private void addSetsFromUsedMachines(List<String> sets) {
		if (this.operation.getMachine().getUses() != null) {
			for (Machine machineUsed : this.operation.getMachine().getUses().getMachinesUsed()) {
				if (machineUsed.getSets() != null) {
					sets.addAll(machineUsed.getSets().getDeferredSets());
					sets.addAll(machineUsed.getSets().getEnumeratedSetsWithElements());
				}
			}
		}
	}



	private void addSetsFromSeenMachines(List<String> sets) {
		if (this.operation.getMachine().getSees() != null) {
			for (Machine machineSeen : this.operation.getMachine().getSees().getMachinesSeen()) {
				if (machineSeen.getSets() != null) {
					sets.addAll(machineSeen.getSets().getDeferredSets());
					sets.addAll(machineSeen.getSets().getEnumeratedSetsWithElements());
				}
			}
		}
	}



	private void getSetsForIncludedMachine(Machine machineIncluded, List<String> sets) {
		if (machineIncluded.getSets() != null) {
			sets.addAll(machineIncluded.getSets().getDeferredSets());
			sets.addAll(machineIncluded.getSets().getEnumeratedSetsWithElements());
		}

		if (machineIncluded.getIncludes() != null) {
			for (Machine m : machineIncluded.getIncludes().getMachinesIncluded()) {
				getSetsForIncludedMachine(m, sets);
			}
		}
	}



	private void getSetsForExtendedMachine(Machine machineExtended, List<String> sets) {
		if (machineExtended.getSets() != null) {
			sets.addAll(machineExtended.getSets().getDeferredSets());
			sets.addAll(machineExtended.getSets().getEnumeratedSetsWithElements());
		}

		if (machineExtended.getExtends() != null) {
			for (Machine m : machineExtended.getExtends().getMachinesExtended()) {
				getSetsForExtendedMachine(m, sets);
			}
		}
	}

	

	private void addConstantsFromExtendedMachines(List<String> constants) {
		if (this.operation.getMachine().getExtends() != null) {
			List<Machine> machinesExtended = this.operation.getMachine().getExtends().getMachinesExtended();

			for (Machine machineExtended : machinesExtended) {
				addConstantsFromExtendedMachine(machineExtended, constants);
			}
		}
	}



	private void addConstantsFromIncludedMachines(List<String> constants) {
		if (this.operation.getMachine().getIncludes() != null) {
			List<Machine> machinesIncluded = this.operation.getMachine().getIncludes().getMachinesIncluded();

			for (Machine machineIncluded : machinesIncluded) {
				addConstantsFromIncludedMachine(machineIncluded, constants);
			}
		}
	}



	private void addConstantsFromUsedMachines(List<String> constants) {
		if (this.operation.getMachine().getUses() != null) {
			List<Machine> machinesIncluded = this.operation.getMachine().getUses().getMachinesUsed();

			for (Machine machineIncluded : machinesIncluded) {
				constants.addAll(machineIncluded.getAllMachineConstants());
			}
		}
	}



	private void addConstantsFromSeenMachines(List<String> constants) {
		if (this.operation.getMachine().getSees() != null) {
			List<Machine> machinesSeens = this.operation.getMachine().getSees().getMachinesSeen();

			for (Machine machineSeen : machinesSeens) {
				constants.addAll(machineSeen.getAllMachineConstants());
			}
		}
	}



	private void addConstantsFromExtendedMachine(Machine machineExtended, List<String> constants) {
		constants.addAll(machineExtended.getAllMachineConstants());

		if (machineExtended.getExtends() != null) {
			for (Machine m : machineExtended.getExtends().getMachinesExtended()) {
				addConstantsFromExtendedMachine(m, constants);
			}
		}
	}



	private void addConstantsFromIncludedMachine(Machine machineIncluded, List<String> constants) {
		constants.addAll(machineIncluded.getAllMachineConstants());

		if (machineIncluded.getIncludes() != null) {
			for (Machine m : machineIncluded.getIncludes().getMachinesIncluded()) {
				addConstantsFromIncludedMachine(m, constants);
			}
		}

	}

	

	private void addPropertiesFromExtendedMachines(List<String> properties) {
		if (this.operation.getMachine().getExtends() != null) {
			List<Machine> machinesExtended = this.operation.getMachine().getExtends().getMachinesExtended();

			for (Machine machineExtended : machinesExtended) {
				addPropertiesFromExtendedMachine(machineExtended, properties);
			}
		}
	}



	private void addPropertiesFromIncludedMachines(List<String> properties) {
		if (this.operation.getMachine().getIncludes() != null) {
			List<Machine> machinesIncluded = this.operation.getMachine().getIncludes().getMachinesIncluded();

			for (Machine machineIncluded : machinesIncluded) {
				addPropertiesFromIncludedMachine(machineIncluded, properties);
			}
		}
	}



	private void addPropertiesFromExtendedMachine(Machine machineExtended, List<String> properties) {
		if (machineExtended.getProperties() != null) {
			properties.addAll(machineExtended.getProperties().getPropertiesClausesList());
		}

		if (machineExtended.getExtends() != null) {
			List<Machine> machinesExtended = machineExtended.getExtends().getMachinesExtended();

			for (Machine m : machinesExtended) {
				addPropertiesFromExtendedMachine(m, properties);
			}
		}
	}



	private void addPropertiesFromIncludedMachine(Machine machineIncluded, List<String> properties) {
		if (machineIncluded.getProperties() != null) {
			properties.addAll(machineIncluded.getProperties().getPropertiesClausesList());
		}

		if (machineIncluded.getIncludes() != null) {
			List<Machine> machinesIncluded = machineIncluded.getIncludes().getMachinesIncluded();

			for (Machine m : machinesIncluded) {
				addPropertiesFromIncludedMachine(m, properties);
			}
		}
	}



	private void addPropertiesFromUsedMachines(List<String> properties) {
		if (this.operation.getMachine().getUses() != null) {
			List<Machine> machinesUsed = this.operation.getMachine().getUses().getMachinesUsed();

			for (Machine machineUsed : machinesUsed) {
				if (machineUsed.getProperties() != null) {
					properties.addAll(machineUsed.getProperties().getPropertiesClausesList());
				}
			}
		}
	}



	private void addPropertiesFromSeenMachines(List<String> properties) {
		if (this.operation.getMachine().getSees() != null) {
			List<Machine> machinesSeen = this.operation.getMachine().getSees().getMachinesSeen();

			for (Machine machineSeen : machinesSeen) {
				if (machineSeen.getProperties() != null) {
					properties.addAll(machineSeen.getProperties().getPropertiesClausesList());
				}
			}
		}
	}

	

	private void addDefinitionsFromAllDependantMachines(Machine machine, Set<String> definitions) {
		if (machine.getDefinitions() != null) {
			definitions.addAll(machine.getDefinitions().getDefinitionClauses());
		}

		if (machine.getSees() != null) {
			List<Machine> machinesSeen = machine.getSees().getMachinesSeen();

			for (Machine machineSeen : machinesSeen) {
				addDefinitionsFromAllDependantMachines(machineSeen, definitions);
			}
		}

		if (machine.getUses() != null) {
			List<Machine> machinesUsed = machine.getUses().getMachinesUsed();

			for (Machine machineUsed : machinesUsed) {
				addDefinitionsFromAllDependantMachines(machineUsed, definitions);
			}
		}

		if (machine.getIncludes() != null) {
			List<Machine> machinesIncluded = machine.getIncludes().getMachinesIncluded();

			for (Machine machineIncluded : machinesIncluded) {
				addDefinitionsFromAllDependantMachines(machineIncluded, definitions);
			}
		}

		if (machine.getExtends() != null) {
			List<Machine> machinesExtended = machine.getExtends().getMachinesExtended();

			for (Machine machineExtended : machinesExtended) {
				addDefinitionsFromAllDependantMachines(machineExtended, definitions);
			}
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
			if (operationIndex < this.combinations.size()) {
				testOperation.append(";\n");
			}
			operationIndex++;
		}

		testMachine.append(testOperation.toString());
		return testOperation.toString().trim();
	}



	private List<String> convertCombinationsToStringFormulas(Set<List<Block>> combinations) {
		List<String> combinationsFormulas = new ArrayList<String>();

		for (List<Block> combination : combinations) {
			combinationsFormulas.add(convertCombinationToStringConcatenation(combination));
		}

		return combinationsFormulas;
	}



	private String convertCombinationToStringConcatenation(List<Block> combination) {
		StringBuffer concatenation = new StringBuffer("");

		for (int i = 0; i < combination.size(); i++) {
			if (i == combination.size() - 1) {
				concatenation.append(combination.get(i).getBlock());
			} else {
				concatenation.append(combination.get(i).getBlock() + " & ");
			}
		}

		if (hasNegativeBlock(combination)) {
			concatenation.append(" /* NEGATIVE */");
		} else {
			concatenation.append(" /* POSITIVE */");
		}

		return concatenation.toString();
	}



	private boolean hasNegativeBlock(List<Block> combination) {
		for (Block block : combination) {
			if (block.isNegative()) {
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



	// TODO: Fix getInputSpace method.
	private void addOperationParameters(StringBuffer testOperation) {
		Set<String> operationInputSpaceAdapted = adaptInputSpace(getInputSpace());
		testOperation.append(formatOperationParameters(operationInputSpaceAdapted));
		testOperation.append(" =\n");
	}



	private Set<String> adaptInputSpace(Set<String> inputSpace) {
		Set<String> inputSpaceAdapted = new HashSet<String>();

		for (String inputVariable : inputSpace) {
			inputSpaceAdapted.add("i__" + inputVariable);
		}

		return inputSpaceAdapted;
	}



	private Set<String> getInputSpace() {
		Set<String> operationInputSpace = this.partitioner.getOperationInputSpace();

		operationInputSpace.addAll(this.partitioner.getOperation().getStateVariablesUsedOnBody());
		operationInputSpace.removeAll(this.operation.getMachine().getAllMachineConstants());
		operationInputSpace.removeAll(this.operation.getMachine().getSetNamesFromAllMachines());
	
		return operationInputSpace;
	}



	private void addOperationPrecondition(StringBuffer testOperation, String formula) {
		String adaptedFormula = formula;
		Set<String> inputSpace = getInputSpace();

		for (String inputVariable : inputSpace) {
			String replaceRegEx = "\\b" + inputVariable + "\\b";
			String auxInputVar = "i__" + inputVariable;
			adaptedFormula = adaptedFormula.replaceAll(replaceRegEx, auxInputVar);
		}

		testOperation.append("PRE\n" + adaptedFormula + "\n");
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
