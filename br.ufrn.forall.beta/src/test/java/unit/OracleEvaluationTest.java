package unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import testgeneration.BETATestCase;
import testgeneration.OracleEvaluation;

public class OracleEvaluationTest {

	
	@Test
	public void shouldGetOracleValuesForMachineWithStateAndOperationWithNoParameters() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1); // inc operation
		
		Map<String, String> stateValues = new HashMap<String, String>();
		stateValues.put("overflow", "FALSE");
		stateValues.put("value", "1");
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getStateValues()).thenReturn(stateValues);
		
		OracleEvaluation oracleEvaluation = new OracleEvaluation(mockedTestCase, operationUnderTest);
		
		Map<String, String> expectedResults = new HashMap<String, String>();
		expectedResults.put("overflow", "FALSE");
		expectedResults.put("value", "2");
		
		assertEquals(expectedResults, oracleEvaluation.getExpectedStateValues());
	}
	
	
	
	@Test
	public void shouldGetOracleValuesForMachineWithStateAndOperationWithNoParameters2() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1); // inc operation
		
		Map<String, String> stateValues = new HashMap<String, String>();
		stateValues.put("overflow", "FALSE");
		stateValues.put("value", "MAXINT");
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getStateValues()).thenReturn(stateValues);
		
		OracleEvaluation oracleEvaluation = new OracleEvaluation(mockedTestCase, operationUnderTest);
		
		assertEquals("TRUE", oracleEvaluation.getExpectedStateValues().get("overflow"));
	}
	
	
	
//	@Test
//	public void shouldGetOracleValuesForMachineWithNoStateAndOperationWithParametersAndReturnVariables() {
//		Machine machine = new Machine(new File("src/test/resources/machines/others/Prime.mch"));
//		Operation operationUnderTest = machine.getOperation(0); // IsPrime(x) operation
//		
//		Map<String, String> stateValues = new HashMap<String, String>();
//		
//		Map<String, String> parametersInput = new HashMap<String, String>();
//		parametersInput.put("x", "2");
//		
//		BETATestCase mockedTestCase = mock(BETATestCase.class);
//		when(mockedTestCase.getStateValues()).thenReturn(stateValues);
//		when(mockedTestCase.getInputParamValues()).thenReturn(parametersInput);
//		
//		OracleEvaluation oracleEvaluation = new OracleEvaluation(mockedTestCase, operationUnderTest);
//		
//		Map<String, String> expectedStateValues = new HashMap<String, String>();
//		
//		Map<String, String> expectedReturnVariables = new HashMap<String, String>();
//		expectedReturnVariables.put("r", "TRUE");
//		
//		assertEquals(expectedStateValues, oracleEvaluation.getExpectedStateValues());
//		assertEquals(expectedReturnVariables, oracleEvaluation.getExpectedReturnVariables());
//	}
	
	
	
	@Test
	public void shouldGetOracleValuesForOperationWithParameters() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Player2.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substitute operation
		
		Map<String, String> stateValues = new HashMap<String, String>();
		stateValues.put("team", "{0,1,2,3,4,5,6,7,8,9,10}");
		
		Map<String, String> parametersInput = new HashMap<String, String>();
		parametersInput.put("pp", "0");
		parametersInput.put("rr", "11");
		
		BETATestCase mockedTestCase = mock(BETATestCase.class);
		when(mockedTestCase.getStateValues()).thenReturn(stateValues);
		when(mockedTestCase.getInputParamValues()).thenReturn(parametersInput);

		Map<String, String> expectedResults = new HashMap<String, String>();
		expectedResults.put("team", "{1,2,3,4,5,6,7,8,9,10,11}");
		
		OracleEvaluation oracleEvaluation = new OracleEvaluation(mockedTestCase, operationUnderTest);
		
		assertEquals(expectedResults, oracleEvaluation.getExpectedStateValues());
	}

}
