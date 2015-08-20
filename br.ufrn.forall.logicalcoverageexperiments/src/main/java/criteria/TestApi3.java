package criteria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.be4.classicalb.core.parser.exceptions.BException;
import de.prob.Main;
import de.prob.animator.domainobjects.ClassicalB;
import de.prob.scripting.Api;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Trace;

public class TestApi3 {

	public static void main(String args[]) {

		Api probApi = Main.getInjector().getInstance(Api.class);
		Map<String, String> probPreferences = new HashMap<String, String>();

		probPreferences.put("MAXINT", "20");
		probPreferences.put("MININT", "-10");

		// Player's variable 'team' is a subset of the set PLAYER

		// try {
		// ClassicalBModel model =
		// probApi.b_load("/Users/ernestocid/Temp/Player.mch", probPreferences);
		// StateSpace stateSpace = model.getStateSpace();
		// Trace trace = new Trace(stateSpace);
		//
		// trace = trace.execute("$setup_constants", new ArrayList<String>());
		// trace = trace.execute("$initialise_machine", new
		// ArrayList<String>());
		//
		// trace = stateSpace.getTraceToState(new ClassicalB(
		// "team = {PLAYER12,PLAYER13,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11}"));
		//
		// System.out.println(trace);
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (BException e) {
		// e.printStackTrace();
		// }

		// Player2's variable 'team' is a subset of NATURAL

		try {
			StateSpace stateSpace = probApi.b_load("/Users/ernestocid/Temp/Player2.mch", probPreferences);
			Trace trace = new Trace(stateSpace);

			trace = trace.execute("$setup_constants", new ArrayList<String>());
			trace = trace.execute("$initialise_machine", new ArrayList<String>());

			trace = stateSpace.getTraceToState(new ClassicalB("team = {1,2,3,4,5,6,7,8,9,10,11}"));

			System.out.println(trace);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BException e) {
			e.printStackTrace();
		}

	}
}
