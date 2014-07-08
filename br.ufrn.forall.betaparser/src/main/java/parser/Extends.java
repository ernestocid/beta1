package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import parser.decorators.predicates.MyPredicate;

import de.be4.classicalb.core.parser.node.AExtendsMachineClause;
import de.be4.classicalb.core.parser.node.AMachineReference;
import de.be4.classicalb.core.parser.node.PMachineReference;

public class Extends {

	private AExtendsMachineClause extendss;
	private Machine machine;
	private List<Machine> machinesExtended;


	
	public Extends(AExtendsMachineClause extendss, Machine machine) {
		this.extendss = extendss;
		this.machine = machine;
		this.machinesExtended = setUpMachinesExtended();
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer extendsText = new StringBuffer("EXTENDS ");
		
		int refCount = 1;
		for (Machine machineExtended : getMachinesExtended()) {
			if(refCount < getMachinesExtended().size()) {
				extendsText.append(machineExtended.getName() + ", ");
			} else {
				extendsText.append(machineExtended.getName() + "\n");
			}
			refCount++;
		}
		
		return extendsText.toString();
	}
	
	
	
	public List<Machine> getMachinesExtended() {
		return machinesExtended;
	}
	
	
	
	public List<String> getMachinesExtendedNames() {
		List<String> machinesExtendedNames = new ArrayList<String>();
		for (Machine machine : getMachinesExtended()) {
			machinesExtendedNames.add(machine.getName());
		}
		return machinesExtendedNames;
	}

	
	
	private List<Machine> setUpMachinesExtended() {
		List<Machine> machinesExtended = new ArrayList<Machine>();
		
		LinkedList<PMachineReference> machineReferences = this.extendss.getMachineReferences();
		
		for (PMachineReference mchRef : machineReferences) {
			if (mchRef instanceof AMachineReference) {
				AMachineReference machine = (AMachineReference) mchRef;
				
				String machineName = machine.getMachineName().getFirst().getText();
				String machineExtendedPath = this.machine.getFile().getParent() + "/" + machineName + ".mch";
				
				Machine extendedMachine;
				
				extendedMachine = new Machine(new File(machineExtendedPath));
				machinesExtended.add(extendedMachine);
			}
		}
		
		return machinesExtended;
	}



	public Set<MyPredicate> getInvariantFromAllMachinesExtended() {
		Set<MyPredicate> invariantFromAllMachinesExtended = new HashSet<MyPredicate>();
		
		for (Machine machine : getMachinesExtended()) {
			if (machine.getInvariant() != null) {
				invariantFromAllMachinesExtended.addAll(machine.getInvariant().getClausesList());
			}
		}
		
		return invariantFromAllMachinesExtended;
	}
}
