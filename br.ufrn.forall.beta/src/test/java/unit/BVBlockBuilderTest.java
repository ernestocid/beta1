package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import parser.Characteristic;
import parser.Machine;
import parser.Operation;
import parser.PredicateCharacteristic;
import testgeneration.BVBlockBuilder;
import testgeneration.Block;
import testgeneration.Partitioner;
import static org.mockito.Mockito.*;

public class BVBlockBuilderTest extends BlockBuilderTest{

	
	@Test
	public void shouldNotGenerateBoundaryBlocksForInvariantClauses() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/Paperround.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		// Setting up expected result
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: papers <: 1..163 isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		ch1Blocks.add(ch1MockedBlock1);
		
		Characteristic mockedCharacteristic1 = mock(PredicateCharacteristic.class);
		when(mockedCharacteristic1.toString()).thenReturn("papers <: 1..163");
		
		expectedBlocks.put("papers <: 1..163", ch1Blocks);
		
		// Characteristic 2
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: magazines <: papers isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		ch2Blocks.add(ch2MockedBlock1);
		
		Characteristic mockedCharacteristic2 = mock(PredicateCharacteristic.class);
		when(mockedCharacteristic2.toString()).thenReturn("magazines <: papers");
		
		expectedBlocks.put("magazines <: papers", ch2Blocks);

		// Characteristic 3
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: card(papers) <= 60 isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		ch3Blocks.add(ch3MockedBlock1);
		
		Characteristic mockedCharacteristic3 = mock(PredicateCharacteristic.class);
		when(mockedCharacteristic3.toString()).thenReturn("card(papers) <= 60");
		
		expectedBlocks.put("card(papers) <= 60", ch3Blocks);
		
		// Characteristic 4
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: hh = 1 - 1 isNegative: true");
		when(ch4MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("Block: hh = 1 isNegative: false");
		when(ch4MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock3 = mock(Block.class);
		when(ch4MockedBlock3.toString()).thenReturn("Block: hh = 1 + 1 isNegative: false");
		when(ch4MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock4 = mock(Block.class);
		when(ch4MockedBlock4.toString()).thenReturn("Block: hh = 163 - 1 isNegative: false");
		when(ch4MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock5 = mock(Block.class);
		when(ch4MockedBlock5.toString()).thenReturn("Block: hh = 163 isNegative: false");
		when(ch4MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock6 = mock(Block.class);
		when(ch4MockedBlock6.toString()).thenReturn("Block: hh = 163 + 1 isNegative: true");
		when(ch4MockedBlock6.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		ch4Blocks.add(ch4MockedBlock3);
		ch4Blocks.add(ch4MockedBlock4);
		ch4Blocks.add(ch4MockedBlock5);
		ch4Blocks.add(ch4MockedBlock6);
		
		Characteristic mockedCharacteristic4 = mock(PredicateCharacteristic.class);
		when(mockedCharacteristic4.toString()).thenReturn("hh : 1..163");
		
		expectedBlocks.put("hh : 1..163", ch4Blocks);

		// Characteristic 5
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: card(papers) < 60 isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(card(papers) < 60) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		Characteristic mockedCharacteristic5 = mock(PredicateCharacteristic.class);
		when(mockedCharacteristic5.toString()).thenReturn("card(papers) < 60");
		
		expectedBlocks.put("card(papers) < 60", ch5Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> result = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, result));
	}
	
	
	
	@Test
	public void shouldGenerateBlocksForTypingMemberOfCharacteristics() {

		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected result
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - NAT set characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class); 
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa = -1 isNegative: true");
		when(ch1MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: aa = 0 isNegative: false");
		when(ch1MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock3 = mock(Block.class);
		when(ch1MockedBlock3.toString()).thenReturn("Block: aa = 1 isNegative: false");
		when(ch1MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock4 = mock(Block.class);
		when(ch1MockedBlock4.toString()).thenReturn("Block: aa = MAXINT-1 isNegative: false");
		when(ch1MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock5 = mock(Block.class);
		when(ch1MockedBlock5.toString()).thenReturn("Block: aa = MAXINT isNegative: false");
		when(ch1MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock6 = mock(Block.class);
		when(ch1MockedBlock6.toString()).thenReturn("Block: aa = MAXINT+1 isNegative: true");
		when(ch1MockedBlock6.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		ch1Blocks.add(ch1MockedBlock3);
		ch1Blocks.add(ch1MockedBlock4);
		ch1Blocks.add(ch1MockedBlock5);
		ch1Blocks.add(ch1MockedBlock6);
		
		expectedBlocks.put("aa : NAT", ch1Blocks);
		
		// Characteristic 2 - NAT1 set characteristic
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class); 
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb = 0 isNegative: true");
		when(ch2MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: bb = 1 isNegative: false");
		when(ch2MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock3 = mock(Block.class);
		when(ch2MockedBlock3.toString()).thenReturn("Block: bb = 2 isNegative: false");
		when(ch2MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock4 = mock(Block.class);
		when(ch2MockedBlock4.toString()).thenReturn("Block: bb = MAXINT-1 isNegative: false");
		when(ch2MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock5 = mock(Block.class);
		when(ch2MockedBlock5.toString()).thenReturn("Block: bb = MAXINT isNegative: false");
		when(ch2MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock6 = mock(Block.class);
		when(ch2MockedBlock6.toString()).thenReturn("Block: bb = MAXINT+1 isNegative: true");
		when(ch2MockedBlock6.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		ch2Blocks.add(ch2MockedBlock3);
		ch2Blocks.add(ch2MockedBlock4);
		ch2Blocks.add(ch2MockedBlock5);
		ch2Blocks.add(ch2MockedBlock6);
		
		expectedBlocks.put("bb : NAT1", ch2Blocks);
		
		// Characteristic 3 - INT set characteristic
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc : INT isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		ch3Blocks.add(ch3MockedBlock1);
		
		expectedBlocks.put("cc : INT", ch3Blocks);
		
		// Characteristic 4 - BOOL set characteristic
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: dd : BOOL isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);

		ch4Blocks.add(ch4MockedBlock1);
		
		expectedBlocks.put("dd : BOOL", ch4Blocks);
		
		// Characteristic 5 - SET characteristic
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ee : {1, 2, 3} isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(ee : {1, 2, 3}) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee : {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - RANGE characteristic	
		
		List<Block> ch6Blocks = new ArrayList<Block>();

		Block ch6MockedBlock1 = mock(Block.class); 
		when(ch6MockedBlock1.toString()).thenReturn("Block: ff = 1 - 1 isNegative: true");
		when(ch6MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: ff = 1 isNegative: false");
		when(ch6MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock3 = mock(Block.class);
		when(ch6MockedBlock3.toString()).thenReturn("Block: ff = 1 + 1 isNegative: false");
		when(ch6MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock4 = mock(Block.class);
		when(ch6MockedBlock4.toString()).thenReturn("Block: ff = 5 - 1 isNegative: false");
		when(ch6MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock5 = mock(Block.class);
		when(ch6MockedBlock5.toString()).thenReturn("Block: ff = 5 isNegative: false");
		when(ch6MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock6 = mock(Block.class);
		when(ch6MockedBlock6.toString()).thenReturn("Block: ff = 5 + 1 isNegative: true");
		when(ch6MockedBlock6.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		ch6Blocks.add(ch6MockedBlock3);
		ch6Blocks.add(ch6MockedBlock4);
		ch6Blocks.add(ch6MockedBlock5);
		ch6Blocks.add(ch6MockedBlock6);
		
		expectedBlocks.put("ff : 1..5", ch6Blocks);
		
		// Characteristic 7 - Abstract set characteristic
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class); 
		when(ch7MockedBlock1.toString()).thenReturn("Block: gg : NAME isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		ch7Blocks.add(ch7MockedBlock1);
		
		expectedBlocks.put("gg : NAME", ch7Blocks);
		
		// Characteristic 8 - SET characteristic
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class); 
		when(ch8MockedBlock1.toString()).thenReturn("Block: hh : awnser isNegative: false");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class); 
		when(ch8MockedBlock2.toString()).thenReturn("Block: not(hh : awnser) isNegative: true");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh : awnser", ch8Blocks);
		
		// Characteristic 9 - STRING characteristic
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("Block: ii : STRING isNegative: false");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		ch9Blocks.add(ch9MockedBlock1);
		
		expectedBlocks.put("ii : STRING", ch9Blocks);
		
		// Characteristic 10 - FUNC characteristic
		
		List<Block> ch10Blocks = new ArrayList<Block>();
		
		Block ch10MockedBlock1 = mock(Block.class);
		when(ch10MockedBlock1.toString()).thenReturn("Block: jj : (NAT --> NAT) isNegative: false");
		when(ch10MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock2 = mock(Block.class);
		when(ch10MockedBlock2.toString()).thenReturn("Block: not(jj : (NAT --> NAT)) isNegative: true");
		when(ch10MockedBlock2.isNegative()).thenReturn(true);
		
		ch10Blocks.add(ch10MockedBlock1);
		ch10Blocks.add(ch10MockedBlock2);
		
		expectedBlocks.put("jj : (NAT --> NAT)", ch10Blocks);
		
		// Characteristic 11
		
		List<Block> ch11Blocks = new ArrayList<Block>();
		
		Block ch11MockedBlock1 = mock(Block.class);
		when(ch11MockedBlock1.toString()).thenReturn("Block: kk : (NAT >-> NAT) isNegative: false");
		when(ch11MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch11MockedBlock2 = mock(Block.class);
		when(ch11MockedBlock2.toString()).thenReturn("Block: not(kk : (NAT >-> NAT)) isNegative: true");
		when(ch11MockedBlock2.isNegative()).thenReturn(true);
		
		ch11Blocks.add(ch11MockedBlock1);
		ch11Blocks.add(ch11MockedBlock2);
		
		expectedBlocks.put("kk : (NAT >-> NAT)", ch11Blocks);
		
		// Characteristic 12
		
		List<Block> ch12Blocks = new ArrayList<Block>();
		
		Block ch12MockedBlock1 = mock(Block.class);
		when(ch12MockedBlock1.toString()).thenReturn("Block: ll : (NAT -->> NAT) isNegative: false");
		when(ch12MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch12MockedBlock2 = mock(Block.class);
		when(ch12MockedBlock2.toString()).thenReturn("Block: not(ll : (NAT -->> NAT)) isNegative: true");
		when(ch12MockedBlock2.isNegative()).thenReturn(true);

		ch12Blocks.add(ch12MockedBlock1);
		ch12Blocks.add(ch12MockedBlock2);
		
		expectedBlocks.put("ll : (NAT -->> NAT)", ch12Blocks);

		// Characteristic 13
	
		List<Block> ch13Blocks = new ArrayList<Block>();
		
		Block ch13MockedBlock1 = mock(Block.class);
		when(ch13MockedBlock1.toString()).thenReturn("Block: mm : (NAT >->> NAT) isNegative: false");
		when(ch13MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch13MockedBlock2 = mock(Block.class);
		when(ch13MockedBlock2.toString()).thenReturn("Block: not(mm : (NAT >->> NAT)) isNegative: true");
		when(ch13MockedBlock2.isNegative()).thenReturn(true);
		
		ch13Blocks.add(ch13MockedBlock1);
		ch13Blocks.add(ch13MockedBlock2);
		
		expectedBlocks.put("mm : (NAT >->> NAT)", ch13Blocks);
		
		// Characteristic 14
		
		List<Block> ch14Blocks = new ArrayList<Block>();
		
		Block ch14MockedBlock1 = mock(Block.class);
		when(ch14MockedBlock1.toString()).thenReturn("Block: (nn + 2) : {1, 2} \\/ {3, 4} isNegative: false");
		when(ch14MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch14MockedBlock2 = mock(Block.class);
		when(ch14MockedBlock2.toString()).thenReturn("Block: not((nn + 2) : {1, 2} \\/ {3, 4}) isNegative: true");
		when(ch14MockedBlock2.isNegative()).thenReturn(true);
		
		ch14Blocks.add(ch14MockedBlock1);
		ch14Blocks.add(ch14MockedBlock2);
		
		expectedBlocks.put("(nn + 2) : {1, 2} \\/ {3, 4}", ch14Blocks);

		// Getting actual result and doing verifications

		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}

	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForNotMemberOfCharacteristic() {

		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Does not belong to NAT characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa /: NAT isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(aa /: NAT) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);

		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa /: NAT", ch1Blocks);
		
		// Characteristic 2 - Does not belong to NAT1 characteristic
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb /: NAT1 isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: not(bb /: NAT1) isNegative: true");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb /: NAT1", ch2Blocks);
		
		// Characteristic 3 - Does not belong to INT characteristic
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc /: INT isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: not(cc /: INT) isNegative: true");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc /: INT", ch3Blocks);
		
		// Characteristic 4 - Does not belong to BOOL characteristic
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: dd /: BOOL isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("Block: not(dd /: BOOL) isNegative: true");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd /: BOOL", ch4Blocks);
		
		// Characteristic 5 - Does not belong to explicit set characteristic
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ee /: {1, 2, 3} isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(ee /: {1, 2, 3}) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee /: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Does not belong to RANGE characteristic
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: ff /: 1..5 isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(ff /: 1..5) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
			
		expectedBlocks.put("ff /: 1..5", ch6Blocks);
		
		// Characteristic 7 - Does not belong to ABSTRACT set characteristic
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: gg /: NAME isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("Block: not(gg /: NAME) isNegative: true");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg /: NAME", ch7Blocks);
		
		// Characteristic 8 - Does not belong to ID characteristic
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("Block: hh /: awnser isNegative: false");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("Block: not(hh /: awnser) isNegative: true");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh /: awnser", ch8Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForSubsetCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(2);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Subset of NAT characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa <: NAT isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(aa <: NAT) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa <: NAT", ch1Blocks);
		
		// Characteristic 2 - Subset of NAT1 characteristic
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb <: NAT1 isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: not(bb <: NAT1) isNegative: true");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb <: NAT1", ch2Blocks);
		
		// Characteristic 3 - Subset of INT characteristic
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc <: INT isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		ch3Blocks.add(ch3MockedBlock1);
		
		expectedBlocks.put("cc <: INT", ch3Blocks);
		
		// Characteristic 4 - Subset of BOOL characteristic
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: dd <: BOOL isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		ch4Blocks.add(ch4MockedBlock1);
		
		expectedBlocks.put("dd <: BOOL", ch4Blocks);
		
		// Characteristic 5 - Subset of explicit set characteristic
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ee <: {1, 2, 3} isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(ee <: {1, 2, 3}) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);

		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee <: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Subset of RANGE characteristic
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: ff <: 1..5 isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(ff <: 1..5) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff <: 1..5", ch6Blocks);
		
		// Characteristic 7 - Subset of ABSTRACT set characteristic
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: gg <: NAME isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		ch7Blocks.add(ch7MockedBlock1);
		
		expectedBlocks.put("gg <: NAME", ch7Blocks);
		
		// Characteristic 8 - Subset of ID characteristic
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("Block: hh <: awnser isNegative: false");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("Block: not(hh <: awnser) isNegative: true");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh <: awnser", ch8Blocks);
		
		// Characteristic 9 - Non typing subset characteristic
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("Block: ii \\/ {2} <: {1, 2} \\/ {3, 4} isNegative: false");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("Block: not(ii \\/ {2} <: {1, 2} \\/ {3, 4}) isNegative: true");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii \\/ {2} <: {1, 2} \\/ {3, 4}", ch9Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForNotSubsetCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(3);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Not subset of NAT
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa /<: NAT isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(aa /<: NAT) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa /<: NAT", ch1Blocks);
		
		// Characteristic 2 - Not subset of NAT1
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb /<: NAT1 isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: not(bb /<: NAT1) isNegative: true");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb /<: NAT1", ch2Blocks);
		
		// Characteristic 3 - Not subset of INT
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc /<: INT isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: not(cc /<: INT) isNegative: true");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc /<: INT", ch3Blocks);
		
		// Characteristic 4 - Not subset of BOOL
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: dd /<: BOOL isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("Block: not(dd /<: BOOL) isNegative: true");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd /<: BOOL", ch4Blocks);
		
		// Characteristic 5 - Not subset of explicit set
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ee /<: {1, 2, 3} isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(ee /<: {1, 2, 3}) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee /<: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Not subset of range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: ff /<: 1..5 isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(ff /<: 1..5) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff /<: 1..5", ch6Blocks);
		
		// Characteristic 7 - Not subset of abstract set
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: gg /<: NAME isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("Block: not(gg /<: NAME) isNegative: true");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg /<: NAME", ch7Blocks);
		
		// Characteristic 8 - Not subset of ID
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("Block: hh /<: awnser isNegative: false");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("Block: not(hh /<: awnser) isNegative: true");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh /<: awnser", ch8Blocks);
		
		// Characteristic 9 - Expression not subset of subset
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("Block: ii \\/ {5} /<: {1, 2} \\/ {3, 4} isNegative: false");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("Block: not(ii \\/ {5} /<: {1, 2} \\/ {3, 4}) isNegative: true");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
				
		expectedBlocks.put("ii \\/ {5} /<: {1, 2} \\/ {3, 4}", ch9Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForSubsetStrictCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(4);

		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1 - Strict subset of NAT
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa <<: NAT isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(aa <<: NAT) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);

		expectedBlocks.put("aa <<: NAT", ch1Blocks);
		
		// Characteristic 2 - Strict subset of NAT1
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb <<: NAT1 isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: not(bb <<: NAT1) isNegative: true");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb <<: NAT1", ch2Blocks);
		
		// Characteristic 3 - Strict subset of INT
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc <<: INT isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		ch3Blocks.add(ch3MockedBlock1);
		
		expectedBlocks.put("cc <<: INT", ch3Blocks);
		
		// Characteristic 4 - Strict subset of BOOL
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: dd <<: BOOL isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		ch4Blocks.add(ch4MockedBlock1);
		
		expectedBlocks.put("dd <<: BOOL", ch4Blocks);
		
		// Characteristic 5 - Strict subset of explicit set
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ee <<: {1, 2, 3} isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(ee <<: {1, 2, 3}) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee <<: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Strict subset of range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: ff <<: 1..5 isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(ff <<: 1..5) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff <<: 1..5", ch6Blocks);
		
		// Characteristic 7 - Strict subset of abstract set
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: gg <<: NAME isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("Block: not(gg <<: NAME) isNegative: true");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg <<: NAME", ch7Blocks);
		
		// Characteristic 8 - Strict subset of ID
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("Block: hh <<: awnser isNegative: false");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("Block: not(hh <<: awnser) isNegative: true");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh <<: awnser", ch8Blocks);
		
		// Characteristic 9 - Expression strict subset of expression
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("Block: ii \\/ {5} <<: {1, 2} \\/ {3, 4} isNegative: false");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("Block: not(ii \\/ {5} <<: {1, 2} \\/ {3, 4}) isNegative: true");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii \\/ {5} <<: {1, 2} \\/ {3, 4}", ch9Blocks);

		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForNotStrictSubsetCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(5);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
			
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1 - Not subset of NAT
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa /<<: NAT isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(aa /<<: NAT) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa /<<: NAT", ch1Blocks);
		
		// Characteristic 2 - Not subset of NAT1
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb /<<: NAT1 isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: not(bb /<<: NAT1) isNegative: true");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb /<<: NAT1", ch2Blocks);
		
		// Characteristic 3 - Not subset of INT
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc /<<: INT isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: not(cc /<<: INT) isNegative: true");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc /<<: INT", ch3Blocks);
		
		// Characteristic 4 - Not subset of BOOL
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: dd /<<: BOOL isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("Block: not(dd /<<: BOOL) isNegative: true");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd /<<: BOOL", ch4Blocks);
		
		// Characteristic 5 - Not subset of explicit set
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ee /<<: {1, 2, 3} isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(ee /<<: {1, 2, 3}) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee /<<: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Not subset of range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: ff /<<: 1..5 isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(ff /<<: 1..5) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff /<<: 1..5", ch6Blocks);
		
		// Characteristic 7 - Not subset of abstract set
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: gg /<<: NAME isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("Block: not(gg /<<: NAME) isNegative: true");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg /<<: NAME", ch7Blocks);
		
		// Characteristic 8 - Not subset of ID
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("Block: hh /<<: awnser isNegative: false");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("Block: not(hh /<<: awnser) isNegative: true");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh /<<: awnser", ch8Blocks);
		
		// Characteristic 9 - Expression not subset of subset
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("Block: ii \\/ {5} /<<: {1, 2} \\/ {3, 4} isNegative: false");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("Block: not(ii \\/ {5} /<<: {1, 2} \\/ {3, 4}) isNegative: true");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
				
		expectedBlocks.put("ii \\/ {5} /<<: {1, 2} \\/ {3, 4}", ch9Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForNotSubsetStrictCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(5);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1 - Not strict subset of NAT
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa /<<: NAT isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(aa /<<: NAT) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);

		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa /<<: NAT", ch1Blocks);
		
		// Characteristic 2 - Not strict subset of NAT1
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb /<<: NAT1 isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: not(bb /<<: NAT1) isNegative: true");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);

		expectedBlocks.put("bb /<<: NAT1", ch2Blocks);
		
		// Characteristic 3 - Not strict subset of INT
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc /<<: INT isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: not(cc /<<: INT) isNegative: true");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc /<<: INT", ch3Blocks);
		
		// Characteristic 4 - Not strict subset of BOOL
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: dd /<<: BOOL isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("Block: not(dd /<<: BOOL) isNegative: true");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd /<<: BOOL", ch4Blocks);
		
		// Characteristic 5 - Not strict subset of explicit set
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ee /<<: {1, 2, 3} isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(ee /<<: {1, 2, 3}) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);

		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee /<<: {1, 2, 3}", ch5Blocks);
		
		// Characteristic 6 - Not strict subset of range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: ff /<<: 1..5 isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(ff /<<: 1..5) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff /<<: 1..5", ch6Blocks);
		
		// Characteristic 7 - Not strict susbet of abstract set
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: gg /<<: NAME isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("Block: not(gg /<<: NAME) isNegative: true");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg /<<: NAME", ch7Blocks);
		
		// Characteristic 8 - Not strict subset of ID
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("Block: hh /<<: awnser isNegative: false");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("Block: not(hh /<<: awnser) isNegative: true");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh /<<: awnser", ch8Blocks);
		
		// Characteristic 9 - Expression not subset of subset
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("Block: ii \\/ {5} /<<: {1, 2} \\/ {3, 4} isNegative: false");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("Block: not(ii \\/ {5} /<<: {1, 2} \\/ {3, 4}) isNegative: true");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
				
		expectedBlocks.put("ii \\/ {5} /<<: {1, 2} \\/ {3, 4}", ch9Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForEqualsCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(12);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Equals to abstract set
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa = NAME isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(aa = NAME) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa = NAME", ch1Blocks);
		
		// Characteristic 2 - Equals to integer literal
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb = 1 isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: not(bb = 1) isNegative: true");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb = 1", ch2Blocks);
		
		// Characteristic 3 - Equals to boolean literal
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc = TRUE isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: not(cc = TRUE) isNegative: true");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc = TRUE", ch3Blocks);
		
		// Characteristic 4 - Equals all the same values array
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: dd = (NAME * {0}) isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("Block: not(dd = (NAME * {0})) isNegative: true");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd = (NAME * {0})", ch4Blocks);
		
		// Characteristic 5 - Equals stated values array
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ee = {0 |-> FALSE, 1 |-> FALSE} isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(ee = {0 |-> FALSE, 1 |-> FALSE}) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee = {0 |-> FALSE, 1 |-> FALSE}", ch5Blocks);
		
		// Characteristic 6 - Equals to range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: ff = 1..5 isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(ff = 1..5) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff = 1..5", ch6Blocks);
		
		// Characteristic 7 - Equals to NAT
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: gg = NAT isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("Block: not(gg = NAT) isNegative: true");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg = NAT", ch7Blocks);
		
		// Characteristic 8 - Equals to NAT1
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("Block: hh = NAT1 isNegative: false");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("Block: not(hh = NAT1) isNegative: true");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh = NAT1", ch8Blocks);
		
		// Characteristic 9 - Equals to INT
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("Block: ii = INT isNegative: false");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("Block: not(ii = INT) isNegative: true");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii = INT", ch9Blocks);
		
		// Characteristic 10 - Equals to INT
		
		List<Block> ch10Blocks = new ArrayList<Block>();
		
		Block ch10MockedBlock1 = mock(Block.class);
		when(ch10MockedBlock1.toString()).thenReturn("Block: jj = BOOL isNegative: false");
		when(ch10MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock2 = mock(Block.class);
		when(ch10MockedBlock2.toString()).thenReturn("Block: not(jj = BOOL) isNegative: true");
		when(ch10MockedBlock2.isNegative()).thenReturn(true);
		
		ch10Blocks.add(ch10MockedBlock1);
		ch10Blocks.add(ch10MockedBlock2);
		
		expectedBlocks.put("jj = BOOL", ch10Blocks);

		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForNotEqualsCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(13);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Not Equals to abstract set
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa /= NAME isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(aa /= NAME) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);

		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa /= NAME", ch1Blocks);
		
		// Characteristic 2 - Not Equals to integer literal
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb /= 1 isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: not(bb /= 1) isNegative: true");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("bb /= 1", ch2Blocks);
		
		// Characteristic 3 - Not Equals to boolean literal
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc /= TRUE isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: not(cc /= TRUE) isNegative: true");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);

		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("cc /= TRUE", ch3Blocks);
		
		// Characteristic 4 - Not Equals all the same values array
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: dd /= (NAME * {0}) isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("Block: not(dd /= (NAME * {0})) isNegative: true");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("dd /= (NAME * {0})", ch4Blocks);
		
		// Characteristic 5 - Not Equals stated values array
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ee /= {0 |-> FALSE, 1 |-> FALSE} isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(ee /= {0 |-> FALSE, 1 |-> FALSE}) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		
		expectedBlocks.put("ee /= {0 |-> FALSE, 1 |-> FALSE}", ch5Blocks);
		
		// Characteristic 6 - Not Equals to range
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: ff /= 1..5 isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(ff /= 1..5) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ff /= 1..5", ch6Blocks);
		
		// Characteristic 7 - Not Equals to NAT
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: gg /= NAT isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("Block: not(gg /= NAT) isNegative: true");
		when(ch7MockedBlock2.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		
		expectedBlocks.put("gg /= NAT", ch7Blocks);
		
		// Characteristic 8 - Not Equals to NAT1
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("Block: hh /= NAT1 isNegative: false");
		when(ch8MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("Block: not(hh /= NAT1) isNegative: true");
		when(ch8MockedBlock2.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		
		expectedBlocks.put("hh /= NAT1", ch8Blocks);
		
		// Characteristic 9 - Not Equals to INT
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("Block: ii /= INT isNegative: false");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("Block: not(ii /= INT) isNegative: true");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("ii /= INT", ch9Blocks);
		
		// Characteristic 10 - Not Equals to INT
		
		List<Block> ch10Blocks = new ArrayList<Block>();
		
		Block ch10MockedBlock1 = mock(Block.class);
		when(ch10MockedBlock1.toString()).thenReturn("Block: jj /= BOOL isNegative: false");
		when(ch10MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock2 = mock(Block.class);
		when(ch10MockedBlock2.toString()).thenReturn("Block: not(jj /= BOOL) isNegative: true");
		when(ch10MockedBlock2.isNegative()).thenReturn(true);
		
		ch10Blocks.add(ch10MockedBlock1);
		ch10Blocks.add(ch10MockedBlock2);
		
		expectedBlocks.put("jj /= BOOL", ch10Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForRelationalCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(6);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results

		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Greater than
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa > bb isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(aa > bb) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);

		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("aa > bb", ch1Blocks);
		
		// Characteristic 2 - Less than
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: cc < dd isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: not(cc < dd) isNegative: true");
		when(ch2MockedBlock2.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		
		expectedBlocks.put("cc < dd", ch2Blocks);
		
		// Characteristics 3 - Greater or equal to
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: ee >= ff isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: not(ee >= ff) isNegative: true");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("ee >= ff", ch3Blocks);
		
		// Characteristic 4 - Less or equal to
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: gg <= hh isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("Block: not(gg <= hh) isNegative: true");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
		
		expectedBlocks.put("gg <= hh", ch4Blocks);
		
		// Characteristic 5 - Typing characteristic
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: ii = -1 isNegative: true");
		when(ch5MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: ii = 0 isNegative: false");
		when(ch5MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock3 = mock(Block.class);
		when(ch5MockedBlock3.toString()).thenReturn("Block: ii = 1 isNegative: false");
		when(ch5MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock4 = mock(Block.class);
		when(ch5MockedBlock4.toString()).thenReturn("Block: ii = MAXINT-1 isNegative: false");
		when(ch5MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock5 = mock(Block.class);
		when(ch5MockedBlock5.toString()).thenReturn("Block: ii = MAXINT isNegative: false");
		when(ch5MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock6 = mock(Block.class);
		when(ch5MockedBlock6.toString()).thenReturn("Block: ii = MAXINT+1 isNegative: true");
		when(ch5MockedBlock6.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		ch5Blocks.add(ch5MockedBlock3);
		ch5Blocks.add(ch5MockedBlock4);
		ch5Blocks.add(ch5MockedBlock5);
		ch5Blocks.add(ch5MockedBlock6);
		
		expectedBlocks.put("ii : NAT", ch5Blocks);
		
		
		// Characteristic 6 - Equal to
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: ii = jj isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(ii = jj) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
		
		expectedBlocks.put("ii = jj", ch6Blocks);
		
		// Characteristic 7 - Typing characteristic
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: jj = -1 isNegative: true");
		when(ch7MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch7MockedBlock2 = mock(Block.class);
		when(ch7MockedBlock2.toString()).thenReturn("Block: jj = 0 isNegative: false");
		when(ch7MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock3 = mock(Block.class);
		when(ch7MockedBlock3.toString()).thenReturn("Block: jj = 1 isNegative: false");
		when(ch7MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock4 = mock(Block.class);
		when(ch7MockedBlock4.toString()).thenReturn("Block: jj = MAXINT-1 isNegative: false");
		when(ch7MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock5 = mock(Block.class);
		when(ch7MockedBlock5.toString()).thenReturn("Block: jj = MAXINT isNegative: false");
		when(ch7MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch7MockedBlock6 = mock(Block.class);
		when(ch7MockedBlock6.toString()).thenReturn("Block: jj = MAXINT+1 isNegative: true");
		when(ch7MockedBlock6.isNegative()).thenReturn(true);
		
		ch7Blocks.add(ch7MockedBlock1);
		ch7Blocks.add(ch7MockedBlock2);
		ch7Blocks.add(ch7MockedBlock3);
		ch7Blocks.add(ch7MockedBlock4);
		ch7Blocks.add(ch7MockedBlock5);
		ch7Blocks.add(ch7MockedBlock6);
		
		expectedBlocks.put("jj : NAT", ch7Blocks);
		
		// Characteristic 8 - Typing characteristic
		
		List<Block> ch8Blocks = new ArrayList<Block>();
		
		Block ch8MockedBlock1 = mock(Block.class);
		when(ch8MockedBlock1.toString()).thenReturn("Block: kk = -1 isNegative: true");
		when(ch8MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch8MockedBlock2 = mock(Block.class);
		when(ch8MockedBlock2.toString()).thenReturn("Block: kk = 0 isNegative: false");
		when(ch8MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock3 = mock(Block.class);
		when(ch8MockedBlock3.toString()).thenReturn("Block: kk = 1 isNegative: false");
		when(ch8MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock4 = mock(Block.class);
		when(ch8MockedBlock4.toString()).thenReturn("Block: kk = MAXINT-1 isNegative: false");
		when(ch8MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock5 = mock(Block.class);
		when(ch8MockedBlock5.toString()).thenReturn("Block: kk = MAXINT isNegative: false");
		when(ch8MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch8MockedBlock6 = mock(Block.class);
		when(ch8MockedBlock6.toString()).thenReturn("Block: kk = MAXINT+1 isNegative: true");
		when(ch8MockedBlock6.isNegative()).thenReturn(true);
		
		ch8Blocks.add(ch8MockedBlock1);
		ch8Blocks.add(ch8MockedBlock2);
		ch8Blocks.add(ch8MockedBlock3);
		ch8Blocks.add(ch8MockedBlock4);
		ch8Blocks.add(ch8MockedBlock5);
		ch8Blocks.add(ch8MockedBlock6);
		
		expectedBlocks.put("kk : NAT", ch8Blocks);
		
		// Characteristic 9 - Not equal to
		
		List<Block> ch9Blocks = new ArrayList<Block>();
		
		Block ch9MockedBlock1 = mock(Block.class);
		when(ch9MockedBlock1.toString()).thenReturn("Block: kk /= ll isNegative: false");
		when(ch9MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch9MockedBlock2 = mock(Block.class);
		when(ch9MockedBlock2.toString()).thenReturn("Block: not(kk /= ll) isNegative: true");
		when(ch9MockedBlock2.isNegative()).thenReturn(true);
		
		ch9Blocks.add(ch9MockedBlock1);
		ch9Blocks.add(ch9MockedBlock2);
		
		expectedBlocks.put("kk /= ll", ch9Blocks);
		
		// Characteristic 10 - Typing characteristic
		
		List<Block> ch10Blocks = new ArrayList<Block>();
		
		Block ch10MockedBlock1 = mock(Block.class);
		when(ch10MockedBlock1.toString()).thenReturn("Block: ll = -1 isNegative: true");
		when(ch10MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch10MockedBlock2 = mock(Block.class);
		when(ch10MockedBlock2.toString()).thenReturn("Block: ll = 0 isNegative: false");
		when(ch10MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock3 = mock(Block.class);
		when(ch10MockedBlock3.toString()).thenReturn("Block: ll = 1 isNegative: false");
		when(ch10MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock4 = mock(Block.class);
		when(ch10MockedBlock4.toString()).thenReturn("Block: ll = MAXINT-1 isNegative: false");
		when(ch10MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock5 = mock(Block.class);
		when(ch10MockedBlock5.toString()).thenReturn("Block: ll = MAXINT isNegative: false");
		when(ch10MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch10MockedBlock6 = mock(Block.class);
		when(ch10MockedBlock6.toString()).thenReturn("Block: ll = MAXINT+1 isNegative: true");
		when(ch10MockedBlock6.isNegative()).thenReturn(true);
		
		ch10Blocks.add(ch10MockedBlock1);
		ch10Blocks.add(ch10MockedBlock2);
		ch10Blocks.add(ch10MockedBlock3);
		ch10Blocks.add(ch10MockedBlock4);
		ch10Blocks.add(ch10MockedBlock5);
		ch10Blocks.add(ch10MockedBlock6);
		
		expectedBlocks.put("ll : NAT", ch10Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForExistentialCharacteristic() {

		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(10);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Existential characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: #(xx).(((xx : NAT) => (xx > aa))) isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(#(xx).(((xx : NAT) => (xx > aa)))) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("#(xx).(((xx : NAT) => (xx > aa)))", ch1Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForUniversalCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(9);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Universal characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: !(xx).((xx : NAT) => (aa = xx & bb = (xx + 1))) isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(!(xx).((xx : NAT) => (aa = xx & bb = (xx + 1)))) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("!(xx).((xx : NAT) => (aa = xx & bb = (xx + 1)))", ch1Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForEquivalenceCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(11);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();
		
		// Characteristic 1 - Equivalence characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: ((aa = 1) <=> (bb = 0)) isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(((aa = 1) <=> (bb = 0))) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		
		expectedBlocks.put("((aa = 1) <=> (bb = 0))", ch1Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForImplicationCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(8);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: ((aa = 10) => (bb > 5)) isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: not(((aa = 10) => (bb > 5))) isNegative: true");
		when(ch1MockedBlock2.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
				
		expectedBlocks.put("((aa = 10) => (bb > 5))", ch1Blocks);
		
		// Getting actual result and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForDisjunctionCharacteristic() {
		
		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(14);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1 - Typing characteristic
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa : BOOL isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);

		ch1Blocks.add(ch1MockedBlock1);
		
		expectedBlocks.put("aa : BOOL", ch1Blocks);
		
		// Characteristic 2 - Typing characteristic
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb : BOOL isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);
		
		ch2Blocks.add(ch2MockedBlock1);
				
		expectedBlocks.put("bb : BOOL", ch2Blocks);
				
		// Characteristic 3 - Typing characteristic
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: cc = -1 isNegative: true");
		when(ch3MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: cc = 0 isNegative: false");
		when(ch3MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock3 = mock(Block.class);
		when(ch3MockedBlock3.toString()).thenReturn("Block: cc = 1 isNegative: false");
		when(ch3MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock4 = mock(Block.class);
		when(ch3MockedBlock4.toString()).thenReturn("Block: cc = MAXINT-1 isNegative: false");
		when(ch3MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock5 = mock(Block.class);
		when(ch3MockedBlock5.toString()).thenReturn("Block: cc = MAXINT isNegative: false");
		when(ch3MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock6 = mock(Block.class);
		when(ch3MockedBlock6.toString()).thenReturn("Block: cc = MAXINT+1 isNegative: true");
		when(ch3MockedBlock6.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		ch3Blocks.add(ch3MockedBlock3);
		ch3Blocks.add(ch3MockedBlock4);
		ch3Blocks.add(ch3MockedBlock5);
		ch3Blocks.add(ch3MockedBlock6);
				
		expectedBlocks.put("cc : NAT", ch3Blocks);
		
		// Characteristic 4 - Disjunction item
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: aa = TRUE isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch4MockedBlock2 = mock(Block.class);
		when(ch4MockedBlock2.toString()).thenReturn("Block: not(aa = TRUE) isNegative: true");
		when(ch4MockedBlock2.isNegative()).thenReturn(true);
		
		ch4Blocks.add(ch4MockedBlock1);
		ch4Blocks.add(ch4MockedBlock2);
				
		expectedBlocks.put("aa = TRUE", ch4Blocks);
		
		// Characteristic 5 - Disjunction item
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: bb = FALSE isNegative: false");
		when(ch5MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: not(bb = FALSE) isNegative: true");
		when(ch5MockedBlock2.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
				
		expectedBlocks.put("bb = FALSE", ch5Blocks);
		
		// Characteristic 6 - Disjunction item
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: cc = 10 isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch6MockedBlock2 = mock(Block.class);
		when(ch6MockedBlock2.toString()).thenReturn("Block: not(cc = 10) isNegative: true");
		when(ch6MockedBlock2.isNegative()).thenReturn(true);
		
		ch6Blocks.add(ch6MockedBlock1);
		ch6Blocks.add(ch6MockedBlock2);
				
		expectedBlocks.put("cc = 10", ch6Blocks);

		// Getting actual results and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesBlocksForNegationCharacteristic() {

		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/BlocksForCharacteristicsTest.mch"));
		Operation operationUnderTest = machine.getOperation(7);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1 - Typing characteristic

		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: aa = -1 isNegative: true");
		when(ch1MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch1MockedBlock2 = mock(Block.class);
		when(ch1MockedBlock2.toString()).thenReturn("Block: aa = 0 isNegative: false");
		when(ch1MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock3 = mock(Block.class);
		when(ch1MockedBlock3.toString()).thenReturn("Block: aa = 1 isNegative: false");
		when(ch1MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock4 = mock(Block.class);
		when(ch1MockedBlock4.toString()).thenReturn("Block: aa = MAXINT-1 isNegative: false");
		when(ch1MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock5 = mock(Block.class);
		when(ch1MockedBlock5.toString()).thenReturn("Block: aa = MAXINT isNegative: false");
		when(ch1MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch1MockedBlock6 = mock(Block.class);
		when(ch1MockedBlock6.toString()).thenReturn("Block: aa = MAXINT+1 isNegative: true");
		when(ch1MockedBlock6.isNegative()).thenReturn(true);
		
		ch1Blocks.add(ch1MockedBlock1);
		ch1Blocks.add(ch1MockedBlock2);
		ch1Blocks.add(ch1MockedBlock3);
		ch1Blocks.add(ch1MockedBlock4);
		ch1Blocks.add(ch1MockedBlock5);
		ch1Blocks.add(ch1MockedBlock6);
		
		expectedBlocks.put("aa : NAT", ch1Blocks);
		
		// Characteristic 2 - Typing characteristic
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: bb = -1 isNegative: true");
		when(ch2MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch2MockedBlock2 = mock(Block.class);
		when(ch2MockedBlock2.toString()).thenReturn("Block: bb = 0 isNegative: false");
		when(ch2MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock3 = mock(Block.class);
		when(ch2MockedBlock3.toString()).thenReturn("Block: bb = 1 isNegative: false");
		when(ch2MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock4 = mock(Block.class);
		when(ch2MockedBlock4.toString()).thenReturn("Block: bb = MAXINT-1 isNegative: false");
		when(ch2MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock5 = mock(Block.class);
		when(ch2MockedBlock5.toString()).thenReturn("Block: bb = MAXINT isNegative: false");
		when(ch2MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch2MockedBlock6 = mock(Block.class);
		when(ch2MockedBlock6.toString()).thenReturn("Block: bb = MAXINT+1 isNegative: true");
		when(ch2MockedBlock6.isNegative()).thenReturn(true);
		
		ch2Blocks.add(ch2MockedBlock1);
		ch2Blocks.add(ch2MockedBlock2);
		ch2Blocks.add(ch2MockedBlock3);
		ch2Blocks.add(ch2MockedBlock4);
		ch2Blocks.add(ch2MockedBlock5);
		ch2Blocks.add(ch2MockedBlock6);
		
		expectedBlocks.put("bb : NAT", ch2Blocks);
		
		// Characteristic 3 - Negation characteristic
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: not(bb : {1, 2, 3}) isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: not(not(bb : {1, 2, 3})) isNegative: true");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);

		expectedBlocks.put("not(bb : {1, 2, 3})", ch3Blocks);

		// Getting actual results and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
	
	
	@Test
	public void shouldGenerateBoundaryValuesForRangesWithMaxInt() {

		// Setting up machine, operation under test, partitioner and BlockBuilder
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/CounterModified.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		// Setting up expected results
		
		Map<String, List<Block>> expectedBlocks = new HashMap<String, List<Block>>();

		// Characteristic 1
		
		List<Block> ch1Blocks = new ArrayList<Block>();
		
		Block ch1MockedBlock1 = mock(Block.class);
		when(ch1MockedBlock1.toString()).thenReturn("Block: 0 <= value isNegative: false");
		when(ch1MockedBlock1.isNegative()).thenReturn(false);

		ch1Blocks.add(ch1MockedBlock1);
		
		expectedBlocks.put("0 <= value", ch1Blocks);
		
		// Characteristic 2
		
		List<Block> ch2Blocks = new ArrayList<Block>();
		
		Block ch2MockedBlock1 = mock(Block.class);
		when(ch2MockedBlock1.toString()).thenReturn("Block: value <= MAXINT isNegative: false");
		when(ch2MockedBlock1.isNegative()).thenReturn(false);

		ch2Blocks.add(ch2MockedBlock1);
		
		expectedBlocks.put("value <= MAXINT", ch2Blocks);

		// Characteristic 3
		
		List<Block> ch3Blocks = new ArrayList<Block>();
		
		Block ch3MockedBlock1 = mock(Block.class);
		when(ch3MockedBlock1.toString()).thenReturn("Block: value < MAXINT isNegative: false");
		when(ch3MockedBlock1.isNegative()).thenReturn(false);
		
		Block ch3MockedBlock2 = mock(Block.class);
		when(ch3MockedBlock2.toString()).thenReturn("Block: not(value < MAXINT) isNegative: true");
		when(ch3MockedBlock2.isNegative()).thenReturn(true);
		
		ch3Blocks.add(ch3MockedBlock1);
		ch3Blocks.add(ch3MockedBlock2);
		
		expectedBlocks.put("value < MAXINT", ch3Blocks);
		
		// Characteristic 4
		
		List<Block> ch4Blocks = new ArrayList<Block>();
		
		Block ch4MockedBlock1 = mock(Block.class);
		when(ch4MockedBlock1.toString()).thenReturn("Block: overflow : BOOL isNegative: false");
		when(ch4MockedBlock1.isNegative()).thenReturn(false);
		
		ch4Blocks.add(ch4MockedBlock1);
		
		expectedBlocks.put("overflow : BOOL", ch4Blocks);
		
		// Characteristic 5
		
		List<Block> ch5Blocks = new ArrayList<Block>();
		
		Block ch5MockedBlock1 = mock(Block.class);
		when(ch5MockedBlock1.toString()).thenReturn("Block: value = 0 - 1 isNegative: true");
		when(ch5MockedBlock1.isNegative()).thenReturn(true);
		
		Block ch5MockedBlock2 = mock(Block.class);
		when(ch5MockedBlock2.toString()).thenReturn("Block: value = 0 isNegative: false");
		when(ch5MockedBlock2.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock3 = mock(Block.class);
		when(ch5MockedBlock3.toString()).thenReturn("Block: value = 0 + 1 isNegative: false");
		when(ch5MockedBlock3.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock4 = mock(Block.class);
		when(ch5MockedBlock4.toString()).thenReturn("Block: value = (MAXINT - 1) - 1 isNegative: false");
		when(ch5MockedBlock4.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock5 = mock(Block.class);
		when(ch5MockedBlock5.toString()).thenReturn("Block: value = (MAXINT - 1) isNegative: false");
		when(ch5MockedBlock5.isNegative()).thenReturn(false);
		
		Block ch5MockedBlock6 = mock(Block.class);
		when(ch5MockedBlock6.toString()).thenReturn("Block: value = (MAXINT - 1) + 1 isNegative: true");
		when(ch5MockedBlock6.isNegative()).thenReturn(true);
		
		ch5Blocks.add(ch5MockedBlock1);
		ch5Blocks.add(ch5MockedBlock2);
		ch5Blocks.add(ch5MockedBlock3);
		ch5Blocks.add(ch5MockedBlock4);
		ch5Blocks.add(ch5MockedBlock5);
		ch5Blocks.add(ch5MockedBlock6);
		
		expectedBlocks.put("value : 0..((MAXINT - 1))", ch5Blocks);
		
		// Characteristic 6
		
		List<Block> ch6Blocks = new ArrayList<Block>();
		
		Block ch6MockedBlock1 = mock(Block.class);
		when(ch6MockedBlock1.toString()).thenReturn("Block: value : INT isNegative: false");
		when(ch6MockedBlock1.isNegative()).thenReturn(false);
		
		ch6Blocks.add(ch6MockedBlock1);
		
		expectedBlocks.put("value : INT", ch6Blocks);
		
		// Characteristic 7
		
		List<Block> ch7Blocks = new ArrayList<Block>();
		
		Block ch7MockedBlock1 = mock(Block.class);
		when(ch7MockedBlock1.toString()).thenReturn("Block: ((overflow = TRUE) => (value = MAXINT)) isNegative: false");
		when(ch7MockedBlock1.isNegative()).thenReturn(false);
		
		ch7Blocks.add(ch7MockedBlock1);
		
		expectedBlocks.put("((overflow = TRUE) => (value = MAXINT))", ch7Blocks);		
		
		// Getting actual results and doing verifications
		
		BVBlockBuilder blockBuilder = new BVBlockBuilder(partitioner);
		Map<String, List<Block>> actualResult = parseMapKeysToStrings(blockBuilder.getBlocks());
		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
		
		assertTrue(compare(expectedBlocks, actualResult));
	}
	
}
