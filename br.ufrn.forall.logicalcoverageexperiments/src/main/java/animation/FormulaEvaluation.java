package animation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import configurations.Configurations;
import de.be4.classicalb.core.parser.exceptions.BException;
import de.prob.animator.domainobjects.AbstractEvalResult;
import de.prob.animator.domainobjects.ClassicalB;
import de.prob.animator.domainobjects.EvalResult;
import de.prob.exception.ProBError;
import de.prob.scripting.Api;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Trace;
import parser.Machine;
import parser.Operation;
import tools.ProBApi;

public class FormulaEvaluation {

	private Operation operation;
	private Machine machine;
	private String formula;
	private Api probApi;
	private Map<String, String> parameterValues;
	private Map<String, String> stateVariablesValues;
	private Map<String, String> solutions;



	public FormulaEvaluation(Operation operation, String formula) {
		this.operation = operation;
		this.machine = operation.getMachine();
		this.formula = formula;
		this.probApi = ProBApi.getInstance();
		this.parameterValues = new HashMap<String, String>();
		this.stateVariablesValues = new HashMap<String, String>();
		evaluateFormula();
	}



	private void evaluateFormula() {
		String pathToMachine = getMachine().getFile().getAbsolutePath();

		try {
			StateSpace stateSpace = ProBApi.getInstance().b_load(pathToMachine, Configurations.getProBApiPreferences());
			Trace trace = new Trace(stateSpace);

			trace = setupConstants(trace);
			trace = initialiseMachine(trace);

			try {
				AbstractEvalResult evalCurrent = trace.evalCurrent(new ClassicalB(formula));

				if (evalCurrent instanceof EvalResult) {

					EvalResult result = (EvalResult) evalCurrent;

					this.solutions = result.getSolutions();
					this.parameterValues = getValuesForParameters(result, getOperation());
					this.stateVariablesValues = getValuesForStateVariables(result, getOperation());

				} else {
					System.out.println("Not an EvalResult");
				}
			} catch (ProBError e) {
				System.err.println("ProB error while evaluating formula!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BException e) {
			e.printStackTrace();
		}
	}



	private Trace initialiseMachine(Trace trace) {
		trace = trace.execute("$initialise_machine", new ArrayList<String>());
		return trace;
	}



	private Trace setupConstants(Trace trace) {
		try {
			trace = trace.execute("$setup_constants", new ArrayList<String>());
		} catch (Exception e) {
			System.err.println("Warning: tried to $setup_constants but failed");
		}
		return trace;
	}



	private static Map<String, String> getValuesForStateVariables(EvalResult result, Operation operationUnderTest) {
		Map<String, String> valuesForStateVariables = new HashMap<String, String>();

		Map<String, String> foundSolutions = result.getSolutions();

		if (operationUnderTest.getMachine().getVariables() != null) {
			for (String variable : operationUnderTest.getMachine().getVariables().getAll()) {
				if (foundSolutions.get(variable) != null) {
					valuesForStateVariables.put(variable, foundSolutions.get(variable).toString());
				}
			}
		}

		return valuesForStateVariables;
	}



	private static Map<String, String> getValuesForParameters(EvalResult result, Operation operationUnderTest) {
		Map<String, String> valuesForInputParams = new HashMap<String, String>();

		Map<String, String> foundSolutions = result.getSolutions();

		for (String param : operationUnderTest.getParameters()) {
			if (foundSolutions.get(param) != null) {
				valuesForInputParams.put(param, foundSolutions.get(param));
			}
		}

		return valuesForInputParams;
	}



	/**
	 * This methods returns a Map containing values for the parameters of the
	 * operation that satisfy the given formula. Each entry in the Map has the
	 * parameter's name as the key and the actual value as the entry's value.
	 * 
	 * @return a Map with values for the operation parameters.
	 */
	public Map<String, String> getParameterValues() {
		return parameterValues;
	}



	/**
	 * This methods returns a Map containing values for the state variables of
	 * the machine that satisfy the given formula. Each entry in the Map has the
	 * variables's name as the key and the actual value as the entry's value.
	 * 
	 * @return a Map with values for the machine variables.
	 */
	public Map<String, String> getStateVariablesValues() {
		return stateVariablesValues;
	}



	/**
	 * This method returns a Map containing values for the free variables on the
	 * formula.
	 * 
	 * @return a Map with values for the free variables of the formula
	 */
	public Map<String, String> getFreeVariablesValues() {
		Map<String, String> freeVariablesValues = new HashMap<String, String>();

		freeVariablesValues.putAll(getParameterValues());
		freeVariablesValues.putAll(getStateVariablesValues());

		return freeVariablesValues;
	}



	public Operation getOperation() {
		return operation;
	}



	public Machine getMachine() {
		return machine;
	}



	public String getFormula() {
		return formula;
	}



	public Api getProbApi() {
		return probApi;
	}



	/**
	 * Returns all solutions obtained by the constraint solver.
	 * 
	 * @return a Map where each entry is a solution for a free variable in the
	 *         formula.
	 */
	public Map<String, String> getSolutions() {
		return solutions;
	}

}
