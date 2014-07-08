package parser;

import java.util.Set;

public abstract class Characteristic {

	
	private CharacteristicType type;
	private String characteristc;
	
	
	public Characteristic(CharacteristicType type, String characteristc) {
		this.type = type;
		this.characteristc = characteristc;
	}

	
	
	public CharacteristicType getType() {
		return this.type;
	}
	
	
	
	public String getCharacteristic() {
		return this.characteristc;
	}
	
	
	
	public abstract Set<String> getVariables();
	public abstract boolean isTypingCharacteristic();
	public abstract boolean isIntervalCharacteristic();
	public abstract String toString();
	
}
