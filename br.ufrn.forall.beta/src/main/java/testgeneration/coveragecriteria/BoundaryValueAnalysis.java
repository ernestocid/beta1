package testgeneration.coveragecriteria;

import general.CombinatorialCriteria;

import java.util.List;
import java.util.Set;

import parser.Operation;
import testgeneration.BVBlockBuilder;
import testgeneration.Block;
import testgeneration.Partitioner;

public class BoundaryValueAnalysis extends InputSpacePartitionCriterion {

	public BoundaryValueAnalysis(Operation operationUnderTest, CombinatorialCriteria combinatorialCriteria) {
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



	@Override
	public String getName() {
		String name = "Boundary Value Analysis ";

		if (getCombinatorialCriterion() == CombinatorialCriteria.EACH_CHOICE) {
			name = name + " (Each-choice)";
		} else if (getCombinatorialCriterion() == CombinatorialCriteria.PAIRWISE) {
			name = name + " (Pairwise)";
		} else if (getCombinatorialCriterion() == CombinatorialCriteria.ALL_COMBINATIONS) {
			name = name + " (All-Combinations)";
		} else {
			name = name + " (Unknown)";
		}

		return name;
	}



	@Override
	public String getAcronym() {
		String acronym = "BV-";

		if (getCombinatorialCriterion() == CombinatorialCriteria.EACH_CHOICE) {
			acronym = acronym + "EC";
		} else if (getCombinatorialCriterion() == CombinatorialCriteria.PAIRWISE) {
			acronym = acronym + "PW";
		} else if (getCombinatorialCriterion() == CombinatorialCriteria.ALL_COMBINATIONS) {
			acronym = acronym + "AC";
		} else {
			acronym = acronym + "??";
		}

		return acronym;
	}

}
