package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import testgeneration.Block;
import testgeneration.ECBlockBuilder;
import testgeneration.Partitioner;
import static org.mockito.Mockito.*;

public class ECBlockBuilderTest extends BlockBuilderTest {

	
	
	@Test
	public void shouldNotGenerateBoundaryBlocksForInvariantClauses() {

		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/Paperround.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("papers <: 1..163");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		ch1Blocks.add(ch1MockedBlock1);
		
		expectedBlocks.put("papers <: 1..163", ch1Blocks);

		// Characteristic 2
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("magazines <: papers");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		ch2Blocks.add(ch2MockedBlock1);
		
		expectedBlocks.put("magazines <: papers", ch2Blocks);
		
		// Characteristic 3

		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("card(papers) <= 60");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		ch3Blocks.add(ch3MockedBlock1);
		
		expectedBlocks.put("card(papers) <= 60", ch3Blocks);
		
		// Characteristic 4
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("hh : MININT..1 - 1");
		when(ch4MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("hh : 163 + 1..MAXINT");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		Block ch4MockedBlock3 = mock(Block.class);
		when(ch4MockedBlock3.toString()).thenReturn("hh : 1..163");
		when(ch4MockedBlock3.isNegative()).thenReturn(false);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		ch4Blocks.add(ch4MockedBlock3);
		
		expectedBlocks.put("hh : 1..163", ch4Blocks);
		
		// Characteristic 5
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("card(papers) < 60");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(card(papers) < 60)");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("card(papers) < 60", ch5Blocks);

		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateBlocksForMemberOfCharacteristics() {
		
		// Setting up basic objects for blocks generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - NAT set characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa : NAT");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa : NAT)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa : NAT", ch1Blocks);
		
		// Characteristic 2 - NAT1 set characteristic		
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb : NAT1");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(bb : NAT1)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb : NAT1", ch2Blocks);
		
		// Characteristic 3 - INT set characteristic
			
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("cc : INT");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		ch3Blocks.add(ch3MockedBlock1);
		
		expectedBlocks.put("cc : INT", ch3Blocks);
		
		// Characteristic 4 - BOOL set characteristic
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("dd : BOOL");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		ch4Blocks.add(ch4MockedBlock1);
		
		expectedBlocks.put("dd : BOOL", ch4Blocks);
		
		// Characteristic 5 - SET characteristic
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("ee : {1, 2, 3}");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(ee : {1, 2, 3})");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee : {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - RANGE characteristic
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("ff : MININT..1 - 1");
		when(ch6MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("ff : 5 + 1..MAXINT");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		Block ch6MockedBlock3 = mock(Block.class);
		when(ch6MockedBlock3.toString()).thenReturn("ff : 1..5");
		when(ch6MockedBlock3.isNegative()).thenReturn(false);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		ch6Blocks.add(ch6MockedBlock3);

		expectedBlocks.put("ff : 1..5", ch6Blocks);
		
		// Characteristic 7 - Abstract set characteristic
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("gg : NAME");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		ch7Blocks.add(ch7MockedBlock1);
				
		expectedBlocks.put("gg : NAME", ch7Blocks);
		
		// Characteristic 8 - SET characteristic
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("hh : awnser");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("not(hh : awnser)");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh : awnser", ch8Blocks);
		
		// Characteristic 9 - STRING set
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("ii : STRING");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		ch9Blocks.add(ch9MockedBlock1);
		
		expectedBlocks.put("ii : STRING", ch9Blocks);
		
		// Characteristic 10 - FUNC characteristic
		
		List<Block> ch10Blocks = new ArrayList<Block>();
		
		Block ch10MockedBlock1 = mock(Block.class);
		when(ch10MockedBlock1.toString()).thenReturn("jj : (NAT --> NAT)");
		when(ch10MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock2 = mock(Block.class);
		when(ch10MockedBlock2.toString()).thenReturn("not(jj : (NAT --> NAT))");
		when(ch10MockedBlock2.isNegative()).thenReturn(true);
		
		ch10Blocks.add(ch10MockedBlock1);
		ch10Blocks.add(ch10MockedBlock2);
		
		expectedBlocks.put("jj : (NAT --> NAT)", ch10Blocks);
		
		// Characteristic 11
		
		List<Block> ch11Blocks = new ArrayList<Block>();
		
		Block ch11MockedBlock1 = mock(Block.class);
		when(ch11MockedBlock1.toString()).thenReturn("kk : (NAT >-> NAT)");
		when(ch11MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch11MockedBlock2 = mock(Block.class);
		when(ch11MockedBlock2.toString()).thenReturn("not(kk : (NAT >-> NAT))");
		when(ch11MockedBlock2.isNegative()).thenReturn(true);
		
		ch11Blocks.add(ch11MockedBlock1);
		ch11Blocks.add(ch11MockedBlock2);
		
		expectedBlocks.put("kk : (NAT >-> NAT)", ch11Blocks);
		
		// Characteristic 12
		
		List<Block> ch12Blocks = new ArrayList<Block>();
		
		Block ch12MockedBlock1 = mock(Block.class);
		when(ch12MockedBlock1.toString()).thenReturn("ll : (NAT -->> NAT)");
		when(ch12MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch12MockedBlock2 = mock(Block.class);
		when(ch12MockedBlock2.toString()).thenReturn("not(ll : (NAT -->> NAT))");
		when(ch12MockedBlock2.isNegative()).thenReturn(true);
		
		ch12Blocks.add(ch12MockedBlock1);
		ch12Blocks.add(ch12MockedBlock2);
		
		expectedBlocks.put("ll : (NAT -->> NAT)", ch12Blocks);
				
		// Characteristic 13
			
		List<Block> ch13Blocks = new ArrayList<Block>();
		
		Block ch13MockedBlock1 = mock(Block.class);
		when(ch13MockedBlock1.toString()).thenReturn("mm : (NAT >->> NAT)");
		when(ch13MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch13MockedBlock2 = mock(Block.class);
		when(ch13MockedBlock2.toString()).thenReturn("not(mm : (NAT >->> NAT))");
		when(ch13MockedBlock2.isNegative()).thenReturn(true);
		
		ch13Blocks.add(ch13MockedBlock1);
		ch13Blocks.add(ch13MockedBlock2);
		
		expectedBlocks.put("mm : (NAT >->> NAT)", ch13Blocks);
		
		// Characteristic 14
		
		List<Block> ch14Blocks = new ArrayList<Block>();
		
		Block ch14MockedBlock1 = mock(Block.class);
		when(ch14MockedBlock1.toString()).thenReturn("(nn + 2) : {1, 2} \\/ {3, 4}");
		when(ch14MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch14MockedBlock2 = mock(Block.class);
		when(ch14MockedBlock2.toString()).thenReturn("not((nn + 2) : {1, 2} \\/ {3, 4})");
		when(ch14MockedBlock2.isNegative()).thenReturn(true);
		
		ch14Blocks.add(ch14MockedBlock1);
		ch14Blocks.add(ch14MockedBlock2);
		
		expectedBlocks.put("(nn + 2) : {1, 2} \\/ {3, 4}", ch14Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForNotMemberOfCharacteristic() {
		
		// Setting up objects for generating blocks
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Does not belong to NAT characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa /: NAT");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa /: NAT)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa /: NAT", ch1Blocks);
		
		// Characteristic 2 - Does not belong to NAT1 characteristic
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb /: NAT1");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(bb /: NAT1)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb /: NAT1", ch2Blocks);
		
		// Characteristic 3 - Does not belong to INT characteristic
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("cc /: INT");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(cc /: INT)");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc /: INT", ch3Blocks);
		
		// Characteristic 4 - Does not belong to BOOL characteristic
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("dd /: BOOL");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("not(dd /: BOOL)");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd /: BOOL", ch4Blocks);
		
		
		// Characteristic 5 - Does not belong to explicit set characteristic
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("ee /: {1, 2, 3}");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(ee /: {1, 2, 3})");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee /: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Does not belong to RANGE characteristic
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("ff /: 1..5");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("not(ff /: 1..5)");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
			
		expectedBlocks.put("ff /: 1..5", ch6Blocks);
		
		// Characteristic 7 - Does not belong to ABSTRACT set characteristic
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("gg /: NAME");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("not(gg /: NAME)");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg /: NAME", ch7Blocks);
		
		// Characteristic 8 - Does not belong to ID characteristic
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("hh /: awnser");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("not(hh /: awnser)");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh /: awnser", ch8Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForSubsetCharacteristic() {
	
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(2);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Subset of NAT characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa <: NAT");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa <: NAT)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa <: NAT", ch1Blocks);
		
		// Characteristic 2 - Subset of NAT1 characteristic
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb <: NAT1");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(bb <: NAT1)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb <: NAT1", ch2Blocks);
		
		// Characteristic 3 - Subset of INT characteristic
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("cc <: INT");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		ch3Blocks.add(ch3MockedBlock1);
		
		expectedBlocks.put("cc <: INT", ch3Blocks);
		
		// Characteristic 4 - Subset of BOOL characteristic
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("dd <: BOOL");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		ch4Blocks.add(ch4MockedBlock1);
		
		expectedBlocks.put("dd <: BOOL", ch4Blocks);
		
		// Characteristic 5 - Subset of explicit set characteristic
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("ee <: {1, 2, 3}");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(ee <: {1, 2, 3})");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee <: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Subset of RANGE characteristic
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("ff <: 1..5");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("not(ff <: 1..5)");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff <: 1..5", ch6Blocks);
		
		// Characteristic 7 - Subset of ABSTRACT set characteristic
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("gg <: NAME");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		ch7Blocks.add(ch7MockedBlock1);
		
		expectedBlocks.put("gg <: NAME", ch7Blocks);
		
		// Characteristic 8 - Subset of ID characteristic
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("hh <: awnser");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("not(hh <: awnser)");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh <: awnser", ch8Blocks);
		
		// Characteristic 9 - Non typing subset characteristic
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("ii \\/ {2} <: {1, 2} \\/ {3, 4}");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("not(ii \\/ {2} <: {1, 2} \\/ {3, 4})");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii \\/ {2} <: {1, 2} \\/ {3, 4}", ch9Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForNotSubsetCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(3);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
			
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Not subset of NAT
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa /<: NAT");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa /<: NAT)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa /<: NAT", ch1Blocks);
		
		// Characteristic 2 - Not subset of NAT1
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb /<: NAT1");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(bb /<: NAT1)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb /<: NAT1", ch2Blocks);
		
		// Characteristic 3 - Not subset of INT
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("cc /<: INT");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(cc /<: INT)");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc /<: INT", ch3Blocks);
		
		// Characteristic 4 - Not subset of BOOL
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("dd /<: BOOL");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("not(dd /<: BOOL)");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd /<: BOOL", ch4Blocks);
		
		// Characteristic 5 - Not subset of explicit set
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("ee /<: {1, 2, 3}");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(ee /<: {1, 2, 3})");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee /<: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Not subset of range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("ff /<: 1..5");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("not(ff /<: 1..5)");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff /<: 1..5", ch6Blocks);
		
		// Characteristic 7 - Not subset of abstract set
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("gg /<: NAME");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("not(gg /<: NAME)");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg /<: NAME", ch7Blocks);
		
		// Characteristic 8 - Not subset of ID
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("hh /<: awnser");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("not(hh /<: awnser)");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh /<: awnser", ch8Blocks);
		
		// Characteristic 9 - Expression not subset of another expression
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("ii \\/ {5} /<: {1, 2} \\/ {3, 4}");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("not(ii \\/ {5} /<: {1, 2} \\/ {3, 4})");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii \\/ {5} /<: {1, 2} \\/ {3, 4}", ch9Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForSubsetStrictCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(4);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1 - Strict subset of NAT
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa <<: NAT");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa <<: NAT)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa <<: NAT", ch1Blocks);
		
		// Characteristic 2 - Strict subset of NAT1
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb <<: NAT1");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(bb <<: NAT1)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb <<: NAT1", ch2Blocks);
		
		// Characteristic 3 - Strict subset of INT
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("cc <<: INT");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		ch3Blocks.add(ch3MockedBlock1);
		
		expectedBlocks.put("cc <<: INT", ch3Blocks);
		
		// Characteristic 4 - Strict subset of BOOL
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("dd <<: BOOL");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		ch4Blocks.add(ch4MockedBlock1);
		
		expectedBlocks.put("dd <<: BOOL", ch4Blocks);
		
		// Characteristic 5 - Strict subset of explicit set
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("ee <<: {1, 2, 3}");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(ee <<: {1, 2, 3})");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee <<: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Strict subset of range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("ff <<: 1..5");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("not(ff <<: 1..5)");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff <<: 1..5", ch6Blocks);
		
		// Characteristic 7 - Strict subset of abstract set
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("gg <<: NAME");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("not(gg <<: NAME)");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg <<: NAME", ch7Blocks);
		
		// Characteristic 8 - Strict subset of ID
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("hh <<: awnser");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("not(hh <<: awnser)");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh <<: awnser", ch8Blocks);
		
		// Characteristic 9 - Expression strict subset of expression
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("ii \\/ {5} <<: {1, 2} \\/ {3, 4}");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("not(ii \\/ {5} <<: {1, 2} \\/ {3, 4})");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii \\/ {5} <<: {1, 2} \\/ {3, 4}", ch9Blocks);

		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForNotSubsetStrictCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(5);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1 - Not strict subset of NAT
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa /<<: NAT");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa /<<: NAT)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa /<<: NAT", ch1Blocks);
		
		// Characteristic 2 - Not strict subset of NAT1
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb /<<: NAT1");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(bb /<<: NAT1)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);

		expectedBlocks.put("bb /<<: NAT1", ch2Blocks);
		
		// Characteristic 3 - Not strict subset of INT
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("cc /<<: INT");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(cc /<<: INT)");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc /<<: INT", ch3Blocks);
		
		// Characteristic 4 - Not strict subset of BOOL
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("dd /<<: BOOL");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("not(dd /<<: BOOL)");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd /<<: BOOL", ch4Blocks);
		
		// Characteristic 5 - Not strict subset of explicit set
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("ee /<<: {1, 2, 3}");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(ee /<<: {1, 2, 3})");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee /<<: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Not strict subset of range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("ff /<<: 1..5");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("not(ff /<<: 1..5)");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff /<<: 1..5", ch6Blocks);
		
		// Characteristic 7 - Not strict susbet of abstract set
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("gg /<<: NAME");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("not(gg /<<: NAME)");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg /<<: NAME", ch7Blocks);
		
		// Characteristic 8 - Not strict subset of ID
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("hh /<<: awnser");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("not(hh /<<: awnser)");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh /<<: awnser", ch8Blocks);
		
		// Characteristic 9 - Expression not subset of subset
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("ii \\/ {5} /<<: {1, 2} \\/ {3, 4}");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("not(ii \\/ {5} /<<: {1, 2} \\/ {3, 4})");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii \\/ {5} /<<: {1, 2} \\/ {3, 4}", ch9Blocks);

		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForEqualsCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(12);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Equals to abstract set
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa = NAME");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa = NAME)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa = NAME", ch1Blocks);
		
		// Characteristic 2 - Equals to abstract set
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb = 1");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(bb = 1)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb = 1", ch2Blocks);
		
		// Characteristic 3 - Equals to abstract set to boolean literal
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("cc = TRUE");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(cc = TRUE)");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc = TRUE", ch3Blocks);
		
		// Characteristic 4 - Equals to abstract setEquals all the same values array
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("dd = (NAME * {0})");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("not(dd = (NAME * {0}))");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd = (NAME * {0})", ch4Blocks);
		
		// Characteristic 5 - Equals stated values array
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("ee = {0 |-> FALSE, 1 |-> FALSE}");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(ee = {0 |-> FALSE, 1 |-> FALSE})");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee = {0 |-> FALSE, 1 |-> FALSE}", ch5Blocks);
		
		// Characteristic 6 - Equals to range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("ff = 1..5");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("not(ff = 1..5)");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff = 1..5", ch6Blocks);
		
		// Characteristic 7 - Equals to NAT
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("gg = NAT");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("not(gg = NAT)");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg = NAT", ch7Blocks);
		
		// Characteristic 8 - Equals to NAT1
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("hh = NAT1");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("not(hh = NAT1)");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);

		expectedBlocks.put("hh = NAT1", ch8Blocks);
		
		// Characteristic 9 - Equals to INT
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("ii = INT");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("not(ii = INT)");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii = INT", ch9Blocks);
		
		// Characteristic 10 - Equals to INT
		
		List<Block> ch10Blocks = new ArrayList<Block>();
		
		Block ch10MockedBlock1 = mock(Block.class);
		when(ch10MockedBlock1.toString()).thenReturn("jj = BOOL");
		when(ch10MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock2 = mock(Block.class);
		when(ch10MockedBlock2.toString()).thenReturn("not(jj = BOOL)");
		when(ch10MockedBlock2.isNegative()).thenReturn(true);
		
		ch10Blocks.add(ch10MockedBlock1);
		ch10Blocks.add(ch10MockedBlock2);
		
		expectedBlocks.put("jj = BOOL", ch10Blocks);

		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForNotEqualsCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(13);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Not Equals to abstract set
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa /= NAME");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa /= NAME)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa /= NAME", ch1Blocks);
		
		// Characteristic 2 - Not Equals to integer literal
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb /= 1");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(bb /= 1)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb /= 1", ch2Blocks);
		
		// Characteristic 3 - Not Equals to boolean literal
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("cc /= TRUE");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(cc /= TRUE)");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc /= TRUE", ch3Blocks);
		
		// Characteristic 4 - Not Equals all the same values array
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("dd /= (NAME * {0})");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("not(dd /= (NAME * {0}))");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd /= (NAME * {0})", ch4Blocks);
		
		// Characteristic 5 - Not Equals stated values array
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("ee /= {0 |-> FALSE, 1 |-> FALSE}");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(ee /= {0 |-> FALSE, 1 |-> FALSE})");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee /= {0 |-> FALSE, 1 |-> FALSE}", ch5Blocks);
		
		// Characteristic 6 - Not Equals to range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("ff /= 1..5");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("not(ff /= 1..5)");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff /= 1..5", ch6Blocks);
		
		// Characteristic 7 - Not Equals to NAT
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("gg /= NAT");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("not(gg /= NAT)");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg /= NAT", ch7Blocks);
		
		// Characteristic 8 - Not Equals to NAT1
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("hh /= NAT1");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("not(hh /= NAT1)");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh /= NAT1", ch8Blocks);
		
		// Characteristic 9 - Not Equals to INT
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("ii /= INT");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("not(ii /= INT)");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii /= INT", ch9Blocks);
		
		// Characteristic 10 - Not Equals to INT
		
		List<Block> ch10Blocks = new ArrayList<Block>();
		
		Block ch10MockedBlock1 = mock(Block.class);
		when(ch10MockedBlock1.toString()).thenReturn("jj /= BOOL");
		when(ch10MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock2 = mock(Block.class);
		when(ch10MockedBlock2.toString()).thenReturn("not(jj /= BOOL)");
		when(ch10MockedBlock2.isNegative()).thenReturn(true);
		
		ch10Blocks.add(ch10MockedBlock1);
		ch10Blocks.add(ch10MockedBlock2);
		
		expectedBlocks.put("jj /= BOOL", ch10Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForRelationalCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(6);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Greater than
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa > bb");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa > bb)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa > bb", ch1Blocks);
		
		// Characteristic 2 - Less than
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("cc < dd");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(cc < dd)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("cc < dd", ch2Blocks);
		
		// Characteristic 3 - Greater or equal to
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("ee >= ff");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(ee >= ff)");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("ee >= ff", ch3Blocks);
		
		// Characteristic 4 - Less or equal to
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("gg <= hh");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("not(gg <= hh)");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("gg <= hh", ch4Blocks);
		
		// Characteristic 5 - Typing characteristic
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("ii : NAT");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(ii : NAT)");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ii : NAT", ch5Blocks);
		
		// Characteristic 6 - Equal to
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("ii = jj");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("not(ii = jj)");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ii = jj", ch6Blocks);
		
		// Characteristic 7 - Typing characteristic
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("jj : NAT");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("not(jj : NAT)");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("jj : NAT", ch7Blocks);
		
		// Characteristic 8 - Typing characteristic
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("kk : NAT");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("not(kk : NAT)");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("kk : NAT", ch8Blocks);
		
		// Characteristic 9 - Not equal to
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("kk /= ll");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("not(kk /= ll)");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("kk /= ll", ch9Blocks);
		
		// Characteristic 10 - Typing characteristic
		
		List<Block> ch10Blocks = new ArrayList<Block>();
		
		Block ch10MockedBlock1 = mock(Block.class);
		when(ch10MockedBlock1.toString()).thenReturn("ll : NAT");
		when(ch10MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock2 = mock(Block.class);
		when(ch10MockedBlock2.toString()).thenReturn("not(ll : NAT)");
		when(ch10MockedBlock2.isNegative()).thenReturn(true);
		
		ch10Blocks.add(ch10MockedBlock1);
		ch10Blocks.add(ch10MockedBlock2);
		
		expectedBlocks.put("ll : NAT", ch10Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForExistentialCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(10);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Existential characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("#(xx).(((xx : NAT) => (xx > aa)))");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(#(xx).(((xx : NAT) => (xx > aa))))");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("#(xx).(((xx : NAT) => (xx > aa)))", ch1Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForUniversalCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(9);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Seting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Universal characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("!(xx).((xx : NAT) => (aa = xx & bb = (xx + 1)))");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(!(xx).((xx : NAT) => (aa = xx & bb = (xx + 1))))");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("!(xx).((xx : NAT) => (aa = xx & bb = (xx + 1)))", ch1Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForEquivalenceCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(11);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Equivalence characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("((aa = 1) <=> (bb = 0))");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(((aa = 1) <=> (bb = 0)))");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("((aa = 1) <=> (bb = 0))", ch1Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForImplicationCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(8);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Implication characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("((aa = 10) => (bb > 5))");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(((aa = 10) => (bb > 5)))");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("((aa = 10) => (bb > 5))", ch1Blocks);

		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForDisjunctionCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(14);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1 - Typing characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa : BOOL");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		ch1Blocks.add(ch1MockedBlock1);
				
		expectedBlocks.put("aa : BOOL", ch1Blocks);
		
		// Characteristic 2 - Typing characteristic
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb : BOOL");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		ch2Blocks.add(ch2MockedBlock1);
		
		expectedBlocks.put("bb : BOOL", ch2Blocks);
				
		// Characteristic 3 - Typing characteristic
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("cc : NAT");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(cc : NAT)");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc : NAT", ch3Blocks);
		
		// Characteristic 4 - Disjunction item
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("aa = TRUE");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("not(aa = TRUE)");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
				
		expectedBlocks.put("aa = TRUE", ch4Blocks);
		
		// Characteristic 5 - Disjunction item
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("bb = FALSE");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("not(bb = FALSE)");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("bb = FALSE", ch5Blocks);
		
		// Characteristic 6 - Disjunction item
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("cc = 10");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("not(cc = 10)");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("cc = 10", ch6Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesBlocksForNegationCharacteristic() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(7);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Typing characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("aa : NAT");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("not(aa : NAT)");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa : NAT", ch1Blocks);
		
		// Characteristic 2 - Typing characteristic
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("bb : NAT");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(bb : NAT)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb : NAT", ch2Blocks);
		
		// Characteristic 3 - Negation characteristic
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("not(bb : {1, 2, 3})");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(not(bb : {1, 2, 3}))");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("not(bb : {1, 2, 3})", ch3Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldCreateEquivalenceClassBlocksForOperationsWithNoPreconditions() {
		
		// Setting basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/LogFile/LogFile.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("log_file <: LOG");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		ch1Blocks.add(ch1MockedBlock1);
		
		expectedBlocks.put("log_file <: LOG", ch1Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassBlocksForRangesWithMaxInt() {

		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/CounterModified.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("0 <= value");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		ch1Blocks.add(ch1MockedBlock1);
		
		expectedBlocks.put("0 <= value", ch1Blocks);
		
		// Characteristic 2
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("value <= MAXINT");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		ch2Blocks.add(ch2MockedBlock1);
		
		expectedBlocks.put("value <= MAXINT", ch2Blocks);
		
		// Characteristic 3
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("value < MAXINT");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(value < MAXINT)");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("value < MAXINT", ch3Blocks);
		
		// Characteristic 4
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("overflow : BOOL");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);

		ch4Blocks.add(ch4MockedBlock1);
		
		expectedBlocks.put("overflow : BOOL", ch4Blocks);
		
		// Characteristic 5
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("value : MININT..0 - 1");
		when(ch5MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("value : (MAXINT - 1) + 1..MAXINT");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		Block ch5MockedBlock3 = mock(Block.class);
		when(ch5MockedBlock3.toString()).thenReturn("value : 0..(MAXINT - 1)");
		when(ch5MockedBlock3.isNegative()).thenReturn(false);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		ch5Blocks.add(ch5MockedBlock3);
		
		expectedBlocks.put("value : 0..((MAXINT - 1))", ch5Blocks);
		
		// Characteristic 6
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("value : INT");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		ch6Blocks.add(ch6MockedBlock1);
		
		expectedBlocks.put("value : INT", ch6Blocks);
		
		// Characteristic 7
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("((overflow = TRUE) => (value = MAXINT))");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		ch7Blocks.add(ch7MockedBlock1);
		
		expectedBlocks.put("((overflow = TRUE) => (value = MAXINT))", ch7Blocks);	
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateEquivalenceClassesForCounter() {
		
		// Setting up basic objects for block generation
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("0 <= value");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		ch1Blocks.add(ch1MockedBlock1);
		
		expectedBlocks.put("0 <= value", ch1Blocks);	
		
		// Characteristic 2
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("value <= MAXINT");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("not(value <= MAXINT)");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("value <= MAXINT", ch2Blocks);
		
		// Characteristic 3
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("value < MAXINT");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("not(value < MAXINT)");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("value < MAXINT", ch3Blocks);
		
		// Characteristic 4
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("overflow : BOOL");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		ch4Blocks.add(ch4MockedBlock1);
		
		expectedBlocks.put("overflow : BOOL", ch4Blocks);
		
		// Characteristic 5
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("value : INT");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		ch5Blocks.add(ch5MockedBlock1);
		
		expectedBlocks.put("value : INT", ch5Blocks);
		
		// Characteristic 6
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("((overflow = TRUE) => (value = MAXINT))");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		ch6Blocks.add(ch6MockedBlock1);
		
		expectedBlocks.put("((overflow = TRUE) => (value = MAXINT))", ch6Blocks);
		
		// Getting actual result and doing verifications
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}

}
