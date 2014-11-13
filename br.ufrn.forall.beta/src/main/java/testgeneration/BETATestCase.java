package testgeneration;

import java.util.Map;

public class BETATestCase implements Comparable<BETATestCase> {

	private String testFormula;
	private String testFormulaWithoutInvariant;
	private Map<String, String> stateValues;
	private Map<String, String> inputParamValues;
	private boolean negative;
	private BETATestSuite testSuite;



	public BETATestCase(String testFormula, String testFormulaWithoutInvariant, Map<String, String> stateValues, Map<String, String> inputParamValues,
			boolean negative, BETATestSuite testSuite) {

		this.testFormula = testFormula;
		this.testFormulaWithoutInvariant = testFormulaWithoutInvariant;
		this.stateValues = stateValues;
		this.inputParamValues = inputParamValues;
		this.negative = negative;
		this.testSuite = testSuite;
	}



	public void setTestFormula(String testFormula) {
		this.testFormula = testFormula;
	}



	public void setTestFormulaWithoutInvariant(String testFormulaWithoutInvariant) {
		this.testFormulaWithoutInvariant = testFormulaWithoutInvariant;
	}



	public void setStateValues(Map<String, String> stateValues) {
		this.stateValues = stateValues;
	}



	public void setInputParamValues(Map<String, String> inputParamValues) {
		this.inputParamValues = inputParamValues;
	}



	public String getTestFormula() {
		return this.testFormula;
	}



	public String getTestFormulaWithoutInvariant() {
		return testFormulaWithoutInvariant;
	}



	public Map<String, String> getStateValues() {
		return stateValues;
	}



	public Map<String, String> getInputParamValues() {
		return inputParamValues;
	}



	public boolean isNegative() {
		return this.negative;
	}



	@Override
	public int compareTo(BETATestCase testCase) {
		return this.getTestFormula().compareTo(testCase.getTestFormula());
	}



	public BETATestSuite getTestSuite() {
		return testSuite;
	}



	@Override
	public String toString() {
		StringBuffer testCaseRepresentation = new StringBuffer("");

		testCaseRepresentation.append("Test Formula: " + this.testFormula + "\n");
		testCaseRepresentation.append("Input Params: " + this.inputParamValues + "\n");
		testCaseRepresentation.append("State: " + this.stateValues + "\n");
		testCaseRepresentation.append("Negative: " + this.negative + "\n");

		return testCaseRepresentation.toString();
	}
}
