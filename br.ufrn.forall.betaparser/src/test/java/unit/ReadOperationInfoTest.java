package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import parser.Characteristic;
import parser.Machine;
import parser.Operation;


public class ReadOperationInfoTest {
	
	
	private Operation operation;
	
	
	@Before
	public void setUp() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Test.mch"));
		this.operation = machine.getOperation(0);
	}
	
	
	
	@Test
	public void canGetOperationName() {
		String operationName = this.operation.getName();
		assertEquals("set_cab_consol_open_button_pressed", operationName);
	}
	
	
	
	@Test
	public void canGetOperationParameters() {
		List<String> expectedParameters = new ArrayList<String>();
		expectedParameters.add("pressed");
		
		assertTrue(expectedParameters.equals(operation.getParameters()));
	}
	
	
	
	@Test
	public void canGetOperationPrecondition() {
		String expectedPrecondition = "pressed : BOOL & ((pressed = TRUE) => (foo = FALSE))";
		String actualPrecondition = operation.getPrecondition().toString();
		
		assertEquals(expectedPrecondition, actualPrecondition);
	}
	
	
	
	@Test
	public void shouldGetMachineFromOperation() {
		assertEquals("Test", operation.getMachine().getName());
	}
	

	
	@Test
	public void shouldGetNestedIfCommandVariables() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/NestedIF.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("yy");
		expectedVariables.add("xx");
		
		Set<String> ifVariables = operationUnderTest.getIfCommandVariables();
		
		assertEquals(expectedVariables, ifVariables);
	}
	
	
	// TODO: Fix problem for nested IFs
//	@Test
//	public void shouldGetNestedIfCommandConditions() {
//		Machine machine = getMachineInstance("machines/others/NestedIF.mch");
//		Operation operationUnderTest = machine.getOperation(0);
//		
//		Set<String> expectedConditions = new HashSet<String>();
//		expectedConditions.add("var1 = var2");
//		
//		Set<String> ifConditions = operationUnderTest.getConditionalCharacteristics();
//		
//		assertEquals(expectedConditions, ifConditions);
//	}
	
	
	
	@Test
	public void shouldGetIfCommandVariables() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/IFMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("var1");
		expectedVariables.add("var2");
		
		Set<String> ifVariables = operationUnderTest.getIfCommandVariables();
		
		assertEquals(expectedVariables, ifVariables);
	}
	
	
	
	@Test
	public void shouldGetIfCommandConditions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/IFMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedConditions = new HashSet<String>();
		expectedConditions.add("var1 = var2");
		
		Set<Characteristic> ifConditions = operationUnderTest.getConditionalCharacteristics();
		
		Set<String> characteristics = parseSetOfCharacteristicsToSetOfStrings(ifConditions);
		
		assertEquals(expectedConditions, characteristics);
	}
	


	@Test
	public void shouldGetIfCommandConditionsInsideParallelSubstitution() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Registrar.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedConditions = new HashSet<String>();
		expectedConditions.add("nn : dom(marriage)");
		expectedConditions.add("nn : ran(marriage)");
		
		Set<String> ifConditions = parseSetOfCharacteristicsToSetOfStrings(operationUnderTest.getConditionalCharacteristics());
		
		assertEquals(expectedConditions, ifConditions);
	}

	
	
	@Test
	public void shouldGetIfElsifCommandVariables() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/IFELSEIFMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("var1");
		expectedVariables.add("var2");
		
		Set<String> ifVariables = operationUnderTest.getIfCommandVariables();
		
		assertEquals(expectedVariables, ifVariables);
	}
	
	
	
	@Test
	public void shouldGetIfElsifCommandConditions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/IFELSEIFMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedConditions = new HashSet<String>();
		expectedConditions.add("var1 > var2");
		expectedConditions.add("var1 < var2");
		expectedConditions.add("var1 = var2");
		
		Set<String> ifConditions = parseSetOfCharacteristicsToSetOfStrings(operationUnderTest.getConditionalCharacteristics());
		
		assertEquals(expectedConditions, ifConditions);
	}
	
	
	
	@Test
	public void shouldGetCaseCommandVariables() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/CASEMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("var2");
		
		Set<String> ifVariables = operationUnderTest.getIfCommandVariables();
		
		assertEquals(expectedVariables, ifVariables);
	}
	
	
	
	@Test
	public void shouldGetCaseCommandConditions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/CASEMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedConditions = new HashSet<String>();
		expectedConditions.add("var2 : {0}");
		
		Set<String> ifConditions = parseSetOfCharacteristicsToSetOfStrings(operationUnderTest.getConditionalCharacteristics());
		
		assertEquals(expectedConditions, ifConditions);
	}
	
	
	
	@Test
	public void shouldGetSelectCommandVariables() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SELECTMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("var1");
		expectedVariables.add("var2");
		
		Set<String> ifVariables = operationUnderTest.getIfCommandVariables();
		
		assertEquals(expectedVariables, ifVariables);
	}
	
	
	
	@Test
	public void shouldGetSelectCommandConditions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SELECTMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedConditions = new HashSet<String>();
		expectedConditions.add("var1 > var2");
		expectedConditions.add("var1 < var2");
		expectedConditions.add("var1 = var2");
		
		Set<String> ifConditions = parseSetOfCharacteristicsToSetOfStrings(operationUnderTest.getConditionalCharacteristics());
		
		assertEquals(expectedConditions, ifConditions);
	}
	
	
	
	@Test
	public void shouldGetConditionsCommandFromMachineWithNoPrecondition() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/IFWithNoPrecondition.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Set<String> expectedConditions = new HashSet<String>();
		expectedConditions.add("xx = 1");
		
		Set<String> ifConditions = parseSetOfCharacteristicsToSetOfStrings(operationUnderTest.getConditionalCharacteristics());
		
		assertEquals(expectedConditions, ifConditions);
	}
	
	
	
	@Test
	public void shouldReturnEmptyWhenGettingReturnVariablesForOperationWithoutReturn() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/OperationWithReturnMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);

		List<String> returnVariables = operationUnderTest.getReturnVariables();
		
		assertTrue(returnVariables.isEmpty());
	}
	
	
	
	@Test
	public void shouldReturnListOfReturnVariablesWhenOperationHasOneReturnVariable() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/OperationWithReturnMachine.mch");
		Operation operationUnderTest = machine.getOperation(1);

		List<String> expectedReturnVariables = new ArrayList<String>();
		expectedReturnVariables.add("xx");
		
		List<String> returnVariables = operationUnderTest.getReturnVariables();
		
		assertEquals(expectedReturnVariables, returnVariables);
	}
	
	
	
	@Test
	public void shouldReturnListOfReturnVariablesWhenOperationHasManyReturnVariable() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/OperationWithReturnMachine.mch");
		Operation operationUnderTest = machine.getOperation(2);

		List<String> expectedReturnVariables = new ArrayList<String>();
		expectedReturnVariables.add("aa");
		expectedReturnVariables.add("bb");
		expectedReturnVariables.add("cc");
		
		List<String> returnVariables = operationUnderTest.getReturnVariables();
		
		assertEquals(expectedReturnVariables, returnVariables);
	}
	
	
	
	@Test
	public void shouldGetStateVariablesMentionedOnBody() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SimpleLift.mch");
		Operation operationUnderTest = machine.getOperation(3);
		
		Set<String> stateVariablesOnBody = operationUnderTest.getStateVariablesUsedOnBody();
		Set<String> expectedStateVariables = new HashSet<String>();
		expectedStateVariables.add("floor");
		
		assertEquals(expectedStateVariables, stateVariablesOnBody);
	}
	
	
	
	private Machine getMachineInstance(String path) {
		Machine machine = new Machine(new File(path));
		return machine;
	}
	
	
	
	private Set<String> parseSetOfCharacteristicsToSetOfStrings(Set<Characteristic> characteristics) {
		Set<String> chs = new HashSet<String>();
		
		for (Characteristic characteristic : characteristics) {
			chs.add(characteristic.toString());
		}

		return chs;
	}

}
	