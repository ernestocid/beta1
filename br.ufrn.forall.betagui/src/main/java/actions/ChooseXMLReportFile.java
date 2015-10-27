package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import views.ExtensionFileFilter;

public class ChooseXMLReportFile extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WindowForTestDataConcretizationAction windowForTestDataConcretizationAction;

	
	public ChooseXMLReportFile(WindowForTestDataConcretizationAction windowForTestDataConcretizationAction) {
		this.windowForTestDataConcretizationAction = windowForTestDataConcretizationAction;
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new ExtensionFileFilter("XML Files", "xml"));
		int result = fileChooser.showOpenDialog(windowForTestDataConcretizationAction.getTestDataConcretizationFrame());
		
		if(result == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			getWindowForTestDataConcretizationAction().getXmlTestCaseSpecificationPathField().setText(filePath);
		}
	}
	
	
	
	public WindowForTestDataConcretizationAction getWindowForTestDataConcretizationAction() {
		return windowForTestDataConcretizationAction;
	}
}