package general;

public enum PartitionStrategy {

	
	EQUIVALENT_CLASSES("Equivalence Classes"), BOUNDARY_VALUES("Boundary Values");
	
	
	private final String text;
	
	
	private PartitionStrategy(String text) {
		this.text = text;
	}

	
	
	public static PartitionStrategy get(int index) {
		return values()[index];
	}
	
	
	
	@Override
	public String toString() {
		return text;
	}
	
	
	
	public String getAcronym() {
		if(text.equals("Equivalence Classes")) {
			return "EC";
		} else if (text.equals("Boundary Values")) {
			return "BV";
		} else {
			return "error";
		}
	}

}
