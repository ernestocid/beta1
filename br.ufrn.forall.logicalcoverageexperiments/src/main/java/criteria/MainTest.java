package criteria;

import java.io.File;

import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;
import animation.FormulaEvaluation;
import de.prob.Main;
import de.prob.scripting.Api;

public class MainTest {
	

	public static void main(String[] args) {
		
		Api probApi = Main.getInjector().getInstance(Api.class);
		
//		Machine machine = new Machine(new File("/Users/ernestocid/Temp/MCDC/Priorityqueue.mch"));
//		Operation operationUnderTest = machine.getOperation(1);
		
		
//		Machine machine = new Machine(new File("/Users/ernestocid/Temp/MCDC/Priorityqueue.mch"));
//		System.out.println(machine.getName() + ": ");
//		printACCTestsForAllOperations(machine, probApi);

		Machine machine = new Machine(new File("/Users/ernestocid/Temp/B2LLVMCaseStudy/Priorityqueue.mch"));
		System.out.println(machine.getName() + ": ");
		printACCTestsForAllOperations(machine, probApi);
		
		
		
//		Machine machine = new Machine(new File("/Users/ernestocid/Temp/LGAR4_mch.eventb"));
//		Operation operationUnderTest = machine.getOperation(0);
		
		
//		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
//		Operation operationUnderTest = machine.getOperation(0);
		
		
//		Machine machine = new Machine(new File("src/test/resources/machines/Simple.mch"));
//		Operation operationUnderTest = machine.getOperation(0);
		
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
		
//		System.out.println("Machine: " + machine.getName());
//		System.out.println("Operation under test: " + operationUnderTest.getName());
//		System.out.println("");
//		
//		printPredicatesSet(operationUnderTest);
//		
//		System.out.println("");
//		
//		printClausesSet(operationUnderTest);
		
//		System.out.println("\nPredicate Coverage (PC) test formulas: ");
//		
//		printTestFormulas(new PredicateCoverage(operationUnderTest), probApi, operationUnderTest);
//		
//		System.out.println("\nClause Coverage (CC) test formulas: ");
//		
//		printTestFormulas(new ClauseCoverage(operationUnderTest), probApi, operationUnderTest);
//		
//		System.out.println("\nCombinatorial Coverage (CoC) test formulas: ");
//		
//		printTestFormulas(new CombinatorialCoverage(operationUnderTest), probApi, operationUnderTest);
		
//		System.out.println("\nActive Clause Coverage (ACC) test formulas: ");
//		
//		printTestFormulas(new ActiveClauseCoverage(operationUnderTest), probApi, operationUnderTest);
		
	}

	

	private static void printACCTestsForAllOperations(Machine machine, Api probApi) {
		for(Operation op : machine.getOperations()) {
			System.out.println("##### Operation " + op.getName() + " #####\n");
			printTestFormulas(new ActiveClauseCoverage(op), probApi, op);
			System.out.println();
		}
	}



	private static void evaluateFormula(Api probApi, Operation operationUnderTest, String formula) {
//		String pathToMachine = operationUnderTest.getMachine().getFile().getAbsolutePath();
		
		FormulaEvaluation ev = new FormulaEvaluation(operationUnderTest, formula, probApi);
		
		System.out.println("Parameters Solution: " + ev.getParameterValues());
		System.out.println("Variables Solution: " + ev.getStateVariablesValues());
		
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
			System.out.println(testId + " - " + testFormula);
			System.out.println("\nFOUND SOLUTIONS:");
			evaluateFormula(probApi, operationUnderTest, testFormula);
			System.out.println();
			testId++;
		}
	
	}
	
}
