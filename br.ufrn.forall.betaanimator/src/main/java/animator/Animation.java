package animator;

import java.util.List;
import java.util.Map;

import parser.decorators.predicates.MyPredicate;


public class Animation {
	
	
	private MyPredicate predicate;
	private List<Map<String, String>> values;
	private String formula;

	
	public Animation(MyPredicate predicate, List<Map<String, String>> values) {
		this.predicate = predicate;
		this.values = values;
		this.formula = predicate.toString();
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
