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
import parser.decorators.predicates.MyPredicate;
import static org.mockito.Mockito.*;

public class ReadOperationInfoTest extends TestingUtils {

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
	public void shouldGetConditionsFromCaseCommandWithEitherExpression() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/CASEMachine.mch");
		Operation operationUnderTest = machine.getOperation(0);

		Set<String> expectedConditions = new HashSet<String>();
		expectedConditions.add("var2 = 0");

		Set<String> ifConditions = parseSetOfCharacteristicsToSetOfStrings(operationUnderTest.getConditionalCharacteristics());

		assertEquals(expectedConditions, ifConditions);
	}
	
	
	
	@Test
	public void shouldGetConditionsFromCaseCommandWithEitherAndOrExpressions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/Calendar.mch");
		Operation operationUnderTest = machine.getOperation(0);

		Set<String> expectedConditions = new HashSet<String>();
		expectedConditions.add("nn = 1");
		expectedConditions.add("nn = 2");
		expectedConditions.add("nn = 3");
		expectedConditions.add("nn = 4");
		expectedConditions.add("nn = 5");
		expectedConditions.add("nn = 6");
		expectedConditions.add("nn = 7");
		expectedConditions.add("nn = 8");
		expectedConditions.add("nn = 9");
		expectedConditions.add("nn = 10");
		expectedConditions.add("nn = 11");
		expectedConditions.add("nn = 12");

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



	@Test
	public void shouldGetGuardsThatLeadToPredicate() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/NestedSubstitutions.mch");
		Operation operationUnderTest = machine.getOperation(0);

		// Setting up test input

		Set<String> clauseVariables = new HashSet<String>();
		clauseVariables.add("xx");

		MyPredicate mockedClause = mock(MyPredicate.class);
		when(mockedClause.toString()).thenReturn("xx = 2");
		when(mockedClause.getVariables()).thenReturn(clauseVariables);

		// Setting up expected results

		MyPredicate mockedExpectedClause = mock(MyPredicate.class);
		when(mockedExpectedClause.toString()).thenReturn("xx /= yy");

		Set<MyPredicate> expectedRelatedClauses = new HashSet<MyPredicate>();
		expectedRelatedClauses.add(mockedExpectedClause);

		// Assertions

		assertTrue(compare(expectedRelatedClauses, operationUnderTest.getGuardsThatLeadToPredicate(mockedClause)));
	}



	@Test
	public void shouldGetGuardsThatLeadToPredicate2() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/NestedSubstitutions.mch");
		Operation operationUnderTest = machine.getOperation(0);

		// Setting up test input

		Set<String> clauseVariables = new HashSet<String>();
		clauseVariables.add("yy");

		MyPredicate mockedClause = mock(MyPredicate.class);
		when(mockedClause.toString()).thenReturn("yy = 2");
		when(mockedClause.getVariables()).thenReturn(clauseVariables);

		// Setting up expected results

		MyPredicate mockedExpectedClause = mock(MyPredicate.class);
		when(mockedExpectedClause.toString()).thenReturn("xx /= yy");

		Set<MyPredicate> expectedRelatedClauses = new HashSet<MyPredicate>();
		expectedRelatedClauses.add(mockedExpectedClause);

		// Assertions

		assertTrue(compare(expectedRelatedClauses, operationUnderTest.getGuardsThatLeadToPredicate(mockedClause)));
	}



	@Test
	public void shouldGetGuardsThatLeadToPredicate3() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/NestedSubstitutions.mch");
		Operation operationUnderTest = machine.getOperation(0);

		// Setting up test input

		Set<String> clauseVariables = new HashSet<String>();
		clauseVariables.add("yy");

		MyPredicate mockedClause = mock(MyPredicate.class);
		when(mockedClause.toString()).thenReturn("yy = 1");
		when(mockedClause.getVariables()).thenReturn(clauseVariables);

		// Setting up expected results

		MyPredicate mockedExpectedClause1 = mock(MyPredicate.class);
		when(mockedExpectedClause1.toString()).thenReturn("xx /= yy");

		MyPredicate mockedExpectedClause2 = mock(MyPredicate.class);
		when(mockedExpectedClause2.toString()).thenReturn("xx = 2");

		Set<MyPredicate> expectedRelatedClauses = new HashSet<MyPredicate>();
		expectedRelatedClauses.add(mockedExpectedClause1);
		expectedRelatedClauses.add(mockedExpectedClause2);

		// Assertions

		assertTrue(compare(expectedRelatedClauses, operationUnderTest.getGuardsThatLeadToPredicate(mockedClause)));
	}



	@Test
	public void shouldGetGuardsThatLeadToPredicate4() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/CarlaNewTravelAgency.mch");
		Operation operationUnderTest = machine.getOperation(7);

		// Setting up test inputs

		Set<String> clauseVariables = new HashSet<String>();
		clauseVariables.add("hh");

		MyPredicate mockedClause = mock(MyPredicate.class);
		when(mockedClause.toString()).thenReturn("hh : HOTEL");
		when(mockedClause.getVariables()).thenReturn(clauseVariables);

		// Setting up expected results

		MyPredicate mockedExpectedClause1 = mock(MyPredicate.class);
		when(mockedExpectedClause1.toString()).thenReturn("dom(global_room_bookings) <<: ROOM");

		MyPredicate mockedExpectedClause2 = mock(MyPredicate.class);
		when(mockedExpectedClause2.toString()).thenReturn("session_request(sid) = br & user_hotel_bookings(session(sid)) = noHotel");

		Set<MyPredicate> expectedRelatedClauses = new HashSet<MyPredicate>();
		expectedRelatedClauses.add(mockedExpectedClause1);
		expectedRelatedClauses.add(mockedExpectedClause2);

		// Assertions

		assertTrue(compare(expectedRelatedClauses, operationUnderTest.getGuardsThatLeadToPredicate(mockedClause)));
	}



	@Test
	public void shouldGetGuardsThatLeadToPredicate5() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/CarlaNewTravelAgency.mch");
		Operation operationUnderTest = machine.getOperation(7);

		// Setting up test inputs

		Set<String> clauseVariables = new HashSet<String>();
		clauseVariables.add("re");

		MyPredicate mockedClause = mock(MyPredicate.class);
		when(mockedClause.toString()).thenReturn("re : CAR_RENT");
		when(mockedClause.getVariables()).thenReturn(clauseVariables);

		// Setting up expected results

		MyPredicate mockedExpectedClause1 = mock(MyPredicate.class);
		when(mockedExpectedClause1.toString()).thenReturn("session_request(sid) = bc & user_rental_bookings(session(sid)) = noCarRent");

		MyPredicate mockedExpectedClause2 = mock(MyPredicate.class);
		when(mockedExpectedClause2.toString()).thenReturn("dom(global_car_bookings) <<: CAR");

		Set<MyPredicate> expectedRelatedClauses = new HashSet<MyPredicate>();
		expectedRelatedClauses.add(mockedExpectedClause1);
		expectedRelatedClauses.add(mockedExpectedClause2);

		// Assertions

		assertTrue(compare(expectedRelatedClauses, operationUnderTest.getGuardsThatLeadToPredicate(mockedClause)));
	}



	@Test
	public void shouldGetGuardsThatLeadToPredicate6() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/CaseWithNestedSubstitutions.mch");
		Operation operationUnderTest = machine.getOperation(0);

		// Setting up test inputs

		Set<String> clauseVariables = new HashSet<String>();
		clauseVariables.add("aa");
		clauseVariables.add("bb");

		MyPredicate mockedClause = mock(MyPredicate.class);
		when(mockedClause.toString()).thenReturn("bb >= aa");
		when(mockedClause.getVariables()).thenReturn(clauseVariables);

		// Setting up expected results

		MyPredicate mockedExpectedClause1 = mock(MyPredicate.class);
		when(mockedExpectedClause1.toString()).thenReturn("xx = 2");

		Set<MyPredicate> expectedRelatedClauses = new HashSet<MyPredicate>();
		expectedRelatedClauses.add(mockedExpectedClause1);

		// Assertions

		assertTrue(compare(expectedRelatedClauses, operationUnderTest.getGuardsThatLeadToPredicate(mockedClause)));
	}
	
	
	
	@Test
	public void shouldGetPredicatesFromNestedIf() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/NestedIF.mch");
		Operation operationUnderTest = machine.getOperation(0);

		Set<String> expectedPredicates = new HashSet<String>();
		expectedPredicates.add("yy > 0");
		expectedPredicates.add("xx > 1");
		expectedPredicates.add("xx = 2");

		Set<String> actualPredicates = parseListOfPredicatesToSetOfStrings(operationUnderTest.getPredicatesFromOperationBody());
		
		assertEquals(expectedPredicates, actualPredicates);
	}



	private Set<String> parseListOfPredicatesToSetOfStrings(List<MyPredicate> predicatesFromOperationBody) {
		Set<String> actualPredicates = new HashSet<String>();
		
		
		for(MyPredicate p : predicatesFromOperationBody) {
			actualPredicates.add(p.toString());
		}
		return actualPredicates;
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
