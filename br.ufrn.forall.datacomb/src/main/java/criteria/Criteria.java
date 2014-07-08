package criteria;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * Class with base methods for implementing a data combination criteria.
 * 
 * @author ernestocid
 *
 */
public abstract class Criteria<T> {
	
	
	private List<List<T>> parametersInputValues;
	
	
	/**
	 * The constructor filters the empty lists that might be added 
	 * on the list of values.
	 * 
	 * @param parametersInputValues: values for each parameter
	 */
	public Criteria(List<List<T>> parametersInputValues) {
		List<List<T>> filteredList = new ArrayList<List<T>>();
		
		// Filtering empty lists
		for(List<T> parameterInputValue : parametersInputValues) {
			if(!parameterInputValue.isEmpty()) {
				filteredList.add(parameterInputValue);
			}
		}
		
		this.parametersInputValues = filteredList;
	}

	
	
	public List<List<T>> getParametersInputValues() {
		return parametersInputValues;
	}

	
	
	public void setParametersInputValues(List<List<T>> parametersInputValues) {
		this.parametersInputValues = parametersInputValues;
	}
	
	
	
	/**
	 * Returns each test combination as a String.
	 * 
	 * @return combinationsAsStrings: set of combinations in the form of Strings
	 */
	public Set<String> getCombinationsAsStrings() {
		Set<List<T>> combinations = getCombinations();
		
		Set<String> combinationsAsStrings = new TreeSet<String>(Collator.getInstance());
		
		for (List<T> combination : combinations) {
			StringBuffer string = new StringBuffer("");
			int valueCount = 1;
			for (T element : combination) {
				if(valueCount < combination.size()) {
					
					if(element == null) {

					} else {
						string.append(element.toString() + " & ");
					}
					
				} else {
					string.append(element.toString());
				}
				valueCount++;
			}
			
			combinationsAsStrings.add(string.toString());
			
		}
		
		return combinationsAsStrings;
	}
	
	
	
	public abstract Set<List<T>> getCombinations();
	
}
