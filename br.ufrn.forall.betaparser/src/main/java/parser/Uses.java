package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import parser.decorators.predicates.MyPredicate;
import de.be4.classicalb.core.parser.node.AUsesMachineClause;
import de.be4.classicalb.core.parser.node.PExpression;

public class Uses {

	
	private AUsesMachineClause uses;
	private Machine machine;
	private List<Machine> machinesUsed;


	
	public Uses(AUsesMachineClause uses, Machine machine) {
		this.uses = uses;
		this.machine = machine;
		this.machinesUsed = setUpMachinesUsed();
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer usesText = new StringBuffer("USES ");

		int refCount = 1;
		for (Machine machineUsed : getMachinesUsed()) {
			if(refCount < getMachinesUsed().size()) {
				usesText.append(machineUsed.getName() + ", ");
			} else {
				usesText.append(machineUsed.getName() + "\n");
			}
			refCount++;
		}
		
		return usesText.toString();
	}
	
	
	
	public List<String> getMachinesUsedNames() {
		List<String> machinesUsedNames = new ArrayList<String>();
		
		for (Machine machineUsed : getMachinesUsed()) {
			machinesUsedNames.add(machineUsed.getName());
		}
		
		return machinesUsedNames;
	}
	
	
	
	public List<Machine> getMachinesUsed() {
		return this.machinesUsed;
	}

	
	
	private List<Machine> setUpMachinesUsed() {
		List<Machine> machinesUsed = new ArrayList<Machine>();
		
		LinkedList<PExpression> machineNameExprs = this.uses.getMachineNames();
		
		for (PExpression machineExpr : machineNameExprs) {
			MyExpression myExpression = MyExpressionFactory.convertExpression(machineExpr);
			String machineName = myExpression.toString();
			String machineUsedPath = this.machine.getFile().getParent() + "/" + machineName + ".mch";
			
			Machine machineUsed;
			
			machineUsed = new Machine(new File(machineUsedPath));
			machinesUsed.add(machineUsed);
		}
		
		return machinesUsed;
	}



	public Set<MyPredicate> getInvariantFromAllMachinesUses() {
		Set<MyPredicate> invariantFromAllMachinesUsed = new HashSet<MyPredicate>();
		
		for (Machine machine : getMachinesUsed()) {
			if (machine.getInvariant() != null) {
				invariantFromAllMachinesUsed.addAll(machine.getInvariant().getClauses());
			}
		}
		
		return invariantFromAllMachinesUsed;
	}
}
