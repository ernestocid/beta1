package integration;

import static org.junit.Assert.*;
import general.CombinatorialCriteria;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import parser.Machine;
import parser.Operation;
import reports.HTMLReport;
import testgeneration.BETATestSuite;
import testgeneration.coveragecriteria.BoundaryValueAnalysis;
import testgeneration.coveragecriteria.ClauseCoverage;
import testgeneration.coveragecriteria.CoverageCriterion;
import testgeneration.coveragecriteria.EquivalenceClasses;
import tools.FileTools;

public class HTMLReportGenerationTest {

	@Before
	public void setUp() {
		Configurations.setMaxIntProperties(20);
		Configurations.setMinIntProperties(-1);
		Configurations.setAutomaticOracleEvaluation(true);
		Configurations.setUseKodkod(false);
		Configurations.setRandomiseEnumerationOrder(false);
		Configurations.setUseProBApiToSolvePredicates(false);
		Configurations.setFindPreamble(false);
		Configurations.setDeleteTempFiles(true);
	}



	@After
	public void tearDown() {
		Configurations.setAutomaticOracleEvaluation(false);
	}



	@Test
	public void shouldGenerateTestReportForOperationUnderTest() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimpleLift.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.EACH_CHOICE);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/simplelift_report.html"));
		report.generateReport();

		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_simplelift_report.html"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/simplelift_report.html"));

		assertEquals(expectedReport, actualReport);
	}



	@Test
	public void shouldGenerateTestReportForBoundaryValuesAllCombinations() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimpleLift.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new BoundaryValueAnalysis(operationUnderTest, CombinatorialCriteria.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/simplelift_BV_AC_report.html"));
		report.generateReport();

		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/simplelift_BV_AC_report.html"));
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_simplelift_BV_AC_report.html"));

		assertEquals(expectedReport, actualReport);
	}



	@Test
	public void shouldGenerateReportForMachineWithoutState() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/NoState.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/report_for_Set_from_NoState.html"));
		report.generateReport();

		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/report_for_Set_from_NoState.html"));
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_report_for_Set_from_NoState.html"));

		assertEquals(expectedReport, actualReport);
	}



	@Test
	public void shouldGenerateHTMLReportForOperationWithReturnValues() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Calc.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/Calc_sum_EC_AC_report.html"));
		report.generateReport();

		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_Calc_sum_EC_AC_report.html"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/Calc_sum_EC_AC_report.html"));

		assertEquals(expectedReport, actualReport);
	}



	@Test
	public void shouldGenerateHTMLReportForOperationWithParametersAndMachineWithState() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Inc.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/Inc_inc_EC_AC_report.html"));
		report.generateReport();

		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_Inc_inc_EC_AC_report.html"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/Inc_inc_EC_AC_report.html"));

		assertEquals(expectedReport, actualReport);
	}



	@Test
	public void shouldGenerateHTMLReportForWithPreambles() {
		Configurations.setFindPreamble(true);
		Configurations.setAutomaticOracleEvaluation(false);

		Machine machine = new Machine(new File("src/test/resources/machines/others/Course.mch"));
		Operation operationUnderTest = machine.getOperation(3); // student_pass_or_fail
		CoverageCriterion coverageCriterion = new ClauseCoverage(operationUnderTest);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/student_pass_or_fail_LC_CC_report.html"));
		report.generateReport();

		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_student_pass_or_fail_LC_CC_report.html"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/student_pass_or_fail_LC_CC_report.html"));

		assertEquals(expectedReport, actualReport);
	}



	// TODO: We cannot put a machine in the state we want if one of its state
	// variables is related to an abstract set.
	// The getTraceToState from prob2 api does not work for this kind of
	// example.
	// @Test
	// public void
	// shouldGenerateHTMLReportForOperationWithParametersAndMachineWithState2()
	// {
	// Machine machine = new Machine(new
	// File("src/test/resources/machines/schneider/Player.mch"));
	//
	// Operation operationUnderTest = machine.getOperation(0);
	//
	// BETATestSuite testSuite = new BETATestSuite(operationUnderTest,
	// PartitionStrategy.EQUIVALENT_CLASSES,
	// CombinatorialCriterias.ALL_COMBINATIONS);
	//
	// HTMLReport report = new HTMLReport(testSuite, new
	// File("src/test/resources/test_reports/html/Player_substitute_EC_AC_report.html"));
	// report.generateReport();
	//
	// String expectedReport = FileTools.getFileContent(new
	// File("src/test/resources/test_reports/html/expected_Inc_inc_EC_AC_report.html"));
	// String actualReport = FileTools.getFileContent(new
	// File("src/test/resources/test_reports/html/Player_substitute_EC_AC_report.html"));
	//
	// assertEquals(expectedReport, actualReport);
	// }

}
