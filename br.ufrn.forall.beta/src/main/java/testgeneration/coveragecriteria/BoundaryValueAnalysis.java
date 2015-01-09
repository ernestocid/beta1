package testgeneration.coveragecriteria;

import general.CombinatorialCriterias;

import java.util.List;
import java.util.Set;

import parser.Operation;
import testgeneration.BVBlockBuilder;
import testgeneration.Block;
import testgeneration.Partitioner;

public class BoundaryValueAnalysis extends InputSpacePartitionCriterion {

	public BoundaryValueAnalysis(Operation operationUnderTest, CombinatorialCriterias combinatorialCriteria) {
		super(operationUnderTest, combinatorialCriteria);
	}



	@Override
	public Set<List<Block>> getCombinationsAsListsOfBlocks() {
		return getCombinationsAccordingToCombinatorialCriterion(getBlocks());
	}



	@Override
	public List<List<Block>> getBlocks() {
		return new BVBlockBuilder(new Partitioner(getOperationUnderTest())).getBlocksAsListsOfBlocks();
	}

}
