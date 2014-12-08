package testgeneration;

import java.util.List;
import java.util.Map;

import animator.Animation;

public interface PredicateEvaluator {

	public List<Animation> getSolutions();
	public List<String> getInfeasiblePredicates();
	public Map<String, Boolean> getFormulaAndTestTypeMap();

}
