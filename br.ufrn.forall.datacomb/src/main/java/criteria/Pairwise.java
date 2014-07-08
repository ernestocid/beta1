package criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Pairwise<T> extends Criteria<T> {

	
	public final static String EMPTY = "_";
	
	
	public Pairwise(List<List<T>> parametersInputValues) {
		super(parametersInputValues);
	}

	
	
	@Override
	public Set<List<T>> getCombinations() {

		if (getParametersInputValues().isEmpty() || getParametersInputValues().size() < 2) {
			return generateOneParameterCombination();
		} else {
			List<List<T>> initial = initialize(getParametersInputValues().get(0), getParametersInputValues().get(1));
			Set<List<T>> combinations = new HashSet<List<T>>();

			if (getParametersInputValues().size() <= 2) {
				combinations.addAll(initial);
				return combinations;
			} else {
				combinations.addAll(inParameterOrderGeneration(initial));
				return combinations;
			}
		}
		
	}
	
	
	
	private Set<List<T>> generateOneParameterCombination() {
		Set<List<T>> combinations = new HashSet<List<T>>();
		
		if(getParametersInputValues().size() == 1) {
			List<T> singleElement = new ArrayList<T>();
			singleElement.add(getParametersInputValues().get(0).get(0));
			combinations.add(singleElement);
			return combinations;
		} else {
			return combinations;
		}
	}

	
	
	private List<List<T>> inParameterOrderGeneration(List<List<T>> initialPairs) {
		List<List<T>> combinations = null;
		for (int paramIndex = 2; paramIndex < getParametersInputValues().size(); paramIndex++) {
			PairCollection<T> uncoveredPairs = horizontalGrowth(initialPairs, getParametersInputValues().get(paramIndex), getParametersInputValues().subList(0, paramIndex));
			combinations = new ArrayList<List<T>>();
			combinations.addAll(verticalGrowth(initialPairs, uncoveredPairs, paramIndex));
			initialPairs = combinations;
		}
		return combinations;
	}
	
	

	private Set<List<T>> verticalGrowth(List<List<T>> combinations, PairCollection<T> uncoveredPairs, int paramIndex) {
		List<List<T>> tempCombinations = new ArrayList<List<T>>();

		for (TestPair<T> testPair : uncoveredPairs.getPairs()) {
			List<T> comb = createCombinationForVerticalGrowth(paramIndex);
			comb.set(testPair.getValueAIndex(), testPair.getValueA());
			comb.set(testPair.getValueBIndex(), testPair.getValueB());
			tempCombinations.add(comb);
		}

		for (TestPair<T> testPair : uncoveredPairs.getPairs()) {
			for (List<T> combination : tempCombinations) {
				boolean growingParamEqualsCombination = combination.get(testPair.getValueAIndex()).equals(testPair.getValueA());
//				boolean otherValueIsEmpty = combination.get(testPair.getValueBIndex()).equals(EMPTY);
				boolean otherValueIsEmpty = combination.get(testPair.getValueBIndex()) == null;
				
				if (growingParamEqualsCombination && otherValueIsEmpty) {
					combination.set(testPair.getValueBIndex(), testPair.getValueB());
				}
			}
		}

		for (List<T> combination : tempCombinations) {
//			if (combination.contains(EMPTY)) {
			if (combination.contains(null)) {
				replaceEmptyValues(combination);
			}
		}

		Set<List<T>> newCombinations = new HashSet<List<T>>();
		newCombinations.addAll(combinations);
		newCombinations.addAll(tempCombinations);
		
		return newCombinations;
	}
	
	

	private void replaceEmptyValues(List<T> combination) {
		for (int i = 0; i < combination.size(); i++) {
//			if (combination.get(i).equals(EMPTY)) {
			if (combination.get(i) == null) {
				combination.set(i, getParametersInputValues().get(i).get(0));
			}
		}
	}
	
	

	private List<T> createCombinationForVerticalGrowth(int paramIndex) {
		List<T> combination = new ArrayList<T>(3);
		for (int i = 0; i <= paramIndex; i++) {
//			combination.add(EMPTY);
			combination.add(null);
		}
		return combination;
	}

	
	
	private PairCollection<T> horizontalGrowth(List<List<T>> initialPairs, List<T> inputValuesForGrowth, List<List<T>> previouslyGrownValues) {
		
		PairCollection<T> uncoveredPairs = new PairCollection<T>(inputValuesForGrowth, previouslyGrownValues, previouslyGrownValues.size());

		if (initialPairs.size() <= inputValuesForGrowth.size()) {
			return growCombinationsAndRemoveCoveredPairs(initialPairs, inputValuesForGrowth, uncoveredPairs);
		} else {
			PairCollection<T> uncoveredCombinations = growCombinationsAndRemoveCoveredPairs(initialPairs, inputValuesForGrowth, uncoveredPairs);
			
			for(int i = inputValuesForGrowth.size(); i < initialPairs.size(); i++) {
				List<T> newCombination = uncoveredPairs.getCombinationsThatCoversMostPairs(initialPairs.get(i), inputValuesForGrowth);
				initialPairs.set(i, newCombination);
				uncoveredPairs.removePairsCoveredBy(newCombination);
			}

			return uncoveredCombinations;
		}
		
	}

	
	
	private PairCollection<T> growCombinationsAndRemoveCoveredPairs(List<List<T>> combinations, List<T> inputValuesForGrowth, PairCollection<T> uncoveredPairs) {
		
		for(int i = 0; i < combinations.size(); i++) {
			if(i >= inputValuesForGrowth.size()) {
				break;
			} else {
				combinations.get(i).add(inputValuesForGrowth.get(i));
				uncoveredPairs.removePairsCoveredBy(combinations.get(i));
			}
		}
		
		return uncoveredPairs;
		
	}


	
	private List<List<T>> initialize(List<T> firstParamValues, List<T> secondParamValues) {
		List<List<T>> initialCombinations = new ArrayList<List<T>>();

		for (T firstParamValue : firstParamValues) {
			for (T secondParamValue : secondParamValues) {
				List<T> combination = new ArrayList<T>();
				combination.add(firstParamValue);
				combination.add(secondParamValue);
				initialCombinations.add(combination);
			}
		}

		return initialCombinations;
	}
}
