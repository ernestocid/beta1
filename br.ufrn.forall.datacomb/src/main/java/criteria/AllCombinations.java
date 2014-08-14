package criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class generates all possible combinations between values for a list of parameters.
 * Since the high number of parameters may result in combinatorial explosions you can limit
 * the max number of combinations generated. The algorithm uses a non-recursive approach.
 * 
 * @author ernestocid
 *
 */
public class AllCombinations<T> extends Criteria<T> {

	
	private long maxCombinations;
	
	
	public AllCombinations(List<List<T>> parametersInputValues) {
		super(parametersInputValues);
		this.maxCombinations = totalCombinationsOf(getParametersInputValues());
	}
	
	
	
	/**
	 * Constructor that receives a max number of combinations as parameter to
	 * avoid combinatorial explosions.
	 *  
	 * @param parametersInputValues list of possible values for each parameter
	 * @param maxCombinations max number of combinations that should be generated
	 */
	public AllCombinations(List<List<T>> parametersInputValues, long maxCombinations) {
		super(parametersInputValues);
		settMaxCombinations(maxCombinations);
	}

	
	
	private void settMaxCombinations(long maxCombinations) {
		this.maxCombinations = maxCombinations;
	}
	
	
	
	private long getMaxCombinations() {
		return maxCombinations;
	}
	
	
	
	/**
	 * Generates all combinations for the values of the given parameters. The method
	 * uses a buffer to compute the combinations. The buffer is an array which stores the
	 * indexes for the parameter values. For example, given param1={a1, a2} and
	 * param2={b1,b2}, the buffer would work like this:
	 * 
	 * {0, 0} => {a1, b1}
	 * {0, 1} => {a1, b2}
	 * {1, 0} => {a2, b1}
	 * {1, 1} => {a2, b2}
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
			
			List<T> singleParameterValues = getParametersInputValues().get(0);
			
			for(T element : singleParameterValues) {
				List<T> singleElementList = new ArrayList<T>();
				singleElementList.add(element);
				combinations.add(singleElementList);
			}
			
			
//			List<T> singleElement = new ArrayList<T>();
//			singleElement.add(getParametersInputValues().get(0).get(0));
//			combinations.add(singleElement);
			return combinations;
		} else {
			return combinations;
		}
	}
	


	private Set<List<T>> generateCombinations() {
		Set<List<T>> combinations = new HashSet<List<T>>();
		long totalCombinations = totalCombinationsOf(getParametersInputValues());
		
		// Stores the index for the input parameters in each combination. Each element of the array represents a parameter index.
		int combBuffer[] = startBuffer(getParametersInputValues());

		// Add first combination
		combinations.add(getCombination(combBuffer));

		// Find and add remaining combinations
		for(long combIndex = 0; combIndex < totalCombinations - 1 && combIndex < getMaxCombinations() - 1; combIndex++) {

			for(int bufferIndex = combBuffer.length - 1; bufferIndex >= 0; bufferIndex--) {
				int paramListSize = getParametersInputValues().get(bufferIndex).size();
				
				if(combBuffer[bufferIndex] < paramListSize - 1) {
					combBuffer[bufferIndex]++;
					break;
				} else {
					combBuffer[bufferIndex] = 0;
				}
			}

			combinations.add(getCombination(combBuffer));
		}
		
		return combinations;
	}
	

	
	/**
	 * Calculates the total number of possible combinations for the given parameters and their values
	 * @param parametersInputValues list of values for each parameter
	 * @return total number of possible combinations
	 */
	private long totalCombinationsOf(List<List<T>> parametersInputValues) {
		if(parametersInputValues.isEmpty()) {
			return 0;
		} else {
			long count = 1;
			for(List<T> current : parametersInputValues) {
				count = count * current.size();
			}
			return count;	
		}
	}
	
	
	
	private List<T> getCombination(int[] combBuffer) {
		List<T> combination = new ArrayList<T>();
		
		for (int i = 0; i < combBuffer.length; i++) {
			combination.add(getParametersInputValues().get(i).get(combBuffer[i]));
		}
		
		return combination;
	}
	
	
	
	private int[] startBuffer(List<List<T>> parametersInputValues) {
		int combBuffer[] = new int[parametersInputValues.size()];
		
		for (int i = 0; i < combBuffer.length; i++) {
			combBuffer[i] = 0;
		}
		
		return combBuffer;
	}

}
