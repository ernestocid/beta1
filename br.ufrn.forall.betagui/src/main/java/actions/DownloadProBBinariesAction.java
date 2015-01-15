package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.prob.Main;
import de.prob.scripting.Api;

public class DownloadProBBinariesAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Downloading probcli");

		Api probApi = Main.getInjector().getInstance(Api.class);
		probApi.upgrade("latest");

		System.out.println("probcli download finished");
	}

}
