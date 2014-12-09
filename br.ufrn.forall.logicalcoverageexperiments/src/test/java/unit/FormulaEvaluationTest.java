package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import animation.FormulaEvaluation;
import parser.Machine;
import parser.Operation;

public class FormulaEvaluationTest {

	@Before
	public void setUp() {
		Configurations.setRandomiseEnumerationOrder(false);
		Configurations.setUseKodkod(true);
	}
	
	
	@Test
	public void shouldEvaluateFormula() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		String formula = operationUnderTest.getPrecondition().toString();
		// formula = averageGrade : 0..5 & averageGrade : INT
		
		// Setting up expected results
		
		Map<String, String> expectedParamValues = new HashMap<String, String>();
		
		expectedParamValues.put("averageGrade", "0");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		
		FormulaEvaluation fe = new FormulaEvaluation(operationUnderTest, formula);
		
		assertEquals(expectedParamValues, fe.getParameterValues());
		assertEquals(expectedStateValues, fe.getStateVariablesValues());
	}
	
	
	
	@Test
	public void shouldEvaluateTestFormula() {
		Machine machine = new Machine(new File("src/test/resources/machines/CaseStudy/scheduler.mch"));
		Operation operationUnderTest = machine.getOperation(3);
		
		String formula = "#active,ready,waiting,rr.(active : POW(PID) & "
				+ "ready : POW(PID) & "
				+ "waiting : POW(PID) & "
				+ "active <: PID & "
				+ "ready <: PID & "
				+ "waiting <: PID & "
				+ "(ready /\\ waiting = {}) & "
				+ "(active /\\ (ready \\/ waiting)) = {} & "
				+ "card(active) <= 1 & "
				+ "((active = {}) => (ready = {})) & "
				+ "active = {} & "
				+ "rr : waiting)";
		
		// Setting up expected results
		
		Map<String, String> expectedParamValues = new HashMap<String, String>();
		
		expectedParamValues.put("rr", "PID3");
		
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		
		expectedStateValues.put("active", "{}");
		expectedStateValues.put("ready", "{}");
		expectedStateValues.put("waiting", "{PID3}");
		
		FormulaEvaluation fe = new FormulaEvaluation(operationUnderTest, formula);
			
		assertEquals(expectedParamValues, fe.getParameterValues());
		assertEquals(expectedStateValues, fe.getStateVariablesValues());
	}

}
