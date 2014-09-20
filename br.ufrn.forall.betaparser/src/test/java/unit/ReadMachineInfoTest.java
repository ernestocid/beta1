package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;


public class ReadMachineInfoTest {

	
	private Machine machine;
	
	
	@Before
	public void setUp() {
		this.machine = getMachineInstance("src/test/resources/machines/others/Test.mch");
	}
	
	
	
	@Test
	public void shouldParseMachine() {
		assertEquals("Test", machine.getName());
	}
	
	
	
	@Test
	public void shouldGetOperations() {
		assertEquals(4, machine.getOperations().size());
	}
	
	
	
	@Test
	public void shouldGetOperationByIndex() {
		assertEquals("set_cab_consol_open_button_pressed", machine.getOperation(0).getName());
	}
	
	
	
	@Test
	public void shouldGetExtendedOperations() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Registrar.mch");
		
		List<Operation> operations = machine.getOperations();
		
		List<String> opNames = new ArrayList<String>();
		
		for (Operation op : operations) {
			opNames.add(op.getName());
		}
		
		
		List<String> expectedOpNames = new ArrayList<String>();
		expectedOpNames.add("dies");
		expectedOpNames.add("born");
		expectedOpNames.add("wed");
		expectedOpNames.add("part");
		expectedOpNames.add("partner");
		
		assertEquals(expectedOpNames, opNames);
	}
	
	
	
	@Test
	public void shouldGetPromotedOperations() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Locks.mch");
		Operation operationUnderTest = machine.getOperation(3);
		
		assertEquals("closedoor", operationUnderTest.getName());
	}
	

	
	
	@Test
	public void shouldGetMachineVariables() {
		Machine machine = getMachineInstance("src/test/resources/machines/SimpleFreeRTOS/SimpleQueue.mch");
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("queues");
		expectedVariables.add("queues_msg");
		expectedVariables.add("queues_msg_full");
		expectedVariables.add("queues_msg_empty");
		expectedVariables.add("queue_items");
		expectedVariables.add("queue_receiving");
		expectedVariables.add("queue_sending");
		expectedVariables.add("first_sending");
		expectedVariables.add("first_receiving");
		
		assertEquals(expectedVariables, machine.getVariables().getAll());
	}
	
	
	
	@Test
	public void shouldGetVariablesText() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Paperround.mch");
		String expectedVaribles = "VARIABLES" + "\n" +
									"magazines," + "\n" +
									"papers" + "\n";		
	    
		assertEquals(expectedVaribles, machine.getVariables().toString());
	}
	
	
	
	@Test
	public void shouldGetInvariant() {
		String expectedInvariant = 
			"cab_consol_open_button_pushed : BOOL & " +
			"cab_side_open_button_pushed : BOOL & " +
			"cab_consol_close_button_pushed : BOOL & " +
			"cab_side_close_button_pushed : BOOL & " +
			"foo : BOOL & " +
			"test_variable <: NAT1 & " +
			"test_variable2 <: NAT1 & " +
			"test_variable3 <: NAT1 & " +
			"((test_variable2 = test_variable) => (test_variable3 /= test_variable)) & " +
			"((test_variable3 = test_variable) => (foo = TRUE))" +
			"\n";
		
		assertEquals(expectedInvariant, machine.getInvariant().toString());
	}
	
	
	
	@Test
	public void shouldGetInvariantClauses() {
		List<String> expectedList = new ArrayList<String>();
		
		expectedList.add("cab_consol_open_button_pushed : BOOL");
		expectedList.add("cab_side_open_button_pushed : BOOL");
		expectedList.add("cab_consol_close_button_pushed : BOOL");
		expectedList.add("cab_side_close_button_pushed : BOOL");
		expectedList.add("foo : BOOL");
		expectedList.add("test_variable <: NAT1");
		expectedList.add("test_variable2 <: NAT1");
		expectedList.add("test_variable3 <: NAT1");
		expectedList.add("((test_variable2 = test_variable) => (test_variable3 /= test_variable))");
		expectedList.add("((test_variable3 = test_variable) => (foo = TRUE))");
		Collections.sort(expectedList);
		
		assertEquals(expectedList, this.machine.getInvariant().getClauses());
	}
	
	
	
	@Test
	public void shouldGetConstants() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Costumer.mch");
		
		Set<String> expectedConstants = new HashSet<String>();
		expectedConstants.add("limit");
		
		assertEquals(expectedConstants, machine.getConstants().getAll());
	}
	
	
	
	@Test
	public void shouldGetConstantsText() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Costumer.mch");
		assertEquals("CONSTANTS limit\n", machine.getConstants().toString());
	}
	
	
	
	@Test
	public void shouldGetAbstractConstants() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/TicTacToe.mch");
		
		Set<String> expectedAbstractConstats = new HashSet<String>();
		expectedAbstractConstats.add("ThreeInRow");
		expectedAbstractConstats.add("WinnerRows");
		
		assertEquals(expectedAbstractConstats, machine.getAbstractConstants().getAll());
	}
	
	
	
	@Test
	public void shouldGetAllCConstants() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/TicTacToe.mch");
		
		Set<String> expectedAbstractConstats = new HashSet<String>();
		expectedAbstractConstats.add("ThreeInRow");
		expectedAbstractConstats.add("WinnerRows");
		
		assertEquals(expectedAbstractConstats, machine.getAllConstants());
	}
	
	
	
	@Test
	public void shouldGetProperties() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Costumer.mch");
		
		Set<String> expectedProperties = new HashSet<String>();
		expectedProperties.add("limit : (GOODS --> NAT1)");
		
		assertEquals(expectedProperties, machine.getProperties().getPropertiesClausesList());
	}
	
	
	
	@Test
	public void shouldGetPropertiesText() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Player.mch");
		
		String expectedProperties = "PROPERTIES" + "\n" +
									"card(PLAYER) > 11" + "\n" +
									"\n";
		
		assertEquals(expectedProperties, machine.getProperties().toString());
	}
	
	
	
	@Test
	public void shouldGetEnumeratedSets() {
		Machine machine = getMachineInstance("src/test/resources/machines/CGP/CGP.mch");
		
		List<String> expectedSets = new ArrayList<String>();
		expectedSets.add("OPERATION_MODE");
		expectedSets.add("DOOR_STATE");
		expectedSets.add("EMG_SWITCH_STATE");
		expectedSets.add("SIDE");

		assertEquals(expectedSets, machine.getSets().getEnumeratedSets());
	}
	
	
	
	@Test
	public void shouldGetEnumeratedSetElements() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/Transport.mch");
		
		List<String> expectedCardTypesSet = new ArrayList<String>();
		expectedCardTypesSet.add("entire_card");
		expectedCardTypesSet.add("student_card");
		expectedCardTypesSet.add("gratutious_card");
		
		assertEquals(expectedCardTypesSet, machine.getSets().getSetElements("CARD_TYPES"));
	}
	
	
	
	@Test
	public void shouldGetDeferredSets() {
		Machine machine = getMachineInstance("src/test/resources/machines/CGP/CGP.mch");
		
		List<String> expectedSets = new ArrayList<String>();
		expectedSets.add("DOORS");
		expectedSets.add("MECHANIC_EMG_SWITCHES");
		expectedSets.add("DOOR_EMG_SWITCHES");
		
		assertEquals(expectedSets, machine.getSets().getDeferredSets());
	}
	
	
	
	@Test
	public void shouldGetSetsText() {
		Machine machine = getMachineInstance("src/test/resources/machines/CGP/CGP.mch");
		
		String expectedSetsClause = "SETS" + "\n" +
				"OPERATION_MODE = {MAN, MCS, ATO, NO_MODE};" + "\n" +
				"DOOR_STATE = {CLOSED_AND_LOCKED, NOT_CLOSED_AND_LOCKED};" + "\n" +
				"EMG_SWITCH_STATE = {ACTUATED, NOT_ACTUATED};" + "\n" +
				"SIDE = {LEFT, RIGHT, NO_SIDE};" + "\n" +
				"DOORS;" + "\n" +
				"MECHANIC_EMG_SWITCHES;" + "\n" +
				"DOOR_EMG_SWITCHES\n" +
				"\n";
    
		assertEquals(expectedSetsClause, machine.getSets().toString());
	}
	
	
	
	@Test
	public void shouldGetInitialisationText() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Player.mch");
		
		String expectedInitialisation = "INITIALISATION" + "\n" +
										"ANY tt" + "\n" +
										"WHERE tt <: PLAYER & card(tt) = 11" + "\n" +
										"THEN team := tt" + "\n" +
										"END\n\n";
		
		assertEquals(expectedInitialisation, machine.getInitialisation().toString());
	}
	
	
	
	@Test
	public void shouldGetUsedMachines() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Marriage.mch");
		
		List<String> expected = new ArrayList<String>();
		expected.add("Life");
		
		assertEquals(expected, machine.getUses().getMachinesUsedNames());
	}
	
	
	
	@Test
	public void shouldGetUsesText() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Marriage.mch");
		
		assertEquals("USES Life\n", machine.getUses().toString());		
	}
	
	
	
	@Test
	public void shouldGetSeenMachines() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Costumer.mch");
		
		List<String> expectedSeenMachines = new ArrayList<String>();
		expectedSeenMachines.add("Price");
		expectedSeenMachines.add("Goods");		
		
		assertEquals(expectedSeenMachines, machine.getSees().getMachinesSeenNames());
	}
	
	
	
	@Test
	public void shoudGetSeesText() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Costumer.mch");
		
		assertEquals("SEES Price, Goods\n", machine.getSees().toString());
	}
	
	
	
	@Test
	public void shouldGetIncludedMachines() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Locks.mch");
	
		List<String> expected = new ArrayList<String>();
		expected.add("Doors");
		
		assertEquals(expected, machine.getIncludes().getMachinesIncludedNames());
	}
	
	
	
	@Test
	public void shouldGetIncludesText() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Locks.mch");
		
		assertEquals("INCLUDES Doors\n", machine.getIncludes().toString());
	}
	
	
	
	@Test
	public void shouldGetExtendsMachines() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Registrar.mch");
		
		List<String> expectedMachineNames = new ArrayList<String>();
		expectedMachineNames.add("Marriage");
		
		assertEquals(expectedMachineNames, machine.getExtends().getMachinesExtendedNames());
	}
	
	
	
	@Test
	public void shouldGetExtendsText() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Registrar.mch");
		
		assertEquals("EXTENDS Marriage\n", machine.getExtends().toString());		
	}
	

	
	@Test
	public void shouldGetFile() {
		assertEquals("Test.mch", this.machine.getFile().getName());
	}
	
	
	
	@Test
	public void shouldGetPropertiesClauses() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Costumer.mch");
		
		Set<String> expectedPropertiesClauses = new HashSet<String>();
		expectedPropertiesClauses.add("limit : (GOODS --> NAT1)");
		
		Set<String> actualPropertiesClauses = new HashSet<String>();
		for (MyPredicate propertieClause : machine.getPropertiesClauses()) {
			actualPropertiesClauses.add(propertieClause.toString());
		}
		
		assertEquals(expectedPropertiesClauses, actualPropertiesClauses);
	}
	

	
	@Test
	public void shouldGetCondensedInvariant() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Registrar.mch");
		
		Set<String> expectedCondensedInvariant = new HashSet<String>();
		expectedCondensedInvariant.add("marriage : (male >+> female)");
		expectedCondensedInvariant.add("male <: PERSON");
		expectedCondensedInvariant.add("female <: PERSON");
		expectedCondensedInvariant.add("male /\\ female = {}");
		
		Set<String> actualCondensedInvariant = new HashSet<String>();
		for (MyPredicate clause : machine.getCondensedInvariantFromAllMachines()) {
			actualCondensedInvariant.add(clause.toString());
		}
		
		assertEquals(expectedCondensedInvariant, actualCondensedInvariant);
	}
	
	
	
	@Test
	public void shouldGetCondensedInvariant2() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Shop.mch");
		
		Set<String> expectedCondensedInvariant = new HashSet<String>();
		expectedCondensedInvariant.add("takings : NAT");
		expectedCondensedInvariant.add("price : (GOODS --> NAT1)");
		
		Set<String> actualCondensedInvariant = new HashSet<String>();
		for (MyPredicate clause : machine.getCondensedInvariantFromAllMachines()) {
			actualCondensedInvariant.add(clause.toString());
		}
		
		assertEquals(expectedCondensedInvariant, actualCondensedInvariant);
	}
	
	
	
	@Test
	public void shouldNotParseMachineWithSpecificationsErros() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/ErrorTest.mch");
		assertFalse(machine.isParsed());
	}
	
	
	
	@Test
	public void shouldGetDEFINTIONS() {
		Machine machine = getMachineInstance("src/test/resources/machines/LuaSpec/Pseudo_Stack.mch");
		List<String> definitions = machine.getDefinitions().getDefinitionClauses();
		
		List<String> expectedDefinitions = new ArrayList<String>();
		
		expectedDefinitions.add("stack_indexes == -stack_top..-1 \\/ 1..stack_top");
		expectedDefinitions.add("pseudo_indexes == pseudo_bottom..MAXINT");
		expectedDefinitions.add("valid_indexes == stack_indexes \\/ pseudo_indexes");
		expectedDefinitions.add("acceptable_indexes == valid_indexes \\/ ((stack_top + 1))..max_stack_top");
		
		assertEquals(expectedDefinitions, definitions);
	}
	
	
	
	private Machine getMachineInstance(String path) {
		Machine machine = new Machine(new File(path));
		return machine;
	}
}
