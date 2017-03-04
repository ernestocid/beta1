package criteria;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.be4.classicalb.core.parser.exceptions.BException;
import de.prob.Main;
import de.prob.animator.domainobjects.ClassicalB;
import de.prob.scripting.Api;
import de.prob.scripting.ModelTranslationError;
import de.prob.statespace.State;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Trace;

public class TestTraceToState {

	public static void main(String[] args) {
		Api api = Main.getInjector().getInstance(Api.class);
		// api.upgrade("latest");
		try {
			Map<String, String> preferences = new HashMap<String, String>();
			preferences.put("MAXINT", "25");

			StateSpace bModel = api.b_load("/Users/ernestocid/Temp/counter.mch", preferences);
			Trace traceToState = bModel.getTraceToState(new ClassicalB("value=5"));
			
			List<State> states = bModel.getStatesFromPredicate(new ClassicalB("value=5"));
			
			System.out.println(states);
			

			System.out.println(traceToState.toString());
			

//			while (traceToState.canGoForward()) {
//				traceToState = traceToState.forward();
//				System.out.println("-----");
//				System.out.println(traceToState);
//			}

			// for(Transition transition : traceToState.getTransitionList()) {
			// System.out.println(transition.getName());
			// }

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ModelTranslationError e) {
			e.printStackTrace();
		}
		
		System.exit(0);

	}

}
