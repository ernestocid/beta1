package testgeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parser.Characteristic;

public abstract class BlockBuilder {

	private Map<Characteristic, List<Block>> blocks = new HashMap<Characteristic, List<Block>>();
	private Partitioner partitioner;
	
	
	public BlockBuilder(Partitioner partitioner) {
		this.partitioner = partitioner;
	}

	

	public Map<Characteristic, List<Block>> getBlocks() {
		return blocks;
	}



	public void setBlocks(Map<Characteristic, List<Block>> blocks) {
		this.blocks = blocks;
	}



	public Partitioner getPartitioner() {
		return partitioner;
	}

	

	public void setPartitioner(Partitioner partitioner) {
		this.partitioner = partitioner;
	}
	
	
	
	public String negateClause(String clause) {
		return "not(" + clause + ")";
	}
	
	
	
	public List<List<String>> getBlocksAsLists() {
		List<List<String>> parameterInputValues = new ArrayList<List<String>>();

		for(Map.Entry<String, List<Block>> entry : getBlocksWithStringKeys().entrySet()) {
			List<String> blocksAsStrings = new ArrayList<String>();
			
			for(Block block : entry.getValue()) {
				blocksAsStrings.add(block.getBlock());
			}
			
			parameterInputValues.add(blocksAsStrings);
		}
	
		return parameterInputValues;
	}
	
	
	
	public List<List<Block>> getBlocksAsListsOfBlocks() {
		List<List<Block>> blockLists = new ArrayList<List<Block>>();
		
		for(Map.Entry<String, List<Block>> entry : getBlocksWithStringKeys().entrySet()) {
			blockLists.add(entry.getValue());
		}
		
		return blockLists;
	} 


	
	/**
	 * Return the blocks map with String keys instead of Characteristic keys.
	 * This conversion is needed because using complex objects as keys on Maps
	 * sometimes results in different positions in the HashMap
	 * 
	 * @return Map of blocks using strings as keys.
	 */
	public Map<String, List<Block>> getBlocksWithStringKeys() {
		Map<String, List<Block>> blocksWithStringKeys = new HashMap<String, List<Block>>();
		
		for(Map.Entry<Characteristic, List<Block>> entry : this.blocks.entrySet()) {
			blocksWithStringKeys.put(entry.getKey().toString(), entry.getValue());
		}
		
		return blocksWithStringKeys;
	}
	
}
