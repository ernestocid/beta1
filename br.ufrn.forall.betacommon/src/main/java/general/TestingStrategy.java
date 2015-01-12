package general;

public enum TestingStrategy {

	INPUT_SPACE_COVERAGE("Input Space Coverage"), LOGICAL_COVERAGE("Logical Coverage");

	private final String text;



	private TestingStrategy(String text) {
		this.text = text;
	}



	public static TestingStrategy get(int index) {
		return values()[index];
	}



	@Override
	public String toString() {
		return text;
	}

}
