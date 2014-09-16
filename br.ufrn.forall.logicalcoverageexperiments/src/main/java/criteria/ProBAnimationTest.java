package criteria;

import java.io.File;
import java.io.IOException;

import parser.Machine;
import parser.Operation;
import configurations.Configurations;
import de.be4.classicalb.core.parser.exceptions.BException;
import de.prob.Main;
import de.prob.animator.domainobjects.ClassicalB;
import de.prob.animator.domainobjects.EvalResult;
import de.prob.animator.domainobjects.IEvalResult;
import de.prob.model.classicalb.ClassicalBModel;
import de.prob.scripting.Api;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Trace;

public class ProBAnimationTest {

	public static void main(String[] args) {
		System.setProperty("prob.home", "/Users/ernestocid/Downloads/ProB/");
		
		String formula2 = "#active,ready,waiting,rr.(active : POW(PID) & "
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
		
		Api probApi = Main.getInjector().getInstance(Api.class);

		Machine machine = new Machine(new File("src/test/resources/machines/CaseStudy/scheduler.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		String pathToMachine = operationUnderTest.getMachine().getFile().getAbsolutePath();
		
		try {
			ClassicalBModel model = probApi.b_load(pathToMachine, Configurations.getProBApiPreferences());
			StateSpace stateSpace = model.getStateSpace();
			Trace trace = new Trace(stateSpace);
			
//			try {
//				trace = trace.execute("$setup_constants", new ArrayList<String>());
//			} catch (NullPointerException e) {
//				System.err.println("This model has no constants so $setup_constants could not be executed");
//			}
			
			
			try {
				trace = stateSpace.getTraceToState(new ClassicalB(formula2));
			} catch (RuntimeException e) {
				System.err.println("Could not execute get trace to state");
			}
			
			IEvalResult evalCurrent = trace.evalCurrent(new ClassicalB(formula2));
			
			if(evalCurrent instanceof EvalResult) {
				EvalResult result = (EvalResult) evalCurrent;
				
				System.out.println(result.getSolutions());
				
			} else {
				System.out.println("Not an EvalResult");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BException e) {
			e.printStackTrace();
		}
	}
	
	
//	private static Map<String, String> getValuesForStateVariables(Trace trace, Operation operationUnderTest) {
//		Map<String, String> valuesForStateVariables = new HashMap<String, String>();
//		
//		if(operationUnderTest.getMachine().getVariables() != null) {
//			for(String variable : operationUnderTest.getMachine().getVariables().getAll()) {
//				valuesForStateVariables.put(variable, trace.evalCurrent(variable).toString());
//			}
//		}
//		
//		return valuesForStateVariables;
//	}
//	
//	
//	
//	private static Map<String, String> getValuesForInputParams(EvalResult result, Operation operationUnderTest) {
//		Map<String, String> valuesForInputParams = new HashMap<String, String>();
//		
//		Map<String, String> foundSolutions = result.getSolutions();
//		
//		for(String param : operationUnderTest.getParameters()) {
//			valuesForInputParams.put(param, foundSolutions.get(param));
//		}
//		
//		return valuesForInputParams;
//	}
	
}
