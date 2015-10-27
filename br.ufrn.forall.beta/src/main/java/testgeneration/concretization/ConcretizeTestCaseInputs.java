package testgeneration.concretization;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.be4.classicalb.core.parser.node.APredicateParseUnit;
import de.be4.classicalb.core.parser.node.PParseUnit;
import de.prob.animator.domainobjects.ClassicalB;
import parser.Implementation;
import parser.Operation;
import parser.decorators.expressions.MyExpression;
import parser.decorators.predicates.MyAExistsPredicate;
import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;
import testgeneration.Block;
import testgeneration.predicateevaluators.ProBApiPredicateEvaluator;
import tools.FileTools;


/**
 * This class is responsible for test data concretization. Based on the 
 * implementations glue invariant, we find the relation between the concrete
 * variables and the abstract variables, and translate the abstract data generated
 * for the test cases into concrete data that is suitable for the implementation.
 *  
 * @author ernestocid
 *
 */
public class ConcretizeTestCaseInputs {

	private File xmlReport;
	private Operation operationUnderTest;
	private Implementation implementation;


	public ConcretizeTestCaseInputs(File xmlReport, Operation operationUnderTest, Implementation implementation) {
		this.xmlReport = xmlReport;
		this.operationUnderTest = operationUnderTest;
		this.implementation = implementation;
	}

	
	/**
	 * Creates a new XML file in the same directory of the original XML report. This 
	 * new XML file contains a node for each test cast that contains the concretized 
	 * test data.
	 * 
	 * @return the new XML file with concretized data.
	 */
	public File concretizeTestData() {
		File newXMLReportWithConcretization = createNewXMLFileToAddConcretizationData();
        addConcretizationNodesToXMLReport(newXMLReportWithConcretization);
		
		return newXMLReportWithConcretization;
	}



	private void addConcretizationNodesToXMLReport(File newXMLReportWithConcretization) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
		
        try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(newXMLReportWithConcretization);
			Element root = doc.getDocumentElement();
			NodeList testCaseList = root.getElementsByTagName("test-case");
			
			for(int i = 0; i < testCaseList.getLength(); i++) {
				Node testCaseNode = testCaseList.item(i);
				appendConcretizationNodes(doc, testCaseNode);
				updateXMLFileWithConcretizationData(doc, newXMLReportWithConcretization);
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}



	private void updateXMLFileWithConcretizationData(Document doc, File newXMLReportWithConcretization) {
		DOMSource source = new DOMSource(doc);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			StreamResult result = new StreamResult(newXMLReportWithConcretization);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}



	private void appendConcretizationNodes(Document doc, Node testCaseNode) {
		Node existentialFormulaNode = getExistentialFormulaNode(testCaseNode);
		String existentialFormula = existentialFormulaNode.getTextContent();
		String concretizationFormula = createConcretizationFormula(testCaseNode, convertTestFormulaToPredicate(existentialFormula));
		
		Map<String, String> inputSpaceVariablesValues = evaluateConcreteVariables(concretizationFormula);

		appendConcretizationDataNode(doc, testCaseNode, inputSpaceVariablesValues);
	}



	private void appendConcretizationDataNode(Document doc, Node testCaseNode, Map<String, String> inputSpaceVariablesValues) {
		Element concreteDataElement = doc.createElement("data-concretization");
		
		for(Entry<String, String> concreteData : inputSpaceVariablesValues.entrySet()) {
			Element inputVariable = doc.createElement("input-variable");
			
			Element variableName = doc.createElement("variable-name");
			variableName.setTextContent(concreteData.getKey());
			
			Element variableValue = doc.createElement("variable-value");
			variableValue.setTextContent(concreteData.getValue());
			
			inputVariable.appendChild(variableName);
			inputVariable.appendChild(variableValue);
			
			concreteDataElement.appendChild(inputVariable);
		}
		
		// Hack to fix indentation
		testCaseNode.appendChild(doc.createTextNode("    "));
		testCaseNode.appendChild(concreteDataElement);
	}



	private File createNewXMLFileToAddConcretizationData() {
		String xmlReportContent = FileTools.getFileContent(xmlReport);
		File xmlReportWithConcreteData = FileTools.createFileWithContent(createXMLFileConcretizedPath(), xmlReportContent);
		return xmlReportWithConcreteData;
	}



	private Node getExistentialFormulaNode(Node testCaseNode) {
		NodeList testCaseChildren = testCaseNode.getChildNodes();
		
		for(int i = 0; i < testCaseChildren.getLength(); i++) {
			if(testCaseChildren.item(i).getNodeName().equals("existential-formula")) {
				return testCaseChildren.item(i);
			}
		}
		
		return null;
	}



	private String createXMLFileConcretizedPath() {
		String parentDirectoryPath = xmlReport.getAbsoluteFile().getParentFile().getAbsolutePath();
		String originalFileName = xmlReport.getName();
		String originalFileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
		
		return parentDirectoryPath + File.separator + originalFileNameWithoutExtension + "_concrete.xml";
	}
	


	private Map<String, String> evaluateConcreteVariables(String concretizationFormula) {
		Map<String, String> concreteVariableValues = new HashMap<String, String>();

		ProBApiPredicateEvaluator ev = new ProBApiPredicateEvaluator(getOperationUnderTest(), new HashSet<List<Block>>());
		Map<String, String> solutions = ev.evaluateFormula(concretizationFormula);

		for (String concreteVariable : implementation.getConcreteVariables()) {
			if (solutions.containsKey(concreteVariable)) {
				concreteVariableValues.put(concreteVariable, solutions.get(concreteVariable));
			}
		}
		
		for(String param : operationUnderTest.getParameters()) {
			if(solutions.containsKey(param)) {
				concreteVariableValues.put(param, solutions.get(param));
			}
		}

		return concreteVariableValues;
	}



	private String createConcretizationFormula(Node testCaseNode, MyPredicate testFormulaPredicate) {
		String concretizationFormula = "";
		StringBuffer formulaBuffer = new StringBuffer("");
		MyAExistsPredicate testCaseFormulaPredicate = null;
		
		if ((testCaseFormulaPredicate = checksTestCasePredicateValidity(testFormulaPredicate)) != null) {
			formulaBuffer.append("#" + createQuantifiedVariablesList(testFormulaPredicate));
			formulaBuffer.append("(" + getStateAndParametersValues(testCaseNode) + " & " + getTestCasePredicate(testCaseFormulaPredicate) + "&" + getImplementationInvariant() + ")");
			concretizationFormula = replaceDeferredSetsForValues(formulaBuffer);
		}
		
		return concretizationFormula;
	}

	

	private String getStateAndParametersValues(Node testCaseNode) {
		NodeList testCaseChildren = testCaseNode.getChildNodes();
		
		for (int i = 0; i < testCaseChildren.getLength(); i++) {
			if(testCaseChildren.item(i).getNodeName().equals("test-inputs-as-formula")) {
				return testCaseChildren.item(i).getTextContent();
			}
		}
		
		return "";
	}

	

	private String createQuantifiedVariablesList(MyPredicate testFormulaPredicate) {
		StringBuffer varList = new StringBuffer("");
		Set<String> variables = new HashSet<String>();
		
		if(testFormulaPredicate instanceof MyAExistsPredicate) {
			MyAExistsPredicate existentialFormula = (MyAExistsPredicate) testFormulaPredicate;
			variables.addAll(existentialFormula.getQuantifiedVariables());
		}
		
		variables.addAll(getImplementation().getConcreteVariables());

		int count = 0;
		
		for(String variable : variables) {
			if(count < variables.size() - 1) {
				varList.append(variable + ", ");
			} else {
				varList.append(variable + ".");
			}
			
			count++;
		}

		return varList.toString();
	}

	

	private String getImplementationInvariant() {
		if(getImplementation().getInvariant() != null) {
			return getImplementation().getInvariant().toString().trim();
		} else {
			return "";
		}
	}



	private String getTestCasePredicate(MyAExistsPredicate testCaseFormulaPredicate) {
		return testCaseFormulaPredicate.getQuantifiedPredicate().toString();
	}

	

	private MyAExistsPredicate checksTestCasePredicateValidity(MyPredicate testFormulaPredicate) {
		if(testFormulaPredicate instanceof MyAExistsPredicate) {
			return (MyAExistsPredicate) testFormulaPredicate;
		} else {
			return null;
		}
	}



	private String replaceDeferredSetsForValues(StringBuffer concretizationFormula) {
		String concretizationFormulaWithSetsReplaced = concretizationFormula.toString();
		
		if(implementation.getValues() != null) {
			Map<String, MyExpression> valueClauseEntries = implementation.getValues().getEntries();

			for (Entry<String, MyExpression> valuesEntry : valueClauseEntries.entrySet()) {
				concretizationFormulaWithSetsReplaced = concretizationFormulaWithSetsReplaced.replaceAll(valuesEntry.getKey(), valuesEntry.getValue().toString());
			}
		}

		return concretizationFormulaWithSetsReplaced;
	}



	private MyPredicate convertTestFormulaToPredicate(String testFormula) {
		MyPredicate predicate = null;
		PParseUnit parseUnit = new ClassicalB(testFormula).getAst().getPParseUnit();

		if (parseUnit instanceof APredicateParseUnit) {
			APredicateParseUnit predicateUnit = (APredicateParseUnit) parseUnit;
			predicate = MyPredicateFactory.convertPredicate(predicateUnit.getPredicate());
		}

		return predicate;
	}



	public File getXMLReport() {
		return xmlReport;
	}



	public Operation getOperationUnderTest() {
		return operationUnderTest;
	}



	public Implementation getImplementation() {
		return implementation;
	}
}