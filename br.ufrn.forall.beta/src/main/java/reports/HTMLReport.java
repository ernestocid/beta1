package reports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import configurations.Configurations;
import parser.decorators.predicates.MyPredicate;
import testgeneration.BETATestCase;
import testgeneration.BETATestSuite;
import testgeneration.OracleEvaluation;
import testgeneration.coveragecriteria.InputSpacePartitionCriterion;
import testgeneration.coveragecriteria.LogicalCoverage;
import testgeneration.preamblecalculation.Event;
import tools.FileTools;

public class HTMLReport {

	private BETATestSuite testSuite;
	private File outputReport;



	public HTMLReport(BETATestSuite testSuite, File outputReport) {
		this.testSuite = testSuite;
		this.outputReport = outputReport;
	}



	public String replaceTagsWithParameters(Map<String, String> parameters, String text) {
		for (String parameter : parameters.keySet()) {
			text = text.replace(parameter, parameters.get(parameter));
		}

		return text;
	}



	public void setTestSuite(BETATestSuite testSuite) {
		this.testSuite = testSuite;
	}



	public void generateReport() {
		Map<String, String> tagsAndReplacement = new HashMap<String, String>();

		tagsAndReplacement.put("{{MACHINE_NAME}}", this.testSuite.getOperationUnderTest().getMachine().getName());
		System.out.println("Got machine name...");
		
		tagsAndReplacement.put("{{OPERATION_NAME}}", this.testSuite.getOperationUnderTest().getName());
		System.out.println("Got operation name...");
		
		tagsAndReplacement.put("{{TESTING_STRATEGY}}", getTestingStrategy());
		System.out.println("Got testing strategy...");
		
		tagsAndReplacement.put("{{COVERAGE_CRITERION}}", getCoverageCriteria());
		System.out.println("Got coverage criterion...");
		
		tagsAndReplacement.put("{{INVARIANT_CLAUSES}}", generateInvariantRestrictionsList());
		System.out.println("Got invariant clauses...");
		
		tagsAndReplacement.put("{{TESTCASE_LIST}}", createTestCaseListHTML());
		System.out.println("Created test case list...");
		
		tagsAndReplacement.put("{{UNSOLVABLE_TEST_FORMULAS}}", createUnsolvableTestFormulasList());
		System.out.println("Created infeasible test cases list...");

		String textWithReplacement = replaceTagsWithParameters(tagsAndReplacement, getReportTemplate());

		FileTools.createFileWithContent(outputReport.getPath(), textWithReplacement);
	}



	private String getCoverageCriteria() {
		return this.testSuite.getCoverageCriterion().getName();
	}



	private String getTestingStrategy() {
		if(this.testSuite.getCoverageCriterion() instanceof InputSpacePartitionCriterion) {
			return "Input Space Partition";
		} else if (this.testSuite.getCoverageCriterion() instanceof LogicalCoverage) {
			return "Logical Coverage";
		} else {
			return "Unknown";
		}
	}



	private String createUnsolvableTestFormulasList() {
		StringBuffer unsolvableTestFormulasText = new StringBuffer("<ul>\n");
		List<String> unsolvableFormulas = this.testSuite.getUnsolvableFormulas();

		if (unsolvableFormulas.isEmpty()) {
			unsolvableTestFormulasText.append("\t<li>None</li>\n");
		} else {
			int formulaId = 1;

			Collections.sort(unsolvableFormulas);

			for (String unsolvableFormula : unsolvableFormulas) {
				unsolvableTestFormulasText.append("\t<li>" + formulaId + " - " + unsolvableFormula + "</li>\n");
				formulaId++;
			}
		}

		unsolvableTestFormulasText.append("</ul>");
		return unsolvableTestFormulasText.toString();
	}



	private String generateInvariantRestrictionsList() {
		StringBuffer restrictionsList = new StringBuffer();
		restrictionsList.append("<ul>" + "\n");

		Set<MyPredicate> invariantRestrictions = this.testSuite.getOperationUnderTest().getMachine().getCondensedInvariantFromAllMachines();

		for (MyPredicate restriction : invariantRestrictions) {
			restrictionsList.append("\t\t<li>");
			restrictionsList.append(restriction.toString());
			restrictionsList.append("</li>" + "\n");
		}

		restrictionsList.append("\t</ul>");

		return restrictionsList.toString();
	}



	private String createTestCaseListHTML() {
		StringBuffer testCaseListHTML = new StringBuffer("");

		for (int i = 0; i < this.testSuite.getTestCases().size(); i++) {
			BETATestCase testCase = this.testSuite.getTestCases().get(i);
			Map<String, String> tags = new HashMap<String, String>();

			tags.put("{{TESTCASE_ID}}", Integer.toString(i + 1) + " " + isNegativeOrPositive(testCase));
			tags.put("{{TESTCASE_FORMULA}}", testCase.getTestFormulaWithoutInvariant());
			tags.put("{{PREAMBLE}}", generatePreambleList(testCase));
			tags.put("{{STATE_VARIABLES_LIST}}", generateStateVariablesList(testCase));
			tags.put("{{INPUT_VALUES_LIST}}", generateInputParametersList(testCase));

			if (Configurations.isAutomaticOracleEvaluation()) {
				tags.put("{{EXPECTED_STATE_VARIABLES_LIST}}", generateExpectedStateVariablesList(testCase));
				tags.put("{{RETURN_VARIABLES_LIST}}", generateReturnVariablesList(this.testSuite));
			} else {
				tags.put("{{EXPECTED_STATE_VARIABLES_LIST}}", generateEmptyExpectedValuesForStateVariables(testCase));
				tags.put("{{RETURN_VARIABLES_LIST}}", generateEmptyExpectedValuesForReturnVariables(testCase));
			}

			testCaseListHTML.append(replaceTagsWithParameters(tags, getTestCaseBlockTemplate()));
		}

		return testCaseListHTML.toString();
	}



	private String generatePreambleList(BETATestCase testCase) {
		StringBuffer preambleText = new StringBuffer("");
		
		List<Event> preamble = testCase.getPreamble();
		
		if(preamble.size() > 0) {
			preambleText.append("<ol>\n");

			for(Event event : preamble) {
				preambleText.append("\t<li>\n");
				preambleText.append("\t" + event.getEventName() + "(" + event.getEventParameters() + ")");
				preambleText.append("\t</li>\n");
			}

			preambleText.append("</ol>");
		} else {
			preambleText.append("<h4>No preamble generated</h4>");
		}
		
		return preambleText.toString();
	}



	private String generateEmptyExpectedValuesForReturnVariables(BETATestCase testCase) {
		Map<String, String> returnVariablesUnknown = generateUnknownValuesForExpectedReturnValues(testCase);
		StringBuffer returnVariablesList = new StringBuffer("");

		if (returnVariablesUnknown.isEmpty()) {
			returnVariablesList.append("<tr class=\"even\"><td colspan=\"2\"><center><i>This operation has no return variables</i></center></td></tr>");
		} else {
			int varCounter = 0;
			for (String variable : returnVariablesUnknown.keySet()) {

				if (varCounter % 2 == 0) {
					returnVariablesList.append("<tr class=\"even\">" + "\n");
				} else {
					returnVariablesList.append("\t\t\t<tr class=\"odd\">" + "\n");
				}

				returnVariablesList.append("\t\t\t\t<td>" + variable + "</td>" + "\n");
				returnVariablesList.append("\t\t\t\t<td><i>" + returnVariablesUnknown.get(variable) + "</i></td>" + "\n");

				returnVariablesList.append("\t\t\t</tr>" + "\n");
				varCounter++;
			}
		}

		return returnVariablesList.toString();
	}



	private Map<String, String> generateUnknownValuesForExpectedReturnValues(BETATestCase testCase) {
		List<String> returnVariables = testCase.getTestSuite().getOperationUnderTest().getReturnVariables();
		Map<String, String> unknownValues = new HashMap<String, String>();

		for (String returnVariable : returnVariables) {
			unknownValues.put(returnVariable, "Unknown");
		}

		return unknownValues;
	}



	private String generateEmptyExpectedValuesForStateVariables(BETATestCase testCase) {
		Map<String, String> stateVariablesUnknown = generateUnknownValueForExpectedStateValues(testCase);
		StringBuffer stateVariablesList = new StringBuffer("");

		if (stateVariablesUnknown.isEmpty()) {
			stateVariablesList.append("<tr class=\"even\"><td colspan=\"2\"><center><i>No related state variables for this operation</i></center></td></tr>");
		} else {
			int varCounter = 0;

			for (String variable : stateVariablesUnknown.keySet()) {
				if (varCounter % 2 == 0) {
					stateVariablesList.append("<tr class=\"even\">" + "\n");
				} else {
					stateVariablesList.append("\t\t\t<tr class=\"odd\">" + "\n");
				}

				stateVariablesList.append("\t\t\t\t<td>" + variable + "</td>" + "\n");
				stateVariablesList.append("\t\t\t\t<td><i>" + stateVariablesUnknown.get(variable) + "</i></td>" + "\n");

				stateVariablesList.append("\t\t\t</tr>" + "\n");
				varCounter++;
			}
		}

		return stateVariablesList.toString();
	}



	private String generateExpectedStateVariablesList(BETATestCase testCase) {
		StringBuffer stateVariablesList = new StringBuffer("");

		Map<String, String> expectedStateValues;

		if (testCase.isNegative()) {
			expectedStateValues = generateUnknownValueForExpectedStateValues(testCase);
		} else {
			OracleEvaluation oracleEvaluation = new OracleEvaluation(testCase, testCase.getTestSuite().getOperationUnderTest());
			expectedStateValues = oracleEvaluation.getExpectedStateValues();
		}

		if (expectedStateValues.isEmpty()) {
			stateVariablesList.append("<tr class=\"even\"><td colspan=\"2\"><center><i>No related state variables for this operation</i></center></td></tr>");
		} else {
			int varCounter = 0;

			for (String variable : expectedStateValues.keySet()) {
				if (varCounter % 2 == 0) {
					stateVariablesList.append("<tr class=\"even\">" + "\n");
				} else {
					stateVariablesList.append("\t\t\t<tr class=\"odd\">" + "\n");
				}

				stateVariablesList.append("\t\t\t\t<td>" + variable + "</td>" + "\n");
				stateVariablesList.append("\t\t\t\t<td><i>" + expectedStateValues.get(variable) + "</i></td>" + "\n");

				stateVariablesList.append("\t\t\t</tr>" + "\n");
				varCounter++;
			}
		}

		return stateVariablesList.toString();
	}



	private Map<String, String> generateUnknownValueForExpectedStateValues(BETATestCase testCase) {
		Map<String, String> expectedStateValues = new HashMap<String, String>();

		for (String stateVariable : testCase.getStateValues().keySet()) {
			expectedStateValues.put(stateVariable, "Unknown");
		}

		return expectedStateValues;
	}



	private String isNegativeOrPositive(BETATestCase betaTestCase) {
		if (betaTestCase.isNegative()) {
			return "(Negative)";
		} else {
			return "(Positive)";
		}
	}



	private String generateReturnVariablesList(BETATestSuite testSuite) {
		StringBuffer returnVariablesList = new StringBuffer("");
		List<String> returnVariables = testSuite.getOperationUnderTest().getReturnVariables();

		if (returnVariables.isEmpty()) {
			returnVariablesList.append("<tr class=\"even\"><td colspan=\"2\"><center><i>This operation has no return variables</i></center></td></tr>");
		} else {
			int varCounter = 0;
			for (String variable : returnVariables) {

				if (varCounter % 2 == 0) {
					returnVariablesList.append("<tr class=\"even\">" + "\n");
				} else {
					returnVariablesList.append("\t\t\t<tr class=\"odd\">" + "\n");
				}

				returnVariablesList.append("\t\t\t\t<td>" + variable + "</td>" + "\n");
				returnVariablesList.append("\t\t\t\t<td><i>" + " " + "</i></td>" + "\n");

				returnVariablesList.append("\t\t\t</tr>" + "\n");
				varCounter++;
			}
		}

		return returnVariablesList.toString();
	}



	private String generateInputParametersList(BETATestCase testCase) {
		StringBuffer inputParamList = new StringBuffer("");

		Map<String, String> inputParams = testCase.getInputParamValues();

		if (inputParams.isEmpty()) {
			inputParamList.append("<tr class=\"even\"><td colspan=\"2\"><center><i>This operation has no parameters</i></center></td></tr>");
		} else {
			int varCounter = 0;
			for (String param : inputParams.keySet()) {

				if (varCounter % 2 == 0) {
					inputParamList.append("<tr class=\"even\">" + "\n");
				} else {
					inputParamList.append("\t\t\t<tr class=\"odd\">" + "\n");
				}

				inputParamList.append("\t\t\t\t<td>" + param + "</td>" + "\n");
				inputParamList.append("\t\t\t\t<td><i>" + inputParams.get(param) + "</i></td>" + "\n");

				inputParamList.append("\t\t\t</tr>" + "\n");
				varCounter++;
			}
		}
		return inputParamList.toString();
	}



	private String generateStateVariablesList(BETATestCase testCase) {
		StringBuffer stateVariablesList = new StringBuffer("");

		Map<String, String> stateValues = testCase.getStateValues();

		if (stateValues.isEmpty()) {
			stateVariablesList.append("<tr class=\"even\"><td colspan=\"2\"><center><i>No related state variables for this operation</i></center></td></tr>");
		} else {
			int varCounter = 0;

			for (String variable : stateValues.keySet()) {
				if (varCounter % 2 == 0) {
					stateVariablesList.append("<tr class=\"even\">" + "\n");
				} else {
					stateVariablesList.append("\t\t\t<tr class=\"odd\">" + "\n");
				}

				stateVariablesList.append("\t\t\t\t<td>" + variable + "</td>" + "\n");
				stateVariablesList.append("\t\t\t\t<td><i>" + stateValues.get(variable) + "</i></td>" + "\n");

				stateVariablesList.append("\t\t\t</tr>" + "\n");
				varCounter++;
			}
		}

		return stateVariablesList.toString();
	}



	private String getReportTemplate() {
		String templateText = "";

		InputStream template = this.getClass().getClassLoader().getResourceAsStream("report_template.html");

		try {
			templateText = IOUtils.toString(template, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return templateText;
	}



	private String getTestCaseBlockTemplate() {
		String templateText = "";

		InputStream template = this.getClass().getClassLoader().getResourceAsStream("testcase_block_template.html");

		try {
			templateText = IOUtils.toString(template, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return templateText;
	}

}
