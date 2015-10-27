package actions;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import views.Application;

public class WindowForTestDataConcretizationAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Application application;
	
	private JFrame testDataConcretizationFrame;
	private JPanel concretizationPanel;
	private JLabel xmlTestCaseSpecificationPathLabel;
	private JTextField xmlTestCaseSpecificationPathField;
	private JLabel implementationPathLabel;
	private JTextField implementationPathField;
	private JButton concretizeButton;
	private JButton chooseXMLFileButton;
	private AbstractButton chooseImplementationFileButton;

	
	public WindowForTestDataConcretizationAction(Application application) {
		this.application = application;
		putValue(NAME, "Concretize Test Data");
		putValue(SHORT_DESCRIPTION, "Opens Test Data Concretization Window");
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {

		// Creating components
		
		testDataConcretizationFrame = new JFrame("Test Data Concretization");

		concretizationPanel = new JPanel(new GridBagLayout());
		concretizationPanel.setBackground(Color.WHITE);
		concretizationPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));

		xmlTestCaseSpecificationPathLabel = createLabel("XML Test Case Specification: ");
		xmlTestCaseSpecificationPathField = new JTextField(25);
		chooseXMLFileButton = new JButton("Choose XML");
		chooseXMLFileButton.addActionListener(new ChooseXMLReportFile(this));
		
		implementationPathLabel = createLabel("Machine's Implementation: ");
		implementationPathField = new JTextField(25);
		chooseImplementationFileButton = new JButton("Choose Implementation");
		chooseImplementationFileButton.addActionListener(new ChooseImplementationFile(this));
		
		concretizeButton = new JButton("Concretize Test Data");
		concretizeButton.addActionListener(new ConcretizeTestDataAction(this));
		
		// Placing components in the right places
		
		concretizationPanel.add(xmlTestCaseSpecificationPathLabel, gbc(0, 0, GridBagConstraints.EAST));
		concretizationPanel.add(getXmlTestCaseSpecificationPathField(), gbc(1, 0, GridBagConstraints.WEST));
		concretizationPanel.add(chooseXMLFileButton, gbc(2, 0, GridBagConstraints.WEST));
		
		concretizationPanel.add(implementationPathLabel, gbc(0, 1, GridBagConstraints.EAST));
		concretizationPanel.add(getImplementationPathField(), gbc(1, 1, GridBagConstraints.WEST));
		concretizationPanel.add(chooseImplementationFileButton, gbc(2, 1, GridBagConstraints.WEST));
		
		concretizationPanel.add(concretizeButton, gbc(1, 2, GridBagConstraints.CENTER));
		
		// Setting up frame
		
		testDataConcretizationFrame.getContentPane().add(concretizationPanel);
		testDataConcretizationFrame.pack();
		testDataConcretizationFrame.setLocationRelativeTo(null);
		testDataConcretizationFrame.setVisible(true);
		
	}
	
	
	
	private GridBagConstraints gbc(int x, int y, int anchor) {
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = x;
		gbc.gridy = y;
		gbc.anchor = anchor;

		return gbc;
	}
	
	
	
	private JLabel createLabel(String name) {
		JLabel label = new JLabel(name);
		label.setBorder(new EmptyBorder(10, 10, 10, 10));
		return label;
	}



	public JTextField getXmlTestCaseSpecificationPathField() {
		return xmlTestCaseSpecificationPathField;
	}

	

	public JTextField getImplementationPathField() {
		return implementationPathField;
	}
	
	
	
	public JFrame getTestDataConcretizationFrame() {
		return testDataConcretizationFrame;
	}
	
	
	
	public Application getApplication() {
		return application;
	}
}