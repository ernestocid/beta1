//package tests.unit;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import main.BoundaryValuesBlockBuilder;
//import main.Partitioner;
//
//import org.junit.Test;
//
//import parser.Machine;
//import parser.Operation;
//
//public class BoundaryValuesBlockBuilderTest {
//
//	
//	@Test
//	public void shouldNotGenerateBoundaryBlocksForInvariantClauses() {
//		Machine machine = new Machine(new File("machines/Paperround.mch"));
//		Operation operationUnderTest = machine.getOperation(0);
//		
//		
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//		
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("papers <: 1..163");
//		
//		expectedBlocks.put("papers <: 1..163", ch1Blocks);
//
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("magazines <: papers");
//		
//		expectedBlocks.put("magazines <: papers", ch2Blocks);
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("card(papers) <= 60");
//		
//		expectedBlocks.put("card(papers) <= 60", ch3Blocks);
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("hh = 1 - 1");
//		ch4Blocks.add("hh = 1");
//		ch4Blocks.add("hh = 1 + 1");
//		ch4Blocks.add("hh = 163 - 1");
//		ch4Blocks.add("hh = 163");
//		ch4Blocks.add("hh = 163 + 1");
//		
//		expectedBlocks.put("hh : 1..163", ch4Blocks);
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("card(papers) < 60");
//		ch5Blocks.add("not(card(papers) < 60)");
//		
//		expectedBlocks.put("card(papers) < 60", ch5Blocks);
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBlocksForTypingMemberOfCharacteristics() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(0);
//		
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//		
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		// NAT set characteristic
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa = -1");
//		ch1Blocks.add("aa = 0");
//		ch1Blocks.add("aa = 1");
//		ch1Blocks.add("aa = MAXINT-1");
//		ch1Blocks.add("aa = MAXINT");
//		ch1Blocks.add("aa = MAXINT+1");
//		
//		expectedBlocks.put("aa : NAT", ch1Blocks);
//		
//		
//		// NAT1 set characteristic
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb = 0");
//		ch2Blocks.add("bb = 1");
//		ch2Blocks.add("bb = 2");
//		ch2Blocks.add("bb = MAXINT-1");
//		ch2Blocks.add("bb = MAXINT");
//		ch2Blocks.add("bb = MAXINT+1");
//		
//		expectedBlocks.put("bb : NAT1", ch2Blocks);
//		
//		
//		// INT set characteristic
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc : INT");
//		
//		expectedBlocks.put("cc : INT", ch3Blocks);
//		
//		
//		// BOOL set characteristic
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("dd : BOOL");
//		
//		expectedBlocks.put("dd : BOOL", ch4Blocks);
//		
//		
//		// SET characteristic
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ee : {1, 2, 3}");
//		ch5Blocks.add("not(ee : {1, 2, 3})");
//		
//		expectedBlocks.put("ee : {1, 2, 3}", ch5Blocks);
//		
//		
//		// RANGE characteristic	
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ff = 1 - 1");
//		ch6Blocks.add("ff = 1");
//		ch6Blocks.add("ff = 1 + 1");
//		ch6Blocks.add("ff = 5 - 1");
//		ch6Blocks.add("ff = 5");
//		ch6Blocks.add("ff = 5 + 1");
//		
//		expectedBlocks.put("ff : 1..5", ch6Blocks);
//		
//		
//		// Abstract set characteristic
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("gg : NAME");
//		
//		expectedBlocks.put("gg : NAME", ch7Blocks);
//		
//		
//		// SET characteristic
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("hh : awnser");
//		ch8Blocks.add("not(hh : awnser)");
//		
//		expectedBlocks.put("hh : awnser", ch8Blocks);
//		
//		
//		// STRING characteristic
//		
//		List<String> ch9Blocks = new ArrayList<String>();
//		ch9Blocks.add("ii : STRING");
//		
//		expectedBlocks.put("ii : STRING", ch9Blocks);
//		
//		
//		// FUNC characteristic
//		
//		List<String> ch10Blocks = new ArrayList<String>();
//		ch10Blocks.add("jj : (NAT --> NAT)");
//		ch10Blocks.add("not(jj : (NAT --> NAT))");
//		
//		expectedBlocks.put("jj : (NAT --> NAT)", ch10Blocks);
//		
//		
//		List<String> ch11Blocks = new ArrayList<String>();
//		ch11Blocks.add("kk : (NAT >-> NAT)");
//		ch11Blocks.add("not(kk : (NAT >-> NAT))");
//		
//		expectedBlocks.put("kk : (NAT >-> NAT)", ch11Blocks);
//		
//		
//		List<String> ch12Blocks = new ArrayList<String>();
//		ch12Blocks.add("ll : (NAT -->> NAT)");
//		ch12Blocks.add("not(ll : (NAT -->> NAT))");
//		
//		expectedBlocks.put("ll : (NAT -->> NAT)", ch12Blocks);
//		
//	
//		List<String> ch13Blocks = new ArrayList<String>();
//		ch13Blocks.add("mm : (NAT >->> NAT)");
//		ch13Blocks.add("not(mm : (NAT >->> NAT))");
//		
//		expectedBlocks.put("mm : (NAT >->> NAT)", ch13Blocks);
//		
//		
//		List<String> ch14Blocks = new ArrayList<String>();
//		ch14Blocks.add("nn + 2 : {1, 2} \\/ {3, 4}");
//		ch14Blocks.add("not(nn + 2 : {1, 2} \\/ {3, 4})");
//		
//		expectedBlocks.put("nn + 2 : {1, 2} \\/ {3, 4}", ch14Blocks);
//		
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForNotMemberOfCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(1);
//		
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//		
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Does not belong to NAT characteristic
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa /: NAT");
//		ch1Blocks.add("not(aa /: NAT)");
//		
//		expectedBlocks.put("aa /: NAT", ch1Blocks);
//		
//		
//		// Does not belong to NAT1 characteristic
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb /: NAT1");
//		ch2Blocks.add("not(bb /: NAT1)");
//		
//		expectedBlocks.put("bb /: NAT1", ch2Blocks);
//		
//		
//		// Does not belong to INT characteristic
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc /: INT");
//		ch3Blocks.add("not(cc /: INT)");
//		
//		expectedBlocks.put("cc /: INT", ch3Blocks);
//		
//		
//		// Does not belong to BOOL characteristic
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("dd /: BOOL");
//		ch4Blocks.add("not(dd /: BOOL)");
//		
//		expectedBlocks.put("dd /: BOOL", ch4Blocks);
//		
//		
//		// Does not belong to explicit set characteristic
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ee /: {1, 2, 3}");
//		ch5Blocks.add("not(ee /: {1, 2, 3})");
//		
//		expectedBlocks.put("ee /: {1, 2, 3}", ch5Blocks);
//		
//		
//		// Does not belong to RANGE characteristic
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ff /: 1..5");
//		ch6Blocks.add("not(ff /: 1..5)");
//			
//		expectedBlocks.put("ff /: 1..5", ch6Blocks);
//		
//		
//		// Does not belong to ABSTRACT set characteristic
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("gg /: NAME");
//		ch7Blocks.add("not(gg /: NAME)");
//		
//		expectedBlocks.put("gg /: NAME", ch7Blocks);
//				
//		
//		// Does not belong to ID characteristic
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("hh /: awnser");
//		ch8Blocks.add("not(hh /: awnser)");
//		
//		expectedBlocks.put("hh /: awnser", ch8Blocks);
//				
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForSubsetCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(2);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//		
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Subset of NAT characteristic
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa <: NAT");
//		ch1Blocks.add("not(aa <: NAT)");
//		
//		expectedBlocks.put("aa <: NAT", ch1Blocks);
//		
//		
//		// Subset of NAT1 characteristic
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb <: NAT1");
//		ch2Blocks.add("not(bb <: NAT1)");
//		
//		expectedBlocks.put("bb <: NAT1", ch2Blocks);
//		
//		
//		// Subset of INT characteristic
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc <: INT");
//		
//		expectedBlocks.put("cc <: INT", ch3Blocks);
//		
//		
//		// Subset of BOOL characteristic
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("dd <: BOOL");
//		
//		expectedBlocks.put("dd <: BOOL", ch4Blocks);
//		
//		
//		// Subset of explicit set characteristic
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ee <: {1, 2, 3}");
//		ch5Blocks.add("not(ee <: {1, 2, 3})");
//		
//		expectedBlocks.put("ee <: {1, 2, 3}", ch5Blocks);
//		
//		
//		// Subset of RANGE characteristic
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ff <: 1..5");
//		ch6Blocks.add("not(ff <: 1..5)");
//		
//		expectedBlocks.put("ff <: 1..5", ch6Blocks);
//		
//		
//		// Subset of ABSTRACT set characteristic
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("gg <: NAME");
//		
//		expectedBlocks.put("gg <: NAME", ch7Blocks);
//		
//		
//		// Subset of ID characteristic
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("hh <: awnser");
//		ch8Blocks.add("not(hh <: awnser)");
//		
//		expectedBlocks.put("hh <: awnser", ch8Blocks);
//		
//		
//		// Non typing subset characteristic
//		
//		List<String> ch9Blocks = new ArrayList<String>();
//		ch9Blocks.add("ii \\/ {2} <: {1, 2} \\/ {3, 4}");
//		ch9Blocks.add("not(ii \\/ {2} <: {1, 2} \\/ {3, 4})");
//		
//		expectedBlocks.put("ii \\/ {2} <: {1, 2} \\/ {3, 4}", ch9Blocks);
//		
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForNotSubsetCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(3);
//		
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//			
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Not subset of NAT
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa /<: NAT");
//		ch1Blocks.add("not(aa /<: NAT)");
//		
//		expectedBlocks.put("aa /<: NAT", ch1Blocks);
//		
//		
//		// Not subset of NAT1
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb /<: NAT1");
//		ch2Blocks.add("not(bb /<: NAT1)");
//		
//		expectedBlocks.put("bb /<: NAT1", ch2Blocks);
//		
//		
//		// Not subset of INT
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc /<: INT");
//		ch3Blocks.add("not(cc /<: INT)");
//		
//		expectedBlocks.put("cc /<: INT", ch3Blocks);
//		
//		
//		// Not subset of BOOL
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("dd /<: BOOL");
//		ch4Blocks.add("not(dd /<: BOOL)");
//		
//		expectedBlocks.put("dd /<: BOOL", ch4Blocks);
//		
//		
//		// Not subset of explicit set
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ee /<: {1, 2, 3}");
//		ch5Blocks.add("not(ee /<: {1, 2, 3})");
//		
//		expectedBlocks.put("ee /<: {1, 2, 3}", ch5Blocks);
//		
//		
//		// Not subset of range
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ff /<: 1..5");
//		ch6Blocks.add("not(ff /<: 1..5)");
//		
//		expectedBlocks.put("ff /<: 1..5", ch6Blocks);
//		
//		
//		// Not subset of abstract set
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("gg /<: NAME");
//		ch7Blocks.add("not(gg /<: NAME)");
//		
//		expectedBlocks.put("gg /<: NAME", ch7Blocks);
//		
//		
//		// Not subset of ID
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("hh /<: awnser");
//		ch8Blocks.add("not(hh /<: awnser)");
//		
//		expectedBlocks.put("hh /<: awnser", ch8Blocks);
//		
//		
//		// Expression not subset of subset
//		
//		List<String> ch9Blocks = new ArrayList<String>();
//		ch9Blocks.add("ii \\/ {5} /<: {1, 2} \\/ {3, 4}");
//		ch9Blocks.add("not(ii \\/ {5} /<: {1, 2} \\/ {3, 4})");
//				
//		expectedBlocks.put("ii \\/ {5} /<: {1, 2} \\/ {3, 4}", ch9Blocks);
//		
//
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForSubsetStrictCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(4);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//
//
//		// Strict subset of NAT
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa <<: NAT");
//		ch1Blocks.add("not(aa <<: NAT)");
//		
//		expectedBlocks.put("aa <<: NAT", ch1Blocks);
//		
//		
//		// Strict subset of NAT1
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb <<: NAT1");
//		ch2Blocks.add("not(bb <<: NAT1)");
//		
//		expectedBlocks.put("bb <<: NAT1", ch2Blocks);
//		
//		
//		// Strict subset of INT
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc <<: INT");
//		
//		expectedBlocks.put("cc <<: INT", ch3Blocks);
//		
//		
//		// Strict subset of BOOL
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("dd <<: BOOL");
//		
//		expectedBlocks.put("dd <<: BOOL", ch4Blocks);
//		
//		
//		// Strict subset of explicit set
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ee <<: {1, 2, 3}");
//		ch5Blocks.add("not(ee <<: {1, 2, 3})");
//		
//		expectedBlocks.put("ee <<: {1, 2, 3}", ch5Blocks);
//		
//		
//		// Strict subset of range
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ff <<: 1..5");
//		ch6Blocks.add("not(ff <<: 1..5)");
//		
//		expectedBlocks.put("ff <<: 1..5", ch6Blocks);
//		
//		
//		// Strict subset of abstract set
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("gg <<: NAME");
//		ch7Blocks.add("not(gg <<: NAME)");
//		
//		expectedBlocks.put("gg <<: NAME", ch7Blocks);
//		
//		
//		// Strict subset of ID
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("hh <<: awnser");
//		ch8Blocks.add("not(hh <<: awnser)");
//		
//		expectedBlocks.put("hh <<: awnser", ch8Blocks);
//		
//		
//		// Expression strict subset of expression
//		
//		List<String> ch9Blocks = new ArrayList<String>();
//		ch9Blocks.add("ii \\/ {5} <<: {1, 2} \\/ {3, 4}");
//		ch9Blocks.add("not(ii \\/ {5} <<: {1, 2} \\/ {3, 4})");
//				
//		expectedBlocks.put("ii \\/ {5} <<: {1, 2} \\/ {3, 4}", ch9Blocks);
//		
//
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForNotStrictSubsetCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(5);
//		
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//			
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Not subset of NAT
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa /<<: NAT");
//		ch1Blocks.add("not(aa /<<: NAT)");
//		
//		expectedBlocks.put("aa /<<: NAT", ch1Blocks);
//		
//		
//		// Not subset of NAT1
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb /<<: NAT1");
//		ch2Blocks.add("not(bb /<<: NAT1)");
//		
//		expectedBlocks.put("bb /<<: NAT1", ch2Blocks);
//		
//		
//		// Not subset of INT
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc /<<: INT");
//		ch3Blocks.add("not(cc /<<: INT)");
//		
//		expectedBlocks.put("cc /<<: INT", ch3Blocks);
//		
//		
//		// Not subset of BOOL
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("dd /<<: BOOL");
//		ch4Blocks.add("not(dd /<<: BOOL)");
//		
//		expectedBlocks.put("dd /<<: BOOL", ch4Blocks);
//		
//		
//		// Not subset of explicit set
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ee /<<: {1, 2, 3}");
//		ch5Blocks.add("not(ee /<<: {1, 2, 3})");
//		
//		expectedBlocks.put("ee /<<: {1, 2, 3}", ch5Blocks);
//		
//		
//		// Not subset of range
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ff /<<: 1..5");
//		ch6Blocks.add("not(ff /<<: 1..5)");
//		
//		expectedBlocks.put("ff /<<: 1..5", ch6Blocks);
//		
//		
//		// Not subset of abstract set
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("gg /<<: NAME");
//		ch7Blocks.add("not(gg /<<: NAME)");
//		
//		expectedBlocks.put("gg /<<: NAME", ch7Blocks);
//		
//		
//		// Not subset of ID
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("hh /<<: awnser");
//		ch8Blocks.add("not(hh /<<: awnser)");
//		
//		expectedBlocks.put("hh /<<: awnser", ch8Blocks);
//		
//		
//		// Expression not subset of subset
//		
//		List<String> ch9Blocks = new ArrayList<String>();
//		ch9Blocks.add("ii \\/ {5} /<<: {1, 2} \\/ {3, 4}");
//		ch9Blocks.add("not(ii \\/ {5} /<<: {1, 2} \\/ {3, 4})");
//				
//		expectedBlocks.put("ii \\/ {5} /<<: {1, 2} \\/ {3, 4}", ch9Blocks);
//		
//
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForNotSubsetStrictCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(5);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//
//
//		// Not strict subset of NAT
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa /<<: NAT");
//		ch1Blocks.add("not(aa /<<: NAT)");
//		
//		expectedBlocks.put("aa /<<: NAT", ch1Blocks);
//		
//		
//		// Not strict subset of NAT1
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb /<<: NAT1");
//		ch2Blocks.add("not(bb /<<: NAT1)");
//
//		expectedBlocks.put("bb /<<: NAT1", ch2Blocks);
//		
//		
//		// Not strict subset of INT
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc /<<: INT");
//		ch3Blocks.add("not(cc /<<: INT)");
//		
//		expectedBlocks.put("cc /<<: INT", ch3Blocks);
//		
//		
//		// Not strict subset of BOOL
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("dd /<<: BOOL");
//		ch4Blocks.add("not(dd /<<: BOOL)");
//		
//		expectedBlocks.put("dd /<<: BOOL", ch4Blocks);
//		
//		
//		// Not strict subset of explicit set
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ee /<<: {1, 2, 3}");
//		ch5Blocks.add("not(ee /<<: {1, 2, 3})");
//		
//		expectedBlocks.put("ee /<<: {1, 2, 3}", ch5Blocks);
//		
//		
//		// Not strict subset of range
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ff /<<: 1..5");
//		ch6Blocks.add("not(ff /<<: 1..5)");
//		
//		expectedBlocks.put("ff /<<: 1..5", ch6Blocks);
//		
//		
//		// Not strict susbet of abstract set
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("gg /<<: NAME");
//		ch7Blocks.add("not(gg /<<: NAME)");
//		
//		expectedBlocks.put("gg /<<: NAME", ch7Blocks);
//		
//		
//		// Not strict subset of ID
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("hh /<<: awnser");
//		ch8Blocks.add("not(hh /<<: awnser)");
//		
//		expectedBlocks.put("hh /<<: awnser", ch8Blocks);
//		
//		
//		// Expression not subset of subset
//		
//		List<String> ch9Blocks = new ArrayList<String>();
//		ch9Blocks.add("ii \\/ {5} /<<: {1, 2} \\/ {3, 4}");
//		ch9Blocks.add("not(ii \\/ {5} /<<: {1, 2} \\/ {3, 4})");
//				
//		expectedBlocks.put("ii \\/ {5} /<<: {1, 2} \\/ {3, 4}", ch9Blocks);
//		
//
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForEqualsCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(12);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Equals to abstract set
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa = NAME");
//		ch1Blocks.add("not(aa = NAME)");
//		
//		expectedBlocks.put("aa = NAME", ch1Blocks);
//		
//		
//		// Equals to integer literal
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb = 1");
//		ch2Blocks.add("not(bb = 1)");
//		
//		expectedBlocks.put("bb = 1", ch2Blocks);
//		
//		
//		// Equals to boolean literal
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc = TRUE");
//		ch3Blocks.add("not(cc = TRUE)");
//		
//		expectedBlocks.put("cc = TRUE", ch3Blocks);
//		
//		
//		// Equals all the same values array
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("dd = (NAME * {0})");
//		ch4Blocks.add("not(dd = (NAME * {0}))");
//		
//		expectedBlocks.put("dd = (NAME * {0})", ch4Blocks);
//		
//		
//		// Equals stated values array
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ee = {0 |-> FALSE, 1 |-> FALSE}");
//		ch5Blocks.add("not(ee = {0 |-> FALSE, 1 |-> FALSE})");
//		
//		expectedBlocks.put("ee = {0 |-> FALSE, 1 |-> FALSE}", ch5Blocks);
//		
//		
//		// Equals to range
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ff = 1..5");
//		ch6Blocks.add("not(ff = 1..5)");
//		
//		expectedBlocks.put("ff = 1..5", ch6Blocks);
//		
//		
//		// Equals to NAT
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("gg = NAT");
//		ch7Blocks.add("not(gg = NAT)");
//		
//		expectedBlocks.put("gg = NAT", ch7Blocks);
//		
//		
//		// Equals to NAT1
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("hh = NAT1");
//		ch8Blocks.add("not(hh = NAT1)");
//		
//		expectedBlocks.put("hh = NAT1", ch8Blocks);
//		
//		
//		// Equals to INT
//		
//		List<String> ch9Blocks = new ArrayList<String>();
//		ch9Blocks.add("ii = INT");
//		ch9Blocks.add("not(ii = INT)");
//		
//		expectedBlocks.put("ii = INT", ch9Blocks);
//		
//		
//		// Equals to INT
//		
//		List<String> ch10Blocks = new ArrayList<String>();
//		ch10Blocks.add("jj = BOOL");
//		ch10Blocks.add("not(jj = BOOL)");
//		
//		expectedBlocks.put("jj = BOOL", ch10Blocks);
//		
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForNotEqualsCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(13);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Not Equals to abstract set
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa /= NAME");
//		ch1Blocks.add("not(aa /= NAME)");
//		
//		expectedBlocks.put("aa /= NAME", ch1Blocks);
//		
//		
//		// Not Equals to integer literal
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb /= 1");
//		ch2Blocks.add("not(bb /= 1)");
//		
//		expectedBlocks.put("bb /= 1", ch2Blocks);
//		
//		
//		// Not Equals to boolean literal
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc /= TRUE");
//		ch3Blocks.add("not(cc /= TRUE)");
//		
//		expectedBlocks.put("cc /= TRUE", ch3Blocks);
//		
//		
//		// Not Equals all the same values array
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("dd /= (NAME * {0})");
//		ch4Blocks.add("not(dd /= (NAME * {0}))");
//		
//		expectedBlocks.put("dd /= (NAME * {0})", ch4Blocks);
//		
//		
//		// Not Equals stated values array
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ee /= {0 |-> FALSE, 1 |-> FALSE}");
//		ch5Blocks.add("not(ee /= {0 |-> FALSE, 1 |-> FALSE})");
//		
//		expectedBlocks.put("ee /= {0 |-> FALSE, 1 |-> FALSE}", ch5Blocks);
//		
//		
//		// Not Equals to range
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ff /= 1..5");
//		ch6Blocks.add("not(ff /= 1..5)");
//		
//		expectedBlocks.put("ff /= 1..5", ch6Blocks);
//		
//		
//		// Not Equals to NAT
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("gg /= NAT");
//		ch7Blocks.add("not(gg /= NAT)");
//		
//		expectedBlocks.put("gg /= NAT", ch7Blocks);
//		
//		
//		// Not Equals to NAT1
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("hh /= NAT1");
//		ch8Blocks.add("not(hh /= NAT1)");
//		
//		expectedBlocks.put("hh /= NAT1", ch8Blocks);
//		
//		
//		// Not Equals to INT
//		
//		List<String> ch9Blocks = new ArrayList<String>();
//		ch9Blocks.add("ii /= INT");
//		ch9Blocks.add("not(ii /= INT)");
//		
//		expectedBlocks.put("ii /= INT", ch9Blocks);
//		
//		
//		// Not Equals to INT
//		
//		List<String> ch10Blocks = new ArrayList<String>();
//		ch10Blocks.add("jj /= BOOL");
//		ch10Blocks.add("not(jj /= BOOL)");
//		
//		expectedBlocks.put("jj /= BOOL", ch10Blocks);
//		
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForRelationalCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(6);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Greater than
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa > bb");
//		ch1Blocks.add("not(aa > bb)");
//		
//		expectedBlocks.put("aa > bb", ch1Blocks);
//		
//		
//		// Less than
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("cc < dd");
//		ch2Blocks.add("not(cc < dd)");
//		
//		expectedBlocks.put("cc < dd", ch2Blocks);
//		
//		
//		// Greater or equal to
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("ee >= ff");
//		ch3Blocks.add("not(ee >= ff)");
//		
//		expectedBlocks.put("ee >= ff", ch3Blocks);
//		
//		
//		// Less or equal to
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("gg <= hh");
//		ch4Blocks.add("not(gg <= hh)");
//		
//		expectedBlocks.put("gg <= hh", ch4Blocks);
//		
//		
//		// Typing characteristic
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("ii = -1");
//		ch5Blocks.add("ii = 0");
//		ch5Blocks.add("ii = 1");
//		ch5Blocks.add("ii = MAXINT-1");
//		ch5Blocks.add("ii = MAXINT");
//		ch5Blocks.add("ii = MAXINT+1");
//		
//		expectedBlocks.put("ii : NAT", ch5Blocks);
//		
//		
//		// Equal to
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("ii = jj");
//		ch6Blocks.add("not(ii = jj)");
//		
//		expectedBlocks.put("ii = jj", ch6Blocks);
//		
//		
//		// Typing characteristic
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("jj = -1");
//		ch7Blocks.add("jj = 0");
//		ch7Blocks.add("jj = 1");
//		ch7Blocks.add("jj = MAXINT-1");
//		ch7Blocks.add("jj = MAXINT");
//		ch7Blocks.add("jj = MAXINT+1");
//		
//		expectedBlocks.put("jj : NAT", ch7Blocks);
//		
//		
//		// Typing characteristic
//		
//		List<String> ch8Blocks = new ArrayList<String>();
//		ch8Blocks.add("kk = -1");
//		ch8Blocks.add("kk = 0");
//		ch8Blocks.add("kk = 1");
//		ch8Blocks.add("kk = MAXINT-1");
//		ch8Blocks.add("kk = MAXINT");
//		ch8Blocks.add("kk = MAXINT+1");
//		
//		expectedBlocks.put("kk : NAT", ch8Blocks);
//		
//		
//		// Not equal to
//		
//		List<String> ch9Blocks = new ArrayList<String>();
//		ch9Blocks.add("kk /= ll");
//		ch9Blocks.add("not(kk /= ll)");
//		
//		expectedBlocks.put("kk /= ll", ch9Blocks);
//		
//		
//		// Typing characteristic
//		
//		List<String> ch10Blocks = new ArrayList<String>();
//		ch10Blocks.add("ll = -1");
//		ch10Blocks.add("ll = 0");
//		ch10Blocks.add("ll = 1");
//		ch10Blocks.add("ll = MAXINT-1");
//		ch10Blocks.add("ll = MAXINT");
//		ch10Blocks.add("ll = MAXINT+1");
//		
//		expectedBlocks.put("ll : NAT", ch10Blocks);
//		
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForExistentialCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(10);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Existential characteristic
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("#(xx).((xx : NAT) => (xx > aa))");
//		ch1Blocks.add("not(#(xx).((xx : NAT) => (xx > aa)))");
//		
//		expectedBlocks.put("#(xx).((xx : NAT) => (xx > aa))", ch1Blocks);
//		
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForUniversalCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(9);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Universal characteristic
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("!(xx).((xx : NAT) => (aa = xx & bb = xx + 1))");
//		ch1Blocks.add("not(!(xx).((xx : NAT) => (aa = xx & bb = xx + 1)))");
//		
//		expectedBlocks.put("!(xx).((xx : NAT) => (aa = xx & bb = xx + 1))", ch1Blocks);
//		
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForEquivalenceCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(11);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//		
//		
//		// Equivalence characteristic
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("((aa = 1) <=> (bb = 0))");
//		ch1Blocks.add("not(((aa = 1) <=> (bb = 0)))");
//		
//		expectedBlocks.put("((aa = 1) <=> (bb = 0))", ch1Blocks);
//		
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForImplicationCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(8);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//		
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//
//
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("((aa = 10) => (bb > 5))");
//		ch1Blocks.add("not(((aa = 10) => (bb > 5)))");
//				
//		expectedBlocks.put("((aa = 10) => (bb > 5))", ch1Blocks);
//
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForDisjunctionCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(14);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//		
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//
//
//		// Typing characteristic
//		
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa : BOOL");
//				
//		expectedBlocks.put("aa : BOOL", ch1Blocks);
//		
//		
//		// Typing characteristic
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb : BOOL");
//				
//		expectedBlocks.put("bb : BOOL", ch2Blocks);
//		
//				
//		// Typing characteristic
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("cc = -1");
//		ch3Blocks.add("cc = 0");
//		ch3Blocks.add("cc = 1");
//		ch3Blocks.add("cc = MAXINT-1");
//		ch3Blocks.add("cc = MAXINT");
//		ch3Blocks.add("cc = MAXINT+1");
//				
//		expectedBlocks.put("cc : NAT", ch3Blocks);
//		
//		
//		// Disjunction item
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("aa = TRUE");
//		ch4Blocks.add("not(aa = TRUE)");
//				
//		expectedBlocks.put("aa = TRUE", ch4Blocks);
//
//		
//		// Disjunction item
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("bb = FALSE");
//		ch5Blocks.add("not(bb = FALSE)");
//				
//		expectedBlocks.put("bb = FALSE", ch5Blocks);
//		
//		
//		// Disjunction item
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("cc = 10");
//		ch6Blocks.add("not(cc = 10)");
//				
//		expectedBlocks.put("cc = 10", ch6Blocks);
//		
//
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesBlocksForNegationCharacteristic() {
//		Machine machine = new Machine(new File("machines/others/BlocksForCharacteristicsTest.mch"));
//		Operation operationUnderTest = machine.getOperation(7);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//		
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//
//
//		// Typing characteristic
//
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("aa = -1");
//		ch1Blocks.add("aa = 0");
//		ch1Blocks.add("aa = 1");
//		ch1Blocks.add("aa = MAXINT-1");
//		ch1Blocks.add("aa = MAXINT");
//		ch1Blocks.add("aa = MAXINT+1");
//		
//		expectedBlocks.put("aa : NAT", ch1Blocks);
//		
//		
//		// Typing characteristic
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("bb = -1");
//		ch2Blocks.add("bb = 0");
//		ch2Blocks.add("bb = 1");
//		ch2Blocks.add("bb = MAXINT-1");
//		ch2Blocks.add("bb = MAXINT");
//		ch2Blocks.add("bb = MAXINT+1");
//		
//		expectedBlocks.put("bb : NAT", ch2Blocks);
//		
//		
//		// Negation characteristic
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("not(bb : {1, 2, 3})");
//		ch3Blocks.add("not(not(bb : {1, 2, 3}))");
//
//		expectedBlocks.put("not(bb : {1, 2, 3})", ch3Blocks);
//
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//	
//	
//	
//	@Test
//	public void shouldGenerateBoundaryValuesForRangesWithMaxInt() {
//		Machine machine = new Machine(new File("machines/others/CounterModified.mch"));
//		Operation operationUnderTest = machine.getOperation(1);
//
//		Partitioner partitioner = new Partitioner(operationUnderTest);
//		BoundaryValuesBlockBuilder blockBuilder = new BoundaryValuesBlockBuilder(partitioner);
//
//		
//		Map<String, List<String>> expectedBlocks = new HashMap<String, List<String>>();
//
//		List<String> ch1Blocks = new ArrayList<String>();
//		ch1Blocks.add("0 <= value");
//		
//		expectedBlocks.put("0 <= value", ch1Blocks);
//		
//		
//		List<String> ch2Blocks = new ArrayList<String>();
//		ch2Blocks.add("value <= MAXINT");
//		
//		expectedBlocks.put("value <= MAXINT", ch2Blocks);
//		
//		
//		List<String> ch3Blocks = new ArrayList<String>();
//		ch3Blocks.add("value < MAXINT");
//		ch3Blocks.add("not(value < MAXINT)");
//		
//		expectedBlocks.put("value < MAXINT", ch3Blocks);
//		
//		
//		List<String> ch4Blocks = new ArrayList<String>();
//		ch4Blocks.add("overflow : BOOL");
//		
//		expectedBlocks.put("overflow : BOOL", ch4Blocks);
//		
//		
//		List<String> ch5Blocks = new ArrayList<String>();
//		ch5Blocks.add("value = 0 - 1");
//		ch5Blocks.add("value = 0");
//		ch5Blocks.add("value = 0 + 1");
//		ch5Blocks.add("value = MAXINT - 1 - 1");
//		ch5Blocks.add("value = MAXINT - 1");
//		ch5Blocks.add("value = MAXINT - 1 + 1");
//		
//		expectedBlocks.put("value : 0..(MAXINT - 1)", ch5Blocks);
//		
//		
//		List<String> ch6Blocks = new ArrayList<String>();
//		ch6Blocks.add("value : INT");
//		
//		expectedBlocks.put("value : INT", ch6Blocks);
//		
//		
//		List<String> ch7Blocks = new ArrayList<String>();
//		ch7Blocks.add("((overflow = TRUE) => (value = MAXINT))");
//		
//		expectedBlocks.put("((overflow = TRUE) => (value = MAXINT))", ch7Blocks);		
//		
//		
//		assertEquals(expectedBlocks, blockBuilder.getBlocks());
//	}
//}
