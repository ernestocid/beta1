package testgeneration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import configurations.Configurations;
import parser.Operation;
import tools.ProBApi;
import de.prob.animator.domainobjects.AbstractEvalResult;
import de.prob.animator.domainobjects.ClassicalB;
import de.prob.animator.domainobjects.EvalResult;
import de.prob.scripting.Api;
import de.prob.scripting.ModelTranslationError;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Trace;

public class InputDataEvaluation {

	private BETATestCase testCase;
	private Operation operationUnderTest;
	private Api probApi;
	private Map<String, String> inputParamsValues;
	private Map<String, String> stateVariablesValues;



	public InputDataEvaluation(BETATestCase testCase, Operation operationUnderTest) {
		this.testCase = testCase;
		this.operationUnderTest = operationUnderTest;
		this.probApi = ProBApi.getInstance();
		;
		this.inputParamsValues = new HashMap<String, String>();
		this.stateVariablesValues = new HashMap<String, String>();
		generateInputData();
	}



	private void generateInputData() {
		try {
			String pathToMachine = this.operationUnderTest.getMachine().getFile().getAbsolutePath();
			String formulaForEvaluation = this.getTestCase().getTestFormula();

			StateSpace stateSpace = probApi.b_load(pathToMachine, getPreferences());
			Trace trace = new Trace(stateSpace);

			trace = trySetupConstants(trace);

			trace = trace.execute("$initialise_machine", new ArrayList<String>());
			trace = stateSpace.getTraceToState(new ClassicalB(formulaForEvaluation));

			AbstractEvalResult evalCurrent = trace.evalCurrent(new ClassicalB(formulaForEvaluation));

			if (evalCurrent instanceof EvalResult) {
				EvalResult result = (EvalResult) evalCurrent;

				this.inputParamsValues = getValuesForInputParams(result);
				this.stateVariablesValues = getValuesForStateVariables(trace);

			} else {
				System.out.println("Not an EvalResult");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ModelTranslationError e) {
			e.printStackTrace();
		}
	}



	private Trace trySetupConstants(Trace trace) {
		boolean machineHasNoConstants = getOperationUnderTest().getMachine().getAllMachineConstants().isEmpty()
				&& getOperationUnderTest().getMachine().getSets() == null;

		if (machineHasNoConstants) {
			return trace;
		} else {

			try {
				trace = trace.execute("$setup_constants", new ArrayList<String>());
			} catch (NullPointerException e) {
				System.err.println("Could not execute $setup_constants");
			}

			return trace;
		}
	}



	private Map<String, String> getValuesForStateVariables(Trace trace) {
		Map<String, String> valuesForStateVariables = new HashMap<String, String>();

		if (this.operationUnderTest.getMachine().getVariables() != null) {
			for (String variable : this.operationUnderTest.getMachine().getVariables().getAll()) {
				AbstractEvalResult evalCurrent = trace.evalCurrent(variable);

				if (evalCurrent instanceof EvalResult) {
					EvalResult result = (EvalResult) evalCurrent;
					valuesForStateVariables.put(variable, result.getValue());
				}

			}
		}

		return valuesForStateVariables;
	}



	private Map<String, String> getValuesForInputParams(EvalResult result) {
		Map<String, String> valuesForInputParams = new HashMap<String, String>();

		Map<String, String> foundSolutions = result.getSolutions();

		for (String param : this.operationUnderTest.getParameters()) {
			valuesForInputParams.put(param, foundSolutions.get(param));
		}

		return valuesForInputParams;
	}



	public BETATestCase getTestCase() {
		return testCase;
	}



	public Operation getOperationUnderTest() {
		return operationUnderTest;
	}



	public Api getProbApi() {
		return probApi;
	}



	public Map<String, String> getPreferences() {
		return Configurations.getProBApiPreferences();
	}



	public Map<String, String> getInputParamsValues() {
		return inputParamsValues;
	}



	public Map<String, String> getStateVariablesValues() {
		return stateVariablesValues;
	}

}
