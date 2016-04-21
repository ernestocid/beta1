package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import parser.Implementation;
import parser.Machine;
import parser.Operation;
import testgeneration.concretization.ConcretizeTestCaseInputs;

public class ConcretizeTestDataAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WindowForTestDataConcretizationAction windowForTestDataConcretizationAction;
	private Implementation implementation;
	private File xmlSpecification;
	private Operation operationUnderTest;
	
	public ConcretizeTestDataAction(WindowForTestDataConcretizationAction windowForTestDataConcretizationAction) {
		this.windowForTestDataConcretizationAction = windowForTestDataConcretizationAction;
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		this.xmlSpecification = new File(getWindowForTestDataConcretizationAction().getXmlTestCaseSpecificationPathField().getText());
		
		File implementationFile = new File(getWindowForTestDataConcretizationAction().getImplementationPathField().getText());
		this.implementation = new Implementation(implementationFile);		
		
		this.operationUnderTest = getOperationUnderTestFromXML(xmlSpecification);
		
		ConcretizeTestCaseInputs concretizeTestCaseInputs = new ConcretizeTestCaseInputs(xmlSpecification, operationUnderTest, implementation);
		File xmlWithConcreteData = concretizeTestCaseInputs.concretizeTestData();
		
		String sucessMessage = "A new XML file with concrete test data was created on:\n " + xmlWithConcreteData.getAbsolutePath();
		
		JOptionPane.showMessageDialog(null, sucessMessage, "Success", JOptionPane.OK_OPTION);
	}

	

	private Operation getOperationUnderTestFromXML(File xmlSpecification) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
		
        try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlSpecification);
			Element root = doc.getDocumentElement();
			NodeList operationUnderTestList = root.getElementsByTagName("operation-under-test");
			
			for(int i = 0; i < operationUnderTestList.getLength(); i++) {
				String implementationDirectory = implementation.getImplementation().getParentFile().getAbsolutePath();
				String refinedMachinePath = implementationDirectory + File.separator + implementation.getNameOfMachineRefined() + ".mch";
				String operationUnderTestName = operationUnderTestList.item(i).getTextContent();
				
				Machine refinedMachine = new Machine(new File(refinedMachinePath));
				
				for(Operation operation : refinedMachine.getOperations()) {
					if(operation.getName().equals(operationUnderTestName)) {
						return operation;
					}
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return null;
	}



	public WindowForTestDataConcretizationAction getWindowForTestDataConcretizationAction() {
		return windowForTestDataConcretizationAction;
	}
}
