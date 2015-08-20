package criteria;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.be4.classicalb.core.parser.exceptions.BException;
import de.prob.Main;
import de.prob.animator.domainobjects.AbstractEvalResult;
import de.prob.animator.domainobjects.ClassicalB;
import de.prob.animator.domainobjects.EvalResult;
import de.prob.exception.ProBError;
import de.prob.scripting.Api;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Trace;

public class TestApi {

	public static void main(String[] args) {
		
		System.setProperty("prob.home", "/Users/ernestocid/Tools/ProB/");
		System.out.println("ProB home: " + System.getProperty("prob.home"));
		
		File machine = new File("src/test/resources/machines/Simple.mch");

		System.out.println("Evaluating Formulas: ");

		evaluateFormula(machine, "#yy,xx.((xx : 0..10 & yy : 0..10) & (not(xx = 10) & yy = 10))");
//		Solution using API: {xx=0}
//		Solution using probcli -eval: yy = 10 & xx = 0
		
		evaluateFormula(machine, "#yy,xx.((xx : 0..10 & yy : 0..10) & (xx = 10 & not(yy = 10)))");
//		Solution using API: {yy=0}
//		Solution using probcli -eval: xx = 10 & yy = 0
		
		evaluateFormula(machine, "#yy,xx.((xx : 0..10 & yy : 0..10) & (xx = 10 & yy = 10))");
//		Solution using API: {}
//		Solution using probcli -eval: yy = 10 & xx = 10		
		
		evaluateFormula(machine, "#yy,xx.((xx : 0..10 & yy : 0..10) & (yy = 10) & ((xx = 10 or 1=1) <=> not(xx = 10 or 1=2)))");
//		Solution using API: {xx=0}
//		Solution using probcli -eval: yy = 10 & xx = 0
		
		evaluateFormula(machine, "#yy,xx.((xx : 0..10 & yy : 0..10) & (xx = 10) & ((1=1 or yy = 10) <=> not(1=2 or yy = 10)))");
//		Solution using API: {yy=0}
//		Solution using probcli -eval: xx = 10 & yy = 0
		
	}

	private static void evaluateFormula(File machine, String formula) {
		
		Api probApi = Main.getInjector().getInstance(Api.class);
		
		try {
			StateSpace stateSpace = probApi.b_load(machine.getAbsolutePath());
			Trace trace = new Trace(stateSpace);
			
//			trace = trace.execute("$setup_constants", new ArrayList<String>());
			trace = trace.execute("$initialise_machine", new ArrayList<String>());
			
			try {
				AbstractEvalResult evalCurrent = trace.evalCurrent(new ClassicalB(formula));
				
				if(evalCurrent instanceof EvalResult) {
					
					EvalResult result = (EvalResult) evalCurrent;
					
					System.out.println(formula);
					System.out.println("Found Solutions: " + result.getSolutions());
					System.out.println();
					
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

}
