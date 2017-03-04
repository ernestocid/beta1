package criteria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tools.ProBApi;
import de.be4.classicalb.core.parser.exceptions.BException;
import de.prob.scripting.ModelTranslationError;
import de.prob.statespace.StateSpace;
import de.prob.statespace.Trace;

public class TestApi5 {

	public static void main(String[] args) {
//		Api probApi = Main.getInjector().getInstance(Api.class);
		Map<String, String> probPreferences = new HashMap<String, String>();

		probPreferences.put("SYMBOLIC", "true");
		probPreferences.put("MAXINT", "20");
		probPreferences.put("MININT", "-1");
		probPreferences.put("RANDOMISE_ENUMERATION_ORDER", "false");
		probPreferences.put("CLPFD", "true");
		probPreferences.put("KODKOD", "false");
		probPreferences.put("CHR", "false");

		try {
			StateSpace stateSpace = ProBApi.getInstance().b_load("/Users/ernestocid/Temp/test.directory/scheduler.mch", probPreferences);
			Trace trace = new Trace(stateSpace);

			trace = trace.execute("$initialise_machine", new ArrayList<String>());


			System.out.println(trace);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ModelTranslationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
