package criteria;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
		
//		String formula = "#queue,nn.((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (nn <= min(ran(queue))) & ((queue /= [] & 1=1) <=> not(queue /= [] & 1=2)))";
		String formula = "#queue,nn.((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & not(nn <= min(ran(queue))) & ((queue /= [] & 1=1) <=> not(queue /= [] & 1=2)))";
//		String formula = "#queue,nn.((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (queue /= [] => not(nn <= min(ran(queue)))) & ((queue /= [] & 1=1) <=> not(queue /= [] & 1=2)))";
	
		
		Api probApi = Main.getInjector().getInstance(Api.class);
		
		try {
			ClassicalBModel model = probApi.b_load("src/test/resources/machines/Priorityqueue.mch", Configurations.getProBApiPreferences());
			StateSpace stateSpace = model.getStateSpace();
			Trace trace = new Trace(stateSpace);
			
//			try {
//				trace = trace.execute("$setup_constants", new ArrayList<String>());
//			} catch (NullPointerException e) {
//				System.err.println("This model has no constants so $setup_constants could not be executed");
//			}
			
//			try {
//				trace = stateSpace.getTraceToState(new ClassicalB(formula));
//			} catch (RuntimeException e) {
//				System.err.println("Could not execute get trace to state");
//			}
			
			trace = trace.execute("$initialise_machine", new ArrayList<String>());
			
			IEvalResult evalCurrent = trace.evalCurrent(new ClassicalB(formula));
			
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
	
}
