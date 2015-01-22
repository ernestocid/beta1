package unit;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import testgeneration.preamblecalculation.Event;
import testgeneration.preamblecalculation.PreambleCalculation;
import static com.google.common.truth.Truth.*;

public class PreambleCalculationTest {

	@Test
	public void shouldFindPreambleForTest() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Classroom.mch"));
		Operation operationUnderTest = machine.getOperation(3);
		String stateGoal = "grades(student) > 2 & grades(student) > 3 & has_taken_lab_classes(student) = TRUE & student : dom(grades) & student : dom(has_taken_lab_classes) & student : students";
		PreambleCalculation preambleCalculation = new PreambleCalculation(operationUnderTest, stateGoal);

		// Setting up expected results

		Map<String, String> event1Params = new HashMap<String, String>();
		event1Params.put("students", "{}");
		event1Params.put("grades", "{}");
		event1Params.put("has_taken_lab_classes", "{}");
		event1Params.put("finished", "FALSE");

		Map<String, String> event2Params = new HashMap<String, String>();
		event2Params.put("student", "STUDENT1");

		Map<String, String> event3Params = new HashMap<String, String>();
		event3Params.put("student", "STUDENT1");
		event3Params.put("grade", "4");

		Map<String, String> event4Params = new HashMap<String, String>();
		event4Params.put("student", "STUDENT1");
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

}
