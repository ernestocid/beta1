package reports;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import parser.decorators.predicates.MyPredicate;
import testgeneration.BETATestCase;
import testgeneration.BETATestSuite;
import testgeneration.OracleEvaluation;
import tools.FileTools;

public class HTMLReport {

	private BETATestSuite testSuite;
	private File outputReport;
	

	public HTMLReport(BETATestSuite testSuite, File outputReport) {
		this.testSuite = testSuite;
		this.outputReport = outputReport;
	}

	

	public String replaceTagsWithParameters(Map<String, String> parameters,	String text) {
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
		tagsAndReplacement.put("{{OPERATION_NAME}}", this.testSuite.getOperationUnderTest().getName());

		tagsAndReplacement.put("{{PARTITION_STRATEGY}}", this.testSuite.getPartitionStrategy().toString());
		tagsAndReplacement.put("{{COMBINATION_STRATEGY}}", this.testSuite.getCombinatorialCriteria().toString());
		
		tagsAndReplacement.put("{{INVARIANT_CLAUSES}}", generateInvariantRestrictionsList());

		tagsAndReplacement.put("{{TESTCASE_LIST}}", createTestCaseListHTML());

		tagsAndReplacement.put("{{UNSOLVABLE_TEST_FORMULAS}}", createUnsolvableTestFormulasList());
		
		String textWithReplacement = replaceTagsWithParameters(tagsAndReplacement, getReportTemplate());

		FileTools.createFileWithContent(outputReport.getPath(), textWithReplacement);
	}
	
	

	private String createUnsolvableTestFormulasList() {
		StringBuffer unsolvableTestFormulasText = new StringBuffer("<ul>\n");
		List<String> unsolvableFormulas = this.testSuite.getUnsolvableFormulas();
		
		if(unsolvableFormulas.isEmpty()) {
			unsolvableTestFormulasText.append("\t<li>None</li>\n");
		} else {
			int formulaId = 1;
			
			Collections.sort(unsolvableFormulas);
			
			for(String unsolvableFormula : unsolvableFormulas) {
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
			tags.put("{{STATE_VARIABLES_LIST}}", generateStateVariablesList(testCase));
			tags.put("{{INPUT_VALUES_LIST}}", generateInputParametersList(testCase));
			tags.put("{{EXPECTED_STATE_VARIABLES_LIST}}", generateExpectedStateVariablesList(testCase));
			tags.put("{{RETURN_VARIABLES_LIST}}", generateReturnVariablesList(this.testSuite));
			
			testCaseListHTML.append(replaceTagsWithParameters(tags, getTestCaseBlockTemplate()));
		}
		
		return testCaseListHTML.toString();
	}

	
	
	private String generateExpectedStateVariablesList(BETATestCase testCase) {
		StringBuffer stateVariablesList = new StringBuffer("");
		
		Map<String, String> expectedStateValues;
		
		if(testCase.isNegative()) {
			expectedStateValues = generateExpectedStateValuesForNegativeTestCase(testCase);
		} else {
			OracleEvaluation oracleEvaluation = new OracleEvaluation(testCase, this.testSuite.getOperationUnderTest());
			expectedStateValues = oracleEvaluation.getExpectedStateValues();
		}
		
		if(expectedStateValues.isEmpty()) {
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



	private Map<String, String> generateExpectedStateValuesForNegativeTestCase(BETATestCase testCase) {
		Map<String, String> expectedStateValues = new HashMap<String, String>();
		
		for(String stateVariable : testCase.getStateValues().keySet()) {
			expectedStateValues.put(stateVariable, "Unknown");
		}
		
		return expectedStateValues;
	}



	private String isNegativeOrPositive(BETATestCase betaTestCase) {
		if(betaTestCase.isNegative()) {
			return "(Negative)";
		} else {
			return "(Positive)";
		}
	}



	private String generateReturnVariablesList(BETATestSuite testSuite) {
		StringBuffer returnVariablesList = new StringBuffer("");
		List<String> returnVariables = testSuite.getOperationUnderTest().getReturnVariables();
		
		if(returnVariables.isEmpty()) {
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
		
		if(inputParams.isEmpty()) {
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
		
		if(stateValues.isEmpty()) {
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
		
		File htmlReportTemplate = new File("src/main/resources/report_template.html");
		
		templateText = FileTools.getFileContent(htmlReportTemplate);
		
//		InputStream template = HTMLReport.class.getResourceAsStream("src/main/resources/report_template.html");
//		
//		try {
//			templateText = IOUtils.toString(template, "UTF-8");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		return templateText;
	}
	
	
	
	private String getTestCaseBlockTemplate() {
		String templateText = "";
		
		File testCaseBlockTemplate = new File("src/main/resources/testcase_block_template.html");
		
		templateText = FileTools.getFileContent(testCaseBlockTemplate);
		
//		InputStream template = HTMLReport.class.getResourceAsStream("src/main/resources/testcase_block_template.html");
//		
//		System.out.println(template);
//		
//		try {
//			templateText = IOUtils.toString(template, "UTF-8");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		return templateText;
	}

}
