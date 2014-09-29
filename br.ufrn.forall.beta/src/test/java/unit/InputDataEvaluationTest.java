package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.prob.Main;
import de.prob.scripting.Api;
import parser.Machine;
import parser.Operation;
import testgeneration.BETATestCase;
import testgeneration.InputDataEvaluation;
import static org.mockito.Mockito.*;

public class InputDataEvaluationTest {

	
	private Api probApi;
	
	
	@Before
	public void setUp() {
		this.probApi = Main.getInjector().getInstance(Api.class);
	}
	
	
	
	@After
	public void tearDown() {
		this.probApi = null;
	}
	
	
	
	@Test
	public void shouldGenerateInputDataForOperationWithParametersAndMachineWithStateAndConstants1() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substittute operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("card(team) = 11 & pp : team & rr : PLAYER & rr /: team & team <: PLAYER"); // Positive test
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("pp", "PLAYER1");
		expectedInputParams.put("rr", "PLAYER12");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("team", "{PLAYER1,PLAYER2,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11}");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
	
	
	@Test
	public void shouldGenerateInputDataForOperationWithParametersAndMachineWithStateAndConstants2() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substittute operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("card(team) = 11 & not(pp : team) & rr : PLAYER & rr /: team & team <: PLAYER"); // Negative test
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("pp", "PLAYER12");
		expectedInputParams.put("rr", "PLAYER12");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("team", "{PLAYER1,PLAYER2,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11}");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
	
	
	@Test
	public void shouldGenerateInputDataForOperationWithParametersAndMachineWithStateAndConstants3() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substittute operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("card(team) = 11 & pp : team & rr : PLAYER & not(rr /: team) & team <: PLAYER"); // Negative test
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("pp", "PLAYER1");
		expectedInputParams.put("rr", "PLAYER1");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("team", "{PLAYER1,PLAYER2,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11}");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
	
	
	@Test
	public void shouldGenerateInputDataForOperationWithParametersAndMachineWithStateAndConstants4() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substittute operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("card(team) = 11 & not(pp : team) & rr : PLAYER & not(rr /: team) & team <: PLAYER"); // Negative test
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("pp", "PLAYER12");
		expectedInputParams.put("rr", "PLAYER1");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("team", "{PLAYER1,PLAYER2,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11}");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
	
	
//	@Test
//	public void shouldNotGenerateForInfeasibleTestCase() {
//		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
//		Operation operationUnderTest = machine.getOperation(1); // inc operation
//		
//		BETATestCase mockedTestCase = mock(BETATestCase.class);
//		when(mockedTestCase.getTestFormula()).thenReturn("not(value <= MAXINT) & value : INT & 0 <= value & overflow : BOOL & ((overflow = TRUE) => (value = MAXINT)) & value < MAXINT");
//		
//		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
//		
//		Map<String, String> expectedInputParams = new HashMap<String, String>();
//		
//		Map<String, String> expectedStateValues = new HashMap<String, String>();
//		expectedStateValues.put("overflow", "FALSE");
//		expectedStateValues.put("value", "0");
//		
//		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
//		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
//	}
	
	
	
	@Test
	public void shouldGenerateInputDataForOperationWithoutParametersAndMachineWithState1() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1); // inc operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("value <= MAXINT & value : INT & 0 <= value & overflow : BOOL & ((overflow = TRUE) => (value = MAXINT)) & not(value < MAXINT)");
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
		Map<String, String> expectedInputParams = new HashMap<String, String>();
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("overflow", "TRUE");
		expectedStateValues.put("value", "20");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
	
	
	@Test
	public void shouldGenerateInputDataForOperationWithoutParametersAndMachineWithState2() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1); // inc operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("value <= MAXINT & value : INT & 0 <= value & overflow : BOOL & ((overflow = TRUE) => (value = MAXINT)) & value < MAXINT");
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
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
		Operation operationUnderTest = machine.getOperation(2); // go_to(nFloor) operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("floor : 1..5 & nFloor : 1..5 & nFloor /= floor"); // Positive
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
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
		Operation operationUnderTest = machine.getOperation(0); // addpaper(hh) operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
//		when(mockedTestCase.getTestFormula()).thenReturn("card(papers) < 60 & magazines <: papers & card(papers) <= 60 & papers <: 1..163"); // Positive
		when(mockedTestCase.getTestFormula()).thenReturn("card(papers) < 60 & hh : 1..163 & magazines <: papers & card(papers) <= 60 & papers <: 1..163"); // Positive
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("hh", "1");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("magazines", "{}");
		expectedStateValues.put("papers", "{}");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
	
	
	@Test
	public void shouldGenerateInputDataForBasketsAdd() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Baskets.mch"));
		Operation operationUnderTest = machine.getOperation(1); // addpaper(hh) operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("cu : dom(baskets) & gg : GOODS & baskets : (CUSTOMER +-> POW(GOODS))"); // Positive
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("cu", "CUSTOMER1");
		expectedInputParams.put("gg", "GOODS1");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("baskets", "{(CUSTOMER1|->{})}");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
	
	
	@Test
	public void shouldGenerateInputDataForInc() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Inc.mch"));
		Operation operationUnderTest = machine.getOperation(0); // inc(nn) operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("value : INT & nn : NAT & (nn + value) <= MAXINT & value < MAXINT"); // Positive
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("nn", "0");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("value", "-1");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
}
