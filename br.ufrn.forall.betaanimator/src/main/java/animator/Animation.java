package animator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import parser.decorators.predicates.MyPredicate;


public class Animation {
	
	
	private MyPredicate predicate;
	private List<Map<String, String>> values;
	private String formula;

	
	public Animation(MyPredicate predicate, List<Map<String, String>> values) {
		this.predicate = predicate;
		this.formula = predicate.toString().replaceAll("i__", "");

		this.values = new ArrayList<Map<String, String>>();
		
		for(Map<String, String> solution : values) {
			Map<String, String> cleanSolutions = new HashMap<String, String>();

			for(Entry<String, String> entry : solution.entrySet()) {
				String cleanEntry = entry.getKey().replaceAll("i__", "");
				cleanSolutions.put(cleanEntry, entry.getValue());
			}
			
			this.values.add(cleanSolutions);
		}
		
	}

	

	public String getFormula() {
		return formula;
	}



	public List<Map<String, String>> getValues() {
		return this.values;
	}

	

	public MyPredicate getPredicate() {
		return this.predicate;
	}
	
}
