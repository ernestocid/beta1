package testgeneration;

import java.util.List;
import java.util.Map;
import java.util.Set;

import parser.Machine;
import parser.Operation;
import animator.Animation;
import animator.Animator;

public class ProBApiPredicateEvaluator extends AbstractPredicateEvaluator {

//	private Operation operationUnderTest;
//	private Set<List<Block>> combinations;
//	private Map<String, Boolean> formulaAndTestTypeMap;
//
//
//
//	public AuxiliarMachinePredicateEvaluator(Operation operationUnderTest, Set<List<Block>> combinations) {
//		this.operationUnderTest = operationUnderTest;
//		this.combinations = combinations;
//		this.formulaAndTestTypeMap = mapFormulaToTypeOfTest(this.combinations);
//
//		Machine auxiliarTestMachine = new Machine(createAuxiliarTestMachineFile(getCombinations()));
//		Animator animator = new Animator(auxiliarTestMachine);
//		this.animations.addAll(animator.animateAllOperations());
//		addInfeasibleFormulasToList(animator);
//		deleteTempFiles();
//	}



	public ProBApiPredicateEvaluator(Operation operationUnderTest, Set<List<Block>> combinations) {
		super(operationUnderTest, combinations);
	}



	@Override
	public List<Animation> getSolutions() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<String> getInfeasiblePredicates() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Map<String, Boolean> getFormulaAndTestTypeMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
