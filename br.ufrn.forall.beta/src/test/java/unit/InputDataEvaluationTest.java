package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import parser.Machine;
import parser.Operation;
import testgeneration.BETATestCase;
import testgeneration.InputDataEvaluation;
import static org.mockito.Mockito.*;

public class InputDataEvaluationTest {

	@Before
	public void setUp() {
		Configurations.setMaxIntProperties(20);
		Configurations.setMinIntProperties(-1);
	}



	@Test
	public void shouldGenerateInputDataForOperationWithParametersAndMachineWithStateAndConstants1() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substittute

		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("card(team) = 11 & pp : team & rr : PLAYER & rr /: team & team <: PLAYER"); // Positive

		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest);

		String ppValue = inputDataEvaluation.getInputParamsValues().get("pp");
		String rrValue = inputDataEvaluation.getInputParamsValues().get("rr");
		String teamValue = inputDataEvaluation.getStateVariablesValues().get("team");

		// Asserting that the team has 11 elements by splitting the String that
		// describes the set.
		assertEquals(11, teamValue.split(",").length);

		// Asserting that the element in ppValues is present on the team String.
		assertTrue(Pattern.compile("\\b" + ppValue + "\\b").matcher(teamValue).find());

		// Asserting that rrValues is a member of the PLAYER set.
		assertTrue(rrValue.contains("PLAYER"));

		// Asserting that rrValue is not a member of the team set.
		assertFalse(Pattern.compile("\\b" + rrValue + "\\b").matcher(teamValue).find());

		// Asserting that the number of parameters and state variables are
		// correct.
		assertEquals(2, inputDataEvaluation.getInputParamsValues().size());
		assertEquals(1, inputDataEvaluation.getStateVariablesValues().size());

	}



	@Test
	public void shouldGenerateInputDataForOperationWithParametersAndMachineWithStateAndConstants2() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substittute

		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("card(team) = 11 & not(pp : team) & rr : PLAYER & rr /: team & team <: PLAYER"); // Negative

		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest);

		String ppValue = inputDataEvaluation.getInputParamsValues().get("pp");
		String rrValue = inputDataEvaluation.getInputParamsValues().get("rr");
		String teamValue = inputDataEvaluation.getStateVariablesValues().get("team");

		// Asserting that card(team) = 11
		assertEquals(11, teamValue.split(",").length);

		// Asserting that not(pp : team)
		assertFalse(Pattern.compile("\\b" + ppValue + "\\b").matcher(teamValue).find());

		// Asserting that rr : PLAYER
		assertTrue(rrValue.contains("PLAYER"));

		// Asserting that rr /: team
		assertFalse(Pattern.compile("\\b" + rrValue + "\\b").matcher(teamValue).find());

		// Asserting that the number of parameters and state variables are
		// correct.
		assertEquals(2, inputDataEvaluation.getInputParamsValues().size());
		assertEquals(1, inputDataEvaluation.getStateVariablesValues().size());
	}



	@Test
	public void shouldGenerateInputDataForOperationWithParametersAndMachineWithStateAndConstants3() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substittute

		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("card(team) = 11 & pp : team & rr : PLAYER & not(rr /: team) & team <: PLAYER"); // Negative

		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest);

		String ppValue = inputDataEvaluation.getInputParamsValues().get("pp");
		String rrValue = inputDataEvaluation.getInputParamsValues().get("rr");
		String teamValue = inputDataEvaluation.getStateVariablesValues().get("team");

		// Asserting that card(team) = 11
		assertEquals(11, teamValue.split(",").length);

		// Asserting that pp : team
		assertTrue(Pattern.compile("\\b" + ppValue + "\\b").matcher(teamValue).find());

		// Asserting that rr : PLAYER
		assertTrue(rrValue.contains("PLAYER"));

		// Asserting that not(rr /: team)
		assertTrue(Pattern.compile("\\b" + rrValue + "\\b").matcher(teamValue).find());

		// Asserting that the number of parameters and state variables are
		// correct.
		assertEquals(2, inputDataEvaluation.getInputParamsValues().size());
		assertEquals(1, inputDataEvaluation.getStateVariablesValues().size());
	}



	@Test
	public void shouldGenerateInputDataForOperationWithParametersAndMachineWithStateAndConstants4() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substittute

		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("card(team) = 11 & not(pp : team) & rr : PLAYER & not(rr /: team) & team <: PLAYER"); // Negative

		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest);

		String ppValue = inputDataEvaluation.getInputParamsValues().get("pp");
		String rrValue = inputDataEvaluation.getInputParamsValues().get("rr");
		String teamValue = inputDataEvaluation.getStateVariablesValues().get("team");

		// Asserting that card(team) = 11
		assertEquals(11, teamValue.split(",").length);

		// Asserting that not(pp : team)
		assertFalse(Pattern.compile("\\b" + ppValue + "\\b").matcher(teamValue).find());

		// Asserting that rr : PLAYER
		assertTrue(rrValue.contains("PLAYER"));

		// Asserting that not(rr /: team)
		assertTrue(Pattern.compile("\\b" + rrValue + "\\b").matcher(teamValue).find());

		// Asserting that the number of parameters and state variables are
		// correct.
		assertEquals(2, inputDataEvaluation.getInputParamsValues().size());
		assertEquals(1, inputDataEvaluation.getStateVariablesValues().size());
	}



	// @Test
	// public void shouldNotGenerateForInfeasibleTestCase() {
	// Machine machine = new Machine(new
	// File("src/test/resources/machines/others/counter.mch"));
	// Operation operationUnderTest = machine.getOperation(1); // inc operation
	//
	// BETATestCase mockedTestCase = mock(BETATestCase.class);
	// when(mockedTestCase.getTestFormula()).thenReturn("not(value <= MAXINT) & value : INT & 0 <= value & overflow : BOOL & ((overflow = TRUE) => (value = MAXINT)) & value < MAXINT");
	//
	// InputDataEvaluation inputDataEvaluation = new
	// InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
	//
	// Map<String, String> expectedInputParams = new HashMap<String, String>();
	//
	// Map<String, String> expectedStateValues = new HashMap<String, String>();
	// expectedStateValues.put("overflow", "FALSE");
	// expectedStateValues.put("value", "0");
	//
	// assertEquals(expectedInputParams,
	// inputDataEvaluation.getInputParamsValues());
	// assertEquals(expectedStateValues,
	// inputDataEvaluation.getStateVariablesValues());
	// }

	@Test
	public void shouldGenerateInputDataForOperationWithoutParametersAndMachineWithState1() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1); // inc operation

		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn(
				"value <= MAXINT & value : INT & 0 <= value & overflow : BOOL & ((overflow = TRUE) => (value = MAXINT)) & not(value < MAXINT)");

		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest);

		Map<String, String> expectedInputParams = new HashMap<String, String>();

		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("overflow", "TRUE");
		expectedStateValues.put("value", "20");

		String overflowValue = inputDataEvaluation.getStateVariablesValues().get("overflow");
		String valueValue = inputDataEvaluation.getStateVariablesValues().get("value");

		assertTrue(expectedInputParams.isEmpty());
		assertEquals("20", valueValue);
		assertTrue(overflowValue.equals("TRUE") || overflowValue.equals("FALSE"));
	}



	@Test
	public void shouldGenerateInputDataForOperationWithoutParametersAndMachineWithState2() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1); // inc operation

		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn(
				"value <= MAXINT & value : INT & 0 <= value & overflow : BOOL & ((overflow = TRUE) => (value = MAXINT)) & value < MAXINT");

		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest);

		Map<String, String> expectedInputParams = new HashMap<String, String>();

		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("overflow", "FALSE");
		expectedStateValues.put("value", "0");

		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}



	@Test
	public void shouldGenerateInputDataForSimpleLiftGoTo() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimpleLift.mch"));
		Operation operationUnderTest = machine.getOperation(2); // go_to(nFloor)

		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("floor : 1..5 & nFloor : 1..5 & nFloor /= floor"); // Positive

		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest);

		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("nFloor", "2");

		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("floor", "1");

		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}



	@Test
	public void shouldGenerateInputDataForPaperroundAddPaper() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Paperround.mch"));
		Operation operationUnderTest = machine.getOperation(0); // addpaper(hh)

		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("card(papers) < 60 & hh : 1..163 & magazines <: papers & card(papers) <= 60 & papers <: 1..163"); // Positive

		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest);

		int hhValue = Integer.parseInt(inputDataEvaluation.getInputParamsValues().get("hh"));
		String papersValue = inputDataEvaluation.getStateVariablesValues().get("papers");
		String magazinesValue = inputDataEvaluation.getStateVariablesValues().get("magazines");

		// Asserting that hh : 1..163
		assertTrue(hhValue >= 1 && hhValue <= 163);

		// Since the generation of sets is not random yet, these will always be
		// empty.
		assertEquals("{}", papersValue);
		assertEquals("{}", magazinesValue);

		// Asserting that the number of parameters and state variables are
		// correct.
		assertEquals(1, inputDataEvaluation.getInputParamsValues().size());
		assertEquals(2, inputDataEvaluation.getStateVariablesValues().size());
	}



	@Test
	public void shouldGenerateInputDataForInc() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Inc.mch"));
		Operation operationUnderTest = machine.getOperation(0); // inc(nn)

		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("value : INT & nn : NAT & (nn + value) <= MAXINT & value < MAXINT"); // Positive

		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest);

		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("nn", "0");

		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("value", "-1");

		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}

}
