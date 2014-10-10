package unit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import criteria.Criteria;
import criteria.EachChoice;
import parser.Machine;
import parser.Operation;
import testgeneration.Block;
import testgeneration.ECBlockBuilder;
import testgeneration.Partitioner;
import testgeneration.TestMachineBuilder;
import static org.mockito.Mockito.*;

public class TestMachineBuilderTest {


	@Test
	public void shouldGenerateTestMachineForFormula() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/Test.mch");
		Operation operationUnderTest = machine.getOperation(0);

		Set<List<Block>> combinations = new HashSet<List<Block>>();
		
		List<Block> combination = new ArrayList<Block>();
		
		Block mockedBlock1 = mock(Block.class);
		when(mockedBlock1.getBlock()).thenReturn("test_variable2 : BOOL");
		
		Block mockedBlock2 = mock(Block.class);
		when(mockedBlock2.getBlock()).thenReturn("test_variable3 : BOOL");
		
		Block mockedBlock3 = mock(Block.class);
		when(mockedBlock3.getBlock()).thenReturn("test_variable : BOOL");
		
		Block mockedBlock4 = mock(Block.class);
		when(mockedBlock4.getBlock()).thenReturn("pressed : BOOL");
		
		Block mockedBlock5 = mock(Block.class);
		when(mockedBlock5.getBlock()).thenReturn("foo : BOOL");
		
		Block mockedBlock6 = mock(Block.class);
		when(mockedBlock6.getBlock()).thenReturn("cab_consol_close_button_pushed");
		
		Block mockedBlock7 = mock(Block.class);
		when(mockedBlock7.getBlock()).thenReturn("cab_side_open_button_pushed");
		
		Block mockedBlock8 = mock(Block.class);
		when(mockedBlock8.getBlock()).thenReturn("cab_consol_open_button_pushed");
		
		Block mockedBlock9 = mock(Block.class);
		when(mockedBlock9.getBlock()).thenReturn("cab_side_close_button_pushed");
		
		combination.add(mockedBlock1);
		combination.add(mockedBlock2);
		combination.add(mockedBlock3);
		combination.add(mockedBlock4);
		combination.add(mockedBlock5);
		combination.add(mockedBlock6);
		combination.add(mockedBlock7);
		combination.add(mockedBlock8);
		combination.add(mockedBlock9);
		
		combinations.add(combination);
		
		TestMachineBuilder testMchBuilder = new TestMachineBuilder(operationUnderTest, combinations);
		
		String testMachine = testMchBuilder.generateTestMachine();

		assertEquals(TESTMACHINE_FOR_TEST, testMachine);
	}
	
	
	
	@Test
	public void shouldGenerateTestMachineForOperationsOnMachinesWithIncludes() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SimpleLocks.mch");
		Operation operationUnderTest = machine.getOperation(0);
			
		ECBlockBuilder blockBuilder = new ECBlockBuilder(new Partitioner(operationUnderTest));
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();
		Criteria<Block> criteria = new EachChoice<Block>(blocks);
		
		TestMachineBuilder builder = new TestMachineBuilder(operationUnderTest, criteria.getCombinations());
		String testMachineText = builder.generateTestMachine();
		
		assertEquals(SIMPLELOCKS_OPENDOOR_TESTMACHINE, testMachineText);
	}


	
	@Test
	public void shouldGenerateTestMachineForOperationsOnMachinesWithSees() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SimpleCostumer.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(new Partitioner(operationUnderTest));
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();
		Criteria<Block> criteria = new EachChoice<Block>(blocks);
		
		TestMachineBuilder builder = new TestMachineBuilder(operationUnderTest, criteria.getCombinations());
		String testMachineText = builder.generateTestMachine();

		assertEquals(SIMPLECOSTUMER_BUY_TESTMACHINE, testMachineText);
	}	
	
	

	@Test
	public void shouldGenerateTestMachineForOperationsOnMachinesWithUses() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SimpleDeath.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(new Partitioner(operationUnderTest));
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();
		Criteria<Block> criteria = new EachChoice<Block>(blocks);
		
		TestMachineBuilder builder = new TestMachineBuilder(operationUnderTest, criteria.getCombinations());
		String testMachineText = builder.generateTestMachine();

		assertEquals(SIMPLEDEATH_TESTMACHINE, testMachineText);
	}
	
	
	
	@Test
	public void shouldGenerateTestMachineForOperationsOnMachinesWithExtends() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SimpleDeathExtends.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(new Partitioner(operationUnderTest));
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();
		Criteria<Block> criteria = new EachChoice<Block>(blocks);
		
		TestMachineBuilder builder = new TestMachineBuilder(operationUnderTest, criteria.getCombinations());
		String testMachineText = builder.generateTestMachine();

		assertEquals(SIMPLEDEATHEXTENDS_TESTMACHINE, testMachineText);
	}
	
	
	
	private Machine getMachineInstance(String path) {
		Machine machine = new Machine(new File(path));
		return machine;
	}
	
	
	
	private static final String SIMPLEDEATHEXTENDS_TESTMACHINE =
			"MACHINE TestsForOp_newdeath_From_SimpleDeathExtends" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for newdeath */" + "\n" +
			"newdeath_test1(" + "\n" +
			"born," + "\n" +
			"dead," + "\n" +
			"pp" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"not(pp : born) & born <: PERSON & dead <: PERSON /* NEGATIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			";" + "\n" +
			"/* Equivalence Class test data for newdeath */" + "\n" +
			"newdeath_test2("  + "\n" +
			"born," + "\n" +
			"dead," + "\n" +
			"pp" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"pp : born & born <: PERSON & dead <: PERSON /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";
	
	
	
	private static final String SIMPLEDEATH_TESTMACHINE =
			"MACHINE TestsForOp_newdeath_From_SimpleDeath" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for newdeath */" + "\n" +
			"newdeath_test1(" + "\n" +
			"born," + "\n" +
			"dead," + "\n" +
			"pp" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"not(pp : born) & born <: PERSON & dead <: PERSON /* NEGATIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			";" + "\n" +
			"/* Equivalence Class test data for newdeath */" + "\n" +
			"newdeath_test2("  + "\n" +
			"born," + "\n" +
			"dead," + "\n" +
			"pp" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"pp : born & born <: PERSON & dead <: PERSON /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";

	
	
	private static final String SIMPLECOSTUMER_BUY_TESTMACHINE =
			"MACHINE TestsForOp_buy_From_SimpleCostumer" + "\n" +
			"\n" +
			"CONSTANTS limit" + "\n\n" +
			"PROPERTIES" + "\n" +
			"limit : (GOODS --> NAT1)" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for buy */" + "\n" +
			"buy_test1(" + "\n" +
			"purchases," + "\n" +
			"price," + "\n" +
			"gg" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"not(price(gg) <= limit(gg)) & purchases <: GOODS & price : (GOODS --> NAT1) /* NEGATIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			";" + "\n" +
			"/* Equivalence Class test data for buy */" + "\n" +
			"buy_test2(" + "\n" +
			"purchases," + "\n" +
			"price," + "\n" +
			"gg" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"price(gg) <= limit(gg) & purchases <: GOODS & price : (GOODS --> NAT1) /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";

	
	
	private static final String SIMPLELOCKS_OPENDOOR_TESTMACHINE =
			"MACHINE TestsForOp_opendoor_From_Locks" + "\n" +
			"\n" +
			"SETS" + "\n" +
			"STATUS = {locked, unlocked}" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for opendoor */" + "\n" +
			"opendoor_test1(" + "\n" +
			"dd," + "\n" +
			"status" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"status : (DOOR --> STATUS) & not(status(dd) = unlocked) /* NEGATIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			";" + "\n" +
			"/* Equivalence Class test data for opendoor */" + "\n" +
			"opendoor_test2(" + "\n" +
			"dd," + "\n" +
			"status" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"status : (DOOR --> STATUS) & status(dd) = unlocked /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";
			

	
	private static final String TESTMACHINE_FOR_TEST = 
			"MACHINE TestsForOp_set_cab_consol_open_button_pressed_From_Test\n\n" +
				
			"OPERATIONS\n" +
			"/* Equivalence Class test data for set_cab_consol_open_button_pressed */\n" +
			"set_cab_consol_open_button_pressed_test1(\n" +
			"test_variable2,\n" +
			"test_variable3,\n" +
			"test_variable,\n" +
			"cab_side_close_button_pushed,\n" +
			"pressed,\n" +
			"foo,\n" +
			"cab_consol_close_button_pushed,\n" +
			"cab_side_open_button_pushed,\n" +
			"cab_consol_open_button_pushed\n" +
			") =\n" +
			"PRE\n" +
			"test_variable2 : BOOL & test_variable3 : BOOL & test_variable : BOOL & pressed : BOOL & foo : BOOL & cab_consol_close_button_pushed & cab_side_open_button_pushed & cab_consol_open_button_pushed & cab_side_close_button_pushed /* POSITIVE */\n" +
			"THEN\n" +
			"skip\n" +
			"END\n" +
			"END";
	
}
