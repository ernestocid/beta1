package testgeneration;

import java.util.Set;

import parser.Characteristic;
import parser.CharacteristicType;

public class EquivalenceClasses {

	public static Set<Block> findBlocks(Characteristic c) {
		
		if(c.getType() == CharacteristicType.PRE_CONDITION || c.getType() == CharacteristicType.CONDITIONAL) {
			if(c.isTypingCharacteristic()) {
				
			} else {
				
			}
		} else if (c.getType() == CharacteristicType.INVARIANT) {
			
		} else {
			
		}
		
		return null;
	}

}
