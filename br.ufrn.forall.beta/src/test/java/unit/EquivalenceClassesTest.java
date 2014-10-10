package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.mockito.Mockito.*;
import parser.Characteristic;
import parser.Machine;
import parser.Operation;
import testgeneration.Block;
import testgeneration.EquivalenceClasses;
import testgeneration.Partitioner;

public class EquivalenceClassesTest {

	
	@Test
	public void shouldGenerateBlocksForRelationalEquals() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/RelationalOperations.mch"));
		Operation equals = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(equals);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("xx = yy");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: xx = yy isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("xx /= yy");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: xx /= yy isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBlocksForRelationalNotEquals() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/RelationalOperations.mch"));
		Operation notEquals = machine.getOperation(1);
		Partitioner partitioner = new Partitioner(notEquals);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("xx /= yy");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: xx /= yy isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("xx = yy");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: xx = yy isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBlocksForRelationalGreater() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/RelationalOperations.mch"));
		Operation greater = machine.getOperation(2);
		Partitioner partitioner = new Partitioner(greater);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("xx > yy");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: xx > yy isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("xx = yy");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: xx = yy isNegative: true");
		
		Block b3 = mock(Block.class);
		when(b3.getBlock()).thenReturn("xx < yy");
		when(b3.isNegative()).thenReturn(true);
		when(b3.toString()).thenReturn("Block: xx < yy isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		expectedBlocks.add(b3);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBlocksForRelationalGreaterEqual() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/RelationalOperations.mch"));
		Operation greaterEqual = machine.getOperation(3);
		Partitioner partitioner = new Partitioner(greaterEqual);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("xx >= yy");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: xx >= yy isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("xx < yy");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: xx < yy isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBlocksForRelationalLess() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/RelationalOperations.mch"));
		Operation less = machine.getOperation(4);
		Partitioner partitioner = new Partitioner(less);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("xx < yy");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: xx < yy isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("xx = yy");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: xx = yy isNegative: true");
		
		Block b3 = mock(Block.class);
		when(b3.getBlock()).thenReturn("xx > yy");
		when(b3.isNegative()).thenReturn(true);
		when(b3.toString()).thenReturn("Block: xx > yy isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		expectedBlocks.add(b3);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBlocksForRelationalLessEqual() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/RelationalOperations.mch"));
		Operation lessEqual = machine.getOperation(5);
		Partitioner partitioner = new Partitioner(lessEqual);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("xx <= yy");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: xx <= yy isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("xx > yy");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: xx > yy isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

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
