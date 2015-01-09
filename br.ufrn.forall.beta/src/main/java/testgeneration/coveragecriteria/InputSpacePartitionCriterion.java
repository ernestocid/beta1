package testgeneration.coveragecriteria;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import criteria.AllCombinations;
import criteria.EachChoice;
import criteria.Pairwise;
import general.CombinatorialCriteria;
import parser.Operation;
import testgeneration.Block;

public abstract class InputSpacePartitionCriterion extends CoverageCriterion {

	private Operation operationUnderTest;
	private CombinatorialCriteria combinatorialCriteria;



	public InputSpacePartitionCriterion(Operation operationUnderTest, CombinatorialCriteria combinatorialCriteria) {
		this.operationUnderTest = operationUnderTest;
		this.combinatorialCriteria = combinatorialCriteria;
	}



	abstract public List<List<Block>> getBlocks();



	protected Set<List<Block>> getCombinationsAccordingToCombinatorialCriterion(List<List<Block>> equivalenceClassesBlocks) {
		if (this.combinatorialCriteria == CombinatorialCriteria.PAIRWISE) {
			return getPairwiseCombinations(equivalenceClassesBlocks);
		} else if (this.combinatorialCriteria == CombinatorialCriteria.EACH_CHOICE) {
			return getEachChoiceCombinations(equivalenceClassesBlocks);
		} else if (this.combinatorialCriteria == CombinatorialCriteria.ALL_COMBINATIONS) {
			return getAllCombinations(equivalenceClassesBlocks);
		} else {
			return new HashSet<List<Block>>();
		}
	}



	private Set<List<Block>> getAllCombinations(List<List<Block>> blocks) {
		return new AllCombinations<Block>(blocks).getCombinations();
	}



	private Set<List<Block>> getEachChoiceCombinations(List<List<Block>> blocks) {
		return new EachChoice<Block>(blocks).getCombinations();
	}



	private Set<List<Block>> getPairwiseCombinations(List<List<Block>> blocks) {
		return new Pairwise<Block>(blocks).getCombinations();
	}



	@Override
	public Operation getOperationUnderTest() {
		return this.operationUnderTest;
	}



	public CombinatorialCriteria getCombinatorialCriterion() {
		return this.combinatorialCriteria;
	}
}
