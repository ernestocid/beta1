package unit;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import configurations.Configurations;
import parser.Machine;
import parser.Operation;
import testgeneration.preamblecalculation.Event;
import testgeneration.preamblecalculation.PreambleCalculation;
import static com.google.common.truth.Truth.*;

public class PreambleCalculationTest {

	@Test
	public void shouldFindPreamble_returnsFourEventsPreamble() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/ClassroomWithoutDeferredSets.mch"));
		Operation operationUnderTest = machine.getOperation(3);
		String stateGoal = "students = {st4} & grades = {(st4|->4)} & has_taken_lab_classes = {(st4|->TRUE)}";
		PreambleCalculation preambleCalculation = new PreambleCalculation(operationUnderTest, stateGoal);

		// Setting up expected results

		Map<String, String> event1Params = new HashMap<String, String>();
		event1Params.put("students", "{}");
		event1Params.put("grades", "{}");
		event1Params.put("has_taken_lab_classes", "{}");

		Map<String, String> event2Params = new HashMap<String, String>();
		event2Params.put("student", "st4");

		Map<String, String> event3Params = new HashMap<String, String>();
		event3Params.put("student", "st4");
		event3Params.put("grade", "4");

		Map<String, String> event4Params = new HashMap<String, String>();
		event4Params.put("student", "st4");
		event4Params.put("present", "TRUE");

		// Getting actual result

		List<Event> preamble = preambleCalculation.getPathToState();

		// Assertions

		assertThat(preamble).hasSize(4);

		assertThat(preamble.get(0).getEventName()).isEqualTo("initialisation");
		assertThat(preamble.get(0).getEventParameters()).isEqualTo(event1Params);

		assertThat(preamble.get(1).getEventName()).isEqualTo("add_student");
		assertThat(preamble.get(1).getEventParameters()).isEqualTo(event2Params);

		assertThat(preamble.get(2).getEventName()).isEqualTo("add_grade");
		assertThat(preamble.get(2).getEventParameters()).isEqualTo(event3Params);

		assertThat(preamble.get(3).getEventName()).isEqualTo("present_on_lab_classes");
		assertThat(preamble.get(3).getEventParameters()).isEqualTo(event4Params);
	}



	@Test
	public void shouldFindPreamble_returnsOnlyInitialisationPreamble() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/atm.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		String stateGoal = "account_id = 1 & account_balance = 0";
		PreambleCalculation preambleCalculation = new PreambleCalculation(operationUnderTest, stateGoal);

		// Setting up expected results

		Map<String, String> expectedInitialisationParameters = new HashMap<String, String>();
		expectedInitialisationParameters.put("account_id", "1");
		expectedInitialisationParameters.put("account_balance", "0");

		// Getting actual result

		List<Event> preamble = preambleCalculation.getPathToState();

		// Assertions

		assertThat(preamble).hasSize(1);
		assertThat(preamble.get(0).getEventName()).isEqualTo("initialisation");
		assertThat(preamble.get(0).getEventParameters()).isEqualTo(expectedInitialisationParameters);
	}



	@Test
	public void shouldFindPreamble_cannotFindPreambleForGoal() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/atm.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		String stateGoal = "0 > 1";
		PreambleCalculation preambleCalculation = new PreambleCalculation(operationUnderTest, stateGoal);

		// Getting actual result

		List<Event> preamble = preambleCalculation.getPathToState();

		// Assertions

		assertThat(preamble).hasSize(0);
	}



	@Test
	public void shouldFindPreamble_returnsLargePreamble() {
		Configurations.setCBCDepth(40);

		Machine machine = new Machine(new File("src/test/resources/machines/others/IncOrDec.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		String stateGoal = "count = 40";
		PreambleCalculation preambleCalculation = new PreambleCalculation(operationUnderTest, stateGoal);

		// Getting actual result

		List<Event> preamble = preambleCalculation.getPathToState();

		Configurations.setCBCDepth(5);

		// Assertions

		assertThat(preamble).hasSize(40);
	}
	
	
	
	@Test
	public void shouldNotFindPreambleForMachineWithNoState_returnsAnEmptyPreamble() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/NoState.mch"));
				
		Operation operationUnderTest = machine.getOperation(0); // Set(xx, yy)
		String stateGoal = "xx > 0";
		PreambleCalculation preambleCalculation = new PreambleCalculation(operationUnderTest, stateGoal);

		// Getting actual result

		List<Event> preamble = preambleCalculation.getPathToState();

		// Assertions
		
		assertThat(preamble).isEmpty();
	}
	
	
	
	@Test
	public void shouldFindPreamblesForOperationsWithReturnVariables_returnsFiveEventsPreamble() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/PlayerWithEnumeratedSets.mch"));
				
		Operation operationUnderTest = machine.getOperation(2); // aa <-- query(pp)
		String stateGoal = "team = {p1,p2,p3}";
		PreambleCalculation preambleCalculation = new PreambleCalculation(operationUnderTest, stateGoal);

		// Getting actual result

		List<Event> preamble = preambleCalculation.getPathToState();
		
		// Assertions
				
		assertThat(preamble).hasSize(5);;
	}
	
	
	
	@Test
	public void shouldFindPreamblesForOperationsFromModularSpecifications_returnsFiveEventsPreamble() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/LifeMarriage/Marriage.mch"));
		
		Operation operationUnderTest = machine.getOperation(1); // part(mm, ff)
		String stateGoal = "male = {p1} & female = {p2} & marriage = {(p1|->p2)}";
		PreambleCalculation preambleCalculation = new PreambleCalculation(operationUnderTest, stateGoal);

		// Setting up expected results

		Map<String, String> event1Params = new HashMap<String, String>();
		event1Params.put("female", "{}");
		event1Params.put("male", "{}");
		event1Params.put("marriage", "{}");

		Map<String, String> event2Params = new HashMap<String, String>();
		event2Params.put("nn", "p1");
		event2Params.put("ss", "boy");

		Map<String, String> event3Params = new HashMap<String, String>();
		event3Params.put("nn", "p2");
		event3Params.put("ss", "girl");

		Map<String, String> event4Params = new HashMap<String, String>();
		event4Params.put("mm", "p1");
		event4Params.put("ff", "p2");

		// Getting actual result

		List<Event> preamble = preambleCalculation.getPathToState();

		// Assertions

		assertThat(preamble).hasSize(4);

		assertThat(preamble.get(0).getEventName()).isEqualTo("initialisation");
		assertThat(preamble.get(0).getEventParameters()).isEqualTo(event1Params);

		assertThat(preamble.get(1).getEventName()).isEqualTo("born");
		assertThat(preamble.get(1).getEventParameters()).isEqualTo(event2Params);

		assertThat(preamble.get(2).getEventName()).isEqualTo("born");
		assertThat(preamble.get(2).getEventParameters()).isEqualTo(event3Params);

		assertThat(preamble.get(3).getEventName()).isEqualTo("wed");
		assertThat(preamble.get(3).getEventParameters()).isEqualTo(event4Params);
	}
}
