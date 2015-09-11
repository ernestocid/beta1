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
	public void shouldGenerateTestMachineForOperationsOnMachinesWithIncludesAndTransitivity() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/TestIncludeMachineC.mch");
		Operation operationUnderTest = machine.getOperation(0);
			
		ECBlockBuilder blockBuilder = new ECBlockBuilder(new Partitioner(operationUnderTest));
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();
		Criteria<Block> criteria = new EachChoice<Block>(blocks);
		
		TestMachineBuilder builder = new TestMachineBuilder(operationUnderTest, criteria.getCombinations());
		String testMachineText = builder.generateTestMachine();
		
		assertEquals(INCLUDEMACHINEC_TESTMACHINE, testMachineText);
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
	public void shouldGenerateTestMachineForOperationsOnMachinesWithSees2() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/TestSEESMachineB.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(new Partitioner(operationUnderTest));
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();
		Criteria<Block> criteria = new EachChoice<Block>(blocks);
		
		TestMachineBuilder builder = new TestMachineBuilder(operationUnderTest, criteria.getCombinations());
		String testMachineText = builder.generateTestMachine();

		assertEquals(TESTSEESMACHINEB_TESTMACHINE, testMachineText);
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
	public void shouldGenerateTestMachineForOperationsOnMachinesWithUses2() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/Marriage/Marriage.mch");
		Operation operationUnderTest = machine.getOperation(1); // "part(mm, ff)" operation
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(new Partitioner(operationUnderTest));
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();
		Criteria<Block> criteria = new EachChoice<Block>(blocks);
		
		TestMachineBuilder builder = new TestMachineBuilder(operationUnderTest, criteria.getCombinations());
		String testMachineText = builder.generateTestMachine();

		assertEquals(USESMACHINEB_TESTMACHINE, testMachineText);
	}
	
	
	
	@Test
	public void shouldGenerateTestMachineForOperationOnMachinesWithUses3() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/TestUsesMachineB.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		ECBlockBuilder blockBuilder = new ECBlockBuilder(new Partitioner(operationUnderTest));
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();
		Criteria<Block> criteria = new EachChoice<Block>(blocks);
		
		TestMachineBuilder builder = new TestMachineBuilder(operationUnderTest, criteria.getCombinations());
		String testMachineText = builder.generateTestMachine();

		assertEquals(USESMACHINEB_TESTMACHINE, testMachineText);
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
	
	
	
	@Test
	public void shouldGenerateTestMachineForOperationsOnMachinesWithExtendsAndTransitivity() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/TestExtendsMachineC.mch");
		Operation operationUnderTest = machine.getOperation(0);
			
		ECBlockBuilder blockBuilder = new ECBlockBuilder(new Partitioner(operationUnderTest));
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();
		Criteria<Block> criteria = new EachChoice<Block>(blocks);
		
		TestMachineBuilder builder = new TestMachineBuilder(operationUnderTest, criteria.getCombinations());
		String testMachineText = builder.generateTestMachine();
		
		assertEquals(EXTENDSMACHINEC_TESTMACHINE, testMachineText);
	}
	
	
	
	private Machine getMachineInstance(String path) {
		Machine machine = new Machine(new File(path));
		return machine;
	}
	
	
	
	private static final String SIMPLEDEATHEXTENDS_TESTMACHINE =
			"MACHINE TestsForOp_newdeath_From_SimpleDeathExtends" + "\n" +
			"\n" +
			"EXTENDS SimpleLife" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for newdeath */" + "\n" +
			"newdeath_test1(" + "\n" +
			"i__dead," + "\n" +
			"i__pp," + "\n" +
			"i__born" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"not(i__pp : i__born) & i__born <: PERSON & i__dead <: PERSON /* NEGATIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			";" + "\n" +
			"/* Equivalence Class test data for newdeath */" + "\n" +
			"newdeath_test2("  + "\n" +
			"i__dead," + "\n" +
			"i__pp," + "\n" +
			"i__born" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"i__pp : i__born & i__born <: PERSON & i__dead <: PERSON /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";
	
	
	
	private static final String SIMPLEDEATH_TESTMACHINE =
			"MACHINE TestsForOp_newdeath_From_SimpleDeath" + "\n" +
			"\n" +
			"USES SimpleLife" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for newdeath */" + "\n" +
			"newdeath_test1(" + "\n" +
			"i__dead," + "\n" +
			"i__pp," + "\n" +
			"i__born" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"not(i__pp : i__born) & i__born <: PERSON & i__dead <: PERSON /* NEGATIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			";" + "\n" +
			"/* Equivalence Class test data for newdeath */" + "\n" +
			"newdeath_test2("  + "\n" +
			"i__dead," + "\n" +
			"i__pp," + "\n" +
			"i__born" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"i__pp : i__born & i__born <: PERSON & i__dead <: PERSON /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";

	
	
	private static final String SIMPLECOSTUMER_BUY_TESTMACHINE =
			"MACHINE TestsForOp_buy_From_SimpleCostumer" + "\n" +
			"\n" +
			"SEES SimplePrice, SimpleGoods" + "\n" +
			"\n" +
			"CONSTANTS limit" + "\n\n" +
			"PROPERTIES" + "\n" +
			"limit : (GOODS --> NAT1)" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for buy */" + "\n" +
			"buy_test1(" + "\n" +
			"i__purchases," + "\n" +
			"i__price," + "\n" +
			"i__gg" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"not(i__price(i__gg) <= limit(i__gg)) & i__purchases <: GOODS & i__price : (GOODS --> NAT1) /* NEGATIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			";" + "\n" +
			"/* Equivalence Class test data for buy */" + "\n" +
			"buy_test2(" + "\n" +
			"i__purchases," + "\n" +
			"i__price," + "\n" +
			"i__gg" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"i__price(i__gg) <= limit(i__gg) & i__purchases <: GOODS & i__price : (GOODS --> NAT1) /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";

	
	
	private static final String SIMPLELOCKS_OPENDOOR_TESTMACHINE =
			"MACHINE TestsForOp_opendoor_From_Locks" + "\n" +
			"\n" +
			"INCLUDES SimpleDoors" + "\n" +
			"\n" +
			"SETS" + "\n" +
			"STATUS = {locked, unlocked}" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for opendoor */" + "\n" +
			"opendoor_test1(" + "\n" +
			"i__dd," + "\n" +
			"i__status" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"i__status : (DOOR --> STATUS) & not(i__status(i__dd) = unlocked) /* NEGATIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			";" + "\n" +
			"/* Equivalence Class test data for opendoor */" + "\n" +
			"opendoor_test2(" + "\n" +
			"i__dd," + "\n" +
			"i__status" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"i__status : (DOOR --> STATUS) & i__status(i__dd) = unlocked /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";
			

	
	private static final String TESTMACHINE_FOR_TEST = 
			"MACHINE TestsForOp_set_cab_consol_open_button_pressed_From_Test\n\n" +
				
			"OPERATIONS\n" +
			"/* Equivalence Class test data for set_cab_consol_open_button_pressed */\n" +
			"set_cab_consol_open_button_pressed_test1(\n" +
			"i__test_variable,\n" +
			"i__cab_consol_open_button_pushed,\n" +
			"i__cab_side_close_button_pushed,\n" +
			"i__test_variable3,\n" +
			"i__test_variable2,\n" +
			"i__foo,\n" +
			"i__cab_side_open_button_pushed,\n" +
			"i__pressed,\n" +
			"i__cab_consol_close_button_pushed\n" +
			") =\n" +
			"PRE\n" +
			"i__test_variable2 : BOOL & i__test_variable3 : BOOL & i__test_variable : BOOL & i__pressed : BOOL & i__foo : BOOL & i__cab_consol_close_button_pushed & i__cab_side_open_button_pushed & i__cab_consol_open_button_pushed & i__cab_side_close_button_pushed /* POSITIVE */\n" +
			"THEN\n" +
			"skip\n" +
			"END\n" +
			"END";
	
	
	public static final String INCLUDEMACHINEC_TESTMACHINE = 
			"MACHINE TestsForOp_opC_From_TestIncludeMachineC" + "\n" +
			"\n" +
			"INCLUDES TestIncludeMachineB" + "\n" +
			"\n" +
			"SETS" + "\n" +
			"SETC = {ee, ff}" + "\n" +
			"\n" +
			"CONSTANTS constantC" + "\n" +
			"\n" +
			"PROPERTIES" + "\n" +
			"constantC : SETC" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for opC */" + "\n" +
			"opC_test1(" + "\n" +
			"i__yy" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"i__yy : SETC /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";
	
	public static final String EXTENDSMACHINEC_TESTMACHINE = 
			"MACHINE TestsForOp_opC_From_TestExtendsMachineC" + "\n" +
			"\n" +
			"EXTENDS TestExtendsMachineB" + "\n" +
			"\n" +
			"SETS" + "\n" +
			"SETC = {ee, ff}" + "\n" +
			"\n" +
			"CONSTANTS constantC" + "\n" +
			"\n" +
			"PROPERTIES" + "\n" +
			"constantC : SETC" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for opC */" + "\n" +
			"opC_test1(" + "\n" +
			"i__yy" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"i__yy : SETC /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";
	
	
	public static final String TESTSEESMACHINEB_TESTMACHINE = 
			"MACHINE TestsForOp_opB_From_TestSEESMachineB" + "\n" +
			"\n" +
			"SEES TestSEESMachineA" + "\n" +
			"\n" +
			"SETS" + "\n" +
			"SETB = {cc, dd}" + "\n" +
			"\n" +
			"CONSTANTS constantB" + "\n" +
			"\n" +
			"PROPERTIES" + "\n" +
			"constantB : SETB" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for opB */" + "\n" +
			"opB_test1(" + "\n" +
			"i__yy" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"i__yy : SETB /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";
	
	public static final String USESMACHINEB_TESTMACHINE = 
			"MACHINE TestsForOp_opB_From_TestUsesMachineB" + "\n" +
			"\n" +
			"USES TestUsesMachineA" + "\n" +
			"\n" +
			"SETS" + "\n" +
			"SETB = {cc, dd}" + "\n" +
			"\n" +
			"CONSTANTS constantB" + "\n" +
			"\n" +
			"PROPERTIES" + "\n" +
			"constantB : SETB" + "\n" +
			"\n" +
			"OPERATIONS" + "\n" +
			"/* Equivalence Class test data for opB */" + "\n" +
			"opB_test1(" + "\n" +
			"i__yy" + "\n" +
			") =" + "\n" +
			"PRE" + "\n" +
			"i__yy : SETB /* POSITIVE */" + "\n" +
			"THEN" + "\n" +
			"skip" + "\n" +
			"END" + "\n" +
			"END";
}
