package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import views.ExtensionFileFilter;

public class ChooseImplementationFile extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WindowForTestDataConcretizationAction windowForTestDataConcretizationAction;

	
	public ChooseImplementationFile(WindowForTestDataConcretizationAction windowForTestDataConcretizationAction) {
		this.windowForTestDataConcretizationAction = windowForTestDataConcretizationAction;
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new ExtensionFileFilter("B Implementations", "imp"));
		int result = fileChooser.showOpenDialog(windowForTestDataConcretizationAction.getTestDataConcretizationFrame());

		if (result == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			getWindowForTestDataConcretizationAction().getImplementationPathField().setText(filePath);
		}
	}

	
	
	public WindowForTestDataConcretizationAction getWindowForTestDataConcretizationAction() {
		return windowForTestDataConcretizationAction;
	}
}
