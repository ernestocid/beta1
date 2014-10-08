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
	
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Characteristic) {
			Characteristic c = (Characteristic) obj;
			if(c.getCharacteristic().equals(getCharacteristic())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	
	@Override
	public int hashCode() {
		return getCharacteristic().hashCode();
	}
	
	
	
	public abstract Set<String> getVariables();
	public abstract boolean isTypingCharacteristic();
	public abstract boolean isIntervalCharacteristic();
	public abstract String toString();
	
}
