package general;

public enum CombinatorialCriteria {

	
	ALL_COMBINATIONS("All-Combinations"), EACH_CHOICE("Each-Choice"), PAIRWISE("Pairwise");
	
	
	private final String text;

	
	private CombinatorialCriteria(String text) {
		this.text = text;
	}
	
	
	
	public static CombinatorialCriteria get(int index) {
		return values()[index];
	}
	
	

	@Override
	public String toString() {
		return text;
	}



	public String getAcronym() {
		if(text.equals("All-Combinations")) {
			return "AC";
		} else if(text.equals("Each-Choice")) {
			return "EC";
		} else if(text.equals("Pairwise")) {
			return "PW";
		} else {
			return "error";
		}
	}
}
