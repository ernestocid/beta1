package criteria;

import java.util.ArrayList;
import java.util.List;

public class PairCollection<T> {

	
	private List<TestPair<T>> pairs = new ArrayList<TestPair<T>>();

	
	public PairCollection(List<TestPair<T>> pairs) {
		this.pairs = pairs;
	}

	
	
	public PairCollection(List<T> inputParameterValues, List<List<T>> inputValueLists, int paramIndex) {
	
		this.pairs = new ArrayList<TestPair<T>>();
		
		for(T value : inputParameterValues) {
			int paramBIndex = 0;
			for(List<T> list : inputValueLists) {
				for (T value2 : list) {
					pairs.add(new TestPair<T>(paramIndex, paramBIndex, value, value2));
				}
				paramBIndex++;
			}
		}
		
	}

	
	
	public List<TestPair<T>> getPairs() {
		return pairs;
	}
	
	
	
	public List<List<T>> toList() {
	
		List<List<T>> valuesList = new ArrayList<List<T>>();
		
		for(TestPair<T> pair : pairs) {
			List<T> pairList = new ArrayList<T>();
			pairList.add(pair.getValueA());
			pairList.add(pair.getValueB());
			valuesList.add(pairList);
		}
		
		return valuesList;
		
	}

	
	
	public void removePairsCoveredBy(List<T> combination) {
		
		List<TestPair<T>> newPairs = new ArrayList<TestPair<T>>(pairs);
		
		for (TestPair<T> testPair : newPairs) {
			boolean firstValueFromPairEqualsValueFromCombination = combination.get(testPair.getValueAIndex()).equals(testPair.getValueA());
			boolean secondValueFromPairEqualsValueFromCombination = combination.get(testPair.getValueBIndex()).equals(testPair.getValueB());
			
			if(firstValueFromPairEqualsValueFromCombination && secondValueFromPairEqualsValueFromCombination) {
				pairs.remove(testPair);
			}
		}
		
	}

	
	
	public List<T> getCombinationsThatCoversMostPairs(List<T> combination, List<T> inputValuesForGrowth) {
		
		List<T> selectedInputCombination = null;
		int maxCoveredCount = 0;
		int coveredCount = 0;
		
		for(T inputValue : inputValuesForGrowth) {
			List<T> combinationCandidate = new ArrayList<T>(combination);
			combinationCandidate.add(inputValue);
			
			coveredCount = calculateCoveredPairsCount(combinationCandidate);
			
			if(coveredCount > maxCoveredCount) {
				maxCoveredCount = coveredCount;
				selectedInputCombination = combinationCandidate;
			}
		}
		
		if(selectedInputCombination == null) {
			selectedInputCombination = new ArrayList<T>(combination);
			selectedInputCombination.add(inputValuesForGrowth.get(0));
		}
		
		return selectedInputCombination;
		
	}

	
	
	private int calculateCoveredPairsCount(List<T> combination) {
	
		int coveredCount = 0;
		
		for (TestPair<T> pair : pairs) {
			if(pair.isCoveredBy(combination)) {
				coveredCount++;
			}
		}
		
		return coveredCount;
		
	}
}
