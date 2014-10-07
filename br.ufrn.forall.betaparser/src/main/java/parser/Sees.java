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
import de.be4.classicalb.core.parser.node.ASeesMachineClause;
import de.be4.classicalb.core.parser.node.PExpression;

public class Sees {
	
	
	private ASeesMachineClause sees;
	private Machine machine;
	private List<Machine> machinesSeen;
	
	
	public Sees(ASeesMachineClause sees, Machine machine) {
		this.sees = sees;
		this.machine = machine;
		this.machinesSeen = setUpMachinesSeen();
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer seesText = new StringBuffer("SEES ");
		
		int refCount = 1;
		for (Machine machineSeen : getMachinesSeen()) {
			if(refCount < getMachinesSeen().size()) {
				seesText.append(machineSeen.getName() + ", ");
			} else {
				seesText.append(machineSeen.getName() + "\n");
			}
			refCount++;
		}
		
		return seesText.toString();
	}

	
	
	public List<Machine> getMachinesSeen() {
		return this.machinesSeen;
	}
	
	
	
	public List<String> getMachinesSeenNames() {
		List<String> machinesSeenNames = new ArrayList<String>();
		
		for (Machine machine : getMachinesSeen()) {
			machinesSeenNames.add(machine.getName());
		}
		
		return machinesSeenNames;
	}
	
	
	
	private List<Machine> setUpMachinesSeen() {
		List<Machine> machinesSeen = new ArrayList<Machine>();
		
		LinkedList<PExpression> machineNameExprs = this.sees.getMachineNames();
		
		for (PExpression machineExpr : machineNameExprs) {
			MyExpression expression = MyExpressionFactory.convertExpression(machineExpr);
			String machineName = expression.toString();
			String machineSeenPath = this.machine.getFile().getParent() + "/" + machineName + ".mch";
			
			Machine machineSeen;
			
			machineSeen = new Machine(new File(machineSeenPath));
			machinesSeen.add(machineSeen);
		}
		
		return machinesSeen;
	}



	public Set<MyPredicate> getInvariantFromAllMachinesSeen() {
		Set<MyPredicate> invariantFromAllMachinesSeen = new HashSet<MyPredicate>();
		
		for (Machine machine : getMachinesSeen()) {
			if (machine.getInvariant() != null) {
				invariantFromAllMachinesSeen.addAll(machine.getInvariant().getClauses());
			}
		}
		
		return invariantFromAllMachinesSeen;
	}
}
