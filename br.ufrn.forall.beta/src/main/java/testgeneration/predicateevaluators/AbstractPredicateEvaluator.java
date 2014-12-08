package testgeneration.predicateevaluators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import parser.Operation;
import testgeneration.Block;
import animator.Animation;

public abstract class AbstractPredicateEvaluator implements IPredicateEvaluator {

	private Operation operationUnderTest;
	private Set<List<Block>> combinations;
	private Map<String, Boolean> formulaAndTestTypeMap;
	private List<Animation> animations;
	private List<String> infeasibleFormulas;



	public AbstractPredicateEvaluator(Operation operationUnderTest, Set<List<Block>> combinations) {
		this.operationUnderTest = operationUnderTest;
		this.combinations = combinations;
		this.formulaAndTestTypeMap = mapFormulaToTypeOfTest(combinations);
		this.animations = new ArrayList<Animation>();
		this.infeasibleFormulas = new ArrayList<String>();
	}



	private Map<String, Boolean> mapFormulaToTypeOfTest(Set<List<Block>> combinations) {
		Map<String, Boolean> mapping = new HashMap<String, Boolean>();

		for (List<Block> combination : combinations) {
			String formula = convertCombinationToStringConcatenation(combination);
			formula = formula.replaceAll("i__", "");
			Boolean isNegative = hasNegativeBlock(combination);

			mapping.put(formula.replaceAll("[()]", ""), isNegative);
		}

		return mapping;
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



	public Operation getOperationUnderTest() {
		return operationUnderTest;
	}



	public Set<List<Block>> getCombinations() {
		return combinations;
	}



	public List<String> getCombinationsAsStrings() {
		List<String> combinationsAsStrings = new ArrayList<String>();

		for (List<Block> combination : getCombinations()) {
			combinationsAsStrings.add(convertCombinationToStringConcatenation(combination));
		}

		return combinationsAsStrings;
	}



	public Map<String, Boolean> getFormulaAndTestTypeMap() {
		return formulaAndTestTypeMap;
	}



	public List<Animation> getAnimations() {
		return animations;
	}



	public List<String> getInfeasibleFormulas() {
		return infeasibleFormulas;
	}

}
