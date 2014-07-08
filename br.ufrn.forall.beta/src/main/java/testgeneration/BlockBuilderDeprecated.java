package testgeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import parser.Characteristic;

public abstract class BlockBuilderDeprecated {

	private Partitioner partitioner;
	private Map<Characteristic, List<Block>> mappingBlocks;
	
	
	public BlockBuilderDeprecated(Partitioner partitioner) {
		this.partitioner = partitioner;
	}
	
	
	
	public Map<Characteristic, List<Block>> getMappingBlocks() {
		return mappingBlocks;
	}



	public void setMappingBlocks(Map<Characteristic, List<Block>> mappingBlocks) {
		this.mappingBlocks = mappingBlocks;
	}



	public Partitioner getPartitioner() {
		return this.partitioner;
	}



	public List<List<String>> parseMapToList(Map<String, List<String>> partitionBlocks) {
		List<List<String>> parameterInputValues = new ArrayList<List<String>>();

		Set<String> keySet = partitionBlocks.keySet();
		for (String key : keySet) {
			parameterInputValues.add(partitionBlocks.get(key));
		}
	
		return parameterInputValues;
	}
	
	
	
	public String negateClause(String clause) {
		return "not(" + clause + ")";
	}
	
	
	public abstract Map<String, List<String>> getBlocks();
	public abstract List<List<String>> getBlocksAsLists();

}
