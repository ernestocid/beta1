package criteria;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import animation.FormulaEvaluation;
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
import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;

public class MainTest {
	

	public static void main(String[] args) {
		
		Api probApi = Main.getInjector().getInstance(Api.class);
		
//		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
//		Operation operationUnderTest = machine.getOperation(0);
		
		Machine machine = new Machine(new File("src/test/resources/machines/Simple.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
//		Machine machine = new Machine(new File("src/test/resources/machines/CaseStmt.mch"));
//		Operation operationUnderTest = machine.getOperation(1);
		
//		Machine machine = new Machine(new File("src/test/resources/machines/Any.mch"));
//		Operation operationUnderTest = machine.getOperation(0);
		
//		Machine machine = new Machine(new File("src/test/resources/machines/CarlaNewTravelAgency.mch"));
//		Operation operationUnderTest = machine.getOperation(0); // bookRoom(sid)
		
//		Machine machine = new Machine(new File("src/test/resources/machines/SelectStmt.mch"));
//		Operation operationUnderTest = machine.getOperation(0);
		
//		Machine machine = new Machine(new File("src/test/resources/machines/CaseStudy/scheduler.mch"));
//		Operation operationUnderTest = machine.getOperation(3);
		
		System.out.println("Machine: " + machine.getName());
		System.out.println("Operation under test: " + operationUnderTest.getName());
		System.out.println("");
		
		printPredicatesSet(operationUnderTest);
		
		System.out.println("");
		
		printClausesSet(operationUnderTest);
		
		System.out.println("\nPredicate Coverage (PC) test formulas: ");
		
		printTestFormulas(new PredicateCoverage(operationUnderTest), probApi, operationUnderTest);
		
		System.out.println("\nClause Coverage (CC) test formulas: ");
		
		printTestFormulas(new ClauseCoverage(operationUnderTest), probApi, operationUnderTest);
		
		System.out.println("\nCombinatorial Coverage (CoC) test formulas: ");
		
		printTestFormulas(new CombinatorialCoverage(operationUnderTest), probApi, operationUnderTest);
		
		System.out.println("\nActive Clause Coverage (ACC) test formulas: ");
		
		printTestFormulas(new ActiveClauseCoverage(operationUnderTest), probApi, operationUnderTest);
		
	}

	

	private static void evaluateFormula(Api probApi, Operation operationUnderTest, String formula) {
		String pathToMachine = operationUnderTest.getMachine().getFile().getAbsolutePath();
		
		
		FormulaEvaluation ev = new FormulaEvaluation(operationUnderTest, formula, probApi);
		
		System.out.println("Parameters Solution: " + ev.getParameterValues());
		System.out.println("Variables Solution: " + ev.getStateVariablesValues());
		
		
		
//		try {
//			ClassicalBModel model = probApi.b_load(pathToMachine, Configurations.getProBApiPreferences());
//			StateSpace stateSpace = model.getStateSpace();
//			Trace trace = new Trace(stateSpace);
//			
//			try {
//				if(operationUnderTest.getMachine().getConstants() != null) {
//					trace = trace.execute("$setup_constants", new ArrayList<String>());
//				}
//			} catch (NullPointerException e) {
//				System.err.println("This model has no constants so $setup_constants could not be executed");
//			}
//			
////			trace = trace.execute("$initialise_machine", new ArrayList<String>());
//			
//			if(operationUnderTest.getMachine().getVariables() != null) {
////				IEvalResult evalCurrent = trace.evalCurrent(new ClassicalB(formula));
//				try {
//					trace = stateSpace.getTraceToState(new ClassicalB(formula));
//				} catch (RuntimeException e) {
////					System.err.println("Could not execute get trace to state");
//				}
//			}
//			
//			IEvalResult evalCurrent = trace.evalCurrent(new ClassicalB(formula));
//			
//			if(evalCurrent instanceof EvalResult) {
//				EvalResult result = (EvalResult) evalCurrent;
//				
//				Map<String, String> valuesForInputParams = getValuesForInputParams(result, operationUnderTest);
//				Map<String, String> valuesForStateVariables = getValuesForStateVariables(trace, operationUnderTest);
//				
//				System.out.println("\t\tValues for parameters: ");
//				
//				for(Entry<String, String> entry : valuesForInputParams.entrySet()) {
//					System.out.println("\t\t\t" + entry.getKey() + ": " + entry.getValue());
//				}
//				
//				System.out.println("\t\tValues for state variables: ");
//				
//				for(Entry<String, String> entry : valuesForStateVariables.entrySet()) {
//					System.out.println("\t\t\t" + entry.getKey() + ": " + entry.getValue());
//				}
//				
//				
//			} else {
//				System.out.println("Not an EvalResult");
//			}
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (BException e) {
//			e.printStackTrace();
//		}
		
	}
	
	
	
	private static Map<String, String> getValuesForStateVariables(Trace trace, Operation operationUnderTest) {
		Map<String, String> valuesForStateVariables = new HashMap<String, String>();
		
		if(operationUnderTest.getMachine().getVariables() != null) {
			for(String variable : operationUnderTest.getMachine().getVariables().getAll()) {
				valuesForStateVariables.put(variable, trace.evalCurrent(variable).toString());
			}
		}
		
		return valuesForStateVariables;
	}
	
	
	
	private static Map<String, String> getValuesForInputParams(EvalResult result, Operation operationUnderTest) {
		Map<String, String> valuesForInputParams = new HashMap<String, String>();
		
		Map<String, String> foundSolutions = result.getSolutions();
		
		for(String param : operationUnderTest.getParameters()) {
			valuesForInputParams.put(param, foundSolutions.get(param));
		}
		
		return valuesForInputParams;
	}



	private static void printPredicatesSet(Operation operationUnderTest) {
		LogicalCoverage pc = new PredicateCoverage(operationUnderTest);
		
		System.out.println("Predicates set P: ");
		
		for(MyPredicate predicate : pc.getOrderedPredicates()) {
			System.out.println("\t" + predicate.toString());
		}
		
	}
	
	
	
	private static void printClausesSet(Operation operationUnderTest) {
		LogicalCoverage pc = new PredicateCoverage(operationUnderTest);
		
		System.out.println("Clauses set C: ");
		
		for(MyPredicate clause : pc.getClauses()) {
			System.out.println("\t" + clause.toString());
		}
	}
	
	
	
	private static void printTestFormulas(LogicalCoverage logicalCoverage, Api probApi, Operation operationUnderTest) {
		int testId = 1;
		
		for(String testFormula : logicalCoverage.getTestFormulas()) {
			System.out.println("\t" + testId + " - " + testFormula);
			evaluateFormula(probApi, operationUnderTest, testFormula);
			testId++;
		}
	
	}
	
}
