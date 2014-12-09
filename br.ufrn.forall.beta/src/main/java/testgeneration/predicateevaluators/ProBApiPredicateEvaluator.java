package testgeneration.predicateevaluators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import configurations.Configurations;
import de.be4.classicalb.core.parser.exceptions.BException;
import de.be4.classicalb.core.parser.node.APredicateParseUnit;
import de.be4.classicalb.core.parser.node.PParseUnit;
import de.prob.animator.domainobjects.ClassicalB;
import de.prob.animator.domainobjects.EvalResult;
import de.prob.animator.domainobjects.IEvalResult;
import de.prob.model.classicalb.ClassicalBModel;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Trace;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;
import testgeneration.Block;
import tools.ProBApi;
import animator.Animation;

public class ProBApiPredicateEvaluator extends AbstractPredicateEvaluator {

	public ProBApiPredicateEvaluator(Operation operationUnderTest, Set<List<Block>> combinations) {
		super(operationUnderTest, combinations);
		evaluate();
	}



	private void evaluate() {
		List<Animation> animations = new ArrayList<Animation>();
		String pathToMachine = getOperationUnderTest().getMachine().getFile().getAbsolutePath();
		List<String> combinations = getCombinationsAsStrings();

		try {
			ClassicalBModel model = ProBApi.getInstance().b_load(pathToMachine, getPreferences());
			StateSpace stateSpace = model.getStateSpace();

			for (String formulaForEvaluation : combinations) {
				List<Map<String, String>> solutions = new ArrayList<Map<String, String>>();

				Trace trace = new Trace(stateSpace);
				trace = trySetupConstants(trace);

				trace = trace.execute("$initialise_machine", new ArrayList<String>());

				try {
					System.out.println("Using ProB API to solve: " + formulaForEvaluation);
					trace = stateSpace.getTraceToState(new ClassicalB(formulaForEvaluation));

					IEvalResult evalCurrent = trace.evalCurrent(new ClassicalB(formulaForEvaluation));

					if (evalCurrent instanceof EvalResult) {
						EvalResult result = (EvalResult) evalCurrent;

						Map<String, String> solution = new HashMap<String, String>();
						solution.putAll(getValuesForInputParams(result));
						solution.putAll(getValuesForStateVariables(trace));

						System.out.println("Found Solution: " + solution);
						
						solutions.add(solution);

						Animation animation = new Animation(converFormulaToMyPredicate(formulaForEvaluation), solutions);
						animations.add(animation);
					} else {
						System.out.println("Not an EvalResult");
					}
				} catch (RuntimeException e) {
					System.out.println("Could not find solution!");
					getInfeasibleFormulas().add(formulaForEvaluation);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BException e) {
			e.printStackTrace();
		}

		super.getAnimations().addAll(animations);
	}



	@Override
	public List<Animation> getSolutions() {
		return super.getAnimations();
	}



	private MyPredicate converFormulaToMyPredicate(String formulaForEvaluation) {
		PParseUnit parseUnit = new ClassicalB(formulaForEvaluation).getAst().getPParseUnit();

		if (parseUnit instanceof APredicateParseUnit) {
			APredicateParseUnit predUnit = (APredicateParseUnit) parseUnit;
			MyPredicate myPredicate = MyPredicateFactory.convertPredicate(predUnit.getPredicate());

			return myPredicate;
		}

		return null;
	}



	@Override
	public List<String> getInfeasiblePredicates() {
		return super.getInfeasibleFormulas();
	}



	@Override
	public Map<String, Boolean> getFormulaAndTestTypeMap() {
		return super.getFormulaAndTestTypeMap();
	}



	public Map<String, String> getPreferences() {
		return Configurations.getProBApiPreferences();
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

		if (getOperationUnderTest().getMachine().getVariables() != null) {
			for (String variable : getOperationUnderTest().getMachine().getVariables().getAll()) {
				valuesForStateVariables.put(variable, trace.evalCurrent(variable).toString());
			}
		}

		return valuesForStateVariables;
	}



	private Map<String, String> getValuesForInputParams(EvalResult result) {
		Map<String, String> valuesForInputParams = new HashMap<String, String>();

		Map<String, String> foundSolutions = result.getSolutions();

		for (String param : getOperationUnderTest().getParameters()) {
			valuesForInputParams.put(param, foundSolutions.get(param));
		}

		return valuesForInputParams;
	}

}
