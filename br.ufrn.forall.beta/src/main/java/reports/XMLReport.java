package reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import testgeneration.BETATestCase;
import testgeneration.BETATestSuite;
import testgeneration.OracleEvaluation;
import testgeneration.coveragecriteria.InputSpacePartitionCriterion;
import testgeneration.coveragecriteria.LogicalCoverage;
import testgeneration.preamblecalculation.Event;
import configurations.Configurations;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

public class XMLReport {

	private BETATestSuite testSuite;
	private File pathToReport;
	private Element xmlRootElement;



	public XMLReport(BETATestSuite testSuite, File pathToReport) {
		this.testSuite = testSuite;
		this.pathToReport = pathToReport;
	}



	public void generateReport() {
		setUpRootNode();
		appendMachineName();
		appendMachineInvariant();
		appendOperationUnderTest();
		appendTestingStrategy();
		appendCoverageCriteria();
		appendOracleStrategy();
		appendTestCases();

		Document doc = new Document(xmlRootElement);
		generateXMLFile(doc);
	}



	private void setUpRootNode() {
		this.xmlRootElement = new Element("test-suite");
	}



	private void appendMachineName() {
		Element machineNameElement = new Element("machine-name");
		machineNameElement.appendChild(this.testSuite.getOperationUnderTest().getMachine().getName());
		this.xmlRootElement.appendChild(machineNameElement);
	}



	private void appendMachineInvariant() {
		if (this.testSuite.getOperationUnderTest().getMachine().getInvariant() != null) {
			Element machineInvariantElement = new Element("machine-invariant");

			List<String> invariantClauses = this.testSuite.getOperationUnderTest().getMachine().getInvariant().getClausesAsList();

			for (String invariantClause : invariantClauses) {
				Element invariantClauseElement = new Element("invariant-clause");
				invariantClauseElement.appendChild(invariantClause);
				machineInvariantElement.appendChild(invariantClauseElement);
			}

			this.xmlRootElement.appendChild(machineInvariantElement);
		}
	}



	private void appendOperationUnderTest() {
		Element operationUnderTestElement = new Element("operation-under-test");
		operationUnderTestElement.appendChild(this.testSuite.getOperationUnderTest().getName());
		this.xmlRootElement.appendChild(operationUnderTestElement);
	}



	private void appendTestingStrategy() {
		Element testingStrategyElement = new Element("testing-strategy");
		testingStrategyElement.appendChild(getTestingStrategy());
		this.xmlRootElement.appendChild(testingStrategyElement);
	}



	private void appendCoverageCriteria() {
		Element coverageCriteriaElement = new Element("coverage-criteria");
		coverageCriteriaElement.appendChild(getCoverageCriteria());
		this.xmlRootElement.appendChild(coverageCriteriaElement);
	}



	private String getCoverageCriteria() {
		return this.testSuite.getCoverageCriterion().getName();
	}



	private String getTestingStrategy() {
		if (this.testSuite.getCoverageCriterion() instanceof InputSpacePartitionCriterion) {
			return "Input Space Partition";
		} else if (this.testSuite.getCoverageCriterion() instanceof LogicalCoverage) {
			return "Logical Coverage";
		} else {
			return "Unknown";
		}
	}



	private void appendOracleStrategy() {
		Element oracleStrategyElement = new Element("oracle-strategy");

		Element stateVariablesElement = new Element("state-variables");
		stateVariablesElement.appendChild(String.valueOf(Configurations.isStateVariablesActive()));

		Element returnVariablesElement = new Element("return-variables");
		returnVariablesElement.appendChild(String.valueOf(Configurations.isReturnVariablesActive()));

		Element invariantOkElement = new Element("invariant-ok");
		invariantOkElement.appendChild(String.valueOf(Configurations.isInvariantOKActive()));

		oracleStrategyElement.appendChild(stateVariablesElement);
		oracleStrategyElement.appendChild(returnVariablesElement);
		oracleStrategyElement.appendChild(invariantOkElement);

		this.xmlRootElement.appendChild(oracleStrategyElement);
	}



	private void appendTestCases() {
		Element testCasesElement = new Element("test-cases");

		for (BETATestCase testCase : this.testSuite.getTestCases()) {
			testCasesElement.appendChild(createTestCaseElement(testCase));
		}

		this.xmlRootElement.appendChild(testCasesElement);
	}



	private Element createTestCaseElement(BETATestCase testCase) {
		Element testCaseElement = new Element("test-case");

		testCaseElement.appendChild(createTestCaseIdElement(testCase));
		testCaseElement.appendChild(createTestCaseFormulaElement(testCase));
		testCaseElement.appendChild(createTestCasePreambleElement(testCase));
		testCaseElement.appendChild(createIsNegativeElement(testCase));
		testCaseElement.appendChild(createStateVariablesElement(testCase));
		testCaseElement.appendChild(createParametersElement(testCase));

		if (Configurations.isAutomaticOracleEvaluation()) {
			testCaseElement.appendChild(createExpectedStateValues(testCase));
			testCaseElement.appendChild(createReturnVariablesElement(testCase));
		}

		return testCaseElement;
	}



	private Element createTestCasePreambleElement(BETATestCase testCase) {
		Element preambleElement = new Element("preamble");
		List<Event> preamble = testCase.getPreamble();

		for (Event event : preamble) {
			preambleElement.appendChild(createPreambleEventElement(event));
		}

		return preambleElement;
	}



	private Element createPreambleEventElement(Event event) {
		Element eventElement = new Element("event");

		eventElement.appendChild(createPreambleEventNameElement(event));
		eventElement.appendChild(createPreambleEventParametersElement(event));

		return eventElement;
	}



	private Element createPreambleEventParametersElement(Event event) {
		Map<String, String> eventParameters = event.getEventParameters();

		Element eventParametersElement = new Element("event-parameters");

		if (!eventParameters.isEmpty()) {
			for (Entry<String, String> parameter : eventParameters.entrySet()) {
				Element parameterElement = new Element("parameter");

				Element parameterNameElement = new Element("parameter-name");
				parameterNameElement.appendChild(parameter.getKey());

				Element parameterValueElement = new Element("parameter-value");
				parameterValueElement.appendChild(parameter.getValue());

				parameterElement.appendChild(parameterNameElement);
				parameterElement.appendChild(parameterValueElement);

				eventParametersElement.appendChild(parameterElement);
			}
		}

		return eventParametersElement;
	}



	private Element createPreambleEventNameElement(Event event) {
		Element eventNameElement = new Element("event-name");
		eventNameElement.appendChild(event.getEventName());

		return eventNameElement;
	}



	private Element createExpectedStateValues(BETATestCase testCase) {
		Element expectedStateValuesElement = new Element("expected-state-values");

		if (testCase.isNegative()) {
			for (String variable : testCase.getStateValues().keySet()) {
				expectedStateValuesElement.appendChild(createVariableElement(variable, "unknown"));
			}
		} else {
			OracleEvaluation oracleEvaluation = new OracleEvaluation(testCase, this.testSuite.getOperationUnderTest());
			Map<String, String> expectedStateValues = oracleEvaluation.getExpectedStateValues();

			for (String variable : expectedStateValues.keySet()) {
				expectedStateValuesElement.appendChild(createVariableElement(variable, expectedStateValues.get(variable)));
			}
		}

		return expectedStateValuesElement;
	}



	private Element createTestCaseIdElement(BETATestCase testCase) {
		Element testCaseIdElement = new Element("id");
		String testCaseIdInteger = String.valueOf(this.testSuite.getTestCases().indexOf(testCase) + 1);
		testCaseIdElement.appendChild(testCaseIdInteger);
		return testCaseIdElement;
	}



	private Element createTestCaseFormulaElement(BETATestCase testCase) {
		Element testCaseFormulaElement = new Element("formula");
		testCaseFormulaElement.appendChild(testCase.getTestFormula());
		return testCaseFormulaElement;
	}



	private Element createIsNegativeElement(BETATestCase testCase) {
		Element testCaseisNegativeElement = new Element("isNegative");
		testCaseisNegativeElement.appendChild(Boolean.toString(testCase.isNegative()));
		return testCaseisNegativeElement;
	}



	private Element createStateVariablesElement(BETATestCase testCase) {
		Element stateVariablesElement = new Element("state-variables");

		for (String variable : testCase.getStateValues().keySet()) {
			stateVariablesElement.appendChild(createVariableElement(variable, testCase.getStateValues().get(variable)));
		}

		return stateVariablesElement;
	}



	private Element createVariableElement(String variable, String values) {
		Element variableElement = new Element("variable");

		variableElement.appendChild(createIdentifierElement(variable));
		variableElement.appendChild(createValuesElement(values));

		return variableElement;
	}



	private Element createIdentifierElement(String identifer) {
		Element identifierElement = new Element("identifier");
		identifierElement.appendChild(identifer);
		return identifierElement;
	}



	private Element createValuesElement(String values) {
		Element valuesElement = new Element("values");
		createValuesListElement(valuesElement, values);

		return valuesElement;
	}



	private void createValuesListElement(Element valuesElement, String values) {

		if (values != null && isSet(values)) {
			createValuesSetElement(valuesElement, values);
		} else {
			valuesElement.appendChild(createSingleValueElement(values));
		}
	}



	private Element createSingleValueElement(String values) {
		Element valueElement = new Element("value");
		valueElement.appendChild(values);
		return valueElement;
	}



	private boolean isSet(String values) {
		if (values.charAt(0) == '{' && values.charAt(values.length() - 1) == '}') {
			return true;
		} else {
			return false;
		}
	}



	private void createValuesSetElement(Element valuesListElement, String values) {

		// If values represent an empty set

		if (values.equals("{}")) {
			valuesListElement.appendChild(createEmptySetValueElement());
		} else {
			String[] valuesArray = values.substring(1, values.length() - 1).split(",");

			for (int i = 0; i < valuesArray.length; i++) {
				Element valueElement = new Element("value");
				valueElement.appendChild(valuesArray[i]);
				valuesListElement.appendChild(valueElement);
			}
		}
	}



	private Element createEmptySetValueElement() {
		Element valueElement = new Element("value");
		valueElement.appendChild("{-}");
		return valueElement;
	}



	private Element createParametersElement(BETATestCase testCase) {
		Element parametersElement = new Element("operation-parameters");

		for (String parameter : testCase.getInputParamValues().keySet()) {
			parametersElement.appendChild(createParameterElement(parameter, testCase.getInputParamValues().get(parameter)));
		}

		return parametersElement;
	}



	private Element createReturnVariablesElement(BETATestCase testCase) {
		Element returnVariablesElement = new Element("return-variables");

		for (String returnVariable : this.testSuite.getOperationUnderTest().getReturnVariables()) {
			Element returnVariableElement = new Element("variable");
			Element identifier = new Element("identifier");

			identifier.appendChild(returnVariable);
			returnVariableElement.appendChild(identifier);
			returnVariablesElement.appendChild(returnVariableElement);
		}

		return returnVariablesElement;
	}



	private Element createParameterElement(String parameter, String values) {
		Element parameterElement = new Element("parameter");

		parameterElement.appendChild(createIdentifierElement(parameter));

		Element parameterValuesElement = new Element("values");
		createValuesListElement(parameterValuesElement, values);
		parameterElement.appendChild(parameterValuesElement);

		return parameterElement;
	}



	private void generateXMLFile(Document doc) {
		try {
			FileOutputStream fos = new FileOutputStream(pathToReport);
			Serializer serializer = new Serializer(fos, "UTF-8");
			serializer.setIndent(4);
			serializer.write(doc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
