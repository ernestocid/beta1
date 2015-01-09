package testgeneration.coveragecriteria;

import general.CombinatorialCriterias;

import java.util.List;
import java.util.Set;

import parser.Operation;
import testgeneration.Block;
import testgeneration.ECBlockBuilder;
import testgeneration.Partitioner;

public class EquivalenceClasses extends InputSpacePartitionCriterion {

	public EquivalenceClasses(Operation operationUnderTest, CombinatorialCriterias combinatorialCriteria) {
		super(operationUnderTest, combinatorialCriteria);
	}



	@Override
	public Set<List<Block>> getCombinationsAsListsOfBlocks() {
		return getCombinationsAccordingToCombinatorialCriterion(getBlocks());
	}



	@Override
	public List<List<Block>> getBlocks() {
		return new ECBlockBuilder(new Partitioner(getOperationUnderTest())).getBlocksAsListsOfBlocks();
	}

}
