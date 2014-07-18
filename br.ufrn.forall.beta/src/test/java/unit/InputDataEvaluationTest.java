package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.prob.Main;
import de.prob.scripting.Api;
import parser.Machine;
import parser.Operation;
import testgeneration.BETATestCase;
import testgeneration.InputDataEvaluation;
import static org.mockito.Mockito.*;

public class InputDataEvaluationTest {

	
	private Api probApi = Main.getInjector().getInstance(Api.class);
	
	
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
		expectedInputParams.put("nFloor", "1");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("floor", "2");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
	
	
	@Test
	public void shouldGenerateInputDataForPaperroundAddPaper() {
		Machine machine = new Machine(new File("/Users/ernestocid/dev/beta/br.ufrn.forall.betagui/build/distributions/beta-1.2-bin/examples/Paperround.mch"));
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
		Machine machine = new Machine(new File("/Users/ernestocid/dev/beta/br.ufrn.forall.betagui/build/distributions/beta-1.2-bin/examples/Baskets.mch"));
		Operation operationUnderTest = machine.getOperation(0); // addpaper(hh) operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("cu : dom(baskets) & gg : GOODS & baskets : (CUSTOMER +-> POW(GOODS))"); // Positive
		
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
	public void shouldGenerateInputDataForInc() {
		Machine machine = new Machine(new File("/Users/ernestocid/dev/beta/br.ufrn.forall.betagui/build/distributions/beta-1.2-bin/examples/Inc.mch"));
		Operation operationUnderTest = machine.getOperation(0); // inc(nn) operation
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getTestFormula()).thenReturn("value : INT & nn : NAT & (nn + value) <= MAXINT & value < MAXINT"); // Positive
		
		InputDataEvaluation inputDataEvaluation = new InputDataEvaluation(mockedTestCase, operationUnderTest, probApi);
		
		Map<String, String> expectedInputParams = new HashMap<String, String>();
		expectedInputParams.put("hh", "1");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		expectedStateValues.put("magazines", "{}");
		expectedStateValues.put("papers", "{}");
		
		assertEquals(expectedInputParams, inputDataEvaluation.getInputParamsValues());
		assertEquals(expectedStateValues, inputDataEvaluation.getStateVariablesValues());
	}
	
}
