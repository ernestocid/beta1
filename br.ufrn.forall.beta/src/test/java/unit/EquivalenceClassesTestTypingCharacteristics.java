package unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Characteristic;
import parser.Machine;
import parser.Operation;
import testgeneration.Block;
import testgeneration.EquivalenceClasses;
import testgeneration.Partitioner;

public class EquivalenceClassesTestTypingCharacteristics {

	@Test
	public void findBlocksForBelongsToNAT_returnsBelongsAndNotBelongsBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation belongsToNAT = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(belongsToNAT);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : NAT");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : NAT isNegative: false");

		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa /: NAT");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: aa /: NAT isNegative: true");

		expectedBlocks.add(b1);
		expectedBlocks.add(b2);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic, belongsToNAT);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}



	@Test
	public void findBlocksForBelongsToNAT1_returnsBelongsAndNotBelongsBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation belongsToNAT1 = machine.getOperation(1);
		Partitioner partitioner = new Partitioner(belongsToNAT1);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : NAT1");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : NAT1 isNegative: false");

		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa /: NAT1");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: aa /: NAT1 isNegative: true");

		expectedBlocks.add(b1);
		expectedBlocks.add(b2);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic, belongsToNAT1);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}



	@Test
	public void findBlocksForBelongsToINT_returnsBelongsBlock() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation belongsToINT = machine.getOperation(2);
		Partitioner partitioner = new Partitioner(belongsToINT);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : INT");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : INT isNegative: false");

		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic, belongsToINT);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}



	@Test
	public void findBlocksForBelongsToBOOL_returnsBelongsBlock() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation belongsToBOOL = machine.getOperation(3);
		Partitioner partitioner = new Partitioner(belongsToBOOL);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : BOOL");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : BOOL isNegative: false");

		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic, belongsToBOOL);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}



	@Test
	public void findBlocksForBelongsToInterval_returnsBelongsToRangeAndBelongsBeforeRangeAndBelongsAfterRangeBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation belongsToInterval = machine.getOperation(4);
		Partitioner partitioner = new Partitioner(belongsToInterval);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : 1..5");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : 1..5 isNegative: false");

		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa : MININT..(1-1)");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: aa : MININT..(1-1) isNegative: true");

		Block b3 = mock(Block.class);
		when(b3.getBlock()).thenReturn("aa : (5+1)..MAXINT");
		when(b3.isNegative()).thenReturn(true);
		when(b3.toString()).thenReturn("Block: aa : (5+1)..MAXINT isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		expectedBlocks.add(b3);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic, belongsToInterval);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForBelongsToAbstractSet_returnsBelongsBlock() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation belongsToAbstractSet = machine.getOperation(5);
		Partitioner partitioner = new Partitioner(belongsToAbstractSet);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : ASET");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : ASET isNegative: false");
		
		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic, belongsToAbstractSet);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForBelongsToId_returnsBelongsAndNotBelongsBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation belongsToAbstractSet = machine.getOperation(6);
		Partitioner partitioner = new Partitioner(belongsToAbstractSet);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : ww");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : ww isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa /: ww");
		when(b2.isNegative()).thenReturn(false);
		when(b2.toString()).thenReturn("Block: aa /: ww isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic, belongsToAbstractSet);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}



	private Set<String> blocksToStrings(Set<Block> blocks) {
		Set<String> blocksAsStrings = new HashSet<String>();

		for (Block block : blocks) {
			blocksAsStrings.add(block.toString());
		}

		return blocksAsStrings;
	}
}
