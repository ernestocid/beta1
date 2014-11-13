package tools;

import de.prob.Main;
import de.prob.scripting.Api;

public class ProBApi {

	private static Api probApi = null;



	public static Api getInstance() {
		if (probApi == null) {
			probApi = Main.getInjector().getInstance(Api.class);
		}

		return probApi;
	}

}
