package unit;

import static org.junit.Assert.assertEquals;
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
import testgeneration.BoundaryValues;
import testgeneration.Partitioner;


public class BoundaryValuesTestTypingCharacteristics {

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
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, belongsToNAT);

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
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, belongsToNAT1);

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
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, belongsToINT);

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
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, belongsToBOOL);

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
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, belongsToInterval);

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
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, belongsToAbstractSet);

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
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, belongsToAbstractSet);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForIsTotalFunction_returnsisTotalFunctionBlock() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation isTotalFunction = machine.getOperation(7);
		Partitioner partitioner = new Partitioner(isTotalFunction);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : (NAT --> NAT)");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : (NAT --> NAT) isNegative: false");
		
		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, isTotalFunction);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForIsPartialFunction_returnsisPartialFunctionBlock() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation isPartialFunction = machine.getOperation(8);
		Partitioner partitioner = new Partitioner(isPartialFunction);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : (NAT +-> NAT)");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : (NAT +-> NAT) isNegative: false");
		
		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, isPartialFunction);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForIsInjectiveFunction_returnsisInjectiveFunctionBlock() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation isInjectiveFunction = machine.getOperation(9);
		Partitioner partitioner = new Partitioner(isInjectiveFunction);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : (NAT >+> NAT)");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : (NAT >+> NAT) isNegative: false");
		
		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, isInjectiveFunction);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForIsTotalSurjectiveFunction_returnsisTotalSurjectiveFunctionBlock() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation isTotalSurjectiveFunction = machine.getOperation(10);
		Partitioner partitioner = new Partitioner(isTotalSurjectiveFunction);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : (NAT -->> NAT)");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : (NAT -->> NAT) isNegative: false");
		
		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, isTotalSurjectiveFunction);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForIsPartialSurjectiveFunction_returnsisPartialSurjectiveFunctionBlock() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation isPartialSurjectiveFunction = machine.getOperation(11);
		Partitioner partitioner = new Partitioner(isPartialSurjectiveFunction);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : (NAT +->> NAT)");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : (NAT +->> NAT) isNegative: false");
		
		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, isPartialSurjectiveFunction);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}



	@Test
	public void findBlocksForIsBijectiveFunction_returnsIsBijectiveFunctionBlock() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation isBijective = machine.getOperation(12);
		Partitioner partitioner = new Partitioner(isBijective);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : (NAT >->> NAT)");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : (NAT >->> NAT) isNegative: false");
		
		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, isBijective);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForBelongsToEnumeratedSet_returnsBelongsAndNotBelongsBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation belongsToEnumeratedSet = machine.getOperation(13);
		Partitioner partitioner = new Partitioner(belongsToEnumeratedSet);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : {1, 2, 3}");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : {1, 2, 3} isNegative: false");

		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa /: {1, 2, 3}");
		when(b2.isNegative()).thenReturn(false);
		when(b2.toString()).thenReturn("Block: aa /: {1, 2, 3} isNegative: true");

		expectedBlocks.add(b1);
		expectedBlocks.add(b2);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, belongsToEnumeratedSet);

		// Assertions

		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForBelongsToSTRING_returnsBelongsSTRING() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TypingPredicates.mch"));
		Operation belongsToSTRING = machine.getOperation(14);
		Partitioner partitioner = new Partitioner(belongsToSTRING);

		// Setting up expected results

		Set<Block> expectedBlocks = new HashSet<Block>();

		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : STRING");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : STRING isNegative: false");

		expectedBlocks.add(b1);

		// Result

		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = BoundaryValues.getInstance().findBlocks(characteristic, belongsToSTRING);

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
