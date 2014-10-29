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

public class EquivalenceClassesTestCommonCharacteristics {

	
	@Test
	public void findBlocksForRelationalEquals_returnsEqualsAndNotEqualsBlocks() {
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
	public void findBlocksForRelationalNotEquals_returnsNotEqualsAndEqualsBlocks() {
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
	public void findBlocksForRelationalGreater_returnsGreaterAndEqualsAndLessBlocks() {
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
	public void findBlocksForRelationalGreaterEqual_returnsGreaterEqualAndLessBlocks() {
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
	public void findBlocksForRelationalLess_returnsLessAndEqualsAndGreaterBlocks() {
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
	public void findBlocksForRelationalLessEqual_returnsLessEqualsAndGreaterBlocks() {
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
	
	
	
	@Test
	public void findBlocksForNegation_returnsNegationAndNoNegationBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation notPredicate = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(notPredicate);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("not(xx = yy)");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: not(xx = yy) isNegative: false");
		
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
	public void findBlocksForImplication_returnsImplicationAndNegImplicationBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation implication = machine.getOperation(1);
		Partitioner partitioner = new Partitioner(implication);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("((xx : NATURAL & yy : NATURAL) => ((xx + yy) : NATURAL))");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: ((xx : NATURAL & yy : NATURAL) => ((xx + yy) : NATURAL)) isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("not(((xx : NATURAL & yy : NATURAL) => ((xx + yy) : NATURAL)))");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: not(((xx : NATURAL & yy : NATURAL) => ((xx + yy) : NATURAL))) isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForEquivalence_returnsEquivalenceAndNegEquivalenceBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation equivalence = machine.getOperation(2);
		Partitioner partitioner = new Partitioner(equivalence);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("((xx : NAT) <=> ((xx + 1) : NAT))");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: ((xx : NAT) <=> ((xx + 1) : NAT)) isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("not(((xx : NAT) <=> ((xx + 1) : NAT)))");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: not(((xx : NAT) <=> ((xx + 1) : NAT))) isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForUniversalQuantifier_returnsUniversalQuantifierAndUniversalQuantifierWithNegPredicateBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation universalQuantifier = machine.getOperation(3);
		Partitioner partitioner = new Partitioner(universalQuantifier);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("!(zz).((zz : NAT) => ((zz + 1) : NAT))");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: !(zz).((zz : NAT) => ((zz + 1) : NAT)) isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("not(!(zz).((zz : NAT) => ((zz + 1) : NAT)))");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: not(!(zz).((zz : NAT) => ((zz + 1) : NAT))) isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForBelongs_returnsBelongsAndNotBelongsBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation universalQuantifier = machine.getOperation(4);
		Partitioner partitioner = new Partitioner(universalQuantifier);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa : ww");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa : ww isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa /: ww");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: aa /: ww isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForNotBelongs_returnsNotBelongsAndBelongsBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation universalQuantifier = machine.getOperation(5);
		Partitioner partitioner = new Partitioner(universalQuantifier);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa /: ww");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa /: ww isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa : ww");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: aa : ww isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForSubset_returnsSubsetAndNotSubsetBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation subset = machine.getOperation(6);
		Partitioner partitioner = new Partitioner(subset);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa <: ww");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa <: ww isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa /<: ww");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: aa /<: ww isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForNotSubset_returnsNotSubsetAndSubsetBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation notSubset = machine.getOperation(7);
		Partitioner partitioner = new Partitioner(notSubset);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa /<: ww");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa /<: ww isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa <: ww");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: aa <: ww isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForSubsetStrict_returnsSubsetStrictAndNotSubsetStrictBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation subsetStrict = machine.getOperation(8);
		Partitioner partitioner = new Partitioner(subsetStrict);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa <<: ww");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa <<: ww isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa /<<: ww");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: aa /<<: ww isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForNotSubsetStrict_returnsNotSubsetStrictAndSubsetStrictBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation notSubsetStrict = machine.getOperation(9);
		Partitioner partitioner = new Partitioner(notSubsetStrict);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("aa /<<: ww");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: aa /<<: ww isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("aa <<: ww");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: aa <<: ww isNegative: true");
		
		expectedBlocks.add(b1);
		expectedBlocks.add(b2);
		
		// Result
		
		Characteristic characteristic = new ArrayList<Characteristic>(partitioner.getCharacteristicsFromPrecondition()).get(0);
		Set<Block> actualResult = EquivalenceClasses.findBlocks(characteristic);

		// Assertions
		
		assertEquals(blocksToStrings(expectedBlocks), blocksToStrings(actualResult));
	}
	
	
	
	@Test
	public void findBlocksForExistentialQuantifier_returnsExistentialQuantifierAndExistentialWithNegPredicateBlocks() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimplePredicates.mch"));
		Operation existentialQuantifier = machine.getOperation(10);
		Partitioner partitioner = new Partitioner(existentialQuantifier);
		
		// Setting up expected results
		
		Set<Block> expectedBlocks = new HashSet<Block>();
		
		Block b1 = mock(Block.class);
		when(b1.getBlock()).thenReturn("#(zz).(zz : NAT & zz > xx)");
		when(b1.isNegative()).thenReturn(false);
		when(b1.toString()).thenReturn("Block: #(zz).(zz : NAT & zz > xx) isNegative: false");
		
		Block b2 = mock(Block.class);
		when(b2.getBlock()).thenReturn("#(zz).(not(zz : NAT & zz > xx))");
		when(b2.isNegative()).thenReturn(true);
		when(b2.toString()).thenReturn("Block: #(zz).(not(zz : NAT & zz > xx)) isNegative: true");
		
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
