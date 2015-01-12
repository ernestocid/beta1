package general;

public enum LogicalCoverageCriteria {

	PREDICATE_COVERAGE("Predicate Coverage"), 
	CLAUSE_COVERAGE("Clause Coverage"), 
	COMBINATORIAL_CLAUSE_COVERAGE("Combinatorial Clause Coverage"), 
	ACTIVE_CLAUSE_COVERAGE("Active Clause Coverage");

	private final String text;



	private LogicalCoverageCriteria(String text) {
		this.text = text;
	}



	public static LogicalCoverageCriteria get(int index) {
		return values()[index];
	}



	@Override
	public String toString() {
		return text;
	}

}
