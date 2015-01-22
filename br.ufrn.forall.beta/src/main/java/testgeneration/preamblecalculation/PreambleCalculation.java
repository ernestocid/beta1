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

import machinebuilder.CBCMachineBuilder;
import parser.Machine;
import parser.Operation;
import probcliinterface.commands.GenerateCBCTestsCommand;

public class PreambleCalculation {

	private Operation operationUnderTest;
	private String stateGoal;



	public PreambleCalculation(Operation operationUnderTest, String stateGoal) {
		this.operationUnderTest = operationUnderTest;
		this.stateGoal = stateGoal;
	}



	public List<Event> getPathToState() {
		List<String> testCasePredicates = new ArrayList<String>();
		testCasePredicates.add(getStateGoal());

		CBCMachineBuilder cbcMachineBuilder = new CBCMachineBuilder(getOperationUnderTest(), testCasePredicates);
		File cbcMachine = cbcMachineBuilder.getBuiltMachine();
		File outputXMLFile = getOutputFileFor(cbcMachine);
		String operationToCover = new Machine(cbcMachine).getOperation(0).getName();

		GenerateCBCTestsCommand cbcCmd = new GenerateCBCTestsCommand(cbcMachine, operationToCover, outputXMLFile);
		cbcCmd.execute();

		List<Event> preamble = getListOfEventsFromXML(outputXMLFile);

		return preamble;
	}



	private List<Event> getListOfEventsFromXML(File outputXMLFile) {
		List<Event> preamble = new ArrayList<Event>();

		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(outputXMLFile);
			doc.getDocumentElement().normalize();

			preamble.add(getInitialisationEvent(doc));
			preamble.addAll(getOtherEvents(doc));

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return preamble;
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
		events.remove(events.size() - 1);

		return events;
	}



	private Event getInitialisationEvent(Document doc) {
		NodeList initialisation = doc.getElementsByTagName("initialisation");
		NodeList childNodes = initialisation.item(0).getChildNodes();

		Map<String, String> initialisationParameters = new HashMap<String, String>();

		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				String variableName = childNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
				String variableValue = childNodes.item(i).getTextContent();

				initialisationParameters.put(variableName, variableValue);
			}
		}

		Event initialisationEvent = new Event("initialisation", initialisationParameters);

		return initialisationEvent;
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
