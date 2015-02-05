package unit.testgeneration.concretization;

import static com.google.common.truth.Truth.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import parser.Implementation;
import parser.Machine;
import parser.Operation;
import testgeneration.concretization.ConcretizeTestCaseInputs;

public class ConcretizeTestCaseInputsTest {

	@Test
	public void shouldConcretizeTestCaseInputs() {
		String testFormula = "#team, pp, rr.(team <: PLAYER & card(team) = 11 & pp : PLAYER & pp : team & rr /: team & rr : PLAYER)";
		Machine machine = new Machine(new File("/Users/ernestocid/Temp/PlayerRefined/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substitute

		Implementation implementation = new Implementation(new File("/Users/ernestocid/Temp/PlayerRefined/Player_i.imp"));

		ConcretizeTestCaseInputs concretization = new ConcretizeTestCaseInputs(testFormula, operationUnderTest, implementation);

		Map<String, String> expectedConcreteInputValues = new HashMap<String, String>();
		expectedConcreteInputValues.put("team_array", "{(0|->1),(1|->8),(2|->2),(3|->10),(4|->7),(5|->9),(6|->6),(7|->5),(8|->4),(9|->3),(10|->11)}");

		assertThat(concretization.getConcreteInputValues()).isEqualTo(expectedConcreteInputValues);
	}

}
