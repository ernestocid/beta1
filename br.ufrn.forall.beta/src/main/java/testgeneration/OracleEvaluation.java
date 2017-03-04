package testgeneration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

public class OracleEvaluation {

	private BETATestCase testCase;
	private Operation operationUnderTest;
	private Map<String, String> preferences;
	private Map<String, String> expectedStateValues;
	private Map<String, String> expectedReturnVariables;
	private Api probApi;



	public OracleEvaluation(BETATestCase testCase, Operation operationUnderTest) {
		this.testCase = testCase;
		this.operationUnderTest = operationUnderTest;
		this.preferences = Configurations.getProBApiPreferences();
		this.probApi = ProBApi.getInstance();
		generateExpectedResults();
	}



	private void generateExpectedResults() {

		StateSpace stateSpace = getModelStateSpace();
		Trace traceBeforeTest = getInitialTrace(stateSpace);

		if (!testCase.getStateValues().isEmpty()) {
			traceBeforeTest = stateSpace.getTraceToState(new ClassicalB(stateDefinition()));
		}

		executeOperation(traceBeforeTest);
	}



	private Trace getInitialTrace(StateSpace stateSpace) {
		Trace traceBeforeTest = new Trace(stateSpace);
		return traceBeforeTest.randomAnimation(1);
	}



	private StateSpace getModelStateSpace() {
		String pathToMachine = operationUnderTest.getMachine().getFile().getAbsolutePath();

		StateSpace stateSpace = null;

		try {
			stateSpace = getProbApi().b_load(pathToMachine, this.preferences);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ModelTranslationError e) {

			e.printStackTrace();
		}

		return stateSpace;
	}



	private void executeOperation(Trace currentTrace) {
		List<String> operationParameters = defineOperationParameters(testCase.getInputParamValues());
		Trace afterTrace = currentTrace.execute(operationUnderTest.getName(), operationParameters);

		this.expectedStateValues = getStateValuesAfterExecution(afterTrace);
		this.expectedReturnVariables = getReturnVariablesAfterExecution(afterTrace);
	}



	private Map<String, String> getReturnVariablesAfterExecution(Trace afterTrace) {
		Map<String, String> returnVariablesAfter = new HashMap<String, String>();
		return returnVariablesAfter;
	}



	private Map<String, String> getStateValuesAfterExecution(Trace afterTrace) {
		Map<String, String> stateValuesAfter = new HashMap<String, String>();

		if (operationUnderTest.getMachine().getVariables() != null) {
			for (String variable : operationUnderTest.getMachine().getVariables().getAll()) {
				AbstractEvalResult result = afterTrace.evalCurrent(variable);

				if (result instanceof EvalResult) {
					EvalResult res = (EvalResult) result;
					stateValuesAfter.put(variable, res.getValue());
				}

			}
		}

		return stateValuesAfter;
	}



	private List<String> defineOperationParameters(Map<String, String> inputParamValues) {
		List<String> operationParams = new ArrayList<String>();

		for (Entry<String, String> param : inputParamValues.entrySet()) {
			operationParams.add(param.getKey() + "=" + param.getValue());
		}

		return operationParams;
	}



	private String stateDefinition() {
		StringBuffer formulaForState = new StringBuffer("");

		Map<String, String> stateValues = testCase.getStateValues();

		int i = 0;

		for (String key : stateValues.keySet()) {
			if (i < stateValues.size() - 1) {
				formulaForState.append(key + " = " + stateValues.get(key) + " & ");
			} else {
				formulaForState.append(key + " = " + stateValues.get(key));
			}
			i++;
		}

		return formulaForState.toString();
	}



	public BETATestCase getTestCase() {
		return testCase;
	}



	public Operation getOperationUnderTest() {
		return operationUnderTest;
	}



	public Map<String, String> getExpectedStateValues() {
		return expectedStateValues;
	}



	public Map<String, String> getExpectedReturnVariables() {
		return expectedReturnVariables;
	}



	public Api getProbApi() {
		return this.probApi;
	}

}
