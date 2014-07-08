package general;

public enum FunctionalCriterias {

	EQUIVALENT_CLASSES("Equivalence Classes"), BOUNDARY_VALUE_ANALISYS("Boundary Analysis");

	private final String text;

	private FunctionalCriterias(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
