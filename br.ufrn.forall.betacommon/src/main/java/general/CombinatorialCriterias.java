package general;

public enum CombinatorialCriterias {

	
	ALL_COMBINATIONS("All-Combinations"), EACH_CHOICE("Each-Choice"), PAIRWISE("Pairwise");
	
	
	private final String text;

	
	
	private CombinatorialCriterias(String text) {
		this.text = text;
	}
	
	
	
	public static CombinatorialCriterias get(int index) {
		return values()[index];
	}
	
	

	@Override
	public String toString() {
		return text;
	}
	
}
