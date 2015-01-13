package testgeneration.coveragecriteria;

import general.CombinatorialCriteria;

import java.util.List;
import java.util.Set;

import parser.Operation;
import testgeneration.Block;
import testgeneration.ECBlockBuilder;
import testgeneration.Partitioner;

public class EquivalenceClasses extends InputSpacePartitionCriterion {

	public EquivalenceClasses(Operation operationUnderTest, CombinatorialCriteria combinatorialCriteria) {
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



	@Override
	public String getName() {
		String name = "Equivalent Classes ";

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
		String acronym = "EC-";

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
