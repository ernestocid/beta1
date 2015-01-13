package testgeneration.coveragecriteria;

import java.util.List;
import java.util.Set;

import parser.Operation;
import testgeneration.Block;

public abstract class CoverageCriterion {

	abstract public Operation getOperationUnderTest();
	abstract public Set<List<Block>> getCombinationsAsListsOfBlocks();
	abstract public String getName();
	abstract public String getAcronym();

}
