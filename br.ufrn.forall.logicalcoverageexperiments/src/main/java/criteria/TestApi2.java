package criteria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.be4.classicalb.core.parser.exceptions.BException;
import de.prob.Main;
import de.prob.model.classicalb.ClassicalBModel;
import de.prob.scripting.Api;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Trace;

public class TestApi2 {

	public static void main(String args[]) {

		Api probApi = Main.getInjector().getInstance(Api.class);
		Map<String, String> probPreferences = new HashMap<String, String>();

		probPreferences.put("MAXINT", "20");
		probPreferences.put("MININT", "-10");

		// Player's variable 'team' is a subset of the set PLAYER
		
		try {
			ClassicalBModel model = probApi.b_load("/Users/ernestocid/Temp/Player.mch", probPreferences);
			StateSpace stateSpace = model.getStateSpace();
			Trace trace = new Trace(stateSpace);

			List<String> substituteParams = new ArrayList<String>();
			substituteParams.add("PLAYER5");
			substituteParams.add("PLAYER17");

			trace = trace.execute("$setup_constants", new ArrayList<String>());
			trace = trace.execute("$initialise_machine", new ArrayList<String>());
			trace = trace.execute("substitute", substituteParams);

			System.out.println(trace);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BException e) {
			e.printStackTrace();
		}
		
		
		// Player2's variable 'team' is a subset of NATURAL		
		
//		try {
//			ClassicalBModel model = probApi.b_load("/Users/ernestocid/Temp/Player2.mch", probPreferences);
//			StateSpace stateSpace = model.getStateSpace();
//			Trace trace = new Trace(stateSpace);
//
//			List<String> substituteParams = new ArrayList<String>();
//			substituteParams.add("1");
//			substituteParams.add("13");
//
//			trace = trace.execute("$setup_constants", new ArrayList<String>());
//			trace = trace.execute("$initialise_machine", new ArrayList<String>());
//			trace = trace.execute("substitute", substituteParams);
//
//			System.out.println(trace);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (BException e) {
//			e.printStackTrace();
//		}

	}

}
