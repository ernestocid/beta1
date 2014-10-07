package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import parser.decorators.predicates.MyPredicate;
import de.be4.classicalb.core.parser.node.AIncludesMachineClause;
import de.be4.classicalb.core.parser.node.AMachineReference;
import de.be4.classicalb.core.parser.node.PMachineReference;

public class Includes {

	
	private AIncludesMachineClause includes;
	private Machine machine;
	private List<Machine> machinesIncluded;
	
	
	
	public Includes(AIncludesMachineClause includes, Machine machine) {
		this.includes = includes;
		this.machine = machine;
		this.machinesIncluded = setUpMachinesIncluded();
	}
	
	
	
	public List<String> getMachinesIncludedNames() {
		List<String> machinesIncludedNames = new ArrayList<String>();
		
		for (Machine machine : getMachinesIncluded()) {
			machinesIncludedNames.add(machine.getName());
		}
		
		return machinesIncludedNames;
	}
	
	
	
	public List<Machine> getMachinesIncluded() {
		return this.machinesIncluded;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer includesText = new StringBuffer("INCLUDES ");
		
		int refCount = 1;
		for (Machine machine : getMachinesIncluded()) {
			if(refCount < machinesIncluded.size()) {
				includesText.append(machine.getName() + ", ");
			} else {
				includesText.append(machine.getName());
			}
			refCount++;
		}
		
		includesText.append("\n");
		
		return includesText.toString();
	}
	
	
	
	private List<Machine> setUpMachinesIncluded() {
		List<Machine> machinesIncluded = new ArrayList<Machine>();
		
		LinkedList<PMachineReference> machineReferences = this.includes.getMachineReferences();
		
		for (PMachineReference pMachineReference : machineReferences) {
			if(pMachineReference instanceof AMachineReference) {
				AMachineReference machine = (AMachineReference) pMachineReference;

				String machineName = machine.getMachineName().getFirst().getText();
				String machineIncludedPath = this.machine.getFile().getParent() + "/" + machineName + ".mch";
				
				Machine includedMachine;
				
				includedMachine = new Machine(new File(machineIncludedPath));
				machinesIncluded.add(includedMachine);
			}
		}
		
		return machinesIncluded;
	}



	public Set<MyPredicate> getInvariantFromAllMachinesIncluded() {
		Set<MyPredicate> invariantFromAllIncludedMachines = new HashSet<MyPredicate>();
		
		for (Machine machine : getMachinesIncluded()) {
			if (machine.getInvariant() != null) {
				invariantFromAllIncludedMachines.addAll(machine.getInvariant().getClauses());
			}
		}
		
		return invariantFromAllIncludedMachines;
	}
}
