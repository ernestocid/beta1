package testgeneration.preamblecalculation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import configurations.Configurations;
import machinebuilder.CBCMachineBuilder;
import parser.Machine;
import parser.Operation;
import probcliinterface.commands.GenerateCBCTestsCommand;


/**
 * This class is used to build preambles for the test cases. It uses the ProB CBC test case generator 
 * to help create the preambles. To calculate a preamble it is necessary to define a state goal. A state goal
 * is a predicate that defines the state that we want the preamble to lead to.
 * 
 * @author ernestocid
 *
 */
public class PreambleCalculation {

	private Operation operationUnderTest;
	private String stateGoal;


	/**
	 * @param operationUnderTest the operation that is being tested.
	 * @param stateGoal the predicate that defines the state that we want to achieve using the preamble.
	 */
	public PreambleCalculation(Operation operationUnderTest, String stateGoal) {
		this.operationUnderTest = operationUnderTest;
		this.stateGoal = stateGoal;
	}


	/**
	 * This method creates a list of events that compose the preamble for the test case.
	 * It creates an auxiliary machine that is used to calculate the preamble
	 * using ProB's CBC algorithm. Once the machine is built, it executes the CBCTestsCommand
	 * using this machine. The command creates an XML file that is read by the method to build
	 * the list of events.
	 *  
	 * @return a List of events that compose the preamble for the test case.
	 */
	public List<Event> getPathToState() {
		CBCMachineBuilder cbcMachineBuilder = new CBCMachineBuilder(getOperationUnderTest(), getStateGoal());
		File cbcMachine = cbcMachineBuilder.getBuiltMachine();
		File outputXMLFile = getOutputFileFor(cbcMachine);

		String operationToCover = new Machine(cbcMachine).getOperation(0).getName();

		GenerateCBCTestsCommand cbcCmd = new GenerateCBCTestsCommand(cbcMachine, operationToCover, outputXMLFile);
		cbcCmd.execute();

		List<Event> preamble = getListOfEventsFromXML(outputXMLFile);

		if (Configurations.isDeleteTempFiles()) {
			cbcMachine.delete();
			outputXMLFile.delete();

			String probFilePath = cbcMachine.getAbsolutePath().replaceAll(".mch", ".prob");
			File probFile = new File(probFilePath);
			probFile.delete();
		}

		return preamble;
	}



	private List<Event> getListOfEventsFromXML(File outputXMLFile) {
		List<Event> preamble = new ArrayList<Event>();

		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(outputXMLFile);
			doc.getDocumentElement().normalize();

			if (hasAtLeastOneElementChild(doc.getElementsByTagName("extended_test_suite"))) {
				NodeList initialisationNodes = doc.getElementsByTagName("initialisation");

				if (hasAtLeastOneElementChild(initialisationNodes)) {
					preamble.add(getInitialisationEvent(initialisationNodes));
				}

				preamble.addAll(getOtherEvents(doc));
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return preamble;
	}



	private boolean hasAtLeastOneElementChild(NodeList nodeChildren) {
		for (int i = 0; i < nodeChildren.getLength(); i++) {
			if (nodeChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
				return true;
			}
		}

		return false;
	}



	private List<Event> getOtherEvents(Document doc) {
		NodeList steps = doc.getElementsByTagName("step");
		List<Event> events = new ArrayList<Event>();

		for (int i = 0; i < steps.getLength(); i++) {
			NodeList stepChildren = steps.item(i).getChildNodes();
			String eventName = steps.item(i).getAttributes().getNamedItem("name").getTextContent();

			Map<String, String> eventParameters = new HashMap<String, String>();

			for (int j = 0; j < stepChildren.getLength(); j++) {
				if (stepChildren.item(j).getNodeType() == Node.ELEMENT_NODE && stepChildren.item(j).getNodeName().equals("value")) {
					String paramName = stepChildren.item(j).getAttributes().getNamedItem("name").getTextContent();
					String paramValue = stepChildren.item(j).getTextContent();

					eventParameters.put(paramName, paramValue);
				}
			}

			events.add(new Event(eventName, eventParameters));
		}

		// Remove last event that was only used as a goal
		if (events.size() > 0) {
			events.remove(events.size() - 1);
		}

		return events;
	}



	private Event getInitialisationEvent(NodeList initialisationNodes) {
		Node someInitialisation = initialisationNodes.item(0);

		if (hasAtLeastOneElementChild(someInitialisation.getChildNodes())) {
			NodeList initialisationSteps = someInitialisation.getChildNodes();
			Map<String, String> initialisationParameters = new HashMap<String, String>();

			for (int i = 0; i < initialisationSteps.getLength(); i++) {
				if (initialisationSteps.item(i).getNodeType() == Node.ELEMENT_NODE) {
					String variableName = initialisationSteps.item(i).getAttributes().getNamedItem("name").getTextContent();
					String variableValue = initialisationSteps.item(i).getTextContent();

					initialisationParameters.put(variableName, variableValue);
				}
			}

			Event initialisationEvent = new Event("initialisation", initialisationParameters);
			return initialisationEvent;
		} else {
			Event initialisationEvent = new Event("initialisation", new HashMap<String, String>());
			return initialisationEvent;
		}
	}



	private File getOutputFileFor(File sourceMachine) {
		String outputXMLFile = sourceMachine.getParentFile().getAbsolutePath() + "/cbc_tests_for_" + operationUnderTest.getName() + "_from_"
				+ operationUnderTest.getMachine().getName() + ".xml";
		return new File(outputXMLFile);
	}



	public Operation getOperationUnderTest() {
		return operationUnderTest;
	}



	public String getStateGoal() {
		return stateGoal;
	}

}
