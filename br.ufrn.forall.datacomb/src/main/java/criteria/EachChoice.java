package criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class responsible for generating combinations using the Each-choice data
 * combinations criteria. The criteria generates a set of combinations ensuring
 * that each value for each parameter appears in at least one test case.
 * 
 * @author ernestocid
 * 
 */
public class EachChoice<T> extends Criteria<T> {

	
	public EachChoice(List<List<T>> parametersInputValues) {
		super(parametersInputValues);
	}

	

	/**
	 * Create the combinations according to the criteria. The method combines
	 * values of the same index, when the list reaches its limit, it will always use
	 * its last values.
	 * 
	 * @return combinations: Set of combinations where each element is a combinations
	 * 							organized as a list;
	 */
	@Override
	public Set<List<T>> getCombinations() {
		if (getParametersInputValues().size() < 2) {
			return generateOneParameterCombination();
		} else {
			return generateCombinations();			
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



	private Set<List<T>> generateCombinations() {
		Set<List<T>> combinations = new HashSet<List<T>>();
		
		int maxNumberOfValues = getMaxNumberOfValues(getParametersInputValues());

		for (int blockIndex = 0; blockIndex < maxNumberOfValues; blockIndex++) {
			List<T> combination = new ArrayList<T>();

			for (List<T> parameterBlocks : getParametersInputValues()) {
				int numberOfBlocks = parameterBlocks.size();
				if (blockIndex < numberOfBlocks) {
					combination.add(parameterBlocks.get(blockIndex));
				} else {
					combination.add(getLastBlock(parameterBlocks));
				}
			}

			combinations.add(combination);
		}
		
		return combinations;
	}

	
	
	/**
	 * Given the possible values for a list of parameters, it returns the number
	 * of values of the parameter with more values.
	 * 
	 * @param parameters: Lists of parameter values;
	 * @return maxNumberofValues: the number of values of the parameter with
	 *         more values;
	 */
	private int getMaxNumberOfValues(List<List<T>> parameters) {
		int maxNumberOfValues = 0;

		for (List<T> parameterInputValues : parameters) {
			int numberOfValues = parameterInputValues.size();
			if (numberOfValues > maxNumberOfValues) {
				maxNumberOfValues = numberOfValues;
			}
		}

		return maxNumberOfValues;
	}
	
	

	private T getLastBlock(List<T> parameter) {
		return parameter.get(parameter.size() - 1);
	}
}