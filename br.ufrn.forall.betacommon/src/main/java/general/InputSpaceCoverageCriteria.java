package general;

public enum InputSpaceCoverageCriteria {

	EQUIVALENT_CLASSES_EACH_CHOICE("Equivalent Classes (Each-Choice)"), EQUIVALENT_CLASSES_PAIRWISE("Equivalent Classes (Pairwise)"), EQUIVALENT_CLASSES_ALL_COMBINATIONS(
			"Equivalent Classes (All-Combinations)"), BOUNDARY_ANALYSIS_EACH_CHOICE("Boundary Analysis (Each-Choice)"), BOUNDARY_ANALYSIS_PAIRWISE(
			"Boundary Analysis (Pairwise)"), BOUNDARY_ANALYSIS_ALL_COMBINATIONS("Boundary Analysis (All-Combinations)");

	private final String text;



	private InputSpaceCoverageCriteria(String text) {
		this.text = text;
	}



	public static InputSpaceCoverageCriteria get(int index) {
		return values()[index];
	}



	@Override
	public String toString() {
		return text;
	}

}
