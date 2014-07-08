package unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import parser.Characteristic;
import testgeneration.Block;

public class BlockBuilderTest {

	
	public Map<String, List<Block>> parseMapKeysToStrings(Map<Characteristic, List<Block>> blocks) {
		Map<String, List<Block>> parserdBlocks = new HashMap<String, List<Block>>();
		
		for(Characteristic ch : blocks.keySet()) {
			parserdBlocks.put(ch.getCharacteristic(), blocks.get(ch));
		}
		
		return parserdBlocks;
	}
	
	
	
	public boolean compare(Map<String, List<Block>> expectedResult, Map<String, List<Block>> actualResult) {
		Set<String> actualResultCharacteristics = actualResult.keySet();
		Set<String> expectedResultCharacteristics = expectedResult.keySet();
		
		for (String characteristic : actualResultCharacteristics) {
			if(setHasCharacteristic(expectedResultCharacteristics, characteristic)) {
				List<Block> expectedBlocksForCharacteristic = expectedResult.get(characteristic);
				List<Block> actualBlocksForCharacteristic = actualResult.get(characteristic);
				
				if(!compareBlocks(expectedBlocksForCharacteristic, actualBlocksForCharacteristic)) {
					return false;
				}
			} else {
				return false;
			}
		}
		
		return true;
	}

	
	
	private boolean setHasCharacteristic(Set<String> keySet, String ch) {
		for (String characteristic : keySet) {
			if(characteristic.toString().equals(ch.toString())) {
				return true;
			}
		}
		return false;
	}
	
	

	private boolean compareBlocks(List<Block> firstBlockList, List<Block> secondBlockList) {
		if(firstBlockList.size() == secondBlockList.size()) {
			for (int i = 0; i < firstBlockList.size(); i++) {
				if(!firstBlockList.get(i).toString().equals(secondBlockList.get(i).toString()) || 
						firstBlockList.get(i).isNegative() != secondBlockList.get(i).isNegative()) {
					return false;
				}
			}
		} else {
			return false;
		}
		
		return true;
	}
	
}
