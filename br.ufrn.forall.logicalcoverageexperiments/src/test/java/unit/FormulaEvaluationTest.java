package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.prob.Main;
import de.prob.scripting.Api;
import animation.FormulaEvaluation;
import parser.Machine;
import parser.Operation;

public class FormulaEvaluationTest {

	
	private Api probApi = Main.getInjector().getInstance(Api.class);;
	
	
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
		
		FormulaEvaluation fe = new FormulaEvaluation(operationUnderTest, formula, probApi);
		
		assertEquals(expectedParamValues, fe.getParameterValues());
		assertEquals(expectedStateValues, fe.getStateVariablesValues());
	}

}
